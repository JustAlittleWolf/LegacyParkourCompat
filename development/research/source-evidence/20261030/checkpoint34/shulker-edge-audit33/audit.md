# Independent audit: Shulker opening push and sneak-edge backoff

Verdict: **ACCEPT the single-call movement difference with a corrected numeric binding.** The supplied draft’s geometry and collision argument survive direct-source recheck, but its `.95F` sine-table result and `h` value are incorrect. Do not bind acceptance to the original draft hash as written. The revised finding in this directory is the reviewed revision.

## Evidence boundary

Read only the assigned source-only witness report/draft and the canonical Feather gen2 sources under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Directly checked source files and their SHA-256 digests are listed in `SHA256SUMS.txt`. The source-report identity records release/mapping provenance. This is a bounded witness audit, not full pair coverage, client-caller review, runtime parity, or a claim about a prior trajectory.

## Callback and reachability within the assigned slice

In 1.10.2 `ShulkerEntity.tick` computes `g = getPeekAmount()*0.01F`, stores `prevOpenProgress`, advances `openProgress` by `0.05F` toward `g`, then forms `d`, `e`, `h=d-e`. For `h>0`, a WEST-attached Shulker expands its shape to `x+0.5+d`, sets the player displacement to `(+h,0,0)`, queries entities against the expanded shape, excludes Shulkers and `noClip`, and calls untyped `entity.move(k,l,m)` (`ShulkerEntity.java:231-296`, especially `257-295`). In 1.11/1.11.1/1.11.2 the same callback sequence uses `entity.move(MoverType.SHULKER,i,j,k)` (`1.11.2 ShulkerEntity.java:246-311`; the 1.11.1 file is byte-identical to 1.11.2).

The exact 1.10.2 `Entity.move(double,double,double)` edge predicate is `onGround && isSneaking() && this instanceof PlayerEntity`; for positive X it repeatedly checks `world.getCollisions(this,getShape().moved(dx,-1,0))` and subtracts `0.05` until support is found or `dx` reaches zero (`Entity.java:468-482`). The 1.11+ typed overload retains that logic only under `(moverType==SELF || moverType==PLAYER)` (`1.11.2 Entity.java:519-532`; same gate in 1.11 and 1.11.1). `SHULKER` therefore skips the edge check.

A legal opening-progress prestate exists through source behavior. Shulker shooting starts set peek amount to `100`; stopping sets it to `0` (`ShulkerEntity.java:581-586`). While closing from `1.0F` toward zero, two source `-0.05F` updates produce `0.95F` then `0.9F`; if the peek target becomes `100` before the next update, the `+0.05F` update reaches `0.95F`. The finding requires only this legal prestate and source entry, not proof of a particular full AI/network trajectory.

## Corrected float calculation

`MathHelper.sin(float)` indexes its 65,536-entry float table with `(int)(x*10430.378F)&65535` (`MathHelper.java:15-17`), whose entries are `(float)Math.sin(i*PI*2/65536)` (`:426-429`). For the actual Java-float steps `.9F + .05F`:

- At `.9F`, `(0.5F+p)*(float)PI` selects index `45875`; the table value is `-0.9510506F`, so `e = 0.975525289773941`.
- At `.95F`, the float expression selects index `47514` (not `47513`); the table value is `-0.9876943F`, so `d = 0.9938471615314484`.
- Thus `h=d-e = 0.018321871757507324` (positive and less than `0.05`).

The supplied player `minX≈1.9838397` remains a valid witness with corrected values: the old opening max-X is about `1.97552529` (gap `0.0083144`); the grown max-X is `1.99384716` (strict overlap about `0.0100075`). After the push, player min-X is about `2.00216157`, beyond both the stair top-step max-X `2.0` and opening-box max-X `1.99384716`.

## Shape, support, and collision resolution

The isolated bottom EAST-facing stair at `(1,0,0)` resolves to `STRAIGHT` because neighboring stair checks do not find a matching stair; its source collision pieces are the bottom base `[0,1]×[0,0.5]×[0,1]` and EAST upper step `[0.5,1]×[0.5,1]×[0,1]`, translated by block position (`StairsBlock.java:33-42,67-102,311-348`). A full support block at `(1,-1,0)` makes placement legal. The player has source dimensions `0.6F×1.8F` (`Entity.java` constructor) and the inherited living step height is `0.6F`; neither changes the direct witness.

For feet at `y=1`, center Z `0.5`, and minX `1.9838397`, the player strictly overlaps the stair top step in X/Z and has its feet on the top face. A prior downward clipping against that top can set `onGround` (`Entity.java:633`); strict intersection in X/Z is sufficient for the downward Y clip. The support block under the stair has maxY `0`; after the old `-1` probe the player minY is `0`, so it only face-touches and does not count as collision.

Block collision admission moves each local shape to block coordinates and adds it only on strict overlap (`Block.java:354-362`, `Box.java:245-251`). Movement `getCollisions` gathers block shapes and entity collision shapes intersecting its query; the entity query expands by `0.25` to enumerate, then strictly checks collision shape versus movement query (`World.java:1013-1028`). Chunk selection returns candidates whose entity boxes strictly intersect requested bounds (`WorldChunk.java:667-681`). Shulker’s collision shape is its current opening shape while alive (`ShulkerEntity.java:445-447`).

In the old edge probe, translating the player `(+h,-1,0)` places minX≈`2.00216157`: this clears the stair upper step and the Shulker opening shape; the support block ends exactly at the probe’s minY=0. No other collision is present in the stated empty neighborhood, so the probe is empty and `0<h<.05` makes old X displacement zero.

In the new SHULKER move, the base collision query has minY=1, so the stair top ends at the query’s lower face and the support block is below the enumerated vertical sweep. Strict overlap rejects the stair either way. The growing Shulker shape is in the entity query and overlaps the initial player box, but positive-X `Box.intersectX` clips only when the moving box’s maxX is at or before the obstacle’s minX; the player is already overlapping that obstacle, so the Shulker box does not reduce `h` (`Box.java:185-203`). With no other obstacle the immediate new X displacement is `h`. This demonstrates a concrete single-call delta; no post-call trajectory is claimed.

## Attachment and callback gates

For the Shulker at `(0,1,0)` with face WEST, the support block at `(-1,1,0)` satisfies the attached-face solid-cube check. The opposite cell `(1,1,0)` is empty, so the source’s “solid behind attached face” relocation check does not fire. The player is strictly inside the new WEST-expanded AABB but outside the old one; the callback’s `h>0`, `not Shulker`, and `!noClip` gates admit the call. EAST upper-step collision and WEST opening expansion have positive X overlap exactly as required. A local logical-client player can receive movement through this entity response; client caller/type-boundary coverage was supplied separately and is outside this independent geometry audit.

## Finding disposition

Accept one independent operation: the grounded sneaking PlayerEntity edge-support reduction performed inside `Entity.move`, which applies to the untyped 1.10.2 Shulker push and is bypassed by the 1.11+ `SHULKER` mover type. The Shulker callback’s operation type is the trigger/boundary for this finding, not a second independent finding. The first inspected release exhibiting the typed call and new gate is 1.11; 1.11.1 and 1.11.2 retain it. No exact patch boundary within releases is asserted.

The corrected, auditable finding is `shulker-opening-sneak-edge-corrected.md` in this directory. Hashes bind it and the original witness inputs; no canonical finding/product file was edited.

Additional collision-response check: ordinary Entity.getCollisionAgainstShape returns null on both releases (`Entity.java:1462` / `1552`), so the player adds no second custom collision response against the Shulker.
