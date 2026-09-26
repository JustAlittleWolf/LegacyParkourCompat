# S5-02: Lava currents push entities in B

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 5, fluid movement inputs; `S5-LAVA-CURRENT`.
- Classification: added behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: `Entity.updateInWaterStateAndDoWaterCurrentPushing()` calls `checkAndHandleWater(FluidTags.WATER)`; base tick has no lava-flow-push call. `checkAndHandleWater` adds averaged fluid flow at strength 0.014. `Entity.java` SHA-256 `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- B: `Entity.updateInWaterStateAndDoFluidPushing()` calls `updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, d)` and the water helper, with `d` equal to 0.007 in ultra-warm dimensions and 0.0023333333333333335 otherwise. The shared fluid helper adds the computed flow when `isPushedByFluid()` is true. `Entity.java` SHA-256 `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666`.

## Source-level difference

B routes lava through the fluid-height/current helper each entity tick; A only routes water through its current helper. A's lava contact flag does not add a lava flow vector.

## Reachability and dependencies

A non-flying player intersecting flowing lava with a nonzero averaged flow vector reaches B's helper and can have its velocity changed. Player flight disables pushing in both versions (`isPushedByWater` in A / `isPushedByFluid` in B).

## Consequence and uncertainty

B adds a dimension-dependent lava-current velocity increment. The flow vector depends on fluid state and bounding-box scan; no trajectory was run. The A client artifact was recovered and SHA-256 verified as `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`; no historical lava-current call exists in its inspected base-tick path.

## Handoff

Independent delta: lava fluid currents can push the player in B. The weak-water-current minimum is cataloged in `S5-01`.
