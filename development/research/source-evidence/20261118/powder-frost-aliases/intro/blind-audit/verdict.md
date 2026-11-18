# Final claim review verdict

## Bound inputs

- Author claim: source/comparison.md, raw SHA-256 eca40f08758e536a9c796eab81edadca20df46aa718f872487b83f104c2ed7b4.
- Independent blind freeze: frozen.md, raw SHA-256 eff0b24d516945677a0dc1aea7838afc96f3f2a04dee3c3f342e155bd968148e.

## Verdict: accepted within the frozen support boundary

The comparison’s endpoint claim is consistent with the frozen audit: powder snow and frozen-tick support are absent in the 1.16.5 Mojmap source and present in 1.17; the 1.17 LivingEntity frost modifier is called after travel, requires non-air support, positive frozen ticks, and an available movement-speed attribute, and adds -0.05F times the frozen proportion. The local-player inheritance chain reaches this post-travel method. Player.getSpeed reads the movement-speed attribute directly, so the modifier is an input to a subsequent grounded ordinary travel. The travel and grounded-speed guards, including the airborne flyingSpeed branch, are correctly bounded. The author does not assert a trajectory, exact intermediate introduction patch, or runtime parity.

## Residual review scope

The author comparison adds claims beyond the frozen audit’s declared scope: detailed PowderSnowBlock.entityInside effects, per-tick powder-snow flag clearing/production, server-side frozen-tick progression and client replication timing, and the LocalPlayer.tick → Player.tick → LivingEntity.tick invocation chain. The freeze did not independently accept those claims; this verdict does not bind them. The final comparison’s own hashes and source identity are accepted only as supplied in the frozen author artifact, not revalidated in this review.

No additional residual exists for the requested historical presence/introduction boundary or the bounded next-ground-travel consumer.

