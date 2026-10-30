# 1.11.2 → 1.12.2 block-query product reconciliation

## Verdict

Two accepted query-time candidates still need product coverage for their stated witnesses: **Fence stair-neighbor collision arms** and the **1.11.2 level-8 water-current side-face check beside a bed**. Pane and Wall already cover the accepted straight-stair witness within their current bounded source domains. Bed bounce is already represented by the existing player hook. No product files were changed.

This review is limited to the assigned fixed-state queries and their current dispatch path. It does not claim full general-predicate coverage for every Fence/Pane/Wall neighbor identity, establish earlier-than-1.11.2 applicability, or prove runtime parity.

## Bound evidence and source aliases

- Product HEAD inspected: `33d1f18d60347eed51c2923fa1c4f5109a60420b`.
- Accepted source audit: `development/research/source-evidence/20261030/checkpoint35/1112-1122-pool31/full-pair-audit-r3/report.md`, SHA-256 `68040F4BF78407D074311ED1B1EB2030F3659D5EB811285E664EFE5FA8E99974`.
- Scope disposition: `development/research/source-evidence/20261030/checkpoint35/1112-1122-pool31/full-pair-audit-r3/scope-disposition.md`, SHA-256 `2BAEE3E5B4018DDFC2D53264518A2B31AA2950C0C8A2E02461A3C585BFF16254`.
- Canonical sources read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.11.2,1.12,1.12.1,1.12.2}\ornithe-feather` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`.

I recomputed these exact-source SHA-256 values. The 1.12, 1.12.1 and 1.12.2 files are byte-identical for every paired block operation below, closing the requested release-alias check:

| Source file | 1.11.2 | 1.12 / 1.12.1 / 1.12.2 (identical) |
|---|---|---|
| `FenceBlock.java` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| `PaneBlock.java` | `1e8968e7d642358cce90f0d3156f07f19c5bca28d2008d5d2d4bf0eccc7266a9` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
| `WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| `LiquidBlock.java` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `BedBlock.java` | `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` |

The exact 1.11.2 Fence source was checked: `FenceBlock.addCollisions(..., forceShape=false)` resolves virtual properties during the movement query, and its old predicate accepts same-material fences/gates or a solid-blocking cube other than pumpkin (with barrier rejection). `1.11.1/FenceBlock.java` is byte-identical to 1.11.2; `1.11/FenceBlock.java` differs (`790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5`). This review did not audit the 1.11 predicate or earlier releases. Since a `V1_11_2` change can resolve for earlier selected profiles, do not treat the pair comparison alone as proof for that wider range.

## Runtime dispatch inspected

On the current client, 26.2 `BlockCollisions.computeNext` obtains a block's collision shape through the entity collision context (`BlockCollisions.java:93`; `EntityCollisionContext#getCollisionShape` calls `BlockState#getCollisionShape(..., context)`). The product `BlockStateCollisionShapeMixin` injects at the return of that context overload and dispatches only when the context yields a player; movement-specific hooks therefore have a query-time interception point while other contexts retain native results. Wall has a separate `WallBlockMixin` return injection. `World.getCollisions` and the local-player movement path in the accepted audit establish that these providers participate in player collision collection.

### Disposition by operation

