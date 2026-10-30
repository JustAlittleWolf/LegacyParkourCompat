# Piston extending-stair witness: 1.8.9 and 1.9 family aliases

## Bounded result

The accepted 1.10.2 → 1.11.2 witness is **not an exact `+0.36 X` response alias in 1.8.9**. In 1.8.9, the same eastward extending bottom, north-facing straight cobblestone stair / nonsneaking 0.6 × 1.8 local-player layout reaches the piston entity query on its second half-step, but the old caller requests `+0.5625 X` (`0.5F + 0.0625F`) through `Entity.move`. In the stated clear route, the player's box already overlaps the moving full-cube AABB, so positive-X clipping does not reduce that request; the bottom slab support only face-touches at `Y=0.5`. The earlier first half-step's moving box ends at `X=0.5` and misses player `minX=0.65`.

For **1.9, 1.9.1, 1.9.2, 1.9.3, and 1.9.4**, the accepted response aliases: on the second half-step, the old union box ends at `X=1`, `moveEntities()` computes `1 - 0.65 + 0.01 = +0.36 X`, and calls the ordinary three-axis `Entity.move`. The witness's bounded preconditions remain necessary. This does not establish all-stair behavior, runtime parity, or full pair coverage.

| Exact profile | Result for this witness |
| --- | --- |
| 1.8.9 | Reaches the response path, but requests and resolves `+0.5625 X`, not `+0.36 X`. |
| 1.9 / 1.9.1 / 1.9.2 | `+0.36 X` alias. |
| 1.9.3 / 1.9.4 | `+0.36 X` alias. |

## Evidence boundary

Compared the supplied accepted 1.10.2 → 1.11.2 response report and audit (input hashes below) with the exact local `ornithe-feather` trees for 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and the accepted 1.10.2 endpoint. All inspected files and normalized method-body hashes are recorded in `source-hashes.json`.

The 1.8.9 and 1.9.4 tree roots include ready, provenance, and full Java source manifests; the recorded files are from `feather-gen2-1.8.9+build.2` and `feather-gen2-1.9.4+build.2` (`ornithe-feather`). The current local 1.9.0–1.9.3 directories contain the exact namespace source trees but no ready/provenance/source-manifest files. The 13 directly inspected source files for each endpoint were checked against its source manifest and all 26 rows match. Their ready/provenance/source-manifest SHA-256 identities are recorded in the companion JSON. Their directly inspected file bytes and method bodies are hashed in the companion JSON; the full artifact pins for 1.9.0–1.9.3 cannot be revalidated from those directories. Thus the response-method applicability is closed against the inspected trees, while the independent exact-artifact identity for those four intermediate patch trees remains a provenance limitation.

The accepted witness inputs were rehashed before inspection:

- `.native-source/20261030/1102-1112-pool31/piston-response-closure/report.md`: `2395b1f9c28502162d6c2e9467c319add9cf89def3841b0c085b9fc67075a6a1`.
- `.native-source/20261030/1102-1112-pool31/piston-response-audit/audit.md`: `7ce10b93402f12ee7bcdbe3c10ff1c8ccd037c02803288d3ba863565a74d7886`.

## Source trace

### 1.8.9: different reachable amount gate

`net/minecraft/block/entity/MovingBlockEntity.java:80–113,127–148` has a distinct caller. `tick()` first sets `lastProgress`, increments by `0.5F`, then on extension calls `moveEntities(progress, progress - lastProgress + 0.0625F)`. Therefore each ordinary half-step passes `0.5625F`; on the second step the eastward moving collision box reaches its unshifted maximum `X=1`, intersects the player's `[0.65,1.25]` AABB, and the caller directly invokes `entity.move(amount * facingX, ...)` (`:87–107`). Unlike 1.9+, this path has no overlap-depth calculation and no `+0.01` adjustment.

The corresponding provider is also the earlier mutable-bounds implementation: `net/minecraft/block/MovingBlock.java:162–193` takes the moved block's collision AABB and shortens the moving face by `progress`; `StairsBlock.updateShape` uses full-block bounds when the stair is straight (`StairsBlock.java:49–61`). Its regular `addCollisions` separately supplies base/step collision bounds and restores full-block bounds at the end (`StairsBlock.java:383–394`). The moving block itself obtains its collision box through that provider (`MovingBlock.java:117–130`). This is not the newer state-shape/union provider.

