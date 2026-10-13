# F-1: Double-tap sprint window is configurable in 1.21.11

- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 649–650.
- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 729–730.

## Difference

Under the same eligible forward-input and sprint-eligibility conditions, 1.20.6 uses a fixed seven-tick double-tap counter. 1.21.11 uses a client option that defaults to seven ticks and allows values from zero through ten.

Setting 1.21.11 to zero prevents the forward double-tap branch from arming a positive counter; settings below or above seven shorten or lengthen the activation window relative to 1.20.6.

With 1.21.11 left at its default, this timer value matches 1.20.6.

## Movement meaning

The local player's `aiStep()` decrements `sprintTriggerTime` and evaluates the forward-input/start-sprint branch before normal player travel.

An accepted sprint state changes player movement speed through the player movement path.

This finding isolates the timer value.

The source proves that 1.21.11 exposes a configurable double-tap interval while 1.20.6 fixes it at seven.

In 1.21.11, the default interval is also seven.

For any nondefault interval, the source predicts that a second eligible tap can activate sprint in a different tick window, changing when sprint movement-speed state begins.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.20.6--1.21.11/findings/F-1-sprint-window.md).
