# Passenger-crouch source provenance erratum — 2026-10-08

This erratum corrects the digest metadata for the already accepted finding `1.20.1-1.20.2/F-1.20.2-PASSENGER-CROUCH-INPUT`. It does not change the finding or its bounded source claim.

## Accepted immutable snapshot

- Commit: `4ee5bbce85b193f972766c324c262e375fed9bd6`
- Path: `workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-PASSENGER-CROUCH-INPUT.md`
- Git blob: `5037e1f8f12ac444c5f565f63111733c65ce5f6e`
- Raw blob size: 6,933 bytes
- Correct SHA-256: `e1d51ee7f7395415dbe5d9d9692ea1f55cdd83cf0172de27c555e70c362a29d7`

This audit independently resolved the exact commit:path object and recomputed the raw-byte digest. The blind acceptance at `626fd4ffc3982169be5990949049ee0d2e3e1bb7` cites the correct commit, path, and Git blob, but its SHA-256 field mistakenly uses the later copy's digest. The accepted finding identity is therefore unchanged; only that digest metadata needs correction.

## Later shortened copy — not the accepted snapshot

- Git blob: `61b6867399b7780479aabde0d197d2c4f633c9fe`
- Size: 6,479 bytes
- SHA-256: `5e22fc1bf04bee71559848d15ce5eaacfc86294b5abb0549b6ea63a20140c3b5`
- Difference: it omits the `EnchantmentHelper#getSneakingSpeedBonus` evidence bullet present in the accepted snapshot.

The shortened copy must not be presented as the immutable accepted snapshot or used to bind the finding. The source-owner run report and blind-review report are preserved unchanged; this backlog erratum is the correction record.

## Independent implementation disposition

The no-code reconciliation is ACCEPTED by independent review commit `0cea9296f492f5d619bdec93b6cc118c16828d84`. Report: `workflows/implementation-reviews/2026-10-08-passenger-crouch-reconciliation-review.md`; report blob `6d88cab3c400664f5ca14627e71678c9148dda8f`; SHA-256 `e3f9312054049d9937ba70093703b0ef333fdba1c00699f6dd8ade357d1a5341` (8,712 bytes). The reviewed no-code proof is commit `dee9c6fa1f4aee135c4a38d6cbd3ba314cbcf392`. Its current-main integration remains pending. The source pair remains partial and runtime behavior unverified.
