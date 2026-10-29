# Independent review: 1.8.9 opaque-neighbor roster

## Verdict: REVISE (source dependency manifest)

The 198-row classification is internally complete and the observed decisions match the exact 1.8.9 sources I inspected. The producer's `source-hashes.json` does not hash all class files used by the row evidence, however, so the source bundle is not closed for independent replay. Add the missing files below to a new immutable source-manifest revision and rebind this review to that exact revision before treating the bundle as accepted. This is a manifest completeness correction; I found no row-value discrepancy in this review.

## Inputs and hashes

- `old-opaque-roster/FINDING.md`: `8AA7BDB0D06B3C306A831A0905215FADAA685E1FF2E816FB3672812755DE96D1`
- `old-opaque-roster/old-opaque-roster.csv`: `3A10D2961F0A8F94649BF329EABA148A1D16BE01811490BF62B96255F6A05ADB`
- `old-opaque-roster/source-hashes.json`: `C907DC92D9C07D1A43EBE57D8A2DE5BF9ED169B5BBBC9935D7485630B33CBCF0`
- Current `1.8.9/ornithe-feather.sources.sha256`: `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`

All 134 source files listed in the producer manifest matched their recorded byte counts and SHA-256 values, and their hashes matched the current `.sources.sha256`. All four referenced accepted report hashes in `source-hashes.json` also matched their current artifacts. The source manifest has 1,612 lines.

## Completeness checks

- Exactly 198 CSV rows; IDs 0 through 197 each occur once; no missing IDs.
- Required CSV columns have no blank values.
- `constructed_opaqueCube`: 72 `true`, 126 `false`, zero unresolved.
- Every row's stated registration line identifies the corresponding `register(id, ...)` call in `Block.init` (including multiline calls).
- The predicate outcome column agrees with the recorded opaque value and the explicit ID 101 exceptions. IDs 20, 95, 101, 102, and 160 have the expected positive exceptions; other nonopaque IDs reject.
- Exact source confirms `Block.isOpaque()` returns `opaqueCube`; `Block(Material, MapColor)` initializes the field with virtual `isSolidRender()`; the only `opaqueCube` assignments found in the full block package are that constructor assignment and `SlabBlock`'s double-slab assignment.
- Exact `PaneBlock.shouldConnectTo(Block)` is `block.isOpaque() || block == this || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE || block instanceof PaneBlock`.
- Slab double/single dispatch and leaves' constructor-time JVM-default `culling == false` explain the exceptional true classifications as recorded.

## Required hash-manifest correction

The roster references these class sources through registered concrete classes or `class_ancestry_to_basis`, but the producer's `source-hashes.json` omits them. I independently checked their current contents and matched each listed hash against the current source manifest; add these exact inputs to the next source-manifest revision:

| File | Bytes | SHA-256 |
|---|---:|---|
| `AbstractLogBlock.java` | 2600 | `A28EB4C72E8DED3DA6F6FB0FEE636AF44A9FC72694BABF1CC4EF902DCF4BB165` |
| `AbstractPressurePlateBlock.java` | 5569 | `85896B070BE577D228E8A660F1F95CAE8956467A957463C4FC839080D2E5B1CB` |
| `AbstractRailBlock.java` | 19263 | `743E8D1B8ACF24ACA8E297A2906D78B010960DAE21B0BE5A4C803AA20038111D` |
| `AxisBlock.java` | 582 | `6E9B75057943373F31BAAB674D7F7E9CCCBE1FD5EFDDE9D44C9261BB7BA93036` |
| `BlockWithBlockEntity.java` | 1839 | `9126813AF904415188C993830F592326FD78F1B9C9E6224373256F6AC23CE175` |
| `ButtonBlock.java` | 10296 | `E79F9805438FC92233E709FEFFA83091FEBDE8DE2A3DAAE8280183EF251E5681` |
| `DiodeBlock.java` | 8058 | `FDDFE9C212D67480AB1F8EC7C4AE236E4E9B310ED9F78C29CD78FFA01080DBB0` |
| `FallingBlock.java` | 2646 | `A3FCBC22CA73E0B31D2719F62094323C24AE7B56EBB3CE58B7A7909C1D5ABAA0` |
| `FlowerBlock.java` | 5453 | `4139A2FC5C274704D8CB861269BC2C62027FFB75F4E17B4AEA85CB77A754CCF4` |
| `HorizontalFacingBlock.java` | 606 | `99C33E79B000C5545640894D0A291FE62DD07ED7330A5B08CA23C1DB3D83DD83` |
| `LiquidBlock.java` | 11869 | `1537FD6B1BCA65E0F910F9A12BCD3BB27F5A86BE7F13A1CAA380C5E66544F1D5` |
| `PlantBlock.java` | 2451 | `A862AC13E8D25C8C3D4DC12AF716351A8C51CBF4AA10F0AE2705B6A036A198BD` |
| `RedSandstoneSlabBlock.java` | 5346 | `07D59A26FC337CFB2533C9CF4B8F9CBA0D3ADAAAC5CA96A50B6845F6C1845A64` |
| `SignBlock.java` | 2364 | `7CCC9F3DA44069426C3FC808E97631A56B8AD10045B89459219B2C2D062E81F6` |
| `StoneSlabBlock.java` | 5656 | `CE79650021C852BA1E70B3F9E5254CF00597A929154A7F10459DB2E37D49FD76` |
| `TransparentBlock.java` | 1338 | `10780460A36E5E73D61381CD91B05A988E30A67E71FC182875A52BDCF387D244` |
| `WoodenSlabBlock.java` | 3267 | `B4788B162E779DD285D1FA7C41781716FEC6B43989F9693DA2D4134064673CD3` |

`Standing` and `Wall` in the roster are nested classes in `BannerBlock.java`, which is already hashed; they do not require separate files.

## Explicit open gate

This review does not close the generic old opaque-ID to current-state mapping. The roster's `OPEN` current-state rows remain open. It makes no product, parity, reachability, or adapter-readiness claim.
