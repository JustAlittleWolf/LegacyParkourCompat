# 26.1 → 26.1.1 fluid slice: blind audit freeze

## Verdict

Scoped equivalence: no movement difference was found for an ordinary, non-riding, non-flying local player when effective world, fluid/resource, and player inputs are held equal. The conclusion covers body-fluid admission and stored height, current sampling and push, and the first ordinary water/lava travel branch. It is not a full player-tick or pair-coverage freeze.

## Source identity and integrity

Both canonical trees are `decompiled_minecraft/{26.1,26.1.1}/unobfuscated`, mapping `unobfuscated` (published names), decompiled with Vineflower 1.12.0 from the exact client artifacts. Ready markers report versions 26.1 and 26.1.1 and source-manifest SHA-256 values `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` and `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`, respectively. Provenance records Mojang client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` (26.1) and `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` (26.1.1); mapping is published unobfuscated.

The consumed Java files below each match their version's source manifest. Each paired file is byte-identical across the two releases:

| Source | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

## Ordered trace and comparison

- **Contact admission and height.** `Entity.baseTick` calls `updateFluidInteraction` before player `aiStep` reaches living travel (`Entity.java:503–524`; `LivingEntity.java:390–400, 2976–3073`; `Player.java:442–453`; `LocalPlayer.java:767–914`). The update resets trackers, derives an interaction box, preflights loaded sections for fluid, and scans integer bounds in x/y/z order (`EntityFluidInteraction.java:32–89, 91–119`). It rejects empty states and cells whose fluid top is below the interaction-box bottom; for admitted cells it resolves the fluid tag, applies the inclusive eye test, and stores `max(fluidTop - entityBoundingBox.minY, priorHeight)` (`:54–72`). `Entity.isInWater()` reads the `wasTouchingWater` value refreshed from the tracker in `updateFluidInteraction` (`Entity.java:1509–1511, 1558–1587`). These bodies are identical.
- **Current sampling and push.** For a player, `isPushedByFluid()` is inherited as true, so tracker update samples flow. Each admitted fluid cell contributes its flow; while the running maximum stored height is below `0.4`, that cell's flow is scaled by that running height before accumulation (`EntityFluidInteraction.java:72–80`). The entity update then pushes water at `0.014` or lava at `0.007` for FAST_LAVA, otherwise `0.0023333333333333335` (`Entity.java:1558–1585`). `Tracker.applyCurrentTo` averages accumulated current for `Player`, skips zero/near-zero aggregate current, scales it, and applies the low-horizontal-speed minimum impulse under its stated thresholds (`EntityFluidInteraction.java:157–195`). These bodies and their call order are identical.
- **First ordinary water/lava move.** `LocalPlayer.aiStep` dispatches through `Player.aiStep` and `LivingEntity.aiStep` to `Player.travel(input)` (`LocalPlayer.java:767–914`; `Player.java:442–453, 1381–1403`; `LivingEntity.java:2976–3073`). For the assigned non-riding, non-flying, non-elytra case, `Player.travel` reaches `LivingEntity.travel`; if the player is already swimming, its prelude may first add vertical velocity using look-angle, jump, and above-fluid-state gates (`Player.java:1381–1403`). The fluid branch is selected only when the current block-position fluid state accompanies stored water/lava contact, `isAffectedByFluids()` is true, and `canStandOnFluid` is false (`LivingEntity.java:2392–2407`; `Player.java:875–877`; default `canStandOnFluid` at `LivingEntity.java:2378–2380`). Water samples effective gravity and falling state before movement, applies relative input, moves, then applies horizontal slowdown and `0.8F` vertical drag (or the climbable-collision y value), followed by falling adjustment and the jump-out gate (`LivingEntity.java:2455–2493, 2635–2649`). Lava moves first, chooses the `fluidHeight <= jumpThreshold` branch for `(0.5, 0.8F, 0.5)` drag plus falling adjustment or otherwise scales all axes by `0.5`, then applies `-baseGravity / 4.0` when gravity is nonzero and checks jump-out (`:2496–2512`). The gates, operation order, constants, casts, and comparisons match exactly.

## Limits

The comparison assumes matching fluid tags/states and heights, loaded-chunk and section state, dimension FAST_LAVA value, player attributes/effects/pose/velocity, and movement inputs. Solid collision geometry, portals, creative flight, Elytra, damage, full pair coverage, and runtime parity are outside this assignment. No tests, builds, or launches were run.