# Source audit response: 1.17.1 to 1.18.2

Date: 2026-10-08
Author response checkpoint: post-freeze correction; the earlier source freeze and independent audit records remain immutable.
Pair status: partial. This response resolves the stated author-side row concern but does not accept the independent audit or freeze the pair. A fresh independent full-pair audit must review this response and the exact frozen report object.

## Audit target and discrepancy

The review target is audit commit `7872eb6457f051df3575f10299ed1aaf1c13eddb` and its report `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/FULL-PAIR-AUDIT-2026-10-08.md`.

The audit records the frozen `run.md` binding as Git blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`, raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`. Re-reading that exact object from the audit commit and the author branch yields the same blob. Parsing its 28 `### Slice` sections by each section's own anchored `- Status:` field gives 13 `compared-no-difference`, 15 `findings`, zero `pending`, zero `in-progress`, zero `not-applicable`, and zero `blocked` rows. The coverage counts agree with the old handoff's `13/15/0` terminal count.

In that exact frozen report object, `### Slice T-PLAYER-UNLOADED-CHUNK: default dimension minY travel response` has `Status: findings`, not `pending`. Its parent coverage and dependency evidence also identifies `D-OVERWORLD-MIN-Y` as resolved. Therefore the audit's stated `13/14/1` parse and pending-row identification cannot be reproduced against its own recorded run blob/raw hash. This response leaves the old freeze, its report, and the audit unchanged and routes this exact object discrepancy for fresh independent verification.

## T-PLAYER-UNLOADED-CHUNK disposition

Disposition: `findings` for the paired source difference under the stated active-dimension inputs; it is not a universal behavior claim.

The reachable path is LocalPlayer ordinary tick -> LivingEntity `aiStep` / `travel` -> ordinary-air branch. With no Levitation, an absent XZ chunk, and `-64 < player Y <= 0`, the branch compares player Y against `LevelReader#getMinBuildHeight()`. The built-in default Overworld minY is 0 in 1.17.1 and -64 in 1.18.2. A therefore writes local vertical velocity `0.0`; B writes `-0.1`. Later common vertical friction preserves the difference (`0` versus `-0.098` when friction is applied, or `0` versus `-0.1` when discarded).

The condition is environment-input scoped: if the active dimension supplies the same server-synchronized minY on both sides, this difference disappears. `hasChunkAt(BlockPos)` checks XZ chunk presence, and terrain generation, void damage, and damage/health production are outside this finding. Emulation eligibility remains a separate later decision.

Paired source evidence is the exact `LivingEntity#travel`, `DimensionType.DEFAULT_OVERWORLD`, and `LevelReader#getMinBuildHeight/#hasChunkAt(BlockPos)` ranges listed in the frozen slice. The candidate is `findings/F-014-overworld-min-y-unloaded-chunk-fall.md`, Git blob `ca968ab66df2cb44ea8c0918a3b4ff870740fc93`, raw SHA-256 `65161ef4a77c6c369af2a74e8a1cc4673cf2f724c8b94fa826548c04a05ae25a`. Its source snapshot checkpoint is `879ac0330e483e029e170d7e28430fae57131efa`.

## Coverage and gate

All 28 declared slice rows remain terminal in the frozen report: 13 compared-no-difference and 15 findings. The seven required inventory headings remain marked complete in that report: `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`, `INV-MODIFIERS`, `INV-EXTERNAL`, and `INV-EXCLUSIONS`. The author closure lists no unresolved source dependency; `D-OVERWORLD-MIN-Y` is explicitly closed by the paired default-dimension data, LevelReader semantics, and the exact travel consumer.

This is a correction to the audit's claimed row parse, with the relevant environmental input boundary made explicit. It is not a claim that the audit accepted the pair. Pair status remains `partial`; implementation reconciliation and runtime validation remain pending/not performed. Fresh independent re-audit is the remaining gate.

## Immutable prior records

- Frozen report `run.md`: Git blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`; raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`.
- Frozen handoff `SOURCE-HANDOFF-2026-10-08.md`: Git blob `62180420b773cbfafe099330a1bbf66de6755a05`; raw SHA-256 `f0b27580679b5b839323b4ee2d5c78745ef59c8fe0d8410722d00fe12bddc134`.
- The previous author freeze and audit history are preserved by Git ancestry. This response does not replace their historical bindings.
