# 1.13.1/.2 mixed-slab coordinate and timer addendum

**Status: geometry path closes; exact timer write is conditional on the listed callback/state gates below.** The source evidence establishes that the specified horizontal collision query sees the top slab before the accumulated full blocks and reaches `ticksToNextAutojump = 1`. The prompt does not explicitly provide every non-geometric condition or the two initial player-column air states, so those are kept as explicit prerequisites rather than silently assumed. This is bounded to the supplied scene and does not audit the general pair.

The original slab alias report is unchanged and remains the source for the 1.14 data-fixer identity `1.13.x minecraft:stone_slab` → `1.14 minecraft:smooth_stone_slab`. Here the 1.13 state is `minecraft:stone_slab[type=top,waterlogged=false]`.

## Movement resolution from the fixture

Inputs: player center `(x=.59, y=8.1, z=1.1)`, requested move `(+.10, -.20, 0)`, player dimensions `.6F × 1.8F`, ordinary non-sneaking movement, and a full support surface at y=7 with top y=8 under the player's footprint.

- The pre-move player box is approximately `[.289999988,.8900000119] × [8.1,9.899999952] × [.799999988,1.400000012]` (the x/z half-width comes from float `.6F / 2`).
- The axis-ordered entity move resolves Y before X. Downward `-.20` is clipped by the support at y=8 to `-.10`; x remains `+.10`, z remains zero. The post-move box is approximately `[.389999988,.990000012] × [8,9.799999952] × [.799999988,1.400000012]`, so position is `(x=.69,y=8,z=1.1)`.
- The neighboring blocks at x=1 start at x=1, leaving about `.01` clearance at the player's maxX. The stone, cap and slab do not clip this ordinary movement. `collidingHorizontally=false`, `collidingVertically=true`, `onGround=true` because requested Y was negative and resolved Y differs. The step-up retry is skipped because the horizontal components were not clipped.
- The entity-move collision collector's expanded box is about `[.289999888,.990000112] × [7.899999876,9.900000052] × [.799999888,1.400000112]` after its `1e-7` expansion. Its conservative BlockPos scan range is x `-1..1`, y `6..10`, z `-1..2`. The y=7 support cell(s) in the player's footprint must be loaded; the listed x=1 obstacles remain outside the x extent of the player box at the contact point.

This proves the stated resolved position and grounded callback precondition when the supplied y=7 support reaches the player's footprint. If the support does not cover any portion of the footprint, report that as a fixture blocker; the auto-jump source alone cannot manufacture grounded state.

## AutoJump query and stream order

`LocalClientPlayerEntity.move` calls `autoJump((float)(newX-oldX),(float)(newZ-oldZ))` after `super.move`. For the described movement the callback receives `dx=.1F`, `dz=0.0F`. With the vanilla base movement attribute `.1F` and no speed modifier, the source operations produce `g=(float)(dx²)=0.010000000707805157`, `l=(float)fastInvSqrt(g)=9.982504844665527`, `vec3d4.x=dx*l=0.9982504993416441`, `p=max(.1F*7F,1F/l)=.699999988079071`, and the extended endpoint x≈`1.4887753391`. This nonzero displacement bypasses the input-direction fallback. A speed modifier changes `p` and the far edge, not the stated first slab candidate.

The AutoJump broad-phase box, from the post-move feet vector through the extended endpoint plus player height and `.6F` horizontal growth, is approximately:

- x `[0.0899999762, 2.0887753630]`
- y `[8.0, 9.7999999523]`
- z `[0.4999999762, 1.7000000238]`

Thus `WorldView.getBlockCollisions` computes iterator coordinates x `-1..3`, y `7..10`, z `-1..2` (its arguments are `i=-1,j=4,k=7,l=11,m=-1,n=3`, then it iterates through each upper bound minus one). It checks chunk-loaded state per cell. The relevant fixture cells `(1,8,0)`, `(1,9,0)`, `(1,8,1)`, `(1,9,1)`, plus the player-column probes `(0,9,1)` and `(0,10,1)`, all lie in chunk `(0,0)`; those states must be loaded. The conservative outer scan also touches negative x/z coordinates, which may be skipped if adjacent chunks are unloaded.

With baseline speed, the two AutoJump side segments run from x `.69` to about `1.4887753391`, at y `8.5099999905`, with z≈`.8005248383` and `1.3994751617`. The dry TOP slab collision box at `(1,8,1)` is `[1,8.5,1]`–`[2,9,2]`; it intersects the positive-z side segment. The full stone at `(1,8,0)` also geometrically meets the other side segment, and the full cap at `(1,9,0)` lies in the broad-phase box, but neither is the first yielded candidate.

That ordering follows the 1.13.1/.2 `WorldView.getBlockCollisions` implementation: individual non-full shapes are streamed as encountered; shapes whose collision shape is exactly the full-cube singleton are accumulated in a `VoxelSet`, then one `IntVoxelShape` for the accumulated full cells is concatenated after the partial-shape stream. A TOP slab is a half-height shape, so it is a partial shape here even though `SlabBlock.isFullBlock` returns true for TOP. The fixture has only the stated non-full slab; it is therefore the first candidate before the aggregated stone/cap shape. AutoJump breaks its collision iterator after that first intersecting candidate, so it never reaches the full-cell aggregate or the later entity-collision stream.

For that slab box, `s=(float)box2.maxY=9.0F`; `box2.center()` is `(1.5,8.75,1.5)`, and legacy `BlockPos` conversion floors it to `(1,8,1)`. With no Jump Boost, `o=1.2F`, so the rise loop executes only `t=1`:

