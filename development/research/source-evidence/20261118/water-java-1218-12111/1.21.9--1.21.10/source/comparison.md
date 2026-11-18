# 1.21.9–1.21.10 ordinary local-player water slice

## Evidence identity

- 1.21.9: canonical `decompiled_minecraft/1.21.9/mojmap/`; `.decompile-provenance.json` records official Mojang `client.jar` SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`, Mojmap `client.txt` SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`, and exact version metadata `1.21.9.json` SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`.
- 1.21.10: canonical `decompiled_minecraft/1.21.10/mojmap/`; `mojmap.ready.json` and `mojmap.provenance.json` identify version `1.21.10`, namespace `mojmap`, exact metadata ID `1.21.10`, and client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. `artifacts.sha256` records Mojmap `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39` and `version.json` SHA-256 `15faa7deb7cd39479db123ad4664e2fb83e05d4729b7e4a8ff813107376e349a`.
- Both sides are exact Mojmap decompilations. The 1.21.10 ready marker binds the source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; each consumed source file's bytes matched its side's source manifest/provenance entry.

## Bounded comparison

No source-level movement difference was found in the assigned ordinary-water path. The checked methods below have equal extracted-body SHA-256 on both sides; line offsets in `Entity.java` are five lines in 1.21.10. Body digests are SHA-256 of the complete method text joined with LF.

| Operation/member | 1.21.9 lines | 1.21.10 lines | Equal body SHA-256 |
| --- | ---: | ---: | --- |
| `Entity.baseTick` | 479–532 | 479–532 | `50a07d4226a16806ff0732ae90a4c7ee26c7396f14620f28b55ca9185d294c89` |
| `Entity.updateInWaterStateAndDoFluidPushing` | 1478–1484 | 1483–1489 | `170103f84b81e438d00e18715d52cc853c6177007fc63f8dff77d0985acb6257` |
| `Entity.updateInWaterStateAndDoWaterCurrentPushing` | 1486–1499 | 1491–1504 | `1809b1044ea1ae9505dfe6f469c3926884b1f6f2f5d1ac36d4a76870d13b649d` |
| `Entity.updateFluidOnEyes` | 1501–1513 | 1506–1518 | `5803e0bd3e472b31d482bf57d2254d777f7a4a2ddcb55541bc45d19fddf6d0ed` |
| `Entity.isEyeInFluid` | 1577–1579 | 1582–1584 | `d3e785c1fcf8a92d9eea2daabd8a22e9e30cfa6d67e651c68b0131cf9388a897` |
| `Entity.updateFluidHeightAndDoFluidPushing` | 3480–3545 | 3485–3550 | `19f09cab00ab6a74185009fa1beee254a3d967473420c8c5d7e040258ff0809d` |
| `Entity.getFluidHeight` | 3556–3558 | 3561–3563 | `d3eddf6850a86fe916e6e55cecece57633fd5cae7a8e72a766fb334a6a050eb8` |
| `Player.travel` | 1295–1318 | 1295–1318 | `c2bc073e777678b667db70b0e3c461c5ef4f1af07fd2c47d9926d280785d4336` |
| `Player.isPushedByFluid` | 1604–1606 | 1604–1606 | `13fb2d62b2981c0f2803af5acb9af8eacff8fc8f6c0f716f8a9d2dc6437315b7` |
| `LivingEntity.travel` | 2275–2284 | 2275–2284 | `38bccb06c9452247ec16ef8d951a0c5e4bda2f8565c407829334e872cef506e5` |
| `LivingEntity.travelInFluid` | 2331–2381 | 2331–2381 | `e40431dac625f80aef6a0cae371c7e2acce25eb9e054fcbbf101beeea1b80d1c` |
| `LivingEntity.getFluidFallingAdjustedMovement` | 2488–2501 | 2488–2501 | `9f4075c7368a54db8c030aafdfe92cc01bf801b44a35cacf6f9bfad5fc109265` |
| `FluidState.getHeight` / `getFlow` | 53–55 / 95–97 | 53–55 / 95–97 | `2d1471da9ba9eaa9d56f365495c8c6de552b6ce485fb0fa6efb4c56a67b775ab` / `95d91a7bd8e3c335cfa484cdf9ed6078c1af7311baa7e37910b50a52109a7d9b` |
| `FlowingFluid.getFlow` / `getHeight` | 55–100 / 439–441 | 55–100 / 439–441 | `fcd9e52021ea523cb5c6f30583eeca77914c7063c44f06df6898cc2930f77700` / `a526dc80720749ab4c28e6155141f2e04a4a2319809aef32f7b33236d268deca` |

The exact body-height/eye chain is `Entity.baseTick` → `updateInWaterStateAndDoFluidPushing` → `updateInWaterStateAndDoWaterCurrentPushing` / `updateFluidOnEyes`; `updateFluidOnEyes` samples `getEyeY()`, gets that position's `FluidState`, and compares the fluid surface (`BlockPos.y + FluidState.getHeight`) against the eye Y before adding its tags. `isEyeInFluid` reads that tag set. For the body, the water update passes `FluidTags.WATER` and scale `0.014` to `updateFluidHeightAndDoFluidPushing`; it scans the deflated bounding box, samples fluid surface heights, records max overlap, and queries each contributing `FluidState.getFlow` when `Player.isPushedByFluid()` is true.

Provider/consumer check: `FluidState.getHeight` dispatches to `Fluid.getHeight`; water's `FlowingFluid.getHeight` returns `1.0F` when the same fluid continues above, else `getOwnHeight()`. The sole direct `getFlow` consumer in the checked entity/fluid source areas is `Entity.updateFluidHeightAndDoFluidPushing`; `FluidState.getFlow` dispatches to the water `FlowingFluid.getFlow`, which samples horizontal neighbors and normalizes the resulting vector. These providers and consumers are unchanged by body hash, with unchanged `FluidState.java`, `FlowingFluid.java`, and `WaterFluid.java` file hashes.

Reachable equality witness: a non-riding local player in ordinary water, with abilities flying false, not sprinting or swimming, and equal effective fluid/block/tag inputs on both releases. `Player.travel` reaches `LivingEntity.travel`; the water gate selects `travelInFluid`. Under those conditions the player is fluid-pushed, body overlap/current sampling is identical, and the first water move has identical ordering: input-relative acceleration, self move, horizontal-climb correction, horizontal `waterSlowDown`/vertical `0.8F` drag, then the same fluid-falling vertical adjustment. No numerical trajectory claim is made beyond this equal-input source result.

## Consumed full-file hashes

These exact source-file SHA-256 values match their source manifests on both sides. `Entity.java` differs as a whole (`1.21.9` `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`; `1.21.10` `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`), but every scoped body above is equal.

| Relative source | 1.21.9 SHA-256 | 1.21.10 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | same |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | same |
| `net/minecraft/world/level/material/FluidState.java` | `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61` | same |
| `net/minecraft/world/level/material/FlowingFluid.java` | `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a` | same |
| `net/minecraft/world/level/material/WaterFluid.java` | `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674` | same |

## Residuals

This closes only the assigned ordinary-water local-player slice under equal effective world/tag inputs. Abilities-flight and its fluid-push suppression, sprint/shallow-water exemption behavior, other creative/Elytra paths, other fluids, geometry, damage, the rest of the pair inventory, and runtime parity remain outside this comparison. No positive finding was created; the source evidence supports bounded equality for the requested path.
