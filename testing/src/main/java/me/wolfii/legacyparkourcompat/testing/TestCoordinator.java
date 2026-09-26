package me.wolfii.legacyparkourcompat.testing;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

/** Process owner and queue for reusable test clients. Paper never launches a Minecraft process. */
public final class TestCoordinator {
    private static final int MAX_CLIENTS = 5;
    private static final Gson JSON = new Gson();
    private final Path root = Path.of(System.getProperty("lpc.projectRoot")).toAbsolutePath();
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
    private final Map<String, Worker> workers = new HashMap<>();
    private final Map<String, Job> jobs = new HashMap<>();
    private final Deque<Job> queue = new ArrayDeque<>();
    private Process gym;
    private HttpServer api;

    public static void main(String[] args) throws Exception { new TestCoordinator().start(); }

    private void start() throws Exception {
        Path logs = root.resolve("testing/run/logs");
        Files.createDirectories(logs);
        if (!gymReady()) {
            gym = launch(logs.resolve("gym.log"), "runParkourGymServer");
            System.out.println("Starting Gym server; log: " + logs.resolve("gym.log"));
        }
        api = HttpServer.create(new InetSocketAddress("127.0.0.1", 25568), 32);
        api.createContext("/api/runs", this::handleRuns);
        api.createContext("/api/clients", this::handleClients);
        api.setExecutor(Executors.newCachedThreadPool());
        api.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        System.out.println("Testing coordinator: http://127.0.0.1:25568/api/runs (up to five persistent clients)");
        enqueueInitialRuns();
        while (true) {
            try { pump(logs); }
            catch (Exception failure) { System.err.println("Coordinator: " + failure.getMessage()); }
            Thread.sleep(500L);
        }
    }

