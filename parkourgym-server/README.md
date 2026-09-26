# Parkour Gym server

This module starts a local Paper 26.2 server for manual parkour practice and automated movement tests. From the repository root:

```powershell
.\gradlew.bat runParkourGymServer
```

The equivalent module task is `:parkourgym-server:runServer`. It downloads Paper, ViaVersion, ViaBackwards, ViaRewind, PolarPaper, and Axiom Paper, installs this helper plugin, prepares `parkourgym-server/run/`, and leaves the server running. The server binds to `127.0.0.1:25565`, runs in offline mode, and makes joining players op so `/gamemode` is available. `/save` writes the loaded Polar world. The [testing coordinator](../testing/README.md) can start the Gym automatically and owns the client processes for automated runs.

## World and player behavior

The tracked source world is `worlds/physics_test.polar` (Git LFS). First launch copies it into the run directory if absent. The `polarpaper:physics_test` world has height `[0, 256)` and a 16×16 stone spawn platform at `y=64`. Nether is disabled. Paper starts a required dummy overworld; the helper unloads it when possible. Mob spawning, weather, and daylight cycling are off, with time fixed at noon (`6000`). Polar has `blockPhysics` on and `blockGravity`, `liquidPhysics`, and `blockFade` off.

Each connected task player gets a separate world cloned from the tracked source, so buttons, blocks, and other interactions are isolated between parallel runs. The Gym accepts at most five task workers. [Reusable block recordings](../testing/README.md) request 14×14 surfaces placed around coordinate 5000 in that worker's world; pads are prepared only when selected. To change the shared test map, edit the source world, run `/save`, and copy the resulting Polar file from `run/plugins/polarpaper/worlds/` back to `worlds/physics_test.polar`. New workers receive the updated source; connected workers retain their existing clone until they leave.

Player damage, including fall and void damage, is cancelled. Server movement checks (`player_movement_check`, `elytra_movement_check`, and Paper `PlayerFailMoveEvent`) are disabled so historical client movement can be tested. The server disables `rate-limit` and Bukkit `connection-throttle` for concurrent worker joins, including when reusing an existing run directory. The defaults in `run-defaults/` seed a fresh server; the build task also applies settings to existing runs.

## Control API

Minecraft traffic uses port 25565. The Gym binds a local HTTP API to `127.0.0.1:25566` and worker WebSockets to `127.0.0.1:25567`. It serves `GET /api/blocks`, `GET /api/clients`, `POST /api/runs`, and `GET /api/runs/<runId>`. HTTP carries run requests and state; WebSocket carries commands to connected TAS clients. The Gym does not launch Minecraft clients. For queued runs, process reuse, request examples, placement choices, and comparison, see [testing/README.md](../testing/README.md).

`PhysicsTestPlugin` manages Paper lifecycle and player worlds; `GymControlApi` serves the local control plane; `RunPreparation` builds pads and resets game mode, gear, and effects for each run. `FailureSnapshotChat` handles the snapshot marker below.

## Movement failure snapshots

An accuracy-test client asks for a server snapshot through a reserved, ordinary player chat message:

```text
!lpcf <runId> <version> <tick>
```

The complete message is ASCII and at most 64 characters. `runId` and `version` are single tokens of at most 36 characters using letters, digits, `.`, `_`, `:`, `+`, `/`, `@`, or `-`. `tick` is a decimal number from `0` to `9999999`. The helper cancels every message beginning with `!lpcf`, including malformed ones, before other players see it. A valid message writes one single-line JSON event to the Paper log, prefixed by `[LPC_FAILURE_SNAPSHOT] `.

The event has `event: "failure_snapshot"`, `schemaVersion`, run ID, version, tick, player and world identity, position and facing, velocity, `onGround`, bounding box, block bounds, and nearby blocks with coordinates, namespaced material, and Bukkit block data. `blockRadius` is `2` around the complete player bounding box, clipped to the world's height. Blocks are emitted in deterministic `y`, `z`, `x` order. Parse the JSON substring after the marker; normal Paper log prefixes may appear before it. The async chat handler moves capture to the player's Folia entity scheduler before reading world state. The comparison workflow requests snapshots both at the first mismatch and after its one-second continuation.
