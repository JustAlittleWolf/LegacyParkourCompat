# Closure correction: 1.8.5–1.8.9 provider-family hash closure

This correction supplements `.native-source/20261030/ordinary-pool/worker-185-189-collision-inventory/report.md` and leaves it unchanged. It uses the complete adjacent Java matrix at `../worker-185-189-complete-java-inventory/adjacent-source-hashes.tsv` (1,612 rows per edge, 6,448 data rows) and the accompanying `inventory.md`. The inventory verifies each of the five exact Ornithe Feather source roots against its source manifest and reports no added or removed Java paths on any edge.

## Matrix results for assigned terminal families

Independent filtering of all 6,448 matrix rows by source path gives zero `changed` rows in each of these namespaces on every adjacent edge:

| Edge | `net/minecraft/block/**` | `net/minecraft/entity/**` | `net/minecraft/world/**` |
|---|---:|---:|---:|
| 1.8.5→1.8.6 | 0 changed | 0 changed | 0 changed |
| 1.8.6→1.8.7 | 0 changed | 0 changed | 0 changed |
| 1.8.7→1.8.8 | 0 changed | 0 changed | 0 changed |
| 1.8.8→1.8.9 | 0 changed | 0 changed | 0 changed |

Because there are also no added or removed source paths, this closes Java-source hash equality for the complete concrete block-provider namespace, not just the previously sampled providers. It includes every mapped block shape override/inherited implementation, every `Block.onEntityCollision` override, fluid and material implementations, contextual shape implementations, block state/property classes, and `Blocks` registration source under that namespace. It likewise closes entity/player and world collision/query source namespaces. Thus all shape/contact/fluid/material/pose/dimension/box terminals enumerated in the original supplement that reside in these namespaces are source-equal at each adjacent boundary. Under equal effective player inputs, movement runtime state, and received vanilla block/world state, their code-level responses compose identically.

The remaining registration routes outside those namespaces have already been classified in the cited group closure and registration supplement/audit: 1.8.5→1.8.6 removes one command-block dispenser behavior, which is server-only and may produce different later received block state; 1.8.7→1.8.8 changes empty skull-owner parsing only; no 1.8.8→1.8.9 Java paths are added/removed and its changed hunk audit identifies no movement-provider registration. The command-block producer is a state-evolution boundary only; it does not change movement response to the same received state. `Bootstrap` and `Blocks` therefore have no unclassified collision-provider registration change.

## Resolved `Box` source identity

The mapped collision box implementation is `net/minecraft/util/math/Box.java` (not `net/minecraft/util/shape/Box.java`). The matrix contains this path on all four edges, each row marked `same`, with SHA-256 `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` on both sides. By composition the file has that hash at 1.8.5, 1.8.6, 1.8.7, 1.8.8, and 1.8.9. This resolves the original path uncertainty.

## Closure verdict and retained limit

**Closed for this assignment:** all Java collision/shape-provider paths; all Java block-contact callback paths; fluid/current/material Java paths; entity/player/world pose, dimension, box, collision collection and collision-query paths; `Box` arithmetic; and Java provider registration routes reviewed above. The matrix's complete path/hash delta set proves these namespaces contain no changed implementation between any adjacent pair. Existing changed-hunk disposition covers the outside-namespace registration changes.

**Still partial only at the input boundary:** contextual providers choose among branches using neighboring vanilla block states, fluid levels, block-entity state and timing/runtime values. This correction establishes the provider code is identical, but does not prove that two worlds supplied different neighboring inputs or non-player state evolution will supply equal values. The already identified command-block dispenser removal is the concrete server-side state producer that can break equal future world state from equal initial worlds. Full-pair movement-terminal inventory/audit and runtime parity remain unclaimed. Packaged resources do not add a remaining movement-provider gap: the independent full-freeze audit reports 1.8.8→1.8.9 blockstates, models, and block textures are entrywise unchanged and composes the prior resource archive findings; its separate verdict still says full-pair freeze is not met.

No concrete changed-edge movement consequence exists within the closed terminal families when their effective input/state values are equal.
