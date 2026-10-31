# Final bounded review: packet targets

- Final candidate commit reviewed: `f52c4c9d57d06ff89464de71459221062df29eea`
- Verdict: **bounded technical review accepted for candidate handoff**.
- This verdict concerns the two accepted packet-target findings and the reviewed candidate only. It does not accept product integration, establish build success, or prove runtime parity.
- The prior `70d1d9f` review noted a missing `ClientPacketPositionState` import. Commit `f52c4c9` adds that import; the final candidate resolves that static compile issue.

## Review basis

The final candidate maps the old 1.16.1 `ClientPacketListener.handleTeleportEntity` response for tracked entities to current `handleEntityPositionSync`, with the current `ServerEntity.sendChanges` position-sync emission. New current `handleTeleportEntity` behavior remains delegated to the original `setValuesFromPositionPacket` helper; its wrapper only clears the local player's pending legacy interpolation step count. It leaves modern relative flags, position/rotation/velocity helper behavior, and the separate old long packet-coordinate baseline untouched.

The explicit `handlesLegacyPackets` policy resolves false in the `V1_15` native-preservation barrier and true in the `V1_16` historical change. Authority routing and queued interpolation use that policy. The resolver therefore applies the historical hooks to selected 1.15.2 through 1.16.1, the native barrier below that range, and native behavior at 1.16.2 and later.

Exact old source confirms that `LocalPlayer.isLocalPlayer()` is true and `Entity.isControlledByLocalInstance()` is false for a local player in 1.16.1. The final candidate applies that historical classification at the current position-sync and relative-move decisions, and advances queued legacy interpolation independently of current local authority.

The current `handleEntityPositionSync` sets its `VecDeltaCodec` base before the authority decision; the candidate keeps this call intact. Current `handleMoveEntity` still decodes through `VecDeltaCodec` before selecting the legacy target, so modern codec bookkeeping remains. The old position target retains the exact 1/4096 floor baseline and negated threshold comparisons. Relative movement retains the long baseline, packet deltas, scale constant and `Vec3.scale` operation. The queued old lerp retains the double intermediate for yaw and pitch, the corresponding float casts, operation order, and old `setRot` modulo behavior.

The change hooks use `@MovementChange` versions, topic-organized `Hook` interfaces, public no-argument constructors, client-only mixins, and automatic discovery. The lower preservation barrier does not change the current authority branch.

## Evidence and limits

Inspected exact 1.15.2 and 1.16.1 Mojmap sources for `LivingEntity.aiStep`, `LivingEntity.lerpTo`, `ClientPacketListener.handleTeleportEntity`, `handleMoveEntity`, and `Entity.setPacketCoordinates`; inspected 26.2 merged deobfuscated bytecode for player authority, interpolation, position-sync, relative-move, teleport-helper and packet-emission paths. Confirmed current `LocalPlayer` inherits the non-null `LivingEntity.getInterpolation()` handler.

No product edits, source generation, build, tests, game launch, or runtime parity comparison were performed. The candidate is accepted only within the bounded source-fidelity review above.
