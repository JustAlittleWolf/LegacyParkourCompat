# Jump-boost addition precision boundary

## Finding

The ordinary ground-jump boost addition remains a binary32 (`float`) addition in 1.12.2, 1.13.2, 1.14.4, 1.15.2, 1.16.5, and 1.17. In 1.17.1 the same operands are added as binary64 (`double`). The operand-producing boost multiplication remains binary32 in 1.17.1; the precision boundary is specifically the addition of base jump power and boost power. With Jump Boost active and a nonzero amplifier term, the 1.17.1 result can retain low bits that earlier versions round off before storing vertical velocity.

## Exact source evidence

Paths below are relative to `decompiled_minecraft/`. Ready metadata reports Vineflower as decompiler. SHA-256 values identify the inspected source files in the local tree.

- 1.12.2, `ornithe-feather` (`feather-gen2-1.12.2+build.2`, mapping manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`): `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength()` returns `0.42F` (1394-1396); `jump()` stores it to float `velocityY`, then adds `(amplifier + 1) * 0.1F` to that float (1398-1402). File SHA-256 `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6`.
- 1.13.2, `ornithe-feather` (`feather-gen2-1.13.2+build.2`, manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`): `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength()` returns `0.42F` (1447-1449); `jump()` performs the same float assignment and addition (1451-1455). File SHA-256 `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C`.
- 1.14.4, `ornithe-feather` (`feather-gen2-1.14.4+build.2`, manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`): `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength()` returns `0.42F` (1752-1754); active-effect branch assigns `getJumpStrength() + 0.1F * (amplifier + 1)` to float `f`, and inactive branch assigns only base strength (1756-1762). File SHA-256 `2CCCF4331CE9E62013EEB8E96163E5E146E1291619EB998CE9867DAA87C02B61`.
- 1.15.2, `mojmap` (manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`): `mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()` returns `0.42F * getBlockJumpFactor()` (1799-1801); `jumpFromGround()` accumulates the active effect's `0.1F * (amplifier + 1)` into float `f`, then supplies it as the vertical `setDeltaMovement` argument (1803-1810). File SHA-256 `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- 1.16.5, `mojmap` (manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`): `mojmap/net/minecraft/world/entity/LivingEntity.java`, same float base calculation (1878-1880), float accumulator and effect gate (1882-1886), and float-to-double argument widening at `setDeltaMovement` (1888-1889). File SHA-256 `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.
- 1.17, `mojmap`: `mojmap/net/minecraft/world/entity/LivingEntity.java`, same float base calculation (1963-1965), float accumulator and effect gate (1967-1971), followed by float-to-double widening in `setDeltaMovement` (1973-1974). File SHA-256 `8E81E5F1317D154FA41B676721FF581BADD46296F77BCEBCADB3C63448E528ED`. **Input limitation:** the local 1.17 directory has the source tree but no `mojmap.ready.json`, `mojmap.provenance.json`, or `mojmap.sources.sha256`; therefore its exact publication/decompile identity and whole-tree integrity cannot be confirmed from the permitted local evidence. The file hash only pins the bytes inspected here.
- 1.17.1, `mojmap` (manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`): `mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()` remains float (1967-1969); `getJumpBoostPower()` returns `double`, but computes its nonzero term as `0.1F * (amplifier + 1)` (1971-1973); `jumpFromGround()` stores `getJumpPower() + getJumpBoostPower()` in double `d` and passes it to `setDeltaMovement` (1975-1978). The float-plus-double expression promotes the base float to double before addition. File SHA-256 `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F`.

Manifest SHA-256 values are from each corresponding `*.ready.json` field `sourceManifestSha256`. For 1.14.4, the inspected Feather mapping is one of two locally ready mappings; the Mojmap source also exists but was not needed for the preceding-endpoint check.

## Operand types and gates

For 1.16.5 and 1.17, `getJumpPower()` and the local accumulator `f` are `float`. `0.1F * (amplifier + 1)` is also a float expression; `f +=` therefore rounds the sum to float before passing it to the double-valued vector setter, where widening is exact. In 1.17.1, the same product is computed in float inside `getJumpBoostPower()`, then widened by its `double` return type. The caller's float-plus-double expression is a double addition, and the setter receives that double directly. The false effect branch returns `0.0` (double); without Jump Boost, adding zero to the exactly widened base float changes no value. The meaningful changed path is an active Jump Boost effect whose nonzero float boost is summed with the float base.

Ordinary base strength is `0.42F` in the earlier fixed-strength versions. From 1.15.2 onward it is `0.42F * getBlockJumpFactor()`; the inspected Entity implementation in 1.16.5, 1.17, and 1.17.1 reads the entity's current and movement-supporting block jump factors (1.16.5 `Entity.java:611-615`; 1.17/1.17.1 `Entity.java:693-697`). This keeps vanilla state in scope and allows ordinary full-block support to give factor 1.0. Equipment- or attribute-derived custom jump power is outside this comparison.

## Caller and reachable player path

In 1.17.1, `LocalPlayer.aiStep()` copies input jumping state and reaches `super.aiStep()` (`client/player/LocalPlayer.java:649-...; 810`); `Player.aiStep()` calls its superclass (`world/entity/player/Player.java:506`). In `LivingEntity.aiStep()`, the ground-jump path checks `jumping`, fluid conditions, `onGround` (or the shallow-water branch), and `noJumpDelay == 0`, then calls `jumpFromGround()` and sets delay 10 (`world/entity/LivingEntity.java:2514-2529`). Thus a local player pressing jump while grounded, outside the liquid alternatives, with zero delay reaches the changed sum. The movement consequence is the immediate vertical delta written for that jump; later trajectory effects are not asserted here.

## Concrete numeric witness

Use the ordinary full-block base `0.42F` and Jump Boost amplifier `0` (term `0.1F`). Exact binary32 operands are `0.41999998688697815` and `0.10000000149011612`.

- Through 1.17, binary32 addition rounds the sum to `0.5199999809265137` (`float` bits `0x3f051eb8`), then widening stores that value as double.
- In 1.17.1, widening the two float operands and adding in binary64 stores `0.5199999883770943` (double bits `0x3fe0a3d704000000`).
- Difference: `0.000000007450580596923828`, one eighth of the binary32 ULP at this magnitude. It is a concrete one-step vertical-velocity difference, not a simulated trajectory.

## Bounded applicability conclusion

The same bounded operation (base strength plus the active Jump Boost addition, before writing vertical velocity) is float-rounded at every checked predecessor: 1.12.2, 1.13.2, 1.14.4, 1.15.2, 1.16.5 and 1.17. The first checked version where the operation is double-rounded is 1.17.1. This establishes an endpoint boundary only; it does not identify the exact patch/version where a change was introduced between any uninspected releases. The 1.17 source body is present and matches the float path, but its absent publication metadata remains an evidence-identity limitation as stated above.

Scope excluded custom equipment/attribute jump changes, sprint-vector changes, water/lava jumps, other movement differences, and runtime or trajectory validation. No tests, runtime launches, source generation, or product source reads were performed.