# Independent documentation rereview — MC1204-1206 scope proof revision 2

**Decision: ACCEPT revision 2** (`2d88ee12481b66b739d613f746bc421cc32e0db5`). The requested stale boundary-status correction is accurate, and the revised proof binds the accepted boundary and implementation reviews to their exact Git objects. No additional scope defect found in this bounded rereview.

## Exact artifacts and review bindings

- Reviewed file: `workflows/fix-implementation/reconciliations/MC1204-1206-feature-scope-2026-10-08-rev2.md`, commit `2d88ee12481b66b739d613f746bc421cc32e0db5`, blob `21e192b3f593402a0c9237fbabbeb6a250383ef3`, raw SHA-256 `da6236f402ebbdd6dac5cfdc84c7d54384439050c15950639f4c33d342c2412c`, 6,266 bytes. Recomputed from the exact Git blob; it matches the supplied digest. The commit adds this one new document.
- Predecessor proof `9acf4bb097ce025b5dbc9c042ef012d5cad54f63` resolves to blob `f20ee7926dae5c36bbfe82238d7ffcdbcd20b596`, raw SHA-256 `b3ead218913011fe6889b6f444fa9ee75d397041a68b20d148dae0e9abb699d4` (14,221 bytes).
- Prior request-changes review `35cbd3a9c49bb163bb718deb307b60307f78efdf` resolves to blob `33ecf0fc625700aaa8e9159a188a6c5e29dc0b6c`, raw SHA-256 `4866dafc2076202d7ed8ebaaca99ad51f0b7b6bb553d9f2434b1d1039ab11841` (8,724 bytes). I confirmed that it requested only correction of the stale boundary-review status and accepted the other scope claims per claim.
- Boundary memo `e277edd19eb8f8ce63681ab7985a5d92df8a259f` resolves to blob `cda4a40cadde768c36b5523cc5c11a3a56adc78f`, raw SHA-256 `a97e20b5abaa131f9ec090d1d186eae63592d839e8cd9c3c254b80acdea8de82` (8,841 bytes). Its independent review `1e5de3b32966e8766cdcf9f7ad56a9278fb79bb0` resolves to blob `33a66275a424f45b614a6bd2b310b7b4f3866c59`, raw SHA-256 `0cc8eed10b9c11f8b2d812aeeece0acbb6dbaf1e91b5bd9f1b73dec877df7a78` (6,943 bytes); the report explicitly ACCEPTS the bounded source-boundary claims.
- Sprint-precision implementation review `8c9e3d5c90279575ee079f07d7a1ee360adf3c58` resolves to blob `82cb2bc57df13f758b33dfcc68e9a7a4c10bf6cf`, raw SHA-256 `e6d30c988ac0571976266a4b21cb4e1d08111edbc298ae4caa3c29b95b9897a8` (7,079 bytes). Its ACCEPT decision is bound to implementation commit `4aa5be97b5495b59736381c9e05e4ca9f6e32a1b`.

## Per-claim disposition

| Claim in revision 2 | Review result |
|---|---|
| The 1.20.5 source boundary memo is accepted | **ACCEPT.** The exact memo and independent report bindings match; the report accepts the bounded 1.20.4/1.20.5/1.20.6 comparison. |
| Source transition versus selectable profile | **ACCEPT.** The document correctly states source transition at 1.20.5 and `V1_20_2` as the selectable profile grouping for 1.20.2–1.20.4. |
| Low-power cutoff remains ineligible for old vanilla 1.20.4 player inputs | **ACCEPT, carried forward.** This remains separate from the accepted source boundary and no cutoff emulation is claimed. The prior review accepted the bounded input analysis. |
| Sprint precision is implemented and separately accepted | **ACCEPT.** The exact implementation review binding matches; the review accepts only the reachable sprint-vector float multiplication and registration. Revision 2 correctly says it is queued for integration and absent from `main` at `3a60fe73`. |
| MC03/MC05/MC06 require no historical change for default inputs | **ACCEPT, carried forward.** These dispositions remain as accepted in the prior review; revision 2 does not broaden them. |
| MC04 fall-flight `noGravity` producer/reachability | **ACCEPT as open.** Revision 2 preserves the separate unresolved producer question and does not attribute it to the modern generic gravity attribute. |
| Pair/runtime status | **ACCEPT.** The proof retains `PARTIAL` source coverage and makes no runtime-parity claim. |

## Six review angles

1. **Requirement and scope:** revision 2 fixes the sole stale-status issue identified by the prior review and separately records the accepted implementation status.
2. **Correctness:** boundary acceptance is accurately distinguished from input eligibility; the source release and selectable profile are not conflated.
3. **Completeness:** all carried-forward dispositions and residuals are stated, including the open MC04 reachability issue and partial pair status.
4. **Conventions:** the correction is a new immutable revision; revision 1 and its review remain unchanged, with exact commit, blob and digest bindings.
5. **Permissions and visibility:** documentation only; no permission, configuration, default, or user-visible behavior changes.
6. **Security:** no executable code or input surface changed.

## Limits and integration state

I reused the previously verified source evidence and did not repeat the whole-source audit or rehash source trees. This rereview verified Git-object bytes and bindings for revision 2, its predecessor, the prior review, and the accepted boundary and implementation review reports. Current local `main` is `3a60fe735560e478bf0aa0d05f5e306c74800f6a`; revision 2's parent merge `ff8f8b70999952245e68265ec9f83d41db5a56d5` contains that `main`. The sprint implementation commit is not an ancestor of current `main`, consistent with the document's queued-integration status.

No tests, build, decompilation, client, server, TAS, Gym, Docker, or push was run. No source or production code was changed.

**Next step:** the corrected scope proof is ready for the coordinating integration owner; the explicitly open MC04 producer question and runtime validation remain separate residuals.

Reviewer: bounded independent documentation rereview, 2026-10-08.
