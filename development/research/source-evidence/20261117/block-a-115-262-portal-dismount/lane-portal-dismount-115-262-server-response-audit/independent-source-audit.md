# Independent source audit: portal-tagged vehicle dismount (1.15 to 26.2)

## Verdict

**Accepted, with a frozen-roster supplement.** The scoped comparison is supported by the exact 1.15 Mojmap and 26.2 published unobfuscated source trees. The author artifact's stated 1.15-to-26.2 behavior and packet interpretation match the source. The added roster covers the entity teleport path, packet schema, and both acknowledgement/response packet classes needed to independently bind the complete path. This endpoint audit makes no claim about the introducing release or packet arrival order.

Author input: `source-roster-comparison-1.15-to-26.2.md`, SHA-256 `64dc9971f362bed4ed047112fe183bbf45efbe4b59cbdd81f3c1c728c0e99769`.

## Source-root identity and writer coordination

The roots are the version-root publications `decompiled_minecraft/1.15/mojmap.*` and `decompiled_minecraft/26.2/unobfuscated.*` (source files below their corresponding mapping directories). Root ready markers, provenance records, and source manifests were read-only verified. Both ready markers, provenance records, mapping/version IDs, manifest hashes, file counts, and byte totals agree.

| Root | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Java files / bytes |
|---|---|---|---|---|
| 1.15 / mojmap | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | 3,329 / 17,522,461 |
| 26.2 / unobfuscated | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | 7,055 / 33,813,717 |

Independent full-root path/file-hash index fingerprints (sorted UTF-8 relative path, NUL, lowercase raw-file SHA-256, LF; SHA-256 over the concatenation) are 1.15 `2230f592a97eee43ad83943575cfa02b680f7025518fbeaff00e1c62325d2dd3` and 26.2 `77e2f2375942f1842e9ab11af36ffbbdf95444603a762319f0d2999abca65624`. These are separate from the publisher's source-manifest identities above.

The shared primary source trees were only read. No source preparation, primary write, or competing decompilation was performed. No inference is made from the stale zero-byte cache lock file.

## Frozen roster supplement and hashes

Before reading the author artifact, I froze the operation roster below from source call edges. The author table binds a subset of these leaves; this supplement adds `Entity.java`, `ServerboundAcceptTeleportationPacket.java`, `ServerboundMovePlayerPacket.java`, and 1.15's packet schema file where the complete dismount/teleport/ack path requires them. Every listed file's raw SHA-256 was recomputed and matched the exact version-root `.sources.sha256` manifest entry.

