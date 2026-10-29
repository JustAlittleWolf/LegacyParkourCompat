# Independent review: light-source provider family

## Verdict

**ACCEPT**, bounded to the five listed legacy IDs, their explicitly cited state mappings, and local movement/ray shape provider claims. This does not close the camera adapter or establish DDA visitation, hit ordering, or whole-operation parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-light-source-family-89-91-123-124-169/FINDING.md`
- Finding SHA-256: `7B9410DB4D8D696E06803A48E0A9BC0AFAF6C6D20FB89B135BB9CAD653D4280C`
- Source ledger: `.native-source/20261028/collision-next/dda-light-source-family-89-91-123-124-169/source-hashes.json`
- Source ledger SHA-256: `3B609173CB62DA2489BB3508FCA5DE4D2925B82FF0300C506B2F07BDE8C3F77D`

All 14 selected Java file hashes matched both the live canonical source files and the release `.sources.sha256` entries (0 mismatches). Both release identity triples (ready marker, provenance, source manifest) also matched their canonical files and the report ledger.

## Source checks

- 1.8.9 `Block.java` registrations confirm ID89 glowstone, ID91 lit_pumpkin, ID123 redstone_lamp, ID124 lit_redstone_lamp, and ID169 sea_lantern. The two redstone lamp IDs are distinct `RedstoneLampBlock(false/true)` instances.
- The old `Block` constructor initializes all six bounds to 0..1; its default `getCollisionShape` and `rayTrace` use those bounds. The named Glowstone, Pumpkin, RedstoneLamp, and SeaLantern classes have no overrides of the cited collision/ray/update-shape providers. Pumpkin's `FACING` state is horizontal and its metadata decoder uses `Direction.byIdHorizontal`; valid 0..3 values map SOUTH/WEST/NORTH/EAST.
- 26.2 `BlockStateData` explicitly maps IDs89, 91, 123, 124, and 169 at their stated numeric rows. ID91's four listed rows preserve facing into `jack_o_lantern`; IDs123/124 map to `redstone_lamp` with `LIT=false/true`. The finding correctly limits ID91 to metadata 0..3: old decoding wraps malformed values modulo four, while unlisted current datafix rows default to the block's first registered state, so malformed-value equivalence is not established.
- `BlockItemIds` and `Blocks` bind the four current keys. `BlockBehaviour` defaults to `Shapes.block()` and `hasCollision=true`; the examined current registrations do not disable collision, and the selected Pumpkin/RedstoneLamp implementations do not override the shape providers. The full-cube current collision/outline conclusion is supported for the mapped states.

## Limits

The findings do not prove that every live chunk is datafixed through these rows, do not cover malformed pumpkin metadata, and do not establish DDA visitation, first-hit, or whole-operation parity. The submitted report keeps those limits explicit.
