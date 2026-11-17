# Bounded correction audit: collision-provider residual inventory r2

**Verdict: ACCEPT for the r2 scope-disposition and reference corrections only.** This verdict does not re-audit the underlying provider comparisons or freeze the 1.8.9→1.9 pair.

## Immutable bindings checked

- Correction: `build/source-189-19-20261117/providers/provider-residual-inventory-r2.md`, SHA-256 `8f0eb6b86fa14e7332233f28fc8de48112a1e718fb133ebc3cb4ba81bdcac1ca`.
- Corrected base inventory: `build/source-189-19-20261117/providers/provider-residual-inventory.md`, SHA-256 `55871477162491da0765481e14be7a4c629260e31e68cf0b692f6d8f17633e1e`.
- Anvil candidate and verdict: report `provider-residual/anvil-orientation-collision-bounds.md`, SHA-256 `fb09fd932a884753ed35b9c014e2e3f01fa2e286e37d5bb40316078969d4afc1`; REQUEST CHANGES verdict `provider-residual/anvil-audit/verdict.md`, SHA-256 `c4cdce25d2e608b92682cc1405ec9f5717702d920f76d9dc74d57b0d6285c78f`.
- Chest candidate and verdict: report `provider-residual/chest-neighbor-collision-bounds.md`, SHA-256 `c4591fd10901cb51cd136f389589c9b9366683d834796e4cc16779ace753bf6e`; REQUEST CHANGES verdict `provider-residual/chest-audit/verdict.md`, SHA-256 `6eda9702672394167595ab40bdab0aa8fb88e7d2e898cfc06c5ae5f940e731ce`.

## Correction checks

- The Anvil and Chest verdicts both preserve conditional source geometry observations but do not establish a deterministic eligible local-player lifecycle for the old shared mutable bounds. Their identified ray-trace, rendering, ambient, and non-player cache writers do not close that gate. The r2 disposition as excluded/failed applicability candidates is therefore supported and does not reopen the lifecycle question.
- R2 preserves the previously frozen pane-connection and piston-head-arm exclusions and keeps moving-piston response separately assigned. It correctly preserves the Fence/EndPortalFrame witness and LilyPad correction within their bounded accepted scopes, without expanding them to pair coverage.
- Both reference-only hash corrections match the exact bytes: `state-provider-callers-audit-r2/verdict.md` is `c0f05fc03f7c66f2a4cfa73bd94429a4d809503846ecbedeccf548bdfd22a7be`; `stairs-clipping-order.md` is `459d7278de596837fb96f5e447517b6c21ce754172943679424dcbdd1c4a92d6`. The r2 report replaces only the mistyped r1 references; the cited technical reports and audit verdicts remain unchanged.

This audit accepts only the r2 applicability disposition and exact-reference corrections. Other provider-row claims remain bound to their prior reports and verdicts. Door malformed-state reachability, stair state-to-coordinate correspondence, complete tick/provider coverage, dependency closure, and the independent full-pair audit remain open. No implementation readiness or runtime parity is claimed.
