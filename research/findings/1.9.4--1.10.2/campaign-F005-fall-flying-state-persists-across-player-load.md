# F005: 1.10.2 persists the player's fall-flying movement state

- **1.9.4**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 425-457.
- **1.10.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F005-fall-flying-state-persists-across-player-load.md); lines 427-461.

## Difference

1.10.2 writes the current fall-flying flag as a `FallFlying` player/entity NBT boolean and restores flag 7 when that boolean is true. 1.9.4's corresponding `LivingEntity` save/load methods do not write or read that key, and its default shared flag byte is zero.

The normal player persistence path invokes these virtual living-entity methods in both releases.

## Movement meaning

For a 1.10.2-saved player record containing `FallFlying=true`, player loading restores the shared flag before the next movement tick.

If the player remains off-ground and not riding with a fly-capable chest-slot Elytra, 1.10.2's `flyingTick()` retains flag 7 and the following `moveRelative()` call selects its fall-flying movement branch.

Loading the same tag through 1.9.4's source path leaves its initialized flag clear, so the ordinary movement branch is selected.

This route uses NBT-backed player state, server player persistence, synchronized flag state and vanilla Elytra equipment/durability checks; no data resource supplies the flag or a glide coefficient.

The source proves that 1.10.2 preserves this movement mode across player-data save/load and 1.9.4 does not.

Under the stated preconditions, the later movement branch differs.

An invalid or missing Elytra, grounded state, or riding state causes 1.10.2's existing `flyingTick()` guard to clear the flag before the glide branch.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F005-fall-flying-state-persists-across-player-load.md).
