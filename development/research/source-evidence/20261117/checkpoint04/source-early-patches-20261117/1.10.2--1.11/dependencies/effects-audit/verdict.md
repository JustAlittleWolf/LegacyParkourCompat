# Independent effects-slice source audit: 1.10.2 → 1.11

**Verdict: ACCEPT — bounded local-player movement-effect slice only.**

## Bound input

Audited report: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/effects-player-movement.md`  
SHA-256: `64afa1ed594432bb1958b452b5fe14cc1f823bee8e486bd84c06c47d6262df3f`

The report's two release markers/manifests and all eight paired source files were checked. Recomputed SHA-256 values for all 16 source files match their exact source-manifest rows; the report's marker and source-manifest identities match the source roots. This audit is limited to the report's effect formulas, lifecycle, and client packet paths. It does not accept full pair coverage.

## Verified

- The built-in speed/slowness modifier definitions and arithmetic agree across the pair: UUIDs, amounts, operation, replacement sequence, and amplifier multiplier are unchanged. `PlayerEntity.getSpeed()` reads the movement-speed attribute in both versions. Effect-driven modifier install/remove/reapply remains behind `!world.isClient`; the client attribute packet handler applies received base values and modifiers on the client thread.
- The local-player jump path dispatches through `PlayerEntity.jump()` to the unchanged `LivingEntity.jump()` formula. Jump boost is added before the sprint impulse in the same order in both versions. The levitation travel expression is also unchanged (`velocityY + (0.05 * (amplifier + 1) - velocityY) * 0.2`).
- Status-effect ticking retains should-apply → apply → duration decrement order. Status-effect add/remove handlers use `PacketUtils.ensureOnSameThread`, construct from packet fields, and remove the requested effect; the two packet classes are byte-identical across the edge.
- The hunger effect's `addFatigue(0.025F * (amplifier + 1))` → `addFatigue(0.005F * (amplifier + 1))` change is excluded from local-client movement: `PlayerEntity.addFatigue` writes exhaustion only when `!world.isClient` in both versions.
- The `fromNbt` negative-amplifier clamp in 1.11 is confined to NBT loading. The ordinary client status-effect packet path constructs from packet fields directly; any server persistence-to-packet consequences remain unproven and outside this accepted slice.
- The 1.11 `ConcurrentModificationException` catch only wraps effect-map iteration. Ordinary client packet changes are serialized to the client thread; behavior under concurrent/custom mutation is not established or accepted.

## Additional bounded observation

`PlayerEntity.jump()` also changes exhaustion arguments: sprinting `0.8F` → `0.2F`, and non-sprinting `0.2F` → `0.05F`. The unchanged `addFatigue` server-only guard means these calls do not alter local-client movement state. This is a server-side exhaustion-production difference, not an accepted movement-effect delta. The report's local movement disposition remains valid; this observation does not expand its claims.

## Boundary

Accept only the checked built-in local-client speed, slowness, jump-boost, and levitation effect behavior and the stated exclusions above. Server-side production for unusual saved effect state, custom/concurrent mutation, other movement providers, and complete 1.10.2 → 1.11 pair coverage remain unaccepted/unresolved here.
