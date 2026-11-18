# Independent source audit: 1.20.2 → 1.20.3

**Verdict: ACCEPTED — bounded no change.** The source claim is that, for an ordinary dry, airborne, nonpassenger local player with flag 7 set, a working Elytra in the default chest slot, and lawful ability flight (`mayfly` and `flying`), `Player.travel` performs the glide move through `LivingEntity.travel` and then clears flag 7 unconditionally in the ability-flight branch. The relevant move and clear behavior is the same in both exact releases.

## Immutable reviewed claim and evidence

- Author report: `c-mid-1202-1203-ability-flight-glide-clear-source/report.md`, SHA-256 `acc2c059a87370128302abe7b1ee27a891758b72da0e3eb6a738e65a33c83544` (verified before opening the report).
- Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only); namespace `mojmap` on both sides.
- 1.20.2 source manifest: `decompiled_minecraft/1.20.2/mojmap.sources.sha256`, SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. Its `mojmap.ready.json` pins version `1.20.2`, mapping `mojmap`, and client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; the marker's artifact manifest is pinned as `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`. The exact client, mapping, and remapped Mojmap artifact hashes in that artifact manifest are respectively `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`, and `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`. The available campaign provenance sidecar hash is `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- 1.20.3 source manifest is embedded in `decompiled_minecraft/1.20.3/mojmap/.decompile-provenance.json` (file SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`). It identifies `1.20.3`/`mojmap` and embeds the examined source hashes. Cached client and Mojang mapping inputs are pinned by SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655` and `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`; the matching local cache pin sidecars and bytes were checked.
- Independently computed SHA-256 for relied Java bytes (each matched its side's source manifest):

| Source | 1.20.2 | 1.20.3 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |

## Independent trace

In both releases, `Player.travel(Vec3)` takes the `abilities.flying && !isPassenger()` branch. It saves the incoming vertical velocity, calls `super.travel`, restores the post-move horizontal components and saved vertical component scaled by `0.6`, calls `resetFallDistance()`, then calls `setSharedFlag(7, false)` without a ground, Elytra, or client-side condition. The flag write therefore follows the real movement in this branch.

For this ordinary local player, the first `LivingEntity.travel` gate (`isControlledByLocalInstance()`) passes. The dry/non-lava route reaches `else if (isFallFlying())`; active flag 7 plus a working chest Elytra satisfies that gate. This route applies the glide velocity math and calls `move(MoverType.SELF, getDeltaMovement())`. Its own flag-7 clear is separately gated by `onGround() && !level().isClientSide`, so it does not clear for the specified airborne local-client case. The later `Player.travel` write is the direct clear in question.

`Entity.checkFallDamage` separately adds the downward movement distance on a non-ground move. `Player.travel` then resets fall distance after `super.travel`; that reset is an intervening state operation, not the flag-clear predicate. `Player.causeFallDamage` separately returns false for `abilities.mayfly`. Fall-distance accumulation/reset and damage eligibility are not accepted as additional differences here. In 1.20.2, `checkMovementStatistics(...)` follows the branch in `Player.travel`; it is absent from the 1.20.3 `Player.travel` body inspected here. Statistic bookkeeping and any associated relocation are outside this bounded verdict.

Compared source locations: 1.20.2 `Player.travel` lines 1465–1474, `LivingEntity.travel` lines 2039–2141, `Entity.checkFallDamage` lines 1107–1124, and `Player.causeFallDamage` lines 1568–1578; 1.20.3 corresponding `Player.travel` lines 1463–1473, `LivingEntity.travel` lines 2036–2138, `Entity.checkFallDamage` lines 1108–1124, and `Player.causeFallDamage` lines 1494–1504. The version-specific line shifts do not alter the traced operation order or gates.

This verdict accepts only the stated ordinary-player glide-move/direct flag-7-clear comparison. It makes no claim that the whole `Player.travel` method, its statistic bookkeeping, or all fall-distance/damage operations are unchanged.
