# MCPK full catalog and claim dispositions — 2026-10-08

## Scope and result

This is the full **catalog pass** over the MCPK Main Page and the movement-relevant pages linked from it, plus every movement, block, and other row in the MCPK Version Differences page. It is not a source-complete historical audit. MCPK claims are leads; exact-release canonical ready sources are the primary evidence. “Source-confirmed” below means only the bounded endpoint/path already recorded in the cited MCPK source adjudication or snapshot. It does not establish first patch release unless an exact pair proves it. “Wiki-only” means the statement is pinned to MCPK but not established by the allowed source evidence. “Excluded” is the project-scope disposition, not a claim that the behavior is false.

MCPK's Version Differences article ends at 1.18 and marks the 1.18 Movement, Blocks, and Other sections TODO. Its endpoint does not establish coverage or absence of changes in 1.19–26.2. The overall audit remains **PARTIAL**. In particular, exact 1.14.0 remains unavailable; the Y=256 water-exit initiating state, Blip trajectories/fall-distance producer, and other page claims listed below remain open.

## Revision and retrieval register

Revision IDs are permanent MediaWiki `oldid` values read through the normal in-app browser during 2026-10-08 16:44–16:47 UTC. Article facts below are bounded to those revisions; the permanent URL for each row is `https://www.mcpk.wiki/wiki/<page_title_with_underscores>?oldid=<Revision>`. Direct Research Fetcher canonical/raw/API retrieval had returned 403/inaccessible in prior records; the direct API URL attempt this pass rendered “File not found” in the browser and is not treated as article content. Main Page history resolves to 8878. `Parkour_Nomenclature` latest history identifies revision 1116. `Movement_Formulas` is revision 1463. The direct `X%2FZ_Facing?action=history` read returned “There is no edit history for this page” and its Page link is marked as a redlink; there is no separate revision-bounded MCPK article claim to adjudicate.

