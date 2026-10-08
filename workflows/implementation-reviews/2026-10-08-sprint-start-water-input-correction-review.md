# Independent review — sprint start water profile correction

**Decision: ACCEPT the proposed correction** in `4ade0220a6c5cfa299c08acab57594270d444660` for the confirmed profile regression in implementation commit `bc1e178f7d05786eea357896ddf90c6a0e5cf25d`. This decision applies to the submitted static code change and its source-supported scope. It is not a full source-pair freeze or runtime validation. The earlier review report’s decision on `bc1e` remains REQUEST CHANGES for the uncorrected commit; this report independently reviews and accepts the correction.

## Scope and bindings

Reviewed the complete seven-file implementation delta from base `a5612be5107c9e4e3d604e43e7a481c5d868c56c` through `bc1e178f7d05786eea357896ddf90c6a0e5cf25d`, then independently reviewed the proposed code correction in `4ade0220a6c5cfa299c08acab57594270d444660`. The correction removes the selected-profile exception from `change/v1_12/SprintInputStart.allowDoubleTapStart`, leaving `return vanilla && player.onGround();`. It changes no other Java code.

- Implementation and evidence handoff: `bd396a4f122f0253c806515b3991df81df6ea8e3`, `workflows/fix-implementation/2026-10-08-sprint-start-water-profile-gap.md`.
- Prior review: `01d4ee9094b2988def1a8d1aad73ffeec70fed8d`, `workflows/implementation-reviews/2026-10-08-sprint-start-water-input-implementation-review.md`.
- Latest `main` at review: `7e7b7bb7068ec7b68f3a8f09cbb42bc498a04bf4`; it is an ancestor of this review branch via merge `1a6d61d8ff9ae3adda3e9a733724cb0648390587`. The main changes inspected are separate from the sprint-input classes and do not conflict with this correction.
- Source finding: `workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md`, SHA-256 `8698af8d4474a11725abdcfb1aabcea8a84e6fb44c31a8254bf6a40810b5f114`. The pair remains active/partial.
- Ready-source hashes recorded by the evidence handoff: 1.8.9 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; 1.9.4 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; 1.10.2 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; 1.11.2 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; 1.12.2 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; 1.13.2 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- Current target source is 26.2 `LocalPlayer.java`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- Secondary wiki corroboration is snapshot `1e5b3a65cdb9946f1fe9553022950454038d9c68`, file `workflows/wiki-audit-2026-10-07/sprint-swimming-1.12.2-to-1.13.2-r2.md`. I read the raw blob `d7118dc7b16a16df254e1b85836bd107690b955f` and recomputed SHA-256 from its bytes: `62140b4d629831c430702bf7aff78fa87784bedd65dbfcb28b47dbb7a19fa782` (5,902 bytes). The secondary artifact is corroboration; decompiled source remains primary.

## Finding and correction

`ChangeResolver` picks the closest registered change whose `emulates` version is at or after the selected profile, separately for each mechanic. Therefore the V1_12 sprint-input change is the closest applicable rule for profiles through V1_12, until the V1_13 change becomes applicable. The submitted implementation had an exact-target guard that returned the native result for older profiles, so those profiles could use the 26.2 airborne double-tap path.

The correction removes that guard. The grounded restriction is supported by the inspected 1.8.9, 1.9.4, 1.10.2, 1.11.2 and 1.12.2 local-player sources. The 1.12.2 and 1.13.2 comparison also confirms the intended boundary: V1_12 requires ground for double-tap and leaves held-key start alone; V1_13 allows double-tap while grounded or submerged, while held-key start requires dry or submerged water. V1_13 resolves its own change and keeps those two branches separate. V1_14 and later do not resolve the V1_13 or V1_12 change. This review does not claim source continuity for every patch release, the first changed release between 1.12.2 and 1.13.2, or any broader 1.13+ behavior.

## Interaction and six review angles

1. **Requirement:** the shallow-water gate and the two local sprint-start routes match the bounded 1.12.2/1.13.2 source finding. The correction restores the older grounded double-tap condition where resolver semantics make the V1_12 change applicable.
2. **Correctness and edge cases:** the new predicate preserves a false incoming value and only disallows airborne double-tap starts. It does not gate the held sprint key. The V1_13 predicates are unchanged and remain distinct.
3. **Call order and timer:** 26.2 `LocalPlayer.aiStep()` decrements the trigger timer, checks shared eligibility, then calls `setSprinting(true)` first in the double-tap branch and second in the sprint-key branch. The two mixin wrappers target those ordinals; later stop calls use `false` and are not intercepted. The timer-window behavior is unchanged.
4. **Shared eligibility and water continuation:** forward input, food/flight, item-use, mobility restriction (Blindness), and fall-flying/slow-movement checks remain in the shared `canStartSprinting()` path before either wrapped call. V1_13 opens the shallow-water start eligibility and then applies branch-specific water conditions. The correction does not change `shouldStopRunSprinting`, `WaterSprintGate`, `SwimmingUpdate`, or sprint continuation. Existing passenger eligibility remains the shared current implementation and is outside the historical local-input claim.
5. **Registration and conventions:** both version providers register the new behavior. The correction remains a small `MovementChange` delta and does not copy movement loops or alter resolution architecture. Disabled/current profiles have no resolved change and call vanilla through the mixin fallback; only `LocalPlayer` is targeted.
6. **Security and permissions:** no permission, configuration, network, deserialization, or command surface changes.

## Limits

The evidence handoff states that original derived jars are unavailable and revised-derived-to-original equivalence is unproven. The cited ready decompiled source snapshots and their hashes support the reviewed predicates. Exact 1.13.0 and 1.13.1 source is absent, so no first-patch boundary is claimed. The sampled earlier endpoints support the grounded rule but do not prove uninterrupted patch-level continuity. No build, tests, client, server, TAS, Gym, Docker, or runtime validation was performed; the task instructions prohibited those actions. Runtime matrix verification remains separate follow-up work.

Reviewer: independent static review, 2026-10-08.
