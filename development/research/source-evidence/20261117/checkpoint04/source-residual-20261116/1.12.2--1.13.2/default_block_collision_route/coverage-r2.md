# 1.12.2→1.13.2 default Block collision route (bounded source slice)

## r2 path correction binding

This revision preserves the bounded source comparison and all evidence from r1 report SHA-256 d6375aead98b30a893065136597b80507e1c596addec0ef8daf186d2e64f31ad unchanged, with only the three source path labels identified in the independent r1 audit corrected below. The audit bound to r1 is default_block_collision_route_audit/verdict.md, SHA-256 b6911190af6e303128a4d2312896571821c8f14ddc81e7d5acfb434e5b6241a2; it verified the 76-key claim and requested only these path-label corrections. No source comparison was repeated for this correction.


Verdict: ACCEPT for the exact common-key slice below. The generic inherited provider produces a full unit cube in both endpoints, and every selected 1.13.2 registration has hasCollision=true. This is a provider/default-gate parity result only. It does not close specialized-provider keys, unresolved aliases, collision-query/candidate/clipping math, all block-provider coverage, movement outcomes, or the release pair.

## Identity and source binding

- 1.12.2 Feather Gen2 build.2 source-manifest identity: b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da.
- 1.13.2 Feather Gen2 source-manifest identity: 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211.
- Local source-manifest file SHA-256: 1.12.2 b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da; 1.13.2 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211.
- Accepted registry inventory identities reused: 1.12.2 29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2; 1.13.2 d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139; exact-key crosswalk bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a.
- Identity ledger: build/source-identities-20261116/locations.tsv. Canonical sources are read-only. Per-class SHA-256 values for the old effective-owner sweep and selected new owners are listed below.

## Compared route

- Old Block.getShape returns FULL_BLOCK_SHAPE, a Box from (0,0,0) to (1,1,1). Old Block.getCollisionShape forwards to state shape; generic Block.addCollisions obtains that collision shape and passes it to the old collision helper. StateDefinition dispatches to the block instance.
- New Block.getShape returns VoxelShapes.block(), built as the full unit cube. New Block.getCollisionShape returns the state shape if the block's final hasCollision field is true, otherwise empty. BlockState dispatches to the registered block; WorldView collision enumeration queries this method.
- A declaration sweep of the 43 selected old concrete registration classes and their non-Block ancestors (BlockWithBlockEntity, TransparentBlock, FallingBlock, HorizontalFacingBlock, FacingBlock) found no getShape override. The sole declaration is generic Block.getShape. Crosswalk rows were restricted to old effective addCollisions=Block, getCollisionShape=Block and new effective Block.getCollisionShape→Block.getShape.
- All selected target registrations were checked for direct noCollision() use and copied noCollision property sources; none were found. Selected registrations therefore take the target full-shape branch for every registered state. The default provider is state-independent, so every legal registered state combination in these keys shares this result.

## Exact common-key rows (76)

