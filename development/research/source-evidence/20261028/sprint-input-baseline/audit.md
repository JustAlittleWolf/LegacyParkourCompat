# Product baseline reconciliation: 1.14.1 → 1.14.2 sprint predicates

## Scope and verdict

This read-only reconciliation compares the independently accepted 1.14.1 → 1.14.2 Yarn sprint-input finding against the product exactly at revision `a0448a5d12d6fb714da8a14562eab5db05a8affe`, before product commit `f41e85b2`.

**Verdict: no product code change is required for the bounded sprint-start and sprint-cancellation predicates.** The pre-existing sprint-start provider and native cancellation fallback already produce the audited 1.14.2 predicates on the supported historical keyboard-input path. The two new `V1_14_2` provider classes in `f41e85b2` are behaviorally redundant for this operation. A concise hook-comment clarification of the mapping-era water method name may be retained; it does not require new providers.

This disposition does not assert pair-wide source coverage or runtime parity.

## Bound source evidence

- Source report: `.native-source/20261028/frontier-c/ADJ-1141-1142-yarn/report.md`, SHA-256 `86BADB04F6007EB47E22AFBB9FA79CEF4B71B9D40E34F9551613631B4440C026`.
- Independent source audit: `.native-source/20261028/frontier-c/ADJ-1141-1142-yarn-audit/audit.md`, SHA-256 `D4B6499FA5751D351F839FB9D5DAC86B0AD47EAEA4FB9DCA7629C1D3FDC47989`.
- Both artifacts bind Yarn 1.14.1+build.10 and 1.14.2+build.7 to their exact source manifests. The source audit corrects the report's stop-check scope: 1.14.2 sprint cancellation negates the positive-forward predicate unconditionally, with no water gate.

## Existing sprint-start coverage at the baseline

`ChangeResolver` selects, per hook, the earliest registered provider whose `emulates` version is at or after the selected profile (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:30-31`). At selected `V1_14_2`, the existing `ForwardSprintInput@V1_14` is older and is excluded; the existing `UnderwaterForwardSprintInput@V1_21_4` is the next provider and is selected.

That provider (`src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_4/UnderwaterForwardSprintInput.java:22`) already implements:

```java
cachedEyeUnderwater
    ? storedForwardInput > 1.0E-5F
    : storedForwardInput >= 0.8
```

This matches the accepted Yarn 1.14.2 `method_20623()` behavior: use `Input.method_20622()` (`movementForward > 1.0E-5F`) when the cached water query is true; otherwise compare the float forward input with double `0.8`. The existing dry provider's unsuffixed `0.8` preserves the promoted-float/double comparison.

The baseline wrapper in `src/client/java/me/wolfii/legacyparkourcompat/mixin/LocalPlayerMixin.java:157-172` covers `aiStep()` and `canStartSprinting()`. The accepted Yarn method has three start-predicate call sites: the `aiStep()` pre-input snapshot and both `canStartSprinting()` post-input checks. The wrapper preserves those original timings. It calls the hook only when historical stored keyboard input is active; otherwise it calls the original `ClientInput.hasForwardImpulse()` operation.

The water argument is a cached eye-water query. In the audited Yarn source, `ClientPlayerEntity.isInWater()` reads a cache populated by `PlayerEntity.updateInWater()` through `Entity.isInFluid(WATER, true)`. The baseline modern call site supplies `LocalPlayer.isUnderWater()`, which is the corresponding cached eye-water state. These names must be compared by the query they represent, not interpreted as modern body-water. No second body-water argument is needed.

## Existing sprint-cancellation coverage at the baseline

The baseline has one registered `SprintRetentionInputHook` provider, `SprintRetentionInput@V1_14` (`src/client/java/me/wolfii/legacyparkourcompat/change/v1_14/SprintRetentionInput.java:23`), which returns `storedForwardInput < 0.8F`. It is older than selected `V1_14_2`, so the resolver excludes it and finds no later retention provider.

At `LocalPlayerMixin.java:177-192`, the post-poll `shouldStopRunSprinting()` wrapper then uses its native fallback, `vanilla.call(input)`. The native `ClientInput.hasForwardImpulse()` predicate tests `moveVector.y > 1.0E-5F`, matching the audited `Input.method_20622()` predicate on the post-poll forward input. Therefore negating the native result yields the unconditional 1.14.2 stop condition, including dry input values in `(1.0E-5F, 0.8F]` that the 1.14.1 provider would have treated differently. No water-state branch is required for retention.

## Disposition of the later patch

At the baseline, the start behavior already resolves to the exact predicate later added under `V1_14_2`; the cancellation behavior already reaches the exact native positive-forward predicate when the older `V1_14` retention provider is excluded. Consequently, adding `ForwardSprintInput@V1_14_2` and `SprintRetentionInput@V1_14_2` does not add distinct behavior for the audited path. The explanatory mapping-name note in `ForwardSprintInputHook` may remain as documentation, provided it describes the cached eye-water equivalence and does not imply modern body-water semantics.

No product, source, or backlog files were edited for this reconciliation. No build, tests, game, or runtime checks were performed.