| Endpoint | Relative source path | SHA-256 |
|---|---|---|
| 1.15 mojmap | `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 mojmap | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 mojmap | `net/minecraft/server/level/ServerPlayer.java` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| 1.15 mojmap | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| 1.15 mojmap | `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| 1.15 mojmap | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| 1.15 mojmap | `net/minecraft/network/protocol/game/ServerboundAcceptTeleportationPacket.java` | `5802eea9360bb2655738bc8aabcac46d111e06b48c57bfabd58b1f431cd7efca` |
| 1.15 mojmap | `net/minecraft/network/protocol/game/ServerboundMovePlayerPacket.java` | `d507e0f95601bd5ec9c180b58e456891dbcaa3b186ec8099e9654c5797c220d1` |
| 26.2 unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 unobfuscated | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 unobfuscated | `net/minecraft/server/level/ServerPlayer.java` | `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e` |
| 26.2 unobfuscated | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2` |
| 26.2 unobfuscated | `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `8da72408493b7279713f134bb2a60c39763e89ce6ceb043eece306c040eaaa5a` |
| 26.2 unobfuscated | `net/minecraft/world/entity/PositionMoveRotation.java` | `a5f0cf32266502dd2c9250d28dfc7bc85cfcf30247e91e3311018af15be319f8` |
| 26.2 unobfuscated | `net/minecraft/world/entity/Relative.java` | `e4a9488f6e6703e184fc62f0e85006e06a6e30d7475c892542db3abbe6a45ed3` |
| 26.2 unobfuscated | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6` |
| 26.2 unobfuscated | `net/minecraft/network/protocol/game/ServerboundAcceptTeleportationPacket.java` | `1d2a17cc736a277da10cc58cfe7fc3a0b59cfaf0ff4c5e7d96af3a58bbbb4d59` |
| 26.2 unobfuscated | `net/minecraft/network/protocol/game/ServerboundMovePlayerPacket.java` | `3f24293dd796c9eb0e9efe726797e09280a1239232813c307c0bb4a84e1ab9c9` |

## Independent trace and claim checks

### Server-side dismount position

- **1.15:** `LivingEntity.stopRiding()` saves the old vehicle, calls `super.stopRiding()`, then runs `findStandUpPosition(oldVehicle)` only if the vehicle changed and the logical level is server-side (`LivingEntity.java:1697, 2389–2395`). The helper first tests the block state at `new BlockPos(vehicle)` against `BlockTags.PORTALS`. In that branch it calls `setPos(vehicle.x, vehicle.getY(1.0) + 0.001, vehicle.z)`. It bypasses the other vehicle/dismount-search branches. `Entity.setPos` updates coordinates and the bounding box directly.
- **26.2:** `LivingEntity.stopRiding()` preserves the changed-vehicle and logical-server gates, then calls `dismountVehicle(oldVehicle)` (`LivingEntity.java:2347, 3293–3298`). For an extant rider and portal-tagged vehicle, that helper selects its fallback target: current rider X/Z and `max(rider Y, vehicle Y)`, optionally refined by `findFreePosition` for width and height each `<= 4.0F`. It then calls `dismountTo(target)` (`LivingEntity.java:2347–2368`). `Entity.dismountTo` calls virtual `teleportTo` (`Entity.java:3347–3349`).
- **Finding:** The 1.15 portal branch places at vehicle X/Z and vehicle top plus `0.001`. The 26.2 fallback starts from rider X/Z and the greater rider/vehicle Y, with the bounded free-position query for small entities. The portal test is against vanilla block state at the vehicle's block position in both versions. This is a server-side dismount response, not historical physics replay.

### Server packet producer and payload

- **1.15:** `ServerPlayer.stopRiding()` calls `super.stopRiding()`, then, if the vehicle changed and `connection != null`, calls `connection.teleport(current X/Y/Z, yRot, xRot)` (`ServerPlayer.java:751–758`). Thus the portal helper's direct `setPos` is followed by this enclosing player-level correction; the portal helper itself does not construct a packet. `ServerGamePacketListenerImpl.teleport` passes `Collections.emptySet()` to its relative-argument overload, records the awaiting position, moves the server player, and sends `ClientboundPlayerPositionPacket` with those same XYZ/rotation values and no relative flags (`ServerGamePacketListenerImpl.java:872–889`). All position and rotation fields in this dismount correction are absolute.
- **26.2:** The dismount helper calls `Entity.dismountTo`, which invokes the player's virtual `teleportTo`. `ServerPlayer.teleportTo(x,y,z)` calls `connection.teleport(PositionMoveRotation(Vec3(x,y,z), Vec3.ZERO, 0, 0), Relative.union(Relative.DELTA, Relative.ROTATION))` (`ServerPlayer.java:1689–1690`). `Relative.DELTA` is `{DELTA_X, DELTA_Y, DELTA_Z, ROTATE_DELTA}` and `Relative.ROTATION` is `{X_ROT, Y_ROT}`; positional `X/Y/Z` flags are absent (`Relative.java:11–20`). The server listener applies the target and sends `ClientboundPlayerPositionPacket.of(id, destination, relatives)` (`ServerGamePacketListenerImpl.java:1261–1269`). Therefore XYZ are absolute; yaw and pitch are relative zero changes; and movement deltas use relative zero changes (including a zero yaw/pitch delta rotation), preserving the current movement vector.

### Local client handling and acknowledgement gates

- **1.15 client:** `ClientPacketListener.handleMovePlayer` has no passenger gate. It adds XYZ only for corresponding relative flags, otherwise assigns the packet values, applies rotation flags, calls `absMoveTo`, then sends `ServerboundAcceptTeleportationPacket(id)` and a position/rotation move packet (`ClientPacketListener.java:669 onward`). For this dismount packet all position/rotation flags are empty: the client applies absolute XYZ/rotation and sets velocity components to zero. The server accepts only the currently awaited teleport ID; on a match it snaps to the saved awaiting XYZ, updates last-good XYZ, conditionally completes dimension change, and clears the awaiting position (`ServerGamePacketListenerImpl.java:378–394`). A mismatched ID is ignored by that gate.
- **26.2 client:** `ClientPacketListener.handleMovePlayer` applies `setValuesFromPositionPacket` only if `!player.isPassenger()`, then sends the teleport acknowledgement and move response regardless of whether it applied the state (`ClientPacketListener.java:795–805`). `PositionMoveRotation.calculateAbsolute` adds current XYZ only for relative position flags, adds current yaw/pitch for relative rotation flags, and adds current delta components for relative delta flags (`PositionMoveRotation.java:40–67`). With this dismount payload, XYZ are absolute while yaw/pitch and delta are preserved through relative zero changes. The source does not establish the player's passenger state when this handler receives the packet. On the server, only a matching teleport ID is accepted; if that ID matches while no awaiting position exists, the listener disconnects for invalid movement; otherwise it snaps to the stored position, updates last-good XYZ, calls `hasChangedDimension`, and clears the saved position (`ServerGamePacketListenerImpl.java:531–550`).

## Bounds

Accepted claims are limited to exact source behavior at 1.15 and 26.2 and the frozen source roots/hashes above. No intervening version, packet arrival ordering, transient passenger state, or runtime parity was inferred or tested. No build, test, gameplay, server, or TAS process was run.
