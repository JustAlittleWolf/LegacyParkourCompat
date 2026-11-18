# Independent verdict — prismarine slab edge probe

## Binding

Reviewed input: `source-1214-1215-prismarine-slab-edge-probe/probe.md` (SHA-256 `5a11242cdf05ed82974e8192e1d5b54da4dfeb4fba89e1e8f9a457b4bb4f8467`).

Verdict: **Accept the bounded 1.21.4 → 1.21.5 source candidate**, conditional on the stated world and map-configured high-speed setup. The edge-query witness and resulting `maybeBackOffFromEdge` return vectors follow the canonical sources. This is not a claim of default-speed frequency or runtime parity.

## Evidence identity and scope

The canonical ready markers identify 1.21.4/mojmap and 1.21.5/mojmap. Their source-manifest digests agree with the marker fields: `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` and `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`. The paired `Player.java`, `Shapes.java`, `IndirectMerger.java`, `SlabBlock.java`, `BlockCollisions.java`, and `CollisionGetter.java` files match those manifests. `IndirectMerger.java` is byte-identical across the pair.

Relevant exact Mojmap slices:

- 1.21.4 `Player.java:1069-1127`; 1.21.5 `Player.java:1026-1084`: same `maybeBackOffFromEdge` gates and X-only, Z-only, joint loop order; changed `canFallAtLeast` query construction.
- 1.21.4 `Shapes.java:160-190` and 1.21.5 `Shapes.java:167-197`; both use the same `IndirectMerger` path and its `1.0E-7` coordinate merge/collapse comparisons.
- `SlabBlock.java` gives BOTTOM prismarine slabs the non-full-block half-height shape `[0,1] × [0,.5] × [0,1]`; the prismarine slab registration resolves to `SlabBlock` in both versions. `BlockCollisions`/`CollisionGetter.noCollision` route this non-singleton slab shape through shape intersection.
- Player calls `maybeBackOffFromEdge` from ordinary `Entity.move` before collision clipping in both versions. Player's ground-surface gate is shift-key state; `onGround()` makes the `isAboveGround` gate true.

## Independent geometry check

With width `0.6F`, the half-width is `0.30000001192092896`. At center `(0.25, 1.25)` and feet `y=.5`, the box is X `[-.050000011920928955, .550000011920929]`, Z `[.949999988079071, 1.550000011920929]`. A at `(0,0,0)` overlaps the initial support query; B at `(-1,0,1)` also overlaps it. Their top surfaces are both exactly `y=.5`.

The paired edge queries differ exactly as claimed for this witness: old 1.21.4 uses the un-inset horizontal bounds; new 1.21.5 adds `1.0E-7` to query minima and subtracts it from maxima. Near C's X minimum at 1, `Shapes.joinIsNotEmpty`/`IndirectMerger` collapses an interval at or below `1.0E-7`. Therefore raw overlap `(1.0E-7, 2.0E-7]` is the distinguishing band: old query has a positive merged interval, while after the new inset the remaining overlap is at most the merger tolerance. Exact endpoints are non-intersections. This is consistent with the probe's `dx=.45000015` (raw C overlap about `1.6192E-7`; post-inset overlap about `6.1921E-8`). The vertical probe still intersects the slab volume below feet `.5`; the changed vertical inset does not remove that volume.

For the stated A/B/C arrangement and `dz=.1`, X-only remains supported by A and Z-only by B, so neither axis loop changes its component. The joint query in 1.21.4 still intersects C and returns the incoming X/Z components. In 1.21.5 the first joint query clears; the loop subtracts signed `.05` on both axes. At roughly `(.40000015,.05)`, the inset query's Z minimum is about `1.000000088`: it is only `8.8E-8` above A's Z maximum and is collapsed by the same `1.0E-7` merger tolerance, so one more joint decrement occurs. It reaches approximately `(.35000015,0)` and exits because Z is zero. The returned movement vectors are thus approximately old `(.45000015,0,.1)` vs new `(.35000015,0,0)` (Y remains the original movement Y).

Projecting those returned horizontal deltas from the initial center gives old endpoint center about `(.70000015, 1.35)` and new endpoint center about `(.60000015, 1.25)`, with feet still `y=.5`. The old projected footprint overlaps C; the new footprint overlaps A. Both footprints have support at the BOTTOM-slab top height. These are projected endpoints for the backoff result; ordinary `Entity.move` then applies its separate collision clipping.

## Reachability and limits

Source-level map reachability is plausible and supported: `Attributes.MOVEMENT_SPEED` is syncable and has range through 1024; `Player.getSpeed()` reads it. `LocalPlayer` multiplies crouched input impulses by `SNEAKING_SPEED` (default `.3`), after which relative movement is scaled by speed and rotated. A static map-configured attribute can therefore supply a finite neighborhood around the required high horizontal delta, with placement adjusted to retain the narrow C overlap. No command, runtime, test, build, product source, wiki, or backlog input was used. The exact configured route and tick-to-tick arrival were not runtime-verified.
