# F03: Honey Block applies a horizontal speed factor to movement calls

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 3–5; block speed factor and collision contact
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic (Honey Block is registered in B and absent from A)
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A's full block registry is `net.minecraft.world.level.block.Blocks` (SHA-256 `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9`); it has no Honey Block registration, and the A source tree has no `HoneyBlock.java`.
- B registers `Blocks.HONEY_BLOCK` with `.speedFactor(0.4F)` at `Blocks.java` lines 2118–2122 (SHA-256 `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`).
- B `Entity.move(MoverType, Vec3)` multiplies horizontal delta movement by `getBlockSpeedFactor()` at line 543. B `Entity.getBlockSpeedFactor()` reads the block at the entity position, and returns its non-default factor at lines 590–598. B `Entity.java` SHA-256 `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- B `Block.getSpeedFactor()` returns the registered block property at lines 717–719. B `Block.java` SHA-256 `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D`.
- Reachability is `LocalPlayer.aiStep` -> `Player.aiStep` -> `LivingEntity.aiStep` -> `travel(Vec3)` -> `Entity.move`. B `LocalPlayer.java` SHA-256 `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`; `Player.java` `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`; `LivingEntity.java` `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.

## Source-level difference

A has no Honey Block block or speed-factor property. In B, a player whose current block lookup resolves to Honey Block receives the `0.4F` factor on X and Z at the end of each `Entity.move` call. The vertical component is multiplied by `1.0` in this call.

## Reachability and dependencies

Local player travel -> entity collision/movement -> post-move block speed-factor lookup -> delta movement. For a player standing on the Honey Block's 15/16-high top, the entity's current block position resolves to the Honey Block cell. The factor is a block property registered in `Blocks`, read through `Block.getSpeedFactor`.

## Consequence and uncertainty

Source proves horizontal delta movement is multiplied by `0.4F` for the stated block lookup. This is a block added after A and does not imply 1.14.4 maps can contain it or that its behavior should be emulated for old maps.

## Handoff

Independent new horizontal speed behavior for B's Honey Block. Related to F04 (jump factor) and F05 (side-contact slide), but each uses a separate movement path. Exact first release remains unknown within the endpoint interval.
