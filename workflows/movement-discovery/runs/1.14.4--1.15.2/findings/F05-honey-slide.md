# F05: Honey Block contact limits descent velocity and clears fall distance

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 4–5; collision contact callbacks and descending motion
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic (Honey Block is registered in B and absent from A)
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A's `Blocks` registry has no Honey Block registration and the A source tree has no `HoneyBlock.java`; A `Blocks.java` SHA-256 `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9`.
- B `HoneyBlock.getCollisionShape(...)` returns a box from `(1, 0, 1)` through `(15, 15, 15)` (lines 22–36); `entityInside(...)` calls slide handling when `isSlidingDown(...)` succeeds (lines 50–57). B `HoneyBlock.java` SHA-256: `40760AEB3C084F1143E87E1E057F18165492EB01B8FCE0815DFDD0882CDC8A03`.
- B `isSlidingDown(...)` requires airborne state, a Y bound relative to the block, downward velocity below `-0.08`, and an X or Z edge-distance predicate at lines 60–77. `doSlideMovement(...)` at lines 85–95 sets vertical velocity to `-0.05`; when previous vertical velocity is below `-0.13`, it also scales X/Z by `-0.05 / velocityY`. It then sets `fallDistance` to `0.0F`.
- B `Entity.move(...)` calls `checkInsideBlocks()` after collision movement at lines 533–535; that method dispatches `BlockState.entityInside(...)` over cells intersecting the entity box at lines 791–824. `BlockState.entityInside(...)` delegates to its block at lines 248–249. B `Entity.java` SHA-256: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`; B `BlockState.java`: `77320FD1E50E58D7A3ED593CCBCF21853C97D125FCCEBDE2E0E305DC47524F21`.

## Source-level difference

When a player is falling beside the Honey Block and satisfies its explicit height, velocity and edge predicates, B replaces vertical velocity with `-0.05`. For descent faster than `-0.13`, B also scales horizontal velocity by `-0.05 / velocityY`; it resets fall distance. A has no Honey Block or corresponding callback.

## Reachability and dependencies

Local player travel -> `Entity.move` -> bounding-box block-cell scan -> `BlockState.entityInside` -> `HoneyBlock.entityInside` -> slide predicates and delta-velocity writes. The block shape and registration are present only in B. The method applies to `Entity`; Honey Block's player movement effect is established by the player reaching the shared movement/collision path.

## Consequence and uncertainty

Source proves the guarded velocity and fall-distance writes. It does not establish an observed trajectory, and the feature is modern-only because A maps cannot contain Honey Block.

## Handoff

Independent Honey Block side-contact descent mechanic; related to F03 speed factor and F04 jump factor. Exact first release remains unknown within the endpoint interval.
