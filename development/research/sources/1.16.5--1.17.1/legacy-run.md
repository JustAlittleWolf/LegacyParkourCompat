# Source record: 1.16.5 → 1.17.1 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Namespace: official Mojang names; explicit `mojmap` CLI mode on both sides. Each release uses its own official client mappings artifact and is independently remapped to `named`.
- B command: `gradlew.bat --no-daemon --rerun-tasks decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful log at ignored `build/movement-discovery/1.17.1-mojmap.log`.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS for Vineflower (B log); target bytecode Java 16 (B); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default 4G heap and repository options.

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
| Fluid flow and spread core | `net/minecraft/world/level/material/FlowingFluid.java` | `A85A5CD6625B1B4A047BD693399E1F78C2978AE207AF927D4ABFAC3B34229552` | `93FAC0AA0B44DCD42A9E05783EEDE16E6E1E72CBC7CBF00D34F6E5A55B67F4C6` |
| Lava flammability height guard | `net/minecraft/world/level/material/LavaFluid.java` | `6E98F387C7561BFF3B66F2E86CE54BAB54A8FF3337639CB106E5E6AAAD8ADA9B` | `24F2E3D50C29A6E9B9CCEEE05CAA5254788772E390C31A6628D5431322D6DC17` |
| Living entity travel branches | `net/minecraft/world/entity/LivingEntity.java` | `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` |
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
