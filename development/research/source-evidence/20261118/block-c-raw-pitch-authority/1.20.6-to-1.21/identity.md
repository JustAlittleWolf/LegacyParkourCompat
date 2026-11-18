# Source identity correction for the 1.20.6 side

Bound report: `report.md`, SHA-256 `331d0c572e31ae77461f03119179db70c3299031d0f498120361fe34c66da21d`. This note supplements that immutable report; it does not change its bytes or source comparison.

## Verified canonical root evidence

The canonical `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.6` root contains the version-root sidecars. `mojmap.ready.json` records `status: ready`, `versionId: 1.20.6`, `mapping: mojmap`, `sourceFileCount: 5329`, and the source/artifact manifest hashes. Its expected hashes exactly match the bytes read:

- `mojmap.sources.sha256`: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`
- `artifacts.sha256`: `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`
- `movement-diagnostics.txt`: `558c501d2956cfa2445e9a75512dcf6c8ff7fec453c7b2f439af171162cab498`

The source manifest has 5,329 rows. The exact relied-file hashes match both its entries and the source bytes:

- `mojmap/net/minecraft/client/MouseHandler.java`: `ed1248f1d62a927907e6e10fae87a0d4ab0a99d91e44a149aeba40d05fbda1e1`
- `mojmap/net/minecraft/world/entity/Entity.java`: `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`

`mojmap.provenance.json` records exact version `1.20.6`, Mojmap, the exact run and the tools. `artifacts.sha256` records the pinned client jar (`02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`), client mappings (`27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745`), mapped client jar (`2add08d295773a0615ca595877f5fd055fa454d7c68fac56d6a4894a1e055284`) and exact `version.json` (`78a5b3480319194842a7829ae81f4e04235199c384817cf13ad2a62eb70c12a5`).

## Precise caveat

The original report’s statement that 1.20.6 provenance was unverified was incorrect: it checked only for the nested `.decompile-provenance.json` convention and cache directory, overlooking the canonical version-root readiness/provenance/source/artifact sidecars. The canonical sidecars establish the exact 1.20.6 Mojmap source identity and tie the cited source bytes to pinned inputs. A nested `.decompile-provenance.json` and raw cache in `.minecraft-decompile-cache/1.20.6` are absent, but their absence does not invalidate this separately recorded ready-root provenance. No claim is made here about revalidating source generation or runtime movement parity.
