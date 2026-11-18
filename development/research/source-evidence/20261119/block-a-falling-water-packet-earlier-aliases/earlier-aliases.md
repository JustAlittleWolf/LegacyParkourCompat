# Earlier selectable 1.8 aliases for packet-driven correction reach

## Bounded verdict

**ACCEPT earlier-release applicability for exact 1.8 through 1.8.8 (selectable 1.8.0–1.8.8).** The narrowly pinned 1.8.9 absolute-coordinate correction path also exists in the earlier exact Feather sources: a decimal absolute Y is parsed and retained as a `double`, the server sends absolute position values through a packet whose Y is read/written as a `double`, the client applies the absolute Y through `updatePositionAndAngles` and rebuilds the entity box, and an eligible local-player tick passes the same loaded-center-chunk gate before the water-collision check in its inherited tick. No call-boundary semantic exception was found in the checked earlier members.

This is a source-applicability result for the already bounded 1.8.9 witness. It does not widen the accepted falling-water fixture, establish natural movement arrival, persistent block/fluid state, complete-tick stability, trajectories, or full-pair coverage/freeze. There is no runtime or game launch in this review. Exact release ID `1.8` is the canonical directory/release label for 1.8.0.

## Immutable inputs and exact source identity

- 1.8.9 pinned correction-path reference: `falling-current-position-reach/reach.md`, commit `da7859ebc082a8249ee810a5cd5fe254d2f59c1c`, raw Git blob SHA-256 `ffd82f3214da46e76faf572b06211a11384e7c57c2896550a3334f0019b1a27c`.
- Prior accepted shape/scan alias continuity context: `falling-current-18-alias-audit/acceptance-addendum.md`, commit `5a64a143033ca2d8b6d94d4e466af53182fac9f6`, raw Git blob SHA-256 `47335f7f9c4fc2608124a57fee06f55eeedf2328cd8f0f872006833a04cb0e36`.
- Canonical source paths are `decompiled_minecraft/<exact-release>/ornithe-feather/`. `source-pins.json` pins the complete SHA-256 of every checked source member file and each provenance record for exact releases 1.8–1.8.9, plus the 1.8.9 ready marker and source manifest.
- The 1.8.9 tree has the existing `ornithe-feather.ready.json` and publication provenance. Earlier 1.8–1.8.8 trees have no separate `ornithe-feather.ready.json`; their in-tree `.decompile-provenance.json` records were present, and each checked file's actual SHA-256 matched that record's per-source digest. This is a provenance-marker difference, not a missing-source blocker. The 1.8.9 tree's separate readiness marker is also pinned by the earlier immutable reference.
- All source reads were from the canonical source directory; no source generation or writes occurred.

## Compared correction-path members

All paths below are relative to each release's `ornithe-feather/` source root. I compared the members named in the pinned 1.8.9 trace and checked complete source-file hashes first, then inspected members for files whose hashes differ.

- `net/minecraft/server/command/AbstractCommand.java`, `parseTeleportCoordinate(..., int min, int max, boolean center)`: all versions retain the decimal parse into a `double`, the `center=false` path adds no half-block offset, and the returned coordinate preserves the absolute input. In 1.8/1.8.1 the nested coordinate class is split into `AbstractCommand__Coordinate.java`; from 1.8.2 it is nested in `AbstractCommand.java`.
- `net/minecraft/server/command/TpCommand.java`, `run`: the player branch passes `coordinate2.getInput()` into `ServerPlayNetworkHandler.teleport`; absolute Y does not enter a float or integer conversion. The server-relative argument set is empty for absolute coordinates. The 1.8.0–1.8.1 source uses older mapped names/local variable names and an unchecked method signature; call order and values match.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, absolute teleport overload: all releases retain absolute target doubles when the relative set is empty, update the server player position, and send the target through `PlayerMoveS2CPacket`.
- `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java`, `read`/`write`: X/Y/Z use `PacketByteBuf.readDouble`/`writeDouble` throughout the range. Relative flags remain separate from the coordinate values.
- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove`: an absolute Y leaves packet `e` untouched, zeroes Y velocity, then calls `updatePositionAndAngles`. In 1.8/1.8.1 the local player is typed as `LocalClientPlayerEntity`; later sources use `PlayerEntity`. This does not change the assignment or inherited update call.
- `net/minecraft/entity/Entity.java`, `updatePositionAndAngles`/`setPosition`: the coordinate assignment remains double-valued and box construction uses that Y directly. No float or integer conversion was introduced.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick`: all checked releases gate the inherited tick on the center chunk being loaded.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `tick`, and `net/minecraft/entity/Entity.java`, `baseTick`: the ordinary player tick reaches the inherited entity/base tick before travel moves the player. For the pinned ordinary awake, unmounted witness, preceding operations do not write the player's position or shape before `checkWaterCollisions`. The complete `PlayerEntity.tick` member and `Entity.baseTick` member are byte-for-byte source-identical from 1.8.2 through 1.8.9. The 1.8/1.8.1 `baseTick` text differs only in decompiler-local names in its server-side portal branch; that branch does not write player position/shape.

## Alias equivalence and exceptions

Complete relevant files are equal across these groups (SHA-256 values are in `source-pins.json`):

- `AbstractCommand.java`: 1.8.2–1.8.9.
- `TpCommand.java`: 1.8–1.8.1 and 1.8.2–1.8.9.
- `ServerPlayNetworkHandler.java`: 1.8.7–1.8.9. Earlier handler files differ, so their absolute teleport overloads were inspected for every release 1.8–1.8.8; each has the same absolute target initialization, relative-flag guards, player position update, and packet send order.
- `PlayerMoveS2CPacket.java`: 1.8.2–1.8.9.
- `ClientPlayNetworkHandler.java`: 1.8.2–1.8.9.
- `Entity.java`: 1.8.2–1.8.9.
- `LocalClientPlayerEntity.java`: 1.8.2–1.8.9.
- `PlayerEntity.java`: 1.8.2–1.8.9.

The 1.8.0–1.8.1 source family has expected decompiler/mapping differences (`AbstractCommand__Coordinate`, old packet argument and local-variable names, erased `Set` generics, exception-signature variation, and altered line layout). The absolute-coordinate, double packet, client write/shape rebuild, and tick call boundaries were inspected despite nonidentical files. For 1.8.2–1.8.8 the checked complete files or relevant enclosing member bodies match 1.8.9; `ServerPlayNetworkHandler.java` is the only repeatedly changed complete file in this slice, and its absolute teleport overload was checked across each differing release.

No separately narrowed patch interval is claimed; all exact selectable releases 1.8–1.8.8 were checked. The prior acceptance-addendum's different pre-target roster deviation and shape/scan continuity verdict remain its own evidence and are not used as proof of this correction path.

