# Persistent TAS lab

Start the lab with `gradlew :testing:runLab`. It starts the local Parkour Gym server if needed and listens on `http://127.0.0.1:25568`. Stop the task to stop the clients and the server it started. The Gym itself serves run state on port 25566 and worker commands over WebSocket on port 25567. All three control ports bind to `127.0.0.1`.

The lab owns up to five Minecraft client processes. It starts the requested version on demand, keeps that client open after each run, and sends later recordings to the same process. Requests for a sixth version wait while all five clients are busy or starting. When one becomes idle, the lab closes the least recently used idle version to make room. The Gym never starts a client process.

Manual recording remains available with `gradlew runTasClient -PclientVersion=1.16.5` (or any supported exact version). Use `.recording start <name>` and `.recording stop <name>` in that client. A manually launched client does not join the lab's worker pool unless `-PtasWorker=true` is explicitly supplied.

To record reusable movement, use `.recording start reusable block <name>` or `.recording start reusable positions <name>`, then `.recording stop`. Leading and trailing ticks with no position change are trimmed. A positions recording stores its original start as `origin`; stand at another start and use `.recording position <recording> <positionName>` to add a named anchor. Both modes retain the same input ticks and facing. During recording, use `.recording equipment swiftSneak 2`, `.recording equipment soulSpeed 3`, or `.recording equipment depthStrider 1` to annotate equipment already worn. Use `.recording effect minecraft:speed 1 1200` to annotate an active effect (zero-based amplifier, duration in ticks), or `.recording effects clear`. The recorder captures Survival or Creative and the current Creative flying state automatically. A run cannot override either of those fields.

The existing root tasks use the lab automatically:

```powershell
.\gradlew.bat runTasWorkflow --project-prop 'tasRecording=tas-results\input.lprc' --project-prop 'tasVersions=1.8.9,1.12.2,current'
.\gradlew.bat runTasCompare --project-prop 'tasExpected=tas-results\reference.lprc' --project-prop 'tasVersion=1.12' --project-prop 'tasCompare=true' --project-prop 'tasMaxUlps=10'
```

The first invocation starts the lab in the background. For direct API use, submit JSON to `POST http://127.0.0.1:25568/api/runs`:

```json
{"clientVersion":"current","recording":"C:\\absolute\\input.lprc","output":"C:\\absolute\\actual.lprc","compare":true,"profile":"1.12","maxUlps":10}
```

For a block reusable recording, add `"placement":{"type":"block","block":"minecraft:slime_block","offsetX":1.5,"offsetZ":-2}`. The Gym builds a 14×14 pad in that worker's private world at the block's deterministic location around coordinate 5000. `GET http://127.0.0.1:25566/api/blocks` lists every available block and its pad origin. Pads are built when selected, so startup does not load hundreds of chunks. Offsets are optional; X/Z are limited to ±6.5 blocks and Y to ±4 blocks. For a named position recording use `"placement":{"type":"position","name":"left_edge"}` with the same optional offsets.

The caller can also provide `"equipment":{"swiftSneak":2,"soulSpeed":3,"depthStrider":1}` and `"potionEffects":[{"id":"minecraft:speed","amplifier":1,"durationTicks":1200}]`. Each supplied field replaces the recording's corresponding setup field. Omitted fields use the setup embedded in the recording; older v1 recordings default to Survival, no equipment, and no effects. The Gym clears prior gear and effects for every run. Unsupported enchantments or effects for the requested client version cause a request error. The output recording embeds the resolved setup, including the recorded Creative flying state.

`clientVersion` is an exact Minecraft version or `current`. `profile` selects the Legacy Parkour movement profile on the current client; omit it for native historical clients. Omit `compare` or set it to `false` to capture a version-specific reference. `GET /api/runs/<runId>` returns queued, starting, running, first failure, and final status. `GET /api/clients` lists the current pool. The Gym also exposes `POST /api/runs`, `GET /api/runs/<runId>`, and `GET /api/clients` on port 25566 for direct control of already connected workers.

`output` is optional on the coordinator API; without it, the lab creates a unique path under `tas-results/`.

At the first mismatched tick, a comparing client posts a `failure` event and requests a server snapshot. It executes 20 further movement ticks, or stops at the recording end, then posts `after_failure` with the final position and ULP distances and requests another snapshot. Clients use a 3 GB maximum heap. Their per-version `options.txt` sets two-chunk view distance, 50 FPS, and low graphics options before launch.

Each connected Minecraft player receives its own Polar world cloned from the saved `physics_test.polar` source. Interactive changes in one client's world are invisible to other clients. Save map edits to the Polar source before starting new workers; workers already connected retain their world until they disconnect. The Gym disables the Minecraft connection throttle and server rate limit, including for an existing run directory.
