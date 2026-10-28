# Swept-AABB DDA tie-rule correction supplement

This immutable supplement corrects one statement in the original source report; neither the report nor the independent audit is modified.

## Bound inputs

- Original author report: `.native-source/20261028/frontier-c/1214-1219-swept-aabb-contract/report.md`, SHA-256 `5b7ed6ee05dda849182d8201ec90fab8a6b23feab3a860bbdfb73ed504c3b25f`.
- Independent audit: `.native-source/20261028/frontier-c/1214-1219-swept-aabb-contract-audit/audit.md`, SHA-256 `4ab7f818896302a85f9e30940bb01821fe670ffba79ff2089907ee4068c7b800`.
- The two input hashes were recomputed from their current filesystem bytes when this supplement was written.

## Correction

Replace the original sentence “Exact ties therefore choose Z over Y over X” with this precise rule for the strict comparison tree used in the DDA:

- If `tx = ty < tz`, the first selected axis is **Y**.
- If the minimum tie includes Z—`tx = tz < ty`, `ty = tz < tx`, or `tx = ty = tz`—the first selected axis is **Z**.
- If there is a unique minimum, that axis is selected.

The tree is `if (tx < ty) { if (tx < tz) X else Z } else if (ty < tz) Y else Z`. Since comparisons are strict, `tx=ty<tz` falls through the outer branch to `ty<tz`, selecting Y. The other listed minimum ties reach the final Z branch (or its inner `else`). This rule applies to the old traversal and the 1.21.9 rewrite; only local variable names and line positions differ.

Concrete check requested by the auditor: for `p=(0.75,0.75,0.25)` and `d=(1,1,1)`, each moving-axis `deltaT` is `1`; `Mth.frac(p)=(0.75,0.75,0.25)`; therefore `nextT=(0.25,0.25,0.75)`. The first condition `tx < ty` is false, and `ty < tz` is true, so **Y** advances first. This is exactly the auditor's counterexample and disproves a universal Z-first priority for minimum ties. The all-equal worked example in the author report remains correct: `p=(0.25,0.25,0.25)`, `d=(1,1,1)` gives equal `nextT=0.75` and selects Z first.

## Exact source references

All references below are canonical read-only Mojmap `BlockGetter.java` files. SHA-256 values are the verified exact file hashes recorded in the original report's `source-file-hashes.csv`; the audit independently rehashed all 42 selected files and confirmed them against the release manifests/provenance. The DDA comparison and its nested branches are at:

| Version | Lines | Source SHA-256 |
|---|---:|---|
| 1.21.4 | 208–222 | `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20` |
| 1.21.5 | 210–224 | `8494b6a560adcd84dec9340cae99a937fa98a5153fa02cf8764348c94c266d76` |
| 1.21.6 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.7 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.8 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.9 | 230–244 | `a9e5a28474bd036451daa7fa1500d37a0a2e0f7bee37bac6765ac4d58571d83c` |

For 1.21.4, the exact branch is at lines 208–222: `$$14 < $$15`, then `$$14 < $$16`, else-if `$$15 < $$16`, otherwise Z. The 1.21.5 names are `$$15`, `$$16`, `$$17`; 1.21.6–.8 use the same comparator names and body; 1.21.9 uses `$$20`, `$$21`, `$$22`. The relevant normalized method hashes and full-file hashes are preserved in the bound report artifacts.

The concrete numeric derivation uses the source expressions at 1.21.4 lines 199–204 (`sign(d)/d` and `Mth.frac`); the corresponding expressions occur inside the same source-hashed DDA methods in each listed release. Source identity and algorithm scope remain as bounded by the bound report and audit. This correction makes no runtime, callback-reachability, movement-parity, or pair-freeze claim.
