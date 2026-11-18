# Final draft review: local dry ground jump

## Bound artifacts

Reviewed the exact author bytes at `12110-12111-local-locomotion/jump/source/comparison.md`, SHA-256 `ad98a8bf4ee292f638b76942bd54b4f3cfe9d18567dd1b42e75c6a7a72b95910`, against frozen blind audit `12110-12111-local-locomotion/jump/blind-audit/frozen.md`, SHA-256 `d53f0021ed8c95dfc48c9cebe96ea925c6398cc40d4eb452c5d3c54c2fbcd144`. Both hashes match the assigned identities. I also checked the cited source bodies against the ready source manifests; the stated LocalPlayer/LivingEntity hashes match, and the newly cited ClientInput and KeyboardInput hashes match their per-release source-manifest entries.

## Verdict

**Not accepted as written; correct one dry-jump delay claim before freezing the author draft.** The overall endpoint conclusion about the specified position/velocity path is supported, as are the input forwarding, power/effect arithmetic, velocity write order, and dry non-fall-flying travel path.

The `LivingEntity.aiStep()` bullet says that on the assigned dry path the fluid-jump branch is not taken and the `else` branch clears `noJumpDelay` to zero, so there is no distinct dry-ground cooldown. That is incorrect for an ordinary non-flying player holding jump while grounded. `isAffectedByFluids()` is true for `LivingEntity` by default (1.21.10 `LivingEntity.java:547-549`; 1.21.11 `:548-550`), and the player override is `!abilities.flying` (1.21.10 `Player.java:886-888`; 1.21.11 `:884-886`). With dry conditions, `$$7` is false; the nested gates reach `(onGround || ...) && noJumpDelay == 0`, call `jumpFromGround()`, and set `noJumpDelay = 10` (1.21.10 `LivingEntity.java:2807-2830`; 1.21.11 `:2933-2956`). The final `else` that resets the timer applies when `jumping && isAffectedByFluids()` is false, not to a normal dry grounded jump. The ten-tick delay is real and identical in the two endpoints. Replace the quoted claim with that path and timing.

The author draft's identity table omits the hashes for input code it relies on. Add the verified `ClientInput.java` and `KeyboardInput.java` source hashes (both releases: `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` and `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`, respectively). `KeyboardInput.tick()` is the runtime override reached by `LocalPlayer.aiStep()` and snapshots the current key state; its body is equal in both releases. If retaining claims about `Input` record reconstruction, include its identical hash too: `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06`.

The `hasImpulse` → `needsSync` write is present and correctly identified. Keep it explicitly residual to the adjudicated kinematic claim: this review establishes the same jump position/velocity operations through dry in-air travel, not parity of all movement bookkeeping or synchronization behavior. The 1.21.11 `shouldTravelInFluid` extraction changes the caller structure, but for dry, non-fall-flying input it reaches the same `travelInAir()` body and vertical velocity consumption; the cited travel arithmetic matches.

## Limits

This is an endpoint source review for 1.21.10 Mojmap to 1.21.11 Mojmap. It does not establish the intermediate patch boundary, runtime tick parity, full player-tick coverage, or behavior for excluded fluid, ability, vehicle, rotation, collision/support, pose, or external-response paths. The frozen blind audit remains unchanged; this verdict is bound only to the exact author revision above.
