# Discovery: 1.18.2 to 1.19.4

- Status: active (B-side provenance and navigation index prepared; paired comparison not started)
- Scope: client player movement; older A = 1.18.2; newer B = 1.19.4
- Repository revision and start date: `c133c29`; 2026-09-26
- Selected naming namespace: Mojang official names. Both sides are intended to use release-specific Mojmap. B uses explicit `mojmap`; paired alignment remains pending verification of A's manifest and source hashes.
- Source preparation command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.19.4 --mappings=mojmap`; successful console transcript was returned by the task session. Raw log file was not retained. The exact release request/resolution and completion message are recorded below.
- Toolchain: Gradle 9.7.1; Loom 1.17.21; JDK 25.0.3+9-LTS decompiler JVM; target bytecode Java 17; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; repository decompiler defaults (4G heap, WARN logger, standard repository options). Other buildSrc dependencies are in the revision-pinned version catalog.
- Worktree: managed `movement-discovery-1-19`, branch `feat/movement-discovery-1-19`, based on `c133c29`. Its ignored `decompiled_minecraft` entry is a verified junction to the shared primary-checkout source root.

## Artifact manifest

### A — 1.18.2

- Exact request: `1.18.2`. The coordinating 1.18.2 owner reports successful exact Mojmap decompilation and is recording artifact hashes and anchors.
- Source root, client/mapping/remapped hashes, successful log and per-file hashes: awaiting the owner's committed manifest. No 1.18.2 output was generated or overwritten by this worktree.
- Mapping alignment: expected Mojmap-to-Mojmap; not certified until the A manifest and selected file hashes are checked.

### B — 1.19.4

- Exact requested/resolved release: `1.19.4` / `1.19.4`, confirmed in successful task output (`Decompiling Minecraft 1.19.4 (requested '1.19.4')`; `Finished 1.19.4 using mojmap`).
- Source root: `decompiled_minecraft/1.19.4/mojmap/` (through the verified shared-root junction).
- CLI mode / naming namespace: `mojmap` / Mojang official names.
- Version JSON: `build/minecraft-decompile-cache/1.19.4/version.json`; SHA-256 `DABA0C50674934924B15007863ADE2C0A8697F370781CD15E7F5AD950176CAB6`.
- Original client jar: `build/minecraft-decompile-cache/1.19.4/client.jar`; SHA-256 `0E79CF7F07C107E9A1FE22ED703E472372B44149E64114944F0811ABBD25F3EC`; Mojang publisher SHA-1 `958928a560c9167687bea0cefeb7375da1e552a8`.
- Mojang client mappings: `build/minecraft-decompile-cache/1.19.4/client_mappings.txt`; SHA-256 `5EF270B938F89CFC77371E0DEEE9F9044C41D9A373FA11DCB1FD1923272C4606`; publisher SHA-1 `f14771b764f943c154d3a6fcb47694477e328148`; version JSON identifies artifact as `client.txt` at the matching object URL.
- Remapped jar: `build/minecraft-decompile-cache/1.19.4/client-mojmap.jar`; SHA-256 `3E40F8B67DC696F4E08F2F7AFD4D4C2E263B2A86221D13141953C7FDAA80375D`.
- Successful completion: `BUILD SUCCESSFUL in 4m 26s`; 3 actionable tasks executed. Vineflower emitted processed-twice notices for ModelBakery, PalettedContainer, and CarvingMask. No decompiler error summary appeared. Loom emitted one access-fix warning for OptionInstance.ValueSet during the preceding project configuration/remapping; assess relevant method bodies if they enter a slice.
- Source hashes (SHA-256), for initial navigation anchors:
  - `net/minecraft/client/player/LocalPlayer.java`: `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`
  - `net/minecraft/client/player/KeyboardInput.java`: `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`
  - `net/minecraft/world/entity/player/Player.java`: `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`
  - `net/minecraft/world/entity/LivingEntity.java`: `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`
  - `net/minecraft/world/entity/Entity.java`: `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`
  - `net/minecraft/world/phys/AABB.java`: `594239BB50298D34533FC8EFC113F4C4B5312A41D3DC1713CC85130D5020EE5E`
  - `net/minecraft/world/phys/shapes/VoxelShape.java`: `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417`
  - `net/minecraft/world/level/material/FlowingFluid.java`: `D84CEB3C0B83DA25FB68599E16CC2298106E745A9EF0C9F32F616D097A8EC56C`
  - `net/minecraft/world/effect/MobEffects.java`: `9266FEF24206A32B67E6977580377E8FB92D860932B2EB909756E387E7DF943C`
  - `net/minecraft/world/item/enchantment/Enchantments.java`: `35FA4D1F9DA93971FA70BF6EC2F9218A8825FF343038B51143AE1404867F38E3`
  - `net/minecraft/world/item/enchantment/EnchantmentHelper.java`: `C98B9E538AAE7FEA33A125560400F346745BAC993E859FFD3912DA58F3CC1128`
  - `net/minecraft/world/level/block/Blocks.java`: `A4F2DF87C2CAE5A3827A818F39F7AD123EE5B18905EAD9947EFE654421C30FF5`
  - `net/minecraft/world/level/block/SlimeBlock.java`: `F5CE06A58CB3E46E6F3C900BDF521E2984765846DC55CA701812194D09D65F54`
  - `net/minecraft/world/level/block/SoulSandBlock.java`: `F6CC9CA6E841B16C4E739E840AC4EBCE46B5B8BD40564473911F2246D1AAB7D8`
  - `net/minecraft/client/multiplayer/ClientPacketListener.java`: `BCC74E52A32D20A3E993EBE4E08FFFE8CACA7481FAD8F326DACDC93796B2B715`
- Jar resources: the client jar contains `data/minecraft/tags/blocks/*.json` and advancement data. Resource inclusion does not establish source of all runtime data; relevant tags and values must be individually inspected and hashed per slice. Server datapacks/synchronized values remain explicit external inputs.

## B-side navigation index

See [b-navigation.md](b-navigation.md). It records only B-side class/member discovery and a reusable order/state map; it contains no A/B behavioral conclusion.

## Correspondence and call order

- Overall correspondence and paired member descriptors: pending A manifest, inheritance and call-path verification on both versions.
- B-side inheritance anchors observed: `LocalPlayer extends AbstractClientPlayer`; `Player extends LivingEntity`; `LivingEntity extends Entity`.
- B-side source call anchors and navigation notes are in `b-navigation.md`.

## Coverage ledger

No paired rows are terminal yet. Initialize each stage as pending and compare only after the A source identity and hashes are verified. B-side inventory is navigation only and cannot support a no-difference or change finding.

## Dependency queue and blockers

- A provenance: obtain the 1.18.2 owner's run manifest/log facts and verify every paired source path/hash before comparison.
- B resource closure: inspect exact client-jar resources (tags/data) referenced by movement consumers as slices reveal them; client jar may not supply server-controlled tags or datapacks.
- Decompiler warning review: if any processed-twice class or OptionInstance access warning touches a cited movement member, verify via mapped bytecode before classifying that slice.

## Finding index

None yet. No paired comparison has been made.

## Resume checkpoint

- Last completed: B exact-version Mojmap generation/provenance inventory and initial navigation anchors.
- Next: read A owner's manifest, verify A source hashes and artifact identity, then confirm Mojmap alignment. Begin stage 1 paired correspondence and bounded input/tick slice.
- Do not regenerate A or B during this run. Any output regeneration requires explicit invalidation/re-hashing of dependent evidence.

## Source audit closure

- Coverage counts: not started; awaiting A provenance verification.
- Unresolved gaps: A manifest and hashes; all paired comparisons; resource closures.
- Runtime validation: not performed (separate workflow).
