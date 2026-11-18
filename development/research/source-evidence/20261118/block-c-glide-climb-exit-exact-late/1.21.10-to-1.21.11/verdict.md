# Independent blind source audit verdict — Elytra climbable exit predicate

## Frozen input

Audited only the supplied bounded report:

- `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-12110-12111-actual-climb-exit/report.md`
- SHA-256 of exact input bytes at audit start: `c06d237d049085aacd812f92f1d32bd4911bff05cfc3d3a676339b0b97f9d723`.

## Source identity check

Canonical local sources are `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`. Both ready markers identify the requested exact release and `mojmap`, and their recorded source-manifest hashes match the local manifest files. Each manifest's entries for the examined `LivingEntity.java` and `Player.java` match the actual local file hashes below. The reports' ready-marker and source-manifest identities match these files.

| Release | Provenance SHA-256 (local) | Ready marker SHA-256 (local) | Source manifest SHA-256 (local) | `LivingEntity.java` SHA-256 | `Player.java` SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.21.10 Mojmap | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| 1.21.11 Mojmap | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |

The supplied report's 1.21.10 provenance SHA-256 is inaccurate: it gives `9a1db4c050c3caa28a1313f89daeda9b28feaa5f8afba42cab798b47d7ad77c`, while the canonical local file hashes to `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`. The local ready marker and source-manifest identities independently match their local files. The supplied 1.21.11 provenance hash and both `LivingEntity.java`/`Player.java` source hashes match.

## Bounded source verdict

**Predicate-difference claim: confirmed, conditional on the stated in-block-state predicate. Movement consequence: confirmed only with the correction below. Applicability to the supplied ladder/vine witness: unresolved because this audit does not establish that witness's `CAN_GLIDE_THROUGH` membership.** This is an operation-level verdict, not a full pair inventory or runtime-parity result.

- `LivingEntity.onClimbable()` in 1.21.10 reads `getInBlockState()` and then checks `CLIMBABLE`, followed by the usable-trapdoor case (lines 1662–1678). The 1.21.11 method adds `isFallFlying() && state.is(BlockTags.CAN_GLIDE_THROUGH)` and returns false before those same checks (lines 1669–1687). This establishes the reported ordered early-false gate. It does not establish tag contents or any block shape behavior.
- The `Player.onClimbable()` override is logically unchanged: abilities flight returns false; otherwise it delegates to `super.onClimbable()` (1.21.10 lines 1954–1957; 1.21.11 lines 2042–2045). Under the stated non-flying-abilities player precondition, that delegation reaches the new `LivingEntity` gate.
- `LivingEntity.aiStep()` checks `isFallFlying()` and calls `updateFallFlying()` immediately before constructing travel input and invoking `travel()` when movement can be simulated (1.21.10 lines 2834–2849; 1.21.11 lines 2960–2975). `updateFallFlying()` performs `checkFallDistanceAccumulation()` and places its `canGlide()` cancellation and remaining work inside `if (!level().isClientSide())` (1.21.10 lines 2901–2921; 1.21.11 lines 3027–3047). Thus this pre-travel update does not clear the already-active glide on the scoped local-client path. The chest-slot eligibility predicate is unchanged in `canGlideUsing` (1.21.10 lines 3704–3711; 1.21.11 lines 3835–3842); no start/gear transition is inferred here.
- For the stated dry path, the fluid branch falls through to `travelFallFlying()` in both releases (`travel`: 1.21.10 lines 2275–2284; 1.21.11 lines 2309–2321). If `onClimbable()` is true in 1.21.10, `travelFallFlying()` calls `travelInAir(input)` and then `stopFallFlying()` (lines 2383–2387). **Correction to the supplied report:** `travelInAir()` only computes and writes delta movement (1.21.10 lines 2306–2329); it contains no `move()` call. `stopFallFlying()` then writes shared flag 7 true and false in that order (lines 2399–2402). The climbable arm therefore does not perform the report's claimed ordinary-air move before clearing the flag.
- In the 1.21.11 `travelFallFlying()` method, if the new gate returns false, the other arm computes fall-flying movement, writes the resulting delta movement, and calls `move(MoverType.SELF, ...)` (lines 2441–2454). It does not call `stopFallFlying()` in that arm. So for a state satisfying both the old climbable route and the new `CAN_GLIDE_THROUGH` early-return condition, the source difference is: 1.21.10 recalculates air velocity then toggles the flag without a `move()` in that arm; 1.21.11 retains the glide branch, performs its explicit `move()`, and does not run that late flag toggle. This is a conditional source consequence, not a predicted trajectory.

## Bounds

No claim is made about exact tag inventory or membership of the supplied witness; `CLIMBABLE`/`CAN_GLIDE_THROUGH` overlap is not established here. No independent fluid-routing, environmental-provider, shape, input/start, gear-transition, or glide-final-tick review was performed. The examined dry-fluid check was only what was needed to select the travel branch. No tests, builds, source generation, launches, Git/history inspection, or network access were used.