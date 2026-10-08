# WORLD-03: End Portal Frame solidity changes local player block-ejection gate

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: local-player pre-travel block push-out; state-based solidity; `WORLD-03`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a non-`noClip` local-player body probe maps to an End Portal Frame block position, its above position is non-solid, and at least one horizontal escape neighbor is survivable
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed
- Snapshot status: new corrected candidate; independent blind review pending

## Paired evidence

- A: source manifest A; `LocalClientPlayerEntity.java`, `mobTick()V` four `pushAwayFrom(DDD)Z` calls at lines 546-549, helper lines 292-341 and `canSurvive(BlockPos)Z` lines 344-346; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`. `Block#isSolid()Z` lines 237-239, default `Block#isCube()Z` lines 245-247 and default `Block#isSignalSource()Z` lines 643-645; `Block.java` SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`. A `EndPortalFrameBlock` constructor sets `Material.STONE` at lines 23-25 and it does not override `isCube()` or `isSignalSource()`; `EndPortalFrameBlock.java` SHA-256 `c2c876aefe34d001ff0e3eebddd0df01ee85b0bf499e205eb1eb95ec44857b9e`. Its `isAnalogSignalSource()` override is lines 61-63 and is distinct from `isSignalSource()`. A `Block.java` registers `end_portal_frame` as `new EndPortalFrameBlock()` at lines 1077-1087.
- B: `LocalClientPlayerEntity.java`, four call sites at lines 657-660, helper lines 349-398 and `canSurvive(BlockPos)Z` lines 401-403; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. `BlockState#isSolid()Z` delegates through `StateDefinition.java` lines 293-300 (SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`) to `Block#isSolid(BlockState)Z` lines 214-216, whose cube and signal defaults are lines 223-225 and 532-534; `Block.java` SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`. B `EndPortalFrameBlock` uses `Material.STONE` at lines 30-32 and explicitly returns false from `isCube(BlockState)` at lines 109-111; its file SHA-256 `1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb`. It overrides analog signal source only at lines 68-70, while state `isSignalSource()` remains false. B registry also contains `end_portal_frame` at `Block.java` lines 988-998.
- `Material.STONE.isSolidBlocking()` is true by the same default material implementation on both sides; A `Material.java` SHA-256 `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`; B `Material.java` SHA-256 `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`.

## Source-level difference

A's End Portal Frame inherits `Block#isCube()==true`. It has `Material.STONE`, whose `isSolidBlocking()` is true, and its analog output does not change the separate `isSignalSource()` predicate; A's `Block#isSolid()` therefore returns true. In B, `EndPortalFrameBlock#isCube(BlockState)` returns false for its states, so `Block#isSolid(BlockState)` returns false for the same block despite unchanged material and signal-source inputs.

The local-player helper differs only in which solidity API it reads: A checks the block instance; B checks the sampled states. The paired `pushAwayFrom` methods keep the same guards, coordinate conversion, west/east/north/south ordering, strict distance comparisons, and `±0.1F` horizontal velocity writes. If the sampled position is the frame and the above position is non-solid, A's `canSurvive` result is false and the escape search runs; B's result is true and skips that write. When an escape neighbor is survivable, this creates a reachable A-only velocity overwrite for a locally controlled player before superclass movement.

## Reachability and dependencies

Both registries contain the historical `end_portal_frame` block and both constructors use `Material.STONE`. The path is the local player's four pre-travel body probes in `LocalClientPlayerEntity#mobTick()V`, then `pushAwayFrom(DDD)Z` and `canSurvive(BlockPos)Z`. A horizontal neighbor must pass the same `canSurvive` predicate for a direction to be selected. The source proves a branch and possible velocity write, not that a particular world geometry produces a final trajectory.

The paired state-solid provider comparison found that piston base is non-solid on both endpoints (A and B `isCube` are explicitly false), Air is non-solid on both due to `Material.AIR`, and the other B-only `isCube` overrides are modern-only blocks without A counterparts. Paired signal-source overrides are semantically equivalent, including trapped chest A `type == 1` versus B `Type.TRAP`; B's only `getMaterial(BlockState)` override returns the block's unchanged material. The broad collision-provider and downstream movement inventories remain open.

## Consequence and uncertainty

The source establishes a difference in the pre-travel branch gate and a possible direct horizontal velocity overwrite of `±0.1F` in A where B skips it under the stated preconditions. It does not establish the resulting position or trajectory. The endpoint pair does not establish the first release where the behavior changed.

## Superseded piston candidate record

The previous version of this finding claimed a B-only player ejection around piston bases. Its exact prior file SHA-256 was `a3125e53e15bbe8f4a7692bd3494716f9845ff69c13db551b379654852c5d07d`. That claim is withdrawn: A `PistonBaseBlock#isCube()Z` returns false at lines 223-225 (SHA-256 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`) and B `PistonBaseBlock#isCube(BlockState)Z` also returns false at lines 216-218 (SHA-256 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`). Preserve this refuted candidate only as history; it is not an independent finding or an implementation handoff.

## Handoff

Corrected source candidate for independent blind review only. Pair discovery remains active/partial; implementation feedback is sealed until full-pair freeze. Runtime validation is not performed.
