# 1.9.1 → 1.9.2 source coverage

## Evidence identity

- Source namespace: `ornithe-feather`.
- 1.9.1 mapping-local source manifest SHA-256: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`; Feather Gen2 `1.9.1+build.2` artifact SHA-256: `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`.
- 1.9.2 mapping-local source manifest SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; Feather Gen2 `1.9.2+build.2` artifact SHA-256: `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`.
- Each manifest has 1,819 source rows. Every manifest path matched exactly one on-disk source file; no extra or missing source paths and no source hash mismatches. All paths were verified against manifest SHA-256 values. The two manifest path sets are the same.

## Ordered player-tick inventory

| Slice | Paired evidence / result | Dependency closure |
|---|---|---|
| Local client tick gate, input polling and client send path | `client/entity/living/player/LocalClientPlayerEntity.java`, `ClientPlayerEntity.java`, `Input.java`, `KeyboardInput.java`; source hashes match across pair. Tick/input method bodies match. | Local tick dispatches into inherited player/living/entity tick; input influences inherited travel and local movement packets. |
| Player state and direct player tick | `entity/living/player/PlayerEntity.java`; source hashes differ. Full source comparison shows only the persisted NBT `DataVersion` literal changes from 175 (1.9.1, `writeCustomNbt`, line 723) to 176 (1.9.2, line 723). Player tick, dimensions, jump, relative movement, equipment/effect predicates and player collision responses are unchanged. | `LivingEntity` and `Entity` movement paths below; no movement dependency from the changed literal. |
| Living movement/tick/jump | `entity/living/LivingEntity.java`; source hashes match. Paired members include `checkFallDamage`, `moveRelative`, `tick`, `jump`, `jumpInWater`, `jumpInLava`, and travel/flight branch bodies. | Reads `Entity` collision/liquid state and `PlayerEntity` abilities/status. |
| Base entity tick, velocity, collision resolution, shape/position, liquid and player-push paths | `entity/Entity.java`; source hash matches. Paired members include `tick`, `baseTick`, `move`, `checkWaterCollisions`, `updateVelocity`, `push`, `getShape`/position updates, collision flags and liquid state predicates. | `World.getCollisions`, `World.applyLiquidDrag`, `Box` and block-state providers. |
| World tick/query and movement input | `client/Minecraft.java` differs only in client version strings and network protocol version (`108`→`109`); no client movement tick/input scheduling changes. `world/World.java` and `util/math/Box.java` match. | Local tick path and world/block queries above. |
| Block collision/shape providers and registrations | Block provider and block-state sources match across pair; no provider file is among the 21 changed manifest paths. Complete provider inventory is present in each 1,819-path source set. | `BlockState` dispatch to `Block` providers, then `World.getCollisions` and `Entity.move`. |
| Direct player responses to block/fluid contacts | Piston/slime/liquid/cobweb/ladder and material collision/drag paths are unchanged in the paired sources. | `Entity.move`, `World.applyLiquidDrag`, and provider methods above. |
| Packet-originated player correction and server/client roles | Client network and packet sources were present in the complete source inventory; no changed packet paths. No server-only simulation was treated as client movement. | Excluded from candidate scope unless a client-side direct movement response changes. |

## Compared changes and dispositions

The complete source-hash delta contains 21 files. The only player/movement-adjacent change is `net/minecraft/entity/living/player/PlayerEntity.java`, where the NBT data-version literal changes 175→176 in `writeCustomNbt`; it cannot affect a fixed-profile movement tick. `net/minecraft/client/Minecraft.java` changes the displayed version and handshake protocol number only. The remaining changed files are GUI/server-list/network-handshake, server, crash/data-fix, snooper, and world-save code; no player tick, input, movement, collision, block-shape or player-response dependency is changed.

No source-confirmed player-movement difference found for this pair. No candidate finding is proposed.
