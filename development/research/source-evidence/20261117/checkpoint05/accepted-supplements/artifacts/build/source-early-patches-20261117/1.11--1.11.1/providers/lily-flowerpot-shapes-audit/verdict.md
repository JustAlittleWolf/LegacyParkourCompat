# LilyPadBlock and FlowerPotBlock provider audit — ACCEPT

Audited report: `build/source-early-patches-20261117/1.11--1.11.1/providers/lily-flowerpot-shapes.md`  
Report SHA-256: `aa2250078bd22d3aa06eeb731a366ad967fc8e9ffba62b9203ada5d7429e3da7`

## Verdict

**ACCEPT** for the finite LilyPadBlock and FlowerPotBlock collision-provider outputs and the stated `World.getCollisions` → `Entity.move` local-player caller binding.

The exact source roots, root ready-marker and source-manifest identities, and all five paired Java source hashes listed in the report were independently rehashed. Each source hash matches its entry in the corresponding `ornithe-feather.sources.sha256` manifest.

In both releases, LilyPadBlock uses the same fixed box `(0.0625, 0, 0.0625)–(0.9375, 0.09375, 0.9375)` for `getShape` and collision output. Its provider excludes `BoatEntity` and otherwise appends that same box through `Block.addCollision`; 1.11.1's extra `forceShape` parameter is unused. FlowerPotBlock has the same fixed box `(0.3125, 0, 0.3125)–(0.6875, 0.375, 0.6875)` in both releases, does not override `addCollisions`, and inherits the unchanged state collision-shape dispatch. The base provider differs only by accepting the new unused boolean parameter in 1.11.1.

Both world collision scans dispatch block-state providers with the queried entity; 1.11.1 passes `false` for `forceShape`. `Entity.move` supplies `this` to the world collision query, so the report's ordinary player movement binding and LilyPad boat predicate are supported. A local player is not a `BoatEntity`.

## Limits

Acceptance is limited to these two providers and the caller binding. It does not assess other providers, FlowerPot item-use/state production, LilyPad destruction behavior, trajectories, remaining pair inventory, or pair freeze. The exact 1.11→1.11.1 pair remains partial pending its separate coverage and dependency audit.
