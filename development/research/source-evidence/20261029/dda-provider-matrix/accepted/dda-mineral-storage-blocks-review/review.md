# Independent review: legacy mineral storage blocks

## Verdict

**ACCEPT** for the bounded source claims covering legacy IDs 41, 42, 57, 133, and 152: registry-key identity, propertyless/default-state metadata behavior, and default full-cube ray and movement providers at 1.8.9 and 26.2. This review does not establish complete DDA adapter coverage, visitation order, hit parity, or overall movement parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-mineral-storage-blocks-41-42-57-133-152/FINDING.md`
- Finding SHA-256: `CC9AC97180228DF26269A618A980103A50B42AF92322B64C498BCAF8477668C1`
- Source hash ledger: `.native-source/20261028/collision-next/dda-mineral-storage-blocks-41-42-57-133-152/source-hashes.json`
- Ledger SHA-256: `3EB035BF76F901391D8C295031E63339C8FC9D9555F0C57436B461A3751A249B`

## Source verification

Read-only canonical roots and release identities match the report:

| Release | Mapping | Ready status | Source files | Source manifest SHA-256 | Ready marker SHA-256 | Provenance SHA-256 |
|---|---|---|---:|---|---|---|
| 1.8.9 | ornithe-feather | ready | 1,612 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 26.2 | unobfuscated | ready | 7,055 | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |

All seven selected Java files were rehashed from the canonical source roots and compared with both the ledger and the exact release `.sources.sha256` entries; there were zero mismatches.

| Release | Source file | Verified SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `net/minecraft/block/RedstoneBlock.java` | `e05994ae006a2d04bea8fc48989518e934751b6147ebcb9c957f7679825efed3` |
| 26.2 | `net/minecraft/references/BlockItemIds.java` | `d9d57f2f04801da5d8d583c23b87b08f45d8b6c681980d1a77308753c801bac6` |
| 26.2 | `net/minecraft/world/level/block/Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| 26.2 | `net/minecraft/world/level/block/Block.java` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |
| 26.2 | `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| 26.2 | `net/minecraft/world/level/block/PoweredBlock.java` | `6bd3d574fc68da488d12cd56ea8ce22b1a6668742cff8c23fb0de5094cfa9563` |

## Findings

- 1.8.9 `Block.java` registers IDs 41/42/57/133 as `gold_block`/`iron_block`/`diamond_block`/`emerald_block`, each constructed as `Block`; ID 152 is `redstone_block`, constructed as `RedstoneBlock` (registration ranges cited in the finding). The cited 1.8.9 `RedstoneBlock` extends `Block` and only overrides `isSignalSource` and `getSignal`; it adds no state or geometry provider.
- 26.2 `BlockItemIds.java` declares the same five lowercase keys, and `Blocks.java` registers each under its corresponding ID constant. The four mineral blocks use plain `Block` registrations. `redstone_block` uses `PoweredBlock::new`; the cited `PoweredBlock` adds signal behavior, with no shape override.
- The inherited 1.8.9 metadata decoder returns the default state. The encoder returns 0 for propertyless states. The block constructor initializes the six mutable bounds to the unit cube; default `updateShape` is empty, default rayTrace clips that bounds box, and default collision provider uses the same bounds. `addCollisions` emits it when intersecting.
- In 26.2, default `BlockBehaviour.getShape` is `Shapes.block()`; default collision shape returns the state shape when `hasCollision` is true. `hasCollision` defaults to true. Each cited registration leaves it enabled and uses no custom shape provider; `PoweredBlock` also has no geometry override.

These checks support the report’s bounded conclusion: all five keys/default states and local ray/movement geometry are full-cube at both endpoints, and there is no per-query old bounds writer in these classes. The changed redstone class is a signal-provider difference only for the reviewed geometry claims. No shape-hit equivalence, DDA order, or trajectory conclusion is inferred.
