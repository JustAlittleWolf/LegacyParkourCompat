# Explosion knockback boundary check

Assignment: first exact stable release carrying the changed player-knockback packet payload and client application, checked only in `ClientboundExplodePacket`, `ClientPacketListener.handleExplosion`, and `ServerLevel`'s per-player producer.

## Verdict

The first inspected release with the changed form is **1.21.2**. The immediately preceding inspected release, **1.21.1**, retains the 1.20.6 form. Thus the inspected adjacent pair proves the boundary between 1.21.1 and 1.21.2. No claim is made about the exact patch introduction inside any wider release interval.

| Release | Packet payload / listener | Per-player producer | Exact source identity |
|---|---|---|---|
| 1.20.6 | Three float fields; nullable constructor input is cast to floats, absent input writes `0.0F`; wire reader/writer read/write three floats. Listener unconditionally adds those values to existing local-player velocity after finalizing explosion. | Passes the hit-player vector (nullable) to packet constructor. | Mojmap provenance SHA-256 `EE53E67E7AB51069A0EAAE5A0CE13D49A9F972BDC13E3C31E041AFA77F4D26E8`; ready marker says version `1.20.6`, mapping `mojmap`, source ledger digest `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. |
| 1.21.1 | Same three-float payload and unconditional velocity addition. | Passes hit-player vector to the packet constructor. | Mojmap provenance SHA-256 `6F84A596EA944A1D40C79B53EAD9D2CDD2B3C726D710A977758D15B5578CE7A9`; ready marker says version `1.21.1`, mapping `mojmap`, source ledger digest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. |
| **1.21.2** | Record carries `Optional<Vec3> playerKnockback`; stream codec applies `ByteBufCodecs.optional` to `Vec3.STREAM_CODEC`. Listener calls `playerKnockback().ifPresent(player::addDeltaMovement)`. | Builds `Optional.ofNullable(getHitPlayers().get(player))` and sends it in the packet. | Hidden full-manifest SHA-256 `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`; verified the three source-file hashes against its `sources` entries. This version has no root ready marker or ledger sidecar; its hidden full manifest is present and was checked rather than treating those sidecars' absence as missing identity. |
| 1.21.3 | Optional `Vec3` payload and conditional client addition remain. | Optional hit-player map entry passed to packet. | Mojmap provenance SHA-256 `24093437C0CD41D807AB0829068EF97828B65BBB95B38DFDC4F706883A8483D7`; ready marker says version `1.21.3`, mapping `mojmap`, source ledger digest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. |
| 1.21.4 | Optional `Vec3` payload and conditional client addition remain. | Optional hit-player map entry passed to packet. | Mojmap provenance SHA-256 `AB0439864C89F4E0DAB09DAAE7B57AB973368EEB0320626822E06F5A9BCE9FB9`; ready marker says version `1.21.4`, mapping `mojmap`, source ledger digest `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`. |
| 1.21.11 | Optional `Vec3` payload and conditional client addition remain (packet also has later explosion presentation fields). | Optional hit-player map entry passed to packet. | Mojmap provenance SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`; ready marker says version `1.21.11`, mapping `mojmap`, source ledger digest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. |

The `Vec3` component codec in 1.21.2 is the mapped vector stream codec, and `Vec3` has double-valued components; the payload therefore retains doubles instead of converting the impulse to three floats. With an absent hit-player entry, 1.21.2 leaves the local player's velocity untouched. In 1.20.6 and 1.21.1, absent input becomes three zero floats and the listener still performs a zero-vector addition. For ordinary nonzero finite existing velocity components, those absent cases produce the same component values. The old listener still adds three +0.0F values (promoted to double), while 1.21.2 skips the addition for an empty optional. By `Vec3.add(double, double, double)` at `decompiled_minecraft/1.21.2/mojmap/net/minecraft/world/phys/Vec3.java:116–117` (SHA-256 `62581D3005CC32401545F9229A9C293DFD61C52E7D30F88456C49D2C2867E68D`), an existing -0.0 component plus +0.0 becomes +0.0; the empty optional preserves -0.0. This is an observable signed-zero state difference; no trajectory consequence is claimed here. Non-float-exact impulse components can also differ after packet transport and addition.

## Source locations

All paths are relative to `decompiled_minecraft/<version>/mojmap/`.

- 1.20.6: `net/minecraft/network/protocol/game/ClientboundExplodePacket.java`, constructor lines 36–67, decode lines 69–89, writer lines 92–113; `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleExplosion`, lines 1167–1184; `net/minecraft/server/level/ServerLevel.java`, per-player `new ClientboundExplodePacket`, lines 1073–1091.
- 1.21.1: same packet constructor/codec ranges as 1.20.6; `ClientPacketListener.handleExplosion`, lines 1172–1189; `ServerLevel` producer, lines 1070–1088.
- 1.21.2: `net/minecraft/network/protocol/game/ClientboundExplodePacket.java`, record and optional codec, lines 15–27; `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleExplosion`, lines 1225–1241; `net/minecraft/server/level/ServerLevel.java`, optional producer, lines 1106–1109; `net/minecraft/world/phys/Vec3.java`, `STREAM_CODEC` and double fields, lines 24–36. The `Vec3.add(double, double, double)` component additions are lines 116–117; SHA-256 `62581D3005CC32401545F9229A9C293DFD61C52E7D30F88456C49D2C2867E68D`.
- 1.21.3: packet record/codec, lines 15–27; listener handler, lines 1225–1241; server producer, lines 1106–1109.
- 1.21.4: packet record/codec, lines 15–27; listener handler, lines 1225–1241; server producer, lines 1107–1110.
- 1.21.11: packet record/codec, lines 17–42; listener handler, lines 1311–1329; server producer, lines 1202–1205.

## Hash verification

For every listed release, SHA-256 of the three relevant decompiled files (`ClientboundExplodePacket.java`, `ClientPacketListener.java`, `ServerLevel.java`) matched that release's source ledger; for 1.21.2 each matched the corresponding entry in `.decompile-provenance.json`'s full `sources` object. `Vec3.java`'s SHA-256 also matched the source ledger/full manifest for all six releases. Provenance hashes above match the assigned identities. The accepted endpoint finding was left unchanged; its supplied revision hash is `5D8D7E798B60BC96F4707EA3F98B3799A829A9A9852EC76CA9745F499C4B3710`.

