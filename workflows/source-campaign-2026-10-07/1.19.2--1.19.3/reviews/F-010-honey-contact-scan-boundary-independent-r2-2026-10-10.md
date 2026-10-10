# F-010 independent blind R2 review — 2026-10-10

- Reviewer: independent source-only worker `review_honey_contact`, continuing the original review assignment.
- Overall incremental implementation-gate verdict: **revision-required**, solely for the unresolved exact first-change boundary. Handoff: **blocked**.
- Source behavior and conditional applicability verdict: **verified**. The revised vanilla command-to-local-player witness closes the previous entry-state producer gap. No further witness revision is requested by this review.
- Immutable revised finding commit: `33fb4482c3a5a49f28557ff934fc8485a09b5605`.
- Finding path: `workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-010-honey-contact-scan-boundary.md`.
- Git blob: `c38a178cd2c96f4725b2f1b62c0ff64d48a25819`; raw SHA-256: `fae6af2f355acd6760d39aec5f5215b773eb60abcc1e588d56e2d6832f6870c0`.
- Final source ledger checkpoint: `a51009154d1f1789e71d6a982e822fd2ef9c68a4`; its finding blob is unchanged. The ledger's broader content was not used as evidence.
- Previous R1 review at `b0bddfc7cf5bb20e2a16b9b79f950fcbce115cd3`, blob `c40d62b2c385ccf6bd2bdc2a674902a92415c6df`, SHA-256 `e5fd4ad60c809c227a09e31ea31f7d402937840b81440e8143a022ce61e59707` remains byte-preserved. Its producer objection is resolved by R2; its boundary limitation remains.
- Pair: 1.19.2 → 1.19.3, partial, pair complete: no. No full-pair audit or runtime validation.

## Independent source witness verification

`TeleportCommand` is byte-identical on both sides. Registration requires permission level 2; `/tp` redirects to it. The targets/location branch admits the supplied `/tp @s 0.7005 64.9 1.5`. `Vec3Argument`, `WorldCoordinates` and `WorldCoordinate` are also byte-identical. Decimal absolute coordinates acquire neither relativity nor integer X/Z center correction. `teleportToPos` 188–229 has no relative position-axis flags for these arguments (rotation flags may still be relative; they do not alter the position). `performTeleport` 257–324 floors to `(0,64,1)` and checks `Level.isInSpawnableBounds` (A 155–165; B 158–168); these coordinates pass the horizontal ±30,000,000 and vertical ±20,000,000 limits. Same-level `ServerPlayer` dispatch calls the connection after dismount/sleep handling. Choose the stated ordinary airborne, non-riding player.

`ServerGamePacketListenerImpl.teleport` A 994–1010/B 988–1004 sends the absolute coordinates; its position-offset bases are zero for absolute axes. The position packet's paired read/write codec preserves X/Y/Z as doubles. `ClientPacketListener.handleMovePlayer` A 563–628/B 592–667 assigns zero velocity on all absolute axes. A finishes through `absMoveTo`; B uses `setPos`. `Entity.absMoveTo` clamps X/Z but these coordinates are safely inside that range, and `setPos` 376–382 rebuilds the box via the current dimensions on both sides. The server command sets its own player onGround true afterward; this is not a local-client onGround write. The client handler sends an onGround-false movement packet and does not assign the client's ground flag. A pre-correction airborne local player remains valid for the witness.

`EntityDimensions.makeBoundingBox` 21–25 is identical. With ordinary player dimensions, float half-width is `0.30000001192092896`; maxX is `1.0005000119209289`. Honey's identical registered collision shape occupies local X/Z `[0.0625,0.9375]`, Y `[0,0.9375]`, so Honey at `(1,64,1)` has minimum solid X `1.0625`. The player's box remains to the left of that shape throughout the downward moves. `Entity.collide`, `collideBoundingBox`, `collideWithShapes`, `Shapes.collide` 204–214, `VoxelShape.collideX` 209–264 and swept `AABB.expandTowards` 145–171 preserve the requested vertical displacement because there is no overlap on X with a solid Honey voxel. Use the witness's isolated air arrangement without colliding entities or external impulses. There is no horizontal clipping or step-up path.

