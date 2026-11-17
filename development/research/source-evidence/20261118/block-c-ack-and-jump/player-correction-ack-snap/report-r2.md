# Blind player correction comparison, revision 2

Terminal: **COMPLETED**

## Exact source identity

The canonical inputs are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap` (1.21.11, Mojmap) and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated` (26.1.2, published unobfuscated).

- `1.21.11/mojmap.sources.sha256` SHA-256: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Adjacent `mojmap.ready.json` embeds the same digest; its SHA-256 is `0ad98d8ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`. `mojmap.provenance.json` records version `1.21.11`, namespace `mojmap`. The independent audit verified all 6,622 listed source files against the manifest.
- `26.1.2/unobfuscated.sources.sha256` SHA-256: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Adjacent `unobfuscated.ready.json` embeds the same digest; its SHA-256 is `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`. `unobfuscated.provenance.json` records version `26.1.2`, namespace `unobfuscated`. The independent audit verified all 6,882 listed source files against the manifest.

The earlier report incorrectly said the canonical trees lacked provenance files. The manifest and provenance sidecars are adjacent to each version/mapping source directory; the ready markers bind the exact manifest digests. That earlier claim is corrected here.

## Bounded comparison

The ordinary direct-player correction packet still carries its teleport ID, `PositionMoveRotation` and relative set. `PositionMoveRotation.calculateAbsolute` retains its relative position/rotation/delta operations, float clamp and vector rotation order. For a nonpassenger, the client applies the correction, sends accept-teleportation, then sends its resulting PosRot. `LocalPlayer.sendPosition` retains the same baselines, movement threshold, reminder and packet selection. The direct server producer saves the applied destination as its awaited client position; a matching acknowledgment resets the movement baseline and clears the pending correction. While that correction is pending, the server's movement handler only accepts the reported rotation. No difference was found in these scoped inputs/gates.

The source difference is in local block prediction rollback following the direct player correction. In 26.1.2 `ClientPacketListener.handleMovePlayer` calls `onTeleport()` after the two response sends, capturing `currentSequenceNr` as `lastTeleportSequence`. A later `ClientboundBlockChangedAckPacket` carries `packet.sequence()` through `ClientPacketListener.handleBlockChangedAck` and `ClientLevel.handleBlockChangedAck` into `endPredictionsUpTo(ackSequence, ...)`.

Both handlers process each retained state only when its own `state.sequence <= ackSequence`. In 1.21.11 every processed entry passes its saved `playerPos` to `ClientLevel.syncBlockState`. In 26.1.2 the argument is chosen once per handler call as `lastTeleportSequence < ackSequence ? playerPos : null`. Therefore, when an acknowledgment sequence is at or below the correction's captured sequence, all retained states processed by that acknowledgment receive `null`, regardless of each state's retained sequence. When an acknowledgment sequence is above the cutoff, all processed states receive their saved positions, including a still-retained position recorded before or at the correction cutoff. This is an acknowledgment-level gate, not a per-state comparison or a persistent/global suppression.

`ClientLevel.syncBlockState` still restores a differing vanilla block state. A player snap occurs only if the state differs, the local player is in this level, the player collides with the restored state, and a non-null saved position is supplied. Thus a same-packet witness is: a direct correction first records sequence `T`; a block-change ack with sequence `S <= T` processes all eligible retained entries, restores any differing states, and cannot snap the player to their saved positions. For `S > T`, saved positions are supplied for all processed eligible entries, including potentially pre-correction entries, and a colliding rollback may still snap the player.

## Finding and bounds

The corrected finding is `finding-r2.md`. Its claim is limited to the ack-sequence gate and the described direct local position response. No block state/shape change is claimed.

This was a bounded correction receiver audit, not full-pair coverage/freeze. Other correction producers, acknowledgment families, movement validation, block providers/shapes, knockback/death/vehicle behavior and unrelated tick slices remain unexamined. The blind independent audit confirmed the source difference and rejected the earlier draft's implication that each retained state's sequence is compared to the teleport cutoff. That audit is bound to the original draft SHA-256 `b56823704008fb9f8b004981c0db1cc8482dee5db2ff691750300e9846dede98` and original report SHA-256 `880bb97eb0b771eb73dbaa74e7cb12c1f34ea015040a4705c11ebfd21d23cee7`; those original files remain unchanged.

No product, Wiki, Git/history or backlog data was used. No code, tests, runtime, build or source generation was performed.