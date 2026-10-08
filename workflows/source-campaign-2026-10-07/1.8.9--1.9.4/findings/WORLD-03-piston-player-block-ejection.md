> Withdrawn candidate, retained for history. The piston-specific claim below is contradicted by B `PistonBaseBlock#isCube(BlockState)==false` at lines 216-218 (SHA-256 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`), matching A `PistonBaseBlock#isCube()==false` at lines 223-225 (SHA-256 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`). Original candidate SHA-256: `a3125e53e15bbe8f4a7692bd3494716f9845ff69c13db551b379654852c5d07d`. Do not treat it as a current finding or implementation handoff; see `WORLD-03-end-portal-frame-player-ejection.md` for the corrected candidate.

# WORLD-03: extended piston state changes local player block-ejection gate

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: local-player pre-travel block push-out; state-based solidity; `WORLD-03`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a local player body probe falls in or immediately above a piston-base block state while `noClip` is false
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity#mobTick()V`, four `pushAwayFrom(DDD)Z` calls at lines 546-549; `#pushAwayFrom(DDD)Z`, lines 292-341; `#canSurvive(BlockPos)Z`, lines 344-346; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`. The block test calls `Block#isSolid()Z`, lines 237-239, SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity#mobTick()V`, four `pushAwayFrom(DDD)Z` calls at lines 657-660; `#pushAwayFrom(DDD)Z`, lines 349-398; `#canSurvive(BlockPos)Z`, lines 401-403; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. The block test calls `BlockState#isSolid()Z`, delegated at `StateDefinition.java` lines 293-295, SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`; `Block#isSolid(BlockState)Z`, lines 214-216, and default `Block#isSignalSource()Z`, lines 532-534, SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`.
- Historical block: A registers piston bases in `Block.java` lines 882-886; B exposes registered `Blocks.PISTON` / `Blocks.STICKY_PISTON` as `PistonBaseBlock` at `Blocks.java` lines 273-278, SHA-256 `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7`. A `PistonBaseBlock#isCube()Z`, lines 223-225, returns false (file SHA-256 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`). B's `PistonBaseBlock` uses solid `Material.PISTON`, has an extended state with a partial `getShape`, and does not override `isCube(BlockState)`; the inherited B default is true (file SHA-256 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`). B `Material.java` lines 44 and 102-104 show the default blocking material solidity, SHA-256 `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`.

## Source-level difference

Before superclass movement, the local player samples four body offsets. `pushAwayFrom` returns without writes under `noClip`; otherwise it asks whether the current and above block positions can “survive” as non-solid. If not, it searches west/east/north/south neighbors using the same distance ordering and writes one horizontal velocity component to `-0.1F` or `+0.1F` for the selected escape direction.

A's `canSurvive` asks the block object whether it is solid. `PistonBaseBlock` overrides block-level `isCube()` to false, so A's `Block#isSolid()` reports false for a piston base. B asks the actual state; default `Block#isCube(BlockState)` is true, and `Block#isSolid(BlockState)` combines state material solidity, `state.isCube()`, and non-signal-source status. `PistonBaseBlock` uses `Material.PISTON`, whose default material blocks movement, and has no state `isCube` override. Thus B's piston-base state is solid for this gate, including the extended state with its partial collision shape. The same pre-travel probe can enter the escape-search and overwrite horizontal velocity in B where A accepts the piston-base position as survivable and skips that write.

## Reachability and dependencies

Pistons existed and are registered on both sides; this does not assign a B-only block mechanic. The route is through the local player's four pre-travel `pushAwayFrom` calls, before the velocity cutoff and travel. The escape write requires a non-`noClip` player sample at the piston block or its upper neighbor, plus at least one neighbor accepted by B's state-solid predicate; tie-breaking and write order are unchanged in the method body. This finding concerns direct player velocity/position response only, not piston or other non-player physics.

## Consequence and uncertainty

The source proves a changed branch gate and a possible direct horizontal velocity overwrite (`±0.1F`) under the stated state and neighbor conditions. It does not prove a resulting position or trajectory. Exact first changed release is unknown within the endpoint interval.

## Handoff

Independent delta: state-based solid classification changes the local player's pre-travel piston-block ejection path. Runtime validation is deferred.
