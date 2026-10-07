# F-007: Local player sends position packets at a finer displacement threshold

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: movement synchronization; T-POSITION-THRESHOLD
- Classification: changed behavior
- Confidence: candidate (server reconciliation and resulting movement consequence remain open)
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A: manifest A; LocalPlayer#sendPosition lines 240-269, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; displacement guard compares squared delta to 9.0E-4, or positionReminder >=20.

B: manifest B; LocalPlayer#sendPosition lines 232-261, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; guard compares Mth.lengthSquared(delta) to Mth.square(2.0E-4). B Mth#square(double) lines 739-741 and #lengthSquared(double,double,double) lines 799-801, SHA-256 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e. ServerGamePacketListenerImpl#handleMovePlayer processes received position packets and calls Player#move; A/B class hashes are recorded in run.md.

## Source-level difference

For a controlled-camera, non-passenger player before the 20-tick reminder, A's movement-packet condition is squared displacement > 9.0E-4. B's is > (2.0E-4)^2 = 4.0E-8. Thus any displacement with squared length strictly between those thresholds triggers a position packet in B but not A. Both reset the position baseline after sending.

## Reachability and dependencies

LocalPlayer#tick -> sendPosition when not a passenger -> ServerboundMovePlayerPacket -> ServerGamePacketListenerImpl#handleMovePlayer -> player movement processing. The server handler receives and applies each packet; client correction/reconciliation consequences remain queued as D-PACKET-RECONCILIATION.

## Consequence and uncertainty

The packet send threshold change is source-proven. It changes when the server receives a position update; whether per-packet collision processing changes a resulting player trajectory or correction is not established. No server/gameplay testing was run.

## Handoff

Keep as a candidate until D-PACKET-RECONCILIATION closes; no accepted finding snapshot.
