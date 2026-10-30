# 1.8.8 source identity correction supplement

This immutable supplement corrects one character in the 1.8.8 source-manifest/provenance hash table in `audit.md`. The report is left unchanged.

For `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.8/ornithe-feather/.decompile-provenance.json`, the directly verified SHA-256 is:

`588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`

The audit table mistyped it as `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`. The corrected value applies to both the 1.8.8 embedded source-manifest marker and provenance marker.

## Bound artifacts

- `audit.md` SHA-256: `5cd36398209d8363bc07d74cc40b27e7b88f2d682f412d7adf62fe62f7ec3dcf`.
- Inventory author's `.native-source/20261030/ordinary-pool/worker-185-189-complete-java-inventory/identity-correction-supplement.md` SHA-256: `8257c83c3a2fdf46305ccb0cf9786dbb9eb2cf44f2d434673d0382b786dd0faf`.

The typo does not affect the full-pair source-freeze verdict. The inventory correction confirms that all 1,612 Java files for each release match their source manifests; the adjacent matrix and all 6,448 paired path/hash records remain unchanged. The corrected identity removes a transcription error only and changes no source, per-edge comparison, terminal-family closure, or state-boundary reasoning.
