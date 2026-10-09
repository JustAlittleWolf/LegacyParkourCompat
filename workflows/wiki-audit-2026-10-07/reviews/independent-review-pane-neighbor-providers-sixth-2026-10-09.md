# Independent source review: pane-provider slice 6 (IDs 181–183)

**Verdict: ACCEPTED, bounded to the three registration-level predicate results and the one-east-neighbor masks below.** Review date: 2026-10-09.

## Immutable input identity

- Reviewed candidate tree and memo commit: `b485bc1b6230a58c63d4e47f1c61c8e66829c8fb`.
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-sixth.md`; Git blob `42d201d0d1bc98cc2f25d0946f93ac8e8420dd32`; raw-file SHA-256 `f132b787d2e7cf34aa9b9bb3b431a46c576be8af89af3126b5e022e55ad41b61`.
- Exact read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../ready/1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready markers say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- All Java-file hashes cited by the memo were recomputed from these roots and match their source-manifest entries. Verification records say source and raw input artifacts match; original derived JAR equivalence remains unproven. Recorded original mapped-JAR hashes (`e36a366f…`, `0df10c86…`) differ from regenerated snapshots (`5c4cff3e…`, `fbcf5079…`); the available evidence does not show that the differences are metadata-only.

## Source check

The paired pane resolvers inspect all four cardinal neighbors. The old predicate is `Block.isOpaque()` backed by the constructor-cached `opaqueCube` (`Block.java:154–156, 198–207`; `PaneBlock.java:134–140`). The new predicate is `defaultState().isCube()` (`PaneBlock.java:133–140`), delegated by `StateDefinition` to `Block.isCube(state)` (`StateDefinition.java:268–270`). IDs 181–183 are registered to the named subclasses in both versions. With the provider due east and air north, south, and west, the only tested connection bit is east.

| Registration and exact dependency | 1.8.9 result | 1.9.4 result | East-only disposition |
|---|---:|---:|---|
| `double_stone_slab2`, ID 181; `DoubleRedSandstoneSlabBlock.isDouble()` is true. Slab solid-render/cube predicates return that value; default state has `SEAMLESS=false`, `VARIANT=RED_SANDSTONE` (`Block.java:1219–1223` / `1140–1144`; `SlabBlock.java:69–72, 90–92` / `47–50, 68–70`; `RedSandstoneSlabBlock.java:24–34` / `25–35`; subclass `isDouble()` lines 3–8). | true | true | East bit in both versions. |
| `stone_slab2`, ID 182; `SingleRedSandstoneSlabBlock.isDouble()` is false. Its solid-render/cube predicates return false; default state has `HALF=BOTTOM`, `VARIANT=RED_SANDSTONE` (registration `Block.java:1224` / `1145–1147`; same slab and parent ranges above; subclass `isDouble()` lines 3–8). | false | false | No east bit in either version. |
| `spruce_fence_gate`, ID 183; both registrations construct `FenceGateBlock(SPRUCE)`. Defaults `OPEN`, `POWERED`, `IN_WALL` are false; old and new solid-render/cube predicates explicitly return false independent of state (`Block.java:1225–1229` / `1148–1152`; `FenceGateBlock.java:21–24, 73–81` / `30–34, 90–98`). | false | false | No east bit in either version. |

These are supported predicate and east-mask dispositions. The memo does not classify slab or gate collision geometry, provider lifecycle, support behavior, or player collision consumers. This verdict makes no lifecycle or non-player behavior claim, does not close the provider census or all reachable masks, and makes no runtime-validation or original derived-JAR-equivalence claim.
