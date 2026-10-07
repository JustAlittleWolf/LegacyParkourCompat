# F-FLUID-DRAG — 1.13.2 replaces material drag sampling with fluid-state flow sampling

- Status: provisional source candidate; artifact-integrity and fluid/world closure pending
- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, checkWaterState lines 944-946 and checkWaterCollisions lines 948-964; SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `World.java` SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- B evidence: `ready/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, checkWaterState lines 961-975 and m_69693160(Tag<Fluid>) lines 2509-2570; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- Difference: A delegates water contact/drag to the World material callback. B samples fluid states intersecting the contracted player box, accumulates fluid flow vectors and adds `0.014`-scaled flow components to velocity; it stores maximum fluid depth for other movement gates.
- Reachability: Entity fluid check is inherited by the player base tick and feeds water contact plus F-WATER-STATE.
- Limits: exact boundary scan, fluid level/falling rules, vector summation order, waterlogged blocks, tags and current cache integrity are pending. This is not a block-state historical emulation claim.
- Integrity hold: source owner/ops reported derived mapped-JAR cache replacement during reproducibility; source tree hashes remain unchanged. Do not accept/freeze until canonical repair and fresh verification.
