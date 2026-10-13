# Historical record reconciliation

This directory records the content reconciliation of 340 migrated historical records. It is migration and reuse of existing evidence, not new discovery, renewed independent acceptance, complete pair coverage or runtime verification.

- [Baseline record inventory](baseline-records.json): immutable pre-reconciliation paths, hashes and original archive links.
- [Exact review identities](review-identities.json): 47 historical decisions; 44 archive finding snapshots match their recorded hashes, three exact reviewed revisions are recovered separately.
- `reviewed-snapshots/` and `review-decisions/`: byte-exact historical documents recovered from local Git. Their relative links describe the original historical tree; use the identity inventory for current locations. Do not edit these evidence bytes.

Per-record canonical/disposition/evidence-gap mappings and current implementation correspondence are being reconciled in disjoint version groups. Existing source trees and the 864-file archive are read-only. Content checks against current canonical sources do not establish original artifact/tool provenance or exact release boundaries.
