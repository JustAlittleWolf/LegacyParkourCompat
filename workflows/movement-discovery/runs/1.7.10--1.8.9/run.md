# Discovery: 1.7.10 to 1.8.9

- Status: partial
- Scope: client player movement; older A = 1.7.10; newer B = 1.8.9
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26. Branch: `feat/movement-discovery-1-8`.
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather for both exact releases; explicit `--mappings=feather` per invocation. Resolver selected `ornithe-feather` with release-specific builds `1.7.10+build.2` and `1.8.9+build.2`; no mapping file crossed versions.
- Source preparation command and log: `gradlew.bat decompileMinecraft --versions=1.7.10 --mappings=feather` (`../../../../build/movement-discovery-1.7.10-feather.log`) and `gradlew.bat decompileMinecraft --rerun-tasks --versions=1.8.9 --mappings=feather` (`../../../../build/movement-discovery-1.8.9-feather.log`). Both logs report the exact request/resolution, mapping build, successful completion and `BUILD SUCCESSFUL`.
- Toolchain/decompiler/remapper versions and options: JDK 25.0.3+9-LTS (decompiler fork; target bytecode Java 8); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; Gradle 9.7.1; default 4G decompiler heap; Tiny Remapper options are `ignoreConflicts`, `renameInvalidLocals`, `rebuildSourceFilenames`, `fixPackageAccess`. The 1.7.10 log reports eight Vineflower switch simplification errors, none in the indexed movement methods. The required movement files exist on both sides; no damaged relevant method was reported.

## Artifact manifest

Paths are relative to this run directory. Decompiled source remains ignored and is exposed through the worktree's verified junction to the shared source root.

| Side | Release | Source root | Client jar path; SHA-256 (publisher SHA-1) | Mode / namespace | Mapping coordinate; path; file SHA-256 | Remapped jar path; SHA-256 |
|---|---|---|---|---|---|---|
| A | 1.7.10 | `../../../../decompiled_minecraft/1.7.10/ornithe-feather/` | `../../../../build/minecraft-decompile-cache/1.7.10/client.jar`; `A4FC2284657544E0F4BCC964F927C2FDA3E3A205178ED1D5D58883AAF9780CCE` (`e80d9b3bf5085002218d4be59e668bac718abbc6`) | `feather` / Ornithe Feather | `feather-gen2:1.7.10+build.2`; `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.7.10+build.2.tiny`; `BEE4C201A7F815EFC947EAFEC2A300D59E5E869F9730F5E76319285D2BD59485` | `../../../../build/minecraft-decompile-cache/1.7.10/client-ornithe-feather.jar`; `23F11A647BA05C65974F8034E1F0FB57FF65E4303AFE1A45EF6F6BB859C0E80A` |
| B | 1.8.9 | `../../../../decompiled_minecraft/1.8.9/ornithe-feather/` | `../../../../build/minecraft-decompile-cache/1.8.9/client.jar`; `14F0D96D1A56FB4F5C3B2233D00699525893FE5CE3DCF181E7DE59120595D298` (`3870888a6c3d349d3771a3e9d16c9bf5e076b908`) | `feather` / Ornithe Feather | `feather-gen2:1.8.9+build.2`; `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.8.9+build.2.tiny`; `DE2023EA2CCA9921402FBFCFE6E475F41DA4932EA6DBC609E35C505B76A32C63` | `../../../../build/minecraft-decompile-cache/1.8.9/client-ornithe-feather.jar`; `B6909198FA0E23C442488F79A914709C444FAE6C37A80DDFB3BE23C67EA2067F` |

- Namespace bridge: not applicable. Both Tiny files declare `official`, `intermediary`, and `named`; the decompile task remaps `official` directly to Feather `named`, without an intermediate remapped jar.

Hashes for all source files cited in findings:

