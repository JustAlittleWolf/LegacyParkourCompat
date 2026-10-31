# Independent final route audit follow-up — Mojmap 1.15.2 → 1.16.5

## Verdict: ACCEPT the revised route reconciliation; pair coverage remains incomplete

This accepts the revised reconciliation as an accurate, source-bounded route record. It repairs the v2 false negative for `MultiPlayerGameMode.handleBlockBreakAck`, keeps the newly verified collision-query candidate explicitly partial, and leaves the remaining no-movement candidates unclassified/open rather than asserting they are resolved. This ACCEPT does not freeze the pair or establish accepted findings, implementation, or runtime parity. Prior audit v2 remains unchanged and retains its historical REVISE verdict.

## Bound inputs and digest verification

| Artifact | SHA-256 |
|---|---|
| Corrected inventory `path-status-ordinal-v2.tsv` | `835ba455d3dd4825f01a74f282eee21c42254cee3870a400ee7f96d9824fbcd5` |
| Corrected route CSV `ordinal-route-v2/path-routing-ledger.csv` | `cb64edf4ebeb6b1a71a7f468272d2bb2a51f9812de5e5915426fc98d15e650ef` |
| Latest reconciled ledger | `7f996ec476c54b51b58f184cf1329c6b8559f4bc0066a5904783c3c754a9f278` |
| Latest remaining-open rows | `68d9ea8845b273345a424c89ebaa150bea953da512e9c7ba9b907b0eea6f2785` |
| Latest reconciliation narrative | `799ffea52f8c84d695209cf86d188d11c8ec3612bdcbb45a8407b6b972c601d1` |
| 60 movement-positive groups | `c5a67cbf5aa337501e81785e81a18b5e7c4762de5faf9cd25c0c039b827a899b` |
| 425 no-movement OPEN checklist | `0d318af387aa8c3d6282204989004b1f1c0f33d8ce0f9a9a14a25d613d273441` |
| Focused block-break acknowledgment audit | `ef99ee99113e2536540c73cba83a2b128598f86065391e4aaca592c070d20d46` |
| Collision collection residual report | `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c` |
| Historical route audit v2, unchanged | `f1fb1a01ee3858a92f4fffce0ce44d26b3b01b19a1d2336535e004b300bfdf87` |

All listed digests rehash. The latest reconciliation `input-hashes.tsv` has 43 records; all 43 rehash with no missing/mismatched artifact. It includes the collision report SHA and collision source/input sidecars. Its row evidence references the historical route audit v2 rather than the newer focused block-break report; I separately rehashed and incorporated the focused report above. The focused report confirms the same row and source hashes, so the omission is provenance bookkeeping, not a route/evidence contradiction.

The collision report's own eight input hashes all match; its 31 source-hash rows contain 30 present files whose SHA-256 values rehash and one expected-absent 1.15.2 `CollisionSpliterator.java`, with no mismatch. The 1.16.5 `CollisionSpliterator` and all other cited source hashes match the exact source roots.

## Structural joins and current counts

The inventory has 3,799 exact-case paths and the corrected route CSV has 2,617 non-identical paths. I repeated the case-sensitive ordinal path/status join: 2,617 distinct routed paths, zero missing route keys, zero status mismatches, and zero unrouted non-identical inventory rows. The 2,617-row latest ledger has unique exact paths and joins the inventory with no status/hash/presence mismatch. The 486-row unresolved set is exact: 19 OPEN movement-positive + 41 PARTIAL movement-positive + 425 OPEN no-movement-route + 1 PARTIAL no-movement-route. No pending source/Realms rows remain.

The corrected route totals are 194 movement-reachable, 585 no-movement-dependency, and 1,838 excluded. The former 779-row independent open-route checklist joins fully: 293 CLOSED_IN_SCOPE, 42 PARTIAL, 444 OPEN; zero paths are missing. These are route/coverage dispositions only, not acceptance or runtime results.

## Confirmed correction and collision residual

`net/minecraft/client/multiplayer/MultiPlayerGameMode.java` is now `movement-reachable` / `OPEN`. The 1.15.2→1.16.5 same-thread `ClientPacketListener.handleBlockBreakAck` caller reaches `minecraft.gameMode.handleBlockBreakAck`; the client method can restore `Minecraft.player` position from matching unacknowledged action state. The old source restores saved position and rotation under its broader correction gate; the new source stores only `Vec3`, requires a changed acknowledged block state, matching client level, and collision with the acknowledged state, and restores position only. `Minecraft.player` is `LocalPlayer`, and the inherited `absMoveTo` immediately updates position/bounds. This establishes a changed, conditional direct local-player response without asserting server packet frequency or a trajectory. Exact source line ranges and hashes are in `block-break-ack-movement-audit/report.md` and the ledger row; that report SHA is bound above.

