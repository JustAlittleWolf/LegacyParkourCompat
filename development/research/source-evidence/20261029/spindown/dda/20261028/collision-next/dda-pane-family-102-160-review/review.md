# Independent review: DDA pane family IDs 102 and 160

## Verdict

**REVISE the conditional geometry-equivalence claim.** The identity, color-alias, live-neighbor provider, mutable-bounds, and ray-refresh findings are supported within their stated scope. However, the report's claim that any identical nonempty old/current connection mask yields the same collision union is false for one-sided masks. The old collision provider emits the connected arm up to the half-block plane; the current shape always also includes its centered post, which extends 1/16 past that plane.

This review binds the source report SHA-256 `13B9095BAF5D7B011E4FA30B8BAAB901F3172557D35C8F0116CE9F2ACFCF91D8` and `source-hashes.json` SHA-256 `5595B40655F69C035428A09CF28ADE6AA10BB811E789DB8FE2A4F25FB08D9D47`. All 16 source-file hashes in the JSON were recomputed against the canonical read-only trees and matched their exact source-manifest entries. Both ready markers are `ready`; the 1.8.9 / `ornithe-feather` and 26.2 / `unobfuscated` source-manifest digests match the canonical manifests.

The review additionally checks 26.2 `net/minecraft/world/level/block/Block.java`, SHA-256 `CEC6A05E644E4A7FEB8253CC4CA772A98F0E116FB098A1B7EE7302984AC7ECAB`, which matches the canonical manifest (manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`).

## Verified claims

- Old registry ID 102 is `glass_pane`, and ID 160 is `stained_glass_pane`. The old stained pane has 16 dye metadata values. The current block-state fixer maps the 16 color-key families, including old `silver` to current `light_gray`; the report cites `BlockStateData.java:7108–7424`, and the source manifest entry matched.
- Old `PaneBlock.addCollisions` queries all four live neighbors and emits strips while mutating shared bounds (`1.8.9 PaneBlock.java:62–90`). Its neighbor predicate is exactly the report's opaque/same-pane/glass/stained-glass-pane set (`:134–141`). `Block.rayTrace` invokes `updateShape` before using bounds, so this family is not a stale-bounds ray-trace witness (`Block.java:456–457`; `PaneBlock.java:100–131`).
- 26.2 registers glass panes as `IronBarsBlock`; stained panes use per-color `StainedGlassPaneBlock` registrations. The state-based collision function is built by `CrossCollisionBlock.makeShapes` from a centered post and arms for true direction properties (`CrossCollisionBlock.java:53–68`, `IronBarsBlock.java:22–34`). The state-function inputs include `WATERLOGGED`, but that value does not change the geometry formula.

## Concrete geometry counterexample

Take identical masks with only west connected: `WEST=true`, `NORTH=EAST=SOUTH=false`.

- In old 1.8.9 `PaneBlock.addCollisions`, the first branch emits the west strip from local X `0` through `0.5`, with Z `7/16` through `9/16`. The second branch emits no strip because neither north nor south is connected (`PaneBlock.java:67–90`). There is no separately emitted central post.
- In 26.2, `IronBarsBlock` constructs its collision function with `postWidth=2/16` and `collisionHeight=16/16` (`IronBarsBlock.java:22–27`). `CrossCollisionBlock.makeShapes` always seeds the result with that post, whose X extent is `7/16` through `9/16`, then unions the west arm (`CrossCollisionBlock.java:53–68`; `Block.column` centers the post at `Block.java:177–184`).
- Thus the current union extends to X `9/16` in the center Z strip, while the old collision union stops at X `1/2`. The area `X=(1/2,9/16]`, `Z=[7/16,9/16]` is in the current collision shape but not in the historical one.

The same 1/16 post extension occurs for a one-sided north/east/south mask. Therefore the report's conditional sentence for every nonempty identical mask, and the corresponding “conditional” item in its bounded consequences, need correction. Equality may hold for selected masks whose old full strips already cover the post (for example a complete opposite pair on an axis), but that is a narrower mask-specific statement and should be source-checked before being claimed. No general neighboring-state equivalence is established.

## Scope and correction needed

Keep the accepted ID/color alias, provider, ray-refresh and mutable-bounds facts. Revise the geometry conclusion to preserve the concrete no-neighbor cross-versus-post difference and document the additional one-sided-mask difference. Any implementation or adapter must compute historical live-neighbor eligibility and old emitted collision strips; it cannot use the modern stored pane mask as proof of historical collision geometry. Keep current modern feature-neighbor cases on native behavior until separately bounded.

This review says nothing about DDA visitation, camera reachability, movement/runtime parity, chunk behavior, or product integration. No build, tests or runtime were run.
