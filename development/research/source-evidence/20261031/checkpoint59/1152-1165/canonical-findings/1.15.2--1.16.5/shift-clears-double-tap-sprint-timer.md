# Shift input clears the double-tap sprint timer

1.15.2 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (627–671); the timer decrements and the item-use branch may clear it, but shift does not clear the timer before the sprint-start branch.
1.16.5 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (629–681); after the same decrement/item-use handling, sampled shift input clears `sprintTriggerTime` before the sprint-start branch.

Difference: With a positive double-tap window having more than one tick remaining at the start of a shift-held tick, the old code retains the decremented positive timer while the new code clears it. The sprint-start branch excludes the shift-held tick. On a later eligible forward press after shift is released, the old timer can start sprint immediately, while the new code begins a fresh seven-tick window.

Movement meaning: The later sprint-state difference is consumed by ordinary player movement and changes the speed state for subsequent travel. The timing gate excludes timers that expire on the shift-held tick. This is a direct local input/state change; no resource or packet dependency is required.
The exact intermediate release introducing this change was not checked.
