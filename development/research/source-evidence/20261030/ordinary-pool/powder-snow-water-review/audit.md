# Independent audit: powder snow and water callback closure

## Verdict and reviewed input

Reviewed `.native-source/20261030/ordinary-pool/powder-snow-water/closure.md`, SHA-256 `c7b9be695a36824c5ffee9653785716d7e7cda372952494b8eaf5b2201bbdfb6` (matched).

**Verdict: accept the endpoint callback/write-order conclusion, with one precision correction to the modern powder-snow gate timing.** The old PowderSnowBlock callback performs its historical response inline; 26.2 retains the stuck/particle path inline but queues typed effects and the block-destruction consumer for collector execution. The old WaterFluid has no entityInside override and the accepted old swept-block dispatcher does not call fluid callbacks. This is a bounded endpoint finding, not pair freeze or runtime-parity evidence.

## Powder snow body and effect timing

- In both old Mojmap endpoints, the LivingEntity/in-block-state gate controls `makeStuckInBlock` and its client particle sub-gates. After that conditional, `setIsInPowderSnow(true)` always runs. Only inside the `ServerLevel` branch, destruction is immediately gated by on-fire, mob-griefing-or-Player, and `mayInteract`; `setSharedFlagOnFire(false)` then runs unconditionally in that server branch, whether or not destruction occurred. The source order and the closure’s account of these writes are accurate.
- In 26.2, the same stuck and particle gates remain inline. `runBefore(EXTINGUISH, consumer)` itself is registered unconditionally. The consumer later checks `ServerLevel`, on-fire, mob-griefing-or-Player, and `mayInteract` when the collector executes it; those gates are not evaluated when registered. Then `FREEZE` and `EXTINGUISH` are registered. Collector order runs the before-consumer for `EXTINGUISH`, then `FREEZE`, then `EXTINGUISH`. `FREEZE` sets powder-snow state and conditionally increments frozen ticks; `EXTINGUISH` clears fire. These typed effects and the destruction consumer therefore do not provide the old per-position inline writes. The closure’s phrase “registered ... under [the] gates” should be read as describing the consumer’s eventual predicate, not a registration-time check.
- The accepted scheduler report/audit confirms the old synchronous block callback order and the modern deferred collector lifecycle. The 26.2 effect type and collector bodies independently agree with that typed-effect description.

## Water callback and phase route

- `WaterFluid.java` in both old Mojmap endpoints contains no `entityInside` override (no such declaration in either file). The modern unobfuscated body registers `EXTINGUISH` through `effectApplier.apply`.
- The accepted scheduler evidence establishes that the old swept pass invokes block-state `entityInside` and `onInsideBlock`, not `FluidState.entityInside`. The old water response belongs to the earlier `Entity.baseTick` path: it calls `updateInWaterStateAndDoFluidPushing` before travel; that updates water-current pushing, splash on first contact (when not first tick), fall distance, `wasTouchingWater`, and fire, or clears `wasTouchingWater` when water is absent. The closure correctly keeps this out of the post-travel swept-fluid callback schedule.

## Summary/finalizer boundary

The closure’s old post-sweep ordering matches `Entity.applyEffectsFromBlocks`: after the sweep, summarize `blocksInside` for FIRE/LAVA, clear `movementThisTick` and `blocksInside`, then run the fire-tick/sound follow-up. Within each accepted old position, add the block state to the summary only after `entityInside` and `onInsideBlock` return. The phase-order supplement clarifies a relevant preceding phase: on-ground `stepOn` runs before the sweep and is not a swept callback or member of `blocksInside`. An inline powder-snow write to `isInPowderSnow` is visible to the old summary/finalizer; the modern deferred FREEZE write is not visible at that point in an old-policy sweep. Keep `stepOn`, the old summary, and the old finalizer at their source boundaries.

## Identity and bounded limits

The cited source hashes match the canonical files. `PowderSnowBlock.java`: 1.21.2 and 1.21.3 `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654`; 26.2 `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417`. `WaterFluid.java`: 1.21.2 and 1.21.3 `96acd37fbabb3a438fd022a4e7d16cb70f6e91f9068bf73a41a4d2e4f2618fbc`; 26.2 `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad`. Old `Entity.java` is byte-identical across 1.21.2/1.21.3 at `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; 26.2 is `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.

Provenance identities match the note: 1.21.2 `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; 1.21.3 `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`, with `mojmap.sources.sha256` SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; 26.2 `unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`, `unobfuscated.ready.json` SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, and source manifest SHA-256 `a7ad74fc712567eb87136afc1eaafcfd602b408e9869f3596331fd34faefe894`. The relevant entries in those source manifests match the file hashes above.

Dispatch/finalizer context is bound to accepted scheduler report `.native-source/20261030/dda-collector-flush/report.md`, SHA-256 `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e`, and independent audit `.native-source/20261030/dda-collector-flush-audit/audit.md`, SHA-256 `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba`. Phase-order supplement `.native-source/20261030/ordinary-pool/shared-callback-phase-order/phase-order.md` SHA-256 `7cf4ff49b5aeb6ec996a38ccb10e0dbdfc7fe0aadb5f08105ff23fc87b7741de` corroborates `stepOn` and finalizer ordering.

The first intermediate patch boundary between 1.21.3 and 26.2 remains unknown. This audit does not broaden into complete pair coverage, other callback bodies, or runtime parity. No source or product files were changed; no tests, builds, or game launches were run.
