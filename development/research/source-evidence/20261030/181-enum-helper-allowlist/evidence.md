# 1.8.1 enum-switch helper allowlist evidence

## Result

Static inspection maps the full Vineflower failure block to **59 diagnostics and 41 distinct helper classes**. Each diagnostic method has a `GETSTATIC` reference to exactly one distinct synthetic `[I` switch-helper class; several methods read two or three arrays from that same helper. There are no unmatched or multi-helper method rows.

One diagnostic at log line 30540 is `const(2)`, not `const(1)`: `StrongholdPieces__EndPortalRoom.postProcess` references `StrongholdPieces__21168123.f_49686088:[I`. It was omitted by the earlier `const(1)`-only listing. The other 58 diagnostics are `const(1)`.

All 41 helper identities below meet the same **static candidate** contract: the pinned 1.8.1 Tiny row maps the raw class to the displayed flattened name; the corresponding failing method bytecode reads the helper's synthetic `[I` field; the helper has no existing nesting metadata and no canonical-name collision; and the pinned 1.8.2 mapping and bytecode provide the displayed canonical nested identity and matching owner metadata. This proves the identity and bytecode-shape inputs for all listed candidates. It does **not** prove that Vineflower will clear the errors; that requires the designated owner's separately authorized candidate generation and existing strict gates.

The source-check scripts used `javap -p -v -c` against the local raw and mapped archives plus the pinned 1.8.2 comparator archive. For all 41 raw and mapped helpers, the class is synthetic, every declared `[I` field is `static final synthetic`, the initializer contains enum `values()`/`ordinal()` mapping writes and `NoSuchFieldError` handlers, and neither `InnerClasses` nor `EnclosingMethod` is present. In the 1.8.1 mapped archive, all 41 flat entries exist and all 41 canonical entries are absent. For every 1.8.2 comparator, the canonical helper is synthetic; the helper has a static `InnerClasses` record and `EnclosingMethod` with null method; and the owner has the matching static `InnerClasses` record. No conflicting target record exists in the 1.8.1 owner metadata.

## Exact candidate table

Tiny row numbers refer to the pinned files listed below. The 1.8.1 mapping cell gives row number, raw obfuscated class, and intermediary class. The owner cell gives its 1.8.1 named owner row and raw class. The comparator cell gives the 1.8.2 row whose destination is the exact canonical identity. Log references identify every failed method row assigned to that helper.

