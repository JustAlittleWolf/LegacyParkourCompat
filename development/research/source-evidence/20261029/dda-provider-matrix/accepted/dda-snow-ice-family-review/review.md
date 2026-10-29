# Independent review: snow layer, ice, and snow block

## Verdict

**ACCEPT**, bounded to the three legacy IDs' identity/state mappings and individual collision/ray providers. This review does not establish DDA visitation, first-hit ordering, or end-to-end ray-trace parity.

## Reviewed revisions and source identity

- Finding: `../dda-snow-ice-family-78-79-80/FINDING.md`
- Finding SHA-256: `7E9AEFE4A4FCE2312A35C938228BF218259B90CA2CC3A57C354DE70B4D467E5F`
- Source manifest: `../dda-snow-ice-family-78-79-80/source-hashes.json`
- Source manifest SHA-256: `017DC539245B423ED5CB4A783CD625BA531A66021E6E0DF03F9E40E25BE02E17`
- Canonical source manifest SHA-256 values: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Ready marker SHA-256 values: 1.8.9 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; 26.2 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.

I independently rehashed all 13 selected Java files from the canonical trees and compared them with both the submitted manifest and the canonical `.sources.sha256` entries: **0 mismatches**. The canonical source-manifest and ready-marker hashes also match the report.

## Claim checks

- **Legacy identity and state:** `1.8.9 Block.java:1009–1011` registers ID 78 as `snow_layer` with legacy key `snow`, ID 79 as `ice`, and ID 80 as `snow` (the snow block). `SnowLayerBlock.getStateFromMetadata` maps the low three bits to `layers = 1..8`; `getMetadataFromState` returns `layers - 1` (`SnowLayerBlock.java:134–145`). These claims match source.
- **Current mappings:** `26.2 BlockStateData.java:3812–3819` maps old `minecraft:snow` layers 1–8 to `minecraft:snow_layer` with the same layer values; line 3820 maps ice to ice; line 3824 maps old `snow_block` to current `snow`. The report correctly distinguishes the colliding old key spelling `snow` by registry identity/state.
- **Old snow-layer movement vs ray bounds:** `SnowLayerBlock.getCollisionShape` computes the box top as `(layers - 1) * 0.125` from the queried state (`SnowLayerBlock.java:40–45`). Its ray-time `updateShape(WorldView,pos)` reads the state and sets mutable top to `layers / 8.0` (`SnowLayerBlock.java:64–71`), called by default `Block.rayTrace` before clipping (`Block.java:456–461`). The two providers differ by one eighth exactly as stated.
- **No stale movement maxY dependency:** old `getCollisionShape` computes its Y maximum directly from the requested state and does not read/write `maxY`; the ray writer sets X/Z to full width and `minY` to zero, while leaving the shape's maxY irrelevant to this collision result. `Block.addCollisions` consumes the getter result (`Block.java:339–350`), and `World`'s collision scan dispatches that path. The report's narrow no-stale-maxY conclusion is supported; it does not claim the shared bounds have no other consumers.
- **Old ice and snow block:** `IceBlock` only supplies rendering/material/gameplay behavior and inherits from `TransparentBlock`; `SnowBlock` likewise has no collision/updateShape override. Their base ray update is the no-op `Block.updateShape`, and default collision bounds are full cube. The report's local provider conclusion is supported.
- **Current snow-layer shapes:** `SnowLayerBlock` selects `SHAPES[layers]` for outline and `SHAPES[layers - 1]` for collision. `Block.boxes(8, height -> Block.column(16.0, 0.0, height * 2))` creates full-width shapes of height `2*height/16`; these are respectively `layers/8` and `(layers-1)/8`, matching the two old formulas for layers 1–8.
- **Current ice/snow collision:** `Blocks.ICE` uses `IceBlock` with `noOcclusion()` but no `noCollision()`; `SNOW_BLOCK` uses default properties. `BlockBehaviour` defaults to a full-block shape and returns it for collision when `hasCollision` is true; that property defaults true and only `noCollision()` clears it. The `IceBlock`/`HalfTransparentBlock` classes do not override collision or outline shape. The report is correct that both current blocks keep full-cube collision.

## Conclusion

The finding and manifests support the stated bounded identity, layer mapping, and individual provider facts. Its exclusions of DDA order, first-hit parity, full old-state reconstruction, and overall ray-trace equivalence are appropriate.
