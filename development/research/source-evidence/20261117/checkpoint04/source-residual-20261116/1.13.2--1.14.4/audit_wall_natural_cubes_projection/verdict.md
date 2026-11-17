# Independent source audit: natural full-cube wall predicate slice

**Verdict: ACCEPT**, bounded to the six assigned wall-neighbor predicate rows.

- Audited author report SHA-256: `2cca6752bda5e54e1b852f71891b91557cfda90038081041e10a9053932a124e`.
- Endpoint source manifests: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Each matches its ready marker and on-disk manifest.
- All 15 Java files listed in the report match both canonical source bytes and the corresponding manifest entries. Report’s listed SHA-256 values were independently checked.
- The cited r2 unexamined-key TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its exact six rows are sponge, wet_sponge, gravel, hay_block, mycelium, and podzol, with the reported concrete classes and providers.
- Source review confirms old BlockState.getFaceShape delegates to the block, the listed classes inherit Block.getFaceShape returning SOLID, and the old WallBlock accepts SOLID for these non-exception, non-gate keys.
- At the target endpoint, the six listed classes and their inheritance chain do not override getShape or getCollisionShape; Block defaults to a full-cube outline and collision shape, with `hasCollision=true` absent any noCollision registration. The relevant WallBlock path calls BlockState.m_87223014; its cached face value is precomputed by Block.isFaceSolid, and its uncached path calls the same method. The target wall attachment, LEAVES, WALLS, and fence-gate branches do not exclude these keys.
- State domains are supported by the exact sources: four singleton keys, hay_block AXIS X/Y/Z, and mycelium/podzol SNOWY false/true. This yields 10 registered key-state pairs and 40 horizontal direction checks; all are true at both endpoints.
- Reused target resource evidence is independently ACCEPTed: `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. I rechecked the bound payload hashes (`walls.json` `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; `leaves.json` `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`) and confirmed none of the six keys is present.

This accepts only the stated source predicate slice. It does not establish player trajectory impact or freeze either source pair. The historical limitation that the original r1 registry crosswalk TSV was overwritten remains relevant to broader crosswalk completeness; this verdict relies only on the exact r2 residual TSV digest above.
