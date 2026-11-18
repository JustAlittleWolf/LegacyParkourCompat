# Source discovery: 1.18.1 → 1.18.2 ordinary vertical air integration

## Evidence identity

Exact Mojmap source trees: `decompiled_minecraft/1.18.1/mojmap/` and `decompiled_minecraft/1.18.2/mojmap/` in the primary checkout. Both ready markers authenticate the literal release ID and `mojmap` mapping family. Their declared source-manifest SHA-256 values match the actual sidecar hashes below; each cited Java file's SHA-256 also matches its manifest entry.

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | `mojmap.sources.sha256` SHA-256 | Ready marker identity |
|---|---|---|---|---|
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | versionId `1.18.1`; mapping `mojmap`; status `ready` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | versionId `1.18.2`; mapping `mojmap`; status `ready` |

Mojmap is the official Mojang-named mapping family in both trees. Each provenance records Vineflower 1.12.0 / Java 25 and the corresponding release; the 1.18.1 provenance also records the exact metadata ID and raw client/mapping artifact pins. Both marker and source manifests are intact. Source diagnostics report successful decompilation; movement methods were directly inspected below and are syntactically complete in the exact file bodies.

## Examined source files

All paths below are relative to the exact tree `decompiled_minecraft/<version>/mojmap/`.

| File | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |

## Path closure and coverage

- `LivingEntity.aiStep` reaches `this.travel(new Vec3(this.xxa, this.yya, this.zza))` in both releases (1.18.1 line 2548; 1.18.2 line 2552). `Player.travel` delegates to `super.travel` for the assigned non-flying case (1.18.1 lines 1424–1436; 1.18.2 lines 1437–1449). `LocalPlayer` delegates through the inherited tick/aiStep path; the inspected local-player overrides do not supply a separate `travel` implementation.
- In `LivingEntity.travel`, the ordinary branch follows the same sequence and math in both releases: obtain block friction; compute `$$27` (`onGround ? friction * 0.91F : 0.91F`); compute `$$28` via `handleRelativeFrictionAndCalculateMovement`; start `$$29` from `$$28.y`; apply gravity `$$29 -= 0.08` when not no-gravity; then write Y as `$$29 * 0.98F` unless `shouldDiscardFriction()`, in which case write `$$29` unscaled. References: 1.18.1 lines 2113–2136; 1.18.2 lines 2117–2140. The excluded fluid, levitation, slow-falling, gliding and flying branches do not alter this comparison.
- `Entity.move` remains the boundary for applying the requested/resolved movement to position and collision state. Its same ordinary landing gate remains `onGround = verticalCollision && requestedY < 0.0` in both releases (1.18.1 lines 568–579; 1.18.2 lines 579–593). `Entity.resetFallDistance()` remains a direct `fallDistance = 0.0F` in both (1.18.1 lines 2101–2103; 1.18.2 lines 2114–2116).
- `LivingEntity.checkFallDamage` still adds distance only when the resolved displacement is downward, then delegates to `Entity.checkFallDamage`; its server-only fall-damage/Soul Speed side effects are outside this dry client movement slice (1.18.1 lines 286–307; 1.18.2 lines 290–311).
- `Entity.baseTick` lava halving, fluid state and world/provider behavior are excluded by the assigned dry/no-fluid preconditions. Player fall-damage calculation, damage/statistics, jump causation, support/shape selection, horizontal math, visual/network state and non-player callers were not audited.

## Candidate finding: float conversion order in fall-distance accumulation

1.18.1, Mojmap: `net/minecraft/world/entity/Entity.java`, `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 979–981: when not grounded and resolved Y `$$0 < 0.0`, assigns `(float)(this.fallDistance - $$0)`. This promotes the existing float to double, subtracts the double displacement, then rounds the result once to float.

1.18.2, Mojmap: `net/minecraft/world/entity/Entity.java`, same method, lines 989–991: `this.fallDistance -= (float)$$0`. This first rounds the displacement to float, then performs float subtraction and stores that float result.

**Difference:** the cast moved from after subtraction to before subtraction. With `fallDistance = 0.25F` and an unobstructed resolved Y request of `-0.08`, 1.18.1 stores `0.33000001311302185F`; 1.18.2 stores `0.32999998331069946F` (approximately `2.98e-8` lower). The downwards branch is reached on ordinary falling movement whenever `onGround` is false; the witness isolates the accumulation operation and does not assert a particular natural trajectory. This is a concrete one-tick common fall-state divergence, with downstream threshold comparisons potentially observing the changed float.

**Canonical relative finding path:** `development/research/findings/1.18.1--1.18.2/fall-distance-accumulation-precision.md`.

## Additional disposition and boundaries

1.18.2 adds a pre-`setPos` clip in `Entity.move` (lines 564–572): if displacement length squared is at least `1.0` and `fallDistance != 0`, it clips the movement segment against `ClipContext.Block.FALLDAMAGE_RESETTING` / water and resets fall distance on a hit. No equivalent exists in 1.18.1's corresponding movement block (lines 561–564). This is a candidate environment-dependent reset, but its applicability depends on block tags/state and collision/clip providers, expressly outside this assignment's no-block air-only slice; report it to the block/collision owner rather than fold it into the isolated finding.

1.18.2 also adds `verticalCollisionBelow = verticalCollision && requestedY < 0.0` after collision (Entity.java line 583); the existing `onGround` gate remains the same. Exact-tree search found no consumer of this field outside its own write in the 1.18.2 source tree, so this addition has no demonstrated effect on the assigned vertical integration.

No difference found in the ordinary dry-air gravity constant, Y drag, their ordering, `shouldDiscardFriction` Y behavior, ground threshold, direct fall-distance reset, or the ordinary player-to-LivingEntity travel delegation. Coverage is limited to these declared classes/methods and dependencies; this does not claim full pair closure or independently audited pair freeze.
