# Anvil: 1.13 family to 26.2 provider closure

## Immutable verdict

**Verdict: collision-provider occupancy is equivalent in 1.13, 1.13.1, 1.13.2, and 26.2 for every valid Anvil facing. No native geometry delta is missing for this operation.** The 26.2 provider is expressed through centered columns and a horizontal-axis rotation, but those components reproduce the 1.13 union exactly, including axis orientation. The chipped and damaged variants use the same provider in every inspected release.

This is a static source verdict about the selected block collision shape. It does not assert runtime parity for the rest of player movement.

## Bound inputs and source identity

Accepted inputs: `.native-source/20261030/collision-pool/geom-nonwalls-review/review.md` (SHA-256 `4f0786929fe751474613d258ca097d5fb5bcec8fba075164f7fca89b130f4c45`), `.native-source/20261030/collision-pool/anvil/FINDING.md` (`ac737707aa9286d6c63aa333fadaad7a58e613d98e5bfb4cf878191a88264f23`), and `development/research/findings/1.12.2--1.13.2/anvil-collision-shape-components.md` (`0b06933542a585cbb7edf64e10d34596631077505a8058d212b8092dfe8df410`).

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Exact release | Mapping/source identity | Source manifest SHA-256 | AnvilBlock SHA-256 |
|---|---|---|---|
| 1.13 | Ornithe Feather Gen2 build 2; client `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` |
| 1.13.1 | Ornithe Feather Gen2 build 2; client `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` |
| 1.13.2 | Ornithe Feather Gen2 build 2; client `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` |
| 26.2 | unobfuscated exact-version source; ready source manifest | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` |

The three 1.13-family `AnvilBlock.java` files are byte-identical. Ready markers identify those exact releases/mappings; their manifest digests above were recomputed from the canonical manifests. Relevant 26.2 helper hashes: `Block.java` `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `Shapes.java` `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891`; `BlockBehaviour.java` `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`; `Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`.

## Component-union comparison

Coordinates below are sixteenths of a block. In 1.13 `AnvilBlock.java:29-37`, `Block.block` (`Block.java:152-154`) divides each coordinate by 16. In 26.2 `Block.column` (`Block.java:177-184`) centers the requested X/Z sizes at 8 and uses the supplied Y interval.

The 26.2 base column `(sizeXZ=12, Y=0..4)` is `[2,0,2]..[14,4,14]`, exactly the 1.13 base. Its unrotated Axis.Z components are:

| 1.13 component selected for facing axis Z (`SHAPE_X`) | 26.2 unrotated column | Exact box |
|---|---|---|
| `LEG_1_SHAPE_Z` | `(sizeX=8, sizeZ=10, Y=4..5)` | `[4,4,3]..[12,5,13]` |
| `LEG_2_SHAPE_Z` | `(sizeX=4, sizeZ=8, Y=5..10)` | `[6,5,4]..[10,10,12]` |
| `TOP_SHAPE_Z` | `(sizeX=10, sizeZ=16, Y=10..16)` | `[3,10,0]..[13,16,16]` |

Each box is identical, so their union is identical. `Shapes.rotateHorizontalAxis` (`Shapes.java:379-384`) maps that Axis.Z union to Axis.X by a quarter-turn around block center. This yields the 1.13 `SHAPE_Z` union for X-axis facing: `[3,4,4]..[13,5,12]`, `[4,5,6]..[12,10,10]`, and `[0,10,3]..[16,16,13]`, plus the unchanged base. This agrees with 1.13's `getShape` axis selection (`AnvilBlock.java:71-74`) and 26.2's axis lookup (`AnvilBlock.java:77-79`). The `FACING` domain is horizontal in both versions, and the default is NORTH (`1.13 AnvilBlock.java:28,39-42`; `26.2 AnvilBlock.java:34,47-50`).

1.13 registers `anvil`, `chipped_anvil`, and `damaged_anvil` as `AnvilBlock` instances (`Block.java:1303-1305`). 26.2 registers the same three keys with `AnvilBlock::new` (`Blocks.java:2853-2881`). Damage tier does not select geometry.

## Player collision-query path

The old default collision provider returns the state's shape when collision is enabled (`1.13 Block.java:404-410`). In 26.2 the equivalent default is `hasCollision ? state.getShape(level, pos) : Shapes.empty()` (`BlockBehaviour.java:323-329`). Both Anvil shape overrides choose only the facing axis and ignore world, position, and—in 26.2—the `CollisionContext` argument. Anvil registrations do not disable collision; 26.2 properties default `hasCollision=true` (`BlockBehaviour.java:985`).

The 1.13 movement collision query calls each state's `getCollisionShape` (`WorldView.java:113-145`). Modern entity collision queries pass an entity context through `CollisionGetter`/`BlockCollisions`, but the default block collision provider discards it by calling `state.getShape(level,pos)` (`CollisionGetter.java:96-105`, `BlockCollisions.java:93`, `BlockBehaviour.java:327-329`). Thus identical Anvil state and player query box produce the same Anvil obstacle shape at all four inspected releases.

## Limits

This closes only the 1.13-family-to-26.2 Anvil collision-provider dependency. It neither revises the separate 1.12.2-to-1.13 discovery finding nor proves trajectory parity or uninspected release boundaries.
