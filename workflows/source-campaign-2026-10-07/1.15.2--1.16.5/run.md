# Discovery: 1.15.2 to 1.16.5
- Status: active
- Scope: source-only client player movement; older A = exact Java Edition 1.15.2; newer B = exact Java Edition 1.16.5.
- Track declaration: no wiki, MCPK, release-notes, or mod-implementation evidence; no runtime implementation in this track.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07. Branch: feat/source-discovery-movement-source-1-15-2-1-16-5.
- Selected naming namespace: pending source-owner confirmation; candidate is Feather for A and Mojmap for B, pending validated readiness.
- Source preparation: exact pair/readiness requested from source owner. Canonical shared root is D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07; the source owner is the sole writer and this researcher will not regenerate, write or clean shared sources.

## Artifact manifest

### A — 1.15.2
- Ready JSON: pending.
- Source root, exact resolved release, successful command/log, client jar identity/hash, mapping coordinate/build/hash, mapped jar hash, source hashes, and relevant body diagnostics: pending validation.

### B — 1.16.5
- Ready JSON: pending.
- Source root, exact resolved release, successful command/log, client jar identity/hash, mapping coordinate/build/hash, mapped jar hash, source hashes, and relevant body diagnostics: pending validation.

The historical report at workflows/movement-discovery/runs/1.15.2--1.16.5/ is navigation evidence only. Its sources/findings are not revalidated or imported as confirmed.

## Correspondence and call order

Pending fresh member-level mapping after readiness. Record actual FQCN/member descriptors, inheritance/replacements, callers, order, reads/writes, and source anchors on both sides. Names alone do not establish correspondence.

## Coverage ledger

Fresh inventory. Every slice starts pending; the prior report closes none.

### Stage 1 — Input and tick order
- S1-input: producers, keyboard state, movement vectors, normalization — pending.
- S1-tick: local/super tick, travel dispatch, packet/report timing — pending.
- S1-sprint-jump: sprint gates/timers/start-stop, jump state/cooldown and impulse — pending.
- S1-sneak-use: sneak/item-use scaling and previous/current input capture — pending.
- S1-autojump-flight: auto-jump probes, flight toggles, unstuck and riding gates — pending.

### Stage 2 — Player state and gates
- S2-pose: pose, dimensions, eye height and resize/query timing — pending.
- S2-lifetime: swimming/crawling, abilities, flight/air speed, init/reset — pending.
- S2-gates: item use, blindness and hunger sprint consumers, edge sneak; exclude food/health emulation — pending.

### Stage 3 — Living movement
- S3-dispatch: travel branch choice and before/after updates — pending.
- S3-ground-air: acceleration, friction, gravity/drag, negligible-velocity thresholds and exact arithmetic — pending.
- S3-jump-climb: jump power/impulses and climbing clamps/gates — pending.
- S3-fluids: water/lava travel, swimming, submersion, descent and liquid jump — pending.
- S3-glide: gliding and movement-attribute/effect consumers — pending.

### Stage 4 — Collision and entity movement
- S4-collision: bounding-box move, axis order, position/velocity response — pending.
- S4-step-edge: step candidates/ties, edge probes/restraint, support and grounding — pending.
- S4-shapes: AABB/VoxelShape, world queries, collision context and pose query timing — pending.
- S4-callbacks: callbacks, fluid contact/push, repeated movement timing — pending.
- S4-writers: packet corrections and client movement-state writers — pending.

### Stage 5 — Blocks and fluids
- S5-registry: registrations/default movement properties and relevant overrides — pending.
- S5-shapes: historical partial-block shapes/support and neighbor/state dependencies — pending.
- S5-motion-factors: friction, speed, jump, bounce, ice, soul sand, slime, beds — pending.
- S5-contact: web, honey, climbables, slowdown and callbacks — pending.
- S5-flows: water/lava flow, bubble columns and resources — pending.
- S5-modern-only: absent/newer movement blocks/states with checked registry evidence — pending.

### Stage 6 — Effects, enchantments, attributes, equipment
- S6-effects: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness — pending.
- S6-enchants: Depth Strider, Frost Walker boundary, Riptide and other movement registrations — pending.
- S6-attributes: consumers, formulas, operations/order, defaults/clamps/aggregation — pending.
- S6-items: equipment slots and item-use slowdown — pending.
- S6-resources: jar tags/data/defaults and synchronized/external input boundaries — pending.

### Stage 7 — External influences and closure
- S7-push: player knockback/push, other-entity effects on player — pending.
- S7-transitions: mount/dismount and launch-item movement — pending.
- S7-world: explosions, piston displacement, corrections and server-supplied values — pending.
- S7-closure: revisit reachable writers, consumers, callbacks and dependencies — pending.

## Dependency queue and blockers

- SRC-PAIR: exact A/B ready JSON, aligned namespace artifacts/hashes, success records and relevant body diagnostics; comparison evidence depends on this.
- Queue changed/influential callees, field writers/consumers, overrides, registries, resources and external producers as discovered.
- Directory presence and historical manifests do not prove readiness.

## Finding index

No confirmed findings in this fresh run. Add only reverified behavioral deltas, one per findings file; previous findings remain candidates.

## Resume checkpoint

- Completed: scope, source-only restrictions, base/branch and coverage inventory.
- Last completed slice: none; source readiness is outstanding.
- Next: validate exact resolved IDs, namespace, artifacts/hashes, successful logs and body diagnostics; then map correspondences and execute all stages.
- Pending coverage count: 30 slices; all pending.
- Explicit unresolved count: 1 source-provenance dependency plus 33 movement slices.

## Source audit closure

- Counts: pending 33; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Closure: not complete; no difference/equivalence claims yet.
- Hash/correspondence audit: pending readiness.
- Runtime validation: not performed.

