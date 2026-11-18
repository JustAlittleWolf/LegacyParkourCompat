# Independent verdict r2 — concrete water/lava flow dispatch, 1.20.1 → 1.20.2

## Bound inputs

- Corrected r1 claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-fluid-contact-1201-1202/comparison.corrected-r1.md`, SHA-256 `74afdce1697062534c51aad9b6c8a9efff5d6b8ae8ff5e6519d6315cbf57b488`.
- Prior independent verdict: [`verdict-r1.md`](verdict-r1.md), SHA-256 `bbde444336b0454011182b56e7f30cead66112efce1e8e22addcf894cef5e6b4`.
- Frozen subtype roster: [`subtype-roster.md`](subtype-roster.md), SHA-256 `eb02dfb414b4d2570d3f0f88ecc5de35188a453ab9d01d8b6b5835f4bfa4fc7d`.
- Canonical Mojmap source roots are the same version-root exports bound in the prior roster addendum. Every subtype file listed in `subtype-roster.md` has a local SHA-256 matching its per-release `mojmap.sources.sha256` entry.

## Subtype dispatch verdict

**Closed: ordinary water and lava source/flowing fluid states inherit the shared flow implementation in both releases; no subtype-specific `getFlow` difference is present.**

Paired exact routing:

- `1.20.1` and `1.20.2` `WaterFluid.java:28`: `public abstract class WaterFluid extends FlowingFluid`. Its nested `Flowing` (line 123) and `Source` (line 141) extend `WaterFluid`. The class and both nested classes contain no `getFlow` override. `WaterFluid.isSame` at lines 94–95 recognizes both `Fluids.WATER` and `Fluids.FLOWING_WATER`.
- `1.20.1` and `1.20.2` `LavaFluid.java:27`: `public abstract class LavaFluid extends FlowingFluid`. Its nested `Flowing` (line 225) and `Source` (line 243) extend `LavaFluid`. The class and both nested classes contain no `getFlow` override. `LavaFluid.isSame` at lines 150–151 recognizes both `Fluids.LAVA` and `Fluids.FLOWING_LAVA`.
- In both versions, `Fluids.java:8–11` binds `FLOWING_WATER` to `new WaterFluid.Flowing()`, `WATER` to `new WaterFluid.Source()`, `FLOWING_LAVA` to `new LavaFluid.Flowing()`, and `LAVA` to `new LavaFluid.Source()`. These references are typed as `FlowingFluid` and register the expected source/flowing instances.
- In both versions, `FluidState.java:91–92` routes the state to its concrete type: `return this.getType().getFlow($$0, $$1, this);`. The source/flowing instances therefore dispatch to the inherited `FlowingFluid.getFlow` implementation.
- The already independently compared paired `FlowingFluid.java:54–115` body implements that shared operation. Its neighbor predicate at lines 101–103 delegates same-fluid recognition to `getType().isSame(this)`, which lets the WaterFluid/LavaFluid `isSame` overrides treat their own source and flowing variants alike. The method and its helper expressions remain the same as recorded against the paired source identities in `verdict-r1.md`; this follow-up does not repeat the prior flow-math review.

The `WaterFluid.java`, `LavaFluid.java`, `Fluids.java`, and `FluidState.java` file hashes are identical across 1.20.1 and 1.20.2 and match their source-manifest rows. `FlowingFluid.java` has different whole-file hashes across the pair, but the direct flow method is already independently bound and found unchanged in r1. The concrete subtype edge itself is now closed for this bounded Java slice.

## Remaining dependency

Effective membership in the data-driven `minecraft:water` and `minecraft:lava` tags remains unresolved. This Java subtype review does not establish versioned tag JSON membership or resource applicability.