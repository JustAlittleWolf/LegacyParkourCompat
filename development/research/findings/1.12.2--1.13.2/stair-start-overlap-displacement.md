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
