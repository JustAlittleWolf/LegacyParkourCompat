# Soul Speed 1.16.3 / 1.16.4 continuity check

Scope: source-only identity and the accepted Soul Speed movement block selector. No product, backlog, wiki, build, test, or runtime work.

## Exact source identities

Mapping is Mojmap in both releases. The hidden mapping-local `.decompile-provenance.json` files identify the exact version inputs and bind the focused source hashes. Provenance file SHA-256 values below were recomputed from the local files.

| Release | Client jar input SHA-256 | Client mappings (`client.txt`) input SHA-256 | Version metadata input SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.16.3 | `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` |
| 1.16.4 | `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` |

Both provenance `sources` entries match the local SHA-256 for `net/minecraft/world/entity/LivingEntity.java` (`b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`) and `net/minecraft/world/entity/Entity.java` (`f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`). The same two whole-file hashes are recorded in the 1.16.2 and 1.16.5 Mojmap source manifests; direct local hashes match those sidecar entries. Thus the focused files are byte-identical across 1.16.2–1.16.5, including both releases covered here.

## Relevant methods and continuity

Exact path root: `decompiled_minecraft/<release>/mojmap/net/minecraft/world/entity/`.

- `LivingEntity.java`, `onSoulSpeedBlock()`, lines 425–427: `return this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SOUL_SPEED_BLOCKS);`
- `LivingEntity.java`, `getBlockSpeedFactor()`, lines 430–432: same predicate, positive Soul Speed enchantment-level gate, `1.0F` result, then `super.getBlockSpeedFactor()` fallback.
- `Entity.java`, `getBlockPosBelowThatAffectsMyMovement()`, lines 627–629: same support lookup, with Y `this.getBoundingBox().minY - 0.5000001`.
- `Entity.java`, `getOnPos()`, lines 594–609 retains the `position.y - 0.2F` floor lookup. It is not used by `onSoulSpeedBlock()` in these releases.
- `Entity.java`, `getBlockSpeedFactor()`, lines 617–625 retains the native block factor lookup/fallback; `Entity.move()` lines 576–577 applies the returned factor to horizontal delta movement.

Method bodies, gates, order, return type, and fallback are identical in 1.16.3 and 1.16.4. Both match the independently accepted 1.16.2 predicate and the previously checked 1.16.5 endpoint. The 1.16.1→1.16.2 first-fix boundary therefore remains intact; no intervening Soul Speed selector change was found in either newly identity-verified release.

## Bounded verdict

Exact source identity and relevant behavior continuity are confirmed for selected profiles 1.16.3 and 1.16.4. The accepted 1.16.1→1.16.2 Soul Speed block-selector fix applies unchanged through both releases. This check adds continuity evidence only; it does not expand the original finding or claim runtime parity.
