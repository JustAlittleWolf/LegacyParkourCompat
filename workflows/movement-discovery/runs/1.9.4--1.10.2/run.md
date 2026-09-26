# Discovery: 1.9.4 to 1.10.2

- Status: active (paired source comparison underway)
- Scope: client player movement; older A = 1.9.4; newer B = 1.10.2
- Repository revision and start date: c133c2999b6673874e35bbdb26759548407f3e11; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: explicit Ornithe Feather (`feather`) on both sides. A resolved to `1.9.4+build.2`; B resolved to `1.10.2+build.2`. This confirms an aligned family and exact endpoints.
- Source preparation commands and successful log evidence: A: `.\gradlew.bat --no-daemon decompileMinecraft --versions=1.9.4 --mappings=feather` (owner chat log `source-preparation.log`); B: `.\gradlew.bat decompileMinecraft --versions=1.10.2 --mappings=feather --console=plain` (`decompile-1.10.2-feather.log`, worktree root). Both logs report exact requested/resolved releases, mappings, output paths and successful completion.
- Toolchain: Gradle 9.7.1; decompiler JVM Temurin 25.0.3+9-LTS; target client bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default heap 4G and repository Vineflower options. Both remapper logs warned about access checks and recorded a repair (A 1 class/32 members; B 1 class/33 members). Inspect any relevant movement bytecode if a selected member intersects a warning.

## Artifact manifest

### A — 1.9.4

- Source root: `decompiled_minecraft/1.9.4/ornithe-feather/`; 1,819 Java files.
- Client jar: `build/minecraft-decompile-cache/1.9.4/client.jar`; 8,736,083 bytes; SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; Mojang SHA-1 `4a61c873be90bb1196d68dac7b29870408c56969`.
- CLI mode / namespace: explicit `feather` / Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.9.4+build.2`, merged-v2 artifact SHA-256 `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; extracted Tiny mapping SHA-256 `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- Remapped jar: `build/minecraft-decompile-cache/1.9.4/client-ornithe-feather.jar`, SHA-256 `bf1ac5827656840275f45c62d26d7b0671575a8131dd0fd9c0268f733c5b0a96`.
- `version.json` SHA-256 `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be`.
- Owner-recorded initial source hashes: `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `Input.java` `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009`; `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.

### B — 1.10.2

- Source root: `decompiled_minecraft/1.10.2/ornithe-feather/` (shared ignored root through this worktree's verified junction).
- Client jar: `build/minecraft-decompile-cache/1.10.2/client.jar`; SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`; Mojang SHA-1 `dc8e75ac7274ff6af462b0dcec43c307de668e40`.
- CLI mode / namespace: explicit `feather` / Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.10.2+build.2`, merged-v2 artifact `build/minecraft-decompile-cache/1.10.2/feather-gen2-1.10.2+build.2-mergedv2.jar`, SHA-256 `c42fe4366482d183f83ff62be5abfefead9d3350e7c9dd9243f8ac1a5b59ed90`.
- Remapped jar `build/minecraft-decompile-cache/1.10.2/client-ornithe-feather.jar`: SHA-256 `11d5e51f209ff559ef9e3f9b3192e1d26810bb3f83905c0e747b0bd0eb4c749d`.
- Successful log: `decompile-1.10.2-feather.log` (local/ignored); confirms exact ID, Feather build, output root, `Finished 1.10.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. No Vineflower error count was reported.
- Indexed source hashes are recorded beside findings. Original client jar resources are available for bounded local inspection.

## Correspondence and call order

- Input: A/B `net.minecraft.client.entity.living.player.KeyboardInput extends Input`; `KeyboardInput.tick()` sets axes from keys in forward/back/left/right order, sets jump/sneak, then scales both axes by `0.3` if sneaking. The bounded key-sampling slice is unchanged. B adds `Input.getMovement()`, consumed by the auto-jump feature.
- Local player: both have `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity`. `LivingEntity.tick()` invokes virtual `mobTick()` (A line 1551; B line 1587). The local class prepares input and handles sprint/jump gates. `LivingEntity` performs movement and calls virtual `move`; B adds a local-player `move` override and follow-up auto-jump scan.
- Player movement integration: `LivingEntity` uses virtual `this.move(velocityX, velocityY, velocityZ)` in the medium/ground/air paths (A lines 1341, 1391, 1420, 1448; B lines 1371, 1421, 1450, 1481). `Entity.move` collision ordering and resolution remain to be compared.
- These correspondences are confirmed by matching Feather names, inheritance, and callers. Remaining roles are indexed as slices are reached; no cross-version equivalence is inferred from class names alone.

## Coverage ledger

- S1a — keyboard direction/jump/sneak sampling: `compared-no-difference`; same ordered key predicates and `0.3` sneak attenuation.
- S1b/S2 — local input and automatic jump: `findings`; see F001. B adds a default-on client behavior.
- S1c — remaining local tick order, sprint/flight/item-use/riding/jump timers: `pending`.
- S2 remaining — player state and movement gates: `pending`.
- S3 — living movement integration: `pending` beyond the virtual movement-call correspondence above.
- S4 — entity collision, boxes, fluids and callbacks: `pending` beyond auto-jump reachability.
- S5 — block/fluid shapes and movement properties: `pending`.
- S6 — effects, enchantments, attributes and equipment: `pending`; Jump Boost is an auto-jump dependency to resolve.
- S7 — external movement influences and packet correction: `pending`.

## Dependency queue and blockers

- D1 (F001/S6): compare Jump Boost registration, amplifier and local jump integration in both versions; required to close the auto-jump height dependency.
- D2 (S5/S6): inspect relevant B and A jar resources/tags/data after movement dependencies are identified. B's jar has 0 `data/` entries, 0 `assets/minecraft/tags/`, and no recipes; its 70 loot tables are present but not yet shown relevant. Model/blockstate assets are rendering resources and are not movement evidence.
- D3 (all stages): identify any remapper warning overlap with selected movement members; inspect mapped jar bytecode if needed.

## Finding index

- [F001](findings/F001-auto-jump.md) — local player automatic obstacle jumping added (source-confirmed; runtime not performed).

## Resume checkpoint

- Last completed slices: S1a keyboard sampling (no difference); S1b/S2 automatic jump addition (F001).
- Next bounded slice: compare A/B local player `tick()`, `serverTickAi()` and `mobTick()` call order plus sprint/jump gates; then continue S2/S3.
- Outstanding dependencies: D1–D3 and all remaining pending slices.
- Assumptions to verify: affected remapper-warning classes/members do not overlap the methods used as evidence; Jump Boost and collision dependencies are accurately scoped.

## Source audit closure

- Coverage counts: 1 compared-no-difference slice; 1 findings slice; remaining S1–S7 slices pending; 0 not-applicable; 0 blocked.
- Gaps: most navigation slices remain unreviewed; resources, effect/enchantment dependencies and external inputs remain unreviewed.
- Evidence audit: paired releases and Feather mapping provenance are recorded; hashes for cited F001 sources appear in that finding. Do not claim exhaustive equivalence from the reviewed slices.
- Runtime validation: not performed (separate workflow).

