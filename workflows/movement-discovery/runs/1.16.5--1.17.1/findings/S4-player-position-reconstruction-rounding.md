# Entity movement reconstructs horizontal position in a different order

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S4-01-PLAYER-POSITION-RECONSTRUCTION`, `S4-ENTITY-COLLISION`
- Classification: position arithmetic order change
- Confidence: source-confirmed
- Applicability: player movement, including the no-physics branch
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A `world/entity/Entity.java`, `move(...)`, lines 487–489 and 505–509, translates the existing bounding box by the movement vector and calls `setLocationFromBoundingbox()`. That helper, lines 801–804, reconstructs X and Z from `(min + max) / 2.0`. The `noPhysics` branch uses the same translate-and-reconstruct sequence at lines 487–489. Source SHA-256 `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666`.
- B `Entity.move(...)`, lines 534–536 and 554–556, adds the movement vector directly to `getX()/getY()/getZ()` and calls `setPos(...)`; the no-physics branch uses this route too. `setPos(...)`, lines 364–372, sets the position and rebuilds the box through `makeBoundingBox()`. Source SHA-256 `AB28E1FBA924771EC048140DFD293EE5A46A7DFe81F71A1A0B1AECC1927232DE`.
- Both `EntityDimensions.java` versions use the same `float g = width / 2.0F` and build X/Z endpoints as `position - g` and `position + g`; SHA-256 `6A4DED79D936BB740232AE923372E47D24A297A35EABA22E9D18FBD637A7EEDF` on both sides. Both player pose maps use width `0.6F` for standing and the same width for the movement poses cited in `Player.java` in the run manifest.

## Source-level difference

For a collision result with nonzero horizontal movement, A first rounds each translated box endpoint and then rounds their midpoint to recover player position. B adds the movement component directly to the stored coordinate, then builds a new box around that coordinate. For the player width `0.6F`, at X `0.1` with horizontal displacement `0.1`, those source expressions produce A X `0.19999999999999998` and B X `0.2` under IEEE-754 double arithmetic. The expression order is source-proven; the numeric example illustrates its rounding consequence.

## Reachability and dependencies

The path is the shared player `Entity.move` implementation in open space or after collision clipping. A uses the reconstructed position in `setPosRaw`; B stores the direct sum through `setPos`. The player pose width used to reconstruct each box is equal in both endpoints, so the difference is the coordinate calculation order rather than a pose-size change. Subsequent position-derived block coordinates and collision queries consume the stored position; their broader comparison remains open under S4-01.

## Consequence and uncertainty

The source and arithmetic establish a possible one-ULP X/Z position delta for a reachable player move. They do not establish a persistent trajectory or a block-boundary crossing in a particular scene. No gameplay validation was performed.

## Handoff

Keep this bounded position-write result separate from axis clipping, collision-shape providers and post-landing callbacks, which remain distinct S4 slices.
