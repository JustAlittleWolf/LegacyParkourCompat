# R2: exact 1.21 Mojmap provenance audit

This report supplements the original edge-probe finding; `report.md` is preserved unchanged. It checks the version roots, embedded source provenance, and archived decompiler inputs separately.

## Version-root inventory

- **1.20.6:** `decompiled_minecraft/1.20.6/` contains one mapping output, `mojmap/`, and the release-level files `artifacts.sha256`, `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, and `movement-diagnostics.txt`. The ready marker names exact release `1.20.6`, mapping `mojmap`, and the source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- **1.21:** `Get-ChildItem -Force decompiled_minecraft/1.21` lists only the `mojmap/` mapping output. Its root does **not** contain `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, or `artifacts.sha256`. `Get-ChildItem -Force decompiled_minecraft/1.21/mojmap` lists `com/`, `net/`, and `.decompile-provenance.json`; there is no second mapping output/flavor under this exact release root.

Therefore the conventional release-level marker set is absent for 1.21. This is not the whole provenance result: the actual 1.21 Mojmap source tree has a complete embedded provenance map, and its inputs can be independently verified below.

## Exact 1.21 source provenance

The embedded `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json` has SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. It records the pinned decompile pipeline, Windows/Java 25.0.3 runtime, Mojmap client-mapping input, and source hashes. The local archived `.minecraft-decompile-cache/1.21/version.json` has `id` `1.21`, and its pin sidecar ties it to the exact `.../1.21.json` metadata URL.

I checked the embedded hash map against every Java file in `decompiled_minecraft/1.21/mojmap`: **5,363 manifest entries, 5,363 Java files, zero missing/unmatched paths and zero hash mismatches**. The exact source tree is internally consistent with its embedded source manifest.

## Archived input blobs (separate from source tree)

The actual archives are in `.minecraft-decompile-cache/1.21/`, not inferred from the decompiled source files. I checked every input URL and SHA-256 in the embedded provenance against the local archived blob and its `.pin.json` sidecar: **73/73 inputs found and matching; zero hash or URL/pin mismatches; zero missing pin sidecars**.

Key primary inputs:

| Input | Archived cache file | SHA-256 |
|---|---|---|
| Release metadata URL ending `/1.21.json` | `version.json` | `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a` |
| Official Mojmap mapping URL ending `/client.txt` | `client_mappings.txt` | `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1` |
| Client URL ending `/client.jar` | `client.jar` | `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` |

The cache also contains `client-mojmap.jar` (derived mapped artifact; it is not a raw provenance URL input and has no `.pin.json` sidecar). The 73/73 input check includes the pinned raw client, mappings, version metadata and all recorded library inputs. No source/output marker was fabricated or written during this audit.

## Provenance conclusion for the bounded comparison

The missing conventional 1.21 ready/provenance/source-manifest sidecars should remain recorded as missing. However, exact 1.21 provenance is established through the exact pinned release metadata, client and Mojmap mapping inputs; their actual local archives and pins; the complete embedded source hash map; and successful local verification of all 5,363 source hashes. Only Mojmap exists as a source-output mapping flavor at `decompiled_minecraft/1.21/`. This resolves the source-identity concern for the already reported 1.20.6–1.21 edge-probe comparison; it does not change that comparison's no-difference verdict or expand its movement scope.
