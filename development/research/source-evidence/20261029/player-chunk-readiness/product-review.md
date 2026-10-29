# Product review: player chunk readiness

## Verdict

**ACCEPT** for product commit `f25ccb999d57a3ee4ec622717a247e9a6fd2211f` plus corrective follow-up `6e72ce24` (`Preserve readiness timeout warning and receiver`). The assigned worktree is clean at the reviewed tip. `git diff --check` passes from base `78e3fd8762540b61403e71f17e6eb1ae649848d8` through `6e72ce24`.

## Resolver and scope

A static resolver projection over the exact product tree reports these selections for `PlayerChunkReadinessHook`: selected `V1_21_2` -> `NativePlayerChunkReadiness`; `V1_21_4` and `V1_21_6` -> `CompiledPlayerChunkReadiness`; `V1_21_9` and `V1_21_10` -> `TimedCompiledPlayerChunkReadiness`; `V1_21_11` and `V26_1` -> `VisiblePlayerChunkReadiness`; `CURRENT` -> no eligible provider/native fallback. Thus the `V1_21_6` provider correctly covers the proven .4–.8 compiled-only interval, while the .2/.3 native boundary remains explicit. The .10 timeout variant begins at .9, and the visible variant begins at .11.

The client mixin targets the current `LevelLoadTracker$WaitingForPlayerChunk.tick()` readiness invocation. It passes the `LocalPlayer` to `MovementRuntime.find`, leaves native behavior for the lower fence and unselected/current profiles, and uses a lazy, per-invocation cached `player.blockPosition()` for both build-height and section queries. The compiled-only adapter reproduces the current renderer's null-view-area and missing-section false cases before testing its compiled mesh sentinel. The visible adapter delegates to the full compiled-and-visible renderer predicate. The 26.2 camera-render-state callback remains native through the no-provider path.

The native receiver is correctly passed to all three original-operation paths (`nativeReady`, native fallback, and timeout-warning preservation). For instance invocations, the `WrapOperation` handler receives the receiver as `@Coerce Object`; the corrected `original.call(waitingForPlayerChunk)` calls match the target descriptor and this repository's existing instance-call patterns.

The timeout path preserves the strict `Util.getMillis() > timeoutAfter` check. The historical 1.21.10, 1.21.11, and 26.1.2 sources emit the same timeout warning. Current 26.2 `WaitingForPlayerChunk.isReady()` also emits that warning at its timeout branch, so invoking the original operation once from `logTimeoutWarning()` preserves the text and single warning. Since `Util.getMillis()` is monotonic, the second check after the already-true strict check remains on the timeout branch; the original result is ignored and the selected historical predicate returns true.

The mixin is listed in the client mixin array. Existing `LocalPlayer` 60-tick fallback and loaded-state code are untouched. No modern-only callback path is emulated. No code or test/runtime/build execution was performed as part of this review.

## Evidence bindings

- Readiness report: `.native-source/20261028/frontier-c/12110-262-player-chunk-readiness-closure/report.md`, SHA-256 `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1`.
- Independent source audit: `.native-source/20261028/frontier-c/12110-262-player-chunk-readiness-audit/audit.md`, SHA-256 `42954706e86c8ca9b35b6d557436aaa1d54ae99c1f25cb029bf7e009c50618c3`.
- Earlier-readiness audit: `.native-source/20261028/frontier-c/12110-262-player-chunk-readiness-audit/supplement-01-earlier-readiness-audit.md`, SHA-256 `8f99267441fa11bb4d4038a948331b6e56133e3aec65fb6cd5d50aaba50ba7a5`.
- Neutral candidate: `af60be8c4d8603eed41917ae5bf6727b4a90493a`; candidate review SHA-256 `dcdc6bf6ae2bd501bbdca394258f2a0d89149c2b5589265a0f0bdd83828beee2`.
- Current 26.2 tracker source used for receiver and warning check: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\net\minecraft\client\multiplayer\LevelLoadTracker.java`, SHA-256 `f08f5d126298ce522d0ca485f89ee6847c4fa986006ffa6d848cf734fbdc3e45`.
- Historical timeout-warning source hashes: 1.21.10 `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13`; 1.21.11 `da4ee92efe3b9e6cbfe312248346e6a2e159acec884cab0a3b84d7483ffb6dc5`; 26.1.2 `141426abb6fc196ea351715f5c85a481475f340eeb377e68c1a3a48a64f8ad03`.
