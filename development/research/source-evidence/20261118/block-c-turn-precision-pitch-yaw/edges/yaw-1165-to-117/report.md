# Yaw delta precision into movement trig: 1.16.5 → 1.17

## Scope

Blind, exact-source comparison of the independently resolved `Entity.turn` yaw write and its ordinary player movement consumer, `Entity.moveRelative`, for 1.16.5→1.17. Pitch behavior, input batching, flight, and post-request collision are outside this report.

## Exact source identity

Both sides are Mojmap decompilations under `decompiled_minecraft/<version>/mojmap/`.

- **1.16.5:** Ready marker identifies release `1.16.5`, mapping `mojmap`, client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The pinned `client_mappings.txt` SHA-256 is `7931ed6d723eceb1d621d5a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`; the Mojmap archive SHA-256 is `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`. `Entity.java` SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`, `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`, and `Mth.java` `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` match `mojmap.sources.sha256`.
- **1.17:** The source tree is `decompiled_minecraft/1.17/mojmap/`. Its `.decompile-provenance.json` SHA-256 is `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; the embedded source hashes match `Entity.java` `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef`, `LivingEntity.java` `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`, and `Mth.java` `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113`. The embedded exact input hashes are client JAR `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, official client mappings `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`, and the `1.17.json` version metadata `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`. This older tree has no separate ready marker; exact release/mapping/input/source provenance comes from the embedded provenance and the path.

## Source comparison

- **1.16.5 Mojmap:** `net/minecraft/world/entity/Entity.java`, `turn(double d, double e)`, lines 340–351. `double g = d * 0.15;` computes yaw delta in double. Current yaw is assigned directly as `(float)(this.yRot + g)` at line 344, then previous yaw as `(float)(this.yRotO + g)` at line 347. There is no finite-value check in this method or on either direct field write. The method writes current yaw before previous yaw.
- **1.17 Mojmap:** `net/minecraft/world/entity/Entity.java`, `turn(double d, double e)`, lines 377–388. `float g = (float)d * 0.15F;` first rounds the argument to float, then multiplies in float. Current yaw is `this.setYRot(this.getYRot() + g)` at line 381 (float addition), followed by `this.yRotO += g` at line 384 (float addition). The new setter, lines 3005–3014, rejects non-finite values with `Util.logAndPauseIfInIde` and leaves current yaw unchanged; it does not reject or sanitize the later direct previous-yaw addition. Current yaw is attempted before previous yaw is updated. Thus ordinary finite yaw deltas can round differently, and invalid deltas now leave current yaw alone while still reaching the unchecked previous-yaw field.

This delta is specifically the yaw precision/write path. In both versions `moveRelative(float, Vec3)` takes current stored yaw, computes the transformed input vector, and adds it to delta movement (`Entity.java` 1075–1077 in 1.16.5; 1174–1176 in 1.17). In both, `getInputVector` normalizes only when input length squared exceeds 1, scales by the passed speed, evaluates float-angle `Mth.sin` and `Mth.cos`, and rotates horizontal components (`Entity.java` 1080–1089; 1179–1188). Both `Mth` versions use the same 65,536-entry sine table and quantize with `(int)(angle * 10430.378F) & 65535` (`Mth.java` 26 and 44 respectively).

For ordinary non-fluid ground/air movement, `LivingEntity.travel` in each version enters the non-fluid branch, selects friction and the ground/air multiplier (`onGround ? friction * 0.91F : 0.91F`), then calls `handleRelativeFrictionAndCalculateMovement` (1.16.5 lines 2022–2027; 1.17 lines 2107–2112). That helper calls `moveRelative(getFrictionInfluencedSpeed(f), input)` directly (1.16.5 line 2063; 1.17 line 2152). Its speed selector uses ground speed when grounded and `flyingSpeed` in air (1.16.5 lines 2106–2108; 1.17 lines 2196–2198). Consequently current yaw feeds the same sine-table rotation into ordinary ground and air horizontal velocity in both releases. The broader travel caller has unrelated differences and is cited only to establish reachability of this direct consumer.

## Finite numeric witness

Take finite `d = 6.774901758530177`, initial current yaw `+0.0F`, and initial previous yaw `+0.0F`; pass `e = 0`. These are legal finite arguments for the public `turn(double, double)` method and do not trigger any clamp on yaw.

- 1.16.5 double scaling then float store gives `g = 1.0162352323532104F`, and current yaw stores that value.
- 1.17 float-cast then float scaling gives `g = 1.0162353515625F`; current yaw and previous yaw store that value.
- `moveRelative` converts each stored yaw to radians with the same float multiply and `Mth.sin` selects table index **184** for 1.16.5 versus **185** for 1.17. The float argument to the table scale is `184.99996948242188F` versus `185.0F`, so this is an actual truncation-boundary crossing, not merely a different stored yaw that quantizes identically.
- With direct `moveRelative(0.1F, new Vec3(0, 0, 1))` and zero initial velocity, the table samples differ (`sin[184] = 0.01763986423611641F`, `sin[185] = 0.01773572340607643F`), so the added X velocity differs (`-0.001763986449897087` versus `-0.0017735723670359305`, using Java float table samples promoted into the double `Vec3` math). The corresponding cosine samples at indices 16568 and 16569 also differ, so Z differs too. This isolates the direct velocity response before any collision handling.

## Finding statement

`Entity.turn` changes yaw delta from double scaling/addition with direct field writes (1.16.5) to float-first scaling/addition with a finite-checked current-yaw setter and unchecked previous-yaw addition (1.17). Because ordinary `moveRelative` rotates its input using the stored yaw and a truncating sine lookup table, finite deltas can cross a table-index boundary and change horizontal velocity for an otherwise identical ground or air input. The change is in scope as one independently resolved yaw-turn operation; this report does not assert any later collision or full-tick trajectory consequence.
