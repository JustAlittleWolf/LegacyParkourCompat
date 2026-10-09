# Independent review: Y=256 stale water-depth producer (2026-10-09)

## Verdict

**ACCEPT — bounded conditional source mechanism only.** The exact endpoint sources support a path where 1.13.2 retains a positive stored water depth after the water predicate clears at the Y=256 loaded-area gate, and its player jump branch can still consume that value. The 1.16 source resets stored fluid heights before the same vertical preflight and gates liquid-jump selection on current water state and positive depth. This does not establish MCPK's reported trajectory, a universal jump-height result, the cause of MC-135831, or the first fixed release.

## Immutable input identities

- Reviewed source report: `workflows/wiki-audit-2026-10-07/mcpk-y256-producer-2026-10-09.md`, merge commit `681d1d54d69ec693f02e07e5a6b1841c401c4bb6`, Git blob `fddcf603a83611d9d10a0f5f684e270433fcfd30`. The same blob is present at source commits `ebdd65a9d997ac9290470dc8038f5df99b13ae38` and `056358705292df325f768525bdeeb9ed877b6fdf`.
- MCPK context read narrowly from the Y=256 water/jump passage in `workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r5.md`, blob `b5d6bd29e4a2e919c0c67f888728d862dfd8bcd5`. That record treats the page symptom and first patch boundary as unresolved; this review uses no external Wiki material.
- Reviewer branch: `fix/review-mcpk-y256-2026-10-09`, created from `main` at `45179643d0f1a40edf37107e624dbd5f2cc97870` in `D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-mcpk-y256-2026-10-09`.

## Ready-publication identity checks

Consumed the existing ready sources read-only. The marker fields and raw source/artifact manifest hashes match on disk.

| Version and mapping | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---|
| 1.13.2 Ornithe Feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1` |
| 1.16 Mojmap | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` |
| 1.16.1 Mojmap cross-check | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |

Each cited source hash below matched the direct file hash and its exact source-manifest row. The 1.16.1 `Entity.java` and `LivingEntity.java` files also match the 1.16 files byte-for-byte. The 1.13.2 source is Feather-derived; its equivalence to the unavailable original derived artifact/client remains unproven and limits confidence in the A-side source attribution.

