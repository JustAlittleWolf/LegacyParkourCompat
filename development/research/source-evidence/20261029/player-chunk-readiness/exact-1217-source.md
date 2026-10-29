# Supplement 03 — Exact 1.21.7 player readiness source contract

This immutable supplement adds the exact published 1.21.7 endpoint to the earlier readiness report. It binds the original report SHA-256 `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1`, Supplement 01 SHA-256 `283a413da6b69fa6b812d7ff2ed8b6711c8c9a5acc10d339cee8d3f2a5e21cd4`, and Supplement 02 SHA-256 `0fba410019738740705517ba4d01489078257ad245ded84c6024abda4eca3b23`. Those files remain unchanged.

## Source identity and provenance

Canonical mapping root inspected: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.7\mojmap`. The release parent contains the `mojmap` directory and no parent-level `.decompile-provenance.json`; the mapping root has `.decompile-provenance.json` (SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`). Its legacy embedded `sources` map has 6,104 entries and `inputs` has 87 entries. Each of the five selected Java files below exists and its bytes match its declared `sources` hash. Thus the exact 1.21.7 mapping-root source identity is available; no modern ready marker is asserted.

The legacy manifest binds these exact Mojang inputs (URL and manifest-declared SHA-256):

- Version metadata: `https://piston-meta.mojang.com/v1/packages/24c116056f9d403403d7b7c06fe018e75aaca464/1.21.7.json` — `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a`.
- Client jar: `https://piston-data.mojang.com/v1/objects/a2db1ea98c37b2d00c83f6867fb8bb581a593e07/client.jar` — `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`.
- Client mappings: `https://piston-data.mojang.com/v1/objects/8d83af626cae1865deaf55fbf96934be4886fd45/client.txt` — `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`.

These hashes are the manifest's input bindings; the downloaded input payloads were not independently rehashed for this supplement. Provenance reports pipeline `lpc-decompile-v1` and Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 amd64.

Selected source file SHA-256 values, each verified equal to the mapping-root `sources` entry:

| Source | SHA-256 |
| --- | --- |
| `net/minecraft/client/multiplayer/LevelLoadStatusManager.java` | `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/renderer/LevelRenderer.java` | `581979a2ef91d93bd5d8cc28079ddeeeb76c76a419c3d5424eb94dbca00a5e63` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |

## Direct readiness predicate and caller chain

- `LevelLoadStatusManager.java:19–29`: In `WAITING_FOR_PLAYER_CHUNK`, `tick()` gets `BlockPos pos = player.blockPosition()` (`:22`), computes whether its Y is outside build height (`:23`), and sets `LEVEL_READY` when outside height **or** `levelRenderer.isSectionCompiled(pos)` **or** spectator **or** not alive (`:24–25`). This is a direct query at the player's block position. The code has no `isSectionCompiledAndVisible` condition or camera-position input. The switch has no explicit `break` after the state change; subsequent cases are empty.
- `LevelLoadStatusManager.java:36–40`: `loadingPacketsReceived()` moves `WAITING_FOR_SERVER` to `WAITING_FOR_PLAYER_CHUNK`.
- `ClientPacketListener.java:1528–29`: the `LEVEL_CHUNKS_LOAD_START` game event calls `loadingPacketsReceived()` when a manager exists. `:1533–35` creates the manager from the supplied `LocalPlayer`, `ClientLevel`, and `minecraft.levelRenderer`, then gives `levelReady` to `ReceivingLevelScreen` as its completion callback.
- `ClientPacketListener.java:2589–94`: client-listener tick invokes manager `tick()`; when `levelReady()` and the local player's `hasClientLoaded()` is false, it sends `ServerboundPlayerLoadedPacket` and sets the local loaded flag true. This is the readiness-to-notification gate visible in source; this supplement makes no claim about packet arrival timing or runtime scheduling.
- `LocalPlayer.java:197–201`: `tick()` first runs `tickClientLoadTimeout()`, then enters the normal `super.tick()` path only if `hasClientLoaded()` is true. This is a local-player simulation/tick gate, distinct from the compiled-section readiness query.
- `Player.java:2053–66`: `hasClientLoaded()` is true when the client-loaded flag is set or its timeout is exhausted; `tickClientLoadTimeout()` decrements the timer while not loaded. This identifies the fallback gate and is not itself a chunk predicate.
- `LevelRenderer.java:1245–48`: `isSectionCompiled(pos)` obtains the render section at the supplied position and returns true when one exists and its mesh is not `UNCOMPILED`. The readiness manager calls this API with the player-derived position. This records only the predicate's source implementation; it does not analyze rendering output or conflate this method with a camera visibility callback.

## Finding

Exact 1.21.7 source contains the direct player-block-position compiled-section readiness predicate and the client-side loaded-state notification/tick gates above. The mapping-root legacy provenance map and all five selected source hashes support this endpoint independently; the conclusion is not inferred from 1.21.6 or 1.21.8. The predicate permits other ready branches (outside build height, spectator, dead), so “direct compiled-section readiness predicate” does not mean that compilation is the sole route to ready.