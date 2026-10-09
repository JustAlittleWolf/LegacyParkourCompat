# F3 R2 implementation technical review

**Verdict: REQUEST CHANGES.** Reviewed code candidate `a5e43adb6251a867f4ac2b9f05ecdf73ca65564f` against base `cb11257e593987c68be98a7dbda8bbea7d5e77fd`, plus final reconciliation `93b4839c4a9d0234a436c1f2072de6052ae5e4a9`. The candidate changes six production files (59 insertions, 6 deletions); the final reconciliation changes one Markdown file (3 insertions, 2 deletions). One likely important applicability issue remains. The cutoff bypass math, target call descriptor/order, registration and native fallback otherwise match the accepted witness. No first-changed-release claim is supported or made by this review.

## Bound inputs

- Code: commit `a5e43adb6251a867f4ac2b9f05ecdf73ca65564f`, parent/base `cb11257e593987c68be98a7dbda8bbea7d5e77fd`.
- Final reconciliation: commit `93b4839c4a9d0234a436c1f2072de6052ae5e4a9`, path `workflows/fix-implementation/reconciliations/F3-nether-lava-shallow-overlap-r2-2026-10-09.md`, blob `ea7601254e1964029beaaff9c14e9f1bca38c3aa`, raw SHA-256 `4b29cffee79b01e89495e99c917ca502710ff2d34d760d3a05fa7574aa055b11`.
- Accepted source finding: commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`, path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`, blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`.
- Independent source acceptance: commit `a76e28419f6b9767eab2e78147883c5459ed60c4`, path `workflows/source-boundary-reviews/2026-10-09-lava-shallow-overlap-r2-review.md`, blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`. Its decision is bounded to the corrected standing, unmounted, non-flying Player Nether LAVA witness. It leaves mounted fluid boxes, other lava heights/vectors and the first changed release in `(1.21.11, 26.1.2]` unresolved.

## Direct 26.2 target verification

I used the canonical ready root `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\26.2\unobfuscated` because the review checkout does not contain the external ready tree. `unobfuscated.ready.json` identifies version `26.2`, mapping `unobfuscated`; its SHA-256 is `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`. The source-manifest and artifact-manifest hashes rechecked against that marker are `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` and `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.

The following direct file hashes match their entries in `unobfuscated.sources.sha256`:

- `Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- `EntityFluidInteraction.java`: `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`.
- `Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.

In target `Entity.updateFluidInteraction()Z`, Water is the first `applyCurrentTo` call (ordinal 0) and LAVA is the second (ordinal 1); the LAVA scale is selected from `FAST_LAVA`. `EntityFluidInteraction.getFluidHeight` returns the selected tracker's measured height. Its update uses `Entity.getFluidInteractionBox`, and its tracker accumulates current only when `ignoreCurrent` is false. `Player.isPushedByFluid()` returns `!abilities.flying`. In `Tracker.applyCurrentTo(Entity,double)`, `currentCount != 0` short-circuits first, then the strict `accumulatedCurrent.lengthSqr() < 1.0E-5F` gate runs before Player averaging, scale, minimum impulse and velocity addition. The redirect's nested target descriptor and the LAVA ordinal therefore match the 26.2 operation.

## Finding

### [P2] Keep the cutoff bypass inside the accepted non-mounted applicability

**Confidence:** likely. **Severity:** important.

At `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java:163-168`, the LAVA branch requires the LAVA tag, `entity instanceof Player`, and a resolved hook whose only predicate is `0 < interaction.getFluidHeight(LAVA) < 0.4`. It does not exclude a mounted Player. When that branch runs, `FluidCurrentCutoffContext` activates the `EntityFluidCurrentMixin` redirect, which replaces the target tracker's squared length with positive infinity and therefore bypasses the strict weak-current return whenever a current exists.

This is reachable for a non-flying Player riding a vehicle: target `Player.isPushedByFluid()` has no passenger exclusion, `Entity.updateFluidInteraction()` still calls the LAVA operation, and `Entity.getFluidInteractionBox()` passes the passenger box through `vehicle.modifyPassengerFluidInteractionBox(...)`. The accepted F-3 source review expressly leaves mounted fluid boxes unresolved. Thus this code changes the cutoff for a mounted-player box even though the accepted evidence covers only an unmounted witness; whether A and B agree for that mounted path has not been established. The final reconciliation calls the implementation bounded to the accepted witness, but the condition is broader.

**Smallest correction:** keep the target-native cutoff for mounted players by adding a passenger exclusion to the new LAVA activation, unless a separately accepted source finding establishes the mounted behavior. This preserves the accepted unmounted witness and avoids extending the finding into the open mounted-box slice.

## Six review angles

1. **Requirement:** The candidate implements the accepted unmounted Player witness. The mounted activation above exceeds its accepted applicability; otherwise no requested movement scope was added.
2. **Correctness and edge cases:** The `0 < height < 0.4` gate is strict on both ends. Positive infinity bypasses only the target's strict squared-length comparison; `currentCount == 0` still short-circuits, and the original vector, scale, Player minimum-impulse and velocity operations remain intact. The mounted edge is the finding above.
3. **Completeness:** The dedicated hook is declared, registered once in `MovementChangeCatalog` at `V1_21_11`, and looked up only at the LAVA call. No second registration or stale `ShallowWaterCurrentContext` reference remains. The missing passenger exclusion is recorded above.
4. **Conventions and duplication:** The mixin remains a thin dispatcher. Renaming the scoped helper to `FluidCurrentCutoffContext` correctly shares the existing WATER bypass mechanism; its `finally` restores a previous nested thread-local value. No duplicate fluid loop or per-version provider was introduced.
5. **Permissions and visibility:** No permission or access boundary changes. Historical movement selection intentionally changes player velocity on the matched path. `MovementRuntime.find` leaves non-player and disabled/native profiles without a change; all bypass-false paths delegate to the original call.
6. **Security:** No new externally supplied data reaches command, path, logging or deserialization sinks.

## Limits

`ChangeResolver` returns no historical changes for CURRENT; for other profiles it admits registrations at or after the selection and chooses the closest. Thus V1_21_11 applies to selections through 1.21.11, while V26_1 (26.1/26.1.1/26.1.2) excludes this older entry. This describes resolver routing only, not a source-derived first-change boundary. The accepted finding leaves first change unknown within `(1.21.11, 26.1.2]`; this review makes no earlier-cutover claim. Other lava heights/vectors, mounted boxes and runtime parity were not verified. No build, tests, client/server, TAS, Gym or Docker activity was performed.
