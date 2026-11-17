# Product correspondence: 1.11.2 to 1.12 fence stair state matrix

**Disposition:** the staged fence candidate covers the newly demonstrated straight-stair witness, but does not implement the complete accepted stair-face matrix. The inner-left and inner-right solid-face admissions remain outside candidate behavior and are delegated to native fallback without a demonstrated native-equivalence result. Treat those matrix rows as an uncovered/unverified candidate gap; do not claim complete 40-state stair coverage.

## Bound inputs

- Matrix report: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/stairs-face-shape-state-matrix.md`, SHA-256 `96df1629a7138eaac34db2ebe8dd464d1e6734c96963459843d21851017c86ed`.
- Independent ACCEPT: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/stairs-face-shape-state-matrix-audit/verdict.md`, SHA-256 `50ffb75a3f39e63bea1398b6e03750796c78d87dbed87daa12adf109ed0fd35b`.
- Existing five-operation product readiness: `build/product-readiness-20261117/mcpk-1112-112-provider-readiness.md`, SHA-256 `A1FEB8CA9ECB01AA9FA575EF03D95742F205EE35CC7B80E858BA716401FC3112`.
- Frozen pending integration patch: `build/integration-staging/early-1112-consolidation-v2.patch`, SHA-256 `49888265ad1b785023196d9bb2f997a1dfeca7b8a0445b0640f389f88af97225`.
- Frozen staging manifest: `build/integration-staging/early-1112-consolidation-v2.json`, SHA-256 `1f960dd8a1abc6c5c6e4e98b9c50594c482efdcc243034fe7197a4c794039209`.
- Candidate provider in that patch: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/FenceCollisionState.java`, SHA-256 `4c25015b9d34ae3e3eaf05998e0e8fb182f637f8c009eeaf925204f923e74b97`.

## Effective provider correspondence

The existing alias table maps 1.11.2 to `V1_11_2` (ordinal 10) and 1.12/1.12.1/1.12.2 to `V1_12` (ordinal 11). `FenceCollisionStateHook` has the old `FenceCollisionState @V1_11_2` provider and the candidate's `FenceCollisionState @V1_12` provider. Closest-applicable resolution selects the former for V1_11_2 and the latter for V1_12. The candidate preserves older gate behavior and changes V1_12 gates/stairs only when its accepted-state logic applies.

The matrix says a stair face is SOLID for resolved STRAIGHT at `F`; for INNER_LEFT at `F` and `CCW(F)`; and for INNER_RIGHT at `F` and `CW(F)`. OUTER_LEFT and OUTER_RIGHT have no solid horizontal faces. The connector queries the opposite face on the stair. In 1.11.2 all stair tuples are non-cube and disconnect; in 1.12 each listed SOLID face sets the adjacent fence connection.

The staged V1_12 provider covers direction-gated fence gates and a stair only when its virtual shape resolves STRAIGHT, setting connection according to `stairFacing == queriedFace`. The ordinary north-facing bottom STRAIGHT stair immediately south of the fence is therefore covered. The provider explicitly returns native fallback for any resolved inner or outer stair. That leaves INNER_LEFT and INNER_RIGHT face admissions unimplemented by this candidate. The accepted matrix establishes these as 1.12 connections that were absent in 1.11.2; the current patch contains no evidence that native fallback for those cases matches the 1.12 target. OUTER-state and unrelated block fallback parity is also not established by this bounded correspondence.

## Result and limits

The candidate is adequate for the already bounded STRAIGHT-stair witness and direction-sensitive gate cases. It is not sufficient to claim the complete newly accepted 40-state/six-face dependency. A separate patch revision would be needed to explicitly model the resolved INNER_LEFT/INNER_RIGHT solid-face table, unless a bounded exact native-source comparison establishes those cases already match. This is a readiness correspondence only: no source re-audit, candidate code review, product edit, Git/history action, build, test, runtime check, pair-completion, or movement-parity claim was performed.
