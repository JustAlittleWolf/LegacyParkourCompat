# Independent product review: received-packet targets

- Exact target: `.native-integrations/packet-targets50` commit `c7c99e98cd44ea0c38775350a8d4cef95c27a2ac`, based on `557eb9cb`.
- Verdict: **bounded technical review accepted for handoff**.
- Scope: accepted 1.16.1-to-1.16.2 teleport-near-coordinate fallback and relative-move zero-delta coordinate-retention findings. This does not establish build success or runtime parity.

## Review findings

- Confirmed resolver direction and boundary: `ChangeResolver` chooses the closest `emulates >= selected` per hook. The `V1_15` native barrier covers 1.15 and 1.15.1 (the `ParkourVersion.V1_15` patch group); `V1_15_2` and `V1_16` resolve the historical hooks to `V1_16`; `V1_16_2` and later do not see them. No upper-range leak found.
- Confirmed actual packet wiring in 26.2 source: `handleEntityPositionSync` sets the vanilla `VecDeltaCodec` baseline, checks authority, then calls `moveOrInterpolateTo` or `snapTo`; the mixin preserves the codec call and changes only the LocalPlayer decision/target when resolved hooks are present. `handleMoveEntity` decodes the codec and updates its baseline before dispatch; the mixin captures packet deltas, selects the historical long baseline target, and queues only when the old packet path is active. Missing hooks and non-LocalPlayer entities use native calls.
- Confirmed rotation-only relative packets still queue the current position with the packet rotations, matching old `lerpTo(current xyz, rotations, 3, false)`; position-bearing zero-delta axes retain their old long coordinates rather than inheriting codec-decoded modern coordinates.
- Confirmed the per-object state is attached only to `LocalPlayer`; long coordinates and queued target/step values are independent instance fields. Temporary delta/tick context is thread-local and cleared at method entry/exit. Current `LivingEntity.aiStep` interpolation is intercepted at its existing `isInterpolating` / `InterpolationHandler.interpolate` seam; the hook advances the old double coordinate and yaw/pitch intermediates, float casts, decrement-before-position ordering, direct rotation writes, and final modulo-only old `setRot` behavior.
- Confirmed the new `ClientboundTeleportEntityPacket` path retains `setValuesFromPositionPacket` native behavior and only cancels a pending historical queue. The invoker matches the exact private static helper signature. Its existing `ClientPacketListenerMixin` wrapper applies to `handleMovePlayer` and the helper body, so it does not duplicate or conflict with the new `handleTeleportEntity` redirect. The new packet mixin descriptors match the inspected 26.2 methods and invocation descriptors. All four mixins are in the client mixin list; no server-side wiring was added.
- Source comments identify compared exact Mojmap releases and current 26.2 unobfuscated targets. No overlap with existing client correction hooks was found.

## Evidence and limits

Inspected exact 1.15.2 and 1.16.1 Mojmap `ClientPacketListener.handleTeleportEntity`, `handleMoveEntity`, `LivingEntity.aiStep`, `LivingEntity.lerpTo`, `Entity.setRot`, and exact 26.2 unobfuscated `ClientPacketListener.handleEntityPositionSync`, `handleMoveEntity`, `handleTeleportEntity`, `LivingEntity.aiStep/getInterpolation`, and `Entity` authority methods. Also checked resolver implementation, `ParkourVersion` patch grouping, mixin configuration, and pre-existing packet-listener mixins.

No source was changed in the product checkout. No build, tests, game launch, or runtime parity check was performed.
