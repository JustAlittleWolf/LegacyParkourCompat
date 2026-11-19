# Opening shulker box player query — independent source verdict

Accepted for the bounded opening-query operation. Reviewed bytes of development/research/findings/1.16.5--1.17/shulker-box-opening-player-query.md have SHA-256 8379a6e2c2845bf5729286f7c5708ea9d0726814ca1e4583e1832b71171104b5. Independent reviewer /root/shulker_verdict used only the assigned immutable finding, project/discovery procedures and the canonical decompiled_minecraft publication. No product source, wiki inventory, mixed backlog, candidate implementation, tests or gameplay were used.

## Trigger, tick and query

Both Mojmap releases register the block entity in world/level/block/entity/BlockEntityType.java:145ff. A first non-spectator viewer increments the open count and issues block event 1 with count 1 (ShulkerBoxBlockEntity.startOpen, 1.16.5:187–198, 1.17:157–169). A client receiving the event calls Level.blockEvent through ClientPacketListener.handleBlockEvent (1293ff / 1133ff). Level:879–881 / 721–723 dispatches the current block state's event and BaseEntityBlock:22–26 / 24–28 forwards to the block entity. ShulkerBoxBlockEntity.triggerEvent:163–180 / 133–150 sets OPENING only for count 1, CLOSING for 0. Other counts do not restart OPENING. A player affected by the moving lid can be someone other than the viewer.

ClientLevel.tickEntities calls tickBlockEntities after entity processing (1.16.5:152–177 / 1.17:161–170). In 1.16.5 Level:463–475 requires a live block entity with a level in a ticking chunk and inside the border, valid for the current block. In 1.17 ShulkerBoxBlock:63–70 binds the type-specific ticker. Level:447–455 and LevelChunk:416ff, 885–896 gate the invocation, using the then-current state as its argument. These are ordinary client world tick paths.

1.16.5 ShulkerBoxBlockEntity:53–86, 97–117, 151–154, 182–184, 271–273. Save old float progress, add 0.1F while opening. Before the last tick, tick() calls the query after updateAnimation while the state is OPENING. On the final threshold the query/move occurs inside updateAnimation, before OPENED, clamp and neighbor notification. In the ordinary uninterrupted transition tick() subsequently sees OPENED and there is no second query. The query rereads the block, requires ShulkerBoxBlock, gets FACING, and gets the current protruding lid slab. Start from the full cube, calculate old + 1.0F * (current - old) via Mth.lerp:473–475 and multiply by 0.5F and the signed axis step as floats. Directionally expand, contract by the opposite unit steps, then add the block position in double precision. Shapes:23–27,47–49 and AABB:91–149,185–198 supply the shape and transformations. In surface-relative outward coordinates the slab is [0, half-interpolated-progress], with both transverse intervals the full block. Ten additions from zero produce 1.00000011920928955078125 before the clamp, giving a face offset of 0.500000059604644775390625 at ordinary small block coordinates.

1.17 ShulkerBoxBlockEntity:63–93, 100–123, 152–153. Save old float, add 0.1F. On reaching the threshold, set OPENED, clamp to 1.0F, notify neighbors, then query once inside the OPENING switch arm. Read FACING of the supplied tick state if it is a ShulkerBoxBlock. Shulker.getProgressDeltaAabb:248–254 uses raw old/current floats, computes float max/min, widens both to double, expands the unit AABB by signed max, then contracts by the negated signed step times (1.0 + min); caller translates by BlockPos. The opening slab is [old, current] measured outward and full-width transversely. AABB:28–30,113ff,145ff,211–219. The ordinary block-shape getter separately halves progress. The immutable witness with nearest east face 0.95 and overlapping transverse axes first passes on tick 10 in 1.17 and never passes from closed in 1.16.5 under the stated clear, stable preconditions.

## Local-player reach and dependencies

ClientPacketListener:391 / 375 enrolls the local player; ClientLevel:273–287 / 223–234 performs the addition. 1.16.5 EntityGetter:30–32, Level:690–708 and LevelChunk:476–492 scan loaded chunks and vertical entity sections, applying strict AABB overlap, exclusion identity and the predicate. In 1.17 TransientEntitySectionManager:50–64 adds the player to the section, ClientLevel:740–742, LevelEntityGetterAdapter:40–42, EntitySectionStorage:41–58,111–117, EntitySection:29–35, Level:577–583 and EntityGetter:31–33 perform the accessible-section scan, overlap and predicate. EntitySelector.NO_SPECTATORS is !entity.isSpectator() (1.16.5:21 / 1.17:18). AABB.intersects:204–210 / 226–232 uses strict inequalities on all three axes; mere touch does not admit. The null excluded entity does not remove the local player. Entity.getPistonPushReaction:2579–2581 / 2709–2711 returns NORMAL. LivingEntity, Player, AbstractClientPlayer and LocalPlayer do not override that method. The shulker box invokes move(SHULKER_BOX, vector) for the admitted player when the reaction is not IGNORE.

