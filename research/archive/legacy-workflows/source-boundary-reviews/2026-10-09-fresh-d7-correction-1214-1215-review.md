# Independent bounded source review: corrected 1.21.4–1.21.5 D7 input path

- Review date: 2026-10-09
- Decision: **ACCEPT — corrected bounded D7 evidence**
- Reviewed source record: `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/run.md`, correction introduced by commit `930e4ed490ef41a98b0527183e9b3b4ad9e0d339`.
- Prior verdict preserved: `REQUEST CHANGES` commit `759a5c441cf7b052a88d020f6f26f8f30832e581` is retained as history. The earlier bounded acceptance of checkpoint `b8c502b89d08df1436b16e5d98e156440a7bad40` is unchanged.
- Scope: the corrected D7 controlled-camera input-to-relative-movement path and its producer/consumer dependencies. D9/F-09 adjudication is assigned separately and was not reviewed here. The separate D8 addition is outside this verdict.
- Pair status remains `partial`; this is not a pair-wide audit or freeze.

## Verification

The canonical 1.21.4 and 1.21.5 Mojmap readiness-marker, source-manifest, artifact-manifest, and diagnostics hashes match the source run. Every source file cited by the corrected D7 evidence was rehashed against its corresponding source manifest. The cited paired ranges match the live source bytes.

A’s `LivingEntity#aiStep()` first handles immobility, then calls `serverAiStep()` only through its effective-AI branch (`LivingEntity.java:2727–2735`). A’s `LocalPlayer#isEffectiveAi()` override returns `true` (`LocalPlayer.java:482–485`). Thus a non-immobile local player reaches the dynamically dispatched `LocalPlayer#serverAiStep()`, which copies sampled axes and jump state only when `isControlledCamera()` is true (`LocalPlayer.java:607–622`). The correction supplies the reachability edge that was missing from the prior record.

B’s `LivingEntity#aiStep()` calls `applyInput()` before its immobility and later server-AI checks (`LivingEntity.java:2712–2721`). `LocalPlayer#applyInput()` gates the controlled-camera path and sends the input vector through `modifyInput()` (`LocalPlayer.java:608–621`, `660–662`). The helper applies `0.98F`, then eligible item-use and sneaking-speed factors, then square-movement correction (`LocalPlayer.java:623–658`). Its non-controlled-camera branch calls the base `LivingEntity#applyInput()`, which applies `0.98F` (`LivingEntity.java:2806–2809`). This accurately records the moved operation and does not claim the changed order is behaviorally interchangeable.

The correction also closes the called-helper evidence for the bounded direct consumer. A/B `Vec2#scale(float)` multiplies each float component by the supplied float (`Vec2.java:22–24` / `28–30`). `Entity#moveRelative()` calls `getInputVector()` and adds its result to delta movement (A `Entity.java:1424–1427`; B `1453–1456`). The paired `getInputVector()` bodies are A `Entity.java:1429–1439` and B `1458–1468`. Their `Vec3#normalize()`, `scale(double)`, and `multiply(double,double,double)` dependencies are A/B `Vec3.java:83–86`, `152–154`, and `164–166`; complete `Vec3.java` SHA-256 is `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` on each side. The paired direct-consumer math and helper operations match. The already recorded `LivingEntity#travelInAir()` call into `moveRelative()` remains a bounded caller example; broader travel branches stay open elsewhere in the pair.

The producer-to-consumer sequence is now supported across the existing fresh input row and this correction: `KeyboardInput#tick()` samples the seven key predicates into `Input` and axes/vector; `LocalPlayer#aiStep()` samples that input; A transfers axes through `serverAiStep()` after the resolved effective-AI and camera gates, while B transfers through `applyInput()` before immobility handling; `LivingEntity` forms the travel vector; `Entity#moveRelative()` consumes it through `getInputVector()` and updates delta movement. No additional missed route was found within this bounded D7 path.

## Dependencies and next action

- D7 is accepted for the bounded controlled-camera input → axes → direct relative-movement path only.
- D9 remains open for the separate reviewer assigned to inspect the immutable F-09 discrepancy. This review neither opens the F-09 artifact nor adjudicates that finding.
- D1–D6 remain open for broader movement coverage. D10 remains open for key-map lifecycle routes. The D8 addition was not evaluated here.
- Next review action: keep D9 independent and proceed with the explicitly queued D10 producer slice when assigned. Do not infer pair completion from this D7 acceptance.