The ordinary local tick route is `LocalPlayer.aiStep` → superclass/player/living aiStep → `Player.travel` → ordinary `LivingEntity.travel` → `handleRelativeFrictionAndCalculateMovement` → `LocalPlayer.move` → inherited `Entity.move`. Zero inputs remain zero through the impulse copy and `0.98F` input damping. The four pre-travel closest-space probes use X approximately `0.4905` or `0.9105`, all in air cell X=0, so they do not push the player toward or away from Honey. Locally controlled aiStep clears interpolation steps. Its `0.003` component cutoff does not zero either subsequent falling velocity. Ordinary non-fluid/non-flight travel, no Slow Falling/Levitation/no-gravity, and no jump supply the witness; no stale swimming, climbing or external impulse is required.

`LivingEntity.travel` A 2042–2049/B 2052–2059 supplies gravity `0.08`; ordinary air processing A 2151–2173/B 2158–2180 moves before gravity/drag. Starting with zero velocity, the first pass leaves Y velocity `-0.0784000015258789`; the second consumes that and leaves `-0.1552320045166016`; the third consumes the latter and reaches Y approximately `64.66636799395752`. These numbers are static arithmetic checks preserving promotion of `0.98F`, not runtime observations. First and second Honey gates reject Y velocity 0 and approximately -0.0784 respectively.

On the third movement the unclipped downward displacement means `Entity.move` sets onGround false, retains position X/Z, runs the air/fall bookkeeping, and reaches `tryCheckInsideBlocks` at 657. The world is loaded, the Y scan is interior, the player is not removed/noPhysics, and this is SELF movement rather than the zero-piston bypass. The base air callbacks are inert. A's max scan X is `floor(1.0005000119209289 - 0.001)=0`; B's is `floor(1.0005000119209289 - 1.0E-7)=1`. Honey cell `(1,64,1)` is omitted only by A. Both retain the inclusive X/Y/Z order, floor conversion and height/chunk guard documented in R1; `ClientLevel.hasChunk` returns true, while the witness separately stipulates actual loaded Honey state.

Honey `isSlidingDown` 66–83 sees airborne state, Y below `64.9375 - 1.0E-7`, velocity below -0.08, and X separation approximately 0.7995 exceeding `0.4375 + 0.6F/2.0F` after the specified epsilon. Identical `doSlideMovement` 91–101 writes immediate Y -0.05 and resets fallDistance. The branch for velocity below -0.13 scales X/Z, which stay zero here. `Entity.deltaMovement` is written directly and read after move by the travel helper; gravity and drag then run, so -0.05 is not the asserted end-of-tick velocity. No ordinary walking arrival or measured trajectory/parity follows from this witness.

## Boundary and handoff decision

The exact paired behavior and a valid source-produced player state are now supported. The author correctly retains `unknown within (1.19.2, 1.19.3]`; R2 inspects no additional release sources and makes no narrower assertion. The campaign's incremental handoff gate forbids a missing required version boundary from entering implementation. Consequently the overall gate remains revision-required/blocked despite the verified applicability. This is a boundary-evidence limitation, not a rejection of the command witness, not a request for ordinary walking evidence, and not a runtime-testing requirement.

Next pointer: obtain and record source-backed exact first-change/release applicability evidence, bind it to an immutable snapshot, then seek the necessary blind boundary review. Retain the verified command-limited applicability and both prior review records. Do not mark the pair complete or hand this record off as implementation-ready.

## Integrity and checkpoint

