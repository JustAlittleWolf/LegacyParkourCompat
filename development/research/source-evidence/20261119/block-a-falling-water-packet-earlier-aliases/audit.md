# Independent audit: earlier 1.8 packet-driven falling-water reach aliases

## Verdict

**ACCEPT earlier-release applicability for exact 1.8 through 1.8.8 (selectable 1.8.0–1.8.8).** The bounded 1.8.9 absolute-coordinate correction reach already accepted in the referenced evidence carries through the earlier exact Feather build.2 sources. Decimal absolute coordinates remain doubles; absolute position flags are absent; packet Y is written and read as a double; the client zeros Y velocity and applies that same double through the position update that rebuilds the player box. On an eligible next local-player tick, the center-chunk-loaded gate precedes the inherited tick, whose base tick checks water before ordinary travel movement.

This verdict extends only applicability of the accepted 1.8.9 packet-driven position reach. It does not reopen the liquid fixture, natural/unaided arrival, block or chunk persistence, whole-tick stability, trajectories, or pair coverage. It assumes the same ordinary awake, unmounted, nonspectator local player and the same required loaded center chunk, with no intervening position-changing event.

## Immutable inputs and source identity

- Earlier-alias claim: `falling-position-earlier-aliases/earlier-aliases.md`, commit `764c6cae3765b4ed7dcb6d522584393193a321e0`, raw Git blob SHA-256 `e784d47ce0264640083667a717946f9af3825df71d6ffe54b6f5d0552aba78f5`.
- Alias pin payload: `falling-position-earlier-aliases/source-pins.json`, same commit, raw Git blob SHA-256 `1f02986b6a77b2850c230c107af1a160ab7dc5c6058d308731d95401eb66a3e2`. Parsed as UTF-8 JSON; schema `1`, mapping `ornithe-feather`, ten unique exact release IDs `1.8` through `1.8.9`, per-release provenance and file SHA-256 maps, and the separately declared 1.8.9 readiness/manifest pins. The claim markdown and JSON raw-byte hashes match their supplied identities.
- Existing 1.8.9 reach: `falling-current-position-reach/reach.md`, commit `da7859ebc082a8249ee810a5cd5fe254d2f59c1c`, raw Git blob SHA-256 `ffd82f3214da46e76faf572b06211a11384e7c57c2896550a3334f0019b1a27c`.
- Prior independent 1.8.9 verdict: `falling-current-position-audit/audit.md`, commit `4830c0cdb1db99597efc2e30922e2e9cea8eecb7`, raw Git blob SHA-256 `c6f14529b34642f9be27ffe890f7c47aa6cb455b98f2d8c31ab8d23c0a115cfb`.
- Canonical earlier sources were read only from `decompiled_minecraft/<release>/ornithe-feather/`. For every checked 1.8–1.8.8 release, each pinned correction-path file hash matched its source-pins entry, each `.decompile-provenance.json` raw hash matched its pin, and the provenance source digest matched each pinned file digest. Provenance records identify the exact per-release `feather-gen2` build.2 artifact. The separately accepted 1.8.9 evidence is reused without replaying its source trace.

## Independent comparison of differing members

- `AbstractCommand.java` and `TpCommand.java`: the 1.8–1.8.1 shape differs from 1.8.9 through old mapped names and the split `AbstractCommand__Coordinate.java`; `TpCommand` parses Y using `center=false`. `parseTeleportCoordinate` calls the double parser, which uses `Double.parseDouble`, and decimal input receives no half-block offset. For absolute input, the coordinate's relative flag is false. From 1.8.2 onward the checked files have the same pinned hashes as 1.8.9.
- `ServerPlayNetworkHandler.java`: the absolute teleport overload was inspected for every distinct pinned file group across 1.8–1.8.8. It initializes target coordinates from the input doubles, adds player coordinates only for present relative flags, applies the server position, then sends the original input values and flags in `PlayerMoveS2CPacket`. The five-argument overload supplies an empty relative set. No earlier-version cast, quantization or reordered coordinate write appears.
- `PlayerMoveS2CPacket.java`: the differing 1.8–1.8.1 packet shape and the 1.8.2–1.8.8 hash-equal group retain `writeDouble`/`readDouble` for X/Y/Z; flags remain a separate unsigned-byte field. No float coordinate representation appears.
- `ClientPlayNetworkHandler.java`: in the differing 1.8 and 1.8.1 client handler bodies, each absolute position axis zeros its corresponding velocity component; the double packet Y remains the applied Y before `updatePositionAndAngles`. The 1.8.2–1.8.8 files are hash-equal to 1.8.9.
- `Entity.java`: for the differing 1.8 and 1.8.1 files, `updatePositionAndAngles` assigns Y in double fields and calls `setPosition`; `setPosition` writes Y directly and rebuilds the box from that Y and the current dimensions. `baseTick` performs `tickSprintingEffect()` then `checkWaterCollisions()` before later travel, with no intervening position/box write on the client path. Later 1.8.2–1.8.8 files are hash-equal to 1.8.9.
- `LocalClientPlayerEntity.java`: every alias gates `super.tick()` on the center `BlockPos(x, 0.0, z)` chunk being loaded; differing 1.8/1.8.1 source layout does not change that order. `PlayerEntity.tick()` in the 1.8 and 1.8.1 groups calls `super.tick()` before its later cape bookkeeping and X/Z clamp; its ordinary awake pre-super operations do not write player position or box. The 1.8.2–1.8.8 player tick files are hash-equal to 1.8.9.

The earlier differences are mapping/local-name, generic/signature and file-layout changes plus distinct server handler bodies inspected above. They do not change the reach operation or the next eligible local-player check ordering. No narrower patch boundary is claimed.

## Limits and actions

No fluid scan or fixture evolution was repeated. No trajectory, game/runtime, test, build, wiki, product history or implementation was consulted. No canonical source was generated or modified. This is an independent source-applicability verdict only.
