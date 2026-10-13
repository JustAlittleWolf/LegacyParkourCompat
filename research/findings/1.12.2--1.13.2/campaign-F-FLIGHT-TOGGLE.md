# F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 747-761.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-FLIGHT-TOGGLE.md); lines 760-775.

## Difference

when flight permission exists, the second jump edge toggles `abilities.flying` in 1.12.2; 1.13.2 adds `else if (!isSwimming())` before toggling.

The first press timer and spectator auto-flight route remain separate guards.

## Movement meaning

- Reachability and dependencies: local-player input edge reaches the branch when not spectator, able to fly, and jumping transitions false-to-true without autojump. 1.13.2 swimming state producer is F-WATER-STATE.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-FLIGHT-TOGGLE.md).
