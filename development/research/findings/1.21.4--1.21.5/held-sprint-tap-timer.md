# Holding sprint on the first forward tap

In 1.21.4 (Mojmap), see `net/minecraft/client/player/LocalPlayer.java`, `aiStep` lines 643–675 and 694–723, `shouldStopSprinting` lines 816–822, and the start predicates at lines 1053–1075. In 1.21.5 (Mojmap), see the same file, `aiStep` lines 683–703 and 718–744, `shouldStopRunSprinting` lines 837–844, and the start predicates at lines 1065–1083. The respective `net/minecraft/client/player/KeyboardInput.java` files sample the sprint binding and forward input at lines 13–33 and 14–35. Both versions snapshot previous forward eligibility before polling current input and decrement a positive sprint timer once at the beginning of the tick.

On an eligible forward transition with an expired timer, 1.21.4 tests the held sprint binding inside the tap branch. Holding sprint starts sprinting there and leaves the timer expired. The subsequent direct-start branch can start sprinting again. In 1.21.5 the tap branch checks only the timer, arms it to seven, and then the direct-start branch sees the sampled sprint control and starts sprinting.

Start from rest with an expired timer, standing on level, dry, unobstructed ground with full food. Stay upright, on foot, free of blindness, outside flight and item use. Use ordinary hold-mode keyboard input, with all other controls released throughout.

| Tick | Controls | 1.21.4 after sprint checks | 1.21.5 after sprint checks |
| --- | --- | --- | --- |
| 1 | Press forward and sprint together | Sprinting, timer 0 | Sprinting, timer 7 |
| 2 | Release both | Walking state, timer 0 | Walking state, timer 6 |
| 3 | Press forward alone | Walking, timer 7 | Sprinting, timer 5 |

Forward input alternates between zero and full forward, so both prior-forward predicates agree on every tick. Releasing forward stops the first sprint in both releases. On tick 3 only 1.21.5 applies the sprint movement-speed modifier (`net/minecraft/world/entity/LivingEntity.java`, `setSprinting` at lines 2077–2084 and 2101–2108 respectively).

The held-key arm/start choice occurs in every checked stable release from 1.7.10 through 1.21.4. Every checked release from 1.21.5 through 26.2 separates the timer choice from the held-key branch. Starting with 1.21.9 the timer assignment reads the sprint-window option. These ranges describe the inner choice only. The enclosing sprint eligibility conditions vary across releases.
