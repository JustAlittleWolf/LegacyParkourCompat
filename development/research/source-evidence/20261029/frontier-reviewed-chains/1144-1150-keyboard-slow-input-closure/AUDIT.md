# Keyboard slow-movement input: 1.14.4 → 1.15

## Result

**The vanilla keyboard slow-movement predicate changed.** In 1.14.4, `KeyboardInput.tick` reduces both movement impulses when the physical sneak key is down or LocalPlayer is visually sneaking/crawling, except in spectator mode. In 1.15, `KeyboardInput.tick` reduces them when LocalPlayer's derived `isMovingSlowly()` predicate is true; that predicate is crouching or visually crawling. The new predicate has no explicit spectator test and does not treat the current tick's sampled shift key as independently sufficient: `LocalPlayer.aiStep` computes `isMovingSlowly()` before `KeyboardInput.tick` refreshes `shiftKeyDown`, so its shift-key component uses the prior stored input state and is subject to the flying, swimming and pose-eligibility gates.

The scale is otherwise the same operation: both versions multiply left and forward impulses by the double literal `0.3`, cast the result to `float`, and do so after reading movement keys and jumping. In 1.15, `Input.tick(boolean)` replaces `Input.tick(boolean, boolean)`, and the stored key-state field changes from `sneakKeyDown` to `shiftKeyDown`. `Input.tick` itself is empty in both versions.

## Exact operation and gates

- **1.14.4, Mojmap:** `KeyboardInput.tick(boolean bl, boolean bl2)` reads the direction/jump/sneak bindings, then applies the two `* 0.3` calculations under `!bl2 && (this.sneakKeyDown || bl)`. In `LocalPlayer.aiStep`, `bl` is `isVisuallySneaking() || isVisuallyCrawling()` and `bl2` is `isSpectator()`. `isVisuallySneaking()` is false while flying or swimming, and otherwise requires the sneaking pose to be enterable; within that gate it is true for the held sneak key or when standing pose is obstructed. `Entity.isVisuallyCrawling()` is swimming pose while not in water. Despite the visual-pose gate, a held sneak key alone still slows a non-spectator in 1.14.4 because it is separately ORed into the keyboard predicate.
- **1.15, Mojmap:** `LocalPlayer.aiStep` passes `isMovingSlowly()` to `Input.tick(boolean)`. `isMovingSlowly()` is `isCrouching() || isVisuallyCrawling()`. LocalPlayer's `isCrouching()` requires not flying, not swimming and permission to enter `Pose.CROUCHING`, then returns held shift or (unless sleeping) inability to enter `Pose.STANDING`. `Entity.isVisuallyCrawling()` remains swimming pose while not in water. The spectator exclusion from 1.14.4 is no longer an explicit input-tick argument/condition; the 1.15 slow predicate relies on these state gates instead. Ordering also differs: `isMovingSlowly()` is evaluated before `KeyboardInput.tick` polls and stores the current shift state. Thus a newly pressed/released shift state reaches this predicate from the previous stored state for that tick, while 1.14.4 tests the freshly polled sneak key directly inside `KeyboardInput.tick`.
- Both releases construct `KeyboardInput(options)` at the client login and respawn installation sites in `ClientPacketListener`; repository-wide search of the two Mojmap `net` trees found no other vanilla `Input` subclass or `Input`/`KeyboardInput` constructor. `KeyboardInput` is the only vanilla subclass in each tree.
- Immediately after `input.tick`, both `LocalPlayer.aiStep` bodies retain the same separate item-use slowdown: when using an item and not a passenger, each impulse is multiplied by `0.2F`. `LocalPlayer.serverAiStep` copies the input impulses to `xxa` and `zza` when this player controls the camera. `LivingEntity.aiStep` passes `(xxa, yya, zza)` to `travel`; `LivingEntity.travel` uses the horizontal components in `moveRelative` for ground, water and air branches. Therefore the changed `0.3` gate changes the horizontal input supplied to ordinary player travel on reachable client-local-player ticks; it does not itself change the downstream travel formulas.

## Input plumbing boundary

