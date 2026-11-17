# Independent audit: 1.16.3 → 1.16.4 player position correction route

Verdict: **ACCEPT** for the bounded producer/receiver route in report `build/source-mid-patches-20261117/1.16.3--1.16.4/player_position_correction/position-correction-route.md`, SHA-256 `0AFE2C27F2117516190273F1586DBA412BC9E5BDF9424A80A484175D51680DE5`.

## Identity and binding

The exact Mojmap source identities are the mapping-local hidden `.decompile-provenance.json#sources` maps at `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Their digests match the source input contract: 1.16.3 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 1.16.4 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. For all five cited files (`ServerPlayer.java`, `ServerGamePacketListenerImpl.java`, `ClientPacketListener.java`, `ClientboundPlayerPositionPacket.java`, and `LocalPlayer.java`), I independently checked that on-disk SHA-256 equals each endpoint's manifest entry. The report's endpoint hashes match.

I independently extracted and compared the complete relevant method text across both endpoint sources. Every pair is byte-identical at the method-text level, and each computed SHA-256 matches the report:

- `ServerGamePacketListenerImpl.handleMovePlayer(ServerboundMovePlayerPacket)`: `cc6b274fbd71db862e77ac821e2ce95eaf60447a004be472bf4fcfcab62e86c6`.
- `ServerGamePacketListenerImpl.teleport(..., Set<RelativeArgument>)`: `5b415c3ad9f2b58f6cd629d8c9de098da86dafed7ac981657f8dcc44022834f8`.
- `ServerPlayer.teleportTo(double, double, double)`: `a52913f2ec948f9184b023d3b965fdfc6e41a710653ecd3bee2c1dc1017e20a0`.
- `ServerPlayer.teleportTo(ServerLevel, ...)`: `99ed1879ddd84d2dd8863415c12fc1994b1b5c3b752e5c3ec1ecbff5f20adb25`.
- `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)`: `93685d72b1bec126554f85eba4d41f24832d57120a8afe61315b3e8efe798d16`.

## Technical review

The compared server movement handler routes its existing correction cases through the same teleport method at both endpoints. The teleport method applies server-side position, creates and sends the relative/absolute position packet, and stores the acknowledgement state. `ServerPlayer` uses this route for same-level movement corrections; dimension changes retain their separate respawn sequence and then send the correction.

The client listener uses relative axes to retain the corresponding delta component and zeroes non-relative components, updates old/current position fields, calls `absMoveTo`, acknowledges the teleport, and sends the follow-up position/rotation packet. The packet class and `LocalPlayer` file are byte-identical across this pair. These sources support the bounded conclusion that this route can write client player position and velocity state but has no source delta between 1.16.3 and 1.16.4.

## Limits

Acceptance is only for the cited correction producer/packet/receiver route and its no-difference conclusion. It does not close other changed position writers, callbacks, resources/tags/providers, the complete movement tick/dependency inventory, or the full pair audit. It makes no runtime claim and does not freeze the pair.