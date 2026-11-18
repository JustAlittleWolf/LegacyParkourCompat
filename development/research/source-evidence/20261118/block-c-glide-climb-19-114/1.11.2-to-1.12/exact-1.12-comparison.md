# Exact 1.11.2 → 1.12 active glide/climb comparison

## Scope

Exact adjacent source pair: 1.11.2 to 1.12 (not 1.12.2). Bounded to an already-active, dry, locally controlled player at ordinary ladder/vine feet, with the default chest Elytra fly-capable. Compared active-flag validation/update, which branch actually governs movement, glide versus ordinary climb handling, and fall-distance writes around movement. Glide start/final transitions, fluids, contact variants and collision geometry are outside this check.

## Source identity and hashes

- 1.11.2: local `ornithe-feather` namespace, exact version marker `versionId: 1.11.2`; mapping artifacts `feather-gen2-1.11.2+build.2-mergedv2.jar` and `feather-gen2-1.11.2+build.2.tiny`. Canonical provenance file `decompiled_minecraft/1.11.2/ornithe-feather.provenance.json`, SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; source manifest `decompiled_minecraft/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. Relevant `LivingEntity.java` SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`.
- 1.12: exact version is evidenced by the canonical hidden source provenance `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json`, which pins the 1.12 client input, Feather 1.12 `build.2` mapping artifact and per-source hashes. Provenance SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; client input SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; mapping artifact URL identifies `feather-gen2/1.12+build.2/feather-gen2-1.12+build.2-mergedv2.jar` with SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`. Relevant `LivingEntity.java` SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`, matching the per-source provenance entry.

The earlier `report.md` in this directory is frozen and remains unchanged; its 1.12 side is 1.12.2, confirmed by its version-root marker and Feather artifact. It is not evidence for this exact 1.12 pair.

## Canonical references

- `decompiled_minecraft/1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: `moveRelative(float,float)` lines 1388–1480; `mobTick()` travel call lines 1798–1803; `flyingTick()` lines 1810–1828; `isFallFlying()` lines 2145–2146.
- `decompiled_minecraft/1.12/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: `moveRelative(float,float,float)` lines 1425–1517; `mobTick()` travel call lines 1842–1847; `flyingTick()` lines 1854–1872; `isFallFlying()` lines 2184–2185.

## Result: no delta in the bounded active-only behavior

In both releases, `mobTick()` calls `flyingTick()` immediately before `moveRelative()`. `flyingTick()` starts from flag 7; a true value is kept in its local variable only when the player is airborne, not riding, and has a chest-slot Elytra accepted by `ElytraItem.canFly`. Otherwise that local value becomes false. The actual `setFlag(7, bl)` is guarded by `!world.isClient`, so the local client does not write this candidate value back, even when validation would have cleared it. For this active-only client case, movement reads the already-active flag through `isFallFlying()`; the control flow is the same in both versions.

Both `moveRelative()` methods check local/side movement authority and the water/lava gates, then test `isFallFlying()` before the ordinary branch. In that glide branch, `velocityY > -0.5` uses a double comparison and sets `fallDistance = 1.0F` before glide math and before `move(MoverType.SELF, ...)`. Because the glide branch is selected first, it skips the ordinary branch's `isClimbing()` clamps and `fallDistance = 0.0F` cleanup. The branch ordering, threshold, `1.0F` write, glide movement call and climb bypass match in exact 1.12. The 1.12 method adds a vertical input parameter for ordinary `updateVelocity`; that signature change does not affect this earlier glide branch.

Movement meaning: while the local player already has the active fall-flying flag, glide movement has authority over ordinary ladder/vine cleanup in both exact releases. No 1.11.2→1.12 delta is established for this bounded case. This does not cover launch/final state transitions or other climb/contact combinations.
