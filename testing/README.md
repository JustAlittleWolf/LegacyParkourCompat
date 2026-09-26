# Persistent TAS lab and comparison

`testing/` runs the coordinator for automated movement tests. Start it explicitly with `.\gradlew.bat :testing:runLab` from the repository root, or let `runTasWorkflow` and `runTasCompare` start it on demand. The coordinator starts the [Parkour Gym](../parkourgym-server/README.md) if needed, starts exact-version [TAS clients](../tas-client/README.md), and owns their processes. Stopping an explicit `runLab` task stops the clients and the Gym instance it started.

The lab listens on `127.0.0.1:25568` (HTTP). The Gym listens on `127.0.0.1:25565` (Minecraft), `25566` (HTTP run state), and `25567` (task-client WebSocket). Control ports bind to localhost. The Gym never launches clients. The lab keeps up to five clients open after runs and queues additional requests. If another version needs a slot, it closes the least recently used idle client; busy clients finish their run first. Each connected player has a private Polar world, so concurrent interactions do not affect one another.

## Gradle workflows

Capture a version-specific reference from one input recording across several native clients:

```powershell
.\gradlew.bat runTasWorkflow -PtasRecording=recordings\jump.lprc -PtasVersions=1.8.9,1.12.2,1.16.5,current
```

Results are written under `tas-results/` as `<recording-name>-<version>-<run-id>.lprc`; a `<recording-name>-manifest.tsv` lists them. A reference capture does not compare source XYZ, since movement is expected to differ between versions. Each result stores the source inputs and observed post-movement positions.

Compare the current client using a chosen historical movement profile against the matching version-specific reference:

```powershell
.\gradlew.bat runTasCompare -PtasExpected=tas-results\jump-1.12.lprc -PtasVersion=1.12 -PtasCompare=true -PtasMaxUlps=10
```

`tasExpected` must be the reference for the selected `tasVersion`, not the cross-version input file. `-PtasOutput=<file.lprc>` optionally chooses the actual output path; it must not exist already. Without it, the task creates a unique file beside the reference. Gradle checks every captured position and the tick count and fails with the first tick, expected and actual XYZ, run ID, and snapshot log path when they differ.

Comparison is exact by default (`Double.compare` on each coordinate, including distinct signed zeros). `-PtasMaxUlps=<integer>` allows a finite per-coordinate ULP distance and reports all three distances at a failing tick plus the maximum observed distance. Adjacent finite negative values and subnormals have distance one; `-0.0` and `+0.0` have ULP distance zero. NaN and infinity fail. `-PtasTolerance=<finite number>` instead allows an absolute per-coordinate difference. The two tolerance options cannot be combined. The final comparison also requires the exact tick count.

For direct one-client automation, `runTasClient` accepts `-PtasRecording=<file>`, `-PtasOutput=<file>`, and `-PtasServer=localhost:25565`; it passes them to the client as JVM properties. A normal `runTasClient` launch is for manual use and does not join the lab's worker pool unless `-PtasWorker=true` is supplied.

## Direct run API

Submit a JSON request to `POST http://127.0.0.1:25568/api/runs`:

```json
{"clientVersion":"current","recording":"C:\\absolute\\input.lprc","output":"C:\\absolute\\actual.lprc","compare":true,"profile":"1.12","maxUlps":10}
```

`clientVersion` is an exact release or `current`. `profile` selects the Legacy Parkour movement version on `current`; omit it for a native historical client. Omit `compare` or set it to `false` to capture a reference. `output` is optional on the coordinator API; it then chooses a unique path under `tas-results/`. `GET /api/runs/<runId>` reports queued, starting, running, first failure, and final status; `GET /api/clients` lists the pool. The Gym also exposes `POST /api/runs`, `GET /api/runs/<runId>`, and `GET /api/clients` on port 25566 for direct control of connected workers.

### Reusable placement and setup

A [reusable block recording](../tas-client/README.md) can be placed on a selected block surface:

```json
{"placement":{"type":"block","block":"minecraft:slime_block","offsetX":1.5,"offsetZ":-2}}
```

`GET http://127.0.0.1:25566/api/blocks` lists available blocks and pad origins. The Gym builds a 14×14 pad in the worker's private world at a deterministic location around coordinate 5000 when requested, avoiding hundreds of loaded chunks at startup. X/Z offsets are limited to ±6.5 blocks and Y to ±4 blocks; omitted offsets are zero. For a named-position recording, use `{"placement":{"type":"position","name":"left_edge"}}` with optional offsets. The original recorded start is named `origin`.

Equipment and effects may be embedded in the recording or chosen per run:

```json
{"equipment":{"swiftSneak":2,"soulSpeed":3,"depthStrider":1},"potionEffects":[{"id":"minecraft:speed","amplifier":1,"durationTicks":1200}]}
```

Each supplied field replaces that field from the recording. Omitted fields use its saved setup. Older v1 recordings default to Survival, no equipment, and no effects. The Gym clears prior gear and effects before every run. Unsupported enchantments or effects for the requested historical client fail the request. The recorded game mode and Creative flying state cannot be overridden. The output `.lprc` embeds the resolved setup.

## Failures and isolation

At the first mismatched tick, the client posts a `failure` event and asks the Gym for a server snapshot. It continues for 20 movement ticks (one second), or until the recording ends, then posts `after_failure` with final position and ULP distances and asks for a second snapshot. Search `parkourgym-server/run/logs/latest.log` for `[LPC_FAILURE_SNAPSHOT]` and the run ID; the Gradle failure reports the matching event when available. The [Gym README](../parkourgym-server/README.md) describes the snapshot format.

The Gym clones `physics_test.polar` for each connected player. Map edits in one worker's world stay private. Save edits to the Polar source before starting new workers; already connected workers keep their existing worlds until disconnect. Worker launches use a 3 GB heap and per-version low graphics `options.txt` with two-chunk view distance and 50 FPS. The Gym disables connection throttling and server rate limiting for concurrent worker joins.

The coordinator implementation is `src/main/java/me/wolfii/legacyparkourcompat/testing/TestCoordinator.java`. Run requests and queueing belong here; world preparation and Minecraft state belong in the Gym, while input capture and playback belong in `tas-client/`.