| 1.8.1 flattened helper → owner → canonical nested name | 1.8.1 helper row: raw / intermediary | 1.8.1 owner row: raw | 1.8.2 canonical row | Failure log lines |
|---|---:|---:|---:|---:|
| `net/minecraft/block/BannerBlock__32351573` → `net/minecraft/block/BannerBlock` → `net/minecraft/block/BannerBlock$32351573` | 30293: `asz` / `C_32351573` | 30290: `asy` | 30368 | 30522 |
| `net/minecraft/block/ButtonBlock__81094094` → `net/minecraft/block/ButtonBlock` → `net/minecraft/block/ButtonBlock$81094094` | 7872: `aub` / `C_81094094` | 7848: `aua` | 7892 | 30534, 30535 |
| `net/minecraft/block/CocoaBlock__33107511` → `net/minecraft/block/CocoaBlock` → `net/minecraft/block/CocoaBlock$33107511` | 20716: `auj` / `C_33107511` | 20706: `aui` | 20773 | 30532 |
| `net/minecraft/block/entity/MovingBlockEntity__37645622` → `net/minecraft/block/entity/MovingBlockEntity` → `net/minecraft/block/entity/MovingBlockEntity$37645622` | 14622: `bdu` / `C_37645622` | 14593: `bdt` | 14679 | 30547 |
| `net/minecraft/block/FlowerPotBlock__10723286` → `net/minecraft/block/FlowerPotBlock` → `net/minecraft/block/FlowerPotBlock$10723286` | 21478: `awb` / `C_10723286` | 21469: `awa` | 21535 | 30524, 30525 |
| `net/minecraft/block/InfestedBlock__49464893` → `net/minecraft/block/InfestedBlock` → `net/minecraft/block/InfestedBlock$49464893` | 15304: `axq` / `C_49464893` | 15300: `axp` | 15362 | 30571 |
| `net/minecraft/block/LadderBlock__59546086` → `net/minecraft/block/LadderBlock` → `net/minecraft/block/LadderBlock$59546086` | 2447: `axd` / `C_59546086` | 2441: `axc` | 2461 | 30527 |
| `net/minecraft/block/LeverBlock__77791932` → `net/minecraft/block/LeverBlock` → `net/minecraft/block/LeverBlock$77791932` | 964: `axh` / `C_77791932` | 951: `axg` | 978 | 30558, 30559, 30560 |
| `net/minecraft/block/LogBlock__28866629` → `net/minecraft/block/LogBlock` → `net/minecraft/block/LogBlock$28866629` | 2644: `ayq` / `C_28866629` | 2640: `ayo` | 2657 | 30564, 30565, 30566 |
| `net/minecraft/block/MushroomBlock__62405337` → `net/minecraft/block/MushroomBlock` → `net/minecraft/block/MushroomBlock$62405337` | 15831: `awx` / `C_62405337` | 15824: `aww` | 15889 | 30519 |
| `net/minecraft/block/QuartzBlock__58206234` → `net/minecraft/block/QuartzBlock` → `net/minecraft/block/QuartzBlock$58206234` | 23049: `azk` / `C_58206234` | 23047: `azj` | 23106 | 30526 |
| `net/minecraft/block/SkullBlock__09110683` → `net/minecraft/block/SkullBlock` → `net/minecraft/block/SkullBlock$09110683` | 21998: `baj` / `C_09110683` | 21982: `bah` | 22055 | 30539 |
| `net/minecraft/block/TorchBlock__01367223` → `net/minecraft/block/TorchBlock` → `net/minecraft/block/TorchBlock$01367223` | 11469: `bbl` / `C_01367223` | 11452: `bbj` | 11497 | 30572 |
| `net/minecraft/block/TripwireHookBlock__66929955` → `net/minecraft/block/TripwireHookBlock` → `net/minecraft/block/TripwireHookBlock$66929955` | 21213: `bbs` / `C_66929955` | 21185: `bbr` | 21270 | 30555 |
| `net/minecraft/client/gui/screen/menu/twitch/StreamUnavailableScreen__15502813` → `net/minecraft/client/gui/screen/menu/twitch/StreamUnavailableScreen` → `net/minecraft/client/gui/screen/menu/twitch/StreamUnavailableScreen$15502813` | 28378: `cak` / `C_15502813` | 28363: `caj` | 28446 | 30548, 30549, 30550 |
| `net/minecraft/client/network/PlayerInfo__33495983` → `net/minecraft/client/network/PlayerInfo` → `net/minecraft/client/network/PlayerInfo$33495983` | 16929: `cet` / `C_33495983` | 16876: `cer` | 16983 | 30533 |
| `net/minecraft/client/render/block/BlockModelRenderer__29788386` → `net/minecraft/client/render/block/BlockModelRenderer` → `net/minecraft/client/render/block/BlockModelRenderer$29788386` | 30112: `clo` / `C_29788386` | 30042: `cln` | 30187 | 30516 |
| `net/minecraft/client/render/ItemInHandRenderer__83556887` → `net/minecraft/client/render/ItemInHandRenderer` → `net/minecraft/client/render/ItemInHandRenderer$83556887` | 4382: `ckj` / `C_83556887` | 4313: `cki` | 4397 | 30542 |
| `net/minecraft/client/render/model/block/MiningQuad__59463020` → `net/minecraft/client/render/model/block/MiningQuad` → `net/minecraft/client/render/model/block/MiningQuad$59463020` | 33713: `cmo` / `C_59463020` | 33705: `cmn` | 33807 | 30521 |
| `net/minecraft/client/render/platform/GlStateManager__48070952` → `net/minecraft/client/render/platform/GlStateManager` → `net/minecraft/client/render/platform/GlStateManager$48070952` | 32033: `cjn` / `C_48070952` | 31857: `cjm` | 32118 | 30569 |
| `net/minecraft/client/render/vertex/BufferBuilder__84289903` → `net/minecraft/client/render/vertex/BufferBuilder` → `net/minecraft/client/render/vertex/BufferBuilder$84289903` | 34690: `civ` / `C_84289903` | 34554: `ciu` | 34812 | 30515 |
| `net/minecraft/client/render/vertex/BufferUploader__14447104` → `net/minecraft/client/render/vertex/BufferUploader` → `net/minecraft/client/render/vertex/BufferUploader$14447104` | 30966: `ciy` / `C_14447104` | 30962: `cix` | 31050 | 30561, 30562 |
| `net/minecraft/client/render/vertex/VertexFormat__53981484` → `net/minecraft/client/render/vertex/VertexFormat` → `net/minecraft/client/render/vertex/VertexFormat$53981484` | 31300: `cut` / `C_53981484` | 31273: `cus` | 31388 | 30545 |
| `net/minecraft/client/render/world/ChunkOcclusionGraph__14330863` → `net/minecraft/client/render/world/ChunkOcclusionGraph` → `net/minecraft/client/render/world/ChunkOcclusionGraph$14330863` | 30960: `cot` / `C_14330863` | 30934: `cos` | 31045 | 30523 |
| `net/minecraft/client/render/world/WorldRenderer__63976075` → `net/minecraft/client/render/world/WorldRenderer` → `net/minecraft/client/render/world/WorldRenderer$63976075` | 2919: `ckp` / `C_63976075` | 2731: `ckn` | 2937 | 30541 |
| `net/minecraft/client/resource/model/BlockElement__85922567` → `net/minecraft/client/resource/model/BlockElement` → `net/minecraft/client/resource/model/BlockElement$85922567` | 34231: `clv` / `C_85922567` | 34216: `clu` | 34328 | 30514 |
| `net/minecraft/client/resource/model/BlockModelShaper__67682539` → `net/minecraft/client/resource/model/BlockModelShaper` → `net/minecraft/client/resource/model/BlockModelShaper$67682539` | 34890: `clk` / `C_67682539` | 34861: `clc` | 35011 | 30546 |
| `net/minecraft/client/sound/system/SoundManager__86087158` → `net/minecraft/client/sound/system/SoundManager` → `net/minecraft/client/sound/system/SoundManager$86087158` | 28292: `czk` / `C_86087158` | 28245: `czh` | 28357 | 30520 |
| `net/minecraft/client/twitch/BroadcastController__86014756` → `net/minecraft/client/twitch/BroadcastController` → `net/minecraft/client/twitch/BroadcastController$86014756` | 28738: `czx` / `C_86014756` | 28627: `czu` | 28806 | 30568 |
| `net/minecraft/entity/living/player/PlayerEntity__28691489` → `net/minecraft/entity/living/player/PlayerEntity` → `net/minecraft/entity/living/player/PlayerEntity$28691489` | 4850: `ahb` / `C_28691489` | 4634: `aha` | 4865 | 30536, 30537, 30538 |
| `net/minecraft/entity/vehicle/MinecartEntity__65200581` → `net/minecraft/entity/vehicle/MinecartEntity` → `net/minecraft/entity/vehicle/MinecartEntity$65200581` | 2301: `adz` / `C_65200581` | 2233: `ady` | 2315 | 30553, 30554 |
| `net/minecraft/item/HoeItem__22169369` → `net/minecraft/item/HoeItem` → `net/minecraft/item/HoeItem$22169369` | 6342: `alm` / `C_22169369` | 6331: `all` | 6357 | 30570 |
| `net/minecraft/network/packet/s2c/play/PlayerCombatS2CPacket__77500029` → `net/minecraft/network/packet/s2c/play/PlayerCombatS2CPacket` → `net/minecraft/network/packet/s2c/play/PlayerCombatS2CPacket$77500029` | 32368: `kg` / `C_77500029` | 32357: `kf` | 32448 | 30567 |
| `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler__25347352` → `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler` → `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler$25347352` | 27768: `rr` / `C_25347352` | 27762: `rq` | 27846 | 30563 |
| `net/minecraft/world/biome/layer/AddEdgeLayer__41582934` → `net/minecraft/world/biome/layer/AddEdgeLayer` → `net/minecraft/world/biome/layer/AddEdgeLayer$41582934` | 26356: `bok` / `C_41582934` | 26335: `boj` | 26430 | 30529 |
| `net/minecraft/world/gen/structure/MineshaftPieces__45169790` → `net/minecraft/world/gen/structure/MineshaftPieces` → `net/minecraft/world/gen/structure/MineshaftPieces$45169790` | 15298: `bjd` / `C_45169790` | 15224: `bjc` | 15356 | 30517, 30518, 30551, 30552, 30556, 30557 |
| `net/minecraft/world/gen/structure/OceanMonumentPieces__25820342` → `net/minecraft/world/gen/structure/OceanMonumentPieces` → `net/minecraft/world/gen/structure/OceanMonumentPieces$25820342` | 30525: `bkh` / `C_25820342` | 30523: `bkg` | 30600 | 30530 |
| `net/minecraft/world/gen/structure/StrongholdPieces__21168123` → `net/minecraft/world/gen/structure/StrongholdPieces` → `net/minecraft/world/gen/structure/StrongholdPieces$21168123` | 15492: `bls` / `C_21168123` | 15451: `blp` | 15550 | 30540 (`const(2)`) |
| `net/minecraft/world/gen/structure/StructureBox__89926849` → `net/minecraft/world/gen/structure/StructureBox` → `net/minecraft/world/gen/structure/StructureBox$89926849` | 15812: `bja` / `C_89926849` | 15746: `biz` | 15870 | 30531 |
| `net/minecraft/world/gen/structure/StructurePiece__04227101` → `net/minecraft/world/gen/structure/StructurePiece` → `net/minecraft/world/gen/structure/StructurePiece$04227101` | 16841: `bmr` / `C_04227101` | 16665: `bmq` | 16895 | 30543, 30544 |
| `net/minecraft/world/gen/structure/VillagePieces__64165767` → `net/minecraft/world/gen/structure/VillagePieces` → `net/minecraft/world/gen/structure/VillagePieces$64165767` | 16625: `bmx` / `C_64165767` | 16314: `bmw` | 16679 | 30528 |

