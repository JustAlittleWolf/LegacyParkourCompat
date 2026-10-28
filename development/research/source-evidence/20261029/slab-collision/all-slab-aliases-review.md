# Independent review: 1.13 slab state aliases through 26.2

Verdict: **ACCEPT**

## Evidence identity and integrity

The canonical read-only sources are `1.13/ornithe-feather` and `26.2/unobfuscated`. Their ready markers identify `versionId=1.13`, `mapping=ornithe-feather`, and `versionId=26.2`, `mapping=unobfuscated`, respectively. The marker source-manifest digests match the closure. The exact 1.13 provenance identifies Feather Gen2 `1.13+build.2` artifacts; the 26.2 provenance identifies the unobfuscated namespace. All 18 entries in the lane's `source-hashes.sha256` match the canonical marker, provenance, source-manifest, and Java files.

## Claim review

- The 1.13 registry has exactly the 19 slab keys listed in the closure (`Block.java:1411-1413,1568-1583`; `Blocks.java:143-161,752-770`). The same 19 registry keys have distinct corresponding registrations in 26.2 `Blocks.java` (`3028-3045`, `3201-3248`, `3296-3381`) and distinct `BlockItemIds` string keys (`264-280`). Each maps to itself by registry key; `petrified_oak_slab` remains separate from `oak_slab`. Later-only keys, including smooth/cut slabs, later wood slabs, and later stone-family slabs, are outside the 1.13 roster.
- Both `SlabBlock` classes define slab type and waterlogged state. Their property names serialize as `type` and `waterlogged`; each enum has exactly `top`, `bottom`, and `double`, while waterlogged is Boolean. Both constructors default to `type=bottom, waterlogged=false`. Therefore each key has the same six permitted property combinations; this is a state-schema finding only.
- The bottom and top bounds are exactly `[0,0,0]–[1,0.5,1]` and `[0,0.5,0]–[1,1,1]`; double uses the full unit cube. In 1.13, `Block.block` divides each coordinate by 16 (`Block.java:152-153`). In 26.2, `Block.column(16, ...)` centers the 16-wide X/Z dimensions and delegates to `Block.box`, which divides by 16 (`Block.java:160-162,177-184`). Both slab shape methods select by slab type alone and ignore world/position/collision context.
- Both base collision providers return the state's shape when `hasCollision` is true and an empty shape otherwise (`1.13 Block.java:409-410`; `26.2 BlockBehaviour.java:327-328`). The property builders default `hasCollision` to true (`1.13 Block.java:1910`; `26.2 BlockBehaviour.java:985`), and none of the 19 registrations sets `noCollision`. Waterlogged does not affect the returned collision shape.

## Scope boundary

The evidence establishes collision geometry for the listed vanilla slab keys, including the default collision-provider gate. It does not establish parity for occlusion/light or face shapes, pathfinding, placement, collision response, fluids, drops, materials, tags, or other block behavior. Keeping block states vanilla and excluding later-only slab keys is consistent with the stated collision-only scope.

