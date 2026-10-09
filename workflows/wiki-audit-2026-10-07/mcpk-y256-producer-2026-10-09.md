# MCPK Y=256 water-exit producer follow-up — 2026-10-09

**State: PARTIAL.** This bounded source check identifies a conditional stale water-depth path at the Y=256 range boundary. It does not runtime-confirm the reported higher-jump trajectory or establish the first fixed release. It does not freeze a source pair or authorize implementation.

## Source publication and integrity

Read-only sources came from the exact published roots below. For each root, the ready marker reported `status: ready`, the expected version and namespace, and source/artifact manifest hashes matching the files on disk. The cited source paths and hashes also match their source-manifest entries. Only the listed movement classes were rehashed; no source tree was rehashed wholesale.

| Version | Root / mapping | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|
| 1.13.2 | `ready/1.13.2/ornithe-feather` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acaafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1` |
| 1.16 | `ready/1.16/mojmap` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` |
| 1.16.1 | `ready/1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| 1.16.2 | `ready/1.16.2/mojmap` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |

The Feather-to-original-JAR equivalence caveat recorded in r5 remains in force. In 1.16.0/1.16.1, `Entity.java` is byte-identical (SHA-256 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`) and `LivingEntity.java` is byte-identical (SHA-256 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`). The corresponding 1.16.2 files have SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` and `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; the bounded check below does not claim whole-file equivalence with 1.16.0.

## Bounded producer trace

The source-supported chain is `Entity.tick` → `baseTick` → water-state refresh and overlap scan → the player-reachable living jump decision. The already reviewed r5 consumer record identifies the 1.13.2 `LivingEntity.mobTick()` branch and the 1.16.0–1.16.2 fluid-jump branch; this follow-up checked only the upstream state producer.

### Source evidence

- **1.13.2 Feather `Entity.java`** (`1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`): `baseTick()` line 394 invokes `m_03231680()`. That refresh calls `checkWaterState()` and the water-depth scan `m_69693160(FluidTags.WATER)`. The scan contracts the current bounding box by `0.001` (line 2510), floors its minimum Y and ceils its maximum Y (2513–2514), iterates those integer cells (2528–2532), and computes each matching water surface as `q + fluidState.getHeight()` (2532–2537). When the surface is at or above the box minimum, it stores the greatest overlap depth; at the end it writes that value to `f_85121000` (2569) and returns whether any matching fluid overlapped (2570). There is no literal `256` check in this body.
- **1.16.0 Mojmap `Entity.java`** (hash above): `baseTick()` lines 372–374 refreshes water/current state, eye-fluid state and swimming state before living AI. The water routine at 947–972 clears/updates fluid heights, tests water overlap, resets fall distance when touching water, and writes `wasTouchingWater`. `updateFluidHeightAndDoFluidPushing()` lines 2633–2697 contracts the current bounding box by `0.001`, derives floor/ceil X/Y/Z cell bounds, queries `level.hasChunksAt` and each `level.getFluidState`, computes the surface as `q + fluidState.getHeight(...)`, accumulates the maximum `surface - box.minY`, stores it under the fluid tag, and returns whether fluid overlapped. There is no literal `256` check in this body either.
- **1.16.1:** the cited `Entity.java` and `LivingEntity.java` bytes match 1.16.0 exactly, so those producer and consumer bodies are identical at this endpoint.
- **1.16.2:** the ready source is present and its cited `Entity.java`/`LivingEntity.java` hashes differ from 1.16.0. The previously bounded consumer check records a structurally unchanged water-jump branch through 1.16.2. This pass did not expand into 1.16.2 world/chunk fluid lookup or assert producer equivalence there.

### Inference, scope, and open dependencies

The jump consumer can select the normal ground jump versus a liquid jump based on the stored fluid overlap depth, the in-water predicate, ground state and the threshold. A change to those inputs at the reported height is therefore a plausible route to the reported higher jump. The checked producer methods derive depth from the entity box and fluid cells and expose no explicit Y=256 transition. This is a negative result about these method bodies only; it does not establish equivalence of the entire fluid lookup path.

The initiating transition remained unresolved after the entity-method slice above. The exact world/chunk follow-up below found a conditional stale-depth mechanism; the higher-jump trajectory itself has not been runtime reproduced, and the exact release boundary is still open.

The direct player jump decision is within movement scope. Water/block state production, health/food production, attack/damage resolution, and non-player or vehicle motion are not inferred or claimed here. No implementation equivalence or cutover is claimed. No tests, builds, runtime clients, TAS, Gym, servers, Docker or network fetches were used.

## Follow-up 2 — world/chunk height gate and stale producer value

This slice compares three paired behavior paths: the loaded-area preflight, world-level fluid lookup with its direct chunk delegation, and chunk-section fluid lookup. The short `BlockPos` chunk overloads are included only as direct delegates to the coordinate overloads. The source marker/manifests for 1.13.2 Feather and 1.16.0 Mojmap remain the verified identities above. Hashes for every cited file below were checked against its exact ready source-manifest entry.

| Version | File | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.16.0 | `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.0 | `net/minecraft/world/level/Level.java` | `5f6be38c6b3e11e1ca00a9067f676151d1288f19d8cd817a3bbd1cc91b8eaa19` |
| 1.16.0 | `net/minecraft/world/level/chunk/LevelChunk.java` | `0bfdae3558d681d8c8074ae7476c3a394239dbcca0fdf7bad5b4386eafb65f12` |

### Source evidence

- **Loaded-area gate:** 1.13.2 `WorldView.isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean allowEmpty)` lines 308–327 returns false unless `maxY >= 0 && minY < 256`; for accepted bounds it checks only the X/Z chunk rectangle. 1.16.0 `LevelReader.hasChunksAt(int i, int j, int k, int l, int m, int n)` lines 168–187 applies the same vertical condition (`m >= 0 && j < 256`) and then checks the X/Z chunk rectangle. The entity overlap methods supply `minY = floor(contractedBox.minY)` and `maxY = ceil(contractedBox.maxY)`. Thus, when the contracted player box has `minY >= 256`, the preflight returns false in both endpoints before any fluid cells are scanned.
- **World lookup:** 1.13.2 `World.getFluidState(BlockPos)` lines 529–536 returns empty for `isOutsideWorldHeight(pos)` and otherwise delegates to the chunk. 1.16.0 `Level.getFluidState(BlockPos)` lines 418–425 analogously returns empty for `isOutsideBuildHeight(blockPos)` and otherwise delegates to the chunk. This slice did not expand those helper definitions; their exact bounds are not needed for the preflight result above.
- **Chunk-section lookup:** 1.13.2 `WorldChunk.getFluidState(int x, int y, int z)` lines 384–404 returns a section fluid only when `y >= 0 && y >> 4 < sections.length`; otherwise it returns empty. Its `sections` array has 16 entries (field line 59), so Y=256 selects index 16 and returns empty. 1.16.0 `LevelChunk.getFluidState(int i, int j, int k)` lines 230–246 uses the same test against a 16-entry array (field line 61), returning empty at Y=256. Both preserve crash-report propagation for unexpected failures.
- **1.13.2 producer/caller:** `Entity.m_69693160(FluidTags.WATER)` (previous slice, lines 2509–2571) calls `WorldView.isAreaLoaded` at line 2517 and immediately returns false if the area is unavailable/out of range. Its sole assignment to `f_85121000` is at line 2569, after the scan. A targeted search found no other assignment in this `Entity.java`. `Entity.baseTick()` invokes the water refresh; `checkWaterState()` sets `inWater = false` on a false scan result, but it does not reset `f_85121000`.
- **1.16.0 clearing/caller:** `Entity.baseTick()` lines 372–374 invokes the water refresh before living AI. `updateInWaterStateAndDoFluidPushing()` starts by clearing `fluidHeight` (line 948), then calls the water scan. The same false range preflight therefore leaves the current depth map empty. The 1.16 jump consumer also requires `isInWater() && k > 0.0` before treating the state as liquid jumping (previously recorded `LivingEntity.java` lines 2424–2446).
- **Reachable consumer:** 1.13.2 `LivingEntity.mobTick()` reads `f_85121000` in the jump branch (previous r5 evidence, `LivingEntity.java` hash `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, lines 1903–1914). It does not require `inWater` to be true before choosing `jumpInLiquid(WATER)` when the stored depth is positive and above 0.4 while airborne. The player path into this inherited branch is recorded in accepted r5. The 1.16.0 branch is the player-reachable consumer recorded above.

### Conditional causal disposition

**Source-supported initiating state:** if a prior 1.13.2 scan stored water depth above 0.4, then the contracted player-box minimum advances to `>= 256`, the next water scan returns early at the `isAreaLoaded` gate before the depth field is assigned. The boolean water state is cleared, but the previous positive depth remains. With jump input while airborne, the 1.13.2 consumer can still choose `jumpInLiquid(WATER)` from that stale depth because this branch does not gate on `inWater`. This identifies a concrete source-grounded route from the Y=256 range boundary to a stale movement input.

**1.16.0 source disposition:** the same range gate still returns false at that boundary, and the chunk section lookup returns empty there. The per-tick clearing of `fluidHeight` before the scan removes the stale-depth state; the consumer's additional in-water predicate also prevents a false liquid-jump selection. These are source-observed protections at 1.16.0. The bounded endpoints do not identify which intermediate release first added them.

This makes the stale-depth route a plausible source mechanism for MCPK's Y=256 water-exit report, not a runtime-verified demonstration that it produces the page's described jump height in every setup. Keep the exact symptom trajectory and first fixed version open. The Feather-derived 1.13.2 source equivalence caveat remains. No health/food, attack/damage, non-player or vehicle behavior is involved in this disposition.

## Resume checkpoint

Next, inspect the intermediate 1.15.2 producer and jump-consumer bodies for the same bounded state reset and `inWater` gate, then inspect the exact player box/pose writer only if it changes whether `floor(contractedBox.minY)` reaches 256. This can narrow applicability but cannot replace the Feather provenance caveat or runtime validation. Keep the exact higher-jump trajectory and first fixed release open unless exact evidence closes them.
