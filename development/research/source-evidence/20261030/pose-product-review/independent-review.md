# Independent product review: player pose priority

## Reviewed snapshot

- Product commit: `1f5f60626b6b1dd9e5e65c784e2ca1d40890dfee` (`Restore 1.21.4 fall-flying priority over swimming`).
- Product baseline requested: `b30ca6a6`.
- Review scope: product code semantics for the rename to `PlayerPosePriorityHook`, the 1.21.2 lower change, the 1.21.4 change, and the `PlayerMixin` dispatch. No product files were changed; no build, tests, or game launch was run.
- Snapshot identity: hashes below bind the reviewed product files and canonical finding summary.

## Verdict

**Accept for the bounded 1.21.4 fall-flying/swimming selector-order correction, retaining the pre-existing sleeping/fall-flying behavior.** No product defect was found in source fidelity, applicability resolution, hook granularity/order, descriptor, registration, logical-side dispatch, or native fallback.

The 1.21.4 selector returns `FALL_FLYING` when fall-flying overlaps sleeping or swimming. On the accepted non-sleeping path, this corrects the modern result only for `fallFlying && swimming`; all other predicate combinations retain the native desired pose. The modification is on `Player.getDesiredPose` return, before `updatePlayerPose` performs its native fit checks, fallback and final pose write. It does not bypass the common swimming-fit guard or the selected-pose fit/fallback logic. The product summary accurately limits the accepted new finding to a conditional pose-state difference and does not claim box or trajectory parity, sleep reachability, or server state production.

The previous sleeping/fall-flying choice remains at `emulates = V1_21_2`, and the new swimming overlap is at `V1_21_4`. `ParkourVersion` groups 1.21.2 and 1.21.3 under `V1_21_2`; closest-later resolution therefore selects the lower entry for that group and older profiles, and selects the 1.21.4 entry for 1.21.4. `CURRENT` resolves to no changes. This preserves the earlier sleep-only behavior and confines the newly accepted swimming correction to its 1.21.4 profile. The 1.21.4 handler's sleep clause preserves existing behavior; the source audit does not establish ordinary reachability of that overlap.

The operation remains one independently resolved desired-pose hook. The existing `PlayerPoseHook` / `PlayerPoseUpdateMixin` handles post-selection resizing and is a separate operation, not a duplicate selector. Both change classes are public, annotated, no-argument implementations of the single `@MechanicType("player.pose.priority")` interface; automatic discovery registers their distinct emulates versions. The existing client-only `PlayerMixin` is already listed in the client mixin configuration. `MovementRuntime.find` retains vanilla on current/disabled profiles and entities outside client-side enabled players, including logical servers.

## Evidence

- Source report: `.native-source/20261028/frontier-c/1214-1215-pose-predicate-reachability/pose-predicate-reachability.md`, SHA-256 `0b818caeee9006bf5ecd23780062540c95afa8ed002b6a52d0cf25f75d5968d4`.
- Independent source audit: `.native-source/20261028/frontier-c/1214-1215-pose-predicate-reachability-audit/independent-audit.md`, SHA-256 `c132476cd35ae49704d60432cb8984243792f339a635022a605ced18446c9f3f`.
- Canonical permanent finding summary: `development/research/findings/1.21.4--1.21.5/swimming-fall-flying-pose-priority.md`, SHA-256 `8b34510c7c9d7a5f1cfb39310f6ec7b7e1ff42cfd1cdd2c0088f2d196f0334a6`.
- Prior candidate review: `.native-source/20261030/pose-candidate-review/review.md`, SHA-256 `e28ba5c798b9e68c7acf07fd554851bf078ce500bfd0ce1b3bd0d23ab1056d60`; candidate commit `544eedc6e0beca58c57a14c4129767106fdc67e2`.
- Resolver semantics were checked in `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`: it picks the closest emulates version newer than or equal to the selected profile, and returns no changes for `CURRENT`.

## Reviewed file hashes

| File | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/PlayerPosePriority.java` | `fa74e7e05dff955e34983bbccf9486d90a85e2ce15026f8dd6e667b4bc3603cf` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_4/PlayerPosePriority.java` | `f072c13cfb0970f89ee66d7a16a2e43c94a79771adedf69ac786ab205c935a0f` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/state/PlayerPosePriorityHook.java` | `f502ece6a59d391ce190a0c2095fb1ae7baba5437dd88ab544fdf6a8f8f6f1f8` |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java` | `3df49dba6174090e7fe79dc10a8aadd2523a2f9ca812f2462f3b8ea504b8a271` |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerPoseUpdateMixin.java` | `03789953459a8b73cedb506c2a98a579870403ff37f13d7f13c1d0736e59e273` |
| `src/client/resources/legacyparkourcompat.client.mixins.json` | `2c5ece62e37eaff13cb73f047aa06b34663d46b09878b60ca44cd09dca89e854` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | `57f3b806f65b48ad0abb2612cdc67bf84c94d70c3a4a8ea230fd7d6f962ab763` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/MovementChangeDiscovery.java` | `0b0d96c154075c009d31d3f33490893a74a825cc6c654dfed97b2ffbba9568eb` |
