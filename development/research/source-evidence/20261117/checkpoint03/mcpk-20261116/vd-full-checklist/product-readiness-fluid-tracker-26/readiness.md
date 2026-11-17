# Product readiness: LocalPlayer water-current cutoff bypass

## Evidence consumed

- `build/later-source-20261116/1.21.11--26.1.2/fluid_travel/coverage.md` — SHA-256 `9054D73FB7CACBD050A2D6F7C8B774E54BB476D845E24F0A61D1AD7CFBE238A0`.
- `development/research/source-evidence/20261117/checkpoint02/later-source-20261116/1.21.11--26.1.2/fluid_travel_audit/audit.md` — SHA-256 `99C1CDFA16E5DC4D95400B107A75CC13E08B302017C81552597C4FE5F5B245E1`.
- `build/mcpk-20261116/vd-full-checklist/fluid-tracker-boundary-followup/fluid-tracker-boundary.md` — SHA-256 `4FFBA6E7EF8F5AFFC80AFA485693BA0B8C1FCE84FF8934B64F8289329CF0AA9D`.

No source re-audit performed. The supplied endpoint acceptance and boundary packet establish default flowing-water reachability and the cutoff's first stable release at 26.1.

## Product correspondence

**Result: covered; no direct product gap found for the accepted water-current cutoff operation.**

- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_11/ShallowWaterCurrentCutoff.java` registers `ShallowWaterCurrentCutoffHook` at `ParkourVersion.V1_21_11`. Its hook returns true at positive tracked water height, restoring admission of nonzero current for the older profile.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java` wraps the water `applyCurrentTo` call only for a non-passenger, pushed-by-fluid `LocalPlayer`, and enters the cutoff context only when the resolved hook says the water height is positive. `EntityFluidCurrentMixin` changes the modern squared-length cutoff only within that entity-scoped context; any nonzero accumulated vector passes while zero remains rejected.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` selects the closest change whose emulated version is greater than or equal to the selected profile. `ParkourVersion` orders `V1_21_11` before `V26_1`, and groups `26.1`, `.1`, and `.2` under `V26_1`. Therefore the V1_21_11 bypass resolves for selected 1.21.11, but not for V26_1+.
- `MovementRuntime.appliesTo` requires a client-side `Player` with enabled emulation. The native client/current profile and logical server do not receive a historical hook. If no historical hook resolves, the mixin uses the original current-length check; fluid reads and flow calculation also retain original-call fallback when no scoped hook resolves.
- Adjacent `FluidCurrentDepthChange` is also emulated at V1_21_11 and corrects depth independently, as its comment documents. It is separate from the accepted cutoff operation and does not indicate a missing cutoff provider.

## Boundary

This is a static product correspondence result for the accepted LocalPlayer water-current cutoff finding. It does not claim complete fluid-current implementation, pair-wide coverage, or runtime parity; the source packet's remaining custom-tag and other exclusions remain in force.
