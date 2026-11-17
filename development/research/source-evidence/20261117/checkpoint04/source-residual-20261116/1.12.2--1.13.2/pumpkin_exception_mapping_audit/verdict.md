# Independent audit: pumpkin exception state mapping

## Verdict

**ACCEPT for the bounded registered state-mapping slice** in `pumpkin_exception_mapping/coverage.md` (SHA-256 `dd9e41b2e52afdaebcb9dee55a8ebdf216348e313558c676a12913a90cabdb93`). This does not accept every importer path, establish movement behavior, or close the pair.

## Identity and source checks

- The ready markers identify 1.12.2 and 1.13.2 Ornithe Feather Gen2 build 2. Their `ornithe-feather.sources.sha256` files hash to the report's source identities: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All 12 Java file hashes in the report match the canonical file bytes and corresponding sidecar entries. The checked files are the three listed 1.12.2 sources and nine listed 1.13.2 sources.
- Old `Block.java` registers IDs 86 (`pumpkin`) and 91 (`lit_pumpkin`) to `PumpkinBlock`. Its inherited `FACING` domain is the four horizontal directions; `getStateFromMetadata` maps horizontal metadata to FACING.
- Target `Blocks.java` aliases `CARVED_PUMPKIN` to `carved_pumpkin` and `LIT_PUMPKIN` to `jack_o_lantern`. Target registrations instantiate both keys as `CarvedPumpkinBlock`. Target `pumpkin` is separately registered as the growable plant, not the flattened destination for legacy block ID 86.

## Mapping and registered paths

`C_80754635.m_11856336(index, target, oldState...)` stores the target dynamic at each index and registers each old dynamic as an input for that index. The eight cited entries are correct:

| Legacy state | Target state |
|---|---|
| `pumpkin`, facing SOUTH/WEST/NORTH/EAST | `carved_pumpkin`, same facing |
| `lit_pumpkin`, facing SOUTH/WEST/NORTH/EAST | `jack_o_lantern`, same facing |

The index values `1376–1379` and `1456–1459` agree with those targets. `m_11299400` looks up an exact dynamic input and returns its target entry; unknown dynamics pass through unchanged. `C_15874933` applies that fixer to `TypeReferences.BLOCK_STATE` and is registered at schema 1450. For the structured entity-state route, `C_29689164` maps the two legacy names to IDs 86 and 91, combines each ID with metadata, and calls `m_46884624`. The 1451:3 registration is present in `DataFixes`. The report's Anvil chunk path relies on the separately accepted leaves migration note, whose audited packet covers the 1451:1 flattening route and Anvil reader path; that reused path identity is correct.

## Pane-exception boundary

The report's limited mapping conclusion agrees with the separately accepted pane neighbor-gate evidence: `pane_neighbor_connection_gate/report.md` SHA-256 `859d87aeb4c4c0c7edf1291d32f0f2dedb038b19a25a51972be175ba0709d970`, with citation-correction audit ACCEPT at `pane_neighbor_connection_gate_audit/verdict-r2.md` SHA-256 `2be51acaaecbede0bd901c8c083d952fd4b194db9aa8617644d8aeea48f509b3`. That evidence lists old `PUMPKIN`/`LIT_PUMPKIN` and target `PUMPKIN`/`CARVED_PUMPKIN`/`LIT_PUMPKIN` exceptions. The audited data mappings show old `PUMPKIN` states go to target `CARVED_PUMPKIN`, while old `LIT_PUMPKIN` states remain target `LIT_PUMPKIN` (`jack_o_lantern`). The additional target `PUMPKIN` is a separate plant registration; this note does not infer an old predecessor for it. Neither packet compares pumpkin FaceShape values or proves all importing paths use these fixers.

## Scope limit

The claim is source-defined behavior for the cited registered Java fixer paths. Applicability to every external/custom importer, malformed inputs, and non-vanilla states remains unproven. The report makes no pane collision, movement, trajectory, or pair-completion claim. No corrections are required within this bounded scope.
