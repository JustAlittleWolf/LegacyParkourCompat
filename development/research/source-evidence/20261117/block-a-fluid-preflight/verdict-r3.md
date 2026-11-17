# Fluid preflight witness audit — corrected r3 arithmetic rebind

## Revision identity

The exact immutable r3 files rehash to their supplied SHA-256 values:

- `comparison.corrected-r3.md`: `46e0d2c25a4e2e2e60edc6ce4a5b0914424f1afa5de548c63ff926effcb87c43`.
- `fluid-scan-chunk-availability-halo.corrected-r3.md`: `a6adff985fc73a36bc08268a6426ac3d9568283f11c8192efe2d01aa2a57cef4`.

The prior audit files remain unchanged. Their pinned `FlowingFluid.java` and `WaterFluid.java` hashes still match the exact source files in the frozen roster for 1.21.11, 26.1, 26.1.1, 26.1.2, and 26.2.

## Arithmetic verdict

**The r3 finite witness is supported, conditional on the listed cells and no other current-producing fluid in the scan.** The source-height correction is now accurate. `WaterFluid.Source.getAmount()` is 8 in both providers; `FlowingFluid.getOwnHeight()` is amount divided by `9.0F`, and `getHeight()` returns 1.0 only when the fluid above has the same type. With no same-type water above `(14,64,8)`, its height is `8/9.0F ≈ 0.8888889F`, and its top is `64 + 8/9.0F ≈ 64.8888889`, not Y=65. The supporting methods are 1.21.11 `WaterFluid.java:149-153`, `FlowingFluid.java:434-445`; and 26.1+ `WaterFluid.java:149-153`, `FlowingFluid.java:466-477`.

At this player's Y, the old scan height is approximately `64.8888889 - 64.001 = 0.8878889`; the new tracker height is approximately `64.8888889 - 64 = 0.8888889`. Both are above `0.4`, so neither scales this flow by fluid depth. The tagged source cell passes both samplers' `fluidTop >= deflatedBox.minY` condition.

The source and the same-fluid source immediately below the east neighbor both have own-height `8/9`. In both exact `FlowingFluid.getFlow` methods, the east contribution is `(8/9) - ((8/9) - 0.8888889F) = 0.8888889F`; the other horizontal faces are motion-blocking, so they contribute no gradient. Both implementations normalize the vector to +X (`FlowingFluid.java:55-99` in 1.21.11; `55-100` in 26.1+). With no other WATER-tagged cells in the sampled box, the water tracker has exactly one current sample. Old Player averaging by sample count and new Player averaging by current count both divide by one; both pass their current guards and scale by `0.014`. The `0.0045` minimum-current rule does not modify a `0.014` impulse. Thus when all preflight chunks are present, the water-current contribution is the same +X impulse in the specified single-sample arrangement. When `(1,0)` is absent, the already-verified new preflight skips before sampling and contributes no water impulse; the old receiver gate remains cache-blind.

For the total player delta in the example to consist only of this water impulse, leave other sampled cells without any other current-producing fluid (in particular lava); the r3 statement that other scan cells are free of WATER alone would not exclude a separate lava-current contribution. The East-below source at `(15,63,8)` is outside the scan cells and serves only to create the source's positive flow gradient.

## Retained chunk isolation

At `(14.9,64,8.5)`, both samplers visit X cells `14..15`, Y cells `64..65`, and Z cell `8`. The new one-block X/Z halo is X `13..16`, Z `7..9`, which maps to chunk X `0..1` and chunk Z `0` only. Hence `(0,0)` loaded with `(1,0)` absent isolates that single missing neighbor; the flow sample and all of its finite fluid/block dependencies remain in `(0,0)`. This preserves the geometry verdict in `verdict-r2.md` without reinterpreting the old z-boundary witness.

## Disposition

Bind the prior conditional source-level verdict to the two exact r3 hashes above. The corrected height values preserve the positive current, no-attenuation branch, and equal single-sample `0.014` scaling. This remains static source evidence, not runtime confirmation or a general claim of fluid-current arithmetic equivalence.
