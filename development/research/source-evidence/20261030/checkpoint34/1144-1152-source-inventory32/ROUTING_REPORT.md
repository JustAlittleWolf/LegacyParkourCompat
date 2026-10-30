# Blind source inventory: Mojmap 1.14.4 → 1.15.2

## Scope and evidence identity

This is a source-only Java path/hash inventory and bounded routing report for ordinary local-player movement edges. It does not claim full tick/dependency coverage or pair freeze. I read the shared and source discovery workflows, exact Mojmap sources and manifests, and the permitted source-only 1.14.4→1.15 inventory/arithmetic audit. I did not read product code or history, wiki material, or the backlog. No source was generated or modified.

Canonical read-only roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap`

| Release | Exact mapping/source identity | Source manifest | Provenance/readiness |
|---|---|---|---|
| 1.14.4 | Provenance namespace `mojmap`, exact requested release `1.14.4`; pinned client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. | 3,250 Java files / 17,080,207 bytes; manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. | Provenance file SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; ready marker SHA-256 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`. The verification marker reports 3,250 verified files, zero differences/missing files, and no raw input artifact differences. |
| 1.15.2 | Provenance namespace `mojmap`, exact version ID `1.15.2`, exact batch includes `1.15.2`; pinned client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`. | 3,332 Java files / 17,543,871 bytes; manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. | Provenance file SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; ready marker SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`. No separate verification JSON is present. |

The ready markers agree with their source-manifest digests. I recomputed every Java file hash on both sides and matched it to its manifest: 3,250/3,250 and 3,332/3,332; zero missing, unlisted, or mismatched files on either side. The complete union matrix is [`JAVA_PATH_HASH_MATRIX.tsv`](JAVA_PATH_HASH_MATRIX.tsv), SHA-256 `412c57a122296389324ccfd30d7443313d1d2088c1297aa09fd2cc0ccb87811d`. It has 3,466 rows and includes both hashes, byte lengths, per-side manifest-match flags, and the path state. [`HASH_VALIDATION.json`](HASH_VALIDATION.json), SHA-256 `3b7713439b5526920f4e9e44b6d7ce50618a8805bbef13277a450fc4f8dc995b`, records the recomputed counts and manifest validation.

| Java path state | Count |
|---|---:|
| Same relative path and byte hash | 1,634 |
| Changed at same relative path | 1,482 |
| Added in 1.15.2 | 216 |
| Removed after 1.14.4 | 134 |

The matrix covers Java files only. It is not a semantic audit of each changed class and does not include resources/tags.

## Ordinary movement edge routing

### Input, crouch pose and edge safety — movement-relevant source changes; reachability gates remain open

- 1.14.4 `KeyboardInput.tick(boolean, boolean)` reads `keySneak` and scales both impulses by `0.3` when `!spectator && (sneakKeyDown || callerSlowState)`. `LocalPlayer.aiStep()` supplies `isVisuallySneaking() || isVisuallyCrawling()` and `isSpectator()` (`KeyboardInput.java:14-26`, `LocalPlayer.java:625-630`).
- 1.15.2 `KeyboardInput.tick(boolean)` reads `keyShift` and scales impulses by `0.3` when its single argument is true. `LocalPlayer.aiStep()` supplies `isMovingSlowly()`, defined as `isCrouching() || isVisuallyCrawling()`; `isCrouching()` checks flying/swimming, pose space, shift state and sleeping (`KeyboardInput.java:14-25`, `LocalPlayer.java:590-604,632-636`). This changes the caller predicate and removes the explicit spectator input to the keyboard scaler. Trace whether the relevant spectator/crawl/crouch states can reach differing impulses before accepting a trajectory effect; the key source also changes from `keySneak` to `keyShift`.
- `Player.updatePlayerPose()` selects `Pose.SNEAKING` from `isSneaking()` in 1.14.4 and `Pose.CROUCHING` from `isShiftKeyDown()` in 1.15.2 (`Player.java:344-372` / `357-385`). The local player overrides those queries from its input object on each side. The crouch/sneak dimensions remain 0.6F wide by 1.5F high in the compared player pose tables; key-binding and `canEnterPose` reachability are still dependencies.
- Edge backoff retains its 0.05 decrement loop, but moves from `Entity.applySneaking()` gated by player + SELF/PLAYER + on-ground + `isSneaking()` in 1.14.4 (`Entity.java:575-611`) to `Player.maybeBackOffFromEdge()` gated by SELF/PLAYER + on-ground + `isStayingOnGroundSurface()` in 1.15.2; the player predicate returns `isShiftKeyDown()` (`Player.java:294-296,1002-1050`). Route through the input/predicate change rather than treating the moved arithmetic as a new numeric difference.

