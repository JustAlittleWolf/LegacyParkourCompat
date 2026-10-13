# F-WATER-STATE — 1.13.2 adds submerged-depth and swimming state to player movement

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 944-946.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-STATE.md); lines 961-975.

## Difference

1.13.2 adds a sampled fluid depth and a swimming flag transition.

Already-swimming transition uses sprint + contact with water + not riding; entry uses sprint + submerged-in-water + not riding.

PlayerEntity suppresses swimming while flying. 1.12.2 has no submerged-depth/swimming state.

## Movement meaning

- Reachability and dependencies: 1.13.2 base-tick water update runs before LivingEntity.mobTick input/jump/travel route.

State is consumed by local sprint/flight gates, PlayerEntity pose/moveRelative, and LivingEntity water jump decisions.

Full Entity/Player tick order, fluid grid boundaries and resource-data closure remain pending.

Source difference does not establish exact gameplay trajectories.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-STATE.md).
