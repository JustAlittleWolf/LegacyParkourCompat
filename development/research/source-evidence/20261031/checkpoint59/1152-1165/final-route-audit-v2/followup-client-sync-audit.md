# Independent client-sync route audit follow-up — Mojmap 1.15.2 → 1.16.5

## Verdict: ACCEPT the bounded client-sync integration; full-pair freeze is NOT READY

The latest reconciliation and its 11 packet/tag closures are internally consistent and properly bounded to equal packet fields, fixed block/fluid state, and equal effective tag membership. The 49 unresolved movement-positive rows and 425 OPEN no-movement checklist rows remain explicitly unresolved. The reconciliation does not claim pair freeze; this independent review agrees. Prior follow-up verdicts in `audit.md` and `followup-audit.md` remain unchanged.

## Bound inputs and hashes

| Artifact | SHA-256 |
|---|---|
| Latest reconciled ledger | `f94538ffed8a78a118bea0170f4aa746e139ff714b7a6def43f4f3f9f3cd2447` |
| Remaining-open rows | `1587c7e206f7367e8fc7b2c071d853a2455b8fdf01c175bb47a48fe96f71566c` |
| Reconciliation narrative | `b16aafa170e28b17132135909aa6059139f226b1025fad576e5c438d3c90c454` |
| 60-path movement-positive crosswalk | `1bdcb50cbfe9ce9a817f7e8452dcc89a9cd155c4ce0997e9f1ac2faccdf37b06` |
| 49-path unresolved movement-positive list | `6d53a121fbe401cc1fbfe8cfec03d1cd765c8f6442b85de67c24581635032328` |
| 425-path no-movement OPEN checklist | `0d318af387aa8c3d6282204989004b1f1c0f33d8ce0f9a9a14a25d613d273441` |
| Client-sync residual report | `4f0650965318cb1d1ec3e46a5b933ca9d2dfc2dc819e4f03495f1e445d5a3c34` |
| Prior block-break acknowledgment audit | `ef99ee99113e2536540c73cba83a2b128598f86065391e4aaca592c070d20d46` |
| Prior collision collection residual report | `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c` |

All seven requested latest digests match. The reconciliation `input-hashes.tsv` contains 47 entries; all 47 rehash without missing or mismatched inputs. The client-sync report SHA matches its `report.sha256`; its eight input hashes all match, and its 14-row source manifest has 24 present endpoint files rehashed successfully plus four exact absent-endpoint cases, with no source hash mismatch. Both the block-break and collision report hashes match their cited artifacts.

## Exact inventory, ledger, and crosswalk checks

- Corrected inventory: 3,799 exact-case path rows. Corrected route CSV: 2,617 distinct non-identical paths. The case-sensitive ordinal path/status join has zero missing keys, mismatches, or unrouted non-identical inventory rows.
- Latest reconciled ledger: 2,617 unique route rows. The unresolved file has 475 unique rows and matches ledger OPEN/PARTIAL rows exactly.
- Prior independent open-route checklist: all 779 rows join to the ledger; dispositions are 304 CLOSED_IN_SCOPE, 34 PARTIAL, 441 OPEN; no PENDING_SOURCE_AUDIT rows.
- The 60-path movement-positive crosswalk joins exactly by path and endpoint SHA-256 to the ledger. It contains 11 CLOSED_IN_SCOPE rows, 16 OPEN rows, and 33 PARTIAL rows. The unresolved positive list is the exact 49-row OPEN/PARTIAL subset (16 + 33), with no duplicate, missing, misrouted, or hash-mismatched paths.
- The 425-path no-movement checklist joins exactly to 425 unique OPEN `no movement dependency` rows, with matching endpoint hashes. It is still explicitly a checklist, not proof of movement eligibility or exclusion. Its generic target text has not been promoted to closure; 83 one-sided paths still lack paired member anchors. Keep those 425 OPEN and member-scoped. Do not replace this with a whole-application caller-proof threshold or claim it resolved.
- Remaining status counts: 16 OPEN movement-positive + 425 OPEN no-movement; 33 PARTIAL movement-positive + one PARTIAL no-movement (`IronBarsBlock.java`) = 475 unresolved total. Route totals remain movement-reachable 194 / no movement dependency 585 / excluded 1,838.

## Eleven packet/tag closures

The 11 paths below are `CLOSED_IN_SCOPE` in the 60-path crosswalk, and their ledger rows have no remaining member/dependency field. The client-sync report traces their relevant receiver/consumer members and holds equal raw logical packet fields, fixed block/fluid states, and equal effective block/fluid tag membership. It does not generalize to changed payload state, dynamic membership differences, or server packet production/scheduling.

