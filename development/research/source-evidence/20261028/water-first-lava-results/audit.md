# Independent audit r2: water-first lava state projection

## Verdict

**ACCEPT — bounded and conditional on the client-specific chunk semantics below.** The r2 source contract now closes the earlier water-predicate and latch-lifetime gaps: it specifies a legacy WATER-presence Boolean, computed at baseTick, and retained until the next baseTick. With that latch, a public LAVA-height mask plus suppression of each LAVA current-application call preserves the named skipped-LAVA body-height/current consequences even when 26.2 performs a post-move full fluid refresh.

The projection leaves 26.2's tracker-backed `isInLava()` and eye-fluid state modern-owned. That is a deliberate boundary, not historical eye or lava-predicate parity. See remaining conditions below; this is not a full movement-pair or runtime-parity acceptance.

## Immutable input and evidence

- Reviewed report: `.native-source/20261028/lava-gate-state-projection/REPORT-r2.md`
- Input SHA-256: `68681c7b10f50fefc343effdce2475d88db841754c36102907de2cfb1c166052`
- Prior report SHA-256: `d95cf93450707328f7579b09fcbc7bf4493ea969bf688bf651ad6d72f35126cc`
- Prior independent audit preserved unchanged: `.native-source/20261028/lava-gate-state-projection-audit/audit.md` (SHA-256 `77951f809359b3676ec64abbac378a8ce4357b21876cb227f0c5878d3457d5f4`).
- Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, consumed read-only.
- I recomputed SHA-256 values for the additional `LivingEntity`, `LevelReader`, `FluidState`, and `FlowingFluid` files in all four releases; each matches its source-manifest entry and the hashes in r2. The 1.16, 1.16.1, and 1.16.2 client-level/chunk-cache source slices were also checked directly. The provenance limitations recorded in the original report remain in force.

## Water probe and boundaries

The r2 probe matches the 1.16/.1 source algorithm: it uses `getBoundingBox().deflate(0.001)`, floors each minimum and ceils each maximum, checks chunk/vertical availability before the cell loop, iterates min-inclusive/max-exclusive indices, checks `FluidState.is(FluidTags.WATER)`, computes `q + getHeight(level,pos)`, and treats `fluidTop >= deflated.minY` as a hit (`Entity.java:2633-2662`). At equality the Boolean hit is true even though the stored maximum depth can remain exactly zero. Therefore the gate must retain the probe's Boolean result; neither positive WATER height nor modern `isInWater()` is an equivalent substitute.

Boat handling is also correctly separated from the probe: the old helper sets `wasTouchingWater=false` and performs no WATER query when `getVehicle() instanceof Boat` (`Entity.java:958-961`). Such a Player must not set the water-first latch, even if the modern scan reports WATER. The old helper otherwise takes the exact WATER query's Boolean, then the old full update returns before LAVA only on that positive result (`947-956`). 1.16.2 continues to scan LAVA and is correctly excluded from this projection.

## ClientLevel `hasChunk` dispatch: required clarification

The `LevelReader.hasChunksAt()` description in r2 is correct as an interface-default call trace, but the Player path's runtime receiver matters:

- Old `LevelReader.hasChunksAt(int,...)` applies the vertical overlap condition and invokes virtual `this.hasChunk(...)` for each X/Z chunk (`1.16 LevelReader.java:168-185`).
- Old `ClientLevel.hasChunk(int,int)` overrides that method to return `true` unconditionally (`1.16 ClientLevel.java:264-267`; 1.16.1 is byte-identical here and 1.16.2 has the same override at `265-267`). Thus, for the logical-client Player, the old X/Z portion of `hasChunksAt` does **not** establish that real chunks are loaded and does not reject an unloaded/out-of-cache chunk.
- The subsequent old `Level.getFluidState()` calls still go through `getChunkAt()` / `getChunk(..., FULL)` and the client chunk cache (`Level.java:207-217`, `418-424`). `ClientChunkCache.getChunk(..., load=true)` returns a valid cached chunk when present, otherwise its `emptyChunk` (`ClientChunkCache.java:64-74`). It does not turn the preceding `ClientLevel.hasChunk()==true` into a real-load check.
- 26.2 `ClientLevel.hasChunk()` also returns true (`ClientLevel.java:517-520`), and its `ClientChunkCache` likewise returns a valid cached chunk or `emptyChunk` when `loadOrGenerate` is true (`ClientChunkCache.java:69-78`). `Level.getFluidState()` obtains the chunk through that cache (`Level.java:376-382`).

