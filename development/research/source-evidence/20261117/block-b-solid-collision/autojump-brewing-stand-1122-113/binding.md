# Brewing Stand first hit on a bottom-slab AutoJump approach

This is a second fixture for the [existing first collision-order finding](../../../findings/1.12.2--1.13.2/autojump-first-collision-order.md). It isolates the ordered boxes of a single Brewing Stand. The previous finding's two-block ordering witness remains independent.

For the reviewed ordinary sprint approach with feet at Y=0.5 and the stand at (0,1,0), the Y=1.01 side ray reaches the stem's footprint. Exact 1.12.2 appends the overlapping stem before the full plate, selects maxY=1.875 and rejects the 1.375 rise. In exact 1.13 and 1.13.2 the optimized union yields the low plate and a raised stem box. Only the plate intersects this ray, giving a .625 rise and setting timer 1. On the full-block control with feet at Y=1 and a Y=1.51 ray, both select the stem's maxY=1.875. Both schedule timer 1 at the .875 rise.

The exact Feather sources and their ready-manifest identities were checked. The old-side finding here is for exact 1.12.2. Earlier aliases, product projection, runtime behavior and full-pair coverage remain separate.

- [Author witness](witness.md), raw SHA-256 `502a93ed7fcdd065d20900d8d70103c37d87f245bea7ec463018e123d672fd53`.
- [Independent source verdict](verdict.md), raw SHA-256 `03a1ac52a30a116071036775fc18e5cd48a9b38d47ebda4fefef68e45e5da59a`.
