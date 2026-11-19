# Honey block side-slide coverage

## Verdict

No independent HoneyBlock side-slide velocity adjustment changed between Java 26.1.2 and 26.2 at equal callback inputs. All movement gates, numeric conversions, branches and write order match. The only HoneyBlock source differences are the CriteriaTriggers import relocation and a ServerPlayer pattern variable replacing the equivalent checked cast in the advancement branch (lines 4, 108–112). The advancement branch is unreachable for LocalPlayer and the changes do not affect side-slide physics. No positive finding draft.

## Reachable path and order

All member ranges below use the published unobfuscated namespace. Paths are relative to each release's decompiled_minecraft/<version>/unobfuscated/net/minecraft directory. Where two ranges differ, the older comes first.

1. The local player's aiStep invokes Player.aiStep, which invokes LivingEntity.aiStep (client/player/LocalPlayer.java:914 and world/entity/player/Player.java:453 in both). The block-effect pass follows travel and post-travel velocity calculation (world/entity/LivingEntity.java:3064–3078 / 3131–3145). The client gate requires local authority (world/entity/Entity.java:3516–3518 / 3589–3591, Player.java:1260–1262 / 1281–1283 and LocalPlayer.java:377–380 in both).
2. world/entity/Entity.java:839–850, 885–915 / 903–914, 949–979 consumes movement segments, fills missing final movement when needed, checks affected-by-blocks (present and physics-enabled), calls ground stepOn first if on ground, and dispatches inside-block callbacks before applying and clearing the collector. The inside scan implementation is equal at 1195–1295 / 1278–1378. It sweeps the deflated box, checks alive and scan budget, skips air and already-visited block positions, and dispatches with the discovered position. world/level/BlockGetter.java has an identical full-file hash and implements the sweep at line 175 onward.
3. Honey Block is registered through HoneyBlock::new in world/level/block/Blocks.java:5928–5932 / 4963–4967. 26.2 uses references/BlockItemIds.java:559, which still creates honey_block. The properties preserve speedFactor(0.4F) and jumpFactor(0.5F). world/level/block/HoneyBlock.java:31, 46–49 uses the same physical collision column, x/z from 1/16 to 15/16 and y from 0 to 15/16 (world/level/block/Block.java:164–166, 181–189 / 160–162, 177–185). The inherited inside-callback shape is the full block returned by world/level/block/state/BlockBehaviour.java:337–339 / 331–333. HalfTransparentBlock has an identical full-file hash. Block state forwards the callback at BlockBehaviour.java:774–778 / 780–784.
4. HoneyBlock.java:64–106 checks sliding, attempts the ServerPlayer-only advancement, invokes movement immediately, runs slide sound/particle effects, then calls its empty base callback (BlockBehaviour.java:375–383 / 377–385). The HoneyBlock predicate ignores isPrecise and the collector. It first excludes on-ground players and feet strictly above block Y + 0.9375 - 1.0E-7. It converts the stored Y velocity v to v / 0.98F + 0.08 and excludes values >= -0.08. It then requires either absolute center offset plus 1.0E-7 to exceed 0.4375 + width / 2.0F. Width is the Entity dimensions float, so the width division is float before the double addition.
5. HoneyBlock.java:114–124 reads stored velocity. When converted Y is strictly below -0.13, it multiplies stored X and Z by -0.05 divided by the converted Y. Otherwise it preserves X and Z. It writes Y as (-0.05 - 0.08) * 0.98F on either branch, then resets fall distance. The float 0.98F widens to double in both Y formulas. The Vec3 setter accepts only finite components and the fall-distance reset writes 0.0 (Entity.java:2841–2843, 3664–3672 / 2908–2910, 3737–3745 and world/phys/Vec3.java:340–342 / 344–346).
6. A concrete reachable case is a locally controlled player falling alongside the exposed honey collision column, with the bounding box overlapping the full-block inside shape, feet below the height cutoff and converted Y = -0.20. A single qualifying callback in either release multiplies X and Z by 0.25, sets Y to the expression above and zeros fall distance. The current movement segment was traversed before the callback, so its write affects later motion.

## Scope and dependencies

Examined the honey registration and shapes, inside sweep and direct dispatch, local-authority and slide gates, velocity arithmetic and finite write, and fall-distance write. The slide case uses the full-block inside-shape branch and leaves swept checks for other inside shapes outside this slice. Broader tick coverage, effects from other blocks and runtime parity remain outside the assignment.

Shared upstream operations can supply different callback inputs across releases. Entity.move in 26.1.2 uses direct horizontal zeroing and updateEntityMovementAfterFallOn (Entity.java:775–785). 26.2 uses restituteMovementAfterCollisions (Entity.java:784–786, 802–843). LivingEntity.travelInAir in 26.1.2 subtracts effective gravity and applies fixed player vertical friction (LivingEntity.java:2430–2452). 26.2 introduces friction and air-drag attribute calculations before the collector (LivingEntity.java:2468–2496). These are dependencies of trajectory comparisons and separate from the HoneyBlock adjustment. Follow up in the shared move/travel slices and have an independent reviewer retrace this bounded coverage before pair freeze.

## Evidence identity

Both versions' ready markers and provenance specify the same exact version IDs as the directories, published unobfuscated names and Vineflower 1.12.0. The computed source and artifact manifest SHA-256 values match the ready markers. The client SHA-256 values below come from the verified artifact manifests. Each listed source file was hashed and matched its source manifest entry.

| Identity | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| Source manifest | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |
| Artifact manifest | 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92 | ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba |
| Client archive | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 |
| Ready marker | f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7 | f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f |
| Provenance | 8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a | a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1 |
| HoneyBlock.java | 998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec | ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa |
| Entity.java | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| LivingEntity.java | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| Player.java | 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 |
| LocalPlayer.java | 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |
| BlockBehaviour.java | 9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13 | 9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e |
| Blocks.java | ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| BlockItemIds.java | — | d9d57f2f04801da5d8d583c23b87b08f45d8b6c681980d1a77308753c801bac6 |
| BlockGetter.java | 623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d | 623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d |
| HalfTransparentBlock.java | 2023e0ac7ca496c19037aaf34781a1dd5d1b6ceb52d7418b1483e5b0d3547712 | 2023e0ac7ca496c19037aaf34781a1dd5d1b6ceb52d7418b1483e5b0d3547712 |
| Block.java | 1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977 | cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab |
| Vec3.java | 57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb | 026c8461113b32116f0183ec42fa1cb4c7414cec38b685484d2309f9a0cb9a33 |
