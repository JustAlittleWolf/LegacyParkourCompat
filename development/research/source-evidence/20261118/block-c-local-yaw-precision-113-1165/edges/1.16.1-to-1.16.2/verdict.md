# Independent source audit: local yaw, 1.16.1 → 1.16.2

## Verdict

**No yaw-only source difference found in the assigned slice.** The ordinary `Entity.turn(double,double)` yaw arithmetic and its first dry ground/air movement contribution are identical in the two exact Mojmap source sets. This closes only that bounded audit slice; it is not a full-pair coverage freeze or a runtime-parity claim.

## Frozen source identity

The exact decompiler run in both provenance records is the pinned Mojmap batch for `1.16.1,1.16.2` (Vineflower 1.12.0, Java 25). Each ready marker's `sourceManifestSha256` and `artifactManifestSha256` equals the SHA-256 of the corresponding local manifest. Every cited source file's SHA-256 below also matches its entry in that source manifest.

| Release | source manifest SHA-256 (whole source-set identity) | artifact manifest SHA-256 | client SHA-256 |
|---|---|---|---|
| 1.16.1 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` |
| 1.16.2 | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` |

Source pins (SHA-256 of the exact files, relative to each release's `mojmap/` directory):

| File | 1.16.1 | 1.16.2 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| `net/minecraft/client/Options.java` | `efcaf563621e2c6ad2e547d5273492e7dbeb13a6e0b9c8639aa0e056a84435bb` | `8682739229287d53940441fcaa6a6d034cb52a60a85ee6246a987e27c9329fba` |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` |

## Compared operation and downstream use

In `Entity.java`, 1.16.1 `setRot` is lines 316–319 and `turn` is 332–344; 1.16.2 has `setRot` at 326–329 and `turn` at 340–352. Both `turn` bodies perform, in order:

1. Compute `f = e * 0.15` and `g = d * 0.15` as `double` values (`0.15` is a double literal).
2. Add `f`/`g` to each current float rotation and narrow each double sum back to `float`; current pitch and yaw are written first.
3. Clamp current pitch only; add the same deltas independently to previous pitch and yaw, narrow those sums to `float`, and clamp previous pitch only.
4. Notify a vehicle if present.

Yaw has no clamp, finite check, or wrap in `turn`; the separate `setRot` method applies `% 360.0F` to yaw and pitch in both versions and is not called by `turn`. The current/previous order, widths, constants and casts match exactly. The mouse route is the same pinned `MouseHandler.java` in both releases: `turnPlayer` gates on grabbed mouse and active window, computes horizontal delta `j`, and invokes `player.turn(j, k * n)` when the player is non-null (lines 212–247). No yaw finite or wrap gate appears on that route.

The first ordinary local dry movement use is also unchanged. `LocalPlayer.isEffectiveAi()` returns `true` (1.16.1 lines 519–521; 1.16.2 lines 494–496), so the `LivingEntity.travel` controlled/AI gate admits the local player. Local input is copied into `xxa`/`zza` in `LocalPlayer.serverAiStep` (1.16.1 lines 633–644; 1.16.2 lines 608–619); `LivingEntity.aiStep` scales them and calls `travel(new Vec3(xxa, yya, zza))` (1.16.1 lines 2452–2458; 1.16.2 lines 2454–2460). On foot, `Player.travel` takes its ordinary `super.travel` path when not swimming or abilities-flying (1.16.1 lines 1380–1410; 1.16.2 lines 1387–1415). In the dry, non-fall-flying branch, `LivingEntity.travel` calls `handleRelativeFrictionAndCalculateMovement`; that calls `moveRelative`, whose `Entity.getInputVector` reads **current** `yRot` and computes `(x*cos - z*sin, y, z*cos + x*sin)` (1.16.1 `Entity.java` lines 1073–1088; 1.16.2 lines 1075–1090). Ground/air only changes the speed/friction scalar (`LivingEntity.java`: 1.16.1 lines 2020–2040, 2060–2063, 2104–2109; 1.16.2 lines 2022–2042, 2062–2065, 2106–2111); the yaw rotation expression is the same.

## Lawful finite witness

For the local mouse path, take the default finite sensitivity `0.5` (the `Options.sensitivity` field is `double`), mouse grabbed, active window, smooth camera off, and finite accumulated horizontal mouse delta `1.0`. The source computes `f = 0.5 * 0.6F + 0.2F` and `gMouse = f*f*f*8.0`; the `float` constants are promoted to `double`, so the result is positive and finite (it need not equal exactly `0.5`/`1.0`). It therefore calls `turn(gMouse, 0.0)`. With finite initial `yRot = yRotO = 0.0F`, both versions compute the same double yaw delta `gMouse*0.15` and narrow each updated yaw independently to the same finite `float`, without wrap.

For the next ordinary local movement, a legal `KeyboardInput` “up” state gives finite `(leftImpulse, forwardImpulse) = (0.0F, 1.0F)`; with no slow-move or item-use modifier, the local impulses flow to travel as `(xxa, zza) = (0.0F, 0.98F)` after the common `0.98F` tick scale. The finite nonzero vector reaches the dry branch on either ground or air. `moveRelative` scales it by the corresponding finite player movement speed and rotates it with the same finite updated yaw, `(float)(gMouse * 0.15)`, in both releases. This witness establishes reachability and a nonzero yaw contribution relative to zero yaw; it does not establish a cross-release behavioral difference.

## Phase-two independent comparison

The frozen source report was read only after its SHA-256 matched the authorized identity `66c861ae87fa11368e0518bda1b2d44dfa4be5d9e76426cbaf3e94961582aa90`. Its exact-source identities and cited movement-source hashes agree with this audit. I independently extracted and compared the paired bodies from the frozen Mojmap files: `Entity.turn`, `Entity.moveRelative`, `Entity.getInputVector`, `LivingEntity.travel`, `LivingEntity.handleRelativeFrictionAndCalculateMovement`, and `Player.travel` are each text-identical between 1.16.1 and 1.16.2. The report's `LocalPlayer.aiStep`→`super.aiStep` line references also resolve. No discrepancy found.

The report's statement that there is no cross-release numeric divergence witness is consistent with this verdict. The lawful finite mouse/keyboard witness above demonstrates a reachable yaw contribution that is the same in both releases; it does not claim a numeric divergence between them. The frozen source report remained read-only; no tests, builds, gameplay, or product/research edits were performed.