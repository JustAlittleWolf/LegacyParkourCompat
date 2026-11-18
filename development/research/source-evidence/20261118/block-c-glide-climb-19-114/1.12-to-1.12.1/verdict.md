# Independent source audit: Elytra climbing alias gate, 1.12 → 1.12.1

## Verdict

**ACCEPTED — bounded no-difference claim confirmed.** The frozen report is bound to SHA-256 `3366c546a958bc60ae98d9ac86dbe291e477f87005a33508772d934bf2dd7d13`. Its assigned operation and witness are supported by the exact local canonical Feather sources. This verdict closes only the reported Elytra/climbing/fall-distance slice; it is not a full-pair coverage audit and does not establish runtime parity.

## Source identity and integrity

Canonical sources inspected read-only under `decompiled_minecraft/<release>/ornithe-feather/`:

- Both releases use the `lpc-decompile-v1` pipeline, Vineflower 1.12.0, and Windows 11 / Eclipse Adoptium 25.0.3 runtime, as recorded in each `.decompile-provenance.json`.
- 1.12 inputs: client `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; Feather `1.12+build.2` mergedv2 artifact `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; Feather metadata `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`.
- 1.12.1 inputs: client `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`; Feather `1.12.1+build.2` mergedv2 artifact `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; Feather metadata `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`.
- Canonical 1.12 provenance manifest SHA-256: `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- Canonical 1.12.1 provenance manifest SHA-256: `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- In both canonical trees, actual `net/minecraft/entity/living/LivingEntity.java` SHA-256 is `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; actual `net/minecraft/entity/Entity.java` SHA-256 is `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. Each matches its own manifest `sources` entry, and the files are byte-identical across releases.
- The adjacent hidden `.ornithe-feather.previous-*` trees have the same per-release manifest hashes and the same two actual source hashes as their canonical counterparts. They corroborate, but were not used in place of, the canonical sources.

## Claim checks

1. **Updater order and authority:** `LivingEntity.java:1842–1847` places `flyingTick()` before `moveRelative(...)` after movement-input scaling. In `flyingTick()` (`:1854–1872`), an active flag 7 is retained only when airborne, not riding, and equipped with an Elytra accepted by `ElytraItem.canFly`; only the server writes the recomputed flag (`:1870–1871`). Thus the client-side updater does not locally clear the already-active flag in this witness. `isFallFlying()` reads flag 7 (`:2184–2186`). The local player’s `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (`:484–485`).
2. **Dry ladder/vine witness and branch precedence:** For an ordinary local player with active flag 7, a valid chest-slot Elytra, airborne/not riding state, and dry movement, `moveRelative()` passes its local-control and water/lava gates (`LivingEntity.java:1425–1429`) and enters the fall-flying branch. `isClimbing()` identifies ladder/vine from vanilla block state at `floor(x), floor(shape.minY), floor(z)` (`:1006–1017`), but its ladder clamps and `fallDistance = 0.0F` are only in the `else` non-fall-flying branch (`:1478–1514`). They therefore do not run for this already-active glide witness.
3. **Strict threshold and pre-move assignment:** The fall-flying branch tests the pre-move `velocityY > -0.5` and assigns `fallDistance = 1.0F` (`LivingEntity.java:1429–1432`) before its velocity math and `move(SELF, ...)` (`:1461–1464`). Equality at `-0.5` does not assign 1.0F. The report correctly distinguishes this assignment from the later collision/fall-distance update.
4. **Post-move clipped boundary:** `Entity.java:697–716` resolves collision, derives `onGround` using the original requested downward displacement, and passes resolved `y` (the clipped vertical displacement) to `checkFallDamage`. `Entity.java:890–899` resets fall distance on landing; otherwise only negative resolved displacement increments it, via `(float)(fallDistance - dy)`. This is after `move()` and can replace or add to the earlier 1.0F state as described.
5. **Release comparison:** The exact two paired files are byte-identical across 1.12 and 1.12.1, so the cited gates, ordering, casts, strict comparison, assignments, and post-collision behavior show no difference in this bounded slice.

## Evidence references

All line references above apply identically to both releases because the full source hashes match. Exact canonical source paths:

- `decompiled_minecraft/1.12/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`
- `decompiled_minecraft/1.12.1/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`
- `decompiled_minecraft/1.12/ornithe-feather/net/minecraft/entity/Entity.java`
- `decompiled_minecraft/1.12.1/ornithe-feather/net/minecraft/entity/Entity.java`

Reviewed report: `c-glide-climb-112-1121-alias-gate/report.md`, SHA-256 `3366c546a958bc60ae98d9ac86dbe291e477f87005a33508772d934bf2dd7d13`.
