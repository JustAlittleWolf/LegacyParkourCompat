# Independent source audit — pumpkin and melon wall exceptions

Verdict: **ACCEPT**, bounded to the four listed registered keys and their legal states as compared in the audited report. This verdict does not close a movement trajectory, remaining provider inventory, or the 1.13.2→1.14.4 pair.

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_pumpkin_melon_exception_projection/report.md`
Audited report SHA-256: `16f0e546c82eeb3fca2bf05520e08df4124ea7efc2e2731d8d09d8bf0e419034` (recomputed from exact report bytes).

## Independent checks

- Both ready markers identify exact versions 1.13.2 and 1.14.4 and their source-manifest hashes equal the reported Feather Gen2 build.2 manifest digests: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- Recomputed SHA-256 values for all 12 source files listed in the report match the reported hashes. The report’s registered-key/type mapping is consistent with the old `Block` registrations and target `Blocks` registrations.
- In 1.13.2, `WallBlock.shouldConnectTo` returns `!isExceptionForConnection(block) && shape == SOLID || bl`. `isExceptionForConnection` explicitly returns true for melon, pumpkin, carved pumpkin, and lit pumpkin (jack o’ lantern), so the shape-based arm is false for all four independently of face shape; the remaining `bl` arm only admits pole faces and a fence gate, which these blocks are not.
- In 1.14.4, `WallBlock.m_71853799` returns `!isExceptionForAttachment(block) && bl || bl2`. The exact target `Block.isExceptionForAttachment` lists all four block singletons, making `bl` false. The `BlockTagsProvider` WALLS registration contains wall blocks only and excludes these keys; none is a `FenceGateBlock`, making `bl2` false.
- `CarvedPumpkinBlock` in both sources extends `HorizontalFacingBlock` and declares FACING as `HorizontalFacingBlock.FACING`; both `Properties.HORIZONTAL_FACING` declarations use `Direction.Plane.HORIZONTAL`, so carved pumpkin and jack o’ lantern each have four legal orientations. `PumpkinBlock` and `MelonBlock` declare no state properties, so their domains are singleton. Thus all listed legal state combinations are covered by the state-independent exception result.

## Scope and limitations

Accepted fact: the old and target horizontal wall-connection bits are false for all four directions for each legal state of `minecraft:pumpkin`, `minecraft:carved_pumpkin`, `minecraft:jack_o_lantern`, and `minecraft:melon`. No difference is found in this bounded slice. No broader registry/state/provider crosswalk or player movement result is implied.