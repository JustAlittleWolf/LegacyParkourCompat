# F-PLAYER-MOVE-PACKET-VALIDATION: player movement coordinate validation changes

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S7.3 (client/server movement authority and externally supplied player position); S1.7 (incoming movement-state packet consumer)
- Classification: changed behavior
- Confidence: source-confirmed by discovery author; independent blind source review pending
- Applicability: server receives a `PlayerMoveC2SPacket` with finite coordinates outside the ±30,000,000 bound or non-finite values; the changed validation runs before the server's player-position acceptance path
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather`
- A source: `1.11.2/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, whole-file SHA-256 `947be17cf3d7217e7c8e563d4dd312cd133de1dc06bcd82b4431c9e5e74bc0bf`; `isInvalidMove(PlayerMoveC2SPacket)` lines 263-270, body SHA-256 `e9ca333b68d4c4d320ae0687fac055cf9f7d0cbf5bab9693470c9363704d6136`; called first in `handlePlayerMove(PlayerMoveC2SPacket)` at line 371, whose body SHA-256 is `6a3fbd8e5cc1306c9e89ccfd979cb6fc532288f5c309c8b605087dd88bd663e7`.
- B manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather`
- B source: `1.12.2/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, whole-file SHA-256 `77bf65b2c48ff952713942e183af1cd5fb243ad4f8fd2e53aa1b97272e7acd7c`; `isInvalidMove(PlayerMoveC2SPacket)` lines 276-283, body SHA-256 `92b3040198192627aabe9062ca10ac4d5f48152cf0ff5964f17a3c331cee7999`; called first in `handlePlayerMove(PlayerMoveC2SPacket)` at line 407, whose body SHA-256 is `f1fd81fe463c3e8845af3e43e192b3dc0c7577cabb746071e711e8ff8d7834b6`.
- Packet accessors: A/B `1.11.2/1.12.2/ornithe-feather/net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` are byte-identical (whole-file SHA-256 `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7`). `getMinY(double)` lines 43-45 has body SHA-256 `f318be2366ec55f2b9bb2ffd42170a23797a14b91b6267ef9595d5298bc52759` in both trees and returns the packet minY when position is present, otherwise the supplied default.
- Control path: the `VehicleMoveC2SPacket` overload of `isInvalidMove` has the same A/B body SHA-256 `da5641b703fbbe504eed23f213b6a79e05fec7a1d8d71d1f9a5e616a9db56f6a`. The corresponding `handlePlayerMove` bodies differ at packet rejection text and a logging-expression form; the changed acceptance predicate is the `PlayerMoveC2SPacket` validator recorded above.
- Source preparation/provenance: both exact endpoint source manifests and immutable `feather-r1-2026-10-07` snapshots are recorded in the run manifest. The original derived mapped JARs remain unavailable; revised snapshots do not establish identity with those originals.

## Source-level difference

On A, the validator returns `false` (valid) whenever X, packet minY, Z, pitch and yaw are all finite, without applying the ±30,000,000 bound. If any value is non-finite, it returns the conjunction `!(abs(X) > 3.0E7) && !(abs(Z) > 3.0E7)`, which can still classify some non-finite packets as valid. On B, it rejects every non-finite value; when all are finite, it rejects if the absolute value of X, minY or Z exceeds `3.0E7`.

The handler evaluates this predicate before its normal player movement branch. If invalid, A calls `disconnect(String)` and B calls `sendDisconnect(TranslatableText)`; otherwise the handler proceeds into riding rotation or ordinary player movement, including collision checks, `move`, position/angle updates, fall handling, and last-good-position writes. Thus a finite packet with an extreme minY is accepted into that path on A but rejected before player position writes on B. Other extreme finite axes and the non-finite cases follow the exact predicate difference above.

## Consequence and uncertainty

Source proves a changed server acceptance boundary for externally supplied player movement coordinates and a different reachable route to position writes versus packet rejection. It does not claim that an ordinary vanilla client emits these values or quantify any gameplay trajectory. The behavior is a packet validation/authority difference, not a change to the local travel loop.

## Handoff

S7.3 owns the inbound player-movement acceptance boundary; S1.7 records the corresponding client packet consumer. Other client correction consumers, outbound movement packet production, server position writers and dimension-transfer authority remain open. A separate blind source reviewer must accept this exact snapshot before implementation handoff.
