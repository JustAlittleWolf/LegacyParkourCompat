# Fall-distance precision applicability scan

**Status:** COMPLETED; exact-source coverage continuous 1.8–1.18.1  
**Assignment:** Blind source-only scan of the ordinary airborne resolved-Y fall-distance writer backward from 1.18.1 through every exact stable release to 1.8.  
**Inputs:** `development/research/source-evidence/20261118/block-c-fall-distance-1181-1182/source.md` (source identity `a92cf56a6e2cd17bb0a0f2779d79da09a2f280a7`); canonical `decompiled_minecraft/` sources and their provenance/readiness sidecars only.  

## Result

The operation is continuously applicable to **1.8 through 1.18.1**, including every exact stable patch release in that interval. No earlier differing exact release was found, so this scan does not establish a lower boundary before 1.8. The closest known different release is the supplied adjacent 1.18.2 endpoint.

In every exact source from 1.8 through 1.18.1, the non-grounded branch accumulates only a negative resolved Y displacement with `fallDistance = (float)(fallDistance - resolvedY)`. The existing float is promoted for subtraction with the double displacement, then rounded once on assignment back to float. At 1.18.2 the operation changes to `fallDistance -= (float)resolvedY`, as documented in the supplied source slice.

## Player/writer reach and operation aliases

In 1.8 Feather, `LocalClientPlayerEntity` extends `ClientPlayerEntity`; the player hierarchy reaches `LivingEntity`, which extends `Entity`. `LivingEntity` calls inherited `move(...)`; that movement reaches the common `Entity.checkFallDamage` writer. In 1.18.1 Mojmap, `LocalPlayer` follows inherited tick/`aiStep`/`travel`; `LivingEntity.travel` reaches `Entity.move`, which invokes `checkFallDamage` with resolved movement. The supplied source slice confirms the ordinary player-to-LivingEntity delegation and writer gate at 1.18.1.

The same technical operation is `Entity.checkFallDamage` in the inspected Feather 1.8+ and modern Mojmap trees. Mapping and package changes do not change the assigned negative-resolved-Y accumulation. The 1.14.1–1.14.3 source trees use Yarn rather than Feather/Mojmap; their exact writer bodies were also verified and remain the same operation. This alias statement concerns the writer operation, not full surrounding method parity.

## Exact source coverage and identities

Inspected every exact stable ID: `1.8`, `1.8.1`–`1.8.9`, `1.9`–`1.9.4`, `1.10`–`1.10.2`, `1.11`–`1.11.2`, `1.12`–`1.12.2`, `1.13`–`1.13.2`, `1.14`–`1.14.4`, `1.15`–`1.15.2`, `1.16`–`1.16.5`, `1.17`, `1.17.1`, `1.18`, and `1.18.1`. For every exact ID, either the outer `<mapping>.ready.json`/`<mapping>.provenance.json` identity was validated and the source manifest’s `Entity.java` hash matched the canonical file, or the tree’s nested `.decompile-provenance.json` named the exact source digest and the input URLs identified the exact version/mapping artifacts. All checked writer expressions use the post-subtraction cast.

Outer ready/provenance evidence applies to: 1.8.9 Feather; 1.9.4 Feather; 1.10.2 Feather; 1.11–1.11.2 Feather; 1.12.2 Feather; 1.13–1.13.2 Feather; 1.14 Feather; 1.14.4 Mojmap; 1.15–1.15.2 Mojmap; 1.16–1.16.2 Mojmap; 1.16.5 Mojmap; 1.17.1 Mojmap; and 1.18–1.18.1 Mojmap. Their exact source paths, writer line numbers, and source-file SHA-256 values are in the preceding immutable `source-evidence.md` report.

The remaining releases use nested provenance. In every row below, the exact canonical source file digest matches its `sources` entry in `.decompile-provenance.json`; the provenance sidecar digest is included for reproduction. Each listed source uses the post-subtraction cast at the cited line.

| Exact release | Mapping | Source member and line | Entity.java SHA-256 | Nested provenance SHA-256 |
|---|---|---|---|---|
| 1.8 | Ornithe Feather | `net/minecraft/entity/Entity.java:711` | `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9` | `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` |
| 1.8.1 | Ornithe Feather | `net/minecraft/entity/Entity.java:716` | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` |
| 1.8.2 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |
| 1.8.5 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | Ornithe Feather | `net/minecraft/entity/Entity.java:712` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |
| 1.9 | Ornithe Feather | `net/minecraft/entity/Entity.java:800` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | Ornithe Feather | `net/minecraft/entity/Entity.java:800` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | Ornithe Feather | `net/minecraft/entity/Entity.java:800` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | Ornithe Feather | `net/minecraft/entity/Entity.java:803` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.10 | Ornithe Feather | `net/minecraft/entity/Entity.java:816` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | Ornithe Feather | `net/minecraft/entity/Entity.java:816` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.12 | Ornithe Feather | `net/minecraft/entity/Entity.java:898` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12.1 | Ornithe Feather | `net/minecraft/entity/Entity.java:898` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` |
| 1.14.1 | Yarn | `net/minecraft/entity/Entity.java:832` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| 1.14.2 | Yarn | `net/minecraft/entity/Entity.java:834` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | Yarn | `net/minecraft/entity/Entity.java:878` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |
| 1.16.3 | Mojmap | `net/minecraft/world/entity/Entity.java:902` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` |
| 1.16.4 | Mojmap | `net/minecraft/world/entity/Entity.java:902` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` |
| 1.17 | Mojmap | `net/minecraft/world/entity/Entity.java:1009` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357` |


## Remaining scope limits

This bounded result covers only the ordinary airborne negative-resolved-Y writer and enough local-player reach to establish that it is reachable. It does not audit other fall resets, collision/support and block providers, fluid branches, fall-damage calculation, vehicle/non-player callers, or all surrounding movement operations. The result is source applicability only, not implementation acceptance or runtime parity.

The earlier `source-evidence.md`, `source-evidence-r2.md`, and `source-evidence-r3.md` in this lane directory are interim artifacts superseded by this corrected provenance-complete revision.