# Cache-root correction supplement

Bound finding SHA-256: 08BF4B732E62524C24A945A6E0455A965D5C5487CD9F5D9BBD13082957655925
Immutable finding: pane-center-overlap.md

Correction: the canonical cache root is D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache. A literal Get-Item -Force probe confirms that this root exists as a directory. Literal Get-Item -Force probes for each exact version subdirectory returned ItemNotFoundException:

- D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.12.2
- D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.13.2
- D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\26.2

This corrects the earlier note's claim that the cache root itself was absent; D:\.minecraft-decompile-cache was the wrong root. It does not change the evidence limit: the three version-specific cache directories are absent, so mapping artifact binaries still cannot be re-read from this cache. Source exports, ready markers, provenance records, manifests, and cited file hashes remain the evidence used by the bound finding. This supplement records only cache-path status; it does not revise the source behavior analysis or the finding's ACCEPT disposition.

