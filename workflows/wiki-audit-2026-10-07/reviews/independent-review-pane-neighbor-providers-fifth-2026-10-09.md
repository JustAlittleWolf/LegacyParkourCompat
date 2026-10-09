# Independent source review: pane-provider slice 5 (IDs 178–180)

**Verdict: ACCEPTED, bounded to the three registration-level predicate results and the one-east-neighbor masks below.** Review date: 2026-10-09.

## Immutable input identity

- Reviewed tree: `b485bc1b6230a58c63d4e47f1c61c8e66829c8fb` (the fifth memo was introduced at `6de66adb6892d8cfce5ed54f8f94c4dea8b273dc`).
- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-fifth.md`; Git blob `63bb0db17b1b5183dfdb57239226db256988948d`; raw-file SHA-256 `c020f254ad9a3e821b59202207ae51784844975214bced0c6b5dd3fa33d8c4b8`.
- Exact read-only Feather roots: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather` and `.../ready/1.9.4/ornithe-feather`, revision `feather-r1-2026-10-07`. Both ready markers say `ready`.
- Source-manifest SHA-256: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- Artifact-manifest SHA-256: 1.8.9 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; 1.9.4 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- The cited Java-file hashes in the memo were recomputed from these roots and match their source-manifest entries. Verification records say source and raw input artifacts match; original derived JAR equivalence is still unproven. The recorded original mapped-JAR hashes (`e36a366f…` and `0df10c86…`) differ from regenerated snapshots (`5c4cff3e…` and `fbcf5079…`); the available evidence does not show that these differences are metadata-only.

## Source check

The paired `PaneBlock` methods inspect north, south, west, and east. In 1.8.9, `shouldConnectTo` reads `Block.isOpaque()` (`PaneBlock.java:134–140`), which returns the constructor-cached `opaqueCube` (`Block.java:154–156, 198–207`). In 1.9.4 it reads the neighbor block's default state's `isCube()` (`PaneBlock.java:133–140`); `StateDefinition` delegates that call to `Block.isCube(state)` (`StateDefinition.java:268–270`). The listed neighboring registrations are present in both `Block.java` registries at IDs 178–180. Under the concrete precondition that a pane has only the listed provider due east and air in its other three cardinal positions, the provider's result is exactly the east connection bit; these predicates do not trigger any pane/glass special-case branch.

| Registration and exact dependency | 1.8.9 result | 1.9.4 result | East-only disposition |
|---|---:|---:|---|
| `daylight_detector_inverted`, ID 178; `DaylightDetectorBlock(true)`. Defaults `POWER=0`; old `isSolidRender()` and `isCube()` and new state-taking forms return false (`DaylightDetectorBlock.java:22–35, 98–105` / `27–40, 118–125`). | false | false | No east bit in either version. |
| `red_sandstone`, ID 179; both registries bind the same `RedSandstoneBlock` variable, whose default `TYPE` is `DEFAULT` (`Block.java:1212–1213` / `1133–1134`; `RedSandstoneBlock.java:13–20`). It inherits the base true solid-render and cube predicates. | true | true | East bit in both versions. |
| `red_sandstone_stairs`, ID 180; both registrations use the smooth red-sandstone base state. Default facing/half/shape are north/bottom/straight; old/new cube predicates are constant false. The later `setOpacity(255)` writes the opacity field and does not change the constructor-cached `opaqueCube` (`Block.java:1214–1218` / `1135–1139`; `StairsBlock.java:36–45, 64–72` / `54–64, 138–146`). | false | false | No east bit in either version. |

The memo's registration, default-state, dispatch, and mask conclusions are supported for these three providers. Its scope excludes stair collision-shape geometry and block update/support lifecycles. This verdict makes no lifecycle or non-player behavior claim, does not close the registry census or all reachable masks, and does not assert runtime validation or original derived-JAR equivalence.
