# Correction supplement: running immersion depth

This supplement applies to the following immutable drafts:

- `coverage.md` SHA-256: `2cb4040f8e2aad534d2ca9a60380a97b8e1bd28ddf7dfed903b9daf1b24bf888`
- `water-contact-and-current-query.md` SHA-256: `391d448311d3719bc42776861da02c59f34221e7fbaec3d6200ad70670cede70`

Evidence: 1.13.2 `net/minecraft/entity/Entity.java`, `m_69693160(Tag<Fluid>)`, lines 2527-2545. The nested scan visits X (`p`), then Y (`q`), then Z (`r`). For each intersecting tagged fluid, line 2537 updates `d = Math.max(e - box.minY, d)` before lines 2540-2541 conditionally scale that cell's flow vector by `d` when the *running* maximum is below `0.4`. The vector is then added to the aggregate at line 2544.

Correction: for multiple contributing cells, the scale factor is the running maximum immersion depth at that point in the scan, not necessarily the final maximum for the whole query. Since `d` is monotonic and the scale check is performed inside the ordered scan, different current vectors can receive different factors, and the aggregate can depend on the scan order. Do not describe every vector as scaled by the final maximum. The finding's single-contributing-state witness is unchanged: for that state, the running maximum is also the final maximum.

The source file is `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`; its manifest SHA-256 is `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
