# F-WATER-SPRINT — 1.13.2 changes local water sprint and descent inputs

- Status: source-confirmed; impact closure pending
- Scope: LocalClientPlayerEntity local-player input path only.
- A source: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, mobTick lines 694-782, setSprinting lines 444-447; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`.
- B source: same class, mobTick lines 698-807, setSprinting lines 458-461; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- Difference: 1.12.2 requires onGround for double-tap sprint; 1.13.2 also accepts submerged water. Held sprint in 1.13.2 requires not-in-water or submerged. Sprint-stop logic branches on swimming and also cancels when in water but not submerged. The flight double-tap branch only toggles flight when not swimming. B invokes `knockDownwards()` for water+sneak; A has no such operation.
- Reachability/dependencies: input sampling is identical (KB-SAMPLE); baseTick water/swimming state is consumed before these gates (F-WATER-STATE). Verify exact tick override route and matching A/B source call order before freeze.
- Direct consequence: the same key sequence can set sprint/flying state differently in water, and sneaking in water writes downward velocity in B.
- Exclusions: food level is read solely as vanilla sprint eligibility here; hunger/food simulation remains excluded. B underwater visibility counter is not movement.
- Limits: source finding is exact for the bounded local player body. Implementation reconciliation and independent graph review have not run.
