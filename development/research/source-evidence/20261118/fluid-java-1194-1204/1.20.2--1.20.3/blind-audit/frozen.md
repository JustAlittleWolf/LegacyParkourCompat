# Blind source audit: 1.20.2 to 1.20.3 fluid movement

## Scope and verdict

Exact Mojmap 1.20.2 and 1.20.3 sources were compared for four assigned local-player operations: water/lava body contact and stored fluid height; fluid-current push and its player normalization gate; eye-fluid update order; and ordinary water/lava travel drag and gravity gates. The paired `Entity` fluid methods and `LivingEntity.travel` are identical over the examined bodies. `Player.travel` differs only by removal of movement-statistics and food-exhaustion bookkeeping; its swimming prelude, flying dispatch gate, and `super.travel` route are unchanged. That bookkeeping is outside the assigned movement outcome, and no player position/velocity write depends on it.

Verdict: no source-confirmed movement delta in the four assigned operations for the same effective tags, world state, and player state. This is a bounded operation audit, not full pair coverage, intermediate-patch boundary, implementation acceptance, or runtime parity.

## Exact evidence identity

Host/source environment: Windows 11, PowerShell; primary source root `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only).

| Release | Source identity and linkage | Client and mapping inputs |
|---|---|---|
| 1.20.2, Mojmap | `1.20.2/mojmap.ready.json`: `status=ready`, `versionId=1.20.2`, `mapping=mojmap`, `clientSha256=fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; `sourceManifestSha256=e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. `mojmap.provenance.json` identifies versionId 1.20.2, namespace mojmap, exact mapping option and tool/runtime; `artifacts.sha256` pins `1.20.2/client.jar` to `fa1a19...` and `client_mappings.txt` to `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` (mapped Mojmap jar: `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`). | Client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; official client mappings SHA-256 `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`. |
| 1.20.3, Mojmap | `1.20.3/mojmap/.decompile-provenance.json`: mapped source provenance contains the exact 1.20.3 client, `client.txt` mapping and `1.20.3.json` metadata URLs below, plus hashes for every source. The manifest has no explicit top-level `versionId` or `namespace`, and there is no sibling ready marker; the exact version/mapping linkage is supplied by the version/mapping root path and the pinned 1.20.3 metadata/client-mapping inputs, so this is a labeling omission, not an unresolved source linkage. | Client SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`; client mappings (`client.txt`) SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`; version metadata `1.20.3.json` SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`. |

For each consumed source file, the on-disk SHA-256 equals its exact source-manifest/provenance entry:

| Relative source path | 1.20.2 SHA-256 | 1.20.3 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

## Compared operations

Method-body digests below are SHA-256 over the exact UTF-8 source span from the stated method signature through its matching closing brace (no trailing newline). Byte counts are included to make the digest contract reproducible. Identical paired digests mean byte-identical decompiled method bodies.

