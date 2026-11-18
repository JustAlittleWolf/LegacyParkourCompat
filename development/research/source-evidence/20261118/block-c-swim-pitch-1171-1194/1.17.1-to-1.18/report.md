# Swim-pitch vertical writer: 1.17.1 → 1.18.0

## Result

**No source difference found in the bounded operation.** For an ordinary active player swimmer who is not a passenger, `Player.travel` computes its pitch-derived look-Y term and conditionally adds a vertical impulse to the current delta movement in the same order, with the same thresholds and numeric types in both releases. The later inherited movement call also reaches the same water-travel `move(SELF, getDeltaMovement())` site. This closes only the swim-pitch vertical writer slice; it does not establish full-pair coverage or a historical behavior change.

## Exact-source identity and integrity

Both sides are Mojmap exact-source publications, read from `decompiled_minecraft/` without regeneration. The ready markers identify exact metadata IDs `1.17.1` and `1.18` (the 1.18.0 release ID). Their source-manifest SHA-256 values were recomputed from the manifest bytes and match the ready markers. Each relied Java file's SHA-256 also matches its entry in that source manifest and its current file bytes.

| Release | Ready marker | Provenance | Source-manifest SHA-256 | Client SHA-256 |
|---|---|---|---|---|
| 1.17.1 / Mojmap | `decompiled_minecraft/1.17.1/mojmap.ready.json` (SHA-256 `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`) | `decompiled_minecraft/1.17.1/mojmap.provenance.json` (SHA-256 `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`); recorded batch `1.15.2,1.16.5,1.17.1,1.18.2`, Mojmap, Vineflower 1.12.0, Java 25.0.3 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` |
| 1.18.0 / Mojmap | `decompiled_minecraft/1.18/mojmap.ready.json` (SHA-256 `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`) | `decompiled_minecraft/1.18/mojmap.provenance.json` (SHA-256 `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`); recorded batch `1.18,1.18.1`, Mojmap, Vineflower 1.12.0, Java 25.0.3 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` |

## Trace and comparison

**Reachable gate.** `Player.isSwimming()` is `!abilities.flying && !isSpectator() && super.isSwimming()` in both versions (`Player.java:1802–1804`). The direct writer is entered only when that predicate is true and `!isPassenger()` (`Player.java:1413`). The swimming override and outer gate match.

**Direct vertical writer.** In both `Player.travel` methods (`Player.java:1409–1435`), before `super.travel`, the operation is:

1. `g = getLookAngle().y` (`double`).
2. `h = g < -0.2 ? 0.085 : 0.06` (`double`; strict `<`).
3. Write only if `g <= 0.0 || jumping || !level.getBlockState(new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())).getFluidState().isEmpty()`.
4. On that gate, read current delta movement, then `setDeltaMovement(old.add(0.0, (g - old.y) * h, 0.0))`.

The sampled block uses the exact expression `getY() + 1.0 - 0.1`, in that order, and the same X/Z reads and fluid-empty predicate. The `h` threshold is evaluated before the write gate. There is no pitch clamp or interpolation in this path.

**Pitch to look-Y math and widening.** `Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())` directly in both versions (`1.17.1 Entity.java:1831–1833`; `1.18 Entity.java:1813–1815`). `getXRot` and `getYRot` return raw `float` rotations (`1.17.1 Entity.java:3007–3022`; `1.18 Entity.java:2998–3013`). `calculateViewVector` performs float math: `pitchRadians = pitch * (float)(Math.PI / 180.0)`, `yawRadians = -yaw * (float)(Math.PI / 180.0)`, then `sin(pitchRadians)` and returns Y as unary `-sin(pitchRadians)` (`1.17.1 Entity.java:1339–1348`; `1.18 Entity.java:1311–1320`). In Java the exact source casts the `Math.PI / 180.0` constant to `float` before multiplying; both products, table results, and unary negation remain `float`.

`Mth.sin(float)` in both releases indexes `SIN[(int)(angle * 10430.378F) & 65535]` (`Mth.java:43–45`). Both initialize the same 65,536-entry float table from `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` (`Mth.java:27–31`). There is no interpolation. `calculateViewVector` passes its float components to `new Vec3(double, double, double)`, so the table-derived Y float is widened to double at construction (`Vec3.java:39–42`). Thus `g` in `Player.travel` is a widened float table result; threshold comparisons and `(g - old.y) * h` then use double arithmetic. These expressions and conversion points match.

**Inherited consumer / actual move.** The ordinary non-flying path invokes `super.travel` after the direct write (`Player.java:1435` in both versions). `LivingEntity.travel` has the same eligible water branch and order (`1.17.1:2003–2040`; `1.18:2005–2042`): `moveRelative(...)`, then `move(MoverType.SELF, getDeltaMovement())`, then subsequent velocity adjustment. Therefore the written velocity is consumed by the same inherited actual-move call. This comparison stops at that call and does not assess fluid adjustment or collision response.

## Relied source hashes

Hashes below are SHA-256 of the exact Java source bytes; each was verified against the corresponding line in `mojmap.sources.sha256` and recomputed from the canonical file.

| File | 1.17.1 | 1.18.0 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| `net/minecraft/world/phys/Vec3.java` | `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e` | `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c` |

No implementation, build, test, game launch, source generation or Git operation was performed.
