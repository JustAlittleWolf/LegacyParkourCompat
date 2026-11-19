# Body-fluid admission at the 1.20 chunk-margin edge

## Result

For each exact Mojmap client release 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, and 1.20.6, the inherited `Entity.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)` body scan admits the specified loaded WATER cell even when the neighboring chunk included by `touchingUnloadedChunk()` is actually absent. The guard expands the player box by one block and its inclusive chunk range reaches chunk X=1, but the virtual client implementation `ClientLevel.hasChunk` returns `true` unconditionally. Thus this concrete client predicate does not reject the loaded WATER cell.

This closes applicability for this exact alias range and input only. It does not establish intermediate patch boundaries outside the seven inspected versions, nor does it cover fluid-current impulses, eye-fluid state, vehicles, non-player entities, or any other guard.

## Exact evidence identity

All seven trees are canonical `decompiled_minecraft/<version>/mojmap/` sources. The 1.20 and 1.20.3 trees have hidden `.decompile-provenance.json` manifests whose source maps match the SHA-256 of each listed file. Those manifests identify the exact metadata and client artifacts plus the Mojmap `client.txt` mapping artifact; they identify the exact IDs in their metadata URLs (`1.20` and `1.20.3`). For 1.20.1, 1.20.2, 1.20.4, 1.20.5, and 1.20.6, the tree-level `mojmap.provenance.json` and `mojmap.sources.sha256` identify the exact release, `namespace=mojmap`, and mapping option; each source file hash below agrees with its source manifest entry. No source was generated or modified for this review.

| Release | Provenance file and SHA-256 | Source manifest SHA-256 | Exact mapping/source note |
|---|---|---|---|
| 1.20 | `mojmap/.decompile-provenance.json` `573F0BB3E18E39AE882769A007A1C97D711DA123034F9A1C894EB407EF58030F` | embedded source map | Metadata URL ends `/1.20.json`; Mojmap `client.txt` input SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de` |
| 1.20.1 | `mojmap.provenance.json` `5AEB1DF09671480AEDDB921C919E92098E33D0A0BCE47E22F82A865CB32400AA` | `mojmap.sources.sha256` `858B56764113E591C60D09BC63E4D90F9C1D67A705645F0C793E8B28F2378465` | `versionId=1.20.1`, namespace and mapping option `mojmap` |
| 1.20.2 | `mojmap.provenance.json` `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05` | `mojmap.sources.sha256` `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887` | `versionId=1.20.2`, namespace and mapping option `mojmap` |
| 1.20.3 | `mojmap/.decompile-provenance.json` `C3D605E55B287E800A86AF8567A75EDCB009D1A9A1744CFCB4E6F47667DCEE0F` | embedded source map | Metadata URL ends `/1.20.3.json`; Mojmap `client.txt` input SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` |
| 1.20.4 | `mojmap.provenance.json` `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385` | `mojmap.sources.sha256` `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1` | `versionId=1.20.4`, namespace and mapping option `mojmap` |
| 1.20.5 | `mojmap.provenance.json` `1D9B247FB839AD0E234054CBA1668073245E60D86783ECA40D44C2B7B23C6242` | `mojmap.sources.sha256` `162DAC6C4F1D539A1C2CEA0255B7938A5281C207AB460BDC265358016D732484` | `versionId=1.20.5`, official Mojang client mappings, namespace and mapping option `mojmap` |
| 1.20.6 | `mojmap.provenance.json` `EE53E67E7AB51069A0EAAE5A0CE13D49A9F972BDC13E3C31E041AFA77F4D26E8` | `mojmap.sources.sha256` `56AAE10684471D7ABB1C366BD5DD431AB976112A87E6CC5C687A68F1EFF06311` | `versionId=1.20.6`, namespace and mapping option `mojmap` |

The exact source SHA-256 groups for the six cited files are:

