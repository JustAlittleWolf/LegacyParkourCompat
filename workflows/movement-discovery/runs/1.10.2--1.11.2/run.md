# Discovery: 1.10.2 to 1.11.2

- Status: blocked
- Scope: client player movement; older A = 1.10.2; newer B = 1.11.2
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26
- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather is the planned common namespace. B was explicitly decompiled with `--mappings=feather` and resolved to Feather `1.11.2+build.2`. A has an existing local `ornithe-feather` output, but its provenance has not been supplied in this chat; namespace alignment is therefore not yet certified.
- Source preparation command and log: B command: `gradlew.bat decompileMinecraft --versions=1.11.2 --mappings=feather --no-daemon --gradle-user-home <worktree>/.gradle-home-movement-discovery`. Exact release resolution and successful completion were confirmed in the command output (`Finished 1.11.2 using ornithe-feather`; `BUILD SUCCESSFUL`, 3m 7s). Gradle did not persist a full decompiler log file; the command output is the available run record. A was not regenerated.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM Eclipse Temurin 25.0.3+9-LTS; default decompiler heap 4G; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1. No custom decompiler options.

## Artifact manifest

### A — 1.10.2

- Exact release: `1.10.2` is the present source-directory name; source output is under `../../../../decompiled_minecraft/1.10.2/ornithe-feather/` relative to this manifest.
- Client jar SHA-256, original client jar SHA-1, resolved Feather coordinate/build and mapping artifact hashes, mapped jar hash, successful command/log: **unknown in this chat; requires provenance from the owning 1.10.2 run.** Do not use this output as final evidence until resolved.
- Existing source output may be used only as a navigation aid pending provenance. It was not regenerated.

### B — 1.11.2

- Exact requested/resolved release: `1.11.2` / `1.11.2`; source root: `../../../../decompiled_minecraft/1.11.2/ornithe-feather/` (1,921 `.java` files).
- Command: `gradlew.bat decompileMinecraft --versions=1.11.2 --mappings=feather --no-daemon --gradle-user-home <worktree>/.gradle-home-movement-discovery`.
- Original client jar: `../../../../build/minecraft-decompile-cache/1.11.2/client.jar`; SHA-1 from Mojang version metadata `db5aa600f0b0bf508aaf579509b345c4e34087be`; SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- Version metadata: `../../../../build/minecraft-decompile-cache/1.11.2/version.json`; SHA-256 `c98508dfc20ed365e6666c8f44879582df033bbde0e3499782381130f3dbaa1c`.
- Mapping family/coordinate: Ornithe Feather, `net.ornithemc:feather-gen2:1.11.2+build.2` (resolved catalog build `1.11.2+build.2`). Mapping Tiny file: `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.11.2+build.2.tiny`; SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`. Downloaded merged mapping jar: `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.11.2+build.2-mergedv2.jar`; SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`.
- Remapped client jar: `../../../../build/minecraft-decompile-cache/1.11.2/client-ornithe-feather.jar`; SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Java source output is a generated reference tree; no source files or resources are committed. The saver excludes jar resources.
- The task completed successfully. Its output included remapper invalid-access warnings and reported `Fixing access for 1 classes and 34 members`; no full persistent log is available to classify warnings against movement-relevant classes. Inspect relevant source bodies and, if a warning affects a cited member, verify that member from the mapped jar before using it as evidence.

## Correspondence and call order

No A-to-B member correspondence has been accepted yet. Establish the A artifact provenance first, then resolve class names, inheritance, member signatures, callers, state reads/writes, and ordered call chains from both exact outputs. Do not infer correspondence from names alone.

## Coverage ledger

All rows are blocked at the source-pair validation gate. No pairwise behavioral comparison has started.

- Slice ID / stage / behavior: S1 / local input and tick ordering
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S2 / player-specific state and gates
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S3 / living movement integration
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S4 / entity movement and collision
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S5 / blocks and fluids producing movement inputs
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S6 / effects, enchantments, attributes and equipment
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

- Slice ID / stage / behavior: S7 / external influences and dependency closure
- Status: blocked
- A and B evidence: A provenance absent; B output exists at the manifest source root.
- Dependency closure / parent slice / related findings: A exact source provenance and successful-generation record.
- Conclusion and rationale: No behavioral conclusion; an unproven A source tree is navigation-only under the workflow contract.

## Dependency queue and blockers

- Blocker A-PROVENANCE: provide exact 1.10.2 request/resolution, repository revision, command and successful completion record; original client jar identity/hash; Feather mapping coordinate/build, mapping file/hash and mapped jar hash; JDK, Vineflower, Tiny Remapper and Mapping IO versions/options. The adjacent chat owns this output. Next action: have the owning run provide this provenance in the current chat, without rerunning or overwriting its output.
- Blocker B-LOG: the Gradle task did not persist a complete log file. The successful command output is available, but the complete warning stream is not. Before relying on B evidence, inspect any remapper/decompiler warnings that overlap cited movement members and verify affected bytecode when needed.

## Finding index

No findings. No candidates were accepted because the comparable source pair has not been certified.

## Resume checkpoint

- Last completed slice: source generation and B provenance only.
- Next bounded slice: after A provenance is supplied, establish the source pair and map logical roles for stage S1 before opening paired methods.
- Outstanding dependencies: A-PROVENANCE and B-LOG above.
- Current assumptions requiring verification: the existing A `ornithe-feather` output corresponds to exact 1.10.2, was generated successfully from the expected artifact, and uses the same Feather naming family/build identified by its owning run.

## Source audit closure

- Coverage counts by status: blocked 7; all other statuses 0.
- Unresolved gaps and limits: source-pair provenance is incomplete, so this catalog is blocked before comparison. The 1.11.2 output contains no jar resources. Runtime validation: not performed (separate workflow).
- Evidence/hash/correspondence audit: B client, metadata, mapping and mapped-jar hashes are recorded. No A artifact hashes or member correspondences are claimed. No findings cite generated source yet.
- Runtime validation: not performed (separate workflow).
