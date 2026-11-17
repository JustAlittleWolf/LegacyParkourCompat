# Independent audit: leaves state migration dependency

## Verdict

**ACCEPT** for the bounded source-side migration claims in `leaves_state_migration/coverage.md` (SHA-256 `ec5c38a6c3fe86a58a02047d51e7f5d832eed6fbc90ead028f4b744fe9b53a81`). Both endpoint source-manifest byte hashes match the reported identities: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. All 12 reported Java file SHA-256 values match the canonical source bytes and corresponding sidecar entries.

## Claims checked

- The old `LeavesBlock` accepts variant IDs below four, and `Leaves2Block` accepts IDs four and above; metadata decoding reads the two variant bits and the `DECAYABLE`/`CHECK_DECAY` bits as described. Registered variants yield 16 legal ID-18 metadata states and 8 legal ID-161 metadata states (metadata 0, 1, 4, 5, 8, 9, 12, 13). The packet does not claim outcomes for invalid/unassigned legacy `leaves2` variants.
- `DataFixes` registers `C_36170522` at schema 1451:1 and `C_58716260` at schema version 1496. The chunk conversion computes legacy block/state indices and passes them through the palette mapping. The mapped valid leaves states resolve to oak, spruce, birch, jungle, acacia, and dark oak keys while retaining `check_decay` and `decayable` before the subsequent Leaves fixer.
- The Leaves fixer initializes recognized leaf palette entries with `persistent` derived from `decayable == false` and `distance=7`; it propagates distances through recognized log neighbors over the six axial directions and leaves unreached leaf states at 7. `check_decay` is not used by this conversion. It records `UpgradeData.Sides` for boundary leaves to permit later edge processing; the note's conditional wording does not assert an actual adjacent log or a particular world outcome.
- Target `LeavesBlock` declares `DISTANCE` and `PERSISTENT`, with the stated default state.
- The inspected Anvil storage route reads chunk `DataVersion`, applies a bounded update to 1493 for versions below that, then updates from `max(1493, version)` to the default target 1631. Thus chunks entering this route below 1493 traverse both the schema-1451 flattening and registered schema-1496 leaf update. The report appropriately limits this conclusion to that inspected Anvil route.

## Boundary

The packet establishes source-defined migration behavior on the specified chunk-loading route. It does not establish that every external importer or custom storage route invokes it, handle malformed/non-vanilla metadata, establish resource/tag membership, or claim movement or pair completion. No correction is required within this scope.
