# Independent source audit: early cardinal grounded AutoJump producer

Status: **COMPLETED — accepted for the bounded WEST, NORTH, and SOUTH fixtures.** The grounded producer, cardinal headings, support, border predicate, historical side-cap difference, first y=64 collision hit, direct candidate-top AIR read, and timer write are supported by the frozen exact-source set. This is a source-derived fixed-fixture result, not runtime playback or parity evidence.

## Frozen inputs and audit boundary

The primary source producer is `early-known-cardinal-ground-source/source-closure.md`, SHA-256 `ABDDC3182837FBBF72CB0AF6EC888FAF8991246707F9B65C8213750FA8494B21`. Companion source-only inputs were checked by SHA-256: alias source `A7CCE5FEE3AFB2CB1C73096891DC61D8A42C40434E43CA7F19B116D378909E0D`; alias audit `991516352E5F07E61D8E12616512B97ACC00B6F19CC3325999A6B86060FB798C`; and east grounded producer source `5A368468C4246330CFC8AC404A370EEE8B131BF4BE330A48B7B728E65645F012`.

I read only the assigned project instructions, README/buildSrc source guidance, orchestration and source-discovery procedures, these source-only evidence files, and canonical read-only Feather Gen2 build.2 sources for 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1, 1.11.2, plus the 1.12.2 control. I did not inspect product/Git history, backlog, wiki lanes, other directions, or corner geometry.

Direct SHA-256 checks on canonical sources confirmed these key identities (paths are under `decompiled_minecraft/<release>/ornithe-feather/net/minecraft/`):

| Source | Release(s) | SHA-256 |
|---|---|---|
| `client/entity/living/player/LocalClientPlayerEntity.java` | 1.10, 1.10.1, 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| same | 1.11, 1.11.1, 1.11.2 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| same | 1.12.2 control | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `world/World.java` | 1.10 | `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877` |
| same | 1.10.1 | `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a` |
| same | 1.10.2 | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` |
| same | 1.11 | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| same | 1.11.1, 1.11.2 | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| same | 1.12.2 control | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `world/border/WorldBorder.java` | all seven | `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257` |

The frozen inputs report the common body digest `2ef3050e68d1ed7f2ce5e69d8899abb4d12a0a57d895e4e452a73c7682247184`. I independently extracted the complete method including signature and braces from each canonical player file, normalized CRLF to LF, and obtained the same SHA-256 `5b124378adc6f3e0116153987d752d813a48d5c4916d07731993d10e537b4aa3` for all seven. Full source-file hashes above also bind its enclosing call and gate text.

## Grounded producer and yaw

The local client copies forward input to `forwardSpeed`; `LivingEntity.mobTick` multiplies it by `0.98F` before movement. On grounded Stone, source order is support slipperiness times `0.91F`, `0.16277136F / (s*s*s)`, then `getSpeed() * t`, followed by `updateVelocity`. The base movement attribute is `0.1F`; Speed II yields `0.14F` in the stipulated effect state. Stone gives `s = 0.546000063419342F`, `t = 0.9999998211860657F`; after the damped forward input and sub-unit input normalization, the horizontal displacement from rest is `0.13719996809959412` blocks.

The horizontal forward transform is `(-sin(yaw), cos(yaw))`. It rotates the same ordinary forward input onto the intended axis: yaw `+90°` WEST (`dx≈-0.1371999681`), `180°` NORTH (`dz≈-0.1371999681`), and `0°` SOUTH (`dz≈+0.1371999681`). The tiny cardinal cross-axis residue does not move the center out of its tangential cell. `LocalClientPlayerEntity.move` calls `super.move` and then calls AutoJump with the actual resulting x/z changes (1.10 `LocalClientPlayerEntity.java:812–816`; corresponding later calls are in the frozen source closure).

The supplied inward starts therefore land at the target post-centers `(-8.99,0.5)`, `(0.5,-8.99)`, `(0.5,8.99)`. A 0.6-wide footprint spans the two contiguous y=63 support blocks for each row: west `x=-10,-9,z=0`; north `x=0,z=-10,-9`; south `x=0,z=8,9`. Each footprint remains over the blocks before and after the move. Their tops equal the feet plane y=64, so the blocks support the floor-clipped tick and only touch the AutoJump query's minimum Y; they do not add an intersecting obstacle box.

For border center `(0,0)` and size `20`, the exact entity-center predicate erodes each edge to `(-9,9)` when the prior outside flag is true; with a false flag it expands to `(-11,11)`. The three post-centers are strictly inside either interval, so the cached predicate holds regardless of that prior flag. `WorldBorder.contains(BlockPos)` tests the unit cell against `[-10,10]`; the relevant exterior wall cells are outside while their loaded physical states remain AIR.

## Collision scan, ray hit, clearance, and write

The AutoJump gates require enabled state, expired timer, grounded, not sneaking/riding, nonzero input, then accept the look-aligned displacement. For this ordinary producer the query uses the nonzero actual displacement. The first exterior side scan coordinates remain those bound by the conditional fixtures: west `(-12,-1)`, north `(-2,-11)`, south `(-2,10)`. The first intersected synthetic full cube on the raised y=64.51 ray is y=64 at west `(-11,64,0)`, north `(0,64,-11)`, and south `(0,64,10)`. The endpoint projects about `1.11717` blocks outward from the post-center, passing the border face by about `0.10717` blocks. At these coordinates/tangential centers, no earlier block lies on either raised segment.

The exact side-column cap predicate differs by release family. In 1.10, 1.10.1, 1.10.2 and 1.11, `q <= 0 || r != k && r != l - 1` prunes both vertical caps for side columns; with `k=63,l=67`, y=63 is skipped. In 1.11.1 and 1.11.2, the entity overload uses the non-enforcing border path and `!bl3 && !bl4 || q != l - 1`, so it prunes only y=66. It processes y=63, but that unit cube only touches query `minY=64` and strict intersection adds no box. The 1.12.2 control retains the top-only predicate. Thus the first y=64 shape intersecting each raised ray is unchanged across all listed releases. This describes only the named side columns, not corner behavior.

The border collision provider supplies temporary Stone for those out-of-border cells while the real world state remains AIR. AutoJump takes the hit box's top `s=65`, creates the candidate top position, then performs one direct `world.getBlockState` clearance read at y=65 for that hit; because it is not inside the collision provider, the read sees loaded AIR. The earlier two headroom probes are separate. The clearance loop visits only `t=1` (`t < 1.2F`), and does not take its `t>1` follow-up branch. It leaves rise `u = 65 - 64 = 1`; `u > 0.5F && u <= 1.2F` passes and writes `ticksToNextAutojump = 1`.

## Verdict and limits

Accepted for the three named fixed WEST/NORTH/SOUTH fixtures across 1.10–1.11.2, with 1.12.2 as the stated control. The historical cap-pruning difference affects only whether y=63 is skipped or processed as a touch-only cube; it does not change the first y=64 ray hit, direct AIR clearance, or timer result. This does not establish runtime playback/parity, arbitrary starts, any other direction, corner geometry, general border behavior, implementation applicability, or intermediate patch cutovers.

No code/build/tests/launch/source generation/push, product/backlog edit, or Git operation was performed.
