# Persistent TAS lab

Start the lab with `gradlew :testing:runLab`. It starts the local Parkour Gym server if needed and listens on `http://127.0.0.1:25568`. Stop the task to stop the clients and the server it started. The Gym itself serves run state on port 25566 and worker commands over WebSocket on port 25567. All three control ports bind to `127.0.0.1`.

The lab owns up to five Minecraft client processes. It starts the requested version on demand, keeps that client open after each run, and sends later recordings to the same process. Requests for a sixth version wait while all five clients are busy or starting. When one becomes idle, the lab closes the least recently used idle version to make room. The Gym never starts a client process.

Manual recording remains available with `gradlew runTasClient -PclientVersion=1.16.5` (or any supported exact version). Use `.recording start <name>` and `.recording stop <name>` in that client. A manually launched client does not join the lab's worker pool unless `-PtasWorker=true` is explicitly supplied.

The existing root tasks use the lab automatically:

```powershell
.\gradlew.bat runTasWorkflow --project-prop 'tasRecording=tas-results\input.lprc' --project-prop 'tasVersions=1.8.9,1.12.2,current'
.\gradlew.bat runTasCompare --project-prop 'tasExpected=tas-results\reference.lprc' --project-prop 'tasVersion=1.12' --project-prop 'tasCompare=true' --project-prop 'tasMaxUlps=10'
```

The first invocation starts the lab in the background. For direct API use, submit JSON to `POST http://127.0.0.1:25568/api/runs`:

```json
{"clientVersion":"current","recording":"C:\\absolute\\input.lprc","output":"C:\\absolute\\actual.lprc","compare":true,"profile":"1.12","maxUlps":10}
```

`clientVersion` is an exact Minecraft version or `current`. `profile` selects the Legacy Parkour movement profile on the current client; omit it for native historical clients. Omit `compare` or set it to `false` to capture a version-specific reference. `GET /api/runs/<runId>` returns queued, starting, running, first failure, and final status. `GET /api/clients` lists the current pool. The Gym also exposes `POST /api/runs`, `GET /api/runs/<runId>`, and `GET /api/clients` on port 25566 for direct control of already connected workers.

`output` is optional on the coordinator API; without it, the lab creates a unique path under `tas-results/`.

At the first mismatched tick, a comparing client posts a `failure` event and requests a server snapshot. It executes 20 further movement ticks, or stops at the recording end, then posts `after_failure` with the final position and ULP distances and requests another snapshot. Clients use a 3 GB maximum heap. Their per-version `options.txt` sets two-chunk view distance, 50 FPS, and low graphics options before launch.

Each connected Minecraft player receives its own Polar world cloned from the saved `physics_test.polar` source. Interactive changes in one client's world are invisible to other clients. Save map edits to the Polar source before starting new workers; workers already connected retain their world until they disconnect.
