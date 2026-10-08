# MC1204-1206-02 — sprint-jump precision implementation

## Finding and accepted evidence

- Finding: immutable `MC1204-1206-02` snapshot at `b168d90c05e813d6dff2ccd97090dc1602d9d418`, SHA-256 `9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315`; endpoint source review accepted it in `581b5bd2cb44981c4889eaf05ea739134e332b87`.
- Exact boundary memo: `e277edd19eb8f8ce63681ab7985a5d92df8a259f`, SHA-256 `a97e20b5abaa131f9ec090d1d186eae63592d839e8cd9c3c254b80acdea8de82`. Its blind source review `1e5de3b32966e8766cdcf9f7ad56a9278fb79bb0` accepted the bounded 1.20.4/1.20.5/1.20.6 source-boundary claims.
- The 1.20.4, 1.20.5, and 1.20.6 ready source markers, manifests, and cited source hashes were revalidated in that review. For the changed method, 1.20.4 `LivingEntity.java` SHA-256 is `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; 1.20.5 and 1.20.6 share `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- The accepted source boundary is exact within the inspected interval: the old sprint multiplication is `Mth.sin(angle) * 0.2F` / `Mth.cos(angle) * 0.2F` in 1.20.4; the 1.20.5 and 1.20.6 bodies use double literal `0.2`. The yaw-to-float angle and trig lookup are unchanged. The cutoff is a separate source change and is not implemented here.
- Scope authorization: `6fb3cc75e33c00959930a2a7b5e8516e9d95f1e3`, final scope-proof tip `9acf4bb097ce025b5dbc9c042ef012d5cad54f63`, classifies the sprint arithmetic as an eligible old-input movement delta and the low-power cutoff as ineligible for vanilla 1.20.4 player jump-power inputs. The accepted boundary review itself does not establish input eligibility or implementation scope; those are covered by this separate scope proof.

## Implementation

- Code commit: `4aa5be97b5495b59736381c9e05e4ca9f6e32a1b` (`fix: emulate 1.20.4 sprint jump precision`).
- Change: `change/v1_20_2/SprintJumpImpulse.java` implements the existing `SprintJumpImpulseBehavior`, computes the same float yaw angle, and multiplies both legacy trig values by `0.2F`. The version's existing `MovementChanges` provider registers it.
- Hook: existing `LivingEntityMixin` modifies the `Vec3` argument to `LivingEntity.addDeltaMovement(Vec3)` in `jumpFromGround()V` and dispatches through `SprintJumpImpulseBehavior`. It passes the vanilla vector through unchanged when no behavior resolves. No new mixin or shared-hook change was necessary.
- Historical registration: `@MovementChange(emulates = ParkourVersion.V1_20_2)` is deliberate. The enum groups 1.20.2–1.20.4 as `V1_20_2` and starts `V1_20_5` at 1.20.5. With `ChangeResolver`'s closest eligible change rule, the new implementation resolves for `V1_20_2`; `V1_20_5` excludes this older change and therefore keeps its native double multiplication. Earlier `V1_19_4` selections continue to choose the nearer existing `V1_19_4` implementation. `CURRENT`, disabled emulation, and non-player entities have no resolved behavior through `MovementRuntime`, so the vanilla argument is returned.
- The implementation preserves the historical operation order and float product before widening to the `Vec3` double components. It does not alter jump-power cutoff handling, jump-strength attributes, vertical velocity, impulse flags, or non-player movement.

## Verification and status

- Static inspection confirmed the provider registration, annotation, existing dispatch/fallback, `ParkourVersion` grouping, and resolver selection rule. `git diff --check` passed before the code commit.
- No tests, build, Gradle task, decompilation, client, server, TAS, Gym, Docker, or runtime comparison was run, as explicitly directed. Compilation and trajectory parity remain unverified.
- Pair discovery remains `PARTIAL`; the accepted finding and boundary memo do not claim full pair coverage. The cutoff and non-default jump-strength attribute remain outside this implementation. Runtime/TAS validation remains pending.
- Current-main merge: merged `main` at `a5c191df557cd152d578150c689227897ac33256` before implementation; no jump-hook, resolver, version-group, or sprint-impulse semantic overlap was found in the incoming commits. The merged main changes concern sleep safety, shallow-water current, and source/reconciliation documentation.
- No implementation-derived information was sent to source-only owners. Independent implementation review is still pending.
