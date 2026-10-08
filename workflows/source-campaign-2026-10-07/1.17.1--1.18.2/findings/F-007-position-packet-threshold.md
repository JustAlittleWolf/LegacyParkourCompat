# F-007: Local player sends position packets at a finer displacement threshold

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: movement synchronization; T-POSITION-THRESHOLD
- Classification: changed player movement synchronization
- Confidence: source-confirmed for bounded packet emission and clear-path server position update
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Exact source and artifact identity

A: version metadata 1.17.1, Mojmap; source manifest SHA-256 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b; 41-entry artifact manifest SHA-256 e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; mapped jar SHA-256 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c.

B: version metadata 1.18.2, Mojmap; source manifest SHA-256 aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a; 41-entry artifact manifest SHA-256 a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036; mapped jar SHA-256 60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba.

## Paired client source evidence

A LocalPlayer#sendPosition, lines 240-269, SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812, compares squared displacement to 9.0E-4, with positionReminder >= 20 as the periodic fallback.

B LocalPlayer#sendPosition, lines 232-261, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095, compares Mth.lengthSquared(delta) to Mth.square(2.0E-4) = 4.0E-8; Mth methods are lines 739-741 and 799-801, SHA-256 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e. Both send an absolute position packet and reset the same local position baseline after sending.

For a controlled-camera, non-passenger local player before the 20-tick reminder, any squared displacement strictly between 4.0E-8 and 9.0E-4 sends a position packet in B and no position packet in A.

## Paired server receiver path

A ServerGamePacketListenerImpl#handleMovePlayer, lines 793-900, SHA-256 270139c717fea2fbbe871c2163f9bd022371e0c01f5d1b87a55d45cff22a7f59; B lines 772-879, SHA-256 c979dd50b8d80d2d00190e08360a4fc689693715a84e248503099f340a8546c9. For a non-passenger, awake player with no pending teleport, each received packet enters the same on-foot path: compute the requested delta from lastGood coordinates, call player.move(MoverType.PLAYER, delta), set the packet target with absMoveTo, then evaluate collision/correction conditions. The small threshold-band delta is below the handler's 0.0625 squared residual correction threshold; on a clear path, move reaches the requested target and no collision correction is triggered. The server movement call delegates to Player#move on both versions; Player.java hashes A 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481 / B bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.

## Reachability and consequence

Choose an on-foot client whose current displacement from its last-sent baseline is 0.001 blocks along an unobstructed horizontal path, with controlled-camera true, positionReminder below 20, and no passenger, sleep, teleport, or world-collision condition. Squared displacement is 1.0E-6, between the A and B thresholds. B sends this packet and the clear-path server handler updates the server player's position to its target in that tick; A sends no packet and the server retains its previous position until a later update. This establishes different server movement synchronization timing from source; it does not claim a different client-side trajectory or a later collision/correction outcome.

## Dependency closure and release boundary

The client send predicate, baseline write, packet type, paired server movement receiver, and bounded clear-path target-position update are checked. D-PACKET-RECONCILIATION remains open for other packet paths and collision-induced server correction behavior. The first changed release is not established; the proven boundary is 1.17.1 versus 1.18.2 only.

## Handoff

Snapshot eligible for blind source review. Pair discovery remains partial; no pair freeze or runtime parity claim.
