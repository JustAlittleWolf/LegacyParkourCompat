# 1.16.4–1.16.5 source-only support/stepping coverage

Status: COMPLETED for the assigned bounded projection; no paired difference found. This is not full-pair freeze or runtime parity evidence.

## Evidence identity

- Older source root: `decompiled_minecraft/1.16.4/mojmap/`; `.decompile-provenance.json` SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`.
- Older exact mapping input: official Mojang `client.txt`, URL object `0837de813d1a6b67e23da3c520a84e872c8d2f0e`, SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`; exact client jar SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`.
- Newer source root: `decompiled_minecraft/1.16.5/mojmap/`; version-root `mojmap.ready.json` confirms version `1.16.5`, mapping `mojmap`, ready status, and source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` (verified against `mojmap.sources.sha256`).
- Newer official mapping artifact: `1.16.5/client_mappings.txt`, SHA-256 `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`; client jar SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`.
- Exact mapped-source path pairs below have equal full-file SHA-256s, with the stated digest also matching each corresponding side's published source identity (older provenance; newer source manifest). Thus the paired methods/bodies are intact and byte-identical.

## Bounded inventory and paired source IDs

All paths below are relative to each version's `mojmap/` root. Paired release IDs are `1.16.4` / `1.16.5`.

- `net/minecraft/client/player/LocalPlayer.java`, `move(MoverType, Vec3)` lines 862–868: wrapper delegates to `super.move`, then updates auto-jump. Call-path witness only; auto-jump internals excluded.
- `net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge` lines 1010–1060 and `isAboveGround` lines 1062–1065: ordinary `SELF`/`PLAYER` admission gate, 0.05 decrement loops and ground/fall-distance probe are identical. Full-file SHA-256: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- `net/minecraft/world/entity/Entity.java`, `move(MoverType, Vec3)` lines 485–592: after non-piston, non-`noPhysics` ordinary movement enters `maybeBackOffFromEdge`, clips, computes horizontal/vertical collision and grounded state, checks fall damage, then calls the underfoot block's fall/step callbacks. Full-file SHA-256: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- Same file, `getOnPos()` lines 594–609: floors X/Z and Y minus `0.2F`; if that state is air, falls back one block only for fences, walls or fence gates. `getBlockPosBelowThatAffectsMyMovement()` lines 627–629 supplies the box-minY-minus-`0.5000001` query. Both bodies are identical across the pair.
- Same file, `collide(Vec3)` lines 668–707: initial clip, grounded-or-downward-collision plus horizontal-block admission, candidate vectors, `vec34.y < maxUpStep` gate, horizontal-distance-squared comparisons, and final downward reclipping are identical and in the same order.
- Same file, `collideBoundingBoxHeuristically` lines 713–727; `collideBoundingBoxLegacy` lines 729–760; `collideBoundingBox` lines 762–795: axis-zero heuristic, legacy vs level-reader path, Y-first clipping, `abs(x) < abs(z)` tie/order, and in-place AABB translations are identical.
- `net/minecraft/world/phys/shapes/Shapes.java`, axis `collide` provider around lines 245–305: generic candidate clipping operation is byte-identical. Shape-family construction is excluded.
- `net/minecraft/world/level/CollisionGetter.java`, `noCollision` and block-collision dispatch lines 36–68, plus `CollisionSpliterator.java` block iteration; `net/minecraft/world/level/BlockGetter.java` `getBlockState` and `getBlockStates` lines 23 and 39–41; `net/minecraft/world/level/LevelReader.java` and `Level.java` block-state access. These paired files are byte-identical. Ordinary block collision queries flow through `CollisionSpliterator` to `BlockState.getCollisionShape`; `getOnPos` uses `isAir` and the fence/wall/gate category fallback, not a collision shape.

## Concrete reachable path / verdict

A local player using an ordinary non-riding land travel branch reaches `LivingEntity.travel`'s `move(MoverType.SELF, delta)` call; `LocalPlayer.move` delegates to the base implementation, and `Player.maybeBackOffFromEdge` runs before `Entity.collide`. When a horizontal collision occurs while on ground (or on a downward collision) and `maxUpStep > 0`, `Entity.collide` evaluates the same step candidates and score/order in both exact releases. Post-clip support position and fence/wall/gate air fallback also match. No support/stepping difference is reachable between this pair in the assigned slices.

## Dependencies and exclusions remaining

Shape-family geometry and block-state shape production are outside this assignment and remain dependencies for proving concrete block-specific candidate outcomes. Also excluded: fluids, callback effects beyond recording their unchanged call site/order, piston sweeps, entity push/locomotion, and auto-jump internals. This projection does not close other movement tick areas or the pair's independent coverage audit.
