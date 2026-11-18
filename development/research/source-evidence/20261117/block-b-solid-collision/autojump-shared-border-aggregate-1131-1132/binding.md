# Shared finite border aggregate in exact 1.13.1 and 1.13.2

For the audited east fixture, a dry ordinary player at z=.5 and feet y=64 moves from x=8.89 to x=8.99 with accepted dx≈.10. Speed II extends the ray across stationary integer border maxX=10. Both positions are strictly inside the one-block-eroded bounds on X and Z, so either prior outside flag produces the same historical collision predicate. The two full Stone floor cells (8,63,0) and (9,63,0) support the footprint. The scanned chunks (0,-1) and (0,0) and all physically AIR clearance cells are loaded. The source comparison assumes no competing block or entity.

Exact 1.13.1 and 1.13.2 collect ordinary canonical full cubes and synthetic full border cells into the same padded [7,12) × [63,67) × [-2,3) voxel mask. The floor cells touch the bottom of the probe but are present before the aggregate overlap test. The greedy decomposition first consumes the joined floor run and then the outside Z runs. The first box that actually intersects either raised ray is [10,12] × [64,66] × [0,1]. Its center floors to (11,65,0). In particular, the y=63,x=11 cell fails the simultaneous X/Y-edge test while y=64,x=11,z=0 passes.

At feet Y=64, the retained top gives rise 2. Command-supplied Jump Boost II (amplifier 1) gives cap 2.7. With physical AIR at (8,65,0), (8,66,0), (8,67,0), (11,66,0) and (11,67,0), both verified old-version callers arm the one-tick timer. With no effect or Jump Boost I, rise 2 exceeds the cap. The exact sample path depends on the world setup. This is a source result for the accepted displacement.

This supersedes the first-box, sample X, and minimum amplifier of the earlier drafts for this eroded fixture. Separate ordinary nonfull shapes precede the finite aggregate, and entity collisions follow it. No adjacent claim is made about the separate tangent fixture, 1.13's different overload, the 1.12 per-cell collector, 1.14 and later infinite borders, or runtime parity.

The Stone floor proof is archived at Git identity `cfb3f14d1331036e6a1df57a38df33d5dbdf8f5e:development/research/source-evidence/20261117/block-b-solid-collision/autojump-border-eroded-gate-1122-1132/floor-support-addendum.md`, raw SHA-256 `13aa0070b905854af3236f6d339f3916310cf913118ca3e640e1788f389526fc`. The [history-independent movement receipt](../autojump-border-eroded-gate-1122-1132/binding.md) binds the earlier supporting move.

## Immutable receipts

- [author-geometry-superseded.md](author-geometry-superseded.md), raw SHA-256 `6ff36be2e8cf6e2c185bbef181802d0a0ba7ec7a6a24c4c59c8c8c326210b8be`.
- [initial-verdict-geometry-superseded.md](initial-verdict-geometry-superseded.md), raw SHA-256 `b2aaceb94233585b671a45fc93f99a7fa8852dc6621611de6df86ecaf41a7a43`.
- [combined-mask-repro.md](combined-mask-repro.md), raw SHA-256 `2eb8a055a56248b39e6cd2f80a3a6d0e0c0eff95dad51390db04af1b8ffca398`.
- [combined-mask-independent-r2.md](combined-mask-independent-r2.md), raw SHA-256 `daf54bbd174f94b5e62e6c0be06a21586472a688b72ed051b9fca33c6a6f3952`.
