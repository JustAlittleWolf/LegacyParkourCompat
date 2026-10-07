# Discovery: 1.21.5 to 1.21.8

- Status: active
- Scope: direct client-player movement only; older A = 1.21.5; newer B = 1.21.8. Excludes health, regeneration, hunger, food, saturation, exhaustion, damage, and combat emulation, including indirect sprint-gate effects. Excludes independent non-player physics and historical behavior for modern-only blocks/features.
- Repository revision and start date: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main` at task-worktree creation); 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated source-owner publication. Candidate not yet asserted.
- Source preparation command and log: source-owner-controlled shared publication; no local decompilation run.
- Toolchain/decompiler/remapper versions and options: pending validated readiness records.
- Track declaration: source-only; no Minecraft Wiki, MCPK/Wiki audit output, mod implementation, tests, gameplay, TAS/Gym/server or Docker launch.

## Artifact manifest

Both sides are pending validated `ready/<exact-version>/<namespace>.ready.json` records. Do not use a directory's presence as readiness. Record each readiness JSON path/hash, exact requested and resolved release IDs, source/artifact manifest SHA-256 values, namespace, client jar identity/hash, mapping coordinate/build/path/hash or published-unobfuscated exception, mapped jar hash or exception, source root, method diagnostics, decompiler/toolchain details, and hashes for every cited source/resource before comparing. Current shared ready inventory contains no publication for either 1.21.5 or 1.21.8; exact source request routed to source owner in chat commentary.

## Correspondence and call order

Pending exact sources. Index logical roles and members only after both ready records and source bodies are verified. Required ordered chain: local client input/sample and tick -> player gates/state -> living travel and jump -> entity move/collision/support/fluid -> block/fluid shape/property/callback producers -> movement attributes/effects/equipment/enchantments (excluding health/food/sprint-gate impact) -> external velocity/position writers and packet consumers. Record callers, inheritance, descriptors, state read/write/timing, line anchors and dependency closure on both sides. Do not infer correspondence from names alone.

Navigation-only prior-run leads: the broader 1.20.6--1.21.11 report mentions LocalPlayer input/tick, KeyboardInput/ClientInput, LivingEntity travel, Entity movement and collision, Player edge support, powder snow, movement effects/enchantments, and correction packet handlers. Treat these only as filenames/roles to re-resolve after source publication; no prior findings, hashes, or no-difference conclusions are carried into this pair.

## Coverage ledger

- S1 / local input and tick ordering: pending; source paths, members and lines await validated source publication. Inventory keyboard/controller state, yaw-to-motion, diagonal normalization, sneak/use scaling, sprint timers excluding food-state gates, jump/cooldown, auto-jump, flight toggle, unstuck, riding, previous/current input and tick ordering.
- S2 / player state and pose gates: pending; sources await publication. Inventory pose, dimensions/eye height, swim/crawl, flight ability/speed, item-use state, edge sneaking, stored air speed, field defaults/writers/resets; exclude health/food and sprint-gate consequences.
- S3 / living movement integration: pending; sources await publication. Inventory dispatch, ground/air acceleration, friction, gravity/drag, velocity thresholds, jump/sprint-jump impulse apart from excluded hunger gate, climb clamps, water/lava, swimming/gliding, operation order and post-travel state; follow direct movement attributes/helpers.
- S4 / entity movement and collision: pending; sources await publication. Inventory position/velocity/box movement, axis resolution/order, step candidates/ties, edge probing, support/on-ground, velocity cancellation/restitution, fluid push, collision-query timing and callbacks; follow reachable AABB/shape/world collision helpers.
- S5 / block/fluid movement inputs: pending; sources await publication. Inventory all registered/overridden movement callbacks, friction/speed/jump factors, historical shapes/providers, neighboring-state logic, fluids/flow/height/current, climbables, partial blocks, landing/bounce, contact/slowing; inspect jar resources/tags as dependencies. Modern-only blocks remain modern-only.
- S6 / effects, enchantments, attributes and equipment: pending; sources await publication. Inventory direct motion consumers and complete producer/registration/application/removal/tag/resource chains; explicitly disposition Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Depth Strider, Soul Speed, Swift Sneak, Frost Walker, Riptide, Elytra, use-item slowdown and movement-relevant components. Exclude health/food state and sprint-gate effects.
- S7 / external influences and closure: pending; sources await publication. Inventory incoming velocity/position corrections, player knockback/push, explosions, pistons, launch items, mount/dismount and relevant client packet handlers; record server-supplied boundaries. Revisit all changed dependencies and reachable movement-state writers.

Each broad seed row is provisional and will be split into bounded behavior/member slices as the pair-specific inventory is built. No no-difference or absence claim has been made. Pending count: 7 provisional stage inventories; source-dependent comparison count: 0.

## Dependency queue and blockers

- DEP-SRC-A / S1-S7 / validated publication for exact 1.21.5, aligned namespace, source and artifact manifests, readiness JSON and movement-method diagnostics / required to inspect any exact method body and hash evidence / source owner to publish and route / unresolved (queued, not a comparison blocker yet).
- DEP-SRC-B / S1-S7 / validated publication for exact 1.21.8, same requirements / required to compare pair and verify correspondence / source owner to publish and route / unresolved (queued, not a comparison blocker yet).

## Finding index

No confirmed findings. Candidate count: 0. No candidates discarded. Findings directory will be added only when a source-confirmed behavioral delta exists.

## Resume checkpoint

- Last completed slice: none; project guidance and navigation contract read.
- Next bounded slice and exact files/members to open: after validated source publication, build filename inventories and resolve LocalPlayer/input tick correspondence for both exact releases (navigation stage S1); first request is full entry/caller chain, not just unchanged travel body.
- Outstanding dependencies: DEP-SRC-A, DEP-SRC-B.
- Current assumptions requiring verification: aligned namespace availability; readiness manifest validity and exact ID resolution; method body quality; resource contents and source hashes.

## Source audit closure

- Coverage counts by status: pending 7 provisional stage inventories; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: full comparison not started because neither exact endpoint has validated ready publication. Queued source work does not make the run blocked or complete. No semantic claims have been made.
- Evidence/hash/correspondence audit: not started; requires validating exact readiness JSON, cited SHA-256 values, and relevant method-body diagnostics before any evidence use.
- Runtime validation: not performed (separate workflow).

