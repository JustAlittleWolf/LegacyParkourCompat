# Independent source audit: spruce, birch, and jungle persistent leaves

Status: **COMPLETED — bounded sibling audit accepted.**

## Frozen roster and verdict

The source-only roster was frozen before opening the author evidence: for the ordinary legacy leaves block, spruce (variant ID 1), birch (ID 2), and jungle (ID 3), each with DECAYABLE=false and either legal CHECK_DECAY value. Oak (ID 0) was excluded from re-audit; leaves2 is a separate registered block and excluded.

Across all 24 assigned releases, the three species are variants of one LeavesBlock implementation. They resolve through AbstractLeavesBlock to the same Material.LEAVES. No species-specific block subclass, material provider, collision/movement predicate, propagation implementation, or persistence override exists. No independent movement difference was found for these siblings. The accepted Oak dry-side flow claim therefore applies through the shared provider and was not re-audited here.

The species enum IDs are 1/2/3 throughout. LeavesBlock restricts its variant property to IDs below four and decodes species with metadata low bits (metadata & 3), with an equivalent modulo-four lookup in the mapped source. Its block-state encoder writes the variant ID, bit 4 when DECAYABLE is false, and bit 8 when CHECK_DECAY is true; the decoder reads those same bits. Persistent block metadata is therefore 5/6/7 for CHECK_DECAY=false and 13/14/15 for CHECK_DECAY=true. LeavesItem preserves the species bits and applies metadata | 4 when placing the block, producing persistent states. Ordinary block-state metadata storage calls the same encoder and decoder.

AbstractLeavesBlock constructs the ordinary leaf block with Material.LEAVES. Material.LEAVES is a plain Material instance; base Material.blocksMovement() returns true. Its translucency flag changes isSolidBlocking(), not blocksMovement(). The ordinary LeavesBlock variants share this effective movement-blocking material. The spruce, birch, and jungle differences in LeavesBlock concern variant color/drop behavior; persistence and provider behavior remain shared.

## Source anchors

- 1.8: Block.java:806 registers numeric block 18 as leaves; Item.java:308 registers LeavesItem; PlanksBlock__Variant.java:7-10 gives IDs 1/2/3; LeavesBlock.java:20-23 restricts the property to IDs below four, and :82-105 decodes/encodes state metadata; AbstractLeavesBlock.java:26 supplies Material.LEAVES; Material.java:13,63-65 defines the provider and default movement predicate; LeavesItem.java:16 sets the persistent bit.
- 1.12.2: Block.java:782 registers leaves; Item.java:410-411 registers LeavesItem; PlanksBlock.java:58-60 gives the species IDs; LeavesBlock.java:58-80 performs the same metadata encoding/decoding; AbstractLeavesBlock.java:25 supplies Material.LEAVES; Material.java:15,71-73 defines the provider and default movement predicate; LeavesItem.java:16 sets the persistent bit.
- Across the roster, Blocks.LEAVES resolves the leaves registry entry. The only leaf subclasses found are LeavesBlock and separately excluded Leaves2Block, each extending AbstractLeavesBlock. No species-specific subclass exists.

The shared dry-side getFlow behavior, stable north-gradient fixture, and query-time Oak result are inherited without re-audit from the accepted Oak source-only verdict. This audit makes no new caller, operation, or runtime-simulation claim.

## Exact source identities

All 24 inputs are exact stable releases mapped with Ornithe Feather Gen2 build.2. Each row gives the Feather merged-mapping archive SHA-256, the exact source manifest/provenance file SHA-256, and a focused source-set SHA-256. The focused set contains the sorted relative-path=SHA-256 records for AbstractLeavesBlock.java, Block.java, Blocks.java, LeavesBlock.java, Material.java, BlockItem.java, Item.java, LeavesItem.java, and the species source (PlanksBlock.java or the separate PlanksBlock__Variant.java). Every source file hash matched its declared manifest/sidecar record in all 24 releases. Seventeen mapping-local provenance manifests were used. Seven exact-version ready markers name the corresponding version-root .sources.sha256 sidecars; every ready marker hash matched its sidecar.

