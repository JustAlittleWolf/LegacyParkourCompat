# Independent final route audit v2 — Mojmap 1.15.2 → 1.16.5

## Verdict: REVISE

The corrected structural join and supplied closure evidence are internally consistent, but the reconciled route ledger still has a concrete false negative: `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` remains `no movement dependency` while its changed `handleBlockBreakAck` member directly restores local-player position, and its behavior differs across the pair. The no-movement residual scan therefore cannot support its blanket target label as a route conclusion. Correct this row to movement-reachable and retain it as an unresolved movement response until its conditional behavior is accepted/closed. This audit does not freeze the pair or establish finding acceptance, implementation, or runtime parity.

## Input identity and joins

All supplied top-level input identities match their requested SHA-256 values:

| Input | SHA-256 | Rows |
|---|---|---:|
| Corrected exact-case inventory `path-status-ordinal-v2.tsv` | `835ba455d3dd4825f01a74f282eee21c42254cee3870a400ee7f96d9824fbcd5` | 3,799 |
| Corrected route CSV `ordinal-route-v2/path-routing-ledger.csv` | `cb64edf4ebeb6b1a71a7f468272d2bb2a51f9812de5e5915426fc98d15e650ef` | 2,617 |
| Revised reconciliation | `0d57ff46c03527d5ca0414783fbad49bad579ecc404e68fec9fcbc098ead4ea9` | — |
| Reconciled route ledger | `87301879dfdcba00d1594ad15de91de3398dfcdc05812345d74390246fb92609` | 2,617 |
| Remaining-open rows | `1b23c75def1134ebc40e71c60654c3044b4122bddf4d6c448d6270788a5abcfe` | 491 |
| No-movement open-member scan | `881e078d4db4587d03dee1c49e8abd88b13f9d7aeab8baab93ad40e9ed1858b1` | 430 |

I independently joined route `Path`/`Delta` to inventory `path`/`status` with case-sensitive ordinal keys: 2,617 distinct rows, zero missing keys, zero status mismatches, and zero non-identical inventory paths left unrouted. Reconciled ledger join: 2,617 distinct rows, zero missing keys/status mismatches. Its unresolved set exactly joins the 491-row remaining-open file (41 PARTIAL + 450 OPEN; no pending rows). All 39 records in route-reconciliation `input-hashes.tsv` rehash successfully. The inventory source identity and row counts agree with the independent full-route report.

## Verified route corrections and later evidence

- **FishingHook correction is supported.** Both exact-case old/new class paths are movement-reachable in the reconciled ledger. The paired client event-31 handler reaches `bringInHookedEntity`, which adds the same 0.1-scaled owner-to-hook vector to a hooked local player’s existing velocity in both endpoints. Event production and synchronized-target timing remain separate dependencies; the response itself is same-form. The corrected totals stated in the reconciliation (193 movement-reachable / 586 no-movement / 1,838 excluded) are supported before the additional correction below.
- **Packet receiver closure is member-bounded and credible.** The receiver report establishes that local-player IDs reach the move/teleport branches. It documents the changed small-distance teleport coordinate fallback as a direct local position response, and the conditional raw zero-delta move coordinate case as a second receiver candidate. The latter remains conditional on a qualifying packet sequence; no server production claim is made. Ability/login decode/application slices are closed only under equal decoded fields. Packet/state interleaving remains an open timing dependency.
- **ClientChunkCache closure is member-bounded and credible.** Its full-chunk replacement → repeated entity section entries → duplicate push query → local-player impulse route is conditional on a full chunk being applied to an already populated chunk. It does not rely on changed server scheduling and is correctly retained as movement-reachable/partial.
- **Realms closure is properly bounded.** Its 81 exact rows join by path and both endpoint hashes. Its report traces login/session transport outside Realms member operations and the saved `Data.Player` cleanup through the integrated-server load path; those do not establish a live Realms member movement write. This does not close other client/server packet routes.

## False negative: `MultiPlayerGameMode.handleBlockBreakAck`

