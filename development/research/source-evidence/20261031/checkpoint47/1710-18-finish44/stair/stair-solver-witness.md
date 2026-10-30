# Stair/step solver witness audit — 1.7.10 to 1.8

## Source identity

Both read-only inputs are exact Ornithe Feather decompilations in the primary source checkout, under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.7.10,1.8}\ornithe-feather`. The embedded `.decompile-provenance.json` SHA-256 values recomputed from those trees are `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f` (1.7.10) and `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` (1.8), matching the permitted source-only identity notes. Mapping family: Ornithe Feather on both sides.

The compared collision solver files are `net/minecraft/entity/Entity.java` (1.7.10 SHA-256 `af4ef7b11bc709d96bd3eea1260a61d38a1a04996bdf87f2a77c459ddf070a69`; 1.8 `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`). Paired support files used below are `net/minecraft/block/SnowLayerBlock.java` (1.7.10 `EA7497CCDEB306339315149C20C299B4BD95850DFC5F511604B668BB60C3DABC`; 1.8 `E68B477B30DFC38924F49859CD9E25722C5BC6CA6D5EE09D681285939AC415B4`), `StairsBlock.java` (1.7.10 `3367469608D32C3AA26158763A4C82E498091C9EAD3844832B3265FB06AC7506`; 1.8 `B50094C97DADAAF056ADA6466BAE8005A72C820E9846EF352886F1FD78CAA5CB`), and `util/math/Box.java` (1.7.10 `0EA7FB1600F9E5D7566593310830D509FEC44AAB792DB6D2D7CFA4CEBEFC6F0A`; 1.8 `2D270470A11082E1CF9F772C94ECFF9ECBF5430BE78947DD152E2E72C20BBC52`).

## Accepted claim

**1.8 adds a second step candidate whose vertical-clearance test spans the horizontal sweep; on the witness below it moves an ordinary local player farther than the sole 1.7.10 candidate.** This is distinct from the separately observed step-height change (0.5F to 0.6F) and from the 1.7.10-only sneak-offset gate.

Paired solver evidence: 1.7.10 `Entity.move(double,double,double)`, `Entity.java:494–557`, gates stepping at line 494, saves the clipped result, restores the pre-move box, tests a single raised path in Y then X then Z, descends by `-stepHeight`, and restores the clipped path on a squared-distance tie (`:495–557`). 1.8 `Entity.move(double,double,double)`, `Entity.java:459–538`, performs the same gate and clipped-result save, then tests candidate A with upward clearance against `startBox.expanded(dx, 0, dz)` and X/Z resolution (`:469–491`), and candidate B with upward clearance against the unexpanded start box and X/Z resolution (`:492–513`). It chooses A only when A's horizontal squared distance is strictly greater; ties choose B (`:514–524`). Both then descend and restore the pre-step clipped path on a tie or loss (`:526–538`).

The ordinary local-player inputs are source-established: `PlayerEntity.java:398` (1.7.10) and `:411` (1.8) set dimensions to `0.6F × 1.8F`; `LivingEntity.java:127` and `:131` set step height to `0.5F` and `0.6F`; 1.7.10 `Entity.java:126` sets `immovable = true`, and a source-tree assignment scan found no later write to that field. Thus the old solver's `!immovable` clipping guards do not alter the candidate for this player. Use the grounded, non-sneaking state so both step gates pass; the 1.7.10 sneak offset is zero, and `onGround` alone satisfies its grounded clause.

## Concrete ordinary-player witness

Coordinates use blocks at integer positions and a player bounding box with width 0.6 and height 1.8.

- Floor: full solid blocks at `y=0`, including `(0,0,0)` and `(1,0,0)`. The player's feet start at `y=1` on the floor.
- Obstacle: snow layer at `(1,1,0)`, with 1.7.10 metadata `1` / 1.8 `LAYERS=2`. Each collision shape is `[1,2] × [1,1.125] × [0,1]`: 1.7.10 `SnowLayerBlock.java:32–36` uses `(metadata & 7) * 0.125F`; 1.8 `:40–46` uses `(layers - 1) * 0.125F`. The block is supported by the floor below. `World.getCollisions` enumerates block providers and calls `addCollisions` on each side (1.7.10 `World.java:972–993`; 1.8 `:873–903`); it does not filter this witness through `canWalkThrough`.
- Overhead obstruction: full solid block at `(1,3,0)` with collision shape `[1,2] × [3,4] × [0,1]`.
- Player starts centered at `(x,z)=(0.69,0.5)`, feet at `y=1`; its box is `[0.39,0.99] × [1,2.8] × [0.2,0.8]`. It is on ground, non-sneaking with `eyeHeightSneakOffset=0`, not in cobweb, and requests `dx=0.10`, `dy=-0.08`, `dz=0`. The initial downward sweep clips at the floor to `dy=0`; horizontal motion meets the snow layer after a `0.01` gap and clips to `dx=0.01`. This satisfies the step gate on both sides.

In 1.7.10 the single candidate raises by `0.5`. At the start X range `[0.39,0.99]`, the roof at X `[1,2]` does not limit this lift. During the raised horizontal pass, however, the player's vertical range is `[1.5,3.3]`, which overlaps the roof. Positive X is clipped to `1 - 0.99 = 0.01`. Candidate distance squared is `0.01²`, tied with the already clipped path, so `Entity.java:552–557` restores the original clipped result: final center X `0.70`.

In 1.8 candidate A's upward test uses the horizontally swept box, which expands the start X range to `[0.39,1.09]` (`Box.expanded`, `Box.java:20–57`) and therefore overlaps the roof. `Box.intersectY` clips lift to `3 - 2.8 = 0.2` (`Box.java:113–130`). At that height the player's top is exactly `3`; the strict face-contact checks in `Box.intersectX` regard touching as non-overlap (`:93–111`), so A moves the full `0.10` in X. It clears the snow top because its feet are at `1.2 > 1.125`. Descent by up to `0.6` clips at the snow top, yielding feet `y=1.125`. Candidate A's squared horizontal distance is `0.10²`; candidate B raises by `0.6`, then the roof clips its X movement to `0.01`, giving `0.01²`. A wins under the strict `>` comparison, and its result also beats the initial clipped `0.01` path. The final center X is `0.79`, with the player settled on the snow layer.

The same roof geometry blocks either full-height candidate: the old 0.5F candidate and the new 0.6F candidate B both overlap the ceiling during horizontal movement. The additional swept-clearance candidate A is what enables the longer path. The witness therefore does not attribute the outcome to the step-height assignment alone.

## Stair orientation and branch audit

I compared all directional branches in the collision-shape methods: 1.7.10 `StairsBlock.java:88–165` (`isInnerStair`) and `:167–248` (`setStepShapeForCollisions`); 1.8 `:218–296` and `:299–382`. 1.8's metadata conversion at `StairsBlock.java:538–550`, together with direction IDs in `util/math/Direction.java:9–14`, maps legacy low bits `0,1,2,3` to EAST, WEST, SOUTH, NORTH, respectively, matching the 1.7.10 `var6 = metadata & 3` cases. Every direction is present on both sides in both methods:

| Facing | Inner-shape neighbor | Outer/step-shape neighbor |
|---|---|---|
| EAST (`0`) | east | west |
| WEST (`1`) | west | east |
| SOUTH (`2`) | south | north |
| NORTH (`3`) | north | south |

For each row, half gating and both neighbor-facing cases correspond under the metadata-to-direction mapping; the same-half/same-facing guard checks the same opposite-side cell. The collision wrapper also matches: 1.7.10 `StairsBlock.java:252–262` and 1.8 `:386–396` add the base shape, add the inner/corner shape, conditionally add the outer step shape, then restore the full block shape. **No remaining facing-branch asymmetry was found in these paired stair collision providers.**

## Verdict and limits

**Accepted source claim with a concrete solver witness.** The 1.8 swept-clearance candidate produces a longer grounded local-player movement in the stated exact geometry. The orientation audit found no branch-direction mismatch in stair inner/outer collision shape construction. This closes only this bounded claim and its directional check; it is not a full-pair coverage audit or freeze, does not establish the exact intermediate patch boundary, and does not claim runtime parity. The older sneak-offset gate and the 0.5F→0.6F step-height assignment remain separate changes.
