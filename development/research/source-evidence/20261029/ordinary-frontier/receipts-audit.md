# Independent direct-player receipt audit: 1.17.1 → 1.18.2

## Verdict

**Bounded audit: accepted with one qualification.** The same-response assertions for position correction, entity motion, explosion impulse, teleport/relative movement guards, and vehicle correction are supported by the exact canonical Mojmap sources under equivalent received payloads. The conditional boat passenger-list yaw difference is also confirmed. This does not establish packet production/order, runtime trajectories, complete movement dependency closure, or a pair freeze.

I independently inspected the selected methods in the canonical read-only sources and verified each selected source file hash against its release-level `mojmap.sources.sha256` entry. I did not use product/history/backlog/wiki material, generate sources, build, test, or launch a game/server.

## Immutable input identity

Author report: `.native-source/20261028/frontier-c/1171-1182-direct-player-receipts/report.md`, SHA-256 `a01283accba05e88fd16f2ff42c3403547433a12469b8b59e72e8d879dad2c17`.

Canonical roots:

- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.17.1/mojmap`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.18.2/mojmap`

Release-level manifests (stored at the release parent, not inside the `mojmap` source directory):

- 1.17.1 `mojmap.sources.sha256`: SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; ready marker agrees on release, mapping and manifest hash.
- 1.18.2 `mojmap.sources.sha256`: SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; ready marker agrees on release, mapping and manifest hash.

Selected source hashes independently recomputed and matched to those manifests:

| Source | 1.17.1 SHA-256 | 1.18.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a` | `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b` |
| `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` | `e44cb1126b9b8093655892fb6ca17fa934cd79cc0453cee5e443969d8792b82e` |

## Independent claim review

| Claim | Verdict | Source check |
| --- | --- | --- |
| Local position correction is the same response | **PASS** | `ClientPacketListener.handleMovePlayer`: 1.17.1 lines 549–613 and 1.18.2 lines 541–605. Both dismount on the packet flag, independently preserve velocity for relative XYZ and zero it for absolute XYZ, update old/current position in the same sequence, add current rotation for relative rotation flags, call `absMoveTo`, then send teleport acceptance and a `PosRot` acknowledgement with `onGround=false`. The extra 1.17.1 tail only marks `started` and closes the loading screen. `Entity.absMoveTo` has the same clamping and rotation writes in both versions (1.17.1 lines 1199–1214; 1.18.2 lines 1174–1189). |
| Entity-motion receipt is the same for an entity id resolving to the local player | **PASS** | `handleSetEntityMotion`: 1.17.1 lines 445–454; 1.18.2 lines 441–446. Both look up the id in the client level and call `lerpMotion(packet Xa/Ya/Za / 8000.0)`. `ClientLevel.addPlayer` and `getEntity` use the same entity storage path in both endpoints (1.17.1 lines 223–247; 1.18.2 lines 262–286); `Entity.lerpMotion` assigns delta movement in both (`1.17.1` line 1890; `1.18.2` lines 1885–1886). This is conditional on successful id resolution and equivalent integer payloads. |
| Explosion impulse receipt is the same | **PASS** | `handleExplosion`: 1.17.1 lines 973–993; 1.18.2 lines 972–977. Both finalize the client-side explosion, then add the packet's three knockback values to current local-player delta movement and assign the sum. The finding assumes equal packet fields; producer calculation and other explosion side effects are outside this direct-response check. |
| Entity teleport and relative move do not directly transform a locally controlled entity through these handlers | **PASS** | `handleTeleportEntity` writes packet coordinates to interpolation bookkeeping before a common `!isControlledByLocalInstance()` guard; `lerpTo` and on-ground writes are inside it in both (1.17.1 lines 484–498; 1.18.2 lines 476–490). `handleMoveEntity` has the same local-control guard around interpolation and on-ground updates (1.17.1 lines 510–529; 1.18.2 lines 502–522). `Entity.isControlledByLocalInstance` resolves its controlling passenger and returns true for a local player in both (1.17.1 lines 2689–2692; 1.18.2 lines 2685–2688). The packet-coordinate bookkeeping write still occurs for teleport and is not equivalent to no write at all. |
| Vehicle correction has the same direct response | **PASS** | `handleMoveVehicle`: 1.17.1 lines 1813–1825; 1.18.2 lines 1723–1730. Both select the local player's root vehicle, gate on it being a non-player locally controlled entity, apply `absMoveTo` from the payload, then send `ServerboundMoveVehiclePacket`. This writes the vehicle transform; this audit does not close passenger transform consumers. |
| Passenger-list reattachment has a conditional boat yaw difference | **PASS** | Both `handleSetEntityPassengersPacket` implementations test `hasIndirectPassenger(localPlayer)` before ejection, eject, resolve each listed id and call `startRiding(vehicle, true)` (1.17.1 lines 825–845; 1.18.2 lines 826–852). In 1.17.1, successful `LocalPlayer.startRiding` writes previous yaw, yaw and head yaw from any boat (lines 155–169). In 1.18.2, that override no longer writes boat yaw (lines 153–164); the packet handler instead writes those three yaw fields when the listed passenger is the local player, the vehicle is a boat and the pre-ejection indirect-passenger flag was false (lines 839–844). Thus, when the local player was already an indirect passenger of that boat before the packet, is listed again, and `startRiding` succeeds, 1.17.1 realigns those fields while 1.18.2 does not. A first attachment with the same gates and successful `startRiding` writes the same three values in both versions. |

## Qualification and limits

In 1.18.2 the yaw write is not guarded by the boolean result of `startRiding`; it follows the call and is gated only by local-player identity and `!wasPassenger` (plus the boat subtype check around the yaw assignments). In 1.17.1 the `LocalPlayer.startRiding` yaw writes occur only after `super.startRiding` succeeds. Therefore the report's successful-first-attachment parity claim is valid, but it should not be generalized to a failed/cycle-rejected `startRiding` case: the new handler could still write yaw there. No ordinary reachable vanilla witness for that failed case was established in this bounded audit.

The audit checks consumer behavior under equal received payloads. It does not assess server-side payload production, packet scheduling or relative ordering, exact intermediate patch introduction, other player state writers (effects/attributes/equipment/entity data/block updates), vehicle passenger-transform consumers, or the remaining tick/collision/fluid dependencies. No runtime trajectory or full-pair coverage conclusion follows.
