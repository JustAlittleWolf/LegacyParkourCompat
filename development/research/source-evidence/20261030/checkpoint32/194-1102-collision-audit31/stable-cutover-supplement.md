# Stable cutover evidence supplement

This supplement records the read-only intermediate-source probe for the farmland collision candidate.

| Release tree | FarmlandBlock.java SHA-256 | Observed collision override |
| --- | --- | --- |
| 1.10 | `9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25` | Present; returns `FULL_BLOCK_SHAPE` |
| 1.10.1 | `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f` | Absent; inherited `Block.getCollisionShape` applies |

The same 1.10.1 file hash is present in the audited 1.10.2 endpoint. These observations bracket the textual change between the available 1.10 and 1.10.1 trees, but are not accepted as an exact stable release cutover because the intermediate source trees have no exact-run provenance, mapping artifact pin, ready marker or source manifest in their version roots. Obtain canonical exact-source pins, then independently verify before changing the interval claim.
