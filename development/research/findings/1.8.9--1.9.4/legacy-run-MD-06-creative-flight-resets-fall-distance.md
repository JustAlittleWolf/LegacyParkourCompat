# MD-06: Creative flight resets accumulated fall distance

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1279-1295.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1372-1390.

Difference: Each 1.9.4 creative-flight movement tick clears accumulated fall distance after the shared movement calculation. 1.8.9 preserves it.

This is separate from the previously cataloged flight-plus-sneaking input rescaling (MD-02).

Movement meaning: The wrapper branch is gated by `abilities.flying` and the player not riding.

The local-player tick toggles that ability for players with `canFly`; both versions expose the flight ability and input path.

The flag-7 clear is the newer fall-flying state and is modern-only, but the fall-distance reset runs on the reachable legacy creative-flight path as well.

Source confirms a different `fallDistance` state after flight movement. 1.8.9 later fall-damage-eligible state could therefore observe a different accumulated distance if flight permission is removed before landing.

While `canFly` remains true, player fall damage is suppressed.

No claim is made about a specific damage event or trajectory, and none was tested.

Original evidence: [MD-06-creative-flight-resets-fall-distance.md](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-06-creative-flight-resets-fall-distance.md).
