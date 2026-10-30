# Independent source audit: early piston stair response aliases

## Verdict

**Supported for the bounded witness.** The ordinary extending bottom, north-facing straight cobblestone stair witness requests `+0.36 X` in 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and the accepted 1.10.2 baseline. In 1.8.9 the same second-half-step client caller reaches the full straight-stair response query but directly requests `+0.5625 X` (`0.5F + 0.0625F`). This 1.8.9 amount is a source-confirmed distinct bounded result.

The 1.8.9 distinction is a separate finding/version delta within the piston-induced player movement response. It remains the same external player operation; this evidence does not require a second hook solely because 1.8.9 uses a different caller signature. Pair coverage, all-stair applicability, and runtime parity are not established.

## Immutable inputs and provenance correction

The candidate report, companion JSON, provenance correction, embedded pin verification, and accepted baseline report/audit are bound by the digests in `source-hashes.json`.

The candidate report’s original statement that 1.9.0–1.9.3 had no in-root provenance/source manifest was false. The later immutable `provenance-supplement.md` corrects that statement; I independently checked the supplement and its `embedded-pin-verification.json` against the exact roots. All four hidden `.decompile-provenance.json` files match the supplement digests and contain exact Feather Gen2 build 2, Minecraft client, Feather metadata, decompiler/runtime/toolchain inputs, and full Java source maps. For each of 1.9, 1.9.1, 1.9.2 and 1.9.3, the correction JSON’s Feather artifact locator/hash, client artifact locator/hash, and version-metadata locator/hash match the in-root `inputs` records. Each root contains exactly 1,819 Java files and has 1,819 Java rows in its source map; every row matches and there are no missing or unpinned Java files. Aggregate result: 7,276 / 7,276 source pins match. The 13 directly relevant files per tree also match the original report JSON.

The sibling manifests for 1.8.9, 1.9.4 and 1.10.2 were also checked fully: 1,612 / 1,612, 1,819 / 1,819 and 1,845 / 1,845 Java entries match their source files. The corrected source identity closes the candidate’s intermediate provenance caveat. It does not extend the movement witness or establish runtime parity.

## 1.8.9 caller, response query, and clipping

`MovingBlockEntity.tick()` copies `progress` to `lastProgress`, increments by `0.5F`, and on extension calls `moveEntities(this.progress, this.progress - this.lastProgress + 0.0625F)` (`1.8.9/net/minecraft/block/entity/MovingBlockEntity.java:127–146`). On the second step, `progress=1.0F`, `lastProgress=0.5F`, so the `float` expression yields `0.5625F`. The caller maps its progress argument to `1-progress=0`; `MovingBlock.getCollisionShape(..., progress=0, ...)` uses the straight stair’s full-unit AABB and queries entities against it (`MovingBlockEntity.java:80–107`; `MovingBlock.java:162–193`; `StairsBlock.updateShape:49–61`). The player’s `[0.65,1.25]` X interval intersects that query on this second step. On the preceding step, the response-query shape ends at X `0.5`, before player `minX=0.65`.

The caller directly invokes ordinary three-axis `entity.move(amount*facingX,...)`. The current world-collision shape uses `getProgress(0.0F)=lastProgress=0.5F`, shortened to X `[0,0.5]` by `MovingBlock.getCollisionShape` (`MovingBlock.java:117–130,162–193`). `World.getCollisions` asks the moving block for that current collision shape (`World.java:891–922`; `Block.addCollisions:339–344`). That box is behind the player and cannot clip positive X: `Box.intersectX` clips a positive request only when the moving entity’s `maxX <= obstacle.minX` (`Box.java:93–107`). The supporting slab has top Y `0.5`, exactly the player’s feet, and strict intersection rejects that face-touch (`Box.intersects:153–158`). With the report’s clear X route, no other obstacle clips the direct request. The source placement is consistent with the isolated layout: eastward extension moves the stair at `q` to `q+EAST` and creates the separate head entity at the piston `pos+EAST` cell (`PistonBaseBlock.java:303–333`); with piston X `-2`, the head remains in X cell `-1` and ends no farther east than X `0`, behind player `minX=0.65`. This distinguishes the full-cube caller query from the current half-extended ordinary collision shape; treating them as the same AABB would be inaccurate.

The client path is reachable: 1.8.9 `Minecraft.tick()` calls `world.tickEntities()` (`Minecraft.java:1635`), and `World.tickEntities()` ticks registered block entities implementing `Tickable` without a client-side exclusion (`World.java:1244–1260`).

## 1.9 family and 1.10.2 alias math

Across the exact 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and 1.10.2 trees, raw source bodies for `getAmountExtended`, both `getShape` overloads, `moveEntities`, and `tick` in `MovingBlockEntity` each have a single identical body hash across all six trees. The exact raw hashes and tree file identities are bound in `source-hashes.json`.

For the witness’s extending eastward moving block, `tick()` advances to `progress=1.0F` with `lastProgress=0.5F`, then calls `moveEntities()`. `getShape(world,pos)` unions the moved-state shape at current and previous progress; `getAmountExtended(p)` is `p-1.0F`, so the full-cube stair response shapes are at X offsets `0` and `-0.5`, and the union’s east face is X `1` (`MovingBlockEntity.java:75–92` in 1.9/1.10.2). `StairsBlock` has no `getShape` override; inherited `Block.getShape` returns `FULL_BLOCK_SHAPE` (1.9 `Block.java:253–255`; later line shifts are covered by the exact file hashes). The response query includes the player box. For east push, `moveEntities()` calculates `box.maxX - player.minX + 0.01`, giving `1 - 0.65 + 0.01 = 0.36`, then calls `entity.move(+0.36,0,0)` (`MovingBlockEntity.java:112–146`).

The moving block’s ordinary collision shape is the same current/previous union (`MovingBlock.java:130–140` in 1.9; `:134–143` in 1.10.2). The starting player box already overlaps it, so the positive-X clip condition is false; `Box.intersectX` clips only when player `maxX <= obstacle.minX` (`Box.java:144–151`). The slab only face-touches vertically. Nonsneaking and a clear route keep the grounded sneak-edge assist, cobweb scaling, and unrelated collision retries inactive; the player is not filtered by the ordinary `PistonMoveBehavior.IGNORE` gate.

Client reachability is present in 1.9: `Minecraft.tick()` calls `world.tickEntities()` (`Minecraft.java:1446`), whose loaded block-entity iteration calls `Tickable.tick()` without a logical-side exclusion (`World.java:1302–1319`). The source roots and manifests cover each inspected 1.9 patch. The accepted 1.10.2 baseline inputs were rehashed and its complete source manifest matches; the accepted report/audit close that endpoint witness.

## Exact-version verdicts

| Exact profile | Bounded witness result |
| --- | --- |
| 1.8.9 | Client response query reached on the second half-step; direct `Entity.move` request/resolved X amount `+0.5625`. |
| 1.9 | `+0.36 X` alias. |
| 1.9.1 | `+0.36 X` alias. |
| 1.9.2 | `+0.36 X` alias. |
| 1.9.3 | `+0.36 X` alias. |
| 1.9.4 | `+0.36 X` alias. |
| 1.10.2 | `+0.36 X` accepted baseline alias. |

## Limits

This closes only the fixed extending bottom straight-stair witness and these source versions. It does not cover arbitrary stair orientations/shapes or mutable stair-shape states, the source/head retract route, the full ordinary player tick, the 1.11 boundary, every intervening patch, server behavior, pair coverage, or runtime parity. The 1.8.9 statement is a direct caller amount whose movement resolution was checked only in the stated clear layout.
