# Independent audit: 1.14–1.15.2 body-water admission continuity

## Verdict

**ACCEPT.** The bounded claim is supported in all eight inspected releases: 1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4, 1.15, 1.15.1 and 1.15.2. There is no rejecting release for the pinned missing-neighbor witness in this interval. This establishes continuity only; it does not locate a later cutover.

## Immutable inputs

- Audited source-only claim: `legacy-fluid/body-admission-114-115-cutover`, commit `e3f6c0f7432964116917edb8dffc69617963ea20`, `evidence.md`; raw blob SHA-256 `cfc879a05b2e971cd993f9c765db19b5fbcb6d35c3485a2bf6ac123f8b5d92a4` (recomputed from file bytes and matched).
- Prior accepted prerequisite: `earlier-body-fluid-admission-source`, commit `64e319ee7f760c687078b0e0d38b984d9a8bb573`, `evidence.md`; raw blob SHA-256 `a55a63de56d7c7585a133c9a6f3d5444b449c5a0c7ed4675468291f1750800cb` (matched).
- Prior independent acceptance: `earlier-body-fluid-admission-audit`, commit `269d069b00c89163ab4012ca81a14811464477b0`, `verdict.md`; raw blob SHA-256 `43f989899a7450ac34e2de93e2228dd0ba9093d3ed13806ced230ae81cd9f3be` (matched).

## Source and mapping verification

The exact source roots are read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. The audited mapping identities are Feather Gen2 `1.14+build.2`; Yarn `1.14.1+build.10`, `1.14.2+build.7`, and `1.14.3+build.13`; and Mojmap for exact releases 1.14.4, 1.15, 1.15.1 and 1.15.2. The 1.14 Feather and Mojmap trees carry exact-version ready/provenance markers; the 1.14.1–1.14.3 Yarn trees carry nested provenance naming their exact Yarn artifact coordinates. For all eight releases, the audited `Entity.java` SHA-256 matches the source manifest or embedded provenance source entry. The further `WorldView`/`ViewableWorld`, `LevelReader`, concrete client-world and `World`/`Level` access files checked also match the candidate's pinned source hashes. No source tree was generated or changed.

## Independent trace and fixture

The operation is reached by the water branch in each release's entity water-state updater: 1.14 invokes its Feather helper; 1.14.1–1.14.3 invoke Yarn `updateMovementInFluid`; 1.14.4–1.15.2 invoke Mojmap `checkAndHandleWater`. I traced those call sites to the actual helper bodies in `Entity.java`; this verdict does not rely on a matching method name alone.

Every helper contracts the player's bounding box by exactly `0.001`, floors each contracted minimum and ceils each contracted maximum. For the inherited accepted fixture (ordinary 0.6-wide player centered at x=15.7, z=8, feet at y=64), the contracted x bounds floor/ceil to 15 and 16. At z=8 they floor/ceil to 7 and 9. The area preflight checks chunk endpoints inclusively: x block endpoints 15 and 16 map to chunks 0 and 1. Its release-specific owner is Feather `WorldView.isAreaLoaded`, Yarn `ViewableWorld.isAreaLoaded`, or Mojmap `LevelReader.hasChunksAt`. Each enumerates both X/Z chunk endpoints and delegates through virtual `this.isChunkLoaded` / `this.hasChunk`.

In every inspected client implementation, that virtual predicate unconditionally returns true: Feather/Yarn `ClientWorld.isChunkLoaded(x,z)`, Mojmap 1.14.4 `MultiPlayerLevel.hasChunk(x,z)`, and Mojmap 1.15.x `ClientLevel.hasChunk(x,z)`. The absent adjacent chunk therefore cannot reject this local-client preflight. After admission, all eight helpers use strict `<` upper limits in their nested fluid-cell loops. For the fixture, x is only 15 (not 16), while z includes 8. The body scan reads `getFluidState` at the still-loaded cell `(15,64,8)`; it does not need to read a fluid cell in chunk 1. This closes the endpoint/loop distinction and virtual client dispatch for every release in the assigned interval.

The distinct sample-admission predicate is also preserved in all eight bodies: matching water is accepted when `q + fluidState.getHeight(world, pos) >= contractedBox.minY` (same operation with release-specific names). At feet y=64, contracted minimum Y is 64.001; the stated water surface above that bottom satisfies the predicate. Thus the guard returns through the client virtual truth, the loaded cell is inspected, and the body-water flag is set. The conclusion is not based on the separate contextual-height accumulator.

## Limits

This is a static exact-source audit of one fixed body-water witness. It does not re-review the 1.13 prerequisite, establish an exact later rejection boundary, or broaden into contextual height, eye/current/travel/body behavior beyond this admission predicate. No product implementation/history, wiki, backlog, build, tests, launch, network access or shared-source writes were used.