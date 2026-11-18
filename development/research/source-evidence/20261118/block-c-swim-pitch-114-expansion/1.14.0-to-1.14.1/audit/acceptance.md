# Independent acceptance: 1.14 → 1.14.1 swim-pitch sign correction

**Decision: ACCEPTED.** The standalone semantic correction is accurate against the exact canonical Yarn source and the independent frozen trace. It corrects the directional interpretation while preserving the no-difference conclusion for the bounded writer.

## Immutable binds

- Source report: `c-swim-pitch-vertical-114-1141-source/report.md` — SHA-256 `7731175b1093bb293fc61962f71677a444cbc3df3ab0c886cdb3e7b76bad871e`.
- Independent audit verdict (preserved unchanged): `c-swim-pitch-vertical-114-1141-audit/verdict.md` — SHA-256 `574638a1f8da96a5e0241b6b55d0c7b89868c65bc4126c51b93070d5682faaee`.
- Standalone semantic correction: `c-swim-pitch-vertical-114-1141-source/correction.md` — SHA-256 `625925fdc5ab7ea63cb3bd31f4452c7bfbbe99b3f4c73ce5f8ddf6e27cec2725`.

## Source verification

Both exact releases have the same `PlayerEntity.travel` gate and constants at lines 1366–1372: `g = getRotationVector().y`, `h = g < -0.2 ? 0.085 : 0.06`, and the update gate `g <= 0.0 || jumping || !sampledFluidState.isEmpty()`.

Both exact `Entity.getRotationVector(float,float)` implementations return Y as `-MathHelper.sin(pitchRadians)` (lines 1180–1188). Both exact `MathHelper.sin(float)` methods read the float sine table with `(int)(f * 10430.378F) & 65535` (lines 11–29). Negative Minecraft pitch points up, so an upward look produces positive `g`; positive pitch points down and produces negative `g`. The correction's unconditional level/downward case and upward jump/fluid gate therefore follow from source. The `0.085` threshold is strict; `g == -0.2` selects `0.06`.

The sampled expression `new BlockPos(this.x, this.y + 1.0 - 0.1, this.z)` evaluates addition before subtraction, then the constructor path floors each coordinate. This claim was independently checked in the exact sources. At level pitch the sine-table result compares equal to zero, so the unconditional `g <= 0.0` test passes.

The source report and independent verdict remain unchanged. No implementation or runtime-parity claim is made.

Signed: `/s/ independent blind source auditor`, 2026-11-18.