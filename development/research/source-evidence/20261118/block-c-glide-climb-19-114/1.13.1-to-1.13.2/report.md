# 1.13.1–1.13.2 glide/climb alias gate

## Result

**No difference in the bounded movement slice.** The exact `LivingEntity.java` source is byte-identical across the pair; the compared player override has different whole-file hashes, but its scoped `moveRelative` body is identical. Therefore this pair does not establish a glide-versus-climb movement boundary for this operation. This is a bounded no-difference result, not full-pair coverage, finding acceptance, or pair freeze.

## Exact inputs and provenance

- Host: Windows PowerShell; project checkout `D:\Javastuff\LegacyParkourCompat`.
- Older source: Minecraft `1.13.1`, `ornithe-feather` (Ornithe Feather Gen2 `1.13.1+build.2`), Vineflower `1.12.0`, Java `25.0.3+9-LTS`. Local provenance `decompiled_minecraft/1.13.1/ornithe-feather.provenance.json`, SHA-256 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`; ready marker SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`. It records exact version `1.13.1`, namespace `ornithe-feather`, mapping coordinate `net.ornithemc:feather-gen2:1.13.1+build.2`, client SHA-256 `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`, and 2,710 source files.
- Newer source: Minecraft `1.13.2`, `ornithe-feather` (mapping artifact names `feather-gen2-1.13.2+build.2`), Vineflower `1.12.0`, Java 25. Local provenance `decompiled_minecraft/1.13.2/ornithe-feather.provenance.json`, SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; ready marker SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`. It records exact version `1.13.2`, namespace `ornithe-feather`, client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, and 2,711 source files.
- Source manifest SHA-256s: 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The manifest entries match the hashes computed from the scoped files below.

## Compared slice

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`; line numbers refer to the exact decompiled source.

- `net/minecraft/entity/living/LivingEntity.java`, 1.13.1 and 1.13.2: whole-file SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` on both sides. `travel` order: `flyingTick()` then `moveRelative(...)`, lines 1920–1927. `moveRelative` local/logical movement gate and dry branch, lines 1478–1488; active-glide strict double condition and literal assignment before the first `move`, lines 1488–1523; mutually exclusive ordinary/climbing branch and its separate later post-move horizontal-collision check, lines 1537–1578. Active-flag reader, `isFallFlying()` returning `getFlag(7)`, lines 2314–2316. `flyingTick`’s chest-Elytra validation and server-only flag write, lines 1938–1957.
- `net/minecraft/entity/living/player/PlayerEntity.java`: 1.13.1 SHA-256 `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`; 1.13.2 SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. The scoped override at lines 1442–1468 is text-identical: for an ordinary non-ability-flying, non-swimming player it delegates to `super.moveRelative`; only the excluded ability-flight branch writes flag 7 false there.
- The checked `LocalClientPlayerEntity.java` hashes are identical: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` in both versions. No local-player activation or other local-player behavior was examined.

## Bounded behavior

For the requested already-active, dry, ordinary local-player precondition (flag 7 already true, no ability flight, and chest Elytra valid), both versions run the base glide branch before the ordinary climb branch. The source uses the strict comparison `this.velocityY > -0.5` (a `double`) and writes `this.fallDistance = 1.0F` before the first `move`. Because the climb handling is in the glide branch’s `else`, a ladder/vine climb predicate is not evaluated in that path. If the active-glide test is false, the ordinary branch evaluates climbing before its first move; the later horizontal-collision climb check is a distinct post-move scope. These gates and order are identical in the pair.

`flyingTick` starts from flag 7, validates chest Elytra only while the bit is already set and the player is airborne and not riding, and writes the recomputed bit only when `!world.isClient`. Thus this bounded client path consumes an already-active flag; it does not establish how activation occurs. `PlayerEntity.moveRelative`’s ability-flight flag clear is separate and excluded by the ordinary-player precondition.

## Bounds and next action

This covers only the dry, already-active glide-versus-climb dispatch/threshold slice and its immediate active-bit reader/writer. It does not cover activation or flag origins, vanilla `isClimbing` internals/shape and contact behavior, fluids, collision outcomes, post-move trajectories, other movement branches, inherited caller/dependency closure, or the rest of the 1.13.1–1.13.2 player tick. No implementation, finding file, wiki lookup, tests, build, or launch was performed. Broader pair coverage and an independent audit remain open.