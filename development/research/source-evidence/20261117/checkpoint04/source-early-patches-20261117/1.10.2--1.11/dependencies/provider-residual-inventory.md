# 1.10.2 → 1.11 residual collision-provider applicability

**Disposition: one remaining static provider difference, CocoaBlock, is reachable through the ordinary local-player collision query.** This report only closes provider/state caller applicability. It does not calculate a final clipped movement result, reopen accepted provider/MBE/piston/SnowLayer slices, or freeze the pair.

## Exact source identity

The pair roots are `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. This report reuses the already independently accepted `dependencies/provider-inventory.md` (SHA-256 `559cfc38964235b8c436cbda1e908637578580c0a766bb655e632db63eeffd96`) and its bounded audit (SHA-256 `6ee8be91180ba236a1bf885a64cb96bdcc6613b5690ff8d75b50f3927adb7a01`). Those bind the exact readiness/source-manifest identities and establish that all file hashes below match the corresponding source rows. No source was regenerated.

| File | 1.10.2 SHA-256 | 1.11 SHA-256 |
|---|---|---|
| `net/minecraft/block/CocoaBlock.java` | `fc7589009c1a84c6e69408fc2ff17eec46202acd03e986555e83812204dd2234` | `97b1ac16965a9a6af7e052de9108c17cccb9579ed08283e4c9813f288bd9cd78` |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `net/minecraft/entity/Entity.java` (ordinary entity dimensions) | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |

## CocoaBlock: changed collision output is reachable

`CocoaBlock.AGE` has values 0–2 and `FACING` remains horizontal. Its `getShape` selects the age/facing box. The superclass collision path is inherited in both releases: `Block.addCollisions` calls the state collision-shape provider, translates a nonempty box, and appends it when it intersects the query. `World.getCollisions(Entity, Box)` passes the queried entity through `BlockState.addCollisions`; the already accepted provider inventory binds this path to ordinary `Entity.move` collision scans. CocoaBlock has no entity filter on this route.

A concrete legal static setup is CocoaBlock at block position `(0,0,0)`, facing NORTH, supported by the ordinary jungle log at `(0,0,-1)`, age 2. `isSupported` reads the block at `pos.offset(FACING)`; placement accepts a horizontal side and starts age 0, and the unchanged tick/grow methods can advance it to age 2. The state domain is valid in both releases.

For that state, the local collision box is:

| Endpoint | Cocoa age-2 NORTH box (local block coordinates) |
|---|---|
| 1.10.2 | `(0.375, 0.3125, 0.0625) – (0.625, 0.75, 0.4375)` |
| 1.11 | `(0.25, 0.1875, 0.0625) – (0.75, 0.75, 0.5625)` |

The 1.11 provider extends the box toward the block center and lowers its minimum Y. An ordinary player uses a 0.6-wide, 1.8-high entity box in both endpoints. For example, a player centered at `(0.5, 0, 1.4)` moving north by `0.6` has an expanded northward collision query spanning X `[0.2,0.8]`, Y `[0,1.8]`, and Z `[0.5,1.7]`; the provider query intersects the 1.11 age-2 box but misses the 1.10.2 box (whose maximum Z is `0.4375`). This establishes changed collision-list membership for an ordinary local-player query. It does not establish the downstream axis-clipping/step-up result or a trajectory; those belong to the separate resolver closure.

## Reused and excluded provider slices

- The accepted provider inventory covers the unchanged common collision providers, registered classes and callback order; no further static provider delta is promoted here.
- MovingBlock/MovingBlockEntity payload and PistonHeadBlock `SHORT` production remain the separate already accepted conditional producer/provider scopes. This report does not re-evaluate their live payload, query geometry or response dependency.
- SnowLayer collision construction is unchanged. Its `isFullBlock` threshold change is handled by the accepted SnowLayer caller slice; no SnowLayer or other `isFullBlock` consumer is reopened here.
- Rails and FlowerBlock differences remain outline-only; Flower's inherited collision provider is explicitly empty. New-only Shulker providers and non-player/render/support/placement paths remain outside this old-profile local-player collision scope.

## Limits and next gate

This report makes CocoaBlock's changed collision provider concrete and locally queryable. It does not claim a resolved movement outcome, source-pair completeness, closed MovingBlock/Piston producer dependencies, full tick inventory, implementation eligibility, or runtime parity. The separate Entity.move resolver and the accepted conditional dynamic piston route remain independent pair gates.