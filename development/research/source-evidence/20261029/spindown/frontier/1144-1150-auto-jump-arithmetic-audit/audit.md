# Independent source-only arithmetic audit: 1.14.4 → 1.15 auto-jump

## Verdict

The normalization-arithmetic correction is source-correct. In both `LocalPlayer.updateAutoJump` bodies, the squared horizontal length is explicitly narrowed to `float` before the call. In 1.14.4, that float argument widens to the only available `Mth.fastInvSqrt(double)` overload; the returned double is then narrowed to `float n`. In 1.15, overload resolution selects the newly present `Mth.fastInvSqrt(float)` overload and returns a float directly. The approximation algorithms use distinct bit widths, constants, and intermediate types. This establishes a source-level arithmetic change only; I did not establish a numeric error bound, threshold crossing, or trajectory consequence.

## Artifact binding and source identity

Recomputed SHA-256 values match the requested correction supplement (`c19bfb775f74f5e0f9272c99992f6713e5a7bee5a3b6c7b3d74a7535b5065ff2`), its bound original report `closure.md` (`82b60d6b1007782c822014f6050fa426bd1e00f01885a14a9f886cdfd74973d6`), the Mojmap pair supplement (`a1edced4bf92b7526de9e3b9c383e82d59c60c4c3b3739e65aebbed2cc627c63`), and the cited independent gate audit (`d1564cde9da9fafb28d0f261dd4e5c86000ef39c07741a21f962feeb04ed30b5`). These match the supplement's binding table and the task's requested hash.

I independently checked the primary local source trees' provenance, ready markers, source-manifest file digests, and the two relevant manifest entries per version:

| Source | Provenance identity | Ready/source-manifest identity | Relevant source-manifest entries |
|---|---|---|---|
| 1.14.4 Mojmap | `mojmap.provenance.json` SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; namespace `mojmap`, version/batch `1.14.4`, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` | `mojmap.ready.json` SHA-256 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `LocalPlayer.java` `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`; `Mth.java` `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576` |
| 1.15 Mojmap | `mojmap.provenance.json` SHA-256 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`; namespace `mojmap`, requested version `1.15`, batch `1.15`/`1.15.1`, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` | `mojmap.ready.json` SHA-256 `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`; source-manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `LocalPlayer.java` `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`; `Mth.java` `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |

The actual source-manifest files hash to the values in their ready markers; each listed Java file's computed SHA-256 matches its corresponding manifest entry. The cited Java file hashes therefore identify the exact files inspected.

## Source trace and overload resolution

- In 1.14.4 `LocalPlayer.java:865` and 1.15 `LocalPlayer.java:868,876`, the method assigns `float i = (float)vec33.lengthSqr()`. `Vec3.lengthSqr()` returns `double` in both source trees (`Vec3.java:99` and `:104`, respectively); the explicit cast makes the call argument's compile-time type `float` after either initial calculation or fallback recomputation.
- At 1.14.4 `LocalPlayer.java:878`, `(float)Mth.fastInvSqrt(i)` passes that float to `Mth`. The full 1.14.4 `Mth.java` declares exactly one overload, `public static double fastInvSqrt(double d)` (`:395-401`). A float can widen to double, and there is no float overload to select.
- At 1.15 `LocalPlayer.java:882`, `Mth.fastInvSqrt(i)` passes a float. The full 1.15 `Mth.java` declares `fastInvSqrt(float)` (`:394-400`) and `fastInvSqrt(double)` (`:402-408`). The float overload is an exact invocation conversion and is more specific than widening to double, so the float overload is selected. The retained double overload is not called here.
- Both callers assign the result to `float n` and then call `vec33.scale(n)` (`1.14.4 LocalPlayer.java:878-879`; 1.15 `:882-883`). `Vec3.scale` accepts `double` in both versions (`Vec3.java:79` and `:84`), so `n` widens to double for vector scaling. That later widening does not alter which `fastInvSqrt` overload was selected or recover precision already lost in `n`.

## Overload bodies and intermediate arithmetic

1.14.4 has only this double body (`Mth.java:395-401`):

```java
double e = 0.5 * d;
long l = Double.doubleToRawLongBits(d);
l = 6910469410427058090L - (l >> 1);
d = Double.longBitsToDouble(l);
return d * (1.5 - e * d * d);
```

Here the parameter, `e`, reconstructed seed `d`, and arithmetic literals/operations are double; the bit seed is a `long`. `updateAutoJump` narrows the returned double at its explicit `(float)` cast.

1.15 adds this float body (`Mth.java:394-400`):

```java
float g = 0.5F * f;
int i = Float.floatToIntBits(f);
i = 1597463007 - (i >> 1);
f = Float.intBitsToFloat(i);
return f * (1.5F - g * f * f);
```

The parameter, `g`, reconstructed seed `f`, and arithmetic operations are float; the bit seed is an `int`, with `0.5F` and `1.5F` float literals. `Float.floatToIntBits` is the source operation (rather than `floatToRawIntBits`). For comparison, 1.15's retained double overload (`Mth.java:402-408`) has the same double formula and 64-bit seed as 1.14.4; its presence does not change the call-site selection.

Thus the supplement's specific statement—1.14.4 performs the double approximation then narrows, while 1.15 performs the float approximation—is confirmed. No numerical test or error analysis is included in this audit.

## Scope and evidence boundary

This audit independently checks only the bound arithmetic correction, its source identity chain, the call-site static type and overload resolution, and every `fastInvSqrt` overload/body in the paired `Mth.java` files. It does not inspect product code, history, backlogs, or wiki material, and it makes no claim about numeric equivalence, normalization error magnitude, threshold sensitivity, or movement outcome.
