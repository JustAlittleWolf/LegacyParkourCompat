# Legacy metadata projection, IDs 0–31

This partition contains 32 registered legacy blocks × all 16 stored metadata nibbles (512 rows). The decoded side records the canonical 1.8.9 decoder result or source-backed failure. Candidate current keys are copied from the assigned registry crosswalk and do not assert state equivalence.

- Rows: 512 unique `(legacy_id, metadata)` pairs.
- Rules: 16 partition-local rules (`L0`–`L15`).
- Decoder failures: sticky piston base ID 29 at metadata 6, 7, 14, and 15. `getFacing` returns null and `StateDefinition.StateImplementation#set` rejects null.
- Current conversion: all rows remain unresolved; no current state mapping was inferred.
- Provenance: `source-hashes.sha256` records consulted canonical sources, assigned crosswalk input, schema-only high partition input, and generated TSVs.

Default-state decoders retain the exact registered instance default state; enum decoder helpers and fallback behavior are specified in the local rule table.