| Version | Verified source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/WorldAccess.java` | `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.16 | `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| 1.16 | `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16 | `net/minecraft/world/level/LevelAccessor.java` | `fe69f6cbf7c02dcecf7ff9b23afdf8de003e47530384256049b11bc18e622aa1` |
| 1.16 | `net/minecraft/world/level/Level.java` | `5f6be38c6b3e11e1ca00a9067f676151d1288f19d8cd817a3bbd1cc91b8eaa19` |
| 1.16 | `net/minecraft/world/level/chunk/LevelChunk.java` | `0bfdae3558d681d8c8074ae7476c3a394239dbcca0fdf7bad5b4386eafb65f12` |
| 1.16 | `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16 | `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| 1.16 | `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |

## Dependency route and finding-specific evidence

1. **Vertical preflight.** In 1.13.2, `World` implements `WorldAccess`, which extends `WorldView`; `WorldView.isAreaLoaded(minX,minY,minZ,maxX,maxY,maxZ,allowEmpty)` returns false unless `maxY >= 0 && minY < 256` (lines 308–325). The entity scan contracts the player box by `0.001`, passes `floor(box.minY)` and `ceil(box.maxY)` to this inherited gate, and returns false before scanning when the gate fails (`Entity.java` lines 2509–2519). Thus the exact condition is `floor(contractedPlayerBox.minY) >= 256`, with the bounds finite and the same player world path invoking the query.
2. **1.13.2 stored-state producer.** `Entity.tick()` calls `baseTick()`; `baseTick()` refreshes water state through the water helper (lines 332–338, 394). The helper calls `checkWaterState()`. On a false scan, that method writes `inWater = false` and does not write the stored depth (lines 961–974). The scan initializes a local depth, but its sole `f_85121000` assignment is after the scan at line 2569; targeted search found no other assignment in the supplied source tree. Therefore, after a previous completed water scan of a water-overlapping box stored depth `> 0.4`, a later out-of-range scan on foot can leave that value stale while clearing `inWater`.
3. **Reachable 1.13.2 Player consumer.** `LivingEntity.tick()` dispatches to `mobTick()` (line 1752). `PlayerEntity.mobTick()` calls `super.mobTick()` (line 481), reaching the inherited jump branch. The local player overrides `isLocallyControlled()` to return true (lines 489–491), so that branch calls virtual `serverTickAi()` before checking the jump flag; `LocalClientPlayerEntity.serverTickAi()` copies `input.jumping` into `jumping` (lines 635–640). With `jumping == true` and stale stored depth `> 0.4`, `LivingEntity.mobTick()` selects `jumpInLiquid(WATER)` at lines 1903–1914 without checking `inWater`. To compare that branch with B's ordinary ground-jump branch, require `onGround == true` on both sides, B's `noJumpDelay == 0`, and that the Player is not in lava. The A-side water-jump branch has no jump-cooldown gate.
4. **1.16 reset and consumer gate.** `Level` implements `LevelAccessor`, which extends `LevelReader`; the inherited `hasChunksAt` vertical preflight uses `maxY >= 0 && minY < 256` (lines 168–186). `Entity.baseTick()` refreshes water before living AI (lines 346–354, 372–374). `updateInWaterStateAndDoFluidPushing()` clears `fluidHeight` before invoking the water scan (lines 947–955). If the same contracted-box range gate fails, the scan returns before its final `fluidHeight.put` (lines 2633–2643, 2696–2697); the water-state updater also sets `wasTouchingWater = false` (lines 958–972). `Player.tick()` calls `super.tick()` and `Player.aiStep()` calls `super.aiStep()`; `LocalPlayer.isEffectiveAi()` returns true, so `LivingEntity.aiStep()` invokes the local player's `serverAiStep()`. That method copies `input.jumping` into `jumping` before the jump branch (LocalPlayer lines 519–521, 634–640). The inherited jump branch chooses a liquid jump only when `isInWater() && k > 0.0` (LivingEntity lines 2425–2446). For the explicit grounded comparison, require `onGround == true`, `isAffectedByFluids() == true`, `isInLava() == false`, and `noJumpDelay == 0`; with the cleared map and false water state, the branch can select `jumpFromGround()` instead.
5. **Lookup fallback.** The A-side `World.getFluidState` and `WorldChunk.getFluidState` return empty outside valid Y/section bounds (lines 529–536 and 384–399); B has corresponding `Level.getFluidState` and `LevelChunk.getFluidState` bounds (lines 418–425 and 230–245). These checks are consistent with the range boundary, though the area preflight returns before the entity overlap scan reaches them in this conditional path.

## Bounded disposition and open limits

The mechanism is source-supported only under the explicit prior-state and current-state conditions above: a completed prior scan of a water-overlapping Player box stored depth above `0.4`; the subsequent contracted Player box has `floor(minY) >= 256`; the Player is not riding a boat or in lava; and the Player jump flag is true. To demonstrate a branch choice of water jump in A versus ordinary ground jump in B, also require ground contact and B's zero jump delay. This is not a claim that every water exit, every player box at Y=256, or every jump follows that route.

The source proves the producer/consumer state path, not MCPK's reported jump height or a specific world trajectory that satisfies every condition. Runtime validation was not performed. The first release that clears the stale state or changes the consumer remains unknown; the 1.13.2 and 1.16 endpoints do not establish a release cutover. This review did not validate the 1.16.2 producer, expand the whole fluid lookup or shape inventory, or make a blind source-pair coverage claim. Feather-derived A-side source provenance and unavailable-original equivalence remain open. No implementation conclusion is offered.

## Review checks

- The immutable report blob, ready markers, source/artifact manifest hashes, cited source files and the targeted producer-field write search were checked.
- No Minecraft Wiki content, blind source-lane report, mod implementation, or author branch files were read or changed.
- No decompilation, build, tests, clients, TAS, Gym, server, Docker, network fetch, or push was used.
- Dependency route for the bounded result: player tick and jump input → entity water refresh → vertical loaded-area gate → depth write skipped/state behavior → Player-reachable jump consumer. Remaining dependencies for any trajectory or first-fix claim: exact prior wet box/depth and world transition, runtime comparison, and intermediate release sources.
## Integration

- Reviewer worktree started at main `45179643d0f1a40edf37107e624dbd5f2cc97870`.
- Before handoff, the reviewer branch fast-forwarded to current main `ee38e8b1ed658e4b7ea2d128547c7571e3a5f7a2`. Incoming commits were documentation integrations in other paths; no incoming change touched this review artifact or the immutable Y=256 source report. No overlapping semantic change was found.
- `main` is an ancestor of the final reviewer commit. The author report and ready source trees remained read-only.
