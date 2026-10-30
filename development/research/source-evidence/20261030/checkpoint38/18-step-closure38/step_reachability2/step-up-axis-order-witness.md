# 1.8 → 1.8.1 step-up reachability follow-up

## Result

A distinct ordinary-player witness exists using the full-cube collision provider in `Block.java`, with the step candidate that moves Z before X. This is not the rejected carpet-overlap layout: the obstacle is a full block, and the selected raised endpoint is separated from it along Z, so neither the endpoint nor the entire downward segment overlaps its collision box.

The demonstrated travel displacement is `(dx, dy, dz) = (+0.25, 0, -0.03)` from a grounded, non-sneaking player. This is an ordinary sprint travel speed with a small lateral component; `LivingEntity`'s grounded travel updates velocity from input and then passes its velocity components to inherited `Entity.move` (1.8 `LivingEntity.java:1119–1161`; same path 1.8.1 `1131–1167`). No teleport, vehicle, custom shape, or non-player movement is needed. Coordinates below use the player position as the horizontal box center and its feet Y as `y`.

## Concrete layout and route

- Player starts at `(x, y, z) = (0.50, 1.00, -0.29)`, grounded on ordinary full-cube floor blocks whose top is `y=1`. The player AABB is `x=[0.20,0.80]`, `y=[1.00,2.80]`, `z=[-0.59,0.01]` (the entity box is centered on X/Z and uses the player's width/height; 1.8 `Entity.java:231`).
- Put a normal full-cube block at block coordinate `(1,1,0)`: collision box `x=[1,2]`, `y=[1,2]`, `z=[0,1]`. This uses `Block`'s default full-block shape and collision-box construction (1.8 `Block.java:158,292–303`; same provider in 1.8.1). The initial player box has only a `0.01` Z overlap with this block, enough for the ordinary X pass to collide.
- Put a full-cube ceiling block at `(0,3,-1)`, with underside `y=3`. It overlaps the starting and swept X/Z ranges. The upward step query therefore clips rise from `0.60` to exactly `0.20`, where the player's top touches the ceiling underside.
- On the ordinary pass, X resolves first. The obstacle clips `dx` from `0.25` to `0.20` (player max X reaches block min X=1); then `dz=-0.03` moves the box away from the obstacle. The ground flag satisfies the step gate.
- In the raised pass, the two candidates use opposite horizontal axis orders. Candidate one raises, resolves X while the initial Z overlap still exists (`dx=0.20`), then moves Z. Candidate two raises, moves Z first to `z=[-0.62,-0.02]`, then resolves X without any Z overlap and gets `dx=0.25`. Candidate two wins: its horizontal squared distance is `0.25²+0.03²=0.0634`, greater than candidate one's `0.20²+0.03²=0.0409` and the ordinary route's `0.0409`.
- The selected endpoint has AABB `x=[0.45,1.05]`, `y=[1.20,3.00]`, `z=[-0.62,-0.02]`. It is disjoint from the obstacle in Z and only touches the ceiling at `y=3` (the box collision predicates are strict). During descent, the endpoint remains outside both boxes' horizontal footprints in at least one axis, so neither can clip the downward request.

The upward swept query does collect the obstacle and ceiling: its X/Z extent includes the starting box expanded by the requested `(+0.25,-0.03)`, and its Y extent reaches `3.40`; `World.getCollisions` forwards intersecting provider boxes. The obstacle does not clip the ascent because its bottom is at the player's starting feet (`Entity` is moving upward away from it). The ceiling clips at `+0.20`. The floor below is not collected: the positive-Y expanded query preserves `minY=1`, and strict `Box.intersects` excludes a floor box whose `maxY=1` (`Box.java:153–158`; `World.java:873–907`). Thus 1.8's extra descent has no support or obstacle in the reused list to stop it.

## Version effect

- **1.8:** after candidate two wins, `Entity.move` requests `dy=-stepHeight=-0.60` (`Entity.java:526`). No collected box intersects the endpoint's downward path, so feet finish at `y=0.60`, below the raised-pass start `y=1.00`.
- **1.8.1:** the same candidate's achieved ascent is `0.20`, and the selected branch requests descent by that actual rise (`Entity.java:524` / `529`). The endpoint returns to feet `y=1.00`.
- **1.8.2 check only:** the selected branches still request the corresponding actual ascent (`Entity.java:520` / `525`, `-n` / `-q`); no broader 1.8.2 comparison was done here.

The source gate, candidate operation order, distance selection, and final descent are visible in 1.8 `Entity.java:459–538` and 1.8.1 `Entity.java:464–538`. `LivingEntity` assigns `stepHeight=0.6F` in both (`1.8:131`, `1.8.1:133`) and ordinary travel calls inherited `move` (`1.8:1161`, `1.8.1:1167`). These values satisfy the actual-rise distinction while keeping fixed vanilla block states.

## Scope and remaining boundary

This closes the ordinary player reachability witness for the specified step-up descent delta and resolves the applicability concern left by the rejected carpet setup. It does not claim complete 1.8→1.8.1 pair coverage, independent audit, runtime parity, or an exact intermediate patch boundary. 1.8.2 was checked only for the actual-rise descent rule, per assignment.

## Evidence identity

The supplied three input reports' SHA-256 values matched their assigned identities before analysis. Exact sources were read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8,1.8.1,1.8.2}\ornithe-feather`. Relevant source hashes already bound by the supplied collision/core coverage are 1.8 `Entity.java` `2be081abe1720f89d7ab90f782b8411e71bc5b8a6a53966b49a2380e3c47ffd9`, `Block.java` `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814`, `Box.java` `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`; 1.8.1 `Entity.java` `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c`, `Block.java` `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c`, and the same `Box.java` hash. 1.8.2 `Entity.java` hash was confirmed as `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.

No implementation, decompilation, build, tests, or game launch was performed.
