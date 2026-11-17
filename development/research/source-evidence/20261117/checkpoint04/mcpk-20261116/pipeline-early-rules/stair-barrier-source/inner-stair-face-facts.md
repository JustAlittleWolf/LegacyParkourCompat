# Supplemental source fact: inner stair horizontal face selectors

Release/mapping: Minecraft 1.12, Ornithe Feather. Source file `decompiled_minecraft/1.12/ornithe-feather/net/minecraft/block/StairsBlock.java`, SHA-256 `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`. This source hash matches the already recorded 1.12 mapping-local provenance `sources` entry; the provenance JSON SHA-256 is `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

At `StairsBlock.getFaceShape`, lines 146-160, `SHAPE=INNER_RIGHT` returns `SOLID` for exactly the horizontal queries where `face == FACING` or `face == FACING.counterClockwiseY()`; the other two horizontal faces return `UNDEFINED` (lines 150-151). `SHAPE=INNER_LEFT` returns `SOLID` for exactly `face == FACING` or `face == FACING.clockwiseY()`; the other two horizontal faces return `UNDEFINED` (lines 152-153). The comparison is made after `resolveVirtualProperties` (line 141).

For vertical queries (line 142), line 143 returns `SOLID` exactly when `face == UP` and `HALF == TOP`, or `face == DOWN` and `HALF == BOTTOM`; the opposite vertical face is `UNDEFINED`. This vertical result is independent of `SHAPE` because it returns before the shape switch. The supplemental claim is limited to these exact source predicates; it does not add a collision or movement conclusion.