The collision report is correctly carried into the residual groups. It closes block cursor order, the default always-true collector predicate, and LocalPlayer entity-candidate filtering. The remaining candidate is the canonical `Shapes.block()` fast path: old `Shapes.create(AABB)` snaps accepted endpoints to the 1/2/4/8 grid before the AND join, while the new `CollisionSpliterator` fast path tests raw `queryAabb.intersects` against block bounds. A LocalPlayer swimming box swept on multiple axes can produce a positive overlap `0 < δ < 1.25E-8` that old snapping erases but new raw intersection accepts. This stays partial/open; no setup frequency or trajectory is claimed. Exact residual rows are `CollisionSpliterator`, `CollisionGetter`, `Shapes`, `DiscreteVoxelShape`, `VoxelShape`, `AABB`, and the linked `Entity.move` / LocalPlayer multi-axis caller path, with exact member/gate wording and hashes in the 60-row group table.

## Exclusion and open-scan audit

The requested exclusion rows now have explicit, source-bounded dispositions: `MouseHandler` is closed to input-device plumbing; `Timer.advanceTime` is closed to fixed-tick scheduling; `WalkNodeEvaluator`, `FlyNodeEvaluator`, and `TurtleNodeEvaluator` are closed to non-player `Mob` navigation. Server-side producers remain separate from client receiver semantics. Do not infer server physics from packet routes: compare client receiver behavior under equal decoded fields, and retain packet/state interleaving as a separate gate.

The 425-row no-movement scan is **not a resolved/no-dependency proof**. Every row joins a distinct OPEN ledger row with matching route and endpoint hashes, and reconciliation explicitly calls it a checklist, not proof of eligibility or exclusion. Each row has the generic negative target text; 83 one-sided paths do not have paired exact member anchors (they say source added/removed). This does not impose whole-application closure: the remaining work is per listed member or one-sided class member, followed only far enough to establish a scoped player sink or concrete exclusion. Preserve the 425 rows as explicitly unclassified OPEN; do not count them as closed. One additional no-movement-route PARTIAL row is `IronBarsBlock.java`, still open for neighbor/state generation.

## Exact unresolved movement-positive paths

The 60-row source table below is bound by its SHA above and contains each exact path, source hashes, covered member anchors, and `OpenMemberOrDependency`. It joins exactly to the unresolved ledger: 19 OPEN and 41 PARTIAL. This is the retained path/member set, not 60 accepted findings.

