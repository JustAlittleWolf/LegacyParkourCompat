# F-006: Boat passenger-list refresh no longer always resets player yaw

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: player-facing boat transition; T-BOAT-PASSENGER
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A: manifest A; LocalPlayer#startRiding lines 154-169, LocalPlayer SHA-256 c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812; ClientPacketListener#handleSetEntityPassengersPacket lines 825-843, SHA-256 a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a.

B: manifest B; LocalPlayer#startRiding lines 153-163, SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; ClientPacketListener#handleSetEntityPassengersPacket lines 826-850, SHA-256 e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b.

## Source-level difference

A sets yRotO, yRot and yHeadRot to boat yaw in LocalPlayer#startRiding after any successful boat mount. B removes those writes from LocalPlayer and performs them in the passenger packet handler only when the player was not already an indirect passenger before the passenger list is rebuilt.

## Reachability and dependencies

Clientbound passenger update -> handler records prior passenger state -> ejects/rebuilds passengers -> LocalPlayer#startRiding. On a refresh while the player is already aboard that same boat, A repeats the yaw reset; B's prior-passenger guard skips it. This is a player-facing orientation transition; boat physics is excluded.

## Consequence and uncertainty

The yaw-state write difference is source-proven. Under the stated refresh condition, the client player retains its existing yaw in B while A sets boat yaw. Any later direction/position consequence is inferred; no vehicle simulation was performed.

## Handoff

Direct player yaw transition delta; no accepted finding snapshot yet.
