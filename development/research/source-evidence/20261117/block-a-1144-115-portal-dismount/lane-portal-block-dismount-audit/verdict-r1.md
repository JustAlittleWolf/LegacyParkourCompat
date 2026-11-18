# Independent source verdict r1: portal-block dismount

## Rebound revisions

| Corrected author draft | SHA-256 before review | SHA-256 after review | Disposition |
|---|---|---|---|
| `../lane-portal-block-dismount-predicate/comparison.corrected-r1.md` | `4f5e0a53ad90d40a8da721eca333438000693c3a35cec3dc5db0f8f0228db170` | `4f5e0a53ad90d40a8da721eca333438000693c3a35cec3dc5db0f8f0228db170` | ACCEPT. Corrected server-to-client route, 1.14.4 extra virtual-teleport correction, and bounded ACK/PosRot caveat match the frozen source. No claim about packet interleaving is made. |
| `../lane-portal-block-dismount-predicate/portal-block-dismount-placement.corrected-r1.md` | `6c91cd5c8b4d82f6cda8251ada7a9d0fd90983abcc7c90a220e611a9c8863edd` | `6c91cd5c8b4d82f6cda8251ada7a9d0fd90983abcc7c90a220e611a9c8863edd` | ACCEPT. Server placement finding and immediate absolute XYZ correction to local player are source-supported; the ending is explicitly bounded before subsequent server processing. |

The inherited independent verdict is [verdict.md](verdict.md), SHA-256 `6df4ca15b07302f8938680fc79975d2827acccc4cdf6a8ea07130cfd8a1d5256`. It accepted the portal-tag branch and server gate, and identified the correction route as the omission. This r1 binds that verdict to the corrected author revisions without changing the original verdict or frozen source artifacts.

## Changed-scope audit

**1.14.4 generic virtual `teleportTo` path — ACCEPT.** For a generic vehicle, `LivingEntity.findStandUpPosition` ends with virtual `this.teleportTo(l, m, n)` at line 1718. `ServerPlayer.teleportTo(double,double,double)` dispatches to `connection.teleport(...)` at 1.14.4 `ServerPlayer.java` lines 1087–1089. After that superclass path returns, the outer `ServerPlayer.stopRiding()` also sends a correction when the vehicle changed and the connection exists (lines 763–770). The corrected draft accurately describes these two synchronous correction calls for that generic path. It does not generalize this to the Boat / AbstractHorse branch, which does not take that generic final call.

For the 1.15 portal-tag branch, `findStandUpPosition` uses `setPos` (lines 1697–1700), so the relevant direct correction is the outer `ServerPlayer.stopRiding()` call (lines 750–757). This accepts the method-call trace only; it makes no claim about ordering or interleaving with the separately tracked passenger-list packet.

**Client application and ACK/PosRot boundary — ACCEPT.** Both `ServerGamePacketListenerImpl.teleport` overloads use an empty relative-argument set in the five-argument overload (1.14.4 lines 860–862; 1.15 lines 872–874). The full overload sends `ClientboundPlayerPositionPacket` with the XYZ and that set (1.14.4 lines 864–878; 1.15 lines 876–890). The client applies the absolute coordinates in `ClientPacketListener.handleMovePlayer`: 1.14.4 `absMoveTo` at lines 659–705; 1.15 `setPosRaw` followed by `absMoveTo` at lines 669–727. The same handlers then send `ServerboundAcceptTeleportationPacket` and `ServerboundMovePlayerPacket.PosRot` (1.14.4 lines 704–706; 1.15 lines 727–729). The corrected text ends at those sends and expressly makes no claim about subsequent server processing. No packet ordering, interleaving, or runtime behavior is inferred.

The underlying portal predicate, `LivingEntity.stopRiding` logical-side gate, client passenger detach, server input route and local-player position application retain the earlier ACCEPT verdicts. The source-only difference remains a server-side vanilla response whose result is then carried to local-player XYZ. It does not establish historical physics on logical servers or settle implementation applicability.

## Frozen source dependencies and identities

The original source roster and addenda remain unchanged and are bound by these hashes:

- [source-roster.md](source-roster.md): `6f3575a3182cfd8f04a9b37df1baf5bb23d96e1fb59500ed46589f7ad9ef53c9`
- [source-roster-addendum.md](source-roster-addendum.md): `3f30a7694aa1dbe00b0d14140d90f349069728e59fdba349405f92c3fe8ff230`
- [source-identity-addendum.md](source-identity-addendum.md): `98f785abe822fba60be57cbdafb6c3dd51df877f78a0adfb4be4d4337762a138`

Exact dependency file hashes, matching the frozen source manifests:

| Relative source | 1.14.4 Mojmap SHA-256 | 1.15 Mojmap SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| `net/minecraft/server/level/ServerPlayer.java` | `0b07bb15e2e3001f03dab2dc943cbca4814ada36c82b6d83f933be4ff01c4b92` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |

Ready-marker hashes: 1.14.4 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; 1.15 `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`. Source-manifest hashes: 1.14.4 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. Provenance hashes: 1.14.4 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; 1.15 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`.
