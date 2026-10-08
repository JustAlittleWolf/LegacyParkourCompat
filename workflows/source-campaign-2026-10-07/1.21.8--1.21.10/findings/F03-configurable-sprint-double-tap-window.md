# F03: Sprint double-tap timing becomes configurable

- Older version A: `1.21.8`
- Newer version B: `1.21.10`
- Mechanic / coverage slice IDs: S1.3
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: player sprint via forward double-tap, with B `sprintWindow` changed from 7
- First changed release: unknown within (`1.21.8`, `1.21.10`]
- Runtime validation: not performed

## Paired evidence

All paths are relative to the corresponding `ready/<version>/mojmap` root in the run artifact manifest.

- A: `net/minecraft/client/player/LocalPlayer.java`, `aiStep()` countdown lines 684–685 and timer assignment lines 722–728; SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`. On a qualifying first forward tap, when the sprint key is not down and the timer is not positive, assigns literal `7`; a later tap with positive timer calls `setSprinting(true)`.
- B: same class/member, countdown lines 688–689 and timer branch lines 726–732; SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`. Corresponding first-tap branch loads `this.minecraft.options.sprintWindow().get()`; a positive timer on a later qualifying tap calls `setSprinting(true)`.
- A: `net/minecraft/client/gui/screens/options/controls/ControlsScreen.java`, complete option array lines 14–16; SHA-256 `1C718BA8CBD5A28FC4709AB58E52C8731F82B209F7E649D9E7EA567F4A2D107B`; it exposes no sprint-window option.
- B: same class/member, lines 14–17; SHA-256 `E3531BB471440179FF38D46A776CE76F3D9915E09055F1EEC90A9E5E14CED4B6`; the option array includes `sprintWindow()`.
- B: `net/minecraft/client/Options.java`, option definition lines 496–505 (range 0–10, default 7) and persistence line 1268; SHA-256 `791DF2C2FD5B37C6E8C3D7775EF7797A20A5839DB00445B383CBC37662E7CEC8`.
- A: `net/minecraft/client/player/LocalPlayer.java`, `canStartSprinting()` lines 1065–1075; the caller gates entry to sprint state. B: same member lines 1062–1069; SHA-256s as cited above.
- A: `net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)` lines 2301–2330; SHA-256 `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`. B: same member lines 2331–2360; SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`. The water branch selects `0.9F` for sprinting and `getWaterSlowDown()` otherwise.

## Source-level difference

A decrements a positive sprint trigger timer and loads a fixed seven ticks on the first qualifying forward tap when the sprint key is not held. B follows the same double-tap path but loads the user option. The visible, persisted range is 0–10 with default 7, so default behavior matches A. A customized value changes the timer used by the subsequent-tap test. The decrement-before-check order is preserved; this finding does not assert an inclusive tick boundary beyond the source expressions.

## Reachability and dependencies

Local-player input -> `LocalPlayer.aiStep` forward double-tap branch -> fixed/configured timer -> `canStartSprinting` -> sprint state -> player travel. The separate sprint-key path bypasses the timer. The inherited water-travel branch reads sprint state to choose its drag coefficient. Other sprint gates still apply, including movement input, food, use state, blindness, flight and vehicle conditions. The option is client-local; no resource, tag or server-supplied value selects the timer.

## Consequence and uncertainty

Source-proven: B adds a 0–10 option, default 7, and reads it in the double-tap timer branch where A loads 7. A positive timer can cause the local player to enter sprint state, which travel consumes.

Predicted only: a customized timer can change whether a second tap near its threshold starts sprint and can thereby change player movement. No trajectory or runtime reproduction was measured. First changed release is unknown within the pair because intermediate releases were not audited.

## Handoff

Independent delta: B adds a default-7 configurable forward-double-tap sprint timer. Applicability requires the double-tap path and a customized option; sprint-key initiation is outside this delta. Related findings: none. Release boundary unknown within (`1.21.8`, `1.21.10`]. Implementation and runtime decisions are deferred.
