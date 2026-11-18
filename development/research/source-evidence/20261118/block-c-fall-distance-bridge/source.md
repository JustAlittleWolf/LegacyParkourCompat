# Fall-distance precision bridge evidence

Status: COMPLETED for the bounded arithmetic bridge; source provenance qualification and gate limitations are recorded below.

## Assignment and scope

Exact requested span: accepted 1.18.2 finding through accepted 1.21.4 finding; intermediate stable IDs checked: 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6, 1.21, 1.21.1, 1.21.2, 1.21.3. Mapping family: Mojmap for every listed source. Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.

Bounded operation: `net.minecraft.world.entity.Entity.fallDistance` and its ordinary negative resolved-Y accumulation in `checkFallDamage`. This does not establish any profile/runtime behavior or exact intermediate change boundary for other gates.

## Exact source locators and hashes

All source file paths below are `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/Entity.java`. SHA-256 is for the complete local file. The field is `public float fallDistance` for all versions listed; line numbers are field / method / negative-delta writer / local ordinary movement call respectively.

| Version | Lines | Entity.java SHA-256 |
|---|---:|---|
| 1.18.2 | 172 / 979 / 990 / 593 | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` |
| 1.19 | 175 / 991 / 1000 / 597 | `3A8CE3090D29AAD1BBFECA31DE300D34B42A7E902F0E78B13EFD5653CD588766` |
| 1.19.1 | 175 / 991 / 1000 / 597 | `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6` |
| 1.19.2 | 175 / 991 / 1000 / 597 | `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6` |
| 1.19.3 | 175 / 1000 / 1009 / 597 | `8C35BDEF3BF2DE7B70AC76AD767BA2C194305E0DDA2E03FDF6544983667ADC32` |
| 1.19.4 | 177 / 1002 / 1011 / 599 | `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B` |
| 1.20 | 179 / 1101 / 1115 / 643 | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` |
| 1.20.1 | 179 / 1101 / 1115 / 643 | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` |
| 1.20.2 | 181 / 1107 / 1121 / 645 | `D7EE49AAEA5E862B92508E767562CABC8F10515605E8FB67565C8C8D5C23B01B` |
| 1.20.3 | 182 / 1108 / 1122 / 646 | `07383522BFF169938136638EF8C3244CA511B56CA4266913524F99F9821331B9` |
| 1.20.4 | 182 / 1108 / 1122 / 646 | `07383522BFF169938136638EF8C3244CA511B56CA4266913524F99F9821331B9` |
| 1.20.5 | 184 / 1124 / 1138 / 647 | `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3` |
| 1.20.6 | 184 / 1124 / 1138 / 647 | `71CD6B9F6C002684154DCE11D3745E8714D82F13C8E1B3AA56743930131C18F3` |
| 1.21 | 186 / 1163 / 1177 / 648 | `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850` |
| 1.21.1 | 186 / 1163 / 1177 / 648 | `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850` |
| 1.21.2 | 192 / 1216 / 1230 / 671 | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` |
| 1.21.3 | 192 / 1216 / 1230 / 671 | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` |
| 1.21.4 | 192 / 1237 / 1251 / 682 | `05F18EF2EC0413FC010230407C812A11553EB5123B68D21B5D7B2C0C175698AD` |

At every version in this table the writer body has the same relevant arithmetic shape: `else if (deltaY < 0.0) { this.fallDistance -= (float) deltaY; }`. The field remains float through 1.21.4. Therefore each input delta is explicitly narrowed to float before subtraction and the float field makes the accumulation itself float. No arithmetic change was found in the omitted versions 1.19 through 1.21.3. In the later accepted 1.21.5 comparison, the field becomes double while the casted input remains; that later field widening is what changes accumulator addition precision.

## Ordinary movement reach and argument

In 1.18.2–1.21.1, the inspected `Entity.move` body calls `checkFallDamage(resolvedMovement.y, onGround, ...)` after collision resolution; representative locators: 1.18.2 line 593, 1.20.5 line 647, 1.21.1 line 648. In 1.21.2–1.21.4 it calls the same writer with the post-resolution vector Y (`$$3.y`), at 1.21.2 line 671 and 1.21.4 line 682. This is the resolved/clipped movement component, not the original requested movement component. The writer's `deltaY < 0.0` branch therefore has a local ordinary movement path for negative resolved Y. The branch does not clamp or otherwise transform that Y before the negative check; it casts only for the subtraction.

## Independently applicable caller-gate limits

The bridge makes no claim that the caller gate is invariant across the whole span. The ordinary movement call is unguarded at the cited 1.18.2, 1.19, 1.20, 1.20.5, and 1.21.1 locations. At 1.21.2 line 670 the call is under `!level.isClientSide() || isControlledByLocalInstance()`. At 1.21.4 lines 681–682 it is under that condition plus `!isControlledByClient()`. These are independent caller applicability changes and were not traced or adjudicated here. Also not assessed: other writers/resets, accumulator consumers, collision/edge behavior, or consequences beyond the writer's local branch.

## Local provenance qualification

For the canonical tree, `.decompile-provenance.json` exists at only four checked version/mapping roots within this span. Manifest SHA-256:

- 1.20: `573F0BB3E18E39AE882769A007A1C97D711DA123034F9A1C894EB407EF58030F`
- 1.20.3: `C3D605E55B287E800A86AF8567A75EDCB009D1A9A1744CFCB4E6F47667DCEE0F`
- 1.21: `90246F277AEFD2350E42E17245FAA51928A949C8DB4427F3BE04A732527DEB7C`
- 1.21.2: `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`

Those manifests identify the source file hash above and Mojmap-remapped exact-version client input, with pipeline/runtime metadata. All other listed roots have no local provenance manifest. Per the build guide, they are legacy trees and are not independently provenance-verified; their local content hashes and required source presence were checked, but exact source-generation input identity cannot be asserted from a manifest. Do not promote manifest-less entries to provenance-verified evidence without the preparation owner's source-generation records or deliberate regeneration.

## Conclusion and boundary

Arithmetic continuity is source-confirmed at every exact stable release enumerated from 1.18.2 through 1.21.4: float accumulator field plus pre-cast float delta subtraction. This closes the requested intermediate arithmetic gap for these local trees. It does not prove an exact patch at which the separate 1.21.5 field type change was introduced, and endpoint comparisons do not justify a cutover date. The absent manifests are the provenance qualification above; caller-gate evolution remains out of scope.
---

# Evidence revision 2: canonical publication authorization

This section amends the earlier “Local provenance qualification” above. The first revision checked only nested `.decompile-provenance.json` files and missed the canonical outer release sidecars. Do not use its statement that all remaining listed roots lack local provenance; the outer chain below closes that gap for the listed versions. The initial evidence is preserved verbatim above, including its caller-gate and source-scope limits.

For each outer-marker release, verification checked that (a) `*.ready.json` names the exact release and `mojmap`; (b) its source-manifest SHA-256 and artifact-manifest SHA-256 match the local sidecar bytes; (c) its diagnostics SHA matches local diagnostic bytes; (d) the exact `Entity.java` source-manifest entry matches the independently computed file SHA; and (e) the artifact manifest contains that release's official `client_mappings.txt` hash. The sibling `*.provenance.json` records exact run version and `mappingOption: mojmap`; the ready marker binds the source, artifact and diagnostics manifests. Hashes below are lowercase SHA-256.

| Version | Chain | Parent marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Exact Mojmap artifact SHA-256 | Entity.java SHA-256 |
|---|---|---|---|---|---|---|
| 1.18.2 | outer ready | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 | outer ready | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` | `9e31de7fc15b1bac0e58abc441a5e56c320266112feaea981bcffd41dd6201e1` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.19.1 | outer ready | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41` | `9b2dfe745719f4de1ce3fb23baeb24e31623c1fcc249e39bbdb068ad7f600236` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.2 | outer ready | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490` | `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.3 | outer ready | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` | `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.4 | outer ready | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` | `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| 1.20 | nested provenance | `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f` | embedded `sources` map; Entity hash matched | embedded `inputs` and tool hash map | official Mojang `client.txt`: `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.1 | outer ready | `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2` | `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.2 | outer ready | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c5d23b01b` |
| 1.20.3 | nested provenance | `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f` | embedded `sources` map; Entity hash matched | embedded `inputs` and tool hash map | official Mojang `client.txt`: `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.4 | outer ready | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.5 | outer ready | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | outer ready | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` | `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.21 | nested provenance | `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` | embedded `sources` map; Entity hash matched | embedded `inputs` and tool hash map | official Mojang `client.txt`: `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.1 | outer ready | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` | `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` | `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.2 | nested provenance | `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` | embedded `sources` map; Entity hash matched | embedded `inputs` and tool hash map | official Mojang `client.txt`: `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.3 | outer ready | `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` | `e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.4 | outer ready | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |

The four nested manifests (1.20, 1.20.3, 1.21, 1.21.2) are the full current `.decompile-provenance.json` form: their embedded source map contains the matching exact `Entity.java` SHA; their input map pins exact version metadata, client jar, official client mappings (`client.txt`), runtime libraries and the decompiler/remapper tools. The 14 outer ready chains above bind local per-release source and artifact manifests; their artifact manifests pin each exact `client_mappings.txt` artifact SHA, and sibling provenance records version and Mojmap run configuration. All 18 local chains passed the checks listed above; source hashes agree with revision 1's table.

Authorization boundary: this verifies internal hash consistency and exact-version/mapping identities asserted by the canonical local publication sidecars. These files are not digitally signed; this pass does not provide an independent signature or re-download/re-hash of each archived upstream input. No mismatches or irreducible local sidecar gaps were found. The source and arithmetic conclusion is unchanged. The 1.21.2/1.21.4 ordinary-call gate changes remain limitations exactly as recorded above.
## Outer provenance sidecar fingerprints

The outer `.provenance.json` SHA-256s below were also checked against `versionId`, `namespace: mojmap`, and the exact run's Mojmap mapping selection and release membership. 1.20.5 uses the alternate v1 provenance layout: its `exactRun.versionIds` is the release list, `mappingFamily` states official Mojang client mappings, and its nested artifact/source manifest hashes, client hash, mapping artifact hash, and Entity source hash all match the ready and sibling sidecars. It is fully verified, not an exception.

- 1.18.2 `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`
- 1.19 `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a`
- 1.19.1 `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245`
- 1.19.2 `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`
- 1.19.3 `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`
- 1.19.4 `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`
- 1.20.1 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`
- 1.20.2 `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`
- 1.20.4 `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`
- 1.20.5 `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`
- 1.20.6 `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`
- 1.21.1 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`
- 1.21.3 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`
- 1.21.4 `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9`

Final auth result: all 18 exact stable version trees have internally consistent canonical local source authorization, mapping-artifact identity, parent source/artifact manifest linkage, and matching `Entity.java` hashes. There is no unresolved local provenance gap. As noted above, these local hash chains are not signed attestations and this pass did not fetch upstream inputs again.