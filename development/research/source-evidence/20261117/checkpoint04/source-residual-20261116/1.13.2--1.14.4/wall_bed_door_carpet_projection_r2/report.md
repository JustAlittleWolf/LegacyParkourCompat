# Wall face projection: beds, doors, and carpets (1.13.2 → 1.14.4)

## Scope and evidence identity

This closes only the three common-key families assigned from `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`): all 16 bed color keys, seven door keys, and all 16 carpet color keys. It compares the old horizontal `FaceShape` result as consumed by 1.13.2 walls, including that wall's old connection-exception gate, with the 1.14.4 wall's attachment gate and full-collision-face predicate for north/east/south/west.

Exact endpoints are 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. Ready markers and manifest byte hashes bind the sources to:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`

The source evidence below was checked against the endpoint manifests. Hashes are SHA-256 of complete source files.

## Predicate being compared

In 1.13.2, `WallBlock.shouldConnectTo` first rejects `isExceptionForConnection(block)`, then accepts the generic arm only for `FaceShape.SOLID` (`WallBlock.java:51–57`); placement and directional neighbor updates ask the neighboring state's opposite horizontal face (`WallBlock.java:81–84, 104–116`). The old exception chain starts with `Block.isExceptionForPlacement` and excludes piston, sticky piston and piston head (`Block.java:241–256`); `WallBlock.isExceptionForConnection` additionally excludes barrier, melon block, pumpkin, carved pumpkin, lit pumpkin, frosted ice and TNT. Beds, doors and carpets are not in either exception set. So for these classes the old bit is false whenever the old face result is `UNDEFINED`.

In 1.14.4, `WallBlock.shouldConnectTo` uses `!isExceptionForAttachment(block) && Block.isFaceSolid(state, world, pos, direction)`, or its separate walls-tag/fence-gate arm (`WallBlock.java:48–52`). The three assigned families are not in the attachment exception list (`Block.java:260–267`) and are not wall-tag members, so their generic result is the collision shape's projected full-face result. `Block.isFaceSolid` calls `isFaceFull(state.getCollisionShape(...), face)` (`Block.java:402–408); `VoxelShape.getFaceShape` selects the occupied slice at that exterior side (`VoxelShape.java:189–200`). The assigned blocks retain collision enabled by their registrations and default properties; none calls `noCollision()`.

Directions in result tuples below are the queried face directions N, E, S, W. For a wall bit named N/E/S/W, WallBlock queries the opposite face on the neighboring block; thus a true face in the table sets the opposite wall bit. These are not measured player trajectories.

## Complete registration and state-domain crosswalk

The old registry is declared in 1.13.2 `Block.java`; the target registry is 1.14.4 `Blocks.java`. Both sides join the listed keys to the same `BedBlock`, `DoorBlock`, and `CarpetBlock` classes.

| Family | Common registered keys | State domain per key | Number of registered key/state combinations |
|---|---|---|---:|
| Beds | `white_bed`, `orange_bed`, `magenta_bed`, `light_blue_bed`, `yellow_bed`, `lime_bed`, `pink_bed`, `gray_bed`, `light_gray_bed`, `cyan_bed`, `purple_bed`, `blue_bed`, `brown_bed`, `green_bed`, `red_bed`, `black_bed` | `FACING=N/E/S/W`; `PART=FOOT/HEAD`; `OCCUPIED=false/true` (16 states/key) | 256 |
| Doors | `oak_door`, `iron_door`, `spruce_door`, `birch_door`, `jungle_door`, `acacia_door`, `dark_oak_door` | `FACING=N/E/S/W`; `OPEN=false/true`; `HINGE=LEFT/RIGHT`; `POWERED=false/true`; `HALF=LOWER/UPPER` (64 states/key) | 448 |
| Carpets | `white_carpet`, `orange_carpet`, `magenta_carpet`, `light_blue_carpet`, `yellow_carpet`, `lime_carpet`, `pink_carpet`, `gray_carpet`, `light_gray_carpet`, `cyan_carpet`, `purple_carpet`, `blue_carpet`, `brown_carpet`, `green_carpet`, `red_carpet`, `black_carpet` | No block-state properties (one state/key); color is carried by each registered instance | 16 |

The door state-definition product includes all 64 defined state combinations per key. `HALF` and `POWERED` do not alter the collision-shape branch; no placement-reachability restriction is assumed for this state-domain crosswalk. Bed `OCCUPIED` does not alter its shape.

Registration loci: 1.13.2 beds `Block.java:901–916`, doors `:1004, 1014, 1579–1583`, carpets `:1399–1429`; target beds `Blocks.java:212–257`, doors `:448–499, 1393–1404`, carpets `:1105–1152`. The class state definitions are in old/new `BedBlock.java:276–278`, `DoorBlock.java:266` / `:256`, and the carpet class has no state-definition override.

## Projection results

