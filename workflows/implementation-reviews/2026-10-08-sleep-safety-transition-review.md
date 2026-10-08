# Independent review: sleep-safety transition

**Decision: REQUEST CHANGES.** Two behavior gaps are recorded below: one confirmed and important, one likely and important under the repository's version-resolver contract. No security finding was confirmed.

**Reviewed implementation commit:** `d892a7ad527fa5a52f57fdb4ef48c178f8a21f41` (`fix: emulate 1.11.2 sleep safety transition`)
**Worker branch final tip:** `4197c0cc411fe7f83a2a1c6f4392752ee3edc4fa`
**Review base / merge base:** `270e8c85d656a0d2fae0770799e207ed1557d9ba` (`integration/main`)
**Review branch:** `fix/review-sleep-safety-transition-r3-2026-10-08`

## Scope and source identity

The implementation commit changes four Java files (62 insertions, no deletions): the `v1_11_2` provider and change, the `PlayerRestSafetyBehavior` hook, and the server `ServerPlayerMixin` bridge. I also read the worker's implementation record at `workflows/fix-implementation/runs/1.11.2-sleep-safety-transition-2026-10-08.md` as context. Generated Minecraft sources were not changed. I reviewed the active mixin configuration, `MovementRuntime`, `ChangeResolver`, the selected version ordering, and the adjacent sleep/pose/saved-state paths.

The accepted source identity is commit `cdc8db327b9d99fb6344c1f98e2f12f8774dc6a1`, path `workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md`, Git blob `a5c69882b5cb15ae110352aaa618bd2c49a153e7`. The checked raw bytes are 7,473 bytes with SHA-256 `d98ec64359ac2cc8857383a9f6ddffdcf3c3b003c8752f9b85bfb290f470b797`, matching the blind acceptance record in commit `960ce5644ceacd8e27bd3cb9145bd4eaf221379c`, `workflows/coverage-review-2026-10-07/early.md`. The accepted finding establishes the old 1.11.2 and new 1.12.2 endpoint behaviors only; it does not establish the first changed release in `(1.11.2, 1.12.2]`. The staged source manifests match the cited A/B source hashes. The original derived mapped JARs are unavailable, so equivalence to those originals remains unproven.

## Findings

### 1. Creative players skip the modified predicate — confirmed, important

**Location:** `src/server/java/me/wolfii/legacyparkourcompat/mixin/ServerPlayerMixin.java:19–35`.

The mixin only changes the predicate argument to `ServerPlayer.startSleepInBed`. In the current 26.2 source (`ServerPlayer.java`, SHA-256 `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`), the entire nearby-monster query is inside `if (!this.isCreative())` at lines 1217–1225. For a creative player, the query never runs, so the wrapped predicate is never called.

Reproduction: a living, awake creative player meets the natural-dimension, time, and bed-range guards, with only a non-angry zombified piglin in the query box. Current 26.2 skips the query and admits sleep; the accepted 1.11.2 source (A `PlayerEntity.java`, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`) and 1.12.2 source (B `PlayerEntity.java`, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`) both perform the nearby-monster query without a creative-mode guard. Both historical endpoints reject sleep before the player state transition.

**Smallest fix:** make the historical rest-safety decision reachable for creative players too, while retaining the current-version path unchanged. The patch must reach the query/admission decision rather than only rewriting a predicate that is skipped.

### 2. Earlier profiles resolve the hook but the implementation disables it — likely, important

**Location:** `src/main/java/me/wolfii/legacyparkourcompat/change/v1_11_2/SleepSafetyTransition.java:11–18`.

`ChangeResolver` returns the closest registered change whose emulated version is not older than the selected profile. With this as the only `player.rest-safety` implementation, selections from `V1_8` through `V1_11_1` resolve the `V1_11_2` change. The additional `selected == ParkourVersion.V1_11_2` condition then makes that resolved behavior a no-op for every one of those profiles, delegating to the 26.2 anger predicate. A non-angry zombified piglin can therefore permit rest in those selections even though the closest applicable historical hook is the accepted old-side `V1_11_2` behavior.

**Smallest fix:** let the selected closest hook apply its 1.11.2 behavior to earlier profiles, unless exact earlier source proves a distinct rest-safety predicate; if it does, add that source-backed versioned behavior instead. The available ready-source set contains 1.11.2 and 1.12.2, but not 1.11 or 1.11.1. Therefore the resolver mismatch is confirmed, while the exact 1.11/1.11.1 historical result remains unverified. No first changed release inside `(1.11.2, 1.12.2]` is inferred here.

## Six review angles

1. **Requirement fit — FAIL.** The patch does not preserve the accepted server admission result for creative players; older selections that resolve the hook also do not receive its predicate behavior.
2. **Correctness and edge cases — FAIL.** Creative-mode control flow bypasses the modified predicate. The exact-version guard also defeats the selected closest hook for earlier profiles.
3. **Missing coverage — FAIL with limits.** Creative admission is a source-proven case. Exact 1.11 and 1.11.1 source behavior was not available in the supplied ready-source set, so earlier historical results remain unverified.
4. **Conventions and duplication — PASS.** The patch adds one shared player mechanic, registered through the existing 1.11.2 provider, and keeps historical code out of the mixin. No duplicate rest-safety implementation was found.
5. **Permissions and visibility — PASS.** Before the change, server sleep admission used the vanilla 26.2 predicate; the patch intends historical admission for an emulated profile. The current/native and disabled paths preserve the original predicate. The changed sleep result is user-visible by design; no permission or access control changes.
6. **Security — PASS.** The change adds no external input, persistence, or trust boundary. It does not swallow exceptions or substitute for vanilla when no hook resolves.

## Verified behavior and limits

The server mixin is loaded by the existing server mixin list. Its lazy `BooleanSupplier` preserves the vanilla predicate call order for the cases that reach it: `SleepSafetyTransition` returns `true` for a zombified piglin under its exact-version gate and otherwise calls the original predicate. `MovementRuntime.find` keeps `CURRENT`, disabled, and non-player contexts on the vanilla path. The hook runs only during the target player's server bed-admission method; it does not emulate anger production, mob AI, combat, health, or non-player movement. The existing pose/dimension behavior acts on player sleep state later, and the saved fall-flying hook is in a separate save-data method; neither overlaps this admission injection. Passenger handling remains in vanilla after the admission query.

The main commit `270e8c85d656a0d2fae0770799e207ed1557d9ba` was already the review branch base. Its changes are limited to slipperiness integration evidence and documentation, with no semantic overlap in the sleep, pose, or saved-state paths. The review branch therefore already contains the latest available `integration/main` and requires no additional merge commit.

No tests, build/Gradle, runtime, game/client/server, TAS, Gym, Docker, or push operation was run. Runtime parity remains unvalidated; the source pair remains active/partial, and its original derived mapped JARs remain unavailable.

**Next step:** correct the two listed implementation gaps, using exact 1.11/1.11.1 source if the earlier-profile predicate is meant to differ, then request a fresh independent review before integration.
