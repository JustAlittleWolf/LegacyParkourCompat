# COMPLETED — independent source audit

## Bound input

- Comparison: `glide-trig-later-source/comparison.md`
- SHA-256: `0165c239fd885fa32e254a09cfe07c06b27bdc3a25a7f00a380d8ee25d3ab44c`
- Scope audited: canonical Mojmap sources from 1.20.6 through 1.21.11, including each listed intermediate release. No source before 1.20.6 was used or inferred.

## Verdict

**Accepted for the stated late source boundary.** The comparison's main claim, intermediate coverage, and Elytra provider path agree with the canonical source. `Entity.calculateViewVector(float, float)` has the same method body in all 13 checked releases. Each `Entity.getLookAngle()` delegates with the player's X/Y rotation to that method.

For 1.20.6 through 1.21.10, `Mth` uses `float[] SIN`, `sin(float)` / `cos(float)`, the float scale `10430.378F`, float index arithmetic and a 65535 mask. In 1.21.11 the lookup API takes `double`, uses the double scale `10430.378350470453`, converts the product to `long` before masking, and still returns a float from the float table. The float radian locals in `calculateViewVector` widen at the 1.21.11 calls. This establishes the observed boundary from the last inspected float lookup (1.21.10) to the first inspected double lookup (1.21.11); it says nothing about introduction before 1.20.6.

A concrete lookup witness is `yaw = -51.026F`, `pitch = 0F` in `calculateViewVector`. The unchanged float conversion gives `-yaw * (float)(Math.PI / 180.0) = 0.8905717134475708F`. Using the respective source lookup expressions, the old sine index is 9289 and the new sine index is 9288; the cosine-offset indices are 25673 and 25672. The source-defined table formulas give distinct values at these indices, so the returned look vector's X and Z components differ. The witness is for the provider math and makes no claim about a particular trajectory.

The exact Elytra caller is as reported: in 1.20.6, 1.21 and 1.21.1, `LivingEntity.travel` reads `this.getLookAngle()` in the fall-flying branch; from 1.21.2 through 1.21.11, `LivingEntity.updateFallFlyingMovement` reads it. This verifies the caller/provider relation only. Gravity and activation behavior were not audited.

## Source integrity and identities

For every listed release, SHA-256 matched the canonical local source manifest/provenance entry for all three files: `Entity.java`, `LivingEntity.java`, and `Mth.java` (39/39 source-file checks). Source inventory identities used in the audit:

| Release | Source manifest/provenance SHA-256 |
|---|---|
| 1.20.6 | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |
| 1.21 | `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` |
| 1.21.1 | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` |
| 1.21.2 | `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` |
| 1.21.3 | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` |
| 1.21.4 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` |
| 1.21.5 | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` |
| 1.21.6 | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` |
| 1.21.7 | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` |
| 1.21.8 | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` |
| 1.21.9 | `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` |
| 1.21.10 | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` |
| 1.21.11 | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` |

Primary source locations: `decompiled_minecraft/<version>/mojmap/net/minecraft/util/Mth.java` (`SIN`, `sin`, `cos`); `.../world/entity/Entity.java` (`calculateViewVector`, `getLookAngle`); and `.../world/entity/LivingEntity.java` (Elytra caller described above). No product source, Git history, wiki, implementation, tests or game launch was consulted.