# Independent review: slipperiness sample-position source range

**Verdict: ACCEPT — source evidence only, bounded to the exact snapshot and ready versions below.**

This accepts the snapshot's source-backed claims about the sampled position, the block-friction consumer, and the observed transition between the two source families. It does not accept or verify the snapshot's embedded implementation-coverage discussion, propose a code change, establish an exact patch-level introduction, or establish full-pair discovery coverage.

## Immutable snapshot binding

- Snapshot commit: `5688e68528861c91031a021f484f80a90c887f2f`.
- Snapshot path at that commit: `workflows/source-campaign-2026-10-07/slipperiness-source-range-evidence-2026-10-08.md`.
- Git blob: `17f0906b1fa7d5e6c7995835786fb9516fc5a6d7`.
- Raw file SHA-256: `dd7d640f7fb206eb5f69bf73bb5b80d929889d03c7e94a9c8a9918efb8357c8e`.
- Reviewed ready-source store: `build/movement-campaign-2026-10-07/ready/<version>/mojmap/`.

I checked every `Entity.java` and `LivingEntity.java` SHA-256 in the snapshot's ten-row source table, as well as each corresponding `mojmap.sources.sha256`; all matched the cited values in the ready-source store. The cited helper files for double-to-block flooring and the 1.20.1 support query also matched. The per-version method ranges below were read from those files.

## Endpoint coverage

In each row, `sampler` is the exact `Entity#getBlockPosBelowThatAffectsMyMovement` body, `friction` is the `LivingEntity#travel` block-state read and grounded multiplier, `drag` is the post-move X/Z multiplier, and `Player.travel → super` verifies that player travel delegates to the `LivingEntity` implementation. `Entity.java`, `LivingEntity.java`, and manifest hashes are the exact values in the bound snapshot table; `Player.java` hashes are independently recorded here.

| Ready version | Sampler range | Friction range | X/Z drag | Player delegation range; `Player.java` SHA-256 |
|---|---:|---:|---:|---|
| 1.15.2 | `Entity.java:600–602` | `LivingEntity.java:1886–1891` | `LivingEntity.java:1911` | `Player.java:1448–1478`; `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16.1 | `Entity.java:612–614` | `LivingEntity.java:2021–2025` | `LivingEntity.java:2039` | `Player.java:1380–1410`; `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| 1.16.2 | `Entity.java:627–629` | `LivingEntity.java:2023–2026` | `LivingEntity.java:2041` | `Player.java:1387–1417`; `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.16.5 | `Entity.java:627–629` | `LivingEntity.java:2023–2026` | `LivingEntity.java:2041` | `Player.java:1387–1417`; `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.17.1 | `Entity.java:709–711` | `LivingEntity.java:2112–2116` | `LivingEntity.java:2133` | `Player.java:1409–1439`; `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| 1.18.2 | `Entity.java:736–738` | `LivingEntity.java:2118–2122` | `LivingEntity.java:2139` | `Player.java:1422–1452`; `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| 1.19.2 | `Entity.java:750–752` | `LivingEntity.java:2151–2155` | `LivingEntity.java:2172` | `Player.java:1464–1494`; `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| 1.19.3 | `Entity.java:758–760` | `LivingEntity.java:2158–2162` | `LivingEntity.java:2179` | `Player.java:1448–1478`; `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| 1.19.4 | `Entity.java:760–762` | `LivingEntity.java:2089–2093` | `LivingEntity.java:2110` | `Player.java:1440–1467`; `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| 1.20.1 | `Entity.java:790–792` | `LivingEntity.java:2109–2112` | `LivingEntity.java:2129` | `Player.java:1446–1473`; `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |

Across every listed `Player.travel` body, both the ability-flight branch and its `else` branch call `super.travel`; each `Player` declaration directly extends `LivingEntity` (`Player.java` class declaration lines 110–118 across these versions). The source therefore reaches the shared living-entity travel method. The land-travel branch reads the state at the returned position, gets its block friction, and computes `onGround ? friction * 0.91F : 0.91F`. The 1.15.2 branch passes friction to `getFrictionInfluencedSpeed`; the later branches pass it to `handleRelativeFrictionAndCalculateMovement`. The resulting velocity is multiplied by the grounded factor for X/Z after movement (unless that branch discards friction). This is enough to establish a reachable player movement consumer for the selected position; this review makes no parity or runtime claim about the downstream formula.

