# Independent source audit verdict

**Verdict: ACCEPT**

Audited report SHA-256: `eb3d1fb2c1a176a23e0929ec259e6a529bdf35197bad5493c88a6209f61e3e8d` (`build/source-residual-20261116/1.13.2--1.14.4/wall_trapdoor_shulker_faces/report.md`).

The exact source-manifest hashes match the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; both ready markers agree on version and source-manifest hash. Every source-file SHA-256 listed in the report matches both its manifest row and the current exact source bytes.

The compared registrations support the reported common keys and classes: seven trapdoors (`oak`, `spruce`, `birch`, `jungle`, `acacia`, `dark_oak`, `iron`) use `TrapdoorBlock`; the uncolored plus 16 dye-colored shulker boxes use `ShulkerBoxBlock`. The trapdoor state dimensions and six shapes agree across endpoints. Old `BlockTags.TRAPDOORS` contains the six wooden keys plus iron, and old attachment exceptions include every shulker box, so the old wall connection result is false for both families even when a face query returns solid.

The report's per-state results are supported by the compared wall predicates and shape code. For all seven trapdoor keys, old horizontal face shapes are undefined; target closed slabs have no full horizontal face. Each open target state has one full boundary face opposite `FACING` (NORTH→SOUTH, SOUTH→NORTH, WEST→EAST, EAST→WEST), while its other three horizontal faces are not full. Therefore old bits are false in every horizontal direction; target bits are true only for that one face of open states.

For all 17 shulker keys, old wall bits remain false for all four horizontal directions at every queried legal facing/animation stage because the old attachment exception suppresses connection. In 1.14.4 the exception list no longer includes shulker boxes, and their registered dynamic collision shape expands the unit cube along `FACING`; it retains the full block boundary on every horizontal side for all six facings and animation progress. Thus target wall bits are true on each horizontal side. The report correctly distinguishes the valid block-entity dependency in the old face query and limits its conclusion to the assigned families/common legal states.

**Corrections: none.** This verdict accepts only the bounded source claims in the audited report; it does not certify broader wall-inventory coverage, pair freeze, or runtime trajectory parity.
