# Block-break acknowledgement shape response review

## Scope and verdict

Independent source-only review of the `ClientPacketListener → MultiPlayerGameMode.handleBlockBreakAck → Player.isColliding` route on exact Mojmap 1.16.5, 1.17, and 1.17.1. The 1.16.5→1.17 `Shapes.create(AABB)` implementation changes its grid-detection arithmetic, but this route has **no concrete conditional or response difference caused by that AABB encoding change for a LocalPlayer**. No state/query witness exists for the player dimensions used by these sources, so no fixture is needed.

## Route and gates

`ClientPacketListener.handleBlockBreakAck` (1.16.5:2380–2391; 1.17:2315–2326; 1.17.1:2333–2344) applies the packet on the client thread and forwards the same logical fields to `gameMode.handleBlockBreakAck`: position, state, action, and `allGood`.

`MultiPlayerGameMode.handleBlockBreakAck` (1.16.5:434–444; 1.17:461–471; 1.17.1:468–478) removes the saved start position keyed by `(blockPos, action)`, reads the currently known block state, and enters the response branch only when

```
(vec3 == null || !allGood || (action != START_DESTROY_BLOCK && currentState != packetState))
    && currentState != packetState
```

It then installs the packet state. Only when the saved position exists, the supplied `ClientLevel` is `player.level`, and `player.isColliding(blockPos, packetState)` is true does it call `player.absMoveTo(savedX, savedY, savedZ)`. Equal packet/state/action/level and saved-position inputs therefore reach the shape query identically in all three versions. The response repositions the player and its box; the overload used does not write velocity.

`Entity.isColliding` is source-identical at 1.16.5:214–218, 1.17:246–250, and 1.17.1:246–250: fetch the supplied block state's collision shape with `CollisionContext.of(this)`, move it by the block position, then test `Shapes.joinIsNotEmpty(movedBlockShape, Shapes.create(this.getBoundingBox()), BooleanOp.AND)`.

## Shape arithmetic and why it cannot flip this response

In 1.16.5 `Shapes.create(AABB)` (Shapes.java:55–84) calls `findBits` on each axis. Its detector accepts coordinates near dyadic grid points using `abs(scaled - floor(scaled)) < 1.0E-7`; a failed axis falls back to `ArrayVoxelShape` with the AABB's exact min/max doubles.

In 1.17/1.17.1 `Shapes.create(AABB)` (Shapes.java:96–98) delegates to the double overload (Shapes.java:61–94). It first returns empty if any extent is `< 1.0E-7`, then uses the changed detector: `abs(scaled - round(scaled)) < 1.0E-7 * gridSize`. Failed detection likewise falls back to an array shape with the exact input min/max doubles. Thus an arbitrary AABB can indeed take different encodings near a grid boundary, but not the AABB of a player here.

The player pose dimensions in Player.java use horizontal widths `0.6F` (standing, crouching, swimming/fall-flying/spin-attack) or `0.2F` (sleeping/dying). EntityDimensions.makeBoundingBox (EntityDimensions.java:21–25) computes `width / 2.0F` and forms the bounds around the player position. Neither full width can match a 1/1, 1/2, 1/4, or 1/8 interval within either detector's tolerance: `0.6F` differs from its nearest 1/8 multiple by about `0.025`, and `0.2F` by about `0.05`, while the largest detector tolerance is `8.0E-7`. Therefore at least the X and Z axis detectors fail for every LocalPlayer pose, at every position. Both versions construct an array shape from the same exact AABB bounds. All listed player pose extents are also strictly greater than `1.0E-7`, so the newer empty-box guard cannot apply.

Given identical raw player AABB coordinates and block-state collision shape, both versions pass the same exact two array-axis coordinate lists into the AND query. The `Shapes.create(AABB)` encoding transition cannot change `isColliding`'s boolean on this acknowledgement route. There is consequently no branch flip and no differing position/box response to report. This verdict is limited to the named AABB encoding transition; no trajectory/runtime test was run.

## Exact source identity

`source-hashes.tsv` records SHA-256 values for the listener, game mode, entity query, shape encoder, index merger, and player pose dimensions. Hashes for 1.16.5 and 1.17.1 match their canonical `mojmap.sources.sha256` entries. The read-only canonical tree has the 1.17 Java source directory but no 1.17 `mojmap.provenance.json`, `mojmap.ready.json`, or `mojmap.sources.sha256`; its direct hashes are recorded and marked manifest-unavailable. Thus the three-version behavior comparison is source-complete, while exact publication-manifest verification is incomplete for the 1.17 middle snapshot. `publication-hashes.tsv` records available publication-sidecar hashes and that gap.

No product files, tests, builds, source generation, or runtime were touched.
