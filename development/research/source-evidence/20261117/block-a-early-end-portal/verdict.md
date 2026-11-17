# Independent source verdict: End Portal and local player

## Input binding

- Supplied immutable draft: `lane-early-end-portal/report.md`, SHA-256 `7df6d63a4956793ef8c581cf4ffaf50b964d6fb269a3bdf1e4fbfa00117bb27b` (verified before reading).
- Frozen independent source roster: `lane-early-end-portal-audit/source-roster.md`, SHA-256 `68D351FBD28F79BB2DBB837BCADD454A4856FDF87D261FC517A15A32E05195C5`.
- The frozen roster binds eight source paths for each of the six exact releases to local publication source hashes; all 48 expected hashes matched the current files. Exact local provenance/sidecar records and input artifact identities are recorded there.

## Conclusion 1 — first checked appearance in 1.9: accepted, narrowly

The literal End Portal callback in 1.8.9 (`net/minecraft/block/EndPortalBlock.java:61–65`) tests `vehicle == null`, `rider == null`, and `!world.isClient`, then calls `entity.changeDimension(1)`. It has no `canUsePortals()` or entity/block-shape intersection expression.

The exact 1.9 callback (`:62–70`) first introduces the `canUsePortals()` test and `entity.getShape().intersects(state.getShape(world, pos).move(pos))`, alongside `!entity.isRiding()`, `!entity.hasPassengers()`, and `!world.isClient`. The callback is byte-identical in 1.9.1 and 1.9.2. In 1.9.3–1.9.4 the same callback body appears at `:63–71`; the 1.9.3 and 1.9.4 files are byte-identical. I therefore accept 1.9 as the first of the checked exact releases with the new literal portal eligibility/intersection expressions, and accept their continued presence through 1.9.4.

The 1.8.9 field checks and later riding/passenger methods are recorded as source-expression differences only. Their behavioral equivalence is not reviewed here. The block's detailed shape construction, player dimensions, block registration, and reachable-height example in the supplied report are also outside this verdict; no geometry or trajectory claim is accepted by this review.

## Conclusion 2 — callback cannot directly transfer the local client player: accepted

Every checked End Portal callback includes `!world.isClient` before its only side effect, `entity.changeDimension(1)`. In 1.8.9 `Entity.changeDimension(int)` independently guards on `!this.world.isClient && !this.removed` (`Entity.java:1657–1692`). In 1.9–1.9.2 it has the same client-world guard at `:1815–1883`; in 1.9.3–1.9.4 the guard is at `:1825–1893`. Thus the End Portal callback and its transfer method cannot execute the dimension-transfer writes against a client-world `LocalClientPlayerEntity`.

In 1.9+, `canUsePortals()` is evaluated before the world-side gate, but the inherited `Entity.canUsePortals()` implementation is a read-only `return true` (`Entity.java:1885–1887` in 1.9–1.9.2; `:1895–1897` in 1.9.3–1.9.4), and `LocalClientPlayerEntity` does not override it. The intersection expression is after `!world.isClient`, so short-circuit evaluation skips it on a client world. No direct local position, velocity, dimension, or portal-state write is made by the End Portal callback.

## Conclusion 3 — client packet consequences are a separate direct-response path

The client does apply state when the corresponding server-to-client packet handlers run. `ClientPlayNetworkHandler.handlePlayerRespawn(PlayerRespawnS2CPacket)` updates the client world and player dimension when the packet dimension differs, then calls `Minecraft.respawnPlayer(dimension)`. In each checked `Minecraft.java`, that method replaces the `LocalClientPlayerEntity`, assigns the packet dimension, and calls `resetPos` (1.8.9 `:1799–1826`; 1.9–1.9.2 `:1926–1954`; 1.9.3–1.9.4 `:1929–1957`). The `handlePlayerMove(PlayerMoveS2CPacket)` route applies packet coordinates and angles through `updatePositionAndAngles` (1.8.9 `ClientPlayNetworkHandler.java:517–551`; 1.9–1.9.2 `:545–579`; 1.9.3–1.9.4 `:546–580`). These are direct local state responses to packet input, distinct from the End Portal callback.

Accordingly, the server-side End Portal eligibility/callback is not a client historical-movement hook. A changed client packet-response operation, if separately demonstrated, remains a distinct direct-player-response question; this report does not establish such a changed operation.

## Dependencies and limits

- The supplied report's exact callback-condition comparison is supported by the frozen `EndPortalBlock.java` and `Entity.java` sources. The 1.9 introduction claim is bounded to the six listed releases; no claim is made about snapshots, prereleases, or unexamined releases between 1.8.9 and 1.9.
- The client packet handlers establish how a received respawn/move packet changes local state. This audit did not inspect server transfer/player-manager code, so it does not establish that a specific overlap causes a particular packet sequence or determine the final arrival position.
- The report's detailed shape/height/reachability analysis, block-registration trace, and equivalence of old vehicle/rider field tests to later riding/passenger methods remain unreviewed. Non-player/vehicle behavior and solid geometry were not reviewed.
- No product code, runtime, build, or tests were inspected or changed.
