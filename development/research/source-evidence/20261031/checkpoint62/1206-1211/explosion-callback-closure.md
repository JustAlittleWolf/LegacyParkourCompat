# Explosion callback movement-dependency closure: 1.20.6 → 1.21 → 1.21.1

Source-only Mojmap comparison of common explosion callbacks and the local-player explosion response. This reconciles whether changed server block callbacks imply a changed historical client movement response; it is not a pair-wide freeze.

## Exact source identity

Read-only source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap`.

- 1.20.6 `mojmap.ready.json`: client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- 1.21 `.decompile-provenance.json` SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`; embedded source map pins the client SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and client mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`.
- 1.21.1 `mojmap.ready.json`: client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.

Every listed Java file below was rehashed locally and matched its endpoint manifest or 1.21 embedded source map. The 1.21 and 1.21.1 hashes are equal for every listed path.

| Mojmap file | 1.20.6 SHA-256 | 1.21 and 1.21.1 SHA-256 |
|---|---|---|
| `world/level/Explosion.java` | `cda91b8011632ef09e04ee18367b0291242f085c0f2beec0484b652ffa16f564` | `2e8ae9259c5a24b13c840c6437583a61874877a6880ffdf62e9d5b32a822f5e1` |
| `world/level/Level.java` | `1566ba1cf48b91885c3dedfc4a05bf675c31b908244ab2c6a45a4007127cb836` | `f698b412b7e849f5076ae268f7819247b623afc905b8d59de9eb60b414e11e96` |
| `world/level/block/state/BlockBehaviour.java` | `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b` | `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` |
| `world/level/block/AbstractCandleBlock.java` | `0dcb47c17933488b82c62ebb3bf7530d87c7a7b95a6f50d307e78bf0d3f8935e` | `54adc95f55a368bab671922c1d75895ff79b10b597e35d9a2f0d1a9fc5fe04dc` |
| `world/level/block/BellBlock.java` | `78ace78be26778806ad5a03a5af84bd7502a0a3fb67e80870aefc220eae347f8` | `22675e9eefddbb2af38d37b5887a7da7a77d7a9fdb06bb494f6ff60dc28ddef9` |
| `world/level/block/ButtonBlock.java` | `5eac6cc9a22e01a5a6d5645a4638682c3932450989fe4738ce22c1b708c00016` | `eaad624e44883f5f64bacd5784a4b0f4c055254930e0ae899d767a0f3c65d2bf` |
| `world/level/block/DoorBlock.java` | `f501a9627aa0a0f67cb883e67f4c073ec1b58e2f4423e9d89b1f6de8b415cedf` | `18e0843f4d24cae34831f59f48b48acaaed9bb358e7cd5f18eb633acf35f76c3` |
| `world/level/block/FenceGateBlock.java` | `4606da81cd18bc1b73ad1d6af335823a86e4e2ee6f45c8e39a99f5a295c0f5fd` | `4e0e93a248c0cb3ef9d125c5cf1564baff2184ebd0f39ba1c8530c9cb7ae9b4b` |
| `world/level/block/LeverBlock.java` | `14444fbdc4f2879759a1d7e4e4b0fc8f6647e81978458cb374c79f089a9736ad` | `3341aaa23f4bdd07489a0ec76a0bdbe8a40ff36879ab6a5725a3d45338ad7f43` |
| `world/level/block/TrapDoorBlock.java` | `13ab02b1ef41576b1ba2e0b228d4ecebf4a9c2308c2be6f2485dde4e0d6eaabc` | `91dacbb5c9b33b5642266e3b9d3a402153237131a58554511eeb93699eff3d6f` |
| `server/level/ServerLevel.java` | `47f08ecd3399d43a9f18c35c31bac40c1e8ef73ba6ec968a4273b6e77ac7a1cd` | `a87ef1125c53a0ada419f583d17e841402235ca569994220ec8a63baa5457181` |
| `network/protocol/game/ClientboundExplodePacket.java` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` | same `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` |
| `client/multiplayer/ClientPacketListener.java` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| `world/entity/EntityType.java` | `5de729295e0d4821db6e88272889291254b14e843baf82a3c2c7ec1588bb7c01` | `23aa8ff6462783b83a09f058381a0ebeb8afb066898806535e850e1f81ca1d0e` |

## Changed common-block explosion callbacks

In 1.20.6, the common `AbstractCandleBlock`, `BellBlock`, `ButtonBlock`, `DoorBlock`, `FenceGateBlock`, `LeverBlock`, and `TrapDoorBlock` callbacks gate behavior on `Explosion.BlockInteraction.TRIGGER_BLOCK` and `!level.isClientSide()`. In 1.21, each uses `Explosion.canTriggerBlocks()` in that position. `DoorBlock` and `TrapDoorBlock` also retain their `type.canOpenByWindCharge()` checks; powered/open/half/lit checks and each state-change action remain in the same order. Those seven class bodies are identical from 1.21 to 1.21.1. `BlockBehaviour.onExplosionHit` retains the same exclusion for `TRIGGER_BLOCK` before default block destruction/drop behavior.