The revised finding's raw Git object bytes were hashed without newline conversion and match the assigned SHA-256. Canonical read-only source readiness, provenance, source-manifest and artifact-manifest identities remain exactly those rehashed in R1 and were rehashed again for R2. New source files below were rehashed and matched the corresponding source-manifest entries. Existing Entity/Honey/registration/player/dimension identities match the frozen finding and R1. Shared parser, dimension and voxel helper files are byte-identical across the pair. Cited command, server teleport, client correction, dimension, VoxelShape, Shapes and AABB excerpt hashes were independently reproduced using UTF-8/LF without a terminal newline. No revised-artifact or unverified-original-equivalence claim is involved.

Only this new R2 review file is owned/changed. Root and branch were verified before writing and staging. Prior R1 bytes are preserved. Static diff checks only; no build, tests, game/TAS/Gym/server/Docker, shared-cache writes, implementation/wiki/mixed-report reads, main merge or outbound messages. Owned background processes: none. Submitted report commit is the final local HEAD; clean state is verified after commit.

### 1.19.2 additional full-file SHA-256

- net/minecraft/server/commands/TeleportCommand.java: `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a`
- net/minecraft/server/network/ServerGamePacketListenerImpl.java: `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2`
- net/minecraft/client/multiplayer/ClientPacketListener.java: `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`
- net/minecraft/world/entity/EntityDimensions.java: `ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502`
- net/minecraft/world/level/Level.java: `2ff6b7ab66c73f0fa41136ab83fb8076742c6bde7c3af10dd6f3170d6d536539`
- net/minecraft/commands/arguments/coordinates/Vec3Argument.java: `959b3955eb347f230125a5a08d51c09cf0433f5cd01ee305aca7ffb90d8977d4`
- net/minecraft/commands/arguments/coordinates/WorldCoordinates.java: `90c6dc406984c1bff6c895cc08d7d67be06de28a9c55b79bae245634c9cce449`
- net/minecraft/commands/arguments/coordinates/WorldCoordinate.java: `7abc758b0c816fd63a6537ece41c25e1a1f77852cda0c1e6f9b2317f3a691235`
- net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java: `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0`
- net/minecraft/world/phys/shapes/VoxelShape.java: `99f8b6e44e6c249b251d98a99e38158ebcb459733b26cc98bae15d51d3b87417`
- net/minecraft/world/phys/shapes/Shapes.java: `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a`
- net/minecraft/world/phys/AABB.java: `12134682c7f0c19a4df431e4509d661b866b84f680b6db82194af8aba7d0a50f`


### 1.19.3 additional full-file SHA-256

- net/minecraft/server/commands/TeleportCommand.java: `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a`
- net/minecraft/server/network/ServerGamePacketListenerImpl.java: `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`
- net/minecraft/client/multiplayer/ClientPacketListener.java: `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`
- net/minecraft/world/entity/EntityDimensions.java: `ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502`
- net/minecraft/world/level/Level.java: `df465bcb6eb6ceaeb423485070e00737102abb3da064e44c440f69f3aede61c1`
- net/minecraft/commands/arguments/coordinates/Vec3Argument.java: `959b3955eb347f230125a5a08d51c09cf0433f5cd01ee305aca7ffb90d8977d4`
- net/minecraft/commands/arguments/coordinates/WorldCoordinates.java: `90c6dc406984c1bff6c895cc08d7d67be06de28a9c55b79bae245634c9cce449`
- net/minecraft/commands/arguments/coordinates/WorldCoordinate.java: `7abc758b0c816fd63a6537ece41c25e1a1f77852cda0c1e6f9b2317f3a691235`
- net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java: `18f1e4f2ebc30510eefb53f139bb97402cf3cf4e1ba803f95d486e983a76e114`
- net/minecraft/world/phys/shapes/VoxelShape.java: `99f8b6e44e6c249b251d98a99e38158ebcb459733b26cc98bae15d51d3b87417`
- net/minecraft/world/phys/shapes/Shapes.java: `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a`
- net/minecraft/world/phys/AABB.java: `12134682c7f0c19a4df431e4509d661b866b84f680b6db82194af8aba7d0a50f`
