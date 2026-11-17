# Independent source audit: Elytra fall-distance state

**Terminal:** COMPLETED — ACCEPTED within the bounded source claim below.

## Immutable input and evidence bounds

- Frozen comparison: glide-fall-distance-source/comparison.md; SHA-256 `cdf603f62d36bcb286891d8147eaa968f6b5009655e2f3cac58c379e79ae910f` (verified).
- Exact pair audited: 1.19.2 → 1.19.3, Mojmap, canonical `decompiled_minecraft/<version>/mojmap/` source only.
- 1.19.2 ready marker `mojmap.ready.json`: `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`; provenance: `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`; source manifest: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`.
- 1.19.3 ready marker `mojmap.ready.json`: `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6`; provenance: `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`; source manifest: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`.
- Audited source SHA-256s, each matching its source-manifest entry:
  - 1.19.2 `Entity.java` `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; `LivingEntity.java` `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`.
  - 1.19.3 `Entity.java` `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; `LivingEntity.java` `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- `Vec3.java` type evidence: 1.19.2 `1d46be5be0e4041bcd6f3bdb86edf8c22d0b4291c0d3e84bf66de311b4c6a886`; 1.19.3 `ec99e432b690cd7db0f8bec30eb6bb51fcb13fa75e035522f6a65609974399af`. Both declare the vector components as `double`; the 1.19.3 `y()` accessor returns `double`.

## Verdict

The comparison's central claim is source-confirmed. In 1.19.2, `LivingEntity.travel` enters the glide clause after the water and lava clauses (`LivingEntity.java:2042-2107`). It first captures `Vec3 $$13 = this.getDeltaMovement()` at line 2108, then evaluates `$$13.y > -0.5` at line 2109 and, if true, writes `fallDistance = 1.0F` at line 2110. `$$13.y` and `-0.5` are `double`; `fallDistance` is `float` and the assigned literal is `float` (`1.0F`). This write is before look-angle capture and glide calculations (`2113+`). The strict `>` means equality at -0.5 does not write.

In 1.19.3, the same glide clause begins at `LivingEntity.java:2117`; it calls `checkSlowFallDistance()` at line 2118 before fetching the delta vector at line 2119 and before glide calculations. `Entity.checkSlowFallDistance()` (`Entity.java:2147-2151`) tests `deltaMovement.y() > -0.5 && fallDistance > 1.0F`, then writes `fallDistance = 1.0F`. The vertical component and `-0.5` comparison are `double`; the fall-distance field and `1.0F` comparison/assignment are `float`. Both conditions are strict. Thus a dry locally controlled player already gliding with vertical speed above -0.5 has its prior distance overwritten to exactly 1.0F in 1.19.2, while 1.19.3 only caps a distance greater than 1.0F and preserves values at or below 1.0F. At vertical speed exactly -0.5, neither version performs this glide write.

The witness is reachable within the stated bound: `travel` runs for effective AI or the locally controlled instance (`LivingEntity.java:2043` in 1.19.2 and corresponding 1.19.3 method opening); when dry, the water/lava branches do not take precedence, leaving the `isFallFlying()` branch eligible. `isFallFlying()` reads shared flag 7 (`LivingEntity.java:3048` in 1.19.2; `3063` in 1.19.3). Its existing-flight validation (`updateFallFlying`, lines 2626+ / 2637+) retains the flag for an airborne, non-passenger, non-Levitation entity with an enabled `Items.ELYTRA` in the chest slot. This supports an ordinary dry existing-flight witness; activation/input is not audited here. The Slow Falling setup earlier in `travel` resets distance only when the vertical velocity is nonpositive and the effect is present (`LivingEntity.java:2045-2049` / `2055-2059`), so the witness excludes that effect as specified.

After the glide travel operation, `Entity.move` passes its resolved vertical displacement to `checkFallDamage` (`Entity.java:597` in both releases). For negative resolved displacement, the accumulator performs `fallDistance -= (float) dy` (`Entity.java:991-1001` in 1.19.2; `1000-1011` in 1.19.3). Therefore any later downward resolved movement can accumulate after the glide write in that tick. The accumulator's grounded reset/downstream block handling is a separate operation.

## Scope separations and limits

This verdict covers only the glide-branch fall-distance write/cap and the local dry witness for the exact 1.19.2 → 1.19.3 pair. It does not locate the change within the release interval, establish full-pair coverage, or establish runtime parity. Fall-damage production/application and `calculateFallDamage` are damage-resolution operations, separate from this movement-state verdict. Slow Falling and Levitation resets elsewhere in `travel` are effect-driven resets, also separate from the glide rule. No damage result or effect-reset behavior is accepted or audited by this verdict.
