# Portal-block dismount placement — bounded source comparison

## Inputs and evidence integrity

- Exact pair: Minecraft 1.14.4 → 1.15, Mojmap on both sides.
- 1.14.4 ready marker: `decompiled_minecraft/1.14.4/mojmap.ready.json`, `versionId=1.14.4`, `mapping=mojmap`, 3,250 source files; its source-manifest SHA-256 is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.
- 1.15 ready marker: `decompiled_minecraft/1.15/mojmap.ready.json`, `versionId=1.15`, `mapping=mojmap`, 3,329 source files; its source-manifest SHA-256 is `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.
- Both source-manifest hashes match the corresponding ready marker. Relevant per-file SHA-256 entries match the files on disk. 1.14.4 provenance SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; 1.15 provenance SHA-256: `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`.
- The Mojmap source roots are the canonical read-only `decompiled_minecraft/<version>/mojmap/` trees. No regeneration or network lookup was used.

## Positive difference: server-side player dismount placement

1.14.4 `net/minecraft/world/entity/LivingEntity.java`:
- `findStandUpPosition(Entity)`, lines 1664–1741, starts placement by branching only on vehicle type (`Boat` / `AbstractHorse` versus generic vehicle). It contains no portal-state or portal-tag test. Generic placement initializes a candidate from vehicle coordinates, searches nearby supported/collision-free locations, and finally calls `teleportTo(...)` or `setPos(...)` in the vehicle-type branch.
- `stopRiding()`, lines 2324–2331, saves the vehicle, calls `super.stopRiding()`, then calls `findStandUpPosition(vehicle)` only when a vehicle was actually detached and `!level.isClientSide`.
- `Player.rideTick()`, lines 455–459, initiates ordinary sneaking dismount only when `!level.isClientSide && isSneaking() && isPassenger()`; `Player.stopRiding()`, lines 973–977, only delegates and resets boarding cooldown.

1.15 `net/minecraft/world/entity/LivingEntity.java`:
- `findStandUpPosition(Entity)`, lines 1697–1792, first reads `level.getBlockState(new BlockPos(vehicle)).getBlock().is(BlockTags.PORTALS)`. If true, it sets the dismounting entity's position directly to `(vehicle.x, vehicle.getY(1.0) + 0.001, vehicle.z)`; this is before the boat/horse and generic placement cases. The predicate reads the block state at the vehicle's current block position; it does not inspect portal origin, portal timer or dimension-transfer state. It can therefore select this placement on an ordinary dismount whenever that current state is tagged `PORTALS`.
- `stopRiding()`, lines 2388–2395, retains the same `!level.isClientSide` gate around calling `findStandUpPosition(vehicle)` after a real detach.
- `Player.rideTick()`, lines 458–463, retains the logical-server gate and uses `wantsToStopRiding()`, which is `isShiftKeyDown()` at lines 286–292. `Player.stopRiding()`, lines 990–994, delegates and resets boarding cooldown; it does not itself set coordinates.

The changed player response is conditional on a server-side living player detaching from a vehicle whose current block position is portal-tagged. It changes the selected dismount position to the vehicle's top-center-like coordinates, including for a non-boat/non-horse vehicle and without a portal-origin requirement. The source proves the conditional position write; it does not establish a downstream trajectory.

## Ordinary local/packet detach reachability

- Riding `LocalPlayer.tick()` sends a `ServerboundPlayerInputPacket` carrying the sneak/shift input (1.14.4 `LocalPlayer.java`, lines 178–190; 1.15, lines 178–190). The server input handler stores that input on the server player (1.14.4 `ServerGamePacketListenerImpl.java`, lines 276–285; 1.15, lines 277–287). Server-side `Player.rideTick()` can consequently call `stopRiding()` through the ordinary key-driven path above.
- The server's passenger-list packet is applied by `ClientPacketListener.handleSetEntityPassengersPacket`: it calls `vehicle.ejectPassengers()`, then rebuilds passenger links (1.14.4 `ClientPacketListener.java`, lines 932–950; 1.15, lines 960–978). `Entity.ejectPassengers()` invokes each passenger's `stopRiding()` (1.14.4 `Entity.java`, lines 1645–1648; 1.15, lines 1609–1612).
- On the client, `LocalPlayer.stopRiding()` only delegates to `Player.stopRiding()` and clears `handsBusy` (1.14.4 and 1.15 `client/player/LocalPlayer.java`, lines 161–165 and 162–166 respectively). Delegation reaches `LivingEntity.stopRiding()`, but its `!level.isClientSide` condition is false on this logical client, so neither `findStandUpPosition` nor its portal predicate nor that method's XYZ placement runs on `LocalPlayer` during a packet detach.

Thus the local packet detach itself does not exercise this predicate or mutate local XYZ. The historical difference is specifically server-side dismount placement, which may affect server-authoritative player position. Any separate client position correction that follows is packet-position behavior and is not established or claimed here. This result excludes portal dimension transport and vehicle movement physics.

## Boundary

The portal-tag branch is absent from the complete 1.14.4 `findStandUpPosition` body and present at the start of the 1.15 body. Exact evidence establishes only that it was introduced within the interval (1.14.4, 1.15]; no intermediate patch was inspected.

## Scoped paired source digests

Each digest below is both the entry in that version's source manifest and the computed file SHA-256.

| Relative source file | 1.14.4 | 1.15 |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |`n| `net/minecraft/server/level/ServerPlayer.java` | `0b07bb15e2e3001f03dab2dc943cbca4814ada36c82b6d83f933be4ff01c4b92` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |