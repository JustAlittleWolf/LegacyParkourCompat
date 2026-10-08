# F-S1-01: 1.21.4 defers the local player movement tick until level readiness or timeout

- Older version A: 1.21.3
- Newer version B: 1.21.4
- Mechanic / coverage slice IDs: local player tick gate; S1-04
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.3, 1.21.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: artifact A in `run.md`; `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `net.minecraft.client.player.LocalPlayer#tick()`, lines 189-212, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`. After the throttler tick, the method calls `super.tick()` unconditionally.
- B manifest: artifact B in `run.md`; `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `net.minecraft.client.player.LocalPlayer#tick()`, lines 191-217, SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`. It decrements the load timeout, then encloses inherited ticking and input/position reporting in `if (this.hasClientLoaded())`.
- B state source: `net.minecraft.world.entity.player.Player`, `Player.java` lines 167-168 (default `clientLoaded=false`, timeout 60) and 2096-2109 (`hasClientLoaded`, timeout decrement and reset), SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- A absence/replacement path: the A `LocalPlayer#tick()` caller path reaches `super.tick()` without a load predicate; A `Player.java` has no corresponding load flag, timeout or getter/setter. A `ClientPacketListener#tick()` lines 2435-2454 only advances the shared load-status manager and does not gate player ticking, SHA-256 `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a`.
- B readiness writer: `net.minecraft.client.multiplayer.ClientPacketListener#tick()`, lines 2441-2464, SHA-256 `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`. When `levelLoadStatusManager.levelReady()` is true, it sends `ServerboundPlayerLoadedPacket` and calls `player.setClientLoaded(true)`.
- Readiness producer, both versions: `LevelLoadStatusManager.java`, bounded methods `tick()` / `levelReady()` / `loadingPacketsReceived()`, lines 17-44, SHA-256 `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec`. The shared state moves from server chunk-load-start to player-chunk/compiled-section readiness and becomes ready when the player is outside build height, its section is compiled, it is a spectator, or it is not alive.
- Reachable tick path, A/B: `Minecraft.runTick()` calls `ClientLevel.tickEntities()` before `Connection.tick()` dispatches the tickable packet listener; see A `Minecraft.java` lines 1724-1732 and 1784-1787 (SHA-256 `4ff5f4b2907fa383e8ba0a79b9f02518491350e48a3079a15c6cfd53e0dc0ce1`) and B lines 1719-1727 and 1779-1782 (SHA-256 `f4c9ba42f66a267ff01ff23f2bc9a8472d6f168effb1fc84314a613f730f94b1`). `ClientLevel.tickEntities()` and `tickNonPassenger()` invoke the local entity tick (A `ClientLevel.java` lines 248-279, SHA-256 `f99ca9b24f796bed5428e6a3b010c489bfac2f43a35e90ff4fd8dad6d2e45aa1`; B lines 252-283, SHA-256 `d21332aed2f9460594f838bbac2c813572fe5addf2ef0b9f9f4e9ffbd74ad313`). `Connection.tick()` invokes `TickablePacketListener.tick()` on both versions (A lines 376-380, SHA-256 `415eef2cf4b40efc938ef5bb6d05643c1c840a28514b5130b0a669a027e1b400`; B same lines, SHA-256 `c25d7ecc9f1f453fc0568625b4090e1c455419d998232d8190a33c345a1ecead`). `ReceivingLevelScreen.isPauseScreen()` returns false in both, `ReceivingLevelScreen.java` lines 83-86, SHA-256 `8700b76cc34d8db3ad1f65854e7897d6a2d8d13b41a9c5c28ed222f4ac44aa64`.
- Movement call-through: A `LivingEntity#tick()` lines 2457-2459 and B lines 2468-2470 call `aiStep()` when the entity is not removed; the paired `LivingEntity#aiStep()` then dispatches jump and travel as recorded in S3-01. A SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`; B SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.

## Source-level difference

For a non-removed, non-passenger, non-frozen local player in an unpaused level, with `clientLoaded == false` and timeout greater than zero, A runs the ordinary inherited local-player tick. B runs only `tickClientLoadTimeout()` and returns without `super.tick()`. B's timer starts at 60 and is decremented before the readiness check, so if readiness never arrives, the inherited tick is permitted when the timer first reaches zero. If readiness becomes true, the packet-listener tick sets the flag after that frame's entity-tick phase; local movement resumes on the next entity tick.

The readiness status manager is present on both sides and uses the same player-chunk/render-section readiness conditions. The change is B's use of that status to gate the local player and its 60-tick fallback. The receiving-level screen itself does not pause the world tick, so the gate changes a reachable tick path.

## Reachability and dependencies

`Minecraft.runTick()` -> `ClientLevel.tickEntities()` -> `tickNonPassenger(LocalPlayer)` -> `LocalPlayer.tick()` -> inherited `LivingEntity.tick()` -> `aiStep()` -> jump/travel. B gates this path on a local client readiness flag set by `ClientPacketListener.tick()` or by the timeout. The loaded packet is an external protocol notification; no server movement behavior is inferred here.

## Consequence and uncertainty

Source proves B suppresses the ordinary local player/AI/movement tick while the load flag is false and the timeout remains positive. This defers input sampling and local travel during that interval. It does not establish that all externally packet-driven position or velocity updates are also suppressed, and no trajectory was measured. The exact release introduction is unknown within the endpoint interval.

## Handoff

This is one client local-player tick-gate delta. Related source inventory: S1-04. Implementation and runtime-testing decisions are deferred.
