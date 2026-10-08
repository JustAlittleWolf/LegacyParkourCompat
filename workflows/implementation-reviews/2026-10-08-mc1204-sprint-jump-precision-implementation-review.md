# Independent implementation review — MC02 sprint jump precision

**Decision: ACCEPT** implementation commit `4aa5be97b5495b59736381c9e05e4ca9f6e32a1b` for the bounded sprint-jump precision delta. **No confirmed functional findings.** This accepts the static implementation for the source-supported scope; it does not close the 1.20.4–1.20.6 source pair or claim runtime parity.

## Scope and evidence

The requirement reconstructed from the accepted MC02 finding and implementation scope proof is to restore the 1.20.4 sprint jump’s float multiplication for old player inputs, while leaving the separate low-power cutoff and modern-only jump attribute inputs out of scope.

Reviewed the implementation diff from the implementation branch merge base `a5c191df557cd152d578150c689227897ac33256` to code commit `4aa5be97b5495b59736381c9e05e4ca9f6e32a1b`: two Java files, 18 insertions, no deletions. The change adds `SprintJumpImpulse` under `change/v1_20_2` and registers it in that existing provider. The implementation record is `8c5686cd61421ff50880bbad728204122570c00b`; the final reconciliation is `a3ecb4667b30d86cdf46c7178cfb03d9bf4d3441`.

- MC02 finding snapshot `b168d90c05e813d6dff2ccd97090dc1602d9d418` was accepted in source review `581b5bd2cb44981c4889eaf05ea739134e332b87`.
- Exact 1.20.4/1.20.5/1.20.6 boundary memo `e277edd19eb8f8ce63681ab7985a5d92df8a259f` was accepted in independent boundary review `1e5de3b32966e8766cdcf9f7ad56a9278fb79bb0`. I independently recomputed the memo’s raw blob digest: blob `cda4a40cadde768c36b5523cc5c11a3a56adc78f`, SHA-256 `a97e20b5abaa131f9ec090d1d186eae63592d839e8cd9c3c254b80acdea8de82`.
- Scope proof `6fb3cc75e33c00959930a2a7b5e8516e9d95f1e3`, final tip `9acf4bb097ce025b5dbc9c042ef012d5cad54f63`, separates the eligible sprint arithmetic from the cutoff, which has no qualifying vanilla 1.20.4 player jump-power input.
- Ready `LivingEntity.java` hashes, each matching its source-manifest row: 1.20.1 `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`; 1.20.4 `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`; 1.20.5 and 1.20.6 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. The 1.20.4, 1.20.5, and 1.20.6 ready marker and source-manifest hashes also match the boundary memo.

## Source comparison and static arithmetic check

The 1.20.4 `LivingEntity.jumpFromGround()` computes a float yaw angle, then adds `-Mth.sin(angle) * 0.2F` and `Mth.cos(angle) * 0.2F` to movement. 1.20.5 changes the sprint addition to a `Vec3` whose float trig values are multiplied by double literal `0.2`; 1.20.6 has the same source-file hash and body as 1.20.5. The `Mth.sin` and `Mth.cos` table lookup expressions are unchanged in those sources. The implementation’s `LegacyTrig` table initialization and float lookup expressions match the inspected source, and its yaw-to-float expression and multiplication order match 1.20.4.

For a static example, at yaw zero the cosine lookup is `1.0F`. The 1.20.4 path computes `1.0F * 0.2F`, yielding float `0.20000000298023224` when widened into the vector’s double component. The 1.20.5 path computes `1.0F * 0.2` as double `0.2`. The new hook returns the former product for covered legacy profiles.

The added method returns `(xImpulse, 0, zImpulse)`. It does not change the earlier vertical velocity write, `hasImpulse`/sync state, jump-power cutoff, or jump-strength attribute evaluation. Its ignored `vanilla` argument is safe at this call site: the existing `LivingEntityMixin` modifies the `Vec3` argument only on the sprinting branch of `jumpFromGround()V`, where the vector is precisely the sprint trig impulse.

## Hook reachability and profile behavior

The source `LivingEntity.aiStep()` reaches `jumpFromGround()` for ordinary grounded/eligible shallow-fluid jumps when `noJumpDelay == 0`; the 1.20.4–1.20.6 `Player.jumpFromGround()` overrides delegate to `super.jumpFromGround()`. The current 26.2 `LivingEntityMixin` hook targets `addDeltaMovement(Vec3)` within that method. The current `LocalPlayer.aiStep()` also contains a grounded flight-activation call, but the existing V1_20_2 `FlightActivationJump` change suppresses that 1.20.5+ addition for the emulated 1.20.2–1.20.4 profile. Ordinary player jumps remain reachable. `MovementRuntime.find` rejects non-player entities and returns no behavior when emulation is disabled.

The resolver chooses the closest change whose `emulates` version is not older than the selected profile. Thus V1_20_2 selects the new rule; V1_20.1’s ready source has the same float formula, so the V1_20 profile’s resolver fallthrough is consistent with the inspected source. V1_19_4 and older profiles retain the nearer existing V1_19_4 implementation, which uses the same float formula. V1_20_5 and V1_20_6 exclude the older V1_20_2 change and keep native double multiplication. `CURRENT` resolves no changes.

The new class contains only the sprint-vector arithmetic and its registration. It does not restore the cutoff or supply values for `generic.jump_strength` or any other modern-only attribute.

## Six review angles

1. **Requirement:** implemented the sprint precision subdelta, with cutoff and generic jump-strength behavior excluded as scoped.
2. **Correctness and edge cases:** yaw cast, trig lookup, multiplication type/order, vector components, and call timing match the 1.20.4 source; no confirmed defect found.
3. **Missing behavior:** the existing mixin dispatch and V1_20_2 provider registration reach the hook; disabled/current and non-player fallback remain native.
4. **Conventions and duplication:** this is a small versioned delta using the existing hook/provider pattern. The repeated formula is the separate V1_20_2 registration needed after the V1_19_4 rule stops resolving; no movement loop or physics class was copied.
5. **Permissions and visible behavior:** no permission, config, or error-surface change. The intended visible change is only the legacy sprint jump’s horizontal impulse precision for covered profiles.
6. **Security:** no new input, network, command, persistence, or deserialization surface.

## Current-main integration and limits

Local `main` advanced from the worktree’s initial base and now points to `3a60fe735560e478bf0aa0d05f5e306c74800f6a`. I inspected the intervening changes and fast-forwarded the review branch to current `main`. They add sprint-start water behavior in `LocalPlayerMixin` and implementation records; they do not modify `LivingEntityMixin`, `SprintJumpImpulseBehavior`, `ChangeResolver`, `ParkourVersion`, or the new impulse class. No semantic overlap or conflict with MC02 was found.

No tests, build, Gradle task, decompilation, game client, server, TAS, Gym, Docker, or push was performed, as directed. Runtime trajectory and compilation remain unverified; the source pair remains `PARTIAL`.

**Next step:** the reviewed sprint-precision delta can proceed to the exclusive integration owner; runtime validation remains a separate pending status.

Reviewer: independent static review, 2026-10-08.
