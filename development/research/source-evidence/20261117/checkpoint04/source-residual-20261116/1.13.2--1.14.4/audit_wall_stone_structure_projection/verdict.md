# Independent source audit: stone, grass block, bedrock, structure block

## Verdict

**ACCEPT**, bounded to the four listed wall-neighbor predicate rows. The audited author packet is `build/source-residual-20261116/1.13.2--1.14.4/wall_stone_structure_projection/report.md`, SHA-256 `40437dcc5f4d747f3ba819aa032308cd7e4eb21d29d7574f6699574477053af4`.

## Identity and crosswalk checks

The ready-marker hashes match the report: 1.13.2 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` and 1.14.4 `bf8c9399e3a36ddd7edc78d98413319000895f49e88db224335f62b2baf21e77`. The canonical Feather source-manifest hashes match: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

I rehashed every Java source named in the report's canonical-source table against the canonical tree and checked each digest against that version's `.sources.sha256` entry. All match. The crosswalk TSV hash is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its four exact rows confirm keys/classes and cardinalities: stone `Block`/`StoneBlock`, grass block `GrassBlock` at both endpoints with `SNOWY:2`, bedrock `BedrockBlock`→`C_97172724`, and structure block `StructureBlock` at both endpoints with `MODE:4`.

## Provider and state checks

The source confirms the old base `Block.getFaceShape` returns `FaceShape.SOLID`; old StoneBlock and BedrockBlock add no face-shape override. GrassBlock inherits through SpreadingSnowyDirtBlock and PodzolBlock, which supplies the boolean `SNOWY` property and does not override the face query. StructureBlock has four `MODE` values (`SAVE`, `LOAD`, `CORNER`, `DATA`) and inherits the generic face query through BlockWithBlockEntity.

At the target, `Block.getShape` returns `VoxelShapes.block()`, the default collision flag is true, and `Block.getCollisionShape` delegates through the state shape unless `hasCollision` is false. None of the four exact registrations calls `noCollision`. Target GrassBlock inherits the podzol snowy state and default shape; the obfuscated bedrock class only overrides `canSpawn`; StructureBlock/BlockWithBlockEntity add no shape override. The target state `m_87223014` uses its cached face bit or calls `Block.isFaceSolid`; `isFaceSolid` rejects LEAVES and then requires a full collision-shape face.

Old `WallBlock.shouldConnectTo` accepts SOLID unless `isExceptionForConnection` applies. None of the four is in the old exception lists. The target generic wall arm likewise requires a non-exception full face; none is in its attachment exception list or LEAVES tag. The verified WALLS resource payload has 14 wall keys and the LEAVES payload has six leaf keys; neither includes these four registrations. Thus the generic wall bit is true for every registered state and horizontal direction on both sides. The target wall-tag and fence-gate alternative arms do not apply.

## Bounded results

- `stone`: 1 state, true→true in N/E/S/W.
- `grass_block`: 2 SNOWY states, true→true in all four directions.
- `bedrock`: 1 state, true→true in N/E/S/W.
- `structure_block`: 4 MODE states, true→true in all four directions.

This accepts only the selected predicate rows. It makes no movement-displacement claim and does not complete/freeze the pair.
