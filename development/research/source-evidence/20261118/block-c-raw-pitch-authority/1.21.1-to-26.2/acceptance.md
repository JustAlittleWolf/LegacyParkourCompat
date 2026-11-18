# Signed independent acceptance: 1.21.1 → 26.2 pitch setter witness

## Immutable review binding

- Discovery witness `c-raw-pitch-setter-1211-262-destination-witness/report.md`, SHA-256: `1cd80e3e5d24fbcd84988dd470b7ee22b619e9579a112240cb4e0b4db770c9ab`.
- Independent exact-source verdict `c-raw-pitch-setter-1211-262-destination-audit/verdict.md`, final SHA-256: `aa73b09290c5abc024b5cee9685bc66cb0ffb78dc677578c44ac62dd8f07334b`.
- Author arithmetic correction `c-raw-pitch-setter-1211-262-destination-witness/correction.md`, SHA-256: `00f51c04b2320f389b2335da2e1c2e0e18dc397209c859eaa3a1f2494e153a45`.

I rehashed the three exact files above. The correction names the same immutable report and final verdict hashes. I independently checked its Java promotion and float-width reasoning against the recorded `OptionInstance<Double>` default and `Entity.turn` setter/multiplication source. With default sensitivity 0.5, binary32 constants `0.6F` and `0.2F` are promoted to double for the sensitivity expression; the resulting finite callback witness gives a float pitch delta of `720.0001220703125F`, not exactly `720.0F`. The old path's final authoritative pitch is `90.0F`; the new setter's float remainder and clamp yield `0.0001220703125F`. The correction therefore closes the numerical qualifier in the frozen verdict without changing its source-backed conclusion.

**Accepted within this bounded movement ownership:** standard mouse callback accumulation and `turnPlayer` scaling → authoritative `Entity.xRot` after `Entity.turn` → the live pitch/look reads in active fall-flying movement for exact releases 1.21.1 Mojmap and 26.2 unobfuscated. The callback sequence remains a source-level conditional reachability witness; this acceptance does not assert a particular OS/device emits the displacement, runtime execution, grounded pitch-driven movement, complete pair coverage, or any unrelated movement operation. No source or frozen verdict file was modified for this acceptance.

**Reviewer:** `/root/c-raw-pitch-setter-1211-262-destination-audit`, independent source audit, 2026-11-18.
