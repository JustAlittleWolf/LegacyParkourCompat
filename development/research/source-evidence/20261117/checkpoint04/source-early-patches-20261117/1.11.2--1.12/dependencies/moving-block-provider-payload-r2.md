# 1.11.2 → 1.12 moving-piston provider payload

## Binding and limits

Source-only bounded follow-up to the exact `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/` pair. Sources were read-only. This closes only the dynamic `MovingBlock` collision-provider and `MovingBlockEntity` payload/timing slice. It does not accept or reopen the piston player-response claim, establish full pair coverage, or freeze the pair.

The source pair is bound to the coverage report SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`; 1.11.2 readiness marker `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` and source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 mapping-local provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

Permitted prior evidence identities checked: provider/callback inventory SHA-256 `39052ed2f95606f012876136a3277b2ff9fc55c075d19936e5e384afc6b193d8`, its bounded audit verdict SHA-256 `0bb66867a2a769aa52588ace79ef592c8ff8b5cc98c4cf726f5e8e735bb1129c`, piston/portal player-response slice SHA-256 `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b`, and full-pair audit r2 SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`. The latter remains `PARTIAL`; these inputs do not imply pair freeze.

Exact inspected source hashes (SHA-256; paths relative to each `ornithe-feather` root):

| Source | 1.11.2 | 1.12 |
| --- | --- | --- |
| `net/minecraft/block/MovingBlock.java` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` | `2bcea815334849934eaa3283eedad3a7a27f7b77503a8789aae1479af725df5c4` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| `net/minecraft/block/PistonBaseBlock.java` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` | `a0b929dec717a33355b291c6483430288a6a0edb9a4f3338b7d708b282898626` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a` |


## Compared route and result

- `MovingBlock.getCollisionShape` (both: lines 129–132) looks up the block entity and returns `null` if it is absent or not a `MovingBlockEntity`; otherwise it returns that entity's dynamic shape. `MovingBlock.addCollisions` (both: lines 135–140) performs the same lookup and forwards to `MovingBlockEntity.addCollisions` only when present. The exact `getShape` fallback to `FULL_BLOCK_SHAPE` is also identical (lines 143–151); that fallback does not cause `addCollisions` to emit a collision box.
- The only relevant `MovingBlock.java` body delta found is the 1.12 `getFaceShape(...)=UNDEFINED` override (lines 192–195); 1.11.2 has no FaceShape API. This is not read by the ordinary block-collision query traced here and does not change the dynamic collision box.
- `MovingBlockEntity.java` is byte-identical at both endpoints. Its payload fields are `movedState`, `facing`, `extending`, `source`, `progress`, and `lastProgress` (lines 23–44). `getShape` unions the moved-state shape at current and previous progress; each box is offset using the extension amount and facing (96–105). The source flag substitutes a piston-head state for the shape when retracting (107–114). Collision emission preserves the source-retraction base case, no-clip direction/progress gate, source-head shortness rule, moved-state collision provider, offset construction, and translated boxes in the same order (318–345). Tick advancement, entity pushing, and completion are identical (273–289): the progress step is `+0.5F`, capped at `1.0F`; the next tick removes the entity and restores the moved state only if the block at that position is still `MOVING_BLOCK`.
- Piston extension/retraction creates the same moving-state/entity pairs in `PistonBaseBlock` at both endpoints. The source piston and moved-block cases call `setBlockState(MOVING_BLOCK, ...)` immediately followed by `setBlockEntity(createMovingBlockEntity(...))` (1.11.2: 178–186, 298–318; 1.12: same lines). Constructor payload ordering is identical and sets the supplied moved state, facing, extending flag, and source flag (MBE: 36–44). NBT persists block id/data, facing, `lastProgress`, extending, and source, and restore sets `lastProgress = progress` (295–315, identical at both endpoints). No payload or timing delta appears in these paths.
- For ordinary player movement, `Entity.move` calls `world.getCollisions(this, this.getShape().expanded(x, y, z))` before clipping Y, X, then Z (1.11.2: 564 onward; 1.12: 567 onward). `World.getCollisions(entity, box)` scans queried block positions and dispatches each state's `addCollisions` (1.11.2: 960–1015; 1.12: 964–1019). The MovingBlock provider then delegates to the same MBE logic above. This ordinary query has no client/server exclusion gate, and this query/provider path has no endpoint behavior difference.

## Remaining lifecycle gate and bounded conclusion

Collision contribution is conditional on a `MovingBlockEntity` being discoverable at that block position. A `MOVING_BLOCK` block state by itself is insufficient: both the shape and collision callbacks return/emit no dynamic shape/box for a missing or wrong-type block entity. This gate is identical at both endpoints. The paired piston producer installs the block state and then its block entity synchronously; NBT restoration restores the same payload, and completion removes the entity and replaces the state only when it remains a moving block. However, this slice does not close client-side packet/chunk restoration ordering or prove that every reachable client query sees the paired state/entity lifecycle together. That lifecycle ordering is the remaining bounded gate for pair closure.

Conclusion: no 1.11.2→1.12 difference was found in the player-eligible dynamic moving-piston collision boxes or ordinary collision-query behavior. Do not infer a piston movement change from this provider slice. The next source action is to pair the client-side moving-block state/block-entity installation and chunk/NBT restoration ordering against the same lifecycle gate, then independently close any remaining piston/base/head provider and state combinations required by the full-pair audit.

## Immutable digest correction
Corrects only the MovingBlockEntity.java SHA-256 identity in this report to a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e, matching both endpoint source files and source-manifest rows. Original report SHA-256: 46ef94ac4acb8ac3f0f05d675059f5dd4e8f4821c1c62633e6d78bd7ff9da694. Bound prior verdict (MovingBlock provider REQUEST CHANGES audit) SHA-256: 458380c29c8f86ed12f5c1a48a7bc32a7e62650a9ec6ca61c6e0a833ecd96fe5. No technical comparisons or accepted claims were changed.
