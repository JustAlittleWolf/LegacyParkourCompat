# Correction supplement rebind

Verdict: **ACCEPT** for the narrowly scoped metadata-serialization correction.

The supplement's exact SHA-256 is `c291bb9e6cb186c7ee518a6c579a196f3ed075b09c22f27e74694ec50cfe48fb8`. I independently verified its bound artifacts:

- Original finding `.native-source/20261028/collision-next/dda-fence-gate-family-legacy/FINDING.md`: `1a573fc598c9a35e4a3a89d593519272c534680301639dea35606b651f49b3db`.
- Original review `.native-source/20261028/collision-next/dda-fence-gate-family-legacy-review/REVIEW.md`: `5777493f363a20f6d3720d7b57e3de5912ccaeb8e6ac6b3d5177ad5b525e85e8`.
- Canonical 1.8.9 `FenceBlock.java`: `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9`.

The source confirms `FenceBlock.getMetadataFromState(BlockState)` explicitly returns `0` (`FenceBlock.java:149–152`). Its metadata decoder is inherited: `Block.getStateFromMetadata(int)` returns `defaultState` (`Block.java:182–184`). These are distinct methods. The inherited base `Block.getMetadataFromState` rejection (`Block.java:186–191`) is shadowed by the fence override. `FenceBlock.addCollisions` independently probes the live world neighbors, so this correction leaves the provider finding unchanged.

This rebind accepts the correction and supersedes only the metadata-serializer error identified in the original REVISE review. The original review remains immutable; all other findings and the stated limits remain as recorded there. No build, test, or runtime was used.
