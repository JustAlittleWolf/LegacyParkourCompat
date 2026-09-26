# F02: Soul Sand slowdown is applied per overlapping cell in 1.14.4, once in 1.15.2

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stage 4 entity movement/contact callbacks; stage 5 Soul Sand movement input
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A: `net.minecraft.world.level.block.SoulsandBlock.entityInside(BlockState, Level, BlockPos, Entity)` multiplies horizontal delta movement by `0.4` at lines 28–30. SHA-256 of `SoulsandBlock.java`: `3F2CE73AAE23FB7E13CF001123343C6C750F00684B5331A5597AE33B011EF4E4`.
- A: `Entity.checkInsideBlocks()` iterates every integer block cell in the padded bounding-box range and invokes `blockState.entityInside(...)` for each at lines 810–831. SHA-256 of `Entity.java`: `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7`.
- B: `SoulsandBlock` has no `entityInside` override; the inherited `Block.entityInside(...)` is empty (B `Block.java` lines 644–646). SHA-256 of `SoulsandBlock.java`: `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF`; B `Block.java`: `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D`.
- B: `Entity.move(...)` calls `getBlockSpeedFactor()` once at line 543, after `checkInsideBlocks()`. `getBlockSpeedFactor()` samples the block at `new BlockPos(this)` first and returns its non-default factor at lines 590–598. B `Entity.java` SHA-256: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- B: `Blocks.SOUL_SAND` is registered with `speedFactor(0.4F)` at lines 601–604. A's registration at lines 577–579 has no factor field; the slowdown is instead the callback above. A `Blocks.java` SHA-256: `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9`; B: `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`.
- The Soul Sand collision shape is 14/16 high in both versions (A/B `SoulsandBlock.java` line 16). Standing player width is `0.6F` in both `Player.java` files (A line 110; B line 111). A Player SHA-256: `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12`; B: `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`.

## Source-level difference

If the player's bounding box straddles the seam between two adjacent Soul Sand cells while standing on their 14/16-high top surface, A's block-cell loop visits both cells. Each callback multiplies X and Z delta movement by `0.4`, so that movement invocation applies the factor twice. B's callback is empty; its post-callback speed-factor lookup samples one block at the entity position and applies `0.4` once.

The source proves the per-cell loop, callback behavior and single selected-cell factor path. Under the stated two-cell precondition, the horizontal multiplier is `0.4 * 0.4` in A and `0.4` in B for that movement invocation.

## Reachability and dependencies

Local player travel -> `Entity.move` -> `checkInsideBlocks` -> Soul Sand callbacks in A; in B, `Entity.move` continues to the one `getBlockSpeedFactor()` lookup. A player's 0.6-wide bounding box can intersect both cells near a seam while its center lies in one cell. The 14/16 collision top leaves the entity's lower bounding-box coordinates in the same block row used by the callback loop.

## Consequence and uncertainty

Source confirms a stronger horizontal velocity reduction in A when two Soul Sand cells are processed during one movement call, compared with B's one selected block factor. No trajectory or gameplay result was observed. This is distinct from the general grounded friction lookup in F01.

## Handoff

Independent existing-block behavior change caused by replacing per-cell Soul Sand velocity callbacks with a single movement speed-factor lookup. The block exists in both endpoints and historical maps; this is within the compatibility scope. First changed release remains unknown within the inspected endpoints.
