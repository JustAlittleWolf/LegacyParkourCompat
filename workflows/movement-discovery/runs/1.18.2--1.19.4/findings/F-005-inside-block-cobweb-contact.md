# F-005: Narrower inside-block inset detects shallow cobweb overlap

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 4 post-move inside-block query; stage 5 cobweb contact slowdown
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (player box shallowly overlaps a cobweb)
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- Artifact identity: exact A/B release-specific Mojmap sources, verified in `run.md`.
- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks()`, lines 872–886; SHA-256 `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`. It forms inclusive block-position bounds from `min + 0.001` and `max - 0.001`, then dispatches each selected state's `entityInside`.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks()`, lines 896–910; SHA-256 `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`. It uses `min + 1.0E-7` and `max - 1.0E-7` before the same cell iteration and `entityInside` dispatch.
- A/B: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/level/block/WebBlock.java`, `entityInside(BlockState, Level, BlockPos, Entity)`, line 16; both versions have SHA-256 `EB8F4433705367AA7CE23C5A6071955B4D67728E299A2993285C6405CE638E49`. Both call `makeStuckInBlock(..., new Vec3(0.25, 0.05F, 0.25))`.
- A/B: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/level/block/Blocks.java`, cobweb registration (A lines 294–296, B lines 445–447); A SHA-256 `CC6B87D2C5897E71E5244B889444AC040E3FA0A139E392523E87FEC99805A0F2`, B `A4F2DF87C2CAE5A3827A818F39F7AD123EE5B18905EAD9947EFE654421C30FF5`. Both register `WebBlock` with `.noCollission()`.
- A `Entity.makeStuckInBlock()` lines 2149–2152 and B lines 2208–2211 set `stuckSpeedMultiplier` and reset fall distance. A/B `Entity.move()` later multiplies the next requested movement by that vector when its length squared exceeds `1.0E-7`, then clears it; both implementations retain that behavior.

## Source-level difference

After a move, the entity invokes `tryCheckInsideBlocks()`. A's `0.001` inset can exclude a block whose only intersection with an entity-box face is shallower than `0.001`; B's `1.0E-7` inset includes such a cell when the overlap exceeds that smaller epsilon. For example, if the box's max X is `1.0005`, the A upper sample is `0.9995` (cell 0), while the B upper sample is `1.0004999` (cell 1). Both versions' cobwebs are non-colliding and share the same `entityInside` implementation. In that shallow-overlap case B dispatches the cobweb callback and stores `(0.25, 0.05F, 0.25)` as the stuck movement multiplier; A does not dispatch it from this query.

## Reachability and dependencies

`LocalPlayer` movement calls `Entity.move()`; after position/collision updates that method calls `tryCheckInsideBlocks()` before its final block-speed-factor multiplication. The changed bounds decide which block callbacks run. In B, `WebBlock.entityInside()` sets the stuck multiplier; the next `Entity.move()` applies it before collision resolution. Other movement-relevant `entityInside` callbacks share this changed query and remain queued individually (including honey, bubble columns, powder snow and sweet-berry bush).

## Consequence and uncertainty

Source proves a larger region in which B recognizes inside-block contact. Under the stated web overlap precondition, B schedules the slowdown multiplier and A does not. This predicts reduced next-move input displacement in B; no trajectory was evaluated. Exact introduction release is unknown within `(1.18.2, 1.19.4]`.

## Handoff

This finding is scoped to cobweb contact. The shared contact-query dependency may affect other block callbacks; track those as separate coverage items and avoid generalizing this one finding to every `entityInside` block. Runtime validation is deferred.
