# Bounded LilyPad correction audit: 1.8.9 to 1.9

Verdict: ACCEPT for the LilyPad correction only. Bound inputs: correction `build/source-189-19-20261117/provider-residual/lilypad-correction-r2.md` SHA-256 `4F0FC4DDC5845C068C9727DE0C75E8CC0C9094C729DEDF916AC72CE7AED5E3A8`; original residual coverage report SHA-256 `C68DA62AC7964E7B6FBBFD78FCD87C028C3B1BCC3B636C1E0D6BCEB9E03D3ED3`; prior bounded audit verdict SHA-256 `176472AE04C85D178A24211642D73E2AAEE80A00D20A2ED7463EFF690969EF3F` (REQUEST CHANGES).

## Identity and source verification

The 1.8.9 source manifest SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004` and provenance SHA-256 `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185` were rehashed. Its `net/minecraft/block/LilyPadBlock.java` manifest row matches the file SHA-256 `C5D5C92A927E5D86FEB5FBF22102E7200037314CDBA6C1207ABBE52445580F35`. The 1.9 mapping-local `.decompile-provenance.json` SHA-256 is `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A`; its LilyPad source row matches file SHA-256 `4E266CCC4F6F1530ABC4F3FDB04ACFF50E171288B0735E5171D8CE34857AF429`. The source files' bytes match these identities.

## Audited correction

In 1.8.9 `LilyPadBlock` constructor lines 15–18 assigns `f=0.5F` and `g=0.015625F`, then calls `setShape(0.5F-f, 0, 0.5F-f, 0.5F+f, g, 0.5F+f)`, yielding X/Z `[0,1]` and Y `[0,1/64]`. Its `getCollisionShape` lines 30–34 returns that world-offset box. The `addCollisions` override lines 23–27 delegates unless the entity is a `BoatEntity`; a player passes the gate. Base `Block.addCollisions` lines 339–344 adds the box when it intersects the query.

In 1.9, `SHAPE` line 15 is `Box(1/16,0,1/16,15/16,3/32,15/16)`. The override at lines 22–25 excludes boats and otherwise adds `SHAPE`; a player passes the gate. Base `Block.addCollision` lines 317–323 offsets the local box and adds it when it intersects the query. Thus the shape actually supplied to player collision is narrower in X/Z and taller in Y in 1.9. The report's corrected dimensions and its separation of the boat-only `onEntityCollision` behavior are source-supported.

## Boundary

ACCEPT covers only the corrected LilyPad provider row and its player collision gate. It does not establish a concrete trajectory, implementation applicability, or full-pair coverage. Other provider rows—including the six classes the prior audit found absent—retain their prior status; the pair remains partial.