| Release | Relative source path | SHA-256 |
|---|---|---|
| A | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `E5FC1D40329A80720D5FCCB60E97E94CA6CDD6F61C5CAB47D91A2A9CC2470BB6` |
| B | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `A5E5B2033322F8867CD845E4095CEA7A82888F6382CBFAFEFC559CF9BA3A76DD` |
| A | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `11B9C8E9691E434D9F80BDCFAE3E5A62143817DC9AE0CA810C05974C8F8F548B` |
| B | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053` |
| A | `net/minecraft/client/entity/living/player/InputClientPlayerEntity.java` | `1E37EEAEBB22B759BFCAE11C8C08F24B37DFF7EBF5F5B17531F92E6CFBB48794` |
| B | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `B322144FCC965C9BF5F6FF18882D17F6F0E1CDAF0E545B1C7663C662ACEB029B` |
| A | `net/minecraft/entity/living/effect/StatusEffect.java` | `7F49E413E1BEFDEA92BE7C61B8DD71618DAFE5534087C22D89234B012EC1DCA6` |
| B | `net/minecraft/entity/living/effect/StatusEffect.java` | `F9BB4D1839337229CB6F6D8DC9AE17A49CFAD757A42388E00ABBAA68BE2B21A3` |
| A | `net/minecraft/item/Items.java` | `5A60696EB2924233D12E8734ECFAD8CBDAFEC11B690D73246DE1FE80ADCB1128` |
| B | `net/minecraft/item/Items.java` | `01E99B0BC18922292E0123C4A7ED6DE6554F1AE08064FC0E897D83CCE1D5EA10` |
| A | `net/minecraft/entity/living/player/PlayerEntity.java` | `350901699E449537372E1C4AF6A79AE256DE39638CDAD049DFC7800B6607791C` |
| B | `net/minecraft/entity/living/player/PlayerEntity.java` | `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88` |
| A | `net/minecraft/entity/living/player/PlayerAbilities.java` | `0B6AD59AF31D6935E00B82BBDE28A2BF0F9619A9B1F301093F0721007D99CAEF` |
| B | `net/minecraft/entity/living/player/PlayerAbilities.java` | `B0350CA7819F995E4EDE358A9D71270CC7A073A9D8817CE218D363CCA7EA0CB0` |
| A | `net/minecraft/entity/living/LivingEntity.java` | `5C1C5CF82D98C2F57A65B857FE02975B749C0562E4C1437AB5F072E202CA941C` |
| B | `net/minecraft/entity/living/LivingEntity.java` | `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E` |
| A | `net/minecraft/entity/Entity.java` | `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69` |
| B | `net/minecraft/entity/Entity.java` | `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B` |
| A | `net/minecraft/enchantment/Enchantment.java` | `36927FF668597F890EBE69B729CD50863D498084D466FC5E407DF40311D05F43` |
| B | `net/minecraft/enchantment/Enchantment.java` | `F42E778392EDDD8A4499B3AE074B059AD0984DCF4A1D775E5933914AE854DD74` |
| A | `net/minecraft/enchantment/EnchantmentHelper.java` | `B85ACA527B0636E6DE8E73082698F627CB1123DABDD9FC3961E5B96EAFEBD9AD` |
| B | `net/minecraft/enchantment/EnchantmentHelper.java` | `71CD5B3766A30B1A5CAB0245719E75BE0CEF1B62DF82DBE3E326F94857716D99` |
| B | `net/minecraft/enchantment/DepthStriderEnchantment.java` | `D0225DAA535309D85A83284A69BB6499617DEE89D3592542AF25427E46496B5D` |
| A | `net/minecraft/block/Blocks.java` | `B912E4C682F9E1FFECD916F3EE5CC6FF1BBA3196E0634E20442B618A0A062E11` |
| B | `net/minecraft/block/Blocks.java` | `1DA2D85406ED991E8F6BC1F420BABF0BD43598478F347C6A4DC9FEA322ED0695` |
| A | `net/minecraft/block/Block.java` | `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B` |
| B | `net/minecraft/block/Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| B | `net/minecraft/block/SlimeBlock.java` | `FA00F5B8903FA580E860CD72CFC11827A06EAAD4600D05BD0F209849B04CBAFA` |
| A | `net/minecraft/block/IceBlock.java` | `8D74437A51BF54CECFA4BD6FCF93900BC80CFA185F4A116307F93EC45FDA60F8` |
| B | `net/minecraft/block/IceBlock.java` | `DAE1D397D40F5167AF82FBDE78174F259ADE1415C96E4D34C8C31C72BDB40A3A` |
| A | `net/minecraft/block/PackedIceBlock.java` | `A396A37D65103A8FBD762EC0ABB8292CC69DC58C95707AC764571BB0393CC7F9` |
| B | `net/minecraft/block/PackedIceBlock.java` | `A396A37D65103A8FBD762EC0ABB8292CC69DC58C95707AC764571BB0393CC7F9` |
| A | `net/minecraft/block/SoulSandBlock.java` | `6216E0BD9C35B694BA0269B164FFB3349923BF7731AA791CAE6CF963390C0650` |
| B | `net/minecraft/block/SoulSandBlock.java` | `10988504552E0B699D99FA19F17ACADF9CCD32D5EC886C6432D35C96407DEDCC` |
| A | `net/minecraft/block/CobwebBlock.java` | `2C287985F8CF05C1DD15B8800EF9995E7F00208D68A2DC12E3D5D54A9A9B3B3C` |
| B | `net/minecraft/block/CobwebBlock.java` | `60CB99FC4C1A891305F7E86C4268794F34958DC1FDB3C6E14F955BD6DF91AF62` |

