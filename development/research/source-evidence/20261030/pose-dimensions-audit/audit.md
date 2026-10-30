# Independent source audit: 1.21.1 → 1.21.2 player pose and dimensions

## Verdict and boundary

The source evidence supports the draft's bounded conclusion: when the same player pose inputs, scale, position, and collision-query results are supplied, the audited pose selection and dimension values are equal between 1.21.1 and 1.21.2. The sole reviewed `Entity.refreshDimensions()` source difference is the threshold literal changing from `4.0` to `4.0F`; both dimensions are `float`, so the comparisons are equivalent. The position-fudge path remains gated by `!(this instanceof Player)`, so this change cannot invoke that repositioning path for a player.

I independently read the exact paired source in the canonical tree and retraced the relevant tick, dimension, pose, and direct player consumers. I also checked `Player.canFallAtLeast`, a direct current-box consumer used by the player's fall-distance logic; its paired body is unchanged. This is a narrow consumer closure, not a complete 1.21.1→1.21.2 movement inventory or pair freeze.

## Immutable source identity

Read-only canonical root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- **1.21.1 Mojmap:** parent ready record identifies version `1.21.1`, Mojmap, 5,363 source files, and client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; artifact manifest SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. Its mapping/artifact identities are recorded in the ready record and provenance sidecar.
- **1.21.2 Mojmap:** mapping-root `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. Its embedded `inputs` map explicitly binds the source tree to `1.21.2.json` SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`, client JAR SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, and `client.txt` SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. The embedded source map directly matches the six selected Java source hashes below. The release parent has no 1.21.2 ready/provenance sidecar, so identity is bound at the mapping root.

| Selected source | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` |
| `net/minecraft/world/entity/Pose.java` | `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca` | `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca` |

The reviewed author draft is `.native-source/20261028/frontier-c/1211-1212-pose-dimensions/closure.md`, SHA-256 `74a263e5e78f04dc8dba75566bd6fa311fc0b280450dbdf9b93fa3e4487694d7`.

## Paired source findings

`Player.updatePlayerPose()` (1.21.1 lines 401–428; 1.21.2 lines 431–458) has the same gate and order: first test the swimming box; then choose fall-flying, sleeping, swimming, spin attack, crouching if sneaking without flight, else standing; accept the target for spectator/passenger or if it fits; otherwise try crouching, then swimming. The paired `Player.canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` (1.21.1 lines 431–433; 1.21.2 lines 461–463) builds the proposed dimensions' box at current position, deflates by `1.0E-7`, and calls `Level.noCollision` in both versions.

`Player.tick()` calls its player pose update after `super.tick()` and `cooldowns.tick()` in both endpoints (1.21.1 line 319; 1.21.2 line 326). `LivingEntity.tick()` refreshes dimensions when `getScale()` differs from `appliedScale`, before the player-specific pose update in both versions; 1.21.2 appends an elytra animation tick after this scale/dimension phase. `Player.getDefaultDimensions(Pose)` and the player pose/dimension declaration block match: standing 0.6×1.8/eye 1.62; fall-flying, swimming, and spin attack 0.6×0.6/eye 0.4; crouching 0.6×1.5/eye 1.27; dying 0.2×0.2/eye 1.62; sleeping uses `LivingEntity.SLEEPING_DIMENSIONS` 0.2×0.2/eye 0.2. `LivingEntity.getDimensions(Pose)` returns that sleeping constant or the selected default dimensions scaled by `getScale()` in both.

`Entity.refreshDimensions()` has the textual threshold edit at 1.21.1 line 2769 / 1.21.2 line 2927: `width() <= 4.0 && height() <= 4.0` becomes `width() <= 4.0F && height() <= 4.0F`. `EntityDimensions.width()` and `height()` return floats. Widening each float for comparison with `4.0` is exact, and `4.0F` is exactly representable; both comparisons therefore have the same result, including for NaN or infinities. Dimension assignment, eye-height caching, and `reapplyPosition()` remain in the same order. The subsequent size-growth position adjustment is guarded by logical-server, non-first-tick, physics, size, and growth predicates and expressly excludes `Player`.

## Direct player consumer coverage

The ordinary player classes were scanned for pose/dimension reads and current-box reads. Relevant direct movement consumers in this slice remain the same:

- `LocalPlayer.suffocatesAt(BlockPos)` uses current bounding-box min/max Y to construct the suffocation query; paired source is identical (1.21.1 lines 432–436; 1.21.2 lines 426–430).
- The four `LocalPlayer.aiStep()` `moveTowardsClosestSpace` candidates use `getBbWidth() * 0.35` in the same order under `!noPhysics` (1.21.1 lines 680–683; 1.21.2 lines 674–677). `Entity.moveTowardsClosestSpace` is unchanged.
- `LocalPlayer.updateAutoJump(float,float)` uses the current bounding-box top and current player width/height to make its candidate box; paired method source is identical (starts at line 912 in both files). Its collision aggregation and blocks' shapes are not closed here.
- `Player.canFallAtLeast(double,double,float)` reads the current bounding box and builds the downward no-collision query used by `isAboveGround`; the method is identical (1.21.1 lines 1125–1128; 1.21.2 lines 1125–1128).
- `Entity.getEyeHeight(Pose)`, cached `getEyeHeight()`, `getEyeY()`, `isEyeInFluid(...)`, `makeBoundingBox()`, `reapplyPosition()`, `getBbWidth()`, and `getBbHeight()` preserve their respective source logic. The player eye/fluid methods continue to read the same cached eye height/query sequence. `Entity.setPose(Pose)` writes pose data only and does not directly refresh dimensions.

Other direct player-class box reads found in the scanned files feed entity-interaction expansion or view-position calculations; item/drop, combat, rendering/view, and bed-eligibility behavior are outside this bounded movement-consumer audit. Remote-player pose updates consume server entity state and are not a local-player tick consumer.

## Residual gates

The result is conditional on equal pose-driving flags, sneaking/flight/sleep/swim/spin state, scale, current position and bounding-box state, and equal collision-query results. The pose fit check's `Level.noCollision` dependencies, collision providers/shapes and neighboring blocks remain open. This audit does not compare upstream movement/input changes that produce those states, liquid-travel or entity-movement changes, callbacks/direct responses, packet paths, full-position/full-velocity consumer closure, or all source slices in the version pair. It provides no intermediate patch cutover and no runtime parity verdict.
