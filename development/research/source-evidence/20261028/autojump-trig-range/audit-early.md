# Independent lookup matrix audit — early rows

Scope: the 1.9.4 absence check and matrix rows 1.10 through 1.12.2. Audited against `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` and the supplied matrix revision.

Matrix SHA-256: `13318F2DD74F66605FAF108422031FC0FE43BB944E803FBA05F41DFA50B2E696`.

## Findings

- **1.9.4:** inspected 1,819 Java source files under the canonical `1.9.4` tree. No Java source matched `auto.?jump|autojump`; the matrix's absence classification is supported.
- **1.10 / 1.10.1:** the consumer at `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` has SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` in both releases. Within `autoJump`, fallback lines 836–837 directly call `MathHelper.sin/cos(this.yaw * (float) (Math.PI / 180.0))`. Each hidden `.decompile-provenance.json` has a `sources` entry for `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` with that hash. The old provenance fields are `pipeline`, `runtime`, `tools`, `inputs`, and `sources`; no run command, batch-version list, or cache-directory field is recorded. Matrix labels **Older provenance; run details missing** are supported.
- **1.10.2:** same consumer suffix and hash; fallback calls are the same direct yaw-based `MathHelper.sin/cos` calls (lines 836–837). Its `ornithe-feather.ready.json` says `status=ready` and names `ornithe-feather.sources.sha256`; that manifest contains the exact consumer hash and relative path. **Ready/manifests** is supported.
- **1.11 / 1.11.1 / 1.11.2:** same consumer suffix, each hash `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; fallback lines 848–849 directly call `MathHelper.sin/cos` with the yaw-to-radians expression. Each ready sidecar is `ready` and its named `ornithe-feather.sources.sha256` pins that hash and path. **Ready/manifests** is supported for all three.
- **1.12 / 1.12.1:** same consumer suffix and hash `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` in each release. Fallback lines 865–866 directly call `MathHelper.sin/cos` with the yaw-to-radians expression. Each hidden `.decompile-provenance.json` `sources` map pins the consumer path to that hash; its old schema omits command, batch-version list, and cache-directory fields. **Older provenance; run details missing** is supported.
- **1.12.2:** consumer suffix hash `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; fallback lines 870–871 directly call `MathHelper.sin/cos` with the yaw-to-radians expression. Its ready sidecar is `ready` and the named source manifest contains the exact hash and relative path. **Ready/manifests** is supported.

No discrepancies found in this audit subset.
