# Independent review: Small Dripleaf client placement candidate

## Verdict: REVISE (source claims valid; not an accepted movement delta)

The proposer correctly identifies two exact 1.17→1.17.1 client-predicted placement-state differences: the upper-cell Vine preclear and flowing-water-derived `WATERLOGGED` value. I independently verified the ordinary placement admission and downstream movement readers. However, both divergent values are produced by the local item-placement operation. Under the assigned equal-raw-input, equal-pre-move-block/fluid-state rule, these do not establish a changed movement operation and are not eligible for movement implementation as stated. Preserve them as source-proven state-producer observations, clearly separated from accepted movement deltas. Small Dripleaf is newly present in 1.17, so the applicable comparison is 1.17→1.17.1 only.

## Independent inputs and identity

The frozen source roster is `roster.md`; dependency addendum is `roster-addendum.md`. The source SHA ledger is `source-hashes.tsv`, mechanically checked against the 1.17 embedded provenance table and the 1.17.1 parent source manifest; all 44 version/file entries match. Ledger SHA-256: `D61B24D4FDE87DDF52C5B263ACF932761464016681D82C12EB2EC192F3A60636`.

Publication identities were checked from the exact source roots:

- 1.17 Mojmap embedded `.decompile-provenance.json`: `AFC9DC30CD8F6406D8EA306E64CC0B3601CC8D7C7BCD314F737573D830A79357`.
- 1.17.1 parent `mojmap.sources.sha256`: `93270D229ACFB751BF56DAF1E7BE26CE3DCFF26B29E94AA157DE621405A3463B`.
- 1.17.1 parent `mojmap.provenance.json`: `DE197F77473955635E8C5730E113F227AF2B8AF9306B74E8A54A765EAFC5A333`; `mojmap.ready.json`: `C7180DCE94D7B57B53A964445B0C788C71F23BA3BA40EEA593C11CC939185E9B`; `artifacts.sha256`: `E52C5DBAE7D9663190CCC55A4F9B44A8E0615FB1FBBD8280811DF43F70EC8AAA`.

These are independently sourced exact-publication identities, not claims that the sources were regenerated in this task. The proposing report inspected after roster freeze is `small-dripleaf-audit/report.md`, SHA-256 `807D1AD783C9EA19270C44CFB5F634B891334C962FA6D8638A25E1574477003D`.

## Findings checked

### 1. Upper Vine prediction difference is reachable and its movement reader is real

`Items.SMALL_DRIPLEAF` is registered as an ordinary `BlockItem` in 1.17 (`Items.java:224`) and as a `DoubleHighBlockItem` in 1.17.1 (`Items.java:224–226`). Both versions' `MultiPlayerGameMode.useItemOn` reach `ItemStack.useOn` for an eligible non-spectator, non-cooldown-blocked local use after sending the interaction packet (`1.17:271–305`; `1.17.1:272–306`). `BlockItem.place` checks `BlockPlaceContext.canPlace`, gets the state, dispatches `placeBlock`, writes the clicked position, then calls `setPlacedBy` (`BlockItem.java:44–75` both versions). The clicked-cell gate is the same `replaceClicked || clickedState.canBeReplaced(context)` (`BlockPlaceContext.java:55–57`).

A valid fixture is Clay at y=-1, lower clicked air at y=0, and an attached Vine at y=1. The lower position is admitted by the context and support gate; `DoublePlantBlock.getStateForPlacement` checks the upper cell for replaceability (`1.17:45–53`; `1.17.1:45–54`). Vine is registered with replaceable-plant material and `noCollission` (`Blocks.java:996–998` both); its `canBeReplaced` delegates to the base material predicate when the clicked cell itself is not Vine (`VineBlock.java:294–297`; `BlockBehaviour.java:208–211`). Small Dripleaf is also `noCollission` (`Blocks.java:3701–3703`), and its shape reaches y=13/16 (`SmallDripleafBlock.java:31–33`), so a player whose feet cell is the upper Vine cell can fit above this lower placement.

In 1.17 the Small Dripleaf item does not dispatch to `DoubleHighBlockItem.placeBlock`; its unchanged `setPlacedBy` returns on the client (`SmallDripleafBlock.java:65–78`), so the upper Vine remains in local prediction. In 1.17.1, `DoubleHighBlockItem.placeBlock` unconditionally writes the above position to source water when `isWaterAt`, otherwise AIR, before delegating (`DoubleHighBlockItem.java:15–22`). With Vine above, the local client therefore predicts AIR there. Vine is in the built-in CLIMBABLE tag (`BlockTagsProvider.java:566+` both), and `LivingEntity.onClimbable()` reads the feet state and that tag (`LivingEntity.java:1423–1438` in 1.17; `1427–1442` in 1.17.1). `handleOnClimbable` uses the predicate and clamps downward velocity to at least -0.15 (`LivingEntity.java:2179–2187`; `2183–2191` respectively). Thus, if movement reads the predicted feet cell before correction, the state change can alter the climb predicate/handling. This is a conditional downstream consequence of a changed item-use-produced state, not evidence that movement differs for equal pre-move state.

### 2. Flowing-water property difference is exact, but remains a producer difference

For a lower clicked cell containing a non-source `FLOWING_WATER` state, Clay support below, and air above, the context can replace the clicked fluid cell and the upper-cell placement gate passes. `SmallDripleafBlock.getStateForPlacement` in 1.17 uses `fluidState.getType() == Fluids.WATER` (`SmallDripleafBlock.java:54–63`), making the lower state not waterlogged for `FLOWING_WATER`. In 1.17.1 it calls `DoublePlantBlock.copyWaterloggedFrom` (`SmallDripleafBlock.java:54–63`; helper `DoublePlantBlock.java:78–82`), whose `LevelReader.isWaterAt` check tests `FluidTags.WATER` (`LevelReader.java:132–134`). The exact built-in water tag includes both source and flowing water (`FluidTagsProvider.java:18`); therefore the new lower state is waterlogged. `SmallDripleafBlock.getFluidState` returns source water when waterlogged and otherwise the superclass fluid (`1.17:81–83`; `1.17.1:77–79`). Both are real locally predicted results because `BlockItem.place` writes to the client level before correction.

This state can be consumed by movement: entity fluid scanning calls `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, ...)`, `isInWater()` exposes the resulting `wasTouchingWater`, and `LivingEntity.travel` selects its water movement branch from that value (`Entity.java:1027–1077`; `LivingEntity.java:2008–2035`, both versions). This is again conditional on placement-produced state being present at the relevant scan. The producer's changed result is excluded by the equal-pre-move-state criterion; the consumer path does not transform it into an eligible movement-operation diff.

## Scope conclusion

Accept the source-level placement admission, prediction, and conditional consumer observations. Reject their promotion to accepted movement deltas under the assigned scope. No runtime trajectory or authoritative server-state claim follows. Do not carry the 1.16.5 profile into this feature-specific candidate.

No build, test, runtime launch, source generation, server producer census, or product-file change was performed.