The key binding poll (`options.keySneak`/`options.keyShift` and `KeyMapping.isDown()`), key binding configuration/device events, and the `ToggleKeyMapping` behavior are input-device plumbing. They are excluded from the movement finding. The source change established here is in the vanilla keyboard predicate that decides whether already-sampled input impulses are slowed, plus the input state field/signature rename and LocalPlayer state predicate feeding it.

## Provenance and source integrity

The exact decompiled trees are the canonical shared roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` — ready marker names exact version `1.14.4`, mapping `mojmap`, 3,250 files / 17,080,207 bytes; source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; client artifact SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. Its provenance records exact run `1.14.4`, mapping option `feather,mojmap`, and Vineflower 1.12.0 / Java 25.0.3.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap` — ready marker names exact version `1.15`, mapping `mojmap`, 3,329 files / 17,522,461 bytes; source-manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; client artifact SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. Its provenance records requested/exact `1.15`, official Mojmap input, source-manifest identity above, and Vineflower 1.12.0 / Java 25.0.3.

I recomputed each `*.sources.sha256` file's SHA-256 against its ready marker, parsed every manifest row, recomputed every listed source hash, and checked the source-file count and aggregate bytes. Both sides matched exactly: 1.14.4 3,250/3,250 files and 17,080,207 bytes, zero missing/hash/malformed-row errors; 1.15 3,329/3,329 files and 17,522,461 bytes, zero errors. The hashes below are the cited files' SHA-256 values; their entries match the corresponding source manifests.

| Release | Cited source path (relative to Mojmap root) | SHA-256 |
|---|---|---|
| 1.14.4 | `net/minecraft/client/player/Input.java` | `8adaf34fa0bcef319d8b3dd678f53ddf68b0293a17e2c1620a6528fa0d9ad1ef` |
| 1.14.4 | `net/minecraft/client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` |
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| 1.15 | `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` |
| 1.15 | `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 | `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |

## Source references

All ranges are one-based lines in the exact source files named above.

| Release | File and lines | Evidence |
|---|---|---|
| 1.14.4 | `net/minecraft/client/player/Input.java:5-25`; `KeyboardInput.java:5-27` | Input fields/signature and empty base tick; raw key reads, impulse assignment, sneak key state and guarded `0.3` scaling. |
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java:588-597,618-634` | Trying/visual sneak gates; `aiStep` computes visual sneak/crawl plus spectator and passes both booleans; unchanged following item-use scaling. |
| 1.14.4 | `net/minecraft/world/entity/Entity.java:1836-1842` | Crawling predicate. |
| 1.14.4 | `net/minecraft/client/multiplayer/ClientPacketListener.java:359,1055` | KeyboardInput installed at login and respawn. |
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java:600-605`; `net/minecraft/world/entity/LivingEntity.java:2146-2147,2223`; `LivingEntity.java:1782,1840,1866,1900` | Input impulses copied to travel fields; travel receives those fields and feeds horizontal input through `moveRelative`. |
| 1.15 | `net/minecraft/client/player/Input.java:5-25`; `KeyboardInput.java:5-27` | Renamed key-state field/signature and empty base tick; raw key reads, impulse assignment, shift key state and unconditional-on-boolean `0.3` scaling. |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java:591-604,625-640` | Crouching gates and slow predicate; `aiStep` feeds it to input before the call refreshes `shiftKeyDown`; unchanged following item-use scaling. |
| 1.15 | `net/minecraft/world/entity/Entity.java:1819-1825` | Crawling predicate. |
| 1.15 | `net/minecraft/client/multiplayer/ClientPacketListener.java:370,1084` | KeyboardInput installed at login and respawn. |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java:607-612`; `net/minecraft/world/entity/LivingEntity.java:2207-2208,2287`; `LivingEntity.java:1831,1889,1915,1949` | Input impulses copied to travel fields; travel receives those fields and feeds horizontal input through `moveRelative`. |

This closes only the assigned input operation and its direct impulse path. It is not a full 1.14.4→1.15 movement-pair coverage or freeze verdict.

