# Independent source audit — frozen inputs and trace

## Frozen source identity (captured before candidate report access)

- Pair: Minecraft `1.13.1` → `1.13.2`; mapping lane: Feather, output mapping directory `ornithe-feather` (Ornithe Feather).
- Canonical source roots: `decompiled_minecraft/1.13.1/ornithe-feather/` and `decompiled_minecraft/1.13.2/ornithe-feather/` in `D:\Javastuff\LegacyParkourCompat`.
- `.decompile-provenance.json`: absent in both roots. Thus the local generated tree itself has no embedded decompiler-input provenance manifest; claims below are pinned to the examined bytes by SHA-256, not inferred provenance.
- Mapping and source material identity supplied by assignment: source comparison artifact `c-mid-1131-1132-ability-flight-glide-clear-source/report.md`, expected SHA-256 `4eeec4c6b3d2f117f41d5ddfb22b75bf7b425b1c2dd8898dcb5ae072d518e526`. The report has not been opened as of this freeze.

Selected paired source files (relative to each source root; byte length; SHA-256):

| Relative file | 1.13.1 bytes / SHA-256 | 1.13.2 bytes / SHA-256 |
|---|---:|---:|
| `net/minecraft/entity/Entity.java` | 87212 / `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | 87212 / `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | 88284 / `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | 88284 / `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | 73472 / `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | 73472 / `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | 4812 / `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` | 4812 / `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | 40247 / `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | 40247 / `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/item/ElytraItem.java` | 1722 / `4ff72d8153400a0ce484c568b899a2b8c23c94654bd7c3f5dd38568da3dcf72b` | 1722 / `4ff72d8153400a0ce484c568b899a2b8c23c94654bd7c3f5dd38568da3dcf72b` |

## Bounded operation and call trace frozen before candidate report access

- Local lawful creative-flight toggle / eligibility: `LocalClientPlayerEntity.java:739–784` contains the client flight toggle and the airborne jump + chest Elytra gate that sends `START_FALL_FLYING`; exact surrounding code is unchanged across this pair (whole-file SHA above). The assigned witness additionally fixes dry, airborne, local, nonpassenger, active chest Elytra conditions.
- Player's creative flight branch: `PlayerEntity.java:1442–1468`, `moveRelative(float,float,float)`: records position, applies swim handling first; for `abilities.flying && !isRiding()`, saves `velocityY` and `speedInAir`, sets ability flight speed, calls `super.moveRelative`, restores Y velocity as saved value × `0.6`, restores airspeed, writes `fallDistance = 0.0F`, then directly clears flag 7 with `setFlag(7,false)`; afterward calls movement-related stats. This executes even if the superclass call performs a glide move.
- Superclass glide operation: `LivingEntity.java:1478–1543`, `moveRelative`: requires locally controlled or logical-side movement and dry/non-lava movement; if fall-flying, adjusts fall distance to `1.0F` when `velocityY > -0.5`, derives look-vector and velocity magnitudes, applies glide pitch/lift/drag terms, then calls `move(MoverType.SELF, velocityX, velocityY, velocityZ)`. Its later collision response is server gated; its own flag-7 clear is guarded by `onGround && !world.isClient`.
- Glide-state maintenance: `LivingEntity.java:1938–1958`, `flyingTick`, validates flag 7 against airborne/nonriding and chest-slot Elytra durability/flight eligibility and only writes the maintained flag on non-client worlds. The direct `PlayerEntity.moveRelative` clear is a separate write and follows the superclass glide move in the call stack.
- `LivingEntity.java:2314` is the `isFallFlying()` predicate (shared flag bit 7); exact superclass source bytes are identical across this pair.
- Other paired checked dependencies for this bounded path: base `Entity.java` movement/state source; client-player superclass and Elytra eligibility source, all byte-identical per the hashes above.

No source-generation, product implementation, wiki, backlog, build, test, game/runtime, network or Git-history input was consulted. The candidate report remains unopened at this freeze point.

## Provenance reconciliation and audit verdict

The exact provenance sidecars are version-level files beside (not inside) the source roots; the earlier freeze note that `.decompile-provenance.json` is absent within both roots is correct but incomplete as to the sidecars. I verified these local files after opening the candidate report:

| Version | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Client JAR SHA-256 |
|---|---|---|---|---|---|
| 1.13.1 Feather | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` |
| 1.13.2 Feather | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` |

The sidecars identify Feather Gen2 `1.13.1+build.2` and `1.13.2+build.2`, Vineflower 1.12.0 and Java 25.0.3+9-LTS. The mapping archive and Tiny SHA-256 values in each artifact manifest match the candidate report. The ready markers agree with the sidecar provenance/source-manifest hashes. Source manifest counts and bytes match the source trees (1.13.1: 2710 files / 14,768,136 bytes; 1.13.2: 2711 files / 14,770,525 bytes). Each of the six listed Java files' actual bytes match its source-manifest record.

Independent trace confirms the candidate's bounded claim. `LocalClientPlayerEntity.mobTick` allows a legitimate creative-flight toggle for `abilities.canFly` when jump edges are satisfied and the player is not swimming; existing live Elytra flight is not a toggle gate. With the assigned airborne, local, dry, nonpassenger, usable-chest-Elytra state, `LivingEntity.flyingTick` preserves flag 7 on the client, then the superclass `moveRelative` takes its live-glide branch and performs the SELF move. Returning to `PlayerEntity.moveRelative`, the creative-flight branch restores/scales velocity and performs its own `fallDistance = 0.0F` and `setFlag(7,false)` writes after that move. The glide's conditional `fallDistance = 1.0F` write is earlier and independent. The superclass's landing flag clear is server-only and does not supersede this local post-move clear.

Paired-source comparison confirms the involved methods/files are identical, except `PlayerEntity.java` has only the nonmovement `writeCustomNbt` `DataVersion` literal change (1628 → 1631). The source report's hashes and file-level claim match the canonical source bytes and sidecars.

**Verdict: ACCEPTED — no difference for the assigned bounded state and operation from 1.13.1 to 1.13.2.** This accepts only the stated live-glide/creative-flight path and does not establish full pair coverage, other Elytra/player states, intermediate patch boundaries, or runtime parity.

Candidate input SHA-256 verified before review: `4eeec4c6b3d2f117f41d5ddfb22b75bf7b425b1c2dd8898dcb5ae072d518e526` (`c-mid-1131-1132-ability-flight-glide-clear-source/report.md`).
