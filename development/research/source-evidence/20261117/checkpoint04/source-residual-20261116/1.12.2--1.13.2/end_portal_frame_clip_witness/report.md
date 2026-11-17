# Static clipping witness: End Portal Frame eye expansion

## Scope and input identity

This closes only the explicit geometric overlap/clipping gate for the accepted `end_portal_frame_shape_provider` finding. It is a static source/math witness, not a trajectory claim, pair freeze, implementation, or runtime result.

The read-only exact-source manifests match the accepted identities: 1.12.2 Ornithe Feather `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 Ornithe Feather `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Locally recomputed manifest SHA-256 values match. The EndPortalFrameBlock source-file hashes also match the accepted report: 1.12.2 `418a00540523c106571df82c95716e305d4fbb55ad5867148028a7611a9cca11`; 1.13.2 `c196aeba3dd6c9683659ab08b71a759b448709e8cc45e68aca448a7e6e470523`.

## Coordinate witness

Use one `EYE=true` End Portal Frame at block position `(0,0,0)`, no other colliding blocks, and this player AABB immediately before an X-only movement:

- AABB: `x=[-0.35, 0.25]`, `y=[0.8125, 2.6125]`, `z=[0.20, 0.80]` (width `0.60`, height `1.80`)
- Requested movement: `(dx,dy,dz) = (0.02, 0, 0)`
- Axis path conditions for an ordinary `SELF` move: `noClip=false`, `isSneaking=false`, `onGround=false`; these suppress the special sneak-edge and step-up alternatives without asserting how this static state was reached.

The provider boxes, translated by block position `(0,0,0)`, are:

- Both releases' base: `[0,0,0]–[1,0.8125,1]`.
- 1.12.2 eye: `[0.3125,0.8125,0.3125]–[0.6875,1,0.6875]`.
- 1.13.2 eye: `[0.25,0.8125,0.25]–[0.75,1,0.75]`.

The AABB's `minY=0.8125` equals the base `maxY`, so the base is vertically non-overlapping under the strict tests in 1.12.2 `Box.intersectX`; the 1.13.2 axis scan likewise starts at `box.minY + 1.0E-7`, inside the eye layer and above the base. The AABB overlaps both eye boxes in Y (from `0.8125` to `1.0`) and in Z (`[0.20,0.80]` overlaps both eye Z intervals).

### 1.12.2 result: requested X motion is not clipped

`Entity.move` resolves X through each returned collision box's `intersectX(playerBox, dx)`. For the eye box, the positive-motion branch applies because `dx=0.02>0` and `player.maxX=0.25 <= eye.minX=0.3125`. The candidate gap is `0.3125 - 0.25 = 0.0625`. Since `0.0625 < 0.02` is false, `intersectX` leaves the limit at `0.02`. The base does not enter the calculation as an overlapping Y obstacle because its `maxY=0.8125` touches, but does not overlap, the AABB's `minY`. Therefore the resolved X offset is `0.02`.

### 1.13.2 result: X motion is clipped to zero

`Entity.move` resolves X with `VoxelShapes.calculateMaxOffset(Direction.Axis.X, ...)` against the frame's combined state shape because `EYE=true`. In `VoxelShape.calculateMaxDistance`, the positive-X scan uses `getCoordIndex(box.maxX - 1.0E-7)` to start just before the contact boundary, then scans the next occupied voxel. `Block.block` scales the eye's declared X edge `4.0` by `1/16`, so that first new eye boundary is `0.25`; the player box's `maxX` is also `0.25`. The candidate offset is `shapeX - box.maxX = 0.25 - 0.25 = 0`; this satisfies the method's `f >= -1.0E-7` boundary allowance and reduces `maxDist` from `0.02` to `min(0.02, 0) = 0`. Thus the resolved X offset is `0`.

This is the requested clipping divergence: for the same static AABB, frame state, and requested vector, the old source leaves `dx=0.02` while the new source clips it to `dx=0`. The witness establishes a geometric collision-clipping consequence; it does not assert a later position, tick trajectory, frequency, or practical map impact.

## Exact source-file hashes

The source-file hashes below are complete file-byte SHA-256 values. Each value was recomputed locally and matched the corresponding entry in that release's `ornithe-feather.sources.sha256` manifest. This pins all cited provider, movement, and axis-clipping methods to the exact published files without making method-slice hash claims.

| Release | Source file | Whole-file SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/EndPortalFrameBlock.java` | `418a00540523c106571df82c95716e305d4fbb55ad5867148028a7611a9cca11` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.13.2 | `net/minecraft/block/EndPortalFrameBlock.java` | `c196aeba3dd6c9683659ab08b71a759b448709e8cc45e68aca448a7e6e470523` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShape.java` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |

| Manifest | Whole-file SHA-256 |
|---|---|
| `1.12.2/ornithe-feather.sources.sha256` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| `1.13.2/ornithe-feather.sources.sha256` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
The assigned finding revision remains the accepted input report SHA-256 `9455cc16142b446abaaef67dba1e7901f7ab85e8635a0e4a3b2a339629081400`, with accepted audit verdict SHA-256 `1aea672763ef81cc4502db4df64b5743fbb9ac83ca9f0c74e9a2c6e3da1c4277`.

## Limits

This closes only the explicit static overlap/clipping gate for the accepted provider difference. It does not establish pair-wide coverage, intermediate introduction version, a reachable gameplay trajectory from an initial world state, runtime parity, or implementation readiness beyond this geometric consequence.