# Knockback jitter source audit

## Verdict: COMPLETED

**Disposition:** The comparison's source claims are supported for the bounded knockback and motion-replication paths. The jitter is generated only in server-side vanilla call paths for a local player; the modern client applies an incoming server-authored velocity without reproducing that jitter. The changed server result can affect later local-player movement through replication, but it is not itself a client movement hook. Keep it as a server/network dependency for scope classification; historical server physics must not be replayed by the client movement runtime.

## Immutable input binding

- Comparison: `knockback-jitter-source/comparison.md`
- Required SHA-256: `9c32b5e11193b7385bf01cea0e49bf578f8d341505b38c4d6393b466308a1052`
- Observed SHA-256: `9c32b5e11193b7385bf01cea0e49bf578f8d341505b38c4d6393b466308a1052`
- Releases and mappings: 1.20.6 Mojmap, 1.21 Mojmap, 1.21.1 Mojmap.
- Source root: primary checkout `decompiled_minecraft/`; inspected read-only.

## Findings

1. **Threshold and draw order — confirmed.** In 1.20.6 `LivingEntity.hurt`, the method returns on `level().isClientSide` before damage and knockback. After successful damage, the attacker-minus-victim X/Z direction is tested against the double literal `1.0E-4`; each loop body draws two `Math.random()` values for X, then two for Z, each pair difference multiplied by `0.01`. It then invokes `knockback(0.4F, x, z)`. The 1.20.6 `LivingEntity.knockback` itself has no jitter gate.

   In both 1.21 and 1.21.1, `LivingEntity.knockback` first scales strength by resistance, checks positive strength, sets `hasImpulse`, and reads current velocity. Its loop then tests `x*x + z*z < 1.0E-5F` (the float literal is promoted for the double expression), drawing X-pair then Z-pair on each iteration. Normalization and the velocity write follow. The 1.21 and 1.21.1 `LivingEntity.java` hashes are identical, so their complete method bodies match. The comparison's account of the threshold interval and random-call order is correct.

2. **Vanilla local-client reachability — confirmed within the inspected call graph.** The local client attack path reaches the target knockback block only when `target.hurt(...)` succeeds; `LivingEntity.hurt` returns false on the logical client. Sweep knockback is inside that success branch and excludes the attacking `Player`. The other vanilla callers are mob melee and `RamTarget` server behavior. In 1.20.6 and later, the damage path also retains the client-side early return. No inspected vanilla call site invokes the jitter gate on the local player from a logical-client action. Mods or custom packets are outside this source-only conclusion.

3. **Received velocity — equivalent packet scaling confirmed.** All three `ClientboundSetEntityMotionPacket` constructors clamp each velocity component to `[-3.9, 3.9]`, multiply by `8000.0`, and cast to `int`; wire encoding/decoding uses shorts. In 1.20.6 the handler divides each integer getter by `8000.0` before `lerpMotion`. In 1.21/1.21.1 the getters perform that division and the handler passes them directly. The decoded values and conversion order at the handler boundary are equivalent. `ServerEntity` sends this packet for `hurtMarked` entities in each release. Thus a server-side knockback difference can reach a player as a changed replicated velocity, but the client is not drawing the jitter or deriving that server result locally.

## Provenance and source integrity gates

- **1.20.6 Mojmap:** ready marker identifies exact version/mapping and client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; source-manifest SHA-256 is `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. Every inspected 1.20.6 source below matched its manifest entry.
- **1.21 Mojmap:** nested `.decompile-provenance.json` records `lpc-decompile-v1`, Vineflower 1.12.0, Java 25.0.3, the 1.21 client jar SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, exact `1.21.json` metadata input SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`, Mojang `client.txt` mapping input SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`, and per-source hashes. The inspected 1.21 source hashes match that nested provenance. This is sufficient to bind the inspected files to the supplied 1.21 tree and inputs, though the tree lacks the separate ready marker/source manifest present for the other two releases.
- **1.21.1 Mojmap:** ready marker identifies exact version/mapping and client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; source-manifest SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. Every inspected 1.21.1 source below matched its manifest entry.

### Inspected source SHA-256

| File | 1.20.6 | 1.21 and 1.21.1 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/Mob.java` | `bce46b968a313bcf0d72eb9c1668f201ead8cd253362525212a244d8131c2dff` | `2c55a6f8f8f205b1f7101cbb75e8c70959d602dbea38ad5b2978798e32c2bcbe` |
| `net/minecraft/world/entity/ai/behavior/RamTarget.java` | `32d3962a91d4d0c5899e8d34f987daf19e19f7c6b2274d00a3e8b4668b9be9c6` | `e471c3205ba24d6a32a5bcedde1701bca05b1e25db3288c8a06c1c27ef47f47e` |
| `net/minecraft/server/level/ServerEntity.java` | `c987db52a532d93be66fa1d5cd05dc849c1c46f8d70f641fa636db3078c262b7` | `0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` |

## Gate result

- Immutable comparison hash: **PASS**.
- Exact-version source hashes/provenance for the bounded operation: **PASS**.
- Threshold, draw order, client reachability and receive-side velocity equivalence: **PASS**.
- Full release-pair movement coverage or intermediate patch boundary: **NOT CLAIMED**; this verdict is limited to the assigned knockback/network operation and the inspected vanilla call graph.

COMPLETED
