# F-S5-PISTON-HEAD-THRESHOLD: retracting moving-piston head uses a different short-shape threshold

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: moving-piston collision shape; S5-PISTON-SHAPE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player block-collision queries intersecting a retracting source piston while its block entity progress is 0.5F and the collision query is not on the matching NOCLIP direction
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`; `getCollisionShape`, lines 333-361, SHA-256 `43ef9a249446cac093eb1f755bd55c4db5416474fa1668772f0f1257672e57a`. When this is a source piston retraction, it derives `PistonHeadBlock.SHORT` from `this.extending != 1.0F - this.progress < 4.0F`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same class/member, lines 335-363, SHA-256 `75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5`. The corresponding expression uses `< 0.25F`.
- A `MovingPistonBlock#getCollisionShape` lines 105-107, SHA-256 `7b767f2f15974a36734037b7342c00f609b13d85505ff818cde18620d733a79e`; B lines 96-98, SHA-256 `34f882c090cfec263f767e72f6104fc546d189688cfc4094f67022a6413e0b3b`. Both dispatch the moving block's shape query to its block entity.
- A `PistonHeadBlock#getShape` lines 78-98 selects short or long arm geometry from `SHORT`; B lines 86-88 selects `SHAPES_SHORT` or `SHAPES_LONG` from the same property. A source SHA-256 `3db4ed7ed90f66b2534c4c19de8adb62416c9092091ae83a5d39bbdd5fe5165f`; B `9854f1d4728809cf9d89ac2632b0458eea12d1ea4488e36585922b1cadddb4bb`.
- The block entity tick advances progress from 0.0F by 0.5F while below 1.0F, so progress 0.5F is source-reachable. The surrounding method is in the cited PistonMovingBlockEntity files.

## Source-level difference

Java evaluates the relational comparison before boolean `!=`. At progress 0.5F, `1.0F - progress` is 0.5F. A's `< 4.0F` comparison is true, so a retracting source piston (`extending == false`) sets `SHORT` true. B's `< 0.25F` comparison is false, so the same state sets `SHORT` false. The two versions therefore select different short/long piston-head collision geometry at that progress. This is a bounded source-state difference; it does not assert a specific player displacement.

## Reachability and dependencies

`MovingPistonBlock#getCollisionShape` dispatches into this block entity method. The shared `CollisionGetter.getBlockCollisions` path passes the moving block's collision shape to the player's movement query. The difference is limited here to the source-piston retraction branch, intermediate progress 0.5F, and queries outside the matching NOCLIP direction. Dynamic piston tick/push response and other moving block-state providers remain part of the open S5 inventory.

## Consequence and uncertainty

For the stated source state, the player collision query receives a union containing the long piston-head shape in B and the short piston-head shape in A. The exact resolved movement depends on the player's requested box and neighboring collision shapes; no trajectory is claimed. The first release boundary within the endpoint interval is not identified.

## Handoff

Independent source delta: the short-head selection threshold differs between the endpoint sources for a retracting source piston at intermediate progress. Related finding IDs: none. Runtime validation was not performed.
