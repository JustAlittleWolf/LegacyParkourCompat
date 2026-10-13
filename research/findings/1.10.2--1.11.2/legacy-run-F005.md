# F005: Jump and movement-distance exhaustion costs change

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1380–1388.
- **1.11.2**, ornithe-feather: `PlayerEntity.java`; lines 1376–1384.

## Difference

The player exhaustion amounts associated with jumping and movement-distance categories differ.

## Movement meaning

Player movement/jump stats -> `addFatigue(float)` -> HungerManager.addExhaustion.

Both `PlayerEntity.addFatigue` implementations only mutate hunger when `!world.isClient`; HungerManager consumes exhaustion above 4.0F.

LocalClientPlayerEntity sprint gating reads synchronized food level (`foodLevel > 6.0F` in inspected path); ClientPlayNetworkHandler applies UpdateHealth food values.

Thus this is a server-authoritative movement-related state input, not a claim of client-side exhaustion simulation.

Different exhaustion accumulation can change later hunger depletion and therefore the local sprint gate after server updates.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/findings/F005.md).
