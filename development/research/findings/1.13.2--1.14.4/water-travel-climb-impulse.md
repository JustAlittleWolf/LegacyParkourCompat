# Water-travel climb impulse

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1617–1655.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1882–1928.

Difference: In 1.13.2's water branch, movement is followed by velocity damping and descent handling; there is no `isClimbing()` impulse in this branch. 1.14.4, after `move`, checks `collidingHorizontally && isClimbing()` and replaces the vertical component with `0.2` before applying water drag/gravity.

Movement meaning: LivingEntity water travel -> Entity.move collision result -> `collidingHorizontally` and `isClimbing` -> vertical velocity.

## Exact release boundary and climbable states

Ornithe Feather `LivingEntity.moveRelative` omits the post-move climb predicate in the water branch in exact 1.12.2, 1.13, 1.13.1 and 1.13.2. Exact 1.14 (`LivingEntity.java:1897–1920`) introduces `collidingHorizontally && isClimbing()` after the move and assigns the double `0.2` to velocity Y before multiplication by `0.8F`. The same assignment appears in 1.14.1–1.14.4. The later `0.3F` clearance response is separate and remains in both paths. Ordinary players reach the branch through superclass travel outside ability flying.

The feet-cell predicate samples ladder, vine, or an open trapdoor with a ladder directly below and the same facing. In the 1.13.2 and 1.14 sources, a waterlogged open trapdoor above a matching ladder supplies a climbable state during water travel. A separate horizontal obstacle can supply collision, and a low ceiling can prevent the later clearance response. Scaffolding is additionally climbable in 1.14 and is absent in 1.13.2. The verified boundary concerns the post-collision assignment for ordinary water travel. Marginal water admission and subsequent trajectories remain open.

[Exact endpoint finding](../../source-evidence/20261117/block-a-water-climb/water-travel-climbable-impulse.md), [source comparison](../../source-evidence/20261117/block-a-water-climb/comparison.md), [independent review and verified source identities](../../source-evidence/20261117/block-a-water-climb/verdict.md).
