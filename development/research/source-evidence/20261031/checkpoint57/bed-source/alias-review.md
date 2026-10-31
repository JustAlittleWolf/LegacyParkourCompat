# Exact applicability: 1.16 and 1.16.1 bed wake alias

## Verdict

**The complete bed stand-up search/support algorithm and its `LivingEntity.stopSleeping` call are exact aliases in 1.16 and 1.16.1.** No within-group difference was found in candidate iteration, gates, dangerous-block rejection, support height, epsilon, result construction, fallback, or direct local-player route. A version-specific implementation split between these two releases is unsupported by the inspected exact sources. This is a bounded applicability finding only; it does not establish the 1.16.1→1.16.2 boundary by itself, prove a producer trajectory, or claim runtime parity.

## Evidence identity and integrity

Canonical read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

| Release | Exact identity | Mapping | Source-set manifest SHA-256 |
|---|---|---|---|
| 1.16 | `mojmap.ready.json`: versionId `1.16`, mapping `mojmap`, status `ready`, 3572 files | Mojmap / `client_mappings.txt` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `mojmap.ready.json`: versionId `1.16.1`, mapping `mojmap`, status `ready`, 3572 files | Mojmap / `client_mappings.txt` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |

For each release, the `BedBlock.java` and `LivingEntity.java` SHA-256 values below match both their bytes and that release's `mojmap.sources.sha256` entry. The complete relevant files are byte-identical across the two releases, so all statements below—including operator order and gates—are covered by exact whole-file identity.

| Source file | 1.16 SHA-256 | 1.16.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/BedBlock.java` | `47fff9ab0073a8970ffd0a0db3120903b43e4246bf0f932687bdee9e6e6de063` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | same |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | same |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | same |

The `1.16` source sidecar `mojmap.provenance.json` identifies exact version metadata and client artifacts and a successful Mojmap/Vineflower preparation. The `1.16.1` sidecar identifies the exact `1.16.1` input in the `1.16.1,1.16.2` Mojmap/Vineflower preparation. Each ready marker binds its source-set manifest hash above.

## Compared method groups

- 1.16 and 1.16.1 `net/minecraft/world/level/block/BedBlock.java`, `findStandUpPosition(EntityType<?>, LevelReader, BlockPos, int)` (lines 227–255) and `getStandingLocationAtOrBelow(EntityType<?>, LevelReader, BlockPos)` (257–288).
- 1.16 and 1.16.1 `net/minecraft/world/entity/LivingEntity.java`, `stopSleeping()` (3016–3032).
- Direct local-player path: both releases' `ClientPacketListener.handleAnimate` handles action `2` by calling `Player.stopSleepInBed(false, false)` (lines 917–937); `Player.stopSleepInBed` calls `super.stopSleeping()` (1289–1296). These two route source files are byte-identical between the releases as well.

## Algorithm and route observations

Both bed candidate searches read the bed facing and iterate `m = 0..1`, then x outer / z inner over inclusive 3×3 ranges. For each cell they call the support helper; a present result returns immediately for index `i <= 0`, otherwise decrements `i`. Neither release uses yaw ordering or the 1.16.2 offset helper.

Both support helpers first reject an initial collision shape whose maximum Y exceeds `0.4375`. They then descend while Y is at least zero, descent from the original candidate is at most two blocks, and the current collision shape is empty. An empty final support shape fails. Support Y is calculated in the same order as `mutableBlockPos.getY() + voxelShape2.max(Y) + 2.0E-7`; candidates whose original Y minus that result exceeds `2.0` fail. Both construct the centered `Vec3`, obtain `entityType.getAABB`, then require `levelReader.noCollision(aABB)` and no matching dangerous block in `aABB.expandTowards(0.0, -0.2F, 0.0)` before returning it. This covers the supportY epsilon, lower/upper search limits, candidate ordering, collision gate, dangerous-block predicate, and acceptance order.

Both `LivingEntity.stopSleeping` implementations, after the same sleeping-position/chunk and bed checks, clear bed occupancy, call `BedBlock.findStandUpPosition(this.getType(), this.level, blockPos, 0)`, and use the same fallback position `above + (0.5, 0.1, 0.5)` when absent. They immediately call `setPos` with the selected/fallback point, then capture position, set standing pose, set that position again, and clear sleeping position, with identical ordering.

The client route is direct: the action-2 receiver invokes `Player.stopSleepInBed(false,false)`; that method immediately calls `super.stopSleeping()`, reaching the algorithm and direct `setPos` writes above. `Player` has no alternate stand-up algorithm here. The receiver has no local-player identity check, so this observation establishes that a represented player entity uses the direct stop-sleep path; it does not establish a packet producer, that the receiver selected only the local player, or a movement trajectory.

## Boundary relation and limits

The supplied accepted 1.16.1→1.16.2 finding (SHA-256 `508f35100e17eadb6a0d94b688d899b9012c01f08bf20816af680f0fc885d4bc`) attributes its change to the paired candidate search and support/helper route. This audit confirms that its 1.16.1 endpoint aliases 1.16 exactly. The previous evidence says the yaw-dependent ordering/helper route begins in 1.16.2; this audit does not independently renew that newer-side review or establish an exact patch commit/build boundary between releases.

No 1.15.2 comparison was needed to answer the bounded alias question.
