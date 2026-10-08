# F-010: Dismount candidates wholly beyond the world border are rejected in B

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-MOUNT-INPUT, T-PLAYER-CORRECTION, INV-EXTERNAL, INV-STATE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap`; `net/minecraft/world/entity/vehicle/DismountHelper.java`, `DismountHelper#canDismountTo(CollisionGetter, LivingEntity, AABB)`, lines 41-43, SHA-256 `a7e0b00886828ea8b3acacde3602d6849c7e4f33a3d33b1ccd0f27ebc1a306b5`; and `#findSafeDismountLocation(EntityType, CollisionGetter, BlockPos, boolean)`, lines 74-89, same hash. The first returns only the block-collision emptiness predicate; the second returns the candidate after the same block-collision check.
- B artifact: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap`; `net/minecraft/world/entity/vehicle/DismountHelper.java`, `#canDismountTo`, lines 41-49, SHA-256 `18cde6b3f0d4297134989620b654a508fb30fe8bf07a2f44d656446ebca99997`; and `#findSafeDismountLocation`, lines 80-104, same hash. Both now require `CollisionGetter#getWorldBorder().isWithinBounds(candidateAABB)` after block-collision checks.
- Border predicate: A `net/minecraft/world/level/border/WorldBorder.java#isWithinBounds(AABB)`, lines 48-50, SHA-256 `b848dd0dc6043d6c9012726844bea53cf4558c0135a08fa3be55960b0e557df9`; B same member, lines 50-52, SHA-256 `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`. Its strict horizontal comparisons accept an AABB when it overlaps the interior in both X and Z; it does not require the full box to be contained.
- Player wake caller: A `LivingEntity#stopSleeping`, lines 3154-3174, `LivingEntity.java` SHA-256 `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; B lines 3141-3161, SHA-256 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`. Both obtain the player stand-up position through `BedBlock#findStandUpPosition`, then call `setPos` with that result.
- Vehicle dismount caller: A/B `LivingEntity#dismountVehicle` (A lines 1948-1959; B lines 1954-1965, same LivingEntity hashes above) delegates to `Entity#getDismountLocationForPassenger` and then `dismountTo`; the paired boat, minecart, pig, horse and strider providers call `DismountHelper#canDismountTo` on candidate player poses. A/B provider hashes are recorded in the run artifact addendum.
- Candidate collision boundary: both versions test block candidates using `CollisionGetter#getBlockCollisions`; A has no world-border test after that query. B's new border guard is explicit in the helper. `getBlockCollisions` does not add the world-border shape; border collision is a separate `CollisionGetter` path recorded in T-ENTITY-COLLISION.

## Source-level difference

For equal block-collision inputs, A accepts a dismount AABB when no block collision shape intersects it. B additionally rejects it when the AABB lies wholly outside the world-border interior along either horizontal axis. An AABB that still overlaps the interior passes B's `isWithinBounds` predicate.

## Reachability and dependencies

The player wake path is `LivingEntity#stopSleeping` -> `BedBlock#findStandUpPosition` -> ordered safe-candidate search -> `DismountHelper#findSafeDismountLocation` -> `LivingEntity#setPos`. Vehicle exits follow `LivingEntity#stopRiding` -> `dismountVehicle` -> the active boat/minecart/pig/horse/strider provider -> `DismountHelper#canDismountTo` -> `dismountTo`. Both paths directly select a player position from collision-tested candidates; no vehicle motion simulation is needed for this finding. The respawn-position helper also uses `findSafeDismountLocation` for player bed/anchor candidates, but respawn trajectory and gameplay are not claimed here.

## Consequence and uncertainty

Source-proven: if an ordered candidate is free of block collisions but wholly outside the border, A accepts it and B rejects it. Where another candidate is also valid, the selected wake/dismount position can differ; if no candidate remains, the caller's fallback may differ from A's previously accepted candidate. This records the candidate-gate delta, not an observed position or a runtime reproduction. Border overlap, block collision and candidate order remain the determining inputs.

## Handoff

Independent delta: the shared safe-dismount candidate helpers add an explicit world-border admission check in 1.18.2. Related border movement findings F-004, F-008 and F-009 concern auto-jump, crouch edge support and the movement collision solver respectively; this finding concerns position selection during wake/dismount. No implementation or runtime disposition is assigned.