| MCPK page | Revision | Catalog disposition |
|---|---:|---|
| Main Page | 8878 | Navigation inventory only; says documentation mostly focuses on 1.8. |
| Version Differences | 8909 | Version table through 1.18; all rows catalogued below. |
| Parkour Nomenclature | 1116 | Glossary / taxonomy, not independent evidence; numerical definitions route to movement/formula pages and source checks. |
| Player Movement | 1395 | General input and acceleration overview; route to Jumping, Sprinting, Sneaking and exact tick paths. |
| Jumping | 4094 | Jump timing, vertical recurrence, ceiling, sprint-jump, liquid claims; wiki-only except bounded threshold/jump-source endpoints in source adjudication. |
| Sprinting | 4201 | Input activation, delay, stop rules and multipliers; wiki-only pending exact input-state comparisons. |
| Sneaking | 6509 | Edge support, dimensions, speed, ladder grip and 1.16.2 edge glitch; 1.10–1.11 endpoint and 1.16.1–1.16.2 edge-backoff findings are source-confirmed only as cited in source adjudication/r3; remaining details open. |
| Mouse Movement | 8877 | Sensitivity, yaw/pitch ranges and large-yaw precision claims; wiki-only; source-call and input-device/frame timing not adjudicated. |
| Status Effects | 3377 | Speed/slowness/jump boost movement multipliers and tables; movement response in scope, exact levels and transitions not source-checked. Effect acquisition and damage/combat producers excluded. |
| Debug Screen | 1735 | Coordinates/facing/debug key reference; utility, not a movement mechanic source. F3+T/F3+G claims route to Hotkey Exploits and remain outside movement-source evidence unless a player response is demonstrated. |
| Ticks | 1607 | 20 Hz/tick and turn-tick/input-randomness assertions; wiki-only. Tick execution source evidence must be exact-release and distinguish game loop from player movement. |
| Timings | 6420 | 0t/1t/2t and pessi/headhitter timing strategies; player-input techniques, not independently source-verified. |
| Tapping | 6342 | One-tick input techniques and threshold-sensitive distances; wiki-only strategy and formula lead; exact input/tick sequence required. |
| Backward Momentum | 1252 | Jump setup/efficiency inequalities; wiki-only strategy claim. |
| Hotkey Exploits | 1360 | Inventory and F3+T freeze setups; UI/render/tick scheduling claims, not verified movement mechanics. |
| 45° Strafe | 7143 | Rotation/input technique, 0.98 vs 1.0 direction factors and sneak diagonal multiplier; wiki's own simplified legacy snippet is not proof of exact modern or all-version math. Source comparison required. |
| Momentum Threshold | 1490 | Component-wise cutoff and 1.9+ threshold; bounded 1.8.9→1.9.4 endpoint evidence recorded in mcpk-source-adjudication; exact first patch not pinned. |
| Movement Formulas | 1463 | Explicitly approximate recurrence overview; directs readers to recursive formulas and says accurate simulation requires source replication. |
| Horizontal Movement Formulas | 8362 | Simplified/float-approximate recurrence, factors and thresholds; source adjudication has bounded examples; every numeric equation remains non-authoritative for tick-exact behavior. |
| Vertical Movement Formulas | 3660 | Simplified jump recurrence and threshold; source adjudication has bounded jump threshold/apex endpoints; operation order must follow exact source. |
| Nonrecursive Movement Formulas | 6901 | Derived closed-form claims/tables; wiki-only calculations, and not a substitute for vanilla float/cast/order replication. |
| Angles | 8361 | Significant-angle table, trig lookup, large yaw and half-angle/sprint-jump claims; wiki-only until exact-release math/path comparison. |
| Longest Jumps | 4152 | Enumerated maximum configurations, margins and construction assumptions; derived strategy dataset; not source evidence and depends on exact movement simulation. |
| Blocks | 1802 | 1.8 collision-shape catalogue; individual geometry needs exact source verification. No block-state emulation; historical shapes for then-existing blocks are in scope. |
| Slipperiness | 2190 | Factors, sample location, ground/air effect and partial-block examples; 1.14.4→1.15.2 sample-offset change source-confirmed; specific geometric examples remain open unless individually resolved. |
| Ladders and Vines | 8910 | Shapes, climb caps and 1.14 jump-climb claim; 1.13.2→1.14.4 jump predicate source-confirmed; reachable-jump and unsupported-vine implications remain open. |
| Soulsand | 2331 | 0.875 shape, per-block 0.4 velocity effect, overlap margin, 1.13 bubble column and 1.15 changes; 1.15 sample-offset implications/source snapshots exist, full collision/effect bounds remain partly open. |
| Cobweb | 1661 | Effect box, velocity reset/scales and derived jump heights/distances; wiki-only pending exact collision/effect application source-path comparison. |
| Collisions | 3201 | Sequential axis order and corner strategies; its 1.14 inequality is **incorrect** versus the exact 1.13.2→1.14.4 source comparison: source adjudication confirms requested absolute X/Z displacement, equality chooses X first. Do not propagate the article condition. |
| X/Z Facing | no page history | Direct history read at `https://www.mcpk.wiki/wiki/X%2FZ_Facing?action=history` says there is no edit history and marks the page as nonexistent. There are no separate article claims; the related axis/corner claims are catalogued under Collisions (3201). |
| Stepping | 2143 | 0.6 step-assist and related glitches; bounded source adjudication covers selected 1.8 stepping and later claims; remaining timing and collision variants open. |
| Blip | 3476 | Technique/trajectory taxonomy and claim of fall-damage cancellation in some cases, 1.15+ until fixed in 1.16; fall-damage cancellation is explicitly unverified and excluded as damage simulation, while any player velocity/fallDistance producer is a separate open movement-state question. |
| Jump Cancel | 3847 | Step- and ceiling-triggered vertical-velocity-loss technique; claims about thresholds, 1.8/1.8.1 and 1.14 patch remain wiki-only except shared step/ceiling facts independently source-checked. |
| Ceiling Hover | 1942 | Slime/bed ceiling bounce setup and collision explanation; wiki-only; requires exact collision dispatch, historical block and player-state verification. |
| Damage Boost | 8079 | Incomplete/WIP network-damage, packet-sync and knockback discussion. Damage/attack resolution and damage simulation are excluded. Direct player-only velocity/impulse application can be in scope, but this page does not establish an exact reproducible source claim; leave open/excluded as stated. |
| Lagback | 8022 | Server movement validation, packet corrections, wall/cobweb/sneak triggers; transport/server-authoritative reconciliation is outside the client movement compatibility claim absent an explicit direct player response. Wiki-only; do not emulate damage or server anticheat behavior from this page. |
| Anvil/Chest Manipulation | 7651 | Stateful shared collision-bound manipulation via look/update actions; 1.8 mechanic is reported patched in 1.9. Existing source endpoint adjudication is recorded in mcpk-1.8.9-1.9.4; triggering/update paths remain to be individually checked where not cited. |
| Water and Lava | 3122 | Marked WIP; 1.8 fluid collision, fallDistance reset, jump thresholds and missing X/Z TODOs. Water producer/consumer paths and 1.16.1 lava flow are partly source-adjudicated; exact first cutovers, full flow boundaries and many movement equations remain open. |