| Release(s) | `Entity.java` | `LevelReader.java` | `ClientLevel.java` | `FlowingFluid.java` | `WaterFluid.java` | `AABB.java` |
|---|---|---|---|---|---|---|
| 1.20, 1.20.1 | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` | `EC8C1F883DC1A71D2666F57CF7E124298272AE8B483770D32ECDB32C385B0BDD` | `8D7434E668BBB868F3D25D638ABAB4CD612AC89F1B54AE701FA81F212694FBC5` | `BA1DFF6CCB28B62760C74D66832D8FDBE7A1CDC37B9C5A0FF476BD200215C904` | `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F` | `594239BB50298D34533FC8EFC113F4C4B5312A41D3DC1713CC85130D5020EE5E` |
| 1.20.2 | `D7EE49AAEA5E862B92508E767562CABC8F10515605E8FB67565C8C8D5C23B01B` | `EC8C1F883DC1A71D2666F57CF7E124298272AE8B483770D32ECDB32C385B0BDD` | `6204DF69A17B375E46F4405FE67A106DEF336C819E589EC0794C04920A31C5D7` | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F` | `594239BB50298D34533FC8EFC113F4C4B5312A41D3DC1713CC85130D5020EE5E` |
| 1.20.3, 1.20.4 | `07383522BFF169938136638EF8C3244CA511B56CA4266913524F99F9821331B9` | `EC8C1F883DC1A71D2666F57CF7E124298272AE8B483770D32ECDB32C385B0BDD` | `2BC6147B758BA25FEA89E1C7C8E28000CF323F1616B01B62AF461CBCE230E308` | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F` | `13AB54EF7B11ABBE41465580EB5CDDB05A4BAD3178995D4609C252D70B96CAD9` |
| 1.20.5, 1.20.6 | `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3` | `D332731858D00CEF003FA7F24DEA6886F2D5A9029918F9C8C0540313AB07A9B7` | `CEFC6FFB53AD508052A7A0A96E21ECFD6D2CF471D920C7E91F1911C61FB911E6` | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F` | `13AB54EF7B11ABBE41465580EB5CDDB05A4BAD3178995D4609C252D70B96CAD9` |

## Trace and concrete input

For each release, `Entity.updateInWaterStateAndDoFluidPushing` calls `updateInWaterStateAndDoWaterCurrentPushing`; the latter calls the inherited body scan with `FluidTags.WATER` and static amount `0.014`. When that scan returns true, the caller sets `wasTouchingWater`, resets fall distance, and clears fire (with splash logic on the transition). This is the body-contact publication path relevant here; no claim is made about the impulse calculation.

The scan first rejects only if `touchingUnloadedChunk()` is true. That helper inflates the actual entity AABB by `1.0`, computes X/Z endpoints using `floor(min)` and `ceil(max)`, then calls the virtual `level().hasChunksAt(minX,minZ,maxX,maxZ)`. In `LevelReader.hasChunksAt(int,int,int,int)`, endpoint chunk coordinates are converted and both endpoint loops are inclusive (`<=`); each queried coordinate calls the virtual `hasChunk`. For an entity whose level is the client-side `ClientLevel`, each inspected release overrides `hasChunk(int,int)` to return `true`, so the modern padded query is true even when adjacent chunk X=1 is actually missing. Therefore `touchingUnloadedChunk()` is false and the body scan is not rejected.

Given x=15.7, z=8.5, feet y=64, standing width 0.6, and the normal player box, the inflated X bounds are [14.4,17.0]; the queried block endpoints are 14 and 17, which include chunk X=0 and X=1 inclusively. The loaded WATER sample at block (15,64,8) is inside the later body scan: the box is deflated by 0.001 and the scan floors its minima, ceils its maxima, then iterates each block coordinate with strict `<` upper bounds. The WATER cell is read from chunk X=0; no fluid query into missing chunk X=1 is needed.

