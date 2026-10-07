# Discovery: 1.21.11 to 26.1.2

- Status: active
- Scope: direct local-player movement; older A = 1.21.11; newer B = 26.1.2.
- Source-only track: yes. No mod implementation inspected; no wiki, MCPK, release-note or remembered-mechanics evidence used.
- Repository revision / start date: 002137b227676caea77f6832b9f4c8d0b6200bff (main); 2026-10-07.
- Work branch: feat/source-discovery-movement-source-1-21-11-26-1-2.
- Naming plan: official-name alignment, A Mojmap and B native unobfuscated. Must verify exact ready records, manifests, artifacts and bodies before use.
- Source preparation: delegated to the sole shared-source owner. No decompiler command run; no shared source/cache/manifest written or cleaned.
- Source protocol state: blocked on source publication, not on scope. At initial check, build/movement-campaign-2026-10-07/ready/ contains no ready JSON for either exact requested release. A 26.2 unobfuscated ready record exists but is not a substitute for 26.1.2. Requested A=1.21.11/Mojmap and B=26.1.2/unobfuscated, with validated JSON, manifests, diagnostics and method bodies.
- Comparison start is pending. Do not treat the following planned rows as compared coverage.
- JDK / Vineflower / remapper / mapping-io versions: pending exact source preparation records.

## Artifact manifest

No pair artifacts admitted yet. Do not cite directory presence as readiness.

| Side | Exact release | Namespace / mode | Ready JSON and hashes | Client jar / mappings / remap | Source root |
|---|---|---|---|---|---|
| A | 1.21.11 | Mojmap / mojmap | pending publication | pending validated artifact manifest | pending |
| B | 26.1.2 | native unobfuscated / unobfuscated | pending publication | pending validated artifact manifest; verify original unobfuscated client jar and mapping/remapped-jar N/A | pending |

## Correspondence and call order

Pending exact source trees and diagnostics. Resolve actual class names, descriptors, inheritance, callers, call order and read/write state for each role on both sides before comparison. Search seeds come from the ordered navigation guide only; they are not evidence of correspondence or completeness.

## Coverage ledger

These are planned slices, not source conclusions. Split any large row into bounded method slices as soon as exact sources are ready. Add every discovered caller, override, writer, resource and dependency; do not close parents before dependency closure.

