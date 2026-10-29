# Independent review: farmland and soul-sand source closure

## Verdict

**ACCEPT** for the bounded source claims about legacy IDs 60 (farmland) and 88 (soul sand): their registry/state aliases and their individual movement-collision and ray/outline providers. This does not establish DDA traversal, first-hit order, player trajectory, or overall response parity.

## Bound inputs

- Finding: `.native-source/20261028/collision-next/dda-low-ground-family-60-88/FINDING.md`
- Finding SHA-256: C96DEE5EF20F4261F34910BD23F2F7BB039479D4EAFC1CDF559B875D35F9A290
- Source ledger: `.native-source/20261028/collision-next/dda-low-ground-family-60-88/source-hashes.json`
- Source ledger SHA-256: F1F79EEF4227FC0102212C094703F8765C59458B4C2167F5FE7495F1E2817865
- Canonical exports: 1.8.9 `ornithe-feather`; 26.2 `unobfuscated`.

I independently rehashed all **11** selected Java source files against the live canonical files and the corresponding `.sources.sha256` rows; there were **0 mismatches**. The 1.8.9 and 26.2 ready markers, provenance records, and source-manifest hashes also match the identities in the input ledger.

## Verified claims

- The 1.8.9 `Block.java` registrations at lines 957–958 and 1024 identify ID 60 as `farmland` and ID 88 as `soul_sand`. Farmland metadata decodes moisture with `metadata & 7` (`FarmlandBlock.java:123–124`); the current `BlockStateData.java:3385–3392` rows map moisture 0–7 to `minecraft:farmland` with the same values. Soul sand uses the inherited default state/metadata 0, and the current datafix rows bind the old state identity to `minecraft:soul_sand` (`BlockStateData.java:3885`; `EntityBlockStateFix.java:110`).
- Legacy farmland's constructor writes mutable ray bounds to height 15/16 (`FarmlandBlock.java:20–25`), while its `getCollisionShape` returns a translated unit cube (`:29–32`). The old default ray provider invokes the no-op default `updateShape` before clipping those bounds (`Block.java:456–460,596–598`).
- Current farmland's `getShape` returns a static 15/16 column (`FarmlandBlock.java:33,83–85`). Its registration does not disable collision; default `BlockBehaviour.Properties.hasCollision` is true and base `getCollisionShape` delegates to the state shape (`BlockBehaviour.java:323–328,985`). This supports the report's conclusion that current movement collision is 1/16 shorter than old farmland movement collision, without conflating that with old ray bounds.
- Legacy soul sand's inherited constructor bounds and default update path leave the ray box full cube (`Block.java:198–206,456–460,596–598`), while its explicit movement provider returns height 14/16 (`SoulSandBlock.java:19–22`). Current soul-sand collision is likewise 14/16 (`SoulSandBlock.java:27–29`); its inherited outline and explicit visual shape are full cube (`BlockBehaviour.java:323–324`; `SoulSandBlock.java:37–39`).
- The report keeps farmland fall conversion and soul-sand horizontal movement effects separate from the geometry comparison and makes no parity claim for those effects.

## Limitations

This review accepts only the bounded identities, state decoding, and local provider formulas above. It does not establish which provider a runtime adapter selects, complete visitor state reconstruction, cache lifecycle, DDA visitation, first-hit behavior, or movement-effect parity.
