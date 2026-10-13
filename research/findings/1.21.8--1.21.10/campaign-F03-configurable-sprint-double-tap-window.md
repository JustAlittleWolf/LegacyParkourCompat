# F03: Sprint double-tap timing becomes configurable

- **1.21.8**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 684–685.
- **1.21.10**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.8--1.21.10/findings/F03-configurable-sprint-double-tap-window.md); lines 688–689.

## Difference

1.21.8 decrements a positive sprint trigger timer and loads a fixed seven ticks on the first qualifying forward tap when the sprint key is not held. 1.21.10 follows the same double-tap path but loads the user option.

The visible, persisted range is 0–10 with default 7, so default behavior matches 1.21.8. 1.21.8 customized value changes the timer used by the subsequent-tap test.

The decrement-before-check order is preserved; this finding does not assert an inclusive tick boundary beyond the source expressions.

## Movement meaning

Local-player input -> `LocalPlayer.aiStep` forward double-tap branch -> fixed/configured timer -> `canStartSprinting` -> sprint state -> player travel.

The separate sprint-key path bypasses the timer.

The inherited water-travel branch reads sprint state to choose its drag coefficient.

Other sprint gates still apply, including movement input, food, use state, blindness, flight and vehicle conditions.

The option is client-local; no resource, tag or server-supplied value selects the timer.

Source-proven: 1.21.10 adds a 0–10 option, default 7, and reads it in the double-tap timer branch where 1.21.8 loads 7. 1.21.8 positive timer can cause the local player to enter sprint state, which travel consumes.

Predicted only: a customized timer can change whether a second tap near its threshold starts sprint and can thereby change player movement.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.8--1.21.10/findings/F03-configurable-sprint-double-tap-window.md).
