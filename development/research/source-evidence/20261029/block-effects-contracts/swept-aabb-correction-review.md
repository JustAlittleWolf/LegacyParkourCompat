# Independent review of swept-AABB tie-rule supplement

## Verdict

ACCEPT. The supplement correctly repairs the original report's general tie-priority statement. I found no residual issue within the supplement's bounded source-level claim.

## Bound artifact identities

Recomputed current-file SHA-256 values:

- Author report `.native-source/20261028/frontier-c/1214-1219-swept-aabb-contract/report.md`: `5b7ed6ee05dda849182d8201ec90fab8a6b23feab3a860bbdfb73ed504c3b25f` (matches supplement).
- Original independent audit `.native-source/20261028/frontier-c/1214-1219-swept-aabb-contract-audit/audit.md`: `4ab7f818896302a85f9e30940bb01821fe670ffba79ff2089907ee4068c7b800` (matches supplement; preserved unchanged).
- Correction supplement `.native-source/20261028/frontier-c/1214-1219-swept-aabb-correction-supplement/correction-supplement.md`: `1a61b3770f0031380be1df36a01c820126a391c07e93dcd1ff6aa4340d0e91ef`.

## Independent source check

I reopened the canonical read-only Mojmap `BlockGetter.java` source in each release and rehashed each whole file. Hashes match the supplement's source references:

| Release | DDA comparison lines | Whole-file SHA-256 |
|---|---:|---|
| 1.21.4 | 208–222 | `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20` |
| 1.21.5 | 210–224 | `8494b6a560adcd84dec9340cae99a937fa98a5153fa02cf8764348c94c266d76` |
| 1.21.6 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.7 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.8 | 219–233 | `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f` |
| 1.21.9 | 230–244 | `a9e5a28474bd036451daa7fa1500d37a0a2e0f7bee37bac6765ac4d58571d83c` |

Each branch is the same strict tree: `if (tx < ty) { if (tx < tz) X else Z } else if (ty < tz) Y else Z` (with release-specific variable names and coordinate increments). Therefore a unique minimum selects its axis; `tx=ty<tz` selects Y; `tx=tz<ty`, `ty=tz<tx`, and a three-way minimum tie select Z. This verifies the correction and also confirms that the all-equal example in the original report remains valid.

For the stated example, the older traversal forms each `deltaT` as `sign(d)/d = 1`, then multiplies by `1-frac(p)` on each positive axis. With `p=(0.75,0.75,0.25)`, `frac(p)=(0.75,0.75,0.25)`, so `nextT=(0.25,0.25,0.75)`: `tx < ty` is false and `ty < tz` is true, hence Y is first. In 1.21.9 the walker derives the swept-box corner start and uses the supplied movement vector; for the equivalent unit positive displacement and these start coordinates, the same fractional offsets and strict branch produce Y first. `Mth.frac` is `x - lfloor(x)`, so the positive fractions used here are exact.

The supplement's source references accurately locate the branch in all six releases, and its claim is limited to the strict-comparison tie rule. This review makes no runtime, callback reachability, movement-parity, or pair-freeze claim.
