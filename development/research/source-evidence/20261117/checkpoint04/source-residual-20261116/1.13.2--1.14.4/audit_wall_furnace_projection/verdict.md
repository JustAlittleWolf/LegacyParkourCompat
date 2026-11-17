# Independent source audit — furnace wall predicate

**Verdict: ACCEPT**, bounded to the `minecraft:furnace` wall-neighbor predicate row.

- Author report SHA-256: `ec6518561b8b9a1f164a9da9f2b07135d45aa42f85b03b56add37926a6996caf`.
- Endpoint source-manifest identities match ready markers: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.
- All report-listed Java hashes match canonical files and the relevant source manifest entries. The 1.14.4-only `net/minecraft/unmapped/C_53340811.java` entry correctly has no 1.13.2 counterpart.
- Registration and inheritance are supported: old `Block.java` registers `furnace` as `FurnaceBlock`; target `Blocks.java` registers it as `C_53340811`, which extends `FurnaceBlock` and adds no shape override. Both FurnaceBlock state definitions use four horizontal FACING values and boolean LIT, for eight states; neither property changes the shape.
- Old inherited `Block.getFaceShape` is `SOLID`, and the old wall connection predicate accepts it. Target inherits the base full-block collision shape with collision enabled; `Block.isFaceSolid` therefore returns true for each horizontal face. Furnace does not enter the attachment exception, WALLS-tag, or fence-gate alternatives. The accepted container/utility/sign/banner/head and named old-exception wall packets do not include furnace.

Result: true→true for all eight states and four horizontal directions. This accepts only this bounded predicate row; no player trajectory, provider-inventory completion, or pair freeze is asserted.
