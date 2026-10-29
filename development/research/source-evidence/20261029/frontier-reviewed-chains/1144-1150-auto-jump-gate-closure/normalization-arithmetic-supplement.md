# Inverse-square-root arithmetic correction: 1.14.4 → 1.15

This is an immutable correction supplement bound to three prior source-only artifacts:

| Bound artifact | SHA-256 |
|---|---|
| Initial `closure.md` | `82b60d6b1007782c822014f6050fa426bd1e00f01885a14a9f886cdfd74973d6` |
| `mojmap-pair-supplement.md` | `a1edced4bf92b7526de9e3b9c383e82d59c60c4c3b3739e65aebbed2cc627c63` |
| Independent `1144-1150-auto-jump-audit/audit.md` | `d1564cde9da9fafb28d0f261dd4e5c86000ef39c07741a21f962feeb04ed30b5` |

The paired Mojmap source identities are those recorded in the bound Mojmap supplement. The exact source files used for this correction are `decompiled_minecraft/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java` (SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`), `decompiled_minecraft/1.14.4/mojmap/net/minecraft/util/Mth.java` (`57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576`), `decompiled_minecraft/1.15/mojmap/net/minecraft/client/player/LocalPlayer.java` (`7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`), and `decompiled_minecraft/1.15/mojmap/net/minecraft/util/Mth.java` (`a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`). These file hashes match their source-manifest entries.

## Corrected arithmetic

In both `LocalPlayer.updateAutoJump` bodies, the squared horizontal length is first assigned to a `float i` from a cast of `vec33.lengthSqr()` (`1.14.4 LocalPlayer.java:865`; 1.15 `:868-869`, with the fallback recomputation at `:872-876`). The normalizer call therefore receives a `float` in both releases, but Java resolves it differently:

- In 1.14.4, `float n = (float)Mth.fastInvSqrt(i)` (`LocalPlayer.java:878`) can only select `Mth.fastInvSqrt(double)` because that is the only overload in the file (`util/Mth.java:395-401`). The float argument widens to double. The method computes `e = 0.5 * d`, reinterprets the 64-bit double raw bits, applies the 64-bit seed constant `6910469410427058090L - (bits >> 1)`, reinterprets that seed as double, and performs one double-precision Newton correction: `d * (1.5 - e * d * d)`. Its returned double is then cast to float for `n`.
- In 1.15, `float n = Mth.fastInvSqrt(i)` (`LocalPlayer.java:882`) selects the newly available exact `float` overload (`util/Mth.java:394-400`). It computes `g = 0.5F * f`, reinterprets `Float.floatToIntBits(f)`, applies the 32-bit seed constant `1597463007 - (bits >> 1)`, reinterprets the seed as float, and performs one float-precision correction: `f * (1.5F - g * f * f)`. It returns a float directly. The separate double overload still exists at `Mth.java:402-408`, but it is not selected for this float argument.

Both paths then pass the float `n` to `vec33.scale(n)` (`1.14.4 LocalPlayer.java:879`; 1.15 `:883`), where normal Java numeric promotion applies to the vector API. The normalized direction calculation is therefore not the same source-level arithmetic procedure: the initial approximation uses different bit widths and seed constants, the Newton correction uses double versus float intermediates, and the older path narrows the result only after the double approximation.

## Correction to prior wording and evidence boundary

Withdraw the sentence in `mojmap-pair-supplement.md` that the “subsequent detection math retain[s] the same source-level structure”; it was overbroad because it omitted the inverse-square-root overload and approximation change. The surrounding vector, dot-product, scan-distance, and threshold expressions retain their shown structure, with the normalization arithmetic described above as a concrete exception. No numerical error comparison, threshold-crossing analysis, runtime experiment, or trajectory consequence was performed. The result establishes the overload and algorithm change only; numerical equivalence and movement effect remain unproven.
