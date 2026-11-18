# 1.8.9 Ornithe Feather source identity addendum

This addendum is author-only source identity evidence for the original pitch report. It does not amend its finding text.

## Immutable binding to the original report

- Original report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-raw-pitch-setter-189-19-source\report.md`
- Original report SHA-256, recomputed read-only: `ee3c6ed28e2f1ab3b694de26e769f36f0745d0382ae667917242fbaecb7a6637`.
- The digest matches the dispatch-supplied original identity. The report was not edited.

## Version-root publication markers

Checked `decompiled_minecraft/1.8.9/` directly. This exact version root contains `ornithe-feather.ready.json`, `ornithe-feather.provenance.json`, `ornithe-feather.success.log`, `ornithe-feather.verification.json`, `ornithe-feather.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`. Therefore the nested `.decompile-provenance.json` check is not applicable to this previously accepted publication.

The ready marker says `status=ready`, version `1.8.9`, mapping `ornithe-feather`, 1,612 source files, 8,248,472 source bytes, and identifies the source and artifact manifests. It records the exact client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`. The provenance marker records Feather Gen2 `1.8.9+build.2` mapped jar and Tiny inputs, Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0, and the exact decompile invocation. The success log is explicitly only a streamed-output excerpt, not a retained complete Gradle log.

### Marker and manifest SHA-256

| File, relative to `decompiled_minecraft/1.8.9/` | SHA-256 |
|---|---|
| `ornithe-feather.ready.json` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` |
| `ornithe-feather.provenance.json` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| `ornithe-feather.success.log` | `8b63dce86b978df08b1d382eff9a5a5ca4927e8193b8ef90a812620a35dc45d0` |
| `ornithe-feather.verification.json` | `a4206eee9ffb45a6f54f93371c8275b7a6c37cf9ab1ca93d5fa0f5d889881620` |
| `ornithe-feather.sources.sha256` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` |
| `artifacts.sha256` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` |
| `movement-diagnostics.txt` | `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28` |

The source manifest, artifact manifest, and diagnostics hashes independently recompute to the values declared by the ready marker. Every row in the 1,612-entry source manifest was checked against the current published `ornithe-feather/` tree: zero missing files, malformed rows, or hash mismatches. This independently verifies the three cited source bytes from the original report:

- `ornithe-feather/net/minecraft/entity/Entity.java`: `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`
- `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`
- `ornithe-feather/net/minecraft/client/render/GameRenderer.java`: `69b0007074901e5bd617f35039f122f21c67402340fe08b3daea1b316c6e7940`

## Retained verification result and artifact availability

`ornithe-feather.verification.json` records `verified-identical-source-and-input-artifacts`: 1,612 source files checked, zero source differences, zero missing published source files, no raw-input artifact differences, and one difference among derived remap artifacts. It explains the sole artifact difference: `1.8.9/client-ornithe-feather.jar` was regenerated in the canonical cache during that verification; its historical digest remains in `artifacts.sha256`. The verification record says the decompiled source tree and raw source inputs match the published tree.

The historical artifact cache path recorded by provenance (`build/movement-campaign-2026-10-07/artifacts`) is absent in the current checkout, so I could not independently rehash the 37 payload files now. The version-root artifact manifest itself exists, hashes to the ready marker's declared digest, and the retained verification result records zero raw-input differences. This limits present-day payload revalidation; it is not a missing readiness/provenance/source-sidecar blocker.

## Scope of this addendum

No source, marker, manifest, or original report was changed. No source generation, build, test, or runtime action was performed. The addendum verifies exact source identity and retained readiness/provenance evidence; it does not assert movement parity or extend pair coverage.
