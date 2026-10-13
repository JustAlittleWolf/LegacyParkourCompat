# S3-02: Fluid jump input is gated and shallow lava can trigger a ground jump

- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s3-02-fluid-jump-gates.md); lines 2266–2278.
- **1.16.5**, mojmap: `LivingEntity.java`; lines 2427–2451.

## Difference

1.16.5 suppresses this liquid-jump branch while the player is ability-flying.

In addition, shallow grounded lava at or below the fluid jump threshold uses the ordinary ground jump in 1.16.5, while 1.15.2's in-lava branch adds only `0.04F` vertical velocity through `jumpInLiquid`.

## Movement meaning

The local/server player jump state reaches the living AI-step branch. 1.15.2 flying player pressing jump in water or lava reaches 1.15.2's liquid jump branch but is rejected by 1.16.5's player override. 1.15.2 nonflying grounded player jumping in shallow lava reaches 1.16.5's `jumpFromGround` branch.

The branch changes vertical impulse and, in shallow lava, jump-delay state.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s3-02-fluid-jump-gates.md).
