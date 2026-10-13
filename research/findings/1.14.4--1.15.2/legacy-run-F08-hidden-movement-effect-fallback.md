# F08: 1.15.2 restores a hidden weaker movement effect

- **1.14.4**, mojmap: `MobEffectInstance.java`; lines 54–101.
- **1.15.2**, mojmap: `MobEffectInstance.java`; lines 66–102.

## Difference

If a shorter, stronger Speed or Slowness instance replaces a weaker instance with more remaining duration, 1.15.2 keeps the weaker instance and counts its remaining duration down behind the active one.

When the stronger instance expires, 1.15.2 restores the weaker amplifier and refreshes the movement-speed attribute modifier immediately. 1.14.4 discards the weaker instance, so expiry removes the movement-speed modifier entirely.

This changes the player's movement speed after the stronger effect ends.

## Movement meaning

Effect application or renewal -> `MobEffectInstance.update` -> `LivingEntity.addEffect` -> active effect tick -> modifier update or removal -> player movement-speed attribute.

The finding is limited to Speed and Slowness attribute effects and their stacking lifecycle.

The source difference is explicit and player-reachable.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F08-hidden-movement-effect-fallback.md).
