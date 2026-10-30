# API correction addendum: red bed collection accessor

**Decision: ACCEPT this API correction.** The guard change in product commit `d0cb77171bfaa310cc89ba7e989c88db1622d806` replaces the nonexistent 26.2 `Blocks.RED_BED` field with `Blocks.BED.red()` at `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_2/WaterBedFallingFluidFace.java:37`. The current root tree retains this guard. The changed file SHA-256 is `70AB00334E8C4463CFC603D10AE1E95EA2368F7C07DA27E75B9795EEA5A702F6`.

Exact 26.2 unobfuscated source confirms the accessor and lookup key are aligned:

- `ColorCollection<T>` is a record whose fifteenth component/accessor is `red` (`world/level/block/ColorCollection.java:15–17`). Its parallel `VALUES` collection has `DyeColor.RED` in that same component (`:18–35`), and `zipMap` constructs records in the same white-to-black order (`:155–173`).
- `BlockItemIds.BED` is created from the colorized `bed` base (`references/BlockItemIds.java:750, 784–785`); `prefixWithColor` prefixes each dye name to that base (`world/level/block/ColorCollection.java:65–67`), and `BlockItemId.create(String)` uses the default `minecraft` namespace (`references/BlockItemId.java`, `create(String)`).
- `Blocks.BED` registers those IDs with `BedBlock::new` (`world/level/block/Blocks.java:696–708`). Applying the same `ColorCollection` order means `Blocks.BED.red()` is the registered `minecraft:red_bed` block.
- The hook variant remains `minecraft:red_bed` (`WaterBedFallingFluidFace.java:27–29`). The existing dispatch resolves a block variant from the registry key (`mechanic/MovementRuntime.java:238–252`), so the state guard and hook variant select the same domain.

No behavioral gate changed. Water-only, non-flying, non-boat conditions remain at lines 34–37; other bed colors and unrelated blocks still take the native supplier. This confirms only the static API/key correction, not build/runtime behavior or full current parity.
