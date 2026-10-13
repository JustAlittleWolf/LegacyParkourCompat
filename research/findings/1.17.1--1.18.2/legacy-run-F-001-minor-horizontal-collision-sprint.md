# F-001: Minor horizontal collisions no longer stop local sprinting

- **1.17.1**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; line 717.
- **1.18.2**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; line 710.

## Difference

For a sprinting, non-swimming local player with forward impulse and food/flight gates satisfied, when movement produces a horizontal collision but `isHorizontalCollisionMinor` classifies its direction change as minor, 1.18.2 leaves the horizontal-collision term false for the sprint-stop test. 1.17.1 treats any horizontal collision as a stop condition.

The other stop predicates remain in place. 1.17.1’s swimming branch uses its own predicate, so this finding concerns the non-swimming branch.

The 1.18.2 classification compares intended and resolved horizontal directions and uses a strict angle threshold `< 0.13962634F`; the classification arithmetic is part of the queued stage-4 slice and must retain its float/double operations when further audited.

## Movement meaning

Local-player `aiStep()` reads the collision fields and may clear sprint. `Entity.move()` writes `horizontalCollision` and, in 1.18.2, calls the virtual `isHorizontalCollisionMinor`; 1.18.2’s `LocalPlayer` override derives minor status from input impulses, yaw, resolved displacement and `Mth`/trigonometric helpers.

The exact collision query and resolved-displacement behavior are stage-4 dependencies.

Source proves that 1.18.2 can retain sprint under the stated minor-collision precondition where 1.17.1 clears it.

Whether a particular geometry/input produces that precondition depends on the collision resolver and is not demonstrated here.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md).
