# Addendum: corrected movement hash TSV

Follow-up audit of the author's formatting supplement; no canonical Minecraft source review was repeated.

- `movement-core-sha256.corrected.tsv` SHA-256: `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6` (matches the supplied hash).
- The corrected file has 21 lines: one header plus 20 data rows. The header has four actual TAB separators; every data row has four actual TAB separators.
- All 20 corrected data rows are byte-for-byte identical as text lines to the original TSV data rows.
- Original `movement-core-sha256.tsv` SHA-256 remains `c732445768bca1f6561d056997fdd4075df569af9473c4264c6c28f092058578`; original `report.md` SHA-256 remains `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb`.
- `correction-note.md` SHA-256 is `7f47f4224b88c46dd7d6f698a99ebcf80154bf63dcb302fcb073ba78dafd4e49` (matches the supplied hash), but it does not bind the claimed immutable hashes: the table cells literally contain `$origHash`, `$reportHash`, and `$corrHash`, rather than digest values. The note also contains a carriage return splitting `report.md` after its initial `r`.

Verdict: the corrected TSV validates and preserves all original data rows. The correction note's hash matches, but its contents do not provide the requested hash binding; the original artifact hashes above were independently recomputed for this addendum.
