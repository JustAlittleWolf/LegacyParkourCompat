# C early 1.8.9 to 1.9: creative flight while fall-gliding

## Evidence identity

Read-only canonical sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather\` and `...\decompiled_minecraft\1.9\ornithe-feather\`. Exact releases: Minecraft 1.8.9 and 1.9; mapping namespace Ornithe Feather on both sides.

- 1.8.9 uses Feather Gen2 `1.8.9+build.2` (`feather-gen2-1.8.9+build.2-mergedv2.jar`, Tiny file). The local source preparation manifest is `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` (SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`); its source-hash index `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256` is SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` (printed value: `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`). The pinned client artifact SHA-256 is `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`; merged Feather mapping archive SHA-256 is `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` and Tiny mapping SHA-256 is `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`. Its pinned source-file hashes are LocalClientPlayerEntity.java `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` and PlayerEntity.java `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`. The preparation record says the batch was 1.8.9 and 1.9.4; the source tree itself is explicitly 1.8.9.
- 1.9 uses Feather Gen2 `1.9+build.2`; pinned merged mapping artifact SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`, Feather metadata SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`, and Mojang client SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`. Canonical manifest `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; toolchain is Vineflower 1.12.0 / Java 25.0.3. Source hashes below are the manifest's hashes and were checked against the current file bytes.

## Finding and bounded trace

**Observation (1.9 only):** a local creative player can hold `abilities.flying == true` while flag 7 (fall-flying) is still true through the gliding `Entity.move` of the same movement update. The direct player ability-flight override then clears flag 7. This is a brief but real overlap, not a state in which both flags persist after that movement call.

A normal game-mode path establishes `canFly`: `ClientPlayerInteractionManager.setGameMode` applies the chosen mode to the local player's abilities (ClientPlayerInteractionManager.java:74-77), and `WorldSettings.GameMode.apply` sets `canFly=true` for CREATIVE (WorldSettings.java:104-120). In `LocalClientPlayerEntity.mobTick`, the previous jump state is saved, input is ticked, and a rising jump when `canFly` arms `pressedJumpTwiceTimer=7`; a later rising jump while that timer is nonzero toggles `abilities.flying`, calls `syncAbilities`, and resets the timer (LocalClientPlayerEntity.java:641-645, 686-700; PlayerEntity.mobTick decrements the timer at 401-403). This toggle has no ground or `isFallFlying` exclusion.

The same ordinary jump path can request Elytra flight when the player is falling, has a fly-capable Elytra, and is not already fall-flying or flying (LocalClientPlayerEntity.java:703-707). The normal `START_FALL_FLYING` server handler validates airborne/downward motion, not already fall-flying, not in water, and a usable Elytra, then calls `ServerPlayerEntity.setFlying`, which sets flag 7 true (ServerPlayNetworkHandler.java:846-853; ServerPlayerEntity.java:1040-1042). Thus a creative player can enter the seven-tick double-jump window as the ordinary Elytra request is made and, once the ordinary server flag update has made the local player fall-flying, toggle creative flight on a later jump edge. No crafted packet is required. The overlap is gated by normal input timing and the accepted flag update arriving before that second edge; source code establishes the path and gates, not a guaranteed network latency.

On the second edge, the local toggle precedes `super.mobTick()` (LocalClientPlayerEntity.java:696-700, 750). The inherited living movement then calls `flyingTick()` and dynamically dispatches `moveRelative` (LivingEntity.java:1697-1698). On the logical client, `flyingTick` leaves the fall-flying flag unchanged because its write is server-only (LivingEntity.java:1705-1722). `PlayerEntity.moveRelative` sees `abilities.flying && !isRiding()`, saves vertical velocity and `speedInAir`, sets flight speed, then invokes `super.moveRelative` (PlayerEntity.java:1366-1384). That superclass follows the still-active `isFallFlying()` branch and calls the actual `Entity.move(...)` at LivingEntity.java:1289-1329. Only after that call returns does PlayerEntity restore/scale movement state and directly execute `setFlag(7, false)` (PlayerEntity.java:1370-1380). So the requested order is toggle + ability sync → client fall-flying state retained → gliding `Entity.move` → direct flag-7 clear.

`PlayerEntity.moveRelative` also writes `fallDistance = 0.0F` immediately before clearing flag 7. That is a separate adjacent operation and is not part of this finding's claim or re-audit. For 1.8.9, local source scan found no Elytra, fall-flying, or `START_FALL_FLYING` implementation; its creative double-jump flight exists, but there is no flag-7 glide state to overlap. Therefore the 1.9 operation has no corresponding 1.8.9 glide operation, and must not be attributed to historical 1.8.9 behavior.

## Relevant file SHA-256

| Version | Relative source | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.8.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.9 | `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9 | `net/minecraft/world/WorldSettings.java` | `31f980844878ca5d20f36c23736484622de44b43f92754e7aaca5f4122ddd0d6` |
| 1.9 | `net/minecraft/client/ClientPlayerInteractionManager.java` | `7f64e714377fba1e09d47b1a89a55e5bee961040b6707ecf9c1065df740fea53` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |

No source generation, build, test, game launch, product/history inspection, or wiki lookup was performed.


