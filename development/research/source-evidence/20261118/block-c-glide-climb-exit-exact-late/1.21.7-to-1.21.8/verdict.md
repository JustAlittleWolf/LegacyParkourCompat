# Independent source audit: 1.21.7 → 1.21.8 Elytra climb exit

## Verdict

**Accepted as a bounded no-difference result.** For an already active, locally authoritative player Elytra state on a dry tick, the inspected ordinary-feet and ladder/vine climbable routes, their velocity gates, and the order of the first movement versus climb-exit flag writes are identical in 1.21.7 and 1.21.8. This supports only the supplied slice; it does not establish full-pair coverage, implementation acceptance, or runtime parity.

## Immutable input

- Audited report: `c-glide-1217-1218-actual-climb-exit/report.md`
- Report SHA-256: `8e11cd4519f36bf6383d7028066eb10dbafcb28b460a71eb46a2bdb86eaa8030`

## Canonical source and manifest identity

Sources are in the current checkout at `decompiled_minecraft/1.21.7/mojmap/` and `decompiled_minecraft/1.21.8/mojmap/`; both sides are Mojmap. For 1.21.7, the adjacent `.decompile-provenance.json` SHA-256 is `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`, and its source table contains the four digests below. For 1.21.8, adjacent `mojmap.ready.json` SHA-256 is `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; it marks version `1.21.8`, mapping `mojmap`, status `ready`, and names `mojmap.sources.sha256`. That adjacent manifest SHA-256 is `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, matching the ready marker, and records 6,105 source files. The ready marker's recorded output path `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.8\mojmap` is absent in this checkout; the current canonical source tree and adjacent manifest are present, and direct file hashes match the manifest. This is a relocated output-path condition, not a source digest mismatch.

| Mojmap source file | 1.21.7 SHA-256 | 1.21.8 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |

## Audited source claims

Each reference below applies identically to both releases; the direct source hashes above are equal, and the checked bodies match.

- **Already-active input boundary:** `LocalPlayer.aiStep()` has the jump/no-climbable/start-flight request at `LocalPlayer.java:770–772`, then stores `wasFallFlying` at `:774`; the active-flight route therefore does not require re-running start-flight logic. `LocalPlayer.tick()` calls `super.tick()` only after `hasClientLoaded()` (`:197–202`).
- **Pre-travel state and updater:** `LivingEntity.aiStep()` applies the player deadband `horizontalDistanceSqr() < 9.0E-6`, zeroing x/z, and independently zeros y when `abs(y) < 0.003` (`LivingEntity.java:2738–2761`). It calls `updateFallFlying()` before dispatching `travel()` (`:2804–2819`). `updateFallFlying()` always calls `checkFallDistanceAccumulation()`, but its `canGlide()` cancellation and `setSharedFlag(7, false)` are nested under `!level().isClientSide` (`:2871–2890`); that cancellation does not clear the flag in the client route under review.
- **Local authority:** `Entity.isLocalInstanceAuthoritative()` chooses `isLocalClientAuthoritative()` on the client (`Entity.java:3335–3342`); `canSimulateMovement()` and `isEffectiveAi()` both return that authority result (`:3349–3355`). The ordinary movement dispatch requires both (`LivingEntity.java:2815–2819`). The verdict assumes the named local player is the locally authoritative client instance; it does not assert the same simulation for a remote or server-side entity.
- **Dry route fork:** With fluid travel excluded and Elytra active, `LivingEntity.travel()` selects `travelFallFlying()` (`LivingEntity.java:2245–2253`). At `:2353–2366`, `onClimbable()` sends the climbable route through `travelInAir()` and calls `stopFallFlying()` only after that returns. Ordinary feet instead calculate the Elytra update, write velocity, then perform `move(MoverType.SELF, ...)` (`:2358–2361`).
- **First movement and exit ordering:** The air fallback calls `handleRelativeFrictionAndCalculateMovement()` (`LivingEntity.java:2276–2281`); that helper applies relative input, passes velocity through `handleOnClimbable()`, writes it, and makes the first move (`:2446–2455`). For a climbable witness, `handleOnClimbable()` resets fall distance, clamps x/z to `[-0.15F, 0.15F]`, limits downward y to `-0.15F`, and retains the player sliding gate (`:2473–2487`). Only after this first move returns does `stopFallFlying()` set shared flag 7 true, then false (`:2369–2372`). The ordinary-feet first move occurs directly at `:2361` without this climb-exit toggle in that branch.

## Bounds

This is source-only and bounded to the active dry route, pre-travel velocity deadband, update/cancellation and authority gates, climbable air fallback, first-move order, and stop-flag order. The ladder/vine feet condition is the supplied witness; this audit did not expand block/tag membership or source discovery beyond it. Excluded: Elytra start and final-tick behavior, custom equipment, fluid/geometry comparisons and other tick operations. No runtime parity claim.

