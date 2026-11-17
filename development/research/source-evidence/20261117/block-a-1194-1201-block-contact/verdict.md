# Independent source audit: block-contact residuals

## Verdict

**Accepted: all three bounded no-difference claims.** The exact Mojmap 1.19.4 and 1.20.1 source pairs show no change in the assigned local-player contact operations: Web/Berry stored multiplier behavior and order; block speed-factor selection and x/z scaling order for Soul Sand/Magma; Slime/Bed landing Y responses and the sneak gate. This is scoped source coverage, not a positive movement finding.

The compared explicit input was rehashed immediately before acceptance: `lane-block-contact-1194-1201/comparison.md`, SHA-256 `5c07a69b69eba2d1f8859fff4655b21819da76afa08c135b79f8466cd0f16def` (UTF-8, no BOM, LF-only). The frozen 26-file source roster was also rehashed against each side's published source manifest: canonical UTF-8 LF SHA-256 `fb32122f094fb175a009b7c03d3d082b7bab51b3f3f67f98eb54a21d3df04aa1`.

## Source readiness and identity

Both canonical roots are ready exact-version Mojmap/Vineflower trees. Ready markers, provenance sidecars, source-manifest and artifact-manifest hashes agree. The source trees contain the exact ready-marker counts and bytes: 1.19.4 has 4,740 source files / 23,021,785 bytes; 1.20.1 has 4,786 / 23,333,756 bytes.

| Version | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` | `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef` |
| 1.20.1 | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` | `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd` |

## Claim-by-claim adjudication

### 1. Web and sweet-berry stuck multiplier

**Accepted, no difference.** In both versions `Entity.move` first checks `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`; it multiplies the incoming displacement, clears the stored multiplier, and zeroes delta movement in that order. After collision handling and `stepOn`, `tryCheckInsideBlocks` dispatches `entityInside`. Web stores `new Vec3(0.25, 0.05F, 0.25)`; Sweet Berry Bush stores `new Vec3(0.8F, 0.75, 0.8)` for a `LivingEntity` that is neither fox nor bee. `makeStuckInBlock` resets fall distance before storing the vector. Thus a local player contact stores the factor for the next `move` invocation, where the same threshold, multiply, clear, and velocity reset consume it. Callback values, consumer order, and dispatch order are unchanged.

Sources: 1.19.4 `Entity.java` 549–689, 896–925, 2208–2211; `WebBlock.java` 15–20; `SweetBerryBushBlock.java` 70–86. 1.20.1 `Entity.java` 593–755, 965–994, 2315–2318; corresponding block callback bodies are byte-identical.

### 2. Soul Sand/Magma speed factor and scaling order

**Accepted, no difference.** The local player's `LivingEntity.getBlockSpeedFactor` override still returns `1.0F` for a Soul Speed enchantment on a Soul Speed block, otherwise delegates to `Entity`. The shared entity selector reads the block at `blockPosition()`; unless it is water or a bubble column, a factor exactly `1.0` falls back to the block at `BlockPos.containing(position.x, boundingBox.minY - 0.5000001, position.z)`. At the end of the same movement operation, after `tryCheckInsideBlocks`, `move` gets that factor and scales delta movement x/z while leaving y multiplied by `1.0`. These reads, gates, float comparison, and order match across the pair.

Soul Sand's registered `.speedFactor(0.4F)` is unchanged. Magma Block has no speed-factor override in either registration and uses the unchanged `BlockBehaviour.Properties` default `1.0F`. These provider values and the callback path are dependencies only; no general collision geometry is claimed.

Sources: 1.19.4 `Entity.java` 659–662, 750–762; `LivingEntity.java` 461–463; `Blocks.java` 1286–1297 and 2843–2855; `BlockBehaviour.java` 864, 918–946. 1.20.1 corresponding slices: `Entity.java` 700–703, 823–831, 790–792; `LivingEntity.java` 476–478; `Blocks.java` 2280–2293 and 4950–4962; `BlockBehaviour.java` 912, 955–1006.

### 3. Slime/Bed landing Y response and sneak gate

**Accepted, no difference.** On a changed vertical displacement, `Entity.move` calls the landed-on block's `updateEntityAfterFallOn`; a grounded move then calls `stepOn`. Slime and Bed use the same response methods in both versions. With `isSuppressingBounce()` false, only negative y velocity bounces: Slime sets player y to `-y` (the LivingEntity multiplier is `1.0`); Bed uses `-y * 0.66F * 1.0`. If suppression is true, both delegate to `Block.updateEntityAfterFallOn`, which multiplies by `(1.0, 0.0, 1.0)` and clears y. Both `Entity.isSuppressingBounce` methods delegate to `isShiftKeyDown`; `LocalPlayer.isShiftKeyDown` reads `this.input != null && this.input.shiftKeyDown` in each release. The local client's sneak input and the player Y response therefore have the same gate and arithmetic. This verdict covers the direct velocity response and input gate only.

Sources: 1.19.4 `Entity.java` 597–614, 1993–1998; `LocalPlayer.java` 595–598; `Block.java` 384–390; `SlimeBlock.java` 18–41; `BedBlock.java` 127–145. 1.20.1 corresponding slices: `Entity.java` 640–658, 2096–2101; `LocalPlayer.java` 598–601; `Block.java` 374–380; `SlimeBlock.java` 18–41; `BedBlock.java` 126–144.

The vanilla movement methods are shared methods and do not themselves impose a logical-side gate. `LocalPlayer` establishes the direct local-client input path; historical-mod client-only execution remains the project invariant, not a side check asserted for these vanilla methods.

## Bounded source roster

Every file digest below was rechecked against that version's `mojmap.sources.sha256` and actual bytes. The canonical roster hash above is SHA-256 over lines `<version>|<file SHA-256>|<relative path>\n`, UTF-8 without BOM, sorted by version then relative path.

| Relative source file | 1.19.4 SHA-256 | 1.20.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/world/entity/player/Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/level/block/BedBlock.java` | `62c0e838509405df5be47e025e852ae9f074cb264508aedd7e76e56148e4db3e` | `aa5fad8b3d4e77af5064b49f2670fa47b259a82e8dbbb80023111439f784b6f4` |
| `net/minecraft/world/level/block/Block.java` | `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` |
| `net/minecraft/world/level/block/Blocks.java` | `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` |
| `net/minecraft/world/level/block/MagmaBlock.java` | `10974833ef6c3599095912de138f95ee5720b6623d98d001bf9024cebdb17fe8` | `434fa5f277cf1b318d212334fe8edf55976781801dae2c5090d9efc364496006` |
| `net/minecraft/world/level/block/SlimeBlock.java` | `f5ce06a58cb3e46e6f3c900bdf521e2984765846dc55ca701812194d09d65f54` | `f5ce06a58cb3e46e6f3c900bdf521e2984765846dc55ca701812194d09d65f54` |
| `net/minecraft/world/level/block/SoulSandBlock.java` | `f6cc9ca6e841b16c4e739e840ac4ebce46b5b8bd40564473911f2246d1aab7d8` | `f6cc9ca6e841b16c4e739e840ac4ebce46b5b8bd40564473911f2246d1aab7d8` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `aaf99e276258941ad011a913c518db6604351637601cbbf5e4a6cd6815520a64` | `aaf99e276258941ad011a913c518db6604351637601cbbf5e4a6cd6815520a64` |
| `net/minecraft/world/level/block/WebBlock.java` | `eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49` | `eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` |
