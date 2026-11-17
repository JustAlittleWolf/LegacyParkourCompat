# Independent audit verdict: Block collision API dispatch

**Verdict: ACCEPT**, bounded to the API dispatch and caller bindings stated in the report. The exact report `build/source-early-patches-20261117/1.11--1.11.1/providers/block-collision-api.md` was SHA-256 `CDD08F4D0DB89F4F6F70F2C16683B8A02E105F5B1924FFF223D8B80204FA4421` at audit.

## Identity and evidence checks

The 1.11 and 1.11.1 ready-marker, provenance, source-manifest, and artifact-manifest SHA-256 values in the report match the local root sidecars. I independently rehashed all seven listed API/caller source files at both endpoints; every file hash matches both the report and its corresponding `ornithe-feather.sources.sha256` row.

The inspected method bodies support the reported API change:

- `BlockProperties.addCollisions`, `StateDefinition.State.addCollisions`, and `Block.addCollisions` gain a trailing `boolean forceShape` in 1.11.1. The state forwards it unchanged to virtual block dispatch. The base block fallback accepts but does not read it, and still obtains `state.getCollisionShape(world, pos)` before the same offset/intersection append path.
- `World` passes `false` for ordinary block collision scans. `LocalClientPlayerEntity.move` delegates to `super.move`; `Entity.move` queries `World.getCollisions`, whose block scan reaches the selector with `false`. The separate local-player auto-jump scan also uses the ordinary `World.getCollisions(this, box)` overload.
- `MovingBlockEntity` supplies `true` for moved-state shape/bounds collection and for the offset moved-state query, while the retracting source-state query supplies `false`. This confirms caller arguments only; it does not establish a player-visible effect.

The report's opening phrase says “four inspected API source files” although its tables list seven API/caller files; all seven were checked and their identity rows are correct. This wording discrepancy does not affect the bounded findings.

## Limits

No per-provider collision output, provider-specific meaning of `forceShape`, movement consequence, complete provider inventory, or pair freeze is accepted by this audit. The pair remains partial.
