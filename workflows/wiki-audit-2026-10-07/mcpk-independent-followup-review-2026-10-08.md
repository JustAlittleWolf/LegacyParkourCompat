# Independent review — MCPK follow-up (2026-10-08)

## Reviewed object

Reviewed only the frozen follow-up identified by all of:

- Commit: `ee60e5c7ede07dc7a33851cd98f0657d867a9c9f`
- Path: `workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08.md`
- Git blob: `a2628811a67b1f30af79ba803f838831a298e572`
- Raw-file SHA-256: `7582fc092c79090211a4ce07cb2ac793aa252add8ae86acf6935bab78080760f`

The review was limited to that file, its explicitly named immutable MCPK snapshots/pages, and the cited exact-version source files and dependencies. Normal source-pair findings, the Minecraft Wiki lane, and mod implementation were not consulted. No tests, builds, game clients, servers, TAS, Gym, Docker, or push operations were run.

## Disposition

**REQUEST CHANGES** for two evidence-trail corrections in the follow-up. The bounded source observations below are accepted as written, subject to their stated endpoint and coverage limits. The two corrections do not change the underlying movement conclusions.

### Bounded claim dispositions

- **1.11 wall shape split — ACCEPT at 1.11.2 and 1.12.2 endpoints; UNSUPPORTED for the 1.11.1 source interval.** The cited `WallBlock.java` identities and ranges show separate outline and collision shape arrays: the two directional outlines can reach 0.875 high while each collision shape is extended to 1.5. `getShape` and the collision consumer use the appropriate distinct arrays. The Version Differences page reports a 1.11.1 defect fixed in 1.11.2, but the supplied source trees do not contain 1.11.1. Keep that boundary unresolved.
- **1.13 water — ACCEPT as a partial endpoint description; UNSUPPORTED as a complete water inventory.** The cited `FlowingFluid`, `WaterFluid`, `Entity`, `PlayerEntity`, and `LivingEntity` code supports the described flow computation, entity-current consumer, player swimming adjustment, and bounded tick-order observations. MCPK Water and Lava revision 3122 is explicitly WIP, and the follow-up correctly leaves remaining shapes and cell edges open.
- **Y=256 water-exit claim — UNSUPPORTED / unresolved.** The cited `resetPos` loops in 1.15.2 and 1.16.1 are respawn-position searches and do not establish the water-exit movement path. The stated lack of 1.16.0 source means the release boundary remains unresolved. Preserve this distinction.
- **1.14 collision axis order — ACCEPT at the 1.14.4 endpoint.** The cited source condition yields Z-before-X when `abs(x) < abs(z)`, otherwise X-before-Z, including X first on a tie. That agrees with Version Differences revision 8909 and conflicts with Collisions revision 3201. The follow-up correctly reports the conflict and does not claim a wider source-proven interval.
- **1.14 pose fallback and ladder/Levitation response — ACCEPT at the 1.14.4 endpoint.** The cited `Player` and `LivingEntity` branches support the bounded descriptions. The follow-up appropriately leaves input reachability, crouch/step edge cases, and the first affected patch open.
- **1.15 slipperiness selector and examples — REQUEST CHANGES to the source identity spelling; ACCEPT the bounded math/examples after that correction.** The selector change and geometry conclusions follow from the cited methods and block heights, including the bottom-slab/top-slab distinction. However, the 1.15.2 file is named `net/minecraft/block/SoulsandBlock.java` in the supplied tree, not `SoulSandBlock.java` as written in the follow-up. The listed hash `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf` belongs to the lowercase-`s` path. Correct the path while retaining the accepted r2 snapshot as immutable and bounded to its examples.
- **1.17 powder snow and freeze timing — ACCEPT the source observations; REQUEST CHANGES to close one explicit citation dependency.** The cited `PowderSnowBlock`, `LivingEntity`, `Entity` frozen-data, `Player`, and `LocalPlayer` endpoints support the stated collision branch, server-side counter update after travel, modifier timing, and client-visible synchronized counter. The follow-up also says the stuck multiplier “resets fall damage,” but its PowderSnowBlock citation starts at lines 32–37, 74, and 86–103 and does not identify the implementation that resets `fallDistance`. Add the direct dependency: 1.17.1 Mojmap `net/minecraft/world/entity/Entity.java`, SHA-256 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`, `makeStuckInBlock` around lines 2150–2157. That method sets `fallDistance` to `0.0F` and stores the stuck-speed multiplier. This verifies the underlying claim; the requested change is to make the source trail explicit.
- **1.16.2/1.17.1 player effects and enchantments — ACCEPT as endpoint behavior, not historical delta coverage.** The cited paths support the bounded descriptions of Slow Falling, Levitation, Dolphin’s Grace, Depth Strider, Elytra, Riptide, Soul Speed, and Frost Walker. The report correctly leaves exact first affected releases, combinations, and historical boundaries open, and treats direct player movement impulses separately from combat resolution.

## Page identity and scope checks

The browser-visible permanent revisions cited in the follow-up are consistent with the reviewed boundaries: Version Differences `oldid=8909` (revision dated 2026-04-17, coverage ends at 1.18 and marks the page no longer maintained); Slipperiness `oldid=2190`; Water and Lava `oldid=3122` (WIP); Ladders and Vines `oldid=8910`; Stepping `oldid=2143`; and Collisions `oldid=3201`. The Collisions page’s stated inequality conflicts with both Version Differences 8909 and the exact 1.14.4 source; the follow-up identifies this accurately.

The follow-up appropriately preserves the absent 1.11.1 and 1.16.0 source boundaries and does not use this MCPK material as evidence for 1.19+ or 26.x. Its overall state remains partial. No source-pair discovery-completion or implementation claim is supported by this review.

## Requested edits

1. Change the 1.15.2 source path from `SoulSandBlock.java` to the actual `SoulsandBlock.java`, preserving its hash and the bounded geometric conclusion.
2. Add the exact 1.17.1 `Entity.makeStuckInBlock` source identity and range to the powder snow bullet so the `fallDistance` reset is directly traceable.
