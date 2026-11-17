# Independent audit — wall/fence face projection r3

Verdict: **ACCEPT**.

Bound report: `build/source-residual-20261116/1.13.2--1.14.4/wall_fence_face_projection/report.md`, SHA-256 `f443dc69075ec23dbf6eb02ddbe77918795fced2edc7ac89b137eb40df7f703e`.

The r3 corrections resolve both issues from my prior bounded audit: the seven 1.13.2 concrete fence registrations are correctly attributed to `net/minecraft/block/Block.java`, with the 1.14.4 registrations in `Blocks.java`; and the target boundary arm width is correctly stated as 2/16, with the 4/16 central post distinguished. The report's old-source registration file and all endpoint source hashes are bound in its evidence table. The corrections do not alter the technical outcome.

I verified the report's endpoint manifest identities against ready source manifests (1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`) and rechecked the bound source evidence and registration mapping. The seven keys are oak, spruce, birch, jungle, acacia, dark oak, and nether-brick fence. Each uses four horizontal directional booleans and `WATERLOGGED`, giving 32 assignments per key and four queried horizontal directions (896 combinations).

The pinned 1.14.4 `data/minecraft/tags/blocks/walls.json` payload hash `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` matches the extracted file; its 14 values contain none of the seven fence keys. The extraction record hash is `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; its raw-client/resource audit is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

The bounded conclusion is supported: the old horizontal `MIDDLE_POLE` face is rejected for FenceBlock because the special branch requires a FenceGateBlock; the target fence's collision face is not full, the seven fence keys are absent from `WALLS`, and they are not fence gates. Thus each tested neighbor direction returns false at both endpoints across the full state domain. No other wall-neighbor families, all-provider inventory, movement trajectory, or pair-freeze claim is made.
