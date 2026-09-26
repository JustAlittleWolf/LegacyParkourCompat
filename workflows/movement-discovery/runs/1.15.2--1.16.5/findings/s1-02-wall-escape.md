# S1-02: Change local-player suffocation escape probes

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 1, local input and tick ordering (unstuck behavior); Stage 4, entity movement and collision; `S1-WALL-ESCAPE`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: artifact manifest A; `../../../../decompiled_minecraft/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer.aiStep()V` lines 650–655 probes four x/z points at `y + 0.5`; `LocalPlayer.checkInBlock(DDD)V` lines 385–424 tests one selected block column and writes a ±0.1 horizontal velocity; `LocalPlayer.blocked(BlockPos)` lines 431–444 iterates the entity's vertical block range and calls `Player.freeAt(BlockPos)`, whose line 1489 checks `!blockState.isSuffocating(...)`. Source hashes: LocalPlayer `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`; Player `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`.
- B: artifact manifest B; `../../../../decompiled_minecraft/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer.aiStep()V` lines 656–661 makes the same four x/z calls, now to `moveTowardsClosestSpace(DD)V`; lines 414–440 test `suffocatesAt` in the selected and neighboring cells and write a ±0.1 horizontal velocity; lines 443–447 test a bounding-box slice deflated by `1.0E-7` through `CollisionGetter.noBlockCollision`. Source hashes: LocalPlayer `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B`; CollisionGetter `B507D6BE11E5985A62CFEB249A99DCB5F8EDAF346F12CB2487797D9E01763EAC`.

## Source-level difference

A tests whether each of four sampled block columns is suffocating across the entity's vertical range. B checks each sampled block against suffocating collision shapes intersecting a horizontal slice of the full bounding box. Both choose the nearest unblocked cardinal neighbor in west/east/north/south order and set the selected horizontal velocity component to magnitude `0.1`; the occupancy query is different.

## Reachability and dependencies

This runs from `LocalPlayer.aiStep()` while `noPhysics` is false, before the parent living movement step. The chosen delta movement is then consumed by normal player movement. A's dependency is `Player.freeAt` and the suffocating predicate. B's dependency is `CollisionGetter.noBlockCollision` over the deflated AABB with the same suffocating predicate.

## Consequence and uncertainty

A concrete differing precondition is a suffocating neighboring block that intersects the player's bounding-box edge but is outside the four sampled x/z block columns. B's broader AABB query can consider that cell while A's column checks cannot. The source proves different probe geometry; it does not establish which exact legacy block layouts yield a different selected direction in every case. Position/velocity consequences are predictions; no gameplay validation was run.

## Handoff

Independent delta: local-player suffocation recovery changes its occupancy query while retaining the four probes and ±0.1 velocity output. Related work: Stage 4 collision/support queries and block suffocation behavior. The first changed release is unknown.
