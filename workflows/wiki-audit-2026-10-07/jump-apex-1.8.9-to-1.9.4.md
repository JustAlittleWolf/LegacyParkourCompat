# Finding snapshot: jump apex from movement cutoff

Snapshot ID: `wiki-jump-apex-cutoff-1.8.9-1.9.4`

Status: source-confirmed bounded calculation; independent Wiki-lane review pending; no runtime validation performed.

## Candidate and boundary

The Minecraft Wiki's Jumping history search result points to the official [history-line template](https://minecraft.wiki/w/Template%3ASandbox/HistoryLine) for the reported 15w45a jump-height change (`1.24919` to `1.2522`). The Jumping article itself could not be fetched in this audit because access was blocked by robots.txt. This exact-release source comparison explains those rounded values for a normal, unassisted, locally controlled ground jump in empty air. It establishes the net difference in `(1.8.9, 1.9.4]`; it does not establish the first snapshot containing the cutoff change.

## Finding and tick calculation

Both `LivingEntity#getJumpStrength()` methods return `0.42F`, and both `jump()` methods assign that value to `velocityY`. In `mobTick()`, each release applies a per-component near-zero cutoff before the jump-input branch and before `moveRelative()` performs travel. For a locally controlled entity, the intervening `0.98` velocity damping is skipped. The jump tick then moves by the current Y velocity before applying normal-air gravity and drag in `moveRelative()`.

For the stated path, the recurrence uses the binary float value of `0.42F` as the initial double velocity, adds that velocity to Y before updating it, then evaluates `(velocityY - 0.08) * 0.98F` in the source order. On subsequent ticks, `mobTick()` first sets `velocityY` to zero when `Math.abs(velocityY) < cutoff`.

| Tick displacement | Velocity entering travel in 1.8.9 | Cumulative rise 1.8.9 | Velocity entering travel in 1.9.4 | Cumulative rise 1.9.4 |
|---:|---:|---:|---:|---:|
| 1 | 0.41999998688697815 | 0.41999998688697815 | 0.41999998688697815 | 0.41999998688697815 |
| 2 | 0.33319999363422365 | 0.7531999805212017 | 0.33319999363422365 | 0.7531999805212017 |
| 3 | 0.24813599859094576 | 1.0013359791121474 | 0.24813599859094576 | 1.0013359791121474 |
| 4 | 0.16477328182606651 | 1.166109260938214 | 0.16477328182606651 | 1.166109260938214 |
| 5 | 0.08307781780646721 | **1.2491870787446813** | 0.08307781780646721 | 1.2491870787446813 |
| 6 | 0 (prior value 0.0030162615090425808 is below 0.005) | **1.2491870787446813** | 0.0030162615090425808 (not below 0.003) | **1.2522033402537238** |

The next 1.9.4 travel update is negative, so tick 6 is its apex. Rounded to the Wiki's displayed precision, the peaks are `1.24919` and `1.2522`. The `0.005`→`0.003` cutoff alone explains the difference for this path; jump launch strength itself does not change. This calculation does not cover status effects, sprinting, water/lava, flight, riding, remote entities, collision, input acceleration, or other environmental influences.

## Exact source evidence and provenance

Paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`.

| Version | Source and ranges | SHA-256 | Manifest check |
|---|---|---|---|
| 1.8.9 | `net/minecraft/entity/living/LivingEntity.java`: `getJumpStrength()`/`jump()` lines 1092-1109; normal-air travel gravity/drag lines 1181-1186; `mobTick()` cutoff and ordering lines 1406-1416, 1431-1450. | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | Matches `ornithe-feather.sources.sha256`, line 859. |
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java`: `getJumpStrength()`/`jump()` lines 1275-1292; normal-air travel gravity/drag lines 1407-1414; `mobTick()` cutoff and ordering lines 1666-1676, 1691-1711. | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | Matches `ornithe-feather.sources.sha256`, line 924. |

The cutoff is executed from `LivingEntity#mobTick()`, before its jump-input branch and `moveRelative()` call. The movement path then enters `LivingEntity#moveRelative()`'s ordinary non-fluid, non-climbing air branch; `move()` applies the current displacement before the same branch subtracts `0.08` and multiplies Y velocity by `0.98F`. The source file hashes above are the checked ready-tree identities. The official Wiki lead is recorded with its retrieval/access status in [wiki-page-fetch-log.md](wiki-page-fetch-log.md).

## Applicability and limits

The calculation assumes an initially stationary player, one ground jump, no status effect, no sprint impulse affecting Y, no fluid, no collision, and local movement control. It is a source-derived explanation of the numeric Wiki claim for this path, not a universal jump height or a pair-completeness finding. The exact snapshot cutover remains unknown and requires source at the candidate snapshot boundary. Runtime validation was not performed.
