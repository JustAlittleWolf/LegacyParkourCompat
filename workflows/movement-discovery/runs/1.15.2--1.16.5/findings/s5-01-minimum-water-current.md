# S5-01: Weak water currents receive a minimum horizontal push

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 5, fluid movement inputs; `S5-WATER-CURRENT-MINIMUM`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: `Entity.checkAndHandleWater(Tag)` lines 2600–2666 averages flow and adds `vec3.scale(0.014)` when nonzero. Source SHA-256 `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- B: `Entity.updateFluidHeightAndDoFluidPushing(Tag,double)` lines 2641–2697 scales by the supplied strength, then raises flow to `0.0045000000000000005` when both horizontal velocity components are below `0.003` and the computed flow is weaker. The water caller passes `0.014` at lines 965–979. Source SHA-256 `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666`.

## Source-level difference

The shared scan, average, player normalization exception, and water strength remain. B adds the minimum-vector-length branch conditioned on low horizontal velocity; A has no corresponding adjustment.

## Reachability and dependencies

Entity base tick invokes the water current update. A non-flying player in flowing water with nonzero averaged flow, both horizontal velocity components under 0.003, and a post-strength vector under the minimum reaches B's boost. Players do not normalize the average flow in either version, and ability flight suppresses fluid push in both versions.

## Consequence and uncertainty

B may apply a larger horizontal velocity increment than A in the specified weak-current state. The exact average depends on surrounding fluid states and bounding-box scan; no trajectory was run.

## Handoff

Independent delta: minimum water-current push at low horizontal speed. Lava current addition is separately cataloged in `S5-02`.
