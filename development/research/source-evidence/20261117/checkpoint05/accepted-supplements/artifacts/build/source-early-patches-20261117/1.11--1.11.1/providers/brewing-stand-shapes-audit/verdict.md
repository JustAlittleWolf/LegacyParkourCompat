# Brewing stand collision provider audit — ACCEPT

Audited report: `build/source-early-patches-20261117/1.11--1.11.1/providers/brewing-stand-shapes.md`  
Report SHA-256: `ae395b5ec725cbf487b5d5d83018c986544d448a32c7052025ed508d88d1467c`

## Verdict

**ACCEPT** for the bounded BrewingStand collision provider comparison, finite state enumeration, and local-player query binding described in the report.

The report hash matches. The 1.11 and 1.11.1 provenance, source-manifest, ready-marker and artifact-manifest hashes match the exact sidecars. Both `BrewingStandBlock.java` hashes and the paired `Block.java`, `World.java`, `Entity.java`, and `LocalClientPlayerEntity.java` hashes match their exact source-manifest rows.

Each endpoint defines exactly three independent boolean `HAS_BOTTLE` properties, so the state space has eight combinations. Both versions emit the same two collision boxes in order: central column `(0.4375, 0, 0.4375)–(0.5625, 0.875, 0.5625)`, then platform `(0, 0, 0)–(1, 0.125, 1)`. The provider does not inspect state for this output. The 1.11.1 `forceShape` parameter is unused. The shared `Block.addCollision` helper translates each box to the block position and appends it only when it intersects the query shape.

`LocalClientPlayerEntity.move` delegates to `super.move`; `Entity.move` passes `this` to `World.getCollisions`, which dispatches each block state’s collision provider. The 1.11.1 world path passes `false` for `forceShape`. This supports the stated ordinary local-player query binding.

## Limits

Acceptance covers only this provider and its query binding. Brewing block-entity behavior, state production/reachability, other providers, trajectories, remaining pair inventory and pair freeze are outside this audit. The exact 1.11→1.11.1 pair remains partial pending its separate coverage and dependency audit.
