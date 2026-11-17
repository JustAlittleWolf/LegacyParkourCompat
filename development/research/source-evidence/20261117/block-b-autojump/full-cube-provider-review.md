# Ordinary full-cube AutoJump providers

Independent source reviewer `/root/autojump_provider_audit` accepted the following substitution claim for exact 1.13, 1.13.1, 1.13.2 and 1.14 (Ornithe Feather Gen2 build 2), and exact 1.14.1 (Yarn build 10). Replacing any admitted stone cell with `stone_bricks`, plain `sandstone`, `obsidian`, `gold_block`, `iron_block`, `diamond_block`, `white_wool` or `white_concrete` preserves the query output in a loaded, interior, entity-free AutoJump query away from the world border.

These registrations construct collision-enabled plain `Block` instances. The base shape lookup returns the shared full-cube singleton and the collision path forwards it. In 1.13 through 1.14 those cells take the identity-based aggregate branch. In 1.14.1 the collector translates and emits each overlapping block shape individually. The query bounds, cell pruning, block order and output boxes are unchanged by the stated substitutions. The bounded substitution covers the collision query and AutoJump's headroom and clearance shape reads. It leaves unrelated block behavior out of scope.

| Release and mapping | Members | SHA-256 |
| --- | --- | --- |
| 1.13 Feather | `Block` registrations and `getCollisionShape` (404–415) | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` |
| 1.13 Feather | `WorldView.getBlockCollisions` (113–185) | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13.1 and 1.13.2 Feather | `Block` registrations and `getCollisionShape` (388–399), identical files | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.1 and 1.13.2 Feather | `WorldView.getBlockCollisions` (112–151), identical files | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.14 Feather | `Blocks` registrations (208, 286, 384–393, 417, 664, 1551) | `b568c300287713c9b5ba59c6382f60014830163008fcd3613fbe54373ed43042` |
| 1.14 Feather | `WorldView.getCollisions` (127–194) | `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` |
| 1.14.1 Yarn | `Blocks` registrations (217, 296, 404–413, 441, 695, 1606) | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfaE7c8af97c74dd` |
| 1.14.1 Yarn | `ViewableWorld.getCollisionShapes` (129–193) | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` |
| 26.2 unobfuscated | `Blocks` registrations, including `WOOL` and `CONCRETE` indexed by dye color | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |

The 26.2 color collections select the white variants. The integrated predicate checks the native block identities and requires the canonical `Shapes.block()` collision result. Partial shapes, other colored variants and other providers remain outside this claim.
