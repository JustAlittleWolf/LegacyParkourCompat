# Independent review: passenger crouch no-code reconciliation

**Implementation/no-code verdict: ACCEPT.**
**Source-provenance verdict: ACCEPTED SOURCE IDENTITY RESOLVED; acceptance digest metadata needs correction.**

**Reviewed reconciliation branch:** `fix/passenger-crouch-reconcile-oct8`
**Exact tip:** `dee9c6fa1f4aee135c4a38d6cbd3ba314cbcf392`
**Reconciliation commits:** `7434f94160058895e62fb9760decafe095cf68aa` and `dee9c6fa1f4aee135c4a38d6cbd3ba314cbcf392`
**Review base / merge base:** `de8eaa594677588f4a70e260561cfd2e872f69d3`
**Latest main used for the independent review branch:** `d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae`

## Scope and summary

The worker branch adds one 29-line reconciliation record, `workflows/fix-implementation/runs/1.20.1--1.20.2-passenger-crouch-reconcile.md`; it changes no Java code. I checked that no-code claim against the existing client mixin, mechanic interface, V1_20 implementation and provider, version resolver/runtime, and passenger-yaw hook. The existing hook changes the exact current `LocalPlayer.aiStep()` passenger predicate that the source finding identifies. No implementation finding was confirmed; no code change is needed for this reconciliation.

The source identity is independently recoverable despite a bad SHA-256 field in the blind-acceptance record. The accepted source commit/path/Git-blob triple points to the original bytes. The digest metadata must still be corrected; this report does not treat `5e22…` as the accepted snapshot's digest.

## Source identity and digest discrepancy

The accepted finding is commit `4ee5bbce85b193f972766c324c262e375fed9bd6`, path `workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-PASSENGER-CROUCH-INPUT.md`, Git blob `5037e1f8f12ac444c5f565f63111733c65ce5f6e`. I independently resolved the commit tree path to that blob, materialized the raw Git-object bytes, and computed SHA-256 **`e1d51ee7f7395415dbe5d9d9692ea1f55cdd83cf0172de27c555e70c362a29d7`** (6,933 bytes). The source-pair run on the source branch also records this digest for the finding at line 371.

The blind-review acceptance is commit `626fd4ffc3982169be5990949049ee0d2e3e1bb7`, `workflows/coverage-review-2026-10-07/middle.md`, section “Independent snapshot review — F-1.20.2-PASSENGER-CROUCH-INPUT”. It cites the accepted source commit, path, and Git blob correctly, but reports SHA-256 `5e22fc1bf04bee71559848d15ce5eaacfc86294b5abb0549b6ea63a20140c3b5`. That digest independently reproduces for the later 6,479-byte working-copy blob `61b6867399b7780479aabde0d197d2c4f633c9fe`, not the accepted source blob.

A raw diff between the accepted object and that working copy shows one deletion: the `EnchantmentHelper#getSneakingSpeedBonus` evidence bullet. The finding's behavior claim and conditions are otherwise unchanged. The blind-review narrative itself discusses the Swift Sneak bonus and input multiplier. Therefore the exact accepted source identity remains resolved by its commit/path/blob binding, and the digest discrepancy is a metadata error; it is not evidence that a different behavior finding was accepted. The truncated working copy should not be presented as the immutable accepted snapshot. Keep the source pair partial and its runtime status unvalidated.

**Required provenance correction:** append a source-record erratum that sets the accepted finding digest to `e1d51…` while retaining commit `4ee5…` and blob `5037…`, and restore or clearly mark the later copy that omits the evidence bullet. No source or coordinator record was edited by this review.

## No-code implementation proof

