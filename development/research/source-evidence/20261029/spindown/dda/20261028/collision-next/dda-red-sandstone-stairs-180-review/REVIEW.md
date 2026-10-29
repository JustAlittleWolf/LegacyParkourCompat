# Independent review: ID 180 red-sandstone stairs

Verdict: ACCEPT, bounded to the report's stated identity/provider-input scope.

## Exact reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-red-sandstone-stairs-180/FINDING.md`
  - SHA-256: `33D31EA216F56D7E67299ED7B7596AF0DFECEC195FE64D33CD698BA2810E112E`
- Source ledger: `.native-source/20261028/collision-next/dda-red-sandstone-stairs-180/sources.sha256`
  - SHA-256: `4E9F80E5F83CC9B33B3885480EDA0CC7C7FBDCD7C6EB36C8C51BEE9E6283178C`

All eight source-file SHA-256 entries in the ledger were recomputed from the listed canonical paths and matched exactly. The canonical decompiled trees do not contain a per-tree `.sources.sha256` manifest; the checked artifacts are the explicit files listed in the finding's ledger.

## Findings

- `Block.java` lines 1212–1217 bind numeric ID 179 to `block13`, a `RedSandstoneBlock`; ID 180 is registered under `red_sandstone_stairs` with `new StairsBlock(block13.defaultState().set(TYPE, SMOOTH))`. `RedSandstoneBlock` defaults to `DEFAULT`, while `SMOOTH` is metadata ID 2. The report correctly distinguishes the old smooth-red-sandstone base state from the stair block identity.
- `EntityBlockStateFix` maps `minecraft:red_sandstone_stairs` to 180. Current `BlockItemIds` keeps `red_sandstone_stairs` and `smooth_red_sandstone_stairs` as distinct IDs. `Blocks` registers the former from `RED_SANDSTONE` and the latter from `SMOOTH_RED_SANDSTONE`; the report's current-base-state distinction is accurate.
- The old shared `StairsBlock` metadata decoder uses bit 2 for half and `Direction.byId(5 - (metadata & 3))` for facing; the encoder writes the corresponding half bit and inverse facing. It carries no old metadata for `SHAPE`.
- The listed modern `BlockStateData` rows include red-sandstone stair facing/half/shape states. Current `StairBlock` declares `FACING`, `HALF`, `SHAPE`, and `WATERLOGGED`, and its shape is selected from stored state. The report appropriately avoids claiming metadata equivalence or old/current shape parity.
- The old movement collision override and ray override are shared `StairsBlock` providers and therefore apply to ID 180 through its constructor. The report's provider summary matches the source at the stated level. Neighbor logic is query-time and based on old stair instances; current stored-shape behavior is not established as equivalent. The report expressly leaves neighbor/provider closure, outer DDA order, and parity open.

## Limits

This acceptance covers only the source-backed ID 180 identity row and its shared provider inputs. It is not acceptance of a complete adapter, current/legacy ray equivalence, DDA behavior, or runtime movement parity. No product source was inspected and no tests, builds, or runtime were used.
