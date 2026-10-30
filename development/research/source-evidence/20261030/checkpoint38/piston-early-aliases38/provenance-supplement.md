# Provenance correction: early piston stair aliases

## Correction to the immutable alias report

This supplement corrects only the provenance caveat in `report.md` (SHA-256 `09c8d2420764bfa96bcb03820d233397a540532680647dcddf108baaf160d33c`). That report remains unchanged. Its claim that the 1.9.0–1.9.3 source trees lack provenance and cannot revalidate full source pins was false: I initially checked only the release-parent directory and missed the hidden provenance file embedded inside each `ornithe-feather` tree.

Each of these exact roots contains `.decompile-provenance.json`:

- 1.9: SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`
- 1.9.1: SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`
- 1.9.2: SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`
- 1.9.3: SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`

Each embedded map identifies its exact Minecraft client artifact, matching Feather Gen2 `build.2` merged-v2 mapping artifact, Feather version metadata, decompiler/runtime/toolchain, and 1,819 Java source hashes. I verified all 1,819 pins against on-disk files for every release: **7,276 / 7,276 match**, with zero missing, mismatched, or unpinned Java files. The 13 files used in the alias check also individually match each embedded source map. Full per-tree artifact locators and hashes, provenance-file digests, and pin counts are in `embedded-pin-verification.json`.

## Corrected eligibility

Withdraw the earlier provenance limitation for 1.9.0–1.9.3. The inspected exact-release trees are source-pinned, so the prior source-level result is eligible across **1.9, 1.9.1, 1.9.2, 1.9.3, and 1.9.4**: this bounded extending bottom straight cobblestone-stair witness requests `+0.36 X` in each inspected 1.9 release. The 1.8.9 distinction remains unchanged: it reaches the response but requests/resolves `+0.5625 X`, so it is not an exact `+0.36` alias.

This correction does not expand the witness, claim all stair shapes, establish pair freeze or runtime parity, or re-open the accepted 1.10.2 → 1.11.2 report. The separate sibling ready/source-manifest files are absent for 1.9.0–1.9.3; the embedded provenance and complete `sources` maps provide the exact pin evidence instead.
