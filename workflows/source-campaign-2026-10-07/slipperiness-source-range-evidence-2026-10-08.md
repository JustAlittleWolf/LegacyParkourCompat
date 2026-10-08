# Slipperiness sampler source-family snapshot: 1.15.2–1.20.1

**Snapshot type:** focused exact-source family comparison; not a normal pair report and not a blind-discovery artifact.
**Status:** evidence frozen for independent review; no implementation change proposed in this commit.
**Task branch:** `feat/slipperiness-source-range-evidence-2026-10-08`
**Base:** current `main` at `0bfb72a0bc08726f2ee3a984203c08b5d90cf34e`.

This snapshot compares every ready exact Mojmap source version from 1.15.2 through 1.20.1 in the local movement campaign source store: 1.15.2, 1.16.1, 1.16.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, 1.19.4, and 1.20.1. It covers the campaign pair boundaries 1.15.2→1.16.5, 1.16.5→1.17.1, 1.17.1→1.18.2, 1.18.2→1.19.2, 1.19.2→1.19.3, 1.19.3→1.19.4, and 1.19.4→1.20.1, plus the additional ready 1.16.1 and 1.16.2 artifacts. It is limited to sample-position selection and its direct friction consumers.

## Evidence and interpretation

At every ready endpoint from 1.15.2 through 1.19.4, `Entity.getBlockPosBelowThatAffectsMyMovement()` constructs the position from entity X/Z and `boundingBox.minY - 0.5000001` using double arithmetic. In 1.15.2–1.19.3 the constructor is `new BlockPos(...)`; in 1.19.4 it is `BlockPos.containing(...)`. These yield the same mathematical floor of the three double coordinates. The method text and cited file hashes below were checked against each ready source tree.

At 1.20.1 the method instead returns `getOnPos(0.500001F)`. When `mainSupportingBlockPos` is present, `getOnPos(float)` uses that stored block's X/Z and normally adjusts Y from entity position; fence, wall, and fence-gate states preserve the support block position. With no stored support, it floors position X/Z and `position.y - 0.500001F`. `checkSupportingBlock` writes the support position from a thin AABB beneath the bounding box and clears it when the entity is not grounded. `CollisionGetter.findSupportingBlock` scans block collisions and selects the candidate nearest the entity position, with a position-order tie break.

The friction caller remains directly connected to the selected block position at all sampled versions. `LivingEntity.travel` obtains the position, reads `BlockState -> Block -> getFriction()`, computes the grounded horizontal multiplier as `friction * 0.91F` (otherwise `0.91F`), and supplies friction to relative movement calculation. After movement, the horizontal components are multiplied by that factor and vertical velocity by `0.98F` in the reviewed land-travel branch. Thus a changed block position can affect both friction-fed acceleration and horizontal post-move drag; the formulas themselves are outside this snapshot's equivalence claim.

### Per-version source identity and ranges

All paths below are under `build/movement-campaign-2026-10-07/ready/<version>/mojmap/net/minecraft/`. Hashes are SHA-256 of the cited source file or ready source manifest. Method ranges identify the exact sampling formula and friction consumer.

| Ready version | `Entity.java`: SHA-256; sampler | `LivingEntity.java`: SHA-256; friction consumer | `mojmap.sources.sha256` |
|---|---|---|---|
| 1.15.2 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `Entity#getBlockPosBelowThatAffectsMyMovement`, 600–602 | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `LivingEntity#travel`, 1886–1889; post-move horizontal/vertical drag at 1911 | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.1 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; sampler 612–614 | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; `travel`, 2021–2024; drag at 2039 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; sampler 627–629 | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `travel`, 2023–2026 | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; sampler 627–629 | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `travel`, 2023–2026; drag at 2041 | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 1.17.1 | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; sampler 709–711 | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; `travel`, 2112–2115; drag at 2133 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18.2 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; sampler 736–738 | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`; `travel`, 2118–2121; drag at 2139 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19.2 | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; sampler 750–752 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; `travel`, 2151–2154; drag at 2172 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; sampler 758–760 | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; `travel`, 2158–2161; drag at 2179 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`; sampler 760–762 | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; `travel`, 2089–2092; drag at 2110 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20.1 | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759`; `getBlockPosBelowThatAffectsMyMovement`, 790–792; `checkSupportingBlock`, 567–585; `getOnPos(float)`, 798–815 | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`; `travel`, 2109–2112; drag at 2129 | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |

### Additional provider and rounding evidence

