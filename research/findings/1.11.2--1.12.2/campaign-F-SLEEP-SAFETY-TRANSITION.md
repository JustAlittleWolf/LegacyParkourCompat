# F-SLEEP-SAFETY-TRANSITION: non-angry zombie pigmen no longer block the sleep movement transition

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1194-1249.
- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1185-1243.

## Difference

Under the same server-side preconditions—living, awake player; natural dimension; non-sunny time; bed in range; and only a non-angry zombie pigman in the 8-by-5-by-8 query box—1.11.2 includes the pigman in the monster list and returns `NOT_SAFE`. 1.12.2 filters the list to monsters angry at this player; `ZombiePigmanEntity.isAngryAt` returns false when `isAngry()` is false, so the list is empty and the method proceeds.

After the gate, both versions stop riding if needed, call `setSize(0.2F, 0.2F)`, move the player to the bed position, set the sleeping state and reset all three velocity components to `0.0`.

Thus 1.12.2 performs those player movement-state writes in this condition while 1.11.2 returns before them.

The server-player override then teleports and sends the sleep packet only when the result is `OK`.

## Movement meaning

Bed interaction calls `PlayerEntity.trySleep`.

On the server this dispatches to `ServerPlayerEntity.trySleep`, which delegates to the changed base eligibility check. 1.12.2's filter uses the MonsterEntity default (`true`) and the ZombiePigman override (`isAngry()`); the other MonsterEntity subclasses found in the exact source scan use the default.

The changed return value gates player unmounting, dimensions, position, sleeping flag, velocity reset and server teleport/sleep synchronization.

Source proves the different sleep result and the resulting server-side player size, position and velocity writes under the stated preconditions.

It does not establish a measured trajectory.

The client handler replays sleep locally, while the server teleport/correction path remains in S1.7 for full packet-authority closure. 1.12.2 also drops shoulder entities before resizing; that changes non-player entities and is outside this finding's player movement consequence.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-SLEEP-SAFETY-TRANSITION.md).
