# F-SWIM-POSE — 1.13.2 adds swimming pose and dimensions

- **1.12.2**, ornithe-feather: `net/minecraft/entity/living/player/PlayerEntity.java`; lines 291-315.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SWIM-POSE.md); lines 334-361.

## Difference

1.13.2 has swimming pose handling and assigns compact dimensions (width 0.6, height 0.6) after a world collision-fit check; 1.12.2 has no swimming pose branch.

The 1.13.2 eye-height method returns 0.4F while swimming; 1.12.2 has no swimming predicate in that method.

PlayerEntity.updateSwimming also suppresses swimming while flying in 1.13.2.

The spin-attack branch shares compact dimensions but is a separate modern-only trigger.

## Movement meaning

- Reachability and dependencies: swimming transition is sourced in Entity base tick and state is consulted by PlayerEntity pose update.

Dimension changes affect getShape, eye-height and collision consumers.

Do not treat this file as a closed collision finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SWIM-POSE.md).
