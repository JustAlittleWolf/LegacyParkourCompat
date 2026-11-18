# Independent exact claim review verdict

## Reviewed frozen inputs

- Blind audit freeze: SHA-256 `7a3da27db6f426bc3e612e5470649aac317aaa4bcdf5ef92eb47b7b2d0105cd3`.
- Source comparison: SHA-256 `09cd49bdc3d4b4b25db01c262c26e2d6001d9a9122a153c3bc51c3948c7a3def`.
- Candidate finding: SHA-256 `489038c56396cfe60dd4ad68e259555049e6180f016c3da0bf82ee0563970319`.

## Source facts

Accept the bounded source claim that 1.21.11 refactors active-use input scaling to read `USE_EFFECTS.speedMultiplier`. The paired local input paths retain the same nonzero-input `0.98F` scale, active-use/non-passenger gate, subsequent sneak scale, and square-input correction. The old active-use multiplier is fixed at `0.2F`; `UseEffects.DEFAULT` in 1.21.11 is also `0.2F`, with `canSprint=false`. Thus ordinary defaulted stacks preserve the old speed and sprint-eligibility state. The bow remains an ordinary built-in registration and receives the common default.

Accept that 1.21.11's spear property builder supplies `UseEffects(true, false, 1.0F)` and that the spear items are registered in 1.21.11 but absent in 1.21.10. This is an accurate source-level feature distinction. The candidate's conditional movement statement follows the component reader if a registered spear is actively in use; the frozen bounded audit did not independently close the spear's use-action reachability.

The frozen equipment audit also agrees with the comparison's bounded equivalence for player speed, jump-strength, gravity readers and modifier calculation/application order. Soul Speed's item-side movement-speed effect is unchanged and conditional; it supplies no pair delta for this claim.

## Historical applicability

No eligible era-common vanilla input demonstrates a movement difference. Existing ordinary active-use stacks receive `0.2F` and `canSprint=false` on the new side, matching 1.21.10's hardcoded slowdown and active-use sprint rejection. Nondefault component values are configurable new-version inputs. The only cited built-in override is attached to newly introduced spear items, so it cannot witness behavior of a 1.21.10 profile. Under feature-era policy, 1.21.11-only item behavior remains native when emulating 1.21.10.

## Verdict

Source fact accepted; historical applicability rejected for the stated pair/profile. **No accepted in-scope delta and no product recommendation.** The candidate may remain as a record of the 1.21.11 component refactor only if its text keeps the new-only spear case separate from historical acceptance. This review does not claim runtime parity or full-pair closure.