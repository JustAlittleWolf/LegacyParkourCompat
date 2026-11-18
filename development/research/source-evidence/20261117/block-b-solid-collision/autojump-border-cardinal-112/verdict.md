# Independent 1.12.2 AutoJump border audit

## Binding and verdict

Reviewed only the 1.12.2 claims in `source-1122-cardinal-border-autojump-sweep/report-r3.md`, SHA-256 `e383daf63378cb6494788c37b87a0b82758440f604acb7df9ff44754cdcbd74c`. I did not assess claims about another release. Verdict: the four-side movement derivation and result are source-supported for the specified centered square, with one source-hash transcription in the report to correct before treating its evidence list as exact.

## Exact-source identity

The canonical read-only source is `decompiled_minecraft/1.12.2/ornithe-feather`. The provenance and ready markers identify Minecraft 1.12.2, Ornithe Feather Gen2 build.2, the exact client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, and the pinned Feather merged jar and Tiny mapping inputs. I independently recomputed the source-manifest SHA-256 as `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, matching the ready marker. The cited `LocalClientPlayerEntity.java`, `World.java`, `WorldBorder.java`, `Box.java`, `Block.java`, `Blocks.java`, `StoneBlock.java`, `Entity.java`, and `BlockPos.java` files each match their entry in that manifest.

One copied hash in r3 is wrong: it gives `LocalClientPlayerEntity.java` as `01a58e94d8c6ff98a8e3794227cdc76a5fcbdad795c70c9cf28854aff9823cc`; the manifest-matched file hash is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`. This is a transcription defect in the report; the canonical file itself is present and matches the source manifest.

## Fixture and source-ordered derivation

Use the report’s corrected border centered at `(0,0)` with bounds `[-10,10]`, feet y=64, tangential center `+0.5`, and post-move normal coordinate `±8.99`. The pre-move normal coordinate is `±8.89`; both positions pass the strict eroded `(-9,9)` test for either prior outside-border flag. `World.isWithinBorder` uses strict comparisons and `World.getCollisions` retains that post-move result in `bl2`; this makes the synthetic border substitution active on all faces regardless of the initial flag.

`LocalClientPlayerEntity.move` runs superclass movement before passing its actual horizontal delta, cast to float, to `autoJump` (lines 846–857). The accepted magnitude `.1` is above `g <= .001F`; the fixture’s aligned yaw and nonzero forward input pass the directional gates. With player speed under Speed II, the lookahead term is `max(f*7, 1/l)` and extends approximately `1.08` blocks outward from the post-move center. The raised line pair is at y=64.51 and tangential coordinates `.2` and `.8` (each line is offset by half the `.6` width, so their separation is the full width).

The source computes the AutoJump query from the post-move point and extended endpoint, then grows it by the full width `.6` on both horizontal axes. Its approximate padded scan ranges are:

| Face | Padded block coordinates scanned `(x,z)` | First substituted scan candidate `(x,y,z)` | First individual ray-hit box `(x,y,z)` |
|---|---|---|---|
| East, max X | x `7..11`, z `-2..2` | `(10,63,-2)` | `(10,64,0)` |
| West, min X | x `-12..-8`, z `-2..2` | `(-12,63,-1)` | `(-11,64,0)` |
| North, min Z | x `-2..2`, z `-12..-8` | `(-2,63,-11)` | `(0,64,-11)` |
| South, max Z | x `-2..2`, z `7..11` | `(-2,63,10)` | `(0,64,10)` |

These are independently verified against the actual x-ascending, then z-ascending, then y-ascending loops in `World.getCollisions` (964–1001), including the double scan-edge skip. The distinct first scan candidates demonstrate why the per-face order should be checked instead of inferred by rotation. For each side the earlier candidate either misses the query or misses both rays; the first wall box intersected by a ray is the y=64 full Stone box shown above. The two rays on each side occupy the same tangential block cell, so their `OR` check does not create two entries or change list order. The world loop visits each candidate block once and `Block.addCollisions` appends individual translated shapes; adjacent synthetic wall cells are not merged or de-duplicated. No earlier eligible shape changes the first ray hit in these four fixtures.

`WorldBorder.contains(BlockPos)` makes the first outboard integer cells x=10, x=-11, z=-11, and z=10. Each first-hit cell remains physically AIR; the border check substitutes `Blocks.STONE.defaultState()` only in the collision query. The physical face cells are AIR at y=63..66. Support is exactly the table’s two default Stone blocks per side at y=63: their full-cube tops reach y=64 but only touch the AutoJump query’s y-min, so strict `Box.intersects` rejects them. The post-move direct headroom reads are y=65 then y=66 at the floored center coordinates shown in r3; they are AIR. Keeping every chunk touched by the padded scan loaded is necessary because the collision loop skips positions whose `(x,64,z)` chunk is not loaded.

For each first ray-hit cube, `maxY=65`; with feet at 64, AutoJump computes `u=1`. The no-Jump-Boost allowance is `o=1.2`, so `u` passes the strict `>0.5` and `<=o` tests and sets `ticksToNextAutojump=1` on all four faces (LocalClientPlayerEntity.java 946–948). The report’s corrected positions, samples, scan candidates, ray-hit cells, and timer conclusion are supported by the 1.12.2 sources.

## Source locations checked

- `LocalClientPlayerEntity.java`: `move` and `autoJump`, lines 846–948.
- `World.java`: border-aware collision scan, lines 964–1014; `isWithinBorder`, lines 1042–1059.
- `WorldBorder.java`: `contains(BlockPos)`, line 24; bounds and center/size accessors.
- `Box.java`: `grown` and strict `intersects`.
- `Block.java`, `AirBlock.java`, `StoneBlock.java`: empty/full collision shapes and collision insertion.
- `Entity.java` and `BlockPos.java`: player shape bounds and flooring used by the fixture.

No product source, wiki inventory, backlog, Git history, build, test, runtime, or decompilation was used.
