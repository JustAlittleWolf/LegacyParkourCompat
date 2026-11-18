# Supplemental exact Box evidence — corrected rebind r1

This supplement adds exact source-file identity for the bound contraction issue identified after the blind roster. It is tied to corrected report SHA-256 `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846` and prior verdict SHA-256 `ab93268fad063c7365f86db3ead3a87fd5f0a0b1d2725a5367422787043482d8`. The original roster and verdict are preserved unchanged.

All four exact Feather source files below matched their own release `ornithe-feather.sources.sha256` entry during this rebind. The preexisting ready markers identify the same exact releases and `ornithe-feather` mapping family recorded in the frozen source roster.

| Release | Box.java SHA-256 | World.java SHA-256 | relevant contract source |
|---|---|---|---|
| 1.8.9 | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `Box.contract(dx,dy,dz)`, lines 176–184: each maximum is set to its former maximum minus the corresponding delta. |
| 1.9.4 | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `Box.contract(amount)`, 222–224 → `expand(-amount)`, 110–112 → `grown(dx,dy,dz)`, 100–108; each maximum is reduced by `amount`. |
| 1.10.2 | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `Box.contract(amount)`, 237–239 → `expand(-amount)`, 114–116 → `grown(dx,dy,dz)`, 104–112; each maximum is reduced by `amount`. |
| 1.11.2 | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `Box.contract(amount)`, 279–281 → `expand(-amount)`, 142–144 → `grown(dx,dy,dz)`, 132–140; each maximum is reduced by `amount`. |

In each `World.applyLiquidDrag`, the chosen upper index is used both as the exclusive end of the current-cell loops and as the corresponding upper coordinate passed to `isAreaLoaded(..., true)`:

| Release | Method lines | Exclusive upper index |
|---|---:|---|
| 1.8.9 | `World.java:1486–1526` | `floor(bounds.max + 1.0)` |
| 1.9.4 | `World.java:1560–1602` | `ceil(bounds.max)` |
| 1.10.2 | `World.java:1570–1612` | `ceil(bounds.max)` |
| 1.11.2 | `World.java:1571–1613` | `ceil(bounds.max)` |

For any nonintegral contracted maximum `M`, the forms agree. At integer `M=1`, 1.8.9 computes `floor(2)=2`, whereas later releases compute `ceil(1)=1`. The loops use `< end`, so only the older loop visits coordinate 1; the loaded-area request also receives different upper bounds. Contraction by 0.001 reduces the maximum but does not exclude integral outcomes. A pre-contract maximum of `1.001` yields `M=1.0` under the exact double subtraction used here.

This difference concerns the caller’s outer current-cell range and loaded-area request. It does not alter `LiquidBlock.getFlow`, the dry oak fence material-face result, or the per-cell flow vector once a central liquid cell is admitted and passes its height gate.
