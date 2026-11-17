# Climbing travel source slice: 26.1.2 → 26.2

## Verdict

Bounded source comparison found no movement-semantic change in the assigned ordinary climb travel clamps, climbable predicate, or Player override. This is slice coverage only; it does not establish full-pair coverage, acceptance, implementation, or runtime parity. Pair status remains partial. No introduction boundary is inferred.

## Exact source identities

Both endpoints are ready `unobfuscated` decompilations from Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, Gson 2.14.0, TinyRemapper 0.14.1.

| Endpoint | Ready identity | Provenance identity | Source manifest SHA-256 | Artifact manifest path and SHA-256 |
| --- | --- | --- | --- | --- |
| 26.1.2 / unobfuscated | `versionId=26.1.2`, `status=ready`, `sourceFileCount=6882`, `sourceBytes=32992615`; ready file SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `namespace=unobfuscated`; exact run is `decompileMinecraft --versions=26.1.2 --mappings=unobfuscated`; success log kind `full-gradle-output`; provenance file SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `unobfuscated.sources.sha256`; declared and actual `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | Ready-declared `artifacts.sha256` at `decompiled_minecraft/26.1.2/artifacts.sha256`; declared and actual `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 / unobfuscated | `versionId=26.2`, `status=ready`, `sourceFileCount=7055`, `sourceBytes=33813717`; ready file SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `namespace=unobfuscated`; exact run is `decompileMinecraft --versions=26.2 --mappings=unobfuscated`; success log kind `summary-excerpt-only`; provenance file SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `unobfuscated.sources.sha256`; declared and actual `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | Ready-declared `artifacts.sha256` at `decompiled_minecraft/26.2/artifacts.sha256`; declared and actual `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

Compared source file hashes match each endpoint's source manifest:

- `net/minecraft/world/entity/LivingEntity.java`: 26.1.2 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; 26.2 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- `net/minecraft/world/entity/player/Player.java`: 26.1.2 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; 26.2 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

## Ordered bounded coverage

1. `LivingEntity.travelInAir` → `handleRelativeFrictionAndCalculateMovement` caller edge, limited to confirming the latter is reached on the ordinary air travel path: 26.1.2 `LivingEntity.java:2430-2435`; 26.2 `:2468-2475`. The callees are invoked in the same position in the path.
2. `LivingEntity.handleRelativeFrictionAndCalculateMovement`: 26.1.2 `:2623-2633`; 26.2 `:2676-2686`. Both perform relative movement, apply `handleOnClimbable` to delta movement, move, then replace vertical movement with `0.2` under `(horizontalCollision || jumping) && (onClimbable() || wasInPowderSnow && canEntityWalkOnPowderSnow(this))`. Order and gate are unchanged.
3. `LivingEntity.handleOnClimbable`: 26.1.2 `:2650-2665`; 26.2 `:2703-2718`. Both gate on `onClimbable()`, reset fall distance, clamp X/Z to `[-0.15F, 0.15F]`, clamp Y only from below with `Math.max(delta.y, -0.15F)`, then set Y to zero when it is negative, current in-block state is not scaffolding, ladder-slide suppression is active, and the entity is a Player. Operation order and literals are unchanged; there is no upper Y clamp.
4. `LivingEntity.onClimbable`: 26.1.2 `:1689-1707`; 26.2 `:1722-1740`. Both return false for spectators; otherwise derive `ladderCheckPos` from `blockPosition()`, read `getInBlockState()`, reject glide-through state while fall-flying, accept `BlockTags.CLIMBABLE` and save the position, or accept a trapdoor only when `trapdoorUsableAsLadder` succeeds and save the position. The fallback is false. Predicate order and side effect are unchanged.
5. `LivingEntity.trapdoorUsableAsLadder`: 26.1.2 `:1709-1716`; 26.2 `:1742-1749`. Both require the current trapdoor's OPEN property, then a ladder directly below whose FACING equals the trapdoor FACING. Conditions and order are unchanged.
6. `LivingEntity.isSuppressingSlidingDownLadder`: 26.1.2 `:3593-3595`; 26.2 `:3659-3661`. Both delegate to `isShiftKeyDown()`; unchanged.
7. `Player.onClimbable`: 26.1.2 `Player.java:2002-2005`; 26.2 `:2023-2026`. Both return false if `abilities.flying`, otherwise delegate to `super.onClimbable()`; unchanged.

In the assigned files, 26.2 declares `CLIMBING_VERTICAL_SPEED = 0.2` at `LivingEntity.java:173`, while the covered vertical overrides remain literal `0.2` values. A source search found no other occurrence of the constant in that file, so this declaration does not alter the covered calculations.

## Bounded movement facts

A player on the ordinary air path reaches the shared friction/collision movement helper. When the climbable predicate passes, the pre-move delta has horizontal components restricted to ±0.15 and downward Y restricted to no less than -0.15; fall distance is reset before those restrictions. Sliding suppression can zero negative Y for a non-scaffolding in-block state on a Player. After collision movement, horizontal collision or jumping can force returned Y to 0.2 when climbable (or the separate powder-snow condition) passes. A Player with flying ability fails the climbable predicate before the superclass block-state checks.

## Open dependencies and next gate

World/state/tag dependencies remain open: `getInBlockState()` and `blockPosition()` resolution; tag membership for `BlockTags.CAN_GLIDE_THROUGH` and `BlockTags.CLIMBABLE`; trapdoor OPEN/FACING and the below-block ladder/FACING state; scaffolding state check; and the powder-snow side of the caller gate. This pass did not inspect tag/resource definitions, block-shape registration/providers, general travel branches, collision implementation, or the source/semantics of the `isShiftKeyDown()` input. Next bounded gate: verify only the cited state/tag and neighbor-state dependencies from exact resources/source, then independently audit this slice. No implementation or runtime conclusion follows.