## Evidence and bounded use

- 1.8.1 Feather build: `feather-gen2-1.8.1+build.2-mergedv2.jar`, pinned SHA-256 `DAC40CBD25C4D76D99D4263FD9754AB0195EFD658756B6828FD06F0C4A8002F7`. Its cached Tiny mapping is `feather-gen2-1.8.1+build.2.tiny`.
- 1.8.2 comparator Feather build: `feather-gen2-1.8.2+build.2`; its cached Tiny mapping and mapped client jar are used only as identity/metadata comparators.
- Immutable inspected inputs (SHA-256):
  - 1.8.1 raw client jar: `66A749AD95ADCB929439155C0341309E89E437444867E108DCB36FD16493B0BD`
  - 1.8.1 mapped client jar: `D2977E05623D692E2D73DACBD0D6328B9226F47D80F824711A6D5FE81DF09001`
  - 1.8.1 Tiny mapping: `F26E3FEA2C580CF9EB2D5C47CD32A071F72177BA5E2C208C39196E669C8A383D`
  - pinned 1.8.1 Feather mapping jar: `DAC40CBD25C4D76D99D4263FD9754AB0195EFD658756B6828FD06F0C4A8002F7`
- pinned 1.8.2 comparator Feather mapping jar: `8235D85A8471D541F2BAD73C2A60C6132A9432FE804B98369982FC8DE847F37B`
  - 1.8.2 Tiny mapping: `D5E27EBA2AA4D62251B3A573D072272802CDB2F1359CF96FD499C969E028AD9F`
  - 1.8.2 mapped comparator jar: `194B478E4FEDEBF3A95BA4557296A70A0548AF4974CB52DC29F94FAE5545983D`
  - retained daemon log: `C4C72FA36D6E88753D04C161CB14122356A98F27B64FD4E6CF0B9CA46D153491`
- Bounded candidate inputs: exact version `1.8.1`; mapping label `ornithe-feather`; exact mapping artifact SHA-256 above; and only the 41 intermediary-to-canonical class pairs listed in the table. For any candidate, require the exact flat class and owner, absent canonical entry, expected synthetic enum-switch field/reference shape, and absent/conflict-free metadata before remapping. TinyRemapper must rewrite each selected class identity and all symbolic references; then add the matching static `InnerClasses` record to helper and owner (`inner=canonical`, `outer=owner`, `inner_name=<numeric suffix>`) and add helper-only `EnclosingMethod(owner, method_index=0)`, following the verified 1.8.2 comparator. Preserve the helper's synthetic flag, all code and unrelated archive entries, then use the already accepted structural verifier and strict zero-ERROR/publication gates.
- No mapping artifact, archive, cache, source tree, buildSrc file, or tracker was modified. No transform, decompile, download, build, test, or game operation was run. This evidence report is the only write.
