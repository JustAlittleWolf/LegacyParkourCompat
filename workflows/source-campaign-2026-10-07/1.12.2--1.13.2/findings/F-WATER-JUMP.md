# F-WATER-JUMP — 1.13.2 selects jumps by fluid depth

- Status: source-confirmed; fluid producer closure pending
- A source: `net/minecraft/entity/living/LivingEntity.java`, mobTick lines 1827-1839; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.
- B source: same class, mobTick lines 1903-1917 and tag jump helper; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. Mapped jar confirms helper descriptor `(Lnet/minecraft/tag/Tag;)V`.
- Difference: A applies the water or lava jump impulse whenever `isInWater()` or `isInLava()` is true, otherwise a grounded jump is allowed when cooldown is zero. B uses sampled fluid depth: above 0.4 routes to water impulse; shallow positive depth through 0.4 allows a grounded ordinary jump when cooldown is zero; lava is checked in the alternate branch.
- Reachability/dependencies: local input copies jumping into the LivingEntity field; B's fluid-depth field is sampled by Entity's fluid-grid calculation before LivingEntity.mobTick. Exact source-to-bytecode mapping for the raw sampler helper is still under review.
- Direct consequence: jump selection differs by water height, including shallow-water ground jumping in B.
- Limits: requires completion of fluid-state/grid/flow and tick-order inventories; no runtime inference is made.
