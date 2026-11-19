package me.wolfii.legacyparkourcompat.recording;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** A persistent task client. WebSocket commands are queued for the Minecraft thread. */
final class TaskWorker {
    private final String host;
    private final String id;
    private final String version;
    private final BlockingQueue<Command> commands = new LinkedBlockingQueue<Command>();
    private final BlockingQueue<Event> events = new LinkedBlockingQueue<Event>();
    private volatile long gameSession;

    TaskWorker(String host, String version) {
        this.host = host;
        this.version = version;
        this.id = System.getProperty("legacyparkour.tas.workerId", UUID.randomUUID().toString());
        Thread receiver = new Thread(new Runnable() { @Override public void run() { receiveLoop(); } }, "lpc-task-commands");
        receiver.setDaemon(true);
        receiver.start();
        Thread reporter = new Thread(new Runnable() { @Override public void run() { eventLoop(); } }, "lpc-task-events");
        reporter.setDaemon(true);
        reporter.start();
    }

    void gameSession(long gameSession) { this.gameSession = gameSession; }

    JsonObject poll() {
        Command command;
        while ((command = commands.poll()) != null) {
            if (command.gameSession == gameSession) return command.payload;
        }
        return null;
    }

    String id() { return id; }

    void event(String runId, String type, String detail, int tick) {
        events.add(new Event(runId, type, detail, tick));
    }

    private void receiveLoop() {
        while (true) {
            try (Socket socket = new Socket(host, GymPorts.WEBSOCKET)) {
                socket.setSoTimeout(15000);
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                byte[] random = new byte[16];
                new SecureRandom().nextBytes(random);
                String key = Base64.getEncoder().encodeToString(random);
                String path = "/workers?id=" + URLEncoder.encode(id, "UTF-8") + "&version=" + URLEncoder.encode(version, "UTF-8");
                String handshake = "GET " + path + " HTTP/1.1\r\nHost: " + host + ':' + GymPorts.WEBSOCKET
                    + "\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Version: 13\r\nSec-WebSocket-Key: " + key + "\r\n\r\n";
                output.write(handshake.getBytes(StandardCharsets.US_ASCII));
                output.flush();
                String response = readHeaders(input);
                if (!response.startsWith("HTTP/1.1 101 ")) throw new IOException("Gym rejected worker: " + response.split("\r\n")[0]);
                socket.setSoTimeout(0);
                while (true) {
                    int first = input.read();
                    if (first < 0) throw new IOException("Gym WebSocket closed");
                    int second = input.read();
                    if (second < 0) throw new IOException("Truncated WebSocket frame");
                    int length = second & 127;
                    if (length == 126) length = (input.read() << 8) | input.read();
                    if (length < 0 || length > 65536 || (second & 128) != 0) throw new IOException("Invalid Gym frame");
                    byte[] data = new byte[length];
                    readFully(input, data);
                    if ((first & 15) == 8) throw new IOException("Gym closed worker WebSocket");
                    if ((first & 15) == 1) {
                        long session = gameSession;
                        if (session == 0L) continue;
                        JsonObject command = new JsonParser().parse(new String(data, StandardCharsets.UTF_8)).getAsJsonObject();
                        // A run from the previous game connection may arrive late on this separate socket.
                        if (session == 1L || !isRunComplete(command.get("runId").getAsString())) {
                            commands.add(new Command(session, command));
                        }
                    }
                }
            } catch (Exception failure) {
                System.err.println("[Legacy Parkour Recording] Gym WebSocket: " + failure.getMessage());
                try { Thread.sleep(2000L); } catch (InterruptedException interrupted) { Thread.currentThread().interrupt(); return; }
            }
        }
    }

    private boolean isRunComplete(String runId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http", host, GymPorts.HTTP,
            "/api/runs/" + URLEncoder.encode(runId, "UTF-8")).openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        try {
            int status = connection.getResponseCode();
            if (status != 200) throw new IOException("Could not check Gym run " + runId + ": HTTP " + status);
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                return new JsonParser().parse(reader).getAsJsonObject().get("complete").getAsBoolean();
            }
        } finally {
            connection.disconnect();
        }
    }

    private void eventLoop() {
        while (true) {
            try {
                Event event = events.take();
                sendEventWithRetry(event);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void sendEventWithRetry(Event event) {
        for (int attempt = 1; attempt <= 10; attempt++) {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("workerId", id);
                json.addProperty("type", event.type);
                json.addProperty("detail", event.detail);
                json.addProperty("tick", event.tick);
                byte[] body = json.toString().getBytes(StandardCharsets.UTF_8);
                HttpURLConnection connection = (HttpURLConnection) new URL("http", host, GymPorts.HTTP,
                    "/api/runs/" + URLEncoder.encode(event.runId, "UTF-8") + "/events").openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                try (OutputStream output = connection.getOutputStream()) { output.write(body); }
                int status = connection.getResponseCode();
                connection.disconnect();
                if (status != 200 && status != 409) throw new IOException("Gym rejected " + event.type + " event: HTTP " + status);
                return;
            } catch (Exception failure) {
                System.err.println("[Legacy Parkour Recording] Could not notify Gym: " + failure.getMessage());
                if (attempt == 10) return;
                try { Thread.sleep(500L); }
                catch (InterruptedException interrupted) { Thread.currentThread().interrupt(); return; }
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
            if (n >= 4 && data[n - 4] == '\r' && data[n - 3] == '\n' && data[n - 2] == '\r' && data[n - 1] == '\n') return new String(data, StandardCharsets.US_ASCII);
        }
        throw new IOException("Invalid Gym WebSocket handshake");
    }

    private static void readFully(InputStream input, byte[] data) throws IOException {
        int position = 0;
        while (position < data.length) {
            int count = input.read(data, position, data.length - position);
            if (count < 0) throw new IOException("Truncated Gym WebSocket frame");
            position += count;
        }
    }

    private static final class Command {
        final long gameSession;
        final JsonObject payload;

        Command(long gameSession, JsonObject payload) {
            this.gameSession = gameSession;
            this.payload = payload;
        }
    }

    private static final class Event {
        final String runId, type, detail;
        final int tick;
        Event(String runId, String type, String detail, int tick) {
            this.runId = runId; this.type = type; this.detail = detail; this.tick = tick;
        }
    }

    private static final class GymPorts {
        static final int HTTP = 25566;
        static final int WEBSOCKET = 25567;
    }
}
