# Independent audit: active Elytra climbability gate, 1.18 → 1.18.1

## Verdict

The bounded behavioral conclusion is independently supported: in both exact source trees, an already-active, dry `isFallFlying()` tick reaches the same `LivingEntity.travel(Vec3)` branch even when `onClimbable()` is true for a ladder or vine at the feet. Before glide look-vector calculations, damping, or `move`, that branch reads `getDeltaMovement()` and performs the strict gate `$$13.y > -0.5`, assigning the float literal `1.0F` to `fallDistance` only when true. No climbability test or climbability-dependent fall-distance write occurs between branch selection and the branch's `move` call. No 1.18→1.18.1 difference is present in this bounded operation.

The supplied frozen report has the correct overall outcome and movement description, but its provenance conclusion is wrong: both adjacent trees have ready and provenance manifests, and their recorded hashes match. The report also contains a typo in the `LocalPlayer.java` SHA-256. Raw cached client/mapping artifacts are absent now, so their payloads cannot be rehashed independently in this checkout; the adjacent run records and source manifests nevertheless provide exact-version/mapping provenance evidence rather than the “legacy trees without manifests” described in the input.

## Frozen input identity

Input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-118-1181-lower-gate/report.md`

SHA-256: `0549a8d8d7b3df67423796f645ea826ea70f24bb2ea58da097c66bea90c28cc9` (matches the assigned frozen identity).

## Exact source and recorded provenance checks

All paths below are relative to `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- Both `1.18/mojmap` and `1.18.1/mojmap` contain `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, `artifacts.sha256`, `movement-diagnostics.txt`, and `mojmap.success.log`. With hidden entries included, neither adjacent version directory contains `.decompile-provenance.json`; that alternate filename is not the manifest used here.
- Each ready record says `status=ready`, names the exact version and `mojmap`, and reports 4,203 source files. For each release, the ready record's SHA-256 for the source manifest, artifact manifest, provenance JSON, and diagnostics file matches `Get-FileHash` of the corresponding adjacent file. The provenance JSON's own recorded source/artifact/diagnostic hashes agree as well.
- Both provenance records name the same exact run `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, with distinct exact version IDs and Mojmap namespace. They record Vineflower 1.12.0, Java 25.0.3, Mojang version-metadata IDs, raw client and official client-mapping SHA-256 values, and remapped-client SHA-256 values. The adjacent `artifacts.sha256` lists those same expected client/mapping/remapped-client hashes.
- Relevant source-manifest entries agree with the actual file hashes on both sides. The four whole-file hashes below are equal across 1.18 and 1.18.1, so the compared method bodies and tag provider are byte-identical in these source trees:

  - `net/minecraft/world/entity/LivingEntity.java`: `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`
  - `net/minecraft/client/player/LocalPlayer.java`: `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`
  - `net/minecraft/world/entity/player/Player.java`: `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`
  - `net/minecraft/data/tags/BlockTagsProvider.java`: `c2d004e2aa3f23e24836b776ebae3c6df5a6bef91d94a4b3e6d199175669f640`

- Adjacent source-manifest SHA-256 values: 1.18 `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`; 1.18.1 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`.
- Adjacent provenance JSON SHA-256 values: 1.18 `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`; 1.18.1 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`.
- `1.18/mojmap.ready.json` SHA-256 is `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`; `1.18.1/mojmap.ready.json` SHA-256 is `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81`.
- The source trees reside under the current canonical `decompiled_minecraft/` root, while the provenance records their original campaign staging/output and artifact-cache paths. Those campaign paths and their client JAR/mapping payloads are absent in the present checkout; `.minecraft-decompile-cache/1.18` and `/1.18.1` are absent too. Thus the historical raw artifacts cannot be independently rehashed now. This limits re-verification of those payload bytes; it does not erase the adjacent self-consistent exact-run/provenance records or the verified source-manifest hashes. No regeneration or network access was performed.

## Independent behavior trace and bounds

In both `LivingEntity.java` versions, `travel(Vec3)` first enters its controlled/effective-AI block. Water handling is selected before lava handling, then the `else if (this.isFallFlying())` branch. Therefore this trace is the dry active-glide path; a water tick does not take this branch. In the active branch, the velocity read and fall-distance test/write occur at lines 2071–2074, before look-angle math at 2076 onward, the damping/write at 2098, and `move(MoverType.SELF, ...)` at 2099. These lines and surrounding ordering are the same in both releases.

`onClimbable()` at lines 1429–1443 returns true for a non-spectator whose feet state is in `BlockTags.CLIMBABLE`; the exact `BlockTagsProvider` listing at lines 566–570 includes both `Blocks.LADDER` and `Blocks.VINE`. The active Elytra branch does not consult this predicate. The vanilla jump-to-start path in `LocalPlayer.aiStep()` at lines 745–749 *does* require `!this.onClimbable()`, and `Player.tryToStartFallFlying()` at lines 1542–1551 separately requires airborne, not already gliding, out of water, no levitation, and an enabled chest Elytra. Those are upstream activation gates, not the already-active branch under review. The result is bounded to a glide that was already active before feet contact/occupancy with the climbable state; this audit does not claim that jump input starts gliding while on the ladder/vine.

After `move`, the branch has a horizontal-collision path that plays a sound and invokes `hurt` only when `!level.isClientSide`. That downstream sound/damage calculation is outside this fall-distance-gate comparison and project scope exclusions; it does not precede or gate the assignment under review. This audit does not cover all Elytra movement, activation parity, every neighboring tick writer, or the complete 1.18→1.18.1 movement pair.

## Corrections to frozen report

1. Replace “Neither tree contains `.decompile-provenance.json`” / “legacy trees without manifests” with the adjacent ready/provenance findings above. The report checked an alternate hidden filename and cache, but missed the actual adjacent files `mojmap.ready.json`, `mojmap.provenance.json`, and the two SHA manifests.
2. The report's `LocalPlayer.java` digest is mistyped. The actual matching hash in both source manifests and on disk is `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`.
3. The report's result and narrow behavioral bounds are accepted. The source evidence establishes a no-difference result for the named gate and ordering only; it is not full-pair acceptance, coverage freeze, runtime parity, or independent verification of unavailable raw artifact payloads.

