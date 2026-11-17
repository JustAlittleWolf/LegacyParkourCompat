# Applicability closure: falling-water glass-side suppression

## Verdict

**Accepted for the exact six requested source releases.** Ordinary glass and every stained-glass color suppress the falling-water side-face contribution in 1.12, 1.12.1, 1.12.2, 1.13, 1.13.1, and 1.13.2. In all six, the falling branch probes both the horizontal neighbor and the block directly above that same neighbor, passing the horizontal direction as the tested face. The suppression gate returns false for ordinary and stained glass at either probe position. No unsupported patch release remains within the requested ranges. This closes applicability only for these six exact releases; it does not infer unexamined versions.

## Frozen-source identity and exact continuity

The inspected roots are the already-frozen Feather generation 2 snapshots under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`. For 1.12 and 1.12.1, the same roster-identified source files also exist in their hidden `.ornithe-feather.previous-*` copies; those copies produced the same `LiquidBlock.java` hash as the active root. File SHA-256 values below were rechecked against the frozen roster identities.

| Release | Current method source | SHA-256 | Result |
|---|---|---|---|
| 1.12 | `net/minecraft/block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | Exact byte match with 1.12.1 and 1.12.2 |
| 1.12.1 | same | same | Exact byte match with 1.12 and 1.12.2 |
| 1.12.2 | same | same | Exact byte match with 1.12 and 1.12.1 |
| 1.13 | `net/minecraft/fluid/FlowingFluid.java` | `da81f379a77ef5da26e87503a1f605df4efbf5d5c02e6b6f68a916dc327df5dd` | File differs from .1/.2; target methods below match exactly |
| 1.13.1 | same | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` | Exact byte match with 1.13.2 |
| 1.13.2 | same | same | Exact byte match with 1.13.1 |

For 1.13, 1.13.1, and 1.13.2, direct extraction of each complete method body (from signature through its matching closing brace, UTF-8 encoded) gave these identical SHA-256 values in every release:

- `FlowingFluid.getFlow`: `0b939874de406ebfedcc2d15234c69b4a946423088491aa5ce3711b06fae942a`
- `FlowingFluid.isSolidFace`: `bd7087ed61a54b0880a3f590785ec3b6a2a497445d625a78cf3b561bf0014093`
- `Block.isExceptionForPlacement`: `9b064e00d22c0d9606c4944c5090886f97a18d62f714f5cb55155e01926bfb60`
- `Block.isExceptionForAttachment`: `5d825d3503f1a18444388155de786ca29fd86fb73234a57a3b0e404ec5567963`

This establishes precise continuity of the tested operation across 1.13.0, .1, and .2 even though the full 1.13 `FlowingFluid.java` and `Block.java` files are not byte-identical to later patches. `StainedGlassBlock.java` is byte-identical across the three (SHA-256 `ae635505e01dd0b377fd6b7ffc8ed4f3ca7dcb603bbc10a06525c2b68f34ef2a`).

The 1.12 support files are byte-identical across all three patches as well: `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `StainedGlassBlock.java` `64cfa035b8c1230bcfe5e269410d9bc52d3c883f4367caf988f87f150215098d`.

## Exact branch, predicate, and numeric trace

- **1.12 / 1.12.1 / 1.12.2:** `LiquidBlock.getFlow` enters its falling-current branch when `LEVEL >= 8`. For each horizontal direction it checks `isFaceSolid(world, neighbor, direction) || isFaceSolid(world, neighbor.up(), direction)`. `LiquidBlock.isFaceSolid` rejects attachment exceptions before asking for a `FaceShape.SOLID` face. The identical `Block.isExceptionForPlacement`/`isExceptionForAttachment` code includes equality checks for `Blocks.GLASS` and `Blocks.STAINED_GLASS`; thus both tested positions return false for both glass blocks. All three complete `LiquidBlock.java` files have the same hash above, so there is no .1 or .2 branch or arithmetic change.
- **1.13 / 1.13.1 / 1.13.2:** `FlowingFluid.getFlow` enters on `state.get(FALLING)`. It uses the same horizontal-neighbor OR neighbor-above probe and direction argument. `isSolidFace` rejects attachment exceptions before the face-shape check. The byte-identical `Block.isExceptionForPlacement` method identifies stained glass by `block instanceof StainedGlassBlock` and ordinary glass by `block == Blocks.GLASS`; `isExceptionForAttachment` delegates to that predicate. Consequently both faces are false in each position in every patch. The measured method-body hashes prove .13.0 has the same branch, short-circuit order, constants, and final normalization as .13.1/.13.2.
- **Numeric witness:** Set the source fluid to falling water, give it no horizontal height gradient, and put one ordinary or stained-glass block at either tested position. The preexisting vector is `(0,0,0)`. Each glass face test is false, so the branch does not add `(0,-6,0)` and final normalization remains `(0,0,0)` in all six releases. With no glass-supported face, the identical branch likewise leaves zero. This is the same suppressed result at both edges of the 1.12 and 1.13 aliases and at each intermediate patch.

## Dye-key proof

- **1.12 family:** `Block` registers one block under `stained_glass`, namely `new StainedGlassBlock(Material.GLASS)`. The same singleton is exposed as `Blocks.STAINED_GLASS`, which is the object checked by the attachment-exception predicate. `StainedGlassBlock` stores color in its `DyeColor` property, iterates `DyeColor.values()` for creative entries, and maps metadata through `DyeColor.byId(metadata)` / `getId()`. Thus every color state is the same `Blocks.STAINED_GLASS` block instance and is covered by the exception.
- **1.13 family:** the registration segment from the white through black entries in `Block.java` has the same SHA-256 `8abc10e9df81a22ddd1d6fe32af19b5b0a30d17f438547b6d4ddbc459bd6280b` in 1.13, 1.13.1, and 1.13.2. It maps the 16 IDs to class instances and dye keys as follows: `white_stained_glass`/WHITE, `orange_stained_glass`/ORANGE, `magenta_stained_glass`/MAGENTA, `light_blue_stained_glass`/LIGHT_BLUE, `yellow_stained_glass`/YELLOW, `lime_stained_glass`/LIME, `pink_stained_glass`/PINK, `gray_stained_glass`/GRAY, `light_gray_stained_glass`/SILVER, `cyan_stained_glass`/CYAN, `purple_stained_glass`/PURPLE, `blue_stained_glass`/BLUE, `brown_stained_glass`/BROWN, `green_stained_glass`/GREEN, `red_stained_glass`/RED, and `black_stained_glass`/BLACK. Each is constructed as `new StainedGlassBlock(...)`; the class-wide `instanceof StainedGlassBlock` check therefore catches all 16 keys. The 1.13-era key `light_gray` deliberately maps to the existing `DyeColor.SILVER` enum key, which remains covered.

## Scope boundary and lineage

This addendum verifies only the glass-side gate and the horizontal-neighbor/neighbor-above falling-current branch for the six named snapshots. It does not broaden the original findings to panes, stairs, lava, fluid spread, or other collision/shape behavior. The original verdict remains at `verdict.md`; its SHA-256 before this addendum was `dbd43349d132420e4b5cb36d9eb7eb905c54b375bd6c0bae0f4aab52d9df4fbb` and was not edited.