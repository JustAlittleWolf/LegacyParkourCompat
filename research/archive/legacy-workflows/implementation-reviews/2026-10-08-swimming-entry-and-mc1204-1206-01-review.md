# Independent static reconciliation review — swimming entry and MC1204-1206-01

**Review checkout:** `fix/review-swim-entry-and-mc1204-jump-2026-10-08`  
**Current main reviewed:** `a5612be5107c9e4e3d604e43e7a481c5d868c56c`  
**Scope:** two independent static reviews; no code edits, tests, build, runtime, Docker, or push.

## Decision 1 — Swimming entry and sprint-start coverage

**SwimmingUpdate source correspondence: ACCEPT for the bounded 1.16.2 profile change. Overall historical sprint-start coverage: REQUEST CHANGES (one confirmed P2 gap).**

### Evidence and integrated-code identity

The integration register resolves the change to implementation commit `5c419e8d950cf8e62174a4562cc352a1d952747f`, accepted implementation-review commit `6ce9b54d1b9eb3bd623e1ab172d1654da3d7af47`, and integration merge `8352994616ce1fb64c2939f9240f21121b2ce76b`; see `workflows/source-campaign-2026-10-07/integration-review-2026-10-08.md`, section “1.16.2 swimming-entry gate”. The current `main` is later than requested checkpoint `270e8c85` and contains that checkpoint. The later relevant `EntityMixin` history does not replace the `updateSwimming` injection.

The implementation is in `change/v1_16_2/SwimmingUpdate.java:9-21`, provider registration `change/v1_16_2/MovementChanges.java:9`, and `EntityMixin.java:39-48`. It changes only player `Entity.updateSwimming()` at HEAD, writes the selected behavior’s result and cancels vanilla only when a behavior resolves. The predicate matches the accepted 1.16.5 source branches: already swimming requires sprinting, in-water, and not riding; entry requires sprinting, underwater, and not riding. The source snapshot is `78683ba65928004ce8b7b6b9371359164a68d43:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md`, SHA-256 `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32`; independent source review `6e045f3d50f9248ab329c5bd698caa935e8a9be3` accepted it. This is source correspondence, not full-pair closure or runtime parity.

Resolver inspection confirms `V1_16_2` covers releases 1.16.2–1.16.5 (`ParkourVersion.java:64`); the registered change also resolves for earlier selections. For selected versions before `V1_13`, the method returns false (`SwimmingUpdate.java:12-14`); `V1_13` through `V1_16_2` execute the predicate; `V1_17` and later do not resolve this older registration. `CURRENT` resolves no changes (`ChangeResolver.java:21-23`), and the mixin leaves native behavior intact when lookup is empty. Non-player entities bypass the hook.

### Sprint-start source check and confirmed gap

