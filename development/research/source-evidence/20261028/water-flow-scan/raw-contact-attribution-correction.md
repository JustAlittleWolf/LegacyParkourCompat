# Contact semantics supplement: citation correction 001

This note clarifies the Tiny-mapping attribution in `contact-semantics-supplement.md`; it does not replace or modify that supplement or the bound `REPORT.md`.

The Tiny payload maps `hasLiquidCollision()Z` as an Entity method (`aK` in 1.8; `aL` in 1.8.1). It does not provide a separate PlayerEntity Tiny method row for the override. The PlayerEntity override itself is verified directly in the exact raw client bytecode for the Tiny-bound PlayerEntity classes `ahd` (1.8) and `aha` (1.8.1): `ahd.aK()Z` and `aha.aL()Z` read the respective abilities `flying` boolean and return its negation. The raw `World.applyLiquidDrag` implementations invoke the method virtually on the `Entity` argument (`wv.aK()Z` / `wx.aL()Z`), so a local PlayerEntity instance dispatches to that override. Therefore the behavioral conclusion remains supported: local player water push is suppressed while flying in both releases. The correction is to the description of what Tiny itself maps, not to the observed raw override or virtual-dispatch behavior.

The exact-version 1.8.9 PlayerEntity source citation remains independent comparison evidence for the same gate (`PlayerEntity.java:1617–1619`).
