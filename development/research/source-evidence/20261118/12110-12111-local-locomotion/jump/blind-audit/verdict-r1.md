# Final draft review: local dry ground jump, revision 1

## Bound artifacts

Reviewed the exact author bytes at `12110-12111-local-locomotion/jump/source/comparison-r1.md`, SHA-256 `fc188af2c85cfbabbbc9f790199b3e1db7f15a3524da302988d6be6839aeb5aa`. Its stated preserved revision-0 comparison hash matches `ad98a8bf4ee292f638b76942bd54b4f3cfe9d18567dd1b42e75c6a7a72b95910`. The blind freeze remains at SHA-256 `d53f0021ed8c95dfc48c9cebe96ea925c6398cc40d4eb452c5d3c54c2fbcd144`; the prior review remains at `484de3fc6d83cb0d6396b227ceed3ed33ef61d8611833ad4f9339dce1d4ebd13`.

## Verdict

**Accepted for the corrected, bounded position/velocity comparison.** Revision 1 fixes the dry-jump branch error: for a non-flying player with held jump, on dry ground and with zero delay, both releases enter the ground branch, call `jumpFromGround()`, and set `noJumpDelay = 10`. The reset-to-zero `else` does not execute on that path. The power/effect calculation, jump velocity write order, and first dry `travelInAir()` consumption agree in the cited endpoints.

The newly listed `KeyboardInput.java` and `ClientInput.java` SHA-256 values match both releases' source-manifest rows. The updated text also correctly limits the `hasImpulse` → `needsSync` result: it records a changed synchronization marker and does not claim parity for all later entity state or network behavior. The dry caller reaches the same in-air path in both releases despite 1.21.11 extracting `shouldTravelInFluid()`.

## Residual source-identity and scope notes

The source ledger still does not include two files needed to make every input-path gate and type claim independently replayable from the ledger. The draft relies on `Input` construction/accessors in `ClientInput.makeJump()` but does not list `net/minecraft/world/entity/player/Input.java`; its verified SHA-256 is `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` in both releases. Also, the grounded gate's applicability to a player relies on the player override `isAffectedByFluids() = !abilities.flying`, while only the base `LivingEntity` method is cited. Make the precondition explicitly non-flying/ordinary survival, as intended by the exclusions, or add `Player.java` and its override to the identity/evidence table. Verified full-file hashes are 1.21.10 `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` and 1.21.11 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.

These are evidence-table completeness notes, not observed behavioral disagreements. This remains an endpoint source comparison only: it does not establish the intermediate patch boundary, runtime parity, complete player-tick coverage, or the full effects of the synchronization marker. The earlier frozen audit and verdict are unchanged.
