# F002: Generic no-gravity state gates fluid travel gravity

- **1.9.4**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1302-1470.
- **1.10.2**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1332-1506.

## Difference

1.10.2 introduces a generic entity no-gravity flag with default false and gates the `LivingEntity` water/lava `-0.02` and ordinary-branch `-0.08` vertical gravity decrements on that flag.

The unloaded-client fallback remains the separate `y > 0 ? -0.1 : 0.0` assignment.

Under the false default, the new gates do not suppress the subtractions.

When saved player NBT sets the flag true, the server syncs it back to that player's client entity; 1.10.2 skips the gated fluid/ordinary gravity subtractions, while 1.9.4 has no corresponding generic player flag.

## Movement meaning

Player `LivingEntity` travel reaches `moveRelative` through the local-player → player → living-entity call path.

The 1.10.2 saved-player NBT, dirty-data broadcast to the owning `ServerPlayerEntity`, and client packet application close the player reachability path for saved `NoGravity=true` state.

Normal default is false.

Pair revision hashes and source/raw inputs were independently verified; the original derived jars are unavailable and their equivalence remains unproven.

Source and bytecode show that 1.10.2 omits these vertical gravity decrements when the synced player flag is true.

The saved-data and network path is source-confirmed.

Exact trajectory consequences depend on fluid state and movement order and are not claimed.

Revised artifact identity is explicitly limited because the original derived jars are unavailable.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F002-no-gravity-fluid-travel.md).
