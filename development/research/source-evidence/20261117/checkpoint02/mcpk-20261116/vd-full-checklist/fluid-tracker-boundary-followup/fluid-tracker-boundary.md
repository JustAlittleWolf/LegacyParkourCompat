# Fluid current boundary follow-up

Scope: source-only closure of the accepted 1.21.11→26.1.2 LocalPlayer fluid-current finding's default water-tag reachability and first changed release. No product implementation, pair coverage, runtime, or custom datapack behavior assessed.

## Evidence identity

Canonical sources: `decompiled_minecraft/<version>/<mapping>/`; all are exact-version ready roots with pinned ready markers, source manifests, artifact manifests, and mapping-local provenance. Hashes below are SHA-256. Intermediate marker exact IDs/mappings and provenance:

- 26.1 unobfuscated: ready `954126940593C1E1E4B987BFD7079452E0D6283C3D1D97A02693F0319F66F0F5`; source manifest `B77289C135AC6E12AA35E75177CEA52D968F49D49E1EC745E510B1A786C9AEBA`; artifact manifest `03E2146CCC20C3F873CEA447CD9A5958CF8137524D66641121D6AF677A62D2A0`; provenance `6567D3AAF2940E0350860ADF0F36B52EE65BD2B23E2AC5A6696CCD70739D3E5B`.
- 26.1.1 unobfuscated: ready `29540FE56DA79D19A9B125A091CF89CDB45E4493E4C5898C677658DCA1474802`; source manifest `88AFDFB8264842713F8CCE86C0BF88E95C2C991EE706349D6ED8B6C22D52FCB3`; artifact manifest `6EE17E11C2CF02A4BA93D66852EA172D5BA857028654FB5DF3C1D9C87F0FAA81`; provenance `9DF9595443D56779D505511F5441BB360CCA71AAFCC570BC16EA8E61A1D847BF`.
- 26.1.2 identity already bound by coverage/audit: ready `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; source manifest `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; artifact manifest `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`; provenance `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`.

For each 26.1, 26.1.1, 26.1.2 root, the six relevant source files below rehash to their source-manifest entries. The three versions' files are byte-identical:

- `EntityFluidInteraction.java`: `5264FF4F1FDDEBC3FA9D63AD2EDBE2EAF617392FF946A867A6817A78B478EE62`
- `FluidTagsProvider.java`: `3E3FAB74C346256BA325B8953F4D6A6D8CF0670B182652C111053B51DE8BEC05`
- `FlowingFluid.java`: `F8C0C4430E81EFDF39B221968272ED8E3A4D030FA5192E04E04D34C9FBD37B28`
- `FluidState.java`: `E35608D5775013C67653EA350309B5150A97F36AA65C27F1C6247AE2AA840F18`
- `WaterFluid.java`: `14CB3D49EF1DB28FDAB367CD6A4F196F20B7886B8768153C6E82906E9D25F0AD`
- `Fluids.java`: `73104FA3FFDEF657D3B834B5F4D295C61CA5AED10772727389C5174EAD0FECCD`

## Gate results

- **Default vanilla water-tag reachability: closed.** `net/minecraft/data/tags/FluidTagsProvider.java:addTags` in 1.21.11 and all checked 26.1 releases puts both `Fluids.WATER` and `Fluids.FLOWING_WATER` in `FluidTags.WATER`. `Fluids.java` registers `FLOWING_WATER` as `minecraft:flowing_water`; `WaterFluid.Flowing` exposes the flowing `LEVEL` state property. `EntityFluidInteraction` samples `fluidState.getFlow(...)` for matching nonempty water-tag cells. `FlowingFluid.getFlow` produces a nonzero horizontal vector when a same-fluid horizontal neighbor has a different `getOwnHeight`; its explicit height-gradient accumulation and normalization make such a flowing state reachable. The accepted witness can therefore use a default vanilla water-tag flowing cell for a non-flying, resting LocalPlayer; no custom tag contribution is needed.
- **First changed release: 26.1, exact among the prepared stable versions.** 1.21.11 has no `EntityFluidInteraction` source and uses the old nonzero-current gate as recorded in the accepted endpoint packet. The 26.1 tracker already has `currentCount != 0 && !(accumulatedCurrent.lengthSqr() < 1.0E-5F)` at `EntityFluidInteraction.Tracker.applyCurrentTo`. The same source hash/logic persists unchanged in 26.1.1 and 26.1.2. 26.1 is the next stable version after 1.21.11; thus the cutoff is 26.1, not merely an unresolved endpoint interval.

Remaining boundary: custom datapack/mod tag edits may alter membership and are outside this default-vanilla reachability closure. This follow-up does not address other source gates or establish pair-wide coverage, implementation, or runtime parity.
