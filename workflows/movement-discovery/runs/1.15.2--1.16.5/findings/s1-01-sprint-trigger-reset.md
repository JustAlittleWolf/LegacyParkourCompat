# S1-01: Clear the pending auto-sprint trigger while crouch is held

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 1, local input and tick ordering; `S1-SPRINT-TRIGGER`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: artifact manifest A; `../../../../decompiled_minecraft/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; `net.minecraft.client.player.LocalPlayer.aiStep()V`; lines 625–640 decrement `sprintTriggerTime` and reset it for item use, and lines 657–670 use a positive timer to start sprinting. SHA-256 `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`.
- B: artifact manifest B; `../../../../decompiled_minecraft/1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; `net.minecraft.client.player.LocalPlayer.aiStep()V`; lines 627–646 do the same timer decrement/item-use reset, and lines 663–679 add `if (bl2) this.sprintTriggerTime = 0` before the same auto-sprint branch. SHA-256 `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B`.

## Source-level difference

Both versions decrement the trigger timer at the start of `aiStep`, capture `input.shiftKeyDown` in `bl2`, and allow the on-ground/underwater auto-sprint branch to call `setSprinting(true)` while the timer is positive. B additionally clears the timer whenever the captured shift state is true. A has no corresponding reset in the compared `aiStep` path; its other reset is the shared item-use branch.

## Reachability and dependencies

The local client calls `LocalPlayer.tick()`, which reaches the living/player tick and dispatches to `LocalPlayer.aiStep()`. With auto-sprint enabled, sufficient forward input, food/flight eligibility, no blindness or item use, and an already-positive double-tap trigger, holding shift through an AI step clears the timer in B. In A the timer continues to count down. The next eligible non-shift step may therefore start sprint immediately in A but require another trigger in B.

## Consequence and uncertainty

The source proves a one-sided timer reset and the shared sprint branch that consumes it. A likely consequence is delayed or suppressed sprint acceleration after releasing crouch in B. The precise outcome depends on the user's key timing and other sprint gates; no trajectory was run. This finding concerns the player sprint state, not any server-side sprint policy.

## Handoff

Independent delta: crouching now cancels a pending auto-sprint trigger. Related findings: none. Applicability is limited to the existing local-player auto-sprint path; the first release containing the change is unknown.
