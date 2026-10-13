# S1-01: Clear the pending auto-sprint trigger while crouch is held

- **1.15.2**, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 625–640.
- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 627–646.

## Difference

Both versions decrement the trigger timer at the start of `aiStep`, capture `input.shiftKeyDown` in `bl2`, and allow the on-ground/underwater auto-sprint branch to call `setSprinting(true)` while the timer is positive. 1.16.5 additionally clears the timer whenever the captured shift state is true. 1.15.2 has no corresponding reset in the compared `aiStep` path; its other reset is the shared item-use branch.

## Movement meaning

The local client calls `LocalPlayer.tick()`, which reaches the living/player tick and dispatches to `LocalPlayer.aiStep()`.

With auto-sprint enabled, sufficient forward input, food/flight eligibility, no blindness or item use, and an already-positive double-tap trigger, holding shift through an AI step clears the timer in 1.16.5.

In 1.15.2 the timer continues to count down.

The next eligible non-shift step may therefore start sprint immediately in 1.15.2 but require another trigger in 1.16.5.

The source proves a one-sided timer reset and the shared sprint branch that consumes it. 1.15.2 likely consequence is delayed or suppressed sprint acceleration after releasing crouch in 1.16.5.

This finding concerns the player sprint state, not any server-side sprint policy.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s1-01-sprint-trigger-reset.md).