- 1.15.2 `core/BlockPos.java` SHA-256 `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`; `BlockPos(double,double,double)` at line 41 delegates to `Vec3i(double,double,double)`. The double-to-integer constructor path uses `Mth.floor` for coordinates. `util/Mth.java` SHA-256 `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`.
- 1.19.4 `Entity.java` keeps the same double inputs but now calls `BlockPos.containing`; the method is the named floor-conversion API.
- Exact `Vec3i(double,double,double)` constructor checks for the old direct-sampler sources: 1.15.2 `Vec3i.java` SHA-256 `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`, lines 23–25; 1.16.1 `00cdb627ef1290aa6e3bcd43eab4162f2f1a3d8147d6b137f230cb395c0ab542`, lines 28–30; 1.16.2 and 1.16.5 `2c04952e6a5cecbc641e69ea6376af91a13ebc4d8d6f427fc5392b50a5619fc2`, lines 28–30; 1.17.1 `500d65e0156a633edbedfa74263b43a858012b8af1afbadf7c3b7ed7dc464649`, lines 28–30; and 1.18.2, 1.19.2, and 1.19.3 `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32`, lines 39–41. Each constructor calls `Mth.floor` independently for all three double coordinates.
- Exact sampler return lines by version: 1.15.2 `Entity.java:601` is `return new BlockPos(this.x, this.getBoundingBox().minY - 0.5000001, this.z);`; 1.16.1 `Entity.java:613` and 1.16.2/1.16.5 `Entity.java:628` use `this.position.x` and `this.position.z` with the same double subtraction; 1.17.1 `Entity.java:710`, 1.18.2 `Entity.java:737`, 1.19.2 `Entity.java:751`, and 1.19.3 `Entity.java:759` keep that expression; 1.19.4 `Entity.java:761` changes only the constructor call to `BlockPos.containing(...)`.
- 1.20.1 `core/BlockPos.java` SHA-256 `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc`; `containing(double,double,double)` at 81–82 explicitly floors each coordinate. `util/Mth.java` SHA-256 `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797`; `floor(double)` at 59–62 casts to int then subtracts one when the original double is less than that integer.
- 1.20.1 `world/level/CollisionGetter.java` SHA-256 `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130`; `findSupportingBlock`, 95–110, scans `BlockCollisions` in the supplied AABB, chooses minimum squared distance to entity position, then breaks exact ties by `BlockPos.compareTo`.
- The 1.20.1 `Entity#move` path calls `setOnGroundWithKnownMovement`, which reaches `checkSupportingBlock`; grounded state therefore controls whether the support position is produced or cleared. This support position is then consumed by `getOnPos(0.500001F)` for the friction and jump-factor lookups.
- Current runtime-native context is 26.2 `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `getBlockPosBelowThatAffectsMyMovement`, lines 1050–1052, also returns `getOnPos(0.500001F)`. This source is outside the requested 1.15.2–1.20.1 range and is included only to identify the native fallback used by the reviewed mod.

### Caller math excerpts

The direct callers preserve the selected `BlockPos` through the `BlockState` and block friction read. Representative exact lines are:

- 1.15.2 `LivingEntity.java:1886–1891`: get sample; read `getFriction()` into `v`; compute `w = onGround ? v * 0.91F : 0.91F`; call `moveRelative(getFrictionInfluencedSpeed(v), vec3)`; then move.
- 1.16.1 `LivingEntity.java:2021–2025`: get sample; read friction into `t`; compute `u = onGround ? t * 0.91F : 0.91F`; call `handleRelativeFrictionAndCalculateMovement(vec3, t)`.
- 1.16.2/1.16.5 `LivingEntity.java:2023–2026`: same input chain; source file hash is identical between these two ready artifacts.
- 1.17.1 `LivingEntity.java:2112–2116`, 1.18.2 `2118–2122`, 1.19.2 `2151–2155`, 1.19.3 `2158–2162`, 1.19.4 `2089–2093`, and 1.20.1 `2109–2113`: same block-state friction input and grounded multiplier, with local names changed by source evolution/remapping.
- The post-move horizontal drag lines are listed in the source table. They multiply X/Z by the previously computed grounded factor and Y by `0.98F`; this makes the sampled block position an input to both acceleration and final horizontal drag.

## Implementation coverage context (reviewed implementation tip `ad684f454d969117ae786f4c038ae573c3170a99`)

`change/v1_15_2/GroundFrictionSamplePoint.java:11-20` returns `BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ())`; `change/v1_15_2/MovementChanges.java:9` registers it. `LivingEntityMixin.java:409-420` dispatches the shared ground-friction block hook and falls back to vanilla when no behavior resolves. `ChangeResolver` filters to changes not older than the selected version and returns no changes for `CURRENT` (`impl/ChangeResolver.java:22,30-31`). The existing V1_14 handler samples `minY - 1.0` (`change/v1_14/GroundFrictionSupportCellChange.java:13-15`).

Consequently, the V1_15_2 sample hook is not selected for V1_16 or later. At the ready source versions 1.16.1, 1.16.2, 1.16.5, 1.17.1, 1.18.2, 1.19.2, 1.19.3, and 1.19.4, the vanilla source still uses the direct double-offset sampler, while these profiles use the modern native method if no other hook exists. This is a concrete bounded coverage gap in the reviewed implementation shape: a position difference is possible when flooring near a Y boundary or when a supporting block's X/Z differs from the entity's center column. This snapshot establishes the source input difference; independent review must decide whether/how to correct it. It is not an accepted finding and does not authorize code changes.

By 1.20.1 the exact source uses the support-cache/float-offset family. This gives an observed transition interval `(1.19.4, 1.20.1]`; it does not establish the exact change release within that interval. The source evidence establishes 1.14.4 `LivingEntity.java` (SHA-256 `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`) uses `minY - 1.0` and 1.15.2 uses `minY - 0.5000001`; the exact change point within `(1.14.4, 1.15.2]` also remains unknown. No unobserved patch-level boundary is inferred.

The previously accepted MCPK snapshot remains commit `78683ba65928004ce8b7b6b9371359164a68d43b`, file blob `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`, content SHA-256 `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c`. Its clean source re-review acceptance is `bdda49ac76ce8b6e136917349e8b59a61d2fabe4`. The MCPK page fetch returned HTTP 403, so live page wording/revision was not verified. Mojmap sources do not establish equivalence to unavailable Feather-derived JARs. This source-family snapshot neither changes that accepted evidence nor claims independent blind acceptance of its own conclusions.

## Review boundary

This is a static, source-hash-backed comparison. No build, test, game client, TAS, server, runtime, or Docker operation was run. No code was modified. No normal source-pair report was edited. The snapshot is committed as a separate evidence artifact so an independent reviewer can assess the bounded later-profile coverage gap.
