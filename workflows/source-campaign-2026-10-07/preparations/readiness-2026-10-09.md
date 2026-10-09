# Shared source readiness — 2026-10-09

## Requested pairs

The canonical publication root is `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready`. All six exact endpoint source roots exist. For these endpoints, the ready marker, source manifest, artifact manifest, diagnostics file, and provenance file were checked by their file hashes; the marker-declared source, artifact, and diagnostics hashes match the files currently present. This was a bounded publication check, not a rehash of source files.

| Pair / endpoint | Namespace and absolute source root | Ready marker (SHA-256) | Source manifest (SHA-256) | Artifact manifest (SHA-256) | Provenance (SHA-256) |
|---|---|---|---|---|---|
| 1.17.1 | `mojmap` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.17.1\mojmap` | `mojmap.ready.json` — `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `mojmap.sources.sha256` — `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `artifacts.sha256` — `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `mojmap.provenance.json` — `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` |
| 1.18.2 | `mojmap` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.18.2\mojmap` | `mojmap.ready.json` — `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `mojmap.sources.sha256` — `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `artifacts.sha256` — `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `mojmap.provenance.json` — `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.21.4 | `mojmap` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.4\mojmap` | `mojmap.ready.json` — `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `mojmap.sources.sha256` — `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `artifacts.sha256` — `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `mojmap.provenance.json` — `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` |
| 1.21.5 | `mojmap` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.5\mojmap` | `mojmap.ready.json` — `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` | `mojmap.sources.sha256` — `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `artifacts.sha256` — `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | `mojmap.provenance.json` — `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` |
| 1.21.11 | `mojmap` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.21.11\mojmap` | `mojmap.ready.json` — `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `mojmap.sources.sha256` — `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `artifacts.sha256` — `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `mojmap.provenance.json` — `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` |
| 26.1.2 | `unobfuscated` — `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\26.1.2\unobfuscated` | `unobfuscated.ready.json` — `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `unobfuscated.sources.sha256` — `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `artifacts.sha256` — `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `unobfuscated.provenance.json` — `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` |

Exact requested and resolved release IDs in the markers match all six endpoints. The `1.17.1 → 1.18.2` and `1.21.4 → 1.21.5` pairs use Mojmap on both sides. The `1.21.11 → 26.1.2` pair uses Mojmap and native unobfuscated official names; the 26.1.2 provenance records the unobfuscated CLI mode. On that side, mapping and remapped-jar inputs are not applicable because the release is published unobfuscated. The 1.17.1 and 1.18.2 provenance JSONs leave `mappingArtifacts` null, but their artifact manifests include the exact release's `client_mappings.txt`, mapped client JAR, and original client JAR hashes.

**Consumer dependency:** use the source roots above and verify each source/resource file cited by a report against the corresponding source manifest. Verify raw inputs cited from the artifact manifest as needed. This readiness check does not verify individual source-file hashes, method bodies, source correspondence, or movement behavior; those remain the consumer's responsibility. No missing endpoint artifact dependency was found at marker level. The source publication does not itself close any discovery slice.

## Other exact endpoint availability

The marker-only inventory contains **34 readiness markers across 33 exact release IDs**; 1.14.4 has both Feather and Mojmap markers. Availability below is marker inventory only: these remaining profiles were not source-root checked or rehashed in this assignment.

| Exact release IDs | Marker namespaces present |
|---|---|
| 1.8.9, 1.9.4, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12.2, 1.13.2 | `ornithe-feather` |
| 1.14.4 | `ornithe-feather`, `mojmap` |
| 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.5, 1.19.2, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.5, 1.20.6, 1.21.1, 1.21.3, 1.21.8, 1.21.10 | `mojmap` |
| 26.2 | `unobfuscated` |

The five profiles 1.19.2, 1.19.3, 1.19.4, 1.20.1, and 1.20.2 have since passed full marker, manifest, source-file, artifact, and readability verification. See [the detailed readiness record](1.19.2-to-1.20.2-readiness-2026-10-09.md).

## Handoff

- Shared source owner: read-only publication check complete; no repair or regeneration required.
- Consumer next action: open the exact marker and manifest from the selected root, verify each cited file hash, then continue the assigned bounded source slice.
- Checks performed: marker JSON exact IDs/namespaces and output paths; root and publication-file presence; actual marker, source-manifest, artifact-manifest, diagnostics, and provenance hashes for the six requested endpoints; hash equality for marker-declared manifests and diagnostics. Marker inventory for other releases only.
- Checks not performed: source-tree rehash; source method/body review; mapping semantic review; movement interpretation; build, tests, or runtime.
