# B-side navigation index: Minecraft 1.19.4 Mojmap

This is a B-side inventory only. It does not establish any change or no-change relative to 1.18.2. Paths are rooted at `decompiled_minecraft/1.19.4/mojmap/`. Hashes are recorded in `run.md` and must be checked again for any newly cited file.

## Ordered class and member anchors observed

### 1. Local input and tick ordering

- `net.minecraft.client.player.LocalPlayer` (`extends AbstractClientPlayer`): `tick()` line 187; `aiStep()` line 645; `move(MoverType, Vec3)` line 880.
- `net.minecraft.client.player.KeyboardInput` (`extends Input`): `tick(boolean, float)` line 21. Resolve the superclass and key mapping/options path when indexing the paired slice.
- Candidate B chain to inspect: `KeyboardInput.tick -> LocalPlayer.tick/aiStep -> Player.aiStep -> LivingEntity.aiStep/travel`. Exact order and guards remain to be indexed from enclosing methods.

### 2. Player-specific state and gates

- `net.minecraft.world.entity.player.Player` (`extends LivingEntity`): `tick()` line 227; `aiStep()` line 499; `jumpFromGround()` override line 1429; `travel(Vec3)` override line 1440.
- `LocalPlayer` owns client-only input and local-player actions; inspect its constructor/default state, crouch/using-item/sprint gates, vehicle handling, and updates to previous/current input.
- Follow `LivingEntity` pose, dimensions, abilities, food and active-item state through direct consumers rather than treating `Player` as the whole state owner.

### 3. Living movement integration

- `net.minecraft.world.entity.LivingEntity`: `jumpFromGround()` line 1955; `travel(Vec3)` line 1983; `aiStep()` line 2454; travel dispatch at `aiStep()` line 2550.
- Within `travel`, observed anchors include fluid handling around 1992, `moveRelative` calls at 2015 and 2030, and ground/air travel helper at 2153. Open the full method and direct helpers, including attributes and branch guards, in bounded slices.
- Effect application/removal path anchors: `LivingEntity` calls effect attribute modifier add methods at lines 991 and 1000. Trace `MobEffects`, attribute definitions, and modifier operations.

### 4. Entity movement and collision

- `net.minecraft.world.entity.Entity`: `makeBoundingBox()` line 383; `tick()` line 405; `move(MoverType, Vec3)` line 549; collision solver at 801; axis collision calls around 852–874; fluid push at 2887; relative acceleration helper `moveRelative` at 1181.
- Movement callbacks appear in `move`: `updateEntityAfterFallOn` at line 610, `stepOn` at 614; fall path includes `fallOn` at 1005. Fluid eye/update paths begin around 431 and 1096.
- Geometry sources: `world/phys/AABB.java`, `world/phys/shapes/VoxelShape.java` (collision helper `collide` line 205), and `Shapes`. Confirm axis ordering, candidate ordering and tie-breaks in paired closures.

### 5. Blocks and fluids

- `world/level/material/FlowingFluid.java`: `getFlow` begins at line 53; inspect `Fluid`, `FluidState`, `LiquidBlock`, and player fluid consumers.
- `world/level/block/Blocks.java` contains base registrations. Relevant class seeds found: `SlimeBlock` (fall and landing callbacks lines 18/27; step callback line 44), `SoulSandBlock` (scheduled tick line 41; inspect shape/slowdown superclass paths), plus honey, cobweb, ice, bed, bubble-column, piston, ladder/vine, powder-snow and partial-block classes.
- Inventory all subclasses/overrides and property assignments; these anchors are not a complete registration audit.

### 6. Effects, enchantments, attributes and equipment

- `world/effect/MobEffects.java`: movement speed/slowdown registrations around lines 14–24; jump boost line 46; blindness line 53; levitation line 73; slow falling line 86; dolphin's grace line 88. Follow each to its player movement consumer and effect behavior.
- `world/item/enchantment/Enchantments.java`: Depth Strider line 27, Frost Walker 28, Soul Speed 30, Swift Sneak 31, Riptide 61. `EnchantmentHelper` line 33 class entry; inspect registered implementations, equipment-slot applicability, helper formulas and block/tag conditions.
- Resource defaults/tags are inside the original `client.jar` but the decompiler output omits them. Inspect and hash exact entries when needed. Server-provided datapacks/tags or synchronized attributes require a documented boundary.

### 7. External influences and dependency closure

- `client/multiplayer/ClientPacketListener`: `handleSetEntityMotion` line 499 and `handleMovePlayer` line 605 are initial incoming motion/position correction anchors. Resolve packet fields and the local-player write path.
- Inspect push/knockback/explosion/piston and mount transitions only where they update local-player position, velocity or movement flags. Separate local computation from externally supplied packet state.

