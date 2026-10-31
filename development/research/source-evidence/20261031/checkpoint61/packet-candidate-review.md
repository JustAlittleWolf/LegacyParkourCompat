# Candidate review 59 — packet movement

## Verdict

**REJECT pending correction**, bound to sanitized candidate commit `766e7f6d7bac60163e4a9c946c9514f5da1007fd` (`Map legacy teleport response to position sync`), with neutral baseline `8d4c781c`.

The final commit makes the intended current-handler correction: it maps the old absolute tracking response to `ClientPacketListener.handleEntityPositionSync` / `ClientboundEntityPositionSyncPacket`, sourced from the current `ServerEntity.sendChanges` path, and leaves the modern relative/velocity `handleTeleportEntity` method uncanceled. The receiver and tick changes are scoped to `LocalPlayer` that is exactly `Minecraft.getInstance().player`; there is no global authority predicate override.

## Source-supported finding domain

- **Relative position reconstruction:** 1.19, 1.19.1 and 1.19.2 use floor-based `VecDeltaCodec` reconstruction; 1.19.3 changes to rounded base quantization. For a nonzero axis, the candidate computes `(Mth.lfloor(base * 4096.0) + delta) / 4096.0`; a zero-delta axis preserves the unquantized base. It updates the codec base after decoding, as the old handler does. Exact source acceptance is limited to the 1.19.2→1.19.3 decoder change; 1.18.2→1.19 remains source-equivalent per the supplied audit.
- **LocalPlayer packet authority/interpolation:** 1.19.3's client-side LocalPlayer predicate is false, so the negated receive guards accept move/teleport responses; 1.19.4 changes the fallback to `isEffectiveAi`, which is true for LocalPlayer, and `LivingEntity.aiStep` clears the pending interpolation. The candidate implements the receive response and three-step tick interpolation without changing a global authority predicate. `Player.isLocalClientAuthoritative()` delegates to `isLocalPlayer()`, LocalPlayer returns true, and LivingEntity supplies a non-null interpolation handler in the 26.2 source.
- The `aiStep` injection point matches the 26.2 call `LivingEntity.aiStep -> Entity.isInterpolating()Z`; `ClientPacketListener` source declares `handleMoveEntity(ClientboundMoveEntityPacket)` and `handleEntityPositionSync(ClientboundEntityPositionSyncPacket)`. The old 1.19.3 lerp order is reproduced: interpolate XYZ by remaining steps, wrap yaw delta, update yaw/pitch, decrement, then `setPos` and `setRot`.

## Blockers

1. **Absolute packet rotations are not quantized to the old wire representation.** In 1.19.3, `ClientboundTeleportEntityPacket(Entity)` stores yaw and pitch as `(byte)(rotation * 256.0F / 360.0F)`; its receive handler reconstructs each angle with `byte * 360 / 256.0F` before `lerpTo`. In 26.2, `ClientboundEntityPositionSyncPacket.of(Entity)` puts the raw float rotations into `PositionMoveRotation`. The candidate passes `target.yRot()` / `target.xRot()` directly into the three-step legacy interpolation state. This skips the old byte truncation and unpacking, so absolute tracked corrections do not reproduce 1.19.3 rotation steps for angles that are not exactly representable by the byte grid. Quantize with the old constructor's cast semantics and old unpack expression before storing the target. Evidence: 1.19.3 Mojmap `ClientboundTeleportEntityPacket` constructor and `ClientPacketListener.handleTeleportEntity`; 26.2 unobfuscated `ClientboundEntityPositionSyncPacket.of`; candidate `ClientPacketListenerMovementMixin.java:91-92`.
2. **Pending legacy interpolation overlaps native modern relative teleports.** `lpc$legacyLerpSteps` is only replaced by another intercepted legacy response and decremented to zero in `aiStep`. The native 26.2 `handleTeleportEntity` remains untouched as required, but its relative-position path can start `InterpolationHandler` interpolation; the candidate's `aiStep` injection then applies the pending legacy position first and the native interpolation immediately afterward. The modern-only response therefore is not preserved independently when it arrives during the three-step legacy window. Add a narrowly scoped lifecycle rule so a native modern-only correction supersedes or safely reconciles the pending legacy target while leaving the native handler active. Evidence: candidate `LivingEntityPacketInterpolationMixin.java` and `ClientPacketListenerMovementMixin.java`; 26.2 `ClientPacketListener.handleTeleportEntity`, `Entity.moveOrInterpolateTo`, and `LivingEntity.aiStep`.

