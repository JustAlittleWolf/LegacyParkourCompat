# 1.14.2 to 1.14.3: creative-flight Elytra flag clear

## Result

No player-movement difference was found in the assigned witness: a local player in dry air, not riding, with a default chest-slot usable Elytra and fall-flying flag already set toggles lawful creative flight while airborne, then performs nonzero travel. Both exact sources use the same operation order. The Elytra travel branch runs inside `super.travel`; afterward `PlayerEntity.travel` restores vertical velocity from the pre-super value multiplied by `0.6`, restores the prior movement-speed field, assigns `fallDistance = 0.0F`, then calls `setFlag(7, false)`. The final flag clear is separate from the fall-distance reset in both releases.

Thus 1.14.3 introduces no source-confirmed delta for this operation. The Elytra movement executed inside `LivingEntity.travel` is real movement: its fall-flying branch computes velocity, moves the entity, and can clear flag 7 on landing server-side. The selected airborne witness reaches that branch before the player wrapper clears the flag. A trajectory consequence is not claimed.

## Exact source comparison

Both releases are Yarn-mapped. 1.14.2 uses Yarn `1.14.2+build.7`; 1.14.3 uses Yarn `1.14.3+build.13`.

- `net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement` (1.14.2 lines 679–704; 1.14.3 lines 679–704): lawful flight-toggle state update is gated by `abilities.allowFlying`; when flight is not locked, the double-jump timer path toggles `abilities.flying` only when not swimming. The subsequent Elytra-start request is gated by descending airborne state, `!isFallFlying()`, and `!abilities.flying`, and checks the equipped chest stack for a usable Elytra. Then `super.tickMovement()` runs (line 761 on both sides). In the stated witness the Elytra is already active, so the start request is not the transition under examination. The local on-ground auto-disable runs only after `super.tickMovement()` and is outside the airborne witness.
- `net/minecraft/entity/player/PlayerEntity.java`, `travel` (1.14.2 lines 1362–1389; 1.14.3 lines 1364–1391): both have the same `abilities.flying && !hasVehicle()` branch, save current vertical velocity and speed, call `super.travel(arg)`, restore horizontal results and saved vertical velocity scaled by `0.6`, restore the prior speed, reset fall distance to `0.0F`, then clear flag 7. Otherwise they call `super.travel(arg)` directly. Statements, gates, types and order match.
- `net/minecraft/entity/LivingEntity.java`, `travel` (1.14.2 lines 1769–1840; 1.14.3 lines 1779–1850): the fall-flying branch's math, gates and writes match in the compared region. The mapped name for self movement differs (`MovementType.field_6308` versus `MovementType.SELF`), but this is the same enum operation under the two Yarn mapping builds, not a behavior change. The wrapper reset happens after this real movement. `isFallFlying()` returns `getFlag(7)` (1.14.2 lines 2596–2598; 1.14.3 lines 2604–2606).
- `net/minecraft/entity/Entity.java`, `getFlag`/`setFlag` (1.14.2 lines 1824–1830; 1.14.3 lines 1864–1870): flag 7 is stored in the entity flags byte. `fallDistance` is a separate float field; `PlayerEntity.travel` writes it independently, before clearing the flag. Both methods have matching bit operations.

## Evidence identity and rehash

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Each side's `.decompile-provenance.json` identifies the exact release, Yarn mapping URL/build, raw client URL/hash and intermediary/Yarn artifact URLs/hashes. Selected Java bytes below were rehashed and match their manifest entries. Raw client and cached intermediary/Yarn mapping artifacts were also rehashed and match both manifests and their `.pin.json` sidecars.

| Version | Mapping | Raw client SHA-256 | Intermediary SHA-256 | Yarn artifact SHA-256 |
| --- | --- | --- | --- | --- |
| 1.14.2 | Yarn 1.14.2+build.7 | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` |
| 1.14.3 | Yarn 1.14.3+build.13 | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` |

| Version | ClientPlayerEntity.java | PlayerEntity.java | LivingEntity.java | Entity.java | provenance manifest SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.14.2 | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

The canonical decompile root has no root-level ready marker. This report establishes only the assigned bounded operation; it is not a full-pair coverage audit or acceptance verdict.
