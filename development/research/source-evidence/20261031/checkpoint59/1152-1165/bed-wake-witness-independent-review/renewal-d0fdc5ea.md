# Renewal — bed-wake paired position witness

Reviewed report revision SHA-256: `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e`

## Verdict

**ACCEPT** this corrected, bounded witness revision for the paired 1.15.2 Mojmap → 1.16.5 Mojmap bed-exit position divergence. The west/east candidate coordinates now match the source arithmetic. Acceptance is limited to the concrete paired source witness; it does not establish an intermediate patch boundary, pair freeze, runtime parity, or post-write trajectory.

## Renewal checks

- The current `bed-wake-pair-witness/report.md` hashes to the revision above.
- Rehashed every source row in its `source-hashes.tsv` and every evidence row in its `input-hashes.tsv`; all match. Rehashed both release source manifests and confirmed the SHA-256 values match the corresponding ready-record manifest identities. Both ready records identify Mojmap.
- 1.15.2 first valid candidate remains `(-1,64,-1)` with center `(-0.5,64.0000002,-0.5)`: current air shape passes the `0.4375` cutoff, the downward search reaches full stone one block below, adds the historical `2.0E-7`, satisfies the vertical bound, and passes `noCollision`.
- 1.16.5 first non-bunk side candidate is east or west according to yaw. `Vec3.upFromBottomCenterOf` yields `(1.5,64,0.5)` east and `(-0.5,64,0.5)` west. Empty current shape plus full stone below gives floor offset zero; the finite/`<1` and dangerous-block gates pass. In both branches the candidate AABB clears the bed shapes and only touches the support floor at its lower boundary, so block collision acceptance succeeds.
- The cited paired route report and prior route review remain hash-pinned inputs and support the conditional wake-packet-to-local-`stopSleeping`/`setPos` path. The route exists at both endpoints; the change is the bed-exit position response, not bed-entry eligibility.

The corrected claim is source-supported: the west branch differs from the old center in z, and the east branch differs in x and z; both also differ in accepted y by the old epsilon. No product/wiki/backlog/build/test/runtime/decompile action was performed.
