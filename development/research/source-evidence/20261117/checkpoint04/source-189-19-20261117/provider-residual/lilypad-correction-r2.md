# LilyPad collision-shape correction (r2)

This immutable supplement corrects only the `LilyPadBlock` row in `build/source-189-19-20261117/provider-residual/supplemental-coverage.md`, SHA-256 `C68DA62AC7964E7B6FBBFD78FCD87C028C3B1BCC3B636C1E0D6BCEB9E03D3ED3`. It is bound to the independent row-audit verdict `build/source-189-19-20261117/provider-residual/batch-a-audit/verdict.md`, SHA-256 `176472AE04C85D178A24211642D73E2AAEE80A00D20A2ED7463EFF690969EF3F`. The original report and audit remain unchanged.

## Exact paired source

- 1.8.9 `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/LilyPadBlock.java`, SHA-256 `C5D5C92A927E5D86FEB5FBF22102E7200037314CDBA6C1207ABBE52445580F35`: constructor lines 15–18 assigns `f = 0.5F`, `g = 0.015625F`, then sets bounds `(0.5F - f, 0.0F, 0.5F - f)` through `(0.5F + f, g, 0.5F + f)`. Those resolve to X/Z `0.0–1.0` and Y `0.0–0.015625` (1/64). `getCollisionShape` lines 30–34 returns those bounds offset by block position.
- 1.9 `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/block/LilyPadBlock.java`, SHA-256 `4E266CCC4F6F1530ABC4F3FDB04ACFF50E171288B0735E5171D8CE34857AF429`: `SHAPE` line 15 is `(0.0625, 0.0, 0.0625)` through `(0.9375, 0.09375, 0.9375)`, i.e. X/Z `1/16–15/16` and Y `0–3/32`. `getShape` lines 36–39 returns it.

## Player collision gate and corrected result

1.8.9 `addCollisions` lines 23–27 delegates for a null entity or any entity that is not a `BoatEntity`. 1.9 `addCollisions` lines 22–25 adds the shape for any entity that is not a `BoatEntity`. A player satisfies the non-boat branch in both releases. Thus this is a player collision-shape difference: the old shape fills the full block footprint and is 1/64 high; the new shape is inset by 1/16 on X/Z and is 3/32 high. The 1.9 `onEntityCollision` boat-break branch at lines 29–33 remains boat-only and is not part of this correction.

This source establishes changed bounds on the player collision path; it does not establish a particular trajectory or whole-pair coverage. Every non-LilyPad claim remains bound to the original report and the cited row-audit verdict without change.
