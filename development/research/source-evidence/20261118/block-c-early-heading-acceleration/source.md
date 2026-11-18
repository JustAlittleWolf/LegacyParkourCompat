# Pre-1.12 yaw math source gate

## Scope and verdict

**Verdict: PASS for the transitive math dependency and ordinary dry local on-foot ground/air gates across 1.9–1.11.2, including the 1.12 boundary.** The pre-1.12 two-axis relative-input path reaches the same float square-root implementation and the same sine-table construction and lookup in every inspected release. No exception was found in this bounded gate.

This closes only the requested `MathHelper` dependency and ordinary nonflying dry local ground/air applicability gate. It is not a new full tick or pair-coverage verdict. At 1.12, the generic movement helper has a third input axis; the accepted adjacent-pair audit records that the player path's vertical input remains zero. That separate dimensional API change is outside this math-gate verdict.

## Exact source evidence

Mapping family: Ornithe Feather. Canonical local source root: `decompiled_minecraft/<version>/ornithe-feather/`.

For each of `1.9`, `1.9.1`, `1.9.2`, `1.9.3`, `1.9.4`, `1.10`, `1.10.1`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, and `1.12`, `net/minecraft/util/math/MathHelper.java` contains identical relevant bodies:

- `sqrt(float x)` returns `(float)Math.sqrt(x)`: the input is promoted to double for `Math.sqrt`, then rounded back to float at the explicit cast.
- `sin(float x)` indexes `SINE_TABLE[(int)(x * 10430.378F) & 65535]`.
- `cos(float x)` indexes `SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]`.
- Static initialization iterates `i` from 0 through 65535 and stores `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. In the table builder, `i` is promoted to double by `Math.PI`; multiplication/division remain double, `Math.sin` returns double, and only the final table store casts to float.

The concatenated selected-body SHA-256 (UTF-8 source text, in order `sqrt(float)`, `sin(float)`, `cos(float)`, table initializer) is identical for all twelve releases: `2adb0a0dd843ee1764cbda21e77d15e6123b4c07ef6d42ffb80c948d95b1ac18`.

Full `MathHelper.java` source hashes by identical groups:

| Releases | SHA-256 |
|---|---|
| 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.1, 1.10.2 | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.11, 1.11.1, 1.11.2 | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |
| 1.12 | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |

The differing full-file hashes arise outside the selected helper bodies. The relevant bodies are byte-identical in the checked canonical files.

## Applicability and dependency closure

The accepted adjacent yaw source reports/audits cover the ordinary dry ground/air relative-acceleration caller through 1.11.2. Their bounded route is local player movement into the non-water, non-lava, non-fall-flying branch, selecting ground or air scale and calling the two-axis `Entity.updateVelocity`. In that consumer the input-square sum is float arithmetic; after its strict small-input gate, the square root call is the float overload verified above. Yaw is converted with the already accepted float expression `yaw * (float)(Math.PI / 180.0)` before the table-backed trig lookups. The unchanged helper provenance therefore closes the deferred transitive math dependency for the accepted pre-1.12 consumer.

For the boundary, the accepted `1.11.2 → 1.12` source verdict says the 1.12 player's new vertical input remains zero on the ordinary player path, so the 3D normalization reduces to the previous two-axis norm in this witness. The horizontal trig expressions still use the same lookup helper and operation order. This report accepts that as the boundary witness only; it does not generalize to a nonzero third input supplied by another entity or path.

## Bound prior evidence

- Accepted source/audit artifacts from `40782447`: `development/research/source-evidence/20261118/block-c-local-yaw-before-113/1.9--1.9.1/`, `1.9.1--1.9.2/`, `1.9.2--1.9.3/`, `1.9.3--1.9.4/`, and `1.11.2--1.12/` (and the retained `1.9--1.9.4/source-pins.md`). The grouped 1.9 adjacent-pair evidence states the dry local ground/air consumer and matching float square-root/trig behavior; the 1.11.2→1.12 audit bounds the dimensional API change and player-zero vertical component.
- Published independent audit: `development/research/source-evidence/20261118/block-c-interrupted-drafts/payload/c-pre113-yaw-194-1112-audit/verdict.md`, whose verdict is limited to yaw and ordinary dry consumer context and explicitly leaves upstream input/scale production outside its scope.

I could not locate a separately named `c-input-earlier-math` comparison/verdict in the available `development/research` artifacts. This source gate independently checked the named canonical helper bodies against the accepted adjacent source evidence; the missing separately named artifact is not used as evidence here.
