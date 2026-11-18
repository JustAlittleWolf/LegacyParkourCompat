# Source identity sidecar: Feather 1.10.2 → 1.11

This sidecar supplements `report.md` without changing its bytes. The exact local source identities are verified by the version-root readiness, provenance, source-hash, and artifact-hash files. The earlier report's provenance caveat was too broad: the trees do not have a *nested* `.decompile-provenance.json`, but both version roots do have `ornithe-feather.ready.json`, `ornithe-feather.provenance.json`, and `ornithe-feather.sources.sha256`.

## Manifest integrity and release/mapping pins

### Older endpoint: 1.10.2

- `ornithe-feather.ready.json` SHA-256: `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`.
- `ornithe-feather.provenance.json` SHA-256: `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`.
- `ornithe-feather.sources.sha256` SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, matching the ready marker. All 1,845 listed source files exist and match their entries.
- Ready marker pins `versionId` and metadata ID `1.10.2`, mapping `ornithe-feather`, client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`, and mapping files `feather-gen2-1.10.2+build.2-mergedv2.jar` / `feather-gen2-1.10.2+build.2.tiny`.
- `artifacts.sha256` SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` matches the ready marker. Its entries pin version metadata JSON to `d0624badea6367233d47c830341952126cdb0f99b1e11fb60428dd530a0d4db5`, the merged Feather JAR to `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`, and Feather Tiny file to `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`. The client entry matches the ready marker's client SHA.
- Provenance records the `--versions=1.10.2,1.11.2,1.12.2 --mappings=feather` run and the exact Feather build.2 mapping filenames. Its hash is directly recorded above; unlike the 1.11 ready marker, the 1.10.2 ready marker has no `provenanceSha256` field. Thus this sidecar binds the observed provenance file by its computed hash, while the root artifact manifest and ready marker bind the release/client/mapping inputs.

### Newer endpoint: 1.11

- The canonical version/metadata ID is `1.11` (the exact release commonly written as 1.11.0 in prose); mapping is `ornithe-feather`.
- `ornithe-feather.ready.json` SHA-256: `3c4102230db0a9f9d2d7f52bbd2a19d2717375f6e31fb618c0450b7fd82bd45`.
- `ornithe-feather.provenance.json` SHA-256: `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`, matching the ready marker's `provenanceSha256`.
- `ornithe-feather.sources.sha256` SHA-256: `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`, matching the ready marker. All 1,918 listed source files exist and match their entries.
- Provenance records requested and resolved version ID `1.11`, mapping option `feather`, client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`, and mapping files `feather-gen2-1.11+build.2-mergedv2.jar` / `feather-gen2-1.11+build.2.tiny`.
- `artifacts.sha256` SHA-256 `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` matches both ready marker and provenance. Its entries pin version metadata JSON to `a77e2568ef2f6733064c394eb40a7f1d8ca8ed48c56ee17cb6c720aeaf0118f4`, merged Feather JAR to `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`, and Feather Tiny file to `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`. The client entry matches the ready marker and provenance client SHA.

## Cited Java source verification

For each cited Java file in `report.md`, I checked the SHA-256 against its endpoint's `ornithe-feather.sources.sha256` entry and recomputed the current file SHA-256. Every pair matched. This includes `Entity.java`, `GameRenderer.java`, `LivingEntity.java`, `MathHelper.java`, `PlayerEntity.java` at both endpoints, and 1.11 `LocalClientPlayerEntity.java`. The exact hashes are already listed in `report.md`.

These version-root manifests correct the source-provenance qualification in `report.md`: the inspected trees are tied to exact release IDs, Feather mapping build.2 artifacts, and source hashes. The content of `report.md` remains unchanged; its SHA-256 remains `AC35E7276F6B76A715556DAE840C48C677BAD9A9664538274FF6A238D996518B`.
