# Cache-root correction supplement

This supplement is bound to the immutable finding `voxel-repeat-axis.md`, SHA-256 `82B7E7684DD4C434C9EEDF972CC1F0274A84CEF86EBAB69A0BCFEC99BD0E244B`.

## Corrected probe

On 2026-10-28, PowerShell `Get-Item -LiteralPath ... -Force` confirmed the corrected canonical cache root exists at `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache` (Directory; no link target). Literal `-Force` probes of its exact `1.12.2`, `1.13.2`, and `26.2` child paths each returned `ObjectNotFound` / “path ... does not exist.” A `Get-ChildItem -LiteralPath $root -Force` listing showed other version folders and metadata, but none of those three exact folders.

This corrects the earlier draft's cache-path statement: `D:\.minecraft-decompile-cache` was the wrong root. The corrected root itself is present; only the three requested version subdirectories are absent.

## Evidence impact

The substantive collision math, collector ordering, caller closure and witness limits in the bound finding do not change. The source identity evidence remains the canonical source-local provenance, ready markers, source manifests and direct source hashes already recorded there. The corrected cache probe confirms that this cache root cannot supply version-local cache evidence for the three exact versions. It does not establish runtime parity, input-only reachability, 26.2 packet-assisted reachability, or pair-wide coverage.
