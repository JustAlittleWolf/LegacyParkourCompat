# Signed semantic correction: swim pitch vertical writer

This note corrects one interpretation in the bound source report. It preserves the report bytes and does not change the no-difference conclusion.

**Bound source report:** `c-swim-pitch-vertical-114-1141-source/report.md`  
**Report SHA-256:** `7731175b1093bb293fc61962f71677a444cbc3df3ab0c886cdb3e7b76bad871e`  
**Bound independent audit:** `c-swim-pitch-vertical-114-1141-audit/verdict.md`  
**Audit SHA-256:** `574638a1f8da96a5e0241b6b55d0c7b89868c65bc4126c51b93070d5682faaee`

## Correction

In both releases, `Entity.getRotationVector(float, float)` computes its Y component as `-MathHelper.sin(pitchRadians)`. Minecraft's negative pitch points upward, so upward-looking pitch produces `g > 0`; positive pitch points downward and produces `g < 0`. At level pitch, `g == 0`.

Consequently the writer's condition `g <= 0.0 || jumping || !sampledFluidState.isEmpty()` runs unconditionally for level or downward-looking directions. For upward-looking `g > 0`, it runs only if the player is jumping or the sampled fluid state is nonempty. The coefficient is `0.085` only for `g < -0.2`; at exactly `-0.2` and above it is `0.06`.

The sample expression and its order remain exactly `new BlockPos(this.x, this.y + 1.0 - 0.1, this.z)`: compute the Y coordinate as `y + 1.0`, then subtract `0.1`, before constructing the floored block position. The no-difference conclusion for the 1.14 to 1.14.1 writer remains unchanged.

Signed: assigned source-trace author, 2026-11-18.