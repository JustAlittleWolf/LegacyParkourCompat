# Independent source audit: active glide fluid fall-distance cap

Status: **SUPPORTED, conditionally admissible** for the 1.21.1 → 1.21.2 operation. The audit accepts the direct source-state delta for a locally controlled client with an already-active shared fall-flying flag. It does not establish an incoming trajectory or its timing. Water is not a positive residual-cap witness.

Machine audit-input digest (SHA-256, UTF-8 of the exact input identity lines listed below): `beaf0921c2da7e5e89857debc8cd1fedab80f303d0781f8e090c9e192d8780bd`.

## Verdicts

- **Core call-site/order claim — ACCEPT.** In 1.21.1 `LivingEntity.tick` calls `super.tick` before travel setup (`LivingEntity.java:2319–2321`); `Entity.tick` calls `baseTick` (`Entity.java:415–419`). The base tick updates fluid state first (`Entity.java:440–441`), halves fall distance for lava (`465–468`), and resets fall distance on water contact (`Entity.java:1250–1263`). `LivingEntity.updateFallFlying` does not cap (`LivingEntity.java:2716–2741`). Instead the cap is inside the later fall-flying travel arm (`LivingEntity.java:2099–2100, 2131–2153`), which fluid branches precede, so qualifying ordinary water/lava travel bypasses it.
- **1.21.2 lava cap — ACCEPT, conditional on explicit state witness.** `LivingEntity.tick` calls `updateFallFlying` only when `isFallFlying()` (`LivingEntity.java:2755–2766`); that method calls `checkSlowFallDistance` first, while shared-flag eligibility clearing is inside `!level.isClientSide` (`2814–2819`). `isFallFlying()` reads shared flag bit 7 (`LivingEntity.java:3266–3268`), and the client-side update does not clear that flag. It therefore caps on the client with the active pre-existing flag before the subsequent effect reset and before `travel`. `checkSlowFallDistance` writes `1.0F` exactly when current `getDeltaMovement().y() > -0.5` and current `fallDistance > 1.0F` (`Entity.java:2484–2488`). Base ticking has already halved the lava fall distance (`Entity.java:447–479`). Thus a witness with post-halving distance `> 1.0F`, vertical delta `> -0.5`, and neither Slow Falling nor Levitation (which reset distance after the cap, `LivingEntity.java:2762–2766`) leaves the cap value for the following travel call. This is only a conditional state transition, not a trajectory claim.
- **Ordinary lava + foot-cell climbable + active flag — ACCEPT as a compatible predicate witness, with no trajectory inference.** Lava contact is produced by `updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, ...)`, whose deflated body AABB is scanned over intersecting cell coordinates (`Entity.java:1295–1300, 3219–3245`); `isInLava` reads the resulting positive height and excludes first tick (`Entity.java:1399–1401`). `onClimbable` independently reads `blockPosition()` / `getInBlockState()` and checks the climbable tag (or ladder trapdoor) (`LivingEntity.java:1561–1576`). The 1.21.2 lava branch is the non-water arm of `travelInFluid`; it contains no climbable gate (`LivingEntity.java:2216–2266`). Neither the cap predicate nor lava-route selection requires `onClimbable`. A foot-cell climbable state can therefore coexist with body-AABB lava overlap as a source-level condition; the sources do not assert a particular map placement or incoming motion.
- **Water + climbable sibling — REJECT as a positive residual witness.** The same base fluid updater checks water first and, on detected water overlap, calls `resetFallDistance()` before setting `wasTouchingWater` (`Entity.java:1295–1317`). `isInWater()` reports that flag (`1252–1254`), and the combined fluid route then selects the water arm before lava (`LivingEntity.java:2181–2187, 2220–2245`). A simultaneously true climbable predicate does not restore a positive pre-travel distance; its water response is checked only after the first move (`2240–2245`).
- **Adjacent source identity — ACCEPT for 1.21.2 → 1.21.3 exact alias of the two checked files; ACCEPT behavioral continuation, not alias identity, for 1.21.4.** SHA-256 of 1.21.2 and 1.21.3 `LivingEntity.java` and `Entity.java` is byte-identical per file. 1.21.4 hashes differ, but the audited operation remains: active-only pre-travel `updateFallFlying` cap, then fluid-first travel routing; its exact call sites are `LivingEntity.java:2190–2198, 2767–2777, 2825–2829` and `Entity.java:453, 480–483, 2512–2516`. No 1.21.4 source alias is claimed.

## Evidence identity and qualification

The supplied comparison was independently hash-checked: `3e1b52e3f5ad10c6008a417133b7e9d1e855d2e34770d23ea8b00badefbaa537`. Local source-file hashes match its table. 1.21.1 and 1.21.4 ready markers identify `mojmap`; the comparison’s 1.21.2 provenance pins release metadata SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45` and client SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, while explicitly noting that the client-mappings artifact is not independently pinned. I accept the reviewed mapped source bodies and its stated release identity; mapping-artifact provenance remains weaker and is not upgraded by this verdict.

Digest input lines (newline-delimited UTF-8):

```
comparison=3e1b52e3f5ad10c6008a417133b7e9d1e855d2e34770d23ea8b00badefbaa537
1.21.1.LivingEntity=324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8
1.21.1.Entity=b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850
1.21.2.LivingEntity=087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52
1.21.2.Entity=a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9
1.21.3.LivingEntity=087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52
1.21.3.Entity=a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9
1.21.4.LivingEntity=e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30
1.21.4.Entity=05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad
```