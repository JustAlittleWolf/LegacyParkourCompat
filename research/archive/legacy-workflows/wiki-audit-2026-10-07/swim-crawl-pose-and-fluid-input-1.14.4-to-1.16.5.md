# Bounded swim/crawl pose and fluid-input comparison: 1.14.4 to 1.16.5

Snapshot ID: `wiki-swim-crawl-pose-fluid-input-1.14.4-1.16.5`

Status: exact-source comparison recorded; independent Wiki-lane review pending. Player pose dimensions and fallback order are materially stable across these endpoints. The comparison identifies a 1.16.5 pose-clearance epsilon and input-gate changes, but leaves exact surface-water thresholds and full swim/crawl dependency closure open. No runtime validation was performed.

## Ready-source provenance

The exact ready endpoints are `build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather`, `.../1.15.2/mojmap`, and `.../1.16.5/mojmap`. Ready metadata identifies all three as ready Vineflower trees; source-manifest SHA-256 values are `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The namespace changes from `ornithe-feather` to `mojmap` after 1.14.4; comparisons are semantic across the exact mapped trees.

Targeted source files and hashes:

| Version | File | SHA-256 |
|---|---|---|
| 1.14.4 | `entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 | `entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 | `entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.14.4 | `client/entity/living/player/LocalClientPlayerEntity.java` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| 1.15.2 | `world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15.2 | `world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.15.2 | `world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.15.2 | `client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.16.5 | `world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.5 | `world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.16.5 | `world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.5 | `client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |

Every source-file digest above matches its endpoint's source manifest.

## Bounded observations

1. **Pose dimensions and fallback order are stable.** In 1.14.4, 1.15.2, and 1.16.5, player dimensions remain standing `.6 × 1.8`, swimming/fall-flying/spin `.6 × .6`, crouching/sneaking `.6 × 1.5`, and dying `.2 × .2`. `updatePlayerPose()` keeps the same priority among fall-flying, sleeping, swimming state, spin, sneaking, and standing. If the desired pose does not fit, it tests crouch/sneak and then falls back to SWIMMING. The SWIMMING pose is therefore a collision-clearance fallback and can occur out of water; this is separate from the swimming state.

2. **1.16.5 changes the pose-fit boundary by a small tolerance.** 1.15.2 `Entity.canEnterPose()` passes the candidate pose box directly to `noCollision()` (lines 1605–1607). 1.16.5 deflates that candidate by `1.0E-7` before the same collision check (lines 1640–1642). The comparison identifies a changed clearance predicate; it does not claim which particular block arrangement changes result without a separate geometric witness.

3. **Swimming-state writer structure remains stable, but its underwater producer changes.** Across these endpoints, `Entity.updateSwimming()` continues an active swim only with sprinting, in water, and not riding/passenger; entry uses sprinting, underwater, and not passenger. The player override blocks swimming while flying. However, 1.14.4 and 1.15.2 derive the cached underwater state through an `isUnderLiquid(WATER, true)` path, whereas 1.16.5 updates `wasEyeInWater` from `isEyeInFluid(WATER)`. That producer and cache change is upstream of both swimming entry and local sprint eligibility. Without comparing the fluid-height sampling geometry and tick order at exact positions, surface-water differences remain unresolved.

4. **1.16.5 gates downward water input for players unaffected by fluids.** 1.15.2 `LocalPlayer.aiStep()` calls `goDownInWater()` when the player is in water and holds the down/sneak key. 1.16.5 adds `isAffectedByFluids()` to that branch. The `Player` override returns false while creative-flying. The changed branch therefore suppresses this explicit downward-water input for a flying player. The corresponding jump-in-liquid and further consumers are not resolved here; no full velocity or trajectory result is claimed.

## Reviewed ranges and remaining dependencies

The bounded comparison checked: player pose dimensions and `updatePlayerPose()` (1.14.4 `PlayerEntity.java` lines 109–118, 343–370; 1.15.2 `Player.java` 111–120, 357–384; 1.16.5 `Player.java` 112–121, 350–377); pose-fit collision checks (`Entity.java` 1.14.4 lines 1628–1630, 2300–2305; 1.15.2 lines 1605–1607; 1.16.5 lines 1640–1642); swimming state and underwater cache writers (`Entity.java` lines 938–984 per endpoint); player swimming/flying override; local sprint/down-input gates; and the associated `LivingEntity` fluid-effect predicate.

Remaining dependencies: exact comparison of `isUnderLiquid(WATER, true)` with eye-fluid sampling geometry and update order; the jump-in-liquid consumer behind `isAffectedByFluids()`; one-block clearance and surface-swimming inputs; and pose consumers beyond the inspected dimensions/eye-height/sync paths. This is not a complete swim/crawl inventory.