I read the exact ready-tree 1.12.2 and 1.13.2 `LocalClientPlayerEntity.java` bodies at the source-review ranges. The 1.12.2 source hash is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; the 1.13.2 source hash is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. Their ready source-manifest hashes are respectively `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The independent wiki-snapshot review `5c94a3d106f010cd033d19c9aa48f47089987b30` correctly flags its earlier narrative/hash transcription errors; the source itself confirms:

- 1.12.2 double-tap sprint at `LocalClientPlayerEntity.mobTick():718-732` requires `onGround`; its separate held-sprint-key branch at `734-740` does not require ground and has no water exclusion.
- 1.13.2 double-tap at `734-748` allows `onGround || isSubmergedInWater()`. Its separate held-key branch at `750-758` allows out-of-water or fully submerged states, excluding partial water.
- Equivalent input cases distinguish them: with normal food/flight eligibility, forward input at least `0.8`, no item use/blindness, and a sprint tap while airborne in water, direct-key start is permitted in 1.12.2 but double-tap start is not. In 1.13.2 fully submerged airborne input permits both paths; grounded partial-water input permits double-tap but not direct-key start.
- Water/submersion state is produced before these local sprint gates consume it: 1.12.2 `Entity.baseTick()` calls `checkWaterCollisions()` before the local player tick route; 1.13.2 `PlayerEntity.tick()` refreshes submersion before `super.tick()`, and the Entity water-state helper updates water and swimming before the later local-player `mobTick()`. `KeyboardInput.tick()` / `input.tick()` precedes each sprint branch. The discovery report records the enclosing tick ranges and dependencies at `workflows/source-campaign-2026-10-07/1.12.2--1.13.2/run.md:123-149`. Original mapped-JAR identity/equivalence remains unproven in that report.

The selected-profile implementation does not preserve the two distinct old input predicates. `change/v1_12/WaterSprintGate.java:8-13` returns `true`; `LocalPlayerMixin.java:156-168` and `175-180` feed that same shallow-water allowance into `isSprintingPossible` for both `canStartSprinting` and sprint continuation. It does not provide a separate double-tap ground guard. For `V1_12`, this can allow current `LocalPlayer.aiStep()`’s airborne double-tap route to start sprinting in water, although 1.12.2 requires ground for that route. For `V1_13`, the V1_12 water-gate registration is no longer eligible, and current `Entity.isInShallowWater()` is `isInWater() && !isUnderWater()` (26.2 source `Entity.java:1617-1619`). Current `LocalPlayer.canStartSprinting()` applies that shared gate before both the double-tap and held-key paths (`LocalPlayer.java:806-818, 1135-1147`). Consequently, grounded partial-water double-tap is rejected along with the direct-key route, although 1.13.2 permits the former and rejects only the latter. The shared input path also lacks the 1.12.2 on-ground condition for the double-tap route.

This is a confirmed existing profile-coverage gap in sprint input, not a defect in the new `SwimmingUpdate` predicate or the source narrative. It matters to the swimming hook because `SwimmingUpdate` consumes `player.isSprinting()` for selected `V1_13` and later profiles, so a wrong sprint-start flag can change whether the historical swimming transition occurs. The smallest complete follow-up is a branch-aware sprint-start mechanic at the local input decision point; a shared water-eligibility boolean cannot represent both source branches. No code was changed under this review assignment.

### Six review angles

1. **Requirement/scope:** swimming update is limited to the accepted predicate; the larger selected-profile sprint prerequisite is not fully emulated.
2. **Correctness/edge cases:** swimmer arithmetic/conditions match the accepted 1.16.5 source; the branch-specific sprint mismatch above is confirmed.
3. **Coverage:** player-only, profile resolver, old-version early return, later-native fallback, and current/disabled fallback were traced; input branches remain the single confirmed issue.
4. **Architecture:** thin common hook dispatches a narrow mechanic; the sprint hook lacks a branch-specific seam.
5. **Permissions/visibility:** no access-control or UI changes.
6. **Security:** no new trust boundary, external input handling, persistence, or resource access.

## Decision 2 — MC1204-1206-01 no-code reconciliation

**ACCEPT — no code needed for the bounded finding.**

The source finding is commit `1985fa829eb597acbc23685935c5f6929c078f78`, path `workflows/source-campaign-2026-10-07/1.20.4--1.20.6/findings/MC1204-1206-01.md`, SHA-256 `eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda`. Blind source review `581b5bd2cb44981c4889eaf05ea739134e332b87` accepted this finding and the reachable ground-double-tap route. The existing no-code reconciliation is `da579ba8ef35f91aae0bb42fd79376057a1dddae:workflows/fix-implementation/reconciliations/MC1204-1206-01.md`.

The 1.20.4 source has no `jumpFromGround()` call in the grounded flight-toggle branch (`LocalPlayer.java:726-732`, file SHA-256 `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`). 1.20.6 adds the call only after flight toggles on and the player is grounded (`LocalPlayer.java:729-739`, SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`). Current 26.2 retains that call at `LocalPlayer.java:841-845` (SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`). The call delegates into the player jump chain; the finding does not emulate damage, attack resolution, food production, or non-player movement.

Current code binds `FlightActivationJump` to `V1_20_2` (`change/v1_20_2/MovementChanges.java:11`, `FlightActivationJump.java:9-14`). `LocalPlayerMixin.java:97-109` wraps the `jumpFromGround()` invocation in `LocalPlayer.aiStep()` and dispatches the hook; without a resolved behavior, it invokes vanilla. The reviewed 26.2 method has exactly one such invocation, inside the grounded flight-activation branch, so the wrapper does not suppress an unrelated ordinary jump. `ParkourVersion.V1_20_2` includes 1.20.2–1.20.4 and `V1_20_5` includes 1.20.5–1.20.6 (`ParkourVersion.java:72-73`). The resolver selects the closest eligible change: selected `V1_20_2` and earlier emulate the older no-call behavior; `V1_20_5` and later leave this V1_20_2 hook ineligible; `CURRENT` has an empty change map. The ordinary native/current path therefore retains the 26.2 call.

This accepts only MC1204-1206-01. MC1204-1206-02 remains outside the review and the boundary remains unknown within `(1.20.4, 1.20.6]`; no claim is made for 1.20.5 source or for a finer first-release boundary. No health, combat, or non-player simulation was added.

## Main integration and validation limits

The review checkout began from current local `main` (`a5612be`), which contains checkpoint `270e8c85` and the swimming integration merge `83529946`. Relevant main-line changes since that merge do not replace the reviewed swimming injection or the flight-jump wrapper. Main was therefore already integrated in the review base; no merge conflict or semantic overlap was found in the reviewed hooks.

No tests, build, client, TAS, server, Gym, Docker, or runtime check was run. Pair discovery and final trajectory parity remain separate and unclaimed.

Reviewer: independent static reconciliation. Date: 2026-10-08.
