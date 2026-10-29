# Legacy ore ID aliases and collision-provider closure

Compared source: Minecraft 1.8.9, Ornithe Feather at `decompiled_minecraft/1.8.9/ornithe-feather`; Minecraft 26.2, published unobfuscated source at `decompiled_minecraft/26.2/unobfuscated`.

## Registry aliases and state projection

`1.8.9/.../block/Block.java:857-859,864,944,987-1000` registers ID 14 `gold_ore`, 15 `iron_ore`, 16 `coal_ore`, 21 `lapis_ore`, 56 `diamond_ore`, 73 `redstone_ore` (`new RedstoneOreBlock(false)`), and 74 `lit_redstone_ore` (`new RedstoneOreBlock(true)`). `Blocks.java:229-238,273,295-296` resolves those same old keys to named constants. `EntityBlockStateFix.java:36-38,43,78,95-96` independently records the numeric-ID/name correspondence for all seven IDs.

For IDs 14, 15, 16, 21, 56, and 73/74, the old block class has no state properties: inherited `Block.getStateFromMetadata` returns `defaultState()` and `getMetadataFromState` returns zero (`Block.java:182-191`). `RedstoneOreBlock`'s `lit` field is a constructor constant and its two registered objects are distinct block IDs, not metadata states (`RedstoneOreBlock.java:16-25`; registration above).

In 26.2, the ordinary aliases register as `gold_ore`, `iron_ore`, `coal_ore`, `lapis_ore`, and `diamond_ore` (`Blocks.java:359-383,661-665,1282-1286`; `BlockItemIds.java:100-115`). Both old redstone IDs use the single `redstone_ore` block (`Blocks.java:1929-1939`; `BlockItemIds.java:108`). The old lit/unlit distinction maps to its `LIT` property: `BlockStateData.java:3784-3785` maps old `redstone_ore` to `redstone_ore` with `LIT=false` and old `lit_redstone_ore` to `redstone_ore` with `LIT=true`. `EntityBlockStateFix.java:95-96` retains the old ID/name identities. No deepslate or other modern-only ore is included in this alias set.

## Collision and ray-bound provider

Old `OreBlock` and `RedstoneOreBlock` extend `Block` and do not override collision-shape or ray-bound methods (`OreBlock.java:15-22`; `RedstoneOreBlock.java:16-25`). The `Block` constructor initializes its mutable bounds to `[0,1]` on all axes (`Block.java:198-202`), and `getCollisionShape` returns a box from those bounds (`Block.java:340-349`). `rayTrace` first calls `updateShape` (`Block.java:456-458`); the inherited `updateShape` body is empty (`Block.java:596-597`). Thus these ore providers expose a full cube for collision and do not change the shared bounds during the ray-shape update path.

In 26.2, the five ordinary ore registrations construct `DropExperienceBlock`; redstone constructs `RedStoneOreBlock`, with ordinary `BlockBehaviour.Properties` and no custom collision shape or `.noCollision()` (`Blocks.java:359-383,661-665,1282-1286,1929-1939`). `DropExperienceBlock` adds drop behavior only and does not override shape methods (`DropExperienceBlock.java:14-31`). `RedStoneOreBlock` adds its `LIT` state and interaction/tick behavior but no shape override (`RedStoneOreBlock.java:22-30,69-90`). The default block shape is `Shapes.block()` (`BlockBehaviour.java:323-325`), collision is enabled by default (`BlockBehaviour.java:985`), and the default collision provider returns the state's shape when enabled (`BlockBehaviour.java:327-328`). The old full-cube collision and local ray-bound providers therefore match this bounded family for equal block position and state projection.

This closes only the seven IDs' alias/state and local shape-provider dependency. It does not establish DDA cell visitation, traversal order, candidate enumeration, first-hit selection, or overall ray-trace parity.