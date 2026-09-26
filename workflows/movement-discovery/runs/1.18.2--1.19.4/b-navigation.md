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
