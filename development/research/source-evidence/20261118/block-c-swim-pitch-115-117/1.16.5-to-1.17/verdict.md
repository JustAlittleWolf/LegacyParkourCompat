# Independent audit: 1.16.5→1.17 player swim pitch-to-Y correction

## Verdict

**Qualified agreement.** The report's primary conclusion is source-confirmed: there is no behavioral delta in the player-level swim Y correction, pitch-to-look-vector conversion, sine lookup math, or the ordering of the correction before the inherited water-travel move. The 1.17 pitch/yaw getters return the stored fields, and the relevant expressions and gates match.

One supporting detail in the report is inaccurate: it says 1.17 uses the newly declared `SIN_SCALE` constant in `Mth.sin`/`Mth.cos`. The 1.17 methods still use the literal `10430.378F`; the constant declared at line 26 is unused there. Since the literal equals the constant's value, this does not change the report's no-behavior-delta conclusion, but the statement should be corrected for an exact math/cast audit. The downstream `Entity.move` implementation claim is outside this bounded audit and receives no verdict here.

## Audited evidence

The supplied source report was read only after freezing the bounded source trace and source-set identities. Its SHA-256 is `f2524fcbc97a6f4d6cb7ba27ad2f83ce0d9f74b4a85cdd2bdd1d6e5c50de8343`.

- 1.16.5 Mojmap `Player.travel(Vec3)`, `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/player/Player.java:1387-1416`: when `isSwimming() && !isPassenger()`, obtains `g` from `getLookAngle().y`, selects `0.085` only for `g < -0.2` and otherwise `0.06`, then under `g <= 0.0 || jumping || above-fluid` adds `(g - currentVelocityY) * h` to delta Y. The correction precedes the call to `super.travel(vec3)`; movement statistics follow it.
- 1.17 Mojmap `Player.travel(Vec3)`, `decompiled_minecraft/1.17/mojmap/net/minecraft/world/entity/player/Player.java:1418-1447`: same gate, threshold, coefficients, above-fluid query, condition order, arithmetic and state-write order, then the same delegation and statistics sequence.
- 1.16.5 `Entity.getLookAngle` reads `xRot`/`yRot` directly (`Entity.java:1700-1702`); 1.17 reads `getXRot()`/`getYRot()` (`Entity.java:1829-1831`). The 1.17 getters return `this.xRot` and `this.yRot` directly (`Entity.java:3005-3018`), so the access change supplies the same values on this path.
- Both `Entity.calculateViewVector(float,float)` bodies multiply pitch and negated yaw by `(float)(Math.PI / 180.0)`, call `Mth.cos`/`Mth.sin` in the same order, and return `(sin(yawRadians) * cos(pitchRadians), -sin(pitchRadians), cos(yawRadians) * cos(pitchRadians)` (1.16.5 `Entity.java:1243-1250`; 1.17 `Entity.java:1337-1344`). Thus the correction's `g` is the same lookup-table-derived negative sine of current pitch.
- 1.16.5 `Mth.sin` and `Mth.cos` use `(int)(f * 10430.378F) & 65535` and `(int)(f * 10430.378F + 16384.0F) & 65535` (`Mth.java:25-30`). 1.17 uses those same literal expressions (`Mth.java:43-48`). Its `SIN_SCALE = 10430.378F` declaration at line 26 is not referenced in either method. Both versions initialize the 65,536-entry float table from `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)` (1.16.5 `Mth.java:12-16`; 1.17 `Mth.java:27-31`). Therefore truncating casts, mask, cosine offset, and lookup table generation are identical.
- The ordinary water path in both inherited `LivingEntity.travel(Vec3)` methods uses the same `isInWater() && isAffectedByFluids() && !canStandOnFluid(...)` gate and executes `moveRelative`, then `move(MoverType.SELF, getDeltaMovement())`, followed by the same water drag/falling adjustments (1.16.5 `LivingEntity.java:1923-1957`; 1.17 `LivingEntity.java:2008-2042`). This confirms the correction is applied before the inherited real move on the ordinary swimming-in-water path.

## Source identity

The primary compared sources are the canonical local Mojmap decompilations. The following content hashes were checked against the version's local evidence:

| Version | Source identity | Player.java | Entity.java | LivingEntity.java | Mth.java |
| --- | --- | --- | --- | --- | --- |
| 1.16.5 | `mojmap.ready.json` SHA-256 `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; `mojmap.provenance.json` SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.17 | hidden mapping-root `.decompile-provenance.json` SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |

The 1.16.5 marker binds source-manifest SHA-256 `9499f261…20823b`, artifact-manifest SHA-256 `f9b9812d…8b370c`, diagnostics SHA-256 `676ea98f…7ec50`, and the exact version/mapping. The raw 1.16.5 cache is absent, so no raw artifact was independently rehashed. For 1.17, the present local pins and their bytes agree for `client.jar` (`14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`), Mojmap `client_mappings.txt` (`191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`), and `version.json` (`537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`). The 1.17 source-file hashes match entries in `.decompile-provenance.json`.

## Boundary

This verdict covers the player swim correction, its pitch conversion/accessors/trigonometry, gates and ordering through the inherited water-branch `move` invocation. It does not audit collision resolution, `Entity.move`, or full 1.16.5→1.17 movement coverage.
