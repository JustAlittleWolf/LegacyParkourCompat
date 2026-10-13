# F-011: Chorus-fruit random teleport rejects a candidate already overlapping the border shape in B

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-PLAYER-EXTERNAL-TELEPORT, INV-EXTERNAL, INV-COLLISION
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player response when a server-side chorus-fruit use reaches a qualifying candidate
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A caller: `ChorusFruitItem#finishUsingItem`, A lines 19-49, `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap/net/minecraft/world/item/ChorusFruitItem.java`, SHA-256 `6b60c70dd0901b438945fda7a8d98f49551023a9a7a8d51d331eb17b784e49a6`. On the logical server it tries up to 16 candidates through `LivingEntity#randomTeleport`; Player is a LivingEntity. The item-use/four-food-point consumption is outside movement scope; the direct position selection is in scope.
- B caller: same member, B lines 19-49, `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/world/item/ChorusFruitItem.java`, SHA-256 `3e9bcb6838cabaff973dcf6891cf89b22499f468a4ee3572ef26e5dd38e49fb6`. Candidate generation, loop count/order, passenger stop, and success break match A.
- A `LivingEntity#randomTeleport`, lines 3026-3069, `LivingEntity.java`, SHA-256 `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; after a block-motion floor is found, it teleports to the candidate, then requires `level.noCollision(this)` and no liquid. On failure it restores the original position and returns false.
- B same member, lines 3020-3063, `LivingEntity.java`, SHA-256 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`; the candidate search, temporary teleport, collision/liquid gates, restore and return are unchanged apart from local names.
- Player reachability: A `ServerPlayer#teleportTo(double,double,double)` lines 1192-1194 delegates to `ServerGamePacketListenerImpl#teleport`; B same method lines 1212-1214 does likewise. The paired packet-listener teleport updates the player's position with `absMoveTo` before returning (A lines 945-959; B lines 928-944), so the subsequent noCollision call queries the temporary player candidate box. ServerPlayer and ServerGamePacketListenerImpl hashes are already recorded in the run artifact manifest addendum.
- A no-collision dispatch: `CollisionGetter#noCollision(Entity,AABB)` lines 47-52 delegates to the block-first collision query; `CollisionSpliterator#worldBorderCheck` lines 103-116 skips the border shape when the source AABB is fully within the floored/ceiled border rectangle. Hashes: `CollisionGetter.java` `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747`; `CollisionSpliterator.java` `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0`.
- B no-collision dispatch: `CollisionGetter#noCollision(Entity,AABB)` lines 46-63 separately asks `WorldBorder#isInsideCloseToBorder(entity,aabb)` and includes the border collision shape when the candidate is near it. Hash: `CollisionGetter.java` `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`.
- Paired `WorldBorder#getCollisionShape` constructs the same outside-of-physical-border shape; `WorldBorder#isInsideCloseToBorder` and fractional physical-edge versus integer floor/ceiling geometry are documented with F-009. A/B `WorldBorder.java` hashes `b848dd0dc6043d6c9012726844bea53cf4558c0135a08fa3be55960b0e557df9` / `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`.

## Source-level difference and reachable condition

For a block-supported candidate whose player AABB is otherwise free of block/entity collisions and contains no liquid, choose a border and candidate box that overlaps the outside-of-floor/ceiling border collision shape by more than `1.0E-7`, while the entity center still passes B's `isInsideCloseToBorder` proximity guard. A's `worldBorderCheck` sees that the deflated AABB already intersects the outside shape (`isOutsideBorder`), so it does not yield that shape; block/entity checks can therefore accept the candidate. B supplies the border shape and `noCollision` rejects the same candidate. For example, with the maximum border at X=10.5 (collision shape begins beyond ceil(X)=11), a width-0.6 player centered at X=10.9 overlaps the border shape while remaining within B's reported two-block proximity margin. Chorus fruit tries this method for a player on the server, so a differing first candidate can stop A's loop while B continues to a later candidate, or make success/failure differ.

This is a source-derived candidate-gate consequence, not a runtime reproduction. It does not claim that item consumption, hunger, sound, or random-number production changed.

## Scope

The direct player destination acceptance is in movement scope. Food/hunger production and the implementation of the chorus-fruit random selection remain outside the movement mechanic; the unchanged caller supplies equivalent candidates to the paired movement collision gate. No implementation or runtime disposition is assigned.
