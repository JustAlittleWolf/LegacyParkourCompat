# F-003 — Lava source conversion gains a gamerule input

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: lava source conversion; S3-climb-swim, S5-fluid-data, S5-lava-source, S7-external-velocity
- Classification: changed behavior
- Confidence: source-confirmed source difference; player movement consequence is conditional on external fluid state
- Applicability: historical player behavior through externally supplied fluid states
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned `mojmap` source trees and official original client jars

## Paired evidence

- A `net/minecraft/world/level/material/FlowingFluid.java`, SHA-256 `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3`: `getNewLiquid(LevelReader, ...)` lines 154-178 tests `canConvertToSource()` at line 171. The abstract hook is parameterless at line 235.
- B same path, SHA-256 `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c`: `getNewLiquid(Level, ...)` lines 154-178 tests `canConvertToSource(level)` at line 171. The abstract hook receives `Level` at line 235.
- A `LavaFluid.java`, SHA-256 `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c`: `canConvertToSource()` lines 189-191 always returns `false`.
- B `LavaFluid.java`, SHA-256 `1d239a6e0c99aee386b5dfb0244a29f4a48689808f9be64dda89c9a63b9fc6da`: `canConvertToSource(Level)` lines 189-191 reads `GameRules.RULE_LAVA_SOURCE_CONVERSION`.
- B `GameRules.java`, SHA-256 `9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`: lines 165-167 register `lavaSourceConversion`, default `false`; absent from A `GameRules.java` (SHA-256 `d59d612944db56c897e407791e405bef71f131bfd4a5bff5b216bd75515b6fb6`).

## Source-level difference

Default behavior preserves A's false result. A non-default true rule permits source conversion after the existing horizontal-neighbor/support conditions.

## Reachability and dependencies

`FlowingFluid.getNewLiquid` -> lava-specific `canConvertToSource(level)` -> resulting world fluid state -> client player's `LivingEntity.travel` fluid-state/contact reads (A lines 2042-2256; B 2052-2263). The gamerule/world fluid state is a server/world input. The source does not establish where the client obtains a non-default rule or a resulting state; that producer boundary remains open under D3.

## Consequence and uncertainty

Source-proven: B can permit source conversion when the gamerule is true and the existing neighbor/support conditions pass; the default false value matches A. Predicted only: a resulting lava-state topology difference can change the fluid height/contact read by player travel. No specific trajectory or non-default server configuration is claimed.

## Handoff

Independent lava-source-conversion delta. Related finding F-002 covers water's separate rule. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-WORLD-MOVEMENT, INV-TICK, INV-EXTERNAL; S3-climb-swim, S5-fluid-data, S7-external-velocity. **Implementation disposition:** deferred until blind freeze.
