# Independent exact-source verdict — 1.15.1 → 1.15.2 stationary block slice

## Review binding

Reviewed producer artifact: `C:\Users\Wolfi\.codex\visualizations\2026\11\18\01a1ed7a-83ee-7ad0-9155-64c0883a7c62\b-mid-01\COMPLETED.md`  
SHA-256: `2f8b36753699a05f93fa54dcb04671f2288353d640f0136a06760a728ff10811` (verified).

Exact sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.15.1,1.15.2}\mojmap`, Mojmap. Manifest SHA-256 values verified independently: 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. Provenance SHA-256 values verified: 1.15.1 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`; 1.15.2 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`. For each release, independently calculated bytes for Blocks.java, BeehiveBlock.java, ConcretePowderBlock.java, BaseEntityBlock.java, FallingBlock.java, Block.java, and BlockState.java match their manifest entries.

## Judgments

### ACCEPT — Beehive and bee nest: no eligible fixed-state collision difference

Both releases register BEE_NEST and BEEHIVE as BeehiveBlock using the same wood material; their respective strength values are unchanged. BeehiveBlock has no shape override and inherits BaseEntityBlock → Block. Its facing and honey-level properties do not affect the collision shape. Block.Properties defaults `hasCollision` to true; the registrations do not disable collision. Block.getShape returns `Shapes.block()`, and getCollisionShape returns the state's shape when collision is enabled. BlockState's shape delegation and cached collision-shape path are byte-identical between versions. The BeehiveBlock source delta concerns honeycomb drops, which does not change ordinary dry player collisions.

### ACCEPT — Concrete powder and cured concrete: no eligible fixed-state collision difference

The Blocks registrations are byte-identical. Cured concrete is an ordinary Block; powder is ConcretePowderBlock → FallingBlock → Block. These classes add no relevant shape override, and their registered properties retain the collision-enabled full-block defaults. ConcretePowderBlock changes in 1.15.2 affect onLand, placement and water/neighbor-triggered state production. Those are outside the fixed-state ordinary dry collision boundary and the explicit scope exclusions, so I agree they do not establish an eligible collision finding in this slice.

### CORRECT — none

I found no factual or scope correction to the producer's bounded no-finding conclusions.

### OPEN — pair-wide coverage and version boundary

This verdict and the producer artifact cover only the assigned block slice. They do not establish full-pair source coverage, close other movement dependencies, or support a full-pair freeze. The exact version at which any behavior was introduced between 1.15.1 and 1.15.2 remains unexamined. No runtime parity claim is made.

## Limits

The review is limited to adjacent fixed, stationary legal states of bee nest/beehive, concrete powder and cured concrete and their ordinary dry client-collision shape route. Bee interactions, honey harvesting effects beyond the inspected drop delta, placement, curing, falling, environmental or neighbor-state production, fluids, pose, and world-border behavior were not accepted as findings. No product code, history, wiki material, tests, builds or game runtime were inspected or used.