# F-S1-SPRINT-RESET: holding shift cancels the pending double-tap sprint window

- **1.15.2**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 625-671.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-SPRINT-RESET.md); lines 627-681.

## Difference

Both versions decrement the pending seven-tick double-tap timer before reading the last sampled shift state. 1.15.2 prevents the auto-sprint branch while `bl2` is true but leaves the timer alive. 1.16.5 clears it on every such tick before the branch.

Thus a still-live double-tap window can survive a held-shift interval in 1.15.2 and can be consumed after shift is released; 1.16.5 cancels that window while shift is held.

The surrounding forward impulse, food/flight allowance, item-use and blindness guards remain in the inspected branch.

## Movement meaning

`LocalPlayer.aiStep()` captures `input.shiftKeyDown` before `input.tick(...)`, then decrements the timer and evaluates the sprint gate.

The timer is set to 7 in the same method when eligible forward impulse starts a double-tap window; the gate consumes a positive timer to call `setSprinting(true)`.

The added 1.16.5 reset is therefore reachable for a local player with shift sampled as held, including while the timer is positive.

Food level and `mayfly` are existing branch predicates, not systems in this finding's scope.

Server reconciliation and observed trajectories are not inferred.

The source proves that 1.16.5 discards the pending auto-sprint window on a held-shift sample while 1.15.2 retains it.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-SPRINT-RESET.md).