| Exact path | Bounded premise / consumer |
|---|---|
| `net/minecraft/network/protocol/game/ClientboundBlockUpdatePacket.java` | Equal decoded position/state and fixed applied block state; receiver applies same state, no direct LocalPlayer writer. |
| `net/minecraft/network/protocol/game/ClientboundChunkBlocksUpdatePacket.java` | Equal logical position/state updates and fixed applied states; packet batching/production excluded. |
| `net/minecraft/network/protocol/game/ClientboundUpdateTagsPacket.java` | Equal effective tag membership at the movement read; client-thread application/representation only. |
| `net/minecraft/tags/BlockTags.java` | Equal effective block tag membership at climbability readers. |
| `net/minecraft/tags/FluidTags.java` | Equal effective fluid tag membership at water/lava movement readers. |
| `net/minecraft/tags/StaticTags.java` | Same block/fluid lookups under equal bound sets. |
| `net/minecraft/tags/SynchronizableTagCollection.java` | Old collection vs newer binding/sync representation under equal resulting sets. |
| `net/minecraft/tags/Tag.java` | Membership lookup returns same result under equal member sets. |
| `net/minecraft/tags/TagCollection.java` | Old lookup vs new container binding under equal membership. |
| `net/minecraft/tags/TagContainer.java` | New bound lookup supplies equal movement predicate values. |
| `net/minecraft/tags/TagManager.java` | Reload/binding role only under equal effective membership; no server-send-timing claim. |

The full member anchors, endpoint hashes, evidence, and premise are retained in `movement-positive-route-groups.tsv` and `client-sync-residual/report.md`. The two client block update rows are not confused with the separate `ClientboundLevelChunkPacket` cache/push route.

## Retained respawn and full-chunk gates

- **Respawn:** `ClientPacketListener` and `ClientboundRespawnPacket` remain PARTIAL only for the ordinary-death sprint state candidate. A newly constructed LocalPlayer can retain `sprinting=true` while its fresh local attribute map lacks the +0.3F sprint-speed modifier; `Player.getSpeed`/grounded `LivingEntity.travel` consume that value. The precise remaining gate is whether default-attribute dirty/sync/update normalizes the map before the first eligible local movement read. The report does not broaden this to other attributes, every respawn, or a generic packet-interleaving blocker.
- **Full chunk:** `ClientChunkCache` is CLOSED_IN_SCOPE for its exact conditional equal-payload cache replacement → duplicate tracked-entity section entries → query duplication → repeated `Entity.push` velocity write to an overlapping LocalPlayer. Its independent review conditionally accepts that source witness. `ClientboundLevelChunkPacket` remains PARTIAL for the same witness; the gate is a full packet applied to a matching resident populated chunk with the LocalPlayer and an overlapping pushable entity present, followed by the local `LivingEntity.pushEntities` query. This does not assert server resend frequency or scheduling. General collision/fluid dependencies remain separate.

## Exact remaining movement-positive routes/gates (49 paths)

The rows below copy the unresolved positive paths and their exact `OpenMemberOrDependency` text from the hash-bound 49-row input. These are the remaining eligible source routes/gates; the table does not assert that every candidate is an accepted behavioral finding.

