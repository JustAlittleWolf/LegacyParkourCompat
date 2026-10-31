# Final integrated source-route audit — Mojmap 1.15.2 → 1.16.5

## Verdict: INCOMPLETE — source pair cannot freeze

The latest ledger and its exact joins are internally consistent. It incorporates the corrected movement routes and later member-bounded closures. It still has 469 OPEN/PARTIAL changed-path rows, including 43 unresolved movement-positive paths, 425 OPEN no-movement-dependency rows that remain unclassified, and one PARTIAL IronBars state-generation row. The 43 movement-positive paths and their exact current member/dependency text are preserved in `final-unresolved-movement-positive-groups.tsv`. This is a row-scoped residual list; it does not impose whole-class or whole-application proof where the cited eligible members are closed.

This verdict concerns source coverage/freeze only. It does not withdraw member-scoped acceptance, and it makes no implementation or runtime-parity claim. Prior v2 audit verdicts and files are preserved unchanged; this report binds only to the latest inputs below.

## Bound artifacts and hashes

| Artifact | SHA-256 |
|---|---|
| Reconciled route ledger | `9fbf4f00a7b19d273522c0fe172255f0108397a05cf5ffdc6aac08a36f7615e3` |
| Remaining OPEN/PARTIAL rows | `1ce8bd34f29a43fd4446d92b6b69c254493b6df9df174476ed1d63288ecd2a95` |
| Reconciliation narrative | `6a704a2bbfce3e90fe5c157574fdb1db42b9189d2b5cdc4800cf9f335f5713c7` |
| Input hash manifest | `249e96fc0db421a16f4fa96ae3a034ffcf6f7884e9a75a96b6bdc42ba0728c71` |
| Movement-positive crosswalk (60 paths) | `57a837629b4ddbf0af744b0bd60bc0aba443c3dfdcf58695a19b6d83bba769f0` |
| Unresolved movement-positive subset (43 paths) | `478d19c78e1fbdde88bc4378594085d8e4fc5f1de8aa7a87d8521a7cf62418e7` |
| No-movement open-member scan (425 rows) | `0d318af387aa8c3d6282204989004b1f1c0f33d8ce0f9a9a14a25d613d273441` |
| Revised Leaves/Liquid/provider report | `d2e657d79ecc8b8fceab9d3b3e0ef56026515eddf4b90a5a343cac9496919406` |
| Leaves renewal review | `1cbafc57a2df29ada20b0a2c1578d6c406b433d38485cef0ca6d78951e8fdb06` |
| Client-sync report | `4f0650965318cb1d1ec3e46a5b933ca9d2dfc2dc819e4f03495f1e445d5a3c34` |
| Block-break acknowledgment audit | `ef99ee99113e2536540c73cba83a2b128598f86065391e4aaca592c070d20d46` |
| Collision collection residual report | `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c` |
| This report's copied 43-row residual list | `478d19c78e1fbdde88bc4378594085d8e4fc5f1de8aa7a87d8521a7cf62418e7` |

All 54 entries in `input-hashes.tsv` were independently rehashed: 54 present, zero missing, zero mismatches. The cited block-break, collision, client-sync, revised Leaves report, and renewal-review hashes also match their current files. The renewed Leaves review is ACCEPT and is explicitly bound to revised report hash `d2e657…19406`; the prior REVISE applies only to the superseded report hash.

## Exact joins and dispositions

- The reconciled ledger contains 2,617 rows with 2,617 unique ordinal `(Delta, exact-case Path)` keys. Counts: 310 `CLOSED_IN_SCOPE`, 35 `PARTIAL`, 434 `OPEN`, and 1,838 unchanged baseline rows. Routes: 193 movement-reachable, 585 no movement dependency, 1,839 excluded.
- `remaining-open-rows.tsv` has 469 rows and matches the OPEN/PARTIAL disposition totals (434 + 35).
- All 779 rows in the prior independent open-route checklist join on exact `(Delta, Path)` and endpoint hashes: zero missing keys and zero hash mismatches. Their latest dispositions total 310 closed, 35 partial, and 434 open; none is pending-source-audit.
- The 60-path movement-positive crosswalk joins exactly to the ledger by `(Delta, exact-case Path)` and endpoint hashes, with zero mismatches. It contains 17 closed, 9 open, and 34 partial rows. Its 43-row unresolved subset is exactly the 9 OPEN + 34 PARTIAL rows, with no missing or extra keys.
- The 425-row no-movement scan has no disposition assertion. Each row joins to a current OPEN `no movement dependency` ledger row and matching endpoint hashes. Keep all 425 explicitly unclassified: they are neither established eligible movement routes nor proven exclusions. They do not justify an application-wide reverse-caller proof rule; resolve at exact changed member/dependency scope. The remaining separate `IronBarsBlock`/wall neighbor-state row is PARTIAL.

## Corrected routes and bounded closures checked

`MultiPlayerGameMode.handleBlockBreakAck` is correctly movement-reachable and OPEN: the same-thread `ClientPacketListener.handleBlockBreakAck` caller can enter a direct `LocalPlayer` position restoration through `absMoveTo`. The residual is the old saved `PosAndRot` removal/correction predicate versus the new `Vec3`, same-level and `isColliding` gate, plus acknowledgment occurrence. It is distinct from server packet production.

