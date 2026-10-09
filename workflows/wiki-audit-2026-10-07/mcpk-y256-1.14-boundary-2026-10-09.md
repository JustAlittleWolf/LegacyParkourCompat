# MCPK Y=256 water-exit 1.14.0 boundary memo — 2026-10-09

**State: PARTIAL.** Exact 1.14.0 Feather source supports the conditional stale water-depth path: its loaded-area gate rejects a box whose floored minimum Y is at least 256 before the scanner rewrites the prior depth, and the player-reachable jump consumer can select the water-jump method from that stale value. This confirms the path is present at this exact endpoint. It does not establish when the path first appeared, when it was first prevented, or that it reproduces MCPK's described higher jump in every setup.

## Immutable evidence and source preparation

The prior Y=256 mechanism candidate remains unchanged and pending independent review at `ebdd65a9:workflows/wiki-audit-2026-10-07/mcpk-y256-producer-2026-10-09.md`, blob `fddcf603a83611d9d10a0f5f684e270433fcfd30`, raw SHA-256 `873d3934c9d902f2521913f358b4ca56b9f8d4d9b9acee9ad0a95354125b9a65`. The 1.15.2 boundary record is separately preserved at `de623cb5:workflows/wiki-audit-2026-10-07/mcpk-y256-1.15.2-boundary-2026-10-09.md`. This memo supplements those records without rewriting either.

The exact 1.14.0 source preparation record is commit `69de5a7cfddde6fdb08b2fdde1d5faf7b4eb2e18` on `fix/source-prep-1-14-exact-2026-10-09`. Its canonical ready root is `ready/1.14/ornithe-feather`; resolved mapping is `net.ornithemc:feather-gen2:1.14+build.2`.

- Ready-marker SHA-256: `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`
- Source-manifest SHA-256: `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`
- Artifact-manifest SHA-256: `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df`
- Original client JAR SHA-256: `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`
- Feather-mapped client JAR SHA-256: `ae171032ec6ad71505488c9c5578c5c04168c74ee2cd822177c24e79d45834d8`
- Mapping JAR `feather-gen2-1.14+build.2-mergedv2.jar` SHA-256: `57f0e3227099d010c4a0c7f2fea03dbd665bc7e612ad204db80fa5b5728e65a3`

The ready marker's source/artifact manifest identities matched the sidecars, and every cited source file below matched its manifest entry:

| File | SHA-256 |
|---|---|
| `net/minecraft/entity/Entity.java` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` |
| `net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` |
| `net/minecraft/world/WorldView.java` | `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` |

## Targeted decompiler integrity check

The preparation log reports 184 remapper invalid-access warnings and an access-fixer pass for 7 classes/60 members. The targeted warning search found no warning naming the four cited source classes. The access-fixer summary does not enumerate its classes, so that summary alone cannot clear any particular class. To check only the cited movement members, `javap -c -p` was run on the exact manifest-verified Feather-mapped client JAR above:

- `WorldView.isAreaLoaded(int,int,int,int,int,int)` bytecode tests `maxY >= 0` and `minY < 256` before the X/Z chunk loop.
- `Entity.m_69693160(Tag)` calls that gate and returns immediately on false; the sole `putfield f_85121000` occurs after the fluid scan.
- `Entity.checkWaterState()` sets `inWater = false` when the scanner returns false and does not write `f_85121000`.
- `LivingEntity.mobTick()` reads `f_85121000` and dispatches the water-liquid jump when the depth is positive and the entity is airborne or the depth exceeds 0.4. `PlayerEntity.mobTick()` bytecode invokes `LivingEntity.mobTick()`.

These bytecode checks match the cited control flow and confirm method/member correspondence for this slice despite the publication's general warnings. They do not validate unrelated decompiled methods or prove equivalence of Feather source to another mapping publication.

## Bounded movement path

This slice follows three behavior paths: per-tick water-state refresh, the height-gated depth scan, and the player-reachable jump consumer. `Entity.tick()` line 333 calls `baseTick()`; `baseTick()` line 354 calls `m_03231680()`. That refresh (lines 890–894) calls `checkWaterState()` (904–920), which sets `inWater = false` when the scan returns false.

The scan `Entity.m_69693160(Tag<Fluid>)` lines 2502–2561 contracts the current entity box by `0.001`, floors the contracted minimum Y at line 2506 and ceils its maximum Y at 2507. It calls `WorldView.isAreaLoaded(...)` at line 2510 and returns false at 2511 when that check fails. `WorldView.isAreaLoaded(int,...)` lines 244–263 returns false unless `maxY >= 0 && minY < 256`; for a contracted box whose floored minimum Y is at least 256, the scan exits before assigning `f_85121000` at line 2559. A targeted search across the 1.14 entity source found no other assignment to that field.

The player-reachable consumer is `LivingEntity.mobTick()` lines 2197–2208. If `jumping` is true, a stale `f_85121000 > 0.4` while airborne reaches the `jumpInLiquid(WATER)` call at lines 2198–2207. This decision does not test `inWater`. `LivingEntity.tick()` calls `super.tick()` at line 1973 and then dispatches `this.mobTick()` at line 2039. `PlayerEntity` extends `LivingEntity` (line 107), and its override delegates to `super.mobTick()` at line 490, so the inherited consumer is reachable for players.

## Endpoint comparison and disposition

- **1.13.2:** the earlier Feather trace records the same `minY < 256` preflight, early return before the depth-field write, clearing of the water boolean, and jump dispatch from stale depth. Its original-JAR equivalence caveat remains.
- **1.14.0:** the exact Feather source and targeted mapped-JAR bytecode checks above confirm the same conditional stale-depth state and player consumer at this endpoint.
- **1.15.2:** the previous Mojmap check confirms the same persisted state at `LevelReader.hasChunksAt` and no `waterHeight` clear before the early return; its player consumer still lacks an in-water gate.
- **1.16.0:** the previous Mojmap check records `fluidHeight.clear()` before the scan and an `isInWater() && k > 0` consumer gate, preventing the stale-depth liquid-jump route.

This evidence establishes the mechanism at exact 1.14.0 and 1.15.2 and its protections by exact 1.16.0. It does not assign a first affected 1.14 patch or first fixed 1.15/1.16 patch from endpoint comparisons. The Y=256 height gate is movement-relevant because its stale result reaches direct player jump selection; health/food production, attack/damage resolution, non-player movement and vehicle physics are excluded. No runtime jump trajectory was tested.

## Next exact dependency

Exact 1.15.0 and 1.15.1 source preparation is queued separately. Once ready, compare the same water-depth reset, range-gated early return and player jump consumer there with 1.14.0, 1.15.2 and 1.16.0. That will check for any earlier 1.15-family change or reversion; do not promote an exact cutover until those method sources are verified. No Minecraft Wiki, mod implementation or normal source-lane report was consulted. No shared sources, decompilation, builds, tests, runtime clients, TAS, Gym, servers, Docker or push operations were used.