The no-movement scan has an exact changed-member anchor for `handleBlockBreakAck`, but its shared target text says “not on ordered graph and no direct route identified from path/body scan.” Exact Mojmap source disproves that route result:

- 1.15.2 `MultiPlayerGameMode.handleBlockBreakAck` removes the saved `PosAndRot`; when its correction predicate is true it calls `clientLevel.setKnownState`, then calls `this.minecraft.player.absMoveTo(savedX, savedY, savedZ, savedYaw, savedPitch)` whenever the saved value exists.
- 1.16.5 changes the saved record to `Vec3`, changes the outer correction predicate, and only then calls `player.absMoveTo(savedX, savedY, savedZ)` when the client level matches the player level and `player.isColliding(blockPos, acknowledgedState)` is true. The old and new routes therefore have different gates; the old also restores saved yaw/pitch while the new call is position-only.
- Both `ClientPacketListener.handleBlockBreakAck` methods run the same-thread client receiver and directly call `minecraft.gameMode.handleBlockBreakAck(level, packet position, packet state, action, success)`. This is a client response to an acknowledgment packet, not server-side player physics.

Exact paired source references: `MultiPlayerGameMode.handleBlockBreakAck` at 1.15.2 `MultiPlayerGameMode.java:424–434` and 1.16.5 `:434–444`; receiver call at 1.15.2 `ClientPacketListener.java:2354–2365` and 1.16.5 `:2380–2391`. Exact source hashes (also agree with the corrected route/inventory endpoints): old `MultiPlayerGameMode.java` `d69f96ab32445bdc5a2cfa1f7ca718d9213d107b7690a15799b660cd3548a4b4`; new `MultiPlayerGameMode.java` `80d1b28891b33de967cbc26e13acec097fb94091bbc226f92ad5ba2750015b66`; old/new `ClientPacketListener.java` `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` / `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`. The source-level route is movement-reachable and its changed conditional position/rotation response warrants an eligible candidate; packet occurrence/timing is not claimed. This row adds at least one route correction to the 430-scan set. If only routing totals are updated, the provisional result becomes 194 movement-reachable / 585 no-movement / 1,838 excluded; its disposition must remain open/partial pending bounded behavior review.

## Scope exclusions and the 430-row residual

The 430-row scan is useful as a method-anchor checklist, but it does **not** establish “no movement dependency” for each row: its identical `Target` text is only a negative path/body-scan observation and it missed the direct sink above. There are 347 paired-path rows with exact method anchors and 83 one-sided paths represented only as “source added/removed; exact endpoint absent.” Neither blanket text nor a row anchor alone closes a route.

This is not a requirement to prove the whole application. Close each residual by following the listed changed member(s), or an added/removed class’s concrete members, to the scoped player movement sink or to a specific exclusion boundary. Stop that chain once source proves its producer/consumer is UI/render/audio/config, server-only physics, non-player physics, or another named exclusion. Do not require reverse-call coverage of unrelated application code. The ledger’s remaining-member wording is generally member-scoped, but the 430 scan cannot be used as proof that all those methods are genuine movement candidates—or as proof that they are absent from the route.

Examples show why the list needs route-specific handling:

