# Renewed review addendum — residual local-player tick candidates

Bound report: `.native-source/20261030/1152-1165-finish43/local-player-tick-uncovered-members/report.md`, SHA-256 `a0ad62636b1995748e8633a926b54625cae0310531519472362dd8510c919db1` (recomputed and equal to the requested immutable revision).

## Identity and ledger verification

The corrected report now points to the actual `.native-source/20261030/1152-1165-pool33/manifest-verification.json`. Its SHA-256 is `673bc4bd8b0317d8fdc1f5b4f0ff8b8cd4eec105e92b272a078f868f26571085`, matching the candidate `input-hashes.tsv`. The verification data reports 3,332 1.15.2 Mojmap files and 3,523 1.16.5 Mojmap files, with zero missing, unlisted, or mismatched entries. Canonical source-manifest hashes remain `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

All seven `input-hashes.tsv` rows and all six source-file hashes recomputed successfully. Each source-file hash matches its exact entry in the corresponding Mojmap source manifest. Source paths and method bodies are unchanged from the first review; the revised claim text was checked against those exact sources.

## Renewed candidate verdicts

1. **Shift-held clear of `sprintTriggerTime`: ACCEPT.** The new report preserves the conditional double-tap path, ordering, and later movement consequence. The old timer must still be positive after its per-tick decrement during the shift-held tick; if it reaches zero on that tick, this candidate has no later timer-state distinction.

2. **Ability-flight gate on LocalPlayer water descent: ACCEPT.** The new report correctly separates the direct `goDownInWater()` response from the water-current route. The `-0.04F` incoming-Y adjustment in old source remains an exact delta-velocity difference; with equal states and the same flying travel branch, `Player.travel`'s capture/restore leaves a `-0.024` post-travel Y-velocity difference. Position displacement remains dependent on the subsequent travel and collision path.

3. **Ability-flight gate on inherited ground-jump selection: ACCEPT.** The revised order is accurate: inherited `LivingEntity.aiStep` calls virtual `serverAiStep` before its jump-selection block; the `LocalPlayer.serverAiStep` implementation copies sampled input to `this.jumping` when controlled locally. The Player override makes `isAffectedByFluids()` false while ability-flying in 1.16.5, so the new branch skips the jump while old source can select it. Player's flying travel preserves 60% of the incoming vertical impulse. This remains an endpoint difference only; no intermediate introduction release is claimed.

## Boundary

These verdicts address source behavior only. They do not establish pair freeze, complete source coverage, implementation, or runtime parity. No product source, tests, build, runtime, or decompilation was used.
