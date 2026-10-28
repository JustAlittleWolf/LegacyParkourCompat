# Independent audit — pose resize dependency closure, revision 1

**Disposition: ACCEPT.** Revision 1 closes the prior evidence gap by pinning each 1.14.0–1.14.4 `LivingEntity` source and giving release-specific movement calls into `Entity.move` plus current-box collision-resolution anchors. The checked identities, pins, hashes, and new anchors match the canonical source tree.

## Integrity and pins

- Assigned comparison SHA-256: `C5B7B1C5DB7EBD76ADCF4F8411FD56A62B9621E69F59C15B9FDAF64797DE5CA0` (matches).
- All 44 source-file hashes in the report match the literal files under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` (44/44).
- The added Feather `LivingEntity.java` rows for 1.14.0 and 1.14.4 match their source-manifest entries. The three added Yarn rows for 1.14.1–1.14.3 match the corresponding hidden `.decompile-provenance.json` `sources` entries.
- The previously checked provenance-record and mapping input hashes for 1.14.1–1.14.3 match. The 1.14.0 version-metadata pin `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` is consistent between the literal Feather provenance and artifact-manifest record.

## Revision-specific anchor verification

The cited per-release `LivingEntity` anchors are present and call inherited movement with the current velocity:

- 1.14.0 `moveRelative`: lines 1819, 1839, 1864, 1898.
- 1.14.1 `travel`: lines 1803, 1823, 1848, 1882.
- 1.14.2 `travel`: lines 1809, 1829, 1854, 1888.
- 1.14.3 `travel`: lines 1819, 1839, 1864, 1898.
- 1.14.4 `moveRelative`: lines 1826, 1846, 1871, 1905.

Each corresponding `Entity.move` delegates to its collision path, and the report's referenced helper anchors inspect the already stored shape or bounding box: 1.14.0 checks `getShape()` at 568/578/588; 1.14.1 checks `getBoundingBox()` at 567/577/587; 1.14.2 at 569/579/589; 1.14.3 at 576/586/596; 1.14.4 checks `getShape()` at 579/589/599. I verified the surrounding `Entity.move` bodies call those helpers. This supports the revised source-closure claim that the updated dimensions feed ordinary movement collision resolution.

The pose selection/fit and world collision/border paths checked in the initial audit remain accurately described in this revision. The added evidence resolves the prior `LivingEntity` identity/anchor omission. No material source contradiction or further closure gap was found within the stated slice.

## Scope

This audit used the assigned revision, canonical read-only decompiled sources, and literal provenance/cache pin records. No product implementation, history, backlog/wiki material, prior audit, build, test, game, or runtime was inspected or run.
