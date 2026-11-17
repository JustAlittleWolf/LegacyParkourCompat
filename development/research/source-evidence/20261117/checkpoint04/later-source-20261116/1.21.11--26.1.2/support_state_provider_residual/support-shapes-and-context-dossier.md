# Bounded source dossier: support shapes and context consumers

## Pair and evidence identity

- Older endpoint: Minecraft `1.21.11`, Mojmap, canonical tree `decompiled_minecraft/1.21.11/mojmap/`.
- Newer endpoint: Minecraft `26.1.2`, published unobfuscated names, canonical tree `decompiled_minecraft/26.1.2/unobfuscated/`.
- Both ready markers report Vineflower, Java 25. The older run used Mojmap and the newer run used unobfuscated input; both provenance records identify Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1, Tiny Remapper 0.14.1, Gson 2.14.0, Gradle 9.7.1, Java 25.0.3+9-LTS.
- Exact run identities: 1.21.11 was prepared in `1.21.8,1.21.10,1.21.11` Mojmap batch; 26.1.2 was a standalone unobfuscated run. The recorded commands and staging identities are in `decompiled_minecraft/1.21.11/mojmap.provenance.json` and `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`.
- Ready-marker source-manifest SHA-256 values were verified against the actual manifest bytes: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` (1.21.11) and `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (26.1.2). The corresponding ready markers also pin client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` and `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`, respectively.
- The source comparison was read-only. No build, decompile, implementation, Git, backlog, wiki, launch, or runtime activity was performed.

## Findings on the bounded slices

The examined shapes, support responses, direction gates, and context consumers below show no movement-relevant difference between these two endpoint sources. These are endpoint observations, not proof of an intermediate patch boundary or full-pair tick coverage.

### Hanging signs and disputed column extent

- `CeilingHangingSignBlock`: 1.21.11 `CeilingHangingSignBlock.java:48-54, 63, 80, 112-125`; 26.1.2 same path `:48-54, 63, 91, 128-151`. Both define `SHAPE_DEFAULT = Block.column(10,0,16)`, generate rotation shapes from `Shapes.rotateHorizontal(Block.column(14,2,0,10))`, select the state shape for collision/support, and use the same upper-neighbor support/update gate. The default `ROTATION` value changes from 0 to 8. `RotationSegment.java:12-14, 19-24` (1.21.11) and `:12-14, 19-27, 38-40` (26.1.2) identify the cardinal segments 0/4/8/12 as north/east/south/west and convert from/to degrees; therefore 0→8 is 180° on both sides. The generated footprint is centered and invariant under 180°; this metadata change does not alter the resolved shape.
- Exact four-argument overload: `Block.java:183-187` (1.21.11) and `:185-189` (26.1.2). It computes half-widths as `14/2=7` and `2/2=1`, then returns `box(8-7, 0, 8-1, 8+7, 10, 8+1)`: **X [1,15], Y [0,10], Z [7,9]**, in 0–16 block coordinates. This is a 14×2 horizontal footprint and 10 high. The two opposing 180° directions share these centered bounds. In footprint terms this is 14×2; it is not a 14×2 horizontal square.
- `WallHangingSignBlock`: 1.21.11 `WallHangingSignBlock.java:42-48, 72-99, 102-130`; 26.1.2 same path `:44-50, 88-100, 103-113, 137-149`. Both build the same axis-rotated union of the plank (`column(16,4,14,16)`, X [0,16], Y [14,16], Z [6,10] before axis rotation) and sign board (`column(14,2,0,10)`, bounds above), return that for shape/support, and return plank-only shape for collision. Same placement attachment tests, sturdy-face predicate, and neighbor-axis survival update.

### Multiface blocks and leaves

- `MultifaceBlock`: 1.21.11 `MultifaceBlock.java:48-61, 138-162`; 26.1.2 same path `:55-68, 147-171`. Both rotate `Block.boxZ(16,0,1)` to the six face orientations, union occupied faces in `makeShapes`, return full-block fallback when no face is set, and return the selected shape in `getShape`.
- Registration and construction: `Blocks.java` in both releases registers `GLOW_LICHEN` with `GlowLichenBlock` and `.noCollision()` (1.21.11 `:2505-2517`; 26.1.2 `:2522-2534`), `SCULK_VEIN` with `SculkVeinBlock` and `.noCollision()` (`:6226-6236`; `:6238-6248`), and `RESIN_CLUMP` directly as `MultifaceBlock::new` with `.noCollision()` (`:2518-2528`; `:2535-2545`). Their collision policy and occupied face-shape construction are unchanged. Glow lichen and sculk vein subclass the same multiface base in both versions.
- `LeavesBlock`: both `LeavesBlock.java:57-59` return `Shapes.empty()` for `getBlockSupportShape`; all registrations inherit it. `Blocks.java` contains the same 11 registrations in both versions: oak, spruce, birch, jungle, acacia, cherry, dark oak, pale oak, mangrove, azalea, flowering azalea (1.21.11 `:569-618`; 26.1.2 `:571-618`). The concrete tinted/untinted/mangrove subclasses still derive from `LeavesBlock` on both sides.

### Chorus flower, dynamic shulker box, gate, mud, and soul sand

- `ChorusFlowerBlock`: both files `:32, 59-60, 149-167` use support shape `Block.column(14,0,15)` and the same support/update survival gates. Its column bounds are X/Z [1,15], Y [0,15].
- `ShulkerBoxBlock`: both files `:47, 141-150` keep the direction-rotated open-support plate and return it only when a block entity exists and is not closed; otherwise support is a full block. The shape query still derives occupied bounds from `ShulkerBoxBlockEntity.getBoundingBox(state)` with full-block fallback when no block entity exists. This remains animation/state dependent rather than a fixed static box.
- `FenceGateBlock`: both files `:47-57, 73-106` preserve facing-axis selection; update only on the facing-clockwise axis; closed-only collision and support shapes; and separate occlusion shape selection for `IN_WALL`. Closed collision column is `column(16,4,0,24)`; closed support column is `column(16,4,5,24)`, each rotated by horizontal axis. Open gates return empty collision and support shapes.
- `MudBlock` and `SoulSandBlock`: both classes use `Block.column(16,0,14)` for collision and `Shapes.block()` for support and visual shape (`MudBlock.java:15,27-38` on both sides; `SoulSandBlock.java:21,34-45` (1.21.11), `SoulSandBlock.java:15,27-38` (26.1.2)). The collision top remains Y=14 while the support-shape response remains a full block.

### Direction gates, updates, upper wall post, and context-sensitive scaffolding

- `FenceBlock`: 1.21.11 `FenceBlock.java:60-68, 78-107`; 26.1.2 same file `:60-68, 80-121`. The four placement directions call `connectsTo` with the neighbor's opposite sturdy face; `updateShape` changes only the side property for horizontal neighbor directions and delegates vertical directions. Gate-direction and fence-tag checks are unchanged.
- `WallBlock`: 1.21.11 `WallBlock.java:70-97, 105-143, 176-213`; 26.1.2 same file `:70-100, 105-150, 176-248`. Same direction-to-neighbor face-sturdy inputs, horizontal recomputation gates and state update. The upper post decision still derives the above neighbor's bottom collision face, checks the 2-wide post test shape, honors `WALL_POST_OVERRIDE`, and applies the high-wall conditions before setting `UP`. Wall collision remains generated separately from outline shape.
- `ScaffoldingBlock`: 1.21.11 `ScaffoldingBlock.java:29-40, 98-139, 151-174`; 26.1.2 same file `:29-40, 98-148, 158-174`. Distance/bottom state updates and downward sturdy-face support checks are retained. Collision still depends on the query context: placement returns empty; an entity above the full-block test shape and not descending gets the stable shape; otherwise the unstable-bottom shape is returned only for nonzero distance, `BOTTOM`, and the below-block context test, with empty as fallback.

## Separate dependencies and limits

- Tag closure is a separate dependency, not folded into this source-shape comparison: scratch reference `modern-provider-tag-audit/verdict-r3.md`, SHA-256 `44d0f3d4f1bab38131d8cfb7fd6f0fe7e53f17d14282bd7822b2e2e410167918` (identity supplied with the assignment).
- The `FluidState` native-water equality comparison was reassigned as a separate raw-identity review (`getType() ==` versus `.is` via `TypedInstance`, stated to preserve the same raw identity). It is not counted here as a shape or direction-gate difference.
- The coordinating brief states that two fresh source reviewers agreed on the occupied support-shape observations. Their individual report identities were not supplied in this lane input, so this dossier records that as assignment context rather than claiming to reproduce or independently hash-bind those verdicts.
- This dossier covers only the named support/shape/direction slices. It does not close the 1.21.11–26.1.2 movement inventory, block/tag closure, intermediate releases, or runtime parity.




