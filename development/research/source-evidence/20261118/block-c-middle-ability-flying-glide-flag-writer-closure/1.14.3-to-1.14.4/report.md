# 1.14.3 → 1.14.4: creative flight during Elytra glide

## Scope and result

Compared only the requested ordinary local-player path: dry, airborne, nonpassenger player; usable default chest-slot Elytra already fall-flying; lawful creative flight toggle; next real travel; direct shared flag 7 clear and separate `fallDistance` write. Result: **no source difference in this bounded operation**. The captured one-tick behavior is present in both releases, so this pair does not support a version-boundary finding for this operation.

## Pinned source identities

All identities below were read locally; no network, regeneration, tests, or product sources were used.

- **1.14.3, Yarn 1.14.3+build.13:** `decompiled_minecraft/1.14.3/yarn/.decompile-provenance.json` SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. Pinned client JAR SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; Yarn archive SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`; Intermediary archive SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`. This is the local legacy hidden-manifest tree.
- **1.14.4, Mojmap:** recognized published tree (`mojmap.ready.json`, provenance, artifact/source hash manifests and verification record), source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; ready status `ready`; verification `verified-identical-source-and-input-artifacts`, 0 source differences, 0 raw-input differences. The source-manifest file's actual hash matched the ready record.
- **1.14.4, Ornithe Feather:** independently recognized published tree with the same readiness/verification status; source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, verified against the ready record. Pinned mapping files: `feather-gen2-1.14.4+build.2-mergedv2.jar` SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`; `feather-gen2-1.14.4+build.2.tiny` SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`.
- The incomplete `.yarn.staging-*` directory was ignored. For 1.14.4 the recognized Mojmap and Feather published trees were used.

Cited source bytes were checked against their pinned manifests. SHA-256: 1.14.3 `PlayerEntity.java` `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963`, `LivingEntity.java` `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`, `ClientPlayerEntity.java` `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`; 1.14.4 Mojmap `Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`, `LivingEntity.java` `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`, `LocalPlayer.java` `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`; 1.14.4 Feather `PlayerEntity.java` `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`, `LivingEntity.java` `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Comparison

- **Local trigger:** 1.14.3 `net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, lines 679–703; 1.14.4 Mojmap `net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 690–714. A legal double-jump toggle flips `abilities.flying` and sends the abilities update. The later Elytra-start request explicitly requires `!abilities.flying`; it does not replace an already-active fall-flying flag. Corresponding gates and order match.
- **Player travel operation:** 1.14.3 `net/minecraft/entity/player/PlayerEntity.java`, `travel`, lines 1377–1389; 1.14.4 Mojmap `net/minecraft/world/entity/player/Player.java`, `travel`, lines 1393–1405. In both, gate is `abilities.flying && !passenger`. The body snapshots current vertical velocity and flying speed; installs creative flying speed; calls superclass travel (the real movement); reads resulting velocity; restores only Y as the snapshotted Y times `0.6`; restores the speed field; assigns `fallDistance = 0.0F`; then clears shared flag bit 7. The else branch is plain superclass travel. Order, constants, types, gate, and writes match. The 1.14.4 Feather `PlayerEntity.moveRelative`, lines 1394–1406, independently corroborates the same flag/fall-distance ordering in that mapping.
- **Superclass glide dependency:** 1.14.3 `net/minecraft/entity/LivingEntity.java`, `travel`, lines 1779–1832; 1.14.4 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1782–1835. When `isFallFlying()` is true, glide velocity is computed and `move(SELF, velocity)` runs before the player wrapper resumes. Its own ground/server flag clear is gated by `onGround && !client`; under the requested airborne local-client precondition that clear does not apply. The later player-wrapper flag clear is unconditional within the creative-flight/nonpassenger branch and happens after movement. The glide math is unchanged for this operation; renames in the Mojmap source do not alter the compared sequence.

## Movement meaning

After the client toggles creative flight while flag 7 is already set, that movement call still enters the superclass Elytra branch and moves using Elytra glide physics. On return, the player travel wrapper restores vertical velocity to the pre-call value multiplied by `0.6`, clears `fallDistance`, then clears flag 7. Those are distinct state writes after movement. The source shows this same transient glide-then-clear behavior in both releases; any following tick uses the no-longer-fall-flying state. This is a source-level consequence only, not runtime parity evidence.

## Coverage boundary

Closed only the requested ordinary dry airborne nonpassenger local-player path and its default chest-slot activation context. No claim is made about passenger, wet, grounded, server-side, malformed equipment, exceptional movement, unrelated flight, or the rest of the full player tick graph. No candidate difference found; a separate finding file is not warranted.
