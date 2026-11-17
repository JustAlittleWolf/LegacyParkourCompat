# Block collision API dispatch in 1.11→1.11.1

## Exact source identity

Both endpoints are the `ornithe-feather` decompilation. Source publication sidecars are at the release roots (one directory above the mapping namespace).

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.11 | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` |
| 1.11.1 | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` |

The four inspected API source files below were rehashed and matched their exact source-manifest rows on both sides. Source hashes are included with the findings; no online mapping lookup or source generation was used.

## API difference

In 1.11, `BlockProperties.addCollisions(World, BlockPos, Box, List<Box>, @Nullable Entity)` has no selector argument. `StateDefinition.State.addCollisions` forwards those arguments to the block's virtual `addCollisions` implementation. The base implementation in `Block.addCollisions` obtains `state.getCollisionShape(world, pos)` and passes it to `addCollision`, which offsets the shape, intersects it with the query box, and appends an intersection.

In 1.11.1, the same three points have an added trailing `boolean forceShape`. `StateDefinition.State.addCollisions` forwards it unchanged to the block's virtual method. The base `Block.addCollisions` accepts the value but does not read it; its provider lookup, offset, intersection test, and append order are unchanged. `BlockProperties` and `StateDefinition` have no other collision-dispatch changes in this comparison.

| Source | 1.11 SHA-256 | 1.11.1 SHA-256 | Relevant member |
| --- | --- | --- | --- |
| `net/minecraft/block/Block.java` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `addCollisions`, 353–355 |
| `net/minecraft/block/state/BlockProperties.java` | `6dc035216a1712982a326fe583a4bc026c6c46da5fca072fcbbdccccffcbd609` | `aa54e753d9ccfbe628b14b24f81a0d8d469d347613f326e454417b581bddaf03` | `addCollisions`, 83 |
| `net/minecraft/block/state/StateDefinition.java` | `a8d5fcb3d6a5582f8130600bbfb01a751a09b527d71a7652682487d80a2f717a` | `95704c944742efb2ec9cba7eb73399a330195556e669451131ba542e6a9fac65` | nested `State.addCollisions`, 371–375 |

## Player query binding and selector inputs

`LocalClientPlayerEntity.move` delegates to `Entity.move`; the local class's override does not replace that movement implementation. The inherited collision-resolving movement queries `World.getCollisions(entity, box)`. In 1.11.1, the block scan calls `BlockProperties.addCollisions(..., entity, false)`, then virtual dispatch reaches the state and block methods above. `LocalClientPlayerEntity` also makes a local auto-jump collision scan through the same `World.getCollisions(this, box)` overload. Both ordinary client player query paths therefore enter the new API with `forceShape=false`.

The 1.11.1 caller inventory shows one distinct `forceShape=true` route: `MovingBlockEntity.moveEntities` collects the moved state's shape to form its movement bounds, and `MovingBlockEntity.addCollisions` uses `true` when querying the moving block state after offsetting the shape. Its retracting source-state route passes `false`. The regular `World` entity collision scan passes `false`. A boat-only call also passes `false` and is excluded non-player behavior. These call sites establish only argument origin and virtual dispatch; they do not establish a movement consequence for any individual provider.

`World.getCollisions` and `MovingBlockEntity` are cited only to bind the selector to its callers. Their broader movement/provider differences are outside this API slice. In particular, this report does not repeat piston shape or push findings and does not evaluate per-block shape output. The boolean's provider-specific meaning and the full changed provider/state inventory remain separate open coverage rows.

| Caller source | 1.11 SHA-256 | 1.11.1 SHA-256 | Bound call |
| --- | --- | --- | --- |
| `net/minecraft/world/World.java` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | player `getCollisions(entity, shape)` scan; 1.11.1 dispatches `false` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | inherited movement caller, including `move` collision/step probes |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | client `move` delegates to inherited method; auto-jump also queries `World.getCollisions(this, box)` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | selector `true` for moved-state shape/bounds and offset moved-state query; retracting source passes `false` |

## Bounded disposition

The shared API change adds a provider selector and forwards it without changing the base block fallback. The normal local-player collision scan supplies `false`; a moving-block shape/bounds path supplies `true`. This is a dispatch/caller binding, not evidence that the flag changes a player-visible collision result. Provider overrides, their legal state outputs, registrations and caller applicability must be assessed in their own bounded source rows. Pair coverage remains partial and no movement finding or freeze is inferred.
