# Sneak edge restraint probes at step height

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 446-725.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(MoverType moverType, double x, double y, double z) {`, lines 459-790.

Difference: The vertical offset used by each support query changes from a fixed one block to the moving entity's `stepHeight`.

The local-player inheritance chain reaches `LivingEntity` and has no later step-height writer, so the default local player changes this probe from `-1.0` to `-0.6`.

Probe order and 0.05 adjustment loop remain the same in the inspected bodies.

Movement meaning: The local client player's `mobTick` samples sneak input; the living travel path calls `Entity.move`; the method guards on grounded, sneaking, and player identity before probing. 1.11.2 also gates this probe by mover type; the separately scoped PISTON bypass is F-03.

Source proves that 1.11.2 samples a different vertical volume for the support query when `stepHeight != 1.0`.

It may change whether the loop considers the player supported and how much requested horizontal displacement is retained.