The page list above is derived from MCPK Main Page revision 8878 and direct linked mechanics/formula pages captured in this audit lane. Child articles are counted even when they are strategies or technical reference pages; those are explicitly classified instead of being treated as mechanics evidence.

## Version Differences: every published version row

Each list below records every claim cluster in the pinned Version Differences table. “Open” means a table assertion/lead with no complete exact-release disposition here. The source adjudication is the detailed authority for bounded confirmations, exclusions and hashes. Table claims do not establish first affected patch unless the exact boundary is independently compared.

### 1.8 baseline

- **Movement:** ellipsis; no delta claim.
- **Blocks:** ellipsis; baseline geometry routes to Blocks and exact 1.8 source comparison.
- **Other:** ellipsis; no delta claim.

### 1.9

- **Movement:** jump apex 1.249→1.252; momentum cutoff .005→.003; sneak height 1.8→1.65. Bounded endpoint source evidence is in mcpk-1.8.9-1.9.4; exact earliest 1.9 patch remains open.
- **Blocks:** end rods, chorus plants/flowers, shulker entities, grass path; ladder width .125→.1875; pane/bar shape from neutral cross to pole with internal arms +1 pixel; lily pad .015625-high/.875-wide; hay-bale fall damage; boat .5625-vs-.6 entity shape; piston arm geometry and west-facing correction; anvil/chest collision correction; large cocoa incorrect until 1.11; pane/bar barrier connection correction in 1.12. Player-relevant shapes are candidate mechanics; shulkers/boats and damage results excluded. Source adjudication verifies several selected endpoints, not every shape or exact first patch.
- **Other:** elytra, levitation, frost walker, entity-push-on-player. Movement effects are open leads; levitation/equipment producers are not movement physics themselves. Non-player collision producer is out of scope; a direct player-only impulse response may be separately in scope.

### 1.10

- **Movement:** optional auto-jump, not while sneaking, can activate at obstacle heights ≥0.5 per wiki, jump height ceiling differs and boost increases it, auto-jump stair jump-cancel claim. Exact 1.10.2 source source-adjudication confirms eligibility uses strict `> 0.5` and upper bound `<= 1.2`; this contradicts the table’s inclusive lower bound. Other activation details and patch boundary remain open.
- **Blocks:** magma-block damage is damage-only; exclude damage response unless an independent direct velocity change is evidenced. 1.10.1 farmland 15/16-high shape is an open player-shape lead.
- **Other:** F3+G debug overlay utility; not movement.

### 1.11

- **Movement:** sneak edge drop cutoff changes to .6; source endpoint comparison verifies 1.10.2→1.11.2 behavior, but exact 1.11.1 wall-height interval remains open because 1.11.1 was absent in the previously checked source inventory.
- **Blocks:** shulker box extension, increment/open/collision pushes and large cocoa fix; shulker state change may push a player and needs producer/consumer trace; cocoa endpoint correction is source-confirmed between 1.10.2 and 1.11.2, earliest patch open. Wall height 1/.875 bug reported for 1.11.1, fixed 1.11.2; exact bug interval open.
- **Other:** none.

### 1.12

- **Movement:** none in table.
- **Blocks:** bed bounce retains .66 downward velocity (player bounce source candidate; bounded source evidence in source adjudication); fence/wall/pane connection to stair backsides and no connection to perpendicular fence gates; pane/bar barrier connection correction. 1.11.2→1.12.2 source endpoints verify stair-side and barrier predicate changes; exact first 1.12 patch not pinned.
- **Other:** none.

### 1.13

