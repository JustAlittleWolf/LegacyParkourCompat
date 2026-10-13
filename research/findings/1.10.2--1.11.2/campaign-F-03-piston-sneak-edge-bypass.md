# F-03: Piston movement bypasses sneak edge restraint

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 468-472.
- **1.11.2**, ornithe-feather: `net.minecraft.entity.Entity#move(MoverType,double,double,double)`; lines 519-523.

## Difference

For an otherwise eligible grounded sneaking player, 1.10.2 always runs the horizontal support probes before movement. 1.11.2 runs them only for SELF and PLAYER mover types.

The reachable piston displacement caller in 1.11.2 uses PISTON, so it does not enter the restraint block.

F-01 separately records the changed probe depth when the block does run; F-02 records the independent piston cumulative cap.

## Movement meaning

The player can intersect a moving piston entity area; 1.11.2 calls `Entity.move(PISTON,...)`.

The player's grounded/sneaking state controls 1.10.2's guard and remains checked in 1.11.2, subject to the additional mover-type gate.

Source proves that 1.11.2 skips the edge-restraint probe for piston-supplied player movement.

This may retain horizontal piston displacement that 1.10.2 would reduce near unsupported edges.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-03-piston-sneak-edge-bypass.md).