1. It samples `(1,9,1)`. The given air state yields an empty collision shape; `s` remains 9.
2. Since `t` is not greater than 1, the extra standing-clearance test is not entered. `t=2` fails `2 < 1.2F`, then the loop ends.
3. The iterator breaks. `u=(float)(s-playerMinY)=9-8=1.0F`; this is `>0.5F` and `<=1.2F`, so the exact source write is `ticksToNextAutojump = 1`.

The direct pre-scan before this collector floors the player's center and current box maxY, not the obstacle's x coordinate: it probes `(0,9,1)` and then `(0,10,1)`. Both must have empty collision shapes for the scanner to run. The prompt names `(1,9,1)` air but does not separately list these two player-column states; if “headroom” includes the x=0 column, this condition is met. Otherwise their fixture state is the remaining geometric evidence gap.

## Border and entities

At these coordinates the default 1.13 world border (`StaticExtent(6.0E7)`, centered at the origin) is far outside both scan boxes. `collidesWithBorder` is true for the player and the entire scan region is inside the border, so no synthetic border cube is emitted. `World.getCollisions` appends entity collision shapes after block shapes; the current player is excluded by the entity query and the fixture states no other entity occupies the expanded query. Consequently the entity stream contributes no candidate. A custom border or another nearby entity would be a different fixture.

## Callback gates that must hold for the timer statement

The method writes the timer only if AutoJump is enabled, `ticksToNextAutojump <= 0`, the player is grounded, not sneaking, not riding, and the movement-input vector is nonzero. These are ordinary fresh-player conditions except that the task did not give the option/input/cooldown values explicitly; the integer cooldown starts at Java's default zero. It also rejects a view direction whose horizontal dot with `vec3d4` is below `-0.15F`; yaw was not supplied, so the timer result is conditional on that dot gate passing. Jump Boost raises `o` and causes additional `t` samples; the numeric one-iteration result above assumes none.

## Manifest and hashes

Exact roots are 1.13.1 Feather Gen2 build.2 and 1.13.2 Feather Gen2 build.2. The version-level `.sources.sha256` manifests hash to `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; each ready marker's declared source-manifest digest matches. All full source hashes below were checked against both exact source manifests. Files shared verbatim by the two releases are identified once; the World.java and PlayerEntity.java files differ, but the relied-on members have the same normalized member hashes in both releases.

| Source file(s) | SHA-256 |
|---|---|
| Both: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| Both: `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| Both: `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| Both: `net/minecraft/util/math/Vec3i.java` | `481827f072a5d36ae3a7f133afed5396988e3ce7c0501b2777a92b60c6a498b8` |
| Both: `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| Both: `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| Both: `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| Both: `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| Both: `net/minecraft/world/EntityView.java` | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` |
| 1.13.1: `net/minecraft/world/World.java` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` |
| 1.13.2: `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| Both: `net/minecraft/world/border/WorldBorder.java` | `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718` |
| Both: `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.1: `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2: `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| Both: `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| Both: `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| Both: `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| Both: `net/minecraft/block/state/property/SlabType.java` | `20ef3591cf5f5cf83886d7ab5034fd8402418f68e517180cbba2fe0c7535f697` |

Key member SHA-256 values use inclusive source lines normalized to UTF-8 LF plus one trailing LF; these match across 1.13.1 and 1.13.2:

| Member | SHA-256 |
|---|---|
| `LocalClientPlayerEntity.java:871-980` move callback and AutoJump body | `08412d19a98a322371d8f19b7de34b1d38957fe7e242d8483fc93470b0762177` |
| `Entity.java:473-776` collision movement and onGround write | `db00110dfe18843f01f774096f9facb08b1903c8612e0a8ae9e659357be7de28` |
| `WorldView.java:112-181` partial/full collection, bounds, border gate | `645c792d89fc0294f59c35fb4a119324677794aad05029594c07f0dd836608d7` |
| `World.java:1755-1775` block/entity append and entity query | `0225643824b3f1c345ea75cfefe5a5ead4f581cdabf2a8fbc59944ed7f3f635f` |
| `EntityView.java:21-35` entity-collision stream | `e4f0738f20760b5f4a2530d826900859db964ee23a8e8b032fa4a230935a4008` |
| `BlockPos.java:212-240` collector iteration order | `f71012951b408dd109226d7836293bf2f88502ff28e572f395d7ffb0c7934598` |
| `MathHelper.java:386-392` legacy fast inverse square root | `1a71a29fa65ab7d518cba33b158ecd9843a45989036d6a91c09be334919b928c` |
| `Box.java:135-142`, `192-203`, `335-337` grown/intersection/center | `ee660610c9d9d5b3d2495888ddd7c84edce299fbb33a05840f7309c8154dc97d`, `1dc74dbd103005d79e9f4cb4f7c17b67baf23c425d11c5293b7d78679af52902`, `6766f0b79d3365b4697f8da370111163fdd90b22888e5b3394b63ad3793839e5` |
| `PlayerEntity.java:158-160`, `1488-1490` speed base/accessor | `a6a8316572d3edf7538633818f2694f07fda43b2fc136c9976589710f1a61078`, `25afda101113747defb7b443ca2d555fd28f1620b38b81f4e2f67912f287f5c4` |
| `SlabBlock.java:24-59` dry state/default and top shape | `55cb8dc07a21c8fbbc38e1497807dec77dd28dbb3affafdc2b2a38e12b16888d` |
| `WorldBorder.java:14-22`, `298-300` default extent | `5a1dd691b4eed6216dce2d15360c612139be1f9efc69fc21369251867c338cd1`, `38881f35327b22e98a8b6b05dbb9d1df25e2fc9bde8d1d77ed9e14bcd5fb60eb` |

