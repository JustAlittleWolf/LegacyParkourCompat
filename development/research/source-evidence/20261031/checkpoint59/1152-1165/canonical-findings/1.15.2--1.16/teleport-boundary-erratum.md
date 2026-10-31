# Correction: teleport boundary source identity

This corrects the false-absence statements in `.native-source/20261030/1152-1165-finish43/teleport-boundary-audit/report.md` (SHA-256 `B735812955161438D8C988DBDA8D8E8F3D18FB5E43F18DBB65F45792B15602BC`). The canonical folder for Minecraft 1.16 is named `1.16`; it is the exact version ID, not a missing `1.16.0` folder. It contains `mojmap/`, `artifacts.sha256`, `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, and `movement-diagnostics.txt`. Its ready metadata says `versionId: 1.16`, `mapping: mojmap`, and identifies the source/artifact manifest hashes. The 1.16.1 and 1.16.2 folders also contain their complete ready/provenance/source-manifest artifacts.

Exact method body result for `ClientPacketListener.handleTeleportEntity`:

- 1.16: retains the three negated `>=` near-coordinate checks and current-position fallback. Source-file SHA-256 `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d`.
- 1.16.1: same method-body and source-file hash as 1.16; retains the fallback. Its `mojmap.ready.json` pins source manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`.
- 1.16.2: removes the proximity choice and passes packet coordinates to `lerpTo` in the same `!entity.isControlledByLocalInstance()` branch. Source-file SHA-256 `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9`; ready metadata pins source manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- 1.16.2 file bytes equal manifest-backed 1.16.3; 1.16.4 and 1.16.5 share the later method body. Therefore the exact available source boundary is 1.16.1 → 1.16.2.

The earlier report's claim that 1.16.0 source was absent is a version-ID naming error. Its claims that 1.16.1 and 1.16.2 lack provenance manifests are also false; the parent artifacts and ready/provenance/source manifests are present and valid. This correction is limited to source identity and the teleport method boundary; LocalPlayer virtual dispatch to `LivingEntity.lerpTo` is covered separately in the revised finding draft.
