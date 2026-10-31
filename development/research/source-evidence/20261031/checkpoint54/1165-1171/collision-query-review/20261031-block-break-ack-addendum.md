# Addendum — block-break acknowledgement collision guard (2026-10-31)

## Verdict

**REJECT this `Shapes.create(current player box)` / `findBits` candidate as a changed block-break-ack predicate or position response.** This is a static source conclusion under equal block-ack, position, player-box, block-state and level inputs; it is not a runtime trajectory claim.

## Source path and paired behavior

In both 1.16.5 and 1.17, `MultiPlayerGameMode.handleBlockBreakAck` removes the stored position for the `(BlockPos, action)`, compares the acknowledged state with the local state, installs the acknowledged state with `clientLevel.setKnownState` on the mismatch path, and then checks `vec3 != null`, same-level identity, and `player.isColliding(blockPos, blockState)`. When all conditions hold, both versions call `player.absMoveTo(vec3.x, vec3.y, vec3.z)` with the stored pre-action position. The matching `Entity.isColliding(BlockPos,BlockState)` body in each version gets the passed state's collision shape, moves it by the block position, and returns `Shapes.joinIsNotEmpty(movedShape, Shapes.create(this.getBoundingBox()), AND)`. `Player` does not override this method.

The `Shapes.create(AABB)` implementation changed between the endpoints, but it cannot change this query shape for a `Player` in either publication. `Player.POSES` has horizontal widths `0.6F` for standing, crouching, fall-flying, swimming and spin attack, and `0.2F` for dying; sleeping uses the inherited sleeping dimensions (also `0.2F`). Unlisted poses fall back to standing. The bounding-box width is therefore always exactly one of those two float values on both X and Z.

For `findBits` to succeed on one axis, both endpoints must be close to integer coordinates at the same scale `1,2,4,8`; consequently the interval's width times that scale must be close to an integer. The closest scaled-integer distances for `0.6F` are approximately `0.4, 0.2, 0.4, 0.19999981`; for `0.2F` they are approximately `0.2, 0.4, 0.2, 0.4`. These exceed even the combined endpoint tolerance (`2.0E-7` world units for the new rule) by many orders of magnitude. Therefore both old floor-based `findBits` and new nearest-rounding `findBits` reject X and Z for every position. Since one failed axis selects the all-axis continuous `ArrayVoxelShape` fallback, the old and new query shapes retain the same exact current player AABB coordinates. The 1.17 extent-empty guard is not involved because the player's horizontal widths and vertical heights are all greater than `1.0E-7`.

Thus, with the same current player box and provider collision shape, `isColliding` returns the same Boolean in the paired sources. The acknowledgement branch gates and stored-position `absMoveTo` response then agree. Position/world coordinates outside `[0,1]` would independently force fallback, but the pose-width proof covers even boxes positioned entirely inside that domain.

This rejects only the proposed `Shapes.create` encoding difference on this callback. It does not claim the full acknowledgement/provider callback census is closed, nor that every other member used by the callback is unchanged.

## Exact-source identity

`block-break-ack-source-hashes.tsv` mechanically records SHA-256 hashes for `MultiPlayerGameMode.java`, `Entity.java`, `LivingEntity.java`, `Player.java`, and `Shapes.java` in 1.16.5, 1.17 and 1.17.1, each checked against the release's declared source manifest or embedded provenance. Every row matches. The 1.16.5 and 1.17.1 parent-sidecar publication identities and the 1.17 embedded `.decompile-provenance.json` are bound in `publication-identities.tsv` from the parent review. The hash ledger has adjacent `.sha256` sidecar.

No product code, wiki, backlog, runtime, build, test, server/TAS/Gym launch, or source regeneration was consulted or changed.
