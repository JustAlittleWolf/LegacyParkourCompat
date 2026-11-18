# Independent blind source audit — 1.13.1–1.13.2 glide/climb alias gate

## Verdict

**ACCEPT the bounded no-difference claim.** The submitted claim is supported for the stated dry, ordinary, already-active local player with a valid chest Elytra at ladder/vine contact. Both exact `LivingEntity.java` files are byte-identical; the differing `PlayerEntity.java` files have an identical scoped override. This verdict covers only the submitted alias gate and is not full-pair coverage, finding acceptance, or pair freeze.

## Immutable input and evidence identity

- Frozen input: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-climb-1131-1132-alias-gate\report.md`; SHA-256 `dd2b774ca3478e0beea2bb15e07c5aff274b04641f603e7e334b821c85d0c703` (independently recomputed).
- Canonical source provenance: 1.13.1 `decompiled_minecraft/1.13.1/ornithe-feather.provenance.json`, SHA-256 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`; 1.13.2 equivalent, SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`.
- Ready markers: 1.13.1 `ornithe-feather.ready.json` SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`; 1.13.2 SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`. Both markers report ready, the exact version and `ornithe-feather`; provenance identifies Feather Gen2 build.2 and Vineflower 1.12.0. The 1.13.1 provenance records Java 25.0.3+9-LTS. The 1.13.2 provenance/marker records Java 25 (its marker does not state a patch version).
- Canonical source manifests: 1.13.1 SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228269c932d3abba`; 1.13.2 SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Manifest entries match the recomputed scoped source-file hashes below.
- Recomputed scoped file SHA-256s (both versions): `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. `PlayerEntity.java`: 1.13.1 `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`; 1.13.2 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.

## Independent trace

Source references below are relative to `decompiled_minecraft/<version>/ornithe-feather/`; matching line ranges were checked in each release.

- `net/minecraft/entity/living/LivingEntity.java`: `travel` calls `flyingTick()` before `moveRelative(...)` (1.13.1 and 1.13.2, lines 1920–1926); active-state validation and server-only flag write (`flyingTick`, lines 1938–1956); active-bit reader (`isFallFlying`, lines 2314–2316); movement gate, dry checks and glide dispatch (lines 1478–1488); strict threshold and immediate `1.0F` assignment before the glide branch's first move (lines 1488–1523); ordinary climb handling and its later post-move collision check (lines 1537–1578). The whole files are byte-identical.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `moveRelative` override (1.13.1 and 1.13.2, lines 1442–1468). The files differ overall, but the scoped override is text-identical.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: whole-file digest is identical in the pair; no activation behavior is inferred from it.

- `LivingEntity.travel` calls `flyingTick()` immediately before `moveRelative(...)` in both releases. In `flyingTick`, the provisional active state comes from flag 7. When that bit is set and the entity is airborne and not riding, chest-slot Elytra validity is checked; the recomputed bit is written only under `!world.isClient`. `isFallFlying()` directly returns flag 7. Thus, for the stated valid-Elytra client precondition, the already-active flag controls the subsequent movement branch; `flyingTick` does not perform a client-side flag write/cancel. This does not establish activation origins.
- `LivingEntity.moveRelative` first has the local/logical movement gate and dry-path gates, then tests `isFallFlying()`. In that branch, the strict test is the `double` comparison `velocityY > -0.5`; when true it immediately assigns the float literal `1.0F` to `fallDistance`, before the first `move(SELF, ...)`. This matches in both versions.
- The ordinary branch is the `else` of the active-glide test. Its `isClimbing()` predicate and pre-move cleanup (horizontal clamps, fall-distance reset, downward-velocity clamp and sneaking adjustment) are therefore not reached while active glide is true. Its separate horizontal-collision climb predicate is after the ordinary branch's first move, also outside the active-glide path. The glide branch has its own first move and does not evaluate the ladder/vine climb predicate.
- `PlayerEntity.moveRelative` has identical scoped behavior despite different whole-file hashes: ordinary non-ability-flying movement delegates to `super.moveRelative`; the ability-flight branch clears flag 7 after that call. The assignment excludes ability flight, so this does not change the bounded result. `LocalClientPlayerEntity.java` is identical, but no activation behavior is inferred from it.

## Bounds

Accepted only: the dry, already-active glide dispatch, threshold/assignment, first-move scope and climb bypass, plus the immediate flag reader and tick-side validation/write authority. Activation or flag origins, `isClimbing` internals, ladder/vine shape/contact behavior, collisions or resulting trajectories, other movement branches, and complete tick/dependency closure remain outside this audit.

