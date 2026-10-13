# Independent re-review: sleep-safety correction

**Decision: ACCEPT for the bounded finding `F-SLEEP-SAFETY-TRANSITION-r3`.** The two previously reported implementation defects are corrected. The source pair remains partial, and an adjacent creative-player query gap at `V1_12` remains open as a separate behavior.

**Original implementation:** `d892a7ad527fa5a52f57fdb4ef48c178f8a21f41`
**Reviewed correction:** `fc9962a8aa967101ec0c4d3a1447b933678e1e79`
**Worker proof / final record:** `04ba46308c389b2b171525193ad3d15a31a11ee4`, `workflows/fix-implementation/runs/1.11.2-sleep-safety-transition-2026-10-08.md`
**Prior review superseded:** `0e1a3bf8cc64f2ba7a87e9b75ba2a840c9387ab2`, `workflows/implementation-reviews/2026-10-08-sleep-safety-transition-review.md`
**Review branch:** `fix/review-sleep-safety-transition-r3-2026-10-08`
**Latest main merged before handoff:** `61f278e268c3ed2bcc4d95e8f7bb6e9c543cfa82`

## Scope and source identity

I re-read the correction diff from the original implementation. It changes six Java files across the rest-safety hooks, the 1.11.2 provider, and `ServerPlayerMixin`; the implementation delta is 41 insertions and 5 deletions. I also read the worker's updated implementation record, the previous review, `ChangeResolver`, `MovementRuntime`, the active server mixin configuration, and the exact 26.2 `ServerPlayer.startSleepInBed` body. No generated source was edited.

The accepted immutable source remains commit `cdc8db327b9d99fb6344c1f98e2f12f8774dc6a1`, path `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`, Git blob `a5c69882b5cb15ae110352aaa618bd2c49a153e7`, raw SHA-256 `d98ec64359ac2cc8857383a9f6ddffdcf3c3b003c8752f9b85bfb290f470b797` (7,473 bytes). Its blind acceptance is recorded in commit `960ce5644ceacd8e27bd3cb9145bd4eaf221379c`, `workflows/coverage-review-2026-10-07/early.md`.

The accepted behavior is bounded: A (1.11.2) queries nearby monsters without an anger filter; B (1.12.2) filters through `IsAngryAtPlayerPredicate`. In B, the ordinary monster default returns true, while `ZombiePigmanEntity.isAngryAt` returns `isAngry()`. Thus, for the finding's only-nearby-non-angry-pigman case, A rejects rest and B permits it. The original mapped JARs remain unavailable; the accepted revised source manifests do not prove identity with those originals. The first release using the anger predicate is still unknown within `(1.11.2, 1.12.2]`.

## Corrected implementation checks

### Old predicate now follows the resolver

`SleepSafetyTransition` no longer accepts a selected-version argument or gates its behavior on equality with `V1_11_2`. For a resolved historical hook, a `ZombifiedPiglin` returns `true` without evaluating the modern anger predicate; other monsters lazily evaluate that original predicate. The `V1_11_2` provider registers this change under the separate `player.rest-safety` mechanic key.

`ChangeResolver` selects the closest change not older than the selected profile, so this `V1_11_2` implementation is the candidate for earlier profiles through `V1_11_2`. The available exact source endpoints 1.8.9, 1.9.4, 1.10.2, and 1.11.2 all run the monster query without an anger predicate; their `PlayerEntity.java` SHA-256 values are respectively `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`, `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`, `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`, and `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`. The exact 1.11 and 1.11.1 sources are still requested by the worker record and were not available in the ready set; do not mark those exact releases source-closed or claim a release boundary from their absence.

### Creative query gate is narrowly dispatched