## Position conversion and 1.20.1 support dependency

For 1.15.2 through 1.19.3, the sampler passes X, `boundingBox.minY - 0.5000001` and Z as doubles to `BlockPos(double, double, double)` / `Vec3i(double, double, double)`. The constructors floor each double independently with `Mth.floor`. The checked constructor ranges are `1.15.2 Vec3i.java:23–25`, `1.16.1/1.16.2/1.16.5:28–30`, `1.17.1:28–30`, and `1.18.2:39–41`; the relevant `Vec3i` file hashes are recorded in the snapshot. The `Mth.floor(double)` body has the same cast-then-correct-negative-fraction operation in every ready version: 1.15.2 `Mth.java:50–53`; 1.16.1/1.16.2/1.16.5 `:50–53`; 1.17.1 `:64–67`; 1.18.2 `:64–67`; 1.19.2 `:65–68`; 1.19.3 `:70–73`; and 1.19.4/1.20.1 `:59–62`. Hashes were checked in each ready tree. In 1.19.4, `BlockPos.containing(double,double,double)` at `BlockPos.java:74–76` performs those three floors directly.

The 1.20.1 sampler changes to `getOnPos(0.500001F)` (`Entity.java:790–792`). The consumer returns the cached `mainSupportingBlockPos` with its X/Z when present and derives Y from `floor(position.y - offset)`, except that fence, wall, and fence-gate states preserve the support position (`Entity.java:798–808`). With no cached support, it floors position X/Z and `position.y - offset` (`:809–814`). The `F` literal is a `float` promoted for subtraction from the double position coordinate; its exact float value is `0.500001013278961181640625`, whereas the old `0.5000001` is a double literal. The inputs therefore differ by about `9.13279e-7` and can floor to different Y positions near an integer boundary.

The producer path is present in the same exact source: `Entity.move` calls `setOnGroundWithKnownMovement` after collision resolution (`Entity.java:629–643`); that setter calls `checkSupportingBlock` (`:553–560`). When grounded, the support check queries a thin AABB immediately below the bounding box and stores the result; when not grounded, it clears the cached position (`:567–585`). `CollisionGetter.findSupportingBlock` scans `BlockCollisions` over that AABB and chooses the position nearest to entity position, breaking exact-distance ties with `BlockPos.compareTo` (`CollisionGetter.java:95–110`).

The 1.20.1 `Entity.java` SHA-256 is `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759`; the `CollisionGetter.java` SHA-256 is `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130`. The 1.19.4 `BlockPos.containing` source hash is `6f7d806a3a3ca3204db248a7d0dba4908685224dc5a3350e2eb51f69f97d9715`. The 1.20.1 `BlockPos.containing` and `Mth.floor` hashes are the snapshot's cited `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` and `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797`.

## Source conclusion and limits

The evidence supports two distinct observed source families: direct double-offset sampling at every listed endpoint from 1.15.2 through 1.19.4, and support-cache / float-offset sampling at 1.20.1. It also supports the earlier comparison cited in the snapshot: 1.14.4 `LivingEntity.java:1837–1839` reads friction at a block position built with `boundingBox.minY - 1.0` (the cited file hash matches). The exact first change between 1.14.4 and 1.15.2 is unknown. The exact change within `(1.19.4, 1.20.1]` is likewise unknown; neither interval licenses a patch-level claim. Unlisted releases, including other 1.15.x patches, were not inspected in this review.

The 1.20.1 position-selection change is a real source difference on the friction input path. Its concrete effect depends on player position, bounding-box minimum Y, support-cache state, and block states at the selected positions; exact movement outcomes are not asserted here. The block-state friction consumer is in player travel scope, while this source comparison alone does not prove a mod implementation binding, selection policy, or runtime parity.

The snapshot's MCPK history says the page request returned HTTP 403 and states that Mojmap evidence cannot establish equivalence to unavailable Feather-derived jars. I did not fetch MCPK, inspect Feather-derived jars, or independently reverify that 403/provenance history; those limitations remain unchanged and unadjudicated by this source review.

No implementation source, tests, builds, clients, TAS, servers, Gym or Docker were inspected or run for this review. No Minecraft source tree or code was changed. This is a bounded sample-position source review, not a full-pair discovery audit or runtime validation.
