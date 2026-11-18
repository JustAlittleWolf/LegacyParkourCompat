# Independent audit — 1.13.1 → 1.13.2 swim-look vertical adjustment

## Pre-review freeze (recorded before opening candidate report)

Scope: only local player swim-look vertical adjustment in `PlayerEntity` override, including exact math/casts and order relative to inherited movement. Fluid and pose predicates are checked only as applicability dependencies. No broader swim physics is audited.

Candidate under review: `c-swim-pitch-vertical-1131-1132-source/report.md`, SHA-256 `f49df3bfbbaaf8c56b0bdea333e72d5f61f94f548f30a353e8ab78a2b4e18e94` (expected digest supplied by assigning orchestrator; confirmed from bytes before opening).

Canonical exact source identities, read-only:

- 1.13.1 Ornithe Feather ready marker SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`; provenance SHA-256 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`; source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; artifact-manifest SHA-256 `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c`.
- 1.13.2 Ornithe Feather ready marker SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; artifact-manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.

Original relied source bytes, SHA-256 (each matched its line in the canonical source manifest):

| Release | Source file | SHA-256 |
|---|---|---|
| 1.13.1 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.1 / 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` (identical) |
| 1.13.1 / 1.13.2 | `net/minecraft/fluid/Fluid.java` | `55afdc25f330663746c253e29a0e272666c595f17cacbc78785b0a85b00f094e` (identical) |
| 1.13.1 / 1.13.2 | `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` (identical) |

Independent actual-real-move witness frozen before candidate access: `c-swim-pitch-vertical-1132-262-destination-witness/report.md`, SHA-256 `676d98d08a99735c2cca1c5e5642945d0d8806f1dedc39af741d5e4c00ded76d`. It is a separate downstream witness; its supported scope is the 1.13.2→26.2 ordinary local-player swimming path and it does not establish this pair's claim.

## Audit verdict

Pending exact-source retrace.

## Independent source verdict — ACCEPT (no difference)

Signed review scope: only the swim-look vertical correction executed in the `PlayerEntity.moveRelative` override and its order before inherited movement. Fluid lookup and player swimming-state/pose conditions are applicability dependencies only. Reviewer: independent source auditor, Windows PowerShell, 2026-11-18.

I independently reopened only the canonical read-only Feather sources after the pre-review freeze. In both releases, the complete method is `PlayerEntity.moveRelative(float forwards, float sideways, float upwards)`, lines 1442–1468 (the preceding line 1441 is `@Override`). The paired method text is byte-for-byte identical after UTF-8/LF extraction: 1,168 bytes, SHA-256 `777895ca04681fd343c6786105350b592ab47abdfdee8f229bbf7afe750e4f83`. The containing source files differ, but not this method.

The exact common swim branch first reads `g` as the `double` look-vector Y, selects `h = 0.085` iff `g < -0.2` else `0.06`, then tests `g <= 0.0 || jumping || !fluidState.isEmpty()` at `BlockPos(x, y + 1.0 - 0.1, z)`. If true, it performs `velocityY = velocityY + (g - velocityY) * h`, with the same double expression and left-to-right arithmetic. There is no cast or reordered operation in this correction. The branch comes before `super.moveRelative(...)`; for the ordinary swimming path, the flying branch is false, so the inherited call follows the correction. The unchanged swim flag update allows the ordinary sprinting/submerged, non-riding player case; `PlayerEntity.isSwimming()` also excludes flying and spectators. These observations establish applicability only; they do not accept fluid physics or pose behavior.

The inherited local-player tick path is reachable: `LocalClientPlayerEntity.tick()` calls `super.tick()` when loaded, `LivingEntity.mobTick()` calls virtual `moveRelative(...)` in its travel section, and the supplied independent real-move witness covers a concrete subsequent ordinary swimming movement. That downstream witness is bound to `676d98d08a99735c2cca1c5e5642945d0d8806f1dedc39af741d5e4c00ded76d`; it is corroborative reachability evidence only and not evidence for a 1.13.2→26.2 patch change.

### Audit note on the candidate's method digest

The candidate's claimed method digest `46e90d04517eb0ab8ec115569d3e1b86e548e47a83bed07babcc04db4bd7b0b6` does not reproduce from its stated complete-method range 1441–1468 under UTF-8/LF. The independently calculated digest for the complete declaration/body range 1442–1468 is `777895ca04681fd343c6786105350b592ab47abdfdee8f229bbf7afe750e4f83` on both releases. This is a digest/reporting defect only: direct comparison confirms exact equality, so it does not overturn the bounded no-difference result. Acceptance is bound to the frozen report SHA and source manifests above, not to the unreproduced digest.

Limits: this verdict accepts only the bounded no-difference finding and its needed applicability. It does not freeze complete 1.13.1–1.13.2 movement-pair coverage, prove a patch introduction boundary, audit swimming-fluid acceleration/drag, collision, or establish runtime parity.