| Family | Old 1.13.2 horizontal result for every state | Target 1.14.4 horizontal result for every state | Verdict |
|---|---|---|---|
| Each bed key/state | `UNDEFINED` → old wall bit (N,E,S,W) `false,false,false,false` | `false,false,false,false` | No face-bit delta |
| Each carpet key/state | `UNDEFINED` → old wall bit (N,E,S,W) `false,false,false,false` | `false,false,false,false` | No face-bit delta |
| Each door key/state | `UNDEFINED` → old wall bit (N,E,S,W) `false,false,false,false` | Exactly one target face is true; mapping below | Face-bit delta on the listed side |

### Beds

Old `BedBlock.getFaceShape` returns `FaceShape.UNDEFINED` without inspecting face or state (`BedBlock.java:271–273`). The target shape provider is the same one already bounded in the accepted bed provider report: it unions a full-footprint top from y=3/16 to 9/16 with two oriented 3/16-square feet extending down to y=0; `FACING` and `PART` orient the feet, while `OCCUPIED` is ignored. On any horizontal side, the projected collision slice lacks the entire 0..1 vertical extent: the top stops at 9/16 and the lower corner supports occupy only parts of the side. Therefore none of N/E/S/W is a full block face for any of the 16 states per color key.

### Doors

Old `DoorBlock.getFaceShape` also returns `UNDEFINED` for every face (`DoorBlock.java:270–272`). The target's four collision shapes are 3/16-thick full-height slabs at one exterior side: `SOUTH_SHAPE` is z=0..3/16, `NORTH_SHAPE` z=13/16..1, `WEST_SHAPE` x=13/16..1, and `EAST_SHAPE` x=0..3/16 (`DoorBlock.java:39–42`). The slab's outward side slice is a full 1×1 square, so the target face predicate is true on that one side and false on the other three. The provider selects that side from `FACING × OPEN × HINGE`; `HALF` and `POWERED` do not affect it. Across each key's 64 defined states:

| `FACING` | Closed (`OPEN=false`), either hinge | Open, `HINGE=LEFT` | Open, `HINGE=RIGHT` |
|---|---|---|---|
| NORTH | SOUTH | WEST | EAST |
| EAST | WEST | NORTH | SOUTH |
| SOUTH | NORTH | EAST | WEST |
| WEST | EAST | SOUTH | NORTH |

Each cell names the only target true face direction. The target is false on the other three faces. That face sets the opposite named wall bit because WallBlock queries the opposite face on the neighboring block. Since the old result was false in all four directions, every door key has target true projections where the old corresponding wall bit was false. This is a source-derived difference in the input to wall collision-state construction, not a claimed tick trajectory.

### Carpets

Old `CarpetBlock.getFaceShape` returns `SOLID` only for DOWN and `UNDEFINED` for the four horizontal faces (`CarpetBlock.java:46–48)). The target provider returns the constant collision shape `[0,1] × [0,1/16] × [0,1]` (`CarpetBlock.java:13, 27–29); the horizontal exterior slices are only 1/16 high, so none is a full projected face. All 16 registered color instances therefore remain false on N/E/S/W.

## Bounded conclusion and limits

This assigned family slice establishes a wall-neighbor projection delta for all seven common door keys, with the target true direction varying by `FACING`, `OPEN`, and `HINGE`. No such delta is established for any of the 16 bed or 16 carpet keys. Wall placement and horizontal updates consume the connection predicate, and the resulting directional wall bits feed the wall shape. No player trajectory or movement parity consequence was tested. The overall common registration/state-space census remains incomplete; this report does not claim complete wall-neighbor parity, pair coverage, pair freeze, or an intermediate patch boundary.

The accepted bed provider report and door provider coverage record are reused only for their identical provider geometry and state-property claims: `bed_shape_provider/report.md` SHA-256 `509950547603b487a25cbc83435367bc2a2cde743a9442909e8d1dde5f17d11a`; `door_shape_provider/coverage.md` SHA-256 `18ba45c9b4c44e258cd044bab46cb8aab3a43383649c7575162a20084aea0df5`. Their acceptance does not cover this wall-face projection. Carpet geometry and all wall predicate comparison claims are grounded directly in the exact source files and hashes below.

Exact intermediate release inspection was not performed; any change boundary is only known to lie somewhere between these concrete endpoints. Decompilation is Feather Gen2 build 2; these findings are tied to that mapping/source identity, not an online mapping lookup. The inherited census's direct-`getFaceShape` scan and incomplete key crosswalk also mean this bounded report is not a complete inventory of wall neighbor behavior.

## Source file SHA-256

| Source file | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/BedBlock.java` | `a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18` | `aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1` |
| `net/minecraft/block/CarpetBlock.java` | `cc3a3e0356a9a0e1ba4d82e4dc2caf880999294bc72ad7be62a43e031faaa4a4` | `55a7faf5d395480ebd6a47ff9820f6a5082807d5f5b2c6072d11a79316a00b25` |
| `net/minecraft/block/DoorBlock.java` | `e01782f96a2214be2bc48dffad8b062db9eda12c04aa4d7747f01575d0df019b` | `5c40e94e04427c8a16a301aad12f7d2efdef866f70b21acd830fc683099dcbcd` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` (target registrations) | — | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/shape/VoxelShape.java` (target projection) | — | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |

No source was generated or modified. No wiki, product implementation, backlog, or Git history was consulted. No tests, build, game, or network access was used.
 






