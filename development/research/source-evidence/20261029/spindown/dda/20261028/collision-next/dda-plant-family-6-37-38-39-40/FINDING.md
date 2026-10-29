# 1.8.9 sapling, flower, and mushroom source closure

## Exact inputs

- Older source: Minecraft `1.8.9`, `ornithe-feather` mapping output at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`; provenance identifies `feather-gen2-1.8.9+build.2-mergedv2.jar` and `feather-gen2-1.8.9+build.2.tiny`. The ready marker reports the exact version and mapping, 1,612 files, and source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- Newer source: Minecraft `26.2`, `unobfuscated` mapping output at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`. The ready marker reports exact version/mapping, 7,055 files, and source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- The selected 9 older and 7 newer files are listed in `source-hashes.json`. Each actual SHA-256 was recomputed and matched its canonical source manifest row. The JSON file SHA-256 is `5c938fc276f0acf89533ecb5cfed0b9893d8b51a16c5ffa6a526b2304bd55b68`.

## Registry identity and state decoding

In old `net/minecraft/block/Block.java`, registrations at lines 839 and 890–895 bind ID 6 to `sapling`, 37 to `yellow_flower`, 38 to `red_flower`, 39 to `brown_mushroom`, and 40 to `red_mushroom`. `net/minecraft/block/Blocks.java` lines 221 and 254–257 expose those same names.

- ID 6 (`SaplingBlock`): old metadata decodes type from `metadata & 7` and stage from `(metadata & 8) >> 3` (`SaplingBlock.java:193–194`). `PlanksBlock.Variant` maps 0 oak, 1 spruce, 2 birch, 3 jungle, 4 acacia, 5 dark oak (`PlanksBlock.java:55–61`); invalid variant IDs fall back to oak (`:92–98`). In 26.2, those six types are separate `oak_sapling`, `spruce_sapling`, `birch_sapling`, `jungle_sapling`, `acacia_sapling`, and `dark_oak_sapling` registrations, each using `SaplingBlock` and a `STAGE` property. This is a species-to-registry-key conversion; the legacy `TYPE` property no longer exists on each modern state. Current extra species (for example cherry and pale oak) have no legacy ID-6 counterpart.
- ID 37 (`YellowFlowerBlock`): the yellow group contains only `DANDELION` at metadata 0. Other metadata values fall back to group index 0 (`FlowerBlock.java:41–42, 84–85, 120–126`). Current `dandelion` is a separate `FlowerBlock` registration.
- ID 38 (`RedFlowerBlock`): red metadata 0–8 maps in order to poppy, blue orchid, allium, houstonia, red tulip, orange tulip, white tulip, pink tulip, and oxeye daisy (`FlowerBlock.java:84–93`). The modern keys are `poppy`, `blue_orchid`, `allium`, `azure_bluet`, `red_tulip`, `orange_tulip`, `white_tulip`, `pink_tulip`, and `oxeye_daisy`. The source-backed rename is old `houstonia` → modern `azure_bluet`; the other mappings retain their names. The old enum falls back to poppy for a metadata value outside the red group's 0–8 range (`FlowerBlock.java:120–126`).
- IDs 39 and 40 are distinct old `MushroomPlantBlock` instances registered as `brown_mushroom` and `red_mushroom`; they have no variant metadata property. They map to modern `brown_mushroom` and `red_mushroom`, each a `MushroomBlock` registration.

Modern registry-key evidence is in `net/minecraft/references/BlockItemIds.java`: sapling aliases are declared at lines 85–91, flowers at 209–221, and mushrooms at 228–229, then used by the corresponding `Blocks.java` registrations. The source-hash manifest includes both files.

## Movement collision provider

All five old block IDs inherit `PlantBlock.getCollisionShape`, which returns `null` (`net/minecraft/block/PlantBlock.java:61–64`). `Block.addCollisions` adds a box only when that result is non-null and intersects the query (`Block.java:340–346`). `SaplingBlock`, `FlowerBlock`, and `MushroomPlantBlock` all inherit that null provider; no class in this bounded family overrides it. Therefore these five legacy IDs contribute no movement collision boxes.

The corresponding 26.2 registrations use `BlockBehaviour.Properties.noCollision()` (`net/minecraft/world/level/block/Blocks.java:187–258, 860–986, 1025–1043`). `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when `hasCollision` is false (`net/minecraft/world/level/block/state/BlockBehaviour.java:327–328`), and `Properties.noCollision()` sets that flag false (`:985–986, 1094–1097`). For these mapped states, the old null movement provider and modern empty collision shape agree on contributing no movement collider.

## Separate outline/ray provider facts

The old `Block.rayTrace` calls `updateShape(world,pos)` and intersects the block instance's mutable min/max fields (`net/minecraft/block/Block.java:456–461`); base `updateShape` is a no-op (`:596–597`). Old `PlantBlock` initializes a 0.4-wide, 0.6-high box (`PlantBlock.java:23–27`); `SaplingBlock` replaces it with a 0.8-wide, 0.8-high box (`SaplingBlock.java:27–32`); `MushroomPlantBlock` uses a 0.4-wide, 0.4-high box (`MushroomPlantBlock.java:10–14`). These ray/outline bounds exist independently from the null movement-collision provider.

Modern shapes also remain available even where collision is disabled: sapling uses a centered 12/16-wide, 12/16-high column (`SaplingBlock.java:25, 41`); flower uses 6/16 width and 10/16 height and applies `state.getOffset(pos)` (`FlowerBlock.java:24, 46–47`); mushroom uses 6/16 width and 6/16 height (`MushroomBlock.java:29, 44`). Current flower registrations select `OffsetType.XZ` (`Blocks.java:860–986`), whereas old base `Block.updateShape` does not apply a position offset before `rayTrace` reads its bounds. These are provider/shape facts only. The report does not claim old-vs-modern DDA visitation, callback order, first-hit parity, nor any end-to-end ray result.

## Bounded result

The old-to-current names/state decode and movement-collision providers are closed for IDs 6, 37, 38, 39, and 40. For movement collision they are a no-collider family, despite nonempty old and current ray/outline bounds. This report is not a DDA traversal proof and says nothing about runtime applicability or unrelated plant IDs.