Both exact FishingHook endpoints remain movement-reachable/PARTIAL: the event-31 response adds the same 0.1-scaled owner-to-hook vector to a hooked local player's velocity, while event production and target/synchronized-state ordering remain unresolved. `ClientChunkCache` is closed for the accepted conditional full-chunk cache/re-add/push witness; `ClientboundLevelChunkPacket` remains PARTIAL only for the stated full packet applied to a matching populated resident chunk with the local player and an overlapping pushable entity. No server resend-frequency or scheduling claim is made.

The client-sync integration's 11 packet/tag rows remain member-bounded to equal logical packet fields, fixed block/fluid state, and equal effective tag membership. The section-update carrier closure is likewise bounded to equal logical updates and fixed states. The paired client-sync report does not infer packet production or resource payloads. ClientPacketListener/ClientboundRespawnPacket remain PARTIAL only for ordinary-death sprint-attribute normalization before the first eligible local movement read.

The revised Leaves row is PARTIAL only under the exact conditional domain: a queried LeavesBlock state absent from effective `BlockTags.LEAVES` at both endpoints can pass old FULL face support but fail the new empty support shape, feeding `FlowingFluid.isSolidFace` and falling-fluid current consumed by LocalPlayer. Tagged at both endpoints is false at both; removal only on the newer side does not create the delta. Effective membership is not established by Java source alone. LiquidBlock is closed for movement-facing members under fixed sampled state; the old lava callback is covered by the separate occupancy/height closure and the newer stable-shape gate is unreachable for LocalPlayer because `canStandOnFluid` is false. Both Soul Sand case endpoints are closed for the fixed-state provider slice: same 14/16 collision shape, registry key, and `.speedFactor(0.4F)`; BubbleColumn scheduling/state production remains outside the equal-sampled-state comparison. AbstractClientPlayer constructor/FOV members are closed with the respawn yaw overwrite and renderer-only FOV consumer.

The serverbound abilities packet is correctly EXCLUDED: its consumer writes only `ServerPlayer.abilities.flying` after `mayfly`; this is not the clientbound packet's LocalPlayer sink. The `ServerGamePacketListenerImpl` row remains open only for its other changed members, not that closed serverbound ability slice.

The collision residual remains a concrete conditional movement witness: canonical full block, swimming LocalPlayer box, and a positive overlap below `1.25E-8` can be erased by old `Shapes.create(AABB)` grid snapping and accepted by the newer raw AABB fast path. The collision report bounds the route through the multi-axis `Entity.move` collector and does not assert occurrence frequency. Its exact collector, shape, AABB, entity and level dependencies remain among the unresolved positive rows.

## Remaining movement-positive member/dependency rows

The complete exact path/status/member-gate table is `final-unresolved-movement-positive-groups.tsv` (43 rows; SHA-256 above), copied byte-for-byte from the hash-bound latest `movement-positive-open-groups.tsv`. The rows retain these concrete residuals:

- Ordinary-death sprint attribute normalization (`ClientPacketListener`, `ClientboundRespawnPacket`).
- Changed block-break acknowledgment correction predicate and occurrence (`MultiPlayerGameMode.handleBlockBreakAck`).
- Conditional full-chunk duplicate-section/push witness (`ClientboundLevelChunkPacket`), and local-player qualifying move-entity receiver occurrence (`ClientboundMoveEntityPacket`).
- Other changed server-authored response/producer members, explicitly separated from server physics and the closed ability packet slice (`ServerPlayer`, `ServerGamePacketListenerImpl`).
- Conditional canonical-block collision grid-snap witness (`Entity`, `CollisionGetter`, `CollisionSpliterator`, `AABB`, `DiscreteVoxelShape`, `Shapes`, `VoxelShape`) and remaining member/caller dependencies (`LocalPlayer`, `LivingEntity`, `Player`, `BlockGetter`, `CollisionContext`, `EntityCollisionContext`, `EntityGetter`, `Level`, `LevelAccessor`, `LevelReader`, `BlockStateProperties`, `Vec3`).
- Event-time FishingHook target/synchronization boundary; projectile target/fall-flying state; teleport and boat/dismount response applicability (`world.entity.fishing.FishingHook`, `world.entity.projectile.FishingHook`, `FireworkRocketEntity`, `ThrownEnderpearl`, `Boat`, `DismountHelper`, `ChorusFruitItem`).
- Leaves same-absence tag gate; remaining existing block registration/provider and neighbor-state routes (including `Blocks`, `WallBlock`, and `PistonMovingBlockEntity`); and the full water/lava occupancy/current/provider/source-cell joins (`FlowingFluid`, `Fluid`, `FluidState`, `FluidStateImpl`, `LavaFluid`, `WaterFluid`).

The copied TSV supplies the exact gate text for each source row; the grouped bullets above are navigation only and do not replace it. The 425 no-movement rows are excluded from this eligible-route list while they remain unclassified.

## Freeze boundary

The source pair cannot freeze on this record: 43 movement-positive routes/gates and 426 other unresolved rows (425 unclassified OPEN plus one PARTIAL IronBars state-generation dependency) remain. Resolve those paths only at the exact eligible changed member and dependency level; retain closures already established for narrower members. Acceptance, implementation, and runtime parity remain separate.

No product/wiki/backlog/build/tests/game/decompile work was performed.
