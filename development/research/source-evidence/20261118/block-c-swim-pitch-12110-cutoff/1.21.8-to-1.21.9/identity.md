# Evidence identity attestation

This note binds the source-readiness verification to the unchanged comparison report:

- Report: `report.md`
- Report SHA-256: `73c08f9a052144725a50d95b40dda195bc139fe33a766fc4c08d81b8f53c6c43`
- Older source root: `decompiled_minecraft/1.21.8/mojmap/`
- Newer source root: `decompiled_minecraft/1.21.9/mojmap/`

## 1.21.8 version-root readiness

The version-root marker `decompiled_minecraft/1.21.8/mojmap.ready.json` says `status=ready`, `versionId=1.21.8`, `mapping=mojmap`, and `sourceFileCount=6105`. The marker's source-manifest digest matches the independently computed SHA-256 of `decompiled_minecraft/1.21.8/mojmap.sources.sha256`; its artifact-manifest digest matches `decompiled_minecraft/1.21.8/artifacts.sha256`.

| Sidecar | SHA-256 |
| --- | --- |
| `mojmap.ready.json` | `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7` |
| `mojmap.provenance.json` | `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa` |
| `mojmap.sources.sha256` | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` |
| `artifacts.sha256` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |

I independently recomputed SHA-256 for each of the four 1.21.8 Java files cited in `report.md`. Each byte hash matches its entry in `mojmap.sources.sha256`:

| Canonical source | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/util/Mth.java` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |

The 1.21.8 version-root sidecars therefore close source readiness and hash identity for the exact cited Mojmap source scope. The raw `.minecraft-decompile-cache/1.21.8/` is absent, so that cache's original downloaded bytes are unavailable for a fresh comparison; this does not leave a source-readiness gap for the published canonical sources cited by the report. The ready marker and provenance sidecar record the exact 1.21.8 Mojmap run and its upstream client digest.

The 1.21.9 mapping-root `.decompile-provenance.json` and all four cited 1.21.9 source hashes remain as recorded in `report.md`. No source files or sidecars were modified during this verification.

## Bound scope

This attestation covers source identity and readiness for the exact 1.21.8→1.21.9 direct player swim-pitch Y comparison in `report.md`. It does not claim pair-wide movement coverage, independent source acceptance, intermediate patch boundaries, implementation correctness or runtime parity.

**Attested result:** the original report's candidate no-difference observation has no remaining source-readiness gap for its cited 1.21.8 Java source bytes. The original report bytes remain unchanged at the SHA-256 above.