| Existing block callback | 1.20.6 source | 1.21/1.21.1 source | Action after the gate |
|---|---|---|---|
| Abstract candle | `AbstractCandleBlock.java:99-104` | same lines | Extinguishes when lit |
| Bell | `BellBlock.java:224-229` | same lines | Attempts to ring |
| Button | `ButtonBlock.java:121-126` | `:119-124` | Presses if not powered |
| Door | `DoorBlock.java:105-114` | same lines | Toggles eligible lower door half if not powered |
| Fence gate | `FenceGateBlock.java:164-180` | same lines | Toggles `OPEN` if not powered |
| Lever | `LeverBlock.java:108-113` | `:106-111` | Pulls lever |
| Trapdoor | `TrapDoorBlock.java:114-120` | same lines | Toggles eligible trapdoor if not powered |

The helper is new at 1.21 `world/level/Explosion.java:409-417`; 1.20.6 `Level.ExplosionInteraction.BLOW` maps to `Explosion.BlockInteraction.TRIGGER_BLOCK` at `world/level/Level.java:575-586`, while 1.21/1.21.1 rename the interaction to `TRIGGER` and map it to the same block-interaction value at `Level.java:580-589`. The added Breeze source test is at 1.21 `Explosion.java:410-414`. The Breeze wind-charge entity registration is feature-gated by `FeatureFlags.UPDATE_1_21` in the 1.20.6 source (`world/entity/EntityType.java:239-247`) and is ungated in 1.21 (`:228-235`), which is why the changed source-specific branch is classified as modern-only here.

For a server-side explosion whose source is not `EntityType.BREEZE_WIND_CHARGE`, `canTriggerBlocks()` is equivalent to the old pair `blockInteraction == TRIGGER_BLOCK && !level.isClientSide()`. Its only extra branch is for a Breeze wind-charge source: if that source type matches, it requires `RULE_MOBGRIEFING`. `BREEZE_WIND_CHARGE` is registered in the 1.20.6 Java tree but requires `FeatureFlags.UPDATE_1_21`; it is a modern-only source for this historical pair. Thus the changed branch only changes whether these block callbacks toggle/extinguish/ring/press a block for an excluded modern-only Breeze wind-charge explosion when mob griefing is disabled. This is a server/world block-state producer difference, not a player movement writer.

The block callback action bodies only change block state and related block/game-event/sound behavior. They do not write player position, velocity, on-ground state, pose, or abilities. Some toggled block states (for example gate/door/trapdoor open state) have collision shapes; their collision provider is a separate same-state input. For equal valid client block-state inputs the local collision query sees the same state and provider. A changed world-side production of the state is not conflated with a changed client movement response.

## Explosion knockback and client response

`ClientboundExplodePacket.java` is byte-identical across all three sources. It stores the block list, block-interaction enum, and knockback components; the latter are floats, zeroed when the optional knockback is null. `ServerLevel.explode` sends the per-player `Explosion.getHitPlayers()` vector with that packet. The specific producer's other explosion/attack/damage calculations are not needed for equal-valid-raw-packet comparison.

`ClientPacketListener.handleExplosion` (1.20.6 lines 1167–1184; 1.21 and 1.21.1 lines 1172–1189) constructs a client `Explosion` with `source=null`, passes packet state, calls `finalizeExplosion(true)`, then adds the packet's three knockback floats to the current player's delta movement. This direct LocalPlayer response is eligible movement behavior, independently of any excluded damage calculation. The relevant addition and order are unchanged across both boundaries. `Explosion.finalizeExplosion` calls block-state callbacks when the interaction is not KEEP (1.20.6 `Explosion.java:313-319`; 1.21/1.21.1 `:313-319`), but their respective source-side gates both reject client execution: old source checks `!level.isClientSide()`, and new `Explosion.canTriggerBlocks()` does the same. Therefore a raw explosion packet with TRIGGER_BLOCK does not make these common callbacks mutate client block state in either version. Server-authored resulting block-state updates are separate external world inputs.


The local-player explosion response does not use an `isControlledByLocalInstance` guard; it directly adds the packet knockback to `minecraft.player`. No gate exclusion has been assumed. The equal raw packet/state assumption makes the direct input and arithmetic directly comparable. No claim is made that the server's knockback producer or trajectory is identical beyond the examined packet route.

## Paired disposition

| Slice | 1.20.6 → 1.21 | 1.21 → 1.21.1 | Result |
|---|---|---|---|
| Common-block `onExplosionHit` receiver gates | Refactored to `canTriggerBlocks`; only source-sensitive mob-griefing branch for Breeze wind charge | Byte-identical | No in-scope player movement delta; modern-only world-state production difference noted |
| Block callback state writes | Same movement-independent callback action bodies after gate; no direct player writes | Byte-identical | Equal block-state inputs feed unchanged per-state shape queries |
| Explosion packet shape and fields | Byte-identical packet class | Byte-identical | Equal raw packet remains valid paired input |
| Local player knockback response | Same `handleExplosion` delta-movement addition/order | Client listener byte-identical | Direct eligible movement response checked; no difference |
| Server explosion producer and packet construction | `ServerLevel` file changes elsewhere; examined packet emission still sends per-player hit vector + block list/interaction | Byte-identical server source | Producer/trajectory not claimed; same raw packet is sufficient for receiver comparison |

**Disposition:** The changed `canTriggerBlocks` check is an adjacent modern-only Breeze explosion/block-state production rule. It does not change an eligible historical LocalPlayer movement response under equal valid client block states and equal explosion packet inputs. The direct explosion knockback response is eligible and source-equal. This closes only this explosion slice; it does not close unrelated block events, environmental callbacks, other packet handlers, or the full pair inventory. No tests, builds, launches, source regeneration, or network access were used.
