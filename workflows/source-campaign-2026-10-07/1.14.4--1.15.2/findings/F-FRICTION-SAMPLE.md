# F-FRICTION-SAMPLE: Ground travel samples a different support block

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-TICK, INV-STATE, INV-WORLD-MOVEMENT; S-FRICTION-SAMPLE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)`, grounded branch lines 1837-1842 samples `new BlockPos(x, bbox.minY - 1.0, z)`, gets friction, derives `w = onGround ? v * 0.91F : 0.91F`, then applies friction-influenced speed; SHA-256 `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`.
- B `net/minecraft/world/entity/LivingEntity.java::LivingEntity#travel(Vec3)`, lines 1886-1891 uses `getBlockPosBelowThatAffectsMyMovement()` before the same friction and acceleration sequence; `Entity.java::getBlockPosBelowThatAffectsMyMovement()` lines 600-602 returns `new BlockPos(x, bbox.minY - 0.5000001, z)`. SHA-256 LivingEntity `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; Entity `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- Both `SnowLayerBlock.java` sources define collision shape at `SHAPE_BY_LAYER[layers - 1]`, with each layer step 2/16 block high; A SHA-256 `dbe1d2884c7d577af49aa214ebd0caf6b7b6d71a3128fd746d9ee796df2da48f`, B `89a20299049e7b33f92bc5a6d18f3c0c5d429a7891d85179315b68845a1ff486`.
- Both `Blocks.java` register Slime Block with friction `0.8F`; A SHA-256 `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`, B `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`. `Block.Properties` default friction is `0.6F` in both releases; A SHA-256 `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f`, B `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`.
- Both sides pass the sampled value to `getFrictionInfluencedSpeed` in the grounded branch (A lines 1837-1842, B lines 1886-1891); the helper formula is identical, `getSpeed() * (0.21600002F / (f * f * f))`, when on ground (A lines 1957-1958, B lines 2006-2007). The same support sample also feeds `w = onGround ? v * 0.91F : 0.91F`.

## Source-level difference

At six snow layers, collision height is `(6 - 1) * 2/16 = 0.625` above the snow cell. With the player's feet at that collision top, A's `minY - 1.0` floors into the supporting Slime Block; B's `minY - 0.5000001` floors into the Snow Layer Block. Snow uses default friction 0.6, while Slime uses 0.8. Thus the sampled friction and the input to ground acceleration differ on this reachable arrangement. Snow's `canSurvive` rejects Ice, so Ice is not a valid example.

## Reachability and dependencies

The player ordinary travel branch samples friction when not in the fall-flying branch, then passes it to `getFrictionInfluencedSpeed` and `moveRelative`. Six-layer Snow has a collision surface and both versions' `SnowLayerBlock.canSurvive` permits a full-face Slime support. The registry values and the shape calculation above establish the concrete support path.

## Consequence and uncertainty

The source proves differing friction inputs to travel. Resulting acceleration and velocity depend on the movement input and current state; no measured trajectory is claimed. More boundary configurations and related support lookups remain unaudited.

## Handoff

Preserve the 1.14.4 support-position sampling for the selected historical version. Continue shape and world-movement inventory under S-FRICTION-SAMPLE.
