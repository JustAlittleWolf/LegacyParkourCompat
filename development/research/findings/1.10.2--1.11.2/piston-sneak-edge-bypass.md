# Piston movement bypasses sneak edge restraint

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 446-725.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities() {`, lines 91-150.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(MoverType moverType, double x, double y, double z) {`, lines 459-790.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities(float progress) {`, lines 116-169.

Difference: For an otherwise eligible grounded sneaking player, 1.10.2 always runs the horizontal support probes before movement. 1.11.2 runs them only for SELF and PLAYER mover types.

The reachable piston displacement caller in 1.11.2 uses PISTON, so it does not enter the restraint block.

F-01 separately records the changed probe depth when the block does run; F-02 records the independent piston cumulative cap.

Movement meaning: The player can intersect a moving piston entity area; 1.11.2 calls `Entity.move(PISTON,...)`.

The player's grounded/sneaking state controls 1.10.2's guard and remains checked in 1.11.2, subject to the additional mover-type gate.

Source proves that 1.11.2 skips the edge-restraint probe for piston-supplied player movement.

This may retain horizontal piston displacement that 1.10.2 would reduce near unsupported edges.