| Operation / source member | 1.20.2 range; digest (bytes) | 1.20.3 range; digest (bytes) | Result |
|---|---|---|---|
| Contact and height reset: `Entity.baseTick()`; `updateInWaterStateAndDoFluidPushing()`; `updateInWaterStateAndDoWaterCurrentPushing()` | `Entity.java:415-473`; `1186-1192`; `1194-1208`. Digests: `a34606c32f6dde7966b150e10ab93964a708715e9e947513d85d7d61819d3276` (1907); `8addbb453717c85be3813105536f0dff844cf7b4e240073080f5450f2535cd79` (375); `ab03229aa4dfe513e8c908643a8ee8220f7e2b23dcb47f006264e5334192d745` (562). | `Entity.java:416-474`; `1187-1193`; `1195-1209`. Same digests and byte counts, respectively. | Identical. `baseTick` calls fluid pushing before eye-fluid update and swimming update. Fluid heights are cleared; water is sampled/pushed first and lava second. The water predicate is stored in `wasTouchingWater`; the lava height is stored before `isInLava` checks it. |
| Current push and player normalization: `Entity.updateFluidHeightAndDoFluidPushing(TagKey<Fluid>, double)` | `Entity.java:3031-3096`; `db16eb13dd961b64586e039cf9996f983523802758ade7e5ffa9bfdfb2c20592` (2415). | `Entity.java:3037-3102`; same digest (2415). | Identical. Per-block flow vectors are summed and averaged when count is positive. `Player` explicitly skips `normalize()`; the coefficient and minimum-push condition then run in the same order. The movement interpretation applies when fluid pushing is enabled and the same flow states are present. |
| Eye-fluid state and query: `Entity.updateFluidOnEyes()` and `Entity.isEyeInFluid(TagKey<Fluid>)` | `Entity.java:1210-1222`; `1286-1288`. Digests: `4b17b52185947ec5fee2e1c1c3a042457b4b1078e504160cea85112163091067` (669); `1b90b24b77103513a951e279d90fa66542d9761f0a8aeb61fa6b039b41c245b0` (101). | `Entity.java:1211-1223`; `1287-1289`. Same digests and byte counts, respectively. | Identical. `wasEyeInWater` is assigned by querying the prior eye-fluid set; the set is then cleared and rebuilt from the current eye sample. `baseTick` preserves contact → eye-fluid → swimming order. |
| Player route to ordinary fluid movement: `Player.travel(Vec3)` | `Player.java:1450-1477`; `84f9a7396f451c13e59d82997a05d641faedf6ffa7b20a948951fb6f0c16021a` (1142). | `Player.java:1451-1473`; `45edd1c97c4167bf73456682c662aaebacdd5674eb553105422c2992e2edd8a3` (944). | Body differs only by removing pre-travel position snapshots and the post-travel `checkMovementStatistics(dx,dy,dz)` call. That callee awards movement stats and produces food exhaustion; it does not write movement state and is outside scope. The swimming adjustment and non-flying `super.travel` route are otherwise unchanged. |
| Water/lava travel drag and gravity: `LivingEntity.travel(Vec3)` (reached by `Player.travel`'s ordinary, non-flying branch) | `LivingEntity.java:2038-2169`; `ea21b381ac1bc1c84402ff0cbba504c1d2f21288354e8fcdbd99390b5e9c1291` (6335). | `LivingEntity.java:2035-2166`; same digest (6335). | Identical. Local-control gate, water predicate / affected-by-fluids / stand-on-fluid gates, water slowdown and depth-strider order, lava predicate / fluid-height threshold, drag, falling adjustment and no-gravity gate all match. |

Push-dispatch guard check: `Entity.push(Entity)` digests are `f21db61f124b2eddd6b0643d7fa207a979cf3959e4639c54161bdc601d4ea113` (1032) on both releases; `Entity.push(double,double,double)` is `85f53d8458e6b485be40dbfb87f4f5e971bb00c5381f0b4e57531e330179eb99` (167) on both; `LivingEntity.push(Entity)` is `96c85874f5236d61d488643f011302441335e79accf4c23c6febbed3c7829401` (109) on both. These wrappers are byte-identical; the fluid-current normalization gate above remains the player-specific current behavior.

## Reachability and dependencies

`LocalPlayer.java` is byte-identical between the releases and has no `travel` override; its `tick()` calls `super.tick()` (`LocalPlayer.java:188-207`). The ordinary local-player path therefore uses the inherited `Player.travel`, whose non-flying branch calls `super.travel`; `LivingEntity.travel` applies only when `isControlledByLocalInstance()` is true. A non-riding local player with the same state and world consequently reaches the compared water/lava gates. `Player.travel`'s swimming pre-adjustment is unchanged. Elytra and creative/flying travel branches are not evaluated here.

The fluid query body depends on the same effective fluid tags, sampled block-fluid states/flow vectors, bounding box and loaded-chunk state. Those inputs are held equal for this comparison; no packaged-resource or map inference was used. The Player-specific `instanceof Player` current normalization condition is present in the exact common source body. No missing caller or source-body dependency blocks this bounded verdict.

## Terminal verdict

Four assigned operations reviewed. No movement difference established for 1.20.2 → 1.20.3 within this bounded slice. `Player.travel` has a source-body change limited to movement-statistics/food-exhaustion bookkeeping. Exact intermediate release boundary, complete movement tick coverage, other assigned lanes, pair freeze, implementation, and runtime parity remain unreviewed.