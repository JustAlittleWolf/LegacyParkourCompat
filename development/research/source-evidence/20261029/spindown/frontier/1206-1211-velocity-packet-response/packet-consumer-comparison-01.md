# Direct client velocity-packet consumer comparison

Pair: Minecraft 1.20.6 → 1.21.1, Mojmap. This is a bounded source-body comparison of client-side handling for entity-motion and explosion packets and the local player's immediate tick consumer. It does not cover packet production, server physics, collisions, trajectories, or pair-level movement coverage.

Identity anchor: the release-parent ready markers, provenance records, and SHA-256-verified source manifests documented in [`provenance-gate.md`](provenance-gate.md). The following additional/consumer files were each checked against their manifest entries before reading:

| Source | 1.20.6 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `382f312df18642c921aaee27c9355fde601d6f9b5b52d652ea2a811fc04cedb9` | `3ffa78e79c23460b88a75d02cdecc96b18ebb70f35668530eaf258830455b8ca` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` |
| `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` |

## Received entity-motion packet

`ClientboundSetEntityMotionPacket.handle` dispatches to `ClientGamePacketListener.handleSetEntityMotion` in both releases (packet source lines 54–55). Both packet versions read and write entity ID followed by three signed shorts (`ClientboundSetEntityMotionPacket.java`, lines 18–20 and 40–49); those fields and their wire order are unchanged. In 1.20.6 its `getXa/getYa/getZa` accessors return raw `int` fields (lines 62–72), and `ClientPacketListener.handleSetEntityMotion` divides each by `8000.0` at the call site (`ClientPacketListener.java`, 515–520). In 1.21.1 the accessors return `double` values computed as the same `int / 8000.0` (`ClientboundSetEntityMotionPacket.java`, 62–72), and the listener passes them directly (`ClientPacketListener.java`, 517–522). For equal decoded integer triplets, both paths perform the same three double divisions and pass the same values onward; the scale moved from listener to packet accessors, without an operation-order or precision change in the resulting components.

Both listeners first call `PacketUtils.ensureRunningOnSameThread`, then resolve the packet ID through `this.level.getEntity(id)` and do nothing if no entity is present. A match is not special-cased as local: any tracked entity with that ID receives the update. When that match is the local player, `Entity.lerpMotion` delegates directly to `setDeltaMovement(x,y,z)` in both versions (`Entity.java`, 1.20.6 lines 2096–98; 1.21.1 lines 2162–64). The setter replaces the complete current vector (`Entity.java`, 3185–95 / 3224–34); the packet path does not add to the old velocity or explicitly reset it first.

## Received explosion packet

The explosion packet source is byte-for-byte hash-identical in both manifests. Its handler dispatches to `handleExplosion`; the listener verifies main-thread execution, builds/finalizes the visual/block explosion, then sets the local player's vector to `currentDelta.add(knockbackX, knockbackY, knockbackZ)` (`ClientPacketListener.java`, 1.20.6 lines 1167–84; 1.21.1 lines 1172–89). The three received knockback components are floats in the packet in both releases. This path always targets `this.minecraft.player`; unlike entity-motion packets, it does not resolve an entity ID. It adds to the current vector, after `finalizeExplosion(true)`, with no packet-handler zeroing or replacement. The packet and handler code are unchanged across this pair.

## Immediate local-player tick consumer

In both releases `LocalPlayer.tick` skips its `super.tick()` call when the player's current chunk is unavailable; otherwise the superclass chain reaches `LivingEntity.tick`, which calls `aiStep()` while the entity is not removed. `LocalPlayer.aiStep` calls its superclass path, which reaches `LivingEntity.aiStep`; that method reads the current delta vector, zeros each component whose absolute value is strictly less than `0.003`, writes the cleaned vector, then reaches `travel` later in the same AI step. References: `LocalPlayer.java` 1.20.6 lines 190–92, 648, 807; 1.21.1 lines 191–93, 644, 804. `LivingEntity.java` 1.20.6 lines 2336–77, 2591–2629, 2680–84; 1.21.1 lines 2319–60, 2586–2624, 2675–79. The threshold comparisons, component order (x, y, z), zero values, setter, and `travel` call site match. `LivingEntity.aiStep` also has the same conditional `0.98` delta scaling for `!isEffectiveAi()` before the threshold cleanup in each version (2604–06 / 2599–2601); the result depends on that runtime gate.

Thus, equal received motion integers produce an equal replacement vector at the handler boundary, and equal explosion floats plus equal prior vectors produce an equal added vector. The shared tick gate/cleanup/call sequence consumes those vectors in the same immediate order, subject to the same state gates. Later branch-specific `travel` behavior and whole-tick movement are not claimed equal by this packet-consumer comparison.

## Input-pin check and limitation

A targeted search of `.native-artifacts` for alternate exact-version pins of the two client JARs, metadata JSONs, Mojmap JARs, and mappings text found no matches. The release artifact manifests therefore remain the available declared input pins; their recorded bytes are unavailable for recomputation at the cache path identified in `provenance-gate.md`. As already recorded there, mapping-root embedded provenance is absent. This limitation is retained; source conclusions above are bound to the release-parent source-manifest digests and the individually matched source hashes. No runtime or trajectory/parity claim follows.