- **Movement:** water movement, reduced-fall walking, sneak dive, sprint/swim pose height .6, current strength/range, shallow jump-out; Y=256 water-exit higher-jump bug fixed by broad 1.16 statement. Selected 1.13.2 water producer/consumer and player tick paths have bounded evidence, but complete fluid math, flow area/height boundaries, Y=256 initiating state, and first fix patch remain open.
- **Blocks:** Blue Ice .989 (source-confirmed at 1.13.2); soul-sand-up/magma-down bubble columns (player response path partly source-adjudicated; exact bounds open); conduit, sea pickle, turtle egg, waterlogged blocks, anvil, 1.5 wall, eye on portal frame, cauldron, hopper, brewing stand shapes; single snow no tangible volume. Shapes not individually checked remain open; zero-height snow statements require geometric/source treatment, not visual inference.
- **Other:** Riptide, Slow Falling, Dolphin's Grace, debug stick, debug target coordinates. Player movement effect math is open; item/effect producers are not themselves movement simulation. Debug utilities excluded.

### 1.14

- **Movement:** X/Z displacement order change; 1.14.4 source comparison confirms axis rule and that the Collisions page states its inequality backwards. Jump-triggered ladder/vine climb predicate is verified at endpoints, but additional reachable jumps and unsupported-vine cases remain open. Sneak 1.5 and sprint while sneaking claims have source details in prior adjudications; external forced-sneak/swim-under-ceiling claims remain unresolved. Lava area boundary, blip-up/wall-blip fix with side clip retained, new blip-down, wall-X conservation and ~.00031 hover remain unverified player mechanic reports. Exact 1.14.0 source is unavailable, so 1.14.4 is not treated as proof of first 1.14 patch behavior.
- **Blocks:** bamboo placement, bell collision, campfire damage, composter, grindstone, lantern, lectern, scaffolding floor/walls/sneak-down, stonecutter, berry damage/slowness, wither rose no collision/damage, beds/cauldron shape. Player-relevant collision/velocity changes are open shape/effect leads; damage only excluded. Source checks are not complete for listed shapes.
- **Other:** mouse tab raw input, vehicle exit floor clip (vehicle behavior excluded), boat water jump fall damage bug (damage/vehicle issue excluded). Input-device change may affect player input but source path is open.

### 1.15

- **Movement:** slipperiness sample only when block is <=.5 high, sneak/sprint input toggling, increased movement while sneaking inside blocks, near-zero momentum jump, boat behavior, visual crouch, Blip fall-damage cancellation, waterlogged ceiling swimming with timed sneak. Source confirms the 1.14.4→1.15.2 sample offset change; specific shape examples only where a snapshot states they were fully resolved. Input toggling and embedded-block speed remain open; boat excluded; fall-damage cancellation excluded and unverified, with any movement-state producer tracked separately; ceiling sequence open.
- **Blocks:** honey collision/slowdown/effect/jump, soul-sand slipperiness and slowdown inner bounds/perimeter/height, interior intermediate floors. Soulsand claims are captured in page revision 2331 and earlier bounded snapshot; detailed changes not all source-checked. Honey player-movement response is in source snapshots; effect producer boundaries may remain open. No block-state emulation.
- **Other:** elytra start after jump, fall/fire damage gamerules, status effect storage/override, boat fall damage fix. Direct player movement portions are open; damage-only/boat claims excluded.

### 1.16

- **Movement:** exact 1.16.0 Mojmap is now ready and its targeted player sources were checked read-only. Flowing-lava player push is source-confirmed in 1.16.0 and 1.16.1; 1.15.2 lacked the generic lava-current path in the prior comparison, so the endpoints bound the introduction to the 1.15.2→1.16.0 interval, not a narrower 1.16 patch. `Entity.java` is byte-identical in 1.16.0/1.16.1 (SHA-256 `27e3bb01…616576`) and has lava sampling at lines 947–955 plus generic bbox sampling in `updateFluidHeightAndDoFluidPushing`; exact 1.16.0 ready identity is in the addendum below. Sneak-on-boat excluded. Blip no longer cancels fall damage is damage-only and the prior Blip claim remains unverified. For Y=256, 1.16.0 and 1.16.1 `Entity.java` and `LivingEntity.java` are byte-identical; the 1.16.0 reset-position method's only `<256.0` loop is a respawn-position search, not the water-depth jump consumer. The 1.16.0–1.16.2 water-jump consumer branches were inspected and do not explain the alleged fix. Initiating state/cause and exact fix remain open. 1.16.2 sneak edge/step-down fix is source-confirmed by exact 1.16.1→1.16.2 snapshot r3.
- **Blocks:** chain shape, 1.16.2 orientations, moving slime collision fix, wall/soulsand connection, pane/bar/wall connection. Player collision shape claims are candidate deltas; exact shapes/boundaries must be checked against sources. Source adjudication does not claim all shapes closed.
- **Other:** Soul Speed and slab/jumping notes; player movement effect is open. Bugs while jumping/on slabs are not fully adjudicated.

