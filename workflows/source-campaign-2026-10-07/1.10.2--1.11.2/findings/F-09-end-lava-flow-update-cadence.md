# F-09: End lava flow updates less often in 1.11.2

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: fluid block updates and player liquid-current response; `S5-fluid-world-flow-current`, `S3-fluid-jump-impulses`, `INV-WORLD-MOVEMENT`, `INV-EXTERNAL`, `INV-STATE`
- Classification: changed fluid update cadence with a player movement consequence
- Confidence: source-confirmed, conditional on flowing lava in an End world
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed
- Independent source review: pending

## Artifact identity and limits

The paired exact-source readiness, original source/artifact manifests, mapping metadata, and reproducibility caveat are recorded in `../run.md`. A and B source manifests are unchanged at SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` and `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, respectively. The cited revision is `feather-r1-2026-10-07`: A snapshot SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision record `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`; B snapshot SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`, revision record `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`. The original mapped JARs are unavailable, and equivalence with the replacement snapshots is unverified.

## Paired source evidence

- A: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/block/LiquidBlock.java::getTickRate`, lines 180-187, SHA-256 `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`; `FlowingLiquidBlock.java::tick`, lines 24-91, SHA-256 `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79`; `TheEndDimension.java::initBiomeSource`, lines 19-22, SHA-256 `02574ce90d251175d9339c0f9f3d2dd54335c898af78c3c8f86a930985658761`; `Dimension.java::hasNoSky`, lines 29 and 170-172, SHA-256 `7e913986a3c688552faadfa948347e08afe94ee781e9e6fba34740bf98ddd159`.
- B: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/block/LiquidBlock.java::getTickRate`, lines 180-187, SHA-256 `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244`; `FlowingLiquidBlock.java::tick`, lines 24-91, SHA-256 `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2`; `TheEndDimension.java::initBiomeSource`, lines 19-22, SHA-256 `f7709ceba704ef01c4094a407fc05576c945824eff6f509abb79f17ba4cfc783`; `Dimension.java::hasCeiling`, lines 29 and 177-179, SHA-256 `98cddc12659b87d8dd3bdecd5d4a6f0c66747a3b9dbbad6126fad7d6a0f2e6fd`.
- Applicability evidence: `Block.java` registers flowing water, source water, flowing lava and source lava at IDs 8-11 in A, lines 748-751, SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`, and B, lines 753-756, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`. `NetherDimension.initBiomeSource` sets A `noSky=true`, lines 12-16, SHA-256 `a1b7be595078ba8778b1e74ea821d45ce3c20c1528e055e7ac5a9f2ad984b0bf`; B sets `hasCeiling=true`, lines 12-16, SHA-256 `a683db8c37fb836d11d358d8418f211cdf03f15e23cc2ae3863cfedbd4aa47f1`.
- Player movement consumer path: A/B `Entity.java::isInLava`, lines 956-958 / 1024-1026, with the `Entity.java` hashes above; paired `LivingEntity.mobTick` water/lava jump branch, lines 1728-1734 / 1784-1790, and fluid-specific `moveRelative`, lines 1332-1506 / 1388-1562, with the `LivingEntity.java` hashes in `../run.md`.

## Source-level difference

Both `LiquidBlock.getTickRate` bodies return 5 for water and 30 for lava in dimensions outside their special predicate. A uses `world.dimension.hasNoSky()` for lava's 10-tick rate; B uses `world.dimension.hasCeiling()`.

A's `TheEndDimension.initBiomeSource` sets `noSky=true`. B's End dimension does not set `hasCeiling`; the B base `Dimension.hasCeiling` returns the `hasCeiling` field, which remains its Java-default `false`. Thus flowing lava's cadence in the End changes from 10 ticks in A to 30 ticks in B. As a control, A's Nether sets `noSky=true` and B's Nether sets `hasCeiling=true`, retaining the 10-tick Nether cadence.

`FlowingLiquidBlock.tick` reads `getTickRate` into its reschedule delay and schedules the changed flowing state with that delay. Its level updates and spread can therefore run at a different cadence in an End containing flowing lava. This can change which lava blocks intersect the player's bounds, which changes `Entity.isInLava()`; the paired `LivingEntity.mobTick` checks that predicate to route jump input, and `moveRelative` uses it to select the fluid travel branch. Those predicates and consumer bodies have the source ranges recorded above and in `../run.md`. A separate direct current path exists in `World.applyLiquidDrag`, but `Entity.checkWaterCollisions` supplies `Material.WATER`, so it does not support an End-lava movement claim. The query/current methods and `LiquidBlock.getFlow/applyMaterialDrag` match after whitespace normalization.

## Reachability and consequence

For a map with flowing lava in the End, the scheduled lava level/spread state can differ over time between A and B. If the resulting lava footprint intersects a locally controlled player's bounds in one endpoint but not the other, the paired `isInLava` consumers can select different jump/travel branches. This establishes a player movement-relevant source path under the stated world and fluid preconditions. It does not establish a particular changed footprint, route, position, or trajectory.

The flow-tick body also changes B's `updateNeighbors` call to pass `false`, suppressing observer updates. Observer blocks were not available to A-era maps and are excluded from this finding. No water cadence change was found. Nether lava remains 10 ticks in both versions.

## Uncertainty and handoff

Runtime behavior was not validated. The original mapped-JAR equivalence limitation from `../run.md` applies. Keep this finding conditional on End-world flowing lava and separate from the water/lava jump helpers in S3-fluid-jump-impulses. Introduction is unknown within the endpoint pair; independent blind source review is pending.
