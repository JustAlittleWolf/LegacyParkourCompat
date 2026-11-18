# B11 dry support and stepping comparison

Scope: ordinary player crouch edge gate, support selection, step collision candidate acquisition and height selection, plus ordinary full cube, lower slab, stair, and pane collision-shape controls. Reviewed endpoint tick/move callers only to establish invocation context; stopped before subsequent position/tick writes and excluded callbacks, fluids, input production, and AutoJump.

## Source identity gate

Canonical source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.21.6,1.21.7,1.21.8}/mojmap`.

The exact `.decompile-provenance.json` SHA-256 values match for 1.21.6 (`56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`) and 1.21.7 (`45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`). The provenance inputs identify each exact release's client jar and Mojang `client.txt` mapping artifact; 1.21.6 mapping artifact SHA-256 is `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`, and 1.21.7 is `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`.

The 1.21.8 `mojmap` root contains only `com/` and `net/`; it has no `.decompile-provenance.json` or source-manifest file. Therefore the requested 1.21.8 release-root source manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` could not be verified, and the 1.21.8 mapping artifact identity is unavailable from the permitted source root. This blocks an accepted exact-edge verdict for 1.21.7→1.21.8.

## Paired source evidence

For all three roots, these files have the same SHA-256, so their intact source bodies are byte-identical across the inspected endpoints:

- `net/minecraft/world/entity/Entity.java` — `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`
- `net/minecraft/world/entity/player/Player.java` — `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
- `net/minecraft/world/entity/LivingEntity.java` — `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`
- `net/minecraft/client/player/LocalPlayer.java` — `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`
- `net/minecraft/world/level/CollisionGetter.java` — `03752693709491670a3c6b1d91114d46d92d6dfb1f48d3ecea3725c08b22ac8c`
- `net/minecraft/world/level/block/Block.java` — `d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2`
- `net/minecraft/world/level/block/SlabBlock.java` — `d3666004285a0df44a3b20244d2f474e2feaea78e766b7da1317ca7bd70c8ec8`
- `net/minecraft/world/level/block/StairBlock.java` — `f08b84f9c6f8ed1f33f95cee0a6f78cf27dc1789c3217d70af5f4be9a2d9225d`
- `net/minecraft/world/level/block/CrossCollisionBlock.java` — `a7729019112720b75fd3d171ea85656e40f650daad7cf882d0483d52cea314d6`
- `net/minecraft/world/level/block/IronBarsBlock.java` — `1b7a684fbf9a4ddef67b838a379ddff7310b57e39e2bec7b203cd44223eb0f93`

Relevant intact method ranges in each identical body: `Entity.move` 663–830 invokes player-adjustable edge backing-off before collision; `Entity.collide` 1002–1031 gates step-up on positive `maxUpStep`, downward collision or grounded state, and horizontal collision, builds the raised collision query, then returns the first sorted candidate whose horizontal squared displacement beats the normal result; `Entity.collectCandidateStepUpHeights` 1033–1052 gathers each collider's Y coordinates, casts candidate height deltas to `float`, excludes negative and equal-to-normal-Y values, stops scanning that shape above the maximum, and unstable-sorts the candidate array. `Player.isStayingOnGroundSurface` 373–375 returns `isShiftKeyDown`; `Player.maybeBackOffFromEdge` 1038–1087 requires not flying, non-upward motion, SELF/PLAYER mover, the ground-surface predicate, and the above-ground predicate before reducing X, then Z, then diagonal movement in 0.05 increments; `Player.canFallAtLeast` 1093–1100 probes an inset support AABB using `1.0E-7` offsets. `LivingEntity.maxUpStep` 3651–3654 returns the step-height attribute, with a minimum of `1.0F` while controlled by a player.

Ordinary controls are likewise unchanged in paired files: a full block uses the standard block collision shape; `SlabBlock.getShape` 59–65 selects top half, bottom half or full cube from vanilla `TYPE`; `StairBlock.getShape` 77–92 chooses precomputed bottom/top straight/outer/inner shapes using `HALF`, `SHAPE`, and facing rotations; `IronBarsBlock` 30–34 constructs cross-collision shapes with 2/16 central-post and 2/16-to-full-height arm bounds, while `CrossCollisionBlock.getCollisionShape` 73–75 selects collision rather than visual shapes. These remain vanilla-state controls.

## Edge coverage and disposition

- **1.21.6→1.21.7:** Exact adjacent endpoint provenance and mapping identities verified. All inspected movement and control source files are byte-identical; no independent support/stepping difference found in this bounded slice.
- **1.21.7→1.21.8:** Inspected endpoint files are byte-identical, but required 1.21.8 source-manifest and mapping-artifact identities are not present in the permitted root. This is a provisional no-difference observation only, not an accepted exact-edge verdict.

No implementation, tests, runtime verification, or source generation was performed. Remaining gate: source preparation owner must provide/publish the exact 1.21.8 manifest with the requested SHA-256 and mapping-artifact identity, then revalidate this edge's source hashes/body integrity.
