# Independent stairs face-shape state-matrix audit — ACCEPT

Pair: exact `1.11.2→1.12`, Ornithe Feather. Audited matrix SHA-256 `96df1629a7138eaac34db2ebe8dd464d1e6734c96963459843d21851017c86ed`; corrected connected-state crosswalk r2 SHA-256 `eda620f3a69bf7115b45dec4328cc48e2e23a720595d66e34b0ae83eaa3694de`; its accepted audit verdict SHA-256 `87da39170d47770ff91890486a11b8713493f9065297a2a57e691a0176c69b65`.

## Source identity

Recomputed source identity hashes: 1.11.2 readiness marker `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`, source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, and provenance `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; 1.12 mapping-local provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. The 1.11.2 source manifest and 1.12 provenance entries match the recomputed endpoint hashes for `StairsBlock.java`, `FenceBlock.java`, `WallBlock.java`, and `Block.java`; the StairsBlock hashes are `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` / `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`.

The bound fence and wall candidate reports and accepted verdicts also match their crosswalk identities: fence `3c5bb22d0700858739e0815ffc295c119aab0787d846705c02fd9db4835c6066` / `b2c3d12448447f22433ab648d92cb63e2bcb5e75037a38ef9bbd836268bc2075`; wall `d9012e7c589f6a72a0716f17e19ab43a060772c8c1e5bd93df371e5656ac1d8d` / `2fc8c608e6b5ecf75dabb530d4448efe94466c6476754850048990cd15d7129f`.

## Verdict

Both StairsBlock sources register only `FACING`, `HALF`, and `SHAPE`. Their domains are four horizontal directions, two halves, and five shapes, yielding all 40 Cartesian state tuples; no `WATERLOGGED` property exists. The property declarations and enum members are unchanged. The old source has no StairsBlock `getFaceShape` override (nor the 1.12 face-shape query API); its explicit `isCube` result is false for every tuple.

The 1.12 table is correct for all six query directions. The vertical branch returns `SOLID` exactly for `UP` on top stairs and `DOWN` on bottom stairs. After resolving virtual stair shape, horizontal `STRAIGHT` is solid only at `F`; `INNER_LEFT` is solid at `F` and `CCW(F)`; `INNER_RIGHT` is solid at `F` and `CW(F)`; both outer shapes are always `UNDEFINED`. All other horizontal queries are `UNDEFINED`, and the method returns no pole or center shape. The resolver derives shape from same-half, perpendicular adjacent stairs with its same-facing side guard, so the matrix's ordinary inner/outer cases and its use of resolved rather than stored `SHAPE` are supported.

The connector direction mapping in the matrix agrees with both 1.12 resolvers: a connector north/east/south/west of a stair queries the stair's south/west/north/east face, respectively. Each connector accepts a non-excluded `SOLID` neighbor face (with its separately audited pole cases); thus the matrix's ordinary north-facing, bottom, straight stair connects to a fence or wall immediately south, which queries the stair's `NORTH=SOLID` face. In 1.11.2, fence and wall predicates instead require the stair's non-cube state to pass the generic solid-blocking cube arm; the stairs-specific false `isCube` result prevents that arm, and stairs are not their special fence/wall/gate cases. The witness is therefore disconnected there. This supports the claimed ordinary legal state combination that connects in 1.12 but not 1.11.2.

ACCEPT for the bounded StairsBlock state-domain and six-direction matrix dependency. Fence/wall box claims are carried forward under their accepted verdicts, not re-audited here. This verdict does not establish trajectories, pair freeze, or runtime parity.
