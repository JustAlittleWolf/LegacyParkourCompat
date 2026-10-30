# Product reconciliation 45: 1.20.4 → 1.20.5 accepted movement findings

Status: completed read-only product reconciliation. This report is not implementation, runtime parity evidence, or pair-completion evidence.

Product snapshot: commit `246fe89a7707c02ec2016af96c7e9a48ee8f8439` (`fix/resume-movement-20261030`). The working tree had unrelated edits before this reconciliation; no product code, source evidence, or backlog files were changed here.

## Bound source evidence

Inputs are the accepted r2 drafts and review reports in `.native-source/20261030/1204-1206-pool44/`:

| Input | SHA-256 |
|---|---|
| `travel-fluid/fluid-eye-sampling-height.md` | `e4e4eec9e4f8f0bf0d7931ebdc572ecac3dc9d49fc8d0e6112e04f6852b279f4` |
| `review-fluid-eye/verdict-r2.md` | `ef9d31bb583f1b339b346de72d5baa44dbedc7c45fe29ffc46062fad50a69d7e` |
| `jump-pretravel/jump-strength-attribute-and-execution-gate.md` | `b9e0253bde8af1afad34b3d88fd99dca3c4f59d7d2a11131dd9cd482c71b11ad` |
| `jump-pretravel/grounded-flight-toggle-jump.md` | `ffe9868aec46f3d3b42b98a2afdfbd3dbcc02c6985eff03eebec32649baee08e` |
| `review-jump/independent-review-r2.md` | `82005825a83f9d01f9d360b8a647974f8ea8dd5e61913eb770426328905c9b9d` |

The reviewed evidence is bounded to the accepted 1.20.4/1.20.5 source differences and 1.20.6 later context. It does not assert full-pair coverage or runtime parity.

## Resolver interpretation

`ParkourVersion` groups 1.20.2, 1.20.3, and 1.20.4 as `V1_20_2`; 1.20.5 and 1.20.6 are `V1_20_5`. The resolver selects the closest `emulates` value greater than or equal to the selected profile, independently per hook. Therefore the existing `V1_20_2` implementations below apply to a 1.20.4 profile, while they are ineligible for a 1.20.5 profile. The latter gets native behavior for these operations unless it has an applicable later implementation. Existing later native fences preserve a native fallback and are not evidence of older-profile equivalence.

## Accepted finding reconciliation

### Fluid eye sampling height — covered

- The independently resolved seam is `PlayerEyeFluidSampleHook` (`fluid.player_eye_sample`), called by `EntityFluidInteractionMixin` while redirecting the eye-Y read in `EntityFluidInteraction.update`.
- `change/v1_20_2/PlayerEyeFluidSample` returns `eyeY - 0.11111111F` and is selected for 1.20.4. It reproduces the accepted 1.20.4 sample coordinate.
- For 1.20.5, that older implementation is ineligible; there is no 1.20.5-or-later `PlayerEyeFluidSampleHook` implementation. Native eye-Y sampling therefore remains in effect, matching the accepted 1.20.5/1.20.6 boundary.
- Preserve the finding's tick delay in any future implementation review: `Player.tick` reads the preceding eye-fluid tags before `Entity.baseTick` refreshes them; the sample changed during one tick is consumed by the following tick's underwater/swimming state path. The existing hook changes only the sampled coordinate; this reconciliation does not claim runtime parity for the subsequent swimming/travel effects.
- `EyeFluidCacheHook` changes are a separate cache operation and are not additional coverage for this sample-height finding.

### Jump-strength computation / low-power executor gate — covered for the bounded transition

- The executor gate has an explicit seam: `JumpFromGroundAdmissionHook`, called by `LivingEntityJumpAdmissionMixin` at the native `1.0E-5F` threshold.
- `change/v1_20_2/JumpFromGroundAdmission` replaces the current threshold with `Float.NaN`; under the native negated `power <= threshold` guard this admits every power, including nonpositive values, matching the unconditional 1.20.4 response. It is selected for `V1_20_2` (including 1.20.4).
- `change/v1_19_4/JumpFromGroundAdmission` is an identity fence for earlier profiles; it is not selected for `V1_20_2` and does not interfere with the 1.20.4 association.
- A 1.20.5 profile cannot resolve the `V1_20_2` override, leaving the native positive-power gate active. This preserves the accepted `power > 1.0E-5F` admission condition and its consequence that the sprint impulse is also skipped when the executor rejects the jump.
- The accepted new generic jump-strength attribute is modern-only at the 1.20.4 boundary. On the normal current Player defaults (`JUMP_STRENGTH = 0.42F`, `SCALE = 1.0F`), current 26.2 `LivingEntity.getJumpPower(float)` computes `(float)attribute * multiplier * blockJumpFactor + jumpBoostPower`; with the native default values this reduces, in the same float operation order, to 1.20.4's `0.42F * blockJumpFactor + jumpBoostPower`. No extra 1.20.4 `JumpPowerHook` is needed for that default path. Retaining non-default modern jump-strength/scale attributes is consistent with the project rule that post-profile features retain modern behavior.

### Grounded flight-toggle jump — covered

- The independent seam is `FlightActivationJumpHook`, wrapped in `LocalPlayerMixin` around the native `LocalPlayer.jumpFromGround()` call.
- `change/v1_20_2/FlightActivationJump` intentionally does not invoke the supplied native callback. At 1.20.4 it suppresses only the newer grounded flight-activation jump while leaving the surrounding input, flight eligibility, toggle, and ability-update order native.
- At 1.20.5 the `V1_20_2` change is ineligible; no later flight-activation override exists, so the native call is retained. The call remains subject to the accepted “flight became enabled and player is grounded” gate.
- When the 1.20.5 flight-toggle call reaches the ground-jump executor, the native positive-power threshold remains active. The 1.20.4 profile instead combines its older no-flight-jump path with the unconditional executor gate for ordinary ground jumps.

### Sprint-jump impulse precision — already covered dependency of the jump finding

- `SprintJumpImpulseHook` is independent of the power and admission hooks and is dispatched from `LivingEntityMixin` at the sprint-impulse argument.
- `change/v1_20_2/SprintJumpImpulse` computes each horizontal product with `0.2F`, retaining historical float product precision for the 1.20.4 profile. It runs only when the native sprint-jump branch is reached; the executor admission override does not bypass native sprint eligibility.
- For 1.20.5, the older change is ineligible and current native double-`0.2` multiplication remains, preserving the newer product. The source finding's positive-power gate remains upstream of that branch.

## Gaps and disposition

No missing minimal seam or missing 1.20.4/1.20.5 cutover was found for these accepted findings. Existing per-operation hooks cover the old-side sample, ground-jump admission, flight-toggle invocation, and sprint impulse precision; the 1.20.5 side uses native behavior. The normal 1.20.4 jump-power expression is already produced by the current native calculation at default attributes, while the introduced modern attribute remains native by scope.

No code, source evidence, findings, or backlog edits are proposed by this reconciliation. No build, test, game launch, or runtime parity claim was made.
