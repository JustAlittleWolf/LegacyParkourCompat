# 1.13.2 prevents double-jump flight toggle while swimming

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 747-761.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 760-775.

## Difference

With flight permission, a prior jump press registered, a new jump press, and no auto-jump override, 1.12.2 toggles flight. 1.13.2 toggles flight only when `isSwimming()` is false.

While swimming, 1.13.2 leaves the ability and double-press timer untouched by this branch.

This is separate from the earlier flight-toggle setup press, which still assigns the timer.

## Movement meaning

The local player processes jump input in `mobTick()`. 1.13.2's swimming flag is set from sprint and water/submersion state, with PlayerEntity filtering for flight/spectator.

Thus the new guard applies only during 1.13.2 swimming; it does not prevent flight-toggle behavior in all water contact or for spectators/flying players.

Exact swimming-state transitions remain covered by the water sprint and stage 2 dependencies.

Source proves 1.13.2 suppresses the flight-state toggle and its sync/reset writes while swimming.

It predicts different vertical flight movement after the attempted second jump.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/flight-toggle-swimming.md).
