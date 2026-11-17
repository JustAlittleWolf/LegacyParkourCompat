# 1.11.2 → 1.12 piston and portal player-response source slice

Status: bounded source observation only; this is not pair coverage or a pair-freeze verdict.

## Inputs and identity

The exact pair is `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/`, per the neutral input queue. The assigned pair inventory is `build/source-early-patches-20261117/1.11.2--1.12/coverage.md` (SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`). 1.11.2 source identity is recorded by the root `ornithe-feather.sources.sha256` (SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, 1,921 entries) and ready marker `ornithe-feather.ready.json` (SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`). 1.12 uses its mapping-local `.decompile-provenance.json` (SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`, 2,048 source entries). Absence of 1.12 root readiness markers is not a source identity gap.

The following source digests are the immutable rows in those release manifests (SHA-256; paths relative to each `ornithe-feather` source root):

| Source | 1.11.2 | 1.12 |
| --- | --- | --- |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/block/PortalBlock.java` | `cc1f875bd9daab78fd628ec9c321be02e00f5ba2fe7a4d5246b035968e13f287` | `d8fb94ff43876692bb61fbb61780995560c9c162c24702d2e25c33062c02abde` |
| `net/minecraft/block/EndPortalBlock.java` | `3e246cd437d5ca61758cd03e3dd258a14f84de9121d043193016274d1b6c03d2` | `ec3eda037b464a6b6fdee78703934b1404a4ac2908b56157d7b42df865ffc544` |
| `net/minecraft/block/EndGatewayBlock.java` | `eb29b053d3db7d606777cf1438e3068a87d7b20b5e3c5d8dc6de3844a60593bc` | `68a5026caceb31ce4645acf7f978f99dab3c8f567ed1cb4618031c44c2947fcc` |
| `net/minecraft/block/entity/EndGatewayBlockEntity.java` | `7ea08050d0be62dd47c0eed64492011ff096e4a2a3bb4485427124ea26b19cbd` | `5f09be49d62896df0ccd2976d89488203b3528852e1d2eb805277f8752c7807e` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |

## Findings

- **Moving piston displacement:** `MovingBlockEntity.java` is byte-identical in the pair. Its `tick` → `moveEntities` → `moveEntity` collision-box path remains unchanged. The player-side piston accumulation is in `Entity.move` (the `pistonMovementDelta` array, per-tick reset, and per-axis clamp to `[-0.51, 0.51]`); comparison of that method shows the same state updates and gates in both versions. The array declaration changes from mutable to `final` in 1.12, while the referenced array remains mutable and the constructor initializes it identically. No direct piston-driven position/velocity/rotation behavior change was found for the local player.
- **Nether portal admission and response:** `PortalBlock.onEntityCollision` in both versions checks `!isRiding()`, `!hasPassengers()`, and `canUsePortals()` before calling `Entity.onPortalCollision(pos)`. The only `PortalBlock` addition in 1.12 is `getFaceShape(...)=UNDEFINED`, a shape-query response unrelated to player movement. `Entity.onPortalCollision` sets `inPortal = true` outside the client/changed-position condition. Only `lastPortalPos`, `lastPortalOffset`, and `lastPortalFacing` updates are gated by `!world.isClient` and changed portal position; these updates compute the same portal offset/facing in both versions. The `Entity.tick` portal timer and `changeDimension` path remain server gated (`!world.isClient && world instanceof ServerWorld`, and `!world.isClient && !removed` respectively); the dimension transfer and position/rotation setup are not a local-client movement response.
- **End portal:** `EndPortalBlock.onEntityCollision` retains the same riding/passenger/portal-use gate and delegates to `Entity.changeDimension(1)`, whose transfer body is server-only. Its source changes are map-color signature and `getFaceShape(...)=UNDEFINED`; no player movement response change found.
- **End gateway:** `EndGatewayBlockEntity.tick` and `teleport(Entity)` retain their server-only entity query/transfer path and identical target position call. The differences in this class are beam accessor naming and logging argument formatting. The gateway may move a server-side player by calling `Entity.teleport`, but this code does not move the local client player directly. `EndGatewayBlock` and portal collision call chains were checked for direct movement changes; no 1.12 local-player response delta was found.
- `Entity` adds the general `onBlockCollision(BlockState)` callback after block collision handling. The only 1.12 override found is `ServerPlayerEntity.onBlockCollision`, which triggers `CriterionTriggers.ENTER_BLOCK`; it does not write position, velocity, or rotation. It is server-side criterion handling, outside this direct movement-response slice.

## Scope boundary

This review covers the listed piston/portal writers and their direct callers only. Ordinary entity and vehicle movement, damage, other movement/provider inventories, and the rest of the assigned pair remain outside this report. Nether/end transfers and gateway teleportation are server-side position changes; client movement replication/teleport packet application is not assessed here. This source slice establishes no pair-level completeness or acceptance.

## Immutable digest correction
Corrects only the MovingBlockEntity.java SHA-256 identity in this report to a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e, matching both endpoint source files and source-manifest rows. Original report SHA-256: 84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b. Bound prior verdict (prior piston/portal wording audit) SHA-256: 127b73971637681565c1ce2d9a3be8ce8c6b70fdee7db3053cbc20a0812ad175. No technical comparisons or accepted claims were changed.
