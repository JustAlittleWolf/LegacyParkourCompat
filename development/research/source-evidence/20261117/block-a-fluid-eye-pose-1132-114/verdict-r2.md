# R2 source verdict: sneaking WATER eye sample and blocked sprint witness

## Claim identity

Verified the handed-off immutable bytes:

- `lane-fluid-eye-cache/comparison.corrected-r2.md` SHA-256 `a396531a2b5342c3bf9791d0bcbcc7ffde38d6f5a8431cea3fb059352038d00f`.
- `lane-fluid-eye-cache/development/research/findings/1.13.2--1.14/sneaking-water-eye-height.corrected-r2.md` SHA-256 `49e1257b73754d381d06787922c6893af753f60acefc64dcc562fa4dabd7dace`.

Both match the handoff. The previous independent verdict remains unchanged: `lane-fluid-eye-pose-audit/verdict.md`, SHA-256 `8c0258c81022165c95dea2e975d07248f0cf37f1032971ab0045d28e85d71f9f`. This R2 file rebinds the sprint-witness disposition after checking the exact input-slowdown alias in the pinned Feather sources. No primary/source/product/Git files were written.

## Verdict

- **Sneaking eye-height/query/cache source delta: ACCEPTED as a source-level predicate difference.** The sampled values remain 1.54F (1.13.2) and 1.27F (1.14), the source-WATER y=0.70 numeric sample remains false versus true, and the player cache is read directly by the local accessor. This alone does not establish changed local movement.
- **Prior held-sneak auto-start witness: REJECTED.** The saved `bl2 = input.sneaking` is true and closes the first branch's `!bl2` requirement. The active sneak input also scales forward below 0.8F.
- **Prior sneak-release held-sprint witness: RETRACTED and REJECTED.** The exact 1.14 input alias carries the previous crouch slowdown across the key release. The second held-sprint branch therefore fails its post-refresh forward-speed gate. The 1.13.2 path has no such prior-pose slowdown, but its unchanged body-water=true plus old eye-cache=false fails the held-sprint water/cache gate. Both versions remain unsprinting in the proposed ordinary release scenario.
- **Movement finding: NOT ACCEPTED from these witnesses.** This verdict rejects the proposed ordinary start routes; it does not assert that the eye-cache delta can have no movement effect in every arbitrary pose, custom/forced input state, mode transition, or already-sprinting scenario. Such a different path would need its own exact reachable witness and dependency closure.

## Exact gate verification

In 1.14 Feather `LocalClientPlayerEntity#mobTick()` first snapshots `bl2 = this.input.sneaking` and `bl3 = this.input.movementForward >= 0.8F`, then computes `bl4 = this.m_63723874() || this.m_99544176()`, then calls `this.input.tick(bl4, this.isSpectator())` (`LocalClientPlayerEntity.java:625–633`). On the ordinary non-flying path with a valid sneaking pose, `m_63723874()` returns `!flying && canEnterPose(SNEAKING) ? (input.sneaking || !canEnterPose(STANDING)) : false` (`LocalClientPlayerEntity.java:596–598`; `Entity#m_87715359(Pose)` is the no-collision pose-fit query at `Entity.java:1576–1578`). For the prior held-sneak input, `input.sneaking` is still true at this evaluation, so `bl4` is true even if the physical key was just released and the current pose can fit standing.

`KeyboardInput#tick(bl, spectator)` then reads the newly released key and forward key, setting `sneaking=false` and `movementForward=1.0F`; because `!spectator && (sneaking || bl)` is true from the saved `bl4`, it scales that forward value to 0.3 (`KeyboardInput.java:13–27`). The later held-sprint branch requires `movementForward >= 0.8F` (`LocalClientPlayerEntity.java:671–681`) and cannot start sprint. The first auto-start branch also cannot start on this release edge because its saved `bl2` remains true (`LocalClientPlayerEntity.java:655–669`). A pre-existing double-tap timer does not change either failed condition.

On 1.13.2, `KeyboardInput#tick()` refreshes the released sneak and leaves forward at 1.0 when the physical key is released (`KeyboardInput.java:13–48`). But the same release-edge snapshot keeps the first auto-start branch closed through `!bl2`; the held-sprint branch requires `(!isInWater() || isSubmergedInWater())`. In the proposed body-water-intersecting source setup, `isInWater()` is true and the 1.13.2 eye cache is false, so that branch also fails (`LocalClientPlayerEntity.java:699–756`).

With sneak held continuously, 1.14's current key state itself also causes 0.3 scaling; the first branch has saved `bl2=true`, and both branches require forward at least 0.8F. On 1.13.2 continuous sneak scales current forward to 0.3 and likewise cannot satisfy either branch's forward threshold. Thus neither the original simultaneous-held-sneak account nor the attempted release-edge path demonstrates ordinary sprint start from the cache flip.

## Bounded evidence identity

The relevant 1.13.2 and 1.14 Feather source files retain their frozen source hashes from the earlier verdict. Rechecked hashes: 1.13.2 `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; 1.13.2 `KeyboardInput.java` `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; 1.14 `LocalClientPlayerEntity.java` `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f`; 1.14 `KeyboardInput.java` `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`; 1.14 `PlayerEntity.java` `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`; 1.14 `Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`.

This is a bounded source verdict only. It does not extend to custom/forced pose/input setups, alternate game-mode paths, or all consumers of the eye cache.