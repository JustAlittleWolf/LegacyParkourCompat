# Ladder, Vine, and SnowLayer support-state production boundary

## Exact source identity

Compared `1.11.2` → `1.12.2`, namespace `ornithe-feather`, Feather Gen2 mappings (`feather-gen2-1.11.2+build.2` and `feather-gen2-1.12.2+build.2`), Vineflower output. Provenance records the exact `decompileMinecraft --versions=1.10.2,1.11.2,1.12.2 --mappings=feather` run. The ready markers pin the source manifests to SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` (1.11.2) and `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (1.12.2). Each class hash below was recomputed and matched its entry in that version's `ornithe-feather.sources.sha256`.

| Class | 1.11.2 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/block/LadderBlock.java` | `3ceafedeb39e0bc3862bed2cc784f490340d832563764adb225ecea40ee83ee7` | `c9d59cd266af92a731710fda0e2a8e60c61fb07131fecdd3b04759277ae24d94` |
| `net/minecraft/block/VineBlock.java` | `b06bafd1f06a7a75ef0239e87f190afe9709dff05ccbeee72144222c2c1c558e` | `3ee4302208ba5820e5fa7f3b166988b673ba691dc78823d1ccfd5e62712368ac` |
| `net/minecraft/block/SnowLayerBlock.java` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | `6ed1d83d9230cd3c1434b4ef5055963048763eea4dbdeaa6a58dd3b8591b915b` |

## Direct movement reads on unchanged raw states

In both versions, ladder `getShape` selects its unchanged box from stored `FACING`; snow `getShape` selects from stored `LAYERS` and `getCollisionShape` uses that shape and the layer count. In both versions Vine `getCollisionShape` returns `EMPTY_BLOCK_SHAPE`. Its `getShape` does resolve virtual `UP`, but this affects the outline shape only: the collision provider returns empty without calling `getShape`.

In 1.12.2, `World.getCollisions` dispatches through `BlockState.addCollisions` → `Block.getCollisionShape` (`World.java:964–1001`, `StateDefinition.java:375–376`, `Block.java:372–373,387–388`). Neither this path nor `Entity`/`LivingEntity` movement methods call `getFaceShape`. The climbing predicate `LivingEntity.isClimbing` reads the raw block identity (`LivingEntity.java:1006–1017`; Ladder and Vine both qualify) and does not inspect FaceShape or Vine's virtual `UP` (`VineBlock.java:46–55,84–86`). Thus FaceShape introduces no direct movement difference when the raw world state is unchanged.

## State production and side gates

- **Ladder:** 1.11.2 `canBePlaced` and `canSurvive` use `BlockState.isSolid` (`LadderBlock.java:55–57,91–93`); 1.12.2 `canAttach` requires a non-exception attachment block, directional `FaceShape.SOLID`, and `!isSignalSource` (`:55–68`). `getPlacementState` uses that test to select stored `FACING` (`:72–83`); `neighborChanged` uses it to remove unsupported ladders (`:87–94`).
- **SnowLayer:** 1.11.2 support validation uses the special ice/packed-ice exclusion, leaves, full snow below, or `isSolidRender && material.blocksMovement` (`SnowLayerBlock.java:79–89`). 1.12.2 adds the barrier exclusion and tests the support's upward face for `FaceShape.SOLID`, retaining the leaves and full-snow cases (`:84–93`). `neighborChanged` calls the same survival predicate (`:96–103`). The raw-state shape and collision calculations are unchanged.
- **Vine:** 1.11.2 attachment checks use `isCube && material.blocksMovement` and `resolveVirtualProperties` sets only ephemeral `UP` from `blocksAmbientLight` (`VineBlock.java:84–121`). In 1.12.2, `resolveVirtualProperties` derives ephemeral `UP` from the block above's downward FaceShape (`:84–86`); `canBePlaced`/`canSurvive` and attachment tests use directional face shape plus explicit exclusions (`:105–120`). Its `neighborChanged` callback is server-guarded and scheduled growth/update logic is inside `!world.isClient` (`:157–166`). The ephemeral `UP` change is not a stored-state write.

Placement is not exclusively server-produced. `ClientPlayerInteractionManager.useBlock` calls `ItemStack.useOn` on the client (`ClientPlayerInteractionManager.java:300,349,354`); `ItemStack.useOn` dispatches to `BlockItem.useOn` (`ItemStack.java:140–141`). `BlockItem.useOn` first checks `canBeReplaced` and chooses the hit position or adjacent position (`BlockItem.java:32–37`), then calls `world.canPlace`, `getPlacementState`, and `world.setBlockState(..., 11)` (`:39–43`). `World.canPlace` invokes `block.canBePlaced` without a logical-side gate (`World.java:2265–2275`). Vine remains replaceable; SnowLayer is replaceable only at one layer (`VineBlock.java:99–101`; `SnowLayerBlock.java:149–153` in 1.11.2 and `:151–155` in 1.12.2). Consequently the 1.12.2 FaceShape gates can affect client-predicted placement: ladder stored facing, and whether a Vine or SnowLayer block is locally placed. Those resulting raw states can feed movement collision or the climbing predicate.

By contrast, `World.setBlockState` invokes neighbor updates only on the non-client branch (`World.java:281–287`), and `World.neighborChanged` itself returns without dispatching on clients (`:401–406`). The support-loss/neighbor-derived Ladder, Vine, and SnowLayer evolution therefore runs on the logical server. Client block update packets apply server states directly through `ClientPlayNetworkHandler.handleBlockUpdate`/`handleBlocksUpdate` → `ClientWorld.setBlockStateFromPacket` → `World.setBlockState` (`ClientPlayNetworkHandler.java:630–635,670–674`; `ClientWorld.java:237–242`); this packet application does not run client neighbor callbacks.

## Bounded disposition and next action

No new movement finding is supported for FaceShape calls during direct movement queries over unchanged raw states. The client-predicted placement path is a real vanilla-state production boundary and can change the raw states movement reads. Under the project invariant to keep vanilla block states, that boundary needs a coordinator scope ruling; do not dismiss it as server-only. Next action: coordinator decides whether this narrow local-placement prediction path belongs in source closure for this pair, then closes or assigns only that bounded state-production coverage. This report makes no pair-freeze claim.
