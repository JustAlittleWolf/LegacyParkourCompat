# 1.21.1 → 1.21.2 player pose and dimension source closure

## Scope and verdict

This bounded comparison follows the local-player pose and dimension path: player tick call order, pose selection and fit gates, player dimension declarations, cached bounding-box/eye-height refresh, and local-player consumers that read the resulting dimensions. The assigned source-only coverage audit was `.native-source/20261028/frontier-c/1211-1212-full-audit/audit.md` (SHA-256 `5fceab4b21a1e1d4d6e70a4bb7e75fab43a308efd421aa519af2cd0fd68b6c93`).

I found no pose-selection or dimension-value delta in these paths. `Player.updatePlayerPose`, its collision-fit predicate, the player pose-to-dimension table, `LivingEntity.getDimensions`, and the local-player width/height/eye-height consumers have identical method or data-source hashes. `Entity.refreshDimensions` has one textual change from `4.0` to `4.0F`; its compared operands are floats, and the branch that could adjust an entity's position expressly excludes `Player`. No local-player position change follows from this difference. This is not a full-pair freeze, movement-parity finding, or closure of collision providers.

## Source identity

Canonical read-only root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release | Identity evidence |
| --- | --- |
| 1.21.1 Mojmap | Release-parent `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.ready.json` `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.sources.sha256` `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; `artifacts.sha256` `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. The ready record names version 1.21.1, Mojmap, 5,363 sources, and client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`. The artifact manifest pins `client_mojmap.jar` (`6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe`) and `client_mappings.txt` (`140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`). The parent provenance records the exact decompile batch and tool versions; it does not itself contain a raw version-metadata input map. |
| 1.21.2 Mojmap | Mapping-root `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; its embedded source map contains 5,654 paths. Its input map pins `1.21.2.json` SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`, client JAR `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, and `client.txt` `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. The release parent contains the mapping directory only; the embedded mapping-root provenance, rather than a parent ready marker, binds these bounded source claims. |

The six selected Java source files were rehashed and matched their respective source maps/sidecar entries:

| Path | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` |
| `net/minecraft/world/entity/Pose.java` | `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca` | `85c714072555d18f9bfac2e2a70460bd2c69318e11122e8dbc8c7814a7019fca` |

## Pose selection and update order

`Player.updatePlayerPose()` has the same exact method-body SHA-256 at both endpoints: `1874d3b4fe473a84fd95328271d08579ff80179835eab6957cfc25cfe7247cc2` (`1.21.1:401–428`; `1.21.2:431–458`). It first requires that the player fit the swimming box. It then selects, in order, fall-flying, sleeping, swimming, spin attack, crouching when sneaking without flight, and standing. It accepts that target if spectator, passenger, or fit; otherwise it falls back to crouching if that box fits, and swimming otherwise. The outer gate, selected-state order, and fallback are unchanged.

`Player.canPlayerFitWithinBlocksAndEntitiesWhen(Pose)` also has identical method-body SHA-256 `62ac4e2bbf754c5668843b5d547fad0c2f0908e707ef525a63023f160b4c3220`. It tests the proposed pose dimensions' box at the current position, deflated by `1.0E-7`, through `Level.noCollision`. The exact call is unchanged; the collision query implementation, shape providers, and geometry-specific reachability are outside this slice.

In both versions `Player.tick()` calls `super.tick()` before its player-specific pose update and invokes `updatePlayerPose()` after `cooldowns.tick()`. The complete `Player.tick()` method differs for unrelated bookkeeping, so this report does not treat its whole method as identical. Within `LivingEntity.tick()`, both versions refresh dirty attributes, read `getScale()`, and call `refreshDimensions()` if that scale differs from `appliedScale`; 1.21.2 appends an elytra-animation tick after this scale/dimension phase. Thus the dimension refresh remains before the later `Player.updatePlayerPose()` in both versions. `Player.getDefaultDimensions(Pose)` has identical method-body hash `17ce84bc7b9d09dbb1432d0627b5e6801fe7ce78506846ca37be5fe3d1cc6305`.

The `Player` static pose/dimension declaration block has identical SHA-256 `8761ba19f73e3e3f853d3228cf9bc9868c6b3f599bc465d61e16fc8b22f2d486`. It maps standing to 0.6×1.8 with 1.62 eye height; fall-flying, swimming, and spin-attack to 0.6×0.6 with 0.4 eye height; crouching to 0.6×1.5 with 1.27 eye height; dying to 0.2×0.2 with 1.62 eye height; and sleeping to the unchanged `LivingEntity.SLEEPING_DIMENSIONS` constant (0.2×0.2 with 0.2 eye height). The source-backed values and pose keys are the same.

