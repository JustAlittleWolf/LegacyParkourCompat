# 1.9 to 1.9.1 piston player response comparison

## Evidence identity

Both sides are exact published releases decompiled to Feather mappings, with embedded `.decompile-provenance.json` manifests. The manifests name the exact Minecraft client and Feather inputs; no mapping lookup outside those manifests was used.

- 1.9: Feather `1.9+build.2`; mapped artifact SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; client SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`; release metadata SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`.
- 1.9.1: Feather `1.9.1+build.2`; mapped artifact SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; client SHA-256 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`; release metadata SHA-256 `b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56`.

The generated `MovingBlockEntity.java` source hash recorded in each manifest is `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c`; direct SHA-256 checks of both files agree. `Entity.java` is likewise identical on both sides (SHA-256 `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`). `ClientWorld.java` is byte-identical. The paired `World.java` ranges covering the regular world tick, block-entity admission/tick loop, and entity tick loop were compared and are equal (the full files differ elsewhere).

## Bounded coverage and result

Compared `net/minecraft/block/entity/MovingBlockEntity.java`: `moveEntities()` (line 90), `finish()` (151), and `tick()` (164) in both releases; also `Entity.getPistonMoveBehavior()` (line 2246) and the caller boundaries in `World` and `ClientWorld`.

A client world calls `super.tick()`. The shared world block-entity loop admits an entity only when it is not removed, has a world, its chunk is loaded, and its position is inside the world border, then invokes `Tickable.tick()`. There is no change to these gates in the compared ranges. A directly queried ordinary player inherits `Entity.getPistonMoveBehavior() == NORMAL`; the moving-block callback skips only entities whose behavior is `IGNORE`.

No source difference was found in the assigned player-response operation. In both versions, each admitted moving-block-entity tick calls `moveEntities()`. The callback queries entities intersecting the current moved-shape envelope, chooses `facing` while extending and the opposite direction while retracting, and for a moved slime block writes the selected axis velocity component to that direction's signed unit offset. It then obtains the entity box, computes the selected-axis overlap displacement plus `0.01`, and issues one `entity.move(...)` request with that displacement multiplied by the direction's axis offset. Other displacement components remain zero.

Progress timing is also equal: `lastProgress` is set from `progress`; before completion, `progress` advances by `0.5F` and is capped at `1.0F` before the response call. Once the prior progress is already at least `1.0F`, the callback still calls `moveEntities()` before removing the block entity and replacing the moving block. `finish()` finalizes without calling `moveEntities()`.

**Conditional equivalence:** given equal moved-state geometry, entity-query overlap/list, and tick/world availability, the two revisions select the same ordinary player, apply the same optional slime velocity write, and issue the same move request in the same order. This finding does not assert the final collision-resolved displacement; clip/solid providers and support, shape/query overlap, and world-availability behavior are held equal or outside this bounded comparison.

No canonical nested finding was created because the assigned operation has no observed difference.