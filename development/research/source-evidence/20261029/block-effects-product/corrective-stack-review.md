# Final focused review: segment lifecycle corrective stack

**Verdict: ACCEPT** for reviewed tip `4da3fd3a8b36f9960220708c59017bbdf3d1524c` (stack: `17e01885` atop product integration `2194dcfd`). This final review supersedes the earlier REVISE on `17e01885`: commit `4da3fd3a` made the piston origin handler static to match its static target. No build, tests, or runtime checks were run.

## V1_21_4 piston explicit scan origin

Exact 26.2 source declares `PistonMovingBlockEntity.moveEntityByPiston` `private static` and calls `entity.applyEffectsFromBlocks(previousPos, entity.position())` (`PistonMovingBlockEntity.java:192-196`). At `4da3fd3a`, the `@ModifyArg` handler is also `private static`; its target descriptor names that method and exact two-argument invocation, it modifies argument 0, and `@Local(argsOnly = true) Entity entity` binds the unique `Entity` parameter of the static helper. The handler accesses no instance state. The mixin is listed in the client mixin config.

The literal annotation projection with `.native-artifacts/project-resolver.py` against this final worktree yields the intended provider table for `PistonCallbackScanOriginHook`:

| Selected enum | Aliased releases | Provider |
| --- | --- | --- |
| `V1_21` | 1.21, 1.21.1 | `NativePistonCallbackScanOrigin@V1_21` |
| `V1_21_2` | 1.21.2, 1.21.3 | `NativePistonCallbackScanOrigin@V1_21_2` |
| `V1_21_4` | 1.21.4 | `PistonCallbackScanOrigin@V1_21_4` |
| `V1_21_5` and later | 1.21.5+ | no eligible provider; native fallback |

The V1_21_4 provider returns `entity.oldPosition()` only for active, non-removed, physics-enabled client `LocalPlayer`s; other callers keep the current origin. The native fences prevent lookahead from applying the `.4` change to `.0-.3`. The `.4` source calls no-argument `applyEffectsFromBlocks()` after piston movement, which supplies `oldPosition() -> position()`; the adapted current explicit call therefore has the matching endpoint. The correction is version-selected and client/local-gated.

## V1_21_5 latest-batch removal

The exact 1.21.5 `Entity.removeLatestMovementRecordingBatch()` only checks whether the nested movement list is empty, then removes the last batch (`Entity.java:781-784`). Its piston caller invokes removal after its explicit scan (`PistonMovingBlockEntity.java:191`). The product's renamed helper now removes the last custom batch whenever one exists, with no mover-type condition. It is dispatched under the selected `MovementCallbackRecordHook.captureAxisSegments(entity)` gate; at V1_21_5 this is the active client local-player capture provider.

This preserves cases where the piston-limited move returns before recording, where the per-move record gate rejects it, and where an earlier non-PISTON batch is last: the source still pops the latest existing batch. If no custom batch exists, the helper returns false and native removal runs against the current movement list; records were suppressed under that same capture gate, so that list has no custom-equivalent entry to remove. The server packet-listener path does not meet the local-player capture gate.

## DDA check

The corrective stack does not modify `BlockCallbackDda.java`. Its `if (attempts++ > 16) break` cap remains, with up to 17 clip bodies and the separate destination-box replay. It is unchanged by both corrective commits.

## Bound hashes

- Final reviewed commit: `4da3fd3a8b36f9960220708c59017bbdf3d1524c`.
- Earlier corrective commit: `17e01885ad8671317d85e54ac7ddcbdfb8394e6d`.
- Prior integration commit: `2194dcfdd9b0495548aa39dd1c8b3c358572469a`.
- Previous focused review (the REVISE before the static-handler fix): `.native-source/20261029/segment-lifecycle-product-review/corrective-commit-17e01885-review.md`, SHA-256 `F5B35828960FC47CBD9DF21B08102F35AFE0D1F52678538338397D2692D52818`.
- 26.2 unobfuscated `PistonMovingBlockEntity.java`: `706BAB1A13BA99BBD334AAD853B99F72DF58FCE5B6B67D30D41F902B63DD8E06`.
- 1.21.5 Mojmap `Entity.java`: `FC177733E9CC4B2D5CF2562E5529D0F4E0B9C3690F081FBD58AA9119EB173DC4`.
- 1.21.5 Mojmap `PistonMovingBlockEntity.java`: `BD7BCC95FB811B6ED4E50FE0679FCC72E86CBF2445EF72C35ED8E25C80DA361E`.
- `PistonMovingBlockEntityMixin.java` at final tip: `97E23D4E2227F94FD95A20F69945144F3F6139355D93E8F780EC5CCF9E6C47FC`.
- `PistonCallbackScanOrigin.java` (`v1_21_4`): `988299E687E093B77C0E622609B5C6EDFA374FA89F8881B44256963EDD07D82B`.
- `MovementCallbackSegmentsAccess.java`: `11C031FF2C28F4AEF16C44113DBEE43F71A30C801473F6ED26BA6A22C874DC14`.
- `EntityMixin.java`: `B7F7BAB51D6BC5A883E6076D90AC02CF773FAF25BF70380FB35B769FD0C26A82`.
- `MovementCallbackRecord.java` (`v1_21_5`): `8DAC41D4F9420C3BAD1B625338B1451C77178288E986859C0E13BE5A99A676C7`.
- `EntityCallbackBatchMixin.java`: `51A79D77A1DAE2A0F2C2AA7E337AF217FF0E35F2E14A93B0A98A7615CB1B899A`.
- `BlockCallbackDda.java`: `545A4C5E06CCCDB4F53AA697168418BE554F3986BB0A84E294D4D1D025566D81`.
