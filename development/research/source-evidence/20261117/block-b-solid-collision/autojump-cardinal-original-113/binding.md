# Original 1.13 cardinal AutoJump border masks

The exact original 1.13 WorldView.getBlockCollisions omits X/Z corner columns and the highest Y cell of any remaining X or Z boundary column. In the audited ordinary outward probe through one stationary integer cardinal face, with the center strictly within the one-block-eroded X and Z bounds, its shared mask contains 29 cells on each face. The two full Stone supports are in the same mask as 27 synthetic border cells. The supports touch the lower probe face. Both prior outside flags select the same branch. The [exact 1.13 east evidence](../autojump-border-old-edge-113/binding.md) documents the original east scope. The [1.13.1 and 1.13.2 cardinal evidence](../autojump-cardinal-shared-mask-1131-1132/binding.md) uses the distinct two-edge skip.

| Face | Ordered boxes | First hit | First hit box X / Y / Z | Floored center |
|---|---:|---:|---|---|
| East | 7 | 4 | [10,12) / [64,66) / [0,1) | (11,65,0) |
| West | 5 | 1 | [-12,-10) / [63,66) / [-1,2) | (-11,64,0) |
| North | 4 | 1 | [-2,3) / [63,66) / [-11,-10) | (0,64,-11) |
| South | 4 | 1 | [-2,3) / [63,66) / [10,11) | (0,64,10) |

Each first hit has y=66. With feet at y=64, Jump Boost II admits rise 2 when the ordinary AutoJump entry gates pass and the physical AIR samples are clear. The head column is y=65,66,67 at (x,z) east (8,0), west (-9,0), north (0,-9), south (0,8). The east first-hit column needs y=66,67; the other three columns need y=65,66. Both preserved raw receipts misplace the extra head read at y=66. In the manifest-verified LocalClientPlayerEntity.autoJump, blockPos already advanced from y=65 to y=66 before the loop. At t=2, the assignment under if (t > 1) advances it to y=67. This yields five unique AIR samples per face in the specified fixture.

This evidence applies to the exact 1.13 version and the loaded two-Stone/ordinary AIR fixtures. Corners, entities, other providers and runtime parity remain open.

- [Author](report.md), raw SHA-256 eb1ba1cd85131bf783970e7acfd490a39ba023cfc1d8b437374502c818aa57f1.
- [Independent verdict](verdict.md), raw SHA-256 7c047a6c5ef3a1fbbed06f7cdc772ffde73e3fcc89da62f271c84cb32fe3e20b.
