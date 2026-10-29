# Independent review: first-hit source crosswalks 064–131 and 132–197

Verdict: **REVISE**

Reviewed artifacts (SHA-256):

| Range | Artifact | SHA-256 |
|---|---|---|
| 064–131 | `range-064-131/crosswalk.tsv` | `3D943C1D021B241088621B02C91C84E19E915E8AEBB3AFC87C7D97EA3FF36BC9` |
| 064–131 | `range-064-131/artifact-hashes.json` | `E8DFE02E50833C6F7C6CE158D7CEDC97B45D2C28ADA513447F19158DDEC61218` |
| 064–131 | `range-064-131/README.md` | `22B17586CB8FFEAE7C120068E7B14F319D77B6D6B07DDB1A1D61E4DD00AFB2DF` |
| 064–131 | `range-064-131/static-check.txt` | `7572F9D8636E5FF18659615B615D1A85BA4A250C8A543A5A24E4769A4BABEB40` |
| 132–197 | `range-132-197/crosswalk.tsv` | `BC3F9D631324D904F779D2A505833FE71008A66396E9467016131C7710407BF5` |
| 132–197 | `range-132-197/source-and-evidence-hashes.json` | `FC7C4617C5167F4DEC0C25FC0D7D9EB4B862A26829A50630A9C4773D2D109BDA` |
| 132–197 | `range-132-197/README.md` | `E51C9A38ACA1369CB5FBFE82D806A31F19D4A2A3F91FC38A30DA9FA5CF5B93C4` |
| 132–197 | `range-132-197/validation.txt` | `536EB26CDD3238C32477D684572086E78E173E93DD27E80D51B164999DEBE28C` |

## Checks performed

- Recomputed row cardinality, unique IDs, and contiguous coverage: 68 rows for IDs 64–131 and 66 rows for IDs 132–197, with no gaps or duplicate IDs.
- Re-ran the 064–131 validator. It passed all 50 listed artifact/source hash checks and verified every metadata value 0–15 for IDs 64–127 against the two cited decode projections.
- Independently recomputed all 147 accepted writer-matrix artifact hashes referenced by the 132–197 crosswalk and all four canonical source hashes listed in its evidence manifest; every digest matched current bytes.
- Checked the reported candidate keys and current-state/provider columns. The crosswalks consistently label registry candidates as non-equivalence evidence and leave current state/property conversion, current provider equivalence, neighbor/paired-cell/entity/chunk inputs, cache history, and whole traversal gates open where the referenced family finding does not close them. I found no unsupported same-name state mapping or claim of adapter readiness.
- Confirmed the 132–197 README and validation text correctly bound its complete registration-row inventory to the accepted matrix while preserving family-scoped limits.

## Required correction

In `range-064-131/crosswalk.tsv`, all IDs 64–127 currently say “16 metadata rows in accepted decode projection; full 0..15 nibble domain coverage not asserted here.” That qualification contradicts the README and validator, which do assert and verify exact 0–15 coverage for every such ID. Replace it with an accurate statement that the full domain is verified by the crosswalk's static check. IDs 128–131 correctly remain marked OPEN. This is a reporting defect; the underlying data and digest checks passed.

No product sources were inspected or changed. No tests, builds, or runtime were used.
