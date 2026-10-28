# Pane center-axis overlap epsilon — bounded source finding

## Disposition

**ACCEPT, bounded source comparison.** For an isolated pane at (0,64,0) with NORTH/EAST/SOUTH/WEST false, player position (0.13749,64.2,0.13750005), and positive X movement 25/8000 = 0.003125, exact decompiled source supports:

- **1.12.2:** collector retains the pane center post; Box.intersectX clips X to the ~9.988079071e-6 gap.
- **1.13.2:** swept query includes the pane; VoxelShape.calculateMaxDistance applies a 1e-7 cross-axis inset that removes the pane's Z cells from the clipping scan, leaving X at 0.003125.
- **26.2:** BlockCollisions tests the swept AABB without query inflation; its AND/index-merger gate coalesces the 6.192092894e-8 overlap, so no pane shape is collected. If supplied to clipping, VoxelShape.collideX has the same 1e-7 inset and would likewise exclude its Z cells.

This is source-level collision behavior only. It does not claim runtime execution, a packet trajectory, complete version equivalence, or anything about the separate voxel witness.

## Geometry

1.12.2 and 1.13.2 initialize Entity.width to 0.6F; Entity.setPosition computes float f = width / 2.0F before adding/subtracting it from the double coordinates. In 26.2, EntityTypes.PLAYER is sized 0.6F by 1.8F; EntityDimensions.makeBoundingBox does the same float half-width calculation.

The binary float half-width is 0.30000001192092896. Thus:

- player maxX = 0.43749001192092896; gap to pane minX 0.4375 = 9.98807907104382e-6.
- player maxZ = 0.43750006192092894; positive overlap past pane minZ 0.4375 = 6.192092893986256e-8.
- player Y interval [64.2,66.0] overlaps pane interval [64,65].

All three center posts have relative X/Z bounds [7/16,9/16] = [0.4375,0.5625], Y [0,1]. 1.12.2 defines these directly in PaneBlock.SHAPES[0]. In 1.13.2, IronBarsBlock passes 1.0F into PaneBlock.makeShapes, generating Block.block(7,0,7,9,16,9). In 26.2, IronBarsBlock passes post width 2.0F to CrossCollisionBlock; Block.column(2,0,16) forms Block.box(7,0,7,9,16,9).

## Gates and exact consumers

### 1.12.2

PaneBlock defaults all four properties false. addCollisions always attempts SHAPES[0], then adds arm boxes only for true properties. When adjacent blocks are clear, virtual-property resolution leaves the properties false. Block.addCollision skips only the EMPTY_BLOCK_SHAPE singleton, translates the center box, then adds it when shape.intersects(box). Box.intersects uses strict positive overlap; the measured Z overlap passes. World.getCollisions scans the swept query area and invokes addCollisions. With the stated no-other-collider case, the center post reaches Entity.move. Entity.move passes candidates through Box.intersectX. Its orthogonal Z gate has strict inequalities and no epsilon, so this positive overlap clips X to pane.minX - box.maxX.

Source ranges: PaneBlock.java:26-75,87-114; Block.java:376-383; World.java:964-1014; Entity.java:567-589; Box.java:185-203,245-251; player width/box: Entity.java:178,298-305.

### 1.13.2

IronBarsBlock defaults the four connection properties false. PaneBlock.makeShapes makes post-only shape index 0 for this state, and getCollisionShape selects it. The movement query in WorldView.getCollisions expands the swept box by 1e-7 on all sides; this gives about 1.619209289e-7 Z overlap with the post. getBlockCollisions uses VoxelShapes.overlap(..., AND) as its candidate predicate. Its exclusion test compares the original player box with the post: SimpleIndexMerger coalesces coordinate boundaries less than 1e-7 apart, so the original 6.192092894e-8 intersection produces no AND interval and does not exclude the block. The expanded swept query has >1e-7 overlap and retains the candidate.

Entity.move then calls VoxelShapes.calculateMaxOffset(X,...). VoxelShape.calculateMaxDistance indexes cross-axis ranges using box.maxZ - 1e-7 = 0.43749996192092894, below the occupied post cells. The Z cell iteration is empty and does not clip X.

Source ranges: IronBarsBlock.java:15-19; PaneBlock.java:32-84; Block.java:136-137; WorldView.java:112-151,154-178; VoxelShapes.java:143-181,194-205,252-270; SimpleIndexMerger.java:34-45; Entity.java:578-589; VoxelShape.java:184-243; player width/box: Entity.java:190,309-315.

### 26.2

IronBarsBlock defaults all four connection properties false. CrossCollisionBlock.makeShapes starts with its center post and ORs only arms whose state properties are true; getCollisionShape selects this shape. Waterlogged is excluded from the cached shape key and does not add geometry.

