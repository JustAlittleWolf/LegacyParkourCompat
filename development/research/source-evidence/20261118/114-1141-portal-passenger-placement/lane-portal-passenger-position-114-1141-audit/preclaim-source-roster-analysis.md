# Pre-claim source roster and analysis: 1.14 → 1.14.1 passenger dismount / position correction

Status: independent pre-claim source review complete for the bounded operation below. No candidate finding text or author claim was supplied, and no product implementation, wiki lane, backlog, or Git history was consulted. This artifact records source facts and a no-difference result for the examined operation; it is not full-pair coverage or runtime-parity acceptance.

## Frozen exact source identities

Both sides use the common Yarn mapping family, with a distinct exact Yarn build per release. Sources are under `decompiled_minecraft/<release>/yarn/`. Local `.decompile-provenance.json` files pin the Minecraft client, intermediary, Yarn mapping, tooling/runtime and per-source digests; all listed relied-on source digests below matched the corresponding manifest `sources` entry when recomputed from the raw file bytes.

- 1.14: client `piston-data…/7a762a59345c13af7d87111207a93f5a8607f6c0/client.jar`, SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`; Yarn `1.14+build.21`, SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; intermediary `1.14`, SHA-256 `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17`; provenance manifest raw SHA-256 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`.
- 1.14.1: client `piston-data…/55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar`, SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`; Yarn `1.14.1+build.10`, SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; intermediary `1.14.1`, SHA-256 `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d`; provenance manifest raw SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`.

### Frozen relevant source roster (raw Java SHA-256)

| Relative source path | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/server/network/ServerPlayerEntity.java` | `6e7b36cc3ef188fe1d144685bcd6620c363f2b95ac9e52b41df310babb325bae` | `6e7b36cc3ef188fe1d144685bcd6620c363f2b95ac9e52b41df310babb325bae` |
| `net/minecraft/server/network/ServerPlayNetworkHandler.java` | `325e51b0b3ba3aa7e796133dda76d8ef566c601d182dbfcc0df54379eeb55264` | `916e7131a20a693fc06f74a2231801f309c5f67c89e61610388bc71eba0db071` |
| `net/minecraft/client/network/ClientPlayNetworkHandler.java` | `4fdd53d82e601147747b01b021a15482135f624c809a2722bb6a449ce272791f` | `13db0a2baa0a2c986c795a2990eab64f3d9a4dc121e360dbd04c2ea84310ddd7` |
| `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java` | `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2` | `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2` |
| `net/minecraft/block/PortalBlock.java` | `4d7ea7eac1026673792084935dc2f2c12bb4e9c395aca57fa7a0a3d6ff6201c3` | `4d7ea7eac1026673792084935dc2f2c12bb4e9c395aca57fa7a0a3d6ff6201c3` |
| `net/minecraft/block/EndPortalBlock.java` | `b071fb5b9a176d86ffaa95b9201ffb3070653589dedd5a1ef5dd697e14cc90bf` | `b071fb5b9a176d86ffaa95b9201ffb3070653589dedd5a1ef5dd697e14cc90bf` |

The actual `PlayerPositionLookS2CPacket` path is `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java` on both sides. The two files are byte-identical; likewise the named `PortalBlock`, `EndPortalBlock`, `PlayerEntity`, `ServerPlayerEntity`, and position/look packet files are byte-identical. Unequal whole-file hashes elsewhere do not themselves imply this operation changed; the exact methods below were compared in context.

## Bounded comparison and source trace

- `Entity.stopRiding`: 1.14 lines 1589–1595; 1.14.1 lines 1589–1595. Same implementation: when `vehicle != null`, save the vehicle, clear `this.vehicle`, then call the saved vehicle's `removePassenger(this)`.
- Generic living dismount gate: `LivingEntity.stopRiding`, 1.14 lines 2306–2312; 1.14.1 lines 2306–2312. Same order and predicates: capture current vehicle; call `super.stopRiding()`; call the virtual `method_6038(oldVehicle)` only if the old vehicle was non-null, the current vehicle changed, and `!world.isClient`.
- Generic stand-up helper: `LivingEntity.method_6038(Entity)`, both versions lines 1650–1723. Same body. Boat and horse vehicles use the separate lateral set-position branch. Other vehicles enter the safe-position search only if movement direction is non-null; candidates are checked in fixed array order, including collision, solid-top and fluid conditions. The helper can call virtual `requestTeleport` (first solid-top candidate or final selected/fallback coordinates); exact `.001` and `1.001` offsets and candidate loop order match.
- Player relation gate: `PlayerEntity.stopRiding`, both versions lines 957–960, calls living behavior then resets `ridingCooldown = 0`; same source bytes.
- Server correction: `ServerPlayerEntity.stopRiding`, both versions lines 759–766, captures old vehicle, calls `super.stopRiding()`, reads vehicle again, then sends `networkHandler.requestTeleport(x,y,z,yaw,pitch)` only if the relation changed and handler is non-null. Same source bytes. `ServerPlayerEntity.requestTeleport(double,double,double)`, both versions lines 1081–1083, directly delegates to the handler with current yaw/pitch. Consequently the helper's inner virtual `requestTeleport` can produce a server correction, and the outer server-player override can issue another absolute correction after `super` returns when a vehicle was actually detached. Boat/horse helper placement uses `setPosition`, so that inner request path is absent for those vehicle types; the outer relation-change correction still applies.
- Portal entry dependency: `PortalBlock.onEntityCollision`, both versions lines 101–103, only calls `setInPortal` when the entity has neither a vehicle nor passengers and `canUsePortals()`. Thus a currently mounted passenger does not enter the portal collision route; it is not automatically dismounted into a portal. `EndPortalBlock` has the same source hash across the pair. A case-insensitive search for the literal `PORTALS` in both mapped source trees found no such named branch; the relevant portal relation gate is the explicit `hasVehicle`/`hasPassengers` predicate above.
- Server correction wire path: `ServerPlayNetworkHandler.requestTeleport`, both versions around lines 799–801, delegates with `Collections.emptySet()`. The shared `teleportRequest` body computes relative offsets only for flags present, updates the server position, and writes packet coordinates minus those offsets. With the empty flag set, x/y/z are absolute. Method code is identical across the pair.
- Logical client correction: `ClientPlayNetworkHandler.onPlayerPositionLook`, both versions lines 642–697. For each XYZ axis independently, a relative flag adds the packet delta to the current coordinate/render coordinate and preserves that velocity component; absence of the axis flag sets the previous render coordinate to the packet coordinate and zeros that velocity component. The position/angle writer then receives the resolved xyz. This flag handling is identical. The packet class has identical raw bytes.

## Pre-claim verdict

No changed movement operation was found in the bounded 1.14→1.14.1 slices above. The generic helper, its side/relationship gates, the server player's outer correction, the empty-flag absolute XYZ correction, and client XYZ flag application are unchanged. Portal collision gates riders out rather than providing an explicit portal dismount branch. This result does not establish a broader release-pair freeze or runtime arrival/parity.

## Raw artifact identity

The adjacent `.sha256` file contains the full SHA-256 of these exact UTF-8/LF bytes.