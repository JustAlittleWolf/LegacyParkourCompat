# Independent exact-source verdict: 1.16.1–1.16.2 container collision slice

## Evidence identity

Minecraft 1.16.1 → 1.16.2, Mojmap. The canonical source roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.16.1/mojmap/` and `.../1.16.2/mojmap/`. Both `mojmap.ready.json` records identify status `ready`, the named releases, Mojmap, and Vineflower. Their provenance SHA-256 values are `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` and `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; source-manifest SHA-256 values are `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` and `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. I verified the hashes of all cited core files against their per-version source-manifest entries, including `ChestBlock.java`, `EnderChestBlock.java`, `HopperBlock.java`, `Blocks.java`, `BlockBehaviour.java`, `Block.java`, `CollisionGetter.java`, and `CollisionSpliterator.java`. Identical helper hashes were verified for `AbstractChestBlock.java`, `TrappedChestBlock.java`, and `entity/Hopper.java`.

## Provider verdicts

| Provider | Verdict | Independent result |
|---|---|---|
| Ordinary chest | ACCEPT | `ChestBlock#getShape` (155–171 both) and `getConnectedDirection` (173–175 both) are identical. Single and paired chest boxes and facing/type selection match. `Blocks.CHEST` registration at 476→475 retains the same block class and relevant properties. |
| Trapped chest | ACCEPT | `TrappedChestBlock.java` is byte-identical across the pair and inherits `ChestBlock` shape behavior. `Blocks.TRAPPED_CHEST` at 1252→1259 retains the same class/properties. |
| Ender chest | ACCEPT | `EnderChestBlock#getShape` (54–56→55–57) returns the same constant shape `Block.box(1,0,1,15,14,15)`; its relevant registration at 1048→1055 is unchanged. The changed container-title declaration is non-physical and excluded. |
| Hopper | ACCEPT | `HopperBlock` physical-shape constants and `getShape` facing dispatch (57–72 both) are identical; the separate interaction shape is also unchanged and was not substituted for physical collision. `Hopper.INSIDE` source is byte-identical; `Blocks.HOPPER` at 1290→1297 retains the same class and relevant properties. |

## Query and cache limits

**ACCEPT — collision flags/context/cache for these providers.** `BlockBehaviour` keeps `hasCollision` defaulting true and the same `noCollission`/`noOcclusion` effects; all four registrations are unchanged in the relevant properties. Default `getCollisionShape` still gates on `hasCollision` and delegates through the state shape path. The cache computes collision shape with `CollisionContext.empty()`, while the ordinary `CollisionSpliterator` query passes entity context. These four providers' `getShape` methods ignore context (and, for these fixed shapes, getter/position), so the empty-context cached shape and queried shape agree. `BlockBehaviour.java` does change for support-type caching: the one-argument `isFaceSturdy` now uses `SupportType.FULL`, whose implementation calls the same `Block.isFaceFull(blockState.getBlockSupportShape(...), direction)` expression used by 1.16.1. This does not change the audited full-support result for these providers. This verdict does not accept every other `BlockBehaviour` predicate or caller.

**ACCEPT — existing ordinary block-collision scan.** `CollisionGetter#getBlockCollisions` retains the same stream construction and `CollisionSpliterator.java` has the same full-file hash in both releases. The scan preserves cursor bounds, predicate/gates, context passed to `getCollisionShape`, block-position translation, full-block shortcut, and general VoxelShape overlap check. The 1.16.2-only `CollisionGetter#noBlockCollision` wrapper is a separate API addition over that scan; its callers and whole-player call graph were not audited here.

## Scope boundary

The compared claim is only stationary ordinary fixed dry chest/trapped chest/ender chest/hopper geometry, relevant collision flags and shape context/cache behavior, plus the existing ordinary collision scan. No source difference or player movement consequence is established in this slice. Placement and neighbor-state production, wet or otherwise changing states, all callers and the full player tick graph, other block families, and broader `BlockBehaviour` changes remain unreviewed. This is not full-pair coverage, independent full-pair audit, or pair freeze.

## Compared coverage report identity

The assigned immutable report `b_containers/coverage.md` was read only after independently deriving the above provider, registration, context/cache, and scan results. Its SHA-256 matches the assigned identity: `e1695d8b7f475d463c5dcf7622110efb1cbc778f8447a10bd63e7e456e355003`. Its bounded no-difference verdict is accepted; this independent verdict makes explicit that acceptance is provider-specific and does not close broader caller or pair coverage.