| Candidate | Product coverage for accepted witness | Verdict and evidence |
|---|---|---|
| Bed landing bounce | `change/v1_11_2/BedBounce.java` resolves `BlockBounceHook` for `minecraft:red_bed` and returns `0.0F` for players. Current `change/v26_1/BlockLanding.java` passes zero-coefficient landing to its vanilla callback, which zeros Y velocity; `CollisionRestitution` passes this hook on vertical collision. | **No code patch.** This matches the 1.11.2 base bed callback (zero player Y on landing) against the non-sneaking 1.12.2 bed reflection. Existing source comments still carry stale acceptance/applicability caveats, but the assigned audit confirms the bounded response. This disposition is for collision response only. |
| Pane arm query vs straight stair | `change/v1_11_2/PaneCollisionShape.java` rebuilds components from four raw neighbor reads at shape-query time. The selected 1.11.2 `PaneNeighborEligibility` rejects the mapped stair identity; selected 1.12 `PaneNeighborFaceShapePolicy` delegates to the directional 1.12 face-shape provider, which accepts the audited straight-stair face. | **No code patch for the accepted witness.** Product behavior is already selected per profile and its collision components preserve source emission order. Unknown/unmapped identities deliberately fall back to native; this is not a claim of universal neighbor-predicate coverage. |
| Wall arm query vs straight stair | `WallBlockMixin` asks `WallNeighborMaskHook` at shape-query time. `change/v1_11_2/WallNeighborMask.java` recognizes the mapped wall and false-waterlogged state, then returns a complete mask of zero only when all neighbors are air or mapped, false-waterlogged legacy stairs. That includes the accepted one-stair/three-air witness. The `v1_12/WallCollisionShape` provider consumes this complete mask, rather than the modern state's arms. | **No code patch for the accepted witness.** This is intentionally a closed finite domain; it does not implement the general 1.11.2 wall predicate for unlisted neighbors. |
| Fence arm query vs straight stair | The return mixin computes a neighbor mask only through `LegacyFenceWallNeighborConnectionHook`. The 1.11.2 implementation returns `false` for every neighbor. The only current shape provider, `v1_12/FenceLegacySolidConnections`, adds arms for the 1.12 frost-ice/TNT exception and otherwise returns empty; when active it ORs additions into stored state flags. It does not recompute a complete historical mask from the raw four neighbors, and cannot express an authoritative all-false mask. | **Missing patch for the accepted witness.** Add a separate Fence query-mask/shape operation (do not overload the unrelated restoration mask): at the movement-context collision-shape query, reread the four raw neighbors and select the old 1.11.2 mask for a proven closed domain containing air and the 14 mapped legacy stair families; produce center-post-only geometry for mask zero. Preserve native shape for unsupported states. Do not infer a general historical predicate from the 1.12 frost-ice/TNT restoration hook. |
| Level-8 water beside bed | Current `WaterScan.scan` explicitly sets `replacesCurrent=false` and skips flow when an in-scan liquid has `LEVEL >= 8` (`mechanic/water/WaterScan.java:178`). Existing `change/v1_12/WaterCurrent.flow` is bounded to non-falling flow, rejects unsupported neighbors (including bed), and has no high-level wall-face branch. Thus the accepted case falls through to 26.2 native flow. Native `FlowingFluid.getFlow` has a falling-state side/above `isSolidFace` check; `isSolidFace` uses `state.isFaceSturdy`, while 1.12.2 `LiquidBlock.isFaceSolid` uses directional `FaceShape.SOLID` and `BedBlock#getFaceShape` is `UNDEFINED`. 1.11.2 delegates the side check to material solidity; bed's wool material is solid. | **Missing patch for the accepted witness.** Add a 1.11.2-selected high-level current path that can handle the audited level-8, zero-gradient bed-side case and applies the old side/above face predicate before the `-6` downward term. It must enter the flow scan where level ≥ 8 currently triggers native fallback, preserve the unmounted/non-flying player gates, and leave the 1.12+ bed-face result unchanged. The face predicate alone is insufficient until the scan can actually invoke historical flow for this level. Keep unsupported surrounding flow dependencies on native fallback. |

## Minimal next-patch scope and gates

1. Implement the **Fence complete-mask query** for the accepted fixed-state witness. The 1.11.2 old method was verified, and 1.12/.1/.2 predicate-source aliases are hash-identical. Keep it independent from Pane and Wall hooks. Before assigning the final change version, audit the differing 1.11 `FenceBlock.java` source (and resolver coverage to still earlier profiles); 1.11.1 equals 1.11.2 at the file-hash level.
2. Implement the **falling/high-level water-current branch** as a separate 1.11.2 behavior. The 1.12/.1/.2 `LiquidBlock` and `BedBlock` aliases are proven above. Resolve the earlier-profile applicability range before a `V1_11_2` hook can be used for profiles earlier than that boundary.
3. Record Pane, Wall-witness and bed-bounce as product-covered dispositions for these bounded source candidates; do not broaden their finite mappings or claim full-predicate parity as part of this reconciliation.

No tests, build, game/TAS/server launch, source generation, or product edits were performed. Existing unrelated worktree modifications were left untouched.
