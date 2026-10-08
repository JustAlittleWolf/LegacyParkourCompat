# Discovery: 1.16.5 to 1.17.1

- Status: partial
- Scope: client player movement; A = `1.16.5`; B = `1.17.1`.
- Repository revision/start date: `c133c29`; 2026-09-26.
- Namespace: official Mojang names; explicit `mojmap` CLI mode on both sides. Each release uses its own official client mappings artifact and is independently remapped to `named`.
- B command: `gradlew.bat --no-daemon --rerun-tasks decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful log at ignored `build/movement-discovery/1.17.1-mojmap.log`.
- A: source owner and coordinator confirmed exact 1.16.5 Mojmap task success. A's raw log is not in the accessible worktree; artifact hashes are recorded below, and the raw log remains a provenance closure item.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS for Vineflower (B log); target bytecode Java 16 (B); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default 4G heap and repository options.

## Artifact manifest

Hashes are SHA-256 unless identified as publisher SHA-1. Source roots are under the shared ignored `decompiled_minecraft` directory. Cache paths below are local and ignored.

### A — 1.16.5

- Requested/resolved `1.16.5`; exact resolution and successful task completion reported by source owner.
- Source root: `decompiled_minecraft/1.16.5/mojmap/`.
- Owner cache client jar `build/minecraft-decompile-cache/1.16.5/client.jar`: SHA-256 `00B5EBBC33E95EA88C1AB80601599C9827E9AA861D93CCC2C7E3AAA281AB00C8`; Mojang SHA-1 `37fd3c903861eeff3bc24b71eed48f828b5269c8`.
- `version.json` SHA-256 `3EEEAB7B3165CC5263DC26FF8FE114FDB718B75A0244EFEAD9D19635D090BA72`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt`; cached `client_mappings.txt` SHA-256 `7931ED6D723ECEB1D621D05A76E10DDF643BF468C6ECF1C4ECF377BD72CF8B8C`; Mojang SHA-1 `374c6b789574afbdc901371207155661e0509e17`.
- Remapped `client-mojmap.jar` SHA-256 `18DA764651703F18941A800D4421C4FC28DB39C1586060C2C7E3AAA281AB00C8`.
- Raw successful decompiler log unavailable locally; do not claim a persisted log.

### B — 1.17.1

- Requested/resolved `1.17.1`; exact ID confirmed in successful log.
- Source root: `decompiled_minecraft/1.17.1/mojmap/` (worktree junction to shared ignored root).
- `client.jar` SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`; Mojang SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`.
- `version.json` SHA-256 `B6125F5A4410C3A71C1CF177DBDBF8C9E409BDE8E435FB094BEDD5AD6CE07902`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; `client_mappings.txt` SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`; Mojang SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`.
- Remapped `client-mojmap.jar` SHA-256 `E85FCB0A8656EACE49020D2B5824D33FAD25FB53E7CCABA0C3DE2DBC6CC0E923`.
- Log `build/movement-discovery/1.17.1-mojmap.log`; successful completion, plus two Vineflower duplicate-lambda processing notices in `ModelBakery`.

### Cited source hashes

Paths are relative to each version's Mojmap source root; hashes are SHA-256.

