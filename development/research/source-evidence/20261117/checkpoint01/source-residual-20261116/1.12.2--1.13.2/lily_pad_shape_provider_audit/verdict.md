# Independent source audit — waterlily / lily pad provider

## Verdict

**ACCEPT for the bounded player collision-shape comparison.** This verdict is bound to report SHA-256 `9CC43882955BDE4A8705681F2BBC24A37CE736729EA9846D67AB3AADD01B6B2D`. The two endpoints supply the same collision bounds for a non-boat local player. The 1.12.2 boat exclusion is a separate vehicle response and is outside this player-only slice.

This is not pair freeze, complete query/resolver parity, support-state/history equivalence, intermediate cutover evidence, or runtime parity.

## Identity and source integrity

- 1.12.2 Ornithe Feather ready marker is bound to source sidecar SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the on-disk sidecar matches.
- 1.13.2 Ornithe Feather ready marker is bound to source sidecar SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the on-disk sidecar matches.
- All 12 Java source hashes listed in the audited report match their endpoint sidecars.
- The report's player-query sentence relies on the legacy `BlockState` dispatch implementation. I verified the exact supplemental sources, also pinned to the 1.12.2 sidecar: `BlockState.java` SHA-256 `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb`; `BlockProperties.java` SHA-256 `6c311b6990c949c61872cdd3f94ab5e33f39c252467662aa667b54716bd48c16`; `StateDefinition.java` SHA-256 `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37`.

## Claim checks

### Mapping and registrations

1.12.2 `Block.java:999` registers numeric block 111 under `waterlily` with `LilyPadBlock`. In 1.13.2 `Block.java:1208` registers `lily_pad` with the same block class, and `Blocks.java:881` resolves the `LILY_PAD` field to that key. `C_80754635.m_11856336` stores the first state string as the converted result and associates each following old state with that entry; `m_11299400` returns that stored result for an indexed input. Its line 4577 input/output pair therefore proves `waterlily` → `lily_pad` rather than relying on the names.

### Geometry and collision route

The old `LilyPadBlock.SHAPE` bounds are `(0.0625, 0, 0.0625)` to `(0.9375, 0.09375, 0.9375)`. The new provider uses `Block.block(1, 0, 1, 15, 1.5, 15)`; `Block.block` divides each component by 16, yielding the same six bounds. Both providers return their static shape without reading state, position, world, fluid, neighbor or entity data.

The old local player reaches `Entity.move` → `World.getCollisions(entity, box)` → `StateDefinition`'s `BlockState.addCollisions` delegate → `LilyPadBlock.addCollisions`. The subclass adds its static collision shape for any non-boat entity. The inherited `PlantBlock.getCollisionShape` returning empty does not erase this route because the world enumerator uses the overridden `addCollisions` method.

The 1.13.2 local player reaches `Entity.move` → `WorldView.getCollisions` → `getBlockCollisions` → `BlockState.getCollisionShape` → inherited `Block.getCollisionShape` → `LilyPadBlock.getShape`. The target registration does not call `noCollision`; `Block.Properties.hasCollision` defaults to true. This path therefore returns the LilyPadBlock voxel shape for a player. It also returns a shape for boats; the old boat-only omission is correctly excluded from this audit's player conclusion.

The 1.13.2 caller-chain source identities for `BlockState`, `WorldView`, `Entity`, and `Block` are included in the audited report's hash table and were revalidated against the target sidecar. The above legacy state-delegate files are the supplemental hashes listed in this verdict.

I also verified that the local-player class chain reaches `Entity.move` without an intervening `move` override in `LocalClientPlayerEntity`, `ClientPlayerEntity`, `PlayerEntity`, or `LivingEntity` at either endpoint. Supplemental exact file hashes (each matched its sidecar):

| Endpoint | Relative source | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |

## Scope limits

The same local collision volume is established for the legacy `waterlily` state converted to 1.13.2 `lily_pad`. Placement/survival support, fluid-driven state production, other providers, general collision query and resolver behavior, and the rest of both release-pair inventories remain open.
