# Independent source audit verdict: 1.18–1.19 body-water admission aliases

**Verdict: ACCEPT**, limited to the contextual body-WATER admission prerequisite in the named Mojmap releases and witness below. This is an alias/applicability confirmation, not a movement-difference boundary or a claim that other fluid paths were reviewed.

## Immutable input and exact source identities

Audited author evidence: `body-admission-118-119-alias-source/evidence.md`, source Git commit `3a9df8f5d4c138ccbf6e5e54f1130ff5172cde91`, raw file SHA-256 `c53ce136645c9ad1d2650f6592aa482e5a8ca53cf269e08a29929270f908dc5d`. The source file hash and commit were independently checked before review.

For each listed release, `decompiled_minecraft/<release>/mojmap/mojmap.ready.json` reports the exact `versionId` and `mapping: mojmap`; its declared SHA-256 for root `mojmap.sources.sha256` matches that file's raw bytes. The root provenance file also matches the following independently recomputed SHA-256. The manifest entries for `Entity.java`, `LivingEntity.java`, `ClientLevel.java`, `Level.java`, `LevelAccessor.java`, `CommonLevelAccessor.java`, `LevelReader.java`, `FluidState.java`, and `FlowingFluid.java` were checked against the corresponding source bytes for every release.

| Release | Source-manifest SHA-256 | Root provenance SHA-256 |
|---|---|---|
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` |

## Claim checked

The inherited `Entity.updateFluidHeightAndDoFluidPushing` sampler starts with `if (this.touchingUnloadedChunk()) return false;`. Its exact brace-delimited method-body SHA-256 is `caf780264208250aeb8ce02a80185d6b3524984b4ede01feeab57eab475e093b` in all eight releases. The exact `touchingUnloadedChunk` body SHA-256 is `998a4d95e8978cdacf42b007db8038dcbbfdf604dc3bae51667404f95aea2cdb` in all eight. The 1.18 signature uses `Tag<Fluid>`; from 1.18.2 it uses `TagKey<Fluid>`, but the sampler body is otherwise byte-identical.

`Entity.touchingUnloadedChunk()` inflates the entity box by 1.0 and passes floor(minX), floor(minZ), ceil(maxX), ceil(maxZ) to `LevelReader.hasChunksAt`. That default converts inclusive endpoint ranges to section coordinates and calls `this.hasChunk` over them. `ClientLevel extends Level`; `Level` inherits `LevelReader` through `LevelAccessor -> CommonLevelAccessor`. No `hasChunksAt` override occurs in these checked types. `ClientLevel.hasChunk(int,int)` returns `true` in each alias. Thus on the actual ClientLevel path, the inherited virtual `touchingUnloadedChunk` guard is admitted even when this padded probe spans the missing adjacent chunk. This conclusion is specific to ClientLevel dispatch; it makes no server-world claim.

For the specified standing box centered at `(15.7,64,8.5)`, width 0.6 and height 1.8, the original bounds are X `[15.4,16.0]`, Z `[8.2,8.8]`, Y `[64,65.8]`. The inflated guard bounds yield X block endpoints 14 and 17, section/chunk X endpoints 0 and 1 inclusive. The missing chunk at X=1 is therefore part of the guard probe, but the client override admits it.

After the guard, the sampler deflates the original box by `0.001`; its loops run from floor(min) inclusive to ceil(max) exclusive. It visits X=15, Z=8 and Y=64,65. The loaded WATER state at `(15,64,8)` is read; no sampled fluid cell is in the absent X chunk. For that sample, the source expression is `double surface = yInt + fluidState.getHeight(level,pos)` and the gate is inclusive: `surface >= deflatedBox.minY`. `FluidState.getHeight` delegates to its fluid type. `FlowingFluid.getHeight` returns `1.0F` when the type at `pos.above()` matches, otherwise `getOwnHeight()`, which is `amount / 9.0F`. The no-same-type-above control therefore uses the positive WATER state's own height (at least `1/9`) and places the surface above `64.001`; the same-type-above control returns `1.0F`. Java evaluates the int-plus-float expression as float before widening it into the double local. The lower loaded WATER sample alone satisfies the gate, sets the sampler boolean, and that boolean is returned. The above-fluid branch is contextual confirmation only: it is not required for this witness and is not evidence of a stacked-column maximum.

The sampled method's `Math.max` accumulator tracks depth for fluid pushing, while the admission boolean is set for a matching cell whose surface passes the gate. This verdict uses only that one-cell positive body-water result. It makes no claim about a maximum across a stacked column, cumulative/aggregate column height, water-current vector, or resulting player trajectory.

## Source locations

All paths are relative to `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/mojmap/`.

| Releases | `Entity.java`: sampler / guard | `ClientLevel.java`: `hasChunk` | `LevelReader.java`: four-endpoint `hasChunksAt` |
|---|---:|---:|---:|
| 1.18, 1.18.1 | 2756 / 2823 | 255 | 188 |
| 1.18.2 | 2769 / 2836 | 254 | 189 |
| 1.19, 1.19.1, 1.19.2 | 2797 / 2864 | 298 | 195 |
| 1.19.3 | 2826 / 2893 | 300 | 200 |
| 1.19.4 | 2887 / 2954 | 299 | 200 |

The caller chain was checked in each release: `LivingEntity.baseTick` calls `super.baseTick`; `Entity.baseTick` calls `updateInWaterStateAndDoFluidPushing`; its unmounted/non-boat water branch calls the sampler with `FluidTags.WATER` and `0.014`.

| Releases | `LivingEntity.java`: baseTick / super call | `Entity.java`: baseTick call / water query |
|---|---:|---:|
| 1.18, 1.18.1 | 318 / 328 | 422 / 1048 |
| 1.18.2 | 322 / 332 | 424 / 1058 |
| 1.19 | 324 / 334 | 428 / 1077 |
| 1.19.1, 1.19.2 | 324 / 334 | 428 / 1072 |
| 1.19.3 | 325 / 335 | 428 / 1077 |
| 1.19.4 | 320 / 330 | 430 / 1083 |

The 1.19.3 boat-predicate edit does not affect this unmounted witness. Fluid height references are `FluidState.java:46` and `FlowingFluid.java:429-440` in 1.18/1.18.1, then `FluidState.java:49` and `FlowingFluid.java:429-440` from 1.18.2 through 1.19.4.

## Limits

This verdict does not re-audit the accepted 1.17.1 predecessor, establish any version cutover, add any caller, or compare the complete tick graph. It is only a positive applicability prerequisite for this contextual body-WATER query on the eight exact client-source aliases above. It does not establish current-water, eye-water, boat, actually-unloaded-body, logical-server, or other fluid-path behavior. No product source, product Git history, wiki, backlog, newly generated source, build, tests, or runtime were consulted.