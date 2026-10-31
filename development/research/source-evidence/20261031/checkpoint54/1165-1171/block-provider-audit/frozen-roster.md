# Frozen source roster: existing block/player response providers

Roster frozen before reading any prior reports. Inputs were AGENTS/README plus orchestration/shared/source-discovery procedures, the exact 1.16.5 Mojmap source tree, and the case-sensitive three-way Java path/hash inventory. Search selected source classes declaring shape, collision, friction/speed, fluid, contact callback, climbability, and survival methods; base behavior, registrations, tags, fluid and shape query dependencies were added explicitly. These are candidates for member-level caller reachability and paired verification, not presumed movement changes.
Candidate/registration/query paths: 148. Roster TSV: frozen-roster.tsv.

The frozen method-name filter was `getShape`, `getCollisionShape`, `getBlockPathType`, `getFriction`, `getSpeedFactor`, `getFluidState`, `entityInside`, `stepOn`, `fallOn`, `updateEntityAfterFallOn`, `isClimbable`, `isLadder`, and `canSurvive` under the 1.16.5 `world/level/block` source tree. `Block.java`, `BlockBehaviour.java`, `Blocks.java`, block/fluid tags and fluid/shape primitives were added as dependency anchors.

No prior reports, product sources/history, backlog or wiki were read before this roster freeze.