| Status | Exact path |
|---|---|
| PARTIAL | `net/minecraft/client/multiplayer/ClientPacketListener.java` |
| OPEN | `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` |
| OPEN | `net/minecraft/client/player/AbstractClientPlayer.java` |
| PARTIAL | `net/minecraft/client/player/LocalPlayer.java` |
| OPEN | `net/minecraft/network/protocol/game/ClientboundBlockUpdatePacket.java` |
| OPEN | `net/minecraft/network/protocol/game/ClientboundChunkBlocksUpdatePacket.java` |
| OPEN | `net/minecraft/network/protocol/game/ClientboundLevelChunkPacket.java` |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundMoveEntityPacket.java` |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundRespawnPacket.java` |
| OPEN | `net/minecraft/network/protocol/game/ClientboundSectionBlocksUpdatePacket.java` |
| PARTIAL | `net/minecraft/network/protocol/game/ClientboundUpdateTagsPacket.java` |
| OPEN | `net/minecraft/network/protocol/game/ServerboundPlayerAbilitiesPacket.java` |
| PARTIAL | `net/minecraft/server/level/ServerPlayer.java` |
| OPEN | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` |
| PARTIAL | `net/minecraft/tags/BlockTags.java` |
| PARTIAL | `net/minecraft/tags/FluidTags.java` |
| PARTIAL | `net/minecraft/tags/StaticTags.java` |
| PARTIAL | `net/minecraft/tags/SynchronizableTagCollection.java` |
| PARTIAL | `net/minecraft/tags/Tag.java` |
| PARTIAL | `net/minecraft/tags/TagCollection.java` |
| PARTIAL | `net/minecraft/tags/TagContainer.java` |
| PARTIAL | `net/minecraft/tags/TagManager.java` |
| PARTIAL | `net/minecraft/world/entity/Entity.java` |
| PARTIAL | `net/minecraft/world/entity/LivingEntity.java` |
| PARTIAL | `net/minecraft/world/entity/fishing/FishingHook.java` |
| PARTIAL | `net/minecraft/world/entity/player/Player.java` |
| PARTIAL | `net/minecraft/world/entity/projectile/FireworkRocketEntity.java` |
| PARTIAL | `net/minecraft/world/entity/projectile/FishingHook.java` |
| PARTIAL | `net/minecraft/world/entity/projectile/ThrownEnderpearl.java` |
| PARTIAL | `net/minecraft/world/entity/vehicle/Boat.java` |
| OPEN | `net/minecraft/world/entity/vehicle/DismountHelper.java` |
| OPEN | `net/minecraft/world/item/ChorusFruitItem.java` |
| OPEN | `net/minecraft/world/level/BlockGetter.java` |
| PARTIAL | `net/minecraft/world/level/CollisionGetter.java` |
| PARTIAL | `net/minecraft/world/level/CollisionSpliterator.java` |
| PARTIAL | `net/minecraft/world/level/EntityGetter.java` |
| PARTIAL | `net/minecraft/world/level/Level.java` |
| PARTIAL | `net/minecraft/world/level/LevelAccessor.java` |
| PARTIAL | `net/minecraft/world/level/LevelReader.java` |
| PARTIAL | `net/minecraft/world/level/block/Blocks.java` |
| OPEN | `net/minecraft/world/level/block/LeavesBlock.java` |
| OPEN | `net/minecraft/world/level/block/LiquidBlock.java` |
| OPEN | `net/minecraft/world/level/block/SoulSandBlock.java` |
| OPEN | `net/minecraft/world/level/block/SoulsandBlock.java` |
| PARTIAL | `net/minecraft/world/level/block/WallBlock.java` |
| PARTIAL | `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` |
| OPEN | `net/minecraft/world/level/block/state/properties/BlockStateProperties.java` |
| PARTIAL | `net/minecraft/world/level/material/FlowingFluid.java` |
| PARTIAL | `net/minecraft/world/level/material/Fluid.java` |
| PARTIAL | `net/minecraft/world/level/material/FluidState.java` |
| PARTIAL | `net/minecraft/world/level/material/FluidStateImpl.java` |
| PARTIAL | `net/minecraft/world/level/material/LavaFluid.java` |
| PARTIAL | `net/minecraft/world/level/material/WaterFluid.java` |
| PARTIAL | `net/minecraft/world/phys/AABB.java` |
| OPEN | `net/minecraft/world/phys/Vec3.java` |
| OPEN | `net/minecraft/world/phys/shapes/CollisionContext.java` |
| PARTIAL | `net/minecraft/world/phys/shapes/DiscreteVoxelShape.java` |
| OPEN | `net/minecraft/world/phys/shapes/EntityCollisionContext.java` |
| PARTIAL | `net/minecraft/world/phys/shapes/Shapes.java` |
| PARTIAL | `net/minecraft/world/phys/shapes/VoxelShape.java` |

The exact member-level dependencies for these paths remain in the hash-bound source table `.native-source/20261030/1152-1165-finish43/route-reconciliation/movement-positive-open-groups.tsv`. They group as follows:

- Client receivers/player state: `ClientPacketListener`, `MultiPlayerGameMode`, `AbstractClientPlayer`, `LocalPlayer`; block/chunk update and entity move/respawn/tag packets; server ability/respawn producers. Remaining gates include block-break acknowledgment response; respawn attribute sync timing; packet/state interleaving; local tick member/dependency closure; and conditional local-player packet occurrence.
- Tags and dynamic state: `BlockTags`, `FluidTags`, `StaticTags`, `SynchronizableTagCollection`, `Tag`, `TagCollection`, `TagContainer`, `TagManager`. Effective datapack/resource/custom/synchronized membership and its use on a specific local tick remain open.
- Entity/player response: `Entity`, `LivingEntity`, `Player`, both FishingHook class paths, `FireworkRocketEntity`, `ThrownEnderpearl`, `Boat`, `DismountHelper`, `ChorusFruitItem`. Separate direct client responses from server-only origin, vehicle/non-player physics, and packet synchronization; the exact conditional residuals are in each row.
- Existing block providers/state: `Blocks`, `LeavesBlock`, `LiquidBlock`, `SoulSandBlock`, `SoulsandBlock`, `WallBlock`, `PistonMovingBlockEntity`, `BlockStateProperties`. Exact remaining gates include provider/neighbor state evolution, wall/IronBars upper-state and tag updates, and live piston block-entity survival/update order into collision queries.
- Collision/query/fluid geometry: `BlockGetter`, `CollisionGetter`, `CollisionSpliterator`, `EntityGetter`, `Level`, `LevelAccessor`, `LevelReader`, `FlowingFluid`, `Fluid`, `FluidState`, `FluidStateImpl`, `LavaFluid`, `WaterFluid`, `AABB`, `Vec3`, `CollisionContext`, `DiscreteVoxelShape`, `EntityCollisionContext`, `Shapes`, `VoxelShape`. Keep the canonical full-block fast-path witness, remaining ordinary Entity.move/pose/auto-jump collision callers, and fluid occupancy/current/jump/provider/tag/source-cell joins as separate bounded residuals.

The exact source member names, prior bounded coverage, endpoint hashes, and open gate for every path are preserved in the 60-row table; that row-level table is the authoritative detailed residual list for this follow-up.

## Separate status axes

- **Route reconciliation:** accepted as structurally complete and honest about unresolved rows. The corrected MultiPlayerGameMode route, collision residual, exclusions, and exact residual sets verify.
- **Pair coverage/freeze:** incomplete. The 486 unresolved rows above and the source tick/provider/tag dependencies remain open; no full-pair freeze is claimed.
- **Source acceptance:** not changed by this audit; no canonical finding acceptance is issued.
- **Implementation/runtime:** not evaluated. No build, test, decompilation, game, TAS, Gym, server launch, or playback was run.