Accordingly, a faithful Player-side target probe must preserve the legacy client dispatch: do not add a real-loaded-chunk/section-availability test for X/Z. The cache-backed `getFluidState()` reads then naturally use present client data or the target cache's `emptyChunk`. The old vertical guard still must be reproduced: the 1.16 call arguments are `(i,k,m,j,l,n)`, so the default check requires `l >= 0` and `k < 256` (`LevelReader.java:168-170`), with the old per-cell outside-build-height behavior retained where it matters. The modern `LevelReader.hasChunksAt` instead uses dynamic `getMinY()/getMaxY()`; calling it unmodified is not sufficient for the old gate (`26.2 LevelReader.java:193-212`).

This client override is why “old X/Z chunk presence” must not be described as “old query only runs in loaded chunks.” It does not invalidate the r2 candidate if its stated old chunk gate means the actual `ClientLevel` virtual-dispatch behavior. It is an implementation acceptance check that r2 should make explicit.

## Tick lifetime and bounded consequences

The 1.16/.1 full fluid update is at baseTick (`Entity.java:372-374`). Later, `LivingEntity.checkFallDamage()` can call only `updateInWaterStateAndDoWaterCurrentPushing()`, and only while `!isInWater()` (`LivingEntity.java:255-259`); it neither clears the fluid map nor scans LAVA. So a base-tick water-positive result leaves LAVA depth at the cleared-map default through this later refresh. The r2 rule to latch the old Boolean at baseTick and not recompute it during later calls matches that lifetime.

In 26.2, the Player baseTick full scan precedes movement (`Entity.java:530-532`); local-authoritative `Entity.move()` can call `checkFallDamage()` after position update (`Entity.java:777-778`), whose `LivingEntity` implementation may perform another full `updateFluidInteraction()` (`LivingEntity.java:363-366`). That update resets and repopulates tracker height, eyes, and current. Keeping the baseTick latch through it means:

- with old baseTick WATER hit and no Boat, every later LAVA public-height read remains masked to zero;
- each later LAVA `applyCurrentTo` call is skipped, so no LAVA impulse from that reentrant scan is applied;
- private LAVA tracker height/current and eye flags remain free to take modern scan values, and `isInLava()` continues to read tracker presence directly (`Entity.java:1742-1744`; public height wrapper `3683-3685`).

This does not claim the post-move scan itself is equivalent to the old water-only refresh. It restores only the report's bounded skipped-LAVA body-height/current consequence. If the baseTick old WATER probe is false, the extra modern post-move LAVA current remains a separate open delta, as r2 states.

## Remaining limits

- The source probe needs the client-specific `hasChunk()==true` dispatch and must not substitute actual loaded chunk/section presence. The r2 report should state this explicitly; the verdict above is conditional on that detail being honored.
- Preserve the old vertical and per-cell outside-build-height semantics. Worlds using modern extended/nonstandard height ranges remain an explicit boundary unless the probe maps old out-of-range cells to empty as legacy `Level.getFluidState()` did.
- External fluid-tag membership/data packs are not bound by these source files; exact tag contents remain an applicability condition where modified data is in scope.
- The probe is a presence-only helper: do not invoke fluid current calculation or update WATER map/flags/fire/fall-distance state as part of gate evaluation.
- Eye sampling and 1.16/.1 collision-callback `isInLava()` remain different operations. The added mask/suppression does not mutate them, but modern tracker refreshes can still update eyes and tracker-backed `isInLava()`; no parity claim is made for those values.
- This does not close the 26.2 false-water post-move current case, WATER height/current differences, other player movement behavior, intermediate release boundaries, or runtime parity.

## Scope audit

Only the assigned r2 report, required discovery/workflow context, and named canonical sources/manifests were used. Product source, Git history, backlog, wiki, other discovery lanes and candidate implementations were not inspected. No builds, tests, game launches or source preparation were run.

The SHA-256 of this audit is computed after writing and reported to the coordinating agent; it is not embedded because that would change the file hash.
