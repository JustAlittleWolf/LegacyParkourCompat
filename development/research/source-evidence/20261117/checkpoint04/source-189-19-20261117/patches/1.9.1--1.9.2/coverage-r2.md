# 1.9.1 → 1.9.2 source coverage (r2)

## Evidence identity

- Pair: `1.9.1--1.9.2`; namespace `ornithe-feather`.
- 1.9.1 source manifest SHA-256: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`.
- 1.9.2 source manifest SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- Each manifest contains 1,819 source rows; all mapped source files and hashes were verified. The path inventories match.
- This r2 supplements the frozen r1 inventory with the bounded packet and save/load edges requested by audit. It supersedes no other pair record.

## Added coverage: packet-originated passenger attachment

`net/minecraft/network/PacketByteBuf.java:77` changes the no-argument `readIntArray()` bound from `readableBytes() / 4` in 1.9.1 to `readableBytes()` in 1.9.2. Its shared integer-array reader rejects a decoded count only when it exceeds the supplied bound, then reads that many VarInts. The sole no-argument call site in both exact source sets is `net/minecraft/network/packet/s2c/play/EntityPassengersS2CPacket.java:30`, whose source body is identical (SHA-256 `C49EC4C182B096F73033BD12EA25D727FE9D68481BEB219EA4387457EBACCB80`).

The client handler `ClientPlayNetworkHandler.handleEntityPassengers` is also identical (SHA-256 `7F67BD2DAA792CFC4AAC1744D30F9CFDB28306EC55583D474DDC1A251561B1D6`). It looks up each passenger entity and invokes `startRiding(entity, true)`. The handler has a local-player branch, so the local player can be attached as a passenger. This changes passenger attachment state and may select the later vehicle-input/send branch in `LocalClientPlayerEntity.tick`; that state effect is distinct from local on-foot movement. The resulting vehicle/non-player physics is excluded by project scope. No in-scope on-foot movement response is introduced, so this does not create a scoped movement finding or an unresolved dependency.

## Added coverage: DataVersion and player save/load

`PlayerEntity.writeCustomNbt` changes the persisted `DataVersion` literal from 175 to 176. The DataFixes current version and the `DataFixerUpper.run` version threshold likewise advance from 175 to 176. The player schema in both source sets applies list migrations only to `Inventory` and `EnderItems`; it does not transform position, motion, rotation, fall, ground, or other movement state. The player read/write movement-state serialization path is unchanged. The 175→176 migration therefore changes the data-fixer/save-load edge for item lists, not movement state, and adds no movement candidate or dependency.

## Final disposition

The original tick/input/travel/collision/provider inventory and its 21-file source delta remain as recorded in `coverage.md`. With the packet-originated attachment path and DataVersion/save-load edge now closed, no source-confirmed difference affects local on-foot player movement for 1.9.1→1.9.2. The pair still requires its separate independent full-pair audit.

