# Independent source-only audit: 1.21.0 → 1.21.1

## Bounded verdict

The prior report's bounded equality finding is confirmed: the 21 selected Java roots are byte-identical, and every selected file hash matches its exact-version source manifest. Both Mojmap source trees are internally complete against their manifests (5,363 Java files and 5,363 mapped paths per side); the complete path sets are equal and exactly 17 shared paths have changed hashes. Every changed-path row agrees with both current source manifests and file hashes.

This audit does not close the release pair. The selected roots and changed-path classification do not establish complete terminal coverage of the reachable player-movement graph. No movement delta was identified in the checked roots; no claim is made about runtime parity or all indirect world-state effects.

## Immutable evidence reviewed

Prior report and sidecars:

| Evidence | SHA-256 |
|---|---|
| `report.md` | `c34799afdefac04a0e138f21443b283f98b29536e5c4fa8e799f73a86fa497ad` |
| `selected-source-hashes.tsv` | `d29ba5a747a09a5cabf642c89bc0353e2adff62cd70d92937752132cec1f3d92` |
| `changed-source-paths.tsv` | `207cc14a9de804226429078b300591b8e779589b0d781346cdfc0d1c65a70f86` |

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Source identity evidence | SHA-256 / result |
|---|---|
| 1.21.0 embedded `.decompile-provenance.json` | `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` |
| 1.21.1 release-parent `mojmap.provenance.json` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` |
| 1.21.1 `mojmap.ready.json` | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` |
| 1.21.1 `mojmap.sources.sha256` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| 1.21.1 `artifacts.sha256` | `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` |

The old embedded manifest pins 73 inputs, including `1.21.json` (`cdd1a44d…efa802a`), `client.jar` (`4b56bc9b…60617e8`), and `client.txt` (`127485d7…b0aac1`). The new ready marker identifies version `1.21.1`, namespace `mojmap`, 5,363 sources, client SHA-256 `499f6897…89b0c99`, and version metadata ID `1.21.1`; its source and artifact manifest hashes match the two files above. The artifact manifest pins the new client jar, mapping jar, and client mappings. There is no embedded `.decompile-provenance.json` inside the 1.21.1 mapping directory; its release-parent provenance, ready marker, source manifest and artifact manifest are the available publication evidence. No download or regeneration was performed.

Independent verification recomputed SHA-256 for all 10,726 Java files, compared each side to its own manifest, checked exact source path sets, checked all 21 selected rows, and checked all 17 changed rows. Results: old/new maps 5,363 each; actual Java files 5,363 each; source-map mismatches 0 on both sides; unmatched map/file paths 0; additions 0; removals 0; shared changed hashes 17; selected-row mismatches 0; changed-row manifest/hash mismatches 0.

## Changed-path inventory correction

All 17 paths were examined with exact-source diffs. Dispositions below are source-path classification, not pair closure.

| Count | Changed paths | Disposition |
|---:|---|---|
| 3 | `net/minecraft/client/multiplayer/chat/report/ReportEnvironment.java`, `net/minecraft/DetectedVersion.java`, `net/minecraft/SharedConstants.java` | Client report version string, detected-version identity/data version, and shared version/protocol constants. No player-movement operation. |
| 8 | `net/minecraft/commands/arguments/EntityArgument.java`, `GameProfileArgument.java`, `MessageArgument.java`, `ScoreHolderArgument.java`, `selector/EntitySelectorParser.java`; `net/minecraft/network/chat/contents/EntityDataSource.java`, `ScoreContents.java`, `SelectorContents.java` | Selector permission/source plumbing and chat-content parsing/resolution. No player-movement operation in these changed members. Command-triggered world/player actions are not equivalent to the local player tick and remain outside this bounded comparison. |
| 4 | `net/minecraft/world/level/block/ChiseledBookShelfBlock.java`, `LecternBlock.java`, `SculkSensorBlock.java`, `ShulkerBoxBlock.java` | `onRemove` callback ordering changes. Chiseled bookshelf moves neighbor-output notification until after `super.onRemove`; the other three call `super.onRemove` before their neighbor notification. The compared diffs do not alter these classes' collision-shape methods. These callbacks can change neighbor-signal timing and therefore are indirect world-state inputs; downstream effects on player collision state were not closed. |
| 2 | `net/minecraft/world/level/block/entity/BlockEntity.java`, `net/minecraft/world/level/chunk/LevelChunk.java` | Adds block-entity state validation on construction/set and removes an existing block entity when its state is invalid for the entity type. This is validation/entity lifecycle behavior, not player movement math. Its interaction with changed callbacks and future block-state inputs was not traced to terminal movement consumers. |

Counts reconcile exactly: 3 + 8 + 4 + 2 = 17.

## Exact remaining terminal coverage before pair freeze

The earlier report explicitly left these slices open; this review confirms those are real coverage gaps and tightens the next inventory:

1. **Block collision providers:** pair and trace the reachable collision enumeration from `Entity.move`/player travel through world and chunk queries to `BlockState`/block collision-shape providers, including all registered era-existing blocks and state/context-dependent shape methods. The unchanged `BlockState`, `BlockBehaviour`, `Blocks`, `Block`, `BlockGetter`, `Level` and `AABB` roots alone do not prove every registered provider is unchanged.
2. **Changed callback descendants:** trace the four changed `onRemove` methods and the changed block-entity validation/removal paths through neighbor updates, block-state writes, and any client-visible state update to the player collision/query terminals. Establish the applicable side and gates; a code change that only runs on a logical server is not client historical movement, but any resulting world state delivered to the client still needs an explicit scope disposition.
3. **Other block callbacks and state inputs:** inventory remaining player-reachable callback/state producers, including neighboring-block dependencies, and close their terminal paths into collision shapes and movement predicates. The 17-path global diff is a candidate list, not proof that unchanged callbacks or registrations need no downstream analysis.
4. **Resources and tags:** compare packaged resources/tags that select or parameterize blocks, fluids, collision and movement-relevant state. Java path manifests do not cover these inputs.
5. **Non-root local-player dependencies and external inputs:** close player tick branches and direct player-response paths not represented by the selected roots, including relevant fluid queries, attributes/effects/equipment/modifier inputs, pose/dimension consumers, callbacks and every received/direct state mutation that can write position or velocity. The selected packet classes establish only the named client response subset; server packet producers and other external state producers were not paired.

Pair freeze still requires the complete ordered tick/source inventory, paired evidence for every terminal slice, closed dependencies, and a separate independent coverage audit. The present audit supplies only an independent check of the prior bounded roots, source identity and its 17-path changed inventory.
