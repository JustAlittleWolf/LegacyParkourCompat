# F-1.20.2-DIODE-SUPPORT-COLLISION: unsupported diode block states are removed in 1.20.2

- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/world/level/block/RepeaterBlock.java`; lines 55-59.
- **1.20.2**, mojmap: `1.20.2/mojmap/net/minecraft/world/level/block/RepeaterBlock.java`; lines 55-63.

## Difference

Preconditions: server-side normal block update flags dispatch neighbor shape updates; a horizontal-facing Repeater or Comparator has a block directly below it that changes to a state whose UP face is not sturdy for `SupportType.RIGID`.

In 1.20.1, the DOWN shape callback on a Repeater reaches the existing server axis-mismatch branch (Y differs from horizontal facing) and writes only its LOCKED property; a Comparator inherits the base `BlockBehaviour#updateShape` body, which returns its current state.

Both remain in the world.

In 1.20.2, the new support guard in RepeaterBlock and the added guard in ComparatorBlock evaluate the supplied changed-neighbor state through `canSurviveOn`; when support fails either returns AIR. `Block.updateOrDestroy` removes either diode on the server.

The support predicate is the same UP/RIGID face-sturdy condition on both endpoints; the difference is the new shape-update removal path.

This is a world block-state presence/removal difference.

Both endpoints provide the same Diode collision shape for an extant Repeater or Comparator state, so no same-state collision-shape delta is established.

The trigger is reachable from the common `Level#setBlock` path: when the state below changes, `BlockState#updateNeighbourShapes` visits the block above with a DOWN direction, and `LevelAccessor#neighborShapeChanged` calls `NeighborUpdater.executeShapeUpdate`.

The cited `Level#setBlock` route dispatches shape updates; no separate `neighborChanged` callback is part of this bounded route.

## Movement meaning

World/block state change below a diode -> `updateNeighbourShapes` -> DOWN `neighborShapeChanged` -> the Repeater/Comparator `updateShape` callback -> retained diode in 1.20.1 / AIR and server removal in 1.20.2 -> `BlockCollisions.computeNext` sees different world block-state populations during `Entity.move`.

This downstream collision difference is caused by which vanilla block state exists, not by different collision geometry for the same supplied state.

Under the project rule that block states stay vanilla, this behavior is excluded from a historical movement patch.

Both blocks are existing vanilla blocks in 1.20.1; that fact does not make their version-specific world-state lifecycle eligible for movement emulation.

Source-proven: under the stated callback preconditions, 1.20.1 retains the Repeater or Comparator block state and 1.20.2 removes it on the server; the extant state has the same 1/8-block Diode collision shape in both endpoints.

The distinct collision-world outcome follows solely from state removal.

This source observation is excluded from historical movement emulation.

No final position, velocity, trajectory, or gameplay reproduction was measured.

The exact support-changing action and any modded update policy are outside this source claim.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-DIODE-SUPPORT-COLLISION.md).
