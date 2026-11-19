# Ordinary piston-carried block displacement, 26.1.2 to 26.2

Bounded verdict. Both exact sources use the same piston-specific displacement for a carried block other than slime or honey. The shared post-collision velocity response changes and remains reachable from `MoverType.PISTON`. This slice yields no independent piston finding.

## Source identity

Both releases use published unobfuscated names and Vineflower 1.12.0 under Java 25. The exact source-manifest and artifact-manifest digests matched the canonical ready markers. Published client and version metadata digests below are the pinned artifact-manifest records. Touched source digests were checked directly against the exact source manifests.

| Version | Source manifest SHA-256 | Artifact manifest SHA-256 | Client JAR SHA-256 | Version JSON SHA-256 |
| --- | --- | --- | --- | --- |
| 26.1.2 | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92 | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | 739c0e0520086c1a6d3dcd3ceec4f9c209848146e8a6012503241a3ba8117775 |
| 26.2 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 | ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 | 4d6b3a5a27fed3c0faab2cb4b6dee7318be310bc3c627a78951519ffefaf1d0c |

## Paired slices

Paths are relative to `net/minecraft` under each exact source root.

| File/member | 26.1.2 lines | 26.2 lines | Source result |
| --- | --- | --- | --- |
| `client/multiplayer/ClientPacketListener.java`, login and `handleBlockEvent` | 533–534, 1526–1529 | 533–534, 1524–1527 | Login assigns the player ID and adds the player to the client level. The client packet dispatches its block event on that level. |
| `client/multiplayer/ClientLevel.java`, `addEntity`, `getEntities` | 410–413, 933–935 | 526–529, 1069–1071 | Adds the local player to transient entity storage and returns the getter used to query that storage. |
| `world/level/Level.java`, `blockEvent`, `setBlockEntity`, `tickBlockEntities` | 903–905, 703–709, 529–549 | 908–910, 708–714, 534–554 | Forwards the event through the block state and registers block entities in the chunk. Ticks live block entities when level time runs normally and the position is eligible. |
| `world/level/block/piston/PistonBaseBlock.java`, `triggerEvent`, `moveBlocks` | 150–217, 273–353 | 150–216, 272–352 | Client event can take the extension branch directly. The carried block becomes a moving-piston block entity with the original carried state, direction, extension flag and `isSourcePiston=false`. Retraction has an equivalent pattern-match spelling change for finalizing the previous piston. |
| `world/level/block/piston/MovingPistonBlock.java`, constructor helper, ticker and collision shape | 57–70, 100–113 | 58–71, 101–114 | Ticker delegates to `PistonMovingBlockEntity.tick`. Its type is now registered in `BlockEntityTypes` (26.2 lines 93–95), versus `BlockEntityType` (26.1.2 line 101), with the same piston factory and supported moving-block state. |
| `client/Minecraft.java`, client tick | 1853–1860 | 1805–1812 | Entity ticks precede block-entity ticks. |
| `world/level/chunk/LevelChunk.java`, ticker binding | 399–405, 704–765 | 399–405, 702–763 | Binds the state-specific ticker with the same live-block-entity and valid-state checks. |
| `world/level/EntityGetter.java`, default `getEntities` and `world/level/Level.java`, filtered implementation | 29–31, 769–785 | 29–31, 774–790 | Same default selector and spatial query through the section getter. The transient section manager, getter adapter, storage and section source files match byte-for-byte. |
| `world/level/block/piston/PistonMovingBlockEntity.java`, block state, swept shape, displacement, immediate aftermath and progress | 106–199, 225–272, 310–383 | 106–199, 225–272, 310–383 | Active tick computes float `progress + 0.5F`, sweeps the translated carried-state collision shape, ignores entities with `PushReaction.IGNORE`, takes the largest positive overlap capped by the float progress difference, adds double `0.01`, and requests the signed axis delta. Marks the moving direction as no-clip, calls `entity.move(PISTON, ...)`, applies effects along actual endpoints, removes the movement recording, clears no-clip and then commits progress. The source-piston correction and slime/honey branches do not enter this ordinary carried-block scenario. |
| `client/player/LocalPlayer.java`, `move` and inherited player edge backing | 986–991, `world/entity/player/Player.java` 880–913 | 986–991, `world/entity/player/Player.java` 880–913 | Local player delegates to entity movement, then measures horizontal movement for autojump. Back-off logic limits itself to SELF or PLAYER movers. |
| `world/entity/Entity.java`, `move`, piston limiter, collision and block effects | 704–800, 1037–1083, 1077–1190, 867–918, 3558–3560 | 711–851, 1101–1147, 1141–1264, 931–982, 3631–3633 | Same default push reaction, per-axis per-game-tick clamp, early zero return and collision geometry on the Entity overload. The renamed collector still inserts the world-border shape for Entity callers. The new separate CollisionContext overload does not handle piston-driven Entity movement. Block effects invocation and recording removal are still reachable with the same inputs on this path. |

## Shared movement dependency

For a loaded client chunk, an extensible piston carrying stone downwards can displace a survival local player whose AABB intersects the stone sweep. A slime floor beneath the player can clip the requested negative Y. At the shared post-clip step 26.1.2 calls the effect block's `updateEntityMovementAfterFallOn` on Y clipping (`world/entity/Entity.java` 776–785), and its slime override reverses a negative Y velocity for a player (`world/level/block/SlimeBlock.java` 32–46). In 26.2 `Entity.move` instead reaches restitution at lines 784–842, with the local player passing `Player.canSimulateMovement` (1290–1292). On a downward clip it gates restitution by effective gravity, suppression and effect state. A negative incoming Y smaller in magnitude than effective gravity can therefore bounce in 26.1.2 and stop vertically in 26.2. With restitution enabled, the 26.2 gravity and drag terms also take the actual clipped Y divided by incoming Y. This is the common collision response to a piston-driven move, with wider applicability beyond pistons.

