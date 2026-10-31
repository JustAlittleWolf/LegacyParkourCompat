# Canonical finding and boundary report review

Verdict: **ACCEPT**.

Bound revisions:

- Canonical finding `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/block-break-ack-skips-correction.md`, SHA-256 `6ab957c74db710ab2cd9f346a42c0e8b214f715b07fb7f687f5d8e113c10df2a`.
- Boundary audit `.native-source/20261030/1152-1165-finish43/block-ack-boundary/report.md`, SHA-256 `4bb89afb9777dad6e7819e9970b9852a980fc79ad5b1fc4fe308383ef167f1ba`.
- Endpoint callback report previously reviewed and accepted at SHA-256 `60b587aaf613805e8f2966d0f6f4f16bd2607913a410b2b4c104e14f3156e925`.

## Review

The canonical claim is faithful to the accepted callback evidence. In 1.15.2 the matching `(BlockPos, Action)` entry is removed, and `!allGood` enters reconciliation regardless of state-reference equality; a present saved `PosAndRot` causes the old five-argument player restore. In 1.16 the correction predicate adds the final `blockState2 != blockState` conjunct, which is false for the stated same-reference witness, so neither state reconciliation nor player restoration occurs. The 1.16 receiver is at `ClientPacketListener.java:2403–2414` and has the same-thread guard before forwarding the packet values. I confirmed the method bodies at those ranges in the exact sources.

The boundary audit's interval result is supported by the checked handler bodies: the added predicate is already present in 1.16 and remains in 1.16.1 through 1.16.5; the saved value and restore remain position-plus-rotation in 1.16/.1, then change to `Vec3` and three-coordinate restore from 1.16.2 through .5. For 1.16.2–.5 the correction predicate is still gated by the same state-reference comparison, and the additional level/collision conditions guard the later position write. The report correctly distinguishes this callback boundary from pair freeze and runtime parity.

The bounded result does not require acknowledgment frequency, server producer, or timing proof. `sendBlockAction`'s entry key and the callback's removal key agree. The 50-entry cleanup is after callback removal, as recorded in the earlier independent review; that detail does not invalidate the present-entry witness.

No substantive correction is required. For maximum terminology precision, the canonical phrase “saved `(position, action)` entry” may be read as “saved entry keyed by `(BlockPos, Action)`”; the saved player position is the map value. `LocalPlayer.absMoveTo` likewise denotes the call on the `LocalPlayer` receiver; the method is inherited from `Entity`. These are clarity notes only and do not alter the accepted claims.
