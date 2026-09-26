package me.wolfii.legacyparkourcompat.physicstest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/** Local control plane. Minecraft traffic stays on 25565; HTTP and WebSocket never touch a game tick. */
final class GymControlApi implements AutoCloseable {
    static final int HTTP_PORT = 25566;
    static final int WS_PORT = 25567;
    private static final int MAX_WORKERS = 5;
    private static final Gson JSON = new Gson();
    private final JavaPlugin plugin;
    private final Map<String, Worker> workers = new ConcurrentHashMap<>();
    private final Map<String, Run> runs = new ConcurrentHashMap<>();
    private HttpServer http;
    private ServerSocket websocket;
    private volatile boolean running;

    GymControlApi(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    void start() throws IOException {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        http = HttpServer.create(new InetSocketAddress(loopback, HTTP_PORT), 32);
        http.createContext("/api/clients", this::clients);
        http.createContext("/api/runs", this::runs);
        http.setExecutor(Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "lpc-gym-http");
            thread.setDaemon(true);
            return thread;
        }));
        websocket = new ServerSocket(WS_PORT, 16, loopback);
        running = true;
        http.start();
        Thread acceptor = new Thread(this::accept, "lpc-gym-websocket");
        acceptor.setDaemon(true);
        acceptor.start();
        plugin.getLogger().info("Gym API listening on 127.0.0.1:" + HTTP_PORT + "; workers connect to ws://127.0.0.1:" + WS_PORT + "/workers");
    }

    private void clients(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) { respond(exchange, 405, error("GET required")); return; }
        List<JsonObject> entries = new ArrayList<>();
        for (Worker worker : workers.values()) {
            JsonObject item = new JsonObject();
            item.addProperty("id", worker.id);
            item.addProperty("version", worker.version);
            item.addProperty("busy", worker.runId != null);
            entries.add(item);
        }
        respond(exchange, 200, JSON.toJson(entries));
    }

    private void runs(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("/api/runs".equals(path) && "POST".equals(exchange.getRequestMethod())) {
            createRun(exchange);
            return;
        }
        String[] parts = path.split("/");
        if (parts.length < 4 || !"api".equals(parts[1]) || !"runs".equals(parts[2])) {
            respond(exchange, 404, error("Unknown endpoint")); return;
        }
        Run run = runs.get(parts[3]);
        if (run == null) { respond(exchange, 404, error("Unknown run")); return; }
        if (parts.length == 4 && "GET".equals(exchange.getRequestMethod())) {
            respond(exchange, 200, JSON.toJson(run.snapshot()));
        } else if (parts.length == 5 && "events".equals(parts[4]) && "POST".equals(exchange.getRequestMethod())) {
            postEvent(exchange, run);
        } else {
            respond(exchange, 405, error("Unsupported method"));
        }
    }

    private void createRun(HttpExchange exchange) throws IOException {
        JsonObject request;
        try { request = JsonParser.parseString(readBody(exchange)).getAsJsonObject(); }
        catch (RuntimeException invalid) { respond(exchange, 400, error("Invalid JSON")); return; }
        String version = string(request, "clientVersion");
        String recording = string(request, "recording");
        String output = string(request, "output");
        if (version == null || recording == null || output == null) {
            respond(exchange, 400, error("clientVersion, recording and output are required")); return;
        }
        Path source;
        Path target;
        try {
            source = Path.of(recording).toAbsolutePath().normalize();
            target = Path.of(output).toAbsolutePath().normalize();
            if (!Files.isRegularFile(source) || source.equals(target) || Files.exists(target)) {
                respond(exchange, 400, error("Recording must exist and output must be a fresh, separate path")); return;
            }
            if (request.has("maxUlps") && (request.get("maxUlps").getAsLong() < 0 || request.get("maxUlps").getAsLong() > Long.MAX_VALUE)) {
                respond(exchange, 400, error("maxUlps must be non-negative")); return;
            }
        } catch (RuntimeException invalid) { respond(exchange, 400, error("Invalid run settings")); return; }
        Worker selected = null;
        Run run;
        synchronized (workers) {
            for (Worker worker : workers.values()) {
                if (worker.version.equals(version) && worker.runId == null) { selected = worker; break; }
            }
            if (selected == null) { respond(exchange, 409, error("No idle worker for " + version)); return; }
            run = new Run(UUID.randomUUID().toString(), version, selected.id);
            selected.runId = run.id;
            runs.put(run.id, run);
        }
        request.addProperty("type", "run");
        request.addProperty("runId", run.id);
        request.addProperty("recording", source.toString());
        request.addProperty("output", target.toString());
        try {
            selected.send(JSON.toJson(request));
        } catch (IOException failure) {
            synchronized (workers) { selected.runId = null; }
            run.event("error", "Worker connection failed: " + failure.getMessage());
            respond(exchange, 503, JSON.toJson(run.snapshot()));
            return;
        }
        respond(exchange, 202, JSON.toJson(run.snapshot()));
    }

    private void postEvent(HttpExchange exchange, Run run) throws IOException {
        JsonObject event;
        try { event = JsonParser.parseString(readBody(exchange)).getAsJsonObject(); }
        catch (RuntimeException invalid) { respond(exchange, 400, error("Invalid JSON")); return; }
        String workerId = string(event, "workerId");
        String type = string(event, "type");
        if (!run.workerId.equals(workerId) || type == null || !List.of("started", "failure", "after_failure", "success", "error").contains(type)) {
            respond(exchange, 400, error("Invalid run event")); return;
        }
        synchronized (run) {
            if (run.complete) { respond(exchange, 409, error("Run already complete")); return; }
            run.event(type, JSON.toJson(event));
        }
        if (run.complete) {
            Worker worker = workers.get(run.workerId);
            if (worker != null && run.id.equals(worker.runId)) worker.runId = null;
        }
        respond(exchange, 200, JSON.toJson(run.snapshot()));
    }

    private void accept() {
        while (running) {
            try {
                Socket socket = websocket.accept();
                Thread thread = new Thread(() -> handleSocket(socket), "lpc-gym-worker");
                thread.setDaemon(true);
                thread.start();
            } catch (IOException failure) {
                if (running) plugin.getLogger().log(Level.WARNING, "WebSocket accept failed", failure);
            }
        }
    }

    private void handleSocket(Socket socket) {
        Worker worker = null;
        try (socket) {
            socket.setSoTimeout(10000);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            String headers = readHeaders(input);
            String[] lines = headers.split("\r\n");
            if (lines.length == 0 || !lines[0].startsWith("GET /workers?")) return;
            Map<String, String> query = query(lines[0].substring("GET /workers?".length(), lines[0].indexOf(' ', 5)));
            String id = query.get("id"), version = query.get("version"), key = null;
            for (String line : lines) if (line.regionMatches(true, 0, "Sec-WebSocket-Key:", 0, 18)) key = line.substring(18).trim();
            if (id == null || version == null || key == null || id.length() > 64 || version.length() > 32) return;
            synchronized (workers) {
                if (workers.size() >= MAX_WORKERS || workers.containsKey(id)) {
                    output.write("HTTP/1.1 503 Service Unavailable\r\nContent-Length: 0\r\n\r\n".getBytes(StandardCharsets.US_ASCII)); return;
                }
                String accept = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                    .digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(StandardCharsets.US_ASCII)));
                output.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: " + accept + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
                output.flush();
                worker = new Worker(id, version, output);
                workers.put(id, worker);
            }
            socket.setSoTimeout(0);
            while (running && input.read() != -1) {
                // Workers send run events through HTTP. Any WebSocket frame here is a close frame.
                break;
            }
        } catch (Exception failure) {
            if (running) plugin.getLogger().log(Level.FINE, "Worker disconnected", failure);
        } finally {
            if (worker != null) {
                workers.remove(worker.id, worker);
                if (worker.runId != null) {
                    Run run = runs.get(worker.runId);
                    if (run != null) run.event("error", "Worker disconnected");
                }
            }
        }
    }

    private static String readHeaders(InputStream input) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        int current;
        while ((current = input.read()) != -1 && bytes.size() < 8192) {
            bytes.write(current);
            byte[] data = bytes.toByteArray();
            int n = data.length;
            if (n >= 4 && data[n - 4] == '\r' && data[n - 3] == '\n' && data[n - 2] == '\r' && data[n - 1] == '\n') return bytes.toString(StandardCharsets.US_ASCII);
        }
        throw new IOException("Invalid WebSocket handshake");
    }

    private static Map<String, String> query(String source) throws IOException {
        Map<String, String> values = new HashMap<>();
        for (String pair : source.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) values.put(URLDecoder.decode(parts[0], "UTF-8"), URLDecoder.decode(parts[1], "UTF-8"));
        }
        return values;
    }

    private static String string(JsonObject object, String key) {
        try { return object.has(key) && !object.get(key).isJsonNull() ? object.get(key).getAsString() : null; }
        catch (RuntimeException invalid) { return null; }
    }

    private static String readBody(HttpExchange exchange) throws IOException {
        byte[] bytes = exchange.getRequestBody().readNBytes(65537);
        if (bytes.length > 65536) throw new IOException("Request too large");
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static String error(String message) { JsonObject value = new JsonObject(); value.addProperty("error", message); return JSON.toJson(value); }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) { output.write(bytes); }
    }

    @Override public void close() throws IOException {
        running = false;
        if (http != null) http.stop(0);
        if (websocket != null) websocket.close();
    }

    private static final class Worker {
        final String id, version;
        final OutputStream output;
        volatile String runId;
        Worker(String id, String version, OutputStream output) { this.id = id; this.version = version; this.output = output; }
        synchronized void send(String message) throws IOException {
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            if (data.length > 65535) throw new IOException("Run command too large");
            output.write(0x81);
            if (data.length < 126) output.write(data.length);
            else { output.write(126); output.write(data.length >>> 8); output.write(data.length); }
            output.write(data);
            output.flush();
        }
    }

    private static final class Run {
        final String id, version, workerId;
        final List<JsonObject> events = new ArrayList<>();
        String state = "queued";
        boolean complete;
        Run(String id, String version, String workerId) { this.id = id; this.version = version; this.workerId = workerId; }
        synchronized void event(String type, String detail) {
            JsonObject event = new JsonObject();
            event.addProperty("type", type);
            event.addProperty("detail", detail);
            event.addProperty("time", System.currentTimeMillis());
            events.add(event);
            state = type;
            complete = "success".equals(type) || "after_failure".equals(type) || "error".equals(type);
        }
        synchronized JsonObject snapshot() {
            JsonObject value = new JsonObject();
            value.addProperty("runId", id);
            value.addProperty("clientVersion", version);
            value.addProperty("workerId", workerId);
            value.addProperty("state", state);
            value.addProperty("complete", complete);
            value.add("events", JSON.toJsonTree(events));
            return value;
        }
    }
}
