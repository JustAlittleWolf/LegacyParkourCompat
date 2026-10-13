# Keyboard slowdown gate

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 13–50.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 13–26.

Difference: 1.13.2 scales horizontal input by 0.3 whenever sneak is held. 1.14.4 scales only for a non-spectator with sneak held or pose-driven slow movement: not flying/swimming, crouch fits and shift held or standing does not fit, plus dry swimming-pose fallback.

Movement meaning: The local input tick supplies the horizontal impulses consumed by movement; pose and spectator gates can change that same-tick input scale.
