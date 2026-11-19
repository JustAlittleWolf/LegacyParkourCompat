# Body-water unloaded-neighbor admission: 1.18–1.19 aliases

## Result

**Positive applicability prerequisite for every listed Mojmap release (1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4).** For an ordinary, unmounted, nonflying player centered at `(15.7, 64, 8.5)`, standing width `0.6`, with loaded WATER at `(15,64,8)` and the adjacent X chunk 1 missing but outside the actual body-scan window, the padded-neighbor guard admits the scan. The still-loaded body WATER sample is found, so the body-fluid query returns true.

This extends only the named BODY-water gate to these exact aliases. It does not establish a movement change inside 1.18–1.19, a cutover elsewhere, or applicability to another fluid/current/eye/vehicle path.

## Exact sources and provenance

All sources were read-only from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Mapping namespace is Mojmap for every release. Each root `mojmap.ready.json` named the exact version, mapping, source-manifest digest and client digest. For every version below, the source-manifest bytes matched `sourceManifestSha256` in the ready marker; the root `mojmap.provenance.json` bytes matched the marker's provenance digest when present. Every cited Java file digest matched its exact entry in the source manifest.

| Exact release | Client jar SHA-256 | Source manifest SHA-256 | Root provenance SHA-256 |
|---|---|---|---|
| 1.18 | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` |
| 1.18.1 | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` |
| 1.18.2 | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.19 | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` |
| 1.19.1 | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` |
| 1.19.2 | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` |
| 1.19.3 | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` |
| 1.19.4 | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` |

The publications use the primary `mojmap.provenance.json`/`mojmap.ready.json` layout; a recursive `-Force` inspection found no nested `.decompile-provenance.json` in the examined Mojmap source trees. No provenance identity was inferred from filenames alone.

Core file SHA-256 values (all verified against their version's source manifest):

| Releases | `Entity.java` | `ClientLevel.java` |
|---|---|---|
| 1.18, 1.18.1 | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `085d5348cdd80e512047eba62000c7c18f6c74e853fe329060eb0d2f18cd36b2` |
| 1.18.2 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `e44cb1126b9b8093655892fb6ca17fa934cd79cc0453cee5e443969d8792b82e` |
| 1.19 | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` |
| 1.19.1, 1.19.2 | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` |
| 1.19.3 | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `8c8a6134a4409c899c098012e1a11e7eee736edac3403b89211f8ae8fa06df85` |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `2347e2ad9ec309ec6df5078ccd9a4870d179b846dabb7a11c6b0603b7cc93a16` |

## Predicate and body scan

The inherited path is stable: `LivingEntity.baseTick()` calls `super.baseTick()`; `Entity.baseTick()` invokes `updateInWaterStateAndDoFluidPushing()`; its water branch calls `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)`. This is the ordinary living/player base tick path. For 1.18, see `Entity.java:397-423,1037-1049` and `LivingEntity.java:318-329`; for 1.19.4, `Entity.java:405-431,1072-1083` and `LivingEntity.java:320-331`.

The WATER sampler's first operation is `if (touchingUnloadedChunk()) return false`. The helper inflates the entity AABB by `1.0`, applies `floor` to its min X/Z and `ceil` to its max X/Z, then negates `level.hasChunksAt(minX,minZ,maxX,maxZ)`. `LevelReader.hasChunksAt(int,int,int,int)` converts all four inclusive block endpoints to section coordinates, then tests both endpoint ranges with `hasChunk`. `ClientLevel.hasChunk(int,int)` unconditionally returns `true` throughout these aliases; the padded neighboring missing chunk therefore cannot trip this client guard.

For the supplied standing box, X is `[15.4,16.0]` and Z is `[8.2,8.8]`. The inflated bounds are `[14.4,17.0]` and `[7.2,9.8]`: `floor/ceil` produce X block endpoints 14 and 17, which map to chunks 0 and 1 inclusive. The missing X chunk 1 is included in the probe, but the client override returns true. The guard returns false (no early exit).

The subsequent sampler deflates the original box by `0.001`. Its X scan endpoints become `floor(15.401)=15` and `ceil(15.999)=16`; the loop condition is `<`, so only X=15 is visited. Z similarly visits only 8. The standing Y bounds deflate to `[64.001,65.799]`; floor/ceil endpoints are 64 and 66, so Y=64 and 65 are the only scanned cells. The specified loaded sample `(15,64,8)` is therefore inside the body scan; no read from the absent chunk is needed.

At that cell the sampler requires `fluidState.is(FluidTags.WATER)` and computes `double surface = yInt + fluidState.getHeight(level,pos)`. `FluidState.getHeight` delegates to the fluid type. `FlowingFluid.hasSameAbove` checks whether the fluid type at `pos.above()` matches; `getHeight` returns `1.0F` when it does, otherwise `getOwnHeight`, whose source expression is `amount / 9.0F`. Java evaluates the `int + float` expression as `float` before widening it into the `double` local. For a full source block without same-water above, the source height still places the surface above `64.001`; with same-water above, the sampled height is exactly `1.0F`. The inclusive `surface >= deflatedBox.minY` succeeds, the scan sets its body-fluid boolean true, and the wrapper's water branch receives true.

`Entity.java`'s `updateFluidHeightAndDoFluidPushing` code body is exact-identical from 1.18.0 through 1.19.4 after omitting the method declaration; its standalone `touchingUnloadedChunk` body is exact-identical too. The brace-delimited source-body SHA-256 for `updateFluidHeightAndDoFluidPushing` is `caf780264208250aeb8ce02a80185d6b3524984b4ede01feeab57eab475e093b`; for `touchingUnloadedChunk` it is `998a4d95e8978cdacf42b007db8038dcbbfdf604dc3bae51667404f95aea2cdb` in all eight releases. The mapped alias signature changes from `Tag<Fluid>` in 1.18/1.18.1 to `TagKey<Fluid>` starting 1.18.2. The old accepted 1.17.1 Mojmap source uses the same method/guard operation and same branch ordering, with different Vineflower local names; it is used only as the accepted predecessor context and was not re-reviewed as an endpoint here. The 1.19.3 water wrapper changes its boat predicate, but this unmounted witness does not enter that branch. No movement-difference boundary is claimed among the requested releases.

## Source member map

All paths are relative to `D:/Javastuff/LegacyParkourCompat/`.

| Release | `Entity.java` body sampler / guard / tick route | `ClientLevel.java` | `LevelReader.java` | `FluidState.java` / `FlowingFluid.java` |
|---|---|---|---|---|
| 1.18 | `updateFluidHeightAndDoFluidPushing`: 2756+; `touchingUnloadedChunk`: 2823+; `baseTick`: 401+ | `hasChunk`: 255-257 | `hasChunksAt`: 178-200 | `FluidState.getHeight`: 46+; `FlowingFluid.hasSameAbove/getHeight`: 429-436 |
| 1.18.1 | 2756+; 2823+; 401+ | 255-257 | 178-200 | 46+; 429-436 |
| 1.18.2 | 2769+; 2836+; 403+ | 254-256 | 179-201 | 49+; 429-436 |
| 1.19 | 2797+; 2864+; 407+ | 298-300 | 185-207 | 49+; 429-436 |
| 1.19.1 | 2797+; 2864+; 407+ | 298-300 | 185-207 | 49+; 429-436 |
| 1.19.2 | 2797+; 2864+; 407+ | 298-300 | 185-207 | 49+; 429-436 |
| 1.19.3 | 2826+; 2893+; 407+ | 300-302 | 190-212 | 49+; 429-436 |
| 1.19.4 | 2887+; 2954+; 409+ | 299-301 | 190-212 | 49+; 429-436 |

For the later-version extents, this report reuses only the previously accepted source-only `earlier-body-fluid-admission-source` artifact geometry and the allowed `development/research/source-evidence/20261117/block-a-fluid-height-preflight/verdict.md` background. It does not revisit their older release endpoints. No product implementation, product Git history, wiki, backlog, source generation, network, tests, build or runtime was used.