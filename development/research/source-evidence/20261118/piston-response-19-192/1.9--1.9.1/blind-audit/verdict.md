# Final claim review: 1.9 → 1.9.1 piston player response

## Bound inputs

- Author claim: `source/comparison.md`, SHA-256 `972367beb7e410616642b4d3956d60ae60e8f2c803bc13392513fe4840c90895`.
- Independent frozen audit: `blind-audit/frozen.md`, SHA-256 `1be1b15d5d580d4a941a77a1b6ca5c09541874f258b41b438867cd6854ef7bbb`.
- Both supplied identities were verified against the files read for this review.

## Verdict

**Accepted for the assigned bounded operation.** The author's conditional-equivalence statement matches the frozen audit: with equal moved-state geometry, entity query overlap/list, and tick/world availability, both releases use the same callback admission gate and list order, choose the same extension/retraction direction, write the same slime velocity component when applicable, and issue the same axis-only `Entity.move` request. The pre-completion and terminal-progress callback ordering also matches. The author correctly leaves final collision-resolved displacement, shape clipping, and trajectory outside the claim.

The adjacent `PistonBaseBlock.move` source-state clear uses update flag `4` in 1.9 and `2` in 1.9.1. This raw difference is outside the reviewed callback-equivalence claim; it is excluded here, with no movement, propagation, clipping, or trajectory consequence inferred. The author's no-difference verdict for the assigned response operation does not contradict it.

## Residual scope

This verdict accepts the bounded conditional comparison only. It does not extend to full-pair coverage, exact intermediate patch boundaries, implementation, or runtime parity.