# Independent audit verdict: crop wall predicate projection

**Verdict: ACCEPT, bounded to the four named wall-neighbor predicate rows.**

- Author packet: `build/source-residual-20261116/1.13.2--1.14.4/wall_crop_predicate_projection/report.md`
- Author packet SHA-256: `bc83675cd30ef740f7b0c1529d554f9ee821aad9de727202bc58a34a45273d30`
- Endpoints: 1.13.2 Feather Gen2 build 2 (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`) and 1.14.4 Feather Gen2 build 2 (`717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`). Both ready markers and source manifests were checked; their source-manifest hashes match the report.

I recomputed all listed Java file hashes from canonical read-only source and matched each to both the packet and its exact endpoint source-manifest entry. The registrations bind all four keys to their reported classes and `.noCollision()` properties. `PlantBlock.getFaceShape` returns `UNDEFINED` unconditionally in 1.13.2; the 1.13 wall generic arm requires `SOLID`, while the middle-pole alternate cannot apply to these plant classes. The 1.14.4 wall placement passes `BlockState.m_87223014` to its helper; that state method delegates to `Block.isFaceSolid`, and the base collision provider returns the empty shape when `hasCollision` is false. The separate wall-tag and fence-gate alternatives do not apply to these keys, consistent with the cited accepted WALLS resource evidence.

The registered state domains are supported by exact source: AGE_3 is 0–3, AGE_7 is 0–7; beetroots and nether wart add AGE_3, wheat adds AGE_7, and carrots inherits WheatBlock's AGE_7 property. This yields 24 key/state combinations and 96 horizontal-direction predicate checks, all false at both endpoints. A source-only search of the pair's residual reports found no prior wall-predicate packet for these four exact keys; the separate `nether_wart_block` generic-block row is a different registry key.

**Historical crosswalk limitation:** this packet is bounded to rows in the corrected r2 unexamined-key TSV. The prior r1 TSV was overwritten, so the earlier crosswalk audit could not byte-diff all unchanged rows. This verdict confirms only these four selected rows and does not assert completeness of the broader crosswalk or inventory.

No movement-trajectory claim or pair-freeze claim is accepted.
