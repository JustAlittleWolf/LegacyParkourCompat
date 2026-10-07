# F-SNEAK-WATER-DESCENT — 1.13.2 adds a local downward water input

- Status: provisional source candidate; artifact-integrity hold and callee closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, mobTick lines 771-782; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. No water+sneak call to `knockDownwards()` occurs in the inspected body.
- B evidence: corresponding member lines 784-807; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. Before flight vertical input, `isInWater() && input.sneaking` invokes `knockDownwards()`.
- Difference: sneaking in water has a new direct velocity-writing call in B. `LivingEntity.knockDownwards()` B line 1466 subtracts `0.04F` from velocityY; A has no paired helper and its local-player mobTick has no corresponding branch.
- Reachability: local keyboard sneaking and sampled water-contact state feed this condition; the method runs before superclass LivingEntity.mobTick travel.
- Limits: source candidate only; exact inherited tick timing, fluid contact, artifact repair and independent review remain pending. No trajectory was measured.
- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze until canonical repair and fresh verification.
