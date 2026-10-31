# Blind residual collision-provider and tag audit: 1.20.6 → 1.21 → 1.21.1

## Evidence identity

Read-only exact Mojmap sources were inspected under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap`.

- 1.20.6 endpoint readiness: `versionId=1.20.6`, `mapping=mojmap`; `mojmap.ready.json` binds client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, and source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- 1.21 source identity: embedded `.decompile-provenance.json` SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; its inputs bind official client SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and client mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. The embedded `sources` hashes matched each checked local Java file.
- 1.21.1 endpoint readiness: `versionId=1.21.1`, `mapping=mojmap`; `mojmap.ready.json` binds client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, and source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.

Checked Java hashes below matched the endpoint source manifests or the embedded 1.21 source-hash map. The 1.21 and 1.21.1 files listed as equal were byte-identical at all three release roots.

| Source | 1.20.6 SHA-256 | 1.21 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|---|
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b` | `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` | same as 1.21 |
| `net/minecraft/world/level/block/state/BlockState.java` | `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b` | same | same |
| `net/minecraft/world/level/BlockGetter.java` | `d0a307889b2f18c9eab586baa92cf92f39a4df7918b17c631d5354a1e3a4edcb` | same | same |
| `net/minecraft/world/level/BlockCollisions.java` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` | same | same |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `aa5cd1702e6061dd7db791bb4cd4498f39ad7fdfb2d22e7701d278f21c10fbbf` | same | same |
| `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | `fa505d477deaeed144816a3ec08512357ee178dc8769534b3a7f59e9a3299587` | same | same |
| `net/minecraft/tags/BlockTags.java` | `9022f664b7405b5f174facfb17f373074df81da647f50f8cf49ee2b393d94311` | `fa4107a372643ddc1c7fc1aec2a3527cc27565a6b217e3e5d1c383cfb9b51de4` | same as 1.21 |
| `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `a0ab3c2a12f8e93ae7e6231069051712cf0f6fcaf3d06e86caa0f9b2d1af310e` | `819d133e6ee8f82485b3d84fcfcfa48e4ee2824431379b93de48c7dddc7b32f0` | same as 1.21 |
| `net/minecraft/world/level/block/FenceBlock.java` | `4808fc42f7f72378a439017e5441a71869dd185607efb5747165e36eb3c57936` | same | same |
| `net/minecraft/world/level/block/WallBlock.java` | `d0280d8ec95a3262ac97870652fe81d9124b19ca6181266721ddce38378c009e` | same | same |
| `net/minecraft/world/level/block/IronBarsBlock.java` | `9cef95eda0c3121553dd6c714b71799313b381e08dfba981b1deff55a90e9e5f` | same | same |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `4606da81cd18bc1b73ad1d6af335823a86e4e2ee6f45c8e39a99f5a295c0f5fd` | `4e0e93a248c0cb3ef9d125c5cf1564baff2184ebd0f39ba1c8530c9cb7ae9b4b` | same as 1.21 |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `a88203cf88219422fef10adfeab87cfe24df272c015de9de635f2dc7949f049b` | same | same |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `5e91cd1ad22caca5712df3275574699a83bb841ceba0858eb54bfb674d7b7943` | same | same |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | same as 1.21 |

## Collision query and shape-provider dependency closure

The local-player block collision query reaches `BlockCollisions` through the same `BlockGetter` route at all three endpoints. `BlockCollisions`, `BlockGetter`, `BlockState`, `CollisionContext`, and `EntityCollisionContext` are byte-identical across the pair. `BlockBehaviour.BlockStateBase.getCollisionShape` still routes to the block's context-sensitive collision provider; its `Cache` computes the empty-context default shape and retains the same dynamic-shape guard. `BlockBehaviour.java` changes its offset-function representation from `Optional<OffsetFunction>` to nullable `OffsetFunction` in 1.21; the `XZ`/`XYZ` calculations, `getOffset` evaluation and zero fallback, and the collision cache/guard code are unchanged in meaning and order. The differing representation is not a movement candidate.

The common-state providers audited for neighbor-dependent or context-dependent shape behavior were FenceBlock, WallBlock, IronBarsBlock, SnowLayerBlock and ScaffoldingBlock. Their source files are byte-identical across all three versions. FenceBlock, WallBlock and IronBarsBlock continue to use the same adjacent-state and block-tag checks for their connection properties. No registration/tag change found in this slice alters the collision shape of a common block ID.

`BlockTags` adds the `MOB_INTERACTABLE_DOORS` key in 1.21 and changes its helper from `new ResourceLocation(path)` to `ResourceLocation.withDefaultNamespace(path)`. Existing keys retain their string identifiers; the movement-relevant default tag entries checked below are not changed. `VanillaBlockTagsProvider` adds the new tag for copper doors and appends modern Cherry/Bamboo registrations to some existing material tags. The entries for old common block IDs remain the same in the checked movement-relevant tags: `CLIMBABLE`, `SOUL_SPEED_BLOCKS`, `WALL_POST_OVERRIDE`, `FALL_DAMAGE_RESETTING`, `UNSTABLE_BOTTOM_CENTER`, `SNOW_LAYER_CAN_SURVIVE_ON`, and the old stair/slab/fence/wall/door/trapdoor members. The provider source is byte-identical for 1.21 and 1.21.1.

Direct callsite tracing confirms the new `MOB_INTERACTABLE_DOORS` tag is used by `world/entity/ai/behavior/InteractWithDoor.java` for Mob door behavior. No current LocalPlayer / player movement or common-block collision-shape provider reads this tag. Its Mob AI effect is outside the assigned player-physics slice; this does not assert that Mob-produced world-state changes can never affect a later player collision.

## Adjacent source difference: fence-gate explosion callback

`FenceGateBlock.java` differs at `onExplosionHit` only in the callback gate: 1.20.6 requires `Explosion.BlockInteraction.TRIGGER_BLOCK && !level.isClientSide() && !POWERED`, whereas 1.21/1.21.1 uses `Explosion.canTriggerBlocks() && !POWERED`. The subsequent OPEN toggle and sound calls match. This is a world/block explosion-response producer change, not a change to the gate's collision-shape provider or a LocalPlayer-specific direct movement response. It needs reconciliation with the separately owned block-response/external-input slice before any pair-wide no-difference disposition; it is not accepted here as a player-movement finding.

## Slice result and limits

No collision-registration, query-route, common-state shape-provider or movement-tag default difference was accepted from this residual slice. The evidence closes only the listed generic query, context, tag and common provider dependencies. New-only blocks and all provider families beyond those listed were not independently inventoried here. The fence-gate explosion callback and broader block-response/piston/portal inputs remain cross-slice dependencies. This is not a full-pair audit or freeze claim.
