# Honey contact scan: 1.19 release applicability follow-up

## Status and boundary

This is a bounded source-only follow-up to accepted immutable finding F-010. F-010 is bound to author snapshot commit `b66cde90c5f34970aa94553f838ec8c65e764ea6`, finding `1.19.2--1.19.3/findings/F-010-honey-contact-scan-boundary.md`, blob `46614ff4d6498f729e7dba61abdaed0e011b6bc3`, SHA-256 `c37c6544856bd4dde35e36801383fe9249044ad4c02341984a8ae8205e217039`. Its independent R3 acceptance is report commit `dded99fb6d78d4d253ab7f367edc029e981b6b91`, report blob `deec2af60b2e8c6b6ea2dd67043971c8d3c126b7`, SHA-256 `7f54077b4570180d26f0f9a4028bae7ffee1f0dd5582a82c7330a5828608ba3d`.

The accepted finding establishes the bounded stable-release change between 1.19.2 and 1.19.3. This memo checks whether the scan and player-side Honey consumer remain applicable in 1.19.4 and records the missing earlier exact sources. It does not close or complete the 1.19.2--1.19.3 pair.

## Source publications and integrity

The three available publications are exact Mojmap ready roots under `build/movement-campaign-2026-10-07/ready/<version>/`. For each version, the ready marker, source manifest, artifact manifest, provenance and diagnostics hashes were recomputed; each cited Java file hash below matches its entry in `mojmap.sources.sha256`.

| Version | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Provenance SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|---|
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e` |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef` |

All source paths below are relative to each version's `mojmap/` root. The paired file digest and manifest entry were checked for every row.

| Source file | 1.19.2 SHA-256 | 1.19.3 SHA-256 | 1.19.4 SHA-256 |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `net/minecraft/world/level/block/HoneyBlock.java` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5` | `bca533675a23fb01ef535e226e7eb780d440d46ebbfd3bf1b98654c82fd0b40a` |
| `net/minecraft/core/BlockPos.java` | `f520f3056b16c033f28db64171471aa165537fc8a1504278a24a4760b3fcf0ec` | `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a` | `6f7d806a3a3ca3204db248a7d0dba4908685224dc5a3350e2eb51f69f97d9715` |
| `net/minecraft/core/Vec3i.java` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` | `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` |
| `net/minecraft/util/Mth.java` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |

The 1.19 and 1.19.1 Mojmap ready markers/source roots are absent from the published `ready/` and `decompiled_minecraft/` trees. Their inset and applicability are therefore unknown here. Preparation need: publish exact 1.19 and 1.19.1 Mojmap sources with readiness/provenance manifests; this source worker did not regenerate them.

## Paired source comparison

### Entity contact scan

- **1.19.2**, `Entity.checkInsideBlocks`, lines 886–915: bounds are formed in order from `minX/minY/minZ + 0.001`, then `maxX/maxY/maxZ - 0.001`. The `hasChunksAt` guard precedes traversal. Iteration order is X, then Y, then Z; each cell reads its `BlockState`, calls `entityInside`, then `onInsideBlock`, with the same per-cell crash wrapping.
- **1.19.3**, lines 894–923: the same bound construction, guard, traversal and callback/error order, but each inset is `1.0E-7` on both min and max coordinates.
- **1.19.4**, lines 896–925: retains the `1.0E-7` insets and the same guard, X/Y/Z traversal, state read, callback order and error handling. It spells conversion as `BlockPos.containing(...)`; 1.19.2/1.19.3 use `new BlockPos(double, double, double)`. In 1.19.2/1.19.3 the constructor delegates through `Vec3i(double, double, double)` to `Mth.floor` for each coordinate. In 1.19.4 `BlockPos.containing` calls the same `Mth.floor` helper for each coordinate. The three `Mth.floor(double)` bodies use the same cast-and-negative-correction expression. This constructor/API spelling change does not change the floor conversion used by this scan.

`Entity.move` reaches `tryCheckInsideBlocks` at line 657 in 1.19.2 and 1.19.3 and line 659 in 1.19.4; the wrapper calls `checkInsideBlocks` in all three versions. The nearby line shift does not alter the relevant post-move contact path.

### Honey contact consumer

In all three versions, `HoneyBlock.entityInside` checks `isSlidingDown` first, then calls `maybeDoSlideAchievement`, `doSlideMovement`, and `maybeDoSlideEffects` in that order when true, followed by `super.entityInside`. The player-relevant gate is unchanged: reject on-ground entities; reject when entity Y is above `blockY + 0.9375 - 1.0E-7`; reject when vertical velocity is at least `-0.08`; then evaluate horizontal edge overlap. `doSlideMovement` scales horizontal velocity when vertical velocity is below `-0.13`, otherwise retains it, writes vertical velocity `-0.05`, and resets fall distance. The `HoneyBlock.java` full-file hash is identical in 1.19.2 and 1.19.3; 1.19.4 has an unrelated fall-damage API edit, while these contact/gate/response bodies remain the same.

Thus the changed scan band is consumed by the same Honey slide predicate and player velocity/fall-distance writer in all three available exact sources. The existence of the unchanged consumer does not extend the scan finding to other versions whose scan sources are unavailable.

## Applicability disposition

- **Verified old inset:** 1.19.2 (`0.001`).
- **Verified changed inset:** 1.19.3 and 1.19.4 (`1.0E-7`); the 1.19.4 floor-conversion spelling change preserves the same `Mth.floor` calls and scan order.
- **Unknown:** 1.19 and 1.19.1, because exact ready source and manifests are absent. Do not group them with either verified behavior until those publications are available and checked.
- **Onset bound:** the accepted immutable F-010 establishes the source difference across 1.19.2 → 1.19.3. This follow-up confirms persistence at 1.19.4; it does not claim earliest behavior for 1.19 or 1.19.1 or inspect development snapshots.

This is source applicability only. It does not claim a runtime trajectory or parity result. Pair discovery remains partial.
