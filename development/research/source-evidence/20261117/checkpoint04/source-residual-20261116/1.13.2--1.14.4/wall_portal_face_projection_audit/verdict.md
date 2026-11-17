# Independent source audit: ACCEPT

## Audited claim and immutable inputs

Audited `build/source-residual-20261116/1.13.2--1.14.4/wall_portal_face_projection/report.md`, SHA-256 `32d38c5dacd17859a02f51c88d30a1f34f0c83c3ec8ab1770925e7da65fc618a`. The report bytes recompute to that digest. The exact read-only Feather Gen2 build 2 source manifests also recompute to the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Each ready marker names the matching source manifest digest. Mapping provenance names `feather-gen2-<version>+build.2` artifacts.

## Independent checks

- The five bounded joins are the common keys `minecraft:end_portal_frame`, `minecraft:end_gateway`, `minecraft:end_portal`, `minecraft:end_rod`, and `minecraft:nether_portal`. Endpoint `Blocks` sources resolve these names to the reported concrete classes; the 1.14.4 registrations directly show the same resource keys. The source report remains correctly bounded to these keys.
- In 1.13.2, `WallBlock.shouldConnectTo` uses the neighbor's `FaceShape`; placement and horizontal neighbor updates query the four opposite horizontal faces. The concrete old overrides are `EndPortalFrameBlock` (only DOWN is SOLID), `EndGatewayBlock`, `EndPortalBlock`, `EndRodBlock`, and `PortalBlock` (UNDEFINED for every face). Thus all five yield UNDEFINED for N/E/S/W and all old cardinal wall bits are false.
- In 1.14.4, the wall predicate receives `BlockState.m_87223014`, which delegates to `Block.isFaceSolid`; that rejects leaves and otherwise applies `isFaceFull` to the effective collision shape, which projects the requested direction and calls `isShapeFullBlock`. The wall's separate WALLS/fence-gate arm does not apply to these five classes. None is an attachment exception. For end-portal frame and end rod, collision is enabled by the default and their registered state shapes are used. The frame's 0–13/16 base and centered 4–12/16 eye cannot fill any horizontal face; the rod's three axis shapes each remain deficient in at least one projected dimension. For end gateway, end portal, and nether portal, registration calls `noCollision`; `Block.getCollisionShape` returns EMPTY before evaluating the state shape. Consequently every target N/E/S/W full-face result is false.
- The registered domains match the report: frame `FACING` (four horizontal directions) × `EYE` (false/true), gateway and end portal with no properties, rod `FACING` (six directions), and nether portal `AXIS` (X/Z). The state-definition builders and endpoint registrations support the domains. Repeating the face reasoning over those domains leaves every listed direction false on both sides.
- Target frame, rod, end-portal and nether-portal shape methods do not consult world, position, or collision context; they use only the stated block state. Gateway has no shape override. Gateway and end portal create their respective block entities, but the no-collision gate dominates the wall collision query. The portal block entity is likewise not an input to the collision-shape path. EndPortalBlock's separate portal-entry collision callback does not change this wall-face result.
- Recomputed source-byte hashes match every listed table entry for the cited `Blocks`, `Block`, `WallBlock`, five block classes and target `VoxelShape` files. The target `VoxelShape` face projection and full-shape predicate are consistent with the report's deficient/empty results.

| Key | Old N/E/S/W | Target N/E/S/W | Finding |
|---|---|---|---|
| `end_portal_frame` | false / false / false / false | false / false / false / false | No delta |
| `end_gateway` | false / false / false / false | false / false / false / false | No delta |
| `end_portal` | false / false / false / false | false / false / false / false | No delta |
| `end_rod` | false / false / false / false | false / false / false / false | No delta |
| `nether_portal` | false / false / false / false | false / false / false / false | No delta |

## Verdict

**ACCEPT** the report's bounded five-key, all-state, four-horizontal-direction conclusion: there is no wall-cardinal-bit change for these keys between 1.13.2 and 1.14.4. No correction is required. This verdict does not certify the remaining wall-neighbor inventory or pair-wide coverage.