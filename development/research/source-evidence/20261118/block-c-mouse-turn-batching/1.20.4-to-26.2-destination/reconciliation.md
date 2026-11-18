# Signed bounded reconciliation: callback clamp witness

## Evidence identities and correction

- Frozen independent audit: `verdict.md`, SHA-256 `14f56153aa9a960abecacbe0367f178323453c6622c9646c33733a32890aba7e`.
- Original author witness report identity supplied for this reconciliation: SHA-256 `2602924a7855d0e9c4fe31e94c4d32b5bae1c6f60620a4db1b98a1f3b75af85b`.
- Actual post-append artifact identity: SHA-256 `ce49122741f763667eaea9c539855485097fd7b430d227bba8ed2ed2157cf9ad`.

I mistakenly appended the comparison to the author's existing `report.md` instead of creating a new file in my own audit scratch. The post-append hash above identifies that artifact as a trace of the misplaced append; this file records the intended signed comparison. The author's separate byte-prefix recovery remains authoritative for that report. My frozen `verdict.md` has not been changed.

## Bounded source comparison and verdict

**Signed acceptance: ACCEPT for the scoped player-movement consequence only.** The 1.20.4 Mojmap and 26.2 unobfuscated source pair shows different scheduling of ordinary mouse deltas before local player travel. In 1.20.4, `MouseHandler.onMove` adds the callback displacement and immediately calls `turnPlayer` (`MouseHandler.java:187–220`, `224–265`); `Minecraft.runTick` drains scheduled tasks before its client-tick loop (`Minecraft.java:1155–1175`). In 26.2, `MouseHandler.onMove` accumulates without turning (`MouseHandler.java:263–279`), and `Minecraft.runTick` calls `handleAccumulatedMovement` after the tick loop (`Minecraft.java:1164–1202`), where one combined turn is applied (`MouseHandler.java:281–332`, `350–377`).

For a reachable routine witness, use an active local player in fall flying; mouse grabbed and window active; baseline event already passed; sensitivity `0.5`; smooth camera off; invert Y off; no scoped first-person sensitivity branch; initial live pitch `85°`; same-frame screen-Y deltas `+100 px`, then `−100 px`, before the relevant movement tick. At that setting each event turns pitch by about `15°`. In 1.20.4 `Entity.turn` applies the first turn and clamps pitch to `90°`; the second opposite event starts from that clamped live pitch and leaves about `75°` (`Entity.java:398–406`). In 26.2 the summed delta is zero, so its batched turn leaves `85°` (`Entity.java:489–497`). The two input values are finite and ordinary.

Modulo is not causal in this routine witness: the `setXRot` argument values are about `100°`, `75°`, and `85°`, far inside a full revolution. 1.20.4's setter assigns a finite value (`Entity.java:3309–3314`); 26.2's setter applies `% 360.0F` and then clamps (`Entity.java:3910–3916`), which does not alter these values before the following explicit clamp in `turn`. The separate larger witness recorded in the frozen audit exercises the modulo branch; it is not needed to accept this bounded per-event-clamp versus zero-net-batch claim.

The local fall-flying path reads live `getXRot()` and `getLookAngle()` in the 1.20.4 `LivingEntity.travel` branch (`LivingEntity.java:2035–2044`, `2099–2125`) and in 26.2 `LivingEntity.updateFallFlyingMovement` (`LivingEntity.java:2602–2627`), reached through `LivingEntity.travel` (`2430–2438`). Thus the next applicable movement tick can consume the different current pitch values. Renderer interpolation is not the operand at issue.

Limits: this is a source-predicted, fixed-profile player-movement difference, not a runtime trajectory result. The timing claim assumes the ordinary GLFW/client-thread path; if cursor callbacks are dispatched from another thread or interleaved with ticks, that scheduling path must be separately traced. The endpoint comparison does not identify an intermediate version boundary. Input-device plumbing is not accepted as an independent mechanic, and no broader tick-graph coverage or runtime parity is claimed.

Signed: `/root/c_mouse_turn_batching_1204_262_destination_audit`.
