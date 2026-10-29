# Narrow metadata coverage correction

The original `crosswalk.tsv` wording said full metadata nibble coverage was “not asserted here” on IDs 64–127. That contradicts the static verification and the per-ID projection rows. This supplement supersedes only that column wording in the corrected derived table; it does not change old decode strings, source findings, current state mappings, provider claims, or IDs 128–131.

- Original `crosswalk.tsv` SHA-256: `3D943C1D021B241088621B02C91C84E19E915E8AEBB3AFC87C7D97EA3FF36BC9`
- Corrected derived table `crosswalk-corrected.tsv` SHA-256: `FD3DE09FE023C6F14026BF66ACCB41CA623F31818208CD0C07D8C5884212E2B7`
- Projection input `.native-source/20261028/collision-next/dda-189-low-projection-064-095/metadata-current-projection.tsv` SHA-256: `3C3FFFB1B004937276CE5076061EED1F7C06C618DDD347FA2A36F4BAF9E0BC0C`
- Projection input `.native-source/20261028/collision-next/dda-189-low-projection-096-127/metadata-current-projection.tsv` SHA-256: `06BCAD8B6C4DA5773BDDA9102C889030FA82E883CD0963B385EDB7B6EC02CD01`
- Recomputed condition: each ID 64–127 has exactly metadata values 0 through 15, once each.
- IDs 128–131 retain their existing metadata-coverage statement.