No data-driven movement definitions are required by these cited vanilla enchantment/block behaviors. Equipment and synchronized world/block state remain external inputs to the client consumer.

## Correspondence and call order

- Input producer: `KeyboardInput.tick()V` -> same method on both releases, lines 13-34. Key reads, movement-axis increments and sneak scaling have identical operation order. B makes `options` final; no movement delta found in this slice.
- Local client player: `LocalClientPlayerEntity.tick()V` -> superclass player tick -> input sampling and `LivingEntity.mobTick()` -> jump and `moveRelative(sidewaysSpeed, forwardSpeed)` -> `Entity.move(dx,dy,dz)`. The immediate superclass changes from A `InputClientPlayerEntity` to B `ClientPlayerEntity`; full state migration remains to be audited.
- Living movement: `LivingEntity.moveRelative(FF)V`, `jump()V`, and `mobTick()V` exist on both sides. B adds `getJumpStrength()F`, `jumpInWater()V`, and `jumpInLava()V`; inspected bodies preserve the old `0.42F`, `0.04F`, jump-boost and sprint impulses. In the dry branch, the audited acceleration/friction values and operation order match. B gates `moveRelative` with `isLocallyControlled()`, which local client player overrides on both releases.
- Collision: `LivingEntity.moveRelative` -> `Entity.move(DDD)V` -> world collision query -> Y/X/Z clipping -> position and on-ground flags -> fall/collision callbacks. A clears vertical velocity in the movement loop; B routes vertical collision to `Block.beforeCollision` after fall handling.
- New movement consumers: B `Blocks.SLIME` -> `SlimeBlock` landing/collision/step callbacks; B `Enchantment.DEPTH_STRIDER` -> `EnchantmentHelper.getDepthStriderLevel(Entity)` -> water branch of `LivingEntity.moveRelative` -> input scale and horizontal drag. A's registration and consumer sources contain no matching entries.
- State in confirmed deltas: movement input axes; player position/bounding box; horizontal and vertical velocity; grounded/collision flags; landing/support block; equipped enchantment level. The server supplies synchronized equipment and block state; only client-side consumption is claimed.

## Coverage ledger

