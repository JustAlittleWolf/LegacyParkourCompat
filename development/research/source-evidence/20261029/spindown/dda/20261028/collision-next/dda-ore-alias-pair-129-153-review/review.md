# Independent review: ore IDs 129 and 153

## Verdict: ACCEPT (bounded source claims)

Reviewed `.native-source/20261028/collision-next/dda-ore-alias-pair-129-153/report.md` against the exact 1.8.9 `ornithe-feather` and 26.2 `unobfuscated` source trees at the canonical read-only source root.

## Checks

- Recomputed all eight selected Java source digests. Each equals both the report ledger and the corresponding entry in its release `sources.sha256` manifest. The ready-marker and provenance digests in the ledger also match the canonical files.
- Confirmed old registry IDs 129 and 153 map to `emerald_ore` and `quartz_ore`, both `OreBlock` registrations. The base `getStateFromMetadata` returns the default state, whose state definition has no properties. The constructor sets unit bounds; the base collision provider offsets those bounds to the block position. The old `rayTrace` calls `updateShape`, and the base implementation is empty. `OreBlock` adds no shape/bounds override.
- Confirmed 26.2 keys `emerald_ore` and `nether_quartz_ore`, both registered as `DropExperienceBlock` with default `hasCollision=true`. `BlockBehaviour` defaults to `Shapes.block()` and derives collision shape from that shape when collision is enabled. The two blocks add no state properties or shape override.
- The report keeps the conclusion local to state and collision-provider equivalence and explicitly leaves cell visitation, DDA ordering, cache mutation by other blocks, and overall parity open.

## Exact artifact hashes

- Reviewed report: `39857028158B939B190B55D1C30B35C29FB344369042AEA2D9FD325E9AEB1AC3`
- Reviewed source ledger: `26BC84F92F6B1F02CA57DFA8350A4823240318FF448C21F6F9B425BCB4C6455A`
- Review: compute after saving this file.

No product implementation, tests, build, or runtime were inspected or run.
