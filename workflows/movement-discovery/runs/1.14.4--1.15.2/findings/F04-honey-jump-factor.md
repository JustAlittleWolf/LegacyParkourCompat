# F04: Honey Block halves the base ground-jump impulse

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 2–3 and 5; jump impulse and block jump factor
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic (Honey Block is registered in B and absent from A)
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `LivingEntity.getJumpPower()` returns `0.42F` at lines 1748–1750; its `jumpFromGround()` uses that value and adds Jump Boost afterward at lines 1752–1758. A `LivingEntity.java` SHA-256: `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`.
- B `LivingEntity.getJumpPower()` returns `0.42F * this.getBlockJumpFactor()` at lines 1799–1801; `jumpFromGround()` adds Jump Boost afterward at lines 1803–1807. B `LivingEntity.java` SHA-256: `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- B `Entity.getBlockJumpFactor()` reads the factor at the entity position and falls back to the block below when the current factor equals `1.0F` (lines 584–588); B `Entity.java` SHA-256 `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- B `Blocks.HONEY_BLOCK` registers `.jumpFactor(0.5F)` at `Blocks.java` lines 2118–2122 (SHA-256 `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`). A's registry has no Honey Block registration; A `Blocks.java` SHA-256 `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9`.

## Source-level difference

With no Jump Boost and a ground jump whose block lookup resolves to Honey Block, B calculates `0.42F * 0.5F` for the initial vertical impulse; A has no Honey Block and uses `0.42F`. If Jump Boost is active, its additive term is applied after this base-power calculation in both source paths.

## Reachability and dependencies

Local player input -> living movement integration -> `jumpFromGround()` -> `getJumpPower()` -> current block's `jumpFactor`. The B factor comes from the Honey Block registration and is read through the shared entity helper. The player reaches this path through the `LivingEntity` superclass.

## Consequence and uncertainty

Source proves the changed vertical impulse formula and its operation order. This is a new block mechanic in B, so it must remain classified as modern-only and does not establish historical behavior for 1.14.4 maps.

## Handoff

Independent Honey Block jump behavior; related to F03 and F05 but separately describable. Exact first changed release remains unknown within the endpoint interval.
