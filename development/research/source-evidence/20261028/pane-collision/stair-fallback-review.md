# Independent review: pane stair dependency fallback

## Verdict

**ACCEPT** for the focused correction in product commit `9e884d8b01789ba454505fc66692fdecc3af22f5`, relative to accepted pane commit `a2c01d420b6328811210f7bba668a5bb2ca6fc5d`.

The correction now treats an unlisted current `StairBlock` at a relevant virtual-shape dependency as unknown. `resolveStairShape` returns `Optional.empty()` if such a stair appears at the front or back probe, and `differsAt` returns empty for the same condition at either side-veto probe. That empty propagates through `stairFaceIsSolid` and `PaneNeighborFaceShape.connects`; the pane provider then returns empty rather than a possibly incorrect arm selection. The existing `MovementRuntime.collectMovementBlock` path returns the native shape immediately when a resolved shape provider returns empty, so the native pane operation handles that neighborhood.

Ordinary non-stairs still return `Optional.of(false)` from `historicalStair`, preserving the 1.12 `!isStair` veto behavior. Listed old stair IDs still enter the exact historical facing/half resolver. The fallback is therefore limited to unknown stair dependencies, rather than all unknown neighbors.

## Source verification

- Canonical 1.12 `StairsBlock.java`, SHA-256 `C52A011A759B6F3EF6D45162D3B61F464CECEEE5D2D8735DE6F6E25A56E55611`: `getStairShape` checks front/back blocks and invokes `isStairSameHalfSameFacing` on the side probe; lines 332–364. `isStair` is `state.getBlock() instanceof StairsBlock`, lines 366–368.
- Canonical 26.2 `Blocks.java`, SHA-256 `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435`: `MUD_BRICK_STAIRS` is registered via `registerLegacyStair` at line 2478; that helper constructs `new StairBlock(...)` at lines 5955–5957. This modern-only block is a real current stair dependency and is not in the old `STAIR_IDS` whitelist.
- Canonical 26.2 `StairBlock.java`, SHA-256 `2A8AD6EEDC5A151AF7A739CCD476F18CAB4FE78E9DF7C1EC601CB9687A77630B`.
- Reviewed product helper `PaneNeighborFaceShape.java`, SHA-256 `86BD20862E61FDBBDDF90139812D54B598A419F3FC571FFCE098D46A30486C8B`: the changed optional propagation covers front, back, and every reached side-veto probe (lines 753–814).
- Reviewed product dispatch: `PaneCollisionShape.collisionShapes` returns empty if any required neighbor is unknown; `MovementRuntime.collectMovementBlock` returns the original native pane shape when a resolved collision-shape hook returns empty (lines 227–245).

## Checks and limits

`git diff --check a2c01d420b6328811210f7bba668a5bb2ca6fc5d 9e884d8b01789ba454505fc66692fdecc3af22f5 --` passed. No build, tests, launch, or runtime checks were run. This accepts the static native-fallback correction for modern stair dependencies; it does not establish runtime reachability or whole-tick parity. The worktree's `git status` command reported that it was not a work tree, so I did not independently verify its clean-state claim.
