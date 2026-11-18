# AutoJump border applicability in exact 1.14.4 to 1.15.2

Both exact Mojmap endpoints admit the stationary border candidate in the same thin contact shell. On the first iterator advance, the border must overlap the current entity box inflated by 1e-7 and clear that box deflated by 1e-7. Swept-probe overreach alone is insufficient. When admitted, the unclipped synthetic border comes before cursor block candidates and retains infinite Y. Its horizontal boundary uses the same floor and ceil construction in both releases.

The first matching border AABB can supply positive-infinite maxY, then the AutoJump branch reads a block above the converted AABB center. Infinite and NaN coordinates affect that sampled position. An available nonempty shape can replace the first height, so the border hit gives no unconditional timer result. If the sampled collision is empty, the infinite rise fails the finite height gate. This source conclusion covers the border candidate only. Other AutoJump gates, broader pair coverage, runtime parity and the [later border timeline](../../../20261028/autojump-border-1152/comparison.md) remain separate.

- [Author comparison](comparison.md), raw SHA-256 `8161f41f5b750bc63ddb6d1ae2d4a6012a62d1e71fe3e79d43cc5bbc4112d1f7`.
- [Independent verdict](verdict.md), raw SHA-256 `c1353ff8232aaaaa74a0d418390f9ddef7d25dbf343db296db50ec1e9e124566`.
