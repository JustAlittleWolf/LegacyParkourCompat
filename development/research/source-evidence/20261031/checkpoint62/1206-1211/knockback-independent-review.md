# Knockback near-zero perturbation candidate: independent review

## Disposition

**Accept as movement-relevant source behavior.** The change can alter a `ServerPlayer`'s velocity during an ordinary player hit and that velocity is sent to the victim's client. The client motion packet is a direct local-player response. The server does not replay historical movement, but the changed server-side damage/knockback result crosses the network and changes the local player's movement state.

## Exact source identity and provenance

Canonical read-only roots used:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.6\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.1\mojmap`

The 1.20.6 tree's `mojmap.provenance.json` identifies exact version 1.20.6, namespace Mojmap, Vineflower 1.12.0 and source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; `mojmap.ready.json` pins the client artifact SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`. The 1.21.1 tree's `mojmap.provenance.json` identifies exact version 1.21.1, namespace Mojmap, Vineflower 1.12.0; its source manifest SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, and its client artifact SHA-256 is `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`. The 1.21.0 Mojmap source directory is present, but has no top-level provenance or ready sidecar. Its reviewed files are identified individually by hashes below.

| Mojmap source file | 1.20.6 SHA-256 | 1.21 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `C66EC8DC3B1856E490E5834A46185589030D9FBC411E3E2CE64C73203CD753B2` | `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8` | `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8` |
| `net/minecraft/world/entity/player/Player.java` | `785D93CCC94E1F912E545B2B0C355EDEB352B44EE8E83A69364DAEC35266DBE2` | `ED32B88C3C7C8418B83DB41823520F2B6B0B49A98610306EF26E9681DC925C71` | `ED32B88C3C7C8418B83DB41823520F2B6B0B49A98610306EF26E9681DC925C71` |
| `net/minecraft/world/damagesource/DamageSource.java` | `7F581D09D3958DC9F5DAB929661FDE59D048DF31B022DF39D3A86A5EC25E65EA` | `B4F97CA0E3A7E9DA20F3D277FFEADA312B3027D321A0C976B3A0CE8239033B10` | `B4F97CA0E3A7E9DA20F3D277FFEADA312B3027D321A0C976B3A0CE8239033B10` |
| `net/minecraft/server/level/ServerEntity.java` | `C987DB52A532D93BE66FA1D5CD05DC849C1C46F8D70F641FA636DB3078C262B7` | `0994216505C8DF7CC67A7A068AE8E71D1F023DBEF5DC1DC31360E3E5BDC2CE14` | `0994216505C8DF7CC67A7A068AE8E71D1F023DBEF5DC1DC31360E3E5BDC2CE14` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `BE4268A7616ED94E2A03411A6FDF002B0E6DC3303EA959C9F41FEF322867D533` | `CC73BB8FAB95346AEA10F7018BA7DE6C723CDE8A52A4AE40629B5370B6FF6D10` | `CC73BB8FAB95346AEA10F7018BA7DE6C723CDE8A52A4AE40629B5370B6FF6D10` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `E121E998EC25211AAECF91FFFD0EA699CEBD47EDDEA9134EFD3F2FFBEF8EB7CD` | `822E00D89E8DEF4CF7A8C22FDFEFBA3B9DC2135A7630E4EBE561A9D547D7F79D` | `822E00D89E8DEF4CF7A8C22FDFEFBA3B9DC2135A7630E4EBE561A9D547D7F79D` |
| `net/minecraft/world/entity/Entity.java` | `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3` | `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850` | `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850` |

## Method change and intermediate comparison

In 1.20.6, `LivingEntity.knockback` (line 1475) scales strength by knockback resistance, then normalizes `(x, 0, z)` and writes horizontal and vertical delta. It has no near-zero random perturbation. The hurt caller at lines 1209–1217 only calls knockback when the source entity is non-null and uses a caller-side retry loop while `x² + z² < 1.0E-4`, drawing random x and z offsets in increments bounded by 0.01.

In 1.21, `LivingEntity.knockback` (line 1441) adds a method-local loop while `x² + z² < 1.0E-5`, drawing random x and z offsets in increments bounded by 0.01. The hurt path at lines 1174–1186 now supplies a direction from a projectile calculation or `DamageSource.getSourcePosition()` and no longer has the 1.20.6 source-entity guard or caller-side `1.0E-4` retry. In 1.21.1, the reviewed `LivingEntity`, `Player`, `DamageSource`, `ServerEntity`, server packet listener, client packet listener, and `Entity` source files are byte-identical to 1.21 by SHA-256; the method and route are unchanged.

