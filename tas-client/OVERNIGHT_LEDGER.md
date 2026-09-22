# Movement source comparison ledger (2026-09-22)

Primary evidence is `decompiled_minecraft/<exact version>`; recordings below cover only basic movement. The inclusive live threshold is 10 ULP per finite XYZ coordinate on every tick, with exact tick count. A passing run reports the maximum ULP on each axis. No passing recording implies complete mechanic coverage.

## Major representatives and run status

Selected in chronological order from `ParkourVersion.java` and pinned TAS versions in `tas-client/build.gradle`: 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2, 1.13.2, 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, 1.19.4, 1.20.4, 1.21.11, current (26.2). The 1.13.2 Forge client is currently unrunnable: its FG3 API lacks `FMLInitializationEvent` and `IFMLLoadingPlugin`, while the TAS sources still target the older Forge API. It remains a decompilable source comparison point. The TAS build also excludes non-current 26.x; 26.1 is decompilable but not runnable by this workflow.

| Pair | Owner / evidence | Change coverage | Validation / gap |
| --- | --- | --- | --- |
| 1.8.9 → 1.9.4 | travel, collision, block read-only reviews | Crouch box 1.8 vs 1.9 covered by `StandingPose` / `SneakHeight165`. General collision and player block hooks equivalent. `Entity.updateVelocity` yaw expression changes from `yaw * (float)Math.PI / 180.0F` at 1.8.9 `Entity.java:839` to `yaw * (float)(Math.PI / 180.0)` at 1.9.4 `Entity.java:941`; no dedicated hook. | Source difference in trig rounding is confident; integration deferred pending a targeted modern input-vector hook and later boundary review. |
| 1.9.4 → 1.10.2 | travel, collision, block read-only reviews | Farmland collision lowers from 1.0 to 0.9375 by 1.10.2; `FullFarmland` covers V1_10. Ordinary collision/travel math equivalent. `hasNoGravity()` guards appear in 1.10.2 air/water/lava travel; relevant only to explicit no-gravity state. | Exact farmland minor-release boundary needs 1.10 / 1.10.1 source comparison. No-gravity case is outside the ordinary parkour input recording. |
| 1.10.2 → 1.11.2 | travel, collision, block read-only reviews | Sneak-edge probe drops from 1.0 to step height (0.6) and gains SELF/PLAYER guard; `OneBlockSneakEdge` covers normal movement. Mature cocoa collision restores larger box; `BuggedCocoaCollision` covers old shape. Travel math equivalent. | Intervening 1.11.1 wall regression requires a later minor pass. External-mover sneak edge remains uncertain. |
| 1.11.2 → 1.12.2 | collision read-only review; travel and block pending | Collision/step/sneak-edge equivalent. Bed bounce appears by 1.12.2 and `NoBedBounce` covers the older lack of bounce; fall-damage effect needs scope review. | Source comparison in progress. |

## Basic recording evidence

Input/reference: `tas-results-review/recording-1.8.9.lprc`, 200 ticks, replayed on the current client with profile `1.8` and `tasMaxUlps=10`. First failure: tick 4; expected XYZ `(9.464639968731529, 65, 14.427811376608872)`, actual `(9.464639958817726, 65, 14.427811379035253)`, ULP XYZ `(5580975, 0, 1365931)`. Run ID `compare-1.8-8137ca5cc9`; actual recording `tas-results-review/recording-1.8.9-actual-1.8-8137ca5cc9.lprc`. The final Gradle result matches the live first-mismatch signal. The gym's local `latest.log` did not contain this run ID, so the failure snapshot remains unavailable for this run.

The 1.8.9 native input/replay discrepancy reported in the handoff is still unresolved. These recordings test only basic movement; source comparison remains the main evidence.
