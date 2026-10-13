# MD-03: Releasing sneak changes the sprint-start gate

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java`; lines 141-171.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 535-564.

## Difference

Precondition: the player was sneaking on the preceding local tick, is now releasing sneak while holding forward on ground, and the other sprint guards pass. 1.7.10's old forward value was sneak-scaled below `0.8F`, so its old-forward transition guard is false; after the input tick restores forward to at least `0.8F`, 1.7.10 can arm or complete the double-tap sprint path. 1.8.9 also sees the restored forward value, but the saved old-sneak flag blocks that path for this tick.

Its separate held-sprint-key path remains available.

The same threshold `0.8F`, input tick order, and sneak scaling are used on both sides; the additional saved-sneak predicate is the behavioral delta.

## Movement meaning

The predicate changes the sprint state and associated movement speed only when the stated transition and remaining gates occur.

Hunger/flight permission, item-use slowdown, blindness and existing sprint timer are guard inputs; the server may synchronize sprint state.

Source-proven: after a preceding sneaking input, 1.8.9 blocks the double-tap sprint gate on the release tick; 1.7.10 does not.

The held sprint-key branch is separate.

Inference: this can delay or suppress sprint activation, changing subsequent movement acceleration.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-03-sneak-release-sprint-gate.md).
