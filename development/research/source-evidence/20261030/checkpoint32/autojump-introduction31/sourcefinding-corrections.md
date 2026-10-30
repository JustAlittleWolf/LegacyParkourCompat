# Source-finding correction record: local auto-jump

This correction record applies to the immutable ordinary-lane draft `.native-source/20261030/194-1102-ordinary31/local-auto-jump.md` (SHA-256 `65ca1b9a69e8823f870ef6523e9f3d8f3df12ecf76406ca98d80db8f2c573f88`) and audit correction `.native-source/20261030/194-1102-ordinary-audit31/local-auto-jump-correction.md`.

## Required corrections

1. Replace any claim that the detector's upper step rise is always `1.2F`. In exact 1.10, 1.10.1 and 1.10.2 source, `o` is initialized to `1.2F` and, while Jump Boost is active, raised by `(amplifier + 1) * 0.75F`; the range accepted at the queue write is `u > 0.5F && u <= o` (`LocalClientPlayerEntity.java:856–860, 911–915`).
2. Qualify the cached option assignment: `autoJumpEnabled = minecraft.options.autoJump` is in `sendMovementToServer` and guarded by `isCamera()` (`LocalClientPlayerEntity.java:168–229`, assignment `:228`). It is not an unconditional cache refresh.
3. The corrected replacement finding here expands the established endpoint evidence to exact 1.10 and 1.10.1 sources. Those releases carry the same local-player and input source hashes as 1.10.2; no detector behavior delta is found within the inspected group. State the first confirmed source-present release as 1.10, retaining the limit that this audit inspected 1.9.4—not every earlier patch release.

The corrected replacement is `.native-source/20261030/autojump-introduction31/local-auto-jump.md`; the immutable earlier draft and audit are left untouched. Full bounded evidence and source identities are recorded in `report.md` in this lane.
