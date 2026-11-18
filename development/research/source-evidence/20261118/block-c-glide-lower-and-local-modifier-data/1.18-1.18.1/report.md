# 1.18 → 1.18.1: active Elytra fall-distance gate and climbability

## Result

For an already-active dry Elytra glide by a standard local, on-foot player, the checked pre-move fall-distance gate is unchanged from 1.18 to 1.18.1. In both `LivingEntity.travel(Vec3)`, the `isFallFlying()` branch reads the current velocity into `$$13`; before look-vector math, damping, or `move`, it executes `if ($$13.y > -0.5) this.fallDistance = 1.0F;`. The exact comparison is strict `> -0.5`; the assignment is the float literal `1.0F`. There is no separate climbable-only exclusion or climbability-triggered fall-distance reset before this branch's move. This bounded comparison yields no 1.18→1.18.1 behavior finding.

The branch then calculates glide movement and eventually damps velocity with `multiply(0.99F, 0.98F, 0.99F)`, sets it, and calls `move(MoverType.SELF, this.getDeltaMovement())`. The assignment therefore precedes movement and applies regardless of ladder/vine contact when glide is already active, subject to the velocity gate.

## Climbability and activation boundary

In both releases `LivingEntity.onClimbable()` (lines 1429–1443) rejects spectators, then reads `blockPosition()` and `getFeetBlockState()`. It returns true when that feet state is in `BlockTags.CLIMBABLE`; the exact release's `BlockTagsProvider` lists both `Blocks.LADDER` and `Blocks.VINE` in that tag (lines 566–570). Thus a ladder or vine at the feet satisfies the historical climbability predicate for a normal player. The predicate also recognizes usable trapdoors as ladders; that detail is outside the asked ladder/vine case.

Keep the separate activation boundary in view: `LocalPlayer.aiStep()` only attempts jump-triggered start while `!this.onClimbable()` (lines 745–749), with chest Elytra, enabled Elytra, and `tryToStartFallFlying()` also required. In both releases `Player.tryToStartFallFlying()` separately requires airborne, not already fall-flying, not in water, and no levitation, then rechecks enabled chest Elytra before starting (lines 1542–1551). This is upstream activation and does not add a climbability check to the already-active travel branch.

The separate `updateFallFlying()` gate (lines 2586–2610) clears its local active flag if the entity is grounded, riding, affected by levitation, or the chest item is not an enabled Elytra; it has no maximum `fallFlyTicks` cutoff. Active glide ticks increment in the surrounding tick code (2327–2330); the counter gates server-side Elytra durability/game-event work every ten ticks, not this pre-move fall-distance gate. These gates delimit this report; they are not claimed as differences.

## Exact source references

All paths are relative to the read-only canonical source root `decompiled_minecraft/` in `D:\Javastuff\LegacyParkourCompat`; mapping family is Mojmap on each side.

- 1.18: `1.18/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2005–2170 (fall-distance test and write at 2070–2074; `move` at 2098–2099); `onClimbable()` lines 1429–1443; `updateFallFlying()` lines 2586–2610; active tick counter lines 2327–2330.
- 1.18.1: `1.18.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, same members and line ranges.
- 1.18: `1.18/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()`, lines 745–749.
- 1.18.1: `1.18.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, same member and line range.
- 1.18: `1.18/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying()`, lines 1542–1551.
- 1.18.1: `1.18.1/mojmap/net/minecraft/world/entity/player/Player.java`, same member and line range.
- 1.18: `1.18/mojmap/net/minecraft/data/tags/BlockTagsProvider.java`, climbable list lines 566–570.
- 1.18.1: `1.18.1/mojmap/net/minecraft/data/tags/BlockTagsProvider.java`, same list and line range.

SHA-256 of each compared whole source file, equal on both sides:

- `LivingEntity.java`: `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`
- `LocalPlayer.java`: `e38d0bbb6e5b9de2a698609b8491d0007406b3be42ba9b95289f533788c996`
- `Player.java`: `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`
- `BlockTagsProvider.java`: `c2d004e2aa3f23e24836b776ebae3c6df5a6bef91d94a4b3e6d199175669f640`

## Provenance and limits

The available trees are stored under the exact `1.18/mojmap` and `1.18.1/mojmap` paths, and all four relevant whole-file hashes match across the pair. Neither tree contains `.decompile-provenance.json`; the local `.minecraft-decompile-cache/` has no 1.18 or 1.18.1 cache directories or pinned release/mapping inputs. Per `buildSrc/README.md`, these are legacy trees without manifests: presence, matching file names, and local hashes do not prove their original release artifacts, mappings build, or decompiler inputs. I did not regenerate sources or access external mappings. Accordingly, the code comparison and local integrity hashes are recorded, but exact upstream source provenance remains unverified.

This is a narrow evidence report, not a complete 1.18→1.18.1 movement-pair inventory, acceptance, or parity verdict. It covers the active glide gate/write and its requested activation/termination bounds only.




