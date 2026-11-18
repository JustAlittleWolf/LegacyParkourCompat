# Independent exact author review verdict

## Bound inputs

This verdict is bound to the exact bytes of these two artifacts:

- `grounded/source/comparison.md` SHA-256 `0171b46fb27c6b0314af1e25eb4b156148aed11ebc2c5e7552a3c2b920bb6a37`.
- `grounded/blind-audit/frozen.md` SHA-256 `5616d5de6df30ec722d85b23cc9a4dfb2fc2b4e153a308017ba7ff5f99dda10a`.

Both observed hashes matched their declared identities before review. No source tree or other evidence was used for this verdict.

## Verdict

**Accept the scoped same-input equivalence for the overlapping ordinary movement path:** given the same input vector, yaw, movement-speed value, grounded state and support/friction-provider results, the frozen audit and comparison agree that grounded acceleration, friction scaling, and the first SELF dispatch are unchanged. The comparison adds upstream `LocalPlayer.applyInput` and `modifyInput` operations; these were outside the frozen audit's independently reviewed slice, so this verdict does not independently accept those added operation claims.

The changed item-use scale does not affect a path where item use is inactive: that branch contributes no item-use multiplier to the ordinary input vector. If item use is active, equality depends on the comparison's separate claim that an item's effective `UseEffects` component resolves to the old `0.2F` default. The frozen audit did not review item/component applicability, and this artifact-only review cannot establish that default across pre-existing items or custom data. Keep active item-use applicability open as a separate claim. The comparison identifies the spear's non-default value as a new-item case; this review records that attribution but does not independently audit its source reachability.

No disagreement was found in speed, pre-move grounded friction sampling/formula, or initial SELF displacement selection within the overlapping scope. Support-provider internals, geometry, post-move behavior and the wider item-use applicability remain unreviewed.