| Role | Path | A | B |
| --- | --- | --- | --- |
| Local player | `net/minecraft/client/player/LocalPlayer.java` | `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| Keyboard producer | `net/minecraft/client/player/KeyboardInput.java` | `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` |
| Input state | `net/minecraft/client/player/Input.java` | `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` | same |

Resource inventory is pending except for the cited B powder-snow entity tag. Neither client jar includes `data/minecraft/dimension_type/`; resolve version-matched server/datapack dimension-type data before closing the levitation slice.


### Additional sources cited by findings

| Role | Path relative to Mojmap root | A SHA-256 | B SHA-256 |
| --- | --- | --- | --- |
| Player jump wrapper / abilities field | `net/minecraft/world/entity/player/Player.java` | `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` |
| ItemStack item predicate | `net/minecraft/world/item/ItemStack.java` | `2BB228453C39F9792DC1496802E46220738DBF32855442696BAAA0288F791156` | `BA25D48580A3C586233956558905467DC183502EFC59334090B65D46F598DA59` |
| Entity horizontal squared distance | `net/minecraft/world/entity/Entity.java` | `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` |
| Vector distance | `net/minecraft/world/phys/Vec3.java` | `B889010C321C45B497E71FF782D108A718ED23A5F605D6162904045E1E01FD48` | `EB52A20224767DAEB5DDC500552C673DF2DAF9CD6868D7E74356411E94EF402E` |
| Math helper | `net/minecraft/util/Mth.java` | `0E4424FFA12923314C309AE13E012768271DFCACE4B5A84727F74E8170AFE290` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` |
| Level height lookup | `net/minecraft/world/level/LevelReader.java` | `D301B992D71B05306A3139E69F3F1529190F82404791EC60F9A693188D29577A` | `4D041CDBC702D532C5B9DA800C34BD1C23F8D3B554ECBDD89DECBB765A6B451A` |
| Dimension type | `net/minecraft/world/level/dimension/DimensionType.java` | `E2F87508C846871CE9A302509E5057E925AB242FA0A5C1BB953FB2B8BFB20DAC` | `2DC118F16B671D3B1B9736C10E960E199790F7930CFA18983F379BEB8D00F27B` |
| Block registry | `net/minecraft/world/level/block/Blocks.java` | `3B39D5CC4CD22F146ED3195AA30CBB9FDFCA49F63783FABF9924A6CE7795FA12` | `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8` |
| Block movement-factor accessors | `net/minecraft/world/level/block/Block.java` | `2838AA4FB4052B3BD78D77B3D232A6321D03BD2DFD52275FFACFE6915A89E5B6` | `01FA40798C7A4AF538C29601A6AD52C82F3C6364F74D00E955A0A41A0A0D88B4` |
| Block movement-factor defaults/builders | `net/minecraft/world/level/block/state/BlockBehaviour.java` | `C62E2F07094E5A7BCAD00CBAE7D24497BF636CC740232A1472F4128A472DEF8B` | `920896E6BC9D7F8794ABA3C5F325D9DFFD9C2C9422A0BE2E5DD0B7474E980515` |
| Bubble-column propagation | `net/minecraft/world/level/block/BubbleColumnBlock.java` | `49E6DDC09F2D99056B0940FF0178C02BD0FE62D7758936EEF751A3B9500F2BA5` | `70AF7C91F54EAB13B32B75BA87C72374E03B51572A175440E8CD166CEFF97394` |
| Magma block tick caller | `net/minecraft/world/level/block/MagmaBlock.java` | `1DCA0790018A0AC501ECA1F8424133B11ACAEB5B4C41D04460CA581083F64C27` | `D757241A0F4930EDCE205E3B97DF597E282450C0B52C77733C9A5D409E74D209` |
| Soul sand tick caller | `net/minecraft/world/level/block/SoulSandBlock.java` | `DA92572788C9416B511C003E50688D802E8248CCDB3E2D5EB155AA482549B095` | `1DC77DDF5F041B1C4049A4B75C445DD2969DACB2BC12027096583B6D5FB1F987` |
| Big Dripleaf contact/shape owner | `net/minecraft/world/level/block/BigDripleafBlock.java` | absent in A | `1E1315B0A53EB85A3365335131E7819C1B667A55E14343D839C5211C671FD59F` |
| Powder snow | `net/minecraft/world/level/block/PowderSnowBlock.java` | absent in A | `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3` |

| Coordinate constructor/floor | `net/minecraft/core/Vec3i.java` | `2C04952E6A5CECBC641E69EA6376AF91A13EBC4D8D6F427FC5392B50A5619FC2` | not cited in B |
| Section-coordinate conversion | `net/minecraft/core/SectionPos.java` | — | `D17E6D4FF5E7573DDE74FDDAEB2733A39ECAC9C9EF4621A10BF868CC0513F52A` |
| Collision-shape dispatch | `net/minecraft/world/level/CollisionGetter.java` | `B507D6BE11E5985A62CFEB249A99DCB5F8EDAF346F12CB2487797D9E01763EAC` | `EB707479E75CF200731DF4546A546FB984BE33CF3E4A8E17D3065A916497F747` |
| Block-state shape dispatch | `net/minecraft/world/level/block/state/BlockState.java` | `EDD2D9DBA0C10D035774F8B2DD7DCF00D112D7A2D67734FBA1851A2D2F91BD36` | `EDD2D9DBA0C10D035774F8B2DD7DCF00D112D7A2D67734FBA1851A2D2F91BD36` |
| Entity dimensions / bounding-box reconstruction | `net/minecraft/world/entity/EntityDimensions.java` | `6A4DED79D936BB740232AE923372E47D24A297A35EABA22E9D18FBD637A7EEDF` | `6A4DED79D936BB740232AE923372E47D24A297A35EABA22E9D18FBD637A7EEDF` |
| Turtle egg contact and shape callbacks | `net/minecraft/world/level/block/TurtleEggBlock.java` | `9345C6162F4DCf306E84B91EA5AB54FF18076006422285BE258FD88B18D1F901` | `B0677B17479CFE6FEF4E8440F0270AD6A7A14AEF468AA5DFc7708DAB819A0340` |
| Pressure-plate event callback | `net/minecraft/world/level/block/BasePressurePlateBlock.java` | `03E9FD59C67530216FC5D81E2DE4AFB84A0CF3AE6644B0E14DD0F8D4BD75898F` | `56C2979B3BA0A461552B5E72CD2A75A037211A6A78BFD8A1A7285B1A8FEAFC39` |
| Sculk sensor event frequencies | `net/minecraft/world/level/block/SculkSensorBlock.java` | absent in A | `AADF9067FE0C4E9B62EC4E175DD59A982C2D997ABC5550A1ED1E1333F1A3AAE6` |
| Sculk sensor listener | `net/minecraft/world/level/block/entity/SculkSensorBlockEntity.java` | absent in A | `28B1796368174C91FE3B6100F0AB3439D1B3600B5996CBDF6F6FEA6AF1468318` |
| Vibration event filter | `net/minecraft/world/level/gameevent/vibrations/VibrationListener.java` | absent in A | `5DBED89CED4EA02F7C084BE3D9B20C7325FB5D56D0F5CC119FE0C86749C1F8FA` |
| Vibration tag generator | `net/minecraft/data/tags/GameEventTagsProvider.java` | absent in A | `C0E81E2882EEB514784D1130CC8260F32052151F36D4B94FBEEF35BF4739A636` |
B client-jar resource cited by the powder-snow finding: `data/minecraft/tags/entity_types/powder_snow_walkable_mobs.json`, SHA-256 `A1D2F5C240C8D21446675AC242F898CC23EF3C343A2BC5FE125249192C9FBC3E`.