| Release | Feather mapping SHA-256 | Source identity file SHA-256 | Focused source-set SHA-256 |
|---|---|---|---|
| 1.8 | 3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe | 52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05 | dc727e9baa8431b0fea5f2e96a43e1b1fffe3a431da7285aa2fbadd7abdf3034 |
| 1.8.1 | dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7 | f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca | f5864b91b1a3d84b46c99f18e4bba3e038d0b3ace229f41dba5347ec92c358c9 |
| 1.8.2 | 8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b | 8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472 | a7d50cc21a655fd94ec88cc4a6d1123b6bfcc6d36013bcf9dd05551962911f59 |
| 1.8.3 | e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2 | 7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed | a7d50cc21a655fd94ec88cc4a6d1123b6bfcc6d36013bcf9dd05551962911f59 |
| 1.8.4 | 8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c | b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00 | a7d50cc21a655fd94ec88cc4a6d1123b6bfcc6d36013bcf9dd05551962911f59 |
| 1.8.5 | 18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d | 986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce | 2e9dcd851a4e56edd041053ba4d043822413c151bfc081c38b3902ecca09d223 |
| 1.8.6 | d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81 | c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9 | 857d9a72d9d4a50c31dad8be608f0774f981b9894956df34b641ae8f3aa05a74 |
| 1.8.7 | e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b | 6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef | 857d9a72d9d4a50c31dad8be608f0774f981b9894956df34b641ae8f3aa05a74 |
| 1.8.8 | 934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f | 588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1 | 857d9a72d9d4a50c31dad8be608f0774f981b9894956df34b641ae8f3aa05a74 |
| 1.8.9 | 303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da | 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004 | 857d9a72d9d4a50c31dad8be608f0774f981b9894956df34b641ae8f3aa05a74 |
| 1.9 | a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a | 8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a | a8d8ed47d3048fc29366dde7051fc696af4cb056e4ca1f3ff7a0d101f39be681 |
| 1.9.1 | 6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3 | e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a | a8d8ed47d3048fc29366dde7051fc696af4cb056e4ca1f3ff7a0d101f39be681 |
| 1.9.2 | e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859 | 9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510 | a8d8ed47d3048fc29366dde7051fc696af4cb056e4ca1f3ff7a0d101f39be681 |
| 1.9.3 | fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3 | 43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a | 390efe3f64c5cb3ed54130064a567de658be44e2b62ede11798d4217cbec88c5 |
| 1.9.4 | 49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125 | c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19 | 390efe3f64c5cb3ed54130064a567de658be44e2b62ede11798d4217cbec88c5 |
| 1.10 | ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd | df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da | 506b6e10f1ab2053f61d7900b7674c97b7db48d31f0e5f1f471a920dd974ec03 |
| 1.10.1 | 6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37 | a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f | 506b6e10f1ab2053f61d7900b7674c97b7db48d31f0e5f1f471a920dd974ec03 |
| 1.10.2 | 18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0 | 91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71 | 506b6e10f1ab2053f61d7900b7674c97b7db48d31f0e5f1f471a920dd974ec03 |
| 1.11 | 658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44 | 8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca | 398994a05c12ebcc852074df6aa0e67fe0f1a6bbcecb0f67ccf8b061c578ddb1 |
| 1.11.1 | 83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928 | 0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d | a3231ed217fb36d70da25ecadcd36bc7717df966109db39c00017ff9f75067f2 |
| 1.11.2 | d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757 | d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0 | a3231ed217fb36d70da25ecadcd36bc7717df966109db39c00017ff9f75067f2 |
| 1.12 | 73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719 | 27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467 | 34b655781ea7422b33cd323264b9142eb3e83a9b8c2f72e34b56dee7b15bc067 |
| 1.12.1 | f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7 | 0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8 | 34b655781ea7422b33cd323264b9142eb3e83a9b8c2f72e34b56dee7b15bc067 |
| 1.12.2 | e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca | b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da | 34b655781ea7422b33cd323264b9142eb3e83a9b8c2f72e34b56dee7b15bc067 |

## Immutable evidence binding

- Accepted Oak source-only evidence: C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/portal-farmland/known-nonfalling-materials-oak-leaves-audit, commit 6a9dd654b5de0c05436fd50312934fc621d1f8d2, evidence.md SHA-256 eebb427a1ef5d27f556bc2d6b6c5f4b28a9ee0202a67db569ee176aff17bba30.
- Immutable author evidence checked only after roster freeze: C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/portal-farmland/known-leaf-provider-legacy, commit 000cd1e8156c2919bb8ed496934e8e7914d12ef2, evidence.md SHA-256 c6153c1be691b098d2696f350e79942cb59815556237c60faed0db05c9fe1a6a.

The author artifact's bounded sibling claim agrees with this independent audit. Source-acceptance scope is limited to the three legal persistent species variants and the shared dry-side material provider. No product/history/backlog/wiki inspection, tests/builds/decompilation/runtime/gameplay, or implementation was performed.

