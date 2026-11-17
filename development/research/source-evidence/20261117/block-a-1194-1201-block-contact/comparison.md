# 1.19.4 → 1.20.1 block-contact residuals

## Scope and result

Canonical Mojmap source comparison for the three assigned client-player contact paths only:

1. Cobweb and sweet berry bush stuck-input multiplier production and consumption.
2. Block speed-factor selection and its position/order relative to movement contact callbacks, including Soul Sand, Magma Block and the independent Honey Block speed-factor property.
3. Slime Block and Bed Block direct landing velocity responses, including crouch/sneak gates.

**Result:** No movement difference is supported for these paths in 1.19.4 → 1.20.1. This is a scoped coverage delta, not a positive finding. No canonical finding file is warranted. Block geometry was treated only as the provider dependency for reaching a callback; no geometry comparison is claimed. Sweet-berry damage, Magma damage, fall-damage calculation, Honey Block custom cadence, and non-player/vehicle behavior are excluded.

## Exact source identity

Both source roots contain ready exact-version Mojmap/Vineflower 1.12.0 trees. For each version, the ready marker's version, namespace, source-manifest SHA-256 and artifact-manifest SHA-256 match the corresponding root sidecars; source-file SHA-256 values below also match the pinned source manifest.

| Version | Ready marker | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|
| 1.19.4 | `decompiled_minecraft/1.19.4/mojmap.ready.json` (status `ready`, 4,740 files) | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` | `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef` |
| 1.20.1 | `decompiled_minecraft/1.20.1/mojmap.ready.json` (status `ready`, 4,786 files) | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` | `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd` |

The matching provenance sidecars are `decompiled_minecraft/<version>/mojmap.provenance.json`; both identify the exact release and `mojmap` namespace/mapping option. These marker and manifest hashes identify the prepared local evidence; they are not runtime-parity evidence.

## Compared slices

### 1. Stuck multiplier production and consumption

- **1.19.4:** `net/minecraft/world/entity/Entity.java`, `move` lines 549–689; `checkInsideBlocks` lines 896–925; `makeStuckInBlock` lines 2208–2211. `WebBlock.entityInside` lines 15–20; `SweetBerryBushBlock.entityInside` lines 70–86.
- **1.20.1:** `Entity.java`, `move` lines 593–755; `checkInsideBlocks` lines 965–994; `makeStuckInBlock` lines 2315–2318. Corresponding block callbacks are unchanged.
- **Comparison:** On entry to `move`, when the stored multiplier's squared length exceeds `1.0E-7`, requested displacement is multiplied by it, the stored value is cleared, and delta movement is zeroed. After position/collision response and `stepOn`, `tryCheckInsideBlocks` invokes intersected states' `entityInside`; the web supplies `(0.25, 0.05F, 0.25)`, while the berry bush supplies `(0.8F, 0.75, 0.8)` for living entities other than foxes and bees. `makeStuckInBlock` resets fall distance and stores that vector. Thus a normal player movement call produces the multiplier for the next `move` invocation; the subsequent invocation consumes it before collision resolution. The production values, consumer threshold, clear/reset order and surrounding dispatch are the same in both versions.
- **Dependencies/dispositions:** The block callbacks are directly comparable and byte-identical between releases. Contact geometry is only a reachability dependency and was not compared. Berry-bush damage is outside this movement finding.

### 2. Speed-factor authority and motion order

- **1.19.4:** `Entity.java`, `move` lines 659–662, `getBlockSpeedFactor` lines 750–758 and `getBlockPosBelowThatAffectsMyMovement` lines 760–762; `LivingEntity.java`, `getBlockSpeedFactor` lines 461–463; `Block.java`, `getSpeedFactor` lines 400–402; `BlockBehaviour.java`, default/property setup lines 864 and 918–946; `Blocks.java`, Soul Sand around line 1286, Magma Block around line 2843, Honey Block around line 3879.
- **1.20.1:** Corresponding `Entity.java` slices are lines 700–703, 823–831 and 790–792; `LivingEntity.java` lines 476–478; `Block.java` lines 390–392; `BlockBehaviour.java` lines 912 and 955–1006; `Blocks.java`, Soul Sand around line 2280, Magma Block around line 4950, Honey Block around line 6652.
- **Comparison:** The player uses the living-entity override: Soul Speed on a Soul Speed block yields factor `1.0F`; otherwise it delegates to Entity. Entity reads the state at `blockPosition()`; unless that state is water or a bubble column, a factor exactly equal to `1.0` falls back to the block at `BlockPos.containing(position.x, boundingBox.minY - 0.5000001, position.z)`. `move` invokes inside-block callbacks first, then gets this factor and multiplies only delta-movement x/z by it. These gates, floating-point comparison and order are unchanged. Soul Sand's `0.4F` property remains; Magma Block uses the unchanged default `1.0F`; Honey Block's independent `0.4F` property is also unchanged. This closes the factor-authority/order slice without evaluating Honey's custom contact cadence.
- **Dependencies/dispositions:** `BlockBehaviour` carries the default and property; `Block.getSpeedFactor` exposes it; the entity method selects which contacted/supporting block is authoritative. Magma's random-tick particles and damage behavior are excluded.

