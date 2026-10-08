# F-004 — Fall-flying fall distance changes farmland trample input

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: glide landing callback input; S3-glide, S3-fall-distance-farm-trample, S4-move-core, S4-callbacks, S5-block-shapes, S7-external-velocity
- Classification: changed behavior
- Confidence: source-confirmed player-state difference; server world-state effect and later client collision consequence are conditional
- Applicability: player fall-flying above farmland, with the world update occurring on the server
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned `mojmap` source trees and official original client jars

## Paired evidence

- A `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`: `travel(Vec3)` lines 2107-2111 sets `fallDistance = 1.0F` when current vertical movement is greater than `-0.5`.
- B same path, SHA-256 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`: `travel(Vec3)` lines 2117-2119 calls `checkSlowFallDistance`; B `Entity.java` lines 2147-2151 resets to `1.0F` only when vertical movement is greater than `-0.5` and `fallDistance > 1.0F`.
- `Entity.move` is text-identical A/B (A SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; B SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`): lines 594-608 set ground state, call `checkFallDamage`, then call the separate `updateEntityAfterFallOn` callback. `Entity.checkFallDamage` at A lines 991-1001 / B lines 1000-1010 passes positive `fallDistance` to the landing block's `fallOn` callback before resetting the value.
- A/B `net/minecraft/world/level/block/FarmBlock.java` are byte-identical, SHA-256 `ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb`: `fallOn` lines 89-98 uses the supplied value in `random.nextFloat() < fallDistance - 0.5F` on the server, then may call `turnToDirt`; `turnToDirt` lines 101-103 performs `setBlockAndUpdate`. `getShape` lines 29, 63-65 returns a 15/16-height shape.
- `Blocks.DIRT` is registered as a plain `new Block(...)` at A `Blocks.java` lines 63-65 / B lines 68-70. `BlockBehaviour.getShape` defaults to `Shapes.block()` at A lines 278-280 / B lines 288-290. Paired file hashes: `Blocks.java` `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b` / `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`; `BlockBehaviour.java` `c0e62233fa21be352953edda6b411727762165fa2cc8d645c7c08406f92df08b` / `183ea122421dc3c0649ff0c64affda3d3cc3090c9fc2e01d96da44410d4b39c1`.

## Source-level difference

For the same fall-flying branch predicate, A unconditionally overwrites `fallDistance` with `1.0F`; B overwrites it only when the prior value exceeds `1.0F`. Therefore, when the pre-branch value is below `1.0F`, A supplies `1.0F` to the landing callback path and B preserves the smaller value.

## Reachability and dependencies

`LivingEntity.travel` -> player `Entity.move` -> `Entity.checkFallDamage` -> landing block `fallOn` -> `FarmBlock.fallOn` -> server `setBlockAndUpdate(farmland -> dirt)` -> replicated block state -> later player collision shape query. The movement response hook `updateEntityAfterFallOn` is a separate call and does not consume `fallDistance`. FarmBlock's relevant code and shapes are identical across A/B; only the upstream travel value differs.

## Consequence and uncertainty

Source-proven: the A and B values can produce different farmland-trample predicates. If the server-side random check succeeds, farmland is replaced with dirt; farmland's shape is 15/16 block height while dirt inherits the full-block shape. Predicted only: after the world update reaches a client and the player later collides with that location, the collision surface can differ. The server RNG, block update, and delivery are external inputs; no actual world update or trajectory is claimed.

## Handoff

Independent fall-distance-to-farmland callback delta. Other landing callbacks, complete shape/provider coverage, and external server-world production remain open. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-TICK, INV-STATE, INV-COLLISION, INV-WORLD-MOVEMENT, INV-EXTERNAL; S3-glide, S3-fall-distance-farm-trample, S4-move-core, S4-callbacks, S5-block-shapes, S7-external-velocity. **Implementation disposition:** deferred until blind freeze.
