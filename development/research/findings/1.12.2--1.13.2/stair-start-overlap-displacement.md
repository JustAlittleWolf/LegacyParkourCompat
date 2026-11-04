# Stair start-overlap displacement

**Bounded source finding for 1.12.2 → 1.13.2.** Exact input snapshot: `build/source-audit/1.12.2--1.13.2-stair-start-overlap-witness.md`, SHA-256 `AC7842519865941555FF8772BC508A61D66FC3C81C62F2D28D4533151A2A3C28`. This is a distinct ordinary-player movement finding. It does not absorb adjacent stair-shape or world-collision findings.

## Exact source pair

Both endpoint trees are Ornithe Feather build.2, Vineflower 1.12.0, Java 25.0.3. Source-manifest SHA-256 values: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The exact source-file hashes are retained in the immutable source audit above.

## Reachable one-tick witness

Use one bottom, straight, south-facing stair at `(0,0,0)` in otherwise empty space. Its collision geometry is the lower half `[0,0,0]..[1,0.5,1]` plus the south upper half `[0,0.5,0.5]..[1,1,1]` in both versions. In 1.12.2 this is emitted as two collision boxes by `StairsBlock.getCollisionShapes` (`StairsBlock.java:66–101`). In 1.13.2 the same union is assembled from selected half-cell shapes in `StairsBlock.java:37–81` (south has horizontal id 0 and chooses mask 12; `Direction.java:19,89`; `StairsBlock.java:53,99–104`).

Place a vanilla player with feet at y=`0.25`, center x=`0.5`, center z=`0.5 - (double)(0.6F / 2.0F)` = `0.199999988079071044921875`. The player is airborne (`onGround=false`), and has standard width `0.6F` and height `1.8F`. `Entity.setPosition` computes half-width as float before promoting it to double (`Entity.java:298–305` in 1.12.2; `:309–316` in 1.13.2), giving this starting box:

- X `[0.1999999880790710449, 0.8000000119209289551]`
- Y `[0.25, 2.0499999523162841797]`
- Z `[-0.1000000238418579102, 0.5]`

The box positively overlaps the lower stair base and exactly touches the upper step at `maxZ=0.5`; it does not penetrate the upper step. This exact center avoids rounding the z value to `0.2`, which would already penetrate the upper box.

An absolute vanilla server teleport establishes the start position: 1.12.2 `TeleportCommand.java:77–95`, `ServerPlayNetworkHandler.java:538–558`; 1.13.2 `TeleportCommand.java:174–214,232–254`, `ServerPlayNetworkHandler.java:801–814`. The command also sets the server entity grounded and zeros vertical velocity (`TeleportCommand.java:104–107` / `:290–293`), while the client position handler applies packet coordinates through `updatePositionAndAngles` directly (`ClientPlayNetworkHandler.java:578–612` / `:621–655`), which calls `Entity.setPosition` without collision resolution and does not copy the server's `onGround` assignment. Thus the stated airborne local-player state is reachable when it is airborne before the correction.

On the next local-player tick, hold forward at yaw 0 with no fluid, flight, climbing, or effects. Input is damped by `0.98F`, then the air movement branch uses `speedInAir=0.02F` (`LivingEntity.java:1844–1847,1485–1493` in 1.12.2; `:1922–1926,1545–1553` in 1.13.2). `Entity.updateVelocity` adds `(float)(0.98F * 0.02F)` to Z velocity, the exactly represented float `0.0196000002324581146240234375` promoted to double (`Entity.java:1049–1066` / `:1057–1074`). `Entity.move` therefore receives `dz=0.019600000232458115...` (`Entity.java:567` / `:580,594`), reached through the local player's ordinary tick chain (`LocalClientPlayerEntity.tick` → `PlayerEntity.tick` → `LivingEntity.tick`; 1.12.2 lines `165,174/204,1619–1620`; 1.13.2 `181,176/208,1689–1690`).

**1.12.2 result:** `Entity.move` queries expanded collision boxes and resolves each intersecting stair AABB independently (`Entity.java:567,595`; `Block.java:378–383`). The swept query includes both stair boxes. The lower base overlaps the starting box but does not clip positive Z; the upper step is separated by exactly zero, so `Box.intersectZ` changes positive movement to zero (`Box.java:225–243`). Result: `z` displacement `0`.

**1.13.2 result:** `Entity.move` requests the moving collision stream (`Entity.java:580,594`), and `WorldView` supplies the original player shape as `excludeBlock` (`WorldView.java:158–164`). Because the stair's union shape intersects the starting player box through the lower base, block collection excludes the entire stair shape (`WorldView.java:136–142`), including its distinct upper component. In the specified empty neighborhood no other candidate remains. `VoxelShapes.calculateMaxOffset` returns the requested distance for the empty stream (`VoxelShapes.java:194–204`). Result: `z` displacement `0.019600000232458115...`.

The difference is a one-tick ordinary local-player displacement at this exact start state, caused by legacy per-AABB candidates versus newer whole-block VoxelShape exclusion. It does not establish a general stair rule or any other collision provider's result.

## Applicability and open gates

