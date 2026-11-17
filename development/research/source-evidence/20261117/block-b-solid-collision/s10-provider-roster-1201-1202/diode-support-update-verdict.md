# Diode support-update review: 1.20.1 → 1.20.2

## Evidence identity

Exact published decompilations: Mojmap, version IDs 1.20.1 and 1.20.2. Both `mojmap.ready.json` markers report `status: ready`, the matching exact `versionMetadataId`, and mapping `mojmap`. They bind client artifacts by SHA-256: 1.20.1 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`; 1.20.2 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`. Source-manifest SHA-256 values are 1.20.1 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` and 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; each matches its ready marker. Provenance files identify exact `1.20.1`/`1.20.2`, namespace `mojmap`, the 1.20.1–1.20.6 batch, and Gradle 9.7.1 / Java 25.0.3 / TinyRemapper 0.14.1 / MappingIO 0.9.1 / Vineflower 1.12.0. Every relevant source file below was SHA-256 checked against its version's source manifest.

Compared source paths (line references are relative to the exact version root):

- `net/minecraft/world/level/block/DiodeBlock.java`: both releases, lines 23–39, 71–84; 1.20.2 additionally lines 42–44.
- `net/minecraft/world/level/block/RepeaterBlock.java`: both, lines 54–59.
- `net/minecraft/world/level/block/ComparatorBlock.java`: 1.20.1 has no `updateShape` override; 1.20.2 lines 44–46.
- `net/minecraft/world/level/block/Block.java`: both, lines 143–168, 231–233.
- `net/minecraft/world/level/block/SupportType.java`: both, lines 27–34.
- `net/minecraft/world/level/Level.java`: both, lines 203–250.
- `net/minecraft/server/level/ServerLevel.java`: both, 1.20.1 lines 979–997, 1487–1491; 1.20.2 lines 985–1003, 1493–1497.
- `net/minecraft/world/level/redstone/InstantNeighborUpdater.java`: both, lines 16–30.
- `net/minecraft/client/multiplayer/ClientPacketListener.java`: 1.20.1 lines 681–684, 771–774; 1.20.2 lines 639–642, 726–729.
- `net/minecraft/client/multiplayer/ClientLevel.java`: both, lines 120–133.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: both, lines 127–139, 290–292, 656–666, 807–812.

## Independent verdict

**Confirmed, narrowly scoped server update-path delta; ordinary support removal already drops the diode synchronously in 1.20.1. No direct same-state collision-shape delta is established.**

In 1.20.1, `DiodeBlock.canSurvive` calls `canSupportRigidBlock(level, pos.below())`. The helper reads the support's UP face using `SupportType.RIGID`. In 1.20.2 `canSurvive` factors that same check through `canSurviveOn(level, belowPos, belowState)`, whose predicate is still `state.isFaceSturdy(level, belowPos, UP, RIGID)`. The helper refactor preserves the exact support predicate and position.

The 1.20.2 `RepeaterBlock.updateShape` and `ComparatorBlock.updateShape` add a DOWN-neighbor guard: when the new supporting state is not rigid on its UP face, return AIR; repeater otherwise retains its prior lock-update/super path. In 1.20.1 neither class has that support-shape guard. Both inherit the base `BlockBehaviour.updateShape`, which returns the existing state.

For ordinary server-side support removal (`Level.destroyBlock` routes to `setBlock(..., 3, ...)`; ordinary `removeBlock` also uses flags 3), `Level.setBlock` first performs bit-1 `blockUpdated`, then shape updates. `ServerLevel.blockUpdated` immediately calls `updateNeighborsAt`; `InstantNeighborUpdater` dispatches the direct neighbor's `neighborChanged`; unchanged 1.20.1 `DiodeBlock.neighborChanged` checks `canSurvive`, drops resources, and removes the repeater/comparator synchronously. Thus the 1.20.2 shape guard does not create the ordinary support-removal outcome. The same remains true for a write with bit 1 set and shape propagation disabled: the old direct neighbor callback removes it.

The additional 1.20.2 route matters when a support-state write enables shape propagation but omits bit 1 (for example, `Level.setBlock` flags 2): `Level.setBlock` invokes `updateNeighbourShapes`, `neighborShapeChanged` runs `updateShape`, and the new DOWN-neighbor guard yields AIR; `Block.updateOrDestroy` then calls `destroyBlock` on the logical server. The old path has neither `blockUpdated`/`neighborChanged` nor a support-dependent `updateShape` removal. This is a real world-authority state-update difference for that flag combination, but this trace does not establish a vanilla player-reachable use that changes a diode's support to non-rigid with those exact flags. Flags with bit 16 suppress the shape propagation; flags with bit 1 still retain the older direct survival/removal path.

Client packet handling does not make this a same-packet local collision exposure. Both clientbound single-block and section-block updates call `setServerVerifiedBlockState(..., 19)`; that reaches `Level.setBlock` with bit 16 set, suppressing `updateNeighbourShapes`. Client prediction/ack reconciliation also uses flag 19. Further, `Block.updateOrDestroy` only calls `destroyBlock` for an AIR result when `!level.isClientSide()`. The server remains authoritative for the new removal route and sends the resulting state update.

The direct diode plate geometry is unchanged: both `DiodeBlock` versions define the same `SHAPE = Block.box(0, 0, 0, 16, 2, 16)` and return it from `getShape`. Neither overrides `getCollisionShape`; `BlockBehaviour.getCollisionShape` returns the state shape when the block has collision. Therefore a repeater/comparator state still present at the queried position has the same 2/16-height collision shape in both versions. `1.20.2` may remove that state in the narrowed server-only update route, but the compared direct collision predicate/shape for the same state does not change.

## Exact source SHA-256 gates

Full source-manifest SHA-256: 1.20.1 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.

| Source file | 1.20.1 SHA-256 | 1.20.2 SHA-256 |
|---|---|---|
| `DiodeBlock.java` | `573ea1b5095f072aff259f3d4e00f21a93f193500cdd15f67e295e26d5904d69` | `3467f696369b5454f7f58ac29a7f923bfa488d469b12207c7cba4118f704be25` |
| `RepeaterBlock.java` | `02ad6b8e76f2403dc12d147baf6272362f2286f6c314b2b919fd609dff3171ca` | `b9e1ed1523b661666874977113fcda9ff055e3624e3a0bad3b7e81e7728f309a` |
| `ComparatorBlock.java` | `b498e556be0872662c5c26f160fd5b37f91fdcaf5f578724a4bee61330bca9f9` | `635d5a3a718b79c566ebf3c20ed0bfc5ac56cc78f9d650417e7dd122ffe6893b` |
| `Level.java` | `c13ead6630a8f77529574afcd6009b0395698489ff8ad6fdf75b0e745c65f61c` | same |
| `ServerLevel.java` | `e1b59acb9d7d52259123dc8244480f8ce66e91e868d3510faefa95ced721a855` | `b78133112b2a5c4c2415a4085cabf56a63262c21efff6854581713c396bd1fbf` |
| `ClientLevel.java` | `8d7434e668bbb868f3d25d638abab4cd612ac89f1b54ae701fa81f212694fbc5` | `6204df69a17b375e46f4405fe67a106def336c819e589ec0794c04920a31c5d7` |
| `ClientPacketListener.java` | `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` | `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264` |
| `Block.java` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` | same |
| `SupportType.java` | `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6` | same |
| `BlockBehaviour.java` | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` |
| `InstantNeighborUpdater.java` | `6a60540ebbe26ac9ee17ac2ace673becc1a63c13d92e485e7ec7be4705cfac0a` | same |
