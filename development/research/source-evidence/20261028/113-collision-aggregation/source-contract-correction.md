# Focused source correction: 1.13.1/.2 superclass collision stream

This supplement preserves the reviewed `report.md` unchanged and narrows one statement in its 1.13.1/.2 section.

## Corrected wording

Read the 1.13.1/.2 statement “the concrete `World.getCollisions` appends entity collision shapes only after the superclass block stream” as: **the concrete `World.getCollisions` appends the entity collision stream after the `WorldAccess.super.getCollisions(...)` stream.** The cited concrete override proves that call order (`World.java:1755-1758`). The stream's block-only contents are proven by the inherited default method in `WorldView`, with `WorldAccess` confirming the interface relationship.

In each release, `WorldAccess` extends `WorldView` (`WorldAccess.java:18`) and declares no `getCollisions` override (entire file, lines 1-66). `WorldView.getCollisions(entity, shape, excludeBlock, excludeEntities)` returns `this.getBlockCollisions(shape, excludeBlock, bl2)` (`WorldView.java:170-178`). That `getBlockCollisions` method is the block-shape stream, including its partial-shape stream and full-cell tail (`WorldView.java:112-152`). Therefore the concrete `World` concatenation is the `WorldAccess`-inherited `WorldView` block stream first and entity collision shapes second. The source files `WorldAccess.java` are byte-identical in 1.13.1 and 1.13.2; the cited `WorldView.java` files are also byte-identical.

This establishes ordering for that concrete stream override. It does not make the API-level statement a claim about every `WorldView` implementation.

## Hash binding

| Release | Source path under canonical root | Relevant lines | SHA-256 |
|---|---|---:|---|
| 1.13.1 | `ornithe-feather/net/minecraft/world/WorldAccess.java` | 18; full file checked for absence of an override | `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be` |
| 1.13.2 | `ornithe-feather/net/minecraft/world/WorldAccess.java` | 18; full file checked for absence of an override | `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be` |
| 1.13.1/.2 | `ornithe-feather/net/minecraft/world/WorldView.java` | 112-178 | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.1 | `ornithe-feather/net/minecraft/world/World.java` | 1755-1758 | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` |
| 1.13.2 | `ornithe-feather/net/minecraft/world/World.java` | corresponding concrete override | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |

The ready roots remain the canonical `1.13.1/ornithe-feather` and `1.13.2/ornithe-feather` exports. Their ready-marker and source-manifest hashes are recorded in the immutable main report; this supplement adds the missing `WorldAccess.java` file hashes needed to bind the inheritance step.