### 1.17

- **Movement:** pigs/striders/horses recheck fluid while riding: excluded (mount/passenger and non-player movement). No player delta asserted in this row.
- **Blocks:** candles, cake, sensors, double dripleaf, dripstone offsets, powder snow (fall-distance collision threshold, boots, stuck movement multiplier/fall behavior/frozen effect), azaleas, amethyst buds. Big Dripleaf 1.17.1 player contact, timer and shape change are source-confirmed in corrected r2 snapshot, and MCPK's first-height-delay was corrected there. Powder-snow player collision, stuck multiplier and boots are endpoint-confirmed; frozen-speed/client/server timing open; fall-damage part excluded. Remaining listed shapes open.
- **Other:** boat rapid teleport (vehicle excluded), bucket desync (transport/state synchronization open and not proven player mechanic), player’s own block coordinates must be water to start swimming (1.17.1 endpoint source-confirmed; transition from 1.16.5 is in corrected swimming r2 snapshot).

### 1.18

- **Movement:** TODO in MCPK table; no published claims to verify from that row.
- **Blocks:** TODO; no published claims to verify from that row.
- **Other:** TODO; no published claims to verify from that row.

### 1.19–26.2

No rows are present in the pinned MCPK Version Differences page. This is a **catalog/page coverage limit**, not evidence that no movement changes exist. Do not extrapolate the page endpoint. The broader MCPK article catalog above may describe version-independent techniques, but it does not fill these missing release rows.

## Remaining exact-source/page gaps

- The X/Z Facing redlink has no page history; its corner/axis claims are covered in Collisions revision 3201, where the 1.14 axis inequality has already been corrected against source.
- Exact MCPK 1.14.0 source is still unavailable. Keep exact 1.14 first-cutover, jump/sneak/climb, pose, collision, blip and related boundaries unresolved; do not block unrelated page/catalog work on that missing release.
- For the Y=256 water-exit claim, source evidence records the 1.13.2 consumer (`LivingEntity.mobTick`) and water-depth producer path plus checked 1.16.0–1.16.2 endpoints; no initiating state/cause or change in the relevant 1.16 jump consumer is established. Keep partial until a state-transition reproducer/source trace closes it.
- Exact 1.16.0 Mojmap ready identity: marker SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; artifact-manifest SHA-256 `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2`. Targeted file hashes: `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`. These two files are byte-identical at 1.16.1. In 1.16.0 `Entity.resetPos()` lines 283–294 has a `<256.0` vertical search only for non-colliding respawn-position selection; `Entity.updateInWaterStateAndDoFluidPushing()` lines 947–972 refreshes water/lava state and flow; `LivingEntity`'s player-reachable fluid-jump branch lines 2424–2449 chooses `jumpFromGround()` or `jumpInLiquid()`. That branch is structurally the same at 1.16.2. This closes the prior source-availability gap, not the Y=256 causal/fix claim.
- For Blip, MCPK revision 3476's trajectory taxonomy and 1.15–1.16 fall-damage statement are wiki reports. No source-grounded geometric trajectory classification or direct `fallDistance` producer is established. Fall-damage simulation itself is excluded.
- Full pair coverage, first patch intervals, water/current boundaries, every listed block shape, exact input/turn timing and each effect/enchantment are not closed merely by this catalog. The audit has not established “all claims implemented” or a complete 1.8.9→26.2 movement trajectory.
- No tests, build, game, TAS, server, Gym or Docker actions were run for this documentation/source-audit pass.

## Linked evidence records

- [MCPK page index and source adjudication](mcpk-wiki.md)
- [Exact-source adjudication, method bodies, hashes and open claims](mcpk-source-adjudication.md)
- [Corrected independent follow-up r5, including Y=256 consumer path and Blip report disposition](mcpk-independent-followup-2026-10-08-r5.md)
- [Bounded 1.15 slipperiness examples r2](mcpk-1.15-slipperiness-examples-snapshot-r2.md)
- [Bounded 1.16.2 step-down r3](mcpk-1.16.2-step-down-snapshot-r3.md)
- [Bounded 1.17 Big Dripleaf r2](mcpk-1.17-big-dripleaf-snapshot-r2.md)
- [Bounded 1.17 swimming r2](mcpk-1.17-swimming-snapshot-r2.md)
