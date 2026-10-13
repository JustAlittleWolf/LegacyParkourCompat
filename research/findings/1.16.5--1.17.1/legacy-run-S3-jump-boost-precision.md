# Jump Boost addition now rounds after widening to double

- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1878–1892.
- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1967–1984.

## Difference

1.16.5 stores `getJumpPower()` in a `float`, conditionally performs `f += 0.1F * (amplifier + 1)`, and writes `f` to the vertical movement component.

The compound assignment rounds the sum back to binary32. 1.17.1 moves the effect term into a `double getJumpBoostPower()` and computes `double d = getJumpPower() + getJumpBoostPower()`.

With an active effect, the product remains float precision before widening, but the addition is binary64; it no longer rounds the total to binary32 before storing it as the vector's double Y component.

Concrete source-derived example: for `getBlockJumpFactor() == 1.0F` and Jump Boost amplifier 0, `getJumpPower()` is binary32 `0.41999998688697815` and the boost product is binary32 `0.10000000149011612`. 1.16.5 assigns binary32 `0.5199999809265137`; 1.17.1 adds their widened values and assigns binary64 `0.5199999883770943`.

This follows the literal suffixes and Java numeric promotion/compound-assignment rules; it is not a gameplay measurement.

## Movement meaning

The player jump dispatch reaches `Player.jumpFromGround()` and then `LivingEntity.jumpFromGround()`. `LivingEntity.aiStep()` invokes the virtual jump method on the grounded jump branch.

Both versions use the same `getJumpPower()` formula, `0.42F * getBlockJumpFactor()`.

The changed operand is the active Jump Boost amplifier.

No block-state change is required for the stated example; the factor is fixed to 1.0F as the example precondition.

Source proves that, with Jump Boost active, the assigned vertical movement value can differ at binary64 precision because 1.16.5 rounds the sum to float and 1.17.1 does not.

The example differs by about `7.45e-9`. 1.16.5 particular trajectory, later collision outcome, or observable landing difference has not been measured.

The endpoint pair does not identify the first release of the change.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-jump-boost-precision.md).
