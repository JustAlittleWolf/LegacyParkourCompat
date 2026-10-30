# 1.8.1–1.8.2 movement-provider registry join

## Scope and source identity

This source-only join covers the supplied collision-provider census and residual callback roles: block singleton aliases, numeric registrations, runtime classes, inherited provider classes, and constructor/registry default states needed to identify the provider for a vanilla map state. It does not inspect product implementations, wiki material, or Git history, and it does not claim full-pair coverage.

Read-only exact mapped roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.1\ornithe-feather` and `...\1.8.2\ornithe-feather`, Feather gen2 `1.8.1+build.2` / `1.8.2+build.2`. Client-jar SHA-256: `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` / `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`. Mapping-jar SHA-256: `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` / `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`. Both use the `lpc-decompile-v1` pipeline; provenance manifest SHA-256: `F3A3BAA039F0E0ADE6E656DFA11A4529909A4B709AC489703C4250262CEFB1CA` / `8AD11EA36F2DDA39AE30D57DAA53316A8C0889A2F3C0C84EE5D79B9B21C25472`.

All block-provider/registry source paths in the supplied collision inventory, plus four direct slipperiness/liquid sources, were rehashed on disk (56 paired paths total); all matched their provenance `sources` entries. `Block.java` and `Blocks.java` also matched the SHA-256 values in the supplied inventory. Additional read-source hashes are listed below.

## Paired registry result

`Blocks.java:215–412` has the same 198 static field-to-registry-key assignments on both sides, including the same casts and `getOrThrow` lookups; parsed field/key pairs have zero differences. These fields are aliases resolved after `Bootstrap`, not block constructors. `Block.java` registers numeric IDs 0–197; the 197 parsed `register(id, key, instance)` expressions for IDs 1–197 have zero normalized key/class differences, and ID 0 is `air`/`AirBlock` on both sides. Movement-provider registrations preserve their class and relevant constructor arguments. Feather changes flattened/nested names for inner types and renamed local variables, but the registered singleton identity is unchanged.

The joined provider registrations (ID : registry key → registered class or inherited provider role) are:

| Provider role | Registered map-state identities |
|---|---|
| Air and base `Block` fallback | `0 air → AirBlock`; base `Block` constructor instances: `4 cobblestone`, `7 bedrock`, `22 lapis_block`, `41 gold_block`, `42 iron_block`, `45 brick_block`, `48 mossy_cobblestone`, `57 diamond_block`, `121 end_stone`, `133 emerald_block`, `152 redstone_block`, `173 coal_block`. Other registered subclasses retain the base full-cube/default collision fallback unless their provider overrides it. |
| Abstract pressure plate / button roles | `70 stone_pressure_plate`, `72 wooden_pressure_plate`, `147 light_weighted_pressure_plate`, `148 heavy_weighted_pressure_plate` → `PressurePlateBlock` / `WeightedPressurePlateBlock` inheriting `AbstractPressurePlateBlock`; `77 stone_button`, `143 wooden_button` → `StoneButtonBlock` / `WoodenButtonBlock` inheriting `ButtonBlock`. |
| Abstract rail role | `27 golden_rail`, `28 detector_rail`, `66 rail`, `157 activator_rail` → `PoweredRailBlock`, `DetectorRailBlock`, `RailBlock`, `PoweredRailBlock` inheriting `AbstractRailBlock`. |
| Plant provider role | `31 tallgrass`, `32 deadbush`, `37 yellow_flower`, `38 red_flower`, `39 brown_mushroom`, `40 red_mushroom`, `115 nether_wart` → registered plant/flower subclasses inheriting `PlantBlock` (yellow/red flowers through `FlowerBlock`); `83 reeds → SugarCaneBlock` is a separately censused role. |
| Banner, sign, skull | `176 standing_banner`, `177 wall_banner` → standing/wall banner subclasses; `63 standing_sign`, `68 wall_sign` → standing/wall sign subclasses; `144 skull → SkullBlock`. |
| Fluid and flow | `8 flowing_water`, `9 water`, `10 flowing_lava`, `11 lava` → `FlowingLiquidBlock` / `LiquidSourceBlock`, constructed with `Material.WATER` for IDs 8–9 and `Material.LAVA` for IDs 10–11. `Blocks.FLOWING_WATER`, `WATER`, `FLOWING_LAVA`, and `LAVA` resolve to those same keys. |
| Piston | `29 sticky_piston`, `33 piston` → `PistonBaseBlock(true/false)`; `34 piston_head → PistonHeadBlock`; `36 piston_extension → MovingBlock` (the moving-block-entity role remains tied to this moving block). |
| Fence, pane, wall, gate | `85 fence`, `113 nether_brick_fence`, `188–192 spruce/birch/jungle/dark_oak/acacia_fence` → `FenceBlock`; `101 iron_bars`, `102 glass_pane`, `160 stained_glass_pane` → `PaneBlock` / `StainedGlassPaneBlock` inheriting it; `139 cobblestone_wall → WallBlock`; `107 fence_gate`, `183–187 wood fence gates` → `FenceGateBlock`. |
| Door and trapdoor | `64 wooden_door`, `71 iron_door`, `193–197 wood doors` → `DoorBlock`; `96 trapdoor`, `167 iron_trapdoor` → `TrapdoorBlock`. |
| Stairs | `53 oak_stairs`, `67 stone_stairs`, `108 brick_stairs`, `109 stone_brick_stairs`, `114 nether_brick_stairs`, `128 sandstone_stairs`, `134–136 spruce/birch/jungle_stairs`, `156 quartz_stairs`, `163–164 acacia/dark_oak_stairs`, `180 red_sandstone_stairs` → `StairsBlock`. |
| Slabs | `43 double_stone_slab`, `44 stone_slab`, `125 double_wooden_slab`, `126 wooden_slab`, `181 double_stone_slab2`, `182 stone_slab2` → paired double/single stone, wood, and red-sandstone slab classes. |
| Remaining directly registered collision/contact providers | `30 web → CobwebBlock`; `46 tnt → TntBlock`; `50 torch → TorchBlock`; `51 fire → FireBlock`; `55 redstone_wire → RedstoneWireBlock`; `60 farmland → FarmlandBlock`; `65 ladder → LadderBlock`; `69 lever → LeverBlock`; `73–74 redstone_ore/lit_redstone_ore → RedstoneOreBlock`; `75–76 unlit/lit redstone_torch → RedstoneTorchBlock` inheriting `TorchBlock`; `78 snow_layer → SnowLayerBlock`; `79 ice → IceBlock`; `81 cactus → CactusBlock`; `88 soul_sand → SoulSandBlock`; `90 portal → PortalBlock`; `92 cake → CakeBlock`; `111 waterlily → LilyPadBlock`; `117 brewing_stand → BrewingStandBlock`; `118 cauldron → CauldronBlock`; `119 end_portal → EndPortalBlock`; `120 end_portal_frame → EndPortalFrameBlock`; `127 cocoa → CocoaBlock`; `131 tripwire_hook → TripwireHookBlock`; `132 tripwire → TripwireBlock`; `154 hopper → HopperBlock`; `165 slime → SlimeBlock`; `174 packed_ice → PackedIceBlock`. |

Other default/full-cube providers in the supplied census resolve through the same registry map and inherit the base behavior or the already-covered provider class. This table is limited to movement-relevant roles; unrelated registry entries are not enumerated.

## State defaults and direct movement constants

`Block` initializes its default state with `stateDefinition.any()` (`Block.java:166` / `:206`) and base slipperiness `0.6F` (`:73` / `:113`). Explicit constructor defaults were compared across the 67 paired provider/related source files examined; the only textual differences in the extracted default-state expressions are three local names in the slab constructors and the flattened-versus-nested banner classes. Their values and ordered property sets match. 1.8.1 banner subclasses are separate `BannerBlock__Standing` / `BannerBlock__Wall` sources; 1.8.2 nests them in `BannerBlock`.

Shape- or movement-relevant defaults and registry-provided base states are unchanged:

- Rails begin at `SHAPE=NORTH_SOUTH`; powered/detector rails add `POWERED=false`. Pressure plates begin unpowered. Pistons begin `FACING=NORTH, EXTENDED=false`; piston heads begin north/default/not-short; the moving block begins north/default.
- Stairs begin north, bottom, straight. Their registered base states retain the matching stone/cobble, brick, stone-brick `DEFAULT`, nether-brick, sandstone `SMOOTH`, planks variants (oak/spruce/birch/jungle/acacia/dark oak), quartz `DEFAULT`, and red-sandstone `SMOOTH` inputs on both sides.
- Single slabs begin bottom with their matching stone/oak/red-sandstone variant; double slabs begin with `SEAMLESS=false` and the same corresponding variant. The single/double instance class remains what selects half versus full-block shape.
- Fence and pane connection flags begin false; wall connections begin false with `UP=false` and `VARIANT=NORMAL`; fence gates begin closed, unpowered, and not in-wall. Doors begin north, closed, left-hinged, unpowered, lower-half; trapdoors begin north, closed, bottom-half.
- Button defaults are north/unpowered; ladder north; lever north/unpowered; hopper down/enabled. End-portal frame begins north/no-eye. Portal axis begins X. These defaults are the same alongside their registry IDs.
- Liquid state starts at `LEVEL=0` in the shared `LiquidBlock`; flow/source singleton constructors retain the WATER/LAVA material split above. Lily-pad source-water predicate still keys off `Material.WATER` and liquid level 0. The movement material constants are unchanged.
- `IceBlock` and `PackedIceBlock` set slipperiness to `0.98F`; `SlimeBlock` sets `0.8F`; the base is `0.6F`. Their IDs remain 79, 174, 165 respectively.
- Other stateful providers use matching constructor defaults: brewing-stand bottle flags false; cactus age 0; cake bites 0; cauldron level 0; cocoa north/age 0; farmland moisture 0; fire age 0 with all flags/side bits false and upper 0; redstone-wire side connections NONE/power 0; snow layers 1; sugar-cane age 0; TNT explode false; tripwire/hook flags false; vine faces false; and standing/wall banners rotation 0/north. These are state production defaults, distinct from the registered singleton's class identity.

`Block.java` registration state inputs were checked for the full stair family above. No ID/key/class change or state-default difference was found. This closes the registry dependency for the supplied movement-provider census; it does not cover later world-driven state changes, broader player tick/travel, or the independent full-pair audit.

## Additional source hashes checked against manifests

Each hash below is the on-disk SHA-256 for the given mapped source in 1.8.1 / 1.8.2 and matched that version's provenance `sources` entry. For the flattened banner rows, the 1.8.2 hash is the enclosing `BannerBlock.java` source (which contains the nested class); that file is also in the supplied collision inventory.

| 1.8.1 source path | 1.8.1 SHA-256 | 1.8.2 paired path | 1.8.2 SHA-256 |
|---|---|---|---|
| `net/minecraft/block/IceBlock.java` | `052befdbb48019def3516d1de4c2652cd9efa79266edf29fa01032098e8c6b45` | same | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` |
| `net/minecraft/block/PackedIceBlock.java` | `a396a37d65103a8fbd762ec0abb8292cc69dc58c95707ac764571bb0393cc7f9` | same | `a396a37d65103a8fbd762ec0abb8292cc69dc58c95707ac764571bb0393cc7f9` |
| `net/minecraft/block/FlowingLiquidBlock.java` | `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c` | same | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` |
| `net/minecraft/block/LiquidSourceBlock.java` | `e4ba29eb92a7c777fa294c2af278d5ea6b440681b39785a38dc9d30918995e39` | same | `2b263d3dad30022c81c53d7682791447e4c8a22fa22c01f4e3c1ce84f8c49568` |
| `net/minecraft/block/SingleStoneSlabBlock.java` | `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98` | same | `3b3808a68671d1f559f20ac6cc71a367223d50e9c646dbf470f2da3a503cca98` |
| `net/minecraft/block/DoubleStoneSlabBlock.java` | `134f7fafbbc635b8367f1fbf0f0375e0eb95a387bc1d8667ef3c9160e899121f` | same | `134f7fafbbc635b8367f1fbf0f0375e0eb95a387bc1d8667ef3c9160e899121f` |
| `net/minecraft/block/SingleWoodenSlabBlock.java` | `367dbdd8ff807d45734d4074ac236dcdd8f7ceb75324eb4e9dad0ac56b11d4d9` | same | `367dbdd8ff807d45734d4074ac236dcdd8f7ceb75324eb4e9dad0ac56b11d4d9` |
| `net/minecraft/block/DoubleWoodenSlabBlock.java` | `7e00e9517411d8e8a6ce48a79203ece8bbb64e10a9d1070c597a55efb466e21a` | same | `7e00e9517411d8e8a6ce48a79203ece8bbb64e10a9d1070c597a55efb466e21a` |
| `net/minecraft/block/SingleRedSandstoneSlabBlock.java` | `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a` | same | `80782d5bb0d60bc09e895efa4f09be5b321764f9e9f2dd7d2ad68ab192bb3b8a` |
| `net/minecraft/block/DoubleRedSandstoneSlabBlock.java` | `71df9e432c231b3c6d0892a58957bbad1b2ece2daa3526a1d330b9944824b203` | same | `71df9e432c231b3c6d0892a58957bbad1b2ece2daa3526a1d330b9944824b203` |
| `net/minecraft/block/StoneSlabBlock.java` | `fa1dbb8f9717934693abd4b5c5c3b8cb0bbf215e85b3b6e3ca8dcf541bba4058` | same | `ce79650021c852ba1e70b3f9e5254cf00597a929154a7f10459db2e37d49fd76` |
| `net/minecraft/block/WoodenSlabBlock.java` | `8bf59e830c0bec20cf1ba2afa04babddf2fc87fe7fced02bdc33ce06a92bd683` | same | `b4788b162e779dd285d1fa7c41781716fec6b43989f9693da2d4134064673cd3` |
| `net/minecraft/block/RedSandstoneSlabBlock.java` | `030873712183e363b1aae74afd2863ab5fcaa7cac541910070943cbb04eeeb77` | same | `07d59a26fc337cfb2533c9cf4b8f9cba0d3adaaac5ca96a50b6845f6c1845a64` |
| `net/minecraft/block/PoweredRailBlock.java` | `ec18d95caba02d9da1b99aa69361fa1b2713fb99572f3ab1eb24cdb57167d072` | same | `129b4739c4d7f06660b53fff11a45af9cc494d4cadf69d7026a967bcf7be32c2` |
| `net/minecraft/block/PressurePlateBlock.java` | `ea9c981867ec07eab01649bf402d6d3da6b5e4398d2b03238cbc54798781b979` | same | `2a78b32351f803b1f48164dde6270cd96f60acfecebebb146fe6358ba7d3e84a` |
| `net/minecraft/block/BannerBlock__Standing.java` | `a01950b182a9bdf611f8013b89b0de33879b9c18830e7fe842f854e5e7975a21` | `BannerBlock.java` (nested `Standing`) | `6918850c398314d113f9e7d21e361d19c9af1fdb74977465ca3223fc3f52dbc4` |
| `net/minecraft/block/BannerBlock__Wall.java` | `a5a8aa38311e14d811866d32e0702105491176b1954d5a13aa8ca5ffdf64b261` | `BannerBlock.java` (nested `Wall`) | `6918850c398314d113f9e7d21e361d19c9af1fdb74977465ca3223fc3f52dbc4` |
| `net/minecraft/block/WeightedPressurePlateBlock.java` | `f2b2bf85c58e6e9efc0672ad64cda1d1a8884ac55e32e722736371a287575bfe` | same | `9929fd85ba07b85051ed54a7909f4be8ea9fbfa9957cc9a820757a618ed0d927` |
| `net/minecraft/block/RailBlock.java` | `682d269a53a421ccdb3c095f6b68d6e1df4cc18f409980efc8a815290b0011be` | same | `2cb1bd437d0b0893d310d1e371ee3df0075d2a2aafaa1a612010c523bfa339aa` |
| `net/minecraft/block/StoneButtonBlock.java` | `8cdcecae0e048a11cba3b65640635ab51b82cb3e02a685b06573e33f4a4207bc` | same | `8cdcecae0e048a11cba3b65640635ab51b82cb3e02a685b06573e33f4a4207bc` |
| `net/minecraft/block/WoodenButtonBlock.java` | `82c8b2969aaedf150b911a004aa38583a7c6241349d38c0668cc2d76e4dd9a84` | same | `82c8b2969aaedf150b911a004aa38583a7c6241349d38c0668cc2d76e4dd9a84` |
| `net/minecraft/block/StandingSignBlock.java` | `741888b079e1ab5712493c985935c5e9b66adef7eccf5398b8779387b6214ccd` | same | `741888b079e1ab5712493c985935c5e9b66adef7eccf5398b8779387b6214ccd` |
| `net/minecraft/block/WallSignBlock.java` | `0855ecda2ac491e2011e0b7c297865fb51aba7e2bbae9c84a4f3b43e627f5842` | same | `5fa003c5ca40bfa2c901e35f8fa21fde096264038c8150780a2e49f8f24e97b2` |
| `net/minecraft/block/TallPlantBlock.java` | `9b7b9d635783cca7aee8e0f501291e29935fda2dd905cdb15c08acf6b58f6971` | same | `0b78945c19044a9920482b7d6b1a4518a45198295f351d6bdd0c5aaa7367fa41` |
| `net/minecraft/block/DeadBushBlock.java` | `4e6425539e0d779339202d2b32d17e224e960c452280ccc9f02cae6cceb19042` | same | `118f60c5fbba71c9969dcda3b03f84becdcaa745b2e99f8b46447494362fb8a1` |
| `net/minecraft/block/YellowFlowerBlock.java` | `50b332456feae7ea609a3d5e8708346fdc4d8f13d130e9e21d687d412ab2c9dd` | same | `0d556d22979c039a890473893e4939c13c6090b29cbe1802562414efde8edcc5` |
| `net/minecraft/block/RedFlowerBlock.java` | `41441970f3d479f1b3e0a40795744d30b5c99a00880e0ddb9c1a998731df6fc7` | same | `66ca1f5e5b254f4536ec76117eb482e6ff61d23fc818406b1093f9bc63d8c14c` |
| `net/minecraft/block/MushroomPlantBlock.java` | `854bc72d379fd4c0d6051d57087e61a36d4f8c6b3319c35144a578713844e7f3` | same | `a751825f879b12b9a9d4450b581cd80abb1c16cd7f8c48ea3acbd958092e103a` |
| `net/minecraft/block/NetherWartBlock.java` | `a60e489e4d24a27953c9d4c249db5fb2d99214a8198f57a1dc19753d188ac89c` | same | `fb9dc0cbb968a696c052b9d138e4061ba34109ae7dd65ed676802e1809469fef` |
| `net/minecraft/block/RedstoneTorchBlock.java` | `915830b07c154ddf9fe351cf259f8d64ec0626732464afb686e3440be039e4af` | same | `e85ac1cd0988ea3657c32623206c72b8131fa30e3dde275e8b9e1610cdbafa70` |
| `net/minecraft/block/FlowerBlock.java` | `f07f8bc23db5e7e9bff8969b19be56984985f408c6551ae93bd2497ba60d3934` | same | `4139a2fc5c274704d8cb861269bc2c62027ffb75f4e17b4aea85cb77a754ccf4` |
| `net/minecraft/block/StainedGlassPaneBlock.java` | `73b066b36463aad13c48354d6dcce59a2f407e6f351718c2f59868b931cf9ba2` | same | `08f8a8d122b7d4cfc2825565e13f47eb36a66b7bdefa7a664a8b769b25b9cf56` |

The preceding hashes are direct on-disk values checked against each manifest; the table is an evidence inventory, not a behavioral-difference list.
