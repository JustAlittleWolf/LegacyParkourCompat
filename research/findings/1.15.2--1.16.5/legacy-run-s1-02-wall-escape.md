# S1-02: Change local-player suffocation escape probes

- **1.15.2**, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 650–655.
- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 656–661.

## Difference

1.15.2 tests whether each of four sampled block columns is suffocating across the entity's vertical range. 1.16.5 checks each sampled block against suffocating collision shapes intersecting a horizontal slice of the full bounding box.

Both choose the nearest unblocked cardinal neighbor in west/east/north/south order and set the selected horizontal velocity component to magnitude `0.1`; the occupancy query is different.

## Movement meaning

This runs from `LocalPlayer.aiStep()` while `noPhysics` is false, before the parent living movement step.

The chosen delta movement is then consumed by normal player movement. 1.15.2's dependency is `Player.freeAt` and the suffocating predicate. 1.16.5's dependency is `CollisionGetter.noBlockCollision` over the deflated AABB with the same suffocating predicate.

1.15.2 concrete differing precondition is a suffocating neighboring block that intersects the player's bounding-box edge but is outside the four sampled x/z block columns. 1.16.5's broader AABB query can consider that cell while 1.15.2's column checks cannot.

The source proves different probe geometry; it does not establish which exact legacy block layouts yield a different selected direction in every case.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s1-02-wall-escape.md).
