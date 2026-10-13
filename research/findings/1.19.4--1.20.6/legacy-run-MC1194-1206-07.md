# MC1194-1206-07: Synchronized step-height attribute changes step-up reach

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 3342-3345.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 3490-3493.

## Difference

At 1.20.6's default `STEP_HEIGHT` value `0.6`, the ordinary player step limit matches the 1.19.4 base limit. 1.19.4 nondefault 1.20.6 attribute value changes `LivingEntity.maxUpStep()` and therefore the vertical candidate in `Entity.collide`; the same ordered candidate and tie-break algorithm then operates over a different height. 1.19.4's player does not read an equivalent attribute.

## Movement meaning

The client player inherits `LivingEntity.maxUpStep()` and `Entity.move()` calls `Entity.collide()` on its movement path.

The passenger-control minimum of `1.0F` can mask lower attribute values for a ridden entity and is retained in both versions.

Source-proven: 1.20.6 can use a step-up limit other than 1.19.4's base player limit when the synchronized attribute differs from `0.6`.

This attribute is modern-only; do not project it into 1.19.4-era behavior.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-07.md).
