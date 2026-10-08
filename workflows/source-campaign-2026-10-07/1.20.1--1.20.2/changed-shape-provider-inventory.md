# Changed block files declaring shape providers: exact pair inventory

Scope: the 402 common Java files beneath `net/minecraft/world/level/block` in each endpoint. This appendix records the 39 files whose A/B source differs and that declare at least one `getShape`, `getCollisionShape` or `getBlockSupportShape` method. Each range is inclusive. Full-file SHA-256 values were recomputed and match the exact endpoint source manifests.

Method-body comparison result: 50 paired methods; 49 bodies are byte-identical, one body differs (`PinkPetalsBlock#getShape`), and one provider is added (`ChorusFlowerBlock#getBlockSupportShape`). No method body was removed.

| Source file | A provider methods and lines | A SHA-256 | B provider methods and lines | B SHA-256 |
|---|---|---|---|---|
| `AzaleaBlock.java` | `getShape` 25-27 | `d4cc0d23254922586c3a0652eed5eacb0cf94f83fcc328a5e9846a10528488d0` | `getShape` 25-27 | `666782120d85706baa31dfdcfdcd99f5acc671762b587f7f865578a2c23c6de2` |
| `BambooSaplingBlock.java` | `getShape` 32-35 | `8e120fea976dab656569da84e45936d3ec2ce0876d2a6c108cf1f4814e125daa` | `getShape` 32-35 | `2fd5b6fa798292f8b9a8248f06ccbc346dc61c00068dc17ad1d7ef0d406fbd09` |
| `BambooStalkBlock.java` | `getShape` 61-65; `getCollisionShape` 73-76 | `fbd9af0ff7562328e7bb9dc7ea29006a957c52437aed1f78121870778a95e27b` | `getShape` 61-65; `getCollisionShape` 73-76 | `b800148e8115b074e3e1e208455cc1993c4f303fffcaaf4e03e165c9ab74dda7` |
| `BigDripleafBlock.java` | `getCollisionShape` 245-247; `getShape` 250-252 | `910a0c0ab94c7b76f69b53ee31bee45bc3ecdda38d483f1662bb75fd513f31d8` | `getCollisionShape` 245-247; `getShape` 250-252 | `ca5892747404d11c09cd3eda093be12122406a1c1465dfed0077ae3bde3c8bc3` |
| `BigDripleafStemBlock.java` | `getShape` 39-51 | `dfe804a43c6fbb7840a04984bd50be9da713c0309ba2a292f391cbb13c978293` | `getShape` 39-51 | `2c7d3b1d23ed937d6fadbbd4d364a3cd583f1c840fd991fd5b719e4e8f4ff4ea` |
| `BubbleColumnBlock.java` | `getShape` 167-169 | `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452` | `getShape` 169-171 | `d50f3502727c7f0d42bbb1216c6f7683f032b88bac46c4ac33d081323272843e` |
| `CampfireBlock.java` | `getShape` 135-137 | `cfe63fec039801b9f6382bab758ec75788b012d33db92f0f513d1ecbf9e13e81` | `getShape` 138-140 | `ba550ab9cd90cd668a6dc4cdfd08de6bf452ede4f748ee974a3911b17929fe11` |
| `ChorusFlowerBlock.java` | none | `49c9a2f77d58c06b9f41775504285f89e803331beba6ed43c86753d55cbb1760` | `getBlockSupportShape` 47-49 | `de8d019fd188f25cab3ca9a1d4bcd5578e667b9d6cd464a7fe03ae703144cdb0` |
| `CocoaBlock.java` | `getShape` 75-88 | `9acd42d354b23a4598a5ae77e7f43e5ec77426bdb8265a6e1c7b7e12822d5345` | `getShape` 75-88 | `e496341c0ad919c3fd16d7f89b8bc9e27f33bda2f4467d8b4b5f8d63447b7b09` |
| `CropBlock.java` | `getShape` 44-46 | `2e46e817cce1503e74f986b2e0cb401ecb865041db68bdb5448df449f5940879` | `getShape` 44-46 | `076d4a6cca5a4f506d758a453b138a264a2acc5b188b4d82bda070d1e5bb65b0` |
| `DaylightDetectorBlock.java` | `getShape` 39-41 | `6b621c28a263077d808bd98f5c1b105e6da5c646d1f8fcbde8acf6591559d115` | `getShape` 39-41 | `ca77b90fcfad678e9186a02d32b082a165cfdedc969fb432c66da1de7d0c14d7` |
| `DecoratedPotBlock.java` | `getShape` 75-77 | `ab926cab03b78a25de0f0fc39241fa5aa10ab601e9a4ec89c8e69b30dd601e46` | `getShape` 84-86 | `940d918c94a413215ba1737475fe8d2f6b206590d60aa16c9ba6c21da84d003b` |
| `DiodeBlock.java` | `getShape` 32-34 | `573ea1b5095f072aff259f3d4e00f21a93f193500cdd15f67e295e26d5904d69` | `getShape` 32-34 | `3467f696369b5454f7f58ac29a7f923bfa488d469b12207c7cba4118f704be25` |
| `FlowerBlock.java` | `getShape` 29-32 | `1178c82a93ba47326447dc726ac216ae5080cdc107942af21098c1599d624aa6` | `getShape` 31-34 | `e736dffd5e8c4c0b4deadb67116b4560b8df42f2c91c8a668de26b8fae00e230` |
| `FungusBlock.java` | `getShape` 33-35 | `cf22874c8f628005a2450ef5c513166c6a281902cf87362dec56f1b3e8442059` | `getShape` 33-35 | `48c2335e918a90b0f6bec65afc032a14c6b0bd634c26a5561f2fb2d5dac065ce` |
| `HopperBlock.java` | `getShape` 60-75 | `42605354bf4b362c5deff1e4e9f3185f6e24767038c0d2f50349785b2273d9a6` | `getShape` 60-75 | `29edf180ce4050cc693d321bfbb51e0193b7a62c51b3c90e3675eab19c739aa9` |
| `LiquidBlock.java` | `getCollisionShape` 55-59; `getShape` 103-105 | `691765d865bdc4c384d7bd4482bbf041def46df4c419b08c975652b37a4353e9` | `getCollisionShape` 57-61; `getShape` 105-107 | `83b0520e518596cce0ac89654efcd32b4ad00bb89f088e50b175bcf11728d762` |
| `MangrovePropaguleBlock.java` | `getShape` 64-74 | `7c53f07c4c47c5cd93c825013d85e8bea7a45c2e3be1fd1ce44fcfc277f84343` | `getShape` 64-74 | `dcbecf6c38842f5d8031644b88e5bedd7c5b53eabe726cb8bcb3909f90af9f2c` |
| `MushroomBlock.java` | `getShape` 31-33 | `adb6ec041568e80f3ca6358e808dcc8c2f8301f77d696ec83c8ac6805db58a51` | `getShape` 31-33 | `bb9500533c6779b2f9d2771bf0dde6275d5f53d35bc560c146e26440c7d64394` |
| `PinkPetalsBlock.java` | `getShape` 48-50 | `f28fcc1bbc8a0fc11f8ba083f0d8a51f704a646cb83333ed497aed6ab78fa32e` | `getShape` 69-71 | `156b7cb8222b250076b721e889e88946eefb032ab826fec7e87ac4f9560bb9cc` |
| `piston/PistonBaseBlock.java` | `getShape` 61-81 | `a3fcfa20e4cd5005f49fdde4e51a49e61e2802820aae278dd4b7f32fd13812b9` | `getShape` 61-81 | `adb5c06fa4e8337bd099f21a7ff40de46c8d784b06931d840eacbd4036e050b3` |
| `PitcherCropBlock.java` | `getCollisionShape` 66-72; `getShape` 95-99 | `33a37f21d8f8ff2aa4c682f51b4ec2bbad2bf986f49176aebca0217eb741fd90` | `getShape` 52-56; `getCollisionShape` 59-65 | `63ec9e66977846ab55a40e3257e511d3638736e8bb1f2d774ca90f982b978a16` |
| `PowderSnowBlock.java` | `getCollisionShape` 96-112 | `40b15b7239a24058cc34ed9f1ede0611edde897badbef5486c493cf620949c5d` | `getCollisionShape` 97-113 | `f94f20b85520194757470722de071fb781107acdadee16598cf401d18248c09f` |
| `RedStoneWireBlock.java` | `getShape` 130-132 | `db9be63a8fe2a90ef72c6321346224c4b564d9f7c445a008f04b84881c7910d3` | `getShape` 130-132 | `decb31ec105dc451b638f1965a7f99a2f8412b6f5d8d41cc1d3c5f4758c3df46` |
| `SaplingBlock.java` | `getShape` 31-33 | `bfc04c9d08a2171ba9cb817ba0379f85c8b0099af2de949be17989e908b65ef6` | `getShape` 31-33 | `4a1e605a17d8c661824fd63e9a36a4e3870ada5c62655ca88d4703eace08b51b` |
| `SeagrassBlock.java` | `getShape` 32-34 | `83912f34a5ff9166c4785834211b21c5a96d8328f2ddc4d6d8559b240eb413b7` | `getShape` 33-35 | `4e76417076ea6294bc9bcab7d9bc6e8d4edd67c24094665913c3f54231849a19` |
| `SeaPickleBlock.java` | `getShape` 87-99 | `1a254d298fdb26d20fbf442624aa1f425fedb0c545474e317af24ce26888523e` | `getShape` 87-99 | `c6f4fb4b81b0d6a8e5d90ece749a6599575288f30bb0627057fd7355867b616d` |
| `ShulkerBoxBlock.java` | `getBlockSupportShape` 229-233; `getShape` 236-239 | `cbac44132ca5feae1563c7590afbd941a581860941b7f2306b29cfd85e75224c` | `getBlockSupportShape` 225-229; `getShape` 232-235 | `7d04605ccfc899fec7f448b465edecf79f2953d21cbf4bcefc46ffc3e4fe593e` |
| `SignBlock.java` | `getShape` 61-63 | `bb80a045c5ab226fbe6715b02f29e7900140394435c53df02b8e90d6bec2ded7` | `getShape` 61-63 | `33ce0b28e5f75aeb07f13cf35f0e4c8c6434e1e6f3924de50a5cb39523abdabb` |
| `SkullBlock.java` | `getShape` 29-31 | `850f2b8d01434936a4bfc4f7962137a705e5000780f63d453708fb45e2664502` | `getShape` 29-31 | `e5b214451911878c4b3dc5b5512b8b5565a015478c4216e0f8f39223970d5808` |
| `SlabBlock.java` | `getShape` 48-58 | `8d0aba6039a7f1cd0ba3ebc32a6c614877848ba0428b11b2117710b288059b00` | `getShape` 49-59 | `95a008f51158ebccd448c5e04af8859b20104d4df4c7fae9e45764ddb228df5f` |
| `SmallDripleafBlock.java` | `getShape` 42-44 | `bb602e1595fbe974c92f341ca3eaddecd65c3d997df2e8f3bcb17d98abf8d51b` | `getShape` 42-44 | `8933640427242d906c7a8896a2943da1206b67a11226f10524aff751d5144228` |
| `state/BlockBehaviour.java` | `getBlockSupportShape` 245-247; `getShape` 285-287; `getCollisionShape` 290-292; overloads 602-621 | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` | same method ranges | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` |
| `StemBlock.java` | `getShape` 49-51 | `8adedca1fae6d7a599c2d1bd004ca637b1182d8badf8ecc20b476a2e69bd973c` | `getShape` 49-51 | `7a5452fe4c8232426c4f8b111fd32158b586396f46ac27d81071a29006f2a735` |
| `SweetBerryBushBlock.java` | `getShape` 48-54 | `aaf99e276258941ad011a913c518db6604351637601cbbf5e4a6cd6815520a64` | `getShape` 48-54 | `73375b2cb49efcbc3e64b49cac0fad3b30dd6a583518242df13c221ad8844cf5` |
| `TallGrassBlock.java` | `getShape` 23-25 | `ac15cb150454bd1d6ca735cd8db264560fee5c9f037098bf9ecdd972f688b2a4` | `getShape` 23-25 | `6c03b4a88e3e777de030fa1b5aa06213f690e726f13afc96ebb14336784c2ba0` |
| `TallSeagrassBlock.java` | `getShape` 32-34 | `5dcfddeb7bac258969faedf3a74940544dbefd9af5ec87abfd630804f316b317` | `getShape` 33-35 | `3ea6f11f1cc3ce05657148588f4ccefc2d69adf34a5ff7b879d4b55c111eef66` |
| `TurtleEggBlock.java` | `getShape` 148-150 | `e619ace0e1bb7961bc1b703f57e0305deb55e4fa357c3cf7aadb78555a46d9c0` | `getShape` 150-152 | `cd27b3cdf6c1fb145c35532d7ffc06fc0b79b67556c10895fd7aeff9dec33474` |
| `WallSkullBlock.java` | `getShape` 43-45 | `e2dcd7959de4c3580fe46720fd8b543120cd0ef640c141e80d6c12218371e955` | `getShape` 43-45 | `7114045ae973c37c364d07dc0824400d071e3a8534d30edaaa9db432aa40441d` |

This is a bounded changed-source pass only. Unchanged block-class bodies, state/property producers, registry/state availability, fluid shape providers, tags/resources, and neighboring-block dependencies remain in S10/D-COLLISION/D-WORLD-MOVEMENT.