- `MultiPlayerGameMode.handleBlockBreakAck` is the false negative above.
- `MouseHandler` is movement-adjacent only through cursor/mouse input. Its pair changes add screen file-drop dispatch (`onDrop`/`setup`, new-side lines 151–170) and `cursorEntered()` setting `ignoreFirstMove` (new-side line 301); this is input-device plumbing, an explicit project exclusion. It should be closed to that boundary rather than kept as an unresolved physics candidate. No changed player travel/collision method is present in this class.
- `Timer.advanceTime` changes from storing `ticks` on the object to returning the local tick count; this is client scheduling/timer plumbing, not a changed operation within a fixed player movement tick. Route it to the fixed-tick boundary, not a broad movement dependency search.
- `WalkNodeEvaluator`, `FlyNodeEvaluator`, and `TurtleNodeEvaluator` changed members navigate/evaluate `Mob` paths. Their direct outputs are non-player AI navigation; this is excluded non-player physics unless a separate source trace proves an in-scope direct local-player response. Merely calling collision or block-query helpers does not make the evaluator a local-player movement provider.
- The `PortalForcer`/`PortalInfo` one-sided route and `ServerPlayer`/server listener producer rows must remain separated from client receiver semantics. Server-side portal/entity movement and packet production are not client movement execution; only a changed local receiver operation for equal packet fields, or a separately established direct client response, can enter this scope. Do not close or accept these from server producer code alone.
- `BlockPos` contains changed spatial enumeration helpers, but the new `betweenClosedStream(AABB)` overload is consumed by `BlockGetter.getBlockStates(AABB)`; the only in-root uses found are the default getter method and structure/util consumers, not the `Entity.move` collision stream. This particular overload is not proof of a player collision delta. Other changed members/callers still require exact bounded disposition.

Because a confirmed eligible client response remains misrouted and some broad residuals have explicit exclusion boundaries while others still need member-level routes, the 430 rows cannot be collectively closed or collectively called movement-eligible. At least the MultiPlayerGameMode false negative must be corrected; clearly excluded members above can be closed specifically; the exact remaining row/member checklist stays the input `remaining-open-rows.tsv` (491 unresolved rows, with its hash above).

## Exact movement-reachable residual checklist

The reconciled open set has 60 movement-reachable rows: 20 OPEN and 40 PARTIAL. These are the precise route groups not to lose during reconciliation:

- Client/input/receiver: `MouseHandler` (input-device exclusion to close), `ClientPacketListener` (partial; includes the missed block-break-ack response and packet interleaving), `AbstractClientPlayer`, `LocalPlayer`, `ClientboundBlockUpdatePacket`, `ClientboundChunkBlocksUpdatePacket`, `ClientboundLevelChunkPacket`, `ClientboundMoveEntityPacket`, `ClientboundRespawnPacket`, `ClientboundSectionBlocksUpdatePacket`, `ClientboundUpdateTagsPacket`, `ServerboundPlayerAbilitiesPacket`, `ServerPlayer`, `ServerGamePacketListenerImpl`.
- Tags and state: `BlockTags`, `FluidTags`, `StaticTags`, `SynchronizableTagCollection`, `Tag`, `TagCollection`, `TagContainer`, `TagManager`.
- Player/entity response and origin: `Entity`, `LivingEntity`, `Player`, both FishingHook paths, `FireworkRocketEntity`, `ThrownEnderpearl`, `Boat`, `DismountHelper`, `ChorusFruitItem`.
- Existing blocks/providers: `Blocks`, `LeavesBlock`, `LiquidBlock`, `SoulsandBlock`, `SoulSandBlock`, `BlockStateProperties`, `WallBlock`, `PistonMovingBlockEntity`.
- Collision/query/fluid geometry: `BlockGetter`, `CollisionGetter`, `CollisionSpliterator`, `EntityGetter`, `Level`, `LevelAccessor`, `LevelReader`, `FlowingFluid`, `Fluid`, `FluidState`, `FluidStateImpl`, `LavaFluid`, `WaterFluid`, `AABB`, `CollisionContext`, `DiscreteVoxelShape`, `EntityCollisionContext`, `Shapes`, `VoxelShape`, `Vec3`.

The detailed exact members/dependencies for those 60 paths are in the input `remaining-open-rows.tsv`; it is retained unchanged and hash-bound above. The 430 no-movement member checklist is not a substitute for resolving these movement-positive residuals.

## Separation of status axes

- **Coverage/routing:** structural inventory and route joins are complete; semantic routes remain incomplete and at least one no-movement classification is false.
- **Source acceptance:** this audit creates no canonical finding and does not bind an accepted source finding. The block-break-ack path is a source-confirmed route/candidate only.
- **Implementation:** not evaluated.
- **Runtime parity:** not evaluated. No tests, build, decompilation, game/TAS/Gym/server launch, or playback was performed.
