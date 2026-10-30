# Correction supplement 001

This supplement preserves and corrects the immutable closure at `closure.md`; it does not modify or replace it.

## Immutable input and mechanical hash audit

Input `closure.md` SHA-256: `DF144E443FEAA6D13D67B5915E396422FCD4E73777CD35F8E503FC55975FFB01`.

I recomputed every release ready-marker and source-manifest hash in the report against the canonical files, and every reported per-Java hash against both the canonical Java file and its matching `*.sources.sha256` entry. All ready markers and manifests match their report values. All listed Java and additional lookup-file hashes match their files and manifests except the combined 1.13.1/1.13.2 `WorldView.java` row, which is a transcription error in the original report.

Correction: `net/minecraft/world/WorldView.java` for both 1.13.1 and 1.13.2 is `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`. The original report contains `9577360b1a1bdde9280f0b72548f858d5ff6c14ad781927832cb374e985f32a543`; that value is not the source-file hash. The corrected value matches both releases' files and canonical manifest entries. Their files remain byte-identical.

## Predicate scope clarification

In 1.13.1 `WorldView.getBlockCollisions` (`WorldView.java:123-150`), `bl3`, `bl4`, and `bl5` mark whether the current X, Y, and Z cells are on the respective scan-region faces (`:127-129`). The admission predicate at `:130` is false whenever any pair of these flags is true. It therefore discards every two-axis edge cell and every three-axis corner cell before `isChunkLoaded`, block-state lookup, collision-shape lookup, or shape intersection. It does not inspect the cell's block state, whether it is `MOVING_PISTON`, the piston block entity, moved state, progress, or candidate geometry.

Accordingly, for every 1.13.1 query envelope, all cells lying on exactly two of that envelope's faces are unconditionally pruned, including cells that would contain a moving-piston block entity carrying a fence at any progress. The query geometry still determines the envelope and its face positions; the pruning predicate is independent of what geometry or progress is present in those cells. This is a scan-predicate conclusion, not a claim that every such cell would geometrically overlap the query, nor a conclusion about parity for arbitrary moved blocks.

26.2 uses explicit `Cursor3D.TYPE_EDGE` (2) for a cell on exactly two faces. `BlockCollisions.computeNext` (`BlockCollisions.java:84-101`) excludes corners, but for type 2 permits evaluation only when the cell's state is `MOVING_PISTON`; it then dispatches to the collision context. Thus the target's candidate-admission rule differs from 1.13.1 at those edges, while chunk/state/BE availability remains the applicability condition already stated in `closure.md`.

The stable cutover remains 1.13.1: 1.13 uses the older predicate at `WorldView.java:127-135`, while 1.13.1 and 1.13.2 use the two-axis predicate. This establishes the exact source boundary for this collector operation only.

## Source identities supporting the clarification

- 1.13.1 Ornithe Feather `net/minecraft/world/WorldView.java`: SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; canonical manifest entry matched.
- 1.13.2 Ornithe Feather `net/minecraft/world/WorldView.java`: SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; canonical manifest entry matched.
- 26.2 unobfuscated `net/minecraft/world/level/BlockCollisions.java`: SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`; canonical manifest entry matched.
- 26.2 unobfuscated `net/minecraft/core/Cursor3D.java`: SHA-256 `9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f`; canonical manifest entry matched.
