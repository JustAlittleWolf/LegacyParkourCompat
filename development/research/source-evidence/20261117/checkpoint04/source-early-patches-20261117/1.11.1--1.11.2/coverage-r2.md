# 1.11.1 → 1.11.2 coverage correction r2

This immutable one-row correction is bound to the original coverage report SHA-256 `262A9753D5CEB9C983F158FF0C9239A3F7854DA20157204769BAE41EC3301EFE` and the bounded candidate ACCEPT verdict `build/source-early-patches-20261117/1.11.1--1.11.2/audit/verdict.md`, SHA-256 `9A9D9FF163F72EABCFC1E09869BCC2789423350BB21842D80C66B75422234DE7`. The verdict accepts only the WallBlock collision-height finding and explicitly leaves full-pair coverage/audit open.

| Original coverage statement | Correction |
| --- | --- |
| “`SHAPES[getShapeIndex(state)]`, whose boxes have maxY 1.0.” | In 1.11.1, two `SHAPES` entries (indices 5 and 10) have maxY 0.875; the remaining entries reach maxY 1.0. Every corresponding 1.11.1 `COLLISION_SHAPES` entry is extended to maxY 1.5. The bounded `WallBlock.addCollisions` difference and accepted movement claim remain unchanged. |

The candidate remains accepted only within the audited bounds. Full 1.11.1→1.11.2 pair coverage and independent full-pair audit remain pending. The original report and candidate are unchanged.
