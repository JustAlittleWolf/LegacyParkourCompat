# Acceptance of corrected 1.12.2 → 1.13 swim pitch finding

## Verdict

**ACCEPTED WITH THE BOUND CORRECTION.** The correction resolves the sole sign/witness objection in the prior audit. The original report remains unchanged and is not accepted by itself; the accepted evidence is the original report read together with its bound correction, under the exact identities below. The source delta is bounded to the 1.12.2 and 1.13 `PlayerEntity.moveRelative` behavior and the inherited local-player movement path.

The correction states the sign consistently with both exact `Entity.getRotationVector` implementations: negative pitch aims upward and yields positive `g`; positive pitch aims downward and yields negative `g`. It then applies the correct `g <= 0` unconditional gate, the `g > 0` jump/above-fluid gate, and the `h` threshold. Its positive-pitch, no-jump witness has `g < -0.2`, so it enters the unconditional branch with `h = 0.085`; the correction also follows the adjusted velocity through the inherited water movement call. It preserves the exact sampled position expression and makes no collision outcome claim. These points close the prior audit's material sign and witness objection.

The source-level addition remains confirmed: 1.12.2's player override has no swimming/look vertical writer; 1.13 adds the conditional writer before the inherited call. This acceptance does not cover fluid-contact mechanics, collision outcomes, other movement operations, intermediate patch introduction, complete pair coverage, implementation, or runtime parity.

## Bound literal identities

- Original report SHA-256: `F1392FA2A961632AA5A64489882A34CDA6BB704F881469D7FCF5DA7A7821B539`.
- Prior independent verdict SHA-256: `8464D4ED99DA56C70A73F3DCEB605ED122CC8342B2B80132FAD846ED0E24AB6F`.
- Correction SHA-256: `FBDF90E90AAFECF9D854A8E17194B6D4485CBA1542614E49C8E0FA87FC67E4DF`.

The correction declares the same exact original and prior verdict hashes. Each was independently recomputed from its literal file bytes during this audit. The canonical source identities and file hashes remain those recorded in the bound independent verdict.

— Independent blind exact-source auditor, signed 2026-11-18
