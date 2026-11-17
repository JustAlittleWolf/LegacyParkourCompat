# Air and command block wall predicate projection (1.13.2 → 1.14.4)

## Bound scope and source identity

This packet compares only six residual common keys from the immutable unexamined wall-projection crosswalk: `minecraft:air`, `minecraft:cave_air`, `minecraft:void_air`, `minecraft:command_block`, `minecraft:chain_command_block`, and `minecraft:repeating_command_block`. The crosswalk is `build/source-residual-20261116/1.13.2--1.14.4/wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`, SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`.

Exact pinned source manifests from Feather Gen2 build 2: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The ready markers identify those exact endpoint versions and source manifest digests. I checked each consulted Java file hash against the corresponding `ornithe-feather.sources.sha256` sidecar; all matched.

| Java source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/AirBlock.java` | `dc80bcd262bc9e8e84bfd66971bfaf8ed621f02899ae8802c9cb2b2fef8a93c4` | `5f39eac1ec12b6e5c37e07a6c153a3a539c10c26accb016e8384c801b6083d04` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/CommandBlock.java` | `a6842dac2a1174662b7250b891cd7dd6812af58e9d68f296653cb0105c641232` | `e64beafe1cf66376ff958d04ddb36fbf3994e4bc9b92e6da9bcd0af68f036010` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

## Provider and predicate routes

Old 1.13.2 `BlockState.getFaceShape` delegates to its block's `getFaceShape` (`BlockState.java:285–287`). `AirBlock.getFaceShape` returns `FaceShape.UNDEFINED` (`AirBlock.java:47–49`); all three air keys are `AirBlock` registrations. `Block.getFaceShape` returns `FaceShape.SOLID` (`Block.java:383–385`), inherited by all three `CommandBlock` registrations. `CommandBlock` has no face-shape or geometry override. Its state builder adds `FACING` and `CONDITIONAL` (`CommandBlock.java:181–183`), giving 6 × 2 = 12 registered states per command key; neither property affects inherited geometry.

Old `WallBlock.shouldConnectTo` accepts `SOLID` unless `isExceptionForConnection` applies, with a separate thick-pole/fence-gate rule (`WallBlock.java:51–55`). The exception helper is the common attachment-exception set plus barrier, melon/pumpkin variants, frosted ice, and TNT (`WallBlock.java:57–66`). The three air keys are `UNDEFINED`; command blocks are not in either exception set and are not fence gates, so the old bits are false for air and true for commands across each key's complete state domain.

New 1.14.4 `BlockState.m_87223014` reaches `Block.isFaceSolid` (`BlockState.java:327–328`). That method rejects LEAVES and otherwise checks whether the collision-shape face is full (`Block.java:402–403`). The target wall predicate additionally checks the WALLS tag/fence-gate arm and attachment exception (`WallBlock.java:48–51`). None of these six keys is in the attachment-exception or WALLS sets and none is a fence gate.

`AirBlock.getShape` returns the empty shape (`AirBlock.java:22–24`), and all three target air registrations set `.noCollision()` (`Blocks.java:21, 1848–1849`); base `Block.getCollisionShape` returns empty when `hasCollision` is false (`Block.java:376–378`). Thus target air wall bits are false. The three target command registrations use `CommandBlock` with default collision-enabled properties and do not call `.noCollision()` (`Blocks.java:792–794, 1435–1440`); `Block.Properties.hasCollision` defaults true (`Block.java:811`) and base `Block.getShape` returns a full cube (`Block.java:371–374`). Therefore target command collision faces are full and target wall bits are true for all directions and all states.

## Exhaustive bounded result

| Keys | Registered states per key | Old wall bit | Target wall bit | Result |
|---|---:|---:|---:|---|
| `air`, `cave_air`, `void_air` | 1 | false (`UNDEFINED`) | false (empty collision face) | false → false; 12 key/direction cases |
| `command_block`, `chain_command_block`, `repeating_command_block` | 12 (`FACING` 6 × `CONDITIONAL` 2) | true (`SOLID`, not excepted) | true (full collision face, not excepted) | true → true; 144 key/state/direction cases |

The same projection holds independently for NORTH/EAST/SOUTH/WEST. This closes only these six wall-neighbor predicate rows. It makes no player trajectory claim, says nothing about other residual registry rows, and does not freeze the pair. The crosswalk's r1 TSV overwrite limitation remains attached to its r2 correction audit; this packet relies on the current r2 residual TSV identity only for selecting these six unexamined keys.

### State-domain and old air-key anchors

`FacingBlock.FACING` aliases `Properties.FACING`; that property explicitly lists NORTH, EAST, SOUTH, WEST, UP, DOWN (`Properties.java:53–55` in 1.13.2 and `:59–61` in 1.14.4). `CommandBlock.CONDITIONAL` aliases `Properties.CONDITIONAL`, a BooleanProperty (`Properties.java:21` and `:24`). `CommandBlock.buildStateDefinition` adds both properties, so each command key has exactly 12 states. In 1.13.2 `Block.init` registers the default registry key `air` with a no-collision AirBlock (`Block.java:775–777`); `void_air` and `cave_air` are likewise AirBlock/noCollision (`Block.java:1872–1873`). The target registrations are `Blocks.AIR` (`Blocks.java:21`), `VOID_AIR` and `CAVE_AIR` (`Blocks.java:1848–1849`).

Property source hashes, verified against both source-manifest sidecars:

| Java source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/block/FacingBlock.java` | `2e90aa010630ae9786a631f56cdee4b454a5d01359f15ac29f52a15ad3c9eb1e` | `2e90aa010630ae9786a631f56cdee4b454a5d01359f15ac29f52a15ad3c9eb1e` |

`CommandBlock` extends `BlockWithBlockEntity`; I also checked that neither endpoint superclass overrides `getFaceShape`, `getShape`, or `getCollisionShape`, so the cited Block behavior is inherited. The endpoint class hashes are 1.13.2 `BlockWithBlockEntity.java` `9f190eebfcff3f23ce76bceafd6295d4e84d93cc3a76d85f83972792c811a266`, and 1.14.4 `dc644d4ab779d5ee65f5a0828097d3732efdbd9cc8fc8ecd1660ae7f1b322a2a`; both were checked against their source sidecars.
