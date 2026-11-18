# Independent verdict: 1.9.1 → 1.9.2 glide/climb alias gate

## Verdict

**Accepted, bounded no-difference:** the frozen claim that the examined client activation, travel/glide/climb, server packet-acceptance, and server flag-write slices are unchanged between exact 1.9.1 and 1.9.2 local Feather sources is supported. The proposed 1.9.1→1.9.2 boundary for this alias-gate claim is unsupported. This is not full-pair coverage and does not establish any other release boundary.

Reviewed immutable input: `report.md`, SHA-256 `0d54ffdfb2a74f4e0d3e75e1a066d56df910fdda6a0ed2176f30540418ab599a`.

## Source identity and hashes

The canonical local roots are `decompiled_minecraft/1.9.1/ornithe-feather/` and `decompiled_minecraft/1.9.2/ornithe-feather/` (Feather Gen2 mapping family). Each `.decompile-provenance.json` was read and SHA-256 checked:

- 1.9.1 manifest: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`; its exact Feather artifact input is `.../1.9.1+build.2/feather-gen2-1.9.1+build.2-mergedv2.jar`, SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; Feather metadata input SHA-256 `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`.
- 1.9.2 manifest: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; its exact Feather artifact input is `.../1.9.2+build.2/feather-gen2-1.9.2+build.2-mergedv2.jar`, SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; Feather metadata input SHA-256 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`.

For each side, actual file hashes match that side's provenance `sources` entry. Each listed full-file hash is also identical on both sides and the source bytes compare equal:

| File, relative to release root | SHA-256, both sides | Examined members/ranges |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `mobTick`, 642–710 |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `isClimbing`, 900–912; `moveRelative`, 1293–1385; travel ordering, 1697–1703; `flyingTick`, 1709–1727; `isFallFlying`, 2042–2044 |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | `handlePlayerMovementAction`, `START_FALL_FLYING`, 806–859 |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | `setFlying` / `clearFlying`, 1040–1046 |

## Independent trace

- In `LocalClientPlayerEntity.mobTick`, ordinary Elytra activation is requested only on a jump rising edge while airborne, with `velocityY < 0.0`, not already fall-flying, and not ability-flying (`mobTick` 642–710; exact test at 704). The y comparison is strict against the double literal `0.0`, evaluated before the later travel call. This client gate has no water check. It then requires the exact chest item `Items.ELYTRA` and `ElytraItem.canFly`, and sends `START_FALL_FLYING`; this block does not locally set flag 7.
- The authoritative packet handler independently accepts that request only when airborne, `velocityY < 0.0`, not already fall-flying, and not in water (`ServerPlayNetworkHandler` 846–854), followed by the same exact chest-item/usability check. On success `setFlying()` sets flag 7 true; the handler's failed first gate calls `clearFlying()`, which writes true and then false (`ServerPlayerEntity` 1040–1046). Thus dry-state acceptance is server-enforced even though the client request gate itself does not test water.
- Travel invokes `flyingTick()` immediately before `moveRelative()` (`LivingEntity` 1697–1703). For an active flag, server-side `flyingTick()` retains it only while airborne, not riding, and wearing a usable Elytra in the chest slot; its flag write is guarded by `!world.isClient` (1709–1727). The client-side updater does not write the flag. In dry ordinary movement, `moveRelative()` checks `isFallFlying()` first (1297) and enters the glide branch; only its `else` reaches the ordinary branch and `isClimbing()` clamps/fall-distance handling (1346–1385). The predicate reads the block at floored x/z and floored shape-min-y; ladder and vine return true (900–912). Therefore ladder/vine climbing does not supersede an already-active glide flag in the examined path.

## Bounds

This independent verdict covers only the adjacent 1.9.1→1.9.2 Feather pair and the four files listed above. The files were read locally and checked against their own local provenance manifests; no source generation, decompilation, product/history/wiki inspection, tests, build or launch was performed. No broader collision/contact or death behavior was reviewed. Pair-wide coverage and other exact patch boundaries remain open.