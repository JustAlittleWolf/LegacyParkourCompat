# Independent source verdict: water-travel climb impulse

**Verdict: ACCEPT** for the bounded claim that the post-move, horizontal-collision + climbable-predicate Y assignment appears in the water-travel branch by 1.14.0 and is absent in the preceding inspected releases. The exact onset within the uninspected 1.13.3 interval is not established. No blocking evidence gate remains for the assigned claim.

## Immutable inputs and source identities

The supplied immutable inputs were verified before reading: `comparison.md` SHA-256 `c3b354689d19e917d9ac5c12cd96b54df868223a78b5ae0af0b6cd8432ed1f38`; `findings/1.13.2--1.14/water-travel-climbable-impulse.md` SHA-256 `dc5e23b20c68877934d66f5d1a659235934701e71a71100f256682aa91beaa33`.

The nine-source roster was frozen at `source-roster.json`, SHA-256 `3b345c0e501c86f3952376ea1391bf9f1a4cd2617613b0efe523afa593dbf80f`. For its listed trees, every source file hash was checked against its source manifest (zero missing and zero mismatches). The finding's endpoint files and the comparison's continuity files also match their pinned manifests:

| Release / mapping | Manifest SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|
| 1.12.2 / Ornithe Feather | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13 / Ornithe Feather | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13.1 / Ornithe Feather | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228269c932d3abba` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 / Ornithe Feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14 / Ornithe Feather | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| 1.14.1 / Yarn | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| 1.14.2 / Yarn | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| 1.14.3 / Yarn | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.4 / Mojmap | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |

The 1.14.1–1.14.3 hashes above come from the hidden `yarn/.decompile-provenance.json` source maps; all 3,155 / 3,155 / 3,159 source hashes verify. Those manifests include exact Yarn mapping metadata/artifact inputs and client artifact identities. The supplied comparison incorrectly says these local Yarn source identities are unavailable. The 1.14.4 source identity cited in that comparison is Mojmap, not the Feather tree used in the frozen nine-entry roster; the cited Mojmap tree was independently verified above.

## Trace and reachability

- 1.13.2 Feather `LivingEntity.moveRelative`, lines 1617–1655: the water branch moves, multiplies Y by `0.8F`, applies its gravity correction, and raises Y to `0.3F` only when horizontal collision and `canMove(... + 0.6F ...)` both pass. There is no `isClimbing()` check in that branch. The same absence/clearance-only response was checked in 1.12.2, 1.13, and 1.13.1.
- 1.14 Feather `LivingEntity.moveRelative`, lines 1897–1920: after movement, horizontal collision plus `isClimbing()` replaces Y with `0.2`; water damping and gravity handling follow; the existing `0.3F` clearance response remains later. The same new response was checked in 1.14.1–1.14.4 (Yarn `LivingEntity.travel` lines 1884/1890/1900 by patch; Mojmap `LivingEntity.travel` lines 1900–1922 for 1.14.4).
- Normal players reach the living branch: 1.13.2 `LivingEntity` calls virtual `moveRelative` at line 1926, and `PlayerEntity` delegates to `super.moveRelative` at lines 1463–1464 when not in its flying override. 1.14 does the corresponding call/delegation at `LivingEntity` line 2220 and `PlayerEntity` lines 1386–1387. In 1.14.1–1.14.4, `LivingEntity` dispatches `travel` at lines 2204, 2210, 2220, and 2223 respectively; the corresponding player overrides delegate to `super.travel` at lines 1379/1386, 1379/1386, 1381/1388, and 1397/1404 when not flying. This establishes a source-call path; it does not rely on a predicted trajectory.
- The climb predicate samples the player's feet cell and returns true for ladder or vine. It also returns true for a trapdoor only when it is open and the block below is a ladder with the same facing (1.12.2 `LivingEntity.java` lines 1006–1029; 1.13.2 lines 1046–1068; 1.14 lines 1232–1257). Therefore an open, waterlogged trapdoor with a matching ladder directly below is a constructive old-feature witness for a non-spectator player whose feet sample that cell and whose bounding box is in water; a separate adjacent obstacle can supply horizontal collision. 1.13.2 `TrapdoorBlock.java` lines 29, 40, 74 and 131–143 confirm waterlogged vanilla states are available. A low ceiling can reject the later +0.6 clearance candidate while leaving the 0.2 response path reachable.
- The 1.14 predicate additionally admits `Blocks.SCAFFOLDING` (`LivingEntity.java` lines 1237–1241; `Blocks.java` line 1902). That block is absent from the 1.13.2 `Blocks` source. Scaffolding is thus a valid 1.14-era provider, but not a valid witness for pre-1.14 profiles; the trapdoor-over-matching-ladder witness avoids that age error.

## Consumption, precision, and limits

The assignment is a double literal `0.2`; the following `0.8F` factor is a binary32 float widened to double for the `Vec3d` multiplication. Thus the new path produces approximately `0.1600000023841858` after damping, not an exact binary64 `0.16`. With default gravity and a non-sprinting player, the subsequent `d / 16.0` correction gives approximately `0.1550000023841858`; the retained clearance response can replace that with widened `0.3F` (`0.30000001192092896`). Sprinting skips this gravity adjustment. The Y impulse is therefore intermediate velocity, not the final post-travel value.

The inspected releases establish absence through 1.13.2 and presence by 1.14.0, then continuity through 1.14.4. They do not establish whether an uninspected 1.13.3 release contains an earlier introduction. The water-admission predicate spelling differs in Yarn 1.14.1–1.14.3 (`isInsideWater`), so marginal fluid-contact equivalence is not part of this acceptance; an ordinarily submerged player path is established.