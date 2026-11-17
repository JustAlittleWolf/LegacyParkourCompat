# Independent source audit verdict: Tripwire shape, 26.1.2 → 26.2

## Bound input and identity

Audited only the assigned source report, its exact 26.1.2 and 26.2 unobfuscated decompilations, and the paired ordinary collision/inside-shape query path needed to verify its claims. The report bytes have SHA-256 `b53dcbb92d5a1d509cc93cba7c8c45351f698368247cecc6c93df869b4943ada`.

Both ready markers report `status=ready`, the assigned version and `mapping=unobfuscated`; their provenance files agree on version and namespace. The marker-declared source and artifact manifest digests match the corresponding manifest bytes. In this checkout those files are named `unobfuscated.provenance.json`, `unobfuscated.sources.sha256`, and `artifacts.sha256` (without leading dots). The report's source-file hashes for TripWireBlock and TripWireHookBlock match both the manifests and exact Java bytes:

- 26.1.2 TripWireBlock `84a31077e77005805cb8f3e697f02c3d3f90c5e671f4ae4304730105d2a6a80d`; TripWireHookBlock `8e9897c46acd70209e82cbc609b92de636c2655b400d823b35390dd63b756f94`.
- 26.2 TripWireBlock `3b379d732599e55b156ffde5ebd0f7043ab4d59e6556f128e830038c77c9249a`; TripWireHookBlock `c08a22c1da82367373a3aab1fcddae4e4821e523216c090a44c60a1b2786a52f`.

## Verdict

**Accept the bounded no-shape-difference conclusion, with one precision correction.** No source difference was found in the wire/hook state properties and defaults, horizontal connection/update rules, paired-state calculation, state propagation, or either collision-shape provider. TripWireBlock returns the same `Block.column(16.0, 1.0, 2.5)` shape when `ATTACHED`, otherwise `Block.column(16.0, 0.0, 8.0)`; its entity-inside collision shape returns `state.getShape(level, pos)` in both releases. TripWireHookBlock's facing-selected rotated shape is also unchanged.

The state-calculation summary needs a narrow correction: `attached` starts as `!isBeingDestroyed`, becomes false on a missing/non-wire position (apart from the designated `wireSource`), and is finally gated by `receiverPos > 1`. The `DISARMED`/armed condition is ANDed into `attached` only at `i == wireSource`; for each scanned wire, `wireArmed && wirePowered` contributes to the separate `powered` accumulator, which is then gated by `attached`. Describing attachment generally as determined by “armed wires” overstates that gate. This wording does not overturn the no-shape-difference conclusion.

The report's player-query linkage is directionally correct, and the ordinary path was independently traced. In both versions, `CollisionGetter.getBlockCollisions` builds a `CollisionContext` from the source entity; `BlockCollisions.computeNext` asks that context for each block's collision shape. `BlockBehaviour.getCollisionShape` dispatches to `state.getShape(...)` when collision is enabled and otherwise returns the empty shape. Entity inside-block processing separately asks `state.getEntityInsideCollisionShape(...)`, intersects the returned shape, then invokes `state.entityInside(...)` for an intersecting block. These routes are equivalent across the pair, aside from line shifts and unrelated surrounding changes. Thus the attached-state shape is observable through the ordinary collision provider, and the tripwire inside-shape override participates in the entity inside-block query.

The observed 26.2 changes are confined to server-side tripwire trigger reevaluation: `entityInside` now suppresses a call when a scheduled tick exists; `checkPressed` schedules delay 0 after a pressed-to-unpressed transition. They do not alter the paired attachment calculation or shape providers. In particular, no evidence in this bounded review links the new zero-delay tick to an `ATTACHED` transition.

## Coverage gate

This is a bounded acceptance only, **not a full-pair source-coverage audit or pair freeze**. Effective world-state inputs and the relevant registrations/resource/tag conditions were not closed or independently verified. Preserve the report's partial-coverage gate; do not infer complete inventory coverage, exact onset, implementation acceptance, or runtime parity from this verdict.

## Evidence pointers

All references are relative to `decompiled_minecraft/<version>/unobfuscated/`:

- `net/minecraft/world/level/block/TripWireBlock.java`: state/shape/placement/update/inside-shape methods around 35–204 in both versions; state-definition tail at 242–245 (26.1.2) / 240–243 (26.2).
- `net/minecraft/world/level/block/TripWireHookBlock.java`: shape/survival/update at 36–81; paired calculation and propagation at 108–194 in both versions.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: default collision dispatch at 333–346 (26.1.2) / 327–340 (26.2); BlockState forwarding at 680–689 / 686–695.
- `net/minecraft/world/level/CollisionGetter.java`: entity collision context and `getBlockCollisions` at 84–104 in both versions.
- `net/minecraft/world/level/BlockCollisions.java`: collision-shape provider dispatch at 79–107 in both versions.
- `net/minecraft/world/entity/Entity.java`: direct collision predicate at 325–328 / 328–331; collision collection and inside-shape processing at 1152–1159 and 1251–1269 (26.1.2), corresponding paths at 1223–1230 and 1334–1352 (26.2).
