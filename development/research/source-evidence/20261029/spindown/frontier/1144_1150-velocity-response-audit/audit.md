# Independent blind audit: 1.14.4 → 1.15 velocity response

## Verdict

**Substance confirmed; one citation correction.** The bounded clientbound velocity-response claims in `1144-1150-velocity-response-closure/REPORT.md` match the exact Mojmap sources. No response-path delta or local-player lookup fallback was found. The report hash supplied for review, `aa6c42c77f0018190850831f0b1e349e1eb1b058f4126e526803806cd1455923`, matches the report file. Its 1.14.4 respawn anchor should also cite `ClientPacketListener.java:1013–1018`, where the old local player's ID is captured into `i`; the existing `1041–1053` anchor covers applying and inserting that ID into the replacement player, but not its source.

## Identity and source integrity

I independently checked the canonical trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `...\1.15\mojmap`, along with their adjacent provenance, ready-marker, and source-manifest files.

| Release | Provenance SHA-256 | Ready SHA-256 | Source-manifest SHA-256 | Manifest entries / bytes |
|---|---|---|---|---:|
| 1.14.4 Mojmap | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | 3,250 / 17,080,207 |
| 1.15 Mojmap | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | 3,329 / 17,522,461 |

Both ready markers declare the hashes above and their stated release IDs, counts, bytes, and client input hashes. I checked every manifest row against the corresponding source file: all 3,250 and 3,329 entries respectively exist and hash correctly, with no missing or mismatched entries. The 1.14.4 provenance identifies requested release `1.14.4` and mapping option `feather,mojmap`; the ready marker selects release `1.14.4` / `mojmap`. The 1.15 provenance and ready marker identify requested/selected release `1.15` and Mojmap. Its preparation batch also included `1.15.1`, but the selected source root and ready identity are specifically `1.15`.

All report-cited source-file hashes match both direct SHA-256 and their manifest entries. In particular, `ClientboundSetEntityMotionPacket.java` is byte-identical in both releases with SHA-256 `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762`.

## Response-path trace

- The packet decodes the VarInt entity ID followed by three shorts, and dispatches to `handleSetEntityMotion`. Its matching file hash establishes identical representation and dispatch in the two selected releases.
- In both `ClientPacketListener` sources, the handler first calls `PacketUtils.ensureRunningOnSameThread`, obtains `this.level.getEntity(packet.getId())`, and only when non-null calls `lerpMotion` with each packet component divided by `8000.0`. There is no player fallback or movement call in the handler. A missing map entry is a no-op.
- Initial login derives the integer key from `ClientboundLoginPacket.getPlayerId()` and passes it to `level.addPlayer(i, minecraft.player)` in both releases; it then sets the player object's ID to `i`. On respawn, both retain `localPlayer.getId()`, set that ID on the replacement `LocalPlayer`, and insert the replacement with `addPlayer(i, localPlayer2)`. Each level's `addPlayer` routes through `addEntity`, which inserts into `entitiesById` under the supplied key, and `getEntity(i)` returns that map entry. Thus a packet using the local player's current entity ID can resolve to that player.
- `Entity.lerpMotion` directly calls `setDeltaMovement(d,e,f)`, whose `Vec3` overload assigns `deltaMovement`. `LocalPlayer`, `AbstractClientPlayer`, `Player`, and `LivingEntity` do not declare an override of either response method in either source tree. A successful lookup therefore replaces the local player's stored velocity immediately; it does not itself displace the player.
- The separate explosion handler in both releases finalizes the client explosion, then directly sets `minecraft.player`'s delta movement to its existing value plus the packet's three knockback components. This is a separate direct local-player response and is not a fallback from the entity-motion handler.

## Downstream 1.15 factor note

The report correctly treats this as separate downstream movement behavior. In 1.15, `LivingEntity.travel` passes `getDeltaMovement()` to `move(MoverType.SELF, ...)`; `LocalPlayer.move` delegates to `super.move`. `Entity.move` performs collision displacement and block-contact handling before line 543 multiplies stored X/Z delta movement by `getBlockSpeedFactor()`. The base getter checks the current block and may use the supporting block when the current factor is `1.0F`, except for water and bubble columns. `Player.getBlockSpeedFactor()` returns `1.0F` while abilities-flying or fall-flying. The 1.14.4 `Entity` source has no corresponding getter or end-of-move multiplication.

Precision: the 1.15 factor is after the displacement for that `move` call, so it changes residual velocity rather than retroactively scaling that call's displacement. That qualification does not alter the report's separate-consumer conclusion; the residual can feed subsequent movement. No trajectory or packet-timing conclusion was used.

## Evidence anchors

The report's line anchors and hashes were checked directly in the sources:

- Packet: both versions, `ClientboundSetEntityMotionPacket.java:10–69`.
- Listener: 1.14.4 `ClientPacketListener.java:334–365, 542–552, 1013–1018, 1041–1085`; 1.15 `ClientPacketListener.java:339–376, 554–564, 1042–1115`.
- Entity ID map: 1.14.4 `MultiPlayerLevel.java:268–280, 311–315`; 1.15 `ClientLevel.java:282–295, 325–329`.
- Velocity write: 1.14.4 `Entity.java:1770–1772, 2675–2680`; 1.15 `Entity.java:1733–1735, 2680–2685`.
- 1.15 downstream consumer: `LivingEntity.java:1891`, `LocalPlayer.java:851–855`, `Entity.java:450–543, 590–602`, and `Player.java:2035–2038`.

This audit is limited to the assigned client response closure. Server packet production, scheduling/delivery timing, and a full movement-tick dependency audit were not examined.
