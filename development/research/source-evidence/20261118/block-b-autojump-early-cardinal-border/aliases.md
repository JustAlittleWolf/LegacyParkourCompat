# Early AutoJump cardinal border aliases: west, north, south

Status: **COMPLETED — source route preserved for the three assigned cardinal fixtures** in 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, and 1.11.2. The result is conditional on the named stationary, loaded, centered, eroded-center fixture and accepted horizontal displacement. It is not runtime parity evidence. EAST, generic border behavior, other directions, and corners are outside this check.

## Scope and bound evidence

This is a source-only projection of the already authored, independently accepted 1.12.2 WEST/NORTH/SOUTH rows in `development/research/source-evidence/20261117/block-b-solid-collision/autojump-border-cardinal-112/report-r3.md` (SHA-256 `e383daf63378cb6494788c37b87a0b82758440f604acb7df9ff44754cdcbd74c`) and its `verdict.md` (SHA-256 `cf9dad860456cfb910449cc8657215934a6e34865d325e2eb43276c526354435`). I reused only the permitted east-derived operation facts in these early lab witnesses:

- `early-border-alias-source/evidence.md`: `a8143fc88eedd05955dd9b64a7803d77d4a83749800b4a9f133d2d138ac547b2`.
- `early-border-alias-audit/audit.md`: `817207b8266b51e046d5b0bbb4ffa2e5272825faafd7e6aebbb12c2e1d43676b8a`.
- `early-border-alias-audit/erratum.md`: `9db9bb24357e72517b4aa65e7b2bfa3505f41742e4ecb04c8a44928fe1ff9180`.
- `early-border-ground-source/source-closure.md`: `5a368468c4246330cfc8ac404a370eee8b131bf4be330a48b7b728e65645f012`.
- `early-border-ground-audit/audit.md`: `1d01af27fa3c0dfec66a01a003a1da2d252fe022f0a30455b80377100a547096`.

The alias audit accepted the exact east-side conditional route across these seven local releases; the ground audit separately closed an ordinary first-forward grounded producer for its east fixture. I used their AutoJump, border, cap-pruning, real-state headroom, and grounded movement constraints only as operation facts, then checked the three named directions below against the exact early sources. No product code, product Git state, wiki material, or backlog was read.

## Exact source identities

Canonical read-only paths are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. The exact source family is Ornithe Feather Gen2 build.2. For every release, current file bytes were SHA-256 checked. LocalClientPlayerEntity is the AutoJump source; World is the collision provider; WorldBorder provides block containment. Existing pin audits bound the source entries to exact source manifests/provenance and Feather build.2 artifacts; 1.10 and 1.10.1 use embedded provenance, while 1.10.2–1.11.2 use their published sidecars.

| Release | `LocalClientPlayerEntity.java` SHA-256 | `World.java` SHA-256 | `WorldBorder.java` SHA-256 | source provenance/manifest SHA-256 |
|---|---|---|---|---|
| 1.10 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | embedded provenance `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | embedded provenance `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | source manifest `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` | source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |

The complete AutoJump method body had the independently checked common hash `2ef3050e68d1ed7f2ce5e69d8899abb4d12a0a57d895e4e452a73c7682247184` across these six sources (offsets: 823 in 1.10–1.10.2; 835 in 1.11–1.11.2). Thus the query construction, directional gates, two lateral rays, direct blockstate clearance reads, rise test, and timer write are byte-identical across this set. File hashes above were recomputed in this check and match the bound witness identities. No source was generated or modified.

## Fixture and per-face scan result

Take a square stationary border centered `(0,0)`, size `20`, strict limits `x,z ∈ [-10,10]`, feet y=64, tangential center `+0.5`, Speed II, AutoJump on, cooldown zero, grounded, not sneaking or riding, no Jump Boost, and the accepted outward displacement about `0.10` (above the `g <= 0.001F` input fallback). The post-move normal center is `±8.99`; every pre/post center is strictly within eroded `(-9,9)` and therefore makes the cached `isWithinBorder` true regardless of the prior outside-border flag. Keep the indicated two default Stone blocks at y=63, the physical wall and all clearance cells AIR, and every padded-scan chunk loaded.

| Face | Player center before → after `(x,z)` | Stone support cells at y=63 | Direct post-move clearance cells | Physical AIR wall / first synthetic ray-hit cell | First synthetic scan candidate |
|---|---|---|---|---|---|
| West (min X) | `(-8.89,.5) → (-8.99,.5)` | `(-10,63,0)`, `(-9,63,0)` | `(-9,65,0)`, `(-9,66,0)` | `(-11,64,0)` | `(-12,63,-1)` |
| North (min Z) | `(.5,-8.89) → (.5,-8.99)` | `(0,63,-10)`, `(0,63,-9)` | `(0,65,-9)`, `(0,66,-9)` | `(0,64,-11)` | `(-2,63,-11)` |
| South (max Z) | `(.5,8.89) → (.5,8.99)` | `(0,63,8)`, `(0,63,9)` | `(0,65,8)`, `(0,66,8)` | `(0,64,10)` | `(-2,63,10)` |

