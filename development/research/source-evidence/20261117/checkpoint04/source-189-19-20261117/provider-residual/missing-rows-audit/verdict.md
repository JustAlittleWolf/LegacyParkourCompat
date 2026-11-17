# Independent source audit: missing provider rows

**Verdict: ACCEPT for the bounded six-row provider report.** This accepts only the source claims and dispositions below; it does not freeze the 1.8.9→1.9 pair.

## Bound inputs and identity

- Report: `build/source-189-19-20261117/provider-residual/missing-rows.md`, SHA-256 `174CF90B0A97D7393A81482CF6FA57A2194023F213263F812D6A0D9804B97300`.
- Prior collision coverage compared for scope/reconciliation: `build/source-189-19-20261117/collision-effects/supplemental-coverage.md`, SHA-256 `d86237f219001e953e29b4d51b3d68b6756bfa3c2ebfc59f2040b2dbb511650b`; `build/source-189-19-20261117/provider-residual/supplemental-coverage.md`, SHA-256 `c68da62ac7964e7b6fbbfd78fcd87c028c3b1bcc3b636c1e0d6bceb9e03d3ed3`.
- 1.8.9 manifest: `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256`, SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local provenance: `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Recomputed class-file hashes for the six provider classes and shared `Block.java` matched the report and their exact manifest/provenance rows.

## Audited claims

- `AbstractPressurePlateBlock` and `ButtonBlock` contribute no player collision box in either release: the old provider returns `null`, while the new provider returns `EMPTY_BLOCK_SHAPE`, which the respective base dispatchers skip. Their changed outline-shape code does not enter the player collision provider path.
- `CauldronBlock` emits the same five boxes in the same order in both releases: base, west, north, east, south. The dimensions and `LEVEL` independence stated in the report match the source.
- `FenceGateBlock` uses the same `OPEN` and `FACING` collision decision and same axis-oriented 1.5-high, quarter-width bounds. The new `IN_WALL` branch affects outline shape only.
- `DoorBlock` has the same 3/16 panel for a valid complementary lower/upper pair. The report correctly leaves a conditional malformed-companion-state dependency: 1.8.9 combines metadata from both adjacent positions without requiring the other block to be this door, while 1.9 resolves complementary properties only when the other position contains this door. Ordinary reachability of such an invalid state is not established.
- `FenceBlock` has matching post/arm spatial union, but per-box decomposition and order differ. Exact sequential clipping remains open. Its neighbor connectivity also changes from `block.isCube()` to `blockState.isCube()`; the report correctly leaves cross-release neighbor-state equivalence unproven.

## Coverage reconciliation and limits

The earlier `collision-effects/supplemental-coverage.md` also mentions Door, Fence and FenceGate. This report's more specific rows refine the earlier broad equivalence statement: Door is only established for valid paired halves, and Fence remains partial for clipping order and the neighbor `isCube` gate. The earlier summary must not be used to close those dependencies. FenceGate's bounded equivalence is consistent with the earlier summary.

The six rows were absent from `provider-residual/supplemental-coverage.md`; the new report fills that residual inventory gap. It does not close all collision providers, stairs/fence clipping, Door/Fence reachability dependencies, other tick-state/provider gaps, or the pair-wide inventory. No pane, piston-head arm, or moving-piston response source was inspected for this audit.
