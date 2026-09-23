# Parkour gym server

`./gradlew runParkourGymServer` (or `./gradlew :parkourgym-server:runServer`) starts a Paper 26.2 server and leaves it running.

The server is offline-mode and bound to `127.0.0.1`. Joining players are op, so `/gamemode` works. `/save` writes the loaded world as Polar.

Server movement checks are off (`player_movement_check`, `elytra_movement_check`, and Paper `PlayerFailMoveEvent`); the client is the authority for legal movement.

The testing world is Polar (`polarpaper:physics_test`), height `[0, 256)`, with a 16×16 stone spawn platform at `y=64`. Players spawn there; the Nether is disabled (`config/paper-global.yml` `misc.enable-nether: false`). Paper still boots a dummy overworld because it requires one, then the helper plugin unloads it when possible. Polar custom gamerules: `blockPhysics` on; `blockGravity`, `liquidPhysics`, and `blockFade` off. Mob spawning, weather, and the daylight cycle are off, and time is noon (`6000`).

The standardized Polar world is `parkourgym-server/worlds/physics_test.polar` (Git LFS). First launch copies it into `parkourgym-server/run/` if that copy is missing. After changing the test world, `/save` and copy the Polar file back into `parkourgym-server/worlds/` to update the tracked world.

If you already have an old `parkourgym-server/run/` from before the height change, delete that folder so the datapack and Polar world can be copied fresh.

Plugins installed automatically: ViaVersion, ViaBackwards, ViaRewind, PolarPaper, Axiom Paper, and this helper plugin.

## Movement failure snapshots

An accuracy-test client records the first position mismatch by sending the following reserved, ordinary chat message as the player:

```text
!lpcf <runId> <version> <tick>
```

The complete message must be ASCII and at most 64 characters. `runId` and `version` are one token of at most 36 characters (`A-Z`, `a-z`, digits, `.`, `_`, `:`, `+`, `/`, `@`, or `-`). `tick` must be a decimal number between `0` and `9999999`. The exact four-space-separated fields are required; there is no details field, so the protocol stays short enough to be safely sent as a normal chat message.

The helper intercepts every message beginning with `!lpcf` at the lowest chat priority and cancels it, so neither valid nor malformed protocol messages are broadcast to other players. A valid message writes exactly one JSON object to the server log, prefixed by the stable marker `[LPC_FAILURE_SNAPSHOT] `:

```text
[LPC_FAILURE_SNAPSHOT] {"event":"failure_snapshot","schemaVersion":1,"runId":"...","version":"...","tick":123,"player":{"uuid":"...","name":"..."},"world":{"key":"...","name":"..."},"position":{"x":0.0,"y":65.0,"z":0.0,"yaw":0.0,"pitch":0.0},"velocity":{"x":0.0,"y":0.0,"z":0.0},"onGround":true,"boundingBox":{"minX":...,"minY":...,"minZ":...,"maxX":...,"maxY":...,"maxZ":...},"blockRadius":2,"blockBounds":{"minX":...,"minY":...,"minZ":...,"maxX":...,"maxY":...,"maxZ":...},"blocks":[{"x":...,"y":...,"z":...,"material":"minecraft:stone","blockData":"minecraft:stone"}],"blockCount":...}
```

The marker is followed by one stable, single-line JSON event; normal Paper logger timestamps/prefixes may appear before it. `blockRadius` is currently the fixed radius `2` around the player's complete bounding box (therefore including feet and head), clipped to the world's height. Blocks are emitted in deterministic `y`, `z`, `x` order and include integer coordinates, namespaced material, and Bukkit block data. A log consumer can parse the substring beginning at the marker and then decode the JSON object. `AsyncChatEvent` captures are moved to the player's Folia entity scheduler before reading world state.
