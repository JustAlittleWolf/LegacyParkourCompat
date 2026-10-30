# Product reconciliation 45: 1.20.4 → 1.20.5 movement findings

Status: product associations reconciled read-only. Source acceptance and runtime parity are separate; the narrowed sprint-precision evidence is pending the source owner's renewed draft/review.

Product snapshot at initial reconciliation: `246fe89a7707c02ec2016af96c7e9a48ee8f8439`. This report supersedes the first reconciliation revision (`1991DBAEFCAD4758B3E868AABBD0CEBE9ABE0F49BD55F0EC18B06F4D9022D442`) after the coordinator narrowed the eligible jump scope. The report-only commit containing the first revision is `75d090815a626641ab9abfc88c8457c3dea13c89`. No product code, source evidence, or backlog files were changed here.

## Bound source inputs

| Input | SHA-256 | Product scope |
|---|---|---|
| `.native-source/20261030/1204-1206-pool44/travel-fluid/fluid-eye-sampling-height.md` | `e4e4eec9e4f8f0bf0d7931ebdc572ecac3dc9d49fc8d0e6112e04f6852b279f4` | Accepted fluid sample-height finding |
| `.native-source/20261030/1204-1206-pool44/review-fluid-eye/verdict-r2.md` | `ef9d31bb583f1b339b346de72d5baa44dbedc7c45fe29ffc46062fad50a69d7e` | Independent accepted verdict |
| `.native-source/20261030/1204-1206-pool44/jump-pretravel/grounded-flight-toggle-jump.md` | `ffe9868aec46f3d3b42b98a2afdfbd3dbcc02c6985eff03eebec32649baee08e` | Accepted, separate flight-toggle operation |
| `.native-source/20261030/1204-1206-pool44/review-jump/independent-review-r2.md` | `82005825a83f9d01f9d360b8a647974f8ea8dd5e61913eb770426328905c9b9d` | Independent accepted verdict for flight-toggle draft |
| `.native-source/20261030/1204-1206-pool44/jump-pretravel/jump-strength-attribute-and-execution-gate.md` | `b9e0253bde8af1afad34b3d88fd99dca3c4f59d7d2a11131dd9cd482c71b11ad` | Historical input only; its new-attribute and executor-gate scope is excluded by the coordinator's later correction |

The product resolver groups 1.20.2–1.20.4 as `V1_20_2` and 1.20.5–1.20.6 as `V1_20_5`. It selects the closest `emulates` version greater than or equal to the selected profile per hook. Thus a `V1_20_2` Change applies to the 1.20.4 profile and is ineligible for 1.20.5; the 1.20.5 profile retains native behavior unless a later Change exists. Lower native fences preserve native fallback and do not claim historical equality.

## Reconciliation

### Fluid eye sampling height — covered

- `PlayerEyeFluidSampleHook` (`fluid.player_eye_sample`) is dispatched from `EntityFluidInteractionMixin` at the eye-Y read in `EntityFluidInteraction.update`.
- `change/v1_20_2/PlayerEyeFluidSample` returns `eyeY - 0.11111111F`, so it covers 1.20.4. For 1.20.5 the `V1_20_2` Change is ineligible and there is no later Change for this hook; native eye-Y sampling remains, matching the accepted 1.20.5/1.20.6 boundary.
- Preserve the accepted delayed consequence: `Player.tick` consumes the prior eye-fluid tags before `Entity.baseTick` refreshes them, so a sample written during one tick affects the underwater/swimming state path on the following tick. The product hook changes only the sample coordinate; this report does not claim runtime parity of the downstream swimming/travel effects.
- `EyeFluidCacheHook` Changes address a distinct cache operation and are not duplicate coverage of the sample-height finding.

### Grounded flight-toggle jump — covered as its own operation

- `FlightActivationJumpHook` wraps the native `LocalPlayer.jumpFromGround()` call in `LocalPlayerMixin`.
- `change/v1_20_2/FlightActivationJump` suppresses only that call for the 1.20.4 profile while leaving the surrounding input, flight eligibility, toggle and ability-update order native. It preserves the accepted rule that flight toggling off or enabling flight while airborne does not jump.
- For the 1.20.5 profile the older Change is ineligible; no later override exists, so the native call is retained with its “flight enabled and grounded” gate.

### Sprint-jump impulse precision — product seam exists; narrowed source review pending

- The coordinator's source-scope correction excludes the new generic `JUMP_STRENGTH` attribute and the `<= 1.0E-5F` executor gate as modern-only at the 1.20.5 boundary. On eligible historical inputs, default jump strength is `0.42F`; cited block factors and nonnegative boost keep power above the threshold. These excluded dimensions do not support a historical gap or require a new 1.20.4 hook.
- The ordinary eligible candidate is the sprint-jump horizontal product change from float `0.2F` multiplication in 1.20.4 to double `0.2` multiplication in 1.20.5.
- `SprintJumpImpulseHook` is independently dispatched from `LivingEntityMixin` at the impulse argument. `change/v1_20_2/SprintJumpImpulse` computes the historical float products for the 1.20.4 profile. For 1.20.5 the older Change is ineligible, leaving native double products. The hook runs only after the native sprint/jump branch is reached; it does not bypass eligibility.
- No new product seam is needed for this candidate. Final source acceptance for the narrowed candidate is pending the source owner's replacement draft and renewed review; this section records code fit, not acceptance.

## Gaps and limits

No missing product seam or source cutover was found for the accepted fluid-sample and grounded-flight findings, or for the narrowed sprint-impulse candidate. The executor-gate and new-attribute dimensions from the original broad jump draft are explicitly excluded by the coordinator's scope correction; they are not reported as gaps.

No builds, tests, game launches, or runtime parity checks were performed. No product or backlog edits are proposed.
