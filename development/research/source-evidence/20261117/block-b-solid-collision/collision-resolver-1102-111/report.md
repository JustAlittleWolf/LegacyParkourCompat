# 1.10.2–1.11 ordinary collision resolver

Immutable source-lane report. Scope: the ordinary collision-enabled Entity.move resolver, including its collision-list input, Y/X/Z clipping, and both step candidates. This is not a full-pair freeze or independent audit.

## Evidence identity

Canonical exact source roots, read only:

- decompiled_minecraft/1.10.2/ornithe-feather/ — exact version 1.10.2, Ornithe Feather feather-gen2-1.10.2+build.2 (mergedv2 and tiny mapping artifacts). Ready source-manifest SHA-256 recomputed as 91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71; artifact-manifest SHA-256 6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116. Mojang client SHA-256 in ready/provenance: 7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9.
- decompiled_minecraft/1.11/ornithe-feather/ — exact version 1.11, Ornithe Feather feather-gen2-1.11+build.2 (mergedv2 and tiny mapping artifacts). Ready source-manifest SHA-256 recomputed as 8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca; artifact-manifest SHA-256 ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd. Mojang client SHA-256 in ready/provenance: f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634.

Recomputed source-manifest and artifact-manifest digests match both ready.json identities. Every consumed file below matches its entry in the corresponding .sources.sha256 manifest; these are recomputed file SHA-256 values:

| Source file | 1.10.2 | 1.11 |
|---|---|---|
| net/minecraft/entity/Entity.java | 05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77 | 3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97 |
| net/minecraft/world/World.java | 888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646 | f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47 |
| net/minecraft/util/math/Box.java | f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155 | f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a |
| net/minecraft/block/Block.java | 1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01 | 3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488 |
| net/minecraft/entity/living/LivingEntity.java | d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82 | 1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b |
| net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java | a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443 | 65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed |

For 1.10.2, ornithe-feather.verification.json reports all 1,845 published source files and raw input artifacts matched. It records one derived remapped intermediary JAR difference, explained there as regenerated intermediate output; its published decompiled source tree matched. No 1.11/ornithe-feather.verification.json exists in this pair directory; the 1.11 ready marker and provenance are present and their source identity checks pass.

## Player binding and bounded gates

- 1.10.2 LocalClientPlayerEntity.move(double,double,double) (812–817) calls super.move(dx,dy,dz) before auto-jump. LivingEntity invokes move(velocityX,velocityY,velocityZ) from ordinary travel (1371 and related branches).
- 1.11 LocalClientPlayerEntity.move(MoverType,double,double,double) (824–829) calls super.move(moverType,x,y,z) before auto-jump. LivingEntity invokes it as move(MoverType.SELF, velocityX,velocityY,velocityZ) (1425 and related branches). The per-profile player caller therefore reaches the shared Entity resolver.
- The resolver's noClip branch bypasses collision resolution; this table is for noClip == false. Sneak-edge reductions, cobweb request scaling, and post-resolver player auto-jump are outside this bounded slice. In 1.11 the sneak-edge probe additionally checks mover type; that gate is excluded here.

## Block collector and geometry dependencies

World.getCollisions(@Nullable Entity, Box) has the same signature in both sources (1.10.2 World.java:903–965; 1.11 World.java:960–1022); the complete 63-line bounded method slice is text-identical. It computes candidate bounds as floor(min*) - 1 and ceil(max*) + 1, scans X then Z then Y, skips horizontal X/Z corner columns (q == 2), skips vertical end cells on the remaining horizontal boundary columns, and skips unloaded chunks. It uses the actual state when inside the border or when the entity is not already fully within the border, otherwise substitutes Blocks.STONE.defaultState(). It invokes each selected state's addCollisions(world,pos,queryShape,list,entity). The method then appends entity-derived collision boxes; those appended entities are outside this block-only analysis.

The default Block.addCollisions passes state.getCollisionShape(world,pos) through a sentinel check (blockShape != EMPTY_BLOCK_SHAPE), translates it by the block position, and appends it only when queryShape.intersects(box). This logic is the same in both (Block.java:342–353 / 353–364). The getCollisionShape world parameter changes from World in 1.10.2 to WorldView in 1.11; per-block shape overrides and their historical geometry were not audited in this assignment.

Box.expanded retains the same per-axis sign branches in both (Box.java:76–102 / 104–130). intersectX/Y/Z retain the same math and gates (Box.java:143–201 / 185–243): orthogonal dimensions must overlap strictly, so exact edge contact is rejected. Along the clipped axis, positive movement clips only when the moving box's max face is <= the obstacle's min face, and negative movement clips only when its min face is >= the obstacle's max face; an already-overlapping interval on the clipped axis satisfies neither side test and receives no correction. A zero limit matches neither positive nor negative branch and remains zero. intersects also uses strict </> for all dimensions (Box.java:207–209 / 249–251). moved constructs a new box by adding each delta (Box.java:128–130 / 166–168).

