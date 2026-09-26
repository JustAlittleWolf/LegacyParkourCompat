# Jump Boost addition now rounds after widening to double

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-JUMP-BOOST`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()` and `jumpFromGround()` lines 1878–1892; SHA-256 `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.
- B: `decompiled_minecraft/1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower()`, new `getJumpBoostPower()`, and `jumpFromGround()` lines 1967–1984; SHA-256 `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F`.
- Player dispatch: A `world/entity/player/Player.java` lines 1376–1377 (SHA-256 `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960`); B same path lines 1398–1399 (SHA-256 `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481`). Both overrides call `super.jumpFromGround()`.
- Reachability: A/B `LivingEntity.aiStep()` calls `jumpFromGround()` at lines 2440/2527 respectively; local players inherit `Player`. The active jump effect is `MobEffects.JUMP` (A/B source hashes match: `64B3B592A48EAC1016C3A307B85C3E201689662DEA68C80B6D642CFFA35FD289`).

## Source-level difference

A stores `getJumpPower()` in a `float`, conditionally performs `f += 0.1F * (amplifier + 1)`, and writes `f` to the vertical movement component. The compound assignment rounds the sum back to binary32. B moves the effect term into a `double getJumpBoostPower()` and computes `double d = getJumpPower() + getJumpBoostPower()`. With an active effect, the product remains float precision before widening, but the addition is binary64; it no longer rounds the total to binary32 before storing it as the vector's double Y component.

Concrete source-derived example: for `getBlockJumpFactor() == 1.0F` and Jump Boost amplifier 0, `getJumpPower()` is binary32 `0.41999998688697815` and the boost product is binary32 `0.10000000149011612`. A assigns binary32 `0.5199999809265137`; B adds their widened values and assigns binary64 `0.5199999883770943`. This follows the literal suffixes and Java numeric promotion/compound-assignment rules; it is not a gameplay measurement.

## Reachability and dependencies

The player jump dispatch reaches `Player.jumpFromGround()` and then `LivingEntity.jumpFromGround()`. `LivingEntity.aiStep()` invokes the virtual jump method on the grounded jump branch. Both versions use the same `getJumpPower()` formula, `0.42F * getBlockJumpFactor()`. The changed operand is the active Jump Boost amplifier. No block-state change is required for the stated example; the factor is fixed to 1.0F as the example precondition.

## Consequence and uncertainty

Source proves that, with Jump Boost active, the assigned vertical movement value can differ at binary64 precision because A rounds the sum to float and B does not. The example differs by about `7.45e-9`. A particular trajectory, later collision outcome, or observable landing difference has not been measured. The endpoint pair does not identify the first release of the change.

## Handoff

Independent delta: retain the version-specific precision of the Jump Boost sum on the player jump path. Applicability is limited to jumps where the active effect contributes and float rounding changes the sum; runtime validation is deferred.
