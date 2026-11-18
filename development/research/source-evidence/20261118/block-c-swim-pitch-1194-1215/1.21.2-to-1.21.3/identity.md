# Corrected 1.21.3 source identity

This hash-bound supplement preserves the original report unchanged. It corrects the report's provisional provenance conclusion using the canonical version-root sidecars at `decompiled_minecraft/1.21.3/`. The original report's SHA-256 was rechecked as `727a0d34eed04539298d7c944832bf3a54928fac6e00cad075aab94b4797d024`.

## Verified published source identity

`mojmap.ready.json` declares status `ready`, version `1.21.3`, mapping `mojmap`, and 5,655 source files totaling 26,544,115 bytes. The canonical `mojmap/` tree contains exactly 5,655 files and totals 26,544,115 bytes. The ready marker declares source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; the current `mojmap.sources.sha256` bytes hash to that value. All four files cited by the original report were individually rehashed and match their manifest entries:

- `net/minecraft/world/entity/player/Player.java`: `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`
- `net/minecraft/world/entity/Entity.java`: `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`
- `net/minecraft/util/Mth.java`: `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da`
- `net/minecraft/world/entity/LivingEntity.java`: `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`

Those same four SHA-256 values were independently confirmed for the available 1.21.2 Mojmap files, so the bounded comparison remains a byte-identical no-difference result.

## Provenance and artifact limits

The version-root `mojmap.provenance.json` identifies exact release `1.21.3`, namespace `mojmap`, a successful `decompileMinecraft` run using pinned tool versions, and the output manifest chain. `mojmap.ready.json` declares the artifact-manifest SHA-256 `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`; current `artifacts.sha256` bytes match. That manifest records 89 artifacts, including 1.21.3 `client.jar`, `client_mappings.txt`, and `client-mojmap.jar` hashes.

The raw artifact directory named by the provenance record is absent, and the default `.minecraft-decompile-cache/1.21.3/` is also absent. Therefore the published 1.21.3 source files and their manifest/provenance chain are locally validated, while the raw client, mapping, and remapped archive bytes could not be independently rehashed here. The provenance sidecar is not itself a cryptographic signature; this record binds its observed bytes and the ready/source/artifact identities by SHA-256.

## Sidecar identities

| File | SHA-256 |
|---|---|
| `decompiled_minecraft/1.21.3/mojmap.ready.json` | `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` |
| `decompiled_minecraft/1.21.3/mojmap.provenance.json` | `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7` |
| `decompiled_minecraft/1.21.3/mojmap.sources.sha256` | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` |
| `decompiled_minecraft/1.21.3/artifacts.sha256` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` |
| Original `report.md` | `727a0d34eed04539298d7c944832bf3a54928fac6e00cad075aab94b4797d024` |
