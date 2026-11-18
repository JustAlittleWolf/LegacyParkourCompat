# 1.19 → 1.19.1: direct `Entity.turn` pitch response

## Scope and result

Compared the direct `Entity.turn(double, double)` pitch response and its immediately relevant finite-value setter, ordinary player caller context, and next active Elytra travel read for 1.19 → 1.19.1 Mojmap sources. **No source difference was found in this bounded slice.** The `turn`, `setXRot`, `LivingEntity.travel`, `Player.travel`, and `MouseHandler.turnPlayer` method texts are byte-identical across the pair after UTF-8 encoding of the extracted method text (identical digest per method below). This is a no-difference coverage result, not an implementation finding or full-pair coverage claim.

## Exact source readiness and provenance

Both source sets carry `status: ready`, version IDs `1.19` / `1.19.1`, `mapping: mojmap`, 4,430 / 4,480 source files, respectively. The ready marker's `sourceManifestSha256` matches the SHA-256 of the corresponding `mojmap.sources.sha256` file. The per-file hashes below also match entries in those manifests.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Ready source manifest SHA-256 |
|---|---|---|---|---|---|
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |

The provenance identities report exact release client/mapping inputs and `Vineflower 1.12.0`, `TinyRemapper 0.14.1`, `mapping-io 0.9.1`, and Java runtime 25. Source-side mapping family is Mojmap on both releases.

## Cited source bytes

Repository-relative paths are under `decompiled_minecraft/<version>/mojmap/`. Hashes are SHA-256 of the complete cited Java file and correspond to the version's `mojmap.sources.sha256` entry.

| File | 1.19 SHA-256 | 1.19.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| `net/minecraft/client/MouseHandler.java` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` |

For extracted method text, both versions have the same SHA-256: `Entity.turn(double,double)` `8af822d2314736acd823056ec0c613d2478d611e601dedba050e8923e1f334aa`; `Entity.setXRot(float)` `e5a15c3fb912300f215b58a156a0a8cdd38f76a6bc6ecf557a3ef8c8132a1eb6`; `LivingEntity.travel(Vec3)` `5db0a579e554fb58850145fb0e9ef1c110f836a87513ac96af81c5f36210302f`; `Player.travel(Vec3)` `c4a4297037206728929ecf50c503ddef12fbfa9d314f222cfc2d978c2fae08d1`; and `MouseHandler.turnPlayer()` `69d88a24ab945f0341c532863145266afe5e35f6514e79f75eaff2c900469482`.

## Operation and movement relevance

- `Entity.java`, `Entity.turn(double,double)`, lines 389–400 on both versions: pitch delta is narrowed first, `float pitchDelta = (float) dy * 0.15F`; yaw is independently narrowed/scaled. Pitch is applied via `setXRot(getXRot() + pitchDelta)`, yaw via `setYRot(...)`, then pitch is clamped by a second `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`. Previous pitch/yaw are incremented afterward, previous pitch is clamped, and passenger notification follows. Statements and order match exactly.
- `Entity.java`, `Entity.setXRot(float)`, lines 3059–3065 (1.19; same method text in 1.19.1): non-finite inputs call `Util.logAndPauseIfInIde(...)` and are discarded; finite inputs assign `this.xRot`.
- Source-derived finite-overflow edge: a finite double pitch input such as `Double.MAX_VALUE` narrows to positive float infinity, and multiplication by `0.15F` remains infinity. The first setter rejects that value, leaving a normal prior pitch untouched; the final pitch clamp consequently sees that retained pitch. In contrast, `xRotO += pitchDelta` becomes positive infinity and the following historical clamp sets `xRotO` to 90 degrees. This is an arithmetic/source deduction, not an executed test. It is identical in both versions. The resulting live pitch used by movement remains the retained pitch.
- Ordinary caller context: `MouseHandler.turnPlayer()`, lines 208–245 in both versions, computes horizontal/vertical deltas and calls `minecraft.player.turn($$7, $$8 * $$13)` when the player exists. The whole `MouseHandler.java` file hash is identical in the pair. This is cited only to establish the ordinary player caller; device scheduling and rendering are outside this comparison.
- The normal `Player.travel(Vec3)` branch delegates to `super.travel($$0)` (`Player.java`, 1.19 lines 1458–1489; 1.19.1 lines 1463–1494). When `LivingEntity.travel` reaches its `isFallFlying()` branch, lines 2107–2120, it reads `getXRot()` into a float radians value and uses `Math.cos(pitch)` in the Elytra lift coefficient; the negative-pitch branch later uses `Mth.sin(pitch)` at line 2126. Thus the direct pitch response is consumed by active player Elytra movement. The branch and arithmetic are identical in both versions.

## Bounded conclusion

The assigned direct pitch response, finite setter boundary, ordinary player caller and next active Elytra travel read have no 1.19 → 1.19.1 source delta in the verified Mojmap source sets. The finite-large-input behavior described above is a shared behavior, not a release difference. No broader tick, input-device, rendering, network, teleport, or full-pair inventory claim is made.
