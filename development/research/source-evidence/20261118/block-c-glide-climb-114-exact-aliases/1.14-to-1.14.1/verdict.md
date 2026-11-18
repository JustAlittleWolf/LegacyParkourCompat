# Independent audit: 1.14 → 1.14.1 active Elytra / ladder-vine slice

## Verdict

**Bounded no-difference conclusion confirmed, with two scope/wording notes.** The frozen report's defined ordinary, dry, on-foot, already-active Elytra path reaches the same glide branch in both releases, and that branch bypasses the ladder/vine climb cleanup. The compared relevant method bodies are unchanged. This verdict does not establish complete pair coverage or full tick parity.

The report's path and flag conclusion are supported. For a complete local-player route, include `ClientPlayerEntity.tickMovement()` as an explicit dependency: its new-glide request branch at `:697–702` requires a jump transition, airborne downward motion, `!isFallFlying()`, no ability flight, and a usable chest Elytra. With the assigned already-active flag, `!isFallFlying()` is false, so this branch sends no new-start request. It snapshots `field_3939` from `isFallFlying()` at `:704` before calling `super.tickMovement()` at `:762`; it does not clear the active flag. This region is byte-identical across the pair despite unrelated client GUI mapping/name changes elsewhere in that file.

In `LivingEntity.tickMovement()`, `initAi()` runs after input scaling and before the virtual `travel(...)` call (`1.14`/`1.14.1`: `LivingEntity.java:2198–2205`). `initAi()` reads flag 7, checks airborne/unmounted state and chest-slot Elytra identity/usability, and only writes the resulting flag when `!world.isClient` (`:2216–2235`). Thus for the stipulated client with a valid, already-active Elytra, this updater does not clear the tracked true flag before travel. `ElytraItem.isUsable()` is the same damage-vs-durability predicate in both (`ElytraItem.java:20–22`; file hashes below).

The ordinary dry glide branch is selected before ground travel (`LivingEntity.java:1772–1777`); it reads current velocity and assigns `fallDistance = 1.0F` only for strict `y > -0.5`, before glide math and `move(...)` (`:1775–1803`). At exactly `-0.5` or lower this assignment is skipped. The client does not execute the server-only glide collision-damage or on-ground flag-clear writes (`:1804–1816`). Since branch A precedes branch B, it skips the non-gliding `method_18801(...)` cleanup (`:1817–1827`), which resets fall distance and clamps the post-velocity-update x/z components and downward y velocity when `isClimbing()` is true (`:1921–1935`). `isClimbing()` reads the state at `new BlockPos(this)` and recognizes ladder and vine (plus scaffolding) (`:1216–1229`; `Blocks.java`: 1.14 ladder `:474`, vine `:753`; 1.14.1 ladder `:474`, vine `:749`). The report calls the clamped components “horizontal input”; more precisely, they are velocity components passed through climb cleanup after `updateVelocity`, not the input vector.

`PlayerEntity.travel()` has swimming and ability-flight branches, then calls `super.travel(arg)` for the stipulated ordinary on-foot case (`PlayerEntity.java:1362–1389`). Both versions have identical `PlayerEntity.java` bytes. The report's “already-active” bound is material: this audit does not claim startup eligibility, flag acquisition, invalid Elytra behavior, wet travel, ability flight, or other player states.

## Exact source and provenance verification

Frozen report identity: `c-glide-climb-114-1141-alias-gate/report.md`, SHA-256 `e4cfd443efdb354810f223ff37a1ae5fd9899c874bdae473eef06f1423cf3755` (matches the assigned digest).

Canonical read-only trees: `decompiled_minecraft/1.14/yarn/` and `decompiled_minecraft/1.14.1/yarn/`. Both `.decompile-provenance.json` manifests identify the local `lpc-decompile-v1` pipeline and Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 amd64. Manifest inputs confirm:

- 1.14 Yarn `1.14+build.21`, mapping artifact SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; client artifact SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`.
- 1.14.1 Yarn `1.14.1+build.10`, mapping artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; client artifact SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.

Recomputed source-byte SHA-256 hashes match each manifest's `sources` entries:

| Relative source file | 1.14 | 1.14.1 |
| --- | --- | --- |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | same |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/item/ElytraItem.java` | `3eec99c8e60c1acdcccf64435c0be55a09d121dbd30a911d1ac7e10bdb7985df` | same |
| `net/minecraft/block/Blocks.java` | `62acdf407d1fa85095da0351d441b5ece10681b036505f7b9972bc21f77b0a0d` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |

The full `LivingEntity.java` diff is the unrelated `method_5879()` → `animateDamage()` mapping rename; `PlayerEntity.java` is byte-identical. `ClientPlayerEntity.tickMovement()` lines `607–767` are identical; its remaining file differences are unrelated screen/API mapping renames. `ElytraItem.java` is byte-identical. Relevant `Blocks.java` declarations preserve the ladder/vine registry names at the cited lines. The report correctly presents its result as a bounded no-difference result and leaves full-pair coverage/audit open.
