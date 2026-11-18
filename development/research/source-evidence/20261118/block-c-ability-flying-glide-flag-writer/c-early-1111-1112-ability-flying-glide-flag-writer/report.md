# 1.11.1 → 1.11.2: creative flight and synchronized fall-flying flag

## Result

No source difference found in the bounded ordinary-dry local player travel path with `PlayerAbilities.flying == true`, not riding, and synchronized entity flag 7 initially true. This is a conditional starting state only; this check does not establish how it was reached. In both releases, `LivingEntity.mobTick()` runs `flyingTick()` and then virtual-dispatches `moveRelative(...)`. On the logical client `flyingTick()` does not write flag 7, because its `setFlag(7, bl)` is guarded by `!world.isClient`. The `PlayerEntity.moveRelative(...)` override sees creative flight, calls `super.moveRelative(...)` first, then restores creative-flight vertical handling and directly calls `setFlag(7, false)`. For the stipulated dry state, the inherited method enters its `isFallFlying()` branch while the synchronized bit is still true and performs that movement simulation before the player override clears the bit in the same call. The operation and ordering are identical in both releases.

This is a no-difference result for that bounded operation, not a pair-coverage or acceptance claim. Elytra activation/request handling, networking activity, NBT, and fall-distance behavior were excluded. Server travel and its flag maintenance were not treated as player client movement evidence.

## Exact source identities

Both local trees identify the exact releases and `ornithe-feather` mapping family. Their ready markers identify Feather Gen2 build.2 mappings and source-manifest digests:

- `1.11.1`: ready marker SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; source manifest `ornithe-feather.sources.sha256` SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`; provenance SHA-256 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`.
- `1.11.2`: ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; source manifest `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; provenance SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`.

For each compared Java file below, the on-disk SHA-256 matched its entry in that release's source manifest. The complete relevant contents of `LivingEntity.java` and `LocalClientPlayerEntity.java` have matching hashes across the pair. `PlayerEntity.java` has different whole-file hashes, but line comparison found only the unrelated `DataVersion` constant change (921 → 922); the `moveRelative` method is identical.

## Bounded source references and file hashes

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

| Release | Source / member | File SHA-256 |
|---|---|---|
| 1.11.1 | `net/minecraft/entity/living/LivingEntity.java`, `mobTick()` (1798–1803), `flyingTick()` (1810–1828), `moveRelative()` (1388–1439) | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.11.2 | same source and members | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.11.1 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative()` (1387–1404) | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| 1.11.2 | same source and member | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` |
| 1.11.1 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()` (632–795), including `super.mobTick()` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.11.2 | same source and member | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.11.1 | `net/minecraft/entity/living/player/PlayerAbilities.java` | `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` |
| 1.11.2 | same source | `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` |

## Coverage bound

Compared only the named local player dispatch chain, the shared creative-flight ability field, and the inherited flag-7 gliding simulation/clear order under the stated dry conditional prestate. No difference is present in this slice. This does not close other player movement areas, identify a release introduction boundary, or prove runtime parity.