| Key | Old concrete registration class | New concrete registration class |
|---|---|---|
| minecraft:stone | StoneBlock | StoneBlock |
| minecraft:dirt | DirtBlock | Block |
| minecraft:cobblestone | Block | Block |
| minecraft:bedrock | BedrockBlock | BedrockBlock |
| minecraft:sand | SandBlock | SandBlock |
| minecraft:gravel | GravelBlock | GravelBlock |
| minecraft:gold_ore | OreBlock | OreBlock |
| minecraft:iron_ore | OreBlock | OreBlock |
| minecraft:coal_ore | OreBlock | OreBlock |
| minecraft:sponge | SpongeBlock | SpongeBlock |
| minecraft:glass | GlassBlock | GlassBlock |
| minecraft:lapis_ore | OreBlock | OreBlock |
| minecraft:lapis_block | Block | Block |
| minecraft:dispenser | DispenserBlock | DispenserBlock |
| minecraft:sandstone | SandstoneBlock | Block |
| minecraft:gold_block | Block | Block |
| minecraft:iron_block | Block | Block |
| minecraft:tnt | TntBlock | TntBlock |
| minecraft:bookshelf | BookshelfBlock | BookshelfBlock |
| minecraft:mossy_cobblestone | Block | Block |
| minecraft:obsidian | ObsidianBlock | Block |
| minecraft:diamond_ore | OreBlock | OreBlock |
| minecraft:diamond_block | Block | Block |
| minecraft:crafting_table | CraftingTableBlock | CraftingTableBlock |
| minecraft:furnace | FurnaceBlock | FurnaceBlock |
| minecraft:redstone_ore | RedstoneOreBlock | RedstoneOreBlock |
| minecraft:ice | IceBlock | IceBlock |
| minecraft:clay | ClayBlock | ClayBlock |
| minecraft:jukebox | JukeboxBlock | JukeboxBlock |
| minecraft:pumpkin | PumpkinBlock | PumpkinBlock |
| minecraft:netherrack | NetherrackBlock | Block |
| minecraft:glowstone | GlowstoneBlock | GlowstoneBlock |
| minecraft:brown_mushroom_block | MushroomBlock | MushroomBlock |
| minecraft:red_mushroom_block | MushroomBlock | MushroomBlock |
| minecraft:mycelium | MyceliumBlock | MyceliumBlock |
| minecraft:end_stone | Block | Block |
| minecraft:redstone_lamp | RedstoneLampBlock | RedstoneLampBlock |
| minecraft:emerald_ore | OreBlock | OreBlock |
| minecraft:emerald_block | Block | Block |
| minecraft:command_block | CommandBlock | CommandBlock |
| minecraft:beacon | BeaconBlock | BeaconBlock |
| minecraft:redstone_block | RedstoneBlock | RedstoneBlock |
| minecraft:quartz_block | QuartzBlock | Block |
| minecraft:dropper | DropperBlock | DropperBlock |
| minecraft:barrier | BarrierBlock | BarrierBlock |
| minecraft:prismarine | PrismarineBlock | Block |
| minecraft:sea_lantern | SeaLanternBlock | SeaLanternBlock |
| minecraft:hay_block | HayBlock | HayBlock |
| minecraft:coal_block | Block | Block |
| minecraft:packed_ice | PackedIceBlock | PackedIceBlock |
| minecraft:red_sandstone | RedSandstoneBlock | Block |
| minecraft:chorus_flower | ChorusFlowerBlock | ChorusFlowerBlock |
| minecraft:purpur_block | Block | Block |
| minecraft:purpur_pillar | AxisBlock | AxisBlock |
| minecraft:repeating_command_block | CommandBlock | CommandBlock |
| minecraft:chain_command_block | CommandBlock | CommandBlock |
| minecraft:frosted_ice | FrostedIceBlock | FrostedIceBlock |
| minecraft:nether_wart_block | Block | Block |
| minecraft:bone_block | BoneBlock | AxisBlock |
| minecraft:observer | ObserverBlock | ObserverBlock |
| minecraft:white_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:orange_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:magenta_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:light_blue_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:yellow_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:lime_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:pink_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:gray_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:cyan_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:purple_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:blue_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:brown_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:green_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:red_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:black_glazed_terracotta | GlazedTerracottaBlock | GlazedTerracottaBlock |
| minecraft:structure_block | StructureBlock | StructureBlock |

## Route source hashes

- 1.12.2 net/minecraft/block/Block.java: e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1
- 1.12.2 net/minecraft/block/state/StateDefinition.java: 934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37
- 1.12.2 net/minecraft/world/World.java: e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594
- 1.13.2 net/minecraft/block/Block.java: 735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4
- 1.13.2 net/minecraft/block/state/BlockState.java: 0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70
- 1.13.2 net/minecraft/world/WorldView.java: 9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f
- 1.13.2 net/minecraft/block/shape/VoxelShapes.java: 313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95

### Class source hashes

