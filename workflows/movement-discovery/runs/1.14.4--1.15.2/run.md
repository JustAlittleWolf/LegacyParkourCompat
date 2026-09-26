# Discovery: 1.14.4 to 1.15.2

- Status: blocked
- Scope: client player movement; older A = exact Java Edition 1.14.4; newer B = exact Java Edition 1.15.2.
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojang official names / Mojmap is the planned common namespace. B was explicitly decompiled with `mojmap`. A has an existing `decompiled_minecraft/1.14.4/mojmap` tree in the shared source root, but this checkout has no matching 1.14.4 cache or run provenance. That tree is a navigation aid only until its owner provides artifact and mapping provenance. No pairwise claims are made yet.
- Source preparation command and log: B: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`; successful transcript extract (not raw log): `source-preparation-transcript-extract.txt`.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; forked JDK 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; mapping-io 0.9.1; ASM 9.10.1; default decompiler heap 4G. B only; the exact invocation used Gradle default JVM options apart from isolated Gradle user home.

## Artifact manifest

### A — Minecraft 1.14.4

- Source root: `decompiled_minecraft/1.14.4/mojmap` (shared existing tree; navigation only, provenance unverified in this checkout).
- Client jar SHA-256, mapping coordinate/build, mapping path/hash, remapped jar hash: unresolved; obtain from the 1.14.4 owning chat. Do not regenerate this output.

### B — Minecraft 1.15.2

- Source root: `decompiled_minecraft/1.15.2/mojmap`.
- Requested and resolved release: exactly `1.15.2`.
- Command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`.
- Original client jar: SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`; size 15,531,492 bytes. Mojang version JSON SHA-256 `E974511243E845B2427AB635EBA5612481CB420A70C0630AA041EAB3EEB590C5`.
- Official client mapping URL as observed in task output: `https://piston-data.mojang.com/v1/objects/1fbf9f0bc9c326af859b3ccf71c2a8f5edc47ef8/client.txt`; publisher SHA-1 `1fbf9f0bc9c326af859b3ccf71c2a8f5edc47ef8`; local SHA-256 `65AD295B6CF63821F5D8F961C128128475E6D39386358D22EB238A3CE6E31777`; size 4,972,120 bytes.
- Remapped client jar SHA-256 `9B78CB6363696CA878D18B3A51A2E5D4AC626C9E06A4FA91B0B827FA85C2961A`; size 11,320,380 bytes.
- Source preparation transcript extract: `source-preparation-transcript-extract.txt`; exact output and warnings recorded there.
- Relevant source hashes/lines and resource entry hashes: add when citing comparison slices. Original client jar is cached at `build/minecraft-decompile-cache/1.15.2/client.jar` and supplies resources because the Java saver omits them.

## Correspondence and call order

Pending verified A provenance. Existing A Mojmap tree may be used only as a navigation aid. Resolve fully qualified classes, member signatures, inheritance, callers and state read/write order before paired conclusions.

## Coverage ledger

All seven ordered stages are pending pair provenance and comparison. No stage is marked no-difference or findings without verified evidence from both sides.

- Stage 1 — local input and tick ordering: blocked pending A provenance.
- Stage 2 — player-specific state and gates: blocked pending A provenance.
- Stage 3 — living movement integration: blocked pending A provenance.
- Stage 4 — entity movement and collision: blocked pending A provenance.
- Stage 5 — blocks and fluids that produce movement inputs: blocked pending A provenance.
- Stage 6 — effects, enchantments, attributes and equipment: blocked pending A provenance and paired resource inventory.
- Stage 7 — external influences and dependency closure: blocked pending A provenance.

## Dependency queue and blockers

- `A-PROVENANCE`: obtain from the owner of the shared 1.14.4 output: exact requested/resolved release, successful decompiler completion, client jar identity/hash, Mojmap coordinate/hash, remapped jar hash, toolchain/options, and any relevant warning/error inventory. Do not regenerate or overwrite the owned source output.
- `B-RESOURCE-PAIRS`: inspect the 1.15.2 original client jar resources alongside 1.14.4 resources after its owner establishes jar identity.

## Finding index

None yet. No gameplay differences have been asserted.

## Resume checkpoint

- Last completed slice: exact B source generation and local provenance inventory.
- Next action: obtain A provenance from its owner; then create verified file/member correspondence and process navigation stages in order.
- Outstanding dependencies: `A-PROVENANCE`, then resource and member dependency closure for each slice.
- Current assumptions requiring verification: that the existing 1.14.4 Mojmap source tree is the output of the exact release and an intact successful decompilation. Its folder name alone is insufficient proof.

## Source audit closure

- Coverage counts: 0 compared; 7 blocked pending A provenance.
- Unresolved gaps: A artifact and mapping provenance; every paired movement slice; both sides' cited resource/dependency closure.
- Evidence/hash/correspondence audit: B artifacts hashed; no paired findings to audit yet. A source tree remains navigation-only.
- Runtime validation: not performed (separate workflow).
