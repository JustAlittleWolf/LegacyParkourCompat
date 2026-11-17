# Terminal

- Assignment: exact 26.2 native `ClientPacketListener.handleExplosion` source map only.
- Operation map: `build/mcpk-20261116/vd-full-checklist/explosion-262-source-map/operation-map.md`
- Operation map SHA-256: `8DF44EE935577B7B6B6949CCD443DDBE6199D37C0C8D6F35C64F2657961E8BD3`
- Exact source output: `decompiled_minecraft/26.2/unobfuscated/`
- Source manifest SHA-256: `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894` (ready marker digest matches; selected source file hashes match entries).
- Relevant operation: `ClientPacketListener.handleExplosion(ClientboundExplodePacket)`, lines 1359-1377; `playerKnockback().ifPresent(this.minecraft.player::addDeltaMovement)` at 1376, after client-thread scheduling and presentation effects. `Minecraft.player` is `LocalPlayer`; `Entity.addDeltaMovement` is the inherited consumer. Server source producer: `ServerLevel.explode`, lines 1240-1244, creates an optional from `ServerExplosion.getHitPlayers()` and sends the clientbound packet to nearby `ServerPlayer` connections.
- Assigned accepted finding SHA verified: `FE2E2D4D066E9E77CA43A65578D6306E6CF76F88BF1D4D2F52ECBCB655DD4EDE`. Independent audit SHA verified: `12FE686B01A13F46551AD8019AE77161037BE6AE30162FB959799EBE7C0562FF`. Boundary-check SHA verified: `C112F7E189F20FE22CCE0E46726DF1092BF95FCEF3DBEADE57CB821E5835C805`.
- No product source, finding, backlog, wiki, tests, build, or runtime files were changed or examined for this bounded mapping task.
