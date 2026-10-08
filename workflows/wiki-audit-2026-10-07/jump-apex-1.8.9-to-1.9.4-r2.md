# Finding snapshot r2: bounded jump recurrence; Wiki attribution unverified

Snapshot ID: `wiki-jump-apex-cutoff-1.8.9-1.9.4-r2`

Status: **source recurrence accepted under the stated assumptions; direct Jumping-page attribution unresolved.** This immutable replacement preserves the prior exploratory snapshot (`ab102b5d5da292e76460801ab362711acd734996`; SHA-256 `3a413b1e0e62cb5da276a6209779e60c3bd97874d7c166f5999142f067bdfd3a`) and the independent decision history. It does not establish a universal jump height, snapshot cutover, or pair completeness. No runtime validation was performed.

## Accepted bounded source calculation

Independent review at commit `b48a12bd3ef62cce13784fffc8b413cd29639ebc`, preserved byte-for-byte in [reviews/independent-review-new-wiki-lane-snapshots-2026-10-08.md](reviews/independent-review-new-wiki-lane-snapshots-2026-10-08.md), ACCEPTED the recurrence for a normal, unassisted, locally controlled ground jump in empty air. The review limits this to source-ordered mathematics and does not accept the Wiki-attribution subclaim.

Both exact release sources launch with `0.42F`. For the bounded path, travel adds the current Y velocity to displacement before updating velocity as `(velocityY - 0.08) * 0.98F`; on the following `mobTick()`, values below the version's near-zero cutoff are cleared before travel. Local control skips the intervening `0.98` entity damping.

| Tick | 1.8.9 entering velocity | 1.8.9 cumulative rise | 1.9.4 entering velocity | 1.9.4 cumulative rise |
|---:|---:|---:|---:|---:|
| 1 | 0.41999998688697815 | 0.41999998688697815 | 0.41999998688697815 | 0.41999998688697815 |
| 2 | 0.33319999363422365 | 0.7531999805212017 | 0.33319999363422365 | 0.7531999805212017 |
| 3 | 0.24813599859094576 | 1.0013359791121474 | 0.24813599859094576 | 1.0013359791121474 |
| 4 | 0.16477328182606651 | 1.166109260938214 | 0.16477328182606651 | 1.166109260938214 |
| 5 | 0.08307781780646721 | **1.2491870787446813** | 0.08307781780646721 | 1.2491870787446813 |
| 6 | 0 (prior value 0.0030162615090425808 is below 0.005) | **1.2491870787446813** | 0.0030162615090425808 (not below 0.003) | **1.2522033402537238** |

The next 1.9.4 travel update is negative, so tick 6 is its apex. The results round to `1.24919` and `1.2522`. This calculation explains those numbers for this path; it does not show where a Wiki article published them.

Exact source-file hashes and manifest-line bindings remain as recorded in the immutable predecessor [jump-apex-1.8.9-to-1.9.4.md](jump-apex-1.8.9-to-1.9.4.md): 1.8.9 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`, 1.9.4 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e`. The source comparison establishes only the net boundary `(1.8.9, 1.9.4]`; exact snapshot introduction remains open.

## Wiki provenance disposition

**Wiki lead, article provenance unverified:** an official `Template:Sandbox/HistoryLine` search result available on 2026-10-08 associates 15w45a with jump-height values `1.24919` and `1.2522`. The Jumping article fetch was blocked by robots.txt; no immutable article revision ID or page text was captured. The search-result text is a lead only and does not establish what the Jumping page says. Do not cite it as primary evidence for article content or release behavior.

The review requested changes specifically to this attribution subclaim while accepting the bounded source recurrence. Direct article attribution remains unresolved until an immutable Jumping-page `oldid`, retrieval outcome/date, and relevant page text or archived fetch artifact are captured. The dated access result remains in [wiki-page-fetch-log.md](wiki-page-fetch-log.md).

## Assumptions and exclusions

Initially stationary player; one ground jump; no status effect, sprint impulse affecting Y, fluid, collision, flight, riding, remote control, or environmental influence. This is not a general trajectory result. Hunger, exhaustion, health, damage, combat, and non-player movement are excluded.
