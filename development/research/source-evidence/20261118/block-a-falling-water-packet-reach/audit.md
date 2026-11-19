# Independent audit: packet-driven falling-water fixture reach

## Verdict

**ACCEPT**, limited to exact Minecraft 1.8.9, Ornithe Feather build.2, and packet-driven position reach. The cited vanilla server command path can place an ordinary local player at the exact call-boundary coordinates in the accepted falling-water fixture. After the correction is handled, an eligible next local-player tick reaches `Entity.checkWaterCollisions` at those coordinates with a box rebuilt from them, before ordinary travel movement writes a new position.

This is not a claim that unaided movement reaches the exact fractional double, that the block arrangement naturally forms or persists, or that the rest of the tick remains at this location. The fixture's block states and required chunks must still be present when the check runs.

## Immutable inputs

- Reach claim: `falling-current-position-reach/reach.md`, author commit `da7859ebc082a8249ee810a5cd5fe254d2f59c1c`; raw blob SHA-256 verified as `ffd82f3214da46e76faf572b06211a11384e7c57c2896550a3334f0019b1a27c`.
- Existing fixture: `falling-current-boundary/evidence.md`, commit `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d`; raw blob SHA-256 verified as `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c`.
- Prior fixture verdict: `falling-current-boundary-audit/audit.md`, commit `72511e30f735be5c245b88d07f5787d268ce0cb1`; raw blob SHA-256 verified as `5329f89c38e4d8ea9012574b43701d66414b1020e0297b0d0534c43179737108`.

All evidence blobs were retrieved with `git -C <scratch repo> cat-file blob` and hashed as raw bytes. The two fixture inputs' hashes match their supplied identities. The fixture audit accepts the liquid scan/contribution only at its stated call boundary; this audit addresses only local-player position reach to that boundary.

## Exact source identity

Canonical read-only source: `decompiled_minecraft/1.8.9/ornithe-feather`, ready for version `1.8.9`, mapping `ornithe-feather`, Feather build.2. The ready marker records source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. For each file below, the current raw file SHA-256 was recomputed and matched its entry in `ornithe-feather.sources.sha256`:

| Source | Relevant member | SHA-256 |
|---|---|---|
| `net/minecraft/server/command/AbstractCommand.java` | `parseDouble`, `parseTeleportCoordinate`, `Coordinate.getInput` | `3f531b41115f1029588c9d64d6d1276f80e9eb1159d7d51b8651cdc44a64dc5c` |
| `net/minecraft/server/command/TpCommand.java` | player-coordinate `execute` branch | `1c174612d55379498cf1eea7186354f824ddd7e51efea7499ad4767e533874bd` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `teleport` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` |
| `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` | constructor, `read`, `write`, argument flags | `3c2c044610df527df20561b4ded36aca91c774b7d0520f9e4cfe657030bf1baf` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| `net/minecraft/entity/Entity.java` | `setPosition`, `setPositionAndAngles`, `baseTick`, `checkWaterCollisions` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `tick` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `tick` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/entity/living/LivingEntity.java` | `tick` / travel order | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |

## Independent trace

1. `AbstractCommand.parseDouble` uses `Double.parseDouble`. In `parseTeleportCoordinate`, `center` is applied only to a whole-number, non-relative input (`!contains(".") && !relative`). The command's decimal X/Y/Z inputs therefore receive no half-block addition. `TpCommand.execute` parses Y with `center=false`; decimal X and Z also bypass centering. For a `ServerPlayerEntity`, absolute coordinates do not add X/Y/Z relative flags. Thus `/tp <player> 0.2 62.60100005364418 0.5 0 0` supplies the exact parsed double Y, X, and Z; explicit yaw/pitch avoid an unrelated orientation choice.
2. `ServerPlayNetworkHandler.teleport` receives the empty relative set, stores absolute targets, calls server `updatePositionAndAngles`, then constructs `PlayerMoveS2CPacket` with the supplied coordinates and empty flags. Packet serialization uses three `writeDouble` calls, and decoding uses three `readDouble` calls; the relative-argument set is serialized separately as one flags byte. No float or integer coordinate representation occurs.
3. On the client, `handlePlayerMove` is marshalled to the client thread by `PacketUtils.ensureOnSameThread`. For each absolute position axis, the corresponding velocity field is set to `0.0`; the empty set leaves the packet values unchanged. It calls `playerEntity.updatePositionAndAngles(d,e,f,g,h)`. `Entity.setPositionAndAngles` assigns `prevY = lastY = y = targetY` and then calls `setPosition`. `setPosition` writes the same double Y and rebuilds the box using the current player width and height (`new Box(x-width/2, y, z-width/2, x+width/2, y+height, z+width/2)`). No coordinate rounding intervenes.
4. On the next eligible client tick, `LocalClientPlayerEntity.tick` first checks the center chunk at `(x,0,z)` and only calls `super.tick()` if it is loaded. `PlayerEntity.tick` reaches `super.tick()` in its ordinary awake, nonspectator path. `LivingEntity.tick` starts with `super.tick()`, which reaches `Entity.baseTick`. The client-side `baseTick` does not enter the server-only portal branch; it updates last-position bookkeeping, calls `tickSprintingEffect`, then `checkWaterCollisions`. None of those operations writes position or shape. Living travel and its movement occur after `baseTick`, later in the inherited tick path. Thus the first water check after packet application sees the packet position and rebuilt box, provided no other event writes position before that tick and the center-chunk gate passes.
5. At `y=62.60100005364418`, the accepted fixture's exact source-order arithmetic `y + (double)1.8F + (double)-0.4F - 0.001` yields contracted maximum Y `64.0`. This establishes that the packet-reachable entity box meets the already accepted scan boundary. It does not retest or broaden that scan fixture.

## Conditions and limits

Applies to an ordinary awake, nonspectator, nonflying, unmounted local player receiving and applying this absolute correction, with the center chunk loaded. There must be no intervening position-changing event between packet handling and the eligible tick; the claim does not guarantee packet-to-tick scheduling or world block/chunk persistence. The command is a source-supported way to request the state, not evidence of continuous natural movement. The server teleport handler itself dismounts its target, but the simple applicability statement assumes the client is unmounted when its next water check runs.

No aliases, implementation, product Git history, wiki material, tests, builds, game/runtime execution, or source generation were consulted or performed. No canonical source was changed.