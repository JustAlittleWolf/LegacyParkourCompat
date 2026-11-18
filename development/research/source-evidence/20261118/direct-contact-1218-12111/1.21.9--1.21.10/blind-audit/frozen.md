# Blind direct-contact audit: 1.21.9 → 1.21.10

## Frozen verdict

**Bounded equality for the ordinary local player path.** In a dry, non-riding, non-flying path, HoneyBlock's slide velocity/fall-distance reset and WebBlock/SweetBerryBushBlock's slowdown producers are movement-equivalent between 1.21.9 and 1.21.10. For Web/berry slowdown, the first subsequent ordinary `MoverType.SELF` consumption remains the same. No direct-contact movement difference was found in this bounded slice.

The changed `Entity.move` branch skips the stored `stuckSpeedMultiplier` only for `MoverType.PISTON` in 1.21.10. That piston-only behavior is outside this audit. The adjacent HoneyBlock `fallOn` calls `causeFallDamage`; damage calculation is outside scope and is not assessed here.

## Exact source identity and body integrity

Both sides are Mojmap sources. The 1.21.9 embedded `.decompile-provenance.json` records the 1.21.9 version metadata input at `https://piston-meta.mojang.com/v1/packages/91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json` (SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), client JAR input (SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`), and namespace directory `mojmap`. The listed per-source hashes match the six 1.21.9 files below.

The 1.21.10 version-root `mojmap.provenance.json` and `mojmap.ready.json` identify version `1.21.10`, mapping `mojmap`, client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`, and the published source manifest (`mojmap.sources.sha256`, whose SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`). Each used 1.21.10 body hash below matches that manifest. No online mappings or other source trees were used.

| Mojmap source file | 1.21.9 SHA-256 | 1.21.10 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/HoneyBlock.java` | `df26bbd9b72ac98b93b97c21c656d76d33c1968ccf136884b177a0b95dff4f61` | `7f8a0dca359d623f5db71e206679f20de8cd7c42f244d0f0230b876f5131f898` |
| `net/minecraft/world/level/block/WebBlock.java` | `ba24400cf87b00c88c5bd441c781a128b6973a46bff3df62c6144e9bfc5abb43` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `0f3629034c12f654701ca7dfb24f1db83a57024e8552247198b11e21dfcf57af` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` |
| `net/minecraft/world/entity/Entity.java` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `195711b3651969f0202296bc4522b1dbc67d190ad21fa73b00e97b5040528620` | `d1bacd23db38cfdc437f3571d297bef8bd2785beceb0ec632e0ad791d5011350` |

## Compared behavior and reachable order

- **Honey slide:** `HoneyBlock.entityInside` calls the same `isSlidingDown` and `doSlideMovement` operations in both versions. The slide gate is: entity is not on ground; entity Y is at most block Y + `0.9375 - 1.0E-7`; `getOldDeltaY(velocityY) < -0.08`; and either horizontal offset from the block center plus `1.0E-7` exceeds `0.4375 + width/2`. When `getOldDeltaY(velocityY) < -0.13`, X/Z velocity are scaled by `-0.05 / getOldDeltaY(velocityY)`; otherwise they are retained. Both branches set Y to `getNewDeltaY(-0.05)` and call `resetFallDistance()`. The method arithmetic, gate order and writes are equal.
- **Honey landing callback:** the `fallOn` body is identical in the compared releases. Its damage call is excluded; this audit makes no claim about damage behavior.
- **Web:** both versions set the stuck multiplier to `(0.25, 0.05F, 0.25)`. A LivingEntity with Weaving instead gets `(0.5, 0.25, 0.5)`. The 1.21.10-added boolean parameter is unused by this block.
- **Sweet berry bush:** both versions set `(0.8F, 0.75, 0.8)` for LivingEntity other than FOX and BEE. The added boolean is unused. Server-side hurt logic is outside the movement finding and excluded.
- **Producer dispatch:** `Entity.checkInsideBlocks` calls block-state `entityInside` only when the moving entity intersects the block's inside shape (or equals the full-block shape) and the position is not already visited in that movement trace. In 1.21.10 it additionally computes `distanceSquared(from,to) > Mth.square(0.9999900000002526) || deflatedTargetBox.intersects(blockPos)` and passes that boolean. Honey, Web and berry do not read it. Their producer logic is therefore reached under the same collision/visited gates in this slice.
- **First ordinary consumption:** `LivingEntity` calls `move(MoverType.SELF, getDeltaMovement())` during travel and later calls `applyEffectsFromBlocks()` after movement. The block-intersection scan stores Web/berry slowdown via `Entity.makeStuckInBlock`, which resets fall distance and writes `stuckSpeedMultiplier`. In both versions `Entity.move` checks multiplier length squared against `1.0E-7`, multiplies requested movement by the multiplier, clears the multiplier, and zeroes delta movement. The 1.21.10 new `MoverType.PISTON` guard wraps only the multiplication; SELF satisfies the guard, so first subsequent SELF consumption is the same. Honey's slide directly writes delta movement and resets fall distance during block-effect scanning; it does not use the stuck multiplier.

## Source anchors

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

- `net/minecraft/world/level/block/HoneyBlock.java`: `fallOn` lines 51–59; `entityInside` 63–71; slide gate 81–101; movement/reset 106–118.
- `net/minecraft/world/level/block/WebBlock.java`: `entityInside` 27–34.
- `net/minecraft/world/level/block/SweetBerryBushBlock.java`: `entityInside` 82–96.
- `net/minecraft/world/entity/Entity.java`: stuck multiplier consumption in `move` 687–692 (1.21.9) / 687–695 (1.21.10); block-effect dispatch at 1194 (1.21.9) / 1199 (1.21.10); `makeStuckInBlock` 2783–2786 (1.21.9) / 2788–2791 (1.21.10); effect application after traced movement at 798–809 (1.21.9) / 801–812 (1.21.10).
- `net/minecraft/world/entity/LivingEntity.java`: ordinary SELF movement calls include lines 2293, 2297, 2301, 2353, 2363, 2391 and 2479; `applyEffectsFromBlocks()` at line 2852 in both releases.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: block `entityInside` signatures and state dispatch at lines 355 and 744–746 in both releases; 1.21.10 adds the boolean parameter and forwards it.

## Bounded coverage

Reviewed the exact producer bodies, the state dispatch and intersection gates, `makeStuckInBlock`, the stored multiplier's first ordinary SELF consumption, and LivingEntity movement/effect ordering for this slice. No Honey/Web/berry ordinary SELF movement difference found. Explicitly unreviewed: piston consumption, damage, fluids/portals, attributes, sprint, collision geometry, unrelated blocks, and whole-pair/runtime behavior.