# F-006: Boat passenger-list refresh preserves a player's existing yaw in 1.18.2

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: direct player orientation and movement input; T-BOAT-PASSENGER
- Classification: changed player movement behavior
- Confidence: source-confirmed for the bounded receiver transition
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Exact source and artifact identity

A: version metadata 1.17.1, Mojmap; source manifest SHA-256 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b; 41-entry artifact manifest SHA-256 e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; mapped jar SHA-256 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c; source root build/movement-campaign-2026-10-07/ready/1.17.1/mojmap.

B: version metadata 1.18.2, Mojmap; source manifest SHA-256 aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a; 41-entry artifact manifest SHA-256 a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036; mapped jar SHA-256 60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba; source root build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.

## Paired source evidence

A LocalPlayer#startRiding, lines 154-169, writes yRotO, yRot, and yHeadRot to the boat yaw after super.startRiding succeeds for a Boat. LocalPlayer.java SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812. A ClientPacketListener#handleSetEntityPassengersPacket, lines 825-843, records prior passenger state, ejects current passengers, then reconstructs the listed passengers by calling startRiding. ClientPacketListener.java SHA-256 a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a.

B LocalPlayer#startRiding, lines 153-163, has no yaw writes. LocalPlayer.java SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095. B ClientPacketListener#handleSetEntityPassengersPacket, lines 826-850, performs the boat yaw writes only when the local player is listed and was not already an indirect passenger before the handler ejects/rebuilds the passenger list. ClientPacketListener.java SHA-256 e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b.

The paired Entity#hasIndirectPassenger / getIndirectPassengersStream methods establish that the prior-passenger test includes direct passengers. Entity#ejectPassengers runs before the handler calls startRiding, so a listed local player can successfully remount the same boat during a refresh. Entity.java hashes are A ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de and B 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a. Entity#moveRelative uses getYRot() to compute the later movement input vector in both versions (A lines 1176-1191; B lines 1151-1166), connecting the player yaw transition to movement direction.

## Reachability and guards

For an inbound passenger-list packet naming a Boat that already contains the local player in its passenger tree and whose replacement passenger list still includes that player, A records the prior state, ejects the passenger list, and successfully remounts the local player. A's successful LocalPlayer#startRiding resets all three yaw fields to the boat yaw. B records the same prior state and rebuilds the list, but its prior-passenger guard skips the yaw reset. The inbound packet and listed local-player conditions are the bounded external input; no vehicle motion is part of this finding.

## Source-level consequence and scope

Under that packet condition, B preserves the player's current yaw fields while A replaces them with boat yaw. Since later player-relative movement reads yRot, this changes the direct player movement input orientation. The source evidence establishes the transition; no particular downstream trajectory was simulated. Vehicle physics and the server-side cause of the passenger-list refresh are outside this bounded receiver finding.

## Dependency closure and release boundary

The receiver path, prior-passenger predicate, passenger-list rebuild, successful remount, and movement-yaw consumer are checked on both sides. No additional local producer is needed to establish the conditional player-state response to the inbound packet. The first changed release is not established; the proven boundary is 1.17.1 versus 1.18.2 only.

## Handoff

Snapshot eligible for blind source review. Pair discovery remains partial; no pair freeze or runtime parity claim.