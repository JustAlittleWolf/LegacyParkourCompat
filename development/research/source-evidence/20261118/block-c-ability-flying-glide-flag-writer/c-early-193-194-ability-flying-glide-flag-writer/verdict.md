# Independent source audit: creative flight with active fall-flying state

**Terminal: COMPLETED**

**Verdict: bounded no-difference accepted.** The frozen comparison's source claim is supported: in the ordinary local-player movement path, when `abilities.flying` is true, the player is not riding, and live flag 7 is already true, both 1.9.3 and 1.9.4 call the superclass Elytra movement first. The superclass computes glide velocity and calls `move(...)`; when it returns, `PlayerEntity.moveRelative` restores the saved vertical velocity and air speed, sets `fallDistance` to `0.0F`, then directly clears flag 7 with `setFlag(7, false)`. The clear belongs to the player override and is unconditional within its creative-flight branch. No source difference for this bounded operation is established between the named releases.

**Reachability and authority.** The normal `LivingEntity` travel path calls `moveRelative(sidewaysSpeed, forwardSpeed)`. `LocalClientPlayerEntity.isLocallyControlled()` returns `true` in both releases, satisfying the superclass movement gate `isLocallyControlled() || isLogicalSideForUpdatingMovement()` for the locally controlled player. The player's creative flight branch then calls `super.moveRelative`; the glide branch tests the already-live `isFallFlying()` state (flag 7) and performs the actual `move(...)` before control returns to the player's direct flag clear. The `setFlag` implementation writes the shared tracked-data bit directly. Unlike the separate on-ground clear in the superclass, the player's clear has no `!world.isClient` gate. This supports the conditional local-player source path; it does not establish when or how the live Elytra flag became set, synchronization timing, or runtime behavior.

**Independent source checks.** `Entity.java` and `LivingEntity.java` are byte-identical across the pair. `PlayerEntity.java` differs elsewhere, but the complete `moveRelative` method is text-identical. The local-player `isLocallyControlled()` override returns true on both sides. Reviewed local source members (all paths relative to `decompiled_minecraft/<version>/ornithe-feather/`):

- `net/minecraft/entity/living/player/PlayerEntity.java:1372–1393` — creative flight branch and post-super flag clear.
- `net/minecraft/entity/living/LivingEntity.java:1302–1356` — local-control gate, flag-7 glide branch and `move(...)` call; `1711` — travel call to `moveRelative`.
- `net/minecraft/entity/Entity.java:1662–1673` — shared-flag read/write; `2254–2257` — logical-side update predicate.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:447–449` — local-client control override.

**Immutable inputs and verified identities.**

- Frozen comparison report SHA-256: `23f3b8cd6811d735e42eb42815648f1e23ca66f2bc1b7bc1773e5d8b4fba34d4`.
- 1.9.3 exact local Feather identity: `1.9.3+build.2`; provenance SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. Its provenance source entries match the independently recomputed local file hashes below. The provenance records the 1.9.3 client and Feather merged-v2/mapping metadata hashes stated in the frozen report.
- 1.9.4 exact local Feather identity: `1.9.4+build.2`; provenance SHA-256 `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`; ready-marker SHA-256 `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`; source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The ready marker identifies the exact client SHA `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` and Feather mapping artifacts `feather-gen2-1.9.4+build.2-mergedv2.jar` / `.tiny`; all three checked source-file digests match its source manifest.
- Local source SHA-256, 1.9.3 / 1.9.4 respectively:
  - `Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.
  - `LivingEntity.java`: `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` / `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
  - `PlayerEntity.java`: `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` / `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.

**Limits.** This is a bounded endpoint operation audit, not pair-wide tick coverage, patch-boundary evidence, a product-finding acceptance, or runtime parity. It does not audit fall-distance behavior (the reset is mentioned only to preserve the observed clear ordering), NBT load, Elytra activation/finalization, latency, network behavior, crafted events, or an A/B comparison.