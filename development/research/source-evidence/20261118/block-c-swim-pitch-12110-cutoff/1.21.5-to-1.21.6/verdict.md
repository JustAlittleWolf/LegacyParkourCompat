# Independent boundary audit: 1.21.5 → 1.21.6 swim pitch Y correction

## Pre-report independent freeze

This trace and the identities below were frozen before opening the assigned source-boundary report. It covers active, nonpassenger `Player.travel` swimming pitch correction, its float/trigonometric inputs, gates and order through the movement call. Evidence is only the canonical local Mojmap decompilation and its manifests.

### Trace and bounded conclusion

- `Player.travel(Vec3)` first sends passengers straight to `super.travel` and returns from the `if/else` path. Only the nonpassenger branch reaches the correction. Within it the writer is gated by `isSwimming()`.
- In both versions, the writer reads `double lookY = getLookAngle().y`, then chooses `double factor = lookY < -0.2 ? 0.085 : 0.06`. It applies the correction only if `lookY <= 0.0 || jumping || !fluidState(containing(x, y + 1.0 - 0.1, z)).isEmpty()`. On that gate it snapshots the current movement and writes `old.add(0.0, (lookY - old.y) * factor, 0.0)` via `setDeltaMovement`. Thus the Y component becomes `oldY + (lookY - oldY) * factor`; the X/Z inputs are zero. This write is before the flight-specific branch and before either `super.travel` call.
- `Entity.getLookAngle()` in both calls `calculateViewVector(getXRot(), getYRot())`. The exact float sequence is `pitchRad = xRot * (float)(Math.PI / 180.0)` and `negativeYawRad = -yRot * (float)(Math.PI / 180.0)`, followed, in order, by `cos(negativeYawRad)`, `sin(negativeYawRad)`, `cos(pitchRad)`, `sin(pitchRad)`. The returned vector is `new Vec3(sinNegativeYaw * cosPitch, -sinPitch, cosNegativeYaw * cosPitch)`. `Vec3.y` stores a double, so `-sinPitch` is widened from float to double; it is not recomputed with double trig.
- Both `Mth.sin(float)` implementations return `SIN[(int)(angle * 10430.378F) & 65535]`. Both `Mth.cos(float)` return `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. `SIN` is a 65,536-element float table initialized as `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. The scale multiply and cosine offset are float expressions; Java casts the resulting float to int by truncation toward zero, then `& 65535` wraps the index. The cast/index expressions and table initializer are identical in the compared source.
- Once `super.travel` runs, `LivingEntity.travel` selects the fluid branch when `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(fluidState(blockPosition()))`; for water the `travelInFluid` water arm performs `moveRelative(...)` and then `move(MoverType.SELF, getDeltaMovement())`. The pre-correction therefore feeds the ordinary active-player move. In the player flying branch, the pre-correction still occurs first; then the method saves the corrected Y, calls `super.travel`, and overwrites resulting Y with saved Y times `0.6`. Nonflying players call `super.travel` directly after the correction.
- Paired `Player.travel`, `Entity.calculateViewVector`, `Entity.getLookAngle`, `Mth.sin/cos` and the relevant `LivingEntity.travelInFluid` water movement have the same predicates, expressions and ordering in these endpoints. The source-line offsets and surrounding class edits do not make this direct correction a behavioral boundary. Frozen result: **no difference for the assigned swim pitch direct-Y-correction operation from 1.21.5 to 1.21.6**. This says nothing about unrelated swim changes or other release intervals.

### Source locations

All paths are relative to `decompiled_minecraft/<version>/mojmap/`.

| Member | 1.21.5 | 1.21.6 |
|---|---:|---:|
| `world/entity/player/Player.java`, `travel(Vec3)` | 1426–1449 | 1438–1461 |
| `world/entity/Entity.java`, `calculateViewVector(float,float)` | 1669–1677 | 1746–1754 |
| `world/entity/Entity.java`, `getLookAngle()` | 2188–2190 | 2356–2358 |
| `util/Mth.java`, table and `sin/cos(float)` | 32–54 | 32–54 |
| `world/entity/LivingEntity.java`, `travel(Vec3)` / `travelInFluid(Vec3)` | 2213–2222 / 2249–2282 | 2245–2254 / 2301–2334 |

### Canonical evidence identity and hash checks

1.21.5 has a ready marker: `decompiled_minecraft/1.21.5/mojmap.ready.json` SHA-256 `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; parsed fields say `status=ready`, `versionId=1.21.5`, `mapping=mojmap`, 5,921 source files, with `sourceManifestSha256=365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` and artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`. The actual source manifest hash matches the ready field. Its provenance file SHA-256 is `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e`; diagnostics SHA-256 is `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755`. The ready marker records client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.
2.1.6 has no adjacent ready marker/source-manifest file; the canonical tree has hidden `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json`, SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`. Its inputs pin the 1.21.6 client jar (`.../objects/740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar`, SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`), Mojang mapping text (`.../objects/848855615bc81e3db1c85e69b6afb150807a1261/client.txt`, SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`) and version metadata (`.../packages/8dd125560431a618094435343acccd45191bb006/1.21.6.json`, SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`). The manifest records Mojmap source paths and their SHA-256 values. Its pipeline/tool/runtime identity is `lpc-decompile-v1`, Vineflower 1.12.0, Mapping IO 0.9.1, Tiny Remapper 0.14.1, Java 25.0.3+9-LTS on Windows 11 amd64.
3. The per-file hashes below were recomputed from canonical source bytes and match the manifest on each side (1.21.5 entries are in `mojmap.sources.sha256`; 1.21.6 entries are in `.decompile-provenance.json`):

| Source | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
|---|---|---|
| `world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `util/Mth.java` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` |

---

## Report comparison

The assigned report was opened only after the independent trace above was frozen. SHA-256 verified as `b29fb1fd1cf8b98f3f4d32aa798043a78a9ba36f81d9bb5f9ecf8fe1a9c0f5c0`.

### Independent audit verdict: agree

The report independently reaches the same bounded no-difference conclusion and its core source trace matches the frozen trace: nonpassenger and swimming gates; one `lookY` read; strict coefficient threshold; nonpositive/jumping/fluid write gate; saved movement snapshot and correction formula; angle conversion and float LUT lookup semantics; correction before superclass fluid movement. The report’s added call-path evidence is also correct: 1.21.5 `LivingEntity.aiStep` requires `canSimulateMovement`, while 1.21.6 also requires `isEffectiveAi`; both methods return `isLocalInstanceAuthoritative`, which resolves true for `LocalPlayer` (`Player.isLocalClientAuthoritative()` delegates to `isLocalPlayer()`, and `LocalPlayer.isLocalPlayer()` returns true). The water path calls `moveRelative` and then `move`; `LocalPlayer.move` delegates to `super.move` before its auto-jump/walk-distance bookkeeping. The report’s listed LocalPlayer file hashes were independently recomputed and match the canonical manifest bytes.

Two quoted source line spans are one line broader than their narrow labels: 1.21.5 `Player.travel` begins at line 1426 (rather than 1425), and the fluid-state expression begins at line 1433 (not 1430); the matching 1.21.6 starts are 1438 and 1445 (rather than 1437 and 1442). The broader spans include the adjacent annotation/swimming gate and do not affect the claim or verdict. All remaining compared slices and identities checked out.

**Verdict:** independent audit passes for the assigned operation. The only provenance limitation is the source tree’s missing 1.21.6 ready marker/separate source manifest; the hidden provenance manifest and cited file hashes are intact and exact for this bounded comparison. This verdict does not claim full-pair coverage, exact intermediate introduction boundary, or runtime parity.