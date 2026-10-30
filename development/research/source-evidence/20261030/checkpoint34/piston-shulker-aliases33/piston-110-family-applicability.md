# Piston stair response applicability: 1.10 family

## Bounded question and verdict

Does the accepted 1.10.2 → 1.11.2 piston/stair response witness also describe the exact earlier 1.10 releases? Yes, for the inspected releases 1.10, 1.10.1, and 1.10.2. The witness's old-response caller, progress timing, stair-outline/full-cube input, stair collision boxes, and entity response paths are byte-identical across these three sources. No 1.10-family source difference was found in the assigned piston response slice.

This is an applicability closure for that witness only. The accepted geometry trace and its limits remain those in `.native-source/20261030/1102-1112-pool31/piston-response-closure/report.md` (SHA-256 `2395b1f9c28502162d6c2e9467c319add9cf89def3841b0c085b9fc67075a6a1`) and its independent audit (`piston-response-audit/audit.md`, SHA-256 `7ce10b93402f12ee7bcdbe3c10ff1c8ccd037c02803288d3ba863565a74d7886`). I did not re-evaluate the paired witness geometry or expand it into pair coverage.

## Exact source identity and pin validation

Read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.10,1.10.1,1.10.2}\ornithe-feather`, Ornithe Feather / Feather Gen2 build 2.

| Release | Provenance identity | Source pin | Full pinned Java rows checked against actual bytes |
| --- | --- | --- | --- |
| 1.10 | Embedded `.decompile-provenance.json` SHA-256 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; input records `feather-gen2-1.10+build.2-mergedv2.jar` SHA-256 `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`. | Embedded source map; 1,845 rows. | 1,845 / 1,845; zero missing or mismatched. |
| 1.10.1 | Embedded `.decompile-provenance.json` SHA-256 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`; input records `feather-gen2-1.10.1+build.2-mergedv2.jar` SHA-256 `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`. | Embedded source map; 1,845 rows. | 1,845 / 1,845; zero missing or mismatched. |
| 1.10.2 | Release-parent ready / provenance / source-manifest SHA-256 `4c6d236e1d06ffc365452c48e73dfa2bf241bbf4ea67a24a7010c5583906a2d` / `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Artifact manifest pins Feather Gen2 `1.10.2+build.2` merged jar and Tiny mapping. | Ready-bound source manifest; 1,845 rows. | 1,845 / 1,845; zero missing or mismatched. |

All source roots identify the exact release and `ornithe-feather` namespace. Embedded inputs and release-parent artifact records are provenance identities; this check validates the complete current Java source pin rows and does not re-fetch raw artifacts.

## Compared response contract

I compared `MovingBlockEntity.getShape(WorldView, BlockPos, float)`, `moveEntities()`, and `tick()`; the stair collision collector and inner/outer shape constructors; `Block.getShape`, `getCollisionShape`, and `addCollisions`; `Entity.move(double,double,double)`; the relevant `World.getEntities` and `World.getCollisions` methods; and `Box.intersectX` / strict `Box.intersects`. Full source-file hashes and normalized method hashes are in `hash-matrix.tsv`.

- The whole `MovingBlockEntity.java`, `StairsBlock.java`, `Block.java`, `Entity.java`, and `Box.java` files have identical SHA-256 values in all three releases. Their old code therefore retains the same progress increment-before-response order, `0.5F` half-steps, union of the moved-state `getShape` at current and previous progress, query/push amount order, and untyped `entity.move(dx,dy,dz)` call.
- `StairsBlock` has no `getShape` override in any of the three releases. Its shape constructor still supplies collision boxes separately, while inherited `Block.getShape` remains the unit full-cube input used by the old piston response. The concrete bottom straight-stair boxes and strict collision intersection relied on by the accepted witness have identical source bodies across the family.
- `World.java` as a whole changes across the releases, but the inspected entity query method body and collision-query method body have matching normalized hashes in all three. Entity dimension source also confirms the ordinary player is reset to `0.6F × 1.8F` in each release (`PlayerEntity.java:399`).

## Applicability result

The accepted 1.10.2 old response is source-applicable to exact profiles 1.10 and 1.10.1 as well as 1.10.2. All three inspected releases take the same full-cube old piston route against the same stair state/boxes and issue the same local player response under the witness preconditions. This establishes no cutover within the inspected 1.10 family and makes no claim about versions outside those three exact releases, the 1.11+ caller, runtime trajectory parity, or full-pair freeze.

Static exact-source inspection and hashing only; no canonical source writes, decompilation, download, build, tests, or game launch.
