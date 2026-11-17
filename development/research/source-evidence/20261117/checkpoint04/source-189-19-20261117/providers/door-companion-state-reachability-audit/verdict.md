# Independent source audit — Door companion-state reachability

## Verdict

**ACCEPT**, limited to the source comparison and applicability disposition in the bound report. The old Door shape-input read difference is source-supported, while deterministic local-player applicability of a mismatched companion half remains unestablished. This is not an accepted movement behavior, implementation-readiness, or pair-freeze claim.

## Bound input

- Report: `build/source-189-19-20261117/providers/door-companion-state-reachability.md`
- SHA-256: `5C8F4F4C9A8C86A97CD8C617EDC25B6BE512A256E632D2B7513ABE607945E3B0`
- Exact pair: 1.8.9 → 1.9, Ornithe Feather.

## Source identity and comparison

The report hash matches the current report bytes. I recomputed hashes for the cited Java files; they match the report and the corresponding release source rows. For 1.8.9, the root sidecars are `decompiled_minecraft/1.8.9/ornithe-feather.sources.sha256` (SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`) and `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` (SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`). The 1.9 mapping-local `.decompile-provenance.json` hash is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; the five cited 1.9 Java rows also match it.

The paired `DoorBlock` sources support the stated conditional delta. In 1.8.9, collision-shape calculation calls `getCombinedMetadata`, which reads metadata at the door position and its below/above positions without checking the neighboring block identity. In 1.9, `resolveVirtualProperties` copies the companion half’s properties only when that adjacent block is the same DoorBlock. For matching lower/upper halves, both versions use the lower half’s facing/open values and upper half’s hinge/powered values; the cited code does not show a Door shape delta for these matched pairs.

The ordinary production paths reviewed support the report’s limited lifecycle conclusion. `DoorItem.place` writes a lower and upper half before neighbor notifications in both versions. The reviewed structure helper and Stronghold door sites also write corresponding lower/upper positions. `DoorBlock.neighborChanged` removes the orphan half if its companion is absent or another block. `World.neighborChanged` guards this callback with `!isClient` in both releases, so this server-side repair alone does not prove client-mirror consistency at every intermediate update.

## Applicability disposition

The generic local-player collision-query route can call into world block collision providers, but that establishes only that a Door shape may be queried; it does not establish a mismatched companion state at that query. The report correctly declines to use an arbitrary malformed snapshot as a witness. Its checked ordinary placement and structure routes create matched pairs, and the source check does not establish a deterministic local-player tick while the client mirror contains a mismatched pair. A transient update window is left unresolved rather than promoted as behavior.

The report’s bounded conclusion is therefore supported: retain the unchecked old metadata read as a conditional source observation, with malformed-pair movement applicability unestablished. No trajectory, world-generation frequency, runtime observation, or pair completion was audited or inferred.
