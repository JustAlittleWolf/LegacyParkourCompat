# Independent source verdict: voxel rotation projection

Verdict: **reject the claimed 1.21.10 → 1.21.11 player-collision change**. The coordinate-system rewrite in the reviewed claim does not change occupied cells, crop coordinates, or the DoorBlock collision shapes in this pair. It is a source-level refactor with matching output for the paths reviewed here, not an accepted movement finding.

## Bound reviewed claim

Draft: `source-12110-12111-clip-core/rotated-voxel-shape-coordinate-mapping.md`

Draft SHA-256: `165530233bc4ee562f0e334d12a8f85b449933ba17a6cd82621736582d694547`

The verdict is bound to those exact bytes and the sources listed below.

## Exact source identity and integrity

Both trees are exact-release `mojmap` decompilations from the recorded shared run: Minecraft `1.21.10` client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; Minecraft `1.21.11` client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.


Provenance manifests (SHA-256): 1.21.10 `9a1db4c050c3caa28a1313f89da9eda3b28fea5f8afba42cab798b47d7ad77c`; 1.21.11 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`. Both identify Mojmap, Vineflower 1.12.0, Java 25.0.3+9-LTS and Gradle 9.7.1. Ready markers bind source-manifest SHA-256 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Every listed relevant source-file digest was checked against its version's source manifest.

## Reproduction and comparison

The draft's witness is a `(2,1,1)` discrete grid with only `(0,0,0)` full, rotated by `ROT_90_Y_NEG` / `BLOCK_ROT_Y_90`. In both versions the output grid is `(1,1,2)` and the occupied cell is `(0,0,0)`.

In 1.21.10, the permutation is P321: output axes select input axes `(Z,Y,X)`. The code builds inversion bits for the permuted axes, then selects a bit using the selected output axis. Since the permutation is a bijection, that selector yields the inversion bit for the output axis itself. For this group the X output is inverted and Z is not. X maps from input Z and has output size 1, so `1 - 1 - 0 = 0`; Z maps from input X, so it is also 0.

In 1.21.11, `OctahedralGroup.rotate(Vector3i)` applies the same P321 permutation and then signs by output axes. The transformed size vector is `(-1,1,2)`; `fixupCoordinate` makes its X size 1 with offset 0. The cell vector becomes `(0,0,0)` after the X sign and therefore also fills `(0,0,0)`. This refutes the draft's `(0,0,1)` versus `(0,0,0)` result.

The crop-point transform agrees as well. Both `Shapes.rotate` implementations select coordinate lists by the permutation. For each destination axis, both reverse the list exactly when that destination axis is inverted, and map each point by `destinationPivot ± (sourcePoint - sourcePivot)`. `makeAxis` in 1.21.10 and `flipAxisIfNeeded` in 1.21.11 preserve that order and produce the same values for the block-centered pivots used by `DoorBlock` (and the same affine map for supplied pivots). `BitSetDiscreteVoxelShape.java` is byte-identical across the two trees (SHA-256 `b90b634f814c22c6b43c5b28884cca992ffe30d3e40ff7013734b9a1a7a68f49`); its fill, occupancy and bounds code introduces no compensating or divergent behavior.

`Shapes.rotateHorizontal` in 1.21.10 builds the four directions from `fromXYAngles` and repeated `ROT_90_Y_NEG`; in 1.21.11 it uses the `BLOCK_ROT_Y_90/180/270` aliases and composition. The aliases resolve to the same enum elements used by that older construction. `DoorBlock` builds the same `SHAPES` from `Block.boxZ(16,13,16)` and uses the same facing rule: closed uses FACING; open chooses clockwise/counter-clockwise from HINGE. HALF does not affect `getShape` in either version. Therefore all FACING × OPEN × HINGE-selected shapes match, and both halves select the same shape as each other.

The ordinary player path reaches the same geometry: `Entity.move` → `collide` → `collectColliders` / `CollisionGetter.getBlockCollisions` → `BlockCollisions.computeNext` → `CollisionContext.getCollisionShape` / `BlockState.getCollisionShape` → default `BlockBehaviour.getCollisionShape` → `DoorBlock.getShape`. Those consumers' relevant collision selection/clip operations retain the same behavior; because the DoorBlock input shape is equal, this claimed delta cannot change the collision result or player movement.

## Evidence hashes

| Source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `DiscreteVoxelShape.java` | `ff01971606746fe19bb42a59cb4e866f92a4eb5da8254890eb9e6cd88ad4f5b1` | `2d4913db81c17ed3e59328d9aa32544263a7a795ce0895dece83fc11ecec8862` |
| `Shapes.java` | `03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63b` | `fbe1aa9c92b4806128325ed82a5255aba5158de68ba62eb1afd53e7811b6b40b` |
| `OctahedralGroup.java` | `b1fcafbd354fc45a0e34535e41e6590e99501781a01ee210441c65e5f75d958d` | `ed73f3def7d999d255595144a4534ed0ab9434c4bbeaceb4ca4c563d02dddd5c` |
| `SymmetricGroup3.java` | `7f31d2fe0e139352ed4d0fb6a9271bdb6879c63d4423c2eea88f5cfff1e9ca2a` | `8b39ff92f4f20b9276e863623ab22d9959a68e4bc8c26beaf2d2f679dd4ceac9` |
| `DoorBlock.java` | `68653f513824a8ce7a453a2ee5951d2bc3aa14a74c7fc1494a97244cec05281a` | `5d0b6099e56f04946da7ba269e28e52d616fe8b25138f7da9d831b294f3cb05e` |
| `CollisionGetter.java` | `a48c939704df996854b73292b72671745f5072fab97e7b7a32f59e29d559b4e0` | `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70` |
| `BlockCollisions.java` | `e60f4d3cf7537a5e3b8b3ad099a2a7ec167f972460fa01045fd2b2141b6ddaa0` | `05875bb3a6fa49628ac92174e68ec3b5bed1cf39ebd4e7158b1885aead0a6a68` |
| `Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |

