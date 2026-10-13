# F-S1-02: 1.21.4 adds local sprint stop and slow-movement start gates

- **1.21.3**, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 638-739.
- **1.21.4**, mojmap: `LocalPlayer#aiStep()`; lines 643-753.

## Difference

1.21.4 adds a local-player active-sprint stop check before the existing input scaling/start logic.

The added state conditions can turn off sprinting even where 1.21.3's active-sprint stop block would leave the sprint flag set. 1.21.4 also prevents a slow-moving local player from starting sprinting unless underwater. 1.21.3 already uses blindness, item use, fall-flight and passenger state to reject sprint starts; this finding concerns their new active-stop effect, the new slow-start restriction, and the camel exception for the passenger stop.

## Movement meaning

The player tick refreshes crouching from current local input/pose-fit state, then 1.21.4 evaluates the stop predicate every AI tick.

Fall-flight can be entered through the player's jump/glide path; blindness and item-use state are represented on the local living entity; passenger/camel identity and fluid state are available from the current client entity state.

Thus the gate can run while the player is already sprinting and a predicate changes.

The next travel call consumes the updated sprint flag.

The food-state producer is excluded, but its existing start/stop read remains within the bounded comparison.

Slow movement permits that later start only underwater.

Vehicle movement and attack/damage resolution are outside this finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.3--1.21.4/findings/F-S1-02-sprint-policy-gates.md).