The raised lateral segments are at y=64.51 and tangential coordinates 0.2 and 0.8 (player width 0.6, half-width offset 0.3). Their first hit is the single listed target wall cell; earlier outside cells in the query miss both segments. The query's padded scan ranges are west x=`-12..-8`, z=`-2..2`; north x=`-2..2`, z=`-12..-8`; south x=`-2..2`, z=`7..11`. `World.getCollisions` visits x then z then y in ascending order, skips the two-axis scan corners, and the first exterior candidate on each listed face is the table's `(x,63,z)`. Candidate y=63 only face-touches the query and is rejected by strict intersection; y=64 is the first ray hit with unit-cube `maxY=65`. Those per-direction first-cell results follow the exact loop order, not a rotation assumption. Tangential exterior faces do not contribute because the padded tangential ranges remain strictly inside the opposite border faces.

The player's two clearance samples start at `BlockPos(x, getShape().maxY, z)` and advance one block after an empty collision shape. With feet 64 and height 1.8 these sample y=65 and y=66 in the table. After a ray hit, the candidate-top loop reads the actual blockstate at `hitPos.up(1)`; for the three wall cells this is respectively `(-11,65,0)`, `(0,65,-11)`, and `(0,65,10)`. Keep those AIR as well. This direct `world.getBlockState` is not the synthetic border collision state: the collision provider substitutes Stone only while building the collision list, and the real loaded AIR at y=65 remains the top-clearance read. The loop's sole `t=1` sample leaves rise `u=65-64=1`; `u>0.5F && u<=1.2F` passes and writes `ticksToNextAutojump=1` in every listed release.

## Exact operation evidence and 1.11.1 cap change

- `LocalClientPlayerEntity.move` invokes `autoJump` after superclass movement using actual post-collision position differences (`LocalClientPlayerEntity.java:813–816` in 1.10; `825–828` in 1.11.2). The common AutoJump body requires enabled state, expired countdown, grounded state, no sneaking/riding, nonzero input, and movement aligned to the current look. It reads two real headroom blockstates, constructs an extended query, grows it by player width, raises two lateral segments by 0.51, requests `world.getCollisions(this, box)`, computes the candidate top from a collision box, then reads the blockstate above that candidate directly and arms one tick for rise 1.
- In 1.10, 1.10.1, 1.10.2 and 1.11, `World.getCollisions(entity, shape)` scans the query and applies `if (q <= 0 || r != k && r != l - 1)` to side-cell vertical candidates. It omits both vertical caps only on side columns; the candidate y=64 is interior to the fixture scan y=63..66 and survives.
- In 1.11.1 and 1.11.2, the entity overload delegates to `getCollisions(entity, shape, false, list)` and that non-enforcing path substitutes `Blocks.STONE.defaultState()` outside `WorldBorder` when the cached center predicate is true. Its scan changes to `if (!bl3 && !bl4 || q != l - 1)`: on side columns it prunes only the upper cap. The same fixture bounds give `k=63`, `l=67`, and the wall candidate y=64 is neither cap, so this exact top-only change also retains all three target wall cells. The first scan cells stay as tabled, including corner pruning and x/z/y iteration order.
- Across every release, border containment treats the wall cells `x=-11`, `z=-11`, and `z=10` as outside. The centered player remains within the strict eroded-center predicate, so the entity collision query uses synthetic Stone for those cells; the block state itself remains physical AIR.

## Direction, yaw, and ordinary grounded motion

The exact AutoJump fallback direction formula is byte-identical across this source set: with forward-only input its horizontal vector is `(-sin(yaw), cos(yaw))`. Thus yaw `+90°` points WEST, `180°` points NORTH, and `0°` points SOUTH; the same yaw also aligns the ordinary player movement direction with the direct AutoJump displacement so the look-angle dot gate passes. The already independently audited grounded producer establishes, for forward-only input, Speed II, grounded Stone friction, and a floor-clipped grounded tick, an actual horizontal move of about `0.1372` from rest; this projects to each cardinal face by the yaw above. To hold the same post-move `±8.99` normal center, start about `0.1372` inward (e.g. west x≈`-8.8528`, north z≈`-8.8528`, south z≈`8.8528`) with the table's support cells. The 0.6-wide footprint remains supported across those pairs. This producer gives a slightly longer lookahead than the accepted 0.10 conditional input, but leaves the same first wall-ray cell and scan bounds: the extra outward reach is under 0.04 block, while the source lookahead extends about 0.98 block and the wall face is 1.01 blocks from the post-move center. This is a source-derived reachability projection from the audited east producer plus the exact cardinal yaw formula, not a claim that runtime playback was executed.

## Verdict boundaries

**Accepted source route for WEST, NORTH, and SOUTH**: all six exact releases preserve the per-cell synthetic Stone candidate through the first AutoJump segment, read loaded real AIR for the named headroom/candidate-top conditions, and arm the rise-1 timer under the named fixture. The 1.11.1 top-only vertical-pruning change does not remove y=64. Existing ordinary grounded input/movement constraints rotate to these three directions and positions as described. No movement parity, arbitrary position, other direction, general border behavior, or implementation claim follows.

No tests, builds, game/TAS/Gym/server launch, source generation, push, product/backlog edit, or primary evidence edit was performed.
