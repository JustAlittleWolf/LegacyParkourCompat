# Independent review: mounted and facing provider family (1.8.9 → 26.2)

## Verdict

**ACCEPT**, bounded to source-confirmed registry identities, state projections, local collision providers, and ray/outline provider behavior for legacy IDs 68, 69, 144, 176, and 177. This does not accept DDA visitation, first-hit ordering, end-to-end ray equivalence, or live-world datafix applicability.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-mounted-facing-family/FINDING.md`
- Finding SHA-256: `FD336FADC32491D310F265F1926AAA12350AF73791DB467FD59DD5976BEDF8C2`
- Hash ledger: `.native-source/20261028/collision-next/dda-mounted-facing-family/source-hashes.json`
- Ledger SHA-256: `CCF53C0A39745B9FEBB165B07B8C55CC39746A633CE407338D16658927381CC2`

I independently rehashed all 27 listed Java source files. Every actual digest matches both the JSON `fileSha256` and `manifestSha256` fields and the corresponding canonical 1.8.9 or 26.2 `.sources.sha256` entry (27/27; no mismatches).

## Source checks

- 1.8.9 `Block.java` registrations confirm ID 68 `wall_sign`, 69 `lever`, 144 `skull`, 176 `standing_banner`, and 177 `wall_banner`. The listed metadata decoders and shape inputs match the report: wall-facing for sign/lever/skull/wall banner, standing rotation for banners, and skull `NODROP` separate from its facing shape.
- Old movement providers are correctly distinguished. Wall signs inherit `SignBlock.getCollisionShape()` returning null; levers and both banner variants return null. Skull is different: `SkullBlock.getCollisionShape()` calls `updateShape(world,pos)` before delegating to the shared-bound box getter, so its movement shape is refreshed from the queried facing. Old camera ray clipping calls virtual `updateShape` before clipping; standing banner uses constructor bounds, while wall banner updates by facing. These are local provider facts only.
- The 26.2 wall-sign rename `wall_sign → oak_wall_sign` is explicit in the v1802 `DataFixers` rename map, and the oak wall-sign registration sets `noCollision()`. Lever keeps the `lever` key; `BlockStateData` maps attachment/facing/powered fields, and the registration also sets `noCollision()`. The old null movement providers therefore match the current empty movement providers for these aliases.
- Skull conversion is explicitly block-entity dependent in `ChunkPalettedStorageFix`: `SkullType` selects the historical skull family and `Rot`/facing selects the standing/wall state. The listed six old skull families share the old geometric formula; current standing and wall skull classes select their shape from block state/type. Current skull registrations use `noOcclusion()`, not `noCollision()`; the default collision gate remains enabled and `BlockBehaviour.getCollisionShape()` delegates to the state's shape. Thus the report correctly keeps skull out of the empty-collision conclusion. Piglin skull geometry is current-only and appropriately excluded.
- Banner conversion uses `Base` with the old rotation/facing and the explicit inverted dye-color map. The datafix deliberately omits white from its generated map; `BlockStateData` explicitly covers the white standing/wall states. Banner patterns do not enter the shape providers. Current banner and wall-banner registrations set `noCollision()`, matching old null movement providers. The reported geometric dimensions do not assert hit/traversal parity.

## Conclusion and limits

The bounded aliases, state inputs, collision-provider distinctions, ray-bound update behavior, and hash bindings are supported by exact source. The report properly leaves DDA traversal, first-hit behavior, complete state conversion, current-only variants, and runtime reachability open. No product source, tests, builds, or runtime were inspected or used.
