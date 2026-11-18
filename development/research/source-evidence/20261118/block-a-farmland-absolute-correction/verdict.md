# Farmland historical receiver audit verdict

**Terminal: COMPLETED.** Scope: independent receiver/packet-route audit of farmland relocation for exact 1.11.2, 1.12, 1.12.1, and 1.12.2 Ornithe Feather sources. This does not repeat the already accepted server-only trigger scope or the first stable 1.12.2 relocation math change.

## Verdict

**Accept the bounded receiver claim:** all four older closures route an affected `ServerPlayerEntity` through inherited `Entity.setPosition(double,double,double)`, which is not overridden in `PlayerEntity` or `ServerPlayerEntity`; this direct world-side position writer does not invoke the player's network correction producer. In 1.12.2 the closure instead calls virtual `Entity.teleport(double,double,double)`. `ServerPlayerEntity` overrides that method in every compared version, delegating to `networkHandler.teleport(x,y,z,yaw,pitch)`. Thus only 1.12.2's new farmland caller dynamically enters the already-existing server-player correction route.

In 1.12.2, the 5-argument `ServerPlayNetworkHandler.teleport` immediately delegates to its overload with `Collections.emptySet()`. That overload sets `requestedTeleportPosition` to the supplied absolute `(x,y,z)`, increments/resets the teleport id, updates the server player with that position and current yaw/pitch, then calls `player.networkHandler.sendPacket(new PlayerMoveS2CPacket(x,y,z,yaw,pitch,relativeArgs,requestedTeleportId))`. Since the relative set is empty, the immediate S2C payload is the absolute farmland-derived coordinates, the player's current yaw/pitch, no relative flags, and the new teleport id. It is `PlayerMoveS2CPacket`, not a movement input packet. Its constructor and `write` method encode doubles x/y/z, floats yaw/pitch, one flags byte, and the teleport id VarInt.

The paired client receiver is `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)`. Its complete body is identical across these four versions: with empty relative flags it zeros all three player velocity components; sets position/angles from the packet; sends `AcceptTeleportC2SPacket(teleportId)`; and sends the same `PlayerMoveC2SPacket.PositionAndAngles` response using `(player.x, player.getShape().minY, player.z, player.yaw, player.pitch, false)`. There is no paired client-side receiver delta for equivalent correction inputs in 1.12.2. Do not split out a client receiver finding from this bounded observation.

This verdict covers only the receiver and packet route. It does not establish an exact patch boundary between the concrete releases, runtime parity, or a new implementation requirement.

## Evidence identity and source roster

Independent blind source roster: `source-roster.md`, SHA-256 `99c88a5a28d948d3d753089b9752edd3473505188ca2873da189f77fb1b4b4d5`.

Supplemental route closure for the paired client handler: `source-closure.md`.

All source bytes were checked against the per-release external source indexes: `ornithe-feather.sources.sha256` for 1.11.2/1.12.2 and hidden `ornithe-feather/.decompile-provenance.json` `sources` maps for 1.12/1.12.1. The complete input identity, index hashes, and source hashes are recorded in the source roster and closure.

Author evidence independently audited after the source roster freeze: `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/portal-farmland/farmland-historical-receiver/receiver.md`, UTF-8 LF SHA-256 `bc0151b3c648d21101fd9920ff779af7ad031235aa4190fc9473343831f738de`. This verdict is bound to that exact report revision and to the exact release/mapping/source identities above.

No product, wiki, or product history was consulted. No build, tests, game/server launch, decompilation, or primary-source writes were performed.