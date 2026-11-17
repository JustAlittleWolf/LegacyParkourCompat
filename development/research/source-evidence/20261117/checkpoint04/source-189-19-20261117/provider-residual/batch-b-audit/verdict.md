# Independent source audit: provider residual batch B

Verdict: ACCEPT for the bounded provider rows listed below. This is not pair-wide acceptance; 1.8.9→1.9 remains partial pending the remaining provider/tick/dependency inventories and their full-pair audit.

## Bound inputs and identity

Audited `build/source-189-19-20261117/provider-residual/supplemental-coverage.md`, SHA-256 `C68DA62AC7964E7B6FBBFD78FCD87C028C3B1BCC3B636C1E0D6BCEB9E03D3ED3`. The 1.8.9 source-manifest SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004` and provenance SHA-256 `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185` match the supplied identities. The 1.9 mapping-local `.decompile-provenance.json` SHA-256 is `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A`. I independently checked all 40 selected class source files exist and match their exact old manifest/new provenance source rows. The comparison is limited to the rows and collision-provider behavior below; no wiki, product implementation/history, or backlog was read.

## Audited rows

- `BannerBlock`: old `getCollisionShape` returns `null`; new returns `EMPTY_BLOCK_SHAPE`. Standing/wall orientation shapes do not provide player collision.
- `DeadBushBlock`, `MushroomPlantBlock`, `NetherWartBlock`, `SaplingBlock`, `TallPlantBlock`, and `WheatBlock`: old `PlantBlock.getCollisionShape` returns `null`; new `PlantBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE`. New subclass AGE/static shapes are outline providers; they do not replace the inherited empty collision provider.
- `DragonEggBlock`, `EnderChestBlock`, and `EnchantingTableBlock`: old constructor bounds and new static `getShape` bounds match respectively: `(1/16,0,1/16)–(15/16,1,15/16)`, `(1/16,0,1/16)–(15/16,7/8,15/16)`, and full footprint to `3/4` height.
- `EndPortalFrameBlock`: both emit the base box `(0,0,0)–(1,13/16,1)` followed, when world `EYE` is true, by the same centered eye box `(5/16,13/16,5/16)–(11/16,1,11/16)`. Order and gate match.
- `CakeBlock`: old bite-derived collision minimum X `(1 + 2*bites)/16`, maximum X `15/16`, Z margins `1/16`, and height `1/2` match the new `SHAPES[state.get(BITES)]` table for bites 0–6.
- `DiodeBlock` and `DoublePlantBlock`: new shapes preserve their old full-footprint, 1/8-height diode and full-unit double-plant boxes.
- `SkullBlock`: facing-specific bounds match. Old `updateShape` reads `FACING` from the world state; new `getShape` reads it from the passed state, so equivalence is conditional on the state/world consistency of the collision-query caller, as the report already records.
- `CocoaBlock`: AGE/FACING-indexed bounds match the old formulas and new arrays. Old `updateShape` obtains the block state from the world; new `getShape` reads the supplied state. Equivalence is conditional on caller state/world consistency, which remains an explicit dependency rather than a claim that every arbitrary mismatched state is equivalent.
- `StemBlock`: old AGE formula `((age * 2 + 2) / 16.0F)` gives the same centered 1/4-square bounds and height as the new AGE-indexed shapes. Old lookup reads world state; new lookup reads the passed state, leaving the same caller-consistency dependency.
- `SugarCaneBlock` and `TripwireBlock`: both releases explicitly provide no player collision (`null` old, `EMPTY_BLOCK_SHAPE` new). Tripwire's 1.9 `ATTACHED`-dependent outline box does not participate in movement collision.
- `TorchBlock`: both releases explicitly provide no collision; its directional geometry is an outline shape.

These checks confirm the report's bounded no-player-collision-delta result for this subset. Static/provider formula equivalence is source evidence only; world-state consistency and downstream movement/query dependencies are not inferred beyond the report's stated bounds.

## Explicit exclusions and remaining state

I did not audit batch A's `SignBlock`, `WallSignBlock`, `TripwireHookBlock`, `VineBlock`, or `FlowerPotBlock`; I did not assess `RedstoneOreBlock` callback behavior. The already-frozen pane and piston-head-arm operations remain excluded. This verdict neither closes the recorded stairs multipart clipping dependency nor caller consistency for the state-driven getters, and it does not accept the pair's remaining providers, tick graph, or full coverage.