Membership differs in both directions. At the final query on a common east-facing pre-state, a player AABB spanning 0.20 to about 0.80 outward (e.g. repositioned while already opening), with transverse overlap, is in the old slab and behind the new strip. This is a per-query witness; an uninterrupted pair of trajectories need not give identical pre-states. A compatibility path limited to changing vectors on native list members misses this old-only player. Excluding a new-only player also means omitting the extra movement call. LocalPlayer.move:863–868 / 895–900 delegates then updates autojump from actual X/Z travel. Entity.move:485–518 / 534–566 handles SHULKER_BOX outside the piston limiter. For an ordinary colliding player it writes verticalCollision and writes onGround = verticalCollision && requestedY < 0.0. A horizontal old-only contact can therefore clear onGround even if obstructed; filtering displacement to zero or only correcting final position does not establish parity. Collision, autojump, displacement magnitude and downstream state are independent dependencies of full movement parity. This verdict accepts admission and call reach, not a particular implementation of those operations.

## Historical range and variant identity

All 23 older stable releases 1.11 through 1.16.5 were compared at the shulker box tick, progress, slab and query call sites, including their differently mapped names. Feather tickAnimation/moveEntities/getLidShape, Yarn updateAnimation/pushEntities/getCollisionBox and Mojmap updateAnimation/moveCollidedEntities/getTopBoundingBox exhibit the old geometry and one pre-clamp final query. In Yarn 1.14–1.14.2 field_12066 increases progress and is set by count 1; field_12063 is the terminal opened state. 1.11–1.13.2 have no neighbor notification at the final transition; 1.14–1.16.5 notify after the query. Early versions interpolate with last + (current - last) * tickDelta; this is a float interpolation at parameter 1.0F. 1.10.2 has no registered shulker box in Block, Blocks or BlockEntity. Exact 1.11 registers 16 dyed boxes (Block.java:1301–1320, BlockEntity.java:260); exact 1.12.2 still has those 16 (Block.java:1320–1339). 1.13 registers one colorless and 16 dyed variants (Block.java:1634–1661). Crucially, 1.13 util/datafix/DataFixes.java:274–288 converts the older purple box/block item to minecraft:shulker_box. The converted plain block can thus represent an era-existing block in a migrated old map. For vanilla migrations, the 15 other dye keys and the modern undyed key carry the historical 16 identities. The separately dyed modern purple is a 1.13 variant under that conversion. A partial pre-1.13 implementation may limit eligibility to the 15 stable dye keys pending an integration decision for non-migrated or translated states; leaving both special keys native then leaves the migrated older purple case outstanding. Exact 1.16.5 and 1.17 are adjacent stable releases for the query cutover.

Separate operations include non-final closing response, the distance/vector supplied after admission, lid collision shape and interactions that produce block events. Mob shulker shell movement, rendering, other entities, server-side physics, unregistered variants and whole-pair freeze fall outside this review. The actionability of a product seam and runtime tick parity remain unassessed.

## Reproducible publication identities

Paths in the following evidence tables are relative to the release/mapping directory beneath decompiled_minecraft. SHA-256 values were recomputed from the source bytes and checked against the corresponding publication's source manifest or embedded provenance. The alias table binds one official mapping or mapping build per release and the full ShulkerBoxBlockEntity source bytes. The final table binds endpoint evidence and registration/flattening evidence.

