# Moving Stone temporal audit verdict

## Verdict

**ACCEPT, narrowly scoped:** in the 1.8.9 Feather Gen2 build.2 source, `MovingBlock.getCollisionShape` returns the carried block's local collision shape clipped by the prior-tick progress. For ordinary carried Stone (full local unit AABB), extension computes `f = 1 - lastProgress`; the facing-side extent is trimmed. A positive facing offset adjusts the corresponding maximum; a negative offset adjusts the minimum.

This verdict concerns the moving block collision shape. It does not establish parity for direct player pushes.

## Immutable source identities and references

- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java` — SHA-256 `700010B071738738F04E0508050590D8D625F0115FC4886FEF6E262F85C6E85`. `getCollisionShape(World, BlockPos, BlockState)`, lines 118–129, obtains the moving entity, samples `getProgress(0.0F)`, applies `1.0F - f` when extending, and delegates to the moved-state overload. The overload at lines 162–195 obtains the moved block's local collision AABB and adjusts only the facing axis: negative offsets modify min at 175–176, 181–182, 187–188; nonnegative offsets modify max at 177–178, 183–184, 189–190.
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java` — SHA-256 `05A5BFBF2556EAA621996BDA365B7BAB01F5B9060CF773E7B9949EBE2F39FFF6`. `getProgress(float)`, lines 54–60, returns `lastProgress + (progress - lastProgress) * tickDelta`, so the collision caller's zero tick delta samples `lastProgress` (with only an upper clamp on tickDelta). `tick()`, lines 128–145, copies progress to lastProgress, increments by 0.5, clamps to 1, and for extension calls `moveEntities` with the updated progress.
- Range check on `MovingBlock.java`: 1.8 and 1.8.1 have identical file SHA-256 `614CC52B357152F6F515B33EA59440DFE338227EA0CE4E7DAC7E43A13A95BA7D`; 1.8.2 through 1.8.9 have identical SHA-256 `700010B071738738F04E0508050590D8D625F0115FC4886FEF6E262F85C6E85`. The formula is therefore accepted for 1.8.2–1.8.9 by byte-identical source. For 1.8 and 1.8.1, the file differs; the 1.8.9 conclusion must not be promoted to those revisions solely from whole-file identity.

## Temporal comparison and projection limits

- **1.9 is different.** `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java` — SHA-256 `7AD3426BB74C9E69B4F521D20E045B1DC1EAFA9B725DAF0E2C0B9B718BC2683C`. `getShape(WorldView, BlockPos)`, lines 79–80, unions results for current `progress` and `lastProgress`. Its per-progress method at lines 83–87 translates the moved block's shape by the progress-derived offset; it does not apply the 1.8 clipped-plate formula. `tick()`, lines 164–178, copies current progress to last progress and advances by 0.5. **REJECT** describing 1.9 as the same single-sample prior-progress plate.
- **26.2 collision dispatch and shape input are different.** `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/piston/MovingPistonBlock.java` — SHA-256 `9A8D9B03BE6866B1E5A2D237F8CB635B89C08E63A241DB2D32338448240102D1`. `getCollisionShape(BlockState, BlockGetter, BlockPos, CollisionContext)`, lines 107–110, fetches the moving block entity and calls its shape method with `(level, pos)`; `CollisionContext` is not forwarded.
- `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` — SHA-256 `706BAB1A13BA99BBD334AAD853B99F72DF58FCE5B6B67D30D41F902B63DD8E06`. `getExtendedProgress(float)`, lines 106–108, is `progress - 1` for extension and `1 - progress` for retraction. `getCollisionShape(BlockGetter, BlockPos)`, lines 366–393, handles source piston-head and no-clip cases, selects the moved state for a non-source moving block, samples current `this.progress`, asks for the moved state's context-free shape, translates it by the extension offset, and unions any piston-head shape. `tick(...)`, lines 310–342, copies progress to `progressO`, computes `newProgress = progress + 0.5`, runs push/stuck handling, then stores and clamps progress. The block collision getter samples current progress directly; it does not interpolate through `progressO`.

**Projection finding:** for ordinary non-source extending Stone, the modern collision shape is a translated full cube at `(progress - 1) * facing`, while 1.8.9 returns a clipped local cube based on `lastProgress`. Thus the 1.8.9 previous-progress plate does not directly project to the 26.2 shape getter at the same block position and progress. A profile whose collision-query phase, progress sample/cadence, or moving-block position origin cannot be mapped to the source behavior is **not projectable from these sources alone**. No coordinate rebasing or extra timing assumption is established here.

## Direct player pushes are separate

In 1.8.9 `MovingBlockEntity.moveEntities(float, float)`, lines 80–112, transforms the passed progress for the collision-box lookup and then calls `entity.move` using the separate `amount`; the extending tick supplies `progress - lastProgress + 0.0625F` at lines 144–145. In 26.2, `tick(...)` invokes `moveCollidedEntities` and `moveStuckEntities` before storing the new progress (lines 335–340); the collision-push path uses movement direction, progress delta, collision-related shape and a movement area (lines 120–130, 251–257). These are distinct player-response operations. The accepted collision-shape claim is not a verdict of direct-push parity.

## Separate witness identity

The accepted witness is identified by the supplied SHA-256 `05a2b44304fe83008b9896d7bb5bcf23b51ea85e3a4f38ee5a94174d87c6ec91` for `moving-block-stone-sweep-witness-r2.md`. This is recorded only as a separate evidence identity; this audit did not inspect or validate that witness's bytes.

Static source review only. No product/source files were changed; no build, tests, or runtime were run.