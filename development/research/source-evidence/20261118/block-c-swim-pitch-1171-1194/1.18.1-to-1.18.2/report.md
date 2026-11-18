# Player swim-pitch vertical adjustment — 1.18.1 to 1.18.2

## Scope and result

Compared the player-owned pre-`super.travel` swimming adjustment in Mojmap `Player.travel(Vec3)` and only the required pitch/look-vector, coefficient/gate, and inherited movement dependencies. **No source difference found in this bounded operation.** This is not a finding and does not establish coverage of the full pair or an exact patch-introduction boundary.

## Exact source inputs and readiness

Read-only trees: `decompiled_minecraft/1.18.1/mojmap/` and `decompiled_minecraft/1.18.2/mojmap/`.

- 1.18.1 provenance: `decompiled_minecraft/1.18.1/mojmap.provenance.json`, SHA-256 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`; identifies exact version `1.18.1`, Mojmap, run `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, and 4,203 source files / 20,530,654 bytes. The matching source manifest is `decompiled_minecraft/1.18.1/mojmap.sources.sha256`, SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`.
- 1.18.2 readiness: `decompiled_minecraft/1.18.2/mojmap.ready.json`, SHA-256 `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`; says ready for exact version `1.18.2`, Mojmap, 4,236 source files / 20,740,504 bytes. Its source manifest is `decompiled_minecraft/1.18.2/mojmap.sources.sha256`, SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`.
- 1.18.2 provenance: `decompiled_minecraft/1.18.2/mojmap.provenance.json`, SHA-256 `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`.

For each relied-on Java file below, its current SHA-256 was checked against the corresponding line in that version's `mojmap.sources.sha256`; every value matched. `Player.java` and `Entity.java` hashes bind the direct adjustment and pitch access/vector calculation; `Mth.java` binds the lookup; `LivingEntity.java` binds the inherited application site; `Vec3.java` was checked as the alternate direction-vector implementation and is unchanged.

| Relative source path | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/util/Mth.java` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` | `32747c5b09fc184baae356e39a0088fd6c9f08f69d9e98b67c19e1de6f1bb2e` |
| `net/minecraft/world/phys/Vec3.java` | `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c` | `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c` |

## Paired source trace

- **Player-owned writer:** `Player.java`, `Player.travel(Vec3)`, 1.18.1 lines 1409–1429; 1.18.2 lines 1422–1442. Both snapshot position, then gate the adjustment on `isSwimming() && !isPassenger()`. They sample `double lookY = getLookAngle().y`; choose `0.085` only when `lookY < -0.2`, otherwise `0.06`; and write `delta.add(0.0, (lookY - delta.y) * coefficient, 0.0)` when `lookY <= 0.0 || jumping || block-above-fluid-nonempty`. The block probe uses `(getX(), getY() + 1.0 - 0.1, getZ())` and tests `getFluidState().isEmpty()`. The pre-super order, literals, strict/non-strict comparisons, sampled values, and vector write are identical. Both then call `super.travel(input)` (or the existing flying branch) and record movement statistics.
- **Pitch source and conversion:** `Entity.getLookAngle()` returns `calculateViewVector(getXRot(), getYRot())` (1.18.1 `Entity.java` lines 1813–1815; 1.18.2 lines 1816–1818). `getXRot()` returns the stored `float xRot` (1.18.1 lines 3010–3012; 1.18.2 lines 3027–3029). In `calculateViewVector` (1.18.1 lines 1311–1319; 1.18.2 lines 1314–1322), pitch is converted as `float pitchRadians = pitch * (float)(Math.PI / 180.0)`; the Y component is `-Mth.sin(pitchRadians)`. Same float input, cast, operation order, sign and output type.
- **LUT index/math:** `Mth.sin(float)` is `SIN[(int)(radians * 10430.378F) & 65535]` in both versions (`Mth.java` lines 43–44); `cos` also matches at lines 47–48. The sine table is initialized with 65,536 `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` entries in both. No interpolation or alternate pitch-index path was found in this trace.
- **Independent vector helper check:** `Vec3.directionFromRotation(float,float)` is text-identical in both at lines 199–204, but `getLookAngle()` uses `calculateViewVector()` directly, so this helper is not the operative swim adjustment path.
- **Inherited movement application:** `LivingEntity.travel(Vec3)` is reached by `Player`'s `super.travel`. In the 1.18.1 water branch it calls `move(MoverType.SELF, getDeltaMovement())` at line 2038; corresponding 1.18.2 call is line 2042. The local fluid processing around that call was not compared as an operation in this assignment. This confirms the adjusted velocity reaches the inherited self-move input on the water branch without expanding the review into fluid physics. `Entity.move(MoverType, Vec3)` begins at 1.18.1 line 541 and 1.18.2 line 543; collision/move resolution was outside the requested boundary.

## Coverage boundary

Examined paired sources: the `Player.travel` prefix through dispatch to `super.travel`; `Entity.getLookAngle`, `getXRot`, and `calculateViewVector`; `Mth` sine/cosine lookup and table initialization; `Vec3.directionFromRotation` as a checked alternate; and only the inherited `LivingEntity.travel` water-branch move call needed to locate application. No-difference coverage is limited to this swim-pitch direct Y adjustment and its pitch-to-vector dependency. Elytra, other fluid behavior, collision resolution, other `Player.travel` paths, and all other pair inventory are unexamined.
