# Ordinary shallow-water current impulse differs

1.11.2 Ornithe Feather `Entity.checkWaterCollisions` calls `World.applyLiquidDrag`. The scan floors its minima and ceils its exclusive maxima from the symmetrically contracted and inset box. The nonflying player receives the normalized sum of normalized per-cell flow vectors at scale `0.014`.

26.2 unobfuscated `Entity.updateFluidInteraction` and `EntityFluidInteraction.Tracker.applyCurrentTo` use depth weighting and a player contribution average. With zero prior horizontal motion they raise a nonzero weak push below the minimum to `0.0045000000000000005`.

In the audited enclosed, loaded water channel the player is centered over a terminal nonfalling `LEVEL=7` cell with a fuller eastern neighbor. Both releases point the current west. 1.11.2 adds `0.014` west. 26.2 weights the one contribution by `1/9` so its `0.014 / 9` impulse reaches the minimum gate. The pinned 26.2 client archive includes `minecraft:flowing_water` in the default WATER tag. A custom pack can change effective membership.

This compares the two exact releases in the stated scenario. The independent historical comparisons document the same ordinary scan across 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1 and 1.11.2. The introduction date and runtime parity remain open.

[Accepted exact finding](../../source-evidence/20261117/block-a-1112-262-ordinary-water-current-source/lane-water-current-source-1112-262/finding-ordinary-water-current-impulse.md), [independent source verdict](../../source-evidence/20261117/block-a-1112-262-ordinary-water-current-source/lane-water-current-source-1112-262-audit/verdict-r1.md), [independent packaged-tag audit](../../source-evidence/20261117/block-a-1152-262-packaged-fluid-tags/lane-exact-1152-262-fluid-resource-audit/evidence.md).
