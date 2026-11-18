# 1.9.3–1.9.4 ability flight and fall-flying flag

## Exact source identity

Both sides are local decompiled `ornithe-feather` sources, using Feather mapping family. The project’s decompile pipeline is Vineflower 1.12.0 on Java 25.0.3+9-LTS; 1.9.3 provenance records the exact Feather metadata and client inputs, and 1.9.4 has a ready marker plus a source manifest.

- **1.9.3:** Feather `1.9.3+build.2`; client SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`; Feather merged-v2 artifact SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; Feather version metadata SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`. Local `Entity.java`, `LivingEntity.java`, and `PlayerEntity.java` SHA-256 values are recorded below. Provenance file SHA-256: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- **1.9.4:** Feather `1.9.4+build.2`; client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; ready-marker SHA-256 `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`. The local source hashes below match the manifest entries. The provenance record identifies the exact decompile run and tool versions.

| Relative source | 1.9.3 SHA-256 | 1.9.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |

## Scoped trace

`Entity.getFlag(index)` reads `syncedData` bit `1 << index`; `setFlag(index, false)` clears that bit in the same tracked byte (`Entity.java:1662–1673` in both releases). `LivingEntity.isFallFlying()` returns `getFlag(7)` (`LivingEntity.java:2052–2054`). The normal living travel path invokes `moveRelative(sidewaysSpeed, forwardSpeed)` after `flyingTick` (`LivingEntity.java:1705–1712`).

For a locally controlled player with ordinary creative `abilities.flying == true`, not riding, and a previously synchronized fall-flying flag still set, `PlayerEntity.moveRelative` takes its creative-flight branch (`PlayerEntity.java:1372–1389` in both versions). It sets `speedInAir` from authorized fly speed (doubled when sprinting), then calls `super.moveRelative`. In that superclass method, the local-control gate passes and the water/lava exceptions admit a flying player. With flag 7 set, the Elytra branch computes glide velocity and calls `this.move(...)` before returning (`LivingEntity.java:1302–1354`). The player override then restores vertical velocity as saved `g * 0.6`, restores air speed, zeros `fallDistance`, and **unconditionally calls `setFlag(7, false)`**. The clear is after the superclass call that already performed the glide move; it does not depend on a collision, landing, a second flag check, or server-side execution. The superclass’s own on-ground clear is gated by `!world.isClient`; the player override’s clear is not.

This establishes a reachable transient state under the stated normal vanilla authorization/synchronization premise, without requiring crafted input or a server/network reproduction. The player override ends fall-flying after that movement call, so subsequent client movement no longer enters the flag-7 glide branch until the tracked flag is set again. This sequence and ordering are the same in 1.9.3 and 1.9.4.

## Comparison result and bounds

No source difference for this operation between 1.9.3 and 1.9.4. `Entity.java` and `LivingEntity.java` are byte-identical by source hash. The relevant `PlayerEntity.moveRelative` body is unchanged at lines 1372–1389, even though the enclosing `PlayerEntity.java` files differ elsewhere. This is a scoped no-difference observation, not an independent movement finding or an exact patch-introduction claim.

Scope is limited to creative local ability flight combined with an already-set fall-flying flag and the flag clear after glide movement. No latency guarantee is asserted; no crafted input, network/server reproduction, NBT, fall-distance behavior beyond the stated reset, flag activation/finalization, or other ability/glide differences were investigated.