The ready-tree source manifests used for hash rechecks are `build/movement-campaign-2026-10-07/ready/1.16.5/mojmap.sources.sha256` (SHA-256 `9499F2611D0E6DDE37CB635F1A28A591416382D6BC188B37D5F99E2B2A20823B`) and `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap.sources.sha256` (SHA-256 `93270D229ACFB751BF56DAF1E7BE26CE3DCFF26B29E94AA157DE621405A3463B`). Recheck corrected the B `LevelReader.java` source hash to the manifest value above.
## Correspondence and call order

See [Navigation index](#navigation-index). Names and signatures are resolved independently in both exact Mojmap trees; no name-only inference is used for a behavioral conclusion.

## Coverage ledger

Each row below is one scoped slice. `pending` means no paired comparison has been completed; it is not evidence of equivalence.

- `S1-INPUT-KEYBOARD`, stage 1: `compared-no-difference`, scoped to `KeyboardInput.tick(boolean)` and `Input` state access. Both sides sample directional/jump/shift controls, derive impulses with identical equality/ternary expressions, then when the same boolean argument is true scale each impulse by `(float)(impulse * 0.3)`. B declares `MOVING_SLOW_FACTOR = 0.3`, but the method uses the same `0.3` double literal. The expression order and cast match; `Input.java` is byte-identical by SHA-256. The meaning of the boolean argument and other local-player input branches are separate slices.
- `S1-LOCAL-AISTEP`, stage 1: `compared-no-difference`, scoped to full `LocalPlayer.aiStep()` movement-gating method (A lines 627–793, B lines 649–823). Checked branches retain the same input/tick order, item-use slowdown, auto-jump, sprint timing/gates, water and vehicle handling, flight gates and jump dispatch. Source edits are direct `abilities` reads to B `getAbilities()` (returns same field) and A `getItem() == Items.ELYTRA` to B `ItemStack.is(Items.ELYTRA)` (returns `getItem() == item`).
- `S1-LOCAL-TICK-GATE`, stage 1: `compared-no-difference`. A constructs `BlockPos(x,0,z)` and `hasChunkAt(BlockPos)` uses floored X/Z shifted right by 4; B uses `getBlockX/Z` kept current by `setPosRaw` flooring and `SectionPos.blockToSectionCoord`, also right shift by 4. Y is ignored on both. B rotation getters return the fields A reads directly.
- `S1-ENTITY-TICK-ORDER`, stage 1: `pending`; compare `LocalPlayer.tick`, parent `Entity.tick`, `LivingEntity.tick` and server/client AI ordering beyond the bounded local-player guard/packet slice. Navigation: `LocalPlayer.java`, `Entity.java`, `LivingEntity.java` in both trees.
- `S1-LOCAL-SPRINT-JUMP-AUTOJUMP-GATES`, stage 1: `pending`; separately close sprint timers, jump/auto-jump and riding/flight branch preconditions and state writes in `LocalPlayer.aiStep()`.
- `S2-PLAYER-STATE-POSE`, stage 2: `pending`; compare player abilities/food/effects defaults, pose and dimensions, eye height, swimming and active-item writers plus reachable local-player gates.
- `S3-JUMP-BOOST`, stage 3: `findings`; source-confirmed precision difference on the player jump path. See [finding](findings/S3-jump-boost-precision.md).
- `S3-POWDER-SNOW-CLIMB`, stage 3: `findings`; modern-only climb assist; related block registration/resource slice `S5-POWDER-SNOW` is recorded below. See [finding](findings/S3-powder-snow-climb.md).
- `S3-02-POWDER-SNOW-STUCK-MOTION`, stage 3: `findings`; B's player-reachable powder-snow contact callback writes the existing stuck multiplier consumed by `Entity.move`; modern-only. See [finding](findings/S3-powder-snow-stuck-motion.md).
- `S3-LEVITATION-UNLOADED-CHUNK`, stage 3: `blocked` for environment-specific disposition pending exact active dimension data and its source/synchronization path. See [finding](findings/S3-levitation-dimension-min.md).
- `S3-GLIDE-DISTANCE-MATH`, stage 3: `compared-no-difference`, scoped to horizontal distance and animation speed helper expressions: A `Entity.getHorizontalDistanceSqr` returns `x*x + z*z` before `Math.sqrt`; B `Vec3.horizontalDistance` computes the same terms in the same order then calls `Math.sqrt`. A `Mth.sqrt(double)` returns `(float)Math.sqrt(d)`, matching B's explicit cast in `calculateEntityAnimation`.
- `S3-DISCARD-FRICTION-PLAYER`, stage 3: `not-applicable`; B field defaults false, and the only B vanilla writers are `LongJumpToRandomPos<E extends Mob>` and `LongJumpMidJump extends Behavior<Mob>`. B writer source SHA-256 values: `4EC55D5D7915C32BF1A649B119A0E325ACB2ABC1D1A356890BEE093C42287FD2` and `36F42F3F7B9A5ECC4F3145A07F0C14EB710AF6BCC28031CB99520313255CE473`; this mob-only state path is not reachable by the local player.
- `S3-TRAVEL-BRANCHES`, stage 3: `pending`; separately compare ground/air, water/lava, climb, fluid, friction and post-travel state paths beyond the bounded slices above.
- `S3-BUBBLE-COLUMN-PROPAGATION`, stage 3: `in-progress`; B replaces A's source-water `growColumn` calls with `updateColumn` reconstruction and upward propagation. Compare update scheduling and edge cases before disposition; shared bubble-column entity callbacks are unchanged in the reviewed body.
- `S4-ENTITY-COLLISION`, stage 4: `pending`; compare `Entity.move`, axis clipping, stepping, shape iteration, callbacks and position/box updates.
- `S4-03-POWDER-SNOW-SHAPE-CONSUMER`, stage 4: `findings`; paired generic collision dispatch reaches B's entity-context powder-snow shape override. See [finding](findings/S4-powder-snow-collision-shape.md); the broader collision slice remains open.
- `S4-01-PLAYER-POSITION-RECONSTRUCTION`, stage 4: `findings`; A reconstructs X/Z from the translated box midpoint while B stores the direct coordinate sum; see [finding](findings/S4-player-position-reconstruction-rounding.md). Broader S4-01 remains open.
- `S4-01-TURTLE-EGG-POST-LANDING-SHAPE`, stage 4: `findings`; a shared callback's stale state changes the state-dependent shape after a qualifying player landing; the lifecycle change itself is out of emulation scope. See [finding](findings/S5-turtle-egg-stale-callback-state.md).
- `S4-01-BIG-DRIPLEAF-TILT-SHAPE`, stages 4–5: `findings`; a new B-only callback state machine lowers and removes the Big Dripleaf collision shape; the block is absent in A and needs no historical emulation. See [finding](findings/S4-big-dripleaf-tilt-collision-shape.md).
- `S5-BLOCK-FLUID-PROPERTIES`, stage 5: `pending`; enumerate player-reachable block/fluid friction, speed/jump factors, flow and callbacks, registrations and tags beyond powder snow.
- `S5-01-REGISTERED-BLOCK-MOVEMENT-FACTORS`, stage 5: `no-difference (bounded)`; shared defaults and explicit `Blocks.java` friction/speed/jump-factor assignments match. Other block/fluid properties, registrations, flow and tags remain open.
- `S5-03-CONTACT-CALLBACK-INVENTORY`, stage 5: `in-progress`; exact-source declaration inventory reproduced (29 A files, 33 B files, 28 shared). Compared shared Slime, Farm, pressure-plate, Turtle Egg, fall-damage, redstone-ore, stair, hopper and Wither Rose callbacks. B-only Big Dripleaf, cauldron, pointed-dripstone and Powder Snow owners have scoped dispositions/findings; remaining shared bodies are still open.
- `S5-05-RESOURCE-AND-REGISTRATION-CLOSURE`, stage 5: `pending`; complete all block/fluid registration, tag and resource dependencies.
- `S5-POWDER-SNOW`, stage 5: `findings`; registration, block class, contact behavior and tag data are source-confirmed modern-only; see [findings](findings/S3-powder-snow-climb.md), [findings](findings/S3-powder-snow-stuck-motion.md) and [findings](findings/S4-powder-snow-collision-shape.md). It is excluded from historical behavior for A-era maps.
- `S5-03-PRESSURE-PLATE-VIBRATION`, stage 5: `findings`; B adds pressure-plate events that its registered sculk-sensor listener can consume, with exact packaged vibration-tag data still open. See [finding](findings/S5-pressure-plate-vibration-event.md).
- `S6-EFFECT-ENCHANTMENT-EQUIPMENT`, stage 6: `pending` except the Jump Boost consumer path documented under `S3-JUMP-BOOST`; compare effect application, enchantment formulas/conditions, attribute aggregation and equipment writers/data.
- `S7-EXTERNAL-STATE`, stage 7: `pending`; compare packet-driven velocity/position corrections, pushes, explosions, pistons and riding transitions, distinguishing client-computed movement from server-supplied state.
- `S7-PRESSURE-PLATE-VIBRATION-DATA`, stage 7: `blocked`; the Java event producer and B sensor consumer are traced, but exact B packaged `data/minecraft/tags/game_events/vibrations.json` is unavailable in the canonical ready directory. Obtain the exact 1.17.1 client/server resource artifact before closing receiver membership.
- `S3-04-TRAVEL-BRANCH-DEPENDENCIES`, stage 3: `pending`; keep the remaining travel and post-travel paths open.
- `S4-01-ENTITY-COLLISION-BASELINE`, stage 4: `pending`; keep axis clipping, stepping and position/box update comparison open.
- `S1-REMAINING-INPUT-TICK-AND-GATES` and `S2-PLAYER-STATE-POSE`, stages 1–2: `pending`; retain the existing entity tick, sprint/jump gates, player state, pose and dimensions dependencies.
## Dependency queue and blockers

- `A-LOG`: obtain the 1.16.5 owner's raw successful log or preserve its transcript location; completion was confirmed, but no raw log is present in the accessible owner build directory.
- `RESOURCES`: inspect exact client-jar resources and relevant server-supplied inputs for stages 5–7.
- `DECOMPILER-WARNINGS`: determine whether B's two `ModelBakery` duplicate-lambda warnings intersect any movement dependency; otherwise disposition them as unrelated.
- `CD-S1-04-01`: external acceptance remains pending for snapshot `5cf13c2f538ee420ed8c13bdc2a65605c20d0d6f`, file SHA-256 `a9e70192a493008498b0fe658b2c89439c831429eff992a5bbc8a55d22e1107b`.
- `S5-03`: continue from the recorded callback owner inventory by comparing shared callback bodies and resolving the cauldron replacement and new B owners; do not treat filename overlap as behavioral equivalence.
- `S7-PRESSURE-PLATE-VIBRATION-DATA`: raw version-matched jar resource is missing; current source tree contains only `mojmap`, readiness/provenance and source manifests. Keep receiver membership blocked pending the exact resource and hash.

## Finding index

- [Jump Boost addition precision](findings/S3-jump-boost-precision.md) — source-confirmed; player behavior.
- [Powder snow climb assist](findings/S3-powder-snow-climb.md) — source-confirmed; modern-only mechanic.
- [Powder snow stuck movement multiplier](findings/S3-powder-snow-stuck-motion.md) — source-confirmed; modern-only contact behavior.
- [Powder snow collision shape](findings/S4-powder-snow-collision-shape.md) — source-confirmed; modern-only player collision shape.
- [Turtle egg landing callback state](findings/S5-turtle-egg-stale-callback-state.md) — source-confirmed lifecycle change with downstream collision-shape consequence; lifecycle is out of emulation scope.
- [Big Dripleaf tilt collision shape](findings/S4-big-dripleaf-tilt-collision-shape.md) — source-confirmed modern-only player contact and collision-shape change.
- [Pressure-plate vibration event](findings/S5-pressure-plate-vibration-event.md) — candidate modern-only event path; packaged vibration tag remains unverified.
- [Pressure-plate vibration event](findings/S5-pressure-plate-vibration-event.md) — candidate modern-only event path; packaged vibration tag remains unverified.
- [Player position reconstruction rounding](findings/S4-player-position-reconstruction-rounding.md) — source-confirmed player position arithmetic difference.
- [Levitation dimension minimum](findings/S3-levitation-dimension-min.md) — candidate; blocked pending external dimension data.

## Resume checkpoint

- Compared/resolved slices: prior slices plus the stuck-movement, Powder Snow shape, player position reconstruction, Turtle Egg callback-state, Big Dripleaf collision-shape and pressure-plate event findings. `S5-03` callback inventory remains in progress; the pressure-plate vibration receiver is blocked on packaged tag data. `S3-LEVITATION-UNLOADED-CHUNK` remains blocked for environment-specific disposition.
- Next: continue the remaining `S5-03` callback/provider comparisons, compare `S3-BUBBLE-COLUMN-PROPAGATION` tick/recovery edge cases, and continue the `S5-05` resource inventory; retain `S3-04`, broad `S4-01`, S7 external inputs and remaining `S1`/`S2` dependencies.
- Outstanding: A raw log, remaining callback bodies, bubble-column propagation/recovery comparison, exact B vibration-tag resource, broader block/provider inventory, dimension data, external `CD-S1-04-01` acceptance, and decompiler-warning disposition.
- Runtime validation: not performed.

## Source audit closure

Partial and in progress. Stage-1 and stage-2 dependencies remain open. Stage-3 records Jump Boost precision and two Powder Snow mechanics; stage-4 records position-rounding, Big Dripleaf and shape-consumer results while general collision remains open. Stage-5 has a reproduced callback inventory, Turtle Egg and modern-only contact findings, and a pressure-plate event candidate, but shared callback/provider and packaged-resource closure remain incomplete. The pressure-plate vibration receiver is blocked on exact B tag data. `CD-S1-04-01` acceptance remains pending. Outstanding dependencies include the A raw log, dimension-type data, remaining callback/provider comparisons, external inputs and B's packaged vibration resource. This catalog does not claim exhaustive equivalence.


## Supporting audit evidence

### Navigation index

#### S5-03 contact-callback owner inventory

Rechecked against the exact ready Mojmap trees using declarations of `entityInside`, `stepOn`, `fallOn` and `updateEntityAfterFallOn` under `net/minecraft/world/level/block`. The source-manifest files and their SHA-256 values are recorded in the artifact manifest. The inventory reproduces 29 A-side files and 33 B-side files, with 28 paths shared. It is a declaration inventory only; shared callback bodies have not all been compared.

Shared paths: `BaseFireBlock.java`, `BasePressurePlateBlock.java`, `BedBlock.java`, `Block.java`, `BubbleColumnBlock.java`, `ButtonBlock.java`, `CactusBlock.java`, `CampfireBlock.java`, `CropBlock.java`, `DetectorRailBlock.java`, `EndPortalBlock.java`, `entity/HopperBlockEntity.java`, `FarmBlock.java`, `HayBlock.java`, `HoneyBlock.java`, `HopperBlock.java`, `MagmaBlock.java`, `NetherPortalBlock.java`, `RedStoneOreBlock.java`, `SlimeBlock.java`, `StairBlock.java`, `state/BlockBehaviour.java`, `SweetBerryBushBlock.java`, `TripWireBlock.java`, `TurtleEggBlock.java`, `WaterlilyBlock.java`, `WebBlock.java` and `WitherRoseBlock.java`. `HopperBlockEntity.entityInside` is a static helper, not a `Block` override, but matched the same declaration filter and is retained in the inventory.

The only A-only path is `CauldronBlock.java`, `entityInside(...)` (SHA-256 `A96D4F3AA5AE2D46A01D8302D4763080C6102426933F13496A9571309B7A0131`). B-only callback owners are `BigDripleafBlock.entityInside(...)` (`BigDripleafBlock.java`, `1E1315B0A53EB85A3365335131E7819C1B667A55E14343D839C5211C671FD59F`), `LavaCauldronBlock.entityInside(...)` (`LavaCauldronBlock.java`, `E9BE04409F64BEEAEEEA0F83E5B0220EF980FB083C4621CAA9D4692DDFF3EFC8`), `LayeredCauldronBlock.entityInside(...)` (`LayeredCauldronBlock.java`, `00DCCE7A9B773ED366F32937BB6A455B6614115EBCF66E4A032BCCF69291B4AA`), `PointedDripstoneBlock.fallOn(...)` (`PointedDripstoneBlock.java`, `A1EAADF6B494B378F4E075DCDFCAD398B3AC5BE86D9541EE919963E786702DB3`) and `PowderSnowBlock.entityInside(...)` (hash in the cited-source table). The cauldron split is a path replacement, not an absence claim. The four new B owners need behavior-level disposition; Powder Snow is traced in the findings below.

#### S5-01 registered block movement factors (bounded no-difference)

Both `BlockBehaviour.Properties` versions initialize friction, speed factor and jump factor to `0.6F`, `1.0F` and `1.0F`, respectively. Both `Block` versions expose those same three stored fields directly. An exact source search of the two `Blocks.java` registries found the same assignments on both sides: Ice and Frosted Ice friction `0.98F`, Slime Block friction `0.8F`, Packed Ice friction `0.98F`, Blue Ice friction `0.989F`, Soul Sand speed factor `0.4F`, and Honey Block speed/jump factors `0.4F`/`0.5F`. No other explicit `.friction(...)`, `.speedFactor(...)` or `.jumpFactor(...)` calls occur in these registry sources. Hashes for `Block.java`, `BlockBehaviour.java` and `Blocks.java` are in the cited-source table. This closes only the registered base-factor value slice; it does not close the broader block/fluid provider, registry, tag or flow inventory.

Shared callback body samples: `SlimeBlock.stepOn(...)` keeps the same `abs(deltaY) < 0.1`, `0.4 + abs(deltaY)*0.2` horizontal multiplier; B only adds and forwards `BlockState`. `FarmBlock.fallOn(...)` keeps the same predicate and dirt conversion; B passes the state already captured by `Entity.move`. `BaseFireBlock`, `BubbleColumnBlock`, `CactusBlock`, `CampfireBlock`, `SweetBerryBushBlock`, `WaterlilyBlock` and `WebBlock` have matching reviewed callback bodies. `BedBlock` retains the same bounce math and suppression branch; B's `fallOn` signature adds state. `HoneyBlock` retains the same slide movement; its fall callback adds the explicit damage source. `MagmaBlock.stepOn(...)` retains the same damage predicate; its B signature adds state. `BasePressurePlateBlock` adds the game-event producer described above. `TurtleEggBlock` reuses the state captured before `fallOn` for the later `stepOn`, unlike A's state reread. The changed `fallOn` call signatures for Block, Bed, Hay and Honey provide the B `DamageSource.FALL` argument to damage handling; that damage system is excluded. `WitherRoseBlock` only changes equivalent type-check syntax for its existing Wither effect; the effect's health/damage behavior is excluded. `HopperBlock` forwards only ItemEntity overlaps to a hopper helper and is not a player movement path. Remaining shared method bodies and replacement/new B owners are still in progress.

Bubble-column dependency trace: A `MagmaBlock.tick(...)` and `SoulSandBlock.tick(...)` call `BubbleColumnBlock.growColumn(...)`; on placement, each new bubble column calls `growColumn(...)` for the cell above. B's Magma and Soul Sand ticks call `updateColumn(...)`, which derives the column state from the supporting block and loops upward through bubble columns/source water. The vertical player response callback is unchanged in the reviewed body, but propagation/reconstruction and tick recovery differ structurally; retain this as `S3-BUBBLE-COLUMN-PROPAGATION` pending edge-case comparison.

B-only callback-owner dispositions: `BigDripleafBlock.entityInside(...)` changes a tilt state whose collision shape lowers and becomes empty; see the modern-only finding. `LavaCauldronBlock.entityInside(...)` invokes lava damage for entities inside its contents. `LayeredCauldronBlock.entityInside(...)` extinguishes a burning entity in its contents and, when interaction is allowed, lowers the cauldron fill level. A's monolithic `CauldronBlock.entityInside(...)` also extinguishes and lowers its water level, but uses its own level/height condition; the subclass split is not by itself a player-movement finding. `PointedDripstoneBlock.fallOn(...)` adds stalagmite fall damage on upward-facing tips, a damage-only path. Damage, fire state and general cauldron lifecycle are outside this movement audit; remaining shared callback bodies and B-only owner resource/registration closure remain open.

#### S4-03 shape-consumer correspondence

On both sides, `Entity.collide(Vec3)` gets block collision shapes through `CollisionGetter.getBlockCollisions(Entity, AABB)`; that dispatch asks `BlockState.getCollisionShape(...)` with a collision context, then the entity collision path resolves the requested axes through `Shapes.collide`. Relevant correspondences are `Entity.java` (`collide`, A lines 668 onward; B lines 750 onward), `CollisionGetter.java` (A lines 58 onward; B lines 61 onward), and `BlockState.java`. Their hashes are recorded in the cited-source table. The generic consumer exists on both sides; B adds the Powder Snow shape provider. This closes only that consumer correspondence and the new block's path, not the full S4-01 axis-clipping audit.

#### Navigation index: 1.16.5 to 1.17.1

Paths are relative to each version's `mojmap` source root. These are navigation correspondences, not assumed behavioral equivalence. Confirm callers, inheritance, signatures and data per slice.

##### 1. Local input and tick ordering

| Role | A — 1.16.5 | B — 1.17.1 | Correspondence / state |
| --- | --- | --- | --- |
| Local player | `client/player/LocalPlayer.java`: `tick()` line 184; `aiStep()` line 627; extends `AbstractClientPlayer` | same class: `tick()` line 191; `aiStep()` line 649; same superclass | Same exact Mojmap names. `tick` handles client tick and movement packet reporting; `aiStep` samples and gates player input before superclass living movement. Inspect each branch separately. |
| Input state | `client/player/Input.java`: `tick(boolean)`, `getMoveVector()`, `hasForwardImpulse()` | same paths and methods | State: impulses, up/down/left/right, jumping, shift. Source hashes are identical. |
| Keyboard producer | `client/player/KeyboardInput.java`: `tick(boolean)` line 13 | same path; line 14 | Compared-no-difference: B declares unused `MOVING_SLOW_FACTOR = 0.3`; both methods still multiply by the same literal and cast. |
| Tick guard / rotation packet | `LocalPlayer.tick()` line 184; `LevelReader.hasChunkAt(BlockPos)` line 158 | `LocalPlayer.tick()` line 191; `LevelReader.hasChunkAt(int,int)` line 170; `Entity.getBlockX/Z()` lines 2904/2940; `Entity.setPosRaw()` line 2956 | Compared-no-difference for vanilla local-player state. A floors `(x,0,z)` and applies `>> 4` to X/Z; B maintains floored block position and applies `SectionPos.blockToSectionCoord` (`i >> 4`). Both ignore Y. B `getYRot/getXRot` return the same fields A reads directly. |

In the inspected beginning of `LocalPlayer.aiStep()`, both read prior `jumping` and shift state into locals and compute pre-sampling sprint impulse; select crouching; call `input.tick(isMovingSlowly())`; notify tutorial; apply `0.2F` movement input slowdown when using an item and not riding; process auto-jump; then continue into sprint/jump/fluid/riding work and `super.aiStep()`. That order matches in the inspected range. B changes direct `abilities` field reads to `getAbilities()`; B `Player.getAbilities()` returns the same field. B `ItemStack.is(Item)` returns `getItem() == item`, matching the prior Elytra equality predicate.

##### 2. Player-specific state and gates

| Role | A | B | Status |
| --- | --- | --- | --- |
| Player superclass/state | Resolve `Player`, `AbstractClientPlayer`, abilities, food, pose/dimensions and active-item writers from `LocalPlayer` inheritance | Same Mojmap package hierarchy | Pending. |
| Pose/dimensions/swim | Resolve player pose selection, dimensions and eye height call paths | Resolve corresponding members | Pending. |

##### 3. Living movement integration

| Role | A | B | Status |
| --- | --- | --- | --- |
| Travel/jump/tick | `world/entity/LivingEntity.java`: `travel(Vec3)` line 1914; `jumpFromGround()` line 1882; `aiStep()` line 2368 | same class/method signatures: `travel(Vec3)` line 2003; `jumpFromGround()` line 1975; `aiStep()` line 2455 | Exact class correspondence; split ground/air/water/lava/climb/jump and post-travel work into bounded slices. |

##### 4. Entity movement and collision

| Role | A | B | Status |
| --- | --- | --- | --- |
| Entity tick/movement | `world/entity/Entity.java`: `tick()` line 354; `move(MoverType, Vec3)` line 485; `moveRelative(float, Vec3)` line 1075 | same signatures: `tick()` line 391; `move(...)` line 534; `moveRelative(...)` line 1176 | Same exact names/signatures; compare axis order, clipping, step candidates, callbacks and transform. |
| Collision geometry | `world/phys/AABB.java`; `world/phys/shapes/VoxelShape.java` | same paths | Member correspondence and shape algorithms pending. |

##### 5. Blocks and fluids

| Role | A | B | Status |
| --- | --- | --- | --- |
| Base block / fluid | `world/level/block/Block.java`; `world/level/material/FlowingFluid.java` | same paths | Friction, speed/jump factors, callbacks, flow and registrations pending. |
| Relevant subclasses and state data | Enumerate registrations, overrides, tags and resource data | Same | Pending; failed name searches are not absence evidence. |

##### 6. Effects, enchantments, attributes and equipment

| Role | A | B | Status |
| --- | --- | --- | --- |
| Effects | `world/effect/MobEffects.java` | same path | SHA-256 currently identical; consumer/application paths pending. |
| Enchantments | `world/item/enchantment/Enchantments.java`; `EnchantmentHelper.java` | same paths | Enchantments registry source hashes match; helper hashes differ. Trace formulas, conditions, slots, tags and data. |
| Attributes/equipment | Resolve `Attributes`, modifier aggregation and equipment update paths | Resolve corresponding paths | Pending. |

##### 7. External influences and dependency closure

Compare client packet consumers for velocity/position correction, pushes, explosions, piston movement and riding transitions. Distinguish client-computed rules from synchronized/server-supplied state. Exact packet-handler correspondences and remaining state writers are pending.

##### Ordered queue

1. Complete stage 1: input/tick sequence; sprint gates and timers; jump and auto-jump; item-use slowdown; flight and riding gates.
2. Resolve stage 2 player state, defaults, pose and gate dependencies.
3. Compare stage 3 living travel/jump branches and helper writers.
4. Compare stage 4 entity movement, box/shape clipping, support and callbacks.
5. Enumerate stage 5 block/fluid overrides, registrations and resources.
6. Trace stage 6 effect/enchantment/attribute/equipment consumers and data.
7. Compare stage 7 external-state paths and close dependencies/cross-mechanic interactions.
