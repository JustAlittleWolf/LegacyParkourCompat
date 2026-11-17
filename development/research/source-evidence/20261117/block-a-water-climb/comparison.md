# Water-travel climb impulse: blind source comparison

## Scope and result

Compared canonical read-only sources for 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4. The player water-travel branch gains a climb-specific vertical impulse between 1.13.2 and 1.14.0. It is present in 1.14 and remains in each inspected later source. This establishes the endpoint onset; no earlier 1.13 patch releases were assigned, so no finer patch boundary is claimed.

## Operation and call path

In 1.12.2 through 1.13.2, `LivingEntity.moveRelative` selects its swimming branch when `isInWater()` is true, except for a flying player. That branch moves, damps velocity, applies gravity, then only raises Y to `0.3F` when horizontal collision is present and `canMove`/clearance succeeds for the `+0.6F` candidate. It does not query `isClimbing()` in this branch. The separate land branch has a `0.2` climb response, which does not establish a swimming response.

Starting in 1.14, the water branch performs its normal `move`, reads the resulting velocity, and when horizontal collision and the climb predicate both hold, replaces only Y with `0.2`. It then applies water damping (`Y * 0.8F`), gravity handling, and retains the later `0.3F` clearance step-up. The new response is therefore a distinct post-move climb path and may run where the clearance check does not admit the old `0.3F` step-up. Its `0.2` assignment occurs before the water multiplier; it is not the final post-travel Y value.

The relevant branch is reachable from player movement. Through 1.13.2, `LivingEntity` invokes virtual `moveRelative` from its travel tick and `PlayerEntity.moveRelative` delegates to `super.moveRelative` outside the flying case. In 1.14, the analogous living-entity travel tick calls `moveRelative(Vec3d)` and `PlayerEntity` delegates to its superclass outside the flying case. In 1.14.1–1.14.4, the living-entity travel tick calls `travel(Vec3d/Vec3)` and the player override delegates to `super.travel` outside its flying case. These are direct source call paths, not a trajectory prediction.

## Predicate and block-state mapping

In 1.12.2–1.13.2, `isClimbing()` rejects spectator players, samples the block at `floor(x)`, `floor(bounding-box minY)`, `floor(z)`, and accepts exactly ladder, vine, or a `TrapdoorBlock` whose `OPEN` property is true and whose block below is a ladder with the same `FACING` property as the trapdoor. In 1.14 onward the predicate has the same provider classes and open-trapdoor/underlying-ladder facing match, and additionally accepts scaffolding. The entity feet-cell sample is expressed as `new BlockPos(this)` in the 1.14 sources.

For pre-1.14 profiles, ladder and vine identity, plus the trapdoor `OPEN`/`FACING` values and the lower ladder's `FACING`, are all explicit vanilla block-state facts and can be queried without inventing custom state. The state rule for an eligible trapdoor is exact: open trapdoor at the sampled feet cell, ladder directly below, equal facings. Closed trapdoors, trapdoors without that ladder, mismatched facings, and other block classes are excluded. Scaffolding is accepted by 1.14+ vanilla predicates but is a 1.14-era feature; it is not an old provider and must not be admitted to older profiles.

## Water-mode admission and continuity

The outer movement dispatch remains: not in water (or a flying player) takes the non-water branch; otherwise normal water travel is used. 1.12.2–1.14 use `isInWater()` for that branch. Yarn 1.14.1–1.14.3 spell the gate `isInsideWater()`; 1.14.4 uses `isInWater()` again. Their exact source field/predicate definitions differ, so this comparison does not assert those admission predicates are interchangeable for marginal fluid-contact states. The climb-specific post-collision condition itself is stable across the inspected 1.14 sources: horizontal collision plus the version's climb predicate, followed by Y assignment to `0.2` before water damping.

## Exact slices

