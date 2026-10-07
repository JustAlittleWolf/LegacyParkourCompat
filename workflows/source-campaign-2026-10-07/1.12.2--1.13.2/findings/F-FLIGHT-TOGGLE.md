# F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming

- Status: provisional source candidate; artifact-integrity hold and state-producer closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, mobTick lines 747-761; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`.
- B evidence: corresponding member lines 760-775; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- Difference: when flight permission exists, the second jump edge toggles `abilities.flying` in A; B adds `else if (!isSwimming())` before toggling. The first press timer and spectator auto-flight route remain separate guards.
- Reachability: local-player input edge reaches the branch when not spectator, able to fly, and jumping transitions false-to-true without autojump. B swimming state producer is F-WATER-STATE.
- Limits: source candidate only; no trajectory measured. Water state call order, artifact repair, reviewer audit and implementation reconciliation remain pending.
- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze until canonical repair and fresh verification.