## Current dependency queue

1. Confirm every B-side class/member against the exact A class chain after reading the A manifest; names are candidates, not proof of correspondence.
2. For each paired slice, record method signatures, callers, state reads/writes, line spans and paired source hashes.
3. Inspect resource/tag entries and hash the exact ZIP entry bytes when a movement consumer relies on them. Identify external server/datapack values rather than assuming vanilla client defaults.
4. Review decompiler warnings against the selected member bodies; use bytecode if a relevant method is damaged or ambiguous.

## B-side resource inventory (original client jar)

The listed SHA-256 values are over the exact ZIP-entry bytes in `build/minecraft-decompile-cache/1.19.4/client.jar`. These are candidate dependencies to follow when relevant movement consumers are compared; no A-side difference is inferred from them.

| Entry | SHA-256 | Observed values |
|---|---|---|
| `data/minecraft/tags/blocks/beds.json` | `AEAB40CCDF3FA735E4229BAD0A60B604E0930BDFD368037D6346AB300FA8B73C` | 16 colored bed IDs |
| `data/minecraft/tags/blocks/climbable.json` | `D0E3E76D7457F3F3F3D7219FE218C7746E4B2E7626D5C388FCF193089069E365` | ladder, vine, scaffolding, weeping/twisting/cave vines and plants |
| `data/minecraft/tags/blocks/ice.json` | `85098BDEB333FB4B630565E8679BFAAE143D78DEFA4DBF0398280A9F4D863DB7` | ice, packed ice, blue ice, frosted ice |
| `data/minecraft/tags/blocks/soul_speed_blocks.json` | `8BA7EAC6F7C74D25601EF4455B71E3947FDB8E51B8FAFD55DDDEE48397B144B6` | soul sand, soul soil |
| `data/minecraft/tags/fluids/water.json` | `DCFA69A748D03DBF788D8F7B0E5EB6C8DE6355A527FCAF74B9210FE4BE2A3004` | water, flowing water |
| `data/minecraft/tags/fluids/lava.json` | `71F50FB9092D78260BC7434731FC5FD426A44E5284A6AD084EC71CB725630C6B` | lava, flowing lava |

Additional entry hashes inventoried for later slices: block tags `fences.json` `214462701D11703B222317FB7D470CAA40E017B5036B127DB242986B92FB30CD`; `fall_damage_resetting.json` `3A765259768C8ECDFCB83E97E00DD808E709D51919D6D52436869BC93BC7B9E4`; `impermeable.json` `B907C5D2B21B762B6FA8C67C0DC1A5BFCB180109BAB2E14F43A83041B50075A5`; `slabs.json` `FFDC8C9B44B9B7F50E535343F99FEF6937DDE47DFAB122D4A343F32F0EE8E03E`; `stairs.json` `01C55DCD3CA0EA469ECB31B93AB683F9CC1C829379C585CE3F83CEB362333656`; `trapdoors.json` `4CC6249A578BCEF82E79E753AE467B3FCAA963DEFB14396DB016D2383FF10378`; `walls.json` `9D59EBD9851DE9061D12FE294A947F3C327A7E1B24D2F21F855138321F0D6EF4`; `wooden_fences.json` `DB42D0337E0C2C596CEE0E8482B4D51CCBB561D05100F41F92060B1E1327D5B6`; `wooden_slabs.json` `C44DEE6A67CC46159240EC6C41EC5D5017722D02A7CDE0AA4F5AFA932071A0EC`; `wooden_stairs.json` `E287405BBB5586CF93313EF6536D152CC195A6E57D2A2FBF9B8E88BCCFE05D99`; `wooden_trapdoors.json` `A1737EB28BB1D92D04782A20912E64700B0643A703593792839E1B4CF0542252`; `all_signs.json` `D58557D867B7F90E14189B46FD90464DC072D7A488AB00CCB34DE09ADC1FC1E6`; `signs.json` `F17ADEC59BD57315440524F6EDFE7D18A8F6F372AFE887DC9A0151055A15706E`; `standing_signs.json` `7FFD9A0CDD11CF4150294F55C8EBD0CCDA45D4F8ED9B847EFDEB13600E8EC6C7`; `wall_signs.json` `F7EF514F4E570E4FD2959BDE56ADCC6892AE61C771572447ADD8CF82AF603E3B`.

The inventory is a navigation aid, not a claim that these tags directly affect every movement mechanic. Resolve each consumer and distinguish client-jar vanilla defaults from tags/datapacks synchronized by a server.
