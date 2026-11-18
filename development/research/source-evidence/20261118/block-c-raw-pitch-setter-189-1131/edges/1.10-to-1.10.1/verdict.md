# Lead C: 1.10 → 1.10.1 pitch-turn source audit

**Verdict: no source-confirmed movement difference in this slice.** The ordinary local camera-pitch update, its clamp, and the active Elytra movement reads of stored pitch are unchanged between exact Feather 1.10 and 1.10.1. The only difference found in `PlayerEntity.java` is its serialized `DataVersion` constant (`510` → `511`), which does not affect movement.

## Source identity and provenance

Both source roots are `decompiled_minecraft/<version>/ornithe-feather/`. Each contains a literal nested `.decompile-provenance.json` with exactly the accepted `DecompileStore.Manifest` fields (`pipeline`, `runtime`, `tools`, `inputs`, `sources`); `DecompileStore.read` requires non-null fields and a nonempty `sources` map. The manifests use the canonical `lpc-decompile-v1` pipeline, and their exact client, Feather mapping artifact and Feather metadata inputs are pinned below. There is no separate version-root marker field in the accepted schema; its absence is not a provenance gap.

- 1.10 manifest SHA-256: `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`
  - Feather metadata `1.10`: `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`
  - Feather `1.10+build.2` mergedv2 artifact: `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`
  - Mojang client artifact: `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`
- 1.10.1 manifest SHA-256: `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`
  - Feather metadata `1.10.1`: `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`
  - Feather `1.10.1+build.2` mergedv2 artifact: `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`
  - Mojang client artifact: `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`

The listed source hashes are the manifest's exact rows and were independently recomputed from each local `.java` file; every listed row matched its local file.

| Exact Feather source row | 1.10 SHA-256 | 1.10.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | same |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | same |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | same |
| `net/minecraft/client/render/GameRenderer.java` | `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` | `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501` |

## Compared behavior

- `net/minecraft/client/render/GameRenderer.java:832-855` calls the local player's camera update only while focused and the mouse path is active. Both ordinary and smooth-camera branches pass the same `h` and signed `i` values to `updateLocalPlayerCamera` in both releases; the file hashes match.
- `net/minecraft/entity/Entity.java:291-301`, `updateLocalPlayerCamera`, is identical in both releases. It snapshots current pitch into local float `f`; writes `pitch = (float)(pitch - inputPitch * 0.15)` (the `0.15` literal makes the multiply/subtract double precision before the cast); clamps that current float to `[-90.0F, 90.0F]`; then updates previous pitch in order with `lastPitch = lastPitch + (pitch - f)`. There is no finite-value predicate in this path.
- `net/minecraft/util/math/MathHelper.java:80-85` implements float clamp with `< min`, then `> max`, otherwise returns `x`. Therefore ordinary out-of-range finite values clamp; `NaN` passes both comparisons and is returned unchanged. This is the same source in both versions.
- The first active-flight movement use is in `net/minecraft/entity/living/LivingEntity.java:1332-1371`. `moveRelative` must be on a local/logical movement side, pass its water and lava gates, and enter `isFallFlying()`. It then calls `getLookVector()` at line 1341 and obtains the direct current-pitch radians at line 1342. The look-vector route (`LivingEntity.java:1852-1865`) requests tick delta `1.0F`, selecting direct `this.pitch`, not interpolated `lastPitch`; `Entity.java:1148-1153` converts the supplied pitch with cosine/sine. That current pitch feeds the Elytra velocity terms. The gates and consumers are byte-identical in both versions. The existing `flyingTick` active-flight gate in `LivingEntity.java:1754-1773` checks airborne/not riding and an Elytra that `ElytraItem.canFly` accepts before preserving the flight flag; this file is unchanged too.
- A line comparison of the only differing relevant row, `PlayerEntity.java`, found just `DataVersion` `510` versus `511`; no pitch-turn or movement logic differs there.

This is a bounded audit of local pitch turning and its active, dry Elytra movement consumer, not a full 1.10/1.10.1 movement-pair coverage freeze. No separate finding is warranted for this proposed boundary.

## Phase-two independent comparison

**Author report audited:** `c-raw-pitch-setter-110-1101-source/report.md`, SHA-256 `d73f427175ec069b22d08edbaa7b4e4251799fe58ceb91bb47a0f7363100f93b` (verified before and after this comparison). The author report agrees with the frozen scoped verdict above.

I independently checked the added dispatch evidence against both Feather roots: `LocalClientPlayerEntity.java` is byte-identical across the pair (manifest/local SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`) and `isLocallyControlled()` returns `true` at lines 458–460. `PlayerEntity.moveRelative()` at lines 1391–1408 is identical in both versions: only the abilities-flying and not-riding branch changes the movement setup and clears flag 7; the ordinary `else` dispatches to `super.moveRelative`, allowing the parent Elytra branch for a non-creative-flying player. Both files' manifest source rows match their local file SHA-256. This supports the author's call chain from local player movement to `LivingEntity.moveRelative`.

The bounded source excerpts, `NaN` comparison behavior, finite-input qualification, active-flight flag gate, direct-current-pitch read through the exact-`1.0F` look-vector route, and no-difference conclusion are consistent with the exact sources and with the previously recorded independent hash checks. The differing `PlayerEntity.java` file remains only the non-movement `DataVersion` constant (`510` to `511`). **Independent verdict: agree; no accepted movement difference for this slice.** This does not establish full-pair coverage.