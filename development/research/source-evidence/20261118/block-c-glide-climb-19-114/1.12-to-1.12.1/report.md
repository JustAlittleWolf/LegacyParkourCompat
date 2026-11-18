# C source comparison: Elytra branch and climbing fallback, 1.12 → 1.12.1

## Bounded result

**No difference found in the assigned operation between 1.12 and 1.12.1.** The relevant `LivingEntity.java` and `Entity.java` source bytes are identical on both sides and match each tree's `.decompile-provenance.json` source hashes. This closes only the stated Elytra/climbing/fall-distance slice; it does not claim full pair coverage or independent audit.

## Exact source identity

Both trees are local canonical `ornithe-feather` decompilations. Each manifest identifies the same `lpc-decompile-v1` pipeline, Vineflower 1.12.0, and Windows 11 / Eclipse Adoptium 25.0.3 runtime. Exact client and mapping inputs differ per release:

| Release | Mojang client input SHA-256 | Feather mapping artifact | Feather metadata SHA-256 |
|---|---|---|---|
| 1.12 | `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` | `feather-gen2/1.12+build.2` (`...-mergedv2.jar`), SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` | `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4` |
| 1.12.1 | `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` | `feather-gen2/1.12.1+build.2` (`...-mergedv2.jar`), SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` | `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b` |

The raw client SHA-256 values and mapping artifact/metadata hashes above are recorded in each release's local `.decompile-provenance.json`. Hashes below were recomputed from the source files and match the corresponding `sources` entries in those manifests:

| Relative source path | 1.12 SHA-256 | 1.12.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

## Compared behavior and bounds

All references are line ranges in the relative source files above, under each release's `decompiled_minecraft/<release>/ornithe-feather/` tree.

- `LivingEntity.java:1842–1847`: after travel input scaling, `flyingTick()` runs before `moveRelative(...)`.
- `LivingEntity.java:1854–1872`: `flyingTick()` copies entity flag 7 into local `bl`; if it is true and the entity is neither grounded nor riding, the chest slot must hold an Elytra for which `ElytraItem.canFly` is true or the local result becomes false. It writes the calculated flag only when `!world.isClient`; the logical client does not locally clear or update this flag here. `LivingEntity.java:2184–2186` confirms `isFallFlying()` simply reads flag 7.
- `LivingEntity.java:1425–1477`: for locally controlled / locally updated entities outside water and lava (with the stated player-flying exceptions), the `isFallFlying()` branch takes precedence. Before any flight acceleration or `move`, it checks the **pre-move** vertical velocity with strict `velocityY > -0.5`; if true, it assigns `fallDistance = 1.0F`. At exactly `-0.5` the assignment does not happen. It then applies flight math, scales velocity, and calls `move(SELF, ...)`. The branch's grounded flag clear is server-only.
- `LivingEntity.java:1478–1517`: the `else` branch is the dry non-fall-flying path. Its climbing check and ladder/vine velocity clamps / `fallDistance = 0.0F` occur before `move`; the post-move horizontal-collision climbing response follows. Thus, while the active flag selects the fall-flying branch, feet-on-ladder/vine climbing reset and clamp do not run in that call.
- `LivingEntity.java:1006–1017`: climbing reads the vanilla state at `floor(x), floor(getShape().minY), floor(z)` (player spectators excluded); ladder and vine states there count as climbing.
- `Entity.java:697–716, 890–899`: collision resolution updates `onGround` from vertical collision and the original requested downward displacement, then calls `checkFallDamage` with resolved `dy`. If landed, it consumes positive fall distance and resets it to zero; otherwise a negative resolved `dy` increases it via `fallDistance - dy`, cast to `float`. This update follows the `move` call, so it can alter the earlier 1.0F assignment (or the climbing reset in the other branch).

The compared `LivingEntity.java` and `Entity.java` hashes are identical across the two releases, so these gates, operation order, threshold, assignment, branch precedence, and collision/fall-distance follow-up are unchanged in this bounded comparison.

## Immutable report identity

Report path: `c-glide-climb-112-1121-alias-gate/report.md`