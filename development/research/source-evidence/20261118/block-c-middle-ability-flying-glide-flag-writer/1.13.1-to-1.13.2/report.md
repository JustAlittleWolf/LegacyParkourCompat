# 1.13.1 → 1.13.2: creative flight during live Elytra glide

## Scope and result

Blind exact-source comparison for an ordinary, dry, airborne, nonpassenger local player with a usable Elytra in the chest slot, an already-live glide state (entity flag 7), and creative flight legally toggled on. Covered the client tick path, Elytra eligibility and glide-state validation, the actual movement call, the post-move flag-7 clear, the flight/glide scheduler path, and the separate fall-distance writer.

**Result: no movement difference found for this state and operation from 1.13.1 to 1.13.2.** The compared movement method bodies and scheduler files are byte-identical. `PlayerEntity.java` differs only at `writeCustomNbt`, where the serialized `DataVersion` changes from 1628 to 1631; this is outside movement.

## Reachability and source behavior (both versions)

`LivingEntity.tick` invokes virtual `this.mobTick()` at line 1752. The local player's `mobTick` legally toggles `abilities.flying` when `abilities.canFly`, the second jump is pressed, the prior jump state is false, the input is not in the auto-jump path, and the player is not swimming (`LocalClientPlayerEntity.java:760–774`). It then calls `super.mobTick()` (`:837`), reaching `PlayerEntity.mobTick` and `LivingEntity.mobTick` (`PlayerEntity.java:464–481`, `LivingEntity.java:1852–1927`). The latter runs `flyingTick()` before dispatching `moveRelative(...)`.

For an airborne nonpassenger with flag 7 set, `LivingEntity.flyingTick` validates chest-slot Elytra usability (`LivingEntity.java:1938–1956`). On the client it does not write flag 7; the live flag therefore reaches the same tick's movement call. `LivingEntity.isFallFlying()` reads flag 7 (`:2314–2316`). In `PlayerEntity.moveRelative`, the creative-flight/nonpassenger branch saves vertical velocity and `speedInAir`, sets flight speed, then calls `super.moveRelative(...)` (`PlayerEntity.java:1442–1465`). Because flag 7 is still set, the superclass executes its Elytra branch: it computes glide acceleration and drag and calls the actual `move(MoverType.SELF, velocityX, velocityY, velocityZ)` at `LivingEntity.java:1478–1523`. The server-only landing clear in that superclass branch does not run on the local client (`:1534–1536`). After the actual move returns, the `PlayerEntity` branch restores the saved speed, scales the saved vertical velocity by `0.6`, resets fall distance, and clears flag 7 (`:1454–1462`). Thus an already-live glide and newly toggled creative flight coexist through one Elytra movement call, with the local post-move clear occurring afterward. The separate glide fall-distance write (`LivingEntity.java:1488–1491`) sets it to `1.0F` only when `velocityY > -0.5`; the creative-flight override writes `0.0F` after the superclass call. Both writers and their order are unchanged across this pair.

The local Elytra-start request is guarded by `!abilities.flying` (`LocalClientPlayerEntity.java:777–781`), so this report's case requires an already-live glide before flight is toggled. That precondition is reachable: the later toggle does not itself gate on `isFallFlying()`, and the current tick proceeds into the superclass movement path before the post-move clear. The assigned state is therefore not inferred from a new glide request while flying.

## Compared slices

- `LocalClientPlayerEntity.mobTick`, jump-toggle and Elytra-start gates, and post-super landing flight reset: lines 653–842. Identical source files.
- `PlayerEntity.moveRelative`, flight branch, superclass dispatch and post-call state writes: lines 1442–1468. Method body identical; the containing source has only the unrelated NBT `DataVersion` change.
- `LivingEntity.moveRelative`, dry-flight gates, Elytra math, actual move and server-only landing clear: lines 1478–1537. Identical source files.
- `LivingEntity.mobTick` → `flyingTick` → `moveRelative` scheduling and flag-7 validation: lines 1852–1927 and 1938–1956. Identical source files.
- `LivingEntity.isFallFlying`: lines 2314–2316; `Entity.move`: line 473 onward. Identical source files.
- `ClientPlayerEntity` and `ElytraItem` source files were hash-checked as supporting caller/eligibility evidence; both are identical.
- `writeCustomNbt` DataVersion change is nonmovement and excluded. No claim is made about other movement operations or other player states.

## Exact source provenance

Canonical read-only source root on each side: `decompiled_minecraft/<version>/ornithe-feather/`. Both readiness markers say `ready`, mapping `ornithe-feather`, and identify the listed Feather Gen2 mapping artifacts. The source-manifest and marker/provenance hashes below were recomputed from the canonical files and agree with the marker's manifest identity.

| Version | Mapping artifact | Client JAR SHA-256 | Source manifest SHA-256 | Ready marker SHA-256 | Provenance SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|---|---|
| 1.13.1 | `feather-gen2-1.13.1+build.2` (mergedv2 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`; tiny `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14`) | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |
| 1.13.2 | `feather-gen2-1.13.2+build.2` (mergedv2 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`; tiny `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`) | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |

The source manifest identities above were checked against the ready markers. The provenance files identify Vineflower 1.12.0 and Java 25.0.3+9-LTS on both sides. The 1.13.1 provenance records `net.ornithemc:feather-gen2:1.13.1+build.2`; the 1.13.2 ready marker names the corresponding `1.13.2+build.2` mapping artifacts.

## Original relied-on source file hashes

SHA-256 of the exact decompiled Java files, verified against each side's source manifest:

| Relative source path | 1.13.1 | 1.13.2 | Comparison |
|---|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | same | identical |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | same | identical |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | only NBT `DataVersion` differs |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | same | identical |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` | same | identical |
| `net/minecraft/item/ElytraItem.java` | `4ff72d8153400a0ce484c568b899a2b8c23c94654bd7c3f5dd38568da3dcf72b` | same | identical |
