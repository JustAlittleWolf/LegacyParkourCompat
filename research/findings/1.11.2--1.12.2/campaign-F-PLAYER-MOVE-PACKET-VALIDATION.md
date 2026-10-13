# F-PLAYER-MOVE-PACKET-VALIDATION: player movement coordinate validation changes

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`; lines 263-270.
- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`; lines 276-283.

## Difference

On 1.11.2, the validator returns `false` (valid) whenever X, packet minY, Z, pitch and yaw are all finite, without applying the ±30,000,000 bound.

If any value is non-finite, it returns the conjunction `!(abs(X) > 3.0E7) && !(abs(Z) > 3.0E7)`, which can still classify some non-finite packets as valid.

On 1.12.2, it rejects every non-finite value; when all are finite, it rejects if the absolute value of X, minY or Z exceeds `3.0E7`.

The handler evaluates this predicate before its normal player movement branch.

If invalid, 1.11.2 calls `disconnect(String)` and 1.12.2 calls `sendDisconnect(TranslatableText)`; otherwise the handler proceeds into riding rotation or ordinary player movement, including collision checks, `move`, position/angle updates, fall handling, and last-good-position writes.

Thus a finite packet with an extreme minY is accepted into that path on 1.11.2 but rejected before player position writes on 1.12.2.

Other extreme finite axes and the non-finite cases follow the exact predicate difference above.

## Movement meaning

Source proves a changed server acceptance boundary for externally supplied player movement coordinates and a different reachable route to position writes versus packet rejection.

The behavior is a packet validation/authority difference, not a change to the local travel loop.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.11.2--1.12.2/findings/F-PLAYER-MOVE-PACKET-VALIDATION.md).
