# 1.16.1 to 1.16.2 input assembly source review

Status: COMPLETED — bounded input-to-travel horizontal slice; no independent difference found.

## Exact evidence identity and integrity

Both sides are exact decompiled `mojmap` sources. Version-root readiness-marker SHA-256 values match the assigned pins:

- 1.16.1 `decompiled_minecraft/1.16.1/mojmap.ready.json`: `C19D707F7D6622733B5189599FF77AA7999CC11A3D900F29D150F9FAB203FFCB`.
- 1.16.2 `decompiled_minecraft/1.16.2/mojmap.ready.json`: `E5F69BAB646B6143B0660A97FADE59595FF2E924D36567B9CD285C8BABD31214`.

Each marker declares its exact `versionId`, `mapping: mojmap`, Vineflower, Java 25, source count/bytes and source-manifest digest. For each side, actual SHA-256 of `mojmap.sources.sha256` equals the marker's `sourceManifestSha256`; actual `artifacts.sha256` equals `artifactManifestSha256`. Provenance declares the same version, `mojmap` namespace and batch `1.16.1,1.16.2`, exact-run decompiler/tool inputs. Provenance file SHA-256: 1.16.1 `A6FF065188A400EC891980CDF2E6C0C238BF5E05168A2F69B29C6CECC2214B98`; 1.16.2 `A9B166406064E877751542FE7DF27ACC32BE3843F6FDC2280DAC32CE0B54207C`.

For every used Java source below, the actual source-file SHA-256 was checked against that exact version's `mojmap.sources.sha256` entry; all matched. `Input.java` and `KeyboardInput.java` have identical source bytes in both versions.

| Source path (both versions) | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` | same |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |

## Ordered bounded trace and gates

Equivalent source statements were inspected in both versions. Line numbers are given as `1.16.1 / 1.16.2` where they differ.

1. `KeyboardInput.tick(boolean bl)` reads up, down, left and right key states (`KeyboardInput.java:13–20` both). Equal opposing states produce `0.0F`; otherwise forward is up `1.0F` / down `-1.0F`, and left is left `1.0F` / right `-1.0F`. It then reads jump and shift. If `bl` is true, each axis is multiplied by the double literal `0.3` and cast back to float, in left-then-forward order (`:21–24`). There is no diagonal normalization here.
2. On the local-player path, `LocalPlayer.aiStep` first computes `crouching` from flying/swimming/pose eligibility and `isShiftKeyDown()` (the previous input shift state), before refreshing keyboard input. It calls `input.tick(isMovingSlowly())`, where `isMovingSlowly()` is `isCrouching() || isVisuallyCrawling()` (`LocalPlayer.java:629–631, 662–666 / 604–606, 637–641`). Thus established crouching or visually crawling enables the keyboard 0.3 factor; a newly pressed shift is read later in this tick and affects the computed crouching state on the next tick. Shift only slows axes through this `bl` gate, not by a separate factor in this path.
3. Immediately after input tick and tutorial notification, item-use slowdown applies when `isUsingItem() && !isPassenger()`: `leftImpulse` then `forwardImpulse` are each multiplied by `0.2F`; it also resets sprint trigger time (`LocalPlayer.java:667–672 / 642–647`). The horizontal scaling is included as a reached ordinary slowdown gate; sprint timer behavior is outside this assignment. No other axis rewrite in the bounded local input segment was found.
4. During `LivingEntity.aiStep`, immobility zeros `jumping`, `xxa`, and `zza`; otherwise effective AI calls virtual `serverAiStep()` (`LivingEntity.java:2415–2421 / 2417–2423`). For the controlled local camera, `LocalPlayer.serverAiStep` copies `input.leftImpulse` to `xxa` and `input.forwardImpulse` to `zza` without additional scaling (`LocalPlayer.java:634–643 / 609–618`). This occurs when `LocalPlayer.aiStep` reaches `super.aiStep()` (`LocalPlayer.java:813` both; 1.16.2 line 788), which leads into the LivingEntity path. Ordinary locally controlled, mobile player is the applicable gate; remote camera / immobile states are outside it.
5. After the AI step, `LivingEntity` multiplies `xxa` then `zza` by `0.98F` (`LivingEntity.java:2453–2454 / 2455–2456`), captures the bounding box and calls `travel(new Vec3(this.xxa, this.yya, this.zza))` (`:2457 / :2459`). The Java float fields are widened as constructor arguments to the vector's double components; horizontal components are the damped values. No intervening horizontal field write appears in this interval. Travel-body arithmetic is not reviewed here.

For the requested ordinary dry, unmounted, nonsprinting state, this gives the same ordered keyboard-axis construction, crouch/crawl gate, item-use gate, local-player copy, `0.98F` field damping, and horizontal vector components in 1.16.1 and 1.16.2. Normal walking produces signed unit/zero axes; the applicable slow-input gate scales these axes before local-player consumption.

## Difference disposition and remaining gate

The full `LocalPlayer.java` and `LivingEntity.java` files differ by source hash, but the assigned input, copy, damping and travel-construction statements above are materially identical. Other edits visible in surrounding methods (including collision-space handling) are outside this slice and were not treated as findings. There is no canonical finding draft because no difference was found in the assigned operation. This completes only this worker's bounded slice; pair coverage, dependency closure and independent full-pair audit remain with the coordinator.
