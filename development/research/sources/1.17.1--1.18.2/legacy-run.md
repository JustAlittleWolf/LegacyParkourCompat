# Source record: 1.17.1 → 1.18.2 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.17.1--1.18.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Naming: aligned official Mojang names. Both sides explicitly generated with `mojmap`, using their own exact version's Mojang mappings.
- A source command (from owner-provided run context): `decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful generation and artifacts verified by coordinator; no A raw Gradle log copied into this worktree.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.18.2 --mappings=mojmap`; output confirmed exact requested/resolved 1.18.2, official mapping applied, finished with mojmap, build successful. Live tool log is not persisted.
- Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS decompiler JVM; B requires Java 17 bytecode. Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1. Vineflower warning severity; several lambda “processed twice” warnings noted at B generation, to disposition if relevant.

Paths in this manifest are relative to the respective `decompiled_minecraft/<version>/mojmap/` source root. SHA-1 values are publisher identities; SHA-256 values were computed locally.

### A: Minecraft 1.17.1

- Exact version JSON ID: `1.17.1`; source owner cache: its isolated `build/minecraft-decompile-cache/1.17.1/`.
- Original client URL: `https://piston-data.mojang.com/v1/objects/8d9b65467c7913fcf6f5b2e729d44a1e00fde150/client.jar`; publisher SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`; local SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; publisher SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`; local SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`.
- Remapped jar local SHA-256 `7277878475794E10CC0169A3E6F431C039186F366B9A5FF0741331EE50E0847C`.
- Shared source root `decompiled_minecraft/1.17.1/mojmap/`; verified B-side access to A `LocalPlayer.java` SHA-256 `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812`.
- Never regenerate A; its owner resolved a prior remapped-jar lock and supplied the verified artifacts/source.

### B: Minecraft 1.18.2

- Exact resolved version `1.18.2`; version JSON release time `2022-02-28T11:42:45+00:00`; client bytecode Java 17.
- Original client URL: `https://piston-data.mojang.com/v1/objects/2e9a3e3107cca00d6bc9c97bf7d149cae163ef21/client.jar`; publisher SHA-1 `2e9a3e3107cca00d6bc9c97bf7d149cae163ef21`; SHA-256 `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/a661c6a55a0600bd391bdbbd6827654c05b2109c/client.txt`; publisher SHA-1 `a661c6a55a0600bd391bdbbd6827654c05b2109c`; SHA-256 `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`.
- Remapped jar SHA-256 `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`.
- Source root `decompiled_minecraft/1.18.2/mojmap/`; 4,236 Java source files.
