# Independent source audit: auto-jump world-border candidate

**Verdict: ACCEPT**

Comparison reviewed: `.native-source/20261028/autojump-border-verification/comparison.md`  
Expected SHA-256: `fa7e383945bac4b87a05549f5de86f9e4d06abd39b9e9f6c9f5d5411c0b3337c`  
Observed SHA-256: `FA7E383945BAC4B87A05549F5DE86F9E4D06ABD39B9E9F6C9F5D5411C0B3337C` (match)

## Audit result

I independently retraced the `LocalPlayer.move` → `updateAutoJump` → `getCollisions` → provider/iterator route and checked the report's claimed file hashes against the exact Mojmap source folders named in the report. All listed hashes match. The exact release folders 1.15.2, 1.16, 1.16.1–1.16.5, 1.17, 1.17.1, 1.18, 1.18.1 and 1.18.2 are covered by the reported hashes; byte-identical hash groups were confirmed at each named path. The representative `VoxelShape.java` hash values in the report also match, and I checked the `toAabbs` source hash in every named release: 1.15.2 has `5FACEEA0E4CE9AD0F2A1A2C72A89D9A768AB877C8728AE2968FE34AA1E03D3EA`; 1.16–1.16.5 `6747647A5B94E69747340D432932B77CE5AD701C8F06E141E15B3DD68F8C3BDE`; 1.17–1.17.1 `6B53EB54933AFACDA1934C9F1E3EC3584294AA3DEC09E5FBF4EEAA52B82D76C4`; and 1.18–1.18.2 `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417`.

For 1.15.2, `LocalPlayer.updateAutoJump` flattens the returned shapes with `toAabbs`, scans in iterator order and assigns a matching candidate's `maxY` to `u`. `CollisionGetter.getCollisions` concatenates block before entity collisions. The block-collision spliterator's first advance checks the border for a non-null entity before visiting blocks, using the source entity's bounding box deflated/inflated by `1.0E-7`; a border shape is yielded only when it overlaps the inflated box and not the deflated box. The same border-first ordering is explicit in 1.16–1.17.1 `CollisionSpliterator.tryAdvance`; its proximity test also uses the source entity's bounding box. The report correctly distinguishes that gate from the auto-jump swept query AABB.

For each of those releases, the exact `WorldBorder` source builds its moving/static collision shape from `Shapes.INFINITY` minus an interior box with Y bounds at negative and positive infinity. The exact `VoxelShape.toAabbs` implementations pass each box from `forAllBoxes` to an `AABB` constructor; the auto-jump candidate scan does not clip candidate Y bounds to its query AABB. The report's infinite-`maxY` explanation is therefore consistent with the inspected source.

For 1.18–1.18.2, `LocalPlayer.updateAutoJump` still flattens the candidates returned by `getCollisions`. `CollisionGetter.getCollisions` concatenates entity shapes before `getBlockCollisions`; `BlockCollisions.computeNext` yields block-state collision shapes and contains no border lookup. The border test in `CollisionGetter` is in `noCollision` via `borderCollision`, not in this iterator path. I additionally checked the inherited entity-collision provider, which the comparison did not hash: `ClientLevel` extends `Level`; `Level` implements `LevelAccessor`; `LevelAccessor` extends `CommonLevelAccessor`; its `getEntityCollisions` delegates to `EntityGetter.getEntityCollisions`, which creates shapes from nearby entity bounding boxes and does not add a world-border shape. This closes the only omitted provider dependency needed for the 1.18 exclusion claim.

Supplemental exact 1.18.x provider-path hashes (Mojmap):

| File | 1.18 / 1.18.1 | 1.18.2 |
|---|---|---|
| `net/minecraft/world/level/EntityGetter.java` | `F7FA5641D3287D5084F4450E1C09CD4D5816C5CFF5412C6F07B14779BB60215F` | same |
| `net/minecraft/world/level/CommonLevelAccessor.java` | `BAABCBA2AD6A0F45779A43B2C7F4AC3C624AC339F4A62C1DCD3B94A1686D99B0` | `865BE88F173AD497718A4C0D4ABD32CFF34D0AC56C6CD8F583F07C2F832D77B6` |
| `net/minecraft/world/level/LevelAccessor.java` | `AACAD21A911C88F5815260B26089CCB6E1D849542744FA9F7B2EAD983D11FCE3` | same |
| `net/minecraft/world/level/Level.java` | `50C31B707534BF9AE2F8ADA8DEF8BAC03C83E1217FF797633B85EA86DDBF8E85` | `397A2FD9ED6745BE2637EE9C8368A0425CD269D1B7A9A4B269FE2D1FBE786099` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `085D5348CDD80E512047EBA62000C7C18F6C74E853FE329060EB0D2F18CD36B2` | `E44CB1126B9B8093655892FB6CA17FA934CD79CC0453CEE5E443969D8792B82E` |

The candidate-order, entity/border proximity gates, and source-backed transition are stated accurately. The comparison labels the witness effect as a prediction and explicitly says no runtime witness was executed; I found no runtime result presented as fact. It does not infer behavior before 1.15.2, and it correctly notes that Mojmap identity is inferred from the source path because mapping artifact/build metadata was not separately inspected.

## Limitations

This audit covers only the stated query and exact releases. It does not establish behavior before 1.15.2, locate the first historical introduction, execute the predicted witness, or verify mapping build metadata. The entity-provider dependency above was independently inspected for the 1.18 exclusion and is recorded here because it was absent from the comparison's source-hash table.

I also checked the exact `Shapes.java` dependency for the unbounded-border-shape explanation. Every requested release has the file; hashes group as follows: 1.15.2 `81BF70D2C8A1D1336DF72BEEDB30B0C9504F14DECC9A06814A794AABF38203C2`; 1.16–1.16.5 `EC218DCA68CAD3525364967AADFBB8C0BEAD0CC44AA3EE2BEBA31E00E55AE2D4`; 1.17–1.17.1 `8249D3E406BA400BD81258AA19BE1FAD4F78B451B2ADD85EEA4F4B2209649A9E`; 1.18–1.18.2 `EC207E73B8E9DE4B1234132F81126973F0CB2BB37646B070D273259F2E91311A`. Its `INFINITY` shape is defined with all six infinite bounds, consistent with the border's unbounded-Y shell.
