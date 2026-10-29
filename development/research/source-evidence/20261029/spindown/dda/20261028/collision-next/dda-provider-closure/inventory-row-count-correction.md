# Provider inventory row-count clarification

This immutable clarification relates the prior inventory revision SHA-256 `5DC4D69ECC97BD3987E91195566EE1DF81CF94C545273901CDDA8C22E05D3CAD` to the current inventory revision SHA-256 `5B970E9685C73E044F51771CA5FE79F00E4D97984F44E17F82978CB6EF05914E`.

## Exact correction

The 1.8.9 source contains `BannerBlock.Wall.updateShape(WorldView,BlockPos)` at `net/minecraft/block/BannerBlock.java:159`. Its body calls `setShape` for the full default bounds and then for facing-specific bounds; this writes the shared `Block.min/max` fields. Its source-file SHA-256 is `6918850C398314D113F9E7D21E361D19C9AF1FDB74977465CA3223FC3F52DBC4`.

The prior inventory revision already contained the Banner writer row and its source hash. A row-count script failed to count it because the first cell used two separate inline-code spans with explanatory text between them. The row has been normalized to one inline-code span. The corrected table has 35 writer rows, including Banner; no writer was added or removed and no source or movement claim changed. The current inventory SHA above binds the normalized table bytes. The previous inventory digest is preserved here rather than replaced as the only record.

## Source check

Canonical read-only source: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather\net\minecraft\block\BannerBlock.java`. The nested class is explicitly `public static class Wall extends BannerBlock`; its world-aware `updateShape` reads `FACING`, calls `setShape`, and switches to facing-dependent extents. This is a ray-time mutable-bound writer when that nested provider reaches the inherited default `Block.rayTrace` path. The note does not expand the accepted movement-consumer verdict beyond Anvil/Chest.
