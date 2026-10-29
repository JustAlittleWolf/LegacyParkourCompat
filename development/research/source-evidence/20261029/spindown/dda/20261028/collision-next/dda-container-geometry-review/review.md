# Independent review: cauldron and hopper provider closure

**Verdict: ACCEPT**, bounded to old IDs 118 (cauldron) and 154 (hopper), their recorded state/alias mappings, and the individual old/current movement and ray/outline providers. This review does not establish DDA visitation, first-hit order, end-to-end ray parity, or player trajectory effects.

## Reviewed inputs

- Finding: `../dda-container-geometry-family-118-154/FINDING.md`
- Finding SHA-256: `E02A36748D4E112FF0500DF4D1406C56A69E103A6BAFACCF65568C7BE8B1B25C`
- Source ledger: `../dda-container-geometry-family-118-154/source-hashes.json`
- Source ledger SHA-256: `67E8EDF79F5A32931B02A7220034E2A7CFA4F6D590C34631E991D5148F166A21`

I independently recomputed all 16 source file SHA-256 values and compared each with both the finding ledger and the exact canonical `.sources.sha256` row. All 16 matched, with zero mismatches. The 1.8.9 and 26.2 ready-marker and source-manifest digests also matched the recorded canonical identities.

## Source checks

### Legacy registrations and providers

The 1.8.9 `Block.java` registrations confirm ID 118 is CauldronBlock and ID 154 is HopperBlock. Cauldron's LEVEL property and metadata methods encode 0–3 directly. Its `addCollisions` sets and appends the 5/16 floor followed by four 2/16 walls, then restores the shared bounds to a full block. The base collision append path reads those current shared bounds. Cauldron's `updateShape` is inherited from the empty base implementation, and the base constructor initializes bounds to a unit cube, supporting the distinction between multi-box movement collision and full-cube inherited ray bounds.

Hopper's default state is FACING=DOWN and ENABLED=true; FACING excludes UP. Metadata uses `Direction.byId(metadata & 7)` and the enabled bit, and the reviewed state rows preserve down/north/south/west/east with enabled/disabled states. The `addCollisions` implementation appends a 10/16-high base and four 2/16 walls, then restores full-cube bounds. Its `updateShape` explicitly writes full-cube bounds for ray clipping. Neither these provider methods nor the cauldron provider reads a block entity or neighbor state. The hopper's block entity creation is separate from those methods.

### Current aliases and providers

The 26.2 `BlockItemIds` entries identify `cauldron` and `hopper`; the BlockStateData rows support the listed hopper facing/enabled states and cauldron levels. `CauldronRenameFix` is registered at schema 2679 and maps level zero to propertyless `cauldron`, while nonzero cauldron levels become `water_cauldron` retaining their level.

`Blocks` registers ordinary, water, lava, and powder-snow cauldrons from the cited classes. All inherit `AbstractCauldronBlock.getShape`, which returns the same static hollow shell; `LayeredCauldronBlock`'s level-dependent filled volume is an entity-inside shape and does not replace that block shape. Collision remains enabled by the default `hasCollision=true` property; `.noOcclusion()` does not disable collision. `ClipContext.Block.OUTLINE` selects `BlockBehaviour.BlockStateBase::getShape`, so current cauldron outline clipping uses that hollow state shape, unlike the old full-cube ray bounds.

Current HopperBlock's state shape combines a spoutless shell with a facing-directed spout clipped to the block. Its default collision shape delegates to the state shape, and OUTLINE uses the same shape getter; the interaction shape is separate. The inspected geometry method depends on state FACING, not block entity or neighbor data. The finding's contrast between current state-based shape providers and old box append/reset providers is accurate.

## Conclusion

The report's bounded claims are source-supported, and its explicit limits are appropriate. No product implementation or runtime behavior was inspected.
