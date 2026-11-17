# Independent source audit — block collision consumer context route

## Verdict

**ACCEPT for the bounded method-route and context-dispatch conclusion**, tied to report SHA-256 `6E2163898F2F92CBE65C10E93B2DF330D85E9BEE236BF72149EC00DA4CA8345E`. The report does not establish a movement delta, geometry change, full consumer equivalence, or pair freeze; this audit accepts none of those claims.

## Identity and file checks

The 1.13.2 and 1.14.4 Feather ready markers both say `status=ready`, identify Ornithe Feather Gen2 build 2, and name the source manifests whose on-disk SHA-256 values match the report's identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. All ten Java-file hashes listed in the report match their respective `.sources.sha256` sidecars and local file hashes.

To check the claimed inherited movement dispatch, I also verified the source-sidecar hashes of these intermediate classes:

| Endpoint | Relative source | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `b1ac72bad8c09f5e68e4d0682d431e3b54471e854810b94d91f830a95ae408dd` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |

Neither intermediate player superclass declares `move`; their visible `move` matches in LivingEntity are calls. The local-player overrides' `super.move` calls therefore reach `Entity.move` at both endpoints.

## Claim checks

- The 1.13.2 local-player override calls `super.move(moverType, x, y, z)`. Inherited `Entity.move` collects old block collisions with the entity and box, then `WorldView.getCollisions(entity, ...)` uses the actor only for border state before invoking the block enumeration that calls `BlockState.getCollisionShape(world, pos)`. The provider call has no actor/context argument.
- In 1.14.4 the local-player override calls `super.move(moverType, vec3d)`. `Entity.move` captures `CollisionContext.of(this)` for collision resolution, and the fresh block-stream branch supplies the entity to `WorldView.getBlockCollisions(entity, box)`. That query creates a matching collision context and calls `BlockState.getCollisionShape(world, pos, context)`, then translates the returned shape to world coordinates.
- The 1.14.4 base `Block.getCollisionShape(..., context)` gates on `hasCollision` and delegates through `state.getSHape`, which selects an empty context. That confirms the report's distinction: the new dispatch supplies context, while the base implementation discards it; a block-specific collision-shape override may consume it.
- The report explicitly limits its route claim to the fresh-stream branch. The other axis-offset route is outside the report's accepted scope. No query-wide equivalence is inferred.

## Boundaries

The call-boundary difference and local-player reachability are source-supported. The finding does not establish a behavior change for any provider, nor does it compare the entire old and new collision query, movement resolver, or all context-consuming block overrides. Pair coverage and independent full-pair audit remain open.
