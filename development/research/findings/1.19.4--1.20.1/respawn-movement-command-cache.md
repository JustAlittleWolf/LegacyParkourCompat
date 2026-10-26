# Respawn movement command-cache audit

## Finding

Respawn command-cache seeding changes in 1.20 for a respawn that does not keep entity data. In 1.19.4, `ClientPacketListener#handleRespawn` always constructs the new `LocalPlayer` with the outgoing old player's `isShiftKeyDown()` and `isSprinting()` values. In 1.20, it passes those values only when `ClientboundRespawnPacket#shouldKeep((byte)2)` is true; otherwise the three-argument `MultiPlayerGameMode#createPlayer` overload delegates with `false, false`. The same 1.20 bytes occur in 1.20.1.

For a concrete ordinary input case, if Shift remains held through a respawn with keep-entity-data false, `KeyboardInput.tick` samples the held key on the first local player tick. In 1.19.4 the prior player's `wasShiftKeyDown` cache can already be true, so `sendPosition` emits no `PRESS_SHIFT_KEY`; in 1.20/1.20.1 the false cache differs from the sampled input, so it emits the command. The analogous sprint command is conditional on the new player becoming sprinting before the comparison and the old player having been sprinting.

## Movement disposition

The cache fields are read only by the command comparisons in `LocalPlayer#sendPosition` / `sendIsSprintingIfNeeded`; their other writes are the constructor seed and the update after sending. The local tick order is `super.tick()` and then `sendPosition()`. `LocalPlayer#aiStep` samples input and advances player control before `sendPosition`; `LocalPlayer#isShiftKeyDown` reads `input.shiftKeyDown`, and `KeyboardInput#tick` reads the current key mapping. Thus the cache does not select local movement input, position, velocity, pose, or movement state. The concrete delta established here is outgoing command emission only. Server-side command handling can set the server player's shift/sprint flags, but server movement production is outside this assignment; no distinct local movement delta or historical movement hook is established by this cache change.

## Exact source citations

All paths below are relative to `decompiled_minecraft/<version>/mojmap/net/minecraft/`.

- 1.19.4 `client/multiplayer/ClientPacketListener.java:1097` — unconditional old shift/sprint values passed to the new player; `:1105-1114` — separate entity-data and attribute keep gates; `:1120` — new `KeyboardInput` installed.
- 1.20 `client/multiplayer/ClientPacketListener.java:1102-1107` — old values passed only under `shouldKeep((byte)2)`, otherwise the three-argument overload; `:1116-1125` — entity data/attributes still use their own gates; `:1131` — new `KeyboardInput` installed.
- 1.20.1 `client/multiplayer/ClientPacketListener.java:1102-1107`, `:1116-1125`, `:1131` — byte-identical to the 1.20 source file.
- 1.19.4 and 1.20/1.20.1 `client/multiplayer/MultiPlayerGameMode.java` — three-argument `createPlayer` delegates with `false, false` (1.19.4 `:377-382`; 1.20/1.20.1 `:370-375`).
- 1.19.4 `client/player/LocalPlayer.java:134-141`; 1.20/1.20.1 `:136-143` — constructor stores both parameters in `wasShiftKeyDown` and `wasSprinting`.
- 1.19.4 `client/player/LocalPlayer.java:187-200`, `:218-228`, `:270-278`, `:595-597`, `:645-659`; 1.20/1.20.1 `:189-202`, `:220-229`, `:272-280`, `:598-600`, `:648-665` — tick order, command comparisons, input-backed shift predicate, and input sampling.
- 1.19.4 and 1.20/1.20.1 `client/player/KeyboardInput.java:20-34`; `client/player/Input.java:5-24` — ordinary keyboard state producer and base input fields. These two files have identical SHA-256 across all three releases.
- 1.19.4 `network/protocol/game/ClientboundRespawnPacket.java:109-110`; 1.20/1.20.1 `:114-115` — `shouldKeep(byte)` is `(dataToKeep & bit) != 0`.
- Server command consumer, boundary evidence only: 1.19.4 `server/network/ServerGamePacketListenerImpl.java:1372-1386`; 1.20/1.20.1 `:1378-1392` maps shift/sprint actions to server player flags. This is not used as evidence for client movement behavior.

## Source identity and integrity

- 1.19.4 Mojmap has `mojmap.provenance.json` and `mojmap.ready.json` with `versionId=1.19.4`, `mapping=mojmap`, source manifest and exact client SHA-256. The relevant class bodies are intact and their hashes match the source manifest.
- 1.20 Mojmap bytes are present at `decompiled_minecraft/1.20/mojmap/`. The cache has no `mojmap.ready.json`, `mojmap.provenance.json`, or `mojmap.sources.sha256`; it does contain `.decompile-provenance.json`, whose source-hash entries match the actual target source bytes. I used those actual bytes and report the missing canonical sidecars explicitly.
- 1.20.1 Mojmap has `mojmap.provenance.json` and `mojmap.ready.json` with `versionId=1.20.1`, `mapping=mojmap`, source manifest and exact client SHA-256. Its target method bodies are intact.
- SHA-256 values for the six inspected client files (ClientPacketListener, MultiPlayerGameMode, LocalPlayer, KeyboardInput, Input, ClientboundRespawnPacket) are recorded in `evidence.json`. The 1.20 and 1.20.1 hashes match for all six files; 1.20's embedded source hashes also match those bytes. This establishes the first new stable endpoint in the provided cache as 1.20, with 1.20.1 as corroboration.

## Dependencies and limits

Dependency closure for this cache claim is closed across respawn packet bit test, respawn constructor call, overload delegation, LocalPlayer cache initialization and comparison, ordinary keyboard input sampling, and command packet consumption. The source result is limited to command emission. It does not establish server movement results, later authoritative client corrections, a trajectory delta, the exact patch that introduced the change inside 1.19.4–1.20, or full movement coverage for the release pair.
