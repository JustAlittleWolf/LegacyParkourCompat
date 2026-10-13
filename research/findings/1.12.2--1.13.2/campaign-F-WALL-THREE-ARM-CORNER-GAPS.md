# F-WALL-THREE-ARM-CORNER-GAPS — three-arm wall shape leaves a reachable collision corner

- **1.12.2**, ornithe-feather: `WallBlock.java`; lines 28-62.
- **1.13.2**, ornithe-feather: `WallBlock.java`; lines 22-39.

## Difference

For NORTH+EAST+SOUTH connected and WEST open, both neighbor formulas produce those three connection bits and `UP=true` when the block above is air. 1.12.2's horizontal direction bits select shape index 13.

Its collision Box is X[4,16] × Z[0,16] × Y[0,24] in sixteenth-block coordinates (the collision table raises the outline's 16/16 height to 24/16).

1.13.2's `UP=true` shape is the OR of the center X[4,12] × Z[4,12], north arm X[5,11] × Z[0,11], south arm X[5,11] × Z[5,16], and east arm X[5,16] × Z[5,11], all with Y[0,24].

This leaves the northwest corner cell region X[4,5] × Z[0,4] empty, while 1.12.2's single Box fills it.

Coordinates above are inclusive shape boundaries; collision requires positive-volume overlap.

## Movement meaning

Place open fence gates immediately NORTH, EAST, and SOUTH of the wall, with each facing chosen so its face toward the wall is `MIDDLE_POLE`; leave WEST and the block above air.

Both versions' wall helper accepts `MIDDLE_POLE` from a fence gate, and the open gate collision getters return empty shapes.

With three connections and air above, the 1.12.2 virtual-property formula and 1.13.2 placement/update formula both give `UP=true`.

Put a 0.6-wide player at y=0 with center x=z=-0.3, so its initial X and Z maxima are both 0.

Request movement `(dx,dy,dz)=(0.3,0,0.24)`, with sneaking and on-ground false.

The shared resolver processes Y, then X, then Z.

During X, the player only touches the wall's z=0 boundary, so 1.12.2 does not clip X.

After X, its box overlaps 1.12.2's wall in X; the positive Z request meets the wall at z=0 and clips to zero. 1.13.2's swept player query reaches at most x=0.3000001 and z=0.2400001 after the collector's 1e-7 expansion.

Those maxima remain below the 1.13.2 north-arm boundary x=5/16 and center boundary z=4/16, so none of the 1.13.2 wall's component shapes overlaps the query.

The open neighboring gates contribute no collision. 1.13.2 therefore retains the requested positive Z displacement for this source-derived query.

This example is a geometric source derivation through the paired collector and axis resolver, not a measured trajectory.

It is limited to the stated three-arm wall state, open-gate neighbors, and player query; no frequency claim is made.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WALL-THREE-ARM-CORNER-GAPS.md).