| Status | Path | Exact remaining gate/dependency |
|---|---|---|
| PARTIAL | `net/minecraft/client/multiplayer/ClientPacketListener.java` | Only named gate remains here: whether default-attribute sync/update normalizes the new local attribute map before the first eligible movement read after ordinary death respawn, when sprint=true is retained but the sprint-speed modifier is absent. |
| OPEN | `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | Bound the old saved-PosAndRot removal/correction predicate and new Vec3/matching-level/isColliding gate, including acknowledgment occurrence/timing; caller is ClientPacketListener.handleBlockBreakAck → Minecraft.gameMode.handleBlockBreakAck. Do not conflate with server packet production. |
| OPEN | `net/minecraft/client/player/AbstractClientPlayer.java` | No later exact artifact identified for the changed eligible member(s) and callers in AbstractClientPlayer.java; exact member-to-LocalPlayer tick/collision/fluid route remains unclosed. |
| PARTIAL | `net/minecraft/client/player/LocalPlayer.java` | Changed LocalPlayer.aiStep/move/underwater and inherited fluid/collision/member dependencies not explicitly paired in the cited closures; dynamic tags and ordinary tick/state ordering remain open. |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundLevelChunkPacket.java` | Conditional receiver occurrence: a full-chunk packet is applied while a matching resident chunk already contains the local player and an overlapping pushable entity; this produces duplicate old-side section entries. No server packet frequency/scheduling claim is made. |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundMoveEntityPacket.java` | Occurrence of the qualifying packet sequence for the local player is not established; server packet production and ordering are outside this receiver audit. |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundRespawnPacket.java` | Default-attribute dirty/sync timing for ordinary death respawn relative to the first eligible LocalPlayer movement read; this is the same exact gate documented for ClientPacketListener. |
| OPEN | `net/minecraft/network/protocol/game/ClientboundSectionBlocksUpdatePacket.java` | Exact receiver/producer member-to-movement-consumer dependency in 'net/minecraft/network/protocol/game/ClientboundSectionBlocksUpdatePacket.java' remains unclosed, including packet/state interleaving against the next local tick for affected position, motion, ability, attribute, effect, entity-data, tag or respawn state; target: movement modifier/tag/inbound synchronization dependency |
| OPEN | `net/minecraft/network/protocol/game/ServerboundPlayerAbilitiesPacket.java` | Exact receiver/producer member-to-movement-consumer dependency in 'net/minecraft/network/protocol/game/ServerboundPlayerAbilitiesPacket.java' remains unclosed, including packet/state interleaving against the next local tick for affected position, motion, ability, attribute, effect, entity-data, tag or respawn state; target: movement modifier/tag/inbound synchronization dependency |
| PARTIAL | `net/minecraft/server/level/ServerPlayer.java` | Other changed server-authored player-response producer gates and applicability to client historical movement remain unclosed; server physics is outside client-only scope. |
| OPEN | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | Exact receiver/producer member-to-movement-consumer dependency in 'net/minecraft/server/network/ServerGamePacketListenerImpl.java' remains unclosed, including packet/state interleaving against the next local tick for affected position, motion, ability, attribute, effect, entity-data, tag or respawn state; target: movement modifier/tag/inbound synchronization dependency |
| PARTIAL | `net/minecraft/world/entity/Entity.java` | Exact fast-path grid-snap witness in collision-collection-residual/report.md: canonical full block, swimming LocalPlayer box and sub-1.25E-8 positive overlap can alter x clipping; occurrence/setup frequency remains unclaimed. |
| PARTIAL | `net/minecraft/world/entity/LivingEntity.java` | Full water/lava/current/provider/tag graph and remaining changed movement members/callers are not closed as one local tick dependency set. |
| PARTIAL | `net/minecraft/world/entity/fishing/FishingHook.java` | Event-31 producer path and event-time target/synchronized-state ordering into the local-player response; the follow-up leaves event production and introduction boundary unclosed. |
| PARTIAL | `net/minecraft/world/entity/player/Player.java` | Remaining changed Player methods and full call-order coupling across pose, fluid, vehicle/passenger, effect and attribute state remain open. |
| PARTIAL | `net/minecraft/world/entity/projectile/FireworkRocketEntity.java` | Synced attached-target id and fall-flying input state at the client tick remain prerequisites not closed by this writer slice. |
| PARTIAL | `net/minecraft/world/entity/projectile/FishingHook.java` | Event-31 producer path and event-time target/synchronized-state ordering into the local-player response; the follow-up leaves event production and introduction boundary unclosed. |
| PARTIAL | `net/minecraft/world/entity/projectile/ThrownEnderpearl.java` | Server-origin teleport production/timing and applicability to local historical profile remain open. |
| PARTIAL | `net/minecraft/world/entity/vehicle/Boat.java` | Applicability of server-only dismount algorithm and authoritative sync disposition for client historical profile remain open. |
| OPEN | `net/minecraft/world/entity/vehicle/DismountHelper.java` | Exact changed member(s), reachable caller chain and movement-state dependency in 'net/minecraft/world/entity/vehicle/DismountHelper.java' remain unclosed; target: possible direct player velocity/position/passenger response |
| OPEN | `net/minecraft/world/item/ChorusFruitItem.java` | Exact changed member(s), reachable caller chain and movement-state dependency in 'net/minecraft/world/item/ChorusFruitItem.java' remain unclosed; target: movement modifier/tag/inbound synchronization dependency |
| OPEN | `net/minecraft/world/level/BlockGetter.java` | No later exact artifact identified that closes an eligible member and its caller in 'net/minecraft/world/level/BlockGetter.java'; the exact changed-member route remains open. Target: ordered local-player tick/input/travel/collision/fluid graph |
| PARTIAL | `net/minecraft/world/level/CollisionGetter.java` | Conditional canonical Shapes.block() query route: old Shapes.create(AABB) can round a sub-1.25E-8 positive overlap to tangency, while the new raw queryAabb.intersects fast path accepts it; see exact witness in collision-collection-residual/report.md. |
| PARTIAL | `net/minecraft/world/level/CollisionSpliterator.java` | For query AABB [(0.375,0.25,0.25)..(1+δ,0.875,0.875)], 0<δ<1.25E-8, old Shapes.create rounds x=1 and omits shape; new raw overlap accepts it. LocalPlayer swimming box swept by (0.025+δ,0.025,0.025) can change Entity.move x clipping. |
| PARTIAL | `net/minecraft/world/level/EntityGetter.java` | General Entity.move entity filtering and remote/entity-to-local-player collision/push selection outside pose-fit remain open. |
| PARTIAL | `net/minecraft/world/level/Level.java` | Other changed Level movement/world-query members and full client local-player contact/collision callers remain open. |
| PARTIAL | `net/minecraft/world/level/LevelAccessor.java` | Other changed LevelAccessor movement/query methods and ordinary Entity.move route dependencies remain open. |
| PARTIAL | `net/minecraft/world/level/LevelReader.java` | Other changed LevelReader movement-query methods and general Entity.move collision route remain open. |
| PARTIAL | `net/minecraft/world/level/block/Blocks.java` | Remaining changed registrations for existing block providers and full BlockStateBase dispatch/state-neighbor/callback consumers remain open. |
| OPEN | `net/minecraft/world/level/block/LeavesBlock.java` | Exact changed provider/member and caller dependencies in 'net/minecraft/world/level/block/LeavesBlock.java' remain unclosed through BlockBehaviour.BlockStateBase -> CollisionGetter/CollisionSpliterator -> Entity.move, including neighboring-state/callback dependencies where applicable; target: source body declares shape/contact/friction/jump/speed provider used by movement graph |
| OPEN | `net/minecraft/world/level/block/LiquidBlock.java` | Exact changed provider/member and caller dependencies in 'net/minecraft/world/level/block/LiquidBlock.java' remain unclosed through BlockBehaviour.BlockStateBase -> CollisionGetter/CollisionSpliterator -> Entity.move, including neighboring-state/callback dependencies where applicable; target: source body declares shape/contact/friction/jump/speed provider used by movement graph |
| OPEN | `net/minecraft/world/level/block/SoulsandBlock.java` | Exact changed provider/member and caller dependencies in 'net/minecraft/world/level/block/SoulsandBlock.java' remain unclosed through BlockBehaviour.BlockStateBase -> CollisionGetter/CollisionSpliterator -> Entity.move, including neighboring-state/callback dependencies where applicable; target: legacy Blocks.SOUL_SAND registration → Soul Sand collision-shape/contact provider → Entity movement |
| OPEN | `net/minecraft/world/level/block/SoulSandBlock.java` | Exact changed provider/member and caller dependencies in 'net/minecraft/world/level/block/SoulSandBlock.java' remain unclosed through BlockBehaviour.BlockStateBase -> CollisionGetter/CollisionSpliterator -> Entity.move, including neighboring-state/callback dependencies where applicable; target: same legacy Blocks.SOUL_SAND registration → Soul Sand collision-shape/contact provider → Entity movement |
| PARTIAL | `net/minecraft/world/level/block/WallBlock.java` | Neighbor/state generation for IronBars connection, upper-block LOW/TALL update, neighbor notifications, WALLS/WALL_POST_OVERRIDE tags, and supplied side mask remain open. |
| PARTIAL | `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | Fixed-state getCollisionShape SHORT cutoff and getCollisionRelatedBlockState/moveCollidedEntities push volume are source-covered; live client BE/state survival and block-update order into a local collision query remain open. |
| OPEN | `net/minecraft/world/level/block/state/properties/BlockStateProperties.java` | Exact changed provider/member and caller dependencies in 'net/minecraft/world/level/block/state/properties/BlockStateProperties.java' remain unclosed through BlockBehaviour.BlockStateBase -> CollisionGetter/CollisionSpliterator -> Entity.move, including neighboring-state/callback dependencies where applicable; target: ordered local-player tick/input/travel/collision/fluid graph |
| PARTIAL | `net/minecraft/world/level/material/FlowingFluid.java` | Complete occupancy/current/provider and lava-flow graph joined to LivingEntity.aiStep/travel, including tag/resource and source-cell dependencies, remains open. |
| PARTIAL | `net/minecraft/world/level/material/Fluid.java` | Complete changed Fluid interface/provider member-to-consumer closure for occupancy, jump thresholds and travel remains open. |
| PARTIAL | `net/minecraft/world/level/material/FluidState.java` | Other changed fluid-state movement members and full provider/tag/tick dependencies remain open. |
| PARTIAL | `net/minecraft/world/level/material/FluidStateImpl.java` | Complete old implementation-to-new provider/member mapping and all movement consumer dependencies remain open. |
| PARTIAL | `net/minecraft/world/level/material/LavaFluid.java` | Complete lava current/flow provider and tag/source-cell consumer route into ordinary local travel remains open. |
| PARTIAL | `net/minecraft/world/level/material/WaterFluid.java` | Complete water occupancy/current provider plus dynamic fluid-tag/source-cell dependencies into local tick remain open. |
| PARTIAL | `net/minecraft/world/phys/AABB.java` | Conditional query: positive overlap δ<1.25E-8 after old grid rounding would still end at x=1; new raw query AABB accepts overlap against the canonical Shapes.block() at (1,0,0). |
| OPEN | `net/minecraft/world/phys/Vec3.java` | Exact changed member(s), reachable caller chain and movement-state dependency in 'net/minecraft/world/phys/Vec3.java' remain unclosed; target: ordered local-player tick/input/travel/collision/fluid graph |
| OPEN | `net/minecraft/world/phys/shapes/CollisionContext.java` | Exact collision member chain in 'net/minecraft/world/phys/shapes/CollisionContext.java' remains unclosed through CollisionGetter/CollisionSpliterator predicates, Shapes/VoxelShape/AABB, entity filtering and precise Entity.move/pose/auto-jump callers; target: ordered local-player tick/input/travel/collision/fluid graph |
| PARTIAL | `net/minecraft/world/phys/shapes/DiscreteVoxelShape.java` | Exact remaining gate: canonical Shapes.block() fast path with a sub-1.25E-8 positive overlap erased by old grid snapping but accepted by new raw AABB intersection; local-player multi-axis movement witness is in collision-collection-residual/report.md. |
| OPEN | `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | Exact collision member chain in 'net/minecraft/world/phys/shapes/EntityCollisionContext.java' remains unclosed through CollisionGetter/CollisionSpliterator predicates, Shapes/VoxelShape/AABB, entity filtering and precise Entity.move/pose/auto-jump callers; target: ordered local-player tick/input/travel/collision/fluid graph |
| PARTIAL | `net/minecraft/world/phys/shapes/Shapes.java` | Keep the source-established local-player witness open: query AABB [(0.375,0.25,0.25)..(1+δ,0.875,0.875)], 0<δ<1.25E-8; old grid snap ends at x=1 (tangent), new raw intersection accepts δ. It is reached through CollisionSpliterator in a multi-axis Entity.move query. |
| PARTIAL | `net/minecraft/world/phys/shapes/VoxelShape.java` | Canonical full-block fast-path witness only; keep query coordinate/grid gate and local-player multi-axis caller as named in collision-collection-residual/report.md. |

