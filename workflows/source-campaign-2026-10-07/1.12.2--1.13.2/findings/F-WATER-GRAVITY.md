# F-WATER-GRAVITY — ordinary 1.13.2 water travel uses a smaller downward adjustment

- Status: provisional source candidate; artifact-integrity hold and dependency closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1555-1584; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. After the water move and `velocityY *= 0.8F`, gravity subtracts `0.02`.
- B evidence: corresponding member, lines 1617-1655; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. After `velocityY *= 0.8F`, when gravity applies and sprinting is false, B uses `d / 16.0` (normally `0.08 / 16.0`); a narrow near-terminal-value condition sets velocityY to `-0.003` instead.
- Preconditions/reachability: local player reaches inherited moveRelative through LivingEntity.mobTick. For the directly comparable case, player is in water, non-sprinting, gravity-enabled, and Slow Falling absent (so `d` retains the default 0.08). Fluid sampling and sprint state are dependencies F-WATER-STATE and F-WATER-SPRINT.
- Difference: under those conditions A's adjustment is `-0.02`; B's ordinary adjustment is `-0.005`, subject to the explicit clamp branch. Source proves these arithmetic paths; no trajectory was measured.
- Limits: Depth Strider changes preceding horizontal/vertical input speed, but the stated velocity-Y subtraction branch is otherwise independent; effect application, fluid state producers, artifact repair and independent review remain open.
- Integrity hold: do not accept/freeze until source owner/ops repair and freshly verify derived mapped artifacts.
