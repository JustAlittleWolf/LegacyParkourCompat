# Static implementation review: WORLD-03 End Portal Frame correction

**Verdict: ACCEPT** (no remaining functional findings; static review only)

## Reviewed identities and scope

- Corrective implementation commit: `8ae770141c4e9ca1b3f5fa3a1459d94132195306` (`fix: match historical push-away sampling and precision`). Corrected feature tip: `ff24abc4f199f6460f79dafd2c83a9a19d0f6121`.
- Review branch `fix/review-world-03-correction-2026-10-08` merged current `main` (`b099aa02d82e57c1672f68375edcbcacdf8e3973`) at merge commit `ce6d3cd6b9f5d5a413201cfb564e134995d03c97` before this report.
- Accepted source snapshot: commit `aa66894e64733ee729bf7176e08232d73b3bc03f`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-03-end-portal-frame-player-ejection.md`, SHA-256 `f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d`, Git blob `dfb558dbf0b03ea9da74522164a84fb7ed4f567b`; blind review `a2e510bdc7b284b4b4d0e7b323f6eea079abee80`.
- Exact read-only source artifacts: 1.8.9 Feather manifest `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 Feather manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; current 26.2 unobfuscated manifest `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Relevant exact source files and SHA-256: 1.8.9 `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; 1.9.4 `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; 26.2 `LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- Focused correction diff: five code files, 82 insertions and 4 deletions. Read the changed suffocation and push-away hooks, both providers, resolver/profile dispatch, current `LocalPlayer` call sites and mixin bindings, the accepted source snapshot, and the current-main fence collision provider. Generated files, builds, tests, and runtime behavior were not examined or run.

## Source parity and resolution

In 1.8.9, `LocalClientPlayerEntity#mobTick` samples four body corners using `getShape().minY + 0.5` (lines 546–549). `pushAwayFrom` constructs a `BlockPos` from those coordinates, whose integer conversion floors them, and `canSurvive` checks only that cell and `pos.up()` (lines 292–350). The 1.9.4 source uses the same sampled Y and two-cell check (call sites 657–660; helper 349–403). Thus the historical End Portal Frame exception is limited to exactly the sampled cell and the cell above.

`EndPortalFramePlayerEjection` now derives `sampleY` from the player's bounding-box minimum plus `0.5D`, then uses `BlockPos.containing` and checks only `samplePos` and `samplePos.above()` (lines 13–26). The query position supplies the candidate's X/Z for both the initial block and each horizontal neighbor. This matches the old probe's floored Y and preserves per-neighbor sampling. It does not special-case a frame in any additional vertical cell. The superclass fallback does not reintroduce such a frame: current 26.2 End Portal Frame states use the default suffocation predicate, which requires a full collision-shape block; the frame shapes (including the eye shape) are not full cubes.

Both accepted endpoints declare `float g = 0.1F` and assign `-g` or `g` to the horizontal velocity field (1.8.9 lines 323–337; 1.9.4 lines 380–394). The current 26.2 helper writes a double `0.1 * directionStep` in its X and Z branches (`LocalPlayer#moveTowardsClosestSpace`, lines 469–475). The new `PushAwayVelocity` hook recreates the float constant and returns the sign-selected float widened to double (lines 12–19). The shared mixin modifies only the selected X argument at call ordinal 0 or Z argument at ordinal 1 (`LocalPlayerMixin`, lines 319–348); the current method has exactly those two `setDeltaMovement(DDD)` sites, each guarded by the corresponding axis branch. The caller supplies a nonzero `+1` or `-1` step whenever either site runs, so the sign test preserves the chosen direction and only the chosen velocity axis changes.

Resolution is independent per mechanic key. `EndPortalFramePlayerEjection` is annotated `V1_8`, and its special case additionally requires target `V1_8`; it is selected only for 1.8 profiles. `PushAwayVelocity` is annotated `V1_9`, the closest applicable response for both selected `V1_8` and `V1_9`; its target guard limits the float response to those profiles. Later selections do not resolve this V1_9 response, and `CURRENT` resolves to no changes, so the native double write remains. The vanilla escape helper retains its existing `!noPhysics` outer gate, four corner order, West/East/North/South candidate order, strict distance comparisons, and axis-only overwrite.

## Current-main interaction

Current `main` added `FencePortalFrameConnection` and its V1_8 provider registration. The merge conflicted only where the two independent registrations were inserted into `change/v1_8/MovementChanges.java`; the merge commit preserves both. The fence hook is keyed as `block.collision` with a fence block-ID variant; the ejection and velocity hooks use `player.client_unstuck.suffocation` and `player.client_unstuck.push_away_velocity`. The `BlockStateCollisionShapeMixin` dispatches player collision shapes, while the End Portal Frame probe reads block states through the local-player `suffocatesAt` hook. These hooks remain distinct and co-apply under V1_8 without replacing one another. `LocalPlayerMixin` has no change between the corrected feature tip and the merged current-main tip; its velocity and suffocation hooks remain separate method sites. No semantic conflict was introduced by the fence provider.

## Six review angles

1. **Requirement:** Both prior parity gaps are corrected: the frame override is restricted to the exact two historical cells, and the 1.8/1.9 push-away response uses the source float value. No extra block-state or collision-shape behavior was added by this correction.
2. **Correctness and edge cases:** No remaining functional finding. The sign input is constrained by the helper's axis-step branches; no zero-axis assignment reaches the hook.
3. **Missing behavior:** Repository search found the expected two `setDeltaMovement` call sites inside `moveTowardsClosestSpace`, and both mixin argument hooks are present. The four historical corner probes and candidate order remain unchanged.
4. **Conventions and duplication:** The response is an independent minimal mechanic and reuses the existing resolver and local-player dispatch. No duplicate physics loop or version-specific copy was added.
5. **Permissions and visibility:** The hooks are on the client `LocalPlayer`; they add no permission checks, public user setting, or block-state mutation.
6. **Security:** No security-relevant input or trust-boundary change was found.

## Limits and next step

Mixin application, runtime movement, and bytecode ordinal binding were not runtime-validated. No tests, builds, game/TAS/server runs, Docker operations, pushes, or implementation edits were performed for this review. The source pair remains partial and this review does not establish a finer first-changed release. No further correction is indicated by this focused re-review.
