# Resource comparison and availability: 1.15.2 to 1.16.1

Frozen roster: `roster.md` in this lane. This comparison is limited to builtin vanilla fluid tag resources.

| Release | Tag | Raw includes / literal values | Effective fluid members |
|---|---|---|---|
| 1.15.2 | `minecraft:lava` | unavailable | unavailable |
| 1.15.2 | `minecraft:water` | unavailable | unavailable |
| 1.16.1 | `minecraft:lava` | unavailable | unavailable |
| 1.16.1 | `minecraft:water` | unavailable | unavailable |

No membership or change claim is made. These are unavailable inputs, not evidence of an empty tag or unchanged content.

## Availability checks and exact pins

- No `1.15.2-resource-manifest.json` or `1.16.1-resource-manifest.json` exists under `development/research/source-evidence/`.
- The version-root `mojmap.ready.json` files identify exact client archive SHA-256 values: 1.15.2 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; 1.16.1 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.
- The version-root ready records also pin artifact-manifest SHA-256 values: 1.15.2 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`; 1.16.1 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`. Their pinned artifact directory from each `mojmap.provenance.json` is `build/movement-campaign-2026-10-07/artifacts`; that directory is absent, so neither artifact manifest nor client archive is available there.
- The repository default `.minecraft-decompile-cache/` has no `1.15.2` or `1.16.1` version directory. The local launcher version directory has neither exact release. The bounded `C:/Users/Wolfi/.gradle/caches/fabric-loom` root has no entries named for either exact release.
- `mojmap.sources.sha256` exists for each release, but is a hash inventory for decompiled Java source, not the required official resource/tag data. Its file SHA-256 is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` for 1.15.2 and `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` for 1.16.1.

Result: raw tag documents, transitive includes, and effective members for both tags and both releases remain unresolved pending the exact pinned resource-bearing archive or matching resource manifests. No network access, external resource pack, wiki, or implementation evidence was used.

SHA-256 is over UTF-8 bytes of the text before this terminal line, including its preceding LF.
SHA-256: 32668cb5915e296217d86f1b6bded359e8132eb08cf37d7f2be530d56be236db
