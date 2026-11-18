# Portal-block dismount placement — corrected source comparison

## Exact pair and evidence identity

- Pair and mappings: Minecraft 1.14.4 → 1.15, Mojmap on both sides.
- Independent verdict read: `../lane-portal-block-dismount-audit/verdict.md`, SHA-256 `6df4ca15b07302f8938680fc79975d2827acccc4cdf6a8ea07130cfd8a1d5256`. Its frozen source roster and roster/identity addenda were read. Both original drafts still match the verdict-bound SHA-256 values; they are preserved unchanged.
- Ready markers identify `versionId=1.14.4` / `mapping=mojmap` (3,250 sources) and `versionId=1.15` / `mapping=mojmap` (3,329 sources). Their source-manifest SHA-256 values match current files: 1.14.4 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. Ready marker SHA-256 values are `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` and `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`; provenance hashes are `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` and `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` respectively.
- Each exact class digest in the frozen roster/addenda was rechecked against both the source manifest and current class bytes, including `ClientboundPlayerPositionPacket.java`. The decompiled trees were read-only; no regeneration or network lookup was used.

## Placement difference and server gate

1.14.4 `net/minecraft/world/entity/LivingEntity.java`, `findStandUpPosition(Entity)` lines 1664–1741, branches first on `Boat` / `AbstractHorse`; its generic placement path has no portal-state test. `stopRiding()` lines 2324–2331 saves the vehicle, delegates, then calls placement only after an actual detach and when `!level.isClientSide`.

1.15 `net/minecraft/world/entity/LivingEntity.java`, `findStandUpPosition(Entity)` lines 1697–1792, first checks whether `level.getBlockState(new BlockPos(vehicle)).getBlock().is(BlockTags.PORTALS)`. When true it sets the dismounting entity to `(vehicle.x, vehicle.getY(1.0) + 0.001, vehicle.z)` before the boat/horse or generic cases. The predicate reads the vehicle's current block state and does not require portal origin or dimension-transfer state. `stopRiding()` lines 2388–2395 retains the actual-detach and `!level.isClientSide` conditions.

The changed response is therefore server-side dismount placement. Ordinary server `Player.rideTick()` reaches `stopRiding()` when mounted shift/sneak input is active: 1.14.4 `Player.java` lines 455–459; 1.15 lines 458–463, with `wantsToStopRiding()` reading shift state at lines 286–292. Mounted `LocalPlayer.tick()` sends that input (both `client/player/LocalPlayer.java`, lines 178–190); server input handling stores it on the passenger `ServerPlayer` (1.14.4 `ServerGamePacketListenerImpl.java` 276–285 and `ServerPlayer.java` 927–939; 1.15 `ServerGamePacketListenerImpl.java` 277–287 and `ServerPlayer.java` 901–914).

## Immediate server correction path and local application

The direct local detach and the server-authored position correction are separate events.

1. A server-side `ServerPlayer.stopRiding()` saves the old vehicle, calls `super.stopRiding()`, then—if the vehicle changed and `connection != null`—calls `connection.teleport` with the server player's resulting XYZ and rotation. This is 1.14.4 `server/level/ServerPlayer.java` lines 763–770 and 1.15 lines 750–757. The call occurs after `LivingEntity.stopRiding()` has performed its server-gated placement.
2. The five-argument `ServerGamePacketListenerImpl.teleport` overload delegates with `Collections.emptySet()` for relative arguments (1.14.4 lines 860–862; 1.15 lines 872–874). The full overload thus subtracts zero relative offsets, sets the server player to the target coordinates, and sends `ClientboundPlayerPositionPacket` with those XYZ and an empty relative-argument set (1.14.4 lines 864–878; 1.15 lines 876–890). These are absolute coordinates.
3. The client handles that position packet in `ClientPacketListener.handleMovePlayer`. With X/Y/Z absent from the relative set, it uses the packet's XYZ directly and applies them to the local player: 1.14.4 lines 659–704 calls `player.absMoveTo(d, e, f, ...)`; 1.15 lines 669–727 computes the direct XYZ, calls `player.setPosRaw(e, i, m)`, then `player.absMoveTo(e, i, m, ...)`. Thus the server-side placement can change the local player's XYZ through this authoritative packet, even though the local detach itself did not run the portal predicate.
4. In that same client handler, after applying the position, the client sends `ServerboundAcceptTeleportationPacket`, followed by `ServerboundMovePlayerPacket.PosRot` (1.14.4 lines 704–706; 1.15 lines 727–729). This report stops at those client sends; it makes no claim about subsequent server processing.

For comparison, in 1.14.4 a generic-vehicle `findStandUpPosition` reaches virtual `teleportTo(l,m,n)` at line 1718. `ServerPlayer.teleportTo(double,double,double)` sends a correction through `connection.teleport` at lines 1087–1089, after which the outer `ServerPlayer.stopRiding()` sends its own post-placement correction. In 1.15 the portal-tag branch calls `setPos` directly, so that branch returns through the outer override and produces its one post-placement correction. This describes only the synchronous correction calls; it does not assert their ordering relative to the separate tracked passenger-list packet.

The passenger-list handler remains distinct: `ClientPacketListener.handleSetEntityPassengersPacket` ejects and rebuilds the passenger list (1.14.4 lines 932–950; 1.15 lines 960–978), and `Entity.ejectPassengers()` calls passenger `stopRiding()` (1.14.4 `Entity.java` 1645–1648; 1.15 1609–1612). The resulting `LocalPlayer.stopRiding()` only delegates and clears `handsBusy` (1.14.4 `LocalPlayer.java` 162–165; 1.15 163–166). On the logical client, `LivingEntity.stopRiding()` fails its server-side gate, so this detach itself does not call `findStandUpPosition` or write XYZ. The separate position packet does write local XYZ as described above. No relative ordering between this passenger-list packet and the immediate correction is asserted here.

## Boundary and limits

The portal-tag branch is absent from the complete 1.14.4 `findStandUpPosition` body and present at the start of the 1.15 body. The exact checked boundary is 1.14.4 without the branch versus 1.15 with it; introduction remains unknown within `(1.14.4, 1.15]`.

This source finding concerns the vanilla server-side player dismount response and its position correction to the client. It does not establish historical physics on a logical server or propose implementation/applicability. It is separate from portal dimension transport, vehicle physics, the independent passenger-list packet ordering, and server handling after the client acknowledgement/position report.

## Scoped source digests

The values below match both the frozen source-manifest entries and current class-file SHA-256 values.

| Relative source file | 1.14.4 | 1.15 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| `net/minecraft/server/level/ServerPlayer.java` | `0b07bb15e2e3001f03dab2dc943cbca4814ada36c82b6d83f933be4ff01c4b92` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |