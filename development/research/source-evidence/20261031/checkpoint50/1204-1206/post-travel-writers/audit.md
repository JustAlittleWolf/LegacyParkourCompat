# Post-travel writers and pose refresh: source audit

Scope: exact Mojmap 1.20.4→1.20.5 and 1.20.5→1.20.6 residual tick/state-writer slice assigned after prior review. This is a bounded source report, not pair freeze or an independent complete-pair audit. No canonical finding is supported by this slice.

## Source readiness and identity

The canonical roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.4,1.20.5,1.20.6}/mojmap/`. The release roots carry `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. The ready markers identify exact releases and `mojmap`; all three are `status: ready`. For each release, the ready marker's source-manifest and artifact-manifest SHA-256 pins matched the files. Full source-manifest verification found all Java paths present and matching, with exact ready counts and byte totals:

| Release | Java files / ready | Bytes / ready | client SHA-256 |
|---|---:|---:|---|
| 1.20.4 | 5048 / 5048 | 24,323,941 / 24,323,941 | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |
| 1.20.5 | 5328 / 5328 | 25,448,860 / 25,448,860 | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` |
| 1.20.6 | 5329 / 5329 | 25,451,020 / 25,451,020 | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` |

For 1.20.5, the ready marker also pins `mojmap.provenance.json` (`1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`). The 1.20.4 and 1.20.6 ready markers do not carry a provenance-file digest; the provenance sidecars exist, and the source/artifact manifests are pinned and fully verified. Per-file comparison identities are in [the supplied exact-case three-way inventory](../whole-pair-inventory/three-way-java-path-inventory.csv); this report did not modify or regenerate shared sources.

## Dispositions

The inventory records these relevant Java file hashes (SHA-256). The 1.20.5 and 1.20.6 hashes match exactly for each listed file.

| Member / relative source file | 1.20.4 → 1.20.5 | 1.20.5 → 1.20.6 | Disposition |
|---|---|---|---|
| `LocalPlayer.tick`, `net/minecraft/client/player/LocalPlayer.java` | changed file; method body same | same file | No post-travel tick delta found. |
| `LocalPlayer.aiStep`, same file | changed method; flight-toggle grounded jump added | same file | Partial for this residual lane: this is the already-covered flight/jump slice (`jump-pretravel/grounded-flight-toggle-jump.md`), so no duplicate finding here. Other edits in this method are local variable/name substitutions. |
| `LocalPlayer.move`, same file | changed file; reviewed with the position/box path | same file | No residual post-travel position/box delta established. |
| `Entity.tick`, `net/minecraft/world/entity/Entity.java` | changed file; method unchanged | same file | No delta. |
| `Entity.baseTick`, same file | changed method: cached block-state field renamed `feetBlockState`→`inBlockState` and still cleared at tick start | same file | No behavior delta established; paired cache getter reads `level().getBlockState(blockPosition())` in both releases (`getFeetBlockState` 1.20.4; `getInBlockState` 1.20.5+). |
| `Entity.move`, `setOnGround`, `setOnGroundWithKnownMovement`, `checkFallDamage`, same file | method bodies equal after extraction | same file | No delta in movement result, ground/vertical-collision writes, supporting-block check, or fall-distance dispatch. `Entity.move` still writes position then computes collision flags, ground state, and fall handling in the same order. |
| `Entity.setPose`, `setPos`, `setDeltaMovement`, same file | method bodies equal after extraction | same file | No direct writer delta. |
| `Entity.refreshDimensions`, `getEyeHeight`, same file | API/data representation changed; pose callback and refresh order retained | same file | Checked with pose/dimension dispositions below. AABB resize/free-position gates are semantically unchanged for the same dimensions. |
| `LivingEntity.tick`, `net/minecraft/world/entity/LivingEntity.java` | changed: refreshes dimensions when `getScale()` differs from `appliedScale` | same file | New SCALE attribute path is a modern-only feature and excluded. |
| `LivingEntity.aiStep`, same file | method body equal after extraction | same file | No delta in post-input movement continuation. |
| `LivingEntity.travel`, same file | changed: fixed `0.08` gravity becomes `getGravity()`; slow-fall clamp and gravity tests use that value; vertical-friction multiplier gains a `FlyingAnimal` branch | same file | The new player gravity attribute is a modern-only feature; its declared default is `0.08`, and `Entity.getGravity()` preserves the `isNoGravity()` zero gate. Under pre-existing default player state the gravity substitution preserves the old value; `FlyingAnimal` is not a player. The other method is in the separately assigned travel/fluid slice. No in-scope residual player delta found here. |
| `LivingEntity.checkFallDamage`, same file | changed: server fall particle threshold becomes `SAFE_FALL_DISTANCE` attribute | same file | Server-only particle/fall-response and new attribute path; does not change client player movement; fall-damage calculation is excluded. |
| `LivingEntity.knockback`, `push(Entity)`, `Entity.push(Entity)` / `push(double)` | method bodies equal after extraction | same file | No direct contact/knockback writer delta. |
| `Player.tick`, `aiStep`, `updatePlayerPose`, `canPlayerFitWithinBlocksAndEntitiesWhen`, `net/minecraft/world/entity/player/Player.java` | methods equal after extraction | same file | No pose-selection/fit/continuation delta. Pose transition remains: choose candidate pose, test candidate fit, crouch fallback, then swim fallback. |
| `Player.getDimensions`→`getDefaultDimensions`, same file; `LivingEntity.getDimensions`, same file | API renamed/reframed; existing pose geometry retained | same file | Existing player box sizes and movement-pose eye heights are preserved: standing 1.62, crouching 1.27, fall-flying/swimming/spin 0.4; sleeping remains 0.2 through the `Pose.SLEEPING` special case; dying is explicitly 1.62. New dimensions scale eye height with the new SCALE attribute only when scale differs from its default, which is outside scope. `EntityDimensions.makeBoundingBox` retains the same centered-width/height AABB expression. |
| `Player.causeFallDamage`, same file | changed: tracks/clears current impulse impact context and may alter fall-damage computation | same file | Damage computation is excluded; no changed position/velocity writer in this method. The corresponding `ServerPlayer.onExplosionHit` context setter is server-only. |
| `Player.attack(Entity)`, same file | changed: adds an early `REDIRECTABLE_PROJECTILE` deflection path before ordinary hit/knockback handling | same file | **Partial; tag dependency open.** The ordinary successful-hit/knockback path still damps the attacking player's horizontal velocity by `0.6` (1.20.4 line 1186; 1.20.5/1.20.6 line 1205), but the new early deflection path bypasses it. An eligible player movement delta requires a pre-existing projectile that both belongs to the new dynamic tag and is attackable/hurt successfully in 1.20.4. Exact tag members and that target gate are not established in this slice; coordinate with the resource/tag coverage owner before accepting or excluding this candidate. |
| `Pose`, `net/minecraft/world/entity/Pose.java`; `EntityDimensions`, `net/minecraft/world/entity/EntityDimensions.java` | changed API/representation (pose IDs; dimension record/eye-height/attachments) | same file | No movement-pose box-size change found. Attachment additions are outside player self-movement. |

The relevant inventory file identities are:

| Source | 1.20.4 | 1.20.5 = 1.20.6 |
|---|---|---|
| `LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `EntityDimensions.java` | `ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` |
| `Pose.java` | `a8d0e2b720ae19f59ba832ad8a6e689bcc0505c2eda529ba6e36e581614587b2` | `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca` |

## Source anchors and call path

- `LocalPlayer.tick` and `aiStep`: 1.20.4 `LocalPlayer.java:188,646`; 1.20.5/1.20.6 `LocalPlayer.java:190,648`. The changed flight-toggle branch is in 1.20.5 `LocalPlayer.java:729–740` and 1.20.6 at the same lines; it belongs to the already-covered flight/jump slice.
- Position/box and ground continuation: 1.20.4 `Entity.java:353,381–387,412–416,556–562,596–647,1108`; 1.20.5/1.20.6 `Entity.java:355,383–389,414–418,557–563,597–648,1124`. The examined methods retain the same call/order semantics; `refreshDimensions` starts at 1.20.4 line 2722 and 1.20.5/1.20.6 line 2733.
- Pose refresh: 1.20.4 `Entity.java:2717–2730,2775–2784`, `LivingEntity.java:3161–3164,3261–3268`, `Player.java:126–139,1833–1845,1944–1946`; 1.20.5/1.20.6 `Entity.java:2728–2747,2786–2793`, `LivingEntity.java:3240–3245`, `Player.java:131–149,1997–1999`, `EntityDimensions.java:7–49`. Sleeping pose eye-height preservation is visible at 1.20.4 `LivingEntity.java:159,3261` and 1.20.5/1.20.6 `LivingEntity.java:168,3240–3241`.
- Travel gravity: 1.20.4 `LivingEntity.java:2035–2168`; 1.20.5/1.20.6 `LivingEntity.java:2100–2238`; `Attributes.GRAVITY` is introduced at 1.20.5 `Attributes.java:42–43` with default `0.08`.
- Contact response: 1.20.4 `LivingEntity.java:1442,1975`, `Entity.java:1398`; 1.20.5/1.20.6 `LivingEntity.java:1475,2032`, `Entity.java:1414`. Attacker self-velocity damping / new projectile branch: 1.20.4 `Player.java:1108,1186`; 1.20.5/1.20.6 `Player.java:1127,1136–1137,1205`.

## Open boundary

One player-reachable direct velocity writer remains a partial candidate: `Player.attack(Entity)` depends on exact dynamic tag membership and target hurt/attackability for the new early redirect branch described above. Other examined post-travel/core position, ground, box, contact, and pose writers yielded no eligible delta. Inputs explicitly out of this residual slice remain separately covered or excluded: packet writers, the flight-toggle jump, travel/fluid formulas, collision providers, block callbacks, server-only producers, and modern-only attributes/features. This report did not audit every path in the broader entity/player roster and does not claim tick-graph dependency closure, independent coverage audit, or pair freeze. The parent should keep those pair-level gates open until it reconciles all assigned slices and their remaining inventory rows.
