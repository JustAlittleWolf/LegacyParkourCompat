# MCPK Y=256 water-exit 1.15.2 boundary follow-up — 2026-10-09

**State: PARTIAL.** This source-only follow-up verifies the stale water-depth mechanism at exact 1.15.2 and compares its endpoint behavior with the previously checked 1.13.2 and 1.16.0 sources. It brackets the observed protection change to after 1.15.2 and by 1.16.0; it does not identify an exact first fixed release or validate the reported jump trajectory at runtime.

## Immutable candidate preserved

The prior source-grounded mechanism candidate remains unchanged at `ebdd65a9:workflows/wiki-audit-2026-10-07/mcpk-y256-producer-2026-10-09.md`, Git blob `fddcf603a83611d9d10a0f5f684e270433fcfd30`, raw SHA-256 `873d3934c9d902f2521913f358b4ca56b9f8d4d9b9acee9ad0a95354125b9a65`. This follow-up is an additional boundary record, not a revision of that candidate or a change to r5's bounded acceptance.

## 1.15.2 publication identity

The exact ready root is `ready/1.15.2/mojmap`. Its marker reports `ready`, version `1.15.2`, and mapping `mojmap`; the marker's source-manifest and artifact-manifest hashes match the sidecar files.

- Ready-marker SHA-256: `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`
- Source-manifest SHA-256: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`
- Artifact-manifest SHA-256: `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`

The cited 1.15.2 source hashes match their manifest entries:

| File | SHA-256 |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/world/level/LevelReader.java` | `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` |

For the 1.13.2 Feather and 1.16.0 Mojmap marker, manifest and source identities, retain the verified records in [the preceding producer follow-up](mcpk-y256-producer-2026-10-09.md). Feather-to-original-JAR equivalence remains unproven.

## Bounded paired behavior paths

The comparison follows three movement paths: per-tick water-state refresh; water-depth scan with its direct loaded-area gate; and the jump consumer. It reuses the prior exact endpoint evidence and adds the 1.15.2 methods below.

### 1.15.2 source evidence

- **Tick order and refresh:** `Entity.tick()` lines 340–346 calls `baseTick()`, and `baseTick()` line 363 calls `updateWaterState()` before the living tick. `updateWaterState()` lines 943–947 calls `updateInWaterState()`, underwater-state refresh and swimming update. `updateInWaterState()` lines 957–973 calls `checkAndHandleWater(FluidTags.WATER)`; on a false result it sets `wasInWater = false` at lines 968–970. Neither refresh method clears `waterHeight`.
- **Height gate and water scan:** `Entity.checkAndHandleWater(Tag<Fluid>)` lines 2593–2652 contracts the current box by `0.001`, floors `minY` and ceils `maxY` (2594–2600), then calls `LevelReader.hasChunksAt(...)` (2601). `LevelReader.hasChunksAt(int i, int j, int k, int l, int m, int n)` lines 157–176 returns false unless `m >= 0 && j < 256`, then checks the X/Z chunk rectangle. When `floor(contractedBox.minY) >= 256`, the scan returns false at line 2602 before its only `waterHeight` assignment at line 2650. A targeted search across the 1.15.2 Mojmap source root found no other `waterHeight =` writer.
- **Jump consumer:** `LivingEntity.aiStep()` lines 2265–2278 checks `this.jumping` and branches on `waterHeight`, `onGround`, and `noJumpDelay`. When airborne with stale `waterHeight > 0.4`, it selects `jumpInLiquid(FluidTags.WATER)` at line 2275; this branch has no `isInWater()` predicate.
- **Player route:** `Player` extends `LivingEntity` (class declaration line 110). `Player.tick()` calls `super.tick()` at line 237; `LivingEntity.tick()` calls `super.tick()` at line 2025 and `this.aiStep()` at line 2103. `Player.aiStep()` delegates to `super.aiStep()` at line 514. Thus the entity refresh runs before the player-reachable living jump branch. Only the movement delegation was used from `Player.aiStep()`; its unrelated health and food code is outside this audit.

### Endpoint comparison

- **1.13.2:** The prior trace records the same bounded pattern: `Entity.baseTick()` invokes water refresh; `m_69693160(FluidTags.WATER)` returns early when `WorldView.isAreaLoaded(...)` fails, before assigning `f_85121000`; `LivingEntity.mobTick()` can then choose `jumpInLiquid(WATER)` from stale depth without an `inWater` gate. The Feather provenance caveat remains.
- **1.15.2:** The `LevelReader` gate preserves the same Y interval (`maxY >= 0 && minY < 256`). `checkAndHandleWater` returns early before the only `waterHeight` assignment; `updateInWaterState` clears `wasInWater` but does not clear the depth field. The player-reachable `LivingEntity.aiStep()` consumer still has no `isInWater()` gate. The source therefore supports persistence of the conditional stale-depth mechanism at this exact endpoint.
- **1.16.0:** The prior trace records that `Entity.updateInWaterStateAndDoFluidPushing()` clears `fluidHeight` before running the scan, so a failed range gate leaves no stale depth. Its jump consumer also requires `isInWater() && k > 0.0` before taking the liquid-jump path. These protections are present in exact 1.16.0 Mojmap.

## Disposition

The conditional stale-depth mechanism is source-supported in 1.13.2 and persists at 1.15.2. It is prevented by source-observed producer clearing and the consumer in-water gate in 1.16.0. The evidence brackets this protection change to **after 1.15.2 and by 1.16.0**; it does not establish a first patch or imply behavior for uninspected 1.14 releases. Exact 1.14.0 preparation is a separate active dependency.

This closes the requested 1.15.2 producer/consumer check, not the reported higher-jump trajectory: source establishes a stale depth that can select the liquid-jump method, but no gameplay/runtime validation quantified the resulting jump. Keep the trajectory and exact release boundary separate from this method-level mechanism. Health/food production, attack/damage, non-player movement and vehicle physics remain excluded. No Minecraft Wiki, mod implementation or normal source-lane report was consulted; no shared sources, builds, tests, runtime programs, TAS, Gym, servers, Docker or push operations were used.

## Next exact dependency

The exact 1.14.0 Feather publication is now reported ready in the separate preparation lane. Its bounded producer/consumer check is recorded in [the 1.14 boundary memo](mcpk-y256-1.14-boundary-2026-10-09.md). That endpoint can establish whether the stale-depth path is present in 1.14.0; it does not replace the exact first-fix boundary check. Preserve the 1.15.2/1.16.0 protection bracket unless a source at the intervening boundary identifies the first change.
