# F01: Ground friction samples a different block under tall partial surfaces

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stage 3 travel friction; stage 5 block collision shape and movement property lookup
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (uses blocks and states available in A)
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A: `net.minecraft.world.entity.LivingEntity.travel(Vec3)` samples `new BlockPos(this.x, this.getBoundingBox().minY - 1.0, this.z)` at lines 1837–1840. SHA-256 of `net/minecraft/world/entity/LivingEntity.java`: `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`.
- B: `net.minecraft.world.entity.LivingEntity.travel(Vec3)` instead samples `this.getBlockPosBelowThatAffectsMyMovement()` at lines 1886–1889. SHA-256 of `net/minecraft/world/entity/LivingEntity.java`: `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- B: `net.minecraft.world.entity.Entity.getBlockPosBelowThatAffectsMyMovement()` uses `this.getBoundingBox().minY - 0.5000001` at lines 600–602. SHA-256 of `net/minecraft/world/entity/Entity.java`: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- Both versions' `net.minecraft.core.Vec3i(double, double, double)` floors each coordinate (A lines 20–22; B lines 23–25). A SHA-256: `C3A2D3ADD563193AE9F361F0BEBF796C6CC7D6627B8D693DDD477CCA2DA3854F`; B SHA-256: `DD4AD167AD9B4BE90E9B13216E8B450B7BC401CA700732C2DD78970739942C7D`.
- A and B `SnowLayerBlock.getCollisionShape` select `SHAPE_BY_LAYER[LAYERS - 1]`; with `LAYERS=6`, that is the 10/16-high shape. The property range is 1–8. A `SnowLayerBlock.java` SHA-256: `DBE1D2884C7D577AF49AA214EBD0CAF6B7B6D71A3128FD746D9EE796DF2DA48F`; B: `89A20299049E7B33F92BC5A6D18F3C0C5D429A7891D85179315B68845A1FF486`. `BlockStateProperties.LAYERS` is line 77 in each release; A file SHA-256 `61A33708F3D794C9B413BE53A3CE482E2313DB12098E5DFB08265AC844A6EDA3`; B `25B94B4403653D6EAC058B9057B92019B97D549D9AC1661040A0A5E137513D07`.
- A and B register snow without a friction override (A `Blocks.java` lines 555–557; B lines 579–581); `Block.Properties.friction` defaults to `0.6F` (A `Block.java` line 851; B line 858). Both register ice with friction `0.98F` (A `Blocks.java` lines 558–559; B lines 582–583). A `Blocks.java` SHA-256: `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9`; B: `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`. A `Block.java` SHA-256: `276022CFC5BC00437FE65A23BBCDC9C078026E68D88930EB3244581F6DBFCF5F`; B: `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D`.
- A `LivingEntity.getFrictionInfluencedSpeed(float)` at lines 1957–1959 and B at 2006–2008 both use the sampled friction in the grounded acceleration formula. The surrounding travel branch also uses `onGround ? v * 0.91F : 0.91F` for horizontal retention (A lines 1837–1842; B lines 1886–1891).
- Reachability: `LocalPlayer.aiStep` calls through `Player.aiStep` to `LivingEntity.aiStep`, which dispatches to `travel(Vec3)` (A LocalPlayer line 772, Player line 510, LivingEntity line 2223; B lines 778, 514 and 2287). Source hashes are recorded in the artifact manifest and newer-side inventory.

## Source-level difference

For a six-layer snow state at block row `y`, its collision top is `y + 10/16`. For a player standing on it, A constructs the friction lookup at `y - 6/16`, which floors to row `y - 1`. B constructs it at `y + 10/16 - 0.5000001`, which floors to row `y`. If row `y - 1` is ice, A uses friction `0.98F`; B uses the snow block's default `0.6F`.

Under the grounded precondition, this changes both the friction-influenced input acceleration and the post-move horizontal retention. The source establishes those formula inputs and operation order; the net position or velocity over multiple ticks depends on prior velocity and input.

## Reachability and dependencies

Local player input -> `LivingEntity.aiStep` -> `travel(Vec3)` -> block lookup -> `getFrictionInfluencedSpeed` and grounded drag -> horizontal velocity. The concrete state uses the existing snow-layer property, its collision shape and the existing ice friction registration on both sides. The relevant friction block is locally present in the world state; no server-derived movement attribute is involved.

## Consequence and uncertainty

Source proves a different block row is sampled and gives different friction values for the stated precondition. The old formula supplies `0.98F`; the new formula supplies `0.6F`. This changes the per-call acceleration and drag terms. No trajectory was recorded. The finding is scoped to grounded states where the two selected rows have different friction; it does not claim a general friction difference on full-height blocks or on ordinary flat ground.

## Handoff

Independent change in the player ground-friction lookup. Related to the broader movement-state lookup refactor, but separate from Soul Sand's repeated contact callback and Honey Block's new speed/jump factors. Exact introduction release remains unknown within the inspected endpoints.
