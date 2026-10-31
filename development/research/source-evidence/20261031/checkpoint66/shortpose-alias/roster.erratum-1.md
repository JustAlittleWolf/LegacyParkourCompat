# Roster erratum 1 — piston provider path and fallback release manifests

The frozen roster's `MovingPistonBlock.java` path omitted its `piston/` package segment. The exact source path for all assigned releases is `net/minecraft/world/level/block/piston/MovingPistonBlock.java`.

The original roster named root-level `mojmap.ready.json`/`mojmap.provenance.json` as identity metadata for every version. For exact source-only snapshots 1.16.3, 1.16.4 and 1.17, the version-level metadata is absent; the mapped source trees carry `.decompile-provenance.json` with the embedded `sources` manifest. These three release-specific embedded maps are admissible source identity evidence and will be hashed and checked against the selected files. The remaining five releases use version-root ready/provenance records and `mojmap.sources.sha256`.

No scope, release, or operation expansion follows from this correction.