The reviewed current target is 26.2. Its ready Mojmap `LocalPlayer.java` has SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`. In `LocalPlayer.aiStep()` at lines 781–786, it computes the local `crouching` flag before `input.tick()` and includes `!this.isPassenger()` before the pose-fit predicates. The matching mixin is loaded through the client section of `legacyparkourcompat.mixins.json`.

`LocalPlayerMixin.java:111–124` applies `@ModifyExpressionValue` to the first `LocalPlayer.isPassenger()Z` call in `aiStep`. The 26.2 source confirms that this first call is the passenger conjunct in the crouching assignment; later `isPassenger()` calls in `aiStep` remain untouched. When no behavior resolves, `.orElse(vanilla)` preserves the exact native value. When `PassengerCrouchBehavior` resolves, `PassengerCrouch.blocksCrouching(...)` returns `false`. Because the target expression is `!isPassenger()`, this changes that one conjunct to true, allowing the original crouch-pose and shift checks to run in their original order. For a passenger, shift held, and a fitting crouch pose, this restores the 1.20.1 flag calculation; for a non-passenger it leaves the predicate result unchanged.

`change/v1_20/MovementChanges.java:8–12` registers the single `PassengerCrouch` implementation. `PassengerCrouchBehavior` uses its own mechanic key (`player.crouch.passenger`); a repository-wide implementation search found only this one implementation. `ParkourVersion.java:71–72` groups 1.20/1.20.1 as `V1_20` and 1.20.2–1.20.4 as `V1_20_2`. `ChangeResolver.java:22,30–31` returns no changes for `CURRENT` and otherwise selects the closest candidate whose emulated version is not older than the selected profile. Consequently V1_20 and earlier can use this V1_20 change, while V1_20_2 and later cannot. `MovementRuntime.find` returns empty when movement emulation does not apply or the entity is not a player; the mixin then uses vanilla. The same fallback applies when the selected profile is `CURRENT`/native.

The historical input ordering is supported by the accepted paired artifacts, which I rehashed: A/B `LocalPlayer.java` are `69a2d043…` / `bb5cbfb0…`; `KeyboardInput.java` is byte-identical at `a8064906…`; `EnchantmentHelper.java` is byte-identical at `c98b9e53…`. The source manifests contain the same hashes. In A, `LocalPlayer.aiStep()` lines 660–665 computes crouching before `input.tick(isMovingSlowly, multiplier)`; B inserts `!isPassenger()` at line 660 before the two pose-fit queries. `isMovingSlowly()` reads the local crouching flag, and `KeyboardInput.tick` scales both directional impulses when its boolean is true. `serverAiStep()` then copies the scaled impulses to `xxa`/`zza`; `LivingEntity.aiStep()` builds the movement vector and invokes player travel. The accepted finding limits the conclusion to the player's own input/movement calculation, not vehicle acceleration or a final mounted trajectory. For selected V1_20 and later-than-V1_14 profiles, the separate V1_13/V1_14 `SneakInputSlowdownBehavior` does not resolve; older-profile slowdown behavior remains independently versioned and is not claimed as part of this finding.

Passenger yaw does not overlap this hook. `BoatPassengerYawRefreshBehavior` has a separate mechanic key and its injection is in `ClientPacketListenerMixin.java:84–101`, during passenger-list refresh. It does not target `LocalPlayer.aiStep()` or the crouch/input path. No competing passenger-crouch implementation or duplicate injection was found.

## Six review angles

1. **Requirement fit — PASS.** The reconciliation claims an existing implementation; the registered hook removes exactly the newer passenger conjunct for the accepted old-side behavior.
2. **Correctness and edge cases — PASS.** The expression is intercepted before negation; the active hook returns false and preserves the rest of the short-circuit order. The vanilla result is retained when the hook is absent.
3. **Missing coverage — PASS with limits.** Registration and resolver boundaries are correct for V1_20 and newer profiles. The source pair remains partial; no complete-pair or mounted-trajectory claim is made.
4. **Conventions and duplication — PASS.** It uses the shared client mixin and existing versioned mechanic hook; it does not duplicate input or travel code.
5. **Permissions and visibility — PASS.** No code, access, configuration, or user-visible behavior changed in the reconciliation branch.
6. **Security — PASS.** No new input trust boundary, persistence, external call, or resource access was introduced.

## Validation limits and next action

No build, tests, client, server, TAS, Gym, runtime, Docker, or push operation was run. This review confirms the no-code implementation disposition only; the exact first changed release is still the accepted `(1.20.1, 1.20.2]` interval, and the source pair remains partial.

**Next correction:** add the provenance erratum described above, then keep this implementation disposition as ACCEPT. No implementation code change is requested.
