# 1.8.5–1.8.9 supplemental independent review: resources and Bootstrap registration

## Verdict

Accept the bounded ordinary movement conclusion under equal effective player movement inputs and equal received vanilla block state. The focused source chain confirms the removed 1.8.5 command-block dispenser registration is a server-side world-state producer only; it does not change client movement response for equal state. Resource selection/reload and model loading terminate in client resource/rendering code in the examined source. The packaged resource boundary is verified through 1.8.8 only. Do not infer 1.8.9 archive/resource equality, full movement dependency closure, whole-game equal-initial-world evolution, or runtime parity.

## Exact inputs and identity checks

Read-only mapped sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`, exact release directories 1.8.5, 1.8.6, 1.8.7, 1.8.8 and 1.8.9. The 1.8.9 release-parent ready marker says versionId `1.8.9`, mapping `ornithe-feather`, 1,612 source files, and client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`. Marker `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather.ready.json` SHA-256: `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; provenance JSON SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

The actual 1.8.5 cache is `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.5/`. Its `client.jar` hashes to `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d`, matching `client.jar.pin.json`; `version.json` hashes to `4fe7e25babf0c16244675812332c1403f0a30f8dac03b9e7c9cfd40ba39c7597`, matching `version.json.pin.json`. This identifies the available early endpoint archive. I did not inspect archive contents anew; the prior correction/review reported the 1.8.5–1.8.8 entrywise resource inventory.

The cache has directories for 1.8.5–1.8.8 but no `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.9/` directory, `client.jar`, or `version.json`. Thus the exact raw client archive still needed for any 1.8.8→1.8.9 packaged-resource audit is `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.9/client.jar` (expected SHA-256 from provenance: `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`); the metadata companion, if entry validation is required, is `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.9/version.json`. No raw 1.8.9 client source/archive was found in the assigned canonical cache. The mapped/decompiled 1.8.9 Java source is present and is not the missing artifact.

Focused source hashes, independently recomputed:

| File | 1.8.5 | 1.8.6 | 1.8.7 | 1.8.8 | 1.8.9 |
|---|---|---|---|---|---|
| `net/minecraft/Bootstrap.java` | `ca472b6750729c52096750c93bb6449840d7ba3683971fc6834806187856df49` | `fb7cf74b1da59c01cc4c8429e7dc363beac0ca58781a3cc0cca0b40fd6ac3ea7` | same as 1.8.6 | `b162806ebe49cabf4eb94c041841b495389c1524e86e6040d8645b913647e550` | same as 1.8.8 |
| `net/minecraft/block/DispenserBlock.java` | `794e6780d586a16a31264d953037d53e46cf44e7ef85ab69aae126f6a1444c31` | same | same | same | same |
| `net/minecraft/world/IBlockSource.java` | `9311a0fa8521aaa260cde5cf1b5a60336848de7f5371435c3b33ae4e32b0d41c` | `2d67bd94abd2062befb25b08dbb529b6b64dca8df4bbe94f767dd47b4ea2a566` | same as 1.8.6 | same | same |
| `net/minecraft/world/BlockSource.java` | `b859797339c0a76ac2b6f80a85141c2ff0b8715b8ad11a6124648e3fd2cfe46f` | `d6d4293be4b357b5126caa8044ff111d70373294165168f86156f850068ff8b8` | same as 1.8.6 | same | same |
| `net/minecraft/block/CommandBlock.java` | `205f3ace007f83f6e3f6fff6ec4064111b48ed231fbb33032ec57b446b1b26fa` | same | same | same | same |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | same | same | same | same |
| `net/minecraft/client/resource/model/ModelManager.java` | `d50f88b9b4242bc5323b26e1424caedfa07473e01a5d6e65f7de22479b3d3c6e` | same | same | same | same |
| `net/minecraft/client/render/block/BlockRenderDispatcher.java` | `9d570953e4abb0dae10ba53da682750a7ceb659e6210f42a08af9add29733d03` | same | same | same | same |

## Registration trace and closure

1.8.5 `Bootstrap.registerDispenseBehaviors` registers `Item.byBlock(Blocks.COMMAND_BLOCK)` (Bootstrap.java:421–436). On an air target, the `!world.isClient` block writes `Blocks.COMMAND_BLOCK` with `TRIGGERED=false`, calls `BlockItem.setBlockNbt`, and calls `world.updateNeighbors(source.getPos(), source.getBlock())`; the stack decrement follows. `Bootstrap.initialize` calls `registerDispenseBehaviors` after `Block.init`, `FireBlock.init`, `Item.init`, and `Stats.init` (around lines 457–462).

The only `DispenserBlock.tick` route into `dispense(world,pos)` is within `if (!world.isClient)` (DispenserBlock.java:131–135); `dispense` obtains the selected behavior and calls it (lines 96–109). The inner registration handler again gates state/NBT/neighbor writes on `!world.isClient`. The handler is absent from 1.8.6 onward. The only source-tree `source.getBlock()` call is its neighbor update. The matching `getBlock()` interface/implementation methods disappear from `IBlockSource` and `BlockSource`. `DispenserBlock`, `CommandBlock`, and `Block` hashes are invariant across 1.8.5–1.8.9. Therefore the actual delta is a server-only behavior that can change later received block state; it is not a client physics delta with equal received state.

At 1.8.7→1.8.8, `Bootstrap` changes skull-owner parsing; source hunk is unrelated to block, item, or dispense registration. `Bootstrap` has identical hash in 1.8.8 and 1.8.9. No further registration delta is indicated by this focused chain.

## Resources and consumer route

The permitted prior correction records hash-pinned JAR entrywise comparison through 1.8.8: 3,085 assets each; path sets equal; the only asset content delta across the .5–.8 span is the six-line English resource-pack compatibility text in `assets/minecraft/lang/en_US.lang` from .8. No blockstates, models, block textures, sounds, shaders, fonts or other text assets changed in those archives. Its independent review corroborates those results. This review accepts that bounded result but does not reproduce its full ZIP inventory.

Mapped Java source shows the client resource/model route in 1.8.9: `Minecraft` creates/registers resource reload listeners; `ModelManager.reload` constructs `ModelBakery`, bakes the model registry and rebuilds the model-shaper cache (ModelManager.java:22–26); `ModelBakery` reads blockstate/model definitions; `BlockRenderDispatcher` consumes baked models to render block states. The `ModelManager` and dispatcher source hashes are equal across all five versions checked. This is evidence that model/resource definitions feed rendering in this code path. Together with the unchanged Java block collision/state/provider hashes in the prior bounded reports, no resource-to-movement route is identified for equal effective inputs and vanilla states. Since the 1.8.9 archive is absent, exact asset path/content equality for .8→.9 remains unknown and is not claimed.

## Bounded disposition

- Same-effective-input, same-received-vanilla-state ordinary movement conclusion: accepted within the limitations of the parent closure/review.
- Bootstrap command-block registration: closed as a server-only world-state producer; retain as an open boundary only if scope expands to equal initial worlds plus all server/non-player state evolution.
- Resource/archive equality: closed by prior verified inventory for 1.8.5–1.8.8; open for 1.8.8→1.8.9 because exact local client archive is absent.
- Full pair-wide movement dependency retrace and runtime parity: not established by this bounded review.

Next source queue opportunity: acquire and provenance/hash-verify the exact 1.8.9 client archive at the absolute expected cache path above, then perform only the bounded 1.8.8→1.8.9 resource entry/path/content comparison. Do not broaden the current same-state source verdict based on presentation asset differences alone.
