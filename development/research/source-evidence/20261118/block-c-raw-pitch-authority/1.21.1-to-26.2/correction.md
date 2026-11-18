# Signed arithmetic correction: 1.21.1 → 26.2 pitch witness

This correction is bound to the immutable discovery report and independent audit verdict below. It corrects only the default-sensitivity arithmetic in the report; the source-backed setter order, mouse witness, and movement consequence remain as reported.

- Discovery report SHA-256: `1cd80e3e5d24fbcd84988dd470b7ee22b619e9579a112240cb4e0b4db770c9ab`
- Independent audit verdict SHA-256: `aa73b09290c5abc024b5cee9685bc66cb0ffb78dc677578c44ac62dd8f07334b`

In both `MouseHandler.turnPlayer` implementations, the sensitivity option is a `Double`, while the literals `0.6F` and `0.2F` are `float`. Java promotes each float literal to double for the expression. With the default option value `0.5`, the exact source arithmetic is approximately:

```text
ss = 0.5 * (double)0.6F + (double)0.2F
   ≈ 0.5000000149011612
sensitivityMod = ss * ss * ss
sens = sensitivityMod * 8.0
     ≈ 1.00000008940697
```

For the same finite accumulated mouse displacement `accumulatedDY = 4800`, in the ordinary non-smooth, non-scoped branch, `yo = accumulatedDY * sens ≈ 4800.000429153455`. The cast in `Entity.turn` rounds this to float `4800.00048828125`; multiplying by `0.15F` produces `xDelta = 720.0001220703125F`.

Starting from pitch zero, 1.21.1 stores `90.0F` after its explicit cumulative clamp. In 26.2, the setter first computes `xDelta % 360.0F`, yielding `0.0001220703125F`, and clamps that value; the subsequent `turn` clamp leaves authoritative pitch at `0.0001220703125F`. The immediate interpolation field `xRotO` remains independently accumulated and clamped to `90.0F` on both sides, as stated in the original report.

No additional OS input limits or runtime behavior are asserted by this correction.
