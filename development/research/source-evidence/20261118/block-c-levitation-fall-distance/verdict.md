# Independent source audit: Levitation fall-distance writer

Frozen input: `c-pose-effect-reset-lower/levitation-writer-fence.md`, SHA-256 `5f8756601b9ad9d64d0bd565369a8a55350274c0e13ba8400e5294343866fa5f` (verified). Mapping family: Ornithe Feather. All source paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

## Actionable Levitation verdict

**ACCEPT — adjacent addition, exact versions 1.12.2 → 1.13.0.** In 1.12.2 `LivingEntity.moveRelative` enters the regular route under `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. After `move(...)`, its Levitation branch adjusts `velocityY` and has no `fallDistance` assignment. In 1.13.0 the same ordered branch performs the same velocity expression and then assigns `fallDistance = 0.0F`. For a locally controlled player reaching the ordinary route with Levitation, this is a post-move reset. The 1.13.0 method's water, lava and fall-flying route dispatch bypasses this ordinary branch; it is not an unconditional effect-wide reset.

- 1.12.2: `LivingEntity.java` SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `moveRelative` 1519–1523, reset absent.
- 1.13.0: SHA-256 `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`; route guard begins 1460; writer 1564, after `move(...)` at 1556 and the Levitation velocity adjustment at 1562–1563.
- The same writer is present in 1.13.1 and 1.13.2 at lines 1583. Both files have SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.

**ACCEPT — no Levitation-branch writer for every sampled exact stable release from effect introduction through the predecessor.** The effect is registered in 1.9 `StatusEffects.java` (field and lookup at 31, 73), and remains registered in 1.12.2 (32, 75). In 1.8.9 the `LivingEntity` source has no Levitation predicate and the effect registry has no Levitation declaration/lookup. The effect branch in `moveRelative` adjusts vertical velocity but does not write fall distance in each listed release:

- 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4
- 1.10, 1.10.1, 1.10.2
- 1.11, 1.11.1, 1.11.2
- 1.12, 1.12.1, 1.12.2

Per-release `LivingEntity.java` hashes match the frozen report. For 1.9–1.9.3, 1.10–1.10.1 and 1.12–1.12.1, the local nested provenance `sources` entry matches the computed file hash. For 1.9.4, 1.10.2, 1.11–1.11.2 and 1.12.2, the release source-manifest entry matches; their ready metadata identifies the exact release. This verifies sampled aliases only and does not prove unsampled intermediate patch behavior.

## Separate Slow Falling scope verdict

**ACCEPT — narrow reset-only/source-boundary statements in the frozen report.** 1.12.2 `StatusEffects.java` has no Slow Falling field or lookup; 1.13.0 adds both (field at 35, lookup at 81). In 1.13.0 `LivingEntity.moveRelative`, the local-authority guard is followed by `velocityY <= 0.0 && hasStatusEffect(SLOW_FALLING)` and `fallDistance = 0.0F` (1460–1464), before the water/lava/fall-flying dispatch. The same gate and writer are in 1.13.1 and 1.13.2 (1480–1484). This supports the exact sampled 1.13.0 introduction/reset-only claim; it does not expand the discovery scope or establish any later partition.

## Partial report-accuracy note

**PARTIAL — provenance wording, not the movement finding.** The frozen report says the 1.9–1.11.1 slices lack top-level ready markers. Exact local release metadata shows ready markers and source-manifest entries for 1.9.4, 1.10.2, 1.11 and 1.11.1; these entries match the computed source hashes. Other older aliases use their nested provenance hashes. This correction does not change the accepted writer/no-writer verdicts.

## Boundaries

This audit is limited to the named `LivingEntity.moveRelative` effect branches, effect registration, and the stated locally controlled route. It is not a full tick-graph or pair-coverage audit and makes no claim about other fall-distance writers, intermediate unsampled patch releases, non-player behavior, effect production, damage, or runtime parity.