### 3. Slime and bed landing response

- **1.19.4:** `Entity.java`, movement dispatch at lines 597–614, `checkFallDamage` around lines 1002–1017, `isSteppingCarefully`/`isSuppressingBounce` lines 1993–1998; `client/player/LocalPlayer.java`, `isShiftKeyDown` lines 595–598. `SlimeBlock.java`, `fallOn`/bounce at lines 18–41 and `stepOn` lines 44–51. `BedBlock.java`, `fallOn`/bounce at lines 127–145.
- **1.20.1:** `Entity.java`, movement dispatch at lines 640–658, `checkFallDamage` around lines 1101–1115, sneak gates lines 2096–2101; `client/player/LocalPlayer.java`, `isShiftKeyDown` lines 598–601. The corresponding Slime and Bed response methods have the same bodies as in 1.19.4.
- **Comparison:** On downward collision the movement dispatcher calls the landed-on block's `updateEntityAfterFallOn`; a grounded move calls `stepOn`. Slime and bed both suppress their bounce when `isSuppressingBounce()` is true; otherwise their bounce response replaces a negative y velocity with its negation times `1.0` for a living player (the non-living multiplier is `0.8`). Bed multiplies that result by `0.66F`, and halves fall distance in `fallOn` (the fall-damage outcome is excluded). Slime's grounded `stepOn` also scales x/z velocity when `abs(y) < 0.1` and `!isSteppingCarefully()`. Both sneak gates resolve to `isShiftKeyDown()` in each Entity version. The LocalPlayer override reads `this.input != null && this.input.shiftKeyDown` in both versions, so the client player's sneak input gate is unchanged. Player response formulas, gate and dispatch order are unchanged.
- **Dependencies/dispositions:** This closes direct player velocity response and the sneaking gate. Generic collision/ground-state math and fall-damage calculation are not claimed.

## Source-file pins

Each digest below was checked against that version's `mojmap.sources.sha256` and against the file bytes.

| Source file relative to `<version>/mojmap/` | 1.19.4 SHA-256 | 1.20.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |\n| `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/world/level/block/WebBlock.java` | `eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49` | `eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `aaf99e276258941ad011a913c518db6604351637601cbbf5e4a6cd6815520a64` | `aaf99e276258941ad011a913c518db6604351637601cbbf5e4a6cd6815520a64` |
| `net/minecraft/world/level/block/Block.java` | `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` |
| `net/minecraft/world/level/block/Blocks.java` | `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` |
| `net/minecraft/world/level/block/SlimeBlock.java` | `f5ce06a58cb3e46e6f3c900bdf521e2984765846dc55ca701812194d09d65f54` | `f5ce06a58cb3e46e6f3c900bdf521e2984765846dc55ca701812194d09d65f54` |
| `net/minecraft/world/level/block/BedBlock.java` | `62c0e838509405df5be47e025e852ae9f074cb264508aedd7e76e56148e4db3e` | `aa5fad8b3d4e77af5064b49f2670fa47b259a82e8dbbb80023111439f784b6f4` |

## Coverage boundary

Examined the scoped paired Entity movement/contact dispatch, stuck multiplier production and consumption, speed-factor override/property authority and ordering, and Slime/Bed landing/sneak callbacks. The Magma Block speed-factor property path was checked through the unchanged default; its out-of-scope damage/particle operations were not treated as movement findings. No other Entity.move callers, player tick inventory, collision providers, contact mechanics, or tick-wide movement areas are claimed by this residual closure.
