# 1.21.11 -> 26.1.2 post-move block-contact callbacks — NO_DIFF

## Identity and scope

- 1.21.11: canonical `decompiled_minecraft/1.21.11/mojmap`; ready manifest `mojmap.ready.json`, ready status, source manifest digest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, artifact manifest digest `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`, client artifact SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- 26.1.2: canonical `decompiled_minecraft/26.1.2/unobfuscated`; ready manifest `unobfuscated.ready.json`, ready status, source manifest digest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, artifact manifest digest `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`, client artifact SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`.
- Both source trees had their ready identities checked against the endpoint manifests/provenance before reading movement behavior.
- Slice: LocalPlayer/LivingEntity SELF movement followed by block-contact `entityInside`/inside-shape callbacks and direct movement-state consequences. This report does not claim pair freeze or general version coverage.

## Verdict

**NO_DIFF in the assigned slice.** The local-player tick still reaches `LivingEntity.aiStep` movement, then calls `applyEffectsFromBlocks()` under the same `!clientSide || isLocalInstanceAuthoritative()` gate. The ordinary collector still scans recorded per-tick movement segments, tests entity-inside collision shapes/fluid intersections, deduplicates visited positions, invokes callbacks with the same precise flag, and flushes effects after scanning. The direct movement-affecting callbacks inspected retain their effective movement consequences and gates: bubble columns, webs, powder snow, sweet berry bushes, and honey blocks. No eligible old/new delta was identified in this slice.

The 26.1.2-only `applyEffectsFromBlocksForLastMovements()` at `Entity.java:852` is not part of the local-player tick path; its only call site is `ItemEntity.java:148`, outside this assignment. Newer block classes without an older counterpart do not establish a historical callback delta. Collision-kernel parity is expressly not re-audited here.

## Dispatch and order evidence

- Old `LivingEntity.java:2977-2978` calls `applyEffectsFromBlocks()` only when `!level.isClientSide() || isLocalInstanceAuthoritative()`. New `LivingEntity.java:3076-3078` has the same condition and call. `LocalPlayer.aiStep` calls `super.aiStep()` after its local input setup (old `LocalPlayer.java:728-875`; new `LocalPlayer.java:767-914`), thereby reaching this `LivingEntity.aiStep` path.
- Old `Entity.java:816-826, 858-870` and new `Entity.java:839-849, 885-897` both transfer recorded movement entries into final movements, append a final position segment only when needed, step the on-ground block hook, check inside-block callbacks, apply-and-clear the step collector, then continue rain/fire bookkeeping. The 26.1.2 convenience method at `Entity.java:852-854` is extra, but not used by `LivingEntity.aiStep`.
- Old scan: `Entity.java:1156-1233`; new scan: `Entity.java:1195-1283`. Both walk movement segments in axis-dependent order where recorded movement data supplies it, use the target-position deflated player AABB, test the block inside collision shape (and fluid separately), require collision with the moving entity shape, deduplicate positions for the pass, advance collector step before each block/fluid callback, and invoke `onInsideBlock` after block `entityInside`. Callback exceptions remain crash-wrapped. No dispatch/order/gate change was found.
- Old collector `InsideBlockEffectApplier.java:45-77`; new same path/lines. Both flush when step changes and on `applyAndClear`, with effect order determined by the enum and before/apply/after staging. This preserves per-contact callback ordering in the inspected pipeline.

## Direct movement-state callback set

| Callback | Old source location | New source location | Movement consequence checked |
|---|---|---|---|
| Bubble column | `BubbleColumnBlock.java:51-59`; `Entity.java:2718-2762, 2748-2763` | `BubbleColumnBlock.java:53-70`; `Entity.java:2782-2829` | Same `isPrecise` gate / above-vs-inside choice; sets vertical delta and (inside case) resets fall distance with the same formula/caps. |
| Web | `WebBlock.java:27-34`; `Entity.java:2810-2813` | `WebBlock.java:27-41`; `Entity.java:2876-2879` | Same stuck-speed multiplier and fall-distance reset. |
| Powder snow | `PowderSnowBlock.java:62-65`; `Entity.java:2810-2813` | `PowderSnowBlock.java:62-72`; `Entity.java:2876-2879` | Same LivingEntity / in-block-state guard, multiplier and reset. Freeze/extinguish effects excluded. |
| Sweet berry bush | `SweetBerryBushBlock.java:82-85`; `Entity.java:2810-2813` | `SweetBerryBushBlock.java:82-92`; `Entity.java:2876-2879` | Same direct slowdown via `makeStuckInBlock` and fall-distance reset. Damage excluded. |
| Honey | `HoneyBlock.java:63-70, 106-116` | `HoneyBlock.java:64-80, 114-124` | Same slide predicate and vertical/horizontal delta calculation; same fall-distance reset. Slide effects excluded. |

The scan of block classes implementing `entityInside` for `setDeltaMovement`, `makeStuckInBlock`, `setPos`, or `resetFallDistance` returned only the four slowdown classes above (Honey, PowderSnow, SweetBerryBush, Web); bubble-column direct motion is through `Entity` methods, listed separately. No position-writing block callback appeared in that scan.

- 1.21.11 `net/minecraft/client/player/LocalPlayer.java:728-875`: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; 26.1.2 same path `:767-914`: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.\n\n## Source hashes

SHA-256 (exact canonical files read):

- 1.21.11 `net/minecraft/world/entity/Entity.java`: `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`
- 26.1.2 `net/minecraft/world/entity/Entity.java`: `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- 26.1.2 `net/minecraft/world/entity/LivingEntity.java`: `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
- 1.21.11 `net/minecraft/world/entity/InsideBlockEffectApplier.java`: `37396f6c0972b542a94583aac19183ba03c948259c6f1510715076cae14036a6`
- 26.1.2 `net/minecraft/world/entity/InsideBlockEffectApplier.java`: `3ed01ec7fec19d9c06c8d40eacc81bbb2e6e0276e5f8cf11d26b4a5706c8e132`
- 1.21.11 / 26.1.2 `net/minecraft/world/level/block/BubbleColumnBlock.java`: `5c48f8d09298ccfb26a2ad4d877816c6b264313f41ae07116e08795b4afba6e8` / `ece83c008e6f8391b468dafe445c288630215fdff490defee50cb7766771e039`
- 1.21.11 / 26.1.2 `net/minecraft/world/level/block/WebBlock.java`: `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` / `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5`
- 1.21.11 / 26.1.2 `net/minecraft/world/level/block/PowderSnowBlock.java`: `84e3f4c9063c6be560b400686afa2bcd6761585c9c56566d023ad8fe47048940` / `5733056253c3f9c7d843e8e7c29deaee67d235d830c63c0641f3a7b70604970f`
- 1.21.11 / 26.1.2 `net/minecraft/world/level/block/SweetBerryBushBlock.java`: `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` / `ca930f1c40d72a03e55e9972a58b4cc3df645ea918625125b4255343bf893a1d`
- 1.21.11 / 26.1.2 `net/minecraft/world/level/block/HoneyBlock.java`: `d6f11b868160cba12b45b1818265ab870fcb96bbb6f28fdc13952013bdf2ab32` / `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec`

## Explicit exclusions and limits

No conclusion is made about health/damage/status/portal behavior, packet behavior, entity pushes, other non-player entity call paths, collision-kernel historical differences, newer-only blocks, or full-pair source coverage/freeze. No implementation/build/test/runtime/product files or backlog/wiki/history were inspected or changed.

