# 1.13.1 and 1.13.2 mixed top-slab AutoJump

Exact 1.13.1 and 1.13.2 Feather Gen2 build.2 register dry TOP `minecraft:stone_slab` as the old top-half provider (`Block.java`, `SlabBlock.java:24–59`). Exact 1.14 Feather Gen2 build.2 renames that historical ID to `minecraft:smooth_stone_slab` in `DataFixes.java:452–476` and registers a distinct Stone slab. The migration identifies the 26.2 Smooth Stone slab as the old provider for this fixture.

For the reviewed Y=8 callback, a dry standing forward SELF move lands on Stone with its horizontal component unchanged. Both actual side rays clear the candidate faces with positive margins. The loaded player column and the cap over the slab are air. `WorldView.java:112–151` emits the translated top-half candidate before the aggregated Stone, and `World.java:1755–1757` appends entities beyond that stream. The slab is the first ray hit in `LocalClientPlayerEntity.java:871–980`; one empty above-slab sample leaves a rise of 1.0 and writes the one-tick timer. This conditional derivation uses the observed landing geometry and supports no ordinary-input claim about producing the exact numerical starting velocity.

| Reviewed artifact | SHA-256 |
| --- | --- |
| mixed-slab-113-alias-source.md | 98c8235f1ac8adf79ba40fed5c94a14d8f8114f4d8d4ae842e9fed8bc7323ce4 |
| Alias audit.md | 6890e13d70a72187660c56fbb267314d9c7ce7c667b16aaba359b02cca38f5a3 |
| coordinate-timer-addendum.md | 4844699b22b41f1dd7989692a04f220000fd873aa8e6d5c70a4efaaccf79b550 |
| coordinate-timer-audit.md | f0fc8b10fe7d77f11ed4a15e211c3d7b120c123860e1fd07b31aa63ab33f6c8d |

Exact source-manifest SHA-256 values are `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` (1.13.1) and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (1.13.2). Runtime parity remains unverified.