    private synchronized void handleRuns(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && "/api/runs".equals(path)) {
            JsonObject request;
            try { request = JsonParser.parseString(new String(exchange.getRequestBody().readNBytes(65537), StandardCharsets.UTF_8)).getAsJsonObject(); }
            catch (RuntimeException invalid) { respond(exchange, 400, error("Invalid JSON")); return; }
            String version = field(request, "clientVersion");
            String recording = field(request, "recording");
            String output = field(request, "output");
            if (version == null || !version.matches("(?:current|[0-9]+(?:\\.[0-9]+)*(?:-forge|-fabric)?)")
                || recording == null) {
                respond(exchange, 400, error("clientVersion and recording are required")); return;
            }
            Path source, target;
            try {
                source = root.resolve(recording).toAbsolutePath().normalize();
                if (output == null) {
                    output = root.resolve("tas-results").resolve(source.getFileName().toString().replaceFirst("(?i)\\.lprc$", "")
                        + "-" + version + "-" + UUID.randomUUID() + ".lprc").toString();
                }
                target = root.resolve(output).toAbsolutePath().normalize();
                if (!Files.isRegularFile(source) || source.equals(target) || Files.exists(target)) {
                    respond(exchange, 400, error("Recording must exist; output must be a fresh, separate file")); return;
                }
            } catch (RuntimeException invalid) { respond(exchange, 400, error("Invalid path")); return; }
            for (Job existing : jobs.values()) {
                if (target.toString().equals(field(existing.request, "output"))) {
                    respond(exchange, 409, error("Output is already reserved by run " + existing.id)); return;
                }
            }
            request.addProperty("recording", source.toString());
            request.addProperty("output", target.toString());
            Job job = new Job(UUID.randomUUID().toString(), version, request);
            jobs.put(job.id, job);
            queue.add(job);
            respond(exchange, 202, JSON.toJson(job.snapshot()));
            return;
        }
        String[] parts = path.split("/");
        if ("GET".equals(exchange.getRequestMethod()) && parts.length == 4) {
            Job job = jobs.get(parts[3]);
            respond(exchange, job == null ? 404 : 200, job == null ? error("Unknown run") : JSON.toJson(job.snapshot()));
            return;
        }
        respond(exchange, 405, error("Unsupported method"));
    }

    private synchronized void handleClients(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) { respond(exchange, 405, error("GET required")); return; }
        List<JsonObject> clients = new ArrayList<>();
        for (Worker worker : workers.values()) {
            JsonObject entry = new JsonObject();
            entry.addProperty("version", worker.version);
            entry.addProperty("alive", worker.process.isAlive());
            entry.addProperty("busy", worker.busy);
            clients.add(entry);
        }
        respond(exchange, 200, JSON.toJson(clients));
    }

    private synchronized void pump(Path logs) throws Exception {
        if (!gymReady()) return;
        for (Worker worker : new ArrayList<>(workers.values())) {
            if (!worker.process.isAlive()) {
                workers.remove(worker.version);
                if (worker.job != null) worker.job.fail("Client process exited; see " + worker.log);
                for (Job queued : new ArrayList<>(queue)) {
                    if (queued.version.equals(worker.version)) {
                        queued.fail("Client process exited before registration; see " + worker.log);
                        queue.remove(queued);
                    }
                }
                continue;
            }
            if (worker.job != null && worker.job.gymRunId != null) {
                JsonObject state = getJson("http://127.0.0.1:25566/api/runs/" + worker.job.gymRunId);
                worker.job.state = field(state, "state");
                worker.job.gymState = state;
                if (state.get("complete").getAsBoolean()) {
                    System.out.println("Run " + worker.job.id + " (" + worker.version + "): " + worker.job.state);
                    worker.job = null;
                    worker.busy = false;
                    worker.lastUsed = System.nanoTime();
                }
            }
        }
        if (queue.isEmpty()) return;
        // Each version has one persistent worker. A sixth version waits while all slots are busy.
        for (Job job : new ArrayList<>(queue)) {
            Worker worker = workers.get(job.version);
            if (worker == null) {
                if (workers.size() >= MAX_CLIENTS) {
                    Worker idle = workers.values().stream().filter(w -> !w.busy && !w.starting)
                        .min((a, b) -> Long.compare(a.lastUsed, b.lastUsed)).orElse(null);
                    if (idle == null) continue;
                    stopProcess(idle.process);
                    workers.remove(idle.version);
                }
                Path log = logs.resolve("client-" + job.version.replaceAll("[^A-Za-z0-9._-]", "_") + ".log");
                String workerId = UUID.randomUUID().toString();
                List<String> command = new ArrayList<>();
                command.add("current".equals(job.version) ? "runCurrentTasClient" : "runTasClient");
                if (!"current".equals(job.version)) { command.add("-p"); command.add("tas-client"); }
                command.add("--project-prop"); command.add("clientVersion=" + job.version);
                command.add("--project-prop"); command.add("tasWorker=true");
                command.add("--project-prop"); command.add("tasWorkerId=" + workerId);
                Process process = launch(log, command.toArray(String[]::new));
                worker = new Worker(job.version, workerId, process, log);
                workers.put(job.version, worker);
                System.out.println("Starting client " + job.version + "; log: " + log);
            }
            if (worker.busy) continue;
            if (!registered(worker)) {
                job.state = "starting_client";
                continue;
            }
            worker.starting = false;
            JsonObject response;
            try { response = postJson("http://127.0.0.1:25566/api/runs", job.request); }
            catch (IOException rejected) {
                if (rejected.getMessage().startsWith("Gym HTTP 400") || rejected.getMessage().startsWith("Gym HTTP 409")) {
                    job.fail(rejected.getMessage());
                    queue.remove(job);
                    continue;
                }
                throw rejected;
            }
            if (response.has("runId")) {
                job.gymRunId = response.get("runId").getAsString();
                job.state = "queued_on_client";
                worker.busy = true;
                worker.job = job;
                queue.remove(job);
            }
        }
    }

    private boolean registered(Worker worker) throws Exception {
        JsonObject[] clients = JSON.fromJson(http.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:25566/api/clients"))
            .timeout(Duration.ofSeconds(3)).build(), HttpResponse.BodyHandlers.ofString()).body(), JsonObject[].class);
        for (JsonObject client : clients) {
            if (worker.version.equals(field(client, "version")) && worker.id.equals(field(client, "id"))) return true;
        }
        return false;
    }

    private boolean gymReady() {
        try { return http.send(HttpRequest.newBuilder(URI.create("http://127.0.0.1:25566/api/clients"))
            .timeout(Duration.ofSeconds(1)).build(), HttpResponse.BodyHandlers.discarding()).statusCode() == 200; }
        catch (Exception absent) { return false; }
    }

    private JsonObject getJson(String url) throws Exception {
        HttpResponse<String> response = http.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(3)).build(),
            HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Gym HTTP " + response.statusCode());
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    private JsonObject postJson(String url, JsonObject value) throws Exception {
        HttpResponse<String> response = http.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(3))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(JSON.toJson(value))).build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 202) throw new IOException("Gym HTTP " + response.statusCode() + ": " + response.body());
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    private Process launch(Path log, String... arguments) throws IOException {
        List<String> command = new ArrayList<>();
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            command.add("cmd"); command.add("/c"); command.add(root.resolve("gradlew.bat").toString());
        } else command.add(root.resolve("gradlew").toString());
        for (String argument : arguments) command.add(argument);
        command.add("--no-daemon");
        ProcessBuilder builder = new ProcessBuilder(command).directory(root.toFile()).redirectErrorStream(true).redirectOutput(log.toFile());
        return builder.start();
    }

    private void enqueueInitialRuns() {
        String versions = System.getProperty("lpc.tasVersions");
        String recording = System.getProperty("lpc.tasRecording");
        if (versions == null || recording == null) return;
        String outputDir = System.getProperty("lpc.tasOutputDir", "tas-results");
        for (String version : versions.split(",")) {
            version = version.trim();
            if (version.isEmpty()) continue;
            JsonObject request = new JsonObject();
            request.addProperty("clientVersion", version);
            request.addProperty("recording", root.resolve(recording).toAbsolutePath().toString());
            request.addProperty("output", root.resolve(outputDir).resolve(Path.of(recording).getFileName().toString()
                .replaceFirst("(?i)\\.lprc$", "") + "-" + version + "-" + UUID.randomUUID() + ".lprc").toString());
            request.addProperty("compare", Boolean.parseBoolean(System.getProperty("lpc.tasCompare", "false")));
            String profile = System.getProperty("lpc.tasProfile");
            if (profile != null) request.addProperty("profile", profile);
            String ulps = System.getProperty("lpc.tasMaxUlps");
            if (ulps != null) request.addProperty("maxUlps", Long.parseLong(ulps));
            Job job = new Job(UUID.randomUUID().toString(), version, request);
            synchronized (this) { jobs.put(job.id, job); queue.add(job); }
            System.out.println("Queued " + version + " as run " + job.id);
        }
    }

    private void stop() {
        if (api != null) api.stop(0);
        for (Worker worker : workers.values()) stopProcess(worker.process);
        if (gym != null) stopProcess(gym);
    }

    private static void stopProcess(Process process) {
        process.descendants().forEach(ProcessHandle::destroy);
        process.destroy();
    }

    private static String field(JsonObject object, String key) {
        try { return object.has(key) && !object.get(key).isJsonNull() ? object.get(key).getAsString() : null; }
        catch (RuntimeException invalid) { return null; }
    }

    private static String error(String message) { JsonObject value = new JsonObject(); value.addProperty("error", message); return JSON.toJson(value); }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) { output.write(bytes); }
    }

    private static final class Worker {
        final String version, id;
        final Process process;
        final Path log;
        long lastUsed = System.nanoTime();
        boolean busy;
        boolean starting = true;
        Job job;
        Worker(String version, String id, Process process, Path log) { this.version = version; this.id = id; this.process = process; this.log = log; }
    }

    private static final class Job {
        final String id, version;
        final JsonObject request;
        String state = "waiting_for_client";
        String gymRunId;
        JsonObject gymState;
        Job(String id, String version, JsonObject request) { this.id = id; this.version = version; this.request = request; }
        void fail(String reason) { state = "error"; gymState = new JsonObject(); gymState.addProperty("error", reason); }
        JsonObject snapshot() {
            JsonObject value = new JsonObject();
            value.addProperty("runId", id);
            value.addProperty("clientVersion", version);
            value.addProperty("state", state);
            if (gymRunId != null) value.addProperty("gymRunId", gymRunId);
            if (gymState != null) value.add("gym", gymState);
            return value;
        }
    }
}
