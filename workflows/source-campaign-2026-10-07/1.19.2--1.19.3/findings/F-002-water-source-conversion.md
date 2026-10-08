# F-002 — Water source conversion gains a gamerule input

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: water source conversion; S3-climb-swim, S5-fluid-data, S5-water-source, S7-external-velocity
- Classification: changed behavior
- Confidence: source-confirmed source difference; player movement consequence is conditional on external fluid state
- Applicability: historical player behavior through externally supplied fluid states
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned `mojmap` source trees and official original client jars

## Paired evidence

- A `net/minecraft/world/level/material/FlowingFluid.java`, SHA-256 `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3`: `getNewLiquid(LevelReader, ...)` lines 154-178 tests `canConvertToSource()` at line 171. The abstract hook is parameterless at line 235.
- B same path, SHA-256 `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c`: `getNewLiquid(Level, ...)` lines 154-178 tests `canConvertToSource(level)` at line 171. The abstract hook receives `Level` at line 235.
- A `WaterFluid.java`, SHA-256 `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f`: `canConvertToSource()` lines 72-74 always returns `true`.
- B `WaterFluid.java`, SHA-256 `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f`: `canConvertToSource(Level)` lines 73-75 reads `GameRules.RULE_WATER_SOURCE_CONVERSION`.
- B `GameRules.java`, SHA-256 `9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`: lines 162-164 register `waterSourceConversion`, default `true`; absent from A `GameRules.java` (SHA-256 `d59d612944db56c897e407791e405bef71f131bfd4a5bff5b216bd75515b6fb6`).

## Source-level difference

Default behavior preserves A's true result. A non-default false rule prevents the source conversion branch after the existing horizontal-neighbor/support conditions.

## Reachability and dependencies

`FlowingFluid.getNewLiquid` -> water-specific `canConvertToSource(level)` -> resulting world fluid state -> client player's `LivingEntity.travel` fluid-state/contact reads (A lines 2042-2256; B 2052-2263). The gamerule/world fluid state is a server/world input. The source does not establish where the client obtains a non-default rule or a resulting state; that producer boundary remains open under D3.

## Consequence and uncertainty

Source-proven: B can prevent source conversion when the gamerule is false and the existing neighbor/support conditions pass; the default true value matches A. Predicted only: a resulting water-state topology difference can change the fluid height/contact read by player travel. No specific trajectory or non-default server configuration is claimed.

## Handoff

Independent water-source-conversion delta. Related finding F-003 covers lava's separate rule. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-WORLD-MOVEMENT, INV-TICK, INV-EXTERNAL; S3-climb-swim, S5-fluid-data, S7-external-velocity. **Implementation disposition:** deferred until blind freeze.
