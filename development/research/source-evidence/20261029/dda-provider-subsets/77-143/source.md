# 1.8.9 button family provider and alias closure

## Scope and source identity

This is a bounded source finding for the two 1.8.9 registered buttons, IDs 77 and 143, against current 26.2. It closes their movement collision provider classification and state aliases. It does not establish DDA traversal order, ray visitation, historical player reachability, or runtime parity.

The read-only 1.8.9 source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather` (Ornithe Feather), and the read-only current root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`. The exact-ready markers, provenance files, source manifests, and each cited Java source are recorded and hashed in `sources.sha256`. Every selected Java file's digest matches its source manifest entry.

## Historical registrations and state decoding

In 1.8.9 `Block.java`, ID 77 is registered as `stone_button` with `StoneButtonBlock`, and ID 143 as `wooden_button` with `WoodenButtonBlock` (lines 1008 and 1139). The subclasses select `ButtonBlock(false)` and `ButtonBlock(true)` respectively; the wood flag changes press tick/arrow behavior, not collision shape construction.

Both use the same `ButtonBlock` state schema: `FACING` and `POWERED`, defaulting to NORTH and false (lines 21–32). `getStateFromMetadata` decodes `metadata & 7`: 0 DOWN, 1 EAST, 2 WEST, 3 SOUTH, 4 NORTH, and 5/6/7 UP; bit 8 supplies POWERED (lines 249–274). Thus all metadata 0–15 are covered and 5–7 are duplicate encodings of UP for each powered value.

## Current identity and property mapping

The current `Blocks` registrations establish `stone_button` as `STONE_BUTTON` (26.2 `Blocks.java:1955`) and the exact old `wooden_button` alias as `oak_button` (`OAK_BUTTON`, line 2777). `BlockStateData` explicitly maps the 1.8.9 old state tuples for `stone_button` IDs 1232–1245 to current `stone_button` states (lines 3800–3811), and old `wooden_button` tuples IDs 2288–2301 to current `oak_button` states (lines 6631–6642). These rows preserve POWERED and horizontal FACING; old DOWN maps to `FACE=CEILING,FACING=NORTH`, and old UP maps to `FACE=FLOOR,FACING=NORTH`. They do not support mapping the old generic wooden button to spruce, birch, or other species.

The current generic `ButtonBlock` state has `FACE`, `FACING`, and `POWERED`, with default wall/north/unpowered (`ButtonBlock.java:51,64`). The current registrations share `buttonProperties()`, which sets `noCollision()` (`Blocks.java:5937–5939`).

## Provider and bounds behavior

For movement collision, old `ButtonBlock.getCollisionShape` returns `null` in every state (`ButtonBlock.java:34–37`), i.e. no collision box. Current `buttonProperties().noCollision()` sets `hasCollision=false`; `BlockBehaviour.getCollisionShape` then returns `Shapes.empty()` (`BlockBehaviour.java:327–329`). This closes empty movement collision for these two aliases across the decoded state space.

The old `updateShape(WorldView,pos)` separately reads the block state and mutates shared block bounds from FACING and POWERED (`ButtonBlock.java:100–131`). Its ray trace path calls `updateShape` before intersecting those bounds (`Block.java:456–465`). This is ray-picking geometry, not the old movement collision provider: the latter is explicitly null. Current `ButtonBlock.getShape` returns state-specific outline geometry built from its precomputed face/facing and powered shapes (`ButtonBlock.java:69–85`). Do not infer movement collision or DDA visitation from either outline implementation.

## Bounded conclusion

For old IDs 77 and 143, an old movement collision provider should classify every valid decoded state as empty. Resolve aliases as `stone_button -> stone_button` and `wooden_button -> oak_button`, preserving the mapped attachment/facing/powered properties. The old shared-bounds ray-picking side effect is a separate mutable-state fact and remains relevant to any future ray-provider analysis; this report makes no claim that current or historical DDA traversal is equivalent.
