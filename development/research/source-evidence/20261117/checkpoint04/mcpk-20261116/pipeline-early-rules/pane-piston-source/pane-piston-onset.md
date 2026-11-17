# Pane and piston collision provider source packet

Scope: exact decompiled 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, and 1.9.4 only; `ornithe-feather` source trees. Source discovery only; no acceptance verdict is asserted.

## Checked onset

- Pane connection-provider geometry: absent in 1.8.9 and present by 1.9; the same provider implementation remains in 1.9.1–1.9.4. First checked stable release with the already accepted pane geometry behavior: **1.9**. This packet reports onset only and does not restate that claim.
- Piston-head collision geometry: differs between 1.8.9 and 1.9 and remains in the 1.9 implementation through 1.9.4. First checked stable release with the changed provider: **1.9**. No finer onset within 1.8.9–1.9 is asserted.

## Piston provider and movement binding

- 1.8.9 `net/minecraft/block/PistonHeadBlock.java:87-118`: collision generation writes facing-specific head bounds, delegates to `Block.addCollisions`, writes facing-specific arm bounds, delegates again, then resets the block bounds to a full unit cube. The arm stays inside the cell (NORTH z=0.25..1.0; DOWN y=0.25..1.0).
- 1.9+ `PistonHeadBlock.java:24-84`: `getShape` selects the facing-specific head `Box`; `addCollisions` submits that state shape first, then `getArmShape`. The arm boxes extend 0.25 beyond the block cell opposite the facing (NORTH z=0.25..1.25; DOWN y=0.25..1.25). `FACING` selects the geometry; `TYPE` and `SHORT` do not select geometry in these methods. This provider does not mutate/reset the block bounds.
- In 1.8.9 `World.getCollisions(Entity, Box)` enumerates block positions and calls `blockState.getBlock().addCollisions(...)` (`World.java:891-921`). In 1.9+, it calls `blockState.addCollisions(...)` (`World.java:893-927`); `BlockProperties` declares this operation and `StateDefinition`'s state implementation forwards it to `block.addCollisions(...)`.
- `Entity.move(dx,dy,dz)` obtains collisions for the expanded entity box, then clips requested Y, X, and Z (`Entity.java:371-456` in 1.8.9; `441-529` in 1.9.4). `LivingEntity` invokes inherited `Entity.move` in its movement routine, and `PlayerEntity` extends `LivingEntity`, establishing ordinary player reachability. This is source reachability only, not a predicted trajectory or runtime parity result.

## Immutable source identities

All six source trees use the `ornithe-feather` mapping namespace and Feather Gen2 build.2 artifacts for their exact named releases. The 1.8.9 and 1.9.4 ready markers bind their root source sidecars. Versions 1.9–1.9.3 use mapping-local hidden `.decompile-provenance.json`; every relevant Java file hash below matches its `sources` entry. The read-only identity locator did not list these exact 1.9-era rows, so I bound identity directly to extant hidden provenance and source hashes without regenerating sources.

| Release | Provenance file SHA-256 | Source hash binding |
|---|---|---|
| 1.8.9 | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | source sidecar `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; ready marker agrees |
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | hidden provenance `sources` map |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | hidden provenance `sources` map |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | hidden provenance `sources` map |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | hidden provenance `sources` map |
| 1.9.4 | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | source sidecar `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; ready marker agrees |

Each manifest pins its exact client, Feather mapping artifact, and tool inputs. For example, 1.9 provenance pins client SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` and merged Feather JAR SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`.

### Per-file SHA-256

Each digest was checked against its manifest binding and local file bytes. A release group means every version named in that cell has that identical file digest.

| Mapping-relative source file | Release(s) | SHA-256 |
|---|---|---|
| `net/minecraft/block/PaneBlock.java` | 1.8.9 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
|  | 1.9, 1.9.1, 1.9.2 | `6c522c4c32af6bc4f34ebf595e24190fcd93c974cacf8c5ab2b24678a78265e4` |
|  | 1.9.3, 1.9.4 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| `net/minecraft/block/PistonHeadBlock.java` | 1.8.9 | `c5515744ce27521195ec802e2bd89ae5327448302542436576bcacf19f245fa4` |
|  | 1.9, 1.9.1, 1.9.2 | `c7dbe83aaa82d46c66db8a29cbb0c395e2ab25d6cb8a957bae1c844365a321ba` |
|  | 1.9.3, 1.9.4 | `9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579` |
| `net/minecraft/block/Block.java` | 1.8.9 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
|  | 1.9, 1.9.1, 1.9.2 | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
|  | 1.9.3, 1.9.4 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| `net/minecraft/block/state/StateDefinition.java` | 1.8.9 | `9449797527f8314614c514321e180c52608db499054dc3ffca897c5cc996cb50` |
|  | 1.9, 1.9.1, 1.9.2 | `6b7e70156f9f834dd3129d02f0a24ce78a91a8bf926ac0cb2aae06955799c0e3` |
|  | 1.9.3, 1.9.4 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |
| `net/minecraft/block/state/BlockProperties.java` | 1.9, 1.9.1, 1.9.2 | `195452bb3239106199206191a4f9aa67ac66412d5f433e5bcf602edfcfda14d3` |
|  | 1.9.3, 1.9.4 | `6df37a30460953981bed2977e2baf9984559f7c64a8a552273686bd0e2f7f01b` |
| `net/minecraft/world/World.java` | 1.8.9 | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
|  | 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
|  | 1.9.1, 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
|  | 1.9.3, 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| `net/minecraft/entity/Entity.java` | 1.8.9 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
|  | 1.9, 1.9.1, 1.9.2 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
|  | 1.9.3, 1.9.4 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | 1.8.9 | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
|  | 1.9 | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
|  | 1.9.1, 1.9.2 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
|  | 1.9.3, 1.9.4 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |

No tests, launches, source regeneration, product inspection, or acceptance/backlog edits were performed.
