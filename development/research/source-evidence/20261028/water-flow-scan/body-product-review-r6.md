# Independent product review: 1.8.9 body-contact scan

**Verdict: REVISE** for product tip `ebb27afe1570f71928049c67a57717b7d2abc577` (`Respect legacy world height in body contact`).

The height intersection fixes ordinary source-water contact membership outside the 1.8.9 world interval, but the shared scan still has an earlier unsupported-fluid exit that can discard the historical result and restore the native contact value.

## Confirmed correction

`WaterScan.java:81-98` retains the raw contact bounds for admission and the source-exclusive-max surface test, while deriving `scannedContactBounds` intersected with `[0,256)`. The one-pass union uses those clipped bounds for contact membership. The flow bounds remain separate. This matches 1.8.9 `World.applyLiquidDrag`'s loop behavior for cells that return air outside the world: `World.java:162-164` defines `contains` as `0 <= y < 256`, and `World.java:612-618` returns `Blocks.AIR.defaultState()` when `contains` is false. The legacy area gate is still applied to the raw bounds, so partial vertical overlap remains admitted.

## Remaining false-contact path

The union also includes the modern native box (`WaterScan.java:106-129`). Cached 26.2 `Entity.getFluidInteractionBox` returns the entity bounding box deflated by `0.001` (`Entity.java:4088-4096`); `EntityFluidInteraction.update` loops its floor/ceil cell bounds (`EntityFluidInteraction.java:34-60`) and adds fluid height to the tracker (`:72`). Therefore modern `LiquidBlock` fluid at a cell outside the historical contact bounds can still be scanned.

Before computing `inContact` or `inFlow` (`WaterScan.java:148-160`), the global water-state guard at `:142-145` returns `Optional.empty()` for any non-`LiquidBlock` water or any `LiquidBlock` with `LEVEL >= 8`. `EntityWaterMixin.java:67` then returns `nativeContact` when the scan is empty.

Concrete case: place falling `LiquidBlock` water (`LEVEL >= 8`, which 1.8.9 `LiquidBlock.getHeightLoss` explicitly supports by treating levels `>= 8` as zero loss; source comparison lines 41-46) at y=2 with player feet y=0.5. The legacy 1.8.9 body query after the `+0.4/-0.4` transform and `0.001` deflation has y bounds approximately `[0.901,1.899)`; `floor(maxY+1)` is 2, so the half-open scan visits only y=0 and y=1. The modern native box spans approximately `[0.501,2.299)`, so its union scan includes y=2. The global `LEVEL >= 8` guard fires there before contact membership and returns empty; native fluid tracking can see the falling fluid at y=2 and return true. The historical query never read that cell and returns no contact. This is a false positive despite the new clip.

The analogous below-world case remains possible at y=-1. Modern waterlogged blocks outside the source domain may still need the existing native-feature fallback, but old `LiquidBlock` water outside the historical contact/flow domains must not trigger it. The unsupported-state decision must be domain-aware while keeping flow fallback independent from the body-contact result.

## Scope

The source evidence supports the bounded 1.8.9 contact query and its Y range. This review does not claim pair-wide coverage or runtime parity. No files were changed; no build, tests, or runtime checks were run.
