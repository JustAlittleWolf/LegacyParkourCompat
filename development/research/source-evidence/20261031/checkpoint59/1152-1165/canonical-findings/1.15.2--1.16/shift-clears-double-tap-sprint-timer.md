# Shift input clears the double-tap sprint timer

1.15.2 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (625–671); the timer decrements and the item-use branch may clear it, but shift does not clear the timer before the sprint-start branch.
1.16 (Mojmap): same member (654–690) adds `if (bl2) this.sprintTriggerTime = 0` (688–690) before the sprint-start branch. This exact operation form remains unchanged in 1.16.1–1.16.5. Boundary: 1.15.2 → 1.16.

Difference: With a positive double-tap window having more than one tick remaining at the start of a shift-held tick, the old code retains the decremented positive timer while the new code clears it. The sprint-start branch excludes the shift-held tick. On a later eligible forward press after shift is released, the old timer can start sprint immediately, while the new code begins a fresh seven-tick window.

Movement meaning: The later sprint-state difference is consumed by ordinary player movement and changes the speed state for subsequent travel. The timing gate excludes timers that expire on the shift-held tick. This is a direct local input/state change; no resource or packet dependency is required.
