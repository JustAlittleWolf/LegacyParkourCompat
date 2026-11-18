# C early 1.11–1.11.1: ability flying with a live Elytra flag

## Bounded result

**Pair:** Minecraft 1.11 → 1.11.1, Ornithe Feather. **Status:** no adjacent source difference found for the assigned ability-flight / live Elytra-flag movement path. This is bounded source coverage, not a full-pair inventory, finding acceptance, or runtime-parity claim.

The key player override is byte-for-byte identical in the two prepared sources. When `abilities.flying && !isRiding()`, it calls `super.moveRelative(...)`, then restores the prior air speed and directly calls `setFlag(7, false)`; the clear occurs after the superclass returns. The inherited live-flag glide path performs its motion calculation and `move(MoverType.SELF, velocityX, velocityY, velocityZ)` inside that superclass call. Thus, with a live flag on entry, a valid chest Elytra, airborne and not riding, and local movement authority, glide movement can run before the ability-flight override clears flag 7 in both releases. `Entity.move` in 1.11.1 adds a preceding branch gated on `MoverType.PISTON`; the glide call is `SELF` and does not enter that branch.

## Exact source evidence

All references are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `net/minecraft/entity/living/player/PlayerEntity.java`: 1.11 `moveRelative(float,float)`, lines 1385–1403; 1.11.1 same method, lines 1387–1405. The method bodies are byte-for-byte identical. The branch requires `abilities.flying && !isRiding()`, delegates first, then executes `setFlag(7, false)` without an `isFallFlying()` condition.
- `net/minecraft/entity/living/LivingEntity.java`: 1.11 `moveRelative(float,float)`, lines 1386–1425; 1.11.1 lines 1388–1427. The method bodies are byte-for-byte identical. Its outer authority gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. In the `isFallFlying()` branch, after the glide velocity math it calls `move(MoverType.SELF, ...)`; the on-ground flag clear is separately gated by `!world.isClient`.
- `net/minecraft/entity/living/LivingEntity.java`: 1.11 tick travel tail, lines 1796–1802; 1.11.1 lines 1798–1804. In both, `flyingTick()` precedes the virtual `moveRelative(...)` call. `flyingTick()` retains a valid airborne, non-riding Elytra flag locally: it writes the recomputed flag only under `!world.isClient` (1.11 lines 1808–1827; 1.11.1 lines 1810–1829).
- `net/minecraft/entity/Entity.java`: 1.11 `move(MoverType,double,double,double)`, lines 455 onward; 1.11.1, lines 459 onward. The 1.11.1 addition at lines 464–500 is limited to `MoverType.PISTON`, so it does not run for the glide's `MoverType.SELF` call.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: both versions have the same local input gates at lines 725–746. Ability flight is toggled only with `abilities.canFly`; Elytra start is requested only while descending, airborne, not already gliding, and `!abilities.flying` (lines 742–745). This establishes valid individual inputs but does not prove a guaranteed same-tick ordinary transition into their conjunction.

## Precondition and limits

The bounded witness assumes normal authorized `abilities.flying` already true and a previously live flag 7 at tick entry, plus local-client authority, `!onGround`, `!isRiding()`, and an equipped Elytra for which `ElytraItem.canFly` is true. These are the gates the paired source uses. The local start request excludes `abilities.flying`, so I do not claim that ordinary input initiates Elytra flight and enables ability flight in the same tick, or guarantee when a legitimate ability grant can coincide with an already-live Elytra flag. No latency-dependent timing claim is made.

This report does not treat fall-distance resets, Elytra startup behavior, NBT loading, unrelated changes in `Entity.move`, malformed states, packet construction, runtime/A-B evidence, or server-side historical physics as findings. In particular, the `fallDistance = 0.0F` write in the ability branch is identical in both releases and outside this flag-order claim.

## Canonical source identity and hashes

Prepared ready markers and provenance identify exact client releases remapped with Ornithe Feather Gen2 build 2 (`ornithe-feather`), decompiled with Vineflower 1.12.0 on Java 25.0.3. The pair has separate exact client and mapping artifacts.

| Version | client SHA-256 | Feather merged mapping JAR SHA-256 | Feather Tiny SHA-256 | source-manifest SHA-256 | provenance SHA-256 |
|---|---|---|---|---|---|
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb100f8e2926eb95dec44` | `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` | `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` |

Relevant source-file SHA-256 values below were checked against each version's `ornithe-feather.sources.sha256` manifest and local bytes:

| Relative source file | 1.11 | 1.11.1 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
