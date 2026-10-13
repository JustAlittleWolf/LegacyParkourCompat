# holding shift cancels the pending double-tap sprint window

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 625–671.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 627–681.

Difference: Both versions decrement the pending seven-tick double-tap timer before reading the last sampled shift state. 1.15.2 prevents the auto-sprint branch while `bl2` is true but leaves the timer alive. 1.16.5 clears it on every such tick before the branch. Thus a still-live double-tap window can survive a held-shift interval in 1.15.2 and can be consumed after shift is released; 1.16.5 cancels that window while shift is held. The surrounding forward impulse, food/flight allowance, item-use and blindness guards remain in the inspected branch.

Movement meaning: `LocalPlayer.aiStep()` captures `input.shiftKeyDown` before `input.tick(...)`, then decrements the timer and evaluates the sprint gate.