- Slice 1.1 / stage 1 / keyboard input sampling: `compared-no-difference`. A and B `KeyboardInput.java:13-34`; hashes above. Identical key sampling/order and sneak scaling. Sprint/tick gates remain open.
- Slice 1.2 / stage 1 / local player tick and input superclass correspondence: `in-progress`. A and B `LocalClientPlayerEntity.java:69-78` / `105-115`; superclass extraction is visible but sprint/input field migration is not indexed.
- Slice 1.3 / stage 1 / sneak-release transition into double-tap sprint: `findings` (`MD-03`). A `InputClientPlayerEntity.java:141-171`; B `LocalClientPlayerEntity.java:535-564`; B additionally gates on the old sneaking flag.
- Slice 1.4 / stage 1 / item-use input slowdown: `compared-no-difference`. A `InputClientPlayerEntity.java:145-149` and B `LocalClientPlayerEntity.java:540-544` both multiply sideways/forward input by `0.2F` and clear double-tap sprint time when using an item while not riding.
- Slice 2.1 / stage 2 / player gates, abilities, pose, dimensions and item use: `pending`. Full defaults and field writers remain.
- Slice 2.2 / stage 2 / flight abilities and airborne sprint input factor: `findings` (`MD-06`). A `PlayerEntity.moveRelative` lines 1236-1250 temporarily uses `getFlySpeed()` directly; B lines 1278-1292 multiplies it by two when sprinting. `PlayerAbilities.getFlySpeed()` has default `0.05F` in both releases.
- Slice 2.3 / stage 2 / vertical flight input magnitude and precision: `findings` (`MD-07`). A `InputClientPlayerEntity.mobTick()` lines 197-205 uses double `0.15`; B `LocalClientPlayerEntity.mobTick()` lines 596-603 uses `getFlySpeed() * 3.0F` under a camera guard.
- Slice 2.4 / stage 2 / camera-only flight velocity gate: `in-progress`. B's `isCamera()` guard is present at `LocalClientPlayerEntity.java:596`; resolve when the local player stops being the active camera and whether player movement remains reachable in that state.
- Slice 3.1 / stage 3 / dry ground/air acceleration and jump impulse: `compared-no-difference`. A and B `LivingEntity.java:1080-1164` / `1092-1168`; inspected movement scalars/constants/order match. Other branches remain.
- Slice 3.2 / stage 3 / water movement with Depth Strider: `findings` (`MD-01`). A `LivingEntity.java:1177-1188`; B `LivingEntity.java:1199-1225`; helper/registration evidence is in the finding.
- Slice 3.3 / stage 3 / Speed and Slowness modifiers plus Jump Boost impulse: `compared-no-difference`. `StatusEffect.java` A lines 20-27 and B lines 24-31 use the same movement attribute UUIDs, amounts (`0.2F`, `-0.15F`) and operation; A/B `LivingEntity.jump()` lines 1080-1093 / 1096-1109 use the same `0.42F`, Jump Boost amplifier formula and sprint impulse. This does not close all effects/attribute interactions.
- Slice 3.4 / stage 3 / flying movement input while airborne: `findings` (`MD-06`). The temporary `speedInAir` value used by `LivingEntity.moveRelative` gains a sprint multiplier of two in B.
- Slice 4.1 / stage 4 / vertical collision and landing callback: `findings` (`MD-02`). A `Entity.java:457-481,567-579`; B `Entity.java:545-573,700-711`; X/Z clipping and step candidate ordering remain open.
- Slice 4.2 / stage 4 / edge-sneak, step-up and complete axis clipping: `pending`. The inspected beginning of the edge-probe loops retains the `0.05` increments, but the complete candidate order and tie-breaking need a bounded comparison.
- Slice 5.1 / stage 5 / slime landing and fall response: `findings` (`MD-02`). A `Blocks.java` registration inventory and base `Block.java:1073-1074`; B `Blocks.java:193,401`, `SlimeBlock.java:24-39`, `Entity.java:571-573,700-711`.
- Slice 5.2 / stage 5 / slime ground-contact damping: `findings` (`MD-05`). A `Entity.move` calls `onSteppedOn` after the distance threshold; B calls before that threshold on eligible grounded moves and slime scales X/Z velocity. See finding evidence.
- Slice 5.3 / stage 5 / slime ground slipperiness: `findings` (`MD-04`). B slime's `0.8F` value is consumed by the grounded movement branch; A has no slime registration.
- Slice 5.4 / stage 5 / shared cobweb, soul-sand and ice movement inputs: `compared-no-difference`. A/B `CobwebBlock.onEntityCollision` calls `onCobwebCollision`; A/B `Entity.onCobwebCollision` sets the flag and clears fall distance; `Entity.move` uses the same `0.25`, `0.05F`, `0.25` displacement scaling and clears all velocity components. A/B `SoulSandBlock.onEntityCollision` multiplies X/Z velocity by `0.4`; A/B `IceBlock` and `PackedIceBlock` assign slipperiness `0.98F`. This is limited to those members, not every shape/data dependency.
- Slice 5.5 / stage 5 / remaining registered blocks, neighboring-state shapes, fluid flow and block callback inventory: `pending`.
- Slice 6.1 / stage 6 / Depth Strider registration/equipment/consumer: `findings` (`MD-01`). Registration, category, cap, equipment-level lookup and movement consumer are linked. Other effects, enchantments, attributes and equipment remain.
- Slice 6.2 / stage 6 / Speed, Slowness, Jump Boost and Blindness movement gates: `compared-no-difference`. Speed/Slowness attribute formulas and jump impulse are evidenced above; A/B sprint start both block Blindness. B additionally has the sneak-release guard (`MD-03`).
- Slice 6.3 / stage 6 / Levitation, Slow Falling, Dolphin's Grace, Frost Walker, Soul Speed, Swift Sneak, Riptide and Elytra: `not-applicable`. Neither endpoint registers the listed effects/enchantments in A/B `StatusEffect.java` and `Enchantment.java`; A/B `Items.java` have no Elytra item. These later-version mechanics are outside the pair; this does not establish behavior in later releases.
- Slice 7.1 / stage 7 / incoming corrections, knockback, pistons and external velocity: `pending`. Packet handlers and client-side velocity writers are not yet indexed.

