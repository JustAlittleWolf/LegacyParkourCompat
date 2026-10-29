# Iron bars (legacy block ID 101): bounded collision source comparison

## Scope and evidence basis

This finding is limited to old block ID 101 (`iron_bars`) in the ready 1.8.9 Ornithe Feather source and the current 26.2 unobfuscated source. It compares the old movement collision provider with the current registered block/provider. It does not claim DDA parity, complete neighbor-family coverage, or whole-movement equivalence. Every cited source file is byte-checked against its canonical source manifest; exact file digests and ready-marker/manifest digests are in `source-hashes.json`.

## Identity and state mapping

In 1.8.9, `Block.java` registers numeric ID 101 as `new PaneBlock(Material.IRON, true)` (registration around line 1040). This establishes that ID 101 is specifically the iron-material PaneBlock instance, independently of neighboring pane IDs. `PaneBlock.getMetadataFromState` returns zero; its default directional properties are north/east/south/west false.

In 26.2, `Blocks.java` registers `BlockItemIds.IRON_BARS`, whose ID is created from `iron_bars`, with `IronBarsBlock::new`. `EntityBlockStateFix.java` maps `minecraft:iron_bars` to legacy numeric ID 101. `BlockStateData.java` registers legacy state ID 1616 (101 × 16) to `minecraft:iron_bars` with all four direction properties false, followed by direction variants. This supports the exact named target and default-state mapping for this bounded comparison; it is not a claim about every historical neighbor’s state conversion.

## Historical collision provider

`PaneBlock.addCollisions` (1.8.9 lines 62 onward) queries north, south, west, east neighbors from the live `World` and calls `shouldConnectTo` on each block. For ID 101, that predicate accepts opaque blocks, the same block instance, glass, stained glass, stained glass panes, and any `PaneBlock` (lines 134 onward). The method then conditionally emits up to two axis-aligned strips by mutating the pane block’s bounds and invoking `super.addCollisions` for each strip.

For a bounded all-air-neighbor case, all four booleans are false. Both conditional expressions select their final `else`, so the old provider emits both strips: an east-west strip spanning x=[0,1], z=[7/16,9/16], and a north-south strip spanning x=[7/16,9/16], z=[0,1], both y=[0,1]. The resulting union is a full-height cross with no central-post-only shape. The base collision collector filters each emitted box against the supplied query; this comparison assumes a query containing the whole unit cell, so both strips are eligible.

## Current collision provider

In 26.2, `IronBarsBlock` extends `CrossCollisionBlock` and constructs it with post width 2/16 and full collision height 16/16. `CrossCollisionBlock.makeShapes` begins every state’s collision shape with the central post, then unions arms for each true directional property. Thus the mapped all-false iron-bars state has only the central post (x,z=[7/16,9/16]), not the historical all-air cross. The current collision shape is state-based; its `getCollisionShape` returns the precomputed state shape. Current connection calculation uses `IronBarsBlock.attachsTo` and face-sturdiness/wall/bar rules, which are not the old `shouldConnectTo` predicate.

## Bounded conclusion and remaining dependency

For old ID 101 with air in all four adjacent cells and a query covering the unit cell, the source-defined collision geometry differs: old emits the two crossed strips; mapped current state emits only the center post. This closes the ID-101 identity/default-state and isolated geometry matrix entry only. General historical connectivity still requires a source-backed adapter for the old neighbor predicate and the relevant adjacent-block identity/state families. Do not infer those families from glass panes or stained glass panes, and do not treat this isolated comparison as a complete movement or DDA result.
