# 1.21.3–1.21.4 block callback segment closure

## Scope and limits

Source-only closure for vanilla `WebBlock` (cobweb), `SweetBerryBushBlock`, and `PowderSnowBlock` `entityInside` callbacks; the block-position traversal and collision predicate feeding those callbacks; ordinary locally controlled player tick reachability; and client piston movement callback reachability. This is not a full pair inventory, pair freeze, runtime parity assessment, or trajectory analysis. No implementation, tests, builds, game/server launches, or canonical source writes were done.

Exact decompiled Mojmap sources were read from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.3\mojmap` and `...\1.21.4\mojmap` (read-only). Version/mapping identity is Mojmap, metadata IDs `1.21.3` and `1.21.4`.

## Source identity and integrity

Both `mojmap.ready.json` markers report `status=ready`, the matching exact `versionMetadataId`, Vineflower decompilation, 5,655 files / 26,544,115 bytes for 1.21.3 and 5,744 files / 26,784,732 bytes for 1.21.4. Ready-marker source-manifest and artifact-manifest SHA-256 values match the actual manifest files:

| Release | source manifest SHA-256 | artifact manifest SHA-256 | client jar SHA-256 |
| --- | --- | --- | --- |
| 1.21.3 | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` | `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b` |
| 1.21.4 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` |

The provenance files identify a single exact decompile run with `--versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap`; each release marker points to its own version metadata ID and corresponding client artifact. The shared batch’s adjacent release identities are 1.21.1 and 1.21.5. Mapping and decompiler tool versions in provenance are mapping-io 0.9.1 and Vineflower 1.12.0. Each whole source manifest hash matches the ready marker. The relevant source-file hashes below were independently recomputed and match their manifest entries.

| Source member | 1.21.3 SHA-256 | 1.21.4 SHA-256 |
| --- | --- | --- |
| `client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |
| `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| `world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| `world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` | `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20` |
| `world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` | `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf` |
| `world/level/block/WebBlock.java` | `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414` | same |
| `world/level/block/SweetBerryBushBlock.java` | `529bb21df59f802b83a589440dc01d9a3b8814499b58b0624c83e4b24a85d2ff` | `aba669e3e88a63005869ccd667aa665351ea70150b42da8a2fb8704f569c50b5` |
| `world/level/block/PowderSnowBlock.java` | `a018411167f5d8b60b131c3f6387dcdd256b736ef64126bdcf33fd4457151654` | same |
| `world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | same |
| `server/network/ServerGamePacketListenerImpl.java` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` | `c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b` |

## Bounded findings

### Callback path and exact inputs

- `LivingEntity` post-travel tick calls `applyEffectsFromBlocks()` when `!level.isClientSide() || isControlledByLocalInstance()` (1.21.3 `LivingEntity.java:2774–2776`; 1.21.4 `:2785–2787`). `LocalPlayer.tick()` calls `super.tick()` (1.21.3 `LocalPlayer.java:189–192`; 1.21.4 `:191–196`); its `move` override delegates to `super.move` (1.21.3 `:873–876`; 1.21.4 `:903–906`). This makes the ordinary local-player post-travel path reachable on client. Remote client-side living entities fail the controlled-instance gate. `LocalPlayer.isLocalPlayer()` returns true and `isEffectiveAi()` returns true (1.21.3 `LocalPlayer.java:347–349, 478–480`; 1.21.4 `:352–354, 483–485`); `Entity.isControlledByLocalInstance()` uses the local-player predicate for a controlling player or falls back to `isEffectiveAi()` (1.21.3 `Entity.java:3149–3151`; 1.21.4 `:3173–3175`).
- The no-argument `Entity.applyEffectsFromBlocks()` supplies exactly `(oldPosition(), position)` (1.21.3 `Entity.java:736–749`; 1.21.4 `:747–762`). If affected by blocks (`!isRemoved() && !noPhysics`, 1.21.3 `:769–771`; 1.21.4 `:781–783`), it first invokes `stepOn` when on ground, appends one `Movement(from,to)` to `movementThisTick`, and passes accumulated movement entries to `checkInsideBlocks`.
- 1.21.3 checks the live `movementThisTick` list and clears it after `checkInsideBlocks` (`Entity.java:748–752`). 1.21.4 copies the list, clears the live list, then checks the copy (`:759–765`). The checker processes entries in list order. `visitedBlocks` suppresses a second callback for the same packed block position within that check and is cleared after the loop (`1.21.3 Entity.java:1038–1078`; 1.21.4 `:1050–1089`). It is cleared after normal traversal, but the not-alive early return before that clear can retain entries. Therefore it is a per-check de-duplication mechanism on the normal path, not a tick-global callback guarantee.
- `BlockGetter.boxTraverseBlocks(from,to,bounds)` forms candidates from the supplied segment and an AABB-derived block range (1.21.3 `BlockGetter.java:172–189`; 1.21.4 same lines). Both versions append the swept block traversal candidates and positions in the AABB in insertion order. 1.21.3 normalizes the segment delta, scales it by `1.0E-7`, and offsets the traversal endpoints (`:179–183`); 1.21.4 starts at the AABB minimum and uses `min - delta` without those epsilon adjustments (`:179–183`). The candidate set is an ordered set, so repeated positions are removed while preserving first insertion.
- The bounds differ materially: 1.21.3 computes one `getBoundingBox().deflate(1.0E-5F)` before iterating every segment (`Entity.java:1040–1047`); 1.21.4 computes `makeBoundingBox(to).deflate(1.0E-5F)` separately for each movement endpoint (`:1054–1059`). For non-full-block inside-collision shapes, the subsequent contact predicate uses `makeBoundingBox(from)` swept by `to-from` against the translated collision-shape AABBs (1.21.3 `Entity.java:1081–1083`; 1.21.4 `:1092–1095`).
- For each candidate, the checker reads the current `BlockState` from the entity’s current level, skips air, and asks the state for `getEntityInsideCollisionShape(level,pos)` (`Entity.java` lines above). The default `BlockBehaviour.getEntityInsideCollisionShape` returns the singleton `Shapes.block()` (1.21.3 `BlockBehaviour.java:353–355`; 1.21.4 same method). The three scoped block classes do not override that provider. The checker bypasses the segment/shape predicate for this full-block sentinel and calls `state.entityInside(level,pos,entity)`; the state method delegates to the block override (`BlockBehaviour.java:736–742`). Thus these three callbacks receive the block state looked up at callback time and the entity; their admission uses candidate positions from the segment traversal, not their ordinary render/outline shape. This does not establish a trajectory or parity outcome.

