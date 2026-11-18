# Stable legacy leaf material providers

## Bounded result

The existing leaves block registration supplies the same Material.LEAVES material for the spruce (metadata 1), birch (2), and jungle (3) variants in all 24 assigned stable releases. The leaf decoder masks species from the low two metadata bits and reduces them modulo four; its valid-state predicate accepts only variant IDs below four. No species-specific material, replacement, collision response, or registration exception was found. This extends the already accepted Oak leaf provider finding to these three variants only.

The shared Oak flow fixture, exact LiquidBlock.getFlow operation, query location, and directional-gradient result are inherited without re-audit from author evidence commit 42817118d79ffa88d1081ed6f5d44d32da232d09 (evidence.md, blob SHA-256 b3d86d632c2d8144a0e31af0200437660aed5bf7f535b1c7b255bb7b1f47bd62) and independent audit commit 6a9dd654b5de0c05436fd50312934fc621d1f8d2 (evidence.md, blob SHA-256 eebb427a1ef5d27f556bc2d6b6c5f4b28a9ee0202a67db569ee176aff17bba30). No new caller or operation is proposed.

## Source trace

In 1.8, block/Block.java:806 registers numeric block 18 under leaves with new LeavesBlock(). item/Item.java:308 creates its LeavesItem. The three target states are defined in block/PlanksBlock__Variant.java:7-9. LeavesBlock.java:19-24 defaults to OAK and restricts VARIANT to IDs below 4; LeavesBlock.java:83-105 decodes (metadata & 3) % 4, sets DECAYABLE from bit 4 and CHECK_DECAY from bit 8, encodes those flags back, and returns the selected variant. LeavesItem.java:16-18 applies metadata | 4 on placement, making the placed leaf non-decayable (persistent), while preserving species bits. Block-state storage uses the leaf's metadata encoder/decoder (Block.java:82, 92, 632).

AbstractLeavesBlock.java:26 constructs the block with Material.LEAVES. Material.java:13 defines that material without setReplaceable; the default Material.blocksMovement() at line 63 returns true. Block.java:187,195,203 shows the material-backed movement/solid predicates. Species and decay properties do not change the block's material. The same lineage and behavior is present in 1.12.2: registration Block.java:782, items Item.java:410-411, variants PlanksBlock.java:58-60, state decoder/encoder LeavesBlock.java:58-80, material parent AbstractLeavesBlock.java:25, and Material.java:15,71,89-104. Leaves2Block is registered separately and is outside this extension; no other tree species are included.

The exact 24-version static sweep checked the decoder, encoded state flags, persistent placement bit, block/item registration, spruce/birch/jungle metadata values, inherited material, default movement predicate, lack of replaceability, and distinct leaves2 registration. All 24 returned OK.

## Exact source and mapping identities

Each mapping artifact is Ornithe Feather build.2 for the exact version in the first column. Mapping SHA-256 is the merged mapping archive digest. The relevant-source digest is SHA-256 over the UTF-8 bytes of the lexicographically sorted relative/path=source-SHA-256 entries for Block.java, LeavesBlock.java, AbstractLeavesBlock.java, Material.java, Item.java, LeavesItem.java, and PlanksBlock.java, plus PlanksBlock__Variant.java where the enum is a separate source file. Before recording, each listed source hash was checked against the actual file and its source manifest/provenance record; every row verified true. For mapping-local provenance rows, source hashes are embedded in .decompile-provenance.json. For the seven version-root rows, the source-manifest SHA is shown in the final column.

| Exact release | Feather build | Mapping archive SHA-256 | Relevant-source set SHA-256 | Version-root sources manifest SHA-256 |
|---|---|---|---|---|
| 1.8 | 1.8+build.2 | 3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe | b0f2dd2a160984db0a0423eff8e92be39dc36aa1fbc2eee7e768d1a108d61be7 | mapping-local |
| 1.8.1 | 1.8.1+build.2 | dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7 | 24785a84938fbe6b91ab0efd525112acb89bd85559f841f7f09954d7c3e8c19e | mapping-local |
| 1.8.2 | 1.8.2+build.2 | 8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.3 | 1.8.3+build.2 | e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2 | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.4 | 1.8.4+build.2 | 8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.5 | 1.8.5+build.2 | 18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.6 | 1.8.6+build.2 | d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81 | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.7 | 1.8.7+build.2 | e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.8 | 1.8.8+build.2 | 934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | mapping-local |
| 1.8.9 | 1.8.9+build.2 | 303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da | c396d78a0572b0eb0d6827837bef034fc3dd0adc4501d205c260299c6c58b5e9 | 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004 |
| 1.9 | 1.9+build.2 | a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a | 92a79dc79f05ee47fe73e61a842c1ae04cbb27ad1ec6a05c39734992de1868cc | mapping-local |
| 1.9.1 | 1.9.1+build.2 | 6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3 | 92a79dc79f05ee47fe73e61a842c1ae04cbb27ad1ec6a05c39734992de1868cc | mapping-local |
| 1.9.2 | 1.9.2+build.2 | e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859 | 92a79dc79f05ee47fe73e61a842c1ae04cbb27ad1ec6a05c39734992de1868cc | mapping-local |
| 1.9.3 | 1.9.3+build.2 | fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3 | de519ed06671dee4d50c79d7e90c19aa254dbfefd5b9af8529078fce95493aab | mapping-local |
| 1.9.4 | 1.9.4+build.2 | 49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125 | de519ed06671dee4d50c79d7e90c19aa254dbfefd5b9af8529078fce95493aab | c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19 |
| 1.10 | 1.10+build.2 | ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd | 843ba6cfdc0304a80384bf99f3948b8803e09beec11c2c1ef39a57fdc9520ae0 | mapping-local |
| 1.10.1 | 1.10.1+build.2 | 6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37 | 843ba6cfdc0304a80384bf99f3948b8803e09beec11c2c1ef39a57fdc9520ae0 | mapping-local |
| 1.10.2 | 1.10.2+build.2 | 18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0 | 843ba6cfdc0304a80384bf99f3948b8803e09beec11c2c1ef39a57fdc9520ae0 | 91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71 |
| 1.11 | 1.11+build.2 | 658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44 | 5b3e845dc2acd412991aa187bd797e33bda8bc14cdf99948daefc1a4e922f19d | 8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca |
| 1.11.1 | 1.11.1+build.2 | 83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928 | 20fe35cbc64106255dc4886f737725e38ae78d101b5dee2e09ab6d212e67574d | 0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d |
| 1.11.2 | 1.11.2+build.2 | d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757 | 20fe35cbc64106255dc4886f737725e38ae78d101b5dee2e09ab6d212e67574d | d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0 |
| 1.12 | 1.12+build.2 | 73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719 | 4b9f62ed42b1807b8f3b6d4e2a0ce588462823e94d8373bdcebb8c7e7d69421a | mapping-local |
| 1.12.1 | 1.12.1+build.2 | f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7 | 4b9f62ed42b1807b8f3b6d4e2a0ce588462823e94d8373bdcebb8c7e7d69421a | mapping-local |
| 1.12.2 | 1.12.2+build.2 | e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca | 4b9f62ed42b1807b8f3b6d4e2a0ce588462823e94d8373bdcebb8c7e7d69421a | b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da |

“mapping-local” source hash provenance is preserved per file in .decompile-provenance.json and was individually matched to the on-disk SHA-256. Version-root source manifest SHA values bind the seven ready/provenance exports. The focused sources were present and matched in all 24 source trees.
