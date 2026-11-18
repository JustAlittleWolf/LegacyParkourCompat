# Cocoa age-two EAST alias: 1.8–1.8.8

## Source identity

All nine read-only sources are `decompiled_minecraft/<version>/ornithe-feather`, with exact Feather Gen2 `build.2` inputs recorded in each `.decompile-provenance.json`. The SHA-256 of each manifest matches the assignment’s expected identity:

| Version | Manifest SHA-256 | Feather Gen2 publication SHA-256 |
| --- | --- | --- |
| 1.8 | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` |
| 1.8.1 | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` |
| 1.8.2 | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` |
| 1.8.3 | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` |
| 1.8.4 | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` |
| 1.8.5 | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` |
| 1.8.6 | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` |
| 1.8.7 | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` |
| 1.8.8 | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` |

## Bounded source result

The concrete witness is cocoa at `(0,0,0)`, `FACING=EAST`, `AGE=2`, supported by jungle log at `(1,0,0)`. `CocoaBlock.isSupported` offsets the cocoa position by its facing and requires `Blocks.LOG` with the jungle planks variant (1.8 `CocoaBlock.java:41–45`; 1.8.2 uses renamed local/type identifiers for the same predicate). The `CocoaBlock.java` source hash groups are 1.8–1.8.1 (`af7d10295415c50debb68d7c35363ff081b030e9eb9e0ed4c5c122d37f0f00dd`) and 1.8.2–1.8.8 (`d4e3c6b53fd33b3ff5e433b333511d93cbb83dbc0bdfb5690efbef734c5542e9`). Both `updateShape` bodies retain the same formulas and EAST bounds: with age two, `j = 4 + age*2 = 8`, `k = 5 + age*2 = 9`, `f = j/2 = 4`; EAST is `[7/16, 3/16, 4/16]–[15/16, 12/16, 12/16]` (1.8 `CocoaBlock.java:70–92`; same relevant body through 1.8.8). `getCollisionShape` updates those bounds before delegating to the block box constructor (`CocoaBlock.java:58–60`). The method-body SHA groups for `getCollisionShape` are identical across all nine; `updateShape` and `isSupported` have identifier-only group changes at 1.8.2, with the operations and constants preserved.

For the stated player box `[-.2,0,.2]–[.4,1.8,.8]` and grounded `dx=.1`, the swept box reaches cocoa at `x=.5`. The ordinary collision provider loops candidate block positions and calls each block’s `addCollisions` with the movement query (`World.java:873–903` in 1.8; corresponding call at 1.8.2 `World.java:891–921`). `Block.addCollisions` asks the block for its collision shape and inserts it only when the query intersects it (`Block.java:292–296` in 1.8; 1.8.2 `Block.java:339–343`). Cocoa therefore enters the collision list. The X clip uses `Box.intersectX` and limits positive displacement to cocoa’s west face: `.4375 - .4 = .0375` (`Box.java:93–110`).

`Entity.move` clips Y, then X, then Z, and permits the step retry only for positive `stepHeight`, a grounded condition (`onGround` or downward-Y clipping), and a changed horizontal component (`Entity.java:438–459`, 1.8). The living-entity default remains `stepHeight=.6F` (1.8 `LivingEntity.java:131`). In this witness the retry’s `.6` rise leaves vertical overlap with cocoa’s top at `.75`, so X remains clipped at `.0375`; the candidate does not exceed the already-clipped horizontal distance and the original result is retained. The 1.8 and 1.8.1 movement bodies differ from the 1.8.2–1.8.8 body, but their changed slices preserve this grounded gate, axis clipping order, and equal-or-better candidate comparison for the stated witness. The `getCollisions`/`move` source bodies are otherwise not asserted identical across the patch line. Player relative movement reaches `LivingEntity.moveRelative`; the assigned witness specifies the resulting movement request directly, so this report does not claim a particular input-to-velocity value (`LivingEntity.java:1115`, `PlayerEntity.java:1277`, `LivingEntity.java:1446`).

## Verdict and limits

Source-only evidence supports extending the accepted mature EAST age-two cocoa witness backward through 1.8, 1.8.1, 1.8.2, 1.8.3, 1.8.4, 1.8.5, 1.8.6, 1.8.7, and 1.8.8. It covers only this age, direction, support arrangement, AABB, grounded +X request and directly exercised collision/step path. It does not cover other cocoa states, other mechanics, 1.9+, independent acceptance, or runtime parity.

