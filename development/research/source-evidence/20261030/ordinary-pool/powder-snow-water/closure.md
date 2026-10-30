# Powder snow and water callback write-order closure: 1.21.2/1.21.3 vs 26.2

## Scope and verdict

This closes only the `PowderSnowBlock` and `WaterFluid` post-travel callback/write-order comparison. The scheduler premise is inherited from the accepted report and audit listed below: 1.21.2/1.21.3 dispatches qualifying block callbacks synchronously; 26.2 supplies an `InsideBlockEffectApplier` and flushes typed registrations after the sweep. This note does not redo traversal, eligibility geometry, collector mechanics, or the wider callback inventory.

**Verdict:** An old-profile post-travel callback must preserve the old powder-snow response inline and must omit 26.2's swept water-fluid callback. Retaining visitation order while invoking the modern callback does not preserve the old write timing.

## Exact endpoint sources and identities

Canonical read-only sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release / namespace | Source slice | SHA-256 |
| --- | --- | --- |
| 1.21.2 / Mojmap | `net/minecraft/world/level/block/PowderSnowBlock.java`, `entityInside` lines 60–88 | `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654` |
| 1.21.3 / Mojmap | same file and lines; byte-identical to 1.21.2 | `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654` |
| 26.2 / unobfuscated | `net/minecraft/world/level/block/PowderSnowBlock.java`, `entityInside` lines 62–103 | `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417` |
| 1.21.2 / Mojmap | `net/minecraft/world/level/material/WaterFluid.java`, class body around lines 73–98 | `96acd37fbabb3a438fd022a4e7d16cb70f6e91f9068bf73a41a4d2e4f2618fbc` |
| 1.21.3 / Mojmap | same file; byte-identical to 1.21.2 | `96acd37fbabb3a438fd022a4e7d16cb70f6e91f9068bf73a41a4d2e4f2618fbc` |
| 26.2 / unobfuscated | `net/minecraft/world/level/material/WaterFluid.java`, `entityInside` lines 86–89 | `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad` |
| 1.21.2 / Mojmap | `net/minecraft/world/entity/Entity.java`, `applyEffectsFromBlocks` lines 740–767, `checkInsideBlocks` lines 1038–1078, `baseTick` line 449 and water update lines 1295–1317 | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/Entity.java`, `checkInsideBlocks` lines 1278–1305, per-position callback lines 1334–1365 | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |

Source publication identities carried from the accepted evidence: 1.21.2 Mojmap provenance SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; 1.21.3 Mojmap provenance SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`, with ready-marker source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; 26.2 unobfuscated provenance SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`, ready-marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, bound source-manifest SHA-256 `a7ad74fc712567eb87136afc1eaafcfd602b408e9869f3596331fd34faefe894`.

Immutable reviewed inputs: `.native-source/20261030/dda-collector-flush/report.md`, SHA-256 `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e`; `.native-source/20261030/dda-collector-flush-audit/audit.md`, SHA-256 `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba`.

## Historical immediate-response contract

### Powder snow

In both old endpoints, at an eligible swept block, `PowderSnowBlock.entityInside` first tests `!(entity instanceof LivingEntity) || entity.getInBlockState().is(this)`. Only when true does it call `makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9))`; its client particle branch then requires client side, x/z movement since `xOld`/`zOld`, and a successful random boolean. After that conditional, regardless of the first gate, it immediately sets `isInPowderSnow` true. On a `ServerLevel`, it immediately destroys the block without drops only when the entity is on fire, mob griefing is enabled or the entity is a `Player`, and `mayInteract` passes; then it clears the entity's shared on-fire flag. These writes happen during that position's callback, before the next old position is processed.

In 26.2 the same stuck predicate, stuck response and particle gate remain inline. The former direct entity/world effects differ: block destruction is registered with `runBefore(EXTINGUISH)` under the on-fire, mob-griefing-or-player, and `mayInteract` gates; `FREEZE` and `EXTINGUISH` are registered next. `FREEZE` is what writes powder-snow state/frozen response at collector flush. Consequently, later positions in the old synchronous sweep must see the effects of each completed powder-snow callback immediately; substituting the 26.2 callback defers the block and entity writes and alters what later callbacks observe. Preserve the old gates and their order from 1.21.2/1.21.3 when modeling this response.

### Water

The 1.21.2/1.21.3 `WaterFluid` has no `entityInside` override. More importantly, the old post-travel `Entity.checkInsideBlocks` sweeps `BlockPos` candidates and invokes only the block state's `entityInside`, then `onInsideBlock`; it never queries a `FluidState` for a swept `entityInside` callback. Therefore there is no historical swept-water extinguish callback to invoke or order among old block callbacks.

Old water exposure/current behavior is a separate, earlier phase: `Entity.baseTick` calls `updateInWaterStateAndDoFluidPushing` before travel. Its water-current path samples water fluid height, and when in water can produce the first-touch splash, reset fall distance, set `wasTouchingWater`, and call `clearFire`; otherwise it clears `wasTouchingWater`. Do not move or duplicate this base-tick response as a post-travel fluid visit. In 26.2, by comparison, `WaterFluid.entityInside` registers `EXTINGUISH` for the new swept-fluid callback; that registration participates in the deferred collector schedule.

## Callback and finalizer order retained

The inherited old sweep order needed here is: mark a non-air position visited before the partial-shape check; when eligible, synchronously call the historical block-state `entityInside`, then virtual `onInsideBlock`; after both return, append that state to `blocksInside`. After all movement segments and positions complete, `applyEffectsFromBlocks` summarizes `blocksInside` for fire/lava, clears movement and block-state collections, and performs the remaining fire-tick/sound logic. Thus powder-snow's immediate writes precede later callbacks and the post-sweep summary; powder snow contributes to that summary only after its callback and `onInsideBlock` succeed. Keep this summary/finalizer at its old boundary. No swept-fluid callback participates in it.

## Remaining gates

- This closes only the inspected endpoint callback bodies and this narrow old summary phase. The first intermediate patch boundary between 1.21.3 and 26.2 is unknown.
- This does not establish applicability for every block/entity subtype, complete pair coverage, or exact trajectory/runtime parity. Other reused modern callback bodies need their own historical body/gate/write-order checks.
- The accepted scheduler evidence supplies the traversal and exception/death context; this closure does not re-audit those mechanics or authorize calling arbitrary modern callbacks under an old policy.
