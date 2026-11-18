# Source identity addendum

Correction and evidence identity for the frozen roster. The roots contain version-level files named `<mapping>.ready.json`, `<mapping>.provenance.json`, and `<mapping>.sources.sha256`; they do not use `.decompile-provenance.json` inside the mapping directory. The original source hashes remain unchanged.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Source-manifest binding |
|---|---|---|---|---|
| 1.14.4 Mojmap | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | Ready marker names exact `versionId=1.14.4`, `mapping=mojmap`, source count 3,250; hash equals current `mojmap.sources.sha256`. Provenance names Mojmap namespace, exact version, release/mapping inputs and toolchain. |
| 1.15 Mojmap | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | Ready marker names exact `versionId=1.15`, `mapping=mojmap`, source count 3,329; hash equals current `mojmap.sources.sha256`. Provenance names Mojmap namespace, exact version, pinned client/mapping inputs and toolchain. |

The frozen file SHA-256 values for each roster entry equal the corresponding entry in these source manifests. This establishes the local published source tree identity used for review; the decompiler output directories are copies under `decompiled_minecraft/` of the recorded ready outputs.