`Entity.move` and `Box.intersectX` were checked for the isolated witness. A positive X request is clipped only against an obstacle whose minimum X is at or beyond the player's maximum X; the overlapping moving-block box is behind/through the player's current AABB and does not clip the request. The slab's top exactly face-touches the player's feet, and strict collision intersection excludes it from horizontal clipping. No other X route obstacle is part of the accepted scenario. The 1.8.9 piston caller has no `PistonMoveBehavior.IGNORE` filter, while 1.9+ does; that gate does not differ for this player, whose behavior is normal. The cobweb multiplier in `Entity.move` is also unreachable in the stated clear layout. The verdict is only this reachable bounded layout, not an assertion about arbitrary mutable stair-shape state.

### 1.9.0–1.9.4: same old full-cube overlap response

In `MovingBlockEntity.java`, the `getShape` overload applies `progress - 1.0F` while extending, translates the moved state's shape, and `getShape(world,pos)` unions the current and last half-step. `tick()` increments/clamps progress by `0.5F` before `moveEntities()` on each step. `moveEntities()` queries entities against that union, skips only `PistonMoveBehavior.IGNORE`, computes the amount from the union's axis face and the entity box, adds `0.01`, and calls `entity.move(...)`. These methods' normalized bodies are identical across all five 1.9 trees and the 1.10.2 endpoint (hashes in companion JSON). For the assigned positions at `progress=1.0`, `lastProgress=0.5`, east-facing, and player `minX=0.65`, the union max is 1.0 and the request is `1.0 - 0.65 + 0.01 = 0.36`.

The moving response shape source remains a full cube for the stair in each 1.9 release: `StairsBlock` does not override `getShape`; `Block.getShape` returns `FULL_BLOCK_SHAPE` (`Block.java:253–255` in 1.9.0–1.9.2; `:273–275` in 1.9.3–1.9.4). This is distinct from the ordinary world collision provider: `StairsBlock.addCollisions` enumerates the base and step boxes (`StairsBlock.java:66–88` in 1.9.0–1.9.2; `:67–89` in 1.9.3–1.9.4), so the accepted bottom-stair geometry remains the two actual boxes. `World.getCollisions` and `Box.intersectX` bodies match the 1.10.2 endpoint across 1.9; the support slab remains a face-touch, and the already-overlapping moving full-cube box does not clip positive X. `Entity.move` differs between 1.9 and 1.10.2 only in the grounded step-assist loop spelling its `0.05` decrement as a local variable versus literals; the witness has no clipped horizontal axis, so that retry gate is not reached. The cobweb scaling gate is likewise inactive.

The block identity is preserved. `Block.java` registers block 4 as `cobblestone`, binds that local block value as the base state of block 67 `stone_stairs` (1.8.9 `:830–836,967`; 1.9.0 `:677–684,818`; 1.9.3 `:722–728,863`). `Blocks.STONE_STAIRS` aliases registry key `stone_stairs` in the inspected endpoints. The stair inherits the regular movable material behavior; the piston structure scan accepts regular movable blocks. The local player has the ordinary reset size `0.6F × 1.8F` in 1.8.9 and all inspected 1.9 patches (`PlayerEntity.resetPos`; body hash in JSON); its inherited piston move behavior is normal in 1.9 and it does not use a sneak-edge-specific piston gate.

Client reachability was checked, not inferred from the server path: `Minecraft.tick` calls `world.tickEntities()` in 1.8.9 (`Minecraft.java:1635`), 1.9 (`:1446`), and 1.9.3 (`:1449`); the relevant `World.tickEntities` methods iterate and invoke `Tickable.tick()` on ticking block entities (`World.java:1244–1254`, `:1302–1313`, `:1308–1319`) without a logical-side exclusion. The piston block entity can therefore issue this direct local-player response on the client.

## Limits

This check closes only the exact extending bottom straight-stair witness's response alias across the stated trees. It does not re-audit the accepted geometry, source/head retract path, every stair shape, whole player tick, 1.11 boundary, server behavior, runtime parity, or the 1.8.9 profile's general piston movement. No implementation, build, tests, game launch, decompilation, download, or canonical source write was performed. The 1.9.0–1.9.3 full artifact provenance gap is explicit above.
