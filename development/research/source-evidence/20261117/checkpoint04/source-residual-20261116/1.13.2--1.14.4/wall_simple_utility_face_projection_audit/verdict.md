# Independent source audit: simple utility face projection

**Verdict: ACCEPT**

## Bound report and evidence identity

Audited `../wall_simple_utility_face_projection/report.md`, SHA-256 `7161b0f0e0ba180cee371574c77aaac747012fa3bcbdba9cd2e597b87afa2142`. The file currently hashes to that value.

The read-only ready markers identify 1.13.2 and 1.14.4 with Ornithe Feather Gen2 build 2. The source-manifest files hash to the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Each of the report's cited block, registry, property, and voxel-shape file hashes matches current source bytes and its endpoint manifest entry.

## Audit result

I independently checked the eight registered keys—cactus, snow, cake, farmland, grass path, daylight detector, enchanting table, and structure void—at both endpoints. Their block builders and property declarations support the stated legal domains: age 0–15, layers 1–8, bites 0–6, moisture 0–7, power 0–15 with inverted false/true, and no block-specific properties for the remaining keys. The registrations join these keys to the compared block classes; structure void alone is registered with `.noCollision()` at both endpoints.

At 1.13.2 the three unconditional overrides (cactus, cake, structure void) return `FaceShape.UNDEFINED`; snow, farmland, grass path, daylight detector, and enchanting table return `SOLID` only for `DOWN`. Thus each old horizontal tuple is correctly `UNDEFINED` for every state.

At 1.14.4, inherited collision dispatch returns the state shape for the first seven registered blocks and an empty shape for structure void because `hasCollision` is false. The providers confirm: cactus has a one-sixteenth X/Z inset; snow collision heights are 0, 2, 4, 6, 8, 10, 12, and 14 sixteenths; cake's X extent is inset and its height is 8/16; farmland and grass path are 15/16 high; daylight detector is 6/16 high; enchanting table is 12/16 high; structure void has empty collision despite its inset outline. The target `isFaceFull` projects the collision shape toward the requested direction and requires a full block projection. Each horizontal projection is therefore non-full, including empty snow at layer one and empty structure void. The target tuple is correctly `false, false, false, false` for every legal state.

No correction is required. The bounded conclusion is supported: these eight rows establish no delta for old horizontal `FaceShape` versus target collision-shape full-face classification. This verdict does not freeze the pair or make claims about other operations or rows. No product files, shared source files, wiki, backlog, history, tests, builds, or runtime systems were used or changed.
