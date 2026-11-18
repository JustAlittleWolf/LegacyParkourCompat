# Independent exact-source audit — 1.21.3 → 26.2

Candidate: C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-ac27-7712-9259-3041950bc340\lane-piston-callback-origin-source-1213-262\local-piston-origin-witness.md
Candidate SHA-256: 08b961f68c0df7d7b07575c0eea43f5a6d531830a615da99132edf26a72c2453
Reviewer source roster was frozen before reading the candidate. Exact local Java sources only.

## Source identity and frozen roster

| Release / mapping | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Ready digest matches manifest |
|---|---|---|---|---|
| 1.21.3 / mojmap | 72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02 | 24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7 | d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce | yes |
| 26.2 / unobfuscated | f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f | a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 | yes |

Every source-file SHA below was recomputed from current bytes and matched the exact path entry in that release's source manifest.

| Relative source | 1.21.3 Mojmap SHA-256 | 26.2 unobfuscated SHA-256 |
|---|---|---|
| net/minecraft/client/Minecraft.java | 4ff5f4b2907fa383e8ba0a79b9f02518491350e48a3079a15c6cfd53e0dc0ce1 | c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0 |
| net/minecraft/client/multiplayer/ClientLevel.java | f99ca9b24f796bed5428e6a3b010c489bfac2f43a35e90ff4fd8dad6d2e45aa1 | f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631 |
| net/minecraft/client/multiplayer/ClientPacketListener.java | 169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a | 9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6 |
| net/minecraft/client/player/AbstractClientPlayer.java | fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c | 7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc |
| net/minecraft/client/player/LocalPlayer.java | fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0 | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |
| net/minecraft/world/entity/Entity.java | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| net/minecraft/world/entity/LivingEntity.java | 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52 | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| net/minecraft/world/entity/player/Player.java | a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |
| net/minecraft/world/entity/ai/attributes/Attributes.java | 14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea | 4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c |
| net/minecraft/world/level/Level.java | ff4d57a3df0b850ba02dc798c6aa6734174790b9fd17673261f33f03d44821e1 | 1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639 |
| net/minecraft/world/level/BlockGetter.java | 5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39 | 623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d |
| net/minecraft/world/level/block/Blocks.java | 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| net/minecraft/world/level/block/WebBlock.java | ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414 | 8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5 |
| net/minecraft/world/level/block/state/BlockBehaviour.java | 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133 | 9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e |
| net/minecraft/world/level/block/piston/PistonMath.java | 562a5c309be43c60cfc42e70b23c6775055aad9b277c82b4337c55306eaaef19 | 6f173e909dd53d0af86add354b054124ee63d570852b10f2cd31ea65dc3f053b |
| net/minecraft/world/level/block/piston/PistonHeadBlock.java | 29923bc413619be019fe8ef69b67a4b7a2c49b6db98c5d765c0afccfa4643bf4 | cd228fe96ce810c19ea747e779cd8f89f4fd3457d6277673d7ead29c8304f5ab |
| net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java | 426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16 | 706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06 |

## Findings and verdict

**Verdict: partly accepted.** The callback-origin delta and cobweb movement writer are source-backed. The candidate's stated piston coordinates are off by one block, so its exact geometry as written does not push the stated player.

### Tick and player path

In 1.21.3, Minecraft.tick calls ClientLevel.tickEntities; that method ticks entities and then tickBlockEntities. In 26.2, Minecraft.tick explicitly calls tickEntities before tickBlockEntities. ClientLevel.tickNonPassenger snapshots old position before entity.tick in both versions. Thus the entity movement and ordinary LivingEntity block-effects pass precede that tick's piston block-entity callback.

The client authority gates accept the LocalPlayer's ordinary travel/effects path: 1.21.3 LivingEntity applies effects under !client || isControlledByLocalInstance; 26.2 uses !client || isLocalInstanceAuthoritative. For both, the ordinary fall can call WebBlock.entityInside. Cobweb's entity-inside collision shape defaults to Shapes.block even though its movement collision is empty. WebBlock calls makeStuckInBlock; for a non-flying Player this writes multiplier (0.25, 0.05F, 0.25) and resets fall distance.

The proposed 2.85-block ordinary fall request is source-reachable from a sufficiently developed fall: ordinary air travel moves by the current velocity, then subtracts gravity (default 0.08) and applies vertical friction 0.98 in 1.21.3; 26.2 retains the same 0.08 gravity and computes the default 0.98 vertical friction. With no collision, the next-tick velocity recurrence is (v - 0.08) * 0.98, whose downward terminal magnitude is 3.92; Entity.move has no displacement clamp. The prior fall may begin high enough that the LocalPlayer arrives at the stated y with current downward velocity -2.85. (1.21.3 LivingEntity.java:2188–2214, Entity.java:619–652, Attributes.java:40–43; 26.2 LivingEntity.java:2468–2498 and 514–516, Entity.java:711–755, Attributes.java:10–12 and 55–61.)

### Callback inputs and scan

1.21.3 piston moveEntityByPiston calls no-argument applyEffectsFromBlocks after Entity.move; that overload supplies oldPosition() to the then-current position. oldPosition is set at the beginning of entity ticking. The later callback can therefore scan the old-tick-origin-to-post-piston span after an ordinary fall.

26.2 piston moveEntityByPiston samples entity.position() immediately before Entity.move, applies block effects only over that pre-piston-to-post-piston span, then removes the latest movement recording. The ordinary LocalPlayer effects pass has already copied and cleared live movement records. No nonempty movement queue is needed for the witness.

The traversal implementations differ and are not presumed equivalent: 1.21.3 boxTraverseBlocks uses a short-span endpoint-box branch and a longer swept-box/DDA path; 26.2 forEachBlockIntersectedBetween walks target-box corners, DDA candidates, and per-movement iteration limits. In this witness the decisive caller input is the long old-position fall span versus the short horizontal piston span. For the latter, target box top is 0 and deflation places it below the cobweb cell y=[0,1].

### Geometry correction and movement writer

The candidate places the west-facing moving head at (1,-2,0), with base at (2,-2,0), but at extension progress 0 the head shape is translated one block east before its first westward 0.5 movement. Its cap sweep is therefore x=[1.5,2], which misses player x=[0.2,0.8].

Corrected geometry: put the extended west-facing normal piston base at (1,-2,0), and its moving-head block entity at destination (0,-2,0), direction WEST, extending, progress 0. Long cap local x=[0,0.25] shifts east by 1; its first westward cap sweep is x=[0.5,1], y=[-2,-1], z=[0,1]. This overlaps the player's box at feet (0.5,-1.8,0.5), width 0.6, height 1.8. The non-slime piston path's positive sweep-query, NORMAL push-reaction, per-shape intersection, and amount gates all pass: WEST computes 0.8 - 0.5 = 0.3, then adds 0.01 for a 0.31 requested push.

This also yields a source-predicted movement write, beyond callback eligibility. The ordinary fall has already written the cobweb multiplier. In 1.21.3 Entity.move applies it to piston moves, scaling WEST movement -0.31 to -0.0775; the following old-origin callback scans the same web again and rewrites the multiplier. In 26.2 Entity.move skips multiplier scaling for MoverType.PISTON but clears it; its explicit short span skips the web and does not rewrite it. This is exact-source predicted behavior, not an observed runtime trace.

### Terminal disposition

The conditional cobweb witness is reachable after correcting the piston/head coordinates by one block. The source establishes callback input and movement-writing differences; it does not establish runtime observation or the exact intermediate patch where the change arose.