# Ordinary Nether respawn-transfer applicability audit

Status: COMPLETED

## Verdict

The bounded applicability claim is supported for the observable local-client dimension-key pair `{minecraft:overworld, minecraft:the_nether}` in either direction, with 26.2 `KEEP_ENTITY_DATA` bit 2 set. The ordinary Nether route enters the dimension-change respawn path; sampled historical clients replace the local player without restoring its velocity. In 26.2, the respawn handler copies the old velocity under bit 2, then the following portal correction applies a zero delta relatively.

A trajectory statement in the input report needs qualification: 26.2 `Relative.DELTA` includes `ROTATE_DELTA` (`Relative.java:19,23`). `NetherPortalBlock.createDimensionTransition` selects output yaw 0 or 90 degrees and combines `DELTA` with `ROTATION` (`NetherPortalBlock.java:227-239`). `PositionMoveRotation.calculateAbsolute` therefore rotates current movement when the yaw changes (`PositionMoveRotation.java:49-60`), then adds the zero relative delta (`:65-66`). Horizontal velocity can be rotated; it is not always the identical vector. The accepted historical-reset applicability and a precise finite witness remain valid.

## Concrete client-state witness

Take the same received client state in either direction: old key Overworld or Nether, incoming key the other member of the pair; finite pre-transfer velocity `v=(0, 0.3, 0)`; and an outgoing correction with position-relative axes absent, zero delta, and `Relative.DELTA ∪ Relative.ROTATION`. The 26.2 respawn packet has mask `3`, so `shouldKeep((byte)2)` is true and `handleRespawn` seeds the replacement with `v` (`ClientboundRespawnPacket.java:12-14,34-35`; `ClientPacketListener.java:1289-1315`). The portal transition's yaw change is 0 or 90 degrees and pitch change is zero; yaw rotation leaves this vertical vector exactly `(0,0.3,0)`. The zero relative delta then adds zero, so after the correction the local velocity is still exactly `v`. In the historical replacement receiver, no old velocity is copied, so the replacement starts at zero; the same zero relative delta leaves it zero. This is a finite, same-received-state difference entering the next local movement tick.

For a horizontal nonzero vector, the modern result may instead be a rotated nonzero vector when the portal axes induce a 90-degree output-yaw change. It remains retained in transformed form, but must not be described as literally unchanged. Neither case identifies the physical cause from packet contents alone.

## Exact evidence checked

- **Historical route and client reset:** In 1.8.9 and 1.12.2, `Entity` portal handling selects the opposite vanilla dimension ID and reaches `changeDimension` (1.8.9 `Entity.java:261-278`; 1.12.2 `:345-362`). `PlayerManager.changeDimension` sends `PlayerRespawnS2CPacket` before its subsequent teleport correction (1.8.9 `PlayerManager.java:435-450`; 1.12.2 `:487-504`). The client handler calls `Minecraft.respawnPlayer`; that method creates a replacement and copies synced data but not delta movement (1.8.9 `ClientPlayNetworkHandler.java:798-817`, `Minecraft.java:1799-1823`; 1.12.2 handler `:905-924`, `Minecraft.java:1965-1994`).
- **Intermediate sampled route/receiver representatives:** 1.14.4 `Entity.handleNetherPortal` chooses Nether versus Overworld and calls dimension change (`Entity.java:1739-1749`); 1.16.5 and 1.18.2 choose the opposite `Level` resource key (`Entity.java:1724-1737`, `:1850-1865`); 1.20.6 uses the same opposite-key route (`Entity.java:2061-2076`); 1.21.1 `NetherPortalBlock.getPortalDestination` chooses the opposite key (`NetherPortalBlock.java:119-131`). Their sampled `ServerPlayer` transitions send respawn packets, and the corresponding client handlers replace the local player without copying old delta movement. This reuses the independently accepted older receiver-reset coverage; it does not set an exact intermediate patch boundary.
- **26.2 route and packet gates:** `NetherPortalBlock.getPortalDestination` selects the opposite vanilla key (`NetherPortalBlock.java:137-148`). On a dimension change, `ServerPlayer.teleport` sends `ClientboundRespawnPacket(..., (byte)3)` then sends the transition correction (`ServerPlayer.java:1105-1138`). The packet defines `KEEP_ENTITY_DATA=2` and mask `3` (`ClientboundRespawnPacket.java:12-14`); `ClientPacketListener.handleRespawn` creates a replacement and copies delta movement only when bit 2 is set (`ClientPacketListener.java:1289-1318`). The later correction resolves the zero relative delta through `calculateAbsolute`, which conditionally rotates current delta and adds each relative component (`PositionMoveRotation.java:40-67`).

Source trees were read-only. Ready/provenance identities and source-manifest identities match the supplied report for all eight samples; every cited method file checked against its source manifest. The supplied `report.md` SHA-256 is `53816861b122dbfdc550381b213eded6d732fab92be555007f7113344015edd1`.

## Limits

This establishes packet-visible applicability, not the physical portal cause. It covers only a local player and the ordinary Overworld/Nether key pair. It does not cover custom destinations, End/credits, death, other transfer causes, portal geometry as an independent movement behavior, or server physics. The sampled versions do not prove every intermediate patch boundary. Historical receiver reset and current runtime parity remain separate accepted gates.

No product, history, wiki, backlog, or primary-source files were edited. No tests, builds, launches, generation, or network actions were performed.
