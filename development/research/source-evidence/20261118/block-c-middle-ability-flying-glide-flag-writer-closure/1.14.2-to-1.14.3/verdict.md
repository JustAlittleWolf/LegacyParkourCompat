# Independent audit verdict: 1.14.2 → 1.14.3 creative-flight Elytra flag clear

**Verdict: CONFIRMED — bounded no-difference claim.** For the assigned witness, both releases execute real fall-flying movement inside `LivingEntity.travel` while entity flag 7 is still set. `PlayerEntity.travel` then restores the saved vertical velocity scaled by `0.6`, restores its prior movement-speed field, assigns `fallDistance = 0.0F`, and separately clears flag 7 with `setFlag(7, false)`. The branch gates, statement order, and movement math are unchanged across these endpoints. This verdict is limited to the described local, dry, airborne, nonpassenger, usable default chest Elytra witness with lawful creative flight; it is not a full-pair coverage audit or runtime-parity result.

## Independent evidence

- In both `ClientPlayerEntity.tickMovement` methods (1.14.2 and 1.14.3, lines 679–704), the ability toggle is gated by `allowFlying` and the unlocked double-jump path; with the Elytra already active, the separate start-fall-flying request is gated out by `isFallFlying()` / `abilities.flying`. `super.tickMovement()` runs at line 761 in both. The on-ground auto-disable follows it and is unreachable for this airborne witness.
- In `PlayerEntity.travel` (1.14.2 lines 1362–1389; 1.14.3 lines 1364–1391), the `abilities.flying && !hasVehicle()` path calls `super.travel(arg)` before writing the post-travel velocity, fall distance, and bit 7. It assigns `fallDistance = 0.0F` and then calls `setFlag(7, false)` as separate writes in both releases.
- `LivingEntity.travel` (1.14.2 lines 1769–1840; 1.14.3 lines 1779–1850) checks `isFallFlying()`, performs the same glide velocity equations, and calls `move` before returning through `PlayerEntity.travel`. The only identified namespace difference in that movement call is `MovementType.field_6308` in the 1.14.2 Yarn output versus `MovementType.SELF` in 1.14.3; this denotes the self movement enum operation, not a behavioral delta.
- `LivingEntity.isFallFlying()` returns `getFlag(7)` in both (1.14.2 lines 2596–2598; 1.14.3 lines 2604–2606). `Entity.getFlag` and `setFlag` read/write the flags data-tracker byte with the same bit masks in both (1.14.2 lines 1824–1835; 1.14.3 lines 1864–1875). `fallDistance` is a separate float field, reset in `PlayerEntity.travel` independently of the flags byte.

## Evidence identity

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. The local provenance manifests and selected source bytes match the writer report. Artifact hashes below were checked against the cached artifacts and recorded pins/manifests.

| Release | Yarn mapping | Raw client SHA-256 | Intermediary SHA-256 | Yarn artifact SHA-256 | Provenance SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.14.2 | 1.14.2+build.7 | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | 1.14.3+build.13 | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

The compared report was frozen at SHA-256 `5785395e47917aebf9ae3d2fe1baa33ddb21242dc35255f051756eebce5b7026` before it was opened. It is substantively consistent with this independent source review.

**Signed:** Independent source auditor, Codex, 2026-11-18.

