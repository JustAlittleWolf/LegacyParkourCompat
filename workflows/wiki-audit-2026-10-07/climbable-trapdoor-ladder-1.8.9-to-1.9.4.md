# Finding snapshot: climbable trapdoor above a ladder

Snapshot ID: `wiki-climbable-trapdoor-1.8.9-1.9.4`

Status: source-confirmed bounded player-movement finding; independent Wiki-lane review pending; no runtime validation performed.

## Candidate and boundary

While checking the Wiki audit's ladder-interaction candidate, the exact 1.8.9→1.9.4 sources show a new `LivingEntity#isClimbing()` case for an open trapdoor positioned directly above a ladder with matching horizontal facing. This adds a reachable player climb state in `(1.8.9, 1.9.4]`. The Minecraft Wiki Ladder page could not be fetched in this audit (access failure is recorded in [wiki-page-fetch-log.md](wiki-page-fetch-log.md)); this snapshot is source-derived and does not present a Wiki sentence as evidence.

## Finding and state path

In 1.8.9, `LivingEntity#isClimbing()` floors player X/Z and the bounding-box minimum Y, reads the block at that position, and returns true only for a ladder or vine, except for spectators.

In 1.9.4, it reads the same position and returns true for a ladder or vine; otherwise, it calls `canClimbTrapdoor()` when the block is a `TrapdoorBlock`. That helper returns true only if the trapdoor's `OPEN` property is true and the block directly below is a ladder whose `FACING` value equals the trapdoor's `FACING`. Closed trapdoors, mismatched facing, and open trapdoors without a ladder below do not satisfy this added case.

The consumer is the ordinary non-fluid branch of `LivingEntity#moveRelative()`. When `isClimbing()` is true, both versions clamp horizontal velocity to ±0.15, reset fall distance, limit downward Y velocity to -0.15, and set negative Y velocity to zero for a sneaking player. After movement, horizontal collision can set Y velocity to 0.2. Those consumer operations are unchanged in this pair; 1.9.4 changes which block configurations reach that consumer. The Ladder collision-thickness change from 2/16 to 3/16 is separate and already recorded in the Feather snapshot.

## Exact source evidence and provenance

Paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each source hash matches the corresponding `ornithe-feather.sources.sha256` row.

| Version | Source and ranges | SHA-256 | Manifest row |
|---|---|---|---:|
| 1.8.9 | `net/minecraft/entity/living/LivingEntity.java`, `isClimbing()` lines 794-800; climb consumer in `moveRelative()` lines 1150-1167 | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | 859 |
| 1.8.9 | `net/minecraft/block/LadderBlock.java`, horizontal `FACING`-oriented ladder shape | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` | 95 |
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java`, `isClimbing()` lines 905-916, `canClimbTrapdoor()` lines 919-927; climb consumer in `moveRelative()` lines 1376-1394 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | 924 |
| 1.9.4 | `net/minecraft/block/TrapdoorBlock.java`, `FACING` and `OPEN` properties lines 23-25; default state lines 34-36 | `bbfbeac22de7e72b55736a35c29c10f372df1d846dd01cd16d7ee2a0913b2339` | 212 |
| 1.9.4 | `net/minecraft/block/LadderBlock.java`, `FACING` property line 17 | `f411d494a4f8c87b2b23d182527713d3b0c2fad4da329b82b84ea301a9d65fcb` | 109 |

## Applicability and limits

This finding applies to a player movement position whose feet cell contains an open trapdoor, with a ladder one block below and matching horizontal facing. It does not claim a general trapdoor climb ability, a changed ladder sneak-hold rule on ladder blocks themselves, or the same behavior in later versions. Later intervals and runtime collision validation remain open.