This is **pair-only endpoint evidence for 1.12.2 → 1.13.2**. It does not establish the first changed release or an applicable implementation cutover; intervening-version coverage remains incomplete. Pair coverage remains partial and other collision providers/dependencies remain unexamined. Implementation remains unresolved: no product Change or hook disposition is asserted here. Runtime parity is unverified; no runtime test was performed.

# Stair start-overlap displacement: release boundary supplement

This supplements the bounded one-tick witness recorded in `build/source-audit/1.12.2--1.13.2-stair-start-overlap-witness.md` (source-witness SHA-256 `AC7842519865941555FF8772BC508A61D66FC3C81C62F2D28D4533151A2A3C28`). It binds the witness to the first inspected release containing whole-shape exclusion. It does not close generic collision-collector coverage.

## Exact release identities

All listed sources use Ornithe Feather Gen2, build.2, and Vineflower. The exact version IDs and manifest digests are:

- 1.12.2: `feather-gen2-1.12.2+build.2-mergedv2.jar`; source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13: `feather-gen2-1.13+build.2-mergedv2.jar`; source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; exact client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`.
- 1.13.1: `feather-gen2-1.13.1+build.2-mergedv2.jar`; source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; exact client SHA-256 `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`.
- 1.13.2: `feather-gen2-1.13.2+build.2-mergedv2.jar`; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; exact client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.

Ready metadata identifies those exact release IDs and mapping builds. The independent boundary reviewer (`plan_recent_implementation_records`) reports that the 1.13 and 1.13.1 source-manifest digests match their ready metadata and that 12 selected relevant source files match their respective manifests. The reviewer independently confirmed whole-shape exclusion in 1.13, persistence through 1.13.1 and 1.13.2, and the loaded-target-chunk precondition. The accepted 1.12.2→1.13.2 endpoint witness records matching source hashes for its cited files. No separate 1.13/1.13.1 rerun or new verification marker is required for this bounded release-boundary supplement under the discovery workflow. This does not imply pair-wide coverage or generic collector acceptance.

## Earliest verified occurrence

**The earliest inspected release with whole-shape exclusion is 1.13.** In 1.13 `WorldView.getBlockCollisions` (`net/minecraft/world/WorldView.java:113-176`), the collision query gets each block's complete collision `VoxelShape`; for each candidate it checks whether the translated exclude shape overlaps that block shape and, when it does, returns no collision shape for that block (`:153-176`). `Entity.move` supplies the player shape to `WorldView.getCollisions` (`net/minecraft/entity/Entity.java:580-583`), which passes it as `excludeBlock` to that collector (`WorldView.java:191-210`). The stair's lower base overlaps the starting player shape, so the entire stair VoxelShape, including the upper component that face-touches the player, is excluded from that query.

1.13.1 and 1.13.2 retain the same whole-shape rule in the stream collector: `WorldView.getBlockCollisions` builds each block collision shape, translates `excludeBlock` into block-local coordinates, and returns an empty shape when `VoxelShapes.overlap(..., BooleanOp.AND)` is true (`WorldView.java:112-151` in both). Their `getCollisions(entity, box, dx, dy, dz)` methods pass the current entity box as the exclusion shape (`:154-164`), and `Entity.move` consumes the resulting collision stream to calculate axis offsets (`Entity.java:580-594`). Thus there is no observed later onset or reversion across the inspected 1.13 release line.

The paired 1.12.2 source uses per-AABB collision candidates: `Entity.move` requests a list of block collision boxes and clips each candidate independently (`Entity.java:567, 595`; cited with the block provider in the endpoint witness). Its stair upper AABB is not overlapped by the player's starting box, so it remains a clipping candidate even though the separate lower AABB overlaps. The resulting earliest boundary supported by checked releases is 1.13 (between 1.12.2 and 1.13); no uninspected release cutover is inferred.

## Bounded movement witness and prerequisites

Use the exact straight, bottom, south-facing stair at `(0,0,0)` and player position, AABB, yaw, input, velocity, tick call chain, and per-release stair geometry from the linked endpoint witness. At the stated start, the player's box has positive-volume overlap with the stair's lower base and exact face contact with its upper south step. With forward movement toward positive Z, 1.12.2 clips that displacement to zero against the upper AABB; 1.13's whole-shape exclusion omits the stair collision shape and permits the requested `0.019600000232458115...` Z displacement.

Preconditions are: the target stair is a straight, bottom stair at the stated position; the player is locally airborne with the stated box and reset vertical velocity; the next local-player tick uses forward input 1 at yaw 0, with no fluid, flight, climbing, or effects; the stair's chunk is loaded so its collision state is queried; and no other colliding block shapes or entities intersect the swept player box. `WorldView` checks `isChunkLoaded` before collecting a block collision shape (1.13 `WorldView.java:131`; 1.13.1/1.13.2 `:130`). Unloaded positions are omitted; the claimed positive displacement only relies on the loaded stair collision candidate being excluded as a whole.

## Scope boundary

This release-boundary check covers only this stair-start-overlap collision query and its one-tick displacement witness. It does not establish every provider, callback, world-border case, entity interaction, or generic collision-collector behavior.

