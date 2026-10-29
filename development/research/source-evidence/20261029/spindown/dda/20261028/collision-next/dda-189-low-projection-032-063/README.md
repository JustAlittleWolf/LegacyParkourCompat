# Legacy metadata projection: IDs 32–63

This partition records the exact 1.8.9 `getStateFromMetadata` result for each assigned block ID and stored metadata nibble 0–15. `metadata-projection.tsv` has 512 rows and uses the field schema from the high-projection table. `rules.tsv` defines each decoder family and its aliases or failure behavior. `provenance.tsv` records SHA-256 hashes for the canonical source files used and the two permitted input tables.

The legacy result is source-backed. A current registry key is carried only from the assigned registry crosswalk. Every current-state projection is explicitly unresolved, so no legacy/current state equivalence is claimed. Invalid old decodes are recorded as failures where the legacy source returns a value that violates the state property's allowed values.

Source scope: canonical local 1.8.9 `ornithe-feather` decompilation. The registry crosswalk supplied the assigned IDs, decoder anchors, candidate current keys, and metadata nibble domain. The high projection table supplied only the output field schema.
