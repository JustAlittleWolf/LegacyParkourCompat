# Final author review: revision 1

## Artifact binding

All six supplied/current and prior artifact SHA-256 values match their specified raw bytes: `comparison-r1.md`, r1 finding, original comparison, original finding, original frozen blind audit, and prior review verdict. The new source hashes in r1 also match the indicated published source manifest or embedded provenance: `Player.java`, `AbstractClientPlayer.java`, and `CollisionGetter.java` for 1.19.4, 1.20, and 1.20.2. No existing artifact or decompiled source was changed.

## Source verdict

Accept the r1 finding for the bounded 1.19.4–1.20 non-air frost-gate change, with 1.20.2 confirming continuation. Its comparison correctly captures both 1.20 state-selection branches and the old fallback:

- Through 1.19.4, the lookup floors X/Z and `position.y - 0.2F`. If that cell is air, it checks one cell below and selects that cell for fences, walls, and fence gates.
- In 1.20/1.20.2, a present support position supplies X/Z; the method retains Y=`floor(position.y - 0.2F)` for ordinary support blocks and returns the support position itself for fence/wall/gate support.
- With support empty, 1.20/1.20.2 returns the floor-coordinate cell directly and omits the old one-below special-block check.

The float offset widens for the double-Y subtraction in both versions, so the stated Y expression is preserved. The modifier UUID, operation, gates, math and post-travel order remain unchanged. The comparison's equal-input example is a valid conditional source-predicate case; it explicitly avoids claiming that a selected coordinate arrangement is reached by full player geometry or a trajectory. The 1.20.2 continuation and endpoint-only profile boundary are appropriately bounded.

The cited writer/reader chain is correct: `Entity.move()` supplies the resolved on-ground result and movement to `setOnGroundWithKnownMovement`; `checkSupportingBlock()` updates or clears `mainSupportingBlockPos`; `CollisionGetter.findSupportingBlock()` selects from block-collision positions; and `getOnPos(0.2F)` consumes that state before the post-travel frost gate. The Player getter attribution and class chain are now accurate for the three recent anchors, and their added pins match.

## Remaining exact-source correction

`comparison-r1.md` still overstates when `checkSupportingBlock()` retries with the movement-translated box. At `Entity.java` 1.20 lines 571–579 and 1.20.2 lines 573–581, the retry occurs only when the first result is empty, `onGroundNoBlocks` is false, and a known movement vector is non-null. If `onGroundNoBlocks` is true, the method assigns the empty result directly. Qualify the retry condition accordingly. This does not change the empty-support airborne branch, the old/new gate comparison, or the r1 finding's acceptance; it is a narrow correction to the comparison's exact writer description.

No exact patch cutover within 1.19.4–1.20, full collision geometry, complete trajectory, or runtime parity is established. No other gate remains for the bounded finding.