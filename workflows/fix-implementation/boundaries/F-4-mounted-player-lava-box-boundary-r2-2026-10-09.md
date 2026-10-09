# F-4 mounted-player lava-box boundary correction (r2)

## Supersession and immutable inputs

This memo corrects only the resolver-key interpretation in the immutable source-boundary memo at commit `4eb152d43f1a386b91d18529a59bea9ca99ffcfd`, blob `239f51263c45e182b68ad031fab5525b24dbe87d`. Its source ranges, hashes, source-file manifest checks, and first-changed-release conclusion remain unchanged. This correction does not revise the accepted finding or its bounded source review.

- Accepted finding: commit `019d5fcef181f6dde2a9630e0b882acfa99d6de9`, blob `bcd14cf6c38270229b035a85f929291254c3664b`, raw SHA-256 `a49bfdd5e363f9faae5b99fe29cfefe393a958019460063fbb175ef2b053e034`.
- Independent bounded source acceptance: commit `964ae707a85126a692de3bf9d41a6db5574867d5`.
- Exact stable-release source cutover established in r1: **26.1**. The corresponding 26.1, 26.1.1, and 26.1.2 `Entity.java`, `EntityFluidInteraction.java`, and `AbstractBoat.java` hashes are identical; 1.21.11 retains the old per-entity deflated lava scan.
- Exact source manifest identities and method ranges are in r1; 26.1/26.1.1 readiness identities are in publication commit `13dcacb388906800c1e3448633381de69ef51846`.

## Correct resolver interpretation

`ChangeResolver` selects changes whose `emulates` version is at or after the selected profile and chooses the nearest such change. A change implementing the **old** behavior from A (1.21.11) through the last version before the cutover therefore uses `@MovementChange(emulates = ParkourVersion.V1_21_11)`. For selected profiles at or before `V1_21_11`, this restores the A-side query-box behavior; selected `V26_1` does not resolve this older change and retains native clipped-box behavior; `CURRENT` resolves no historical changes.

The r1 statement suggesting `V26_1` as the implementation key was incorrect and is superseded. The current `V26_1` group already covers the changed releases 26.1, 26.1.1 and 26.1.2, so no `ParkourVersion` split is needed. This establishes the version key for the accepted bounded operation only; it does not claim full-pair completion or broader runtime parity.
