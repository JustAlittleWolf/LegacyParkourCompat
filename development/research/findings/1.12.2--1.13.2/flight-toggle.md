# F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: when flight permission exists, the second jump edge toggles `abilities.flying` in 1.12.2; 1.13.2 adds `else if (!isSwimming())` before toggling.

The first press timer and spectator auto-flight route remain separate guards.

Movement meaning: - Reachability and dependencies: local-player input edge reaches the branch when not spectator, able to fly, and jumping transitions false-to-true without autojump. 1.13.2 swimming state producer is F-WATER-STATE.
