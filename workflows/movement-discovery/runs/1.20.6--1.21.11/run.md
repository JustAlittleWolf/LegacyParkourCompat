# Discovery: 1.20.6 to 1.21.11

- Status: active; paired comparison not started.
- Scope: client player movement; older A = exact 1.20.6; newer B = exact 1.21.11.
- Repository revision and start point: `c133c2999b6673874e35bbdb26759548407f3e11`; managed worktree branch `feat/movement-discovery-1-21`; 2026-09-26.
- Selected alignment target: Mojang official names. B uses the release's official Mojmap. A must be independently provenanced in the same official-name namespace before any paired comparison or conclusion.
- B source preparation: reused existing successful 1.21.11 Mojmap output; no Gradle task was rerun for this work. Prior command recorded in `../1.21.11--26.1.2/run.md`: `gradlew.bat decompileMinecraft --versions=1.21.11,26.1.2 --mappings=mojmap`. That combined invocation failed on the later 26.1.2 item after logging `Finished 1.21.11 using mojmap`. The raw earlier successful log was not retained; `build/movement-discovery-1.21.11-mojmap-log-extract.txt` is a labeled transcript extract only.
- Toolchain recorded by the earlier manifest: Gradle 9.7.1; decompiler JVM JDK 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default 4G heap and repository decompiler options. This run did not invoke them.
- Shared source root: worktree `decompiled_minecraft` is a verified junction to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Do not regenerate shared outputs owned by another chat.

## Artifact manifest

### A — 1.20.6

- Status: not available in this checkout at inventory time; source/cache generation and provenance belong to the adjacent 1.20.6 chat.
- Requested/resolved release, client jar, mappings, mapped jar, source root and hashes: pending that chat's provenance handoff.
- Required next action: verify exact 1.20.6 release resolution, artifact hashes and successful decompiler completion, then confirm the source uses Mojang official names. Do not start paired claims until this is done.

### B — 1.21.11

- Exact requested/resolved release: `1.21.11` / `1.21.11`, as recorded in the existing provenance manifest and successful completion extract.
- Client jar: `build/minecraft-decompile-cache/1.21.11/client.jar`; SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; publisher SHA-1 `ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd`.
- Version JSON: `build/minecraft-decompile-cache/1.21.11/version.json`; SHA-256 `13e195800429ad001c3d897dd646638b2bc9a9fc5ce01d840d440eb0f2ea5351`.
- CLI mode / naming namespace: `mojmap` / Mojang official names.
- Official mapping artifact: cached as `build/minecraft-decompile-cache/1.21.11/client_mappings.txt`; SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`; publisher SHA-1 `031a68bebf55d824f66d6573d8c752f0e1bf232a`.
- Remapped jar: `build/minecraft-decompile-cache/1.21.11/client-mojmap.jar`; SHA-256 `853daa46088f8f5c6924d08a4019394c1647d847015fe399c791872aaac5f26f`.
- Source root: `decompiled_minecraft/1.21.11/mojmap/`. It was already present; it was not regenerated for this run. Current client, version JSON, mapping and remapped-jar hashes were rechecked against `../1.21.11--26.1.2/run.md`. Existing successful-source evidence is the prior manifest plus the transcript extract noted above; no claim is made that the extract is a raw log.
- Verified source SHA-256 inventory is in [navigation-1.21.11.md](navigation-1.21.11.md). Source files remain ignored/local and are not committed.

## Correspondence and call order

No A-to-B member correspondence has been established yet. The B-only navigation inventory, exact source anchors, and call-chain seed are in [navigation-1.21.11.md](navigation-1.21.11.md). Resolve correspondence from source on both sides after A provenance is available; matching names alone are insufficient.

## Coverage ledger

All rows are pending. The B-side navigation inventory is preparation, not comparison evidence.

- Stage 1 / input and tick ordering: `pending`; pair not available.
- Stage 2 / player-specific state and gates: `pending`; pair not available.
- Stage 3 / living movement integration: `pending`; pair not available.
- Stage 4 / entity movement and collision: `pending`; pair not available.
- Stage 5 / blocks and fluids: `pending`; pair not available.
- Stage 6 / effects, enchantments, attributes and equipment: `pending`; pair not available.
- Stage 7 / external movement influences and dependency closure: `pending`; pair not available.

## Dependency queue and blockers

- `DEP-A-PROVENANCE`: exact 1.20.6 client/mapping/source provenance and success evidence are pending the adjacent chat. This blocks namespace alignment and every paired claim. Resume by verifying its manifest and artifact/source hashes; do not regenerate that output here.
- `DEP-B-RAW-LOG`: the raw successful 1.21.11 decompiler log was not retained in the earlier run. Current cached artifact hashes and source hashes match the prior manifest; the available log evidence is a transcript extract. If stronger provenance is required, request coordinator clearance before any regeneration.
- After pair alignment, open Stage 1 in navigation order and queue newly discovered dependencies as required by the workflow.

## Finding index

No findings. No paired comparisons have been performed.

## Resume checkpoint

- Completed: read-only workflow inventory; managed worktree and `feat/movement-discovery-1-21` branch created from `c133c29`; verified shared-source junction; rechecked cached 1.21.11 artifact hashes and indexed source hashes; prepared B-only navigation index.
- Next: obtain 1.20.6 provenance from the owning chat, verify exact release and official-name alignment, then build pair correspondence before comparing Stage 1 slices.
- Do not: regenerate 1.20.6 or 1.21.11 without coordinator clearance; make paired claims before A provenance; run clients or gameplay tests.

## Source audit closure

- Coverage: 0 compared; 7 navigation stages pending.
- Unresolved gaps: all paired movement slices; A provenance; raw 1.21.11 log retention limitation.
- Evidence/hash audit: existing B cached artifact identities and current indexed source hashes were verified; raw B success log was not available in this checkout history and is not represented as retained.
- Runtime validation: not performed (separate workflow).