## Bounds

Examined ordinary stone-carrier creation from the client block event, block-entity ticker identity and scheduling, direct selection and displacement, default piston clamp, Entity collision and ordered aftermath. Compared the ordinary extension and sticky retraction creation branch, with an extension as the reachability example. The shared restitution operation owns its wider applicability, block restitution registrations, suppression tags, gravity/drag variants and independent review. The source-piston correction, honey dragging, slime as the carried block, state-dependent carried shapes and effects of unrelated blocks are outside this bounded verdict. No runtime parity claim.


## Paired source SHA-256

| Source under net/minecraft | 26.1.2 | 26.2 |
| --- | --- | --- |
| `client/Minecraft.java` | 8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1 | c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0 |
| `client/multiplayer/ClientLevel.java` | 6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7 | f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631 |
| `client/multiplayer/ClientPacketListener.java` | eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82 | 9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6 |
| `client/player/LocalPlayer.java` | 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |
| `world/entity/Entity.java` | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| `world/entity/LivingEntity.java` | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| `world/entity/player/Player.java` | 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |
| `world/entity/ai/attributes/Attributes.java` | 8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5 | 4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c |
| `world/level/Level.java` | 77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01 | 1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639 |
| `world/level/EntityGetter.java` | e37ea40ed0f9ebb8234addc98a5e05271bfad2fe9ffce5a7babf3bfaaa700d29 | e37ea40ed0f9ebb8234addc98a5e05271bfad2fe9ffce5a7babf3bfaaa700d29 |
| `world/level/CollisionGetter.java` | dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01 | 8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c |
| `world/level/chunk/LevelChunk.java` | 689dc09c757e4b31651b5083962f667218d2643060a636caef3fc189435855cb | 4e93a4279f5fad591348ce27a0bc379973ed495aa7c2e0216c46ec86ee0e1878 |
| `world/level/block/Blocks.java` | ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| `world/level/block/entity/BlockEntityType.java` | 0e28972cb52f241f18abd5f8ae62a0ad82393c97d68715bfd1a62a536d08a780 | 221b4f3f5d4a99ed92694f3ec49b70b0d3944b050299e65eeca90b5f9647b2bf |
| `world/level/block/entity/BlockEntityTypes.java` | — | 42a8f0389f8613f9475ad22daea6e9c1a6c7cde99d216af0c851633f43e4e400 |
| `world/level/block/piston/MovingPistonBlock.java` | 87e8c6c1374b73d086b41a40adce0194f2b30f0ee195ec331b51989be7b92c73 | 9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1 |
| `world/level/block/piston/PistonBaseBlock.java` | c2ea332220c200d0eade0269bdadb79f9926845dbfb6304ae0cbb41d35c87288 | 5287608eaedbd6402f4c6fac93645cf595631b1d2b3dd6987789225045a64a91 |
| `world/level/block/piston/PistonMovingBlockEntity.java` | 67bceb946c172c5c022663fda32d123a6bbb85ca4171705ca9dc2c54105ab6e6 | 706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06 |
| `world/level/block/piston/PistonMath.java` | 6f173e909dd53d0af86add354b054124ee63d570852b10f2cd31ea65dc3f053b | 6f173e909dd53d0af86add354b054124ee63d570852b10f2cd31ea65dc3f053b |
| `world/level/block/piston/PistonStructureResolver.java` | ece59be9c33498824b00ac6216225eb9c0be8b296fcc66723b37c58a21f467bf | ece59be9c33498824b00ac6216225eb9c0be8b296fcc66723b37c58a21f467bf |
| `world/level/block/SlimeBlock.java` | 84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e | e0fc3087b66777a2800676aaeee47a6e98395ea4c964d5a301f3a73b3577c754 |
| `world/level/entity/TransientEntitySectionManager.java` | 8d1c25616b2d10b7730b54ee289ae25ec21c42cdec83732da1f689a8c67ea453 | 8d1c25616b2d10b7730b54ee289ae25ec21c42cdec83732da1f689a8c67ea453 |
| `world/level/entity/LevelEntityGetterAdapter.java` | 8b5fe169ea4badc3beca10b8cdecedb5e40dc06427bf96d4b31a4f4116b60611 | 8b5fe169ea4badc3beca10b8cdecedb5e40dc06427bf96d4b31a4f4116b60611 |
| `world/level/entity/EntitySectionStorage.java` | 7a57413210e71562ab43c6d12c07960f631af8a64cd61b4a5d7dda4bdead9404 | 7a57413210e71562ab43c6d12c07960f631af8a64cd61b4a5d7dda4bdead9404 |
| `world/level/entity/EntitySection.java` | b713b8324b8f73bf884cef7904076c9b1dda1c8661f7f8b88bd6b7bae92990fa | b713b8324b8f73bf884cef7904076c9b1dda1c8661f7f8b88bd6b7bae92990fa |
| `world/phys/AABB.java` | 22f23018fd588c2c4893d872f730381127e3d3ddd1a8c960901ecb8dae626392 | 1ffdd0f6d26a4dd1e26407ea9a386eab7903e43462e60c9ae5fdc50a0468f10b |
