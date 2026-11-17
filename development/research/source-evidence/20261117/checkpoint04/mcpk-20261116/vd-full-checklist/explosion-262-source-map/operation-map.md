# Exact 26.2 explosion receiver source map

Scope: native 26.2 `ClientPacketListener.handleExplosion` receiver path for the accepted Explosion LocalPlayer received-impulse operation. This is a source map only; it makes no acceptance, implementation-readiness, source-boundary, full-coverage, or runtime-parity claim.

## Source identity and integrity

- Exact version / namespace: `26.2` / `unobfuscated`.
- Published source root: `decompiled_minecraft/26.2/unobfuscated/`.
- Provenance: `decompiled_minecraft/26.2/unobfuscated.provenance.json`, SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.
- Ready marker: `decompiled_minecraft/26.2/unobfuscated.ready.json`, SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; it identifies version `26.2`, mapping `unobfuscated`, and the source manifest below.
- Full source manifest: `decompiled_minecraft/26.2/unobfuscated.sources.sha256`, SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`; ready-marker manifest digest matches. Each listed source file below was independently SHA-256 checked against its manifest entry.
- Provenance notes exact-run directory in `C:\Users\Wolfi\.codex\worktrees\3e2d\LegacyParkourCompat`; the shared output is this checkout's `decompiled_minecraft/26.2/unobfuscated/`. Provenance records Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, MappingIO `0.9.1`, Gson `2.14.0`, TinyRemapper `0.14.1`, and exact run ID `26.2`.

## Native receiver and packet dispatch

All line numbers below refer to the exact published source files identified above.

1. `net/minecraft/network/protocol/game/ClientboundExplodePacket.java:17-25` declares a record implementing `Packet<ClientGamePacketListener>`; field 4 is `Optional<Vec3> playerKnockback`. Its composite stream codec at lines 26-42 encodes/decodes that field with `Vec3.STREAM_CODEC.apply(ByteBufCodecs::optional)` (lines 33-34). The packet dispatch at lines 49-51 calls `listener.handleExplosion(this)`.
2. `net/minecraft/network/protocol/game/ClientGamePacketListener.java:67` declares `handleExplosion(ClientboundExplodePacket packet)`.
3. `net/minecraft/client/multiplayer/ClientPacketListener.java:342` declares `ClientPacketListener` implements `ClientGamePacketListener`. Its `handleExplosion(final ClientboundExplodePacket packet)` implementation is lines 1359-1377.
4. Receiver order in `handleExplosion`: line 1360 calls `PacketUtils.ensureRunningOnSameThread(packet, this, this.minecraft.packetProcessor())`; lines 1361-1375 obtain the explosion center and apply the local sound, explosion particle, and tracked explosion effects; line 1376 then calls `packet.playerKnockback().ifPresent(this.minecraft.player::addDeltaMovement)`. There is no fallback/add call for an empty optional in this method.
5. `net/minecraft/client/Minecraft.java:332` declares `public @Nullable LocalPlayer player`. Thus the method reference at listener line 1376 targets the client’s `LocalPlayer` field (nullable in general); the presence consumer invokes it only when the packet optional is nonempty. This source path does not add knockback to a server-side player.
6. `net/minecraft/world/entity/Entity.java:3747-3751` implements the inherited `addDeltaMovement(Vec3 momentum)` consumer. It checks `momentum.isFinite()` and, if finite, stores `getDeltaMovement().add(momentum)` through `setDeltaMovement`.

## Server send context (source-side only)

`net/minecraft/server/level/ServerLevel.java:1211-1245` is the server-side `explode(...)` operation. After `ServerExplosion.explode()` and particle selection, lines 1240-1244 iterate `ServerPlayer` recipients within squared distance `< 4096.0`, obtain `Optional<Vec3>` from `explosion.getHitPlayers().get(player)` using `Optional.ofNullable`, then send a `ClientboundExplodePacket` on that player's connection. This producer context identifies how the client receiver obtains the payload; it does not run historical movement or alter the fact that the mapped consumer is client-local.

## Source file SHA-256

| Relative source path under `decompiled_minecraft/26.2/unobfuscated/` | SHA-256 |
|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `9CB0CC8AFEBAE9F4E42F428A52719C645817D03893DC371E6711C7BD16EAC1B6` |
| `net/minecraft/client/Minecraft.java` | `C79D698D2323CB97A7E7570FA736CC7A0B88EAB7ADAB8577E1BC66BA021CCDF0` |
| `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `CB83A7FBA6DB21183955F38DCE919A98ED5BC47366AEDD15F00A73C3963B366A` |
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `9857BD02F8FBAE5E97110551EE227C60E2FD86B2B22D122E1900DFE9D2813258` |
| `net/minecraft/server/level/ServerLevel.java` | `06C436EBB91C04F8C30B58BDF115F87DA8B5D0D14D5551C6B33E228744037CB9` |
| `net/minecraft/world/entity/Entity.java` | `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5` |

