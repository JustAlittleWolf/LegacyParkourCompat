# Wheat collision shape provider: 1.12.2 → 1.13.2

## Bounded verdict

No ordinary player collision delta is established for `minecraft:wheat`. Its AGE-dependent shape geometry is unchanged for ages 0–7, and the shape remains excluded from block collision in both versions. The provider route changes, but it does not make wheat collidable. This closes only the registered wheat key/provider slice; it does not freeze the pair.

## Exact key and effective owners

The accepted 1.12.2 registry inventory row 59 (`build/source-drafts/registry-collision-1122.md:84`) joins `minecraft:wheat` to `WheatBlock`, with effective `addCollisions=Block`, `getCollisionShape=PlantBlock`, and `getShape=WheatBlock`. The accepted 1.13.2 inventory (`registry-collision-1132.md:174`) joins the same exact key to `WheatBlock`, with `Block.getCollisionShape → WheatBlock.getShape`; the crosswalk records this as `EXACT_KEY_ONLY` at `registry-collision-crosswalk.md:113`. This is an exact-key join, not an alias inference. Inventory and crosswalk hashes are listed below.

## Provider and geometry

1.12.2 `WheatBlock.java:18-28,40-42` declares `AGE` in `[0,7]` and `getShape` indexes its eight `Box` entries directly by AGE. Their y maxima are `0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0`; each box spans x/z `[0,1]` and y from 0. Inherited `PlantBlock.java:64-73` `getCollisionShape` returns `EMPTY_BLOCK_SHAPE` for every state, so the wheat provider shape is not used in ordinary block collision. The base collision call is `Block.java:372-389`; state-to-block dispatch is `BlockProperties.java:82` and `StateDefinition.java:370-371`.

1.13.2 `WheatBlock.java:18-28,35-38` uses `Properties.AGE_7` and indexes eight `VoxelShape` entries by that state value. The entries use y maxima `2,4,6,8,10,12,14,16` in block units; `Block.block` at `Block.java:136-138` divides coordinates by 16.0, yielding the same eight bounds. `PlantBlock.java:11-50` has no `getCollisionShape` override. The base `Block.getCollisionShape` at `Block.java:393-395` returns an empty shape when `hasCollision` is false. The canonical wheat registration at `Block.java:999` constructs wheat with `Block.Properties.of(Material.PLANT).noCollision()`; `Properties.noCollision` sets `hasCollision=false` (`Block.java:1943-1946`), so this gate excludes all AGE shapes from collision. State-to-block dispatch is `state/BlockState.java:209-211`.

## Ordinary player reachability

In 1.12.2, `Entity.move` (`Entity.java:462,567`) queries `World.getCollisions` (`World.java:964-1001`); the block loop calls `BlockState.addCollisions`, and the wheat state reaches `PlantBlock.getCollisionShape`, which returns empty. In 1.13.2, `Entity.move` (`Entity.java:473,580,612`) queries `World.getCollisions` (`World.java:1755-1757`) and `WorldView.getCollisions` (`WorldView.java:154-177`); block collision asks the state's collision provider, whose base `Block.getCollisionShape` applies wheat's `hasCollision=false` and returns empty. `PlayerEntity` extends `LivingEntity`, which extends `Entity` (`living/player/PlayerEntity.java:98`; `living/LivingEntity.java:94`), so ordinary player movement follows these routes. Wheat's shape cannot constrain a player's movement in either release.

## Evidence identities

Exact source manifests match the assigned identities: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The ready markers report these same source-manifest hashes.

Source-file SHA-256 values, verified against the canonical source manifests:

| Version | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/WheatBlock.java` | `df121e79a01eb3f71b427cc0b40e9c8c84fe86813328d1b246a7fa6456b135d3` |
| 1.12.2 | `net/minecraft/block/PlantBlock.java` | `919eb37d8e751b1515d0e9cb3af6b8ce516d2409d9639fdf4bfa6260fad5808c` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/block/WheatBlock.java` | `f8b50a11a50f546f2b474fa20cc272a0534d4276adf94ef5c936e83edfef2d62` |
| 1.13.2 | `net/minecraft/block/PlantBlock.java` | `a1234ccec309bd8805fbe3bad48bce076e17c131e048d2a57d18cdb75f0f286e` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

Accepted source-only registry inputs:

- `build/source-drafts/registry-collision-1122.md`: `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`
- `build/source-drafts/registry-collision-1132.md`: `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`
- `build/source-drafts/registry-collision-crosswalk.md`: `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`
- `build/source-residual-20261116/1.12.2--1.13.2/provider_override_roster/provider-override-roster.md`: `524e27cf0618ea8942a961f27835fabf7091248a0629b9f03f4bd4d39c05a979`

The scope excludes growth, harvesting, food, rendering, and non-player paths. No implementation, gameplay, or pair-freeze claim is made.