## Dimension refresh and local-player consumers

`Entity.setPose(Pose)` is identical (body SHA-256 `191b1317ee9cb9864d2072320c05018997af02574539ebac760e232b3a6774fa`): it writes `DATA_POSE`; it does not itself refresh cached dimensions. `LivingEntity.getDimensions(Pose)` (SHA `22e827a2143de260506ef118dccda1ea11cf5ab1365f57597dc4e6b28716321c`) returns the sleeping constant or the player's default pose dimensions scaled by `getScale()`. `Entity.fixupDimensions`, bounding-box construction, reapplication of position, eye-height accessors, and `EntityDimensions`/`Pose` source files are equal across the endpoints.

`Entity.refreshDimensions()` has different method-body hashes (`1.21.1 ccbc184429d176bc408b303867eec6631bdfd9746687a1ca30ce84d32bbc4057`; `1.21.2 edb5f8b2ed8c232d806a8924d22297e0239b0dcd1a2ac06d25e3d173b0d6e323`) because its size-safety comparisons changed from `width() <= 4.0 && height() <= 4.0` to `width() <= 4.0F && height() <= 4.0F`. `EntityDimensions.width()` and `height()` return floats; the double literal promotes each float exactly, and 4.0 is exactly representable in both formats, so the comparisons have the same outcome, including non-finite values. The rest of the method still refreshes cached dimensions and eye height, calls `reapplyPosition()`, and retains the same gates. In particular, the later position-fudge branch requires `!(this instanceof Player)`, so it cannot reposition the local player in either version.

The checked dimension consumers are source-identical:

- `LocalPlayer.updateAutoJump(float,float)` (method SHA-256 `f90bdb8d9803725e49921b8087f97d531d59308974c4eec214b5a8bee5d59fa6`) reads the current bounding-box top and player width/height when making its swept candidate box. `LocalPlayer.canAutoJump()` is also unchanged (SHA `413197fc8bd28cc2345b66b12dd8c2546c41d0628eb4d444a102411f18f908a5`). Collision aggregation and individual block shapes remain open.
- `LocalPlayer.suffocatesAt(BlockPos)` uses the current bounding box's min/max Y; its body hash is `c2295f94d9fe76ac19944f25ca43b8f7a5a688be8df79fd5b7cdd1003bf7b51f` on both sides.
- The four `LocalPlayer.aiStep()` `moveTowardsClosestSpace` calls use `getBbWidth()` to form the same four offsets. `Entity.moveTowardsClosestSpace` has identical body hash `65883eed4fd30307516309b1a8fd38a4ff88b481fab2a9c3349261328a8fbea2`.
- Eye/fluid accessors `Entity.getEyeHeight(Pose)`, `getEyeHeight()`, `getEyeY()`, and `isEyeInFluid(...)` have identical body hashes (`00dd59243ad3e2dcacc1f0dcd1379e8c80cf6b74667c5f2ea975b6de6de34746`, `5dea084fed841be7ba38a87e4a9541d60cef3f2497020912e37c31767f3d932c`, `dd37066339a8986baac3bf97aae3c3317319dfcbf4e7b89f57ee4a2f35a9fc56`, and `1b90b24b77103513a951e279d90fa66542d9761f0a8aeb61fa6b039b41c245b0`). They continue to read the same cached eye height and use the same query order.
- `Entity.makeBoundingBox()` and `reapplyPosition()` are unchanged (hashes `e9ced3348fb0bb66ea70ef3f170b76a17c603216ead0c13668f627057bfa623b` and `0936844f00abbc229f417f42ad4cd9b8b9643d39bade41822f3f1b514a9eb4e6`). `getBbWidth()` and `getBbHeight()` are unchanged (`3ae12d5903747897ccb54069af06d7c25f9717af4a7d3371838fc642a6eedb3f` and `320c02e5f49adb90c60e696344a19d3965166d18494e4d8498c4f692d5bec926`).

The relevant geometry and state inputs are not all closed here. This comparison establishes source equality of the pose-selection/dimension path when the same pose flags, scale, position and collision-query results are supplied. It does not establish that those upstream values or collision answers remain equal across the full pair, nor does it trace related fluid-travel, entity-movement, callback, or packet changes.