`ServerPlayerMixin` modifies the boolean expression for `ServerPlayer.isCreative()Z` only in `startSleepInBed(BlockPos): Either`. In the ready 26.2 source (`ServerPlayer.java`, SHA-256 `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`), that method has one `isCreative()` call: the `if (!this.isCreative())` guard around the nearby-monster query at lines 1217–1225. Returning false from the `V1_11_2` query-gate hook opens that query for a creative player; it leaves the non-creative path unchanged. The mixin's method descriptor constrains the selector to this one sleep-admission method, so creative checks in unrelated `ServerPlayer` methods are not modified.

The query still runs after the method's existing awake/alive, bed-rule, range, obstruction, respawn-position, and sleepability checks, and before the rest transition. The `@ModifyArg` hook still wraps the single `getEntitiesOfClass` predicate argument. For the old-profile non-angry pigman case, it returns true without calling the modern predicate, preserving the historical early rejection; for other monsters, vanilla predicate evaluation stays lazy and in order. The correction changes only this player's server-side admission path; it does not change anger production, mob AI, combat, health, or non-player behavior.

`MovementRuntime.find` returns empty when emulation is disabled, the selected profile is `CURRENT`, or the entity is not a player. The new expression hook then returns the native `isCreative()` value, and the predicate wrapper calls the original predicate. Both mechanics have distinct keys and one implementation each. The server mixin is already listed in the server mixin set. No overlap was found with passenger handling, saved fall-flying data, or the existing pose/dimension hooks, which run at separate operations.

## Adjacent profile coverage still open

The new query-gate mechanic emulates `V1_11_2`, so it correctly restores the confirmed old query path for that profile and older selections. `V1_12` and later do not resolve it. In 26.2, creative players skip the monster query. The accepted B source at 1.12.2, however, runs the query unconditionally with its anger predicate. Therefore, for a creative `V1_12` player with an angry zombified piglin or another blocking monster nearby, 1.12.2 rejects rest while this 26.2 path skips the query and admits it. For the accepted finding's non-angry-pigman input, B's predicate is false and both paths admit rest; B permits the non-angry pigman.

This adjacent source-backed gap is separate from the bounded non-angry-pigman finding. The first later release that introduced the creative query guard has not been established. Keep the pair partial and track this as a separate immutable, independently reviewed query-control-flow finding; establish the later cutoff (including the 1.12 patch releases) before extending the version registration or claiming `V1_12` creative rest coverage.

## Six review angles

1. **Requirement fit — PASS for the bounded finding.** Old profiles now reject a non-angry zombified piglin; the 1.12.2 endpoint still admits that pigman through the modern anger predicate.
2. **Correctness and edge cases — PASS for the correction.** The exact creative expression now reaches the safety query for old profiles, while the original predicate remains lazy for unaffected monsters and native profiles.
3. **Missing coverage — PASS with explicit limits.** Exact 1.11/1.11.1 source is still requested. Broader creative-query parity for `V1_12` is an adjacent open behavior requiring its own source boundary and review.
4. **Conventions and duplication — PASS.** The query gate and monster predicate are separate, narrow mechanic hooks registered through the existing provider; no duplicate sleep hook or loop was added.
5. **Permissions and visibility — PASS.** No permissions or access rules change. The historical rest result is user-visible by design; current/disabled profiles retain the native result.
6. **Security — PASS.** No new trust boundary, persistence, external input, or swallowed exception is introduced.

## Validation and integration

The source-pair status remains active/partial; the reviewed change does not establish whole-pair movement coverage or runtime parity. No tests, build/Gradle, game, client, server, TAS, Gym, Docker, or push operation was run.

The review branch contains latest main `61f278e268c3ed2bcc4d95e8f7bb6e9c543cfa82`. The commits after `a5612be5107c9e4e3d604e43e7a481c5d868c56c` change three source-preparation and release-roster documentation files only; they have no sleep, pose, saved-state, or query-hook overlap. The merge was clean.

**Next step:** accept this bounded implementation correction. Keep the 1.11/1.11.1 exact-source request and separate later creative-query boundary open; do not treat this re-review as source-pair closure or runtime validation.
