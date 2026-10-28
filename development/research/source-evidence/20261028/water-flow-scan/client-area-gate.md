# Water-flow loaded-area gate reconciliation

## Verdict

The current water-flow integration does not preserve the exact 1.8.9 client scan admission across loaded and unloaded horizontal regions. The old source check named isAreaLoaded(..., true), but for ClientWorld it is effectively only a fixed-height gate: ClientChunkCache.hasChunk always returns true, and allowEmpty=true makes World.isChunkLoadedAt accept every X/Z chunk coordinate. The old flow scan therefore runs in-height even when client chunks are absent, reading the client cache's empty chunk where needed.

The current native preflight is materially stricter and cannot be treated as equivalent. EntityFluidInteraction.update calls hasFluidAndLoaded on an X/Z range expanded by one block around the current fluid box. It rejects if any FULL chunk lookup is absent and also requires at least one scanned section to have fluid. The V1_16_2 product provider returns empty for profiles older than 1.16, leaving this 26.2 preflight in force for 1.8.9. If it rejects, the tracker does not scan and Entity.updateFluidInteraction does not call applyCurrentTo for water. WaterScan is still evaluated later at the isInFluid(WATER) expression, but the current integration applies its stored impulse only inside the EntityMixin applyCurrentTo redirect. Thus a WaterScan result can exist without the historical current being applied.

## Exact 1.8.9 behavior

The independently accepted source comparison records Ornithe Feather 1.8.9 source identity and scan details in .native-source/20261028/liquid-scan-upper-bound-closure/comparison-revised-2.md; its accepted audit is audit-revised-2.md. World.java hash 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee matches the ready source manifest.

World.applyLiquidDrag at World.java:1486-1494 computes floor(min) and floor(max+1.0) bounds, then calls isAreaLoaded(..., true). World.isAreaLoaded at :205-224 rejects only when maxY < 0 or minY >= 256, otherwise iterating X/Z chunk coordinates inclusively. World.isChunkLoadedAt at :226-227 delegates to chunkSource.hasChunk and, because allowEmpty is true, does not require a non-empty chunk.

ClientWorld uses ClientChunkCache. Its ClientChunkCache.java:31 hasChunk returns true unconditionally. Therefore the isAreaLoaded X/Z loop cannot reject a missing client chunk. ClientChunkCache.getChunk at :54-57 returns its empty chunk sentinel when no chunk is stored. The ordinary player caller, Entity.checkWaterCollisions at Entity.java:746-759, submits the transformed and contracted player box to applyLiquidDrag. Consequently, for an ordinary client player the old source scans the calculated query whenever its old Y gate passes; missing horizontal chunks read through the empty-chunk fallback.

Selected 1.8.9 source hashes, each verified against ornithe-feather.sources.sha256:
- World.java: 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee
- ClientChunkCache.java: 5f22bccf9270a107e8471bab996cf6f9dbb4c92ccd48c8ad0a42144dbf919920
- Entity.java: d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b

## Current product and exact 26.2 preflight

The integrated branch reviewed was fix/integrate-water-flow-scan-bound-20261028 at a44ccd82, based on main f462c4db. The current main later advanced to af3985c8, so the product integrator must merge that tip before handoff.

In 26.2, EntityFluidInteraction.update at EntityFluidInteraction.java:32-43 computes the current entity fluid box bounds and calls hasFluidAndLoaded with X/Z expanded by one block. The helper at :91-113 requests each chunk with getChunk(..., FULL, false), returns false on any null, and then requires at least one fluid-bearing section in the requested vertical range. When false, the fluid tracker is reset but does not collect flow; Entity.updateFluidInteraction later applies currents only through applyCurrentTo when its tracked water predicate is true.

The product's EntityFluidInteractionMixin intercepts the preflight at EntityFluidInteractionMixin.java:100-115. For selected profiles older than 1.16, the resolved V1_16_2 FluidScanLoadedChunks provider returns Optional.empty, so the original 26.2 helper result is used. WaterScan itself is invoked by EntityWaterMixin.java:39-55 when the outer method asks isInFluid(WATER). It computes a result from its own query at WaterScan.java:58-116, but does not apply impulse there. EntityMixin.java:520-536 applies that stored impulse only when the native update invokes applyCurrentTo. Thus the native preflight remains an admission dependency of actual flow application, even though it is not an admission dependency of computing the WaterScan result.

For absent client chunks, 26.2 ClientChunkCache.getChunk at ClientChunkCache.java:69-80 returns its empty chunk only when loadOrGenerate is true; the native preflight passes false and gets null for a missing/out-of-range chunk. Direct Level.getBlockState uses Level.getChunk with its loading overload and reaches the client's empty-chunk sentinel. These are not equivalent gates: 26.2 requires loaded FULL chunks in a wider range and fluid in a section, whereas the 1.8.9 client source check accepts all X/Z coordinates and lets empty chunks read as air.

Selected 26.2 source hashes, verified against unobfuscated.sources.sha256; ready/provenance/source-manifest identities are versionId 26.2, namespace unobfuscated, ready marker f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f, provenance a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1, and source-manifest a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894.
- EntityFluidInteraction.java: 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62
- Entity.java: 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5
- Level.java: 1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639
- LevelReader.java: 8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1
- ClientChunkCache.java: e36e3ea9cc4b8bf4e312a0b3b460f99bf2445906de964e7f3fbd53a9cef56f1d

## Integration condition

Do not use the current 26.2 hasFluidAndLoaded result as the 1.8.9 flow-scan gate. The source-backed old-client admission predicate is the legacy query's vertical check: computed maxY >= 0 and minY < 256; its horizontal client-chunk predicate is always true. For a missing chunk, the old and current client caches both provide an empty-chunk read path, but current flow application must not depend on the modern tracker invoking applyCurrentTo. Integration must either provide a separate old-flow application path for the already-computed historical WaterScan result or otherwise prove an equivalent tracker path while keeping current-only behavior scoped to the selected player/client operation. The current branch does not yet prove that condition.

The accepted witness assumes the scan region has water/current content and is inside the source's old vertical range. No build, test, runtime, or product edits were performed for this reconciliation.
