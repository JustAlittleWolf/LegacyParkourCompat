# Independent lower-fence pose caller audit

## Verdict

**ACCEPTED, bounded to the caller and same-tick reachability claim.** In 1.11, a local player can enter the audited crouch-to-stand clearance call on the lower-fence overlap through an ordinary tick: the player starts the tick with crouched dimensions, releases sneak before movement, moves while airborne, and only then runs pose sizing. In 1.11.1/1.11.2, this same candidate is reached by the ordinary swept query and clipped to contact before the post-movement pose check. The common boolean-overload caller is established across all four inspected versions. This does not establish 1.10.2 moving-fence geometry or a posture effect there.

## Immutable inputs

- Finding: `1.10.2--1.11.2/lower-row-moving-fence-collision.md`, Git blob `19b9a9fd867ad378a6f7a6dd90d9b67fe5027dd7`, raw SHA-256 `fa81a5b6184c1ae606d037ba7c3775adb48e60b9a2a7807a85b457d082892217`.
- Frozen source comparison: `c-lower-fence-pose-source/comparison.md`, raw SHA-256 `9cc3ec968fa9788cce2c8c9781cb9e0d65feed1ad56786eedb257696ba5df12e` (recomputed locally; match).
- Canonical sources: `decompiled_minecraft/{1.10.2,1.11,1.11.1,1.11.2}/ornithe-feather/`. Recomputed SHA-256 values for the four caller-side files in each release match the identities listed in the frozen comparison. No decompiler output was written or regenerated.

## Caller and ordering evidence

In each version, `LocalClientPlayerEntity.tick()` admits its normal tick under the loaded-chunk gate and calls `super.tick()` (`LocalClientPlayerEntity.java`: 1.10.2 lines 152-155; 1.11/1.11.1/1.11.2 lines 159-162). `PlayerEntity.tick()` invokes `super.tick()` before its tail call to `updatePlayerPose()` (`PlayerEntity.java`: 1.10.2 line 250; later versions line 249; inherited tick begins at lines 175/174). Thus inherited player travel/movement precedes pose clearance in this tick.

`updatePlayerPose()` selects standing height `1.8F` when `isSneaking()` is false. For a dimension change, it anchors a candidate `Box` at the current shape's `minX/minY/minZ`, adds the target float width/height to those minima, calls `world.getCollisions(box)`, and calls `setSize` only when the boolean result is false (`PlayerEntity.java`: 1.10.2 lines 295-319; 1.11/1.11.1/1.11.2 lines 295-318). This is the boolean `World.getCollisions(Box)` overload identified in the immutable finding, not the entity-taking list overload. The shared caller/anchor check is the part that applies to all four versions; only 1.11+ lower-row fence-provider assertions are taken from the accepted finding.

For the 1.11 witness, the player is crouched before this tick (`height=1.65F`) but current local input has released sneak. `LocalClientPlayerEntity.isSneaking()` reads the current input (`LocalClientPlayerEntity.java`: lines 608-611), while the old height remains until the late pose update. The sneak-edge adjustment in `Entity.move()` is gated by `onGround && isSneaking() && this instanceof PlayerEntity` (1.11 lines 477-480); it therefore does not run while airborne (and the released input independently makes `isSneaking()` false). The step-up branch is gated by `stepHeight > 0 && (onGround || downward vertical clipping) && horizontal clipping` (1.11 lines 559-560). For the stated airborne, zero-Y movement, it is not entered. The request can consequently reach the normal swept query without sneak-edge backoff or a step attempt.

## Candidate box arithmetic and overlap

The source expression preserves the current shape minima. With the supplied witness, after an unclipped `-0.2` X movement those minima are approximately `(x+1.049999988079071, y+1.3, z+0.199999988079071)`. Width is the float `0.6F` promoted to double (`0.6000000238418579`); standing height is float `1.8F` promoted to double (`1.7999999523162842`). The candidate box's upper corner is therefore approximately `(x+1.650000011920929, y+3.099999952316284, z+0.800000011920929)`. This follows the exact anchor and addition order in `updatePlayerPose`; the shorter decimal endpoints in the frozen comparison are rounded descriptions.

The immutable finding supplies the accepted post box `[x+0.875,x+1.125] × [y,y+1.5] × [z+0.375,z+0.625]`. The candidate has strict positive overlap on every axis: approximately `0.075000012` on X, `0.2` on Y, and `0.25` on Z. This is a caller-box intersection check against the accepted geometry, not an independent re-audit of its scan or provider derivation.

In 1.11 the accepted scanner omits the candidate cell, so the swept move can reach the overlapping endpoint and its subsequent boolean pose preflight sees the same omitted cell. In 1.11.1/1.11.2 the accepted scanner supplies the post to the swept query; the requested move clips at the post's X face (contact, with no positive-volume overlap). The later candidate clearance query is still called, but this exact post-movement overlap state is not reached through that ordinary tick. If such a state is supplied by another path, the source caller itself remains applicable. The position-conditional restriction is material and is retained here.

## Scope limits

This verdict accepts the ordinary caller, candidate anchor/float box, and stated 1.11 versus 1.11.1/1.11.2 same-tick reachability distinction, conditional on the immutable finding's already accepted scan and geometry evidence. It makes no new scan, fence-shape, or runtime claim; it infers no equal exact moving-fence geometry in 1.10.2 from shared caller code. No tests, launches, builds, product files, wiki material, or product history were used.