## Ordinary resolver branch table

Methods: 1.10.2 Entity.move(double,double,double), Entity.java:446–626; 1.11 Entity.move(MoverType,double,double,double), Entity.java:455–648.

| Branch | 1.10.2 | 1.11 | Verdict |
|---|---|---|---|
| Broad query | Query once with getShape().expanded(dx,dy,dz), retain original shape, and apply the returned list. | Same with x,y,z. | Same operation and order. |
| Y clip | Always fold all list boxes through intersectY(shape,dy), then always set shape to moved(0,dy,0), including a zero input or zero clipped result. | Enter only if requested y != 0.0; then fold the list and always set shape to moved(0,y,0), including if clipping reduces it to zero. | Source branch differs for exact zero input; intersectY at zero returns zero. The old path still adds zero to every box coordinate via moved and assigns a fresh box; this is numerically inert for ordinary finite nonzero bounds but may change signed-zero representation. |
| Ground/step eligibility value | After Y, bl2 = onGround || (h != dy && h < 0.0), where h is saved requested Y. | Same expression (h saved requested Y). | Identical operands and order. |
| X then Z clips | Fold X against every block box using current shape, always move by result; then fold Z against every box using the X-updated shape, always move by result. | X phase only when x != 0.0; after folding, assign moved shape only when clipped x != 0.0. Z does the same after X. | Source branch differs on zero components and components clipped to zero. Box's zero-limit clip is a no-op, and Entity.setShape is a plain field assignment (1.10.2 Entity.java:2094–2096; 1.11 2154–2156). The old path nevertheless adds the clipped zero to box coordinates; numeric movement is unchanged for ordinary finite nonzero bounds, while signed-zero representation may differ. |
| Step gate / saved normal result | If stepHeight > 0.0F && bl2 && (g != dx || i != dz), save normal q=dx,r=dy,s=dz and shape box2; restore pre-move shape, request dx=g,dy=stepHeight,dz=i. | Same gate and sequence with names x,y,z; saves q=x,r=y,s=z, then restores and requests x=g,y=stepHeight,z=i. | Same. g/i are the pre-main-clip horizontal request values retained by this resolver (after any excluded sneak adjustment). |
| Step candidate A | Query expanded (g,stepHeight,i). Let box3 be original shape and box4=box3.expanded(g,0,i). Clip upward t against box4; move box3 by (0,t,0); clip X into w, move X; clip Z into z, move Z. Candidate A is this final box3. | Same math/order with corresponding variables x,y,z, candidate box3. | Same candidate. |
| Step candidate B | From original box5, clip upward ac against list2, move up; clip X into af, move X; clip Z into ai, move Z. Candidate B is var84. | From original box5, clip upward af, move up; clip X into ai, move X; clip Z into al, move Z. Candidate B is var85. | Same candidate. In both alternatives, Y then X then Z; all folds retain list order. |
| Candidate A/B comparison | al = w*w + z*z; am = af*af + ai*ai; choose A only when al > am, else choose B (including tie). Chosen Y is -t for A / -ac for B. | ao = w*w + ac*ac; ap = ai*ai + al*al; choose A only when ao > ap, else choose B (including tie). Chosen Y is -t / -af. | Same semantic operands: A's clipped X/Z squares vs B's clipped X/Z squares; strict > means ties select B. |
| Final downward clip / normal-vs-step | Fold chosen negative Y against list2, move Y. Revert to saved normal result if q*q+s*s >= dx*dx+dz*dz; equality reverts. | Same with q*q+s*s >= x*x+z*z; equality reverts. | Same comparison operands/strictness and revert branch. |

## Verdict and limits

The bounded block collector, Box clipping predicates, axis sequence, and both step alternatives/comparisons show no movement-value difference between these exact sources. 1.11 adds guards that skip zero Y/X/Z clipping work in the ordinary branch (X/Z also skip the set-shape operation when clipping reaches zero). For ordinary finite nonzero box bounds the zero guards do not change numeric movement values. The old path still performs additions by signed zero when it creates moved boxes, so bit-level signed-zero propagation can differ; this review did not establish a reachable player-state consequence. Do not file this as an accepted movement finding without additional reachability and observable-effect evidence.

Open limits: excluded sneak-edge probe, cobweb scaling, entity collision append, per-block collision-shape overrides, complete travel/tick graph, remaining pair inventory, and independent full-pair coverage audit. No intermediate patch boundary was inspected. The source worker has not independently reviewed the pair.


