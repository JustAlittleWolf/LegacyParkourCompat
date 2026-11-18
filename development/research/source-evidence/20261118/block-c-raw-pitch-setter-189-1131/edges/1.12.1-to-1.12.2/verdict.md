# Lead C blind source audit: local pitch / Elytra, 1.12.1 → 1.12.2

## Verdict

**No source difference in the assigned operation.** The ordinary local camera-pitch update, its previous-pitch adjustment, and the first pitch-dependent calculations in dry local Elytra glide are unchanged across these releases. This verdict covers only that bounded slice; it does not freeze the pair or claim broader movement coverage.

## Authenticated exact sources

- **1.12.1 Feather build.2:** used the current nested tree `decompiled_minecraft/1.12.1/ornithe-feather/`. Its embedded `.decompile-provenance.json` SHA-256 is `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; it pins the 1.12.1 client artifact to `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` and Feather `1.12.1+build.2` mergedv2 mapping artifact to `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`. The provenance embeds per-source hashes.
- The sibling retained tree `decompiled_minecraft/1.12.1/.ornithe-feather.previous-d0fbab78-2176-466b-af5b-fe37679e260f/` is **not** the selected current source. Its provenance bytes happen to match the nested current manifest exactly (same SHA above), and its audited source-file hashes also match; it provides no conflicting result. The 1.12.1 version directory has no separate root-level source publication sidecars, so I do not attribute a root-level publication identity to it.
- **1.12.2 Feather build.2:** used `decompiled_minecraft/1.12.2/ornithe-feather/` with its version-root sidecars. The ready marker identifies `1.12.2`, `ornithe-feather`, and `feather-gen2-1.12.2+build.2`; its declared `ornithe-feather.sources.sha256` digest is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, matching the file's SHA-256. The version-root provenance names the exact release/mapping namespace and mapping artifacts. The source-file hashes below match that manifest and the source bytes directly.

Relied source SHA-256 values (`1.12.1` / `1.12.2`):

| Feather source | SHA-256 |
|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` / same |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` / same |
| `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` / same |
| `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` / same |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` / `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The local-player class differs as a whole, but its relevant override is identical in both: `isLocallyControlled()` returns `true` (`LocalClientPlayerEntity.java:483–486`). The caller and audited movement/math files have identical full-file hashes across the pair.

## Source comparison

- In both `GameRenderer.java:863,867`, both smooth-camera branches call `minecraft.player.updateLocalPlayerCamera(h, i * j)`.
- In both `Entity.java:307–318`, the helper snapshots current pitch into `f`, updates yaw, computes current pitch as `(float)(this.pitch - pitch * 0.15)`, clamps current pitch to `[-90.0F, 90.0F]`, then applies `lastPitch = lastPitch + (pitch - f)`. Thus the previous-pitch delta is based on the **clamped new current** minus the old current; it does not simply copy current pitch. `MathHelper.clamp(float, float, float)` at `MathHelper.java:80–85` uses lower-then-upper comparisons. Finite overrange camera deltas therefore cannot leave the ordinary camera-updated current pitch outside ±90 (including finite inputs whose scaled result exceeds the range). These expressions, casts and write order are identical.
- The first active local glide path is `LivingEntity.tick()`'s travel call to `moveRelative` (`LivingEntity.java:1842–1848`). `LocalClientPlayerEntity.isLocallyControlled()` makes its local branch true. In `LivingEntity.moveRelative` (`1425–1435`), the dry path passes the water and lava gates, then requires `isFallFlying()`. Its first pitch-dependent operation is `getLookVector()`; `Entity.getLookVector()` (`Entity.java:1690–1692`) forwards the current `pitch` and `yaw` to `getRotationVector(float,float)` (`1239–1244`), which uses that pitch directly in sine/cosine. The next explicit read is `float i = this.pitch * (float)(Math.PI / 180.0)` (`LivingEntity.java:1434–1435`). No clamp or normalization occurs in this active glide branch before either read.

Accordingly, ordinary local mouse-camera input clamps finite overrange to ±90 before glide reads it. If another path has already stored a finite overrange pitch, the dry active glide calculations consume it directly; that behavior is unchanged in 1.12.1 and 1.12.2. The audit does not assert how often or by which external state path such a value is reached.


## Phase-two corroboration and recovery note

VERIFIED: I reconfirm the same bounded no-difference result. The two releases have byte-identical `Entity.java` and `LivingEntity.java`; the local player's relevant `isLocallyControlled()` override is text-identical. `GameRenderer` supplies float mouse deltas to `Entity.updateLocalPlayerCamera`, which computes the new pitch with the same `(float)(oldPitch - inputPitch * 0.15)` expression, clamps current pitch to ±90, then updates `lastPitch` by `newCurrent - oldCurrent`. Finite overrange camera input therefore cannot leave ordinary camera-updated current pitch outside the clamp, while that formula does not independently clamp `lastPitch`. In dry, active local Elytra glide, `LivingEntity.moveRelative` first calls `getLookVector()` (which reads stored pitch for trig), then reads pitch explicitly for radians; neither glide read clamps it. No operation difference is supported for this slice.

### Scratch mutation/recovery record

In the prior phase-two step, I appended the corroborating verdict to the frozen source report after the instruction I received explicitly named that report as the sole phase-two target. The subsequent scope correction identified that source report as author-owned and prohibited mutation. My earlier append changed that file. A read-only check found its original 7,751-byte prefix still intact with the expected SHA-256 `c33977836b350f20c3c321c25f0ad248fd64e51fc38edcdf04f3be158715c048`; the rest is the appended tail. The coordinator stated it is making an exact prefix copy in a distinct file. This receipt records the mutation and recovery evidence; I did not touch the source report during this repair.
