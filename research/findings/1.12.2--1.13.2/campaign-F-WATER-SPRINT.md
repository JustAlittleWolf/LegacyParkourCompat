# F-WATER-SPRINT — 1.13.2 changes local sprint eligibility in water

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 694-745.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md); lines 698-758.

## Difference

1.12.2 double-tap sprint start requires `onGround`. 1.13.2 permits `(onGround || isSubmergedInWater())`; its held-sprint gate additionally requires not-in-water or submerged. 1.13.2 has separate sprint-stop conditions when swimming versus ordinary sprinting, including cancellation in water when not submerged.

## Movement meaning

- Reachability and dependencies: identical keyboard input feeds LocalClientPlayerEntity.mobTick; 1.13.2's water/submerged/swimming states are produced by the Entity/PlayerEntity tick path documented in F-WATER-STATE.

Food level is read only as sprint eligibility; food simulation is excluded.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md).