## Dependency queue and blockers

- D1; slices 3.2/6.1; inspect `getHighestEnchantmentLevel`, equipment slots and packet application to the local player; could change the level consumed in water travel; next: trace equipment writers and `EntityEquipmentS2CPacket` handlers both ways. Client lookup is confirmed; synchronization provenance remains open.
- D2; slices 4.1/5.1; enumerate all overrides of collision/landing/step callbacks, movement factors and registered collision shapes; could change support, velocity and friction; next: inventory block classes/registrations and follow changed owners.
- D3; stages 1/2; compare sprint gates/transitions, input-use slowdown, jump timers, flight, swimming/pose and tick ordering after the `InputClientPlayerEntity` to `ClientPlayerEntity` extraction; next: resolve overrides, callers and field writers.
- D4; stage 7; inspect packet handlers for velocity, position correction, piston displacement, riding and launch items; these write movement state outside local travel.
- D5; stages 5/6; trace remaining resource/tag/default and synchronized-server data for each candidate; no external resource was needed for the two cited Java-registered behaviors.

## Finding index

- [MD-01](findings/MD-01-depth-strider-water-movement.md): Depth Strider changes water input acceleration and horizontal drag in B; source-confirmed.
- [MD-02](findings/MD-02-slime-block-landing.md): B adds slime-block bounce and fall damage suppression; source-confirmed.
- [MD-03](findings/MD-03-sneak-release-sprint-gate.md): B blocks the double-tap sprint gate on the tick after sneaking; source-confirmed.
- [MD-04](findings/MD-04-slime-ground-friction.md): B's slime block uses `0.8F` ground slipperiness in player acceleration and drag; source-confirmed.
- [MD-05](findings/MD-05-slime-ground-contact-damping.md): B calls slime's low-speed ground damping before the step-distance threshold; source-confirmed.
- [MD-06](findings/MD-06-sprint-flying-horizontal-acceleration.md): sprinting doubles the flight movement input factor in B; source-confirmed.
- [MD-07](findings/MD-07-flight-vertical-input-rounding.md): B computes vertical flight input from float ability speed and adds a camera guard; source-confirmed.
- Discarded candidate: B's dry `moveRelative` reads slipperiness through `BlockState` and uses `MathHelper.clamp` for ladder X/Z bounds; A uses equivalent access and explicit lower/upper comparisons. No difference is claimed without boundary evidence.
- Discarded candidate: B extracts water/lava jump additions into helpers; each still adds `0.04F`, matching A's inline branches.

## Resume checkpoint

- Last completed slice: stage 6.3, later-version movement effects, enchantments and Elytra absence check.
- Next bounded slice: stage 1.2/2; compare A `InputClientPlayerEntity.mobTick()` and local-player sprint/input methods with B `ClientPlayerEntity` and `LocalClientPlayerEntity.mobTick()`, starting at input sampling and sprint transitions.
- Outstanding dependencies: D1-D5; X/Z collision/step behavior; registered block/shape and fluid inventory; effects/attributes/equipment; external movement-state writers.
- Assumptions requiring verification: `getEquipment()` reflects boots after packet synchronization; the landing block selected by `Entity.move` is slime under the stated top-surface collision precondition.

## Source audit closure

- Coverage counts: compared-no-difference 6; findings 10; in-progress 2; pending 4; not-applicable 1.
- Unresolved gaps and limits: stages 1-7 are not closed; see ledger and D1-D5. This is a bounded partial source audit, not proof of equivalence. Introduction is unknown within `(1.7.10, 1.8.9]`.
- Evidence/hash/correspondence audit: cited source hashes are recorded above; exact releases and Feather builds have successful decompiler logs. Generated source and raw diff output remain local/ignored.
- Runtime validation: not performed (separate workflow).
