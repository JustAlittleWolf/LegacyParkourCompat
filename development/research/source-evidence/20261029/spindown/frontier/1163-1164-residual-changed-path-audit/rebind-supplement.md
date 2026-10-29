# Residual audit revision rebind supplement

Date: 2026-10-29.

I re-opened the current closure and TSV after the closure author’s revision/rebind request. Their exact SHA-256 values are:

- `closure.md`: `ac47558c7fe7ac0c67a92c7cf6f94a815c61902bcec5b5ecb2bdf4a89855edb3`.
- `residual-changed-paths.tsv`: `d26252d6822e271123b146ff5b61d70496e19fc72629c16284895e22da0921fb`.

The TSV remains the same byte revision used for the original row-by-row audit. I repeated reconciliation against the canonical 1.16.3 and 1.16.4 Mojmap provenance `sources` maps and recomputed every listed source file hash. All 71 rows still match both manifests and source bytes; the result is 64 changed shared paths, 7 additions and 0 removals, with no state/hash errors and no omitted changed or added path. The original review of the residual EnderDragon, FireworkRocketEntity and ServerGamePacketListenerImpl dispositions therefore applies to this exact closure/TSV identity. No exception was found in the row evidence.

This supplement rebinds the original audit’s 71-row conclusion to the closure digest now supplied by the author. The earlier audit’s note that its then-supplied closure digest did not match the file is historical and superseded for this binding; the original audit is preserved unchanged. This remains a residual source audit only: it does not establish full-pair freeze, runtime parity, collision-shape aggregation closure, effective resource/tag membership, or closure over external packet/callback producers.
