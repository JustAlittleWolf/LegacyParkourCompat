# Source record: 1.7.10 → 1.8.9 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather for both exact releases; explicit `--mappings=feather` per invocation. Resolver selected `ornithe-feather` with release-specific builds `1.7.10+build.2` and `1.8.9+build.2`; no mapping file crossed versions.
- Source preparation command and log: `gradlew.bat decompileMinecraft --versions=1.7.10 --mappings=feather` (`../../../../build/movement-discovery-1.7.10-feather.log`) and `gradlew.bat decompileMinecraft --rerun-tasks --versions=1.8.9 --mappings=feather` (`../../../../build/movement-discovery-1.8.9-feather.log`). Both logs report the exact request/resolution, mapping build, successful completion and `BUILD SUCCESSFUL`.
- Toolchain/decompiler/remapper versions and options: JDK 25.0.3+9-LTS (decompiler fork; target bytecode Java 8); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; Gradle 9.7.1; default 4G decompiler heap; Tiny Remapper options are `ignoreConflicts`, `renameInvalidLocals`, `rebuildSourceFilenames`, `fixPackageAccess`. The 1.7.10 log reports eight Vineflower switch simplification errors, none in the indexed movement methods. The required movement files exist on both sides; no damaged relevant method was reported.

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