### Scoped callback consumers

- `WebBlock.entityInside` always calls `makeStuckInBlock` with `(0.25,0.05F,0.25)`, or `(0.5,0.25,0.5)` for a `LivingEntity` with Weaving (`WebBlock.java:26–35` in both releases; whole-file hashes identical).
- `SweetBerryBushBlock.entityInside` requires `LivingEntity` and excludes foxes and bees before `makeStuckInBlock(state,(0.8F,0.75,0.8))` (`SweetBerryBushBlock.java:79–91`, unchanged callback body). Its berry-damage branch additionally requires `ServerLevel`; therefore that branch is not reached by the local client callback. The other release-only file difference in this class is outside this callback (`getCloneItemStack` signature).
- `PowderSnowBlock.entityInside` applies the movement multiplier when the entity is not living or its `getInBlockState()` already identifies this block, then sets the in-powder-snow flag (`PowderSnowBlock.java:60–88`, identical file hash across releases). Its usual collision-shape override (`:100–115`) is not the separate entity-inside collision-shape provider used by this callback loop.

### Locally reachable piston callbacks and exclusions

- `PistonMovingBlockEntity.tick(Level,...)` calls `moveCollidedEntities` while progress is below 1 (`:286–315`); no logical-side guard wraps that branch. The collision helper invokes `entity.move(MoverType.PISTON, delta)` and then `entity.applyEffectsFromBlocks()` for each actual piston push (`:182–187`). On a local player this is a direct client response, independent of the ordinary post-travel call. The no-argument apply method still supplies `(oldPosition(), position)`, so its scan input spans tick-old position to the post-push position; it does not supply a captured pre-push position. The movement list is consumed by each call in both releases. Exact details of duplicate callbacks caused by multiple piston substeps within one tick depend on block candidate coordinates and the per-call clear/de-dup logic; no trajectory claim is made.
- `recordMovementThroughBlocks` adds segment inputs without immediately checking them (`Entity.java:1034–1035` in 1.21.3; `:1046–1048` in 1.21.4). Whole-source call-site search found only two callers in each release, both in `ServerGamePacketListenerImpl` (1.21.3 `:452, :930`; 1.21.4 `:454, :990`). Those are server packet handling paths, not LocalPlayer client tick accumulation. Historical client movement must not run on logical servers; server callback execution remains native behavior.
- `Entity.applyEffectsFromBlocks` and piston handling are shared vanilla entity paths; non-player entities can also reach these callbacks. This scoped result makes no claim that callbacks are globally player-only. Non-player physics and server-side execution are outside historical movement emulation scope. Removed and `noPhysics` entities are excluded by `isAffectedByBlocks`; dead entities stop the callback loop at its next alive check. Piston source-base correction after a push (`fixEntityWithinPistonBase`) may perform an additional position correction after the shown apply call; no separate post-correction callback was established here.

## Open gates

The checked evidence closes only this requested callback/segment dependency bundle for these exact two releases. It does not establish when any behavior changed between other patches, full movement-area coverage, runtime parity, player trajectory consequences, or callback reachability for every block-state/segment combination. Exact per-input candidate ordering and outcomes remain dependent on concrete positions, current level states, entity dimensions, and state-specific segment collision data.

