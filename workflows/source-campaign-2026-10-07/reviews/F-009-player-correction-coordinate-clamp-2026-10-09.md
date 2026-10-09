# Independent blind source review: F-009 player correction coordinate clamp

- Verdict: **REQUEST CHANGES**
- Review date: 2026-10-09
- Pair: 1.19.2 → 1.19.3
- Review scope: only the immutable F-009 snapshot, its paired exact Mojmap sources, and the server packet producer/command path relevant to its reachability. No mod implementation, wiki, or MCPK material was inspected. This is a finding review, not a full-pair audit.

## Immutable candidate identity

- Commit: `aaa61230b12b7b22bbad919ad35802d4ea19c146`
- Path: `workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-009-player-correction-coordinate-clamp.md`
- Git blob: `21f79bf3b7283676cddedc3c1eb64f6793383c24`
- Raw blob SHA-256: `bfbc58498c6c969a75b1287b9e49a612f2e0fe94823df3e2845b5b6779fadf50`

## Publication identity and checked hashes

The candidate's exact Mojmap source-manifest hashes match the read-only cache:

- A, 1.19.2: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.sources.sha256`, SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; artifact manifest SHA-256 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`.
- B, 1.19.3: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap.sources.sha256`, SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; artifact manifest SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`.

The cited hashes below were recomputed from the published source files and match the entries in the corresponding `mojmap.sources.sha256` manifests.

| Source | 1.19.2 SHA-256 | 1.19.3 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3` | `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2` | `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0` | `18f1e4f2ebc30510eefb53f139bb97402cf3cf4e1ba803f95d486e983a76e114` |
| `net/minecraft/server/commands/TeleportCommand.java` | `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a` | `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a` |
| `net/minecraft/world/level/Level.java` | `2ff6b7ab66c73f0fa41136ab83fb8076742c6bde7c3af10dd6f3170d6d536539` | `df465bcb6eb6ceaeb423485070e00737102abb3da064e44c440f69f3aede61c1` |

## Verified source behavior

The claimed client-side conditional delta is source-confirmed.

- In A, `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` resolves each axis from the packet's relative flag, writes preliminary position state, then calls `Player.absMoveTo` (`ClientPacketListener.java`, lines 563–628). `Entity.absMoveTo(double,double,double)` clamps X and Z independently to `[-3.0E7, 3.0E7]`, leaves Y unchanged, and passes the result to `setPos` (`Entity.java`, lines 1202–1209). For a finite resolved absolute X or Z strictly outside the interval, A therefore sets the client player's coordinate to the corresponding endpoint.
- In B, the same handler uses `setPos(resolvedX, resolvedY, resolvedZ)` and does not call `absMoveTo` (`ClientPacketListener.java`, lines 592–667). `Entity.setPos` writes the position and rebuilds the bounding box without a coordinate clamp (`Entity.java`, lines 376–379). Thus, if the handler receives an absolute packet coordinate outside the interval, B retains it in player position and the bounding box.
- The absolute-coordinate condition matters: the X and Z relative-argument bits must both be absent for the packet fields themselves to be used as resolved coordinates. The handler's packet-thread gate is `PacketUtils.ensureRunningOnSameThread`; the packet dispatch path calls `handleMovePlayer`. The `requestDismountVehicle` branch is orthogonal to this coordinate path; no vehicle behavior is relied upon here.

## Server producer and reachability gap

The lower-level sender can construct the described packet, but the reviewed vanilla producer path does not establish that a vanilla caller can supply the required coordinates.

- In both versions, `ServerGamePacketListenerImpl.teleport(...)` stores the requested coordinates in `awaitingPositionFromClient`, applies `player.absMoveTo(...)` to the server-side player, then constructs `ClientboundPlayerPositionPacket` from the original arguments minus offsets selected by the relative flags (`ServerGamePacketListenerImpl.java`, A lines 994–1010; B lines 988–1004). With X and Z relative flags absent, those offsets are zero, so this method passes the original X/Z values through despite the server player's own clamp.
- `ClientboundPlayerPositionPacket` stores X/Y/Z as `double`; its read and write methods use `readDouble` and `writeDouble` without coordinate normalization (`ClientboundPlayerPositionPacket.java`, lines 18–52). The client packet dispatch is therefore technically capable of delivering these values.
- The direct vanilla command route to this sender is gated. `TeleportCommand.performTeleport` first constructs a `BlockPos` and throws when `Level.isInSpawnableBounds` is false (`TeleportCommand.java`, lines 254–285). In both versions, `Level.isInWorldBoundsHorizontal` requires X and Z to be at least `-30000000` and strictly less than `30000000` (`Level.java`, A lines 155–164; B lines 158–167). Since the command checks the block position derived from the requested coordinates, it rejects coordinates outside the claimed clamp interval before the same-level player route calls `connection.teleport`.
- The examined other call sites in the exact server sources pass existing player coordinates or a previously accepted position. The normal non-passenger server movement path applies `clampHorizontal` before accepting incoming X/Z; the pending-correction resend uses the stored server position. I found no vanilla source path in this bounded review that demonstrates an out-of-range finite absolute coordinate reaching the sender. This does not exclude custom server code or a non-vanilla packet producer.

## Required revision

Keep the paired client behavior as a source-confirmed conditional difference, but revise or supplement the reachability/applicability claim. Either identify an exact vanilla call path whose coordinate preconditions pass a finite absolute X/Z beyond the clamp despite the inspected gates, or explicitly scope the scenario to an externally supplied/custom-server packet and state that vanilla producer reachability is unproven. Do not present the existence of an unguarded sender method alone as proof of a reachable vanilla player path.

No finding acceptance or pair-completion status is asserted by this review. Runtime validation was not performed.
