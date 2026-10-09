# Independent source review: pane-provider slice 3 (2026-10-09)

## Scope and verdict

Reviewed only the immutable third slice memo at `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-third.md` from frozen candidate tree `8f97dda72e0775c1e39990b087433b2e552d5564`. The memo was created in `6e2032baa70ad2be504ae6053d1008cb86534a09`, has Git blob `9b7d00b2c2ca39aa980c0fdf3b0741d810fd985b`, and raw-file SHA-256 `5553e9639c6abfaedaaaed7f572d680dd29dc36d210c4ca3beac3b8751b745f0`.

**Verdict: ACCEPT, bounded to the three listed shared registrations and their one-east-neighbor pane-mask witnesses.** The exact source supports the reported no-delta results. This does not establish a full provider census, every world-reachable mask, collision geometry, provider lifecycle behavior, or runtime parity.

## Source identity

The 1.8.9 and 1.9.4 ready records report `ready` for `feather-r1-2026-10-07`. Their source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the artifact-manifest SHA-256 values are `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` and `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

I recomputed all eleven source-file hashes listed by the memo and compared each file with its source-manifest row; every hash matches. The verification records report zero source-file differences and no raw-input-artifact differences. They also report a regenerated derived remapped JAR mismatch in each version. Original derived-JAR equivalence therefore remains unproven; the regenerated JARs do not establish that the difference is metadata-only.

## Bounded findings

The 1.8.9 pane resolver checks the four cardinal neighbors and uses `block.isOpaque()`. `Block.isOpaque()` returns constructor-cached `opaqueCube`. In 1.9.4 the resolver checks those same four directions and uses `block.defaultState().isCube()`, which delegates through the resolved state to `Block.isCube(state)`.

| Registration | Exact source disposition | East-neighbor mask |
|---|---|---|
| Prismarine, ID 168 | Both registries instantiate `PrismarineBlock`; its `Material.STONE` default state is `ROUGH`. It inherits base `isSolidRender()` / `isCube()` results of true in both source versions. | E in both versions; no change. |
| Sea Lantern, ID 169 | Both registries instantiate `SeaLanternBlock(Material.GLASS)`. The class has no opacity/cube override; inherited base predicates return true in both versions. | E in both versions; no change. |
| Carpet, ID 171 | Both defaults use `COLOR=WHITE`. `CarpetBlock.isSolidRender()` and `isCube(state)` return false. Registration’s `setOpacity(0)` changes only the separate opacity field; old `opaqueCube` was cached false from the override. | No E arm in either version; no change. |

The memo’s provider classification and mask statements are supported. Its next-slice pointer—Hardened Clay (172), Double Plant (175), Standing Banner (176)—is a historical pointer; those registrations are covered by the fourth memo.

## Limits

No full registry census or all-mask analysis was repeated. No lifecycle, placement, collision-shape, non-player, or runtime behavior is claimed. The source/JAR provenance caveat above remains open.
