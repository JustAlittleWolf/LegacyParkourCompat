# Independent source audit: frosted ice wall neighbor

Verdict: **ACCEPT**.

Audited immutable report: `build/source-residual-20261116/1.13.2--1.14.4/wall_frosted_ice/report.md`, SHA-256 `4d7cb6bb2737bad9af18d3e84097101d788697f00742c408457bb1f92271d62f`.

The exact ready markers identify Minecraft 1.13.2 and 1.14.4 with `ornithe-feather` mappings. Their source-manifest files hash to the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

I independently checked the report's source-hash table against both current source bytes and the corresponding manifest rows. Every cited hash matches. The relevant hashes are:

| Release | Source | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/FrostedIceBlock.java` | `096940541255ab371d6f197fbf22f30b99da48ff1bde8be5c3be15ba00a299be` |
| 1.13.2 | `net/minecraft/block/IceBlock.java` | `8c0a9bed03f026be289f183114c6bc71b7e321d6ab4e915921cd2f51e3ce0cec` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/FrostedIceBlock.java` | `92c2cb87594561fcf9c089853440bc5dd2440f14de269d92d6ccdf96348c0432` |
| 1.14.4 | `net/minecraft/block/IceBlock.java` | `031de8fc5c554d672e8d46c9de5628651f7653893c5bd1127ca8e3125e303aed` |
| 1.14.4 | `net/minecraft/block/TransparentBlock.java` | `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

Claim checks:

- The shared key `frosted_ice` registers a `FrostedIceBlock` in old `Block.java` and target `Blocks.java`. Both block classes bind `AGE` to `Properties.AGE_3`, add it to the state definition, and initialize it to 0; both `Properties.java` files define `AGE_3` as `IntegerProperty.of("age", 0, 3)`. The old and target registration sources contain the cited sole `frosted_ice` registration in their respective registration units.
- In 1.13.2, `FrostedIceBlock -> IceBlock -> TransparentBlock -> Block` supplies no `getFaceShape` override, so inherited `Block.getFaceShape` returns `FaceShape.SOLID` for every direction. `WallBlock.shouldConnectTo` returns false for FrostedIceBlock because `isExceptionForConnection` explicitly includes `Blocks.FROSTED_ICE`; the pole/gate arm does not apply to `SOLID`. The exception therefore dominates the inherited face shape for all AGE states and all four horizontal faces.
- In 1.14.4, the wall's generic arm is exactly `!isExceptionForAttachment(block) && bl`, where `bl` is the directional boolean passed by placement/update callers. The target attachment list is leaves, barrier, carved/lit pumpkin, melon and pumpkin; FrostedIceBlock is absent. `BlockState.m_87223014` resolves to its cached per-direction face-solid value or `Block.isFaceSolid(this, world, pos, direction)`. That predicate rejects leaves and tests `isFaceFull(state.getCollisionShape(world, pos), face)`. Frosted ice has collision enabled by the default block properties, inherits `Block.getShape` returning a full cube through `IceBlock` and `TransparentBlock`, and has no collision-shape override. Thus north, east, south and west each test as full collision faces; the shape is independent of AGE. Target callers pass SOUTH, WEST, NORTH and EAST for the corresponding north, east, south and west neighbors in both placement and neighbor update.
- The report's old false / target true result for all four horizontal directional arm bits follows from those predicates and callers. No correction is required. This verdict accepts only the bounded frosted-ice residual; it does not establish pair freeze or runtime parity.
