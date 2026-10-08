# F-MOVE-TINY-POSITION-WRITE — tiny free movement updates position only in 1.21.3

- Older version A: 1.21.1, Mojmap.
- Newer version B: 1.21.3, Mojmap.
- Mechanic / coverage slice IDs: client player movement; S-ENTITY-MOVE, S-TRAVEL.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.1, 1.21.3]
- Runtime validation: not performed

## Paired evidence

Manifest artifact roots are `../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap` (A, source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, artifact manifest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`) and `../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap` (B, source manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, artifact manifest `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`).

- A `Entity.move(MoverType, Vec3)`, `net/minecraft/world/entity/Entity.java`, lines 598-649, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`: after `$$2 = this.collide($$1)`, it computes `double $$3 = $$2.lengthSqr()` and calls `setPos(...)` only under `if ($$3 > 1.0E-7)`.
- B `Entity.move(MoverType, Vec3)`, `net/minecraft/world/entity/Entity.java`, lines 619-652, SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`: after `$$3 = this.collide($$1)`, it computes `double $$4 = $$3.lengthSqr()` and calls `setPos(...)` under `if ($$4 > 1.0E-7 || $$1.lengthSqr() - $$4 < 1.0E-7)`.
- Both endpoints: `Entity.setPos(double, double, double)`, A `Entity.java` lines 388-395 and B lines 399-406, uses `setPosRaw(...)` then `setBoundingBox(makeBoundingBox())`. The file hashes are the A and B `Entity.java` hashes above.
- Player reachability on A: `LocalPlayer.tick`, `net/minecraft/client/player/LocalPlayer.java`, lines 191-210, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `Player.aiStep`, `net/minecraft/world/entity/player/Player.java`, lines 513-535, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; and `LivingEntity.aiStep`/`travel`, `net/minecraft/world/entity/LivingEntity.java`, lines 2586-2681 and 2091-2217, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`, reach `Entity.move` in ordinary land/air and fluid movement.
- Player reachability on B: `LocalPlayer.tick`, `net/minecraft/client/player/LocalPlayer.java`, lines 189-212, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`; `Player.aiStep`, `net/minecraft/world/entity/player/Player.java`, lines 543-556, SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d30996736e9c7c1340de`; and `LivingEntity.aiStep`/`travel`, `net/minecraft/world/entity/LivingEntity.java`, lines 2690-2779 and 2178-2314, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`, reach `Entity.move` in ordinary land/air and fluid movement.

## Source-level difference

For a nonzero movement result with squared length at or below `1.0E-7`, A does not write the entity's new position. If the post-edge-backoff request is collision-free, B's expression has a zero difference between requested and actual squared lengths, so its second condition is true and it writes the position even when the actual squared length is at or below `1.0E-7`. More generally, B also writes when the difference `requested.lengthSqr() - actual.lengthSqr()` is below `1.0E-7`, including some small collision-shortened movements. The literal threshold and operation order above are preserved.

## Reachability and dependencies

Both ordinary local-player tick paths reach `LivingEntity.travel` and then `Entity.move`; the requested vector is passed through stuck-speed handling, edge backoff and `collide` before the compared condition. `setPos` writes the position and recomputes the bounding box on both versions. The concrete sufficient precondition is a nonzero, unobstructed post-backoff movement whose squared length is at most `1.0E-7`: A skips `setPos`, B calls it. No external input, attribute, effect, equipment, resource or block-provider dependency is needed to establish this bounded branch difference.

## Consequence and uncertainty

Source-proven: under the sufficient precondition, B writes the small actual displacement and rebuilds the bounding box; A does not perform that write. This can change the stored position and subsequent position-based queries. The size, accumulation and observable trajectory consequence are predictions and were not runtime-tested. Other `Entity.move` differences, including support/collision state and external callers, remain open in S-ENTITY-MOVE.

## Handoff

Independent delta: 1.21.3 uses a second requested-versus-actual squared-length condition that permits small actual displacements to update position. Related slice: S-ENTITY-MOVE; the ordinary local-player path and the `setPos` consumer are evidenced above. Applicability is limited to the precise move conditions above. First changed release unknown within (1.21.1, 1.21.3]. Implementation and testing decisions are deferred.
