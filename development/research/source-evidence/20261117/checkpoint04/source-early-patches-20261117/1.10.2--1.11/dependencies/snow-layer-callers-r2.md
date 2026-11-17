# 1.10.2 → 1.11 SnowLayer full-block caller gate: bounded correction

**Correction to:** `dependencies/snow-layer-callers.md`, SHA-256 `7226b06fb29902ca0296c183f10834fb754fbd8af087ff45d9e4551dcd89807b`.

**Audit identity:** `dependencies/snow-layer-callers-audit/verdict.md`, SHA-256 `90fe324dd3d11b00e0bfb36ab3c0abadbe1af743b4fb1d8f619cd8b1a6c07ee8` (`REQUEST CHANGES`).

## Bounded change

Add the omitted caller family to the original report's all-source `isFullBlock()` inventory: `net/minecraft/item/BedItem.java`, `useOnBlock`, line 48, in both `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. The condition checks `isFullBlock()` on the supporting states below both bed positions before placing the bed. Classify this as player-triggered bed placement eligibility, which is outside the assigned player-movement scope.

This correction is limited to the caller inventory. It does not change the accepted bounded conclusions: `SnowLayerBlock.isFullBlock` changes from 7 to 8 layers without an eligible local-client player-movement caller; the collision-height path remains player-reachable and unchanged between the endpoints; and the snow-on-snow layer threshold change remains placement-only. It does not expand pair coverage or alter the original report's other claims.
