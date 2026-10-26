# Legacy wall generic-neighbor identity evidence (source-only)

Inputs are the frozen 1.11.2 registration/classifier extraction and exact 26.2 unobfuscated vanilla source. This checkpoint addresses only identity/state correspondence; it does not change the product or claim that native wall logic is equivalent.

## Provenance

- `legacy-wall-neighbor-classifier/classifier.json`, SHA-256 `6b5bd6c1dc3b16f7c7dd38aa8b41c59d8c25b2bb314e3feb9b0ddd64a0d05b3c`.
- `legacy-wall-neighbor-classifier/registered-blocks.json`, SHA-256 `72799aabd4985abbbb22edd7c0c28760411149a6fa48e7867571bf0781f0ae79`.
- Current vanilla flattening source: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/net/minecraft/util/datafix/fixes/BlockStateData.java`, SHA-256 `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4`; its exact source manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Older source set is 1.11.2 Feather (`feather-gen2-1.11.2+build.2`), exact source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.

## Finding

The classifier has 100 old registration IDs without a same-string 26.2 registration. I extracted every balanced `BlockStateData.register(int, target, legacy...)` invocation and joined its numeric flattening index against each old ID's 16 metadata slots (`id*16 .. id*16+15`). The source implementation writes the first state argument as the target at that numeric index and indexes subsequent legacy tags as aliases (`BlockStateData.java`, `register` around lines 686+, with default-state fill in `finalizeMaps`). The attached JSON preserves each matched metadata value, exact target-state expression, numeric index, and source line.

99 of the 100 old IDs have explicit target resource identifiers in that legacy flattening table, covering 787 explicit metadata rows; `skull` (ID 144) remains unresolved because its target uses `FILTER_ME`, not an explicit identity. This is exact legacy conversion evidence, not proof that all 99 names remain current 26.2 registry identities after later renames. No spelling-based inference was applied. The exhaustive rows are in [flattening-mappings.json](flattening-mappings.json), SHA-256 `af7c95fdab33b37fd8eefa8814fb26aa6030880979be241982a4187a929a69fa`.

Concrete examples from the rows: `planks` (ID 5) splits by metadata into six plank identities; `stained_glass` (95) and `stained_hardened_clay` (159) each split into 16 color targets; `wooden_slab` (126) and `double_wooden_slab` (125) preserve species/state variants; ID 208 `grass_path` is explicitly table-mapped to `minecraft:grass_path`; shulker color IDs map to their legacy color-state identities. These are not to be collapsed to a single state where metadata expresses a variant.

Stairs are the useful semantic witness even though they already have same-name registration identities and are outside the 100 unmatched set. Old source `1.11.2/.../StairsBlock.java` has `isCube()` return false (lines 145–147; exact source hash `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627`); old stairs therefore fail the generic legacy conjunction regardless of their solid material. The 26.2 flattening table preserves `oak_stairs` and its half/facing/shape state variants (inspect the `BlockStateData.java` oak-stairs entries around line 1940), so the historical false result must remain for those states; matching identity alone does not mean connection equivalence.

## Remaining gates

- Resolve `skull` ID 144 from an authoritative source path that explicitly identifies its output, or keep it open.
- For all flattening outputs, audit later 26.2 rename fixes and current live registry fields before asserting a current identity mapping (the flattening output alone is not that proof).
- Keep the bounded old predicate as its own truth table: `material.isSolidBlocking() && state.isCube()`, with barrier rejection, self/fence-gate special acceptance, and pumpkin rejection in source order. Do not substitute modern `isSolidRender`, infer blanket wall equivalence, or include modern-only blocks.
- No implementation, runtime parity, test, or build conclusion is established here.
