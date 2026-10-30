# Explosion packet receiver route: 1.17.1–1.18.2

## Scope and result

This closes the changed route `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` → `ClientGamePacketListener.handleExplosion` → `ClientPacketListener.handleExplosion` → the local player's delta movement, for equal valid packet payloads and equal client-thread application order. The source-side pool38 receipt report, unresolved-route audit, and the `ClientboundExplodePacket.java` routing row were the only prior research inputs used. Exact sources are read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`; identities and hashes are in [source-hashes.tsv](source-hashes.tsv).

**Disposition: no receiver/application difference for the direct LocalPlayer movement response.** The 1.17.1 packet-file hash differs from 1.18 onward, but the decoder, dispatch, and getters preserve the same fields, wire order, types, conversions, and values. The listener source is identical across 1.18/1.18.1, while the four selected handler bodies have the same operation order and movement write.

## Decode and dispatch

In all four `ClientboundExplodePacket.java` files, the buffer constructor reads center `x`, `y`, `z`, and power as floats in that order (stored in double fields for the center); floors the three stored coordinates; reads the block list as signed byte offsets from those floored coordinates; then reads knockback X/Y/Z as floats in order (`:39–55`). The packet’s `handle(ClientGamePacketListener)` forwards itself to `handleExplosion` (`:80–82`). The getters expose the same values and types (`:84–110`). The `write` method uses matching field order and types (`:58–77`). No decode gate or payload conversion differs for the stated equal-valid-payload comparison.

`ClientGamePacketListener` declares `handleExplosion(ClientboundExplodePacket)` on all four sides (`:62`). `ClientPacketListener.handleExplosion` begins with `PacketUtils.ensureRunningOnSameThread(packet, this, minecraft)` on each side (1.17.1 `:973–974`; 1.18/1.18.1 `:968–969`; 1.18.2 `:972–973`). A packet successfully applied by this handler therefore follows the client event-loop gate. `PacketUtils`' internal implementation changes in 1.18.2, as already recorded in the permitted receipt report; this does not change the handler’s subsequent valid-packet write sequence. Cross-packet ordering and packet-vs-tick interleaving remain assumptions, not claims of this route closure.

## Applied effects and exact player write

After the thread gate, each handler constructs `Explosion(level, null, packet.getX(), packet.getY(), packet.getZ(), packet.getPower(), packet.getToBlow())`, then calls `finalizeExplosion(true)`, then writes:

`minecraft.player.setDeltaMovement(minecraft.player.getDeltaMovement().add(packet.getKnockbackX(), packet.getKnockbackY(), packet.getKnockbackZ()))`.

References: 1.17.1 `ClientPacketListener.java:975–991`; 1.18 and 1.18.1 `:970–972`; 1.18.2 `:974–976`. The first handler formats the constructor over multiple lines; argument order is unchanged. Thus the direct player effect is vector addition to the current velocity followed by one `setDeltaMovement` call. No clamp, hurt-result test, player/entity lookup, or conditional around that write exists in these bodies.

The constructed explosion has a null source and receives the packet block list. The handler calls `finalizeExplosion`, not `explode`. In all four `Explosion.java` sources, `finalizeExplosion` has the same client sound/particle and block-list application sequence; it contains no `hurt` call. Its 1.17.1 body begins at `:241`, and 1.18 through 1.18.2 at `:233`. Consequently the packet’s direct knockback is not gated by client-side explosion damage. `LocalPlayer` also overrides `hurt(DamageSource, float)` to return `false` in all four versions (1.17.1 `LocalPlayer.java:145–148`; 1.18/1.18.1 `:142–145`; 1.18.2 `:143–146`). This override is not on the handler’s knockback path: the handler does not call `hurt`; it adds the separately decoded packet vector directly. Damage immunity therefore suppresses no part of this direct vector write.

`finalizeExplosion(true)` precedes the velocity addition, so the same packet’s listed block effects are applied before the direct velocity write in all four versions. Their downstream effects on later collision queries are outside this direct LocalPlayer response slice and require equal resulting client world state for movement comparison.

## Exact evidence identity

Source-manifest digests (as recorded by the permitted pool38 source identity report): 1.17.1 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 1.18 `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`; 1.18.1 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`; 1.18.2 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. All four use Mojmap.

No server explosion/damage/knockback producer was inspected for acceptance. This conclusion does not compare how the server calculates the packet vector, what values a producer sends, cross-packet ordering, runtime parity, or the complete release pair.