Entity.collideBoundingBox requests block collisions for boundingBox.expandTowards(movement). BlockCollisions uses cursor coordinates expanded by 1e-7 for broad enumeration, then makes entityShape = Shapes.create(box). For non-full-cube pane shapes, the exact gate is !shape.isEmpty() && Shapes.joinIsNotEmpty(shape, entityShape, AND); the full-cube singleton identity branch is not used. Shapes.joinIsNotEmpty reaches IndirectMerger: boundaries within 1e-7 are coalesced, so this 6.192092894e-8 intersection has no positive-width AND cell. The pane is not collected for the bounded swept box.

The axis consumer is Shapes.collide → VoxelShape.collideX. Its cross-axis range uses moving.max(cAxis) - 1e-7; the supplied Z edge again indexes below the post cells. This corroborates the modern epsilon alias, but the pane is already rejected at collection.

Source ranges: EntityTypes.java:1163-1173; EntityDimensions.java:15-23; IronBarsBlock.java:30-34; CrossCollisionBlock.java:53-84; Block.java:160-162,177-199; Entity.java:1141-1145,1195-1206,1214-1241,1244-1259; CollisionGetter.java:96-105; BlockCollisions.java:48-61,78-103; Shapes.java:175-231,234-244,299-320; IndirectMerger.java:14-62; VoxelShape.java:252-310.

## Source identity and access notes

Primary evidence is the exact decompiled Java export at D:\Javastuff\LegacyParkourCompat\decompiled_minecraft. Version-ready records and SHA-256 source manifests were checked using literal paths; targeted source hashes match those manifests.

- 1.12.2: provenance says version 1.12.2, namespace ornithe-feather, mapping artifacts feather-gen2-1.12.2+build.2-mergedv2.jar and feather-gen2-1.12.2+build.2.tiny. Provenance SHA-256: 747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE. Ready source-manifest SHA-256: b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da. PaneBlock.java: 83862F6633B8241C3788E9C9A7E4FD0275C010EC790019B8003908996F4814B3; Box.java: F788B8146B14F299CCB58EA0854609F845C298A963295D503DE2E30E15D66F3A.
- 1.13.2: provenance says version 1.13.2, namespace ornithe-feather, mapping artifacts feather-gen2-1.13.2+build.2-mergedv2.jar and feather-gen2-1.13.2+build.2.tiny. Provenance SHA-256: E25950B7855FC4F2AEE1A662F0BE6B0AF9F28788D9E27293075CFC5A3AA5D383. Ready source-manifest SHA-256: 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211. PaneBlock.java: 9422E3F2AA9843ECD041F5CED13D05A4C7472D697F7DFD838450E49969E5326C; VoxelShape.java: D95B0607647DA11595E24E1E26E9615CB7DA7705EA7885908544362DF62275C1; WorldView.java: 9577360B1A1BDDE9281F0B72593E5E6DDE45D9A54719CD7FF933F3ECD31B971F; SimpleIndexMerger.java: 5557E978B8E817A53D965F3917267E25BDA0D08055139D4DF2CB086120D354D1.
- 26.2: provenance says version 26.2, mapping/namespace unobfuscated. Provenance SHA-256: A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1. Ready source-manifest SHA-256: a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894. CrossCollisionBlock.java: DCD2DBBCD093A68DEE4EE282E5F3101C519D633F3F173DD33383BD4B548C1BA5; BlockCollisions.java: EB8A8F6F07B1D6384F17987818F056D41ADFD6B2AD120D26774BFA550F4FC51E; VoxelShape.java: 419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854; Shapes.java: 6ED9460D58727770BDEC59C4F3184F075644F3C411BE1EEAB2EAE17E8ECE1891; IndirectMerger.java: 9905EA12B115A355E7546E512532E23F6F9BF720168A61789F2A93A0347605C3.

The requested read-only cache D:\.minecraft-decompile-cache is absent. Provenance instead names D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts, also absent. Export source, version IDs, mapping names/artifact IDs in provenance, ready markers, manifests, and targeted file hashes are present and internally consistent. Mapping artifact binaries could not be reread, so mapping identity is metadata-backed, not revalidated against the JAR/Tiny bytes.

The permitted ordinary-closure note was available but contributes no collision assertion used here. Dependencies: player float width and bbox construction; pane false-state shape; swept block collection; collection predicates and identity branches; axis clipping consumer; epsilon-aware voxel coordinate merger. Movement relevance is limited to Entity.move / collision resolution if the supplied displacement is presented. No build, test, game, or runtime execution was performed.

