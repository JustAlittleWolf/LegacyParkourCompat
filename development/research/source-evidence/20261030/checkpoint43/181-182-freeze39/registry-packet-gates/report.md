# Residual source coverage: registrations and packet state gates

Pair: Minecraft 1.8.1 → 1.8.2; Feather `feather-gen2` `1.8.1+build.2` → `1.8.2+build.2`; source roots `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8.1,1.8.2}\ornithe-feather`. Read-only source review. No product implementation/history, wiki, backlog, tests, builds, launches, or decompilation used.

## Evidence identity and hashes

Reused the previous source-only audit's provenance identity: pipeline `lpc-decompile-v1` with `threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`, Eclipse Adoptium 25.0.3+9 on Windows 11 amd64. Client SHA-256: 1.8.1 `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`; 1.8.2 `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`. Mapping artifact hashes: 1.8.1 `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7`; 1.8.2 `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37`. Prior independent source-tree audit states every Java source matched its `.decompile-provenance.json` manifest (1.8.1: 2,500/2,500; 1.8.2: 1,612/1,612); these target source hashes therefore inherit that verified manifest status. This pass recomputed hashes for the core paired files:

| Relative source path | 1.8.1 SHA-256 | 1.8.2 SHA-256 |
|---|---|---|
| `net/minecraft/block/Blocks.java` | `af42dc7d1fe95a3cc070427e32cd76213d6eab4df49118fee99aa3c7a3a004b0` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| `net/minecraft/item/Items.java` | `d1b14bcd6bfc6a62f75b9aedc8fe386489a1d1b001faadd1110b49c3429c51b1` | `01e99b0bc18922292e0123c4a7ed6de6554f1ae08064fc0e897d83cce1d5ea10` |
| `net/minecraft/entity/Entities.java` | `26e37b84cead6a3f39cc999f09a010e21f90796714eabf804fb8309df81796a5` | `3b50cb33c8781aefe96a04858fb666a79b990c6ce2f16190cd3a07151d44e7d5` |
| `net/minecraft/entity/living/attribute/EntityAttributes.java` | `b8926d7c46d56d643d8c2a2ff318bc40bd7bb2b2e7dfe8d8faaae5611cb13c09` | `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa` |
| `net/minecraft/entity/living/effect/StatusEffect.java` | `6a555b196758c5906ffa854dec5a050757f6223785c9990718ed67148ba7d4f4` | `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3` |
| `net/minecraft/entity/living/effect/StatusEffectInstance.java` | `1a36f6c852b036bb4cab7f8d114fb391a4b76eb2fc2b977c07d57309b1723666` | `6b69d6232532247f004c0f493ab619e02651d383c3c766bbc6e9c9ea8913e34e` |
| `net/minecraft/enchantment/Enchantment.java` | `4c7d8fc2d9bd47aa4f963b5267299d9599cd6b6af737bd85a523eb598e406481` | `f42e778392eddd8a4499b3ae074b059ad0984dcf4a1d775e5933914ae854dd74` |
| `net/minecraft/enchantment/EnchantmentHelper.java` | `5a1b9c99d508c0175256b90c66fcf60b01aa7b607c574bcb6c453de00d8507a7` | `16f46386c3e8fe19d7720d8e631111e82eb3f7260449bb867b401a24d73c244e` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65fe3f74154996a5b94a9d5534a4805b40e13be245d50178b838ce0e79802118` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |

Hashes differ for all large registries/handlers; differing hashes alone are not semantic deltas. This pass checked the named movement-related field/provider values and relevant method paths, not every unrelated registration statement in those large files.

## Checked slices: no movement-semantic delta found

- **Static block/item/entity and attribute registration:** paired `Blocks.java`, `Items.java`, `Entities.java`, and `EntityAttributes.java` inspected for movement-provider/attribute registrations and targeted entries. `EntityAttributes.MOVEMENT_SPEED` remains a trackable `RangedEntityAttribute` with base `0.7F`, minimum `0.0`, unbounded maximum. `LivingEntity.initAttributes` registers max health, knockback resistance, movement speed; `PlayerEntity` sets movement-speed base to `0.1F` on both sides. No relevant registration/value change found in the checked entries. `Blocks`/`Items` broader unselected rows remain partial.
- **Effects and movement modifiers:** `StatusEffect` and `StatusEffectInstance` movement-relevant fields/paths checked with `LivingEntity` jump, speed, effect add/remove, tick/apply and `ModifiableEntityAttributeInstance.computeValue` from prior coverage. Speed modifier remains `+0.2F`, operation 2; slowness remains `-0.15F`, operation 2; jump boost remains the same registered effect and jump addition (`(amplifier + 1) * 0.1F`). Modifier operation ordering, duration/amplifier state and recomputation gates show no movement-semantic delta. Observed diffs in these classes are generic annotations, local-variable renames and decompiler formatting around equivalent expressions.
- **Enchantment/equipment source:** paired `Enchantment.DEPTH_STRIDER` registration remains ID 8, `DepthStriderEnchantment`, feet armor category, max level 3; `EnchantmentHelper.getDepthStriderLevel` still obtains highest level from entity equipment. Living/player movement consumer was already paired in prior scan: depth-strider adjustment remains in the water movement calculation with the same constants/order. No changed movement modifier or depth-strider provider was identified in inspected entries. Complete armor/equipment item modifier provider enumeration is not closed here.
- **Status effect packet sinks:** paired client handler `handleEntityStatusEffect` constructs the instance from payload effect/duration/amplifier/particles, sets permanent flag, then invokes `LivingEntity.addStatusEffect`; `handleEntityRemoveStatusEffect` invokes `removeEffect`. Same state route/order in the paired methods. Payload is applied only to a resolved `LivingEntity` after `PacketUtils.ensureOnSameThread`.
- **Attribute packet sink:** paired `handleEntityAttributes` resolves the entity; throws if it is non-living; for each payload entry resolves/registers the attribute, writes base value, clears modifiers, then adds each payload modifier. Movement speed is trackable in `EntityAttributes` and thus eligible for this replicated state path. No changed ordering/write found in the checked paired method.
- **Other client state sinks enumerated in this pass:** paired handlers for entity velocity (`lerpVelocity(packet components / 8000.0)`), entity metadata (`SyncedData.update`), player movement correction (relative coordinate flags add current coordinate; absolute coordinate branch zeroes that velocity component; then position/angles write), player abilities (flying/creative/invulnerable/canFly and fly/walk speed setters), and entity equipment (`setEquipment(slot,item)`) inspected. These handlers reach state used by movement; no changed write/order/gate found. Entity removal/status effects are similarly client-applied.
- **Packet shapes:** packet source classes for status-effect, remove-effect, entity-attributes and velocity were present in the paired trees. Payload getter/handler paths line up as used above. Full protocol encoder/decoder round-trip and every packet state update are not claimed closed.

## Applicability and dependency closure

All inspected receiver handlers are client-side and update the local client world/player or tracked living entities; they are reachable state inputs to subsequent movement. That does not imply historical physics on either logical server. Server-side effect/attribute/equipment production and world-state changes remain authoritative vanilla state and are not to be replayed as historical movement under project invariants. Any world-state production that happens only on logical servers is excluded from historical movement; direct client player responses after receiving server state remain in scope.

No resource or tag absence claim is made: exact packaged resource inputs were not assigned/provided. No new source finding is justified by the paired slices examined.

## Partial / unexamined

- `Blocks.java`, `Items.java`, `Entities.java`: only movement-related registration/provider members were targeted; complete registration-row inventory and all static initialization dependencies not audited.
- Item/armor equipment modifier providers and full equipment-change production/replication closure (including server constructors/senders) not fully enumerated.
- Server-side producers/senders for status-effect add/remove, entity-attribute changes and all other player-state packets were not compared end-to-end. Current evidence closes the named client sinks/payload usage only, not server mutation → packet serialization → client sink.
- Packet codecs/wire serialization for each state packet and indirect metadata providers not exhaustively compared.
- Existing pair gaps from prior audit remain open: complete block provider/shape inventory, liquid scans/state changes, full dispatcher and all direct player-response paths. This assignment report does not freeze the 1.8.1–1.8.2 pair.

## Next action

Continue exact paired closure from server-side state mutation to packet constructor/codec and client write for status effects, attributes, equipment, abilities, velocity and correction packets; then finish the remaining movement-relevant `Items`/armor modifier and registered block-provider inventories. Carry these terminal slices to the independent full-pair coverage audit; current result is partial with no accepted delta.
