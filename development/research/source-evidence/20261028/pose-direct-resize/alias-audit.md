# Independent audit: 1.13 pose alias and application closure

**Verdict: ACCEPT.** The comparison is accurately bounded to Ornithe Feather 1.13, 1.13.1, and 1.13.2. I found no material source, identity, or line-reference discrepancy in the claims audited.

## Audited inputs and identity

- Comparison: `.native-source/20261028/pose-113-alias-closure/comparison.md`
- Comparison SHA-256 (recomputed): `838688222D57EF3CF5ABC0421A72B7E414399294F7440FFBAD47203C44F28BBE` (matches the assigned pin).
- Canonical source tree inspected read-only: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

I directly enumerated each exact version directory and its `ornithe-feather` directory with `Get-ChildItem -Force`. The only mapping-directory entry was `net`; no `.decompile-provenance.json` existed at either checked level for any of the three versions. The comparison's named provenance/ready/verification sidecars were present. Their direct hashes match its claims:

| Version | provenance | ready marker | source manifest | additional pin |
|---|---|---|---|---|
| 1.13 | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | artifact manifest `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228269c932d3abba` | artifact manifest `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |
| 1.13.2 | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | artifact manifest `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`; verification `5fd1e406a0ccc1c7a57e18cd7566f23332e1bf3c3322b59a4b25ae2d1f2d7bba` |

The provenance records directly name the 1.13 and 1.13.1 mapping coordinates and run. The 1.13.2 provenance is sparse and lacks a resolved coordinate; its ready marker and artifact/source manifests provide the stated version, namespace and artifact pins. Its verification record states 2,711 source files verified with zero source-file differences, while separately recording one derived remapped-jar difference; the comparison correctly makes no stronger claim that every mapping artifact was identical.

Direct source SHA-256s were recomputed for all six cited Java files in all three releases and agree with the cited source manifests and comparison table:

| Version | `PlayerEntity.java` | `Entity.java` | `WorldView.java` | `World.java` | `LocalClientPlayerEntity.java` | `ClientPlayerEntity.java` |
|---|---|---|---|---|---|---|
| 1.13 | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.1 | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |
| 1.13.2 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` |

## Findings

- **Pose selection and resize:** Direct inspection confirms the cited ordered selector and dimensions, `getShape()`-anchored candidate box, literal `hasNoCollisions(null, box)` call, and guarded `setSize` call in 1.13 `PlayerEntity.java:333–360` and 1.13.1/1.13.2 `:334–361`. The three method bodies are text-identical.
- **Resize write:** `Entity.setSize` is text-identical at `:285–302` in all three versions. It stores dimensions first; width shrink reconstructs around current x/z and returns; otherwise it expands from the current box minimum. The expansion move is gated by `!firstTick && !world.isClient`, matching the comparison.
- **Ordinary local-player path:** `LocalClientPlayerEntity` extends `ClientPlayerEntity` (1.13 `:87`, later `:89`); `ClientPlayerEntity` extends `PlayerEntity` (`:22` in all versions) and contains no `tick` override. Local tick calls `super.tick()` when the current chunk is loaded (1.13 `:179–181`, later `:181–183`); the inherited `PlayerEntity.tick` reaches `updatePlayerPose` at its tail (1.13 `:253`, later `:254`). This supports the comparison's ordinary-tick reachability statement.
- **Collision-query boundary:** In 1.13, `WorldView.getCollisions` returns one `VoxelShape` and its `hasNoCollisions` tests `.isEmpty()` (`WorldView.java:199–218, 270–276`). With null entity, entity-dependent border-state updates and entity merging are skipped. In 1.13.1/1.13.2, `WorldView.getCollisions` returns `Stream<VoxelShape>` and `hasNoCollisions` applies `allMatch(VoxelShape::isEmpty)` (`:166–178, 201–207`); `World.getCollisions` appends entity collisions only for non-null entity (`World.java:1755–1758`). With the literal null pose argument, those paths check block collision shapes and do not include entity collision shapes or the entity-dependent border shape. The collision representation/query boundary is supported; equivalence of resulting geometry is correctly left open.
- **Consumers and limits:** The cited `Entity.move` paths read and mutate the entity shape and fetch collision geometry (1.13 `Entity.java:589–615`; 1.13.1/1.13.2 `:589–615`). Player eye height reads pose/dimension state (1.13 `PlayerEntity.java:1853–1864`; later `:1859–1870`), and fluid submersion samples at `y + getEyeHeight()` (1.13 `Entity.java:1041–1049`; later `:1042–1050`). These citations support direct consumer links only, consistent with the stated boundary.

## Audit boundary

This audit verifies only the pinned comparison and literal canonical source/provenance material for 1.13, 1.13.1 and 1.13.2. It does not establish behavior in other versions, geometry-wide equivalence of the collision API change, complete movement parity, runtime behavior, or any product implementation claim. No build, tests, or game/runtime actions were performed.
