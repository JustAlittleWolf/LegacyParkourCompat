# Frozen blind source audit: 1.21.10–1.21.11 water movement slices

## Assignment boundary

This is an independent exact-source audit limited to an ordinary, non-riding local player on the water-only path. It freezes the four listed operation slices and their verdicts. It does not freeze full-pair movement coverage, establish an intermediate patch boundary, or claim runtime parity.

## Exact source identity

- Older: Minecraft `1.21.10`, Mojang mappings (`mojmap`), canonical source root `decompiled_minecraft/1.21.10/mojmap`.
  - Ready marker pins `versionId` and `versionMetadataId` `1.21.10`, client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`, 6,386 source files, and source-manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
  - Artifact manifest SHA-256: `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.
- Newer: Minecraft `1.21.11`, Mojang mappings (`mojmap`), canonical source root `decompiled_minecraft/1.21.11/mojmap`.
  - Ready marker pins `versionId` and `versionMetadataId` `1.21.11`, client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`, 6,622 source files, and source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
  - Artifact manifest SHA-256: `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.
- Both provenance manifests record the exact `1.21.8,1.21.10,1.21.11` Mojmap decompile run, Vineflower `1.12.0`, Java `25.0.3+9-LTS`, and the same pinned artifact-manifest identity. The ready markers' source-manifest and artifact-manifest hashes match the consumed manifests. I checked every source-manifest entry against its file: 6,386/6,386 and 6,622/6,622 hashes match.

## Frozen ordered roster and verdicts

### 1. Water body-height/contact prediction — no difference

- `Entity.updateInWaterStateAndDoWaterCurrentPushing`: 1.21.10 `Entity.java:1491–1504`; 1.21.11 `Entity.java:1509–1522`.
- `Entity.updateFluidHeightAndDoFluidPushing`: 1.21.10 `Entity.java:3485–3557`; 1.21.11 `Entity.java:3509–3581`.
- `Entity.isInWater`: 1.21.10 `Entity.java:1433–1435`; 1.21.11 `Entity.java:1452–1454`.
- For the ordinary player path, both versions sample the same deflated bounding box (`0.001`), floor/ceil the same bounds, scan the same x/y/z cells, test the requested fluid tag, accept a fluid surface at `surfaceY >= bbox.minY`, accumulate the maximum submersion height, store that value, and set water contact from the same boolean. The water caller passes `0.014` in both versions; `isInWater()` reads the same `wasTouchingWater` field. No water body-height or contact boundary changed.

### 2. Water eye prediction — no difference

- `Entity.updateFluidOnEyes`: 1.21.10 `Entity.java:1506–1518`; 1.21.11 `Entity.java:1524–1536`.
- `Entity.isEyeInFluid`: 1.21.10 `Entity.java:1582–1584`; 1.21.11 `Entity.java:1600–1602`.
- `Entity.getEyeY`, `getDimensions`, and `refreshDimensions`: 1.21.10 `Entity.java:3207–3221`, `3581–3583`, `3663–3665`; 1.21.11 `Entity.java:3226–3240`, `3605–3607`, `3691–3693`.
- Both versions cache the previous eye-water membership, calculate the eye cell from `(x, eyeY, z)`, read that cell's fluid height, and add the fluid tags only when `cellY + height > eyeY`; the water predicate reads the same cached tag set. Dimension refresh assigns `dimensions` and `eyeHeight` in the same order. `EntityType.PLAYER` retains `.sized(0.6F, 1.8F).eyeHeight(1.62F)` in both (`EntityType.java:1164–1170` and `1191–1197`, respectively). With identical pose and fluid inputs, eye prediction is equivalent.

### 3. Water flow sample and push — no difference

- `Entity.updateFluidHeightAndDoFluidPushing`: 1.21.10 `Entity.java:3485–3557`; 1.21.11 `Entity.java:3509–3581`.
- `FluidState.getHeight` and `getFlow`: both versions `FluidState.java:53–54` and `95–96`.
- `FlowingFluid.getFlow` and `getHeight`: both versions `FlowingFluid.java:55–94` and `439–441`.
- The box-cell iteration, fluid surface test, `getFlow` sampling, sub-`0.4` flow scaling, averaging, Player normalization exception, coefficient scaling, low-horizontal-speed minimum push, and velocity addition are textually unchanged in the corresponding `Entity` body. The delegated `FluidState` and `FlowingFluid` methods are also unchanged. No flow sample or water-current push delta is established.

### 4. First water travel move, drag, and vertical response — same-input equivalent after extraction

- `LivingEntity.travel`: 1.21.10 `LivingEntity.java:2275–2280`; 1.21.11 `LivingEntity.java:2309–2316`.
- Water branch of `LivingEntity.travelInFluid`: 1.21.10 `LivingEntity.java:2331–2363`; extracted to `travelInWater` at 1.21.11 `LivingEntity.java:2368–2406`, reached through `shouldTravelInFluid` at `2319–2321`.
- `LivingEntity.getFluidFallingAdjustedMovement`: 1.21.10 `LivingEntity.java:2488–2503`; 1.21.11 `LivingEntity.java:2546–2561`.
- For a fixed ordinary non-riding player's same water/contact, velocity, input, sprint, ground, effect, attribute, collision and gravity inputs, both versions select the fluid branch under the same predicate. The 1.21.11 helper retains the old water order: derive sprint/water drag and acceleration, account for efficiency and Dolphins Grace, `moveRelative`, move with current velocity, apply the climbable collision y value, multiply horizontal drag and `0.8F` vertical drag, then apply fluid-falling adjustment. The extracted `jumpOutOfFluid` is the old shared post-branch horizontal-collision/free-space check at the same point in the water path. The following `floatInWaterWhileRidden` has no effect for a non-riding player. The default water slowdown (`0.8F`), fluid-affect/stand predicates, water-movement-efficiency attribute definition, and fluid-falling adjustment are unchanged. The split is not a behavior difference for this bounded input.

## Coverage limits and exclusions

Examined paired slices: ordinary-player water body contact/height sampling, eye-water prediction and its player dimension input, water flow sampling/push, and the first water travel move/drag/vertical response including their listed direct helpers. The local-player class does not define a separate `travel` override; the audited route is inherited from `LivingEntity`.

Not examined as full coverage: the complete player tick graph, every caller or consumer of fluid state, all pose/attribute/effect transitions, collision-shape or block-fluid construction differences, other direct water responses, and any unlisted movement operation. Riding behavior, lava and dimension metadata/codecs, creative/fall-flying paths, geometry/damage, runtime parity, and the full 1.21.10–1.21.11 pair are outside this assignment. These verdicts close only the listed slices as no-difference/equivalence checks.
