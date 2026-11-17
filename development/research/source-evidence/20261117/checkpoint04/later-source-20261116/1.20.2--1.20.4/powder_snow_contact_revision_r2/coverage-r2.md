# Powder snow contact / stuck multiplier — 1.20.2 → 1.20.4 (r2)

Status: bounded source comparison complete; no difference found in this operation. This r2 adds the local-player tick gate/super-call chain requested by the audit; unchanged source identities and callback/multiplier findings reuse the prior verified r1 packet. It supplements the omitted post-move callback dependency only. It is not pair-wide coverage or a freeze.

## Identity and provenance

| Release | Canonical source root | Namespace / ID | Ready marker SHA-256 | Provenance sidecar SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|---|---|
| 1.20.2 | `decompiled_minecraft/1.20.2/mojmap` | Mojmap / `1.20.2` | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` |
| 1.20.4 | `decompiled_minecraft/1.20.4/mojmap` | Mojmap / `1.20.4` | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` |

Both ready markers identify the stated version and mapping namespace, report ready status, and bind the listed source and artifact manifests. Provenance sidecars independently identify the same version and namespace. Cited source-file SHA-256 values below were checked against the corresponding source-manifest entries. No full-tree rehash is claimed.

## Callback and next-move operation

- `PowderSnowBlock.entityInside` has the same relevant body at 1.20.2 lines 57–59 and 1.20.4 lines 64–66. It checks `!($$3 instanceof LivingEntity) || $$3.getFeetBlockState().is(this)` and then calls `makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9))`. Thus for a Player (a LivingEntity), this callback stores the multiplier only when its feet block state is this Powder Snow block. The remaining client particle work is outside this comparison.
- `Player.makeStuckInBlock` is identical at 1.20.2 lines 1640–1643 and 1.20.4 lines 1566–1569: it delegates to `super` only when `!abilities.flying`. `Entity.makeStuckInBlock` is identical at 1.20.2 lines 2353–2356 and 1.20.4 lines 2354–2357: it resets fall distance and stores the supplied vector in `stuckSpeedMultiplier`.
- `Entity.move` consumes the stored multiplier near its start: when `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`, it componentwise-multiplies the requested displacement, clears the stored vector to `Vec3.ZERO`, and zeros delta movement. Anchors: 1.20.2 lines 608–612; 1.20.4 lines 609–613. This is a one-next-move effect: the powder callback is reached after movement, so a value stored by that callback is applied on the subsequent `Entity.move` call, then cleared before collision resolution.
- In the ordinary non-noPhysics branch, `Entity.move` calls `tryCheckInsideBlocks()` after its collision/position and movement-contact processing, before applying the block speed factor to horizontal delta movement (1.20.2 lines 702–704; 1.20.4 lines 703–705). The same `tryCheckInsideBlocks` → `checkInsideBlocks` sequence scans the inset bounding box if chunks are loaded, obtains each intersecting block state, and dispatches `state.entityInside(...)` (1.20.2 lines 755–760, 967–986; 1.20.4 lines 756–761, 968–987). `BlockStateBase.entityInside` delegates to the block's `entityInside` override (1.20.2 lines 693–695; 1.20.4 lines 740–742).

## Local client Player reachability

`LocalPlayer.tick` reaches its superclass path only when `level().hasChunkAt(getBlockX(), getBlockZ())` (both versions lines 188–190). `AbstractClientPlayer.tick` updates `deltaMovementOnPreviousTick` then calls `super.tick()` (both lines 54–57); `Player.tick` sets `noPhysics = isSpectator()` and later calls `super.tick()` (1.20.2 lines 229–256; 1.20.4 lines 224–251). That superclass chain reaches `LivingEntity.tick`, which calls virtual `aiStep()` for a non-removed entity (1.20.2 line 2312; 1.20.4 line 2309). The ordinary movement route described below therefore requires this chunk-loaded LocalPlayer tick gate, a non-removed player, and non-spectator movement (`noPhysics` is false). `LocalPlayer.aiStep` calls `super.aiStep()` (both line 800), and `Player.aiStep` calls `super.aiStep()` (1.20.2 line 522; 1.20.4 line 523). In `LivingEntity.aiStep`, the non-ridden branch calls `travel(input)` (1.20.2 line 2601; 1.20.4 line 2603). The Player travel override delegates to `super.travel(...)` on its ordinary non-ridden branches (1.20.2 lines 1450–1477; 1.20.4 lines 1451–1473). `LivingEntity.travel` enters its controlled movement path at 1.20.2 lines 2038–2039 / 1.20.4 lines 2035–2036 and issues `this.move(MoverType.SELF, ...)` at 1.20.2 lines 2070, 2085, 2128 / 1.20.4 lines 2067, 2082, 2125. The local-player `isLocalPlayer()` and `isEffectiveAi()` implementations return true in both endpoints, and `LocalPlayer.move` delegates to `super.move(...)` (1.20.2 lines 871–876; 1.20.4 lines 872–877). Therefore this callback/multiplier route is reachable by local client Player movement; no server-side or runtime claim is made.

## Comparison verdict and limits

The bounded operation is unchanged across these endpoints: Powder Snow's eligibility and multiplier, Player's flying veto, Entity's storage/reset behavior, next-move multiplier application and clear order, and the block-contact dispatch after movement are equal. No source delta is reported for this dependency. This establishes neither the broader pair's complete coverage nor Powder Snow state/resource availability in a particular world. It does not address other post-move callbacks, falling/damage effects, shape or tag membership, arbitrary datapacks, trajectories, runtime parity, or product applicability.

## Cited Java source identity

SHA-256, by repository-relative path:

| Source | 1.20.2 | 1.20.4 |
|---|---|---|
| `net/minecraft/world/level/block/PowderSnowBlock.java` | `f94f20b85520194757470722de071fb781107acdadee16598cf401d18248c09f` | `302e784a106a37b00aafa0ff7ac5d638c78ccb61f34c2efac4d952f6a284a55c` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |


