# F-WATER-SPRINT-TRAVEL — 1.13.2 sprinting changes water drag and skips gravity

- Status: provisional source candidate; artifact-integrity hold and dependency closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1555-1584; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. Water multiplier begins with base movement multiplier; with Depth Strider absent it resolves to 0.8F. Gravity applies `velocityY -= 0.02` when enabled.
- B evidence: corresponding member, lines 1617-1655; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. Water multiplier begins at 0.9F when sprinting (before Depth Strider/Dolphin's Grace adjustments), and the gravity update is guarded by `!isSprinting()`.
- Preconditions/reachability: player movement in water with sprint already active; no Depth Strider or Dolphin's Grace for the quoted horizontal multiplier comparison; gravity enabled. Sprint eligibility differs separately in F-WATER-SPRINT.
- Difference: with the stated inputs, A multiplies X/Z velocity by 0.8F and applies water gravity; B multiplies X/Z by 0.9F and skips the water gravity update.
- Limits: source proves the update branch and coefficients under the stated modifier exclusions; it predicts momentum retention but no trajectory was measured. Effect/enchantment registration and input-state closure remain open.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
