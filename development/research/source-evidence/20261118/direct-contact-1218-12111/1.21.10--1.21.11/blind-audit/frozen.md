# Blind direct-contact audit: 1.21.10 → 1.21.11

## Frozen disposition

**Bounded no-difference verdict.** For an ordinary dry, non-riding, non-flying local player, the examined Honey Block slide velocity/fall-distance response and the Web Block/Sweet Berry Bush stuck-multiplier producer followed by the first ordinary `MoverType.SELF` consumer have the same behavior and writer/reader order in 1.21.10 and 1.21.11. This closes only these named direct-contact operations; it is not full-pair coverage or runtime-parity evidence.

## Exact source identity

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

Both ready markers identify their exact `versionId` and `mapping=mojmap`. The 1.21.10 source manifest SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; marker-pinned client SHA-256 is `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. The 1.21.11 source manifest SHA-256 is `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; marker-pinned client SHA-256 is `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`. Each marker's source-manifest, artifact-manifest and diagnostics hashes match the corresponding published files. Relevant file hashes below also match each source manifest.

| Source file (Mojmap, relative to version `mojmap/`) | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/HoneyBlock.java` | `7f8a0dca359d623f5db71e206679f20de8cd7c42f244d0f0230b876f5131f898` | `d6f11b868160cba12b45b1818265ab870fcb96bbb6f28fdc13952013bdf2ab32` |
| `net/minecraft/world/level/block/WebBlock.java` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/InsideBlockEffectApplier.java` | `ab7e6b651a2a97f8cdbb8b4b52084f9d44fd3d4ed3a1a202c41c2b0c4a7d2f11` | `37396f6c0972b542a94583aac19183ba03c948259c6f1510715076cae14036a6` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |

## Bounded source trace

- **Honey slide:** `HoneyBlock.java`, `entityInside` and `isSlidingDown` (lines 63–104 in both releases), then `doSlideMovement` (106–116). In both, the dry witness requires airborne state, the same height and downward-speed gates, and the same lateral-position gate. When sliding, `getOldDeltaY` determines whether horizontal velocity is scaled: only below `-0.13`, by `-0.05 / getOldDeltaY(currentY)`; otherwise X/Z are retained. Y is set to `getNewDeltaY(-0.05)` in both branches, then fall distance resets. `HoneyBlock.java` differs only in imports for moved minecart/boat packages; these bodies and constants are equal.
- **Block producers:** `WebBlock.entityInside` (27–34) uses `(0.25, 0.05F, 0.25)` for the no-Weaving baseline and calls `makeStuckInBlock`. `SweetBerryBushBlock.entityInside` (82–96) calls it for a living non-fox/non-bee entity; its server-side berry damage branch is outside this audit. Both source files have identical SHA-256 across the pair. `Entity.checkInsideBlocks` synchronously invokes each block-state `entityInside` callback while scanning (1.21.10 line 1199; 1.21.11 line 1218); these block bodies directly call entity methods rather than queueing their response in the applier. `Entity.makeStuckInBlock` stores the supplied vector in `stuckSpeedMultiplier` and resets fall distance (1.21.10 lines 2788–2791; 1.21.11 lines 2810–2813).
- **Producer timing:** `LocalPlayer.aiStep` delegates to `super.aiStep` (1.21.10 line 834; 1.21.11 line 875). In `LivingEntity.aiStep`, the ordinary movement path calls `travel` before `applyEffectsFromBlocks` (1.21.10 lines 2845–2853; 1.21.11 lines 2971–2979). In the ordinary dry travel path, the SELF move uses `move(MoverType.SELF, getDeltaMovement())` (1.21.10 line 2479; 1.21.11 line 2537). Thus direct Honey response and direct stuck-multiplier writes happen in the post-travel block-effects pass, after that tick's ordinary SELF move.
- **First consumer/order:** `Entity.move` reads the stored multiplier at entry, multiplies requested movement only for non-PISTON movement, then clears the multiplier and sets delta movement to zero before collision/movement (1.21.10 lines 670–695; 1.21.11 lines 685–710). The first ordinary SELF move on the following tick is therefore the consumer for Web/Berry's prior block-contact multiplier. Both releases have the same relevant branch and statement order. The same following travel reads Honey's post-pass delta movement; Honey's next velocity writer preserves the identical predicates, operations, and ordering.

## Limits

This audit excludes piston-only multiplier behavior, fall-damage calculation, collision geometry/support, fluids, portals, attributes, sprint, weaving-effect variants, and unrelated changes in the pair. It does not close dependencies or inventories outside these direct-contact paths, and makes no claim about runtime parity.