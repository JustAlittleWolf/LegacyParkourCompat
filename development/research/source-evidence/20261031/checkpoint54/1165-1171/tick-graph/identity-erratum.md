# Erratum: 1.17.1 source-manifest digest in the ordinary tick inventory

The provenance paragraph in `inventory.md` gives an incorrect 1.17.1 `mojmap.sources.sha256` digest. Preserve that original report unchanged. The correct digest is recorded in the checked three-release identity inventory at `../inventory-boundary-triage.md`; the source-side manifest is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap.sources.sha256`. A fresh PowerShell SHA-256 of that sidecar matches the corrected identity inventory. The 1.16.5 identity and the 1.17 embedded-provenance identity in the ordinary tick report are not corrected by this erratum.

This is a provenance-text correction only; it does not alter the tick route, source-member evidence, or its behavioral conclusions.
