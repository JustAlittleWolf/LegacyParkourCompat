# Body-fluid admission: exact 1.14–1.15 stable cutover check

## Result

The known 1.13 local-water admission continues in every requested stable release: 1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4, 1.15, 1.15.1, and 1.15.2. No rejecting release occurs in this bounded interval; the first exact stable rejection is not established here.

The witness is the already accepted client-side player condition: ordinary 0.6-wide player centered at `(15.7, 64, 8)`, with WATER at `(15,64,8)` whose surface is above the contracted box bottom, chunk 0 loaded and adjacent chunk 1 absent. For X, the 0.001-deflated bounds are approximately `[15.401, 15.999]`, so `floor(minX)=15`, `ceil(maxX)=16`. The area preflight checks chunk endpoints after right-shifting both 15 and 16, hence chunks 0 and 1 inclusively. The subsequent body-cell loops use strict `<` upper bounds, so they inspect x=15 only. The candidate sample is in the still-loaded chunk; no fluid read from the absent neighbor is needed. The same bounds apply across the requested releases, whose helper uses `getBoundingBox().deflate(0.001)` and floor/ceil conversion.

| Exact release | Source/mapping identity | Body guard | Actual client-side virtual chunk predicate | Fixture outcome |
|---|---|---|---|---|
| 1.14 | Feather Gen2 `1.14+build.2` | `isAreaLoaded(i,k,m,j,l,n)` | `ClientWorld.isChunkLoaded(x,z) -> true` | admits WATER |
| 1.14.1 | Yarn `1.14.1+build.10` | `isAreaLoaded(min,max)` | `ClientWorld.isChunkLoaded(x,z) -> true` | admits WATER |
| 1.14.2 | Yarn `1.14.2+build.7` | `isAreaLoaded(min,max)` | `ClientWorld.isChunkLoaded(x,z) -> true` | admits WATER |
| 1.14.3 | Yarn `1.14.3+build.13` | `isAreaLoaded(min,max)` | `ClientWorld.isChunkLoaded(x,z) -> true` | admits WATER |
| 1.14.4 | Mojmap, exact `1.14.4` | `hasChunksAt(i,k,m,j,l,n)` | `MultiPlayerLevel.hasChunk(x,z) -> true` | admits WATER |
| 1.15 | Mojmap, exact `1.15` | `hasChunksAt(i,k,m,j,l,n)` | `ClientLevel.hasChunk(x,z) -> true` | admits WATER |
| 1.15.1 | Mojmap, exact `1.15.1` | `hasChunksAt(i,k,m,j,l,n)` | `ClientLevel.hasChunk(x,z) -> true` | admits WATER |
| 1.15.2 | Mojmap, exact `1.15.2` | `hasChunksAt(i,k,m,j,l,n)` | `ClientLevel.hasChunk(x,z) -> true` | admits WATER |

For the 1.14.1–1.14.3 route, `Entity.world` is the concrete client world at runtime; `World` implements `IWorld`, which inherits `ViewableWorld`. The interface default `ViewableWorld.isAreaLoaded` tests the inclusive chunk rectangle with virtual `this.isChunkLoaded`, reaching `ClientWorld`'s unconditional `true` override. For 1.14 Feather the equivalent default lives in `WorldView`; the same concrete `ClientWorld` override returns true. In 1.14.4 and 1.15, `LevelReader.hasChunksAt` checks inclusive chunk endpoints through virtual `this.hasChunk`; the concrete client-level overrides above return true. Therefore the absent chunk does not reject this client body query despite the inclusive guard margin.

Each body helper then executes its strict-upper-bound cell loops and reads the candidate via the concrete world/level `getFluidState` path. The cell at `(15,64,8)` is read from the loaded local chunk. The helper's sample-admission condition is the separate `fluid-surface >= deflatedBox.minY` comparison; the stated surface condition satisfies it in all inspected releases.

## Separate contextual-height dependency

