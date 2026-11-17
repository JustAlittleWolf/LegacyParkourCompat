# End Portal contact callback: 1.8.9 through 1.9.4

## Result

The first checked release with the new End Portal contact predicate is **1.9**. In 1.8.9, an entity whose current box enters the block's unit cell is sent to `changeDimension(1)` when it has neither vehicle nor rider, on the server. In 1.9, the server call additionally requires no riding/passengers, `canUsePortals()`, and a positive intersection between the entity box and the End Portal block's 0.75-high shape. These callback conditions remain unchanged in 1.9.1, 1.9.2, 1.9.3 and 1.9.4.

This is server-authoritative dimension-transition eligibility, not a direct local movement response. The block callback has no local position/velocity write and rejects dimension change on `world.isClient`. Client world/position changes are handled from server respawn and player-move packets. Whether the server-only transition predicate belongs in a movement finding remains a scope decision; no canonical finding draft is included.

## Source path and contact geometry

- Block registration resolves `end_portal` to `Blocks.END_PORTAL`: `net/minecraft/block/Blocks.java:350` (1.8.9), `:375` (1.9–1.9.2), `:377` (1.9.3–1.9.4).
- 1.8.9 `net/minecraft/block/EndPortalBlock.java:31–43,61–65`: the old block's `updateShape` sets a 1/16-high shape, `addCollisions` is empty, and the callback checks only `vehicle == null`, `rider == null`, and `!world.isClient` before `changeDimension(1)`.
- 1.9 `net/minecraft/block/EndPortalBlock.java:20,33–35,62–70` (callback body unchanged through 1.9.4): the callback tests `!isRiding()`, `!hasPassengers()`, `canUsePortals()`, `!world.isClient`, and `entity.getShape().intersects(state.getShape(world, pos).move(pos))`; the block shape is `[0,1] × [0,0.75] × [0,1]`. `addCollisions` remains empty in all checked versions; this is not a solid-block collision change.
- Entity contact dispatch follows movement: `net/minecraft/entity/Entity.java:608,648–660` (1.8.9) and `:692,737–750` (1.9.4). `checkBlockCollisions` enumerates block coordinates from the current entity box with `+0.001`/`-0.001` inset and invokes each block callback. It does not itself require an intersection with the block's movement shape.
- Player dimensions are `0.6F × 1.8F`: `net/minecraft/entity/living/player/PlayerEntity.java:417` (1.8.9), `:387` (1.9–1.9.2), `:388` (1.9.3–1.9.4). A reachable post-move state with horizontal overlap and `minY` in `(portalY + 0.75, portalY + 1)` still dispatches the callback for that block cell. The 1.8.9 predicate accepts it; the newer shape intersection rejects it. This is a contact/eligibility difference, not a changed local velocity calculation.
- Client authority trace: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:517–551,798–818` (1.8.9) and `:546–581,863–883` (1.9.4) apply `PlayerMoveS2CPacket` coordinates and `PlayerRespawnS2CPacket` dimensions. This establishes packet-dependent local state; server teleport implementation was not examined.

## Release-by-release callback check

| Release | EndPortalBlock SHA-256 | Checked callback result |
|---|---|---|
| 1.8.9 | `1cb419f44592e68244e06b34799c6354a2bcc3d08bd849e0709fca36b54e1e40` | vehicle/rider + server gate only |
| 1.9 | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` | first checked release with new entity eligibility + 0.75-shape intersection |
| 1.9.1 | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` | byte-identical to 1.9 |
| 1.9.2 | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` | byte-identical to 1.9 |
| 1.9.3 | `7afd1729a13ee3e14e191a6fb2ce41b847a94fc01fac526a4121183074da8650` | callback body unchanged; file differs outside it, including nullable annotation on `addCollisions` |
| 1.9.4 | `7afd1729a13ee3e14e191a6fb2ce41b847a94fc01fac526a4121183074da8650` | byte-identical to 1.9.3 |

## Source identity and publication evidence

All six release trees use the `ornithe-feather` mapping namespace. The 1.8.9 and 1.9.4 endpoint provenance/ready records identify Feather build.2, Vineflower 1.12.0 and Java 25.0.3+9-LTS; their source manifests match the ready markers' expected hashes. Each of the six cited source files at both endpoints matches its source-manifest entry. The 1.9.4 verification record reports published source files verified identical.

For 1.9–1.9.3, literal reads of `ornithe-feather/.decompile-provenance.json` found in-tree provenance records. The listed SHA-256 for every cited `Blocks.java`, `EndPortalBlock.java`, `Entity.java`, `PlayerEntity.java`, `LocalClientPlayerEntity.java` and `ClientPlayNetworkHandler.java` matches the current file in that release. The records pin version-specific client and Mojang metadata, Feather version metadata, and the corresponding Feather build.2 merged mapping artifact. Thus the new predicate's first checked appearance at 1.9 is supported by the exact local source/input records, rather than inferred from a later endpoint. The in-tree records are local provenance data, not signed publisher attestations; the recorded input artifacts were not re-fetched or independently re-hashed in this static task.

| Release | In-tree provenance SHA-256 | Feather mapping artifact SHA-256 | Feather metadata SHA-256 | Client jar SHA-256 | Version metadata SHA-256 |
|---|---|---|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688` |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56` |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `a0b96ff1dabf71650c40957b615188067e6ee338e3a30522b4c3fd8613a25c0f` |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` | `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985` | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` | `2ec3b7abc12f0ad4d4e3c777dc0c429fa948ffd4d1bdbb89a81aecbd30021be5` |

Input URL patterns are `https://maven.ornithemc.net/releases/net/ornithemc/feather-gen2/<version>+build.2/feather-gen2-<version>+build.2-mergedv2.jar`, `https://meta.ornithemc.net/v3/versions/feather/<version>`, `https://piston-data.mojang.com/v1/objects/<client-object-hash>/client.jar`, and `https://piston-meta.mojang.com/v1/packages/<version-metadata-object-hash>/<version>.json`.

The remaining barrier is scope: the accepted source change only controls a server-side dimension transition, and the local player applies resulting world/position state from packets. No server teleport implementation, solid movement-blocker shape work, runtime, test, or build was performed.