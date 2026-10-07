# Incremental static review of movement implementation

Reviewed `feat/movement-impl-1-8-9-1-9-4` through `8b71cda368a99d786187c22671c016e5fe939311`. The separate integration documentation branch at `674cab2` does not contain this implementation branch as an ancestor, so these conclusions apply to the cited implementation tip. This is a static source review; the wiki-audit reports were not read.

## Pane collision masks: prior defect resolved

The first pane implementation (`cea48d0`) formed both bars with `east == west ? X : ...` and `north == south ? Z : ...`. That made a missing pair of connections produce a full bar whenever the perpendicular pair was nonempty. Against the accepted PANE-02 behavior and exact 1.8.9 `PaneBlock.addCollisions` source (`PaneBlock.java:67-90`), this over-collided for six masks: N-only, S-only, N+S, E-only, W-only, and E+W when the perpendicular pair was empty. For example, N-only must produce only `Z_NORTH`, while the original formula added full `X` as well.

Commit `8b71cda` fixes the assembly in `PaneCollisionShape.java:73-86`: the no-neighbor case explicitly returns `X ∪ Z`; otherwise each axis contributes only when at least one of its two connections exists, using the appropriate half bar or full bar for an opposing pair. This matches the source branches for all 16 masks, including the six formerly incorrect masks. **No pane-mask finding remains open at this implementation tip.** This is a source comparison; no runtime witness was run.

## Crouching dimensions: pre-existing scoped gap, not a regression from this implementation

The implementation base `c1c5ea3` has no `SneakingDimensions` change; at that point the only `PlayerDimensionsBehavior` implementation handled swimming. The later `cd42884` adds a V1_8-only standing-height behavior to preserve 1.8's lack of sneak resizing. It does not add a V1_9 crouching-height delta. Exact accepted 1.9.4 source evidence establishes that crouching requests 0.6×1.65 dimensions and retains the previous dimensions when the candidate box collides; the current emulated V1_9.4 path still leaves `Pose.CROUCHING` at 26.2's native 1.5 height. This is a remaining behavior-coverage gap relative to that accepted source, not a regression introduced by the V1_8-only commits. I do not extend the requested fix range through V1_13 without an independently accepted historical boundary for those versions. No patch recommendation is made here because the current behavior hook must first be shown to express the source's stateful fit/retain rule.

## Dimension refresh after profile changes: prior gap addressed

Commit `52a1620` adds an epoch check at the shared `LivingEntity.tick()` head, filters to players, compares the currently cached width, height, and eye height with `player.getDimensions(player.getPose())`, and calls `refreshDimensions()` only when they differ. The controller epoch is advanced for both global and per-player profile changes. Exact 26.2 source has `Player.tick()` call `super.tick()` before `updatePlayerPose()`, and both `LocalPlayer.tick()` and `ServerPlayer.doTick()` reach that player tick path. Thus the refresh runs on both sides before the native pose update, and does not add dimensions to non-player entities or change the disabled/current resolver result. The original live-profile dimension-cache finding is resolved at the source/lifecycle level by this commit. Runtime Mixin application and live witnesses remain unverified.

## Review limits

- No tests, build, client/server, game, TAS, gym, Docker, or runtime witness was run for this incremental review.
- The implementation branch is not merged into the separate integration documentation branch at `674cab2`; final combined-tree review remains pending integration.