The same-water-above/contextual-height result is not used to derive the chunk admission result. In the 1.14 and 1.15 helpers, the successful sample sets the body-fluid flag when its surface reaches the box bottom, while a separate accumulator tracks the maximum `surface - box.minY` and is later stored as water height. Flow response is also downstream. This proof closes only admission at the already accepted surface condition; it does not re-audit contextual height, eye-fluid state, flow/current response, or unrelated queries.

## Exact source pins

All files are read-only from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. SHA-256 hashes below are of canonical source-file bytes. The 1.14.1–1.14.3 Yarn source hashes were checked against each tree's `.decompile-provenance.json` `sources` entry; the other source hashes were checked against their published source manifests.

| Release/mapping | Entity.java | Area/chunk interface | Concrete client world | World/Level fluid access |
|---|---|---|---|---|
| 1.14 Feather | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` | WorldView `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` | ClientWorld `eb4985a468055bb206add2f5ae8d94864c7ab3a771fd40844c4fa6bc4e35b1b8` | World.java `cf5ca80f1c70b27efc7fe5e3ba57cfac029ad0e5d33e3882ee133847fb117594` |
| 1.14.1 Yarn | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | ViewableWorld `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` | ClientWorld `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` | World `eee7555801e8a6a0f074c0fe18bfc2d486ac60d71f1c60d11ff58d45ff5b62a9` |
| 1.14.2 Yarn | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | ViewableWorld `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` | ClientWorld `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f` | World `a09125982274d2d641318c26244a1834ff15222a34396ac8cefab00efd30a0ef` |
| 1.14.3 Yarn | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | ViewableWorld `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d` | ClientWorld `9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2` | World `e06517f3da812047d5fbf8cdb390b8b5c73dc7fb71350e314b2c2230ae15cdf0` |
| 1.14.4 Mojmap | `31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7` | LevelReader `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` | MultiPlayerLevel `3d31b9e969da5a4c79c49987648c921ddd30da1946e4fae5a3dec7f62252bf10` | Level `9278afc8e69353e5a483b345f9a6db2f11e67cfc98c43f9e7bd62cd0bebe5b02` |
| 1.15 Mojmap | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | LevelReader `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` | ClientLevel `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | Level `405d48c1d17f3c06ad8feb645d87ccb5c5ae405b44048590f4fddc9d4ec89a17` |
| 1.15.1 Mojmap | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | LevelReader `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` | ClientLevel `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | Level `405d48c1d17f3c06ad8feb645d87ccb5c5ae405b44048590f4fddc9d4ec89a17` |
| 1.15.2 Mojmap | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | LevelReader `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` | ClientLevel `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | Level `39cbd69f51c58711a3072bce84c80adfddf19837dba3e9582b0d6315d53e62bf` |

Source readiness/provenance was checked before tracing: 1.14 uses the exact Feather `1.14+build.2` sidecar; 1.14.1–1.14.3 use nested Yarn provenance containing each exact client and mapping artifact pin; 1.14.4, 1.15, 1.15.1, and 1.15.2 have exact-release Mojmap ready/provenance markers. No source generation or modification was performed.

## Prior bounded evidence

The earlier positive witness and its independent audit remain separate immutable inputs: `earlier-body-fluid-admission-source` commit `64e319ee7f760c687078b0e0d38b984d9a8bb573`, `evidence.md` raw Git blob SHA-256 `a55a63de56d7c7585a133c9a6f3d5444b449c5a0c7ed4675468291f1750800cb`; `earlier-body-fluid-admission-audit` commit `269d069b00c89163ab4012ca81a14811464477b0`, `verdict.md` raw Git blob SHA-256 `43f989899a7450ac34e2de93e2228dd0ba9093d3ed13806ced230ae81cd9f3be`. Their repositories were not modified.

Static source result only. The requested exact release range is closed for this witness; later releases and a global first-rejection boundary remain outside this proof.