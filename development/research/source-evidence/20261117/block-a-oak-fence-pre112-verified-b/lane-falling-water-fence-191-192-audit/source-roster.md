# Frozen source/provenance roster — 1.9.1 → 1.9.2

Frozen before inspection of the assigned author comparison. Host and execution environment: Windows 11, native Windows PowerShell; source checkout read-only.

## Exact source identities

Both source roots are `decompiled_minecraft/<version>/ornithe-feather/`, generated with the repository `buildSrc` decompiler pipeline `lpc-decompile-v1` (single thread, generic/ascii output; Vineflower 1.12.0; Java 25). Each provenance manifest enumerates 1,819 source files. Frozen manifest SHA-256:

- `1.9.1/ornithe-feather/.decompile-provenance.json`: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`
- `1.9.2/ornithe-feather/.decompile-provenance.json`: `9928b467de b35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` (normalized: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`)

Pinned mapping inputs are Ornithe Feather Gen2 build.2 mergedv2: 1.9.1 artifact SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; 1.9.2 artifact SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`. Raw Mojang client SHA-256: 1.9.1 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`; 1.9.2 `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`. Manifest metadata responses are pinned separately. Sources are mapped in compatible Feather naming families; no online mappings used.

## Pair-wide changed source inventory

Manifest comparison found 21 differing source hashes out of 1,819 paired paths. This is the complete changed-file inventory; only `PlayerEntity.java` lies near player movement, and its sole diff is the saved NBT `DataVersion` constant (175 → 176), outside scope. Remaining files are out-of-scope metadata, UI, networking, persistence, and server changes. No source-level movement candidate is evidenced by this pair-wide changed-file set.

| File | 1.9.1 SHA-256 | 1.9.2 SHA-256 | Disposition |
|---|---|---|---|
| `net/minecraft/client/gui/overlay/DebugOverlay.java` | `980f60c0d701ceaa15a7853cff57e56c7c328a15938e9b635004b4e729f5bcab` | `9532286eb39f216cd7c2319bf4655ae685e866832ce166e95cee05f8fa4ddf0d` | UI; excluded |
| `net/minecraft/client/gui/screen/menu/multiplayer/ConnectScreen.java` | `8e323b9beb78eda6a46880a4a2373f1b33d78c235245e8d8d64d14770f9b507d` | `b6e35bc02c7d2ca1bc4a49767b26e8ab4b235e0e5f587f8ab03d78de22a52e72` | network UI; excluded |
| `net/minecraft/client/gui/screen/menu/TitleScreen.java` | `fdd41510a23542beabb010179a74d5ce69a87eb9b4575e90cd2c702e5cbdb48e` | `075351b2259f393d89e86be7d1f92a0bdfa038c9c71df6ff87a580dc184e9a64` | UI; excluded |
| `net/minecraft/client/gui/widget/ServerListEntryWidget.java` | `d044e089c3ba4250057d3952b940dfd5b7a3b29ea5e9a40fd3c0652566242238` | `c2b3f99622182f168d64eda978dae5b2dfef143d322fd455152081fc37f0d37c` | server-list UI; excluded |
| `net/minecraft/client/Minecraft.java` | `f6b11d65fc630fa9ca5f462c0c14d04eed488e3aba2bc3ad2b6e6b2c3eb17a0b` | `9f90629608f2b120427e608668cbc6900b1fc56b8f433f8591272f5e82ae2056` | client orchestration; no player movement path in pair delta |
| `net/minecraft/client/network/MultiplayerServerListPinger.java` | `b25012e621ecb4f840f35594aab3dcb0a8e90eee0f3dc13b078eff43b541f43b` | `d136c427d70b10a5885f2113e32c3f63de4f58781fe00716e616025c49ab343b` | network; excluded |
| `net/minecraft/client/options/ServerListEntry.java` | `79c67e9318c435516a248b8d4fdb6783efe6dbd5a12419f18076993d107a918b` | `fd22113fb7d0f984323b230591f9eafbb5f9cc693e95ae33cf221542e35bd073` | server-list persistence; excluded |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | saved NBT `DataVersion` only; excluded |
| `net/minecraft/network/PacketByteBuf.java` | `b12848a6453b72d8a916bcc028a396e3faff72bd2cb0a6e8dfab4e24429bc472` | `4511dafe34b6e7628920422025fda4beeec29daa7ab466b3a5638e5a5c73d696` | networking; excluded |
| `net/minecraft/realms/RealmsConnect.java` | `f0328be5a59c7dc79e6b1e15270bc08ced767f00b80e6200284b5c70df4c52d3` | `26f70e12f4f93487aa2d9aa6528d0604ac1c4df1ccda00903407a75378c5033a` | network; excluded |
| `net/minecraft/realms/RealmsSharedConstants.java` | `b5ec9099baf1d601fe332924622c69dc75942224a282f0ab24dd3e770711b81d` | `aa8e7b3d49b4af2190bc18def91a2ee5973547b76a2e87f83b775004c4bf3bad` | metadata; excluded |
| `net/minecraft/server/integrated/IntegratedServer.java` | `e31c82d23c772a81aa7d11831bfc1c483c6973c4e24d87f3d70f86d0c120bb2c` | `cac5e3df729085ef70330418eb912bebf006a25d610906f553a247bbdbd84a08` | logical server; excluded |
| `net/minecraft/server/MinecraftServer.java` | `2dae0b584626e34b7bbf1e974bbd920ee862a04db879f269a3c6e02f78636865` | `af664a571138394fb7807e748b54e2b0102dfe1f2dd3054a28399dc683e9cf8c` | server; excluded |
| `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler.java` | `6af655f7eb617b5d5d41bbfc5cf44eb938e22b155958cde404c15528398c9d1d` | `7fc2351d40fb06dcd105def003e415a60deac1c08d0b164964d93adae7b94a7e` | networking; excluded |
| `net/minecraft/snooper/Snooper.java` | `5b9335dc780682ce687d1495eb012d25cd69f439d0d318816193eb9a3f67cda9` | `c9d281f38bdd660d9cfd4e3ec5ac114ee73ebd7e55d1bf81f219ea05506cc672` | telemetry; excluded |
| `net/minecraft/util/crash/CrashReport.java` | `12b1cd4e85e81117a071272577c90cf956262b9ec9e1ec2745b1fbb9d5b18b49` | `9ec83f54d4ad71d725d4738928e2ff1b1989fe07a1b82c87d56390901177b58b` | crash reporting; excluded |
| `net/minecraft/util/datafix/DataFixerUpper.java` | `89509823a5743c1b026f3400e6e53bb771249a13438362ff5e3c46c00eb181b2` | `663bfb40e48d6ff7f3967fea9eb8f36cefa00cd26f6be334b1dc66587f75b75f` | persistence/datafix; excluded |
| `net/minecraft/util/datafix/DataFixes.java` | `6e9b9b36768dc5a3793bbf89831bca487f4e5c3a429e8848803162b5c6eda34d` | `54c93c81ab1d8b304d99d0226ab0e93e95ea258b95ec0ca39950cb9dd6b5f008` | persistence/datafix; excluded |
| `net/minecraft/world/chunk/storage/AnvilChunkStorage.java` | `80bc15e6d27509df9ed73e0173f212160ad8d2b9b41bf1fa83defec532c10a1b` | `5eec6b54b44b02ae70cde487b118b5ffbca05db495547f9fcb41e0aac33fcc84` | persistence; excluded |
| `net/minecraft/world/storage/WorldSaveInfo.java` | `f7a41117745df54772d908c27bfa158d6fbe931a1806b02f3b41cee7fbe265cd` | `44e4eb6fda3f9f4bee408342c4cf1ff9a2f92b6465612a861ccc82ac03cebd24` | persistence; excluded |
| `net/minecraft/world/WorldData.java` | `11396d093d07e6752ed1fd614976b8b81aa32a7d557e828325332af3e28b2fee` | `935afb9d4df9c71128978822e5a434a6c8e74d9cec52cf3e0c7c750653d24f16` | persistence/world metadata; excluded |

## Paired movement trace roster (all listed files have identical recorded source hashes on both sides)

| Relative source | SHA-256 | Planned slice |
|---|---|---|
| `net/minecraft/block/FenceBlock.java` | `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` | ordinary fence material, shape, `isCube`, `isSolidRender`, side connection |
| `net/minecraft/block/Blocks.java` | `ae6e28482364703673c89e33d885ef30d7c7421ab5a0e0a0c2d4e656600bb352` | oak fence registration/material |
| `net/minecraft/block/LiquidBlock.java` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` | `getFlow`, falling-liquid side/above cue, normalize and apply drag |
| `net/minecraft/block/Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` | material block predicates and `isFaceSolid` |
| `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` | oak material `blocksMovement` / `isSolid` semantics |
| `net/minecraft/block/FlowingLiquidBlock.java` | `66dce05969a86a163965a3e473e3e00b14e0da7dacffb4c75064c13605e3511b` | flowing-state/depth source and `LEVEL >= 8` semantics dependency |
| `net/minecraft/block/state/BlockState.java` | `9168e488472aa91bf30b01c4a6dce912cbd5ee312cfda65a89f753748b199476` | state property and liquid shape/depth dispatch |
| `net/minecraft/block/state/AbstractBlockState.java` | `a42fa7e1a7909a0a48937a4aec8ec05094e350d8e17fddd4d5ea9636d794cd4a` | shared state predicates |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | water contact and movement/material-drag call chain |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | player travel, water flow application, loaded-chunk checks |
| `net/minecraft/entity/living/player/PlayerEntity.java` | changed hashes above | flying/riding branch and player movement predicates; movement methods compared directly despite unrelated NBT-only diff |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | local tick loaded-chunk gate and nonflying movement cues |
| `net/minecraft/world/World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | loaded-chunk and state access |
| `net/minecraft/world/WorldView.java` | `212c5386769dce8f12c06cf8fe9fed6f931bda8cd519239a20bc894a4c1f2fd8` | state-view access and block side predicates |
| `net/minecraft/util/math/Vec3d.java` | `9c1c6438c2c7f87d78d12d80cc58a6c03644eabf51edf8d8b56bcf61b32afe1a` | vector normalization numeric path |
| `net/minecraft/util/math/Direction.java` | `03ef526e1f5c0a4d4a2a47263bb849665780e69ffb3d59b42368faa60ea61a1f` | horizontal neighbor offsets/iteration order |
| `net/minecraft/util/math/BlockPos.java` | `a5a85ad3abbd0979cb61d856a25b1d224f5b40bbfcb6d4bebaba6a104020c19c` | pooled mutable neighbor positions |
| `net/minecraft/world/chunk/WorldChunk.java` | `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267` | chunk-loaded predicate support |

This frozen roster covers the assigned narrow trace only: dry ordinary oak fence beside falling water (both horizontally adjacent side cue and same-column above cue), normalized flow, the client movement consumption path, and LocalClientPlayer loaded/nonflying gates. It does not claim full-pair movement coverage.