### Auto-jump — preserve the prior arithmetic finding; endpoint confirmed

The earlier source-only 1.14.4→1.15 arithmetic audit established the normalization overload change. I checked the 1.15.2 endpoint directly: `LocalPlayer.updateAutoJump()` still casts `lengthSqr()` to `float`, calls `Mth.fastInvSqrt(i)` at line 882, and scales using the returned `float`; 1.15.2 `Mth.fastInvSqrt(float)` uses the 32-bit `Float.floatToIntBits`/`int` seed and float arithmetic (`LocalPlayer.java:868-883`, `Mth.java:394-408`). In 1.14.4 the same call site explicitly narrows the result of the only `double` overload (`LocalPlayer.java:865-879`, `Mth.java:395-401`). This preserves the prior source finding for the 1.14.4→1.15.2 endpoints; it does not establish an error bound or a trajectory threshold crossing.

The auto-jump gate and vertical references also changed: 1.14.4 checks enabled/timer/on-ground/`!isSneaking()`/not passenger/nonzero input, with scan heights relative to bounding-box `minY`; 1.15.2 `canAutoJump()` checks additionally `!isStayingOnGroundSurface()`, movement, and `getBlockJumpFactor() >= 1.0`, while scan comparisons use `getY()` (`LocalPlayer.java:856-958` / `862-975`). Keep these as separate gates from normalization arithmetic; the auto-jump scan and collision-context dependencies are not closed here.

### Travel and block modifiers — direct ordinary movement candidate and dependency gate

- The paired `LivingEntity.travel(Vec3)` bodies retain the same branch order and dry-ground, water/lava and jump-effect formulas in the compared method. The source diff in this method is coordinate getter substitution plus the grounded friction lookup: 1.14.4 uses `new BlockPos(x, boundingBox.minY - 1.0, z)`; 1.15.2 calls `getBlockPosBelowThatAffectsMyMovement()`, which builds from `minY - 0.5000001` (`LivingEntity.java:1782-1938` / `1831-1987`; `Entity.java:600-602`). At fractional support heights these expressions can select different block Y cells. This changes the grounded friction input before `getFrictionInfluencedSpeed` and the `w` travel multiplier; close partial-height support and collision-context dependencies.
- 1.15.2 adds a post-move X/Z delta-velocity multiplier in `Entity.move()` (`Entity.java:543`), backed by `getBlockSpeedFactor()` (`590-598`). `Player.getBlockSpeedFactor()` bypasses it while abilities-flying or fall-flying (`Player.java:2028-2030`). The `SOUL_SAND` registration adds `.speedFactor(0.4F)` in 1.15.2 (`Blocks.java:601-603`), while the 1.14.4 registration has no factor (`Blocks.java:577-579`). This is an existing-block player-velocity candidate: the factor is selected after movement/contact processing and changes the horizontal velocity retained by the movement path. Exact support-cell and ordinary tick consequences remain to be closed.
- 1.15.2 also introduces `jumpFactor` support and multiplies `LivingEntity.getJumpPower()`'s base `0.42F` by `getBlockJumpFactor()` (`LivingEntity.java:1799-1801`; `Entity.java:584-588`). The only non-default `.jumpFactor` registration found in the checked block registrations is new Honey Block (`Blocks.java:2118-2122`), absent in 1.14.4. Therefore this check does not establish a changed jump height on a pre-existing block; keep Honey as a new-feature boundary and confirm the full factor producer/registration inventory before treating it as an older-profile delta. The new auto-jump `>= 1.0` factor gate belongs to the same dependency route.

### Collision/contact — core axis resolution checked; callback edge is movement-relevant

The paired `Entity.collide()` and `collideBoundingBoxLegacy` / `collideBoundingBox` slices retain the same axis order, step-up comparison, bounding-box moves and `Shapes.collide` calls. The broad file hashes differ, but the inspected collision resolver procedure does not. `Entity.move()` changes the edge-backoff dispatch, extracts the old inline floor/fence/gate lookup to `getOnPos()` (same floor calculation and fallback), and changes the `stepOn` callback gate: 1.14.4 places it inside `makeStepSound()` with a player sneak exclusion and passenger exclusion (`Entity.java:508-519`); 1.15.2 calls it under `onGround && !isSteppingCarefully()` independently of movement-noise/sound gating (`Entity.java:499-504`).

