# Moving-fence scan closure

## Independent source verdict

**ACCEPT, bounded:** the moving-fence player-collision witness is a real 1.12.2-to-1.13.2 collector difference. Exact stable cutover is **1.13.1**: 1.13 still has the old upper-Y-only pruning; 1.13.1 and 1.13.2 use two-axis-face pruning, and their `WorldView.java` bytes are identical. This is an operation-level verdict only; it does not close pair coverage or runtime parity.

In 1.12.2 `World.getCollisions` expands the swept player box by floor/ceil plus one cell. The X/Z boundary flags exclude only cells on both horizontal boundary axes; if a cell is on only one X/Z boundary, only the loop's maximum-Y face is pruned. Therefore a cell on minimum-X and minimum-Y with interior Z is visited. Its `MovingBlockEntity.addCollisions` obtains the moved state's collision boxes, translates them by piston progress, and appends them. `FenceBlock` supplies a 0-to-1.5-high center post and a connected east arm ending at X=1. A fence moved east by 0.5 has an arm spanning world X=1.125..1.5 and Y=0..1.5. A player swept box whose minimum X reaches 1.4, minimum Y is 1.1, and whose Z interval contains the cell interior overlaps that shape, so old collection can clip the player's horizontal movement against it.

1.13 `WorldView.getBlockCollisions` retains the same asymmetric predicate, though it now obtains the moving shape through `MovingBlockEntity.getCollisionShape`. At 1.13.1 and 1.13.2, `WorldView.getBlockCollisions` rejects a cell whenever any two of X/Y/Z are query-region faces. That rejects the witness cell before reading its state or shape. Fence geometry remains state-derived and 1.5 high in 1.13.2 (`FenceBlock` delegates to `PaneBlock`'s collision-shape table).

The 1.12.2 aliases are closed: `World.isChunkLoaded(BlockPos)` delegates with `allowEmpty=true`; `ClientWorld.isChunkLoadedAt` returns true for that flag. `World.getBlockState` then reaches `getChunk(BlockPos)` and the client chunk source; an absent client entry is represented by `EmptyChunk`, whose `getBlockState` returns air, so it cannot supply the moving-fence witness. The witness requires an actually available client chunk containing the moving piston block entity. 1.13 uses `WorldView.isChunkLoaded(pos)` as the `allowEmpty=true` alias; `ClientWorld` likewise returns true, and the empty-client-chunk state is collisionless. These aliases do not falsify the witness when its moving-piston cell and block entity are present.

26.2 does **not** retain the 1.13.1 omission for this witness. `Cursor3D` classifies a two-axis-face cell as `TYPE_EDGE` (2), and `BlockCollisions` admits that cell only when its state is `MOVING_PISTON`. Its collision provider obtains the `PistonMovingBlockEntity` shape, which applies the moved fence's vanilla collision shape and progress translation. The corresponding geometry remains eligible: current `FenceBlock` uses `CrossCollisionBlock` with collision height 24/16, and east connection arms are state-derived; the large-collision-shape check is true for this fence because its max Y exceeds 1. The specific 26.2 edge path uses the moving-piston state check, not the large-shape check.

The modern lookup is safely non-loading for a client collision scan: `BlockCollisions` calls `CollisionGetter.getChunkForCollisions`; `Level` requests `FULL` with `loadOrGenerate=false`; a null provider is skipped, and block state is read from the returned chunk. Therefore the 26.2 query cannot force-load the witness cell. Applicability requires that the relevant current client chunk is resident and contains the moving-piston state and its matching block entity. Cross-session chunk-residency/data equivalence is not established, so no witness is asserted when that condition is absent.

## Minimal independent contract

For fixed historical profile 1.12.2, reproduce the swept block-collision scan's asymmetric bounds pruning and the moved-state collision boxes for loaded client cells. For profile 1.13, keep the old pruning rule. Starting with 1.13.1, use two-axis-face pruning; 1.13.2 is byte-identical for the collector. This source difference does not by itself justify an adapter over arbitrary moving blocks: the checked witness is a moving fence carried by a piston moving-block entity. Preserve native fallback where loaded client block/entity data cannot establish that witness.

## Closed gates and remaining limits

- **Closed:** exact `isChunkLoaded` aliases and missing-client-chunk behavior for 1.12.2 and 1.13.
- **Closed:** 26.2 collision lookup is non-loading and null-safe on the scanned path.
- **Closed:** 26.2 cursor-edge admission and moving-piston collision-shape dispatch; the vanilla fence's 1.5-high shape remains applicable.
- **Open:** equality of historical and modern client chunk residency or received block/entity data across sessions.
- **Open:** no conclusion for arbitrary moving-block states, other collision collector entry points, unrelated trajectories, pair coverage, implementation, or runtime parity.

## Exact source identities

Canonical read-only root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\<mapping>\`. All ready markers report `status=ready`; listed Java file hashes were recomputed and match the corresponding canonical `*.sources.sha256` entries. Mapping provenance is embedded in each `*.provenance.json`.

| Release / mapping | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 / Ornithe Feather | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 / Ornithe Feather | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 / Ornithe Feather | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 / Ornithe Feather | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 26.2 / unobfuscated | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

Relevant Java-file hashes (each equals the manifest entry):

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| 1.12.2 | `net/minecraft/block/FenceBlock.java` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| 1.12.2 | `net/minecraft/client/world/ClientWorld.java` | `468def1eb1bd0791ce1d8b194ba22fe0fe49962bb3e696fb34b83bf4de7fb72c` |
| 1.13 | `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13.1 and 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13.2 | `net/minecraft/block/entity/MovingBlockEntity.java` | `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` |
| 1.13.2 | `net/minecraft/block/FenceBlock.java` | `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` |
| 1.13.2 | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 26.2 | `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| 26.2 | `net/minecraft/core/Cursor3D.java` | `9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f` |
| 26.2 | `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06` |
| 26.2 | `net/minecraft/world/level/block/piston/MovingPistonBlock.java` | `9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1` |
| 26.2 | `net/minecraft/world/level/block/FenceBlock.java` | `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93` |
| 26.2 | `net/minecraft/world/level/block/CrossCollisionBlock.java` | `dcd2dbbcd093a68dee4ee282e5f3101c519d633f3f173dd33383bd4b548c1ba5` |
| 26.2 | `net/minecraft/world/level/Level.java` | `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639` |

## Source ranges

- 1.12.2 `World.java:186-192,243-249,680-687,964-1005,1017-1019`; `MovingBlockEntity.java:318-345`; `FenceBlock.java:46-50,58-80`; `ClientWorld.java:104-107`; `EmptyChunk.java:39-42`; `Entity.java:567-568`.
- 1.13 `WorldView.java:113-151`; `ClientWorld.java:112-114`; `Entity.java:580-581`.
- 1.13.1/1.13.2 `WorldView.java:112-151`; 1.13.2 `MovingBlockEntity.java:324-351`; `FenceBlock.java:25-28`; `PaneBlock.java:82-84`; `Entity.java:580-598`.
- 26.2 `BlockCollisions.java:55-61,64-75,79-105`; `Cursor3D.java:4-7,55-70`; `MovingPistonBlock.java:106-114`; `PistonMovingBlockEntity.java:366-393`; `FenceBlock.java:37-42`; `CrossCollisionBlock.java:37-68,81-84`; `BlockBehaviour.java:577-579,930-945`; `Level.java:768-771`.

Additional lookup hashes: 1.12.2 `net/minecraft/world/chunk/EmptyChunk.java` `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0`; 26.2 `net/minecraft/client/multiplayer/ClientChunkCache.java` `e36e3ea9cc4b8bf4e312a0b3b460f99bf2445906de964e7f3fbd53a9cef56f1d`. Both match their canonical source manifests. The current cache path is `ClientChunkCache.java:69-77` (`null` when `loadOrGenerate=false`).
