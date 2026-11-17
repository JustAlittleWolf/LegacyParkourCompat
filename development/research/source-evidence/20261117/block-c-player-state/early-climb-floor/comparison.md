# Feather climbability at open trapdoors above ladders

## Scope and exact sources

Compared the Feather decompilations for every exact release 1.8, 1.8.1, 1.8.2, 1.8.3, 1.8.4, 1.8.5, 1.8.6, 1.8.7, 1.8.8, 1.8.9, and the 1.9 introduction anchor. All source trees are under decompiled_minecraft/<version>/ornithe-feather/.

For 1.8 through 1.8.8, each tree has .decompile-provenance.json with a per-file source hash and pinned exact version metadata, client artifact, and Feather mapping input. The LivingEntity.java hash matches its provenance source entry for every release. 1.8 and 1.8.1 have different file hashes but identical isClimbing method bodies; the 1.8.2 through 1.8.8 files share hash 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e.

1.8.9 is identified by ornithe-feather.provenance.json and ornithe-feather.ready.json: exact version 1.8.9, Feather feather-gen2-1.8.9+build.2, 1,612 source files, and source manifest SHA-256 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004. The manifest's LivingEntity.java entry matches the published source file hash 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e. The verification record reports all 1,612 published sources identical to the verified source tree.

For 1.9, .decompile-provenance.json pins Feather metadata https://meta.ornithemc.net/v3/versions/feather/1.9, client SHA-256 0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307, and Feather mapping artifact feather-gen2-1.9+build.2-mergedv2.jar (SHA-256 a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a). The published LivingEntity.java hash matches its provenance source entry (5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb).

## Source comparison

In 1.8 (LivingEntity.java:790-796) and 1.8.1 (LivingEntity.java:796-802), isClimbing() floors x, the player's shape minY, and z, fetches that block, then returns true only for Blocks.LADDER or Blocks.VINE, subject to the existing player spectator exclusion. 1.8.2 through 1.8.8 use the same predicate at LivingEntity.java:794-800; their file hash is unchanged. 1.8.9 has that same predicate at LivingEntity.java:794-800.

The exact 1.8 and 1.8.9 registries instantiate ladder as LadderBlock at numeric block ID 65 and trapdoor as TrapdoorBlock at ID 96 (block/Block.java:885,948 in 1.8; block/Block.java:965,1032 in 1.8.9). Thus the queried upper block is a trapdoor, distinct from ladder and vine, in each pre-1.9 release. No pre-1.9 isClimbing() implementation inspected recognizes that block or examines its state.

In 1.9, LivingEntity.isClimbing() at LivingEntity.java:896-919 retains the floored player-coordinate lookup and spectator exclusion. It returns true for ladder or vine, or for a TrapdoorBlock accepted by canClimbTrapdoor(pos, state). That helper (LivingEntity.java:910-919) requires the trapdoor state's OPEN property to be true, then reads pos.down() and requires a ladder block whose LadderBlock.FACING equals the upper state's TrapdoorBlock.FACING. The corresponding state properties are declared in block/TrapdoorBlock.java:23-24; the ladder facing property is in block/LadderBlock.java. The registry still assigns ladder ID 65 and trapdoor ID 96 (block/Block.java:816,892), with the named block references wired through block/Blocks.java:312,347.

This predicate is consumed by the living player's movement response: in 1.8 LivingEntity.java:1146,1162, in 1.8.9 LivingEntity.java:1150,1166, and in 1.9 LivingEntity.java:1363,1379. The first call applies climb-related velocity and fall-distance limits; after movement, the second applies the upward response on horizontal collision. The 1.9 addition therefore lets a qualifying trapdoor enter those existing climbing responses.

## Bounded ordinary vanilla witness

Use a non-spectator player with feet at (0.5, 2.0, 0.5), so flooring x, minY, and z selects (0,2,0). Put an open trapdoor at (0,2,0) with FACING=NORTH, and a ladder at (0,1,0) with FACING=NORTH.

- In every inspected release from 1.8 through 1.8.9, isClimbing() sees the trapdoor block and returns false.
- In 1.9, it sees an open trapdoor, then the same-facing ladder immediately below, and returns true.
- A plain ladder or vine at the sampled player block remains climbable in both eras. A closed trapdoor, or an open trapdoor whose lower block is not a same-facing ladder, fails the new 1.9 condition.

## Finding and limits

The precise introduction boundary is between 1.8.9 and 1.9: all nine 1.8 patch releases were checked, and the explicit open, same-facing trapdoor-over-ladder predicate first appears in the inspected 1.9 source. This establishes the source boundary for the named releases; it does not attribute a historical bug rationale.

Coverage is limited to this climbability predicate, its referenced vanilla registrations and state properties, and the player movement call sites that consume it. Collision shapes, vehicle or other entity physics, other tick operations, and runtime parity were not examined.

TERMINAL: COMPLETED
## Per-release source hash record

The predicate-bearing LivingEntity.java source hashes checked against the provenance entry or canonical source manifest are:

- 1.8: 645624913e265cad50293f659812a35c3c4303d1e8bdafdcca4eaf2e8ed5c69d
- 1.8.1: fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f
- 1.8.2, 1.8.3, 1.8.4, 1.8.5, 1.8.6, 1.8.7, 1.8.8, 1.8.9: 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e
- 1.9: 5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb

The provenance metadata URL for each 1.8–1.8.8 directory names that exact Feather release. The 1.8.9 ready and provenance records identify exact versionId and mapping, as noted above.