- 1.12.2 net/minecraft/block/AxisBlock.java: 043094888447998c451a572d303cdf2c572d7760e311573f7ce12c91e5ceed4a
- 1.12.2 net/minecraft/block/BarrierBlock.java: 57b4fb0ef1f0feda7dec76d62136e8cf302548cb5c9cd47ebd55d66db114b94b
- 1.12.2 net/minecraft/block/BeaconBlock.java: 4526cd043c9c3888b69283682807e0b08d680854652946d0c6feae49a961ae6e
- 1.12.2 net/minecraft/block/BedrockBlock.java: 370e563f30cafa2ef8e9bad05aaf3d691a25fc9e60a053cca11a8caad27c5852
- 1.12.2 net/minecraft/block/Block.java: e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1
- 1.12.2 net/minecraft/block/BlockWithBlockEntity.java: f62677bf00ae213f20fdd5c3b11faabc27f451c86cd2766ff5062fdbe0760cf8
- 1.12.2 net/minecraft/block/BoneBlock.java: 17c58c39963e71b39433856d9a72c8c1efd804f74408f200c983b22db5143445
- 1.12.2 net/minecraft/block/BookshelfBlock.java: 4c2e90e4d49bec1ad754ae0298e3b0b66bba7587fb3d11ae086fd4abb1bccabb
- 1.12.2 net/minecraft/block/ChorusFlowerBlock.java: ff3d646a0951e486f0754657d01692e8bdc36ca22cf5e85ef93382ae59d0e0ce
- 1.12.2 net/minecraft/block/ClayBlock.java: 03feb4f7de37aae893e951a267a7fcb9e81e8dc1283f88a143b1f4ab0bf91768
- 1.12.2 net/minecraft/block/CommandBlock.java: 43ad1906237b47dc2c6e34a3d79324883909210d33e455a266b39b875e23552e
- 1.12.2 net/minecraft/block/CraftingTableBlock.java: c4a71faaef9f6f53c3272ac817117615ed10c4ccfdccb7f63f4203e87cda1193
- 1.12.2 net/minecraft/block/DirtBlock.java: d62b9035655c2bd2169a68ad5f49b6172fd1dba8b148eb0046477038979c4de6
- 1.12.2 net/minecraft/block/DispenserBlock.java: 9fd8044a5d642760d8a7591203b8248a864ee09792e743f839e94cee2c1588c3
- 1.12.2 net/minecraft/block/DropperBlock.java: 5bf3c519839c376e8e90b2f90f796dc2c952359387865621d5fe9fe3d2a38dd8
- 1.12.2 net/minecraft/block/FacingBlock.java: 32f747c0ca9145efa9f36e99a7673f62f4c67a2c308ac33cc0e3ca5f9666cf22
- 1.12.2 net/minecraft/block/FallingBlock.java: 27c2f2849a969907972b4add3fdb4ba42b5a2430fd91b0dc37452a77a95e892c
- 1.12.2 net/minecraft/block/FrostedIceBlock.java: 27bbe9206e78f1b737883403169ab3a7f8783b45062478fed6329aae3a578b9b
- 1.12.2 net/minecraft/block/FurnaceBlock.java: 75d62a42c4889d7391b2eb69c02fe734ba1eeaf1971b5410029e59c6a9a791f0
- 1.12.2 net/minecraft/block/GlassBlock.java: 554a0884bfef7bc61b4a2535193037dfb0de0e338227ae0335dbd6432ef62471
- 1.12.2 net/minecraft/block/GlazedTerracottaBlock.java: e4e902f0cf53d688359746cfdc6ee98237804f01828793401f9a253f15b47ed1
- 1.12.2 net/minecraft/block/GlowstoneBlock.java: 52d7079adca146dd02c775b4eb3086dbf58ca15f45f014501283cfc0edd2b9a8
- 1.12.2 net/minecraft/block/GravelBlock.java: 8dbabe65cae1e3fd6cd7414f51fcc440ae18a8b3b5c6a49ad16335a14dbf539f
- 1.12.2 net/minecraft/block/HayBlock.java: 10719b737d5c7235363f24481b7bb87255baa7c93899836f8fa6abb097353c68
- 1.12.2 net/minecraft/block/HorizontalFacingBlock.java: 99c33e79b000c5545640894d0a291fe62dd07ed7330a5b08ca23c1db3d83dd83
- 1.12.2 net/minecraft/block/IceBlock.java: c92bb850b67f5b3857d150eb6cc1c1d17d351ef0a553613a09fffe543c1d7952
- 1.12.2 net/minecraft/block/JukeboxBlock.java: 114d2299019556d0b23c74a4f4bd467b6a813ed858fe81c4dbbed6fd1b451711
- 1.12.2 net/minecraft/block/MushroomBlock.java: ccb9c38bb043e36916e5aec25fc94ae8d12f38c9666414bb3463cde4e4ccf8ae
- 1.12.2 net/minecraft/block/MyceliumBlock.java: 3f03e333d140213e46f8c3514efda9745b120ce760668a8ad4317631340a3515
- 1.12.2 net/minecraft/block/NetherrackBlock.java: 4f10b635f6c25d0395d238b3a63d85b3f20d4ffdfe35451cb15f60ab31db6c1f
- 1.12.2 net/minecraft/block/ObserverBlock.java: 04ce16139c2cc7c5bf4420151669dc4486ddb26e8e3bad1722a6730ddc054782
- 1.12.2 net/minecraft/block/ObsidianBlock.java: a4befae37e458e04a7c70d48471482c0703c2e9abeb06c31974e56fbc9de2fe8
- 1.12.2 net/minecraft/block/OreBlock.java: 618d5378fe6cb53d196eb556f55dc529ee5d5657c6a253cf72b721be24324327
- 1.12.2 net/minecraft/block/PackedIceBlock.java: a396a37d65103a8fbd762ec0abb8292cc69dc58c95707ac764571bb0393cc7f9
- 1.12.2 net/minecraft/block/PrismarineBlock.java: 15497a7a9635491918b03c05adbb613814c2dc2c60dda8c2a680a01fa9f31ab1
- 1.12.2 net/minecraft/block/PumpkinBlock.java: 732365c7ba81c3301cf164ab749bf52b5ce540a7b3a84ad50798db465ab77c9d
- 1.12.2 net/minecraft/block/QuartzBlock.java: a2eeae521b4ded7cfaf0d15f204542c46be0b1d76850813303588a17f7101793
- 1.12.2 net/minecraft/block/RedSandstoneBlock.java: 4f55d2328198d232d8b4d33d95f4d0211bc69ee8decdef5d58170c63e4187406
- 1.12.2 net/minecraft/block/RedstoneBlock.java: 5a4c06d66a84a24cd6cd1af00b1de411f980349fb574cde43cfe6509ca7bc12a
- 1.12.2 net/minecraft/block/RedstoneLampBlock.java: 4ff00416209a09cb6bdf1a302d090cfffef6fc1091c528742cfdef6a6d98ac1c
- 1.12.2 net/minecraft/block/RedstoneOreBlock.java: 5d200f042a07ad928f1f38b9d7464a1a019f53ca7e63b9f23036dc71f8939ecd
- 1.12.2 net/minecraft/block/SandBlock.java: 34718db8f1c2b952312bcc81d1f8205ba63e38b7c43ba7fa19b0c68e283ca1c8
- 1.12.2 net/minecraft/block/SandstoneBlock.java: 0ea5eb90ef6af8d522fbd3bcbca3d9e785f0330a297b997b837be067a5e37aa5
- 1.12.2 net/minecraft/block/SeaLanternBlock.java: 4e59c6f7f1bbeeb827b811d3be7b3b5662779eb6f437b2583927382d1bc37264
- 1.12.2 net/minecraft/block/SpongeBlock.java: ebcba78fe0bb075859c3fa516775927ce385238d62aad9bfc8ab13b3e2cfddfd
- 1.12.2 net/minecraft/block/StoneBlock.java: 49358cc69e314af3952211266696dd45b48a98eb37dacf93e32c59fdff758d22
- 1.12.2 net/minecraft/block/StructureBlock.java: 0cd13417e2a7036ab8e0feb7f6eb211c357a05a38eba591012a5d50a35f37ba4
- 1.12.2 net/minecraft/block/TransparentBlock.java: fee15d971d8d00a905655e4b47e48e6a226505094d03227c849110b1407e5108
- 1.13.2 net/minecraft/block/AxisBlock.java: 6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4
- 1.13.2 net/minecraft/block/BarrierBlock.java: 4cc8dc003dc1484994082fdd383e7d5cd0b300cc0b42b3b34f0462c2b3b58eb6
- 1.13.2 net/minecraft/block/BeaconBlock.java: c8489ab810e21c2efd95e24acf8eb40e120e8d4f78248b886c65a87d901efdaf
- 1.13.2 net/minecraft/block/BedrockBlock.java: 36071246d817e9334a75574d95b513c32ed7812341495d89dffeba5f6ea10133
- 1.13.2 net/minecraft/block/Block.java: 735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4
- 1.13.2 net/minecraft/block/BookshelfBlock.java: 2522570fc04f4af776f32a806cdf978ee8f488e55c94d94d515d83add38d7517
- 1.13.2 net/minecraft/block/ChorusFlowerBlock.java: cacbbd84e9be8912d8e79d6d90cafd72a70b81d3e96518b8f0eb2eaf65fe19c4
- 1.13.2 net/minecraft/block/ClayBlock.java: 9cfa1a299e79781c2c010290b0122f5ad01e06d78aa76fdc916204a28c75ffd0
- 1.13.2 net/minecraft/block/CommandBlock.java: a6842dac2a1174662b7250b891cd7dd6812af58e9d68f296653cb0105c641232
- 1.13.2 net/minecraft/block/CraftingTableBlock.java: 67d68a1efdc53188613e2627fb3cdd353668df752effac7efbb7a0a330e97aa3
- 1.13.2 net/minecraft/block/DispenserBlock.java: 2841b2ad7aef0801d2dd2b242997e874c5581c726f694c9e8a4eb02971da0e6e
- 1.13.2 net/minecraft/block/DropperBlock.java: 5067742bba5e16c79a5004fed1b97e374b6e312f626bd3daedebc81ea1cc9a3a
- 1.13.2 net/minecraft/block/FrostedIceBlock.java: 096940541255ab371d6f197fbf22f30b99da48ff1bde8be5c3be15ba00a299be
- 1.13.2 net/minecraft/block/FurnaceBlock.java: 6e946bbdf3fcbcd74146c08ec6f1d1d044ffaa471f4418b38098f9acb9938542
- 1.13.2 net/minecraft/block/GlassBlock.java: 5faf248e82edb63d4ed19766bff9e328d28b5adf8c6bb6447f23059ff45082ac
- 1.13.2 net/minecraft/block/GlazedTerracottaBlock.java: b1cf8730569665444e87746d071147ec04bec547acf2e5ad19b8b5aa5c6bfc6a
- 1.13.2 net/minecraft/block/GlowstoneBlock.java: c03d8d8ff45e0a3b945ce52255ea58087da4a7c379031a50e96daa75c273199e
- 1.13.2 net/minecraft/block/GravelBlock.java: d9cea103365ce3184bdd021c32fb3a7a43058a839c46192b7c5d416592d64c73
- 1.13.2 net/minecraft/block/HayBlock.java: a69ddb3554c2453c8d04ddeec14a1e0feed001592e49384a710f630bac0c7a64
- 1.13.2 net/minecraft/block/IceBlock.java: 8c0a9bed03f026be289f183114c6bc71b7e321d6ab4e915921cd2f51e3ce0cec
- 1.13.2 net/minecraft/block/JukeboxBlock.java: 01374fa0573e3baf830b570b3b99bf38b179fc989cb640bb794169f819fca1c1
- 1.13.2 net/minecraft/block/MushroomBlock.java: 5f52979e5571b2ee4a784fce69110528617f18a5222c66e95d1d0b3b5b52e33b
- 1.13.2 net/minecraft/block/MyceliumBlock.java: b0c1b38d9b394cb04f94dd961ecd57829b602d448e30e7ac2ca1e461dbd95dcc
- 1.13.2 net/minecraft/block/ObserverBlock.java: 787d9c0ecbb16e0e5543299a214e038c64861a237d512aa0b509a7ed4d353e6a
- 1.13.2 net/minecraft/block/OreBlock.java: e9e6b3266604d8436d0afe6a9d1eab08e060d5e9ded3f3f213f9694d4f769786
- 1.13.2 net/minecraft/block/PackedIceBlock.java: b533ff3dfd50ec74a2b518a2a23b7c576accc2cdba462f4ea70953c85d1c92a4
- 1.13.2 net/minecraft/block/PumpkinBlock.java: 3cdfac9817a2ad46014223c28814cd8f8b5ed07ba08051915b1ff7972324973a
- 1.13.2 net/minecraft/block/RedstoneBlock.java: 7b7284efaae2873529bc9da2a5e817716c0bcd83eb45f1333cc5ad77bf0bc9e0
- 1.13.2 net/minecraft/block/RedstoneLampBlock.java: afca31b3cc29135d5396bb6fc12fa255226d5025e3b5a7c95cf58037a2a6b57a
- 1.13.2 net/minecraft/block/RedstoneOreBlock.java: 0d93a4c0847926c35c7cccf1cfceb435b9a55c7f46287105d1391ceb81f7f64c
- 1.13.2 net/minecraft/block/SandBlock.java: 62833fc411f7fc8b1f5be6dd9515899f1cb0147edf4eeed3a3b1e35762ab9fe6
- 1.13.2 net/minecraft/block/SeaLanternBlock.java: eacaddd89ceaa65525bfc35c4e344a6bce6f38a10f050e7c73f1cec1f441791a
- 1.13.2 net/minecraft/block/SpongeBlock.java: 924211cbd821f0ff205bf0fc8a02d4071841f31494b271df75477476e1238a18
- 1.13.2 net/minecraft/block/StoneBlock.java: 4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906
- 1.13.2 net/minecraft/block/StructureBlock.java: 2d0c67f5993a5dbe3fac3ffe1268e6ac99328537bef596554d39fbf72ddf141c
- 1.13.2 net/minecraft/block/TntBlock.java: 2acf48c3575f434324af23053816855e8e34e21119653c304b438af0ae03a305

## Exact limits and open dependencies

- Excludes keys whose old or new effective provider is specialized, unresolved registry aliases, and registrations not in the 76-row crosswalk filter.
- Does not compare old/new world candidate enumeration, shape intersection predicates, clipping order or displacement. Separate collision-consumer/math evidence remains necessary for movement-path conclusions.
- No movement trajectory or pair-level coverage/freeze claim. Remaining specialized provider/state/context roster and full pair gates remain open as tracked by the existing ledger; this note does not alter them.


