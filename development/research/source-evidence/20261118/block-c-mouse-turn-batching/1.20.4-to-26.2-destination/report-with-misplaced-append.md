# Blind destination witness: mouse-turn batching and Elytra pitch

## Verdict

**Source-predicted difference.** In a same-frame pair of ordinary opposite vertical mouse movements, 1.20.4 applies and clamps each callback's turn separately; 26.2 adds the callback displacements and applies one turn after the frame's client ticks. When the first turn reaches the pitch limit, the opposite turn does not undo that clamp in 1.20.4. Thus the next movement tick can start fall-flying movement with a different authoritative `getXRot()` pitch. This is a mouse-input timing/aggregation effect; the separate `setXRot` modulo change does not cause the example below.

## Exact source identity and integrity

- 1.20.4 Mojmap source set: ready marker says `status=ready`, `versionId=1.20.4`, `mapping=mojmap`, 5,048 source files. The ready marker's source manifest digest is `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; the sibling `mojmap.sources.sha256` hashes to that digest. Its artifact-manifest digest is `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`, matching the sibling file.
- 26.2 unobfuscated source set: ready marker says `status=ready`, `versionId=26.2`, `mapping=unobfuscated`, 7,055 source files. The ready marker's source manifest digest is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; the sibling `unobfuscated.sources.sha256` hashes to the marker's value `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Its artifact-manifest digest is `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`, matching the sibling file.
- The cited Java bodies match their source-manifest SHA-256 entries. Paths below are relative to each canonical source root.

## Trace and comparison

**Event dispatch and gates.** In both releases, `MouseHandler.setup` registers the GLFW cursor-position callback by scheduling `onMove` through `minecraft.execute` (1.20.4 `MouseHandler.java:169-184`; 26.2 `MouseHandler.java:235-261`). The initial `ignoreFirstMove` event only resets the cursor baseline. For later events, ordinary gameplay requires the window to be active and mouse grabbed; 1.20.4 also gates accumulation on both. Screen handling is immaterial to the player turn in the scenario.

**1.20.4, per-event apply.** `MouseHandler.onMove` adds `(new position - previous position)` to `accumulatedDX/DY`, immediately calls `turnPlayer`, then stores the new position (`MouseHandler.java:187-220`). `turnPlayer` calculates the sensitivity-scaled delta from the accumulator, clears the accumulator, applies Y inversion, and calls `player.turn` (`MouseHandler.java:224-265`). Therefore each queued cursor callback becomes its own turn operation. In the frame loop, scheduled tasks are drained before the client tick loop, and a separate `turnPlayer` call occurs after ticks and before render (`Minecraft.java:1155-1175`). With both callbacks queued before the movement tick, both individual turns have already changed current pitch when that tick begins.

**26.2, per-frame batch.** `MouseHandler.onMove` only accumulates raw position deltas when the window is active, updates the position, and does not turn the player (`MouseHandler.java:263-279`). `Minecraft` calls `handleAccumulatedMovement` after the frame's tick loop (`Minecraft.java:1180-1202`). That method, while the window is active, delivers mouse/screen events and calls `turnPlayer` only when the mouse is grabbed and a player exists; it then resets both accumulators (`MouseHandler.java:281-332`). `turnPlayer` scales the combined delta, applies configured X/Y inversion and calls `player.turn` once (`MouseHandler.java:350-377`). The resulting pitch is therefore available to the next client movement tick, rather than the ticks already completed in that frame.

**Turn operation and clamp.** Both `Entity.turn` implementations cast the pitch input to `float`, multiply by `0.15F`, add it to current pitch with `setXRot`, then explicitly clamp current pitch to `[-90, 90]`; the operation order relevant here is the same (1.20.4 `Entity.java:398-406`; 26.2 `Entity.java:489-497`).

For a concrete ordinary-input witness, start at current pitch `85°`, keep sensitivity, smooth-camera state and inversion fixed, establish the cursor baseline, and deliver same-frame vertical cursor displacements `+100` then `-100` pixels. With smooth camera off, no scoped first-person branch, and sensitivity `0.5`, their turn magnitudes are about `15°` each. In 1.20.4 the first event raises pitch above `90°` and clamps it to `90°`; the second applies the opposite turn from that clamped state, leaving pitch about `75°`. In 26.2 the accumulated vertical displacement is zero, so its one batched turn leaves pitch at `85°`. The pair is finite and ordinary; each unclamped `setXRot` argument stays within `[-360°,360°]`.

**Separate modulo control.** 1.20.4 `Entity.setXRot` validates finiteness and assigns the supplied value (`Entity.java:3309-3315`). 26.2 validates finiteness, applies `% 360.0F`, and clamps to `[-90°,90°]` (`Entity.java:3910-3916`). For each `Entity.turn` input in this witness, the pre-clamp pitch argument is inside `[-360°,360°]`, so modulo leaves it unchanged. The current setter's first clamp and the explicit following clamp in `turn` produce the same result as the old raw assignment followed by that explicit clamp. The state difference comes from two separately clamped turns versus one zero-net batched turn, not modulo.

**Next movement consumer.** The active fall-flying branch in 1.20.4 `LivingEntity.travel` reads `getLookAngle()` and current `getXRot()` to compute the pitch angle, lift term, downward conversion and dive term (`LivingEntity.java:2035-2118`, especially `2099-2117`). In 26.2, `LivingEntity.travel` dispatches fall flying to `travelFallFlying` (`LivingEntity.java:2430-2434`); `updateFallFlyingMovement` reads both current `getLookAngle()` and current `getXRot()` (`LivingEntity.java:2602-2626`). These are authoritative current entity values, not render-interpolated rotations. Thus on the next movement tick after the callbacks, the source-predicted pitch difference feeds directly into active Elytra velocity computation. The exact velocity outcome is not runtime-verified here.

## Source file SHA-256

| Release / source member | SHA-256 |
| --- | --- |
| 1.20.4 `net/minecraft/client/MouseHandler.java` | `a75d99f45510c6689ddd7b241b28033b07ccbedc59e8a4288fc15d367bcc0b56` |
| 1.20.4 `net/minecraft/client/Minecraft.java` | `4c672a48906a76408bfbf77fda303d6cead7b22c6e4632aed7a5d8182e057b0a` |
| 1.20.4 `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.4 `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 26.2 `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |
| 26.2 `net/minecraft/client/Minecraft.java` | `c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0` |
| 26.2 `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
## Signed independent comparison: ordinary clamp witness and modulo witness

I independently audited this bounded slice as `/root/c_mouse_turn_batching_1204_262_destination_audit`; my frozen verdict is `c-mouse-turn-batching-1204-262-destination-audit/verdict.md`, SHA-256 `14f56153aa9a960abecacbe0367f178323453c6622c9646c33733a32890aba7e`. The witness already in this report and the frozen verdict's witness are compatible, but demonstrate separate parts of the behavior:

- **Small ordinary two-delta witness, isolating scheduling and clamp:** at sensitivity 0.5 the cursor scale is 1 and each ±100 px event produces ±15° pitch. From 85°, 1.20.4 clamps the first 100° turn to 90°, then the opposite turn leaves 75°. 26.2 batches the displacements to zero, preserving 85°. These setter inputs are well inside ±360°; modulo does not explain this result. The active movement tick sees 75° on the 1.20.4 per-event path versus 85° on the 26.2 zero-net batch path.
- **Separate finite modulo-control witness:** at sensitivity 1.0, starting at 0° and delivering `+700 px` then `−100 px` in the same event batch, the turn scale is approximately 0.6144° per pixel. 1.20.4 separately clamps the first approximately +430.08° event to +90°; the following approximately −61.44° event leaves approximately +28.56° for the next local travel tick. 26.2's local tick(s) in that run happen before the accumulated mouse turn and therefore consume the previous 0° pitch. After those ticks, the single +600 px batch attempts approximately +368.64°; 26.2 `setXRot` applies `% 360.0F`, leaving approximately +8.64° for the first local travel tick in a later run. Here modulo is material to the post-batch pitch: without it the explicit clamp would produce +90°. This larger cursor delta is finite and legal; it is specifically chosen to cross 360° and control for modulo.

Thus the +100/−100 report witness is the cleaner ordinary demonstration of per-event clamp versus batching; the +700/−100 audit witness independently establishes the setter's modulo effect at the modulo boundary. Neither claim relies on renderer interpolation, and neither supplies a runtime trajectory result. Signed: `/root/c_mouse_turn_batching_1204_262_destination_audit`.
