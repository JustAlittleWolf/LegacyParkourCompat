# F-WATER-SPRINT — 1.13.2 changes local sprint eligibility in water

- Status: provisional source candidate; artifact-integrity hold and state-producer closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, mobTick lines 694-745; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`.
- B evidence: corresponding member lines 698-758; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- Difference: A double-tap sprint start requires `onGround`. B permits `(onGround || isSubmergedInWater())`; its held-sprint gate additionally requires not-in-water or submerged. B has separate sprint-stop conditions when swimming versus ordinary sprinting, including cancellation in water when not submerged.
- Reachability: identical keyboard input feeds LocalClientPlayerEntity.mobTick; B's water/submerged/swimming states are produced by the Entity/PlayerEntity tick path documented in F-WATER-STATE. Food level is read only as sprint eligibility; food simulation is excluded.
- Direct consequence: matching forward/sprint inputs may set or clear the sprint flag differently in water, affecting the later water travel branch.
- Limits: source candidate only pending source artifact repair, exact call-order closure, fluid producer closure, independent review, and implementation reconciliation. No trajectory was measured.
- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze until canonical repair and fresh verification.
