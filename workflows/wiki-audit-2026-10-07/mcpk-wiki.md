# MCPK wiki audit — 2026-10-07

Current resumable status and next steps: [mcpk-checkpoint.md](mcpk-checkpoint.md). This audit remains partial. The Feather revision-specific snapshot still awaits reviewer acceptance, and four bounded findings now have superseding r2 candidates awaiting a clean independent review; none is formally accepted. Exact immutable identities are in [the clean re-review candidate register](mcpk-clean-rereview-candidates-2026-10-08.md).

Scope: MCPK parkour wiki only, matched against supplied exact-release source snapshots where available. No Minecraft Wiki pages or other audit-track findings were consulted. No implementation files were changed; no tests, builds, servers, or Docker actions were run.

## MCPK pages inspected

| Page | Sections read | Last edited |
|---|---|---|
| [Version Differences](https://www.mcpk.wiki/wiki/Version_Differences) | 1.8–1.18; Movement, Blocks, Other. The page says it is no longer maintained. | 2026-04-17 12:51 |
| [Momentum Threshold](https://www.mcpk.wiki/wiki/Momentum_Threshold) | Component-wise cutoff and 1.9+ value. | 2021-08-23 12:14 |
| [Horizontal Movement Formulas](https://www.mcpk.wiki/wiki/Horizontal_Movement_Formulas) | Horizontal recurrence and threshold notes. | 2023-12-22 23:14 |
| [Vertical Movement Formulas](https://www.mcpk.wiki/wiki/Vertical_Movement_Formulas) | Jump recurrence, threshold, embedded legacy code. | 2022-01-28 14:00 |
| [Collisions](https://www.mcpk.wiki/wiki/Collisions) | Box, order, horizontal/vertical collisions, 1.14+. | 2021-11-14 08:15 |
| [Blocks](https://www.mcpk.wiki/wiki/Blocks) | 1.8 collision-shape catalogue. | 2021-08-24 12:38 |
| [Slipperiness](https://www.mcpk.wiki/wiki/Slipperiness) | Movement effect, block sampling, 1.15. | 2021-09-04 13:06 |
| [Ladders and Vines](https://www.mcpk.wiki/wiki/Ladders_and_Vines) | Shape and movement changes. | 2026-04-17 12:53 |
| [Stepping](https://www.mcpk.wiki/wiki/Stepping) | Step attempts, candidate resolution, historical behavior. | 2021-08-26 13:16 |
| [Anvil/Chest Manipulation](https://www.mcpk.wiki/wiki/Anvil/Chest_Manipulation) | Shared shape manipulation and later correction. | 2022-09-04 09:12 |

The Version Differences page stops at 1.18 and has TODOs in all 1.18 categories. It does not support coverage claims for 1.19+ or 26.x releases.

## Source findings and integrity status

Detailed method bodies, operation order, endpoint hashes, and remaining open claims are in [mcpk-source-adjudication.md](mcpk-source-adjudication.md). The initial 1.8.9→1.9.4 comparison is in [mcpk-1.8.9-1.9.4.md](mcpk-1.8.9-1.9.4.md). The revision-specific evidence snapshot is [mcpk-feather-r1-finding-snapshot.md](mcpk-feather-r1-finding-snapshot.md). Consumer checks matched all six immutable snapshots, all 11,958 listed Feather source files, and 222 unchanged raw-input records; the independent operations audit was reported passed. Feather-backed findings now await independent reviewer acceptance of that exact snapshot and its dependencies. The original derived mapped JARs are unavailable, and equivalence to the revised snapshots remains unproven.

- **1.8.9→1.9.4:** threshold and jump-apex behavior, sneak dimensions, ladder width, pane/bar neutral shape, lily-pad geometry, west piston arm, and chest/anvil shape behavior are verified at endpoints. Single-layer snow has no positive-volume collision difference. Exact first 1.9 patch is not pinned.
- **1.10.2 auto-jump:** option and eligibility/obstacle checks exist; source uses strict `> 0.5`, not `≥ 0.5`. It is optional client input behavior.
- **1.10.2→1.11.2 sneak edge safety:** support search changes from a one-block drop probe to `stepHeight` (0.6 blocks). Exact first 1.11 patch is open.
- **1.11 cocoa:** age-2 boxes duplicate age-1 boxes in 1.10.2 and become larger/lower in 1.11.2; endpoint comparison verifies the correction.
- **1.12:** bed bounce retains 66% of downward speed. Fence, wall, and pane predicates switch to face-shape checks that connect stair sides; pane/bar code excludes barriers. Exact first 1.12 patch is open.
- **1.12.2→1.13.2 single snow layer:** degenerate zero-height box becomes an empty shape; no positive-volume collision at either endpoint.
- **1.13 Blue Ice:** slipperiness is `0.989F`.
- **1.13.2→1.14.4 collision order:** requested X/Z displacement magnitudes select the first horizontal axis; equality takes X first. The Version Differences table matches source. The MCPK Collisions page states the opposite inequality and is wrong on that condition.
- **1.14:** player crouch dimensions are 0.6×1.5 at the endpoint. Jump input adds to the climb-assist predicate, verifying jump-to-climb behavior at the endpoint. Exact crouch boundary and additional reachability claims remain open.
- **1.14.4→1.15.2 slipperiness sampling:** source changes from `minY - 1.0` to `minY - 0.5000001`. Bed, bottom half-slab, and Soul Sand examples are resolved at the endpoints; a top slab samples inside itself. See [the r2 bounded example snapshot](mcpk-1.15-slipperiness-examples-snapshot-r2.md).
- **1.16.1→1.16.2 sneak step-down:** source replaces the `onGround` gate with a sneaking and above-ground predicate; the 0.05 movement backoff remains. See [the r2 bounded snapshot](mcpk-1.16.2-step-down-snapshot-r2.md).
- **1.17.1:** swimming entry requires water at the player's block position. Powder snow collision depends on fall distance and boots; source also confirms the 0.9/1.5/0.9 movement multiplier and boot climbing.
- **1.17 Big Dripleaf:** player contact enters UNSTABLE immediately, with no collision-height change; source schedules the first lower shape after 10 ticks and an empty leaf collision shape after a second 10 ticks. The MCPK page's stated 20-tick delay before the first reduction conflicts with this endpoint source; see [the r2 bounded snapshot](mcpk-1.17-big-dripleaf-snapshot-r2.md).
- **1.16.5→1.17.1 swimming entry:** adds a water-tag check at the player’s block position only on entry; the existing-swimming continuation branch is unchanged. See [the r2 bounded snapshot](mcpk-1.17-swimming-snapshot-r2.md).

## Still unresolved

- Exact 1.11.1 wall-height bug interval.
- Full 1.13 water math and flow-height boundaries, plus remaining block-shape dimensions.
- 1.14 sprint input while crouched, ceiling-induced poses, blips, and listed movement bugs; whether jump climbing adds reachable jumps and unsupported-vine details.
- Y=256 water-exit fix boundary; lava pushing is verified at the 1.16.1 endpoint.
- Movement math or exact version boundaries for player-affecting Elytra, Levitation, Frost Walker, Slow Falling, Dolphin’s Grace, Riptide, and Soul Speed. Damage-only details and non-player entity physics are out of scope.
- 1.17 powder-snow frozen-speed/client-server tick timing; fall-damage details are outside movement scope.
- 1.18 has only TODOs; the inspected wiki page has no later sections.

The four original bounded snapshots remain immutable but are superseded by r2 candidates for clean review. The r2 candidates preserve the direct-fetch HTTP 403 provenance limit; they do not represent fresh MCPK page verification. These and the other listed items remain coverage gaps pending review, not negative findings. Ready source endpoints do not automatically settle claims whose methods were not inspected.
