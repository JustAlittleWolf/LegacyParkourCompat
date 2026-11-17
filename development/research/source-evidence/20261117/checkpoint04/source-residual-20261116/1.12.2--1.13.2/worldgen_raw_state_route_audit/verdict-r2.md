# Independent audit: native structure raw-state route r2

## Verdict

**ACCEPT** for the narrow r2 caller-binding correction in `worldgen_raw_state_route/coverage-r2.md` (SHA-256 `fcbc87e212db1a66a79185a57de7f56ecdc22d6857f320443e05c7fc83ccb8af`). It binds the unchanged r1 report (SHA-256 `5322f1d26d3688f464f8cb55a4f60101d2acdd040f22f6a98e170f3e0448930a`) and prior audit verdict (SHA-256 `840c1d595d02854146ca94c493e814600127d3ee3ae02ae991f15874c6f0048d`).

## Correction checked

The only substantive addition is a concrete 1.12.2 native caller: `OverworldChunkGenerator.populateChunkAfterTerrain` invokes `oceanMonument.place(this.world, this.random, new ChunkPos(...))` when its structure gates pass (lines 407–411). `OverworldChunkGenerator.java` is SHA-256 `96df9a1b15529baa5f4450d29fff50004881ae45a304029d7ed30b1ddcae400f`; current bytes match the 1.12.2 source-manifest entry. The r2 diff adds this callsite sentence and the matching source-identity row; all other r1 text is unchanged.

This supplies a native caller for the old `StructureFeature.place(World, Random, ChunkPos)` → `StructureStart.postProcess` route already verified in r1. It does not alter the setter/flag findings or broaden the conclusion beyond native structure-piece placement. External imports, edited worlds, save migration, geometry, movement, and pair-freeze claims remain outside scope.
