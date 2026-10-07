# F-WATER-STATE — 1.13.2 adds submerged-depth and swimming state to player movement

- Status: provisional source candidate; artifact-integrity and tick-order closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, checkWaterState lines 944-946, checkWaterCollisions lines 948-964; SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. Water contact is delegated to `World.applyLiquidDrag` with Material.WATER.
- B evidence: corresponding Entity.java, checkWaterState lines 961-975, helper m_03231680 lines 947-951, updateSwimming lines 953-959, plus raw sampler m_69693160(Tag<Fluid>) lines 2509-2570 and getter m_02485546 lines 2573-2575; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- Difference: B adds a sampled fluid depth and a swimming flag transition. Already-swimming transition uses sprint + contact with water + not riding; entry uses sprint + submerged-in-water + not riding. PlayerEntity suppresses swimming while flying. A has no submerged-depth/swimming state.
- Reachability: B base-tick water update runs before LivingEntity.mobTick input/jump/travel route. State is consumed by local sprint/flight gates, PlayerEntity pose/moveRelative, and LivingEntity water jump decisions.
- Limits: raw helper names need fresh descriptor/source pairing after artifact repair; full Entity/Player tick order, fluid grid boundaries and resource-data closure remain pending. Source difference does not establish exact gameplay trajectories.
- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze until canonical repair and fresh verification.
