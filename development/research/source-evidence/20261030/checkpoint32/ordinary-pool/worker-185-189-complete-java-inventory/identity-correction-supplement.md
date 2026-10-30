# Source identity correction supplement

This supplement corrects one provenance/manifest identity typo in `inventory.md`; that inventory remains unchanged. Directly recomputed SHA-256 for the read-only 1.8.8 source marker `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.8/ornithe-feather/.decompile-provenance.json` is:

- Correct: `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`
- Mistyped in the inventory table: `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`

The same file supplies the 1.8.8 embedded `sources` map used as the source manifest. Its corrected SHA-256 is therefore the value above for both the 1.8.8 source manifest and provenance columns. Its declared client SHA-256 remains `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8`.

I recomputed the exact source-manifest and provenance-marker SHA-256 identities for all five releases. No other manifest/provenance hash in the inventory table is mistyped. 1.8.5–1.8.8 use the embedded `.decompile-provenance.json` source maps; 1.8.9 uses `ornithe-feather.sources.sha256` and separately identifies `ornithe-feather.provenance.json`. The verified hashes are:

| Release | Source-manifest SHA-256 | Provenance SHA-256 |
|---|---|---|
| 1.8.5 | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |

This correction does not change or invalidate the Java file matrix: all 1,612 Java files per release were directly matched to the corresponding source manifest, and the four adjacent comparisons remain 6,448 records with the same path/hash values. Output hashes before and after this supplement are unchanged for the original outputs: `inventory.md` SHA-256 `0731eac7a0a6452a53fc0baefb5fb1178a856308e9046f0c6f5a2a60dfe58961`; `adjacent-source-hashes.tsv` SHA-256 `14e9dfe46e4ee15f898f36959028a0f66ad7b225d1e6e8b879cea3f130fbea6f`.
