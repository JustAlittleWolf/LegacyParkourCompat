# Independent review — open Shulker local-player escape finding

## Verdict bound to revision

**REVISE** the immutable draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16.5/open-shulker-local-player-escape.md`, SHA-256 `77643b4f988f57d80487154a0f400984f5574c2cf7146b8998ac75d6ba32d1b1`. No substantive movement claim is contradicted. The stated EAST-facing OPENED witness, player coordinates, old four-probe response sequence, new rejection, and stated trajectory limitation agree with the accepted independent witness audit (`suffocation-provider-witness-independent-audit/report.md`, SHA-256 `214e87591315d984782e035e51261666d7ca96f6e02648e14d18baaa5906547d`). The revision needs source-range corrections/additions so each asserted dependency is directly cited.

## Verified facts

- Old `LocalPlayer.checkInBlock` at lines 385–429 computes the four candidate distances in WEST, EAST, NORTH, SOUTH order using strict `<`; `blocked` at lines 431–443 scans the player's vertical block cells, and old `Player.freeAt` lines 1489–1490 delegates to `BlockState.isSuffocating`. Old ShulkerBox lines 62–64 returns `true`; its `getShape` lines 223–225 reads the BE bounding box.
- New LocalPlayer lines 414–441 gates direction selection on `suffocatesAt(blockPos)`. Lines 443–447 query the player's vertical column with `noBlockCollision` and pass `state.isSuffocating` as the predicate. New `Blocks.shulkerBox` lines 3224–3236 uses `true` for a missing Shulker BE and otherwise `isClosed`; BE lines 290–292 is closed only for status `CLOSED`. An OPENED BE therefore fails the predicate.
- New `CollisionSpliterator` tests its predicate before calling `getCollisionShape`, but the predicate expression begins on **line 71**, not line 72. `CollisionGetter.noBlockCollision`/`getBlockCollisions` lines 62–68 forwards the predicate to that spliterator.
- The four old samples and direction tie outcomes are correct for a standing 0.6-wide player at X/Z `.5`, Y `.25`, with other checked cells air. Each old probe floors to `(0,0,0)`; the two westward ties and two eastward ties leave final X delta `+0.1` after the four sequential writes. The new initial suffocation test is false for the same probes, so no movement write occurs. `noPhysics=false` passes the common gate.

## Exact corrections to the draft

1. Change the 1.16.5 `CollisionSpliterator` range from `(72–86)` to **`(71–86)`**. Line 71 contains `this.predicate.test(blockState, this.pos)`; this is the evidence that the predicate executes before the shape lookup at line 74.
2. Add `net/minecraft/world/level/CollisionGetter.java`, **`noBlockCollision` / `getBlockCollisions` (62–68)**. This closes the caller edge showing the predicate supplied by `LocalPlayer.suffocatesAt` is passed into `CollisionSpliterator`.
3. The sentence “both versions use the same extended collision box” is not fully sourced by the draft's current new-version references. Add 1.16.5 `net/minecraft/world/level/block/ShulkerBoxBlock.java`, `getShape` **(218–220)**, and `net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java`, `getBoundingBox` **(93–100)**. The old equivalents are already cited.
4. To directly support “stably OPENED at progress `1.0F`” and the shape's use of progress, add both endpoints' `ShulkerBoxBlockEntity.updateAnimation` **(61–86)** and `getProgress` **(271–273)**. State the witness as `progressOld=progress=1.0F` if retaining the exact stable-state wording; both methods show the OPENED tick sets/stabilizes those values.
5. The concrete player box/probe arithmetic uses the standing dimensions and feet-based position-to-box conversion but currently cites neither. Add `Player.java`, `STANDING_DIMENSIONS` / standing pose map (1.15.2 **111–114**, 1.16.5 **112–115**) and `Entity.java`, `setPos` (1.15.2 **315–320**, 1.16.5 **331–334**). For the explicit 1.16.5 width-centering/feet-origin calculation, also add `EntityDimensions.java`, `makeBoundingBox` **(21–24)**.
6. “Leaving `+0.1` before `super.aiStep`” needs the continuation sites as well as the probe sites. Add 1.15.2 `LocalPlayer.aiStep` **(778)** and 1.16.5 **(788)**, each containing `super.aiStep()`.
7. Add 1.15.2 `BlockState.isSuffocating` **(268–270)** to directly source the dispatch from `Player.freeAt` to the Shulker override.

All cited ranges above were checked against the literal Mojmap endpoint source trees. Existing report source ledger identity and the source pins checked by the accepted audit remain unchanged. These are citation/traceability fixes; they do not alter the witness or movement conclusion.