| Release | Mapping | Mapping SHA-256 | Block entity SHA-256 |
|---|---|---|---|
| 1.11 | ornithe-feather feather-gen2-1.11+build.2-mergedv2.jar | 658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44 | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.11.1 | ornithe-feather feather-gen2-1.11.1+build.2-mergedv2.jar | 83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928 | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.11.2 | ornithe-feather feather-gen2-1.11.2+build.2-mergedv2.jar | d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757 | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.12 | ornithe-feather feather-gen2-1.12+build.2-mergedv2.jar | 73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719 | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.12.1 | ornithe-feather feather-gen2-1.12.1+build.2-mergedv2.jar | f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7 | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.12.2 | ornithe-feather feather-gen2-1.12.2+build.2-mergedv2.jar | e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca | ae3127af04c5c4159026e91ab3bd4092f91f51ab8090ee40e85404c86b08f119 |
| 1.13 | ornithe-feather feather-gen2-1.13+build.2-mergedv2.jar | a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897 | d123b08d4d3562ccb972a4433ec5a743cdd6877c59c5b6aa20d06af2063fa5ed |
| 1.13.1 | ornithe-feather feather-gen2-1.13.1+build.2-mergedv2.jar | 6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d | d123b08d4d3562ccb972a4433ec5a743cdd6877c59c5b6aa20d06af2063fa5ed |
| 1.13.2 | ornithe-feather feather-gen2-1.13.2+build.2-mergedv2.jar | 317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b | d123b08d4d3562ccb972a4433ec5a743cdd6877c59c5b6aa20d06af2063fa5ed |
| 1.14 | yarn yarn-1.14+build.21-v2.jar | 0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64 | f578738693c91173639837445170cd573905b9bb9df55d74d132943e3f879f5e |
| 1.14.1 | yarn yarn-1.14.1+build.10-v2.jar | f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630 | f578738693c91173639837445170cd573905b9bb9df55d74d132943e3f879f5e |
| 1.14.2 | yarn yarn-1.14.2+build.7-v2.jar | 3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b | f578738693c91173639837445170cd573905b9bb9df55d74d132943e3f879f5e |
| 1.14.3 | yarn yarn-1.14.3+build.13-v2.jar | ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e | b710ce20062f271a735b43fdc79266d5b590caf76dfb11374386eefc0ddc9c81 |
| 1.14.4 | mojmap client_mappings.txt | 2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910 | a85df7969733b5e8722b700a8f0cd6400ba5d24930c0699f94ec922b47663441 |
| 1.15 | mojmap client_mappings.txt | 11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a | fb4742271c97668118d954c24ce43d0828755947c9ca5c642be01f4ffe589517 |
| 1.15.1 | mojmap client_mappings.txt | f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34 | fb4742271c97668118d954c24ce43d0828755947c9ca5c642be01f4ffe589517 |
| 1.15.2 | mojmap client_mappings.txt | 65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777 | fb4742271c97668118d954c24ce43d0828755947c9ca5c642be01f4ffe589517 |
| 1.16 | mojmap client_mappings.txt | 53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.1 | mojmap client_mappings.txt | 6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.2 | mojmap client_mappings.txt | c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.3 | mojmap client.txt | c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.4 | mojmap client.txt | 93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.5 | mojmap client_mappings.txt | 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.17 | mojmap client.txt | 191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471 | 0f1b810c4977ad8f9c27b6abb97ae9d1af8d5445e1cf514d33042f74a9a59cb7 |

