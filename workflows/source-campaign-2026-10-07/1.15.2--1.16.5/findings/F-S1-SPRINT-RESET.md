# F-S1-SPRINT-RESET: holding shift cancels the pending double-tap sprint window

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: local-player tick input; S1-LOCAL-AISTEP, S1-SPRINT-RESET
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/client/player/LocalPlayer.java`; `net.minecraft.client.player.LocalPlayer#aiStep()`, lines 625-671; SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`. The method decrements a positive `sprintTriggerTime` at lines 627-629, samples `bl2 = input.shiftKeyDown` at 632-633, and its auto-sprint branch requires `!bl2` at 658-665 before consuming/replacing the timer at 666-670. No shift-held reset occurs between those statements.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same class/member, lines 627-681; SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`. B adds `if (bl2) { sprintTriggerTime = 0; }` at lines 663-665 before the same auto-sprint branch at 668-681.

## Source-level difference

Both versions decrement the pending seven-tick double-tap timer before reading the last sampled shift state. A prevents the auto-sprint branch while `bl2` is true but leaves the timer alive. B clears it on every such tick before the branch. Thus a still-live double-tap window can survive a held-shift interval in A and can be consumed after shift is released; B cancels that window while shift is held. The surrounding forward impulse, food/flight allowance, item-use and blindness guards remain in the inspected branch.

## Reachability and dependencies

`LocalPlayer.aiStep()` captures `input.shiftKeyDown` before `input.tick(...)`, then decrements the timer and evaluates the sprint gate. The timer is set to 7 in the same method when eligible forward impulse starts a double-tap window; the gate consumes a positive timer to call `setSprinting(true)`. The added B reset is therefore reachable for a local player with shift sampled as held, including while the timer is positive. Food level and `mayfly` are existing branch predicates, not systems in this finding's scope. Server reconciliation and observed trajectories are not inferred.

## Consequence and uncertainty

The source proves that B discards the pending auto-sprint window on a held-shift sample while A retains it. Exact user-input timing is determined by the client key sampling that precedes `aiStep`; no trajectory or runtime result is claimed.

## Handoff

Independent source delta: B makes held shift cancel a pending double-tap sprint trigger before the sprint-start gate. Related finding IDs: none. Boundary within the endpoint interval remains unknown.
