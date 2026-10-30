# Independent publication review: Hopper band correction

## Bounded verdict

**ACCEPT.** The documentation-only change at `d5f4a6567890edfb4a8f13b2e7eac2908118b4b9` accurately corrects the 1.12.2-to-1.13 Hopper geometry description. The permanent finding states the added full-footprint solid band at Y=10/16..11/16, correctly says the center funnel from Y=4/16..10/16 remains solid, and describes lower-volume changes without claiming internal clipping-plane parity. The provider JavaDoc states the same correction and retains its limitation to occupied-volume preservation. I found no inaccurate geometry statement introduced by this correction.

This acceptance covers the specified source-backed publication correction and the bounded target-closure retrace below. It does not claim movement-tick or runtime parity, nor geometry equivalence of internal clipping decomposition.

## Source retrace

- Exact 1.12.2 Ornithe Feather `HopperBlock.java` defines the common bottom box from Y=0 to 0.625 and four 0.125-wide, full-height side strips. `addCollisions` appends those five boxes independently in the stated order (lines 38–42, 56–62). The change's phrasing that the horizontal facing outlet is already inside the old bottom slab is geometrically correct: the new side outlet is 4/16 deep and spans Y=4/16..8/16, while the old bottom slab covers the full X/Z footprint through 10/16.
- Exact 1.13 `HopperBlock.java` defines a full-footprint top box Y=10..16 and an 8/16-square center funnel Y=4..10. It subtracts `Hopper.INSIDE`, which spans X/Z=2..14 and Y=11..16, with `ONLY_FIRST`; therefore the full footprint remains occupied throughout Y=10..11, including the named band, while the central funnel remains occupied throughout Y=4..10. Facing outlets are then unioned per valid facing. The 1.13.1 and 1.13.2 `HopperBlock.java` and `Hopper.java` files have identical SHA-256 values to the 1.13 files as recorded in the target closure.
- The corrected finding's `Movement meaning` accurately says the 10/16..11/16 band is additional above the old slab and that lower volume changes by facing. “Adds central collision volume” is true but less spatially complete than the earlier “full-footprint solid band”; the same sentence explicitly names the full-footprint band, so it is not misleading in context.
- The provider JavaDoc's statement that the old union preserves old occupied volume describes its returned five-box union. Its explicit caveat says this does not preserve internal clipping planes from the original separate boxes, so it does not imply exact clipping-operation parity.

## Target closure check

I retraced the source-only 1.13-family-to-26.2 Hopper claim in `anvil-hopper-target-closure/HOPPER.md` against the canonical source tree `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`:

- The stated source manifests, release client identities, and 1.13-family `HopperBlock.java`/`Hopper.java` hashes match the bound identities in the closure and its independent audit. I recomputed the Hopper source hashes for 1.13, 1.13.1, and 1.13.2; all three versions match.
- Both 1.13 `Properties.HOPPER_FACING` and 26.2 `BlockStateProperties.FACING_HOPPER` accept DOWN and all four horizontal facings while excluding UP. Both Hopper blocks default to DOWN and ENABLED=true; 1.13 collision shape selection switches on FACING only. In 26.2 `makeShapes` likewise reads FACING only and normalizes ignored ENABLED for its cached shape map.
- The 26.2 base uses `Block.column(16,10,16)` and `Block.column(8,4,10)`, subtracting `Block.column(12,11,16)`, matching the 1.13 outline, funnel, and inside extents. Its NORTH spout is `Block.boxZ(4,4,8,0,8)` rotated around `(8,6,8)/16` and clipped to the unit block before union. The inward half lies within the common center funnel; the remaining outward half equals the 1.13 four-unit outlet. Rotation gives the same occupancy for NORTH/EAST/SOUTH/WEST and, for DOWN, leaves the same Y=0..4 centered 4/16-square outlet after the overlapping Y=4..6 section is contained in the funnel.
- Both releases have collision enabled for Hopper. The 26.2 default collision provider uses `state.getShape` and Hopper's override does not depend on `CollisionContext`; the separate interaction-shape provider does not enter the player collision query.

Thus the target closure's **collision-provider occupied-volume equivalence** is supported for the stated five facings and both ENABLED values. This conclusion concerns the union of occupied collision volume only; it does not establish matching internal clipping planes, collision traversal, or player movement outcomes.

## Scope and files

Reviewed exact diff `d5f4a6567890edfb4a8f13b2e7eac2908118b4b9`, canonical finding `development/research/findings/1.12.2--1.13.2/hopper-collision-funnel-and-facing.md`, provider `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/HopperCollisionShape.java`, closure inputs `ANVIL.md` and `HOPPER.md`, and independent audit `anvil-hopper-target-audit/review.md`. No implementation or research backlog changes were made.
