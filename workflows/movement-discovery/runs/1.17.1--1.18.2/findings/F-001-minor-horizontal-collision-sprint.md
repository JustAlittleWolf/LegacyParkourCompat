# F-001: Minor horizontal collisions no longer stop local sprinting

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: stage-1/sprint-stop; dependency stage-4/horizontal-collision-classification
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A source root: `decompiled_minecraft/1.17.1/mojmap/`. `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer.aiStep()`, line 717 (file SHA-256 `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812`): `boolean bl7 = bl6 || this.horizontalCollision || this.isInWater() && !this.isUnderWater();` The stop branch at lines 718–724 calls `setSprinting(false)` when `bl7` is true (except its separate swimming branch).
- B source root: `decompiled_minecraft/1.18.2/mojmap/`. `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer.aiStep()`, line 710 (file SHA-256 `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`): `boolean $$6 = $$5 || this.horizontalCollision && !this.minorHorizontalCollision || this.isInWater() && !this.isUnderWater();` The same stop branch follows at lines 711–717.
- B flag producer: `net/minecraft/world/entity/Entity.java`, `Entity.move()`, lines 581–587: after collision resolution, sets `horizontalCollision`, computes `minorHorizontalCollision = isHorizontalCollisionMinor(resolvedMovement)` when horizontal collision occurred, else clears it. File SHA-256 `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`.
- B local-player classifier: `net/minecraft/client/player/LocalPlayer.java`, `isHorizontalCollisionMinor(Vec3)`, lines 999–1013. It rotates input impulses into world-relative intent, compares that horizontal vector with the resolved displacement, and returns true when their angle is below `0.13962634F` radians. Same LocalPlayer source hash above.
- A checked absence: A `Entity.move()` at lines 534–561 sets only `horizontalCollision` from requested-versus-resolved X/Z (`Entity.java` SHA-256 `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE`); its `Entity.java` has no `minorHorizontalCollision` field or classifier. A LocalPlayer has no override of `isHorizontalCollisionMinor`.

## Source-level difference

For a sprinting, non-swimming local player with forward impulse and food/flight gates satisfied, when movement produces a horizontal collision but `isHorizontalCollisionMinor` classifies its direction change as minor, B leaves the horizontal-collision term false for the sprint-stop test. A treats any horizontal collision as a stop condition. The other stop predicates remain in place. A’s swimming branch uses its own predicate, so this finding concerns the non-swimming branch.

The B classification compares intended and resolved horizontal directions and uses a strict angle threshold `< 0.13962634F`; the classification arithmetic is part of the queued stage-4 slice and must retain its float/double operations when further audited.

## Reachability and dependencies

Local-player `aiStep()` reads the collision fields and may clear sprint. `Entity.move()` writes `horizontalCollision` and, in B, calls the virtual `isHorizontalCollisionMinor`; B’s `LocalPlayer` override derives minor status from input impulses, yaw, resolved displacement and `Mth`/trigonometric helpers. The exact collision query and resolved-displacement behavior are stage-4 dependencies. Movement consequence is inferred from the source branch; no trajectory was tested.

## Consequence and uncertainty

Source proves that B can retain sprint under the stated minor-collision precondition where A clears it. Whether a particular geometry/input produces that precondition depends on the collision resolver and is not demonstrated here. Endpoint evidence does not locate the exact introducing release.

## Handoff

Historical delta: B exempts classified minor horizontal collisions from the local sprint-stop condition. Related dependency: stage-4 collision classification and axis-resolved movement. Implementation and gameplay testing are deferred.
