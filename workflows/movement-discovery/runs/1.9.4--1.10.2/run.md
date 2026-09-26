# Discovery: 1.9.4 to 1.10.2

- Status: active (pair comparison pending 1.9.4 source owner)
- Scope: client player movement; older A = 1.9.4; newer B = 1.10.2
- Repository revision and start date: c133c2999b6673874e35bbdb26759548407f3e11; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather on both sides, explicit `feather` mode. The 1.10.2 side completed with Feather Gen2 mappings `1.10.2+build.2`; 1.9.4 provenance/output is owned by the adjacent-version chat and is not yet available in the shared source root. Pair alignment remains to be confirmed from that side's mapping coordinate and provenance.
- Source preparation command and log: `gradlew.bat decompileMinecraft --versions=1.10.2 --mappings=feather --console=plain`; successful log at `decompile-1.10.2-feather.log` (worktree root, ignored/local). Exact requested and resolved release both 1.10.2; output `decompiled_minecraft/1.10.2/ornithe-feather/`; `BUILD SUCCESSFUL`, `Finished 1.10.2 using ornithe-feather`.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM Eclipse Temurin 25.0.3+9-LTS; target client bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1. Feather mapping artifact `net.ornithemc:feather-gen2:1.10.2+build.2` (`mergedv2`); default decompiler heap (4G); no custom options beyond explicit release/family and plain console.

## Artifact manifest

### A — 1.9.4 (owned by adjacent-version source chat)

- Exact requested/resolved release: pending owner's provenance.
- Source root / client jar hash / CLI mode / mapping coordinate and SHA-256 / mapped jar SHA-256: pending; do not regenerate in this chat.
- Successful decompiler log and source hashes: pending owner's report.

### B — 1.10.2

- Exact requested and resolved release: 1.10.2 (confirmed in successful decompiler log).
- Source root: `decompiled_minecraft/1.10.2/ornithe-feather/` (shared ignored source directory, reached by verified worktree junction).
- Client jar: `build/minecraft-decompile-cache/1.10.2/client.jar`; SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`; Mojang version metadata publisher SHA-1 `dc8e75ac7274ff6af462b0dcec43c307de668e40`.
- CLI mode / naming namespace: explicit `feather` / Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.10.2+build.2`, `mergedv2`. Cached source mapping artifact: `build/minecraft-decompile-cache/1.10.2/feather-gen2-1.10.2+build.2-mergedv2.jar` (SHA-256 `c42fe4366482d183f83ff62be5abfefead9d3350e7c9dd9243f8ac1a5b59ed90`; remove the display space when checking: `C42FE4366482D183F83FF62BE5ABFEFEAD9D3350E7C9DD9243F8AC1A5B59ED90`).
- Remapped jar: `build/minecraft-decompile-cache/1.10.2/client-ornithe-feather.jar`; SHA-256 `11d5e51f209ff559ef9e3f9b3192e1d26810bb3f83905c0e747b0bd0eb4c749d`.
- Successful log: `decompile-1.10.2-feather.log` (local, untracked/ignored); contains exact release resolution, mapping coordinate/build, output path, `Finished 1.10.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. It reports remapper invalid-access warnings; inspect source/JVM bytecode before relying on any affected member. No Vineflower error count was reported.
- Relevant source hashes will be recorded alongside bounded slices after the A side becomes available. No source files are cited yet.
- Resource jar entries: none inspected yet. Original client jar retains resources for later bounded resource review.

## Correspondence and call order

Not started: must resolve exact classes, inheritance and member signatures on both versions before comparison. B-side navigation seeds are present in Feather output: `net.minecraft.client.entity.living.player.LocalClientPlayerEntity`, `KeyboardInput`, `Input`, `net.minecraft.entity.living.player.ClientPlayerEntity`, `PlayerEntity`, `net.minecraft.entity.living.LivingEntity`, and `net.minecraft.entity.Entity`. Presence only; not yet correspondence claims.

## Coverage ledger

All navigation stages remain `pending` until both exact source sides are verified and the bounded source comparison begins. No no-difference or findings statuses are claimed.

- Stages 1–7 (input/tick; player state/gates; living movement; entity/collision; blocks/fluids; effects/enchantments/attributes/equipment; external influences): pending paired source/provenance.

## Dependency queue and blockers

- D1 — 1.9.4 artifact provenance: source owner must provide exact release, selected namespace/CLI mode, successful log, client/mapping/remapped artifact hashes and source root. Pair alignment and all comparisons depend on this.
- D2 — B remapper warnings: specific warning-affected movement members, if any, require validation before use; inspect relevant decompiled member or mapped jar bytecode only if a selected slice intersects them.
- D3 — 1.10.2 client jar resources and referenced tags/defaults: pending stage 5–6 slice discovery.

## Finding index

None yet.

## Resume checkpoint

- Last completed slice: none; source preparation only.
- Next bounded slice: after D1 resolves, verify aligned Feather provenance and exact A/B release IDs, then index stage 1 input/tick entry points (`LocalClientPlayerEntity`, input classes, and callers) on both sides. Start with no more than three method pairs / roughly 300 source lines per side.
- Outstanding dependencies: D1–D3 above.
- Current assumptions requiring verification: 1.9.4 source owner used the matching Feather family/build; class and member correspondence remains unverified.

## Source audit closure

- Coverage counts by status: pending 7 stages; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: no pair comparison until the 1.9.4 owner's provenance/source is available; resource/data review has not begun.
- Evidence/hash/correspondence audit: B client/mapping/remapped artifact identities recorded; no source-level findings or source hashes cited yet. Pair alignment not yet confirmed.
- Runtime validation: not performed (separate workflow).

