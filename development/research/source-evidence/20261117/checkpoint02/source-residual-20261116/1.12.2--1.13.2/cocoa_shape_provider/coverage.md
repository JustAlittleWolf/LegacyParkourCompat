# Cocoa player-collision shape provider: 1.12.2 → 1.13.2

## Source identity

- 1.12.2 Feather source manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`
- 1.13.2 Feather source manifest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- Canonical source identity locator: `build/source-identities-20261116/locations.tsv` and `build/source-identities-20261116/README.md`.
- Every source file hash below matches its version's `ornithe-feather.sources.sha256` entry.

## Bounded source finding

**Verdict: no Cocoa collision-shape difference in the examined provider path.** The registered key is `cocoa` in both endpoints; both registry constructions instantiate `CocoaBlock`. The old registration is block id 127 / key `cocoa` (`Block.java:1054`), while the 1.13 registration is `register("cocoa", new CocoaBlock(...))` (`Block.java:1227`) and `Blocks.COCOA` resolves `getOrThrow("cocoa")` (`Blocks.java:894`; old alias at `Blocks.java:425`).

The state pair remains horizontal `FACING` and `AGE` values 0–2. In 1.12.2 `AGE` is `IntegerProperty.of("age", 0, 2)`; `createStateDefinition` declares `(FACING, AGE)` and legacy metadata decodes/encodes those same properties (`CocoaBlock.java:20, 169–181`). In 1.13.2 `AGE` aliases `Properties.AGE_2`, default state sets `FACING=NORTH, AGE=0`, and the state builder adds `(FACING, AGE)` (`CocoaBlock.java:22, 36–39, 143–145`). The changed serialization/registration framework is not evidence of a changed Cocoa state-to-shape mapping in this slice.

Both getters index shape arrays by `AGE`, then choose the array for `FACING` (`CocoaBlock.java:77–90` old; `63–76` new). All twelve boxes are equal: the old normalized coordinates equal the new pixel coordinates divided by 16. For example, EAST age 0 is old `[11/16,7/16,6/16 → 15/16,12/16,10/16]`, new `[11,7,6 → 15,12,10] / 16`; the same equality holds for all three ages and four directions. Neither provider consults world, neighbor, or contextual state when producing its shape.

The shape is reachable from local-player movement collision queries. In 1.12.2, `LocalClientPlayerEntity.move` calls `super.move` (`LocalClientPlayerEntity.java:846–850`); `Entity.move` gathers `World.getCollisions` (`Entity.java:462` onward; calls at 525–613); `World.getCollisions` iterates block states and calls `addCollisions` (`World.java:964–1019`), whose block default requests `state.getCollisionShape` (`Block.java:371–388`). In 1.13.2, the same local-player override calls `super.move` (`LocalClientPlayerEntity.java:871–876`), and `Entity.move` obtains world collision shapes (`Entity.java:473–612`). `BlockState.getCollisionShape` delegates to the block (`BlockState.java:205–210`); the default `Block.getCollisionShape` returns `state.getShape` when `hasCollision` is true (`Block.java:388–399`). Cocoa is registered without `.noCollision()`; `Block.Properties.hasCollision` defaults to true (`Block.java:1901`), so its `getShape` array is the player collision provider. In 1.12.2 the default collision method likewise delegates to `state.getShape` (`Block.java:367–388`), while `getOutlineShape` returns that same state shape translated to world coordinates (`Block.java:366–369`).

## Evidence identities

| Version | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/CocoaBlock.java` | `653021a977a09f4c6befed7f5a84dcbc336560d4291ba8534105d778f6aeb52c` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/block/state/BlockProperties.java` | `6c311b6990c949c61872cdd3f94ab5e33f39c252467662aa667b54716bd48c16` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13.2 | `net/minecraft/block/CocoaBlock.java` | `6655df26d136eb21d94b323496c77b5237484a86a1362e4ddd636e35c6997445` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

## Closure boundary

This closes only the Cocoa provider's key/class/state-property and collision-shape mapping, including its direct local-player query path. It does not close the pair's broader block-provider/state/neighbor inventory or the pair's source coverage. Fertilizer/growth, fall damage and entity-response behavior were not examined.
