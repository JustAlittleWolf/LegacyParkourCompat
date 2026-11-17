# 1.16.3 → 1.16.4 ordinary player source coverage (r1)

Status: **PARTIAL**. This report covers a static exact-source inventory and a bounded ordinary player movement/collision comparison. It is not a pair freeze, runtime claim, or claim about the exact introduction patch within this release interval. No eligible player movement difference was found in the examined client tick and movement providers. One changed projectile path is explicitly excluded as non-player physics; the attached Elytra firework boost branch is identical.

## Source identity and inventory

Both sides are official Mojmap source publications. Canonical roots are `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Their source maps are the `sources` object in each mapping-local hidden `.decompile-provenance.json` (not a root `.sources.sha256` sidecar). Provenance/source-map SHA-256 values are `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` for 1.16.3 and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` for 1.16.4. The bound Mojang metadata inputs identify `1.16.3.json` as SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` and `1.16.4.json` as SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. Mapping artifact identity hashes are `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` and `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, respectively (source-input readiness contract `build/source-inputs-115-116-20261117/README.md`).

I rehashed every listed source file against each exact `sources` map and checked disk Java inventory: 3,516 files on the 1.16.3 side and 3,523 on the 1.16.4 side; no missing files or hash mismatches. The paired inventory has 7 additions, no removals, and 64 same-path SHA changes. The additions are `PlayerEntry.java`, `PlayerSocialManager.java`, `SocialInteractionsPlayerList.java`, `SocialInteractionsScreen.java`, `TextFilter.java`, `TextFilterClient.java`, and `CryptException.java`; these are social/text-filter support and do not introduce movement providers. The changed-file census includes UI/Realms/social changes and networking/server changes. `ClientPacketListener.java` and `FireworkRocketEntity.java` were inspected in the bounded movement slice. `ServerPlayer.java`, `ServerGamePacketListenerImpl.java`, and `ServerLevel.java` are flagged as potentially relevant but not closed here; `EnderDragon.java` is a non-player candidate. Other changed Java bodies also remain outside the bounded review.

## Examined reachable player path and unchanged dependencies

The checked client path includes `LocalPlayer.tick()` / `aiStep()` / `move()`; `Player.tick()` / `aiStep()` / `jumpFromGround()` / `travel(Vec3)`; inherited `LivingEntity.tick()` / `aiStep()` / `travel(Vec3)` and `causeFallDamage`; and `Entity.tick()` / `move()` / `causeFallDamage`. Player overrides were checked in the dispatch chain; the LocalPlayer path is not treated as if it dispatched directly to LivingEntity. The following path/hash entries are manifest-bound and byte-identical across the pair:

| Source path | SHA-256 at both endpoints |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/level/CollisionGetter.java` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |
| `net/minecraft/world/level/BlockGetter.java` | `847fe74a63e7c5a48c028cd40ddbd579ee616370bd3a19b03ff1ab4271175e2b` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `8d9e84cf4fdc62cc1a4fc437f828ddccfcc0a2cc9bddd1fd1f621e178beff59a` |
| `net/minecraft/world/phys/shapes/VoxelShape.java`; `Shapes.java` | `6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde`; `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` |
| `net/minecraft/world/level/block/Block.java`; `net/minecraft/world/level/block/state/BlockBehaviour.java` | `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6`; `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` |
| `net/minecraft/world/level/material/FluidState.java`; `Fluid.java` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d`; `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` |
| `net/minecraft/world/effect/MobEffectInstance.java`; `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180`; `6cc7410e181197a56040682aad1525ac1a110140bf7e99ad598c886f892345a5` |
| `net/minecraft/world/item/ItemStack.java` | `2bb228453c39f9792dc1496802e46220738dbf32855442696baaa0288f791156` |

`LocalPlayer` input and pre-travel decisions, player-specific travel/jump path, base entity movement/collision resolution, direct collision-provider APIs and the listed effects/attribute/equipment dependencies therefore have no Java byte delta in this pair. This observation does not establish all tag/resource membership, block registrations, or neighboring-block/data-driven dependencies.

## Changed candidates and disposition

`net/minecraft/client/multiplayer/ClientPacketListener.java` changed overall (manifest-bound SHA-256 `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` → `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`). The inspected player-motion/correction entry points are unchanged: `handleSetEntityMotion(ClientboundSetEntityMotionPacket)` applies the quantized packet components through `Entity.lerpMotion`; `handleMovePlayer(ClientboundPlayerPositionPacket)` applies relative/absolute coordinates and rotation, raw/absolute position, resets relative velocity components, and acknowledges the teleport; `handleExplosion(ClientboundExplodePacket)` adds the packet knockback vector to the local player's delta movement. Their relevant bodies match at both endpoints. Other `ClientPacketListener` changes are not reported as movement differences.

`net/minecraft/world/entity/projectile/FireworkRocketEntity.java` changed (SHA-256 `a181f955d4dc6d6df1282059281bf3b399886b46739efcee766754724270db21` → `8efc3d56a4320ee15d643fd8e26f2bd3478a7d615b3419976ece4755e8ce6fed`). In `tick()`, the free-rocket branch changes horizontal acceleration from fixed `1.15` to `horizontalCollision ? 1.0 : 1.15`; this alters the projectile's own trajectory after horizontal collision, which is non-player projectile physics and excluded. The attached-Elytra branch, which reads the attached living entity's look and delta movement and writes its added boost, is unchanged. No player movement delta is claimed from this change.

`ServerPlayer.java`, `ServerGamePacketListenerImpl.java`, and `ServerLevel.java` are changed, but server-side player initialization, movement checks/correction senders and their relationship to client position writes are not closed in this report. `EnderDragon.java` is excluded non-player physics. Many changed sources are in UI/Realms/social areas; the full set of 64 changed Java bodies has not been exhaustively dispositioned.

## Remaining gates

- Full tick inventory is not independently retraced. In particular, changed server-side correction/teleport producers and network-dependent player state writers need a paired dependency audit before making a broad “no difference” claim.
- Packaged resources, tags, registrations, attributes/effects/equipment membership, and block/fluid data not bound in Java source maps remain unexamined.
- Neighbor-dependent collision/provider behavior and all changed source bodies have not been exhaustively closed; no exact patch-onset boundary is established.
- No runtime or historical-playback parity was examined.

Next action: independent blind audit the bounded source identity, inventory census, call-graph references, selected manifest-bound hashes, ClientPacketListener handlers and FireworkRocketEntity disposition. Preserve **PARTIAL** and resource/dependency gates regardless of that bounded audit.


