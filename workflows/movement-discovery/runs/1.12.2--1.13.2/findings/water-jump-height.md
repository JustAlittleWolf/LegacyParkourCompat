# 1.13.2 adds normal jumping in shallow water

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.1 (living jump dispatch); dependency from 1.2 (local jump input)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A entry: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity.mobTick()V`, lines 1827-1839; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. When `jumping` and `isInWater()` are true, the branch calls `jumpInWater()`, whose body at lines 1413-1415 adds `0.04F` to `velocityY`. The ordinary jump branch is skipped.
- B entry: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity.mobTick()V`, lines 1903-1917; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. The branch checks water-fluid height `f_85121000`; for positive height at most `0.4`, with `jumping` and `jumpingCooldown == 0`, it calls `jump()` rather than the fluid impulse. B base `LivingEntity.jump()V` at lines 1451-1464 sets `velocityY = getJumpStrength()`, adds jump-boost and sprint-jump effects when applicable, and marks velocity dirty. The local-player call dynamically reaches `PlayerEntity.jump()V` at `PlayerEntity.java:1431-1438`, which delegates to that base body and adds jump statistics/fatigue. A’s PlayerEntity wrapper at `PlayerEntity.java:1375-1382` is text-identical. The B base fluid jump handler at lines 1470-1472 adds `0.04F`.
- B height provenance: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `m_69693160(Tag<Fluid>)`, lines 2521-2574; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. For a tagged fluid it computes `d = Math.max(e - box.minY, d)` where `e = q + fluidState.getHeight()`, stores `d` in `f_85121000`, and the getter returns that field. Water-state refresh invokes the water-tag check from `Entity.checkWaterState()` (lines 961-981).
- Reachability: A/B `LocalClientPlayerEntity.serverTickAi()` copies local input jump state to the living entity's `jumping` field (A lines 630-642, B lines 633-642; source hashes in run manifest). `LivingEntity.tick()` calls `mobTick()` after `super.tick()` updates entity water state; the dynamic local-player path passes through `PlayerEntity.mobTick()` to `LivingEntity.mobTick()`, where the branch is evaluated. The local player dispatches through PlayerEntity.jump(), whose wrapper delegates to the base jump body; both endpoint wrappers were inspected and match.

## Source-level difference

For water contact with measured overlap height greater than zero and no more than `0.4`, a jump input reaches the normal jump in B when the jump cooldown is zero. A instead applies its water jump's `+0.04F` vertical increment regardless of ground state or jump cooldown. The newer normal jump assigns its configured jump strength and may also apply jump-boost and sprint-jump effects. In deeper water B retains the `+0.04F` fluid impulse branch. Both endpoint classes and members were inspected directly; this is an endpoint difference only.

## Reachability and dependencies

The client input is sampled in `LocalClientPlayerEntity.mobTick()`, copied into `jumping` by the local `serverTickAi()` override, then read by `LivingEntity.mobTick()`. B's water-height field is refreshed in the entity water-state path before living movement processing. The fluid height is computed from version-specific fluid states and the contracted entity box. Jump strength, jump boost, and sprint state affect B's normal jump magnitude; these dependencies remain open in stages 2 and 6. The finding describes the client-side movement code and does not establish server handling of synchronized motion.

## Consequence and uncertainty

Source proves the branch selection and that B enters the normal-jump body under the stated precondition, while A adds `0.04F`. It predicts different vertical velocity and, when sprinting, potentially different horizontal velocity. No trajectory was measured. B's Feather output leaves the field/helper names `f_85121000`, `m_69693160`, and `m_74407200` unmapped; their relevant semantics are established from their declarations, call sites, bodies and field writes in the same output. Exact source hashes are recorded above.

## Handoff

Independent water-jump dispatch change. Related to stage 1 jump-input ordering, but distinct from swimming sprint eligibility and sneaking-water descent. Release of introduction is unknown within the endpoint interval.
