# Independent source-only verdict: local sprint chunk gates

**Verdict: ACCEPT, bounded to the assigned 1.14.3→1.14.4 ordinary local-client chunk-availability guards.** The frozen comparison is accurately supported on this claim. This is not a claim of complete pair coverage, full movement parity, or equivalent fluid-state lookup for missing client chunks.

## Bound input identity and machine checks

- Frozen input: `c-input-chunk-gate-source/comparison.md`; SHA-256 `fa15e0bb61f4726cf5f18dde252c0920aac8f7ab34f3911f1e6d41e575d26b92` (matches assigned expected SHA `fa15e0bb61f4726cf5f18dde252c0920aac8f7ab34f3911f1e6d41e575d26b92`).
- 1.14.3 Yarn: provenance embedded at `decompiled_minecraft/1.14.3/yarn/.decompile-provenance.json`; SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. Its source records identify 1.14.3 and input Yarn artifact `yarn-1.14.3+build.13-v2.jar`. The six audited Java files below match embedded source hashes.
- 1.14.4 Mojmap: publication sidecars are at the **version root**, not inside `mojmap/`: `decompiled_minecraft/1.14.4/mojmap.provenance.json` (SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`), `mojmap.sources.sha256` (SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`), and `mojmap.ready.json` (SHA-256 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`). The ready marker's source manifest digest is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` and matches the computed manifest hash. Six audited Java files match the source manifest. The source publication is 1.14.4 Mojmap. The 1.14.4 provenance is the published sibling JSON; there is no embedded `.decompile-provenance.json` in the Mojmap source directory.

| Release / mapping | Relative source | Computed SHA-256 | Matches provenance/manifest |
|---|---|---|---|
| 1.14.3 Yarn | `net/minecraft/world/ViewableWorld.java` | `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d` | `True` |
| 1.14.3 Yarn | `net/minecraft/world/chunk/ChunkManager.java` | `06bebe0a01ffd7a80c5508eb2fcd850543e1ce71b34ea33d58fcf7b56a7c7120` | `True` |
| 1.14.3 Yarn | `net/minecraft/client/world/ClientWorld.java` | `9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2` | `True` |
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `True` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | `True` |
| 1.14.3 Yarn | `net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | `True` |
| 1.14.4 Mojmap | `net/minecraft/world/level/LevelReader.java` | `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` | `True` |
| 1.14.4 Mojmap | `net/minecraft/world/level/chunk/ChunkSource.java` | `98fa075980543e2a495e5d421bd2ceed17b98a94097902f1601b5aa8060a2beb` | `True` |
| 1.14.4 Mojmap | `net/minecraft/client/multiplayer/MultiPlayerLevel.java` | `3d31b9e969da5a4c79c49987648c921ddd30da1946e4fae5a3dec7f62252bf10` | `True` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `True` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `True` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `True` |

## Source verdict

- The shared ViewableWorld.isAreaLoaded and LevelReader.hasChunksAt methods use the same vertical test (maxY >= 0 && minY < 256), right-shift both X/Z endpoints by four, and iterate min through max chunk coordinates inclusively. On corresponding inputs, the guard logic agrees, including negative X/Z chunk selection and terminal chunk inclusion.
- Dispatch for a local client world resolves to ClientWorld.isChunkLoaded in 1.14.3 and MultiPlayerLevel.hasChunk in 1.14.4; both return true unconditionally. So an incompletely available client chunk does not make either point or area guard reject. The eye-point checks in Entity.isSubmergedIn(tag, true) / Entity.isUnderLiquid(tag, true) use those same overridden predicates. The base manager/source methods do check getChunk(..., FULL, false) != null, but are not selected by these local-client overrides.
- These facts support only admission at the examined guards. After admission, the AABB fluid scan still asks the world for fluid state; this audit does not decide what every missing client chunk returns there. I do not infer the absence of other chunk-dependent or fluid-flow parity differences.
- Sprint input reads water state (isInWater/underwater predicates) through the player tick paths, so the ordinary local-player context is relevant. This verdict is only about the matching chunk-admission guard behavior at those sources; it does not reopen or decide the separately owned water contact producer or eye-sample math claims.

## Storage/citation correction

The frozen comparison says the 1.14.4 source sidecar is not present in the current canonical directory view, while citing a previously recorded manifest digest. That path statement is stale/incorrect: the canonical checkout currently contains the published 1.14.4 Mojmap sidecars alongside the `mojmap/` directory at `decompiled_minecraft/1.14.4/`. The computed `mojmap.sources.sha256` digest matches both `mojmap.ready.json` and the previously recorded digest. Cite the root-level sibling sidecar paths above. The absent file is only the embedded `mojmap/.decompile-provenance.json`; use the sibling `mojmap.provenance.json` as the publication provenance.

No product, shared research, backlog, wiki, implementation, runtime, build, or test files were changed; only this assigned audit verdict was written.

