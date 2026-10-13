# WORLD-18: empty collision providers and outline separation

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-18-empty-collision-providers.md).
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-18-empty-collision-providers.md).

## Difference

- 1.8.9 source hashes: ButtonBlock ; AbstractPressurePlateBlock ; AbstractRailBlock ; FireBlock ; SugarCaneBlock ; TorchBlock ; TripwireHookBlock .
- 1.9.4 source hashes: ButtonBlock ; AbstractPressurePlateBlock ; AbstractRailBlock ; FireBlock ; SugarCaneBlock ; TorchBlock ; TripwireHookBlock .

## Movement meaning

- Reachability and boundary: the shared historical blocks are registered in both endpoints and queried through the normal world block-collision assembly in COLL-01.

Pressure plates include their paired subclass behavior only for this collision-provider disposition; entity activation, arrow/button interaction, rail support and fire/contact callbacks remain separate.
- Disposition: no provider-local player collision box is emitted in either endpoint for this bounded set.

For thirteen blocks, the source return representation changes from null to the empty-shape sentinel; AirBlock returns null on both sides.

No collision-list entry is appended in the paired generic dispatch.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-18-empty-collision-providers.md).
