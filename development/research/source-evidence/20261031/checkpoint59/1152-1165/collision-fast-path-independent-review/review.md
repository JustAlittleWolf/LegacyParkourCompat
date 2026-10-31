# Independent review: collision full-block fast path

## Verdict

**ACCEPT** for the narrow full-block fast-path movement delta, bound to residual report SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c` and the exact paired canonical sources listed below. The grid-rounding witness is valid and can change the result of a fixed LocalPlayer multi-axis move. The collector migration and default entity-candidate filtering described in the report do not establish separate LocalPlayer deltas under the stated vanilla/default-call conditions.

## Source identity

The residual's `source-hashes.tsv` was checked against the canonical read-only sources at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Every listed present file matched; the listed 1.15.2 `CollisionSpliterator.java` was absent as expected. Relevant paired SHA-256 values:

| Exact source | SHA-256 |
|---|---|
| 1.15.2 `mojmap/net/minecraft/world/level/CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` |
| 1.16.5 `mojmap/net/minecraft/world/level/CollisionGetter.java` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |
| 1.16.5 `mojmap/net/minecraft/world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` |
| 1.15.2 `mojmap/net/minecraft/world/phys/shapes/Shapes.java` | `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2` |
| 1.16.5 `mojmap/net/minecraft/world/phys/shapes/Shapes.java` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` |
| 1.15.2 `mojmap/net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.16.5 `mojmap/net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.15.2 `mojmap/net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16.5 `mojmap/net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.15.2 `mojmap/net/minecraft/world/phys/AABB.java` | `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18` |
| 1.16.5 `mojmap/net/minecraft/world/phys/AABB.java` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |

Context report identities checked:

- Collision-context audit SHA-256 `18d7062450876070ff88fa67464942d321140e9320a4c06e02e1706ede28a87f`.
- Closest-space closure SHA-256 `ee897e32ed5879be7fa66f33c7d2e12ce2a0275d5acb4624d3476359fd2b8c6a`.
- Ordinary world-border report SHA-256 `acf86ce7cf34e1fa44aff5a538ebdd2ab65496fd9e3ec5a14a3b37bd6513eba3`.

## Collector and fast-path comparison

1.15.2's `CollisionGetter.getBlockCollisions` creates `Shapes.create(aABB)` and admits a translated block shape only when `Shapes.joinIsNotEmpty(queryShape, movedShape, AND)` succeeds. 1.16.5's ordinary overload supplies an always-true block-state predicate; `CollisionSpliterator` retains the old join check for other shapes, but for the identity-canonical `voxelShape == Shapes.block()` it instead calls strict raw-AABB `box.intersects(blockBounds)`. This means the old and new paths are not generally equivalent when `Shapes.create` snaps coordinates to a grid.

`Shapes.create(AABB)` first runs `findBits` independently for all three endpoint pairs. When all succeed, it rounds the endpoints to the corresponding 1/2/4/8 subdivision; otherwise it uses exact AABB coordinates. Thus the ordinary geometry remains equivalent when the old query shape is exact or its rounded bounds preserve the same intersection result. It may differ when rounding crosses a full-block boundary. The distinction applies only to the new canonical-full-block fast path; noncanonical collision shapes still pass through the old join predicate.

The supplied witness satisfies the grid conditions. For `AABB[(0.375, 0.25, 0.25) .. (1+δ, 0.875, 0.875)]`, with `0 < δ < 1.25e-8`, x endpoints first align at 1/8 resolution; y and z endpoints also align at 1/8. At the upper bound, `8(1+δ) < 8+1e-7`, so `findBits` accepts that resolution, and rounding maps the x maximum to exactly `1`. The old query therefore only touches the unit block at `(1,0,0)` and the AND join is empty. The new raw strict intersection sees positive overlap `δ` and yields the block. A numeric check at `δ=1e-8` gives `8(1+δ)=8.00000008`, within the strict `1e-7` grid tolerance.

## Movement-route check

Both `LocalPlayer.move` overrides delegate to `Entity.move`. On its ordinary non-`noPhysics` route, `Entity.move` obtains the current box and invokes `collideBoundingBoxHeuristically`; when at least two requested components are nonzero, that helper calls `getBlockCollisions(entity, aABB.expandTowards(vec3))`. The query is exactly the swept AABB used in the report, with no extra inflation. The default collector overload is selected, so the predicate is true. Border/entity candidates can be absent for the witness setup (place the block away from the world border and use an otherwise empty collision neighborhood).

A swimming Player box has 0.6 width and height in both paired sources. Set its box to x `[0.375,0.975]`, y `[0.25,0.85]`, z `[0.25,0.85]`, and use fixed move `(0.025+δ, 0.025, 0.025)`. Its swept query has the witness bounds. The collector yields the cube only in 1.16.5. During ordered axis clipping, the box overlaps the cube in Y and Z, while positive X movement crosses its x=1 face. The yielded cube therefore clips requested x to `0.025`; with no other collision shapes, the old collector leaves x at `0.025+δ`. The `Entity.move` requested-versus-clipped component comparison sees that response difference. This establishes a source-level movement candidate under a fixed move/query; it does not estimate how often ordinary play reaches this exact coordinate/velocity setup.

## No-delta findings and limits

The residual's collector traversal comparison is sound for the ordinary LocalPlayer query: expanded integer bounds and cursor visitation order, type skips, large-shape and moving-piston gates, context creation, state/shape lookup, and positive-volume admission are retained. The default 1.16.5 predicate adds no candidate filtering. The report's LocalPlayer entity-candidate migration closure is supported by the paired `EntityGetter`, `Level`, `LevelChunk`, Boat, Shulker and Player sources pinned in its source ledger, and the independent collision-context/closest-space reports do not reopen those gates. The separately reported border and closest-space witnesses concern their bounded operations and do not remove this `Entity.move` block-shape witness.

The fast-path condition is narrow: canonical `Shapes.block()` identity, all three `findBits` tests accepted, and endpoint rounding that changes strict overlap with a candidate block. Exact tangencies remain false under both paths when rounding does not move an endpoint across the face. The report appropriately leaves provider frequency and arbitrary modded/non-LocalPlayer behavior outside this acceptance. This verdict accepts the source difference only; it does not claim runtime parity testing, a patch-introduction boundary within 1.15.2–1.16.5, or any implementation status.