## Integration seams and limits

- Keep `handleTeleportEntity` native; historical absolute tracking corrections use `handleEntityPositionSync` in this target runtime.
- Keep the compat behavior on the actual local client `LocalPlayer` instance and packet/tick route only. Do not move this policy into `Entity.isLocalInstanceAuthoritative` or other global authority methods.
- The v1.18.2 lower barriers intentionally preserve the candidate's earlier partial-coverage policy; this review makes no parity claim for those earlier profiles. The v1.19 decoder and v1.19.3 authority entries have no redundant upper barrier.
- The review is source-only. No build, tests, game launch, decompilation, product checkout, or candidate edits were performed. The result is source evidence, not runtime parity certification.

## Evidence inputs

- Independent pair audit: `.native-source/20261031/1182-1194-finish50/independent_full_pair_audit/audit.md`, SHA-256 `38ab3b0702ad6a47a5a1a38493d2a842f65caa7cadc0060d802ced9a6641d9bb`.
- Alias review: `.native-source/20261031/packet-authority-alias57/review.md`, SHA-256 `79a1a40b6d1bd8b1d67c5d36b2fe9cbf797a0e53cec67947197064aee1415ebd`.

## Renewed review — final commit `df7aed6e84a7e576eebf26844da807da0c2c521e`

This verdict supersedes the prior rejection above and is bound to the exact candidate commit `df7aed6e84a7e576eebf26844da807da0c2c521e` (`Preserve tracked packet angles and supersession`) over neutral baseline `8d4c781c`.

**ACCEPT for the two independently accepted source operations in the stated domain.** The full final diff contains only the previously reviewed packet compatibility implementation plus the following verified corrections:

1. For the absolute tracked-response mapping, the candidate now recreates the 1.19.3 wire angle path exactly: cast `rotation * 256.0F / 360.0F` to `byte`, then compute `byte * 360 / 256.0F`. 1.19.3 `ClientboundTeleportEntityPacket(Entity)` and `handleTeleportEntity` perform those exact operations. 26.2 `ClientboundEntityPositionSyncPacket.of(Entity)` carries raw `float` angles, so the candidate's conversion is necessary and now preserves old truncation, signed-byte interpretation, and float operation order before queuing three interpolation steps.
2. Native current `ClientPacketListener.handleTeleportEntity` remains noncanceled. The new injection runs after the exact `PacketUtils.ensureRunningOnSameThread(Packet, PacketListener, PacketProcessor)` call and only zeroes the pending compatibility countdown when the addressed entity is a `LocalPlayer` identical to `Minecraft.getInstance().player` with the authority hook enabled. The handler then continues natively and can establish its relative/velocity interpolation. This prevents the legacy target from being applied first on the same `LivingEntity.aiStep` where the native `InterpolationHandler` runs. The exact current source has one matching ensure call in `handleTeleportEntity`; the target owner and erased descriptor match the source declaration and invocation.

The remaining audited behavior is sound within scope: `handleMoveEntity` keeps the old zero-axis codec-base retention and floor arithmetic for the v1.19 decoder variant, then writes the resulting base; both packet paths queue three steps; the `aiStep` injection is immediately before the current `Entity.isInterpolating()Z` call, matching the old `LivingEntity.aiStep` order (XYZ fraction, wrapped yaw, pitch fraction, decrement, `setPos`, then `setRot`). Packet receive and tick behavior are local-client-only and use actual player identity; there is no global authority override. The lower v1.18.2 barriers and lack of upper barriers retain the candidate's partial-coverage policy and make no earlier-profile parity claim.

The `handleTeleportEntity` cleanup is intentionally noncancelling and occurs only after the packet thread guard succeeds, so off-thread packets are rescheduled before this state mutation. Non-player and vehicle paths remain native. The cleanup concerns only direct eligible LocalPlayer corrections; vehicle behavior is outside this review's assigned domain.

This is an immutable source review, not runtime parity evidence. No product checkout or history was read; no candidate edits, build, tests, game launch, or decompilation were performed. The source audit and alias-review inputs remain the same immutable hashes listed above.