These exact rows leave the following bounded source gates open: `MultiPlayerGameMode.handleBlockBreakAck` old saved `PosAndRot` correction predicate versus new `Vec3`/same-level/`isColliding` gate and acknowledgment occurrence; ordinary-death respawn default-attribute normalization timing; conditional full-chunk packet/cache witness; conditional move-entity packet receiver path; local player tick/collision/fluid member dependencies; dynamic tag membership only where the equal-membership premise does not apply; entity-origin/vehicle response boundaries; existing block shape/contact providers and neighbor/update timing; live piston state survival; full fluid occupancy/current/provider/source-cell joins; and collision shape/query dependencies, including the accepted canonical full-block grid-snap witness. The row-level table names the exact member/gate per path.

## Freeze and status boundaries

Full-pair freeze is **not ready**: 475 changed-path rows remain OPEN/PARTIAL, including 49 movement-positive routes and 425 OPEN no-movement rows not yet semantically closed, plus the IronBars neighbor/state PARTIAL. The client-sync closures shrink the open set but do not satisfy full endpoint route classification, terminal coverage, or complete dependency closure. This is a source coverage verdict only; it does not change source-finding acceptance, implementation, or runtime parity. No product/wiki/backlog work, build/test, decompilation, or game/TAS/Gym/server launch was performed.
