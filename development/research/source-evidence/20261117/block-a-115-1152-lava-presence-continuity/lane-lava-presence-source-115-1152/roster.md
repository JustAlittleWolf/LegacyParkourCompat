# Lava-presence source roster: 1.15 → 1.15.2

## Bounded assignment

Compare exact Mojmap source for 1.15, 1.15.1, and 1.15.2 for two related slices: (1) the `Entity.move` / `checkInsideBlocks` / `BlockState.entityInside` / `LiquidBlock.entityInside` producer of `isInLava`; (2) the locally controlled player's `LivingEntity.travel` and held-jump consumers, including tick timing and a source-lava surface witness. This is bounded source coverage, not a full movement-tick inventory or pair freeze. No statement is made about releases after 1.15.2.

## Exact source identities

All three exact version roots are `decompiled_minecraft/<version>/`, mapping namespace `mojmap`. For each root, `mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256` were read. The ready marker's release ID and mapping are exact; its source manifest SHA-256, full file count and total source bytes match the actual manifest and source tree. Every listed Java file was SHA-256 checked against the manifest (zero missing files or mismatches).

| Release | Ready status | Source files / bytes | Source-manifest SHA-256 | Client SHA-256 |
|---|---:|---:|---|---|
| 1.15 | ready | 3,329 / 17,522,461 | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |
| 1.15.1 | ready | 3,329 / 17,524,592 | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |
| 1.15.2 | ready | 3,332 / 17,543,871 | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

`artifact-identities.tsv` contains programmatically computed SHA-256 values for each marker, provenance sidecar, and source manifest. The 1.15.2 ready marker has no `provenanceSha256` field; its provenance file is nevertheless present and independently pinned in that TSV. Its marker and source manifest do bind the verified source tree. The markers' recorded output paths point to preparation output locations; this comparison uses the current exact trees under `decompiled_minecraft/` and verifies each file against the sidecar manifest.

## Paired source inventory

- Predicate producer: `net/minecraft/world/entity/Entity.java` (`move`, `checkInsideBlocks`, `setInLava`, `isInLava`); `net/minecraft/world/level/block/state/BlockState.java` (`entityInside`); `net/minecraft/world/level/block/LiquidBlock.java` (`entityInside` and fluid-state initialization).
- Local consumers and ordering: `net/minecraft/client/player/LocalPlayer.java` (`tick`, `isEffectiveAi`, `serverAiStep`, `aiStep`); `net/minecraft/world/entity/player/Player.java` (`aiStep` and `travel`); `net/minecraft/world/entity/LivingEntity.java` (`tick`, `aiStep`, `travel`, `jumpInLiquid`); `Entity.java` (`isControlledByLocalInstance`).
- Ordinary lava and separate surface-height evidence: `net/minecraft/world/level/block/Blocks.java`; `net/minecraft/data/tags/FluidTagsProvider.java`; `net/minecraft/world/level/material/Fluids.java`; `LavaFluid.java`; `FlowingFluid.java`; `FluidState.java`; plus `Entity.checkAndHandleWater` and `updateWaterState`.

`source-files.sha256` records the full-file SHA-256 for each listed source path in each release. `comparison-slices.sha256` records programmatically computed hashes of the numbered, LF-normalized source slices compared across all three releases. Both manifests are bound to the exact version root in each manifest path.
