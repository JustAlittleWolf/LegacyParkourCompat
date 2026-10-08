# Discovery: 1.14.4 to 1.15.2

- Status: partial
- Scope: client player movement; older A = exact Java Edition 1.14.4; newer B = exact Java Edition 1.15.2.
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26.
- Selected naming namespace and alignment: Mojang official names / Mojmap for both releases. Each exact release used its own official `client.txt` mapping artifact. This is the same naming family, not cross-application of one release's mapping to the other. Requested and resolved IDs match both sides.
- Source preparation: A owner command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.14.4 --mappings=mojmap`; owner checkpoint `29f6a2d` records `Finished 1.14.4 using mojmap`. B command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`; success transcript extract is [Source preparation record](#source-preparation-record).
- Log limitation: neither owner's raw console log is retained. A's committed provenance record is checkpoint `29f6a2d`; B's successful-output transcription is labeled as an extract, not a raw log. Both report exact-version successful completion.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default decompiler heap 4G. Separate isolated Gradle user homes were used.

## Artifact manifest

### A — Minecraft 1.14.4

- Source root: `decompiled_minecraft/1.14.4/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.14.4` / `1.14.4`.
- Original client jar SHA-256: `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; size 25,191,691 bytes. Client artifact object `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9`.
- Version metadata SHA-256: `615F466A39D2C19AE9B6E2401AA7BD07DBDA337F976BCCE383326B8D6BA4E532`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `6073e4ba6949217eb708c4512be2ccc1850a603f` (`client.txt`); mapping file SHA-256 `2DD53A5E70BA493CF6E33C0FC52BBDF4C57F9429C7A13842565AA825FD44D910`.
- Remapped client jar SHA-256: `7781BDCC8E8D9173173731F2564866F17C07CB43FE602A9A9F14B2753DC34665`.
- Decompiled source count reported by the owner: 3,250 Java files. Independently checked anchor `net/minecraft/client/player/LocalPlayer.java` SHA-256 `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- Owner reported three remapper invalid-access warnings for `ClientPacketListener` / `MapRenderer$MapInstance` and an access repair for one class; Vineflower reported duplicate `ModelBakery` lambda processing. No warning was identified for the movement slices used below; inspect any newly discovered relevant damaged body before relying on it.
- Owner source generation/provenance checkpoint: `29f6a2d`; raw log was not retained.

### B — Minecraft 1.15.2

- Source root: `decompiled_minecraft/1.15.2/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.15.2` / `1.15.2`.
- Original client jar SHA-256: `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`; size 15,531,492 bytes. Client artifact object `e3f78cd16f9eb9a52307ed96ebec64241cc5b32d`.
- Version metadata SHA-256: `E974511243E845B2427AB635EBA5612481CB420A70C0630AA041EAB3EEB590C5`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `1fbf9f0bc9c326af859b3ccf71c2a8f5edc47ef8` (`client.txt`); mapping file SHA-256 `65AD295B6CF63821F5D8F961C128128475E6D39386358D22EB238A3CE6E31777`.
- Remapped client jar SHA-256: `9B78CB6363696CA878D18B3A51A2E5D4AC626C9E06A4FA91B0B827FA85C2961A`.
- Source preparation extract: [Source preparation record](#source-preparation-record); decompiler warnings and exact successful completion are recorded there. The task reported no decompilation error.

### Per-finding source/resource hashes

Record each cited source path and SHA-256 in its finding. Paired tag resources and their hashes are in [Paired block and fluid tags](#paired-block-and-fluid-tags). Do not commit generated source trees, client jars or mapping files.

## Correspondence and call order

The two sources use a shared Mojmap namespace. Resolve each compared role through exact class/member signatures, inheritance, callers and state order in the coverage ledger and individual findings. Same-name classes or methods alone do not establish correspondence. Source-level candidate paths are indexed in [Newer-side source inventory](#newer-side-source-inventory).

## Coverage ledger

See [Detailed coverage ledger](#detailed-coverage-ledger) for the stage-by-stage audit, terminal rows, and open coverage gaps.

## Finding index

Findings are tracked one per file under `findings/`; add only after the bounded slice and dependency closure are source-confirmed.

## Resume checkpoint

- Completed: exact source provenance for both releases; input correspondence; paired block/fluid tags; friction, Soul Sand, Honey speed/jump/slide, portal dismount and Bee player-knockback findings.
- Next: close the open rows in [Detailed coverage ledger](#detailed-coverage-ledger), continuing player-state gates and living travel, then collision, blocks/fluids, remaining effects/equipment and external updates. The bounded movement-attribute and Depth Strider correspondence is recorded in [Stage 6 modifier correspondence](#stage-6-modifier-correspondence).
- Assumptions: no gameplay trajectory was observed; findings are source-level. Server-synchronized state is identified as externally authoritative rather than locally computed.

## Source audit closure

- Coverage status: partial; seven source-confirmed findings, a terminal input-refactor row, and bounded no-difference attribute/effect/Depth Strider correspondences are recorded. Other navigation stages have explicit open coverage gaps in [Detailed coverage ledger](#detailed-coverage-ledger).
- Unresolved gaps: remaining paired player-state, living-travel, collision-order, block/fluid consumer, effect/equipment and external-update audits.
- Evidence/hash/correspondence audit: exact original client jars and release-specific Mojmap mappings are recorded. Each finding records cited source hashes; paired tags and resource hashes are in [Paired block and fluid tags](#paired-block-and-fluid-tags).
- Runtime validation: not performed; gameplay trajectory validation is a separate workflow.


## Supporting audit evidence

### Detailed coverage ledger

##### Coverage ledger

The stage numbers follow the established source-navigation order. Rows marked terminal have paired endpoint evidence or a bounded source-level disposition; queued rows remain open and prevent an overall-complete status.

| Stage | Slice | A/B correspondence and source boundary | Status |
|---|---|---|---|
| 1. Input and tick order | Keyboard input sampling, sneak/crawl scaling, LocalPlayer call order | A `LocalPlayer.aiStep -> Input.tick(boolean, boolean) -> KeyboardInput.tick`; B `LocalPlayer.aiStep -> Input.tick(boolean) -> KeyboardInput.tick`. Predicates and tick ordering were compared in [Stage 1 input correspondence](#stage-1-input-correspondence). | Terminal: no movement difference established for corresponding states. |
| 2. Player state and gates | Pose, dimensions, abilities, food, sprint and player attributes | Player pose/crouch and flying gates were consulted for stage 1. Player movement-speed base value and sprint modifier are paired in [Stage 6 modifier correspondence](#stage-6-modifier-correspondence). [Stage 2 sprint gate correspondence](#stage-2-sprint-gate-correspondence) pairs the local sprint start/stop predicates, impulse threshold, double-tap timer and food-level read. Full comparison of dimensions, abilities, state transitions and other player-state writers has not been completed. | in-progress: selected movement-attribute and sprint-gate paths compared; remaining player-state writers and gates pending. |
| 3. Living movement integration | Jump, travel, ground friction, fluid travel, ladder, flight/fall-flying and movement attributes | F01 establishes grounded partial-snow friction lookup difference. Honey jump factor is separately F04. The paired LivingEntity.travel excerpt confirms matching branch order and formulas for fall-flying, ordinary air/ground travel, water/lava, gravity, ladder boost, Slow Falling, Levitation, Depth Strider and Dolphin's Grace; A uses raw coordinates where B uses accessors in these corresponding expressions. Stage 6 additionally closes the Depth Strider lookup/registration and unchanged speed-effect modifier inputs. F08 records B's hidden weaker Speed/Slowness effect restoration and player attribute update. F10 establishes that B can enter the unchanged fall-flying travel branch while still rising. Jump Boost additive math is the same; the base jump hook changes through the block factor in F04. Water/lava flow and other entity/world consumers are not audited here. | in-progress: F01/F04/F08/F10 findings; selected common formulas compared; other dependencies pending. |
| 4. Entity movement and collision | move order, collision resolution, step/edge behavior, block-cell callbacks, impulses and packet corrections | F02 establishes Soul Sand callback multiplicity. F05 establishes Honey contact slide. F06 identifies server position placement; exact prediction path remains separate. F09 establishes a changed Riptide activation gate before a shared player impulse. General collision/step/edge and impulse ordering were not exhaustively paired. | in-progress: findings F02/F05/F06/F09; general collision and correction slices pending. |
| 5. Blocks and fluids | Registrations, collision shapes, friction/speed/jump factors, callbacks, fluid tags and flow | F01/F02 cover snow, ice and Soul Sand; F03–F05 cover B-only Honey behavior. Paired tag inventory reports unchanged ice/water/lava tags and non-movement bamboo representation delta. Other movement-relevant blocks and fluid formula consumers are not exhaustively audited. | in-progress: findings F01–F05 and paired tag review; broader consumer inventory pending. |
| 6. Effects, enchantments, attributes and equipment | speed/slowness/jump/levitation/slow-fall/dolphin effects; depth strider/frost walker/riptide; item-use and armor paths | [Stage 6 modifier correspondence](#stage-6-modifier-correspondence) pairs the player movement-speed base value, sprint modifier, Speed/Slowness registrations and Depth Strider registration/lookup; their compared formulas and constants match. F08 traces the changed Speed/Slowness hidden-effect lifecycle into the player movement-speed attribute. F09 traces the Riptide item's activation gate into a player impulse. Frost Walker's registration, enchantment implementation and `LivingEntity.onChangedBlock` hook are paired without a body change. Fall-flying firework attachment is paired in [Stage 7 external movement correspondence](#stage-7-external-movement-correspondence). The paired LivingEntity travel/jump excerpt resolves unchanged formulas for Jump Boost addition, Slow Falling, Levitation, Depth Strider and Dolphin's Grace. Honey jump-factor integration is F04. Other movement-linked item-use/equipment consumers remain under audit. | in-progress: F08/F09 and selected attribute/effect/enchantment paths compared; remaining effect/enchantment/equipment consumers pending. |
| 7. External influences | Server position/velocity, mounts, portals, other entities and world callbacks | F06 is server-side portal dismount placement with entity-tracking/correction boundary; F07 is B-only Bee sting knockback through common `LivingEntity.hurt`. F10 pairs local Elytra prediction with server acceptance. Fall-flying firework attachment and non-mounted Ender Pearl/Chorus Fruit teleports are paired in stage 7 audit evidence; mounted-entity teleport propagation and End Gateway root-vehicle selection remain open. Other entity motion is out of scope. | in-progress: findings F06/F07/F10; remaining external writers and local correction route pending. |

###### Source-confirmed findings

- F01 — ground friction support-cell sampling changes under tall partial surfaces.
- F02 — Soul Sand horizontal multiplier applies once per overlapping cell in A and once by selected block factor in B.
- F03 — Honey Block horizontal speed factor (B-only block).
- F04 — Honey Block jump factor (B-only block).
- F05 — Honey Block descent sliding contact callback (B-only block).
- F06 — portal-aware server-side dismount placement.
- F07 — Bee sting can add knockback to player velocity (B-only entity interaction).
- F08 — a hidden weaker Speed/Slowness effect resumes in B and updates movement speed after a stronger effect expires.
- F09 — B rejects Riptide Trident use when one durability remains, preventing the shared release impulse.
- F10 — B permits Elytra flight to start while the player is rising, before the fall-flying travel branch already shared by both versions.

###### Explicit resource dispositions

- The one changed common tag, `bamboo_plantable_on`, expands the same five substrate values inline; only bamboo support/growth consumers were found. No player movement path is established.
- B-only bee hive/growable, crop, flower, tall-flower, shulker-box tags are not movement mechanics by themselves. Bee attack's player velocity effect is covered by F07; independent Bee movement remains out of scope.
- B-only `portals` tag participates in F06. Identical water/lava and ice tag bytes do not by themselves establish identical Java flow/collision formulas.

##### Dependency queue and blockers

- `PLAYER-STATE`: complete paired audit of dimensions, hunger/sprint, pose and ability gates.
- `LIVING-TRAVEL`: pair jump timing, gravity, water/lava travel and flow, ladders, flight/fall-flying and attribute consumers.
- `COLLISION-ORDER`: pair collision clipping, step height, edge probing, callbacks and velocity mutation ordering beyond F02/F05.
- `BLOCK-FLUID-CONSUMERS`: exhaustively enumerate relevant movement registrations and paired fluid/block consumers.
- `EFFECT-EQUIPMENT`: resolve each effect, enchantment and equipment consumer enumerated in source inventory.
- `EXTERNAL-INPUTS`: distinguish inbound player corrections from tracked-entity position/velocity replication for all reachable position/velocity writers.

These are audit coverage gaps, not source-generation blockers. The catalog remains in progress until those rows are resolved or each candidate is closed with paired evidence.

### Stage 6 modifier correspondence

This bounded comparison follows movement-speed attribute inputs and the Depth Strider producer/consumer chain. The exact source files are in the paired 1.14.4 and 1.15.2 Mojmap trees named in the artifact manifest.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `registerAttributes` sets movement-speed base value to `0.1F` in both. `aiStep` takes ability walking speed on the server, updates flying speed by the same sprint constant, and copies the attribute value into the living movement-speed field; the compared statements match. Player `getSpeed` reads the same movement-speed attribute. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | Sprint modifier UUID, description, `0.3F` amount and `MULTIPLY_TOTAL` operation match. `setSprinting` removes an existing modifier with that UUID before conditionally adding the same modifier. `LivingEntity.getSpeed` still returns the stored speed field; no delta is established in this slice. |
| `net/minecraft/world/effect/MobEffects.java` | `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412` | `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412` | Speed applies the same movement-speed `MULTIPLY_TOTAL` modifier (`0.2F`); Slowness applies the same modifier (`-0.15F`). Jump Boost and Dolphin's Grace registrations are byte-identical; their movement consumers are in the paired travel/jump comparison. |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `EDD49C2FBC3B557441FAE63D5FBCAAE0374DE85562376EC7859AA877AA83A5F4` | `AC48EA19FD7B49226D4887086B1C907297E79454243C37559AE49DCAA504FCDD` | `getDepthStrider` delegates to the same `getEnchantmentLevel(Enchantments.DEPTH_STRIDER, livingEntity)`. The compared method bodies match. |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` | `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` | Depth Strider remains registered as `WaterWalkerEnchantment(RARE, ARMOR_SLOTS)`; Frost Walker and Riptide registrations are also identical. |
| `net/minecraft/world/item/enchantment/WaterWalkerEnchantment.java` | `A6DD0D9C1A20A308F288984F23E38E520CDD13E94BAA3BD6125C3C42B887432D` | `A6DD0D9C1A20A308F288984F23E38E520CDD13E94BAA3BD6125C3C42B887432D` | Identical armor-feet category, level bounds and Frost Walker incompatibility for Depth Strider. |
| `net/minecraft/world/item/enchantment/FrostWalkerEnchantment.java` | `FE60B0B7FF9689B721DC8E8904ACAFD3544C217EC72BFBEBC7C11C3788B1208A` | `FE60B0B7FF9689B721DC8E8904ACAFD3544C217EC72BFBEBC7C11C3788B1208A` | Identical frosted-ice placement bounds, eligibility and scheduled melt. `LivingEntity.onChangedBlock` invokes the same lookup and callback body in both sources. |
| `net/minecraft/world/item/enchantment/TridentRiptideEnchantment.java` | `F8CEBAC038B65AD4DF4C2C11AF400B343FA0A6780BF7CDDCDC5C672E90B66AE6` | `F8CEBAC038B65AD4DF4C2C11AF400B343FA0A6780BF7CDDCDC5C672E90B66AE6` | Identical equipment slot, level bounds and compatibility; the changed item-use durability check is separately recorded in F09. |

Disposition: no movement delta is established for the bounded player base-speed, sprint-modifier, Speed/Slowness registration, or Depth Strider lookup paths. F08 separately records the changed effect-instance lifecycle, and F09 records the Riptide item-use gate. Potion/equipment lifecycle writers beyond these paths, remaining movement-linked item-use consumers, and externally synchronized attributes remain queued under `EFFECT-EQUIPMENT` and `EXTERNAL-INPUTS`.

### Stage 7 external movement correspondence

The attached firework path is a player-only external velocity producer. Both `FireworkRocketItem.use` methods spawn an attached rocket for an already fall-flying player on the server and shrink the item outside creative mode. The corresponding `FireworkRocketEntity.tick` branch checks `attachedToEntity.isFallFlying()`, reads the player's look vector and current velocity, then writes the same three component expressions to player velocity: current component plus `look * 0.1 + (look * 1.5 - current) * 0.5`. B uses `getX/getY/getZ` to reposition the rocket where A reads raw fields; the attached-player velocity arithmetic and following copy of player velocity into the rocket match. The neighboring unattached rocket motion and explosion/damage behavior are not player movement paths in this slice.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/item/FireworkRocketItem.java` | `8C263D79FA09F92F9495013F264E5382D844A44C22A60E111430B6A87E3D57DA` | `85680DCE68EA4013CE7827D6AD538D4D230C139C4938C09DB22BDDAEBAE17F53` | `use` has the same fall-flying gate and attached-rocket constructor path. The changed `useOn` places an unattached rocket offset from the clicked face in B; it does not affect the attached player path. |
| `net/minecraft/world/entity/projectile/FireworkRocketEntity.java` | `EBBB6705D9535FFA54E362C3FF352BF938C24C0FA665EC70F1B5A2DD29078B02` | `9EDC7C59FB28BDFADCB376447D7670B90BA5607596923DF2D7CC762D385578FC` | Attached-entity player impulse formula is unchanged; B replaces raw player position field reads with getters when positioning the rocket. |

Disposition: no player movement delta is established for the fall-flying firework attachment path. This does not close all external writers: packet corrections, knockback sources, portal dismount and other player-only velocity/position callbacks remain separately covered or open in the stage 7 queue.

#### Stage 7 server movement acceptance and correction

The paired `ServerGamePacketListenerImpl.handleMovePlayer` paths retain the same packet-coordinate defaults, packet-rate and moved-too-quickly checks, player collision checks, movement call, attempted-position reconciliation, corrective teleport decision, floating predicate, chunk update, and last-good-position writes. The `n` residual test remains `n > -0.5 || n < 0.5` in both versions; its `||` operation is preserved. B adds `fallDistance = 0.0F` for a positive requested vertical delta before the jump-from-ground check; this changes fall state, not a position or velocity write in this handler, and is outside the movement-response slice. `ServerGamePacketListenerImpl.teleport` also sends the same absolute/relative player coordinate construction and calls the same `absMoveTo` in both versions; B uses position getters for the relative offsets.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912F528C03DA34FA703646B6383C2B49AC3493EFD7BA78CB8FF27AA7F693B67D` | `3D46E455509891A3E0EF50472610D565255F5991987E971BA00AF2140D25982C` | `handleMovePlayer` pairs ordinary player movement acceptance and server correction; no position/velocity delta is established. `teleport` retains the same packet/position route. |

Disposition: no changed ordinary inbound player movement or server correction behavior was established in these methods. Fall-distance lifecycle, vehicle packet handling, other server-authoritative writers, and client handling of their corrections remain in the external-input inventory.

#### Stage 7 player teleport-item correspondence

The non-mounted Ender Pearl impact path and Chorus Fruit candidate search were paired through their player-position calls. The item launch speeds, target sampling formulas, 16 Chorus Fruit attempts, downward support search, collision/liquid checks, and fall-distance reset are unchanged; B uses position getters where A reads the same entity fields. Chorus Fruit's passenger dismount check is also present in both. Ender Pearl consumes the stack before spawning in A and after spawn in B, but both `use` methods create the projectile with `shootFromRotation(..., 1.5F, 1.0F)`.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/item/EnderpearlItem.java` | `046E99836398D47D5CD2963D9778477934BF0B99D4F963F03C79074CA48793FA` | `6D8F6D100DD78F4B316702A86BAF793F40237774F628ABB175B4A88712725F0C` | Same projectile spawn, shooter and launch-speed path. Stack shrink order moved after entity spawn in B; no player movement difference is established by that ordering. |
| `net/minecraft/world/entity/projectile/ThrownEnderpearl.java` | `0F31E38341EB5DC4923A29598A88ED3F2C8A27E7BD73A6D012DEB05326E4D7A6` | `6BE17996602ADE94B52A77085C41D2C69EC869F47197B948FEA41BEF81B04DC6` | Ordinary player impact still teleports to the pearl position and clears fall distance; B uses coordinate getters. End Gateway contact remains a delegated separate path. |
| `net/minecraft/world/item/ChorusFruitItem.java` | `F4F149D0D56F0A461817FF4FF83ED0DB9283D9284453DDE290038F1BC0C89334` | `D6E1F698FA831FE5FCC3E9D21F48F8F253CCB4B819DC5E1976E18B2CEABBD6B7` | Same 16 randomized target attempts and `randomTeleport` call; differences in position access are getters replacing raw fields. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `randomTeleport` retains the same downward support search, collision/liquid acceptance, fallback restore, and success event. B keeps candidate coordinates local until it calls `teleportTo`; A temporarily writes the candidate into entity coordinates and restores on failure. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `teleportTo` is not identical: B updates chunk positions and teleport flags for the root plus passengers, then repositions direct passengers; A updates only the target entity. This is a mounted-entity passenger propagation slice and remains open for player reachability. |

End Gateway adds another queued mounted-player branch: A's gateway tick passes the first entity from its portal list to `teleportEntity`; B passes that entity's root vehicle. A/B `TheEndGatewayBlockEntity.java` hashes are `EC4279C2B216F60F36ADF382F404689C23F836C6FB9CE1919A34A7D3B832A3AF` and `4BE661E53F4EE7E06C2754CEF1D4F5837766F776B0AC51AF4ADB3AC855668D10`. Resolve the passenger ordering and teleport-root consumer before closing mounted-player portal movement. Independent vehicle movement remains out of scope.

Disposition: no difference is established for the ordinary non-mounted Ender Pearl/Chorus Fruit player teleport inputs and candidates. The changed passenger propagation and End Gateway root-vehicle path remain in the stage 7 dependency queue.

### Newer-side resource inventory

#### Newer-side resource inventory (Minecraft 1.15.2)

Original input jar: `build/minecraft-decompile-cache/1.15.2/client.jar`; SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`.

The following exact jar entries were inspected directly from the original client jar. Hashes are SHA-256 of the uncompressed entry bytes.

| Jar entry | SHA-256 | Content relevant to navigation |
|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `ice`, `packed_ice`, `blue_ice`, `frosted_ice` |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `water`, `flowing_water` |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `lava`, `flowing_lava` |
| `data/minecraft/tags/blocks/bee_growables.json` | `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80` | `crops`, `sweet_berry_bush` |
| `data/minecraft/tags/blocks/beehives.json` | `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415` | `bee_nest`, `beehive` |

These tag values are not themselves a movement difference. They are recorded for later consumer tracing. The B jar contains no `data/minecraft/enchantments/` entries; this is not absence evidence because enchantments in this version are code-registered. A and B tag contents and referenced values are paired in [Paired block and fluid tags](#paired-block-and-fluid-tags) using the verified jar identities in `run.md`.

### Paired block and fluid tags

#### Paired block/fluid tag inventory

Input jars: A 1.14.4 SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; B 1.15.2 SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`. The inventories were read directly from both original client jars after independently verifying A's hash against its owner's provenance.

##### Inventory result

- A contains 52 `data/minecraft/tags/blocks/*.json` and `.../tags/fluids/*.json` entries; B contains 58.
- 51 entry paths are common. Fifty have identical uncompressed bytes. The only common entry whose bytes changed is `data/minecraft/tags/blocks/bamboo_plantable_on.json`.
- A-only entry: `data/minecraft/tags/blocks/dirt_like.json` (SHA-256 `CBEDCEDB203119E9312A8FBA25E282DDA4ED7C1E73121AE5F8746232B8B73DDE`). Its values are dirt, grass block, podzol, coarse dirt and mycelium.
- B-only entries: `bee_growables.json`, `beehives.json`, `crops.json`, `flowers.json`, `portals.json`, `shulker_boxes.json`, and `tall_flowers.json`, all under `data/minecraft/tags/blocks/`.

##### Relevant unchanged values

| Entry | A SHA-256 | B SHA-256 | Result |
|---|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | Same four ice blocks |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | Same still/flowing water values |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | Same still/flowing lava values |

##### Changed and new entries

- `bamboo_plantable_on.json`: A SHA-256 `C906DD6EAF99018B42E85D4DBD99D2125E56B9A139E7E6BD1980C8FE0B697401`; B SHA-256 `9D806316F075528EC519BC9DAAEA7B886DA346162854C17A611F83783C3F51C4`. A expresses dirt-like substrates through `#minecraft:dirt_like`; B expands the same five values inline. The only consumers found in the versioned Java trees are `BambooBlock` and `BambooSaplingBlock` support/growth checks. This changes tag representation, not the accepted substrate set. No difference in player movement is established by this tag delta.
- `data/minecraft/tags/blocks/portals.json`: B SHA-256 `FECDF2F3A61C8521647BB59802F4ECC18569D4B86786700499AC11E5718F80A9`; values are Nether portal, End portal and End gateway. A has no such resource. B consumes `BlockTags.PORTALS` in `LivingEntity.findStandUpPosition` during server-side dismount handling; see finding `F06`.
- `bee_growables.json`: B SHA-256 `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80`; values are crops and sweet berry bush.
- `beehives.json`: B SHA-256 `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415`; values are bee nest and beehive.
- Bee-related tags are consumed by the new Bee entity's target/hive behavior. Bee is inspected only for its attack effect on player velocity; its independent motion is outside scope. See finding `F07`.
- Remaining B-only crop, flower and shulker-box tag values are not movement mechanics on their own. Their consumers must be considered only if they form a dependency of player movement or an external player velocity/position update.

##### Scope note

These resources are datapack inputs. Identical values or missing files alone do not establish a Java behavior conclusion. Findings cite their consumer, registration and the exact jar entries where those data values matter.

### Newer-side source inventory

#### Newer-side source navigation inventory (Minecraft 1.15.2)

Source root: `decompiled_minecraft/1.15.2/mojmap`. This is a navigation inventory, not the completed pair correspondence. All entries below are for the generated exact 1.15.2 output. Relative file paths are from that root. SHA-256 values were computed from the source files on 2026-09-26.

| Stage | Source paths / members to resolve | B source SHA-256 |
|---|---|---|
| 1. Local input and tick ordering | `net/minecraft/client/player/LocalPlayer.java`: `tick`, `aiStep`, `isMovingSlowly`, input dispatch; `KeyboardInput.java`: `tick(boolean)`; `Input.java`: input state and tick signature | `LocalPlayer.java` `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`; `KeyboardInput.java` `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7`; `Input.java` `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` |
| 2. Player state and gates | `net/minecraft/world/entity/player/Player.java`: `tick`, `updatePlayerPose`, dimensions, attributes; `Abilities.java`; `world/food/FoodData.java`; `LocalPlayer.java`: `isCrouching`, `isMovingSlowly`, `serverAiStep` | `Player.java` `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`; `Abilities.java` `FBEE269C9150B34FA9CCA5369307F47CE46A96E609E192C3D4092BDE318853D4`; `FoodData.java` `22595659140B58A2C8B97E68B34A09E9390353A1BAF3925B4276639A6EA05256` |
| 3. Living movement integration | `net/minecraft/world/entity/LivingEntity.java`: `jumpFromGround`, `travel`, `aiStep`, `getSpeed`; fluid travel and attribute consumers/callees | `LivingEntity.java` `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`; `world/entity/monster/SharedMonsterAttributes.java` (hash to add when cited) |
| 4. Entity movement and collision | `net/minecraft/world/entity/Entity.java`: `tick`, `move`, `getBlockSpeedFactor`, `moveRelative`; `world/phys/AABB.java`; `world/phys/shapes/VoxelShape.java`, `Shapes.java`; client packet listener | `Entity.java` `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`; `AABB.java` `82324C0E6A3D69E80424656E6098B39CE41F0F17A5E6EFBE6542FE8EACA24A18`; `VoxelShape.java` `5FACEEA0E4CE9AD0F2A1A2C72A89D9A768AB877C8728AE2968FE34AA1E03D3EA`; `Shapes.java` `81BF70D2C8A1D1336DF72BEEDB30B0C9504F14DECC9A06814A794AABF38203C2`; `ClientPacketListener.java` `D806D286AF4B93D7C61C3C884F344A5870CE1871FB0A811506031ADF78FAFC30` |
| 5. Blocks and fluids | `net/minecraft/world/level/block/Blocks.java`; `HoneyBlock.java`; block subclasses and registrations; `world/level/material/FluidState.java`, `FlowingFluid.java` | `Blocks.java` `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`; `HoneyBlock.java` `40760AEB3C084F1143E87E1E057F18165492EB01B8FCE0815DFDD0882CDC8A03`; `FluidState.java` `F88BFF7D54EA0220EE67E0FE36A78DF67871E52AE636E584EA26DD107F652C15`; `FlowingFluid.java` `E8A395552E53F33BCB5648325615D31BC1796CE2A8AEB501BDDA3127622DCCDF` |
| 6. Effects, enchantments, attributes, equipment | `net/minecraft/world/effect/MobEffects.java`, `MobEffect.java`, `MobEffectInstance.java`; `world/item/enchantment/Enchantments.java`, `EnchantmentHelper.java`; `world/entity/monster/SharedMonsterAttributes.java`; effect application and equipment paths | `MobEffects.java` `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412`; `MobEffect.java` `F218A040FF9F77272395245C4FFECC3F2266335600B79BE0A5B2823C46AD7DD0`; `MobEffectInstance.java` `4999D12CD468E402506F067DBD8694B1C8125DD9131E4F8A2FCC1848F616F180`; `EnchantmentHelper.java` `AC48EA19FD7B49226D4887086B1C907297E79454243C37559AE49DCAA504FCDD`; `Enchantments.java` `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` |
| 7. External influences | `net/minecraft/client/multiplayer/ClientPacketListener.java`, `world/entity/Entity.java`, and movement callbacks once reachable dependencies are indexed | `ClientPacketListener.java` and `Entity.java` hashes recorded above |

##### Candidate leads to resolve after pair provenance

- Input refactor: 1.14.4's observed navigation tree has `KeyboardInput.tick(boolean, boolean)` and LocalPlayer calls it with `isVisuallySneaking() || isVisuallyCrawling()` plus `isSpectator()`. B has `KeyboardInput.tick(boolean)` and calls it with `isMovingSlowly()`. B's `isMovingSlowly()` delegates to `isCrouching() || isVisuallyCrawling()`. Determine exact correspondence, state timing, spectator behavior, and crouch pose gates. This is a lead only until A provenance is established.
- Honey block: B registers `Blocks.HONEY_BLOCK` with `speedFactor(0.4F)` and `jumpFactor(0.5F)`. `HoneyBlock.fallOn` passes `0.2F` to `causeFallDamage`; `entityInside` conditionally applies sliding. `isSlidingDown` requires airborne state, Y/velocity thresholds and horizontal offset; `doSlideMovement` can scale X/Z and writes Y `-0.05`, then clears `fallDistance`. Check the exact class/member/lines, registration, player reachability and A-side absence before creating a modern-only finding. Do not imply this block's behavior belongs in 1.14.4 emulation.

##### Resource inventory boundary

The B original client jar is available at `build/minecraft-decompile-cache/1.15.2/client.jar`. `jar tf` confirms it includes data-driven block/fluid tags and many other `data/minecraft` JSON resources; it contains no movement-resource conclusion by itself. Relevant entries, content hashes and referenced tag closure remain to be inventoried with the corresponding A jar using the verified 1.14.4 owner provenance recorded in `run.md` and the paired inventory in [Paired block and fluid tags](#paired-block-and-fluid-tags). Enchantment/effect registrations in this release are being traced through source code; absence of a dedicated JSON directory is not evidence of absence.

### Stage 1 input correspondence

#### Stage 1 correspondence note: keyboard movement input

A `LocalPlayer.aiStep()` calls `Input.tick(bl4, isSpectator())`; B calls `Input.tick(isMovingSlowly())`. The call occurs before both versions copy movement impulses into the local player tick path. Exact source hashes: A `LocalPlayer.java` `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`, `KeyboardInput.java` `33DAA0833A95E09E728C1B8F509020DD13B70E38ABA922E2B1E10EC5C9B7739A`; B `LocalPlayer.java` `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`, `KeyboardInput.java` `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7`.

In A the slowdown predicate is `!isSpectator && (sneakKeyDown || (isVisuallySneaking || isVisuallyCrawling))`. In B it is `isMovingSlowly`, whose predicate is `isCrouching || isVisuallyCrawling`; `isCrouching` uses the same crouch pose-entry/shift-or-obstructed-standing conditions and excludes flying and swimming. A's `isVisuallySneaking` has matching flying/swimming and pose-entry conditions. Player pose update in each version selects standing when flying rather than crouching; spectator input therefore is not slowed by either route. The signature refactor has no movement delta established for corresponding ordinary, crawling, swimming, flying, or spectator states. This row is resolved as a no-difference correspondence, not a finding. The two sources are Mojmap-named but state equivalence was checked through predicates and call order.

### Stage 2 sprint gate correspondence

In A and B, `LocalPlayer.aiStep` samples the previous jumping and sneak/shift states, advances keyboard input, then uses the same sprint-start gates: grounded or underwater, not sneaking, sufficient forward impulse, not already sprinting, food level above `6.0F` or `mayfly`, not using an item, and no Blindness. The double-tap window remains 7 ticks; the sprint key route and sprint-stop conditions for insufficient forward input, horizontal collision, and water/swimming retain the same order. `hasEnoughImpulseToStartSprinting` is unchanged: underwater it asks `input.hasForwardImpulse()`, otherwise it compares `forwardImpulse >= 0.8`.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | Sprint predicates and helper match; A `sneakKeyDown` corresponds to B `shiftKeyDown`, consistent with the input key correspondence above. The food-level producer is excluded by campaign scope; only the identical `> 6.0F || mayfly` movement gate is recorded here. |

Disposition: no sprint threshold or gate-order difference is established in this slice. Pose/state writers, flight/ability transitions and other player-state dependencies remain open under stage 2.

### Source preparation record

#### Source preparation transcript extract (transcribed from task output; not raw Gradle log)

- Command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`
- Repository revision: `c133c2999b6673874e35bbdb26759548407f3e11`
- Requested release resolved exactly: `Decompiling Minecraft 1.15.2 (requested '1.15.2')`
- JDK: `25.0.3+9-LTS` (forked from `C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot`)
- Mapping mode: `mojmap`; official Mojang mapping file downloaded and applied.
- Output: `decompiled_minecraft/1.15.2/mojmap`
- Decompiler: Vineflower; `Finished 1.15.2 using mojmap`
- Warnings: three invalid-access remapper warnings for `MapRenderer$MapInstance` and fixer action for 1 class; Vineflower reported `ModelBakery.lambda$loadModel$25` and `$26` processed twice. No decompilation error was reported in the task output.
- Result: `BUILD SUCCESSFUL in 3m 49s` (Gradle 9.7.1).
- Limitation: this extract is a transcription of task output, not a raw saved log.
