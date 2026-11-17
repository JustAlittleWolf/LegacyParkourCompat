# Local input modifiers source comparison

## Exact source identity

- Older: `26.1.2`, namespace/mapping `unobfuscated`; `decompiled_minecraft/26.1.2/unobfuscated.ready.json` declares version `26.1.2`, mapping `unobfuscated`.
- Newer: `26.2`, namespace/mapping `unobfuscated`; `decompiled_minecraft/26.2/unobfuscated.ready.json` declares version `26.2`, mapping `unobfuscated`.
- Both ready markers report Vineflower and Java 25. The corresponding provenance records also declare the same exact version and `unobfuscated` mapping option.
- 26.1.2 manifest digests verified: `unobfuscated.sources.sha256` `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; `artifacts.sha256` `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.
- 26.2 manifest digests verified: `unobfuscated.sources.sha256` `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; `artifacts.sha256` `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- The ready markers name the artifact manifest `artifacts.sha256`; the task's `unobfuscated.artifacts.sha256` path does not exist in either version directory. I verified the marker-declared `artifacts.sha256` files instead.
- Every cited Java file hash below matches its entry in that release's `unobfuscated.sources.sha256` manifest.

## Compared slice

| Source member and lines | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java`: `startUsingItem`/`isUsingItem` and item-use helpers, 551-569; `isMovingSlowly`, 687-689; `applyInput` and `modifyInput`, 692-723; sprint eligibility `canStartSprinting`, 1144-1148 | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/entity/player/Player.java`: player attribute builder, 207-220; movement-speed read into speed, 448-457; walking-speed base assignment, 638-641 | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java`: `MOVEMENT_SPEED`, 67-69 / 77-79; `SNEAKING_SPEED`, 79-81 / 92-94 | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |

## Bounded comparison

No difference found in this slice. In both endpoints, local item-use state is `startedUsingItem`; sprint inhibition is active use plus the used stack's `USE_EFFECTS.canSprint() == false`. The use slowdown is applied only when actively using an item and not riding, and its factor comes from `USE_EFFECTS.speedMultiplier()`. Slow movement is `isCrouching() || isVisuallyCrawling()`. For controlled-camera input, `applyInput` sends nonempty movement input through `modifyInput`; it scales by `0.98F`, then (when eligible) by item-use speed, then (when moving slowly) by a float cast of `getAttributeValue(SNEAKING_SPEED)`, before square-movement adjustment. `canStartSprinting` has matching item-use, passenger and slow-movement gates (underwater exception retained). Player attribute setup adds `MOVEMENT_SPEED` at `0.1F` and `SNEAKING_SPEED`; Player `aiStep` loads movement speed into the entity speed field after `super.aiStep()` returns, following the input/travel work in this client tick; saved abilities set the movement-speed base. Attribute declarations match: movement speed default `0.7`, range `0..1024`, syncable; sneaking speed default `0.3`, range `0..1`, syncable. The observed source changes to other UI/context members and line positions do not alter this bounded movement path.

## Coverage and dependency gate

- Examined: local active-item eligibility/use slowdown and sprint gate; crouch/visual-crawl slow-movement predicate; controlled-camera input scaling order; player movement/sneaking attribute declarations and direct player consumers. `LocalPlayer.modifyInput` reads `SNEAKING_SPEED` before travel; `Player.aiStep` reads and sets `MOVEMENT_SPEED` after `super.aiStep()` returns, following input/travel.
- Partial/open: effective item/equipment state, `USE_EFFECTS` component values/defaults and synchronization into `useItem`; attribute modifier sources/synchronization that determine the effective `SNEAKING_SPEED` value. These inputs were not established from exact resources here, so their dependency is open.
- Unexamined by assignment: keyboard vector construction, square-move math itself, ordinary travel calculations, jumping, `Entity.move`, block callbacks, and other movement slices.
- Verdict: bounded endpoint no-difference only. This is not onset evidence, full-pair freeze, implementation acceptance, or runtime parity.