The fixed fixture has WATER only at (15,64,8), with AIR at (15,65,8). `AABB.deflate(0.001)` delegates to `inflate(-0.001)`, which raises the lower Y bound from 64.0 to 64.001. Since there is no matching WATER above, `FlowingFluid.getHeight` uses the source state's own height; `WaterFluid.Source.getAmount` returns 8, and `FlowingFluid.getOwnHeight` computes `8 / 9.0F`. The expression `intY + floatHeight` is evaluated as float and then widened to double, yielding 64.888885498046875. The source comparison is inclusive (`surface >= deflatedBox.minY`), so this surface qualifies against 64.001. This sample contributes positive depth (about 0.8878855) and returns body contact. The scan can also visit Y=65, but its state is AIR in this fixture, so it cannot increase the stored maximum.

A separate stacked-water control would make `hasSameAbove` return `1.0F` for the lower sample and could add an upper WATER sample of its own to the scan. That control is not used to calculate the fixed fixture's final maximum depth or to establish its admission.

## Exact member references

All paths below are relative to `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` and use Mojmap names.

| Release(s) | `Entity.java` | `LevelReader.java` | `ClientLevel.java` | `FlowingFluid.java` |
|---|---|---|---|---|
| 1.20, 1.20.1 | `Entity.updateInWaterStateAndDoFluidPushing` 1176-1182; water contact publisher `updateInWaterStateAndDoWaterCurrentPushing` 1184-1198; `updateFluidHeightAndDoFluidPushing` 3001-3066; `touchingUnloadedChunk` 3068-3075 | `hasChunksAt` 185-205 | `hasChunk` 302-305 | `hasSameAbove` / `getHeight` 425-437; `getOwnHeight` 435-437; `WaterFluid.Source.getAmount` 141-150; `AABB.inflate/deflate` 173-184, 262-268 |
| 1.20.2 | `Entity.updateInWaterStateAndDoFluidPushing` 1186-1192; water contact publisher `updateInWaterStateAndDoWaterCurrentPushing` 1194-1208; `updateFluidHeightAndDoFluidPushing` 3031-3096; `touchingUnloadedChunk` 3098-3105 | `hasChunksAt` 185-205 | `hasChunk` 303-306 | `hasSameAbove` / `getHeight` 425-437; `getOwnHeight` 435-437; `WaterFluid.Source.getAmount` 141-150; `AABB.inflate/deflate` 173-184, 262-268 |
| 1.20.3, 1.20.4 | `Entity.updateInWaterStateAndDoFluidPushing` 1187-1193; water contact publisher `updateInWaterStateAndDoWaterCurrentPushing` 1195-1209; `updateFluidHeightAndDoFluidPushing` 3037-3102; `touchingUnloadedChunk` 3104-3111 | `hasChunksAt` 185-205 | `hasChunk` 309-312 | `hasSameAbove` / `getHeight` 425-437; `getOwnHeight` 435-437; `WaterFluid.Source.getAmount` 141-150; `AABB.inflate/deflate` 180-192, 269-275 |
| 1.20.5, 1.20.6 | `Entity.updateInWaterStateAndDoFluidPushing` 1203-1209; water contact publisher `updateInWaterStateAndDoWaterCurrentPushing` 1211-1225; `updateFluidHeightAndDoFluidPushing` 3052-3117; `touchingUnloadedChunk` 3119-3126 | `hasChunksAt` 185-205 | `hasChunk` 310-313 | `hasSameAbove` / `getHeight` 425-437; `getOwnHeight` 435-437; `WaterFluid.Source.getAmount` 141-150; `AABB.inflate/deflate` 180-192, 269-275 |

The call-site line block shows the selected tag and static amount. The `Entity` scan lines show the padded rejection, deflated body box, exclusive cell loops, matching-fluid check, contextual surface, inclusive comparison, max-depth update, and return flag. `LevelReader` and `ClientLevel` establish the actual virtual query result, not merely a generic method declaration.
