# DDA age-crop family source closure (1.8.9 → 26.2)

Status: source-bounded provider and state-alias closure for the five named legacy registry IDs. This closes the ordinary block movement-collision provider for these aliases; it does not close DDA traversal, hit ordering, or interaction behavior.

## Findings

### Legacy registry and state decoding

The exact 1.8.9 `Block.java` static registrations identify ID 59 as `wheat`, 104 as `pumpkin_stem`, 105 as `melon_stem`, 141 as `carrots`, and 142 as `potatoes` (lines 956, 1044–1045, 1137–1138). Their class constructors are `WheatBlock`, two distinct `StemBlock` instances with different fruit arguments, `CarrotsBlock`, and `PotatoesBlock` respectively.

`WheatBlock.AGE` is integer 0–7 and its metadata decoder sets AGE directly. `CarrotsBlock` and `PotatoesBlock` inherit that state definition and decoder from WheatBlock. Each legacy stem has AGE 0–7 and FACING (all directions except DOWN); its metadata decoder sets AGE while FACING comes from the default state (UP), with `resolveVirtualProperties` deriving a horizontal direction from adjacent fruit when queried through that path. Thus age metadata 0–7 is valid for each of the five IDs; raw metadata outside that range is not accepted by the property setter and is not a supported state in this report.

### Legacy player collision provider

All five classes inherit `PlantBlock.getCollisionShape`, which unconditionally returns `null` (`PlantBlock.java:62`). Legacy `Block.addCollisions` only appends a box when the provider result is non-null (`Block.java:339–344`). Therefore all five produce no player movement collision box, at every valid age; stem age and facing do not alter this provider result.

This result is distinct from their ray/outline bounds. `PlantBlock` initializes mutable bounds to a centered 0.4-wide, 0.6-high box. Wheat overrides its constructor bounds to a full-footprint 0.25-high box. Carrots and potatoes inherit wheat's constructor bounds. Stem bounds are centered and 0.25 high at construction/reset; `StemBlock.updateShape` recomputes height as `(age * 2 + 2) / 16.0`, preserving a centered width of 0.25. `Block.rayTrace` calls `updateShape(world,pos)` before clipping the ray to the mutable bounds (`Block.java:456–459`); this evidence does not support a stale-age-bounds claim. These ray/outline bounds must not be substituted for `getCollisionShape` in movement collision.

### Current key/state aliases and movement provider

Current `BlockItemIds.java` explicitly binds `WHEAT_CROP` to `wheat`, `PUMPKIN_CROP` to `pumpkin_stem`, `MELON_CROP` to `melon_stem`, `CARROT_CROP` to `carrots`, and `POTATO_CROP` to `potatoes` (lines 739–749). Current `Blocks.java` registers those keys to `WHEAT` (`CropBlock`), `PUMPKIN_STEM` and `MELON_STEM` (`StemBlock`), `CARROTS` (`CarrotBlock`) and `POTATOES` (`PotatoBlock`). This is a direct key-level alias mapping, not an inference from similar names.

Each of those five current registrations supplies `BlockBehaviour.Properties.noCollision()`. That sets `hasCollision=false`; the base `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when `hasCollision` is false (`BlockBehaviour.java:327–328, 1094–1097`). The current movement collision provider is therefore empty for all five aliases, independent of age. For the valid legacy age states 0–7, provider behavior matches the old null/no-box behavior. This closes movement collision for arbitrary block arrangements containing only these five aliases as far as their individual provider results are concerned; it makes no claim about the surrounding collector or ray traversal.

Current age schemas remain AGE_7 for all five. Current outline shape formulas are not a movement collision provider: wheat uses a full-width column of height `(2 + 2*age)/16`; carrots and potatoes each use a full-width column of height `(2 + age)/16`; both current stems use a centered width `2/16` column of height `(2 + 2*age)/16`. This differs from old ray/outline bounds: wheat/carrot/potato were a full-width, fixed 0.25-high bounds; stems were centered width 0.25 with age-scaled height `(2 + 2*age)/16`. Current collision is disabled despite those outline differences. Any DDA operation that consumes outline/ray shapes needs its own historical provider treatment; this finding does not claim equivalence for it.

## Applicability and limits

- Closed: source alias identity; valid age metadata range 0–7; each block's legacy movement collision provider (none); current corresponding key and provider (empty).
- Not closed: old-vs-current ray/outline hit shapes, caller selection of collision versus outline provider, DDA visitation/order/early-hit behavior, nondefault or malformed property encodings, or aliases beyond these five.
- No runtime, game, build, or test evidence is included.

## Source references

Full canonical source paths and SHA-256 hashes are in `sources.sha256`. Key line references are included above. The two selected canonical source trees are the task's read-only `1.8.9/ornithe-feather` and `26.2/unobfuscated` trees.
