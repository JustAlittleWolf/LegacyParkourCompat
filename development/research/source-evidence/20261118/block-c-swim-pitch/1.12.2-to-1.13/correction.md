# Signed correction: vertical sign and witness

**Correction author:** Codex source worker  
**Date:** 2026-11-18  
**Bound original report SHA-256:** `f1392fa2a961632aa5a64489882a34cda6bb704f881469d7fcf5da7a7821b539`  
**Bound independent audit SHA-256:** `8464d4ed99da56c70a73f3dceb605ed122cc8342b2b80132fad846ed0e24ab6f`

This correction supersedes the original report's interpretation of the look-vector sign and its movement witness. The original report is preserved unchanged.

## Corrected source reading

In 1.13, `Entity.getLookVector()` returns `getRotationVector(this.pitch, this.yaw)` (`net/minecraft/entity/Entity.java:1690–1692`). The rotation-vector code computes `f = pitch * (float)(Math.PI / 180.0)`, then returns a vector whose Y component is `-sin(f)` (`Entity.java:1250–1258`). Therefore **negative pitch aims upward and yields positive `g`; positive pitch aims downward and yields negative `g`**.

In `PlayerEntity.moveRelative` (`net/minecraft/entity/living/player/PlayerEntity.java:1435–1462`), while `isSwimming() && !isRiding()`:

- `h` is `0.085` only when `g < -0.2`; otherwise it is `0.06`.
- The velocity adjustment runs unconditionally for `g <= 0` (downward or level look).
- For `g > 0` (upward look), it runs only when `jumping` is true or this exact query finds fluid: `!this.world.getBlockState(new BlockPos(this.x, this.y + 1.0 - 0.1, this.z)).getFluidState().isEmpty()`.
- If the gate passes, the exact update is `this.velocityY = this.velocityY + (g - this.velocityY) * h;`.

The exact sampled position is written as `this.y + 1.0 - 0.1`; this correction does not substitute a simplified coordinate.

## Reachable local witness and actual move

For a local 1.13 player in the swimming state, unmounted and not flying/spectating, use positive pitch (for example `pitch = +30°`, looking downward), no jump, and zero vertical movement input. Then `g = -sin(+30° in radians) < -0.2`, so `h = 0.085` and the gate passes through `g <= 0` regardless of the sampled fluid result. If `velocityY` on entry is zero, the source update makes it approximately `-0.0425` (the exact operation remains the expression above).

`LivingEntity.moveRelative` reaches its water branch for this player and calls `updateVelocity(...)`, then `this.move(MoverType.SELF, this.velocityX, this.velocityY, this.velocityZ)` at `net/minecraft/entity/living/LivingEntity.java:1598–1624` (the `move` call is line 1621). Thus, with zero vertical movement input, the pitch-adjusted negative `velocityY` is used as the current tick's movement delta before subsequent water drag/gravity operations. This establishes a downward movement witness at the call site without making a collision outcome claim.

For the opposite sign, a negative pitch produces `g > 0`; that upward-looking case does **not** pass through `g <= 0` and needs jumping or nonempty fluid at the exact sampled block position above.

## Exact pair boundary

For 1.12.2, `PlayerEntity.moveRelative` at `net/minecraft/entity/living/player/PlayerEntity.java:1386–1403` contains the flying adjustment and `super.moveRelative` delegation but no swimming or look-vector vertical adjustment. The added conditional is present in the 1.13 override. This statement is limited to these exact method bodies and the inherited movement call cited above; it does not establish an intermediate patch boundary or full-pair coverage.

**Signed content identity:** this file's SHA-256 is supplied with the correction handoff.