- 1.12.2 Feather: `net/minecraft/entity/living/LivingEntity.java:1006-1029` (climb predicate), `1425-1429` (water dispatch), `1555-1584` (water branch; clearance response at 1582), `1514-1517` (land-only climb response). Player delegation: `net/minecraft/entity/living/player/PlayerEntity.java:1386-1400`.
- 1.13.2 Feather: `net/minecraft/entity/living/LivingEntity.java:1041-1068`, `1478-1486`, `1617-1655` (water branch; clearance response at 1652; no climb-predicate call in the water branch). Player delegation: `net/minecraft/entity/living/player/PlayerEntity.java:1442-1464`.
- 1.14 Feather: `net/minecraft/entity/living/LivingEntity.java:1232-1259` (predicate), `1788` (water dispatch), `1897-1920` (water movement, new climb impulse at 1900, retained clearance response at 1918). Player delegation: `net/minecraft/entity/living/player/PlayerEntity.java:1363-1387`; living travel-tick dispatch at `LivingEntity.java:2220`.
- 1.14.1 Yarn: `net/minecraft/entity/LivingEntity.java:1216-1258` (predicate), `1772` (water dispatch), `1881-1909` (water response and later clearance response); player delegation at `net/minecraft/entity/player/PlayerEntity.java:1362-1386`; living travel-tick dispatch at `LivingEntity.java:2204`.
- 1.14.4 Mojmap: `net/minecraft/world/entity/LivingEntity.java:1228-1256` (predicate), `1791` (water dispatch), `1897-1920` (water response and later clearance response); player delegation at `net/minecraft/world/entity/player/Player.java:1380-1404`; living travel-tick dispatch at `LivingEntity.java:2223`.
- 1.13 and 1.13.1 were checked at their complete `LivingEntity.moveRelative` water and land branches: same absence of a climb check in water and same `0.3F` clearance response as 1.13.2. 1.14.2 and 1.14.3 were checked at their complete `LivingEntity.travel` water branches: same horizontal-collision plus climb-predicate `0.2` response as 1.14.1.
- Exact distinguishing lines in those continuity sources: 1.13 Feather `LivingEntity.java:1633` (water clearance), 1.13.1 Feather `:1652` (water clearance), 1.14.2 Yarn `:1890` (water climb impulse), and 1.14.3 Yarn `:1900` (water climb impulse); their corresponding land/lava hits are separate earlier branch entries.

## Source identity and measured hashes

Namespace directories: 1.12.2–1.14 use `ornithe-feather`; 1.14.1–1.14.3 use `yarn`; 1.14.4 uses `mojmap`. Exact local provenance/source sidecars identify Feather build 2 for the assigned 1.12.2, 1.13.x, and 1.14 sources, and 1.14.4 Mojmap output; measured `LivingEntity.java` hashes match their source sidecars. The 1.14.1–1.14.3 Yarn directories have no local provenance or source-hash sidecars. Their hashes below are measurements of the present files only and do not independently prove their mapping artifact/source publication identity. The exact 1.13.2-to-1.14 finding endpoints do have sidecar-backed source identity.

| Release / namespace | `LivingEntity.java` SHA-256 | Local source identity |
|---|---|---|
| 1.12.2 / ornithe-feather | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | Provenance and source sidecar; Feather 1.12.2 build.2 |
| 1.13 / ornithe-feather | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | Provenance and source sidecar; Feather 1.13 build.2 |
| 1.13.1 / ornithe-feather | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | Provenance and source sidecar; Feather 1.13.1 build.2 |
| 1.13.2 / ornithe-feather | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | Provenance and source sidecar; Feather 1.13.2 build.2 |
| 1.14 / ornithe-feather | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` | Provenance and source sidecar; Feather 1.14 build.2 |
| 1.14.1 / yarn | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | No local provenance/source sidecar |
| 1.14.2 / yarn | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | No local provenance/source sidecar |
| 1.14.3 / yarn | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | No local provenance/source sidecar |
| 1.14.4 / mojmap | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | Provenance and source sidecar; Mojmap output from Feather 1.14.4 build.2 inputs |

## Residuals

- No 1.13.3 or other intermediate sources were assigned; the added impulse's implementation boundary is only established as after 1.13.2 and present by 1.14.0.
- 1.14.1–1.14.3 repeat the behavior in present source files, but their local Yarn source identity is unverified because the provenance/source sidecars are absent.
- Water admission predicate spelling/semantics differ in the assigned 1.14.1–1.14.3 Yarn sources (`isInsideWater`) from the surrounding Feather/Mojmap sources (`isInWater`); determine exact compatibility per source evidence before reusing a single modern query for those boundary states.
- The old climbing predicate's applicability outside the stated player water-travel call path and other movement consumers was not audited in this bounded assignment.