| Release / mapping | Evidence file | SHA-256 |
|---|---|---|
| 1.10.2 ornithe-feather | net/minecraft/block/Block.java | 1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01 |
| 1.10.2 ornithe-feather | net/minecraft/block/Blocks.java | 40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9 |
| 1.10.2 ornithe-feather | net/minecraft/block/entity/BlockEntity.java | f22e924e7a09af7f46a5832bd9d657d59fe970598287aa0ad55b872d9263d7a7 |
| 1.11 ornithe-feather | net/minecraft/block/Block.java | 3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488 |
| 1.11 ornithe-feather | net/minecraft/block/Blocks.java | 69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d |
| 1.11 ornithe-feather | net/minecraft/block/entity/BlockEntity.java | 63953a4e67b192fc7e853c7cf4d5e0450d723b09cbd0f15c85c0cd88c2f68088 |
| 1.12.2 ornithe-feather | net/minecraft/block/Block.java | e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1 |
| 1.13 ornithe-feather | net/minecraft/block/Block.java | e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476 |
| 1.13 ornithe-feather | net/minecraft/util/datafix/DataFixes.java | da06ab5f5bcc0e46f9660d2116f8a158fb3e8bec00f74fd61740785fdd3f062f |
| 1.16.5 mojmap | net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java | 4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322 |
| 1.16.5 mojmap | net/minecraft/world/level/block/ShulkerBoxBlock.java | a88a18bc4c4299876053f9ec38987861fa81acf3a38289a49cc3dbee04b940d8 |
| 1.16.5 mojmap | net/minecraft/world/level/block/BaseEntityBlock.java | 211813ad33e63b550e77af30ed25cf68375521cdd8b550b5fd91035d93f9883f |
| 1.16.5 mojmap | net/minecraft/world/level/block/entity/BlockEntityType.java | a22d789a9d06bc071f3f462f8803560ae56cb72059d402217e93b5a7a22ef3d4 |
| 1.16.5 mojmap | net/minecraft/client/multiplayer/ClientPacketListener.java | a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e |
| 1.16.5 mojmap | net/minecraft/client/multiplayer/ClientLevel.java | 96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93 |
| 1.16.5 mojmap | net/minecraft/client/player/AbstractClientPlayer.java | 05c36f559ada7061a4a2ee8efaf05a21502945af83a893cb4c849749afb84859 |
| 1.16.5 mojmap | net/minecraft/client/player/LocalPlayer.java | 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b |
| 1.16.5 mojmap | net/minecraft/world/level/Level.java | e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80 |
| 1.16.5 mojmap | net/minecraft/world/level/EntityGetter.java | 12b4ad28f0a92414ec1478dd42b6a41ff6cd0a0a00650ae37490ea8cd0a1bba5 |
| 1.16.5 mojmap | net/minecraft/world/level/chunk/LevelChunk.java | 0bfdae3558d681d8c8074ae7476c3a394239dbcca0fdf7bad5b4386eafb65f12 |
| 1.16.5 mojmap | net/minecraft/world/entity/EntitySelector.java | 1fd642a27e3773d2c8bb79a20db965b2ea4876fea232490fbbbdc7efbf78fd9a |
| 1.16.5 mojmap | net/minecraft/world/entity/Entity.java | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| 1.16.5 mojmap | net/minecraft/world/entity/player/Player.java | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 |
| 1.16.5 mojmap | net/minecraft/world/entity/LivingEntity.java | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 |
| 1.16.5 mojmap | net/minecraft/world/phys/AABB.java | 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878 |
| 1.16.5 mojmap | net/minecraft/util/Mth.java | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 |
| 1.16.5 mojmap | net/minecraft/world/phys/shapes/Shapes.java | ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4 |
| 1.17 mojmap | net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java | 0f1b810c4977ad8f9c27b6abb97ae9d1af8d5445e1cf514d33042f74a9a59cb7 |
| 1.17 mojmap | net/minecraft/world/level/block/ShulkerBoxBlock.java | 13e37b77465151a27bc8526adec0235c491824d027d6cb4d3f44f99292f6d1c0 |
| 1.17 mojmap | net/minecraft/world/level/block/BaseEntityBlock.java | b3678f8296344a1e3e90aea54fc3a725441aa3d90b2e752d67bdba48c55a6140 |
| 1.17 mojmap | net/minecraft/world/level/block/entity/BlockEntityType.java | fa635bfcc3ad0efc19d18fd65605c1ab55e552a025c745749db6ca8b51794240 |
| 1.17 mojmap | net/minecraft/client/multiplayer/ClientPacketListener.java | 394b6fd04134212fb08eba2498b9bccc2baadf5ae08a64dea9f4c8a52a34e6f5 |
| 1.17 mojmap | net/minecraft/client/multiplayer/ClientLevel.java | fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2 |
| 1.17 mojmap | net/minecraft/client/player/AbstractClientPlayer.java | d0508c94f43e7e90683b8f46586665bd1e1f0dca2fe25274b852f5e6c0ce05e8 |
| 1.17 mojmap | net/minecraft/client/player/LocalPlayer.java | 08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4 |
| 1.17 mojmap | net/minecraft/world/level/Level.java | 730d3cc1f1c1c7054c102bb32d635d8f5a3657fff8073df863f0848ed1f9a0f8 |
| 1.17 mojmap | net/minecraft/world/level/EntityGetter.java | 2491753cf00c868e42b1451b255a6ed2c1a4a3c4194445f47772bb592e6cc3e9 |
| 1.17 mojmap | net/minecraft/world/level/chunk/LevelChunk.java | a34711404b63d0530e28a6ee8efa903e8257a59dc7fd63663a3a555bae31a367 |
| 1.17 mojmap | net/minecraft/world/entity/EntitySelector.java | 63f72ac3907b18f09716e9e64cfd3b7e8767f43792430ef79f15c11b59845c51 |
| 1.17 mojmap | net/minecraft/world/entity/Entity.java | 81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef |
| 1.17 mojmap | net/minecraft/world/entity/player/Player.java | 9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516 |
| 1.17 mojmap | net/minecraft/world/entity/LivingEntity.java | 8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed |
| 1.17 mojmap | net/minecraft/world/phys/AABB.java | dd143ccd01d0d05e50619910bceb98a0cec0ca526e161e9812ae1e0db6eb7b7e |
| 1.17 mojmap | net/minecraft/world/entity/monster/Shulker.java | 102331800abe50283910e0fd94334c1ee4d4094bcd471bad3426ed9a2bcf3816 |
| 1.17 mojmap | net/minecraft/world/level/entity/EntitySection.java | 42564db04c51c6c9febbe1e7eb68276f28a5537efe154999bafe381f1c48c0bf |
| 1.17 mojmap | net/minecraft/world/level/entity/EntitySectionStorage.java | dcb755b5bd6c544e87205e1826ce1d3f2df0223b162274d8f235452929d0335e |
| 1.17 mojmap | net/minecraft/world/level/entity/LevelEntityGetterAdapter.java | 349762658f2a45e4e6f50890632c4823b967ee3f0157164cd611e6b965941489 |
| 1.17 mojmap | net/minecraft/world/level/entity/TransientEntitySectionManager.java | 546c923aabd71fbd40e27697807eebc97e2dfef710da3245d2797b22f3499a15 |
