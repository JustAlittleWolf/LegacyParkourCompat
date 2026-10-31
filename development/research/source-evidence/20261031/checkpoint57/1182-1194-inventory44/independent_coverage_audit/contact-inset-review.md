# Independent review: contact-cell inset, 1.19.2→1.19.3

**Verdict: ACCEPT** as a reachable change to an existing direct player movement response. The callback used to close reachability is the vanilla cobweb provider; the modified candidate is `Entity.checkInsideBlocks`.

## Exact source facts

- `Entity.move` calls `tryCheckInsideBlocks()` after movement (`net/minecraft/world/entity/Entity.java:657` in both versions). `tryCheckInsideBlocks` calls `checkInsideBlocks()` (`Entity.java:685–687` in both versions). Thus the changed scan is on the local player's ordinary client movement route.
- 1.19.2 `Entity.checkInsideBlocks` forms the scanned min/max cells from `AABB.min + 0.001` / `max - 0.001` (`Entity.java:886–889`); 1.19.3 uses `1.0E-7` instead (`Entity.java:894–897`). It loops inclusive over those block coordinates and dispatches `BlockState.entityInside` (`1.19.2 Entity.java:893–901`; 1.19.3 `:901–909`). The smaller inset includes block cells when the AABB overlaps them by more than `1.0E-7` but no more than `0.001`.
- Existing `Blocks.COBWEB` is a `WebBlock` registered with `.noCollission()` in both versions (`1.19.2 net/minecraft/world/level/block/Blocks.java:348–350`; `1.19.3 Blocks.java:377–379`). Its unchanged `WebBlock.entityInside` always calls `Entity.makeStuckInBlock(state, new Vec3(0.25, 0.05F, 0.25))` (`net/minecraft/world/level/block/WebBlock.java:11–20` both versions).
- `Entity.makeStuckInBlock` resets fall distance and stores that multiplier (`1.19.2 net/minecraft/world/entity/Entity.java:2173–2176`; 1.19.3 `:2188–2191`). The next `Entity.move` multiplies the requested displacement by it and clears it (`Entity.java:560–564` in both versions). This changes position response, not just damage or an event.

## Reachable consequence

Because the cobweb has no collision shape, the player's box can move while its side overlaps the cobweb cell by a thin amount. For an AABB side within `(0, 0.001]` of the cell boundary, 1.19.2's larger inset can round the scanned minimum into the adjacent cell and skip the cobweb, while 1.19.3's smaller inset can include it. When the newly scanned callback runs, it installs the movement multiplier; the next client movement request is scaled. A source-level path and concrete overlap interval are established without a trajectory or game launch.

The lane report's statement that the contact-inset candidate had been covered is consistent with this independent route review. Other possible callback effects from the expanded scan remain part of the broader provider inventory; this verdict establishes at least one eligible reachable player response.
