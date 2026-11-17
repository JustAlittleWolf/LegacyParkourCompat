# Independent audit: water/lava movement property providers

**Verdict: ACCEPT** for the bounded provider-side source and packaged fluid-tag claims in the report below. This verdict is not a pair freeze, client fluid-travel equivalence, datapack/runtime registry claim, or runtime parity result.

## Immutable report reviewed

- Path: `build/later-source-20261116/1.20.2--1.20.4/fluid_property_providers/coverage.md`
- SHA-256: `2A23AC21ABF38C2A27284B7C8FC862F8847BD6D5E1BC76BB6E5F76F22987FC22`

## Identity and evidence audit

Verified both endpoint ready marker, provenance, source-manifest, and artifact-manifest hashes against the report. Each ready marker declares `status=ready`, exact `versionId` 1.20.2 or 1.20.4, `mapping=mojmap`, and source/artifact manifest SHA-256 bindings that match the files at the version root. Provenance files declare the corresponding exact version and Mojmap namespace. All seven equal-hash provider Java files and the differing `LiquidBlock.java` and `Blocks.java` files match the cited on-disk SHA-256 values and have corresponding source-manifest entries.

The fluid contract, state delegation, water/lava providers, built-in fluid registration, and tag-key files are byte-identical at both endpoints, as reported. `FlowingFluid.getFlow` uses the same horizontal neighbor-height accumulation, falling-side-wall check and `-6.0` adjustment, followed by normalization. `getHeight`/`getOwnHeight` retain the same source/amount-dependent rules. The paired WaterFluid and LavaFluid implementations, including source/flowing amount and source predicates, replacement/tag predicates and dimension-selected spread constants, are identical. `FluidTags` creates default-namespace `water` and `lava` fluid keys, which correspond to the resource locations reported.

`LiquidBlock.java` differs only by the 1.20.4 codec imports/declaration/method in the inspected source. The cache constructor still creates source, levels 1–7 and falling level 8 in the same order; `getFluidState` still selects the cached entry at `min(LEVEL, 8)`. The `Blocks.WATER` and `Blocks.LAVA` registration excerpts match exactly across endpoints, and bind to the same source/lava types and relevant properties. The full `Blocks.java` files differ elsewhere, which the report states.

The cited isolated `input-reference.json` hash and independent resource-audit hash both match. The four per-endpoint water/lava tag payload files match the claimed byte counts, SHA-256 values and parsed members (`minecraft:water`/`minecraft:flowing_water`; `minecraft:lava`/`minecraft:flowing_lava`). The referenced audit is ACCEPT and specifically ties these payloads to the pinned artifact JAR entries, while preserving its stated limitation that the downloaded local JAR paths are not provenance-recorded paths.

## Scope limits

The report properly limits itself to provider-side Java operations and the named packaged fluid tags. It does not claim fluid-travel/currents application behavior, runtime registry or datapack equivalence, intermediate-version onset, all fluid collision shapes, or complete pair coverage. No in-scope omission or evidence correction was found.
