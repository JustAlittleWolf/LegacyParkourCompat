# Independent source audit: Elytra glide fall-distance gate

## Verdict

**Accepted for the bounded claim: no difference between 1.16.3 and 1.16.4.** For the stated ordinary, dry, on-foot local-player witness with an already-true fall-flying shared flag and an enabled Elytra in the chest slot, standing at ladder/vine feet, both releases retain fall-flying and enter the same Elytra travel branch. Before glide-velocity calculation and movement, the branch assigns `fallDistance = 1.0F` iff the current vertical movement component is strictly greater than `-0.5`. The branch does not test climbability. The climbable fall-distance reset belongs to the separate non-flight friction path.

This verifies only that witness and operation. It does not establish flag activation, a particular ladder trajectory, final tick fall distance, an intermediate patch boundary, or full-pair coverage.

## Bound input and source identity

Input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1163-1164-alias-gate/report.md`  
Input SHA-256 (exact file bytes): `4e25caa9ee4477dcd677a206f8ec32f5cef82017ca8d2468b8ca837f749c6244`.

Read-only exact sources: `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Both `.decompile-provenance.json` files identify Mojmap sources produced with pipeline `lpc-decompile-v1`, Vineflower 1.12.0, and Eclipse Adoptium Java 25.0.3+9 on Windows 11 x64. Manifest SHA-256: 1.16.3 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 1.16.4 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. For both versions, actual SHA-256 values of `LivingEntity.java`, `Player.java`, `LocalPlayer.java`, and `Entity.java` match the manifest entries; each corresponding file hash is byte-identical across the pair.

## Audited path and boundaries

- `LocalPlayer.java`, `LocalPlayer.aiStep`, lines 725–732: the jump-input start request requires `!onClimbable()`, then `wasFallFlying` snapshots `isFallFlying()`. This gates starting flight from that input path; it does not stop an already-true flag.
- `LivingEntity.java`, `LivingEntity.aiStep`, lines 2454–2459, then `updateFallFlying`, lines 2478–2497: the updater runs immediately before dynamic `travel`. With flag true, airborne, not passenger, no Levitation, and enabled chest Elytra (lines 2480–2483), it retains `bl = true`; there is no climbable test. It only writes the shared flag back when `!level.isClientSide` (2494–2495), so the client-side active flag is retained for this witness.
- `Player.java`, `Player.travel`, lines 1402–1414: an ordinary player without creative `abilities.flying` takes `super.travel`, not the creative-flight branch. `LivingEntity.travel`, lines 1923–1979, reaches `isFallFlying()` after the dry witness bypasses water and lava handling.
- `LivingEntity.java`, `LivingEntity.travel`, lines 1979–1984: it snapshots `getDeltaMovement()` as `vec36`, tests the `double` component `vec36.y > -0.5`, and writes float `1.0F` before glide calculations. Equality at `-0.5` and lower values skip the write. It next computes glide velocity, writes it at line 2007 and calls `move` at line 2008. No climbability check or climbable fall-distance reset occurs in this branch.
- The cleanup is instead in `LivingEntity.travel`'s `else` non-flight route (lines 2022–2026) through `handleRelativeFrictionAndCalculateMovement` (2062–2065), which calls `handleOnClimbable`; that method checks `onClimbable()` and writes `fallDistance = 0.0F` (2089–2103). `onClimbable`, lines 1363–1384, reads feet state and accepts `BlockTags.CLIMBABLE` (plus its separate ladder-trapdoor case); ordinary ladder/vine feet satisfy the tag predicate.
- The `1.0F` assignment is pre-move, not a guaranteed final tick value. `Entity.move`, lines 513–518, calls `checkFallDamage` with realized vertical movement. `Entity.checkFallDamage`, lines 894–903, clears fall distance on landing or adds realized downward displacement when negative; `LivingEntity.checkFallDamage`, lines 257–278, delegates to it. Thus the same tick can replace the pre-move value at this immediate state boundary.

These method bodies are identical at the cited lines in the two releases; the four examined file hashes independently confirm identical source bytes. No source difference is present for this bounded operation.