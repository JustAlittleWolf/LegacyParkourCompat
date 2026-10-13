# F-1.20.2-PARTIAL-UPDATE-LERP-TARGET: rotation-only update preserves the pending remote-player position target

- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`; lines 567-585.
- **1.20.2**, mojmap: `1.20.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`; lines 525-546.

## Difference

Precondition: the client has already queued a positional target for a RemotePlayer and `lerpSteps > 0`, then receives a relative-move packet with rotation but no position.

Both handlers enter their `!isControlledByLocalInstance()` path.

1.20.1 passes the current displayed XYZ back into `lerpTo`, replacing the pending position target with the displayed position and resetting the interpolation count to 3. 1.20.2 passes the pending target XYZ, preserving that position correction while updating yaw/pitch and resetting the count.

The `lerpTargetX/Y/Z` accessors explicitly select the pending values only while a target remains active.

## Movement meaning

`RemotePlayer` is created by the client packet handler for another player.

Later server relative-move packets reach `handleMoveEntity`, which queues interpolation targets via inherited `LivingEntity.lerpTo`; RemotePlayer's own `aiStep` applies them.

This is direct client-side Player position state, not vehicle movement.

The local controlled player is excluded from these packet interpolation branches by the handler guard.

Source-proven: 1.20.2 retains the queued XYZ target on a rotation-only packet; 1.20.1 cancels it by replacing the target with the current displayed position.

Predicted: the intermediate client-side remote-player position path differs after that packet sequence.

No final position or gameplay result is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-PARTIAL-UPDATE-LERP-TARGET.md).
