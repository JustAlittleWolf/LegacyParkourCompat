# Bounded early AutoJump east-border timer witness

The [final independent audit](east-fixture-final-audit.md) controls this exact source witness. Use an ordinary grounded player with feet at (9.7,64,0), stationary border centered at (0,0) with east boundary x=10, loaded chunks (0,-1) and (0,0), physical support at (9,63,0), and the audited eastbound input fallback and clear probe. The historical stored `outsideWorldBorder` flag is false at the query. The expanded position test admits synthetic border cells. The stored flag is a required premise.

- Exact 1.12, 1.12.1 and 1.12.2 append individual synthetic stone boxes during the X/Z/Y scan. The first qualifying cell is (10,64,-1). Its maxY is 65 and the sampled physical block at (10,65,-1) is AIR. Rise 1 sets timer 1.
- Exact 1.13.2 aggregates the finite synthetic cells after ordinary block candidates. The first qualifying greedy box is [10,11] × [63,67] × [-1,1]. It samples actual AIR at (10,66,0), leaving maxY 67 and rise 3. Timer remains 0. The source admits single-edge y=63 and y=66 cells in the interior x=10, z=-1/0 column. This corrects the interim maxY=66 account and earlier imprecise first-box extents. The final audit also corrects the earlier old-side z=0 sample.
- The separate exact 1.14 endpoint admits a synthetic border complement ahead of cursor blocks through its current-entity thin contact shell. In the bounded empty far-out sample case, its infinite-height east AABB yields no timer. This establishes the checked endpoint's provider form and conditional result. Intermediate patch and broader alias applicability are open.

The source result covers this east fixture. The west side and competing obstructions are outside its scope. The current early AutoJump scanner rejects the border envelope and lacks the historical prior flag. Implementation, a history-independent witness and runtime parity remain unresolved. The earlier overwritten author draft is unavailable. Its prior digest is historical metadata only. The corrected author bytes and the separate addenda are archived here, with the final audit governing conflicting intermediate geometry.

## Immutable archives

- [author-analysis.md](author-analysis.md), raw SHA-256 `d62ffcb2be2b95f3ddb18f542a0eb7b1ee09d986a70d4818e00b3a804f872d99`.
- [author-clearance-addendum.md](author-clearance-addendum.md), raw SHA-256 `14e5b9566c6bd0a868441830053ebe67f83ee99ef451d1956a692249b49033f6`.
- [cut-chain-verdict.md](cut-chain-verdict.md), raw SHA-256 `91bbb4ddda9462a535a10102f5179f51d9d20466954752145af432ed38e1a2cb`.
- [binding-addendum.md](binding-addendum.md), raw SHA-256 `b5b7bd46039c89e70842e439b0ace0eda2f22f85451266edefbd61715c7363bd`.
- [east-fixture-final-audit.md](east-fixture-final-audit.md), raw SHA-256 `dc5d6d7de8e3dcb265655fe66ac9f297f7422b90c003f0dd6db58e72c442f1bf`.
- [old-alias-verdict.md](old-alias-verdict.md), raw SHA-256 `01e68d4683765eea545b05da61d251bafae01a77c3632ae3644dbc3b12f920f8`.
- [114-endpoint-author-report.md](114-endpoint-author-report.md), raw SHA-256 `59521c01515b13b3d50af364dd1fba2d1f4d6d77e0ffbb2076ce1a52dbc43691`.
