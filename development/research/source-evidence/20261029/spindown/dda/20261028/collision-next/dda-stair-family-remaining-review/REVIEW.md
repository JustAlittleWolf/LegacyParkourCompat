# Independent review: remaining legacy stair registrations

Verdict: ACCEPT, bounded to the source claims in the reviewed finding. This does not establish DDA traversal, runtime parity, or product implementation acceptance.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-stair-family-remaining-53-67-108-109-114-134-136-156-163-164/FINDING.md`
  - SHA-256: `3C7855D790DF2AE0A892C5025A7986C27271DB83FF7FD3B4D2D4FEB72EBDDB34`
- Pinned source manifest: `.native-source/20261028/collision-next/dda-stair-family-remaining-53-67-108-109-114-134-136-156-163-164/sources.sha256`
  - SHA-256: `787EE41DDF408A13787EE7762E327350FAE56448265D53D87F0DD76017FE2E2F`
- All seven source-file digests in that manifest were recomputed against the canonical source tree and matched.

## Checks

- The 1.8.9 `Block.java` registrations confirm the listed IDs, names, and shared `StairsBlock` implementation. ID 67 is `stone_stairs` constructed with the block registered at ID 4 (`cobblestone`); the flattening item table maps `minecraft:stone_stairs.0` to `minecraft:cobblestone_stairs`. The current `Blocks` table registers `COBBLESTONE_STAIRS` over `COBBLESTONE`, while the separate later `STONE_STAIRS` is over `STONE`. The finding's corrected ID 67 identity is therefore supported. IDs 156, 163, and 164 are respectively quartz, acacia, and dark oak stairs, as the finding states.
- `EntityBlockStateFix` has the corresponding legacy ID/name rows, and `BlockStateData` has state rows for the mapped stair families. The current `Blocks` registrations use the corresponding base blocks for the families listed.
- In old `StairsBlock`, metadata bit 2 selects top/bottom and `Direction.byId(5 - (metadata & 3))` selects facing; bit 3 is ignored. The report correctly bounds this to eight effective states with high-bit aliases.
- Old collision emission order is base half, second half-step after the inner query, optional quarter after its query, then reset of the mutable bounds to the unit cube. Neighbor identity is `instanceof StairsBlock`; same-half/facing checks and the diagonal veto are applied by the cited predicates. This confirms the reported cross-material old-stair interaction, while leaving the exact provider callbacks and traversal open.
- Current `StairBlock` consumes the state's stored `SHAPE`; its shape updater uses classic behind/outer then front/inner checks and `canTakeShape`. Both current stair detection and side-veto detection include every current `StairBlock`. The finding correctly identifies modern-only stair neighbors as a dependency that cannot be treated as an old stair (or silently as an old non-stair) by a historical provider. Its bounded rule—recompute with old identity-gated semantics or retain native behavior for that route—is supported.

## Scope limits retained

Acceptance covers registration/state mapping and the stated stair-shape/provider facts only. It does not establish that the current stored shape is always current, that modern collision callbacks match old collision emission, that ray/DDA visitation is equivalent, or that a product adapter is complete. Waterlogged/fluid paths and modern-only neighbor dependencies remain outside the accepted equivalence claim.
