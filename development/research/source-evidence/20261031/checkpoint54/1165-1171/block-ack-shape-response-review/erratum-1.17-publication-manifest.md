# Erratum: 1.17 publication manifest location

This erratum corrects the publication-manifest limitation stated in `report.md` and the 1.17 row of `publication-hashes.tsv`. The original review and its sidecar remain unchanged.

The canonical 1.17 Mojmap publication records source hashes in the embedded file `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17\mojmap\.decompile-provenance.json`, under its `sources` object. That file's SHA-256 is `AFC9DC30CD8F6406D8EA306E64CC0B3601CC8D7C7BCD314F737573D830A79357`. The mechanical ledger `1.17-embedded-source-hashes.tsv` lists every 1.17 Java source used in this review, the embedded manifest entry, and the direct file hash; all nine entries match.

The 1.17 source publication is therefore verified. The earlier statement that 1.17 lacked publication source hashes is withdrawn. The source-only no-difference conclusion and all other review claims remain unchanged.
