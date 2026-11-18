# Water movement source comparison

**Pair:** Minecraft 1.21.8 → 1.21.9, Mojmap. **Result:** no difference in the bounded ordinary, non-riding water-state, current-push, or water-travel procedures below; no canonical finding is warranted for this slice.

## Source identity

- **1.21.8:** `decompiled_minecraft/1.21.8/mojmap/`; version-root `mojmap.ready.json` identifies `versionId=1.21.8`, `mapping=mojmap`, `status=ready`, and client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. The ready-marker SHA-256 is `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`; its declared `mojmap.sources.sha256` digest matches the actual file: `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.
- **1.21.9:** `decompiled_minecraft/1.21.9/mojmap/`; `.decompile-provenance.json` SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its pinned client input is `client.jar` SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`; official Mojang `client.txt` mapping input SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3` (the exact-version metadata input is `1.21.9.json`). The provenance source-hash entries for every file consumed below match the file bytes.
- The 1.21.8 manifest and 1.21.9 provenance source hashes were checked for `Entity.java`, `LivingEntity.java`, `Player.java`, `LocalPlayer.java`, `Fluid.java`, `FluidState.java`, `FlowingFluid.java`, and `BlockBehaviour.java`. Relevant full-file SHA-256 pairs (old / new): `Entity.java` `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` / `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`; `LivingEntity.java` `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` / `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `Player.java` `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` / `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`; `LocalPlayer.java` `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` / `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`.

## Compared path

For an ordinary local player, `LocalPlayer.aiStep` delegates to `Player.aiStep`, then `LivingEntity.aiStep`; with simulation enabled and no controlling passenger, the latter dispatches `travel`. `Player.travel` applies the existing swimming vertical input adjustment when swimming, then delegates through `LivingEntity.travel` when not flying. Its non-riding wrapper, `isAffectedByFluids`, and `isPushedByFluid` bodies are unchanged. `LivingEntity.travel` selects fluid travel when the cached water predicate is true, the player is fluid-affected, and the current block fluid is not standable.

Each `Entity.baseTick` updates fluid heights and water current before updating eye-fluid state. It then refreshes swimming state. The water body-height writer, cached `isInWater` / `isUnderWater` predicates, eye scan, and `getEyeY = position.y + eyeHeight` are identical. The eye scan first reads the previous eye-fluid set into `wasEyeInWater`, clears the set, then samples the eye block and adds its fluid tags only when the fluid surface is strictly above the eye. In the ordinary non-boat case, both releases therefore retain the same update order and one-scan lag.

The water-height/current routine is identical: deflate the bounding box by `0.001`; scan floor-to-ceil x/y/z cells; accept matching fluid whose `cellY + getHeight` is at least the deflated box bottom; keep the maximum submerged height; and, when fluid-pushed, sample `FluidState.getFlow`. Each sample is scaled by the running maximum submerged height reached at that scan step when it is below `0.4`; vectors are summed and averaged by sample count. Player flow is not normalized. The result is scaled by `0.014`, raised to the `0.0045000000000000005` minimum in the stated low-horizontal-velocity gate, then added to delta movement. `FluidState.getHeight/getOwnHeight`, `FlowingFluid.getHeight/getOwnHeight/hasSameAbove/getFlow/isSolidFace/affectsFlow`, and the `blocksMotion` / `isFaceSturdy` predicates used by the flow sampler show no body change. For equal fluid/block states and world samples, this gives the same body-height and flow push.

The ordinary water branch in `LivingEntity.travelInFluid` is identical. It chooses horizontal slowdown `0.9F` while sprinting or `getWaterSlowDown()` (`0.8F` for a player), begins with input acceleration `0.02F`, halves water-movement efficiency off ground, applies efficiency interpolation and Dolphins Grace, then performs `moveRelative`, one self move, climbable vertical correction, horizontal/vertical multiplication by `(slowdown, 0.8F, slowdown)`, and fluid-falling adjustment. The player swimming pre-adjustment and `getFluidFallingAdjustedMovement` are also identical. Under equal position/bounds, fluid/world samples, eye height/pose, movement inputs, attributes/effects, and current sprint/swim state, the checked path has no differing operation or gate.

