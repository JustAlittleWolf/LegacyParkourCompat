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
- Player movement integration: `LivingEntity` uses virtual `this.move(velocityX, velocityY, velocityZ)` in the medium/ground/air paths (A lines 1341, 1391, 1420, 1448; B lines 1371, 1421, 1450, 1481). `Entity.move` clips Y, X, then Z in both endpoints; `World.getCollisions` and the existing `Box` axis-intersection helpers were also compared. The only observed `Entity.move` expression changes in the reviewed method replace a local `double 0.05` with the same literal; no arithmetic/order change was found.
- These correspondences are confirmed by matching Feather names, inheritance, and callers. Remaining roles are indexed as slices are reached; no cross-version equivalence is inferred from class names alone.

## Coverage ledger

- S1a — keyboard direction/jump/sneak sampling: `compared-no-difference`; same ordered key predicates and `0.3` sneak attenuation.
- S1b/S2 — local input and automatic jump: `findings`; see F001. B adds a default-on client behavior.
- S1c — remaining local tick order, sprint/flight/item-use/riding/jump timers: `compared-no-difference` for the reviewed shared gates and ordering. B's `ticksToNextAutojump` injection suppresses double-tap flight toggling for a synthetic jump (F001); sprint thresholds, item-use attenuation, riding jump handling, and ordinary double-tap jump/flight gates otherwise match. B also adds fall-flying sound bookkeeping, which does not change player motion in this method.
- S2 — player jump and movement helpers: `compared-no-difference` for the reviewed jump and relative movement calculations; local auto-jump is separately F001. Remaining player state/gates are in S1c.
- S3a — gravity and base movement in `LivingEntity.moveRelative`: `compared-no-difference` for default `NoGravity=false`; the 0.8 base multiplier and 0.15F climbing clamp retain their numeric values. `NoGravity=true` is a separate conditional candidate F002.
- S3b — server-synchronized `NoGravity=true` on a player: `blocked`; B client source accepts the flag, but whether vanilla 1.10.2 server code ever writes true for PlayerEntity is not established. See D4/F002.
- S3c — `Entity.move`, `World.getCollisions`, and existing `Box` axis clipping helpers: `compared-no-difference` in reviewed methods; F003 is a changed block input to the shared pipeline.
- S4 — entity collision, boxes, fluids and callbacks: `in-progress`; collision pipeline reviewed, entity push/fluid/callback inventory remains open.
- S5a — farmland shape/collision dispatch: `finding` F003.
- S5b — other movement-relevant block shapes, slipperiness, fluids and callbacks: `in-progress`; see source-audit closure for examined deltas and open inventory.
- S6 — effects, enchantments, attributes and equipment: `in-progress`; Jump Boost formula in ordinary jump is unchanged in reviewed methods, but registry/dependency inventory remains open.
- S7 — external movement influences and packet correction: `pending`.

## Dependency queue and blockers

- D1 (F001/S6): compare Jump Boost registration, amplifier and local jump integration in both versions; required to close the auto-jump height dependency.
- D2 (S5/S6): inspect relevant B and A jar resources/tags/data after movement dependencies are identified. B's jar has 0 `data/` entries, 0 `assets/minecraft/tags/`, and no recipes; its 70 loot tables are present but not yet shown relevant. Model/blockstate assets are rendering resources and are not movement evidence.
- D3 (all stages): identify any remapper warning overlap with selected movement members; inspect mapped jar bytecode if needed.
- D4 (S3b/F002): inspect exact 1.10.2 server-side PlayerEntity `NoGravity` writer and packet emission path to establish whether a vanilla player can receive true; if no vanilla writer exists, close as mod/server-supplied external input with that limit.

## Finding index

- [F001](findings/F001-auto-jump.md) — local player automatic obstacle jumping added (source-confirmed; runtime not performed).
- [F002](findings/F002-no-gravity.md) — conditional gravity suppression under B-only synchronized `NoGravity` state (candidate for PlayerEntity; writer path unresolved).
- [F003](findings/F003-farmland-collision.md) — farmland collision changes from full-block to its 15/16 shape.

## Resume checkpoint

- Last completed slices: S1a keyboard sampling (no difference); S1b/S2 automatic jump addition (F001); player jump/relative movement helper formulas (no difference); S3 default-false gravity and shared collision pipeline (no difference); S5a farmland collision change (F003). S3b remains blocked on D4.
- Next bounded slice: compare A/B local player `tick()`, `serverTickAi()` and `mobTick()` call order plus sprint/jump gates; then finish remaining blocks, fluids, effects/equipment and external influences.
- Outstanding dependencies: D1, D3, D4 and all remaining pending slices.
- Assumptions to verify: affected remapper-warning classes/members do not overlap the methods used as evidence; Jump Boost and collision dependencies are accurately scoped.

## Source audit closure

- Coverage counts: 5 compared-no-difference slices (S1a, S1c shared gates, S2 reviewed helper formulas, S3a default path, S3c shared collision algorithms); 3 findings/candidate slices (F001, F002, F003); 1 blocked slice (S3b/D4); S4, S5b and S6 in progress; S7 pending; 0 not-applicable.
- Gaps: this is a partial source audit, not an exhaustive equivalence claim. Local tick/gate inventory, entity push and fluid paths, remaining block properties, resource/effect/equipment dependencies and external influences remain open. S3b needs version-matched server-source verification. Door `mirror()` differs in 1.10.2 by toggling HINGE as well as rotating FACING; paired `getShape()` uses HINGE for open-door geometry, and structure/template placement calls mirror, so this is an indirect world-generation/template input rather than a direct player movement algorithm delta. It remains open as an environmental collision-state candidate pending a bounded relevance check.
- Evidence audit: paired releases and Feather mapping provenance are recorded; hashes for cited F001 sources appear in that finding. Do not claim exhaustive equivalence from the reviewed slices.
- Runtime validation: not performed (separate workflow).



