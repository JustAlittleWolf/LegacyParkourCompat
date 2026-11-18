# Independent author review verdict, revision 1

## Binding

Reviewed additive author comparison: `source/comparison-r1.md`, SHA-256 `6550a3a37cd51e48072fc9395b408fadbe3f41ee774650e2585c2ab10315fa6a` (recomputed; matches assignment).

The original author comparison remains unchanged at SHA-256 `eec89743cb57c69122cb6616da2fcbe2f51fb9bd692a001a0ba342d3ba7e614a`. The independent freeze remains unchanged at `60a48ca6772a7843bbefd712bb8dc39e0c233ab58d7fee22a0d9a168d631960f`, and the earlier review remains unchanged at `7dbd070d72a92cedae2291ac9d1b711e367768505fba6afecf943e6583185f2c`.

## Verdict

**ACCEPT the additive finite-path reset evidence and order, with its stated bounds.** The cited source file hashes match the earlier frozen source identity. I independently re-extracted the exact `Entity.move(MoverType, Vec3)` member from both canonical Mojmap files: both hashes equal the comparison's `cd092568f6af74a10a4c9dfe1d9e53b70582a50ad57625ff9132c9ae9cdaf697`. The cited `handleRelativeFrictionAndCalculateMovement` member hashes also match on both sides and match the author value `625ccacd48603ff77e42a7aaa54de632d9c743336d0eda89be8bacf59ecab392`.

The writer sequence is correctly bounded and described: the stuck-multiplier branch may clear velocity before clipping; the method calculates X then Z clip-difference flags; if there is a horizontal collision it writes blocked X/Z as zero while preserving Y; the conditional fall-on callback follows vertical clipping; then the block speed factor scales X/Z. The helper reads back movement, after which dry travel performs its existing velocity calculation. With identical finite values and fixed clip/provider/callback results, this source-identical sequence is equivalent. The 1.21.11 finite setter gate accepts each finite vector in this witness. The original verdict's coverage objection is resolved for this additive `Entity.move` slice; the original verdict file is preserved unchanged.

**ACCEPT the non-finite boundary as stated, not as parity.** The separately cited `setDeltaMovement(Vec3)` and `addDeltaMovement(Vec3)` hashes recompute to the stated values: 1.21.11 adds `isFinite()` guards absent in 1.21.10. The three-double overload remains source-identical. Those guards can change writes involving non-finite vectors; the comparison makes no equivalence claim for them and supplies no reachability witness. It correctly keeps them separate from the unchanged finite threshold operation.

## Residuals

The additive comparison closes the paired `Entity.move` writer-order gap for the fixed-input finite path only. It does not establish parity of collision geometry/providers, clip results, block callback results, non-finite values, external/effect/push inputs, or other excluded movement branches. Intermediate patch introduction remains unknown.
