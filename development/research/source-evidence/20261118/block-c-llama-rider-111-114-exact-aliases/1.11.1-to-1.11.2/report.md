# 1.11.1 → 1.11.2 Llama rider placement (bounded local comparison)

## Evidence identity and limitation

The local trees are labeled `decompiled_minecraft/1.11.1/ornithe-feather/` and `decompiled_minecraft/1.11.2/ornithe-feather/`. The exact mapped source files are present. However, hidden-file inspection found no `.decompile-provenance.json` in either mapping root, and `.minecraft-decompile-cache/1.11.1/` and `.minecraft-decompile-cache/1.11.2/` are absent. The trees therefore lack the project provenance tying their contents to exact game artifacts, mapping artifacts/builds, decompiler and runtime. The `SharedConstants.java` files also contain no release identifier. The mapping family is indicated by the directory label only; exact mapping-build identity is unavailable. These are not verified exact-version inputs, so this comparison is provisional and does not establish a source-accepted 1.11.1→1.11.2 finding. No alternate release or mapping was substituted.

No source-generation tool SHA can be tied to these trees without provenance. For traceability, current local decompiler task/engine entrypoint source hashes are:

- `buildSrc/src/main/java/legacyparkourcompat/minecraft/DecompileMinecraftTask.java`: `A9037520D53D7C8967C4F6C6B243EF0FA661D2DDBBADA6696850F8F81AE2636B`
- `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileEngine.java`: `E5FF539DE45BEA22F842A8C5C49AABDF8C1A6B84944B046ED28572078DE9BA0E`
- `buildSrc/src/main/java/legacyparkourcompat/minecraft/MinecraftDecompileMain.java`: `E4E748483C969E82F566D274CB2D7B71B4F4906BABFF4AE20F829FD7F1217375`

These hashes identify the current source-generation code only; they do not prove it generated either inspected tree.

## Bounded comparison

Older local label, mapping directory `ornithe-feather`: `decompiled_minecraft/1.11.1/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java`, `updateRiderPositon(Entity)` lines 144–150 and `getMountHeight()` lines 154–155; `net/minecraft/entity/Entity.java`, `rideTick()` lines 1569–1582; `net/minecraft/entity/living/player/PlayerEntity.java`, `rideTick()` lines 379–399 and `getRideHeight()` lines 948–950.

Newer local label, mapping directory `ornithe-feather`: corresponding files in `decompiled_minecraft/1.11.2/ornithe-feather/`; `LlamaEntity.java` lines 60–63, 144–150 and 154–155; `Entity.java` lines 1556–1569 and 2342–2347; `PlayerEntity.java` lines 379–399 and 948–950.

The two `LlamaEntity.java` files are byte-identical (SHA-256 `1577CF77A5C1D0E47DDE0E13327DC333FCD65AA86C7E9073F49B2C678F0257B1`). In both, `updateRiderPositon(Entity rider)` first gates on `this.hasPassenger(rider)`, calculates `f = cos(bodyYaw * (float)(PI / 180.0))` and `g = sin(bodyYaw * (float)(PI / 180.0))` as floats, declares `float h = 0.3F` (unused), then calls `rider.setPosition(this.x + 0.3F * g, this.y + this.getMountHeight() + rider.getRideHeight(), this.z - 0.3F * f)`. For a player rider, the sole direct Y write in this Llama override is the second argument: a left-associative double sum of mount `y`, Llama `getMountHeight()`, and `PlayerEntity.getRideHeight()`. The Llama override returns `this.height * 0.67` from `getMountHeight()`; `PlayerEntity.getRideHeight()` returns `-0.35`. The standard Llama constructor sets its dimensions to `(0.9F, 1.87F)`, so for that adult the Y expression is `this.y + (1.87F * 0.67D) + (-0.35D)` with float-to-double promotion on multiplication and ordinary left-associative double addition. `Entity.height` is a float. The parent `Entity.rideTick()` zeroes velocity, ticks the rider, and, if it remains riding, invokes its mount's `updateRiderPositon(this)`. `PlayerEntity.rideTick()` delegates to `super.rideTick()`; its `!world.isClient && isSneaking() && isRiding()` gate only stops riding and clears sneaking. The Llama callback has no logical-side guard of its own. Its `hasPassenger` guard is the parent-membership check; `Entity.hasPassenger` scans the current passenger list and compares entries with `equals`. These callback, numeric, ordering and guard facts match in the inspected trees; no Llama player-Y placement difference is present in these local files.

This conclusion is restricted to the standard vanilla Player riding a vanilla Llama callback. It makes no claim about other vehicles or non-player movement.

## Relevant file hashes (SHA-256)

- `1.11.1/.../LlamaEntity.java`: `1577CF77A5C1D0E47DDE0E13327DC333FCD65AA86C7E9073F49B2C678F0257B1`
- `1.11.2/.../LlamaEntity.java`: `1577CF77A5C1D0E47DDE0E13327DC333FCD65AA86C7E9073F49B2C678F0257B1`
- `1.11.1/.../Entity.java`: `5A906DE1D40A4A5BA8AEDC9A6A9DC704E0ED862DF177CF967FB1A9454765FA96`
- `1.11.2/.../Entity.java`: `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440`
- `1.11.1/.../PlayerEntity.java`: `8B37FD286B2DAC1B343BD708B80284B43BB31C61BAFC63BDCF6620BA3A59065C`
- `1.11.2/.../PlayerEntity.java`: `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B`

Ellipses above abbreviate only the already stated version/mapping prefixes; the complete inspected paths are in the bounded-comparison section.

## Disposition

Provisional no-difference in the visible local files; exact-pair verification remains open because source/mapping/tool provenance is absent. The bounded callback and caller comparison is complete for this assignment. No finding is proposed pending provenance-backed exact inputs.

