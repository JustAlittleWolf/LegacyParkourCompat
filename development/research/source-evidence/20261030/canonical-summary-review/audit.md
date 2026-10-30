# Canonical summary review

Reviewed at product-checkout commit `5a70ff09d0e4db41b4c116b0a1af79660b3715ac`. Review is limited to the three named permanent findings and the accepted source-review artifacts listed below. Canonical decompiled sources were read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Verdicts

- **Accept** `development/research/findings/1.13.2--1.14.4/eye-submersion-context-and-chunk-gate.md` (SHA-256 `850eb5a77e1e81d193bc0285b9a2da721c4ef323be2a4f0d149de1ad5415de1b`). The contextual height rule and float addition match the exact sources. The finding correctly removes the invalid absent-adjacent-chunk witness and says the client `isChunkLoaded` override returns true, so the refresh's `true` argument does not gate on client residency. The 1.14 symbol is cited as `isInFluid` in Yarn evidence where applicable; 1.14.4's two-argument alias and wrapper are correctly described. The swimming-state consequence follows the cited refresh/update order.
- **Accept** `development/research/findings/1.12.2--1.13.2/elytra-glide-math.md` (SHA-256 `633c1f796fcb7106c3ef529878bcbfd8d2db84523fbe368d313c75d2acef5953`). The two expressions, float lift narrowing, double operations, operation order, one-ULP witness values, and narrow movement consequence match the exact-source verdict and corrections. The finding explicitly limits the numeric witness to a static acceleration example and excludes trajectory/runtime parity claims.
- **Accept** `development/research/findings/1.12.2--1.13.2/moving-fence-edge-collision-scan.md` (SHA-256 `fb376fd239b532fc48218cfe63c2dada2cb30b8c9741f91d35673b4103b014b9`). The old scan visits the min-X/min-Y face cell with interior Z; the newer two-axis-face predicate prunes it before state/shape inspection. The moved fence dimensions and swept-box witness agree with the bounded closure. The finding's 1.13 and 1.13.1 boundary claims are supported by the exact source slices.

No prose correction is required. These are individual source-operation findings; this review makes no full-pair coverage, implementation, or runtime-parity determination.

## Canonical Java source hashes

Every hash below was recomputed from the named canonical file during this review.

### Eye submersion

| Release / mapping | Relative source path | SHA-256 |
|---|---|---|
| 1.13.2 / Ornithe Feather | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 / Ornithe Feather | `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| 1.14 / Yarn | `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` |
| 1.14 / Yarn | `net/minecraft/fluid/BaseFluid.java` | `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` |
| 1.14 / Yarn | `net/minecraft/client/world/ClientWorld.java` | `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` |
| 1.14.4 / Ornithe Feather | `net/minecraft/entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 / Ornithe Feather | `net/minecraft/fluid/FlowingFluid.java` | `6afb033d18e6d28ff6d7deda88f5f1432914903b0fef100c44fb8372d3e148ce` |
| 1.14.4 / Ornithe Feather | `net/minecraft/client/world/ClientWorld.java` | `c4674cc7c6b322bef6a5a951aa73be2b405de4bc64804fb9b6bb96c1832b1768` |

### Elytra glide math

| Release / mapping | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 / Ornithe Feather | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13.2 / Ornithe Feather | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |

### Moving fence edge scan

| Release / mapping | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 / Ornithe Feather | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 / Ornithe Feather | `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| 1.12.2 / Ornithe Feather | `net/minecraft/block/FenceBlock.java` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| 1.13 / Ornithe Feather | `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13.1 / Ornithe Feather | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 / Ornithe Feather | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 / Ornithe Feather | `net/minecraft/block/entity/MovingBlockEntity.java` | `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` |
| 1.13.2 / Ornithe Feather | `net/minecraft/block/FenceBlock.java` | `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` |
| 1.13.2 / Ornithe Feather | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |

The corrected 1.13.1/1.13.2 `WorldView.java` hash is the value in correction 001; the earlier closure's transcription error is not reproduced here.

## Bound input hashes

The following reviewed source evidence artifacts were hash-checked in the working tree:

- Eye query audit `f81c0f9f04e9d6c669b28c7ed35632f237b56594fc1a7229f5888afbc50338df`; correction 1 `f9f028bec16e1a2d947726734d6aa328ab8d021fdb6f900dc5cb59a47ad70319`; correction 2 `77a05378a678d77adc33e940a02248271a45c861ee954c11f0fb12bdb796eec8`; independent client-gate audit `02663167e2d840f14a252820d61130635e95a1544aec6ee5bfeb7eef62830b5c`.
- Glide operation verdict `d879bb85d5bc0a63cfef01debc64ff37b3935b864149d6b8e2be41a0d70521de`; extension and corrections 1–3 `55c4700afef47ed9426f587de41e6d169ca8c9ec11ea71d46c93b3ecd841bea0`, `bf50b8ff9727bf990f6c14a6dd9c8bdcc054c976320dda52d723bca8bd691e21`, `1aea484b1afe839143a07c9d525b1a159352f8b5ebd9a41924df3d9a95e3c351`, `0b331bb0af10f52c1bfcacfec16adc80d3030595c89f1ba60a5b3ce5475168ba`; alias audit `2402d7193d836a81a7506e976d1e8e3b5ca62ca1b5880209f240e9135c7a522e`.
- Fence closure `df144e443feaa6d13d67b5915e396422fcd4e73777cd35f8e503fc55975ffb01`; correction 001 `08f1eb5513c31ff65f714e203155be6b1ca6c21994518806aa31fc63c81896e9`.

No product source, product diff/history, wiki, backlog, build, test, game/runtime, decompilation, or canonical source write was used. The only created artifact is this bounded review record.
