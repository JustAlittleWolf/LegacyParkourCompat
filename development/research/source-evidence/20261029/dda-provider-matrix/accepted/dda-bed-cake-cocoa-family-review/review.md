# Independent review: bed, cake, cocoa, and double-plant provider closure

## Verdict

**ACCEPT**, bounded to the source claims for legacy IDs 26, 92, 127, and 175; their named 26.2 mappings and individual movement/ray providers. This does not accept DDA visitation, first-hit, paired-cell reconstruction from one cell, or player-trajectory parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-bed-cake-cocoa-family/FINDING.md`
- Finding SHA-256: `7E94156E24A2B90122E5312AD9C996D826DC6D2F8D94A2342F8690A5C46F9548`
- Source ledger: `.native-source/20261028/collision-next/dda-bed-cake-cocoa-family/source-hashes.json`
- Source-ledger SHA-256: `DCCBE9F4A41F5B9E45604546A0A4B198637AA928DB18779A7C5A4333E30FA311`

Exact source identities match the canonical trees: 1.8.9 Feather Gen2 build.2 merged-v2 ready/provenance/source-manifest SHA-256 values are `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95`, `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185`, and `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`. 26.2 unobfuscated ready/provenance/source-manifest SHA-256 values are `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`, `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`, and `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`.

All 17 ledger entries were recomputed against the canonical files and matched both the ledger digest and the corresponding `.sources.sha256` entry (17/17, zero mismatches). The ready-marker, provenance and source-manifest digests above also match their canonical files.

## Source checks

- **ID and state identity:** 1.8.9 `Block.java` registrations bind 26/92/127/175 to bed/cake/cocoa/double_plant. The cited old decoders support the bed facing/part/occupied encoding, cake bites 0–6, cocoa’s 12 valid age/facing combinations (age 3 is outside its declared property range), and double-plant lower-variant/upper-half encoding. `BedBlock.resolveVirtualProperties` reads the adjacent head’s occupied property for a foot state; `DoublePlantBlock.resolveVirtualProperties` reads the lower cell’s variant for an upper state. Both dependency descriptions match source, and the finding correctly limits their use.
- **Legacy movement and ray providers:** bed sets and refreshes a constant full-footprint box through `setDefaultShape`; its inherited movement getter reads mutable bounds but the bed writer is state invariant. Cake ray/updateShape uses the world bite state, while its movement getter derives the box directly from the passed state, matching the reported bite formula. Cocoa’s movement getter calls `updateShape` before delegating to the mutable-bound getter; that writer derives its box from the queried state’s facing and age, so the report does not infer stale ray-cache dependence. DoublePlant’s bound writer sets a unit cube and its `PlantBlock` movement getter returns null; the report correctly says the paired variant read does not affect that writer/provider.
- **26.2 mapping and providers:** `BlockItemIds`, `Blocks`, and `BlockStateData` support the stated mappings. Bed old states map to `red_bed` states; the current bed uses the part/facing shape, and `OCCUPIED` is not in its geometry. Cake’s `Block.box(1+2*b,0,1,15,8,15)` matches the legacy state-derived collision bounds for bites 0–6. Cocoa’s `Block.column(4+2*age,7-2*age,12)` followed by the cited offset and horizontal rotation expands to the same bounds as the old four-facing formula for valid ages/facings. The six DoublePlant lower mappings and upper mappings to `peony` are present in the referenced `BlockStateData` rows; the finding does not claim an upper cell alone reconstructs its old lower-cell variant. The cited current DoublePlant registrations use `noCollision`; its inherited default outline is `Shapes.block()`.

## Review disposition

No material source or digest errors found. Accept only the named aliases, state projections, and individual providers/formulas. The explicit limits on state-pair reconstruction and DDA traversal/hit order are necessary and retained.