That gate has a concrete player movement route. In 1.14.4 `Player.makeStepSound()` is false while abilities-flying (`Player.java:1644-1647`), so a flying player does not enter the old `stepOn` block. In 1.15.2, a grounded player that is not stepping carefully enters the new callback even while flying. `SlimeBlock.stepOn()` scales X/Z delta movement by `0.4 + 0.2 * abs(deltaY)` when `abs(deltaY) < 0.1` and the player is not sneaking/stepping carefully (1.14.4 `SlimeBlock.java:44-51`; 1.15.2 `:42-49`). Route this as a candidate direct movement response for a flying local player with a grounded low-vertical-speed slime contact; verify that concrete state is reachable and that the callback's post-collision velocity is consumed by local-player travel. This is a source-backed conditional route, not a runtime parity claim.

Other changed block collision providers, block callbacks, collision contexts and resource/tag inputs remain open. The Java hash matrix can locate changed paths but cannot prove those dependencies closed.

### Fluids — checked predicate rewrite with equivalent source arithmetic

`Entity.isUnderLiquid(tag, checkChunk)` retains the same boat/chunk/tag/fluid-height gates and `+ 0.11111111F` threshold. The call changed from `y + getEyeHeight()` to `getEyeY()` (`Entity.java:1057-1070` / `1040-1053`); the inspected accessors return the same `eyeHeight` field and `getEyeY()` returns `y + eyeHeight` (`Entity.java:2345-2347, 2712-2714` / `2340-2342, 2712-2714`). No numeric fluid predicate difference was found in this slice. The `LivingEntity.travel()` fluid branches retain their source expressions apart from direct-field-to-getter substitutions. Changed fluid/block Java paths and fluid tags/resources were not exhaustively traversed, so keep that inventory as a dependency gate rather than a finding.

## Evidence pins for movement-facing classes

Hashes are SHA-256 of the exact relative Java file under the canonical roots; every listed file also matched its side's source manifest.

| Relative source path | 1.14.4 | 1.15.2 |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `8adaf34fa0bcef3198d3b3dd678f53ddf68b0293a17e2c1620a6528fa0d9ad1ef` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` |
| `net/minecraft/client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/util/Mth.java` | `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| `net/minecraft/world/level/block/Block.java` | `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f` | `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d` |
| `net/minecraft/world/level/block/Blocks.java` | `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9` | `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` |
| `net/minecraft/world/level/block/SoulsandBlock.java` | `3f2ce73aae23fb7e13cf001123343c6c750f00684b5331a5597ae33b011ef4e4` | `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf` |
| `net/minecraft/world/level/material/FluidState.java` | `26070b6d83813243a7a7c34cf9af9e3e4217681a8e7a97285c6dbb7d473f480d` | `f88bff7d54ea0220ee67e0fe36a78df67871e52ae636e584ea26dd107f652c15` |
| `net/minecraft/world/phys/shapes/Shapes.java` | `f1a2e60ae358236de7379e66543f99d474e3d431e42c5afbda688cb99b70985d` | `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `da5b700fdc06b7ef3a58ba14753f73ca9a5ba40120736be79b5e795a925f0a82` | `25b72516e924f382fbe581457d1621cc0bec78810e4ba44d3d6e525a0c2d3c70` |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | `6a49550faa944196e16d55a8d28ca8b6cd49f1b75bb491ddc345b935ea0651b4` | `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea` |

## Coverage boundary and next gates

This inventory hashes the complete paired Java path union. Semantic work in this report is limited to local input and pose predicates, auto-jump normalization/gates, `LivingEntity.travel`, `Entity.move`/collision resolver, speed/jump factor dispatch, the under-liquid predicate, and the specific Soul Sand/Slime callback routes. It does not close the complete ordered inherited tick, every changed block shape/callback, all fluids/tags/resources, equipment/effect/attribute producers, local-player response ingress, or all changed Java classes. Next bounded source work should close those dependencies and separately audit these claims before anyone treats the pair as frozen. Source comparison gives no exact intermediate release cutover.
