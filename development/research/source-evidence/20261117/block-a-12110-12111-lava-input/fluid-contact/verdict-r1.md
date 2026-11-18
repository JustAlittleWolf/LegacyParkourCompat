# Scoped re-review: lava-current input, 1.21.10 → 1.21.11

## Inputs checked

I verified both authorized notes as strict UTF-8 without BOM or CR bytes and matched their declared SHA-256 hashes:

- `comparison.corrected-r1.md`: `f32f527933fa36746f4289064d1e51d58f7e39aca66c14ce404ffabb46c33a27`
- `lava-current-input.md`: `de0ec1cbe40f9416c3a0614cae2ed1154b36c3539b7e0c39528feec9afcf6990`

Their exact Mojmap roots and complete source manifests were already frozen and verified for 1.21.10 and 1.21.11. I checked the scoped methods and registry/default source passages against those canonical files.

## Finding: distinct data inputs, matching built-in defaults

The compared code confirms a real input substitution in `Entity.updateInWaterStateAndDoFluidPushing`:

- **1.21.10:** `dimensionType().ultraWarm() ? 0.007 : 0.0023333333333333335` (`Entity.java:1483–1488`). The old `DimensionType` has an `ultraWarm` record component, and its codec requires the `ultrawarm` Boolean field (`DimensionType.java:25–40, 50–68`).
- **1.21.11:** `environmentAttributes().getDimensionValue(EnvironmentAttributes.FAST_LAVA) ? 0.007 : 0.0023333333333333335` (`Entity.java:1501–1506`). The new `DimensionType` removes `ultraWarm` and decodes an optional `attributes` map with `EnvironmentAttributeMap.EMPTY` as its default (`DimensionType.java:26–40, 77–100`). `FAST_LAVA` is `minecraft:gameplay/fast_lava`, a Boolean whose declared default is false (`EnvironmentAttributes.java:126–128`). `EnvironmentAttributeSystem` supplies the dimension attribute layer and falls back to the attribute default when there is no sampler (`EnvironmentAttributeSystem.java:55–69, 95–103`). Thus an omitted new attribute is false by source contract; it is not implicitly the old `ultrawarm` value.

The **three requested built-in dimension values are closed and agree at bootstrap-source level**:

| Dimension type key | 1.21.10 `ultraWarm` | 1.21.11 `FAST_LAVA` | Exact bootstrap evidence |
| --- | --- | --- | --- |
| `minecraft:overworld` | `false` | `false` (attribute omitted; default false) | Old `DimensionTypes.java:13–33`; new `DimensionTypes.java:26–54` |
| `minecraft:the_nether` | `true` | `true` (explicit) | Old `DimensionTypes.java:34–54`; new `DimensionTypes.java:56–88` |
| `minecraft:the_end` | `false` | `false` (attribute omitted; default false) | Old `DimensionTypes.java:55–75`; new `DimensionTypes.java:89–116` |

Both releases' `BuiltinDimensionTypes.java:8–10` register these keys in `Registries.DIMENSION_TYPE` using the default namespace (`ResourceLocation` in 1.21.10 and `Identifier` in 1.21.11). These are canonical registry identities, not inferred names. The compared publication contains decompiled Java source, not the actual dimension JSON payloads; the checked values are the built-in bootstrap defaults. No claim is made about a particular datapack's serialized resource values.

## Player-current meaning and remaining gate

For a non-flying, on-foot `Player`, `isPushedByFluid()` is true (`Player.java:1603–1606` in 1.21.10; `1678–1681` in 1.21.11). If the loaded lava scan finds a nonzero contributing current vector, its unchanged body averages the vector, scales it by the passed coefficient, can apply the `0.0045000000000000005` minimum-current adjustment, and adds the result to player delta movement. Holding fluid states and the averaged vector fixed, differing input Booleans select coefficients `0.007` and `0.0023333333333333335`. Before minimum-current adjustment their scaled vectors differ by a factor of three; if both paths clamp, final added impulses can instead match. The source change is therefore a direct but conditional player-current behavior change when the dimension inputs differ and flowing lava contributes.

Built-in agreement does **not** prove equivalence for world-defined dimensions or a target parkour map. No exact old/new map resource or persisted-world input was provided here. Legacy-field migration, loading behavior, and whether the current mod can represent or preserve a target value remain separately owned and **unassessed**; this review does not close those gates. Do not infer custom-world mismatch or equivalence without those exact inputs.

## Scoped verdict

Uphold the corrected report's scoped classification: input substitution is source-confirmed; built-in Overworld, Nether and End values match; a real-map movement difference remains unresolved pending the exact old/new world data. The earlier broad fluid-scan and travel review remains in the preserved `verdict.md` and is not reopened by this scoped re-review.