## Exact routine evidence

Method-body SHA-256 values below are computed over UTF-8 method text with LF line separators; paired values are identical.

| Operation | 1.21.8 lines | 1.21.9 lines | Body SHA-256 |
|---|---:|---:|---|
| `Entity.updateFluidHeightAndDoFluidPushing` | 3414–3479 | 3480–3545 | `19f09cab00ab6a74185009fa1beee254a3d967473420c8c5d7e040258ff0809d` |
| `Entity.updateInWaterStateAndDoWaterCurrentPushing` | 1431–1444 | 1486–1499 | `1809b1044ea1ae9505dfe6f469c3926884b1f6f2f5d1ac36d4a76870d13b649d` |
| `Entity.updateFluidOnEyes` | 1446–1458 | 1501–1513 | `5803e0bd3e472b31d482bf57d2254d777f7a4a2ddcb55541bc45d19fddf6d0ed` |
| `FlowingFluid.getFlow` | 54–99 | 55–100 | `fcd9e52021ea523cb5c6f30583eeca77914c7063c44f06df6898cc2930f77700` |
| `LivingEntity.travel` | 2245–2254 | 2275–2284 | `38bccb06c9452247ec16ef8d951a0c5e4bda2f8565c407829334e872cef506e5` |
| `LivingEntity.travelInFluid` | 2301–2351 | 2331–2381 | `e40431dac625f80aef6a0cae371c7e2acce25eb9e054fcbbf101beeea1b80d1c` |
| `LivingEntity.getFluidFallingAdjustedMovement` | 2458–2471 | 2488–2501 | `9f4075c7368a54db8c030aafdfe92cc01bf801b44a35cacf6f9bfad5fc109265` |
| `Player.travel` | 1438–1461 | 1295–1318 | `c2bc073e777678b667db70b0e3c461c5ef4f1af07fd2c47d9926d280785d4336` |
| `Player.isAffectedByFluids` / `isPushedByFluid` | 1033–1035 / 1783–1785 | 886–888 / 1604–1606 | `a252c2c24d2d4ba37a2ae793cd04839256969a5ab90d5f1078ee784f24faa704` / `13fb2d62b2981c0f2803af5acb9af8eacff8fc8f6c0f716f8a9d2dc6437315b7` |
| `FluidState.getHeight` / `getOwnHeight` | 53–55 / 57–59 | 53–55 / 57–59 | `2d1471da9ba9eaa9d56f365495c8c6de552b6ce485fb0fa6efb4c56a67b775ab` / `0b5336d5aa754191e1a1afbe4d2429c457cd8555f99c768c0258e47bea5efc9b` |
| `FlowingFluid.getHeight` / `getOwnHeight` | 434–436 / 439–441 | 439–441 / 444–446 | `a526dc80720749ab4c28e6155141f2e04a4a2319809aef32f7b33236d268deca` / `43c5d00ea7c11dab1093e3745b43c783b38b6f19a30a0ddb2ff0f1845b79566c` |

## Boundary and residuals

The surrounding `Entity.baseTick` differs in unrelated fire handling, and `LivingEntity.baseTick` / `aiStep` contain changes outside the water procedures (including non-movement drowning handling). The water-state calls remain in the same order, and the jump-to-travel gates inspected are unchanged. `LocalPlayer.aiStep` also changes sprint-trigger arming from literal `7` in 1.21.8 to the 1.21.9 sprint-window option; this is upstream input-state behavior, so the travel comparison above conditions on the same current sprint state. The separately excluded abilities-flight/run-sprint shallow-water exemption, other creative/Elytra paths, riding/boat paths, lava, and unrelated collision/damage behavior are not findings from this assignment. No runtime, game, or build verification was performed.
