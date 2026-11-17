# Independent review: 1.11.2 → 1.12 StairsBlock collision matrix

## Verdict and scope

The bounded result is correct: there is no source difference in the ordinary `StairsBlock` collision-emission tuple, neighbor `SHAPE` resolution, candidate order, or block-box filter between exact 1.11.2 and 1.12 Ornithe Feather sources. I independently derived the 40 `(FACING, HALF, SHAPE)` rows before opening the assigned author artifacts; all 40 emitted-box sequences and all 18 local box extents agree with the report. The separate [independent derivation](independent-derivation.txt) contains the full matrix.

This is source/static review only. It does not establish runtime parity or an intermediate patch boundary. No game, test, build, or decompilation command was run.

## Source identity and integrity

- 1.11.2 is Ornithe Feather Gen2 build.2. Its ready marker pins client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`, and its source-manifest SHA-256 is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. The provenance file SHA-256 is `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`.
- 1.12 is Ornithe Feather Gen2 build.2. Its embedded `.decompile-provenance.json` SHA-256 is `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; it records client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`, Feather mapped archive SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`, exact 1.12 Feather metadata input, and the pinned decompiler/runtime/tool identity. This older tree has no separate ready/source-manifest files; the embedded provenance supplies the per-source manifest.
- I verified current source SHA-256 values against the 1.11.2 source manifest and 1.12 embedded provenance for `StairsBlock.java`, `Block.java`, `World.java`, `Entity.java`, `LocalClientPlayerEntity.java`, `Box.java`, and `Direction.java`; every checked file matched its recorded hash. The four source hashes printed in the author report also match.

## Independent operation audit

The compared `StairsBlock` state definition has horizontal `FACING`, two `HALF` values and five `SHAPE` values, giving 40 property tuples. Both source versions have identical text for the state/shape constants; `addCollisions`, `getCollisionShapes`, both directional shape selectors, `resolveVirtualProperties`, `getStairShape`, and its neighbor predicates. The `Direction` helper implementation is byte-identical as a source file.

For each tuple, emission starts with the half-specific base. It appends the facing/half inner box for `STRAIGHT`, `INNER_LEFT`, and `INNER_RIGHT`; then it appends an outer quadrant for every non-straight shape. The quadrant selector is identical in each version: `OUTER_LEFT` uses facing, `OUTER_RIGHT` clockwise, `INNER_RIGHT` opposite, and `INNER_LEFT` counterclockwise. Candidate order is base, optional inner, optional outer. The table in `independent-derivation.txt` matches all 40 author rows.

`addCollisions` resolves virtual properties only under `!forceShape`; when `forceShape` is true, it trusts the passed state. The ordinary World collector passes `false`, so it resolves the center stair before emission. `getStairShape` tests `pos + facing` first; only when that does not return does it test `pos + facing.opposite()`. A candidate neighbor must be a stair, share `HALF`, and have a perpendicular horizontal facing. Its extra-cell guard rejects a same-facing, same-half stair at the direction-specific side cell. Otherwise the result is `STRAIGHT`. The report's neighbor witness conditions are consistent with these gates.

`Block.addCollision` is identical: it skips only the `EMPTY_BLOCK_SHAPE` singleton, translates each local candidate by `BlockPos`, checks strict `queryBox.intersects(translatedBox)`, and appends survivors in candidate order. `Box.move(BlockPos)` and `Box.intersects` are identical across the pair. There is no merge/deduplication of overlapping candidates.

The ordinary player path statements checked out: World calls `blockState.addCollisions(..., false)`; its public list wrapper calls the collector with `enforceWorldBorder=false`; and `LocalClientPlayerEntity.move` forwards to `super.move` before auto-jump handling in both versions. The reported `Entity.move` difference is exactly one additional air flap-sound branch in 1.12 after the shared collision movement work; it does not alter the stair query/emission code. The reported extracted-body hashes for both World wrappers and the local-player forwarding method also reproduced.

## Small report/script qualifications

- The report should explicitly state the `forceShape=true` bypass branch as a gate. It already establishes that the ordinary player path passes `false`, so this is a prose completeness note, not a wrong matrix cell.
- `derive_matrix.py` hard-codes the direction maps and all 18 extents. It reads source files to hash selected method bodies, but it does not parse those bodies/constants or assert that the hard-coded table is extracted from them. Thus the table is independently confirmed here, while the script's reproduction description slightly overstates how source-driven that matrix generation is. Clarify the prose or add source assertions before describing it as mechanically derived from source.
- No matrix cells are missing, duplicated, or overstated. The 40 row set is internally complete and matches source selection and append gates.
