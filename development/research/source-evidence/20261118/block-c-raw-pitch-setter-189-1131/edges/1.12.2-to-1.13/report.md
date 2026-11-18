# C source report: local camera pitch turn (1.12.2 → 1.13.0)

## Exact release pair and bounded result

This report's literal comparison is Minecraft 1.12.2 to 1.13.0 (`versionId` `1.13` in the exact Feather source inventory). The earlier 1.12.2 → 1.13.2 endpoint report remains frozen and is not used as the newer-source identity here. This result is limited to the local camera turn, its ordinary mouse input path, and the first stored-pitch use in an active dry fall-flying player travel request.

1.12.2 `Entity.updateLocalPlayerCamera(float yaw, float pitch)` first saves current pitch in a float, then updates yaw and computes current pitch as `(float)(this.pitch - pitch * 0.15)`. Since `0.15` is double, that expression promotes the float argument to double before casting the result back to float. It clamps current pitch to `[-90, 90]`, then updates previous pitch by the *actual clamped current change*: `lastPitch += this.pitch - oldPitch`. This direct operation does not clamp previous pitch.

1.13.0 `Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` first computes double `d = cursorDeltaY * 0.15` and `e = cursorDeltaX * 0.15`; it casts `this.pitch + d` and `this.yaw + e` to float, clamps current pitch, then casts `this.lastPitch + d` to float and clamps previous pitch separately. Thus the literal setter changes float arguments to double arguments, subtract-current to add-current, and previous-pitch advancement from actual clamped current change to raw requested delta followed by its own clamp. Each version clamps current after its current-pitch expression; only 1.13.0 clamps previous pitch in this setter.

The ordinary input caller also changes width and scale arithmetic. In 1.12.2, `Mouse.tick` stores LWJGL `getDY()` in an `int`; `GameRenderer` computes float `f = mouseSensitivity * 0.6F + 0.2F`, float `g = f * f * f * 8.0F`, and float `i = mouse.y * g`, applies the invert-mouse integer sign, and passes a float pitch delta. In 1.13.0, `MouseHandler` accumulates cursor-coordinate differences in doubles; `turnPlayer` computes float `f = mouseSensitivity * 0.6F + 0.2F` and double `g = f * f * f * 8.0`, uses either double smooth-turn output or raw `accumulatedDY * g`, applies the invert-mouse integer sign, and passes a double. I do not infer native device-axis equivalence from the mapped source.

A finite overrange is reachable through the normal mouse input path. At sensitivity 0.5, both paths give `f = 0.5`, `g = 1.0`; a signed vertical delta of 601 yields 90.15 degrees before the setter's sign and clamp. The source paths pass signed integer/double mouse deltas without a pitch-range cap before the setter, and the invert-mouse option can reverse the sign. The setter therefore reaches either current-pitch endpoint using finite ordinary input. For an equal starting current/previous pitch, both will also end at that endpoint after the per-version previous-pitch work, though the intermediate update and clamp order differ.

## First stored-pitch use in active dry fall-flying travel

`LivingEntity.travel` calls `moveRelative` in both exact releases (1.12.2 line 1847; 1.13.0 line 1907). In `moveRelative`, execution must be locally controlled or use the logical side for updating movement, must pass the water/lava gates (unless player ability flight bypasses them), and must have `isFallFlying()` true. At the first stored-angle use in that branch, `getLookVector()` reads `Entity.this.pitch` and yaw (1.12.2 and 1.13.0 `Entity.java` line 1691). Immediately after, `LivingEntity.moveRelative` directly reads current pitch to compute radians (1.12.2 line 1435; 1.13.0 line 1475); that angle feeds cosine and the negative-pitch lift condition. This ties the setter's stored pitch to an actual active dry Elytra travel request. This is not a complete Elytra mechanics comparison or full pair coverage.

## Exact source, mapping, and manifest pins

All source paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`. The source SHA-256 values are the individual entries in the corresponding `ornithe-feather.sources.sha256` manifest; I recomputed each cited Java file and matched it to that entry.

| Exact version source | Member / range | Source SHA-256 |
|---|---|---|
| `1.12.2/net/minecraft/entity/Entity.java` | `updateLocalPlayerCamera`, 307–317; `getLookVector`, 1690–1692 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `1.13/net/minecraft/entity/Entity.java` | `updateLocalPlayerCamera`, 318–330; `getLookVector`, 1690–1692 | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `1.12.2/net/minecraft/client/Mouse.java` | `tick`, 20–23 | `b6c9132938c64f2b79138e87de60cee4b965e6c28d485798d7131ec2bf0fd058` |
| `1.12.2/net/minecraft/client/render/GameRenderer.java` | local mouse scaling / call, 844–868 | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
| `1.13/net/minecraft/client/MouseHandler.java` | cursor accumulation and `turnPlayer`, 168–217 | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| `1.12.2/net/minecraft/entity/living/LivingEntity.java` | `travel` request, 1842–1848; `moveRelative`, 1425–1464 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `1.13/net/minecraft/entity/living/LivingEntity.java` | `travel` request, 1901–1908; `moveRelative`, 1459–1504 | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |

The ready markers identify exact releases and `ornithe-feather` mappings. The 1.12.2 mapping input is Feather Gen2 build.2, merged jar SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and Tiny SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The 1.13.0 mapping input is `net.ornithemc:feather-gen2:1.13+build.2`, merged jar SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` and Tiny SHA-256 `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84`.

| Version | Raw client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|
| 1.12.2 | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3` |
| 1.13.0 (`versionId` `1.13`) | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | `efb942576b5b4a3bcbf1e5ba1a53feb19e21c79d1b57e4ed429d52db2fadd4d3` |

The present source manifests, artifact manifests and diagnostics files match the corresponding ready-marker hashes. This report cites only the literal 1.12.2 and 1.13.0 source identities; the earlier 1.13.2 endpoint artifact has not been edited.