The relocation is behavior-relevant: for valid nonzero source vectors with squared length in `[1.0E-5, 1.0E-4)`, 1.20.6 retries and perturbs the vector in `hurt`, while 1.21/1.21.1 passes it unchanged through the new method-local threshold.

## Callsite and authority trace

The complete direct `.knockback(...)` callsite search across the three Mojmap trees found:

- `LivingEntity.hurt`: on 1.20.6 it performs caller-side randomization; on 1.21/1.21.1 it passes source-derived vectors into the changed method. `LivingEntity.hurt` returns `false` on client-side levels before reaching the damage/knockback route in all three versions. This excludes local client-side damage prediction, but it does not exclude a server-side `ServerPlayer` target.
- `LivingEntity.blockedByShield`: applies knockback to the direct attacker. This can be a player, but its client effect depends on the server motion sync route; it is not needed for the accepted witness.
- `Player.attack`: applies yaw-derived knockback to a successful living target and, for sweeping, to selected living entities other than the attacker and primary target. Those yaw vectors are not near zero. The 1.20.6/1.21 client `MultiPlayerGameMode.attack` also calls local `Player.attack` after sending its attack packet, but the target's `hurt` returns false on the client, so this prediction path does not reach the added knockback through damage. On the server, `ServerGamePacketListenerImpl` invokes `player.attack(target)` after rejecting `target == player`; it does not reject a target that is a `ServerPlayer`.
- `Mob` and `RamTarget` apply knockback to their targets. They are other-entity attack/AI routes; target `LivingEntity.hurt` can still be the player-motion route when the target is a player.

For a `ServerPlayer` damaged by a player attack, `LivingEntity.hurt` calls `markHurt`, setting `hurtMarked`. `Player.attack` then explicitly sends `ClientboundSetEntityMotionPacket` when the target is a `ServerPlayer` with `hurtMarked`, and clears the flag. `ClientPacketListener.handleSetEntityMotion` resolves the entity ID from the client level and calls `lerpMotion` on that entity; when the ID is the receiving `LocalPlayer`, this writes the local player's motion. Thus this is a server-produced damage/knockback value, but its observable output is a client motion response and is in the requested movement-response scope.

## Equal-state source witness

Use the same pre-hit state in both versions: a non-invulnerable, on-ground `ServerPlayer` victim with zero delta movement and zero knockback resistance; a valid attacking `ServerPlayer` at horizontal offset `(0.004, 0)` from the victim; positive non-critical hit damage; no attack knockback enchantment. This is a valid close-range hit, and the target is not the attacking player.

The player attack damage source's `getSourcePosition()` is the direct attacker's position (`DamageSource.java:101` in 1.20.6; line 106 in 1.21/1.21.1). The resulting horizontal input has squared length `0.004² = 1.6E-5` (sign depends on which player is placed at the positive offset):

- 1.20.6: `1.6E-5 < 1.0E-4`, so the hurt caller replaces the vector with random near-zero x/z perturbations before `knockback` normalizes and writes velocity.
- 1.21 and 1.21.1: `1.6E-5 >= 1.0E-5`, so the new method does not perturb it and normalizes the original axis-aligned vector.

With the same pre-hit velocity and strength, these produce different horizontal motion except for a degenerate random draw exactly collinear with the original vector. The attack path sends the victim's changed velocity to that victim's client as described above. This witnesses a reachable `ServerPlayer` to `LocalPlayer` motion response without relying on a self-hit or assuming a side guard excludes the local player.

## Limits

No build, tests, game launch, decompilation, or network access was performed. Source discovery and disposition only; no implementation or runtime parity claim is made.

## Scope erratum (authoritative disposition for this review)

The source arithmetic and reachability witness above are retained, but the original movement-scope disposition is superseded: **this candidate is not accepted as eligible local-player movement under the assigned boundaries.** `LivingEntity.hurt` returns on logical clients before its knockback path. The witness therefore executes knockback on the logical server against a `ServerPlayer`; this server-side attack/damage hit production is explicitly excluded from the movement scope. Although that producer sends a `ClientboundSetEntityMotionPacket`, the equal raw packet receiver on `LocalPlayer` is unchanged across the compared versions. The changed source is thus an excluded server-side producer, not a changed local-player movement operation or response consumer.

The exact threshold/source delta and its source witness above remain valid statements about server-side knockback production. They do not establish an eligible local-player behavior delta under the assigned scope.
