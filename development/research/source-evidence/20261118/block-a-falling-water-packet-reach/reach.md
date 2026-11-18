# Packet-driven reach of the falling-water boundary fixture

## Bounded verdict

**REACHABLE, packet-driven only.** In exact Minecraft 1.8.9, an ordinary local player can arrive at the already accepted call-boundary position `(x=0.2, y=62.60100005364418, z=0.5)` through the vanilla server teleport correction path. After the correction is applied, the next eligible local-player tick reaches `Entity.checkWaterCollisions` with that position and its correspondingly rebuilt player box, before that tick's ordinary travel/movement writes. This closes player-position call-boundary reach for the accepted falling-water fixture.

This does not claim that unaided continuous movement lands on that exact double, that a complete natural tick remains at the position, or that the water/fence arrangement is produced or persists naturally. The prior fixture is only composable if its block states and needed chunks still hold when the check executes. Scheduled world fluid updates and other world evolution are independent of the packet-driven position result and are not simulated here.

## Exact 1.8.9 source trace

All paths are relative to `decompiled_minecraft/1.8.9/ornithe-feather/`.

- `net/minecraft/server/command/AbstractCommand.java`, `parseTeleportCoordinate` and `Coordinate.getInput`: an absolute decimal coordinate is parsed by `parseDouble` into a `double`; no grid rounding is applied when `center=false` (about lines 300–335, 505–522).
- `net/minecraft/server/command/TpCommand.java`, player branch in `execute`: for a `ServerPlayerEntity`, absolute coordinates produce an empty relative-argument set and are passed as `getInput()` values to `networkHandler.teleport` (about lines 70–111). Thus the command `/tp <player> 0.2 62.60100005364418 0.5 0 0` supplies the target Y as the parsed double.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, `teleport`: with the empty relative set, it stores the provided absolute coordinates, calls the server player's `updatePositionAndAngles`, and sends `PlayerMoveS2CPacket(x,y,z,yaw,pitch,relativeArgs)` (about lines 368–396).
- `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java`, `read`/`write`: X/Y/Z are encoded and decoded with `PacketByteBuf.writeDouble`/`readDouble`; relative flags are carried separately. The absolute empty set therefore preserves the supplied Y double across packet serialization.
- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove`: absolute X/Y/Z flags cause each corresponding velocity component to be set to `0.0`; it then calls `playerEntity.updatePositionAndAngles(d,e,f,g,h)`. For the empty relative set, `e` remains exactly packet Y.
- `net/minecraft/entity/Entity.java`, `updatePositionAndAngles` and `setPosition`: the local client writes `lastY = y = targetY`, then `setPosition` writes `this.y` and rebuilds the shape as `new Box(x-width/2, y, z-width/2, x+width/2, y+height, z+width/2)`. No float conversion or rounding is applied to Y.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick`, gates `super.tick()` on the center chunk being loaded. With the fixture's center chunk loaded, the inherited player/entity tick runs.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `tick`, has no position-changing operation before `super.tick()` in the ordinary awake, nonspectator case. `net/minecraft/entity/Entity.java`, `baseTick`, updates last-position bookkeeping and calls `tickSprintingEffect()` before `checkWaterCollisions()`; those preceding operations do not write position or shape. Travel and its `move(...)` call happen later in the living-entity tick. Hence the first water-collision check after packet application sees the packet position/box, before later movement changes them.

At the inherited shape geometry used by the accepted witness, Java evaluates `62.60100005364418 + (double)1.8F + (double)-0.4F - 0.001` in source order to exactly `64.0`. The `-0.4F` widening and final double contraction are important. The existing accepted evidence records the full liquid-scan and falling-flow fixture; this addendum establishes only that ordinary vanilla server correction can place the local player at its call boundary.

## Applicability and limits

- Applies to exact 1.8.9, Ornithe Feather, packet-driven absolute teleport/correction of an ordinary, unmounted, nonspectator, nonflying local player, provided the center chunk gate passes.
- A vanilla command such as `/tp <player> 0.2 62.60100005364418 0.5 0 0` is one source-supported way to request the exact coordinates. This is an externally directed correction, not a continuous movement trajectory.
- Absolute correction zeroes local velocity components and applies the packet yaw/pitch; choosing `0 0` avoids an unrelated orientation change. Player box dimensions are the ordinary inherited width/height used by the accepted fixture.
- This does not establish no intervening client world tick, natural persistence of the prescribed block arrangement, a stable full tick, or any trajectory after the water effect. Fluid scheduling and block updates remain outside this bounded reach claim.

## Evidence inputs

The accepted fixture and its independent audit were read from the expressly permitted immutable evidence revisions:

- `falling-current-boundary/evidence.md`, commit `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d`, expected Git blob SHA-256 `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c` (working-file SHA-256 recorded there: `595a2a01ebe0e87320815fc2b7d23043d10b74ee1fa7971c44cda9bacf75e795`).
- `falling-current-boundary-audit/audit.md`, commit `72511e30f735be5c245b88d07f5787d268ce0cb1`, expected SHA-256 `5329f89c38e4d8ea9012574b43701d66414b1020e0297b0d0534c43179737108`.
- `falling-current-18-alias-audit/acceptance-addendum.md`, commit `5a64a143033ca2d8b6d94d4e466af53182fac9f6`, expected SHA-256 `47335f7f9c4fc2608124a57fee06f55eeedf2328cd8f0f872006833a04cb0e36`.

Canonical 1.8.9 source publication identity is the exact version and mapping path `decompiled_minecraft/1.8.9/ornithe-feather`; prior permitted evidence confirms its ready marker, source manifest and relevant source-file hashes. This worker did not generate or modify canonical sources.
