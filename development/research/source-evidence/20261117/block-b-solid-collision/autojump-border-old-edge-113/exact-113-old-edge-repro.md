# Exact 1.13 old-edge aggregate repro

Read-only exact Feather Gen2 source inspection with arithmetic in scratch. The author r2 source receipt is SHA-256 `346d7f558c6024c60a8da412c8597f0ae3ab757d4220bb033398ad987c62b2b9`; the independent sample-state verdict is SHA-256 `998d62735a18c5d7624d2bc3ae0d012ab58e5ef80c4dcd4ca8b03a0abd69559b`. Their first-ray selection and timer results agree. The author r2's complete old mask count is the correct one.

## Collector and occupancy

Exact 1.13 `WorldView.getBlockCollisions` (113–188) computes the padded scan, loops X/Z/Y, and admits a cell when `(!xEdge || !zEdge) && ((!xEdge && !zEdge) || y != lastY)`. Both X and Z interior keep the high-Y row. Specifically x=10 and z=-1,0,1 are interior, so y=66 contributes all three cells. Retaining them changes the independent verdict's old count 26 to 29. Exact 1.13.1 has 24 cells under its two-edge exclusion. Both counts include the two Stone floor cells.

The 1.13 AutoJump overload passes empty exclusion and `bl=false` (WorldView 199–218) so every canonical full cube that survives that gate enters the mask before overlap. For this entity-free full-cube/AIR fixture, the ordinary nonfull-shape side and the entity side are empty. `VoxelShapes.joinUnoptimized` (129–146) returns the nonempty IntVoxelShape operand for OR with empty and therefore cannot change its greedy coordinates. The consumer iterates the resulting aggregate's boxes (`LocalClientPlayerEntity.autoJump`, 934). Exact 1.13.1 produces the same kind of finite mask but the aggregate is a later stream entry. Its final aggregate-level overlap succeeds in this fixture, so retaining one existing overlap check would not affect the result when used for this narrow 1.13 probe.

The scan bounds are X [7,12), Y [63,67), Z [-2,3). The player's two actual ray Z coordinates are approximately 0.200525 and 0.799475, with Y 64.510000 and east endpoint X 10.068286. The exact batch differs only on these five 1.13-only cells: (10,63,-2), (10,63,2), (11,63,-1), (11,63,0), (11,63,1). The upper physical Stone option at (11,66,0) is outside the admitted mask in both scans. Every other occupied position is shared.

## Output, including boxes below or outside the rays

### 1.13

| Order | X | Y | Z | Hit |
|---:|---|---|---|---|
| 1 | (8, 12) | (63, 64) | (0, 1) | False |
| 2 | (10, 11) | (63, 66) | (-2, 0) | False |
| 3 | (10, 11) | (63, 66) | (1, 3) | False |
| 4 | (10, 12) | (64, 66) | (0, 1) | True |
| 5 | (10, 11) | (66, 67) | (-1, 2) | False |
| 6 | (11, 12) | (63, 66) | (-1, 0) | False |
| 7 | (11, 12) | (63, 66) | (1, 2) | False |

### 1.13.1

| Order | X | Y | Z | Hit |
|---:|---|---|---|---|
| 1 | (8, 11) | (63, 64) | (0, 1) | False |
| 2 | (10, 11) | (63, 67) | (-1, 0) | False |
| 3 | (10, 11) | (63, 67) | (1, 2) | False |
| 4 | (10, 11) | (64, 66) | (-2, -1) | False |
| 5 | (10, 12) | (64, 66) | (0, 1) | True |
| 6 | (10, 11) | (64, 66) | (2, 3) | False |
| 7 | (10, 11) | (66, 67) | (0, 1) | False |
| 8 | (11, 12) | (64, 66) | (-1, 0) | False |
| 9 | (11, 12) | (64, 66) | (1, 2) | False |


In both releases the first qualifying box is [10,12] × [64,66] × [0,1], center (11,65,.5) and block center (11,65,0). With actual AIR at all clearance samples, Jump Boost II admits rise 2. With physical full Stone at (11,66,0), the direct t=1 clearance read replaces the top with 67, so rise 3 exceeds cap 2.7 before the timer write. The mask difference does not create a timer difference for either checked state. The old selector does emit a different floor/side sequence, which is preserved here without broad claims.

## Narrow product correspondence

The registered integration branch already has the finite shared-mask branch in the 1.14 boundary Change selected by V1_13 and V1_13_1. Its east border admission explicitly allows only V1_13_1. That Change already computes the original 1.13 and 1.13.1 scan predicates for interior candidates. A bounded original-1.13 candidate can reuse that one scan and decomposition for the established AIR fixture: admit V1_13 only within the same exact stationary east, loaded, eroded-center, two-Stone and effect envelope, then choose the 1.13 predicate for the synthetic cells. Keep physical Stone in the same mask. The verified full grid and ordered boxes are the acceptance target, not only the coincident timer. Existing 1.13.1/1.13.2, 1.12 and later infinite routes and fallback must retain scope. The optional upper Stone branch requires a separate narrow physical-state allowance if it is included in candidate scope. No state writes, collector duplication or profile model changes are required.

## Exact consumed file hashes

### 1.13

- world/WorldView.java `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`.
- client/entity/living/player/LocalClientPlayerEntity.java `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08`.
- block/shape/VoxelSet.java `c450bdcdbf1e0f4fa4bf5675ee6f8f08d03bb73f63eeb35f0f622b6799aeca65`.
- block/shape/VoxelShapes.java `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330`.
- block/shape/IntVoxelShape.java `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`.
- block/Block.java `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476`.
- block/StoneBlock.java `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906`.
- world/World.java `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5`.
- world/border/WorldBorder.java `f400517accdeaa99976a80f0a88bb79c85c1146790e6549534a0fad953000bbf`.

### 1.13.1

- world/WorldView.java `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.
- client/entity/living/player/LocalClientPlayerEntity.java `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- block/shape/VoxelSet.java `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`.
- block/shape/VoxelShapes.java `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`.
- block/shape/IntVoxelShape.java `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`.
- block/Block.java `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- block/StoneBlock.java `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906`.
- world/World.java `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6`.
- world/border/WorldBorder.java `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`.

