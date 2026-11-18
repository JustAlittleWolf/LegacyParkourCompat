# 1.14.1→1.14.2 creative-flight toggle while Elytra glide is active

Scope: exact Yarn source comparison for a local player who is dry, airborne, not riding, has a usable default chest-slot Elytra and fall-flying flag 7 already set, then lawfully toggles creative flight. Examined the local flight toggle path, player travel wrapper, inherited Elytra movement branch, and fall-flying flag predicate. This is a bounded slice, not full-pair movement coverage.

## Source identity

Both sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/yarn/`. Each hidden `.decompile-provenance.json` is valid JSON and pins the exact Minecraft client, intermediary, and Yarn inputs. For 1.14.1 the inputs are client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`, intermediary `1.14.1` SHA-256 `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d`, and Yarn `1.14.1+build.10` SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`. For 1.14.2 they are client SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`, intermediary `1.14.2` SHA-256 `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`, and Yarn `1.14.2+build.7` SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.

The manifest source hashes below match the on-disk bytes:

- `1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java`: `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`
- `1.14.2/yarn/net/minecraft/client/network/ClientPlayerEntity.java`: `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947`
- `1.14.1/yarn/net/minecraft/entity/player/PlayerEntity.java`: `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`
- `1.14.2/yarn/net/minecraft/entity/player/PlayerEntity.java`: `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398`
- `1.14.1/yarn/net/minecraft/entity/LivingEntity.java`: `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`
- `1.14.2/yarn/net/minecraft/entity/LivingEntity.java`: `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`

No root-level ready marker is present in `decompiled_minecraft/`.

## Comparison

`ClientPlayerEntity.tickMovement` toggles `abilities.flying` only when `abilities.allowFlying` is true and its double-jump/toggle gates pass; the same condition and order occur in 1.14.1 lines 680–704 and 1.14.2 lines 679–703. The adjacent Elytra-start request remains suppressed while `abilities.flying` is true in both versions.

`PlayerEntity.travel` takes the same creative-flight branch in both: `abilities.flying && !hasVehicle()`; saves the pre-travel vertical velocity and fly speed; calls `super.travel(arg)`; retains the resulting horizontal velocity while replacing vertical velocity with saved `i * 0.6`; restores fly speed; sets `fallDistance = 0.0F`; and calls `setFlag(7, false)`. See 1.14.1 lines 1361–1390 and 1.14.2 lines 1361–1390. The operations and order match.

With dry conditions and flag 7 active, the inherited `LivingEntity.travel` enters the same Elytra branch in both versions. It can set `fallDistance` to `1.0F` when current vertical velocity is above `-0.5`, computes glide velocity, and calls `move(...)` before returning. The player wrapper then independently resets `fallDistance` and clears flag 7 after that real movement. Compare 1.14.1 lines 1763–1816 and 1.14.2 lines 1769–1822; after accounting for the six-line offset, the branch body is identical. `isFallFlying()` continues to read `getFlag(7)` in both versions.

**Difference:** none found in the examined slice. For this reachable witness, the active glide calculation and move still occur before creative-flight cleanup, and both releases then separately reset fall distance and clear the Elytra flag. No version-specific movement consequence is established here.

## Coverage boundary

Examined only the supplied flight-toggle/glide/cleanup slice and its direct gates. Other player-tick branches, movement dependencies, callbacks, and the rest of the 1.14.1→1.14.2 pair remain unexamined. This result does not close pair coverage.