| Slice | Stage / bounded behavior | Status | Evidence / dependencies / conclusion |
|---|---|---|---|
| S1.1 | Local input sampling and key/controller input state | pending | Pair sources unavailable; resolve producers, consumers, fields and timing. |
| S1.2 | Client player tick ordering, superclass tick and travel dispatch | pending | Pair sources unavailable; full ordered call path and state snapshot required. |
| S1.3 | Yaw-to-input conversion, diagonal normalization and input scaling | pending | Pair sources unavailable; exact float/cast/order comparison required. |
| S1.4 | Sprint transitions, timers and start/stop gates | pending | Pair sources unavailable; exclude health/food simulation, retain only direct state consumers. |
| S1.5 | Jump input, jump cooldown/state, auto-jump and riding gates | pending | Pair sources unavailable; resolve producers and reset timing. |
| S1.6 | Flight toggle/input, abilities and flight-speed path | pending | Pair sources unavailable; include only direct player movement path. |
| S1.7 | Unstuck behavior and other input-to-tick movement gates | pending | Pair sources unavailable; discover from complete entry call graph. |
| S2.1 | Player pose selection and pose transition timing | pending | Pair sources unavailable; include all reachable writers and guards. |
| S2.2 | Pose dimensions, eye height, resize collision queries | pending | Pair sources unavailable; follow shape/query timing and state writes. |
| S2.3 | Swim/crawl state and movement-mode selection | pending | Pair sources unavailable; resolve triggers and downstream travel dispatch. |
| S2.4 | Ability defaults, stored air speed and player movement state | pending | Pair sources unavailable; follow initialization, updates and resets. |
| S2.5 | Item-use slowdown and direct active-item movement gate | pending | Pair sources unavailable; food/hunger simulation excluded. |
| S2.6 | Edge sneaking and support probing | pending | Pair sources unavailable; follow collision and support dependencies. |
| S2.7 | Sprint-gate consumers of hunger/blindness state | pending | Pair sources unavailable; disposition consumers only; health, hunger, food and effect simulation excluded. |
| S3.1 | Travel dispatch and ground acceleration | pending | Pair sources unavailable; preserve complete method order and dependencies. |
| S3.2 | Ground friction, speed-factor consumption and post-travel drag | pending | Pair sources unavailable; resolve block/state providers and attribute inputs. |
| S3.3 | Air acceleration, drag/gravity and velocity thresholds | pending | Pair sources unavailable; explicit cutoff and all velocity writers/consumers required. |
| S3.4 | Jump power, sprint-jump impulse and jump state writes | pending | Pair sources unavailable; exact arithmetic and conditions required. |
| S3.5 | Climbing movement and clamps | pending | Pair sources unavailable; resolve climbable blocks/shapes and movement state. |
| S3.6 | Water travel, swimming, buoyancy/drag and fluid effects | pending | Pair sources unavailable; include fluid heights/flow and effect/attribute dependencies. |
| S3.7 | Lava travel and fluid contact path | pending | Pair sources unavailable; exclude damage effects except direct movement-state consumers. |
| S3.8 | Gliding and movement-affecting elytra path | pending | Pair sources unavailable; disposition item/attribute data and direct player calls. |
| S3.9 | Relative movement helpers, vector math and attributes | pending | Pair sources unavailable; close changed helper and aggregation dependencies. |
| S3.10 | Travel post-updates, velocity reset/restitution and fall-state writes | pending | Pair sources unavailable; follow all reachable callers and state writers. |
| S4.1 | Entity move entry, bounding-box and position updates | pending | Pair sources unavailable; preserve axis order and collision-query timing. |
| S4.2 | Axis resolution, collision candidates and tie-breaking | pending | Pair sources unavailable; include AABB/shape/world query dependencies. |
| S4.3 | Step-up candidates, comparison and selection | pending | Pair sources unavailable; compare all candidate paths and exact comparisons. |
| S4.4 | Edge probes, on-ground and support lookup | pending | Pair sources unavailable; include support block and neighboring-state reads. |
| S4.5 | Collision flags, velocity cancellation and callbacks | pending | Pair sources unavailable; include callback ordering and implementations. |
| S4.6 | Fluid state, contact, height and push/vector calculations | pending | Pair sources unavailable; connect fluid path to local player movement. |
| S4.7 | Pose/dimension-dependent collision query repetition | pending | Pair sources unavailable; close interaction with S2 pose slices. |
| S5.1 | Block/state movement defaults and registrations | pending | Pair sources unavailable; enumerate registrations/properties; do not infer from names. |
| S5.2 | Friction/speed/jump factors and their consumers | pending | Pair sources unavailable; follow default values and all reachable overrides. |
| S5.3 | Landing/bounce callbacks and support behavior | pending | Pair sources unavailable; include slime/bed paths where present and applicable. |
| S5.4 | Ice, soul sand, honey, web and other direct slowdown surfaces | pending | Pair sources unavailable; classify absent blocks as modern-only with checked registry evidence. |
| S5.5 | Climbable block registration, collision and contact behavior | pending | Pair sources unavailable; close S3 climbing dependencies. |
| S5.6 | Partial-block collision/support shapes and neighbor-dependent shapes | pending | Pair sources unavailable; enumerate overrides/providers; compare states only where block existed in A. |
| S5.7 | Pistons, bubble columns and other movement-producing block/fluid paths | pending | Pair sources unavailable; distinguish direct player displacement from entity simulation. |
| S5.8 | Relevant block/fluid tags, data and resource defaults | pending | Pair sources unavailable; inspect original version-matched jars; absent external data remains blocked. |
| S6.1 | Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphins Grace and Blindness consumers | pending | Pair sources unavailable; source-list every consumer/formula; exclude health/damage/food simulation. |
| S6.2 | Movement attributes: definitions, operations, aggregation and movement consumers | pending | Pair sources unavailable; include defaults, modifier order, clamping and synchronized inputs. |
| S6.3 | Depth Strider, Soul Speed, Swift Sneak and Riptide paths | pending | Pair sources unavailable; trace registration to condition/data to application to consumer. |
| S6.4 | Frost Walker boundary and movement-relevant server-supplied effects | pending | Pair sources unavailable; do not infer server-side world mutation. |
| S6.5 | Equipment, elytra, use-item state and movement-relevant components/tags | pending | Pair sources unavailable; exclude modern-only equipment behavior from old profiles. |
| S6.6 | Other movement-affecting effects/enchantments found by registration inventory | pending | Pair sources unavailable; discovery must extend beyond named examples. |
| S7.1 | Incoming velocity/position corrections and local player packet consumers | pending | Pair sources unavailable; separate client computation from external input. |
| S7.2 | Player knockback/push and explosion velocity writers | pending | Pair sources unavailable; inspect only player-facing path, not independent entity physics. |
| S7.3 | Piston displacement, launch items and external movement impulses | pending | Pair sources unavailable; identify client vs server ownership. |
| S7.4 | Mount/dismount transitions and riding movement gates | pending | Pair sources unavailable; follow player transition state only. |
| S7.5 | Closure sweep: all reachable player movement writers, changed dependencies and cross-mechanic interactions | pending | Pair sources unavailable; audit all stages and update parents after dependency findings. |

## Dependency queue and blockers

| ID | Origin | Missing input / why movement-relevant | Next retrieval / state |
|---|---|---|---|
| D0 | All stages | Exact A Mojmap source/artifact publication required for all source comparisons | Request owner publication for 1.21.11; pending. |
| D1 | All stages | Exact B native-unobfuscated 26.1.2 source/artifact publication; 26.2 is not a valid substitute | Request owner publication for 26.1.2; pending. |
| D2 | S6 resources | Version-matched original jar resources and provenance for referenced data/tags | Verify against ready artifact manifests after D0/D1. |

## Finding index

No findings yet. No equivalence conclusions yet. No discarded candidates yet.

## Resume checkpoint

- Last completed slice: none; source pair not published.
- Next: obtain and validate the exact A/B ready JSON records; verify IDs/namespaces/manifest hashes, method diagnostics and relevant bodies; then build ordered correspondence and begin S1 slices.
- Pending slices: 50 planned rows above, before dependency-driven expansion.
- Explicit pending count: 50 coverage rows plus 3 source/data dependencies. Status remains active while this source-only work continues; if handed off with rows pending, set partial and list exact gaps.
- Assumptions requiring verification: Mojmap is available for exact 1.21.11; 26.1.2 is published unobfuscated; all relevant method bodies in both ready trees are intact.

## Source audit closure

- Coverage counts: 50 pending; 0 in-progress; 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked. These are not terminal conclusions.
- Unresolved gaps: pair source publication; exact manifest/hash verification; method correspondence and diagnostics; all source comparisons and resource dependency closure.
- Provenance vs semantics vs actual coverage: provenance is pending for both sides; no semantics compared; actual source coverage is 0/50 planned slices.
- Runtime validation: not performed (separate workflow).
