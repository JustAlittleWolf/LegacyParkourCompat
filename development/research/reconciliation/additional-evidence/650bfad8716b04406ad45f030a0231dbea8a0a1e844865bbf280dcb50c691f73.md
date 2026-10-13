# F-003 additive source follow-up: auto-jump order closure

- Date: 2026-10-10
- Pair: 1.17.1 → 1.18.2
- Finding: F-003, `T-AUTOJUMP-ORDER`
- Base snapshot: `e531086eecc778432b40b2b9ef39499b8a7f0dba`
- Prior finding snapshot: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, blob `e801da616021d5cab6a1ec6d985210803824f618`, raw SHA-256 `221aa9b6e306ded17642e6cf59afcf5c03d12f676d0bc7ded30e80cb670f77f1`
- Prior independent full-pair audit: `e0e36eff36a92be82327bf2a8140f18141d7cc21`
- Worktree branch: `feat/source-autojump-order-closure-2026-10-10`

This file supplements the accepted finding and the frozen pair audit. It does not edit either record. The added paired-source trace closes the F-003 entity-filter, candidate-geometry, timer-writer and movement-consumer dependencies, and pins the earliest changed stable source to 1.18. The source-level witness below is a derivation from vanilla source; it is not a runtime observation.

## Source publication identities

All four exact Mojmap publications were `ready`. Their `mojmap.ready.json` source and artifact manifest hashes were rechecked against the files. Source files cited below were rehashed and matched their version's `mojmap.sources.sha256` entries. Source manifests and artifact manifests are under `build/movement-campaign-2026-10-07/ready/<version>/`.

| Exact version | Client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- |
| 1.17.1 | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |
| 1.18 | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` |
| 1.18.1 | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` |
| 1.18.2 | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |

The relevant `LocalPlayer.java`, `CollisionGetter.java` and `EntityGetter.java` source hashes are identical in 1.18 and 1.18.1. Both exact releases show the newer order. No source was generated, moved or modified in the shared cache.

The exact intermediate hashes are `LocalPlayer.java` `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`, `CollisionGetter.java` `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`, and `EntityGetter.java` `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` in both 1.18 and 1.18.1. Each hash is present in its corresponding source manifest.

## Paired source closure

Evidence paths below are relative to the corresponding `ready/<version>/mojmap/` root.

| Behavior | 1.17.1 evidence / SHA-256 | 1.18.2 evidence / SHA-256 |
| --- | --- | --- |
| Player call and candidate handling | `net/minecraft/client/player/LocalPlayer.java`, `move` lines 884–898; `updateAutoJump` lines 904–1002; `canAutoJump` lines 1004–1012. SHA-256 `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | Same path, `move` lines 880–894; `updateAutoJump` lines 900–1014; `canAutoJump` lines 1016–1024. SHA-256 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| Category order | `net/minecraft/world/level/CollisionGetter.java`, `getCollisions` lines 57–59: `Stream.concat(blocks, entities)`. SHA-256 `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747` | Same path, `getCollisions` lines 67–75: return entity list first, then block iterable. SHA-256 `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` |
| Entity candidate filters and bounds | `net/minecraft/world/level/EntityGetter.java`, `getEntityCollisions` lines 56–71. It inflates the query by `1.0E-7`, requires candidate-bounds intersection, then applies the caller predicate and `player.canCollideWith(candidate)`. SHA-256 `2491753cf00c868e42b1451b255a6ed2c1a4a3c4194445f47772bb592e6cc3e9` | Same path, lines 57–75. It keeps the `1.0E-7` query inflation and candidate bounding boxes; for a non-null player it applies `NO_SPECTATORS.and(player::canCollideWith)`. SHA-256 `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` |
| Player collision predicate | `net/minecraft/world/entity/Entity.java`, `canCollideWith` / `canBeCollidedWith`, lines 1681–1688. SHA-256 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | Same path, lines 1666–1673. SHA-256 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| Concrete eligible entity | `net/minecraft/world/entity/EntityType.java`, `BOAT` registration lines 175–177: width `1.375F`, height `0.5625F`; `net/minecraft/world/entity/vehicle/Boat.java`, `canBeCollidedWith` lines 125–136 returns true. EntityType SHA-256 `acea872f54bf5b2996aa7af28791b91da62f2b4bbd12c43af52a40f563e88b52`; Boat SHA-256 `70ae54899433bc5a7d3df16360732206ff1a754a9eb4ee29be6b2536f164d27d` | Same paths and values; registration lines 178–180; Boat lines 125–136. EntityType SHA-256 `1fed7ce770fe7f1e49efefb0760f28c798510f5f947e1adba29c8d8fd6c72887`; Boat SHA-256 `4e09ba747b5ee6a6a63c9663bebc96c561013501126550fe55e506b9cce33fe4` |
| Concrete block collision height | `net/minecraft/world/level/block/FenceBlock.java`, constructor lines 27–32, passes `24.0F` (24/16 = 1.5 blocks) as collision height; `CrossCollisionBlock.java`, lines 36–53, builds the collision shape using that height. Fence SHA-256 `901c348c92afeb9b6054c44377a64b3edefc9ef0bf1fb6e121893029e98532c1`; CrossCollisionBlock SHA-256 `4a4b225bb69f07ce38c492c97e2dab6d847563d86982fd47d0ac6e9b90923c66` | Same paths and values; constructor lines 27–32 and shape builder lines 36–53. Fence SHA-256 `b9b38034baf9829bcd80a18d1eb60aabfdd9fb9885f37c69f4126fcbdc42c174`; CrossCollisionBlock SHA-256 `de1c71e81995e9d7e66e829904e7aeaf2d15dbc88d30a30c20c6dd384b5a4c79` |
| Timer-to-jump consumer | `LocalPlayer.java`, timer consumption / input write lines 672–675; `serverAiStep` assignment line 618; SHA-256 `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`. `world/entity/LivingEntity.java`, `aiStep` lines 2455–2527 and `jumpFromGround` line 1975; SHA-256 `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `LocalPlayer.java`, timer consumption / input write lines 665–668; `serverAiStep` assignment line 611; SHA-256 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`. `world/entity/LivingEntity.java`, `aiStep` lines 2461–2533 and `jumpFromGround` line 1981; SHA-256 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

`LocalPlayer#updateAutoJump` first requires `canAutoJump`: auto-jump enabled, timer `<= 0`, grounded, not staying on a ground surface, not a passenger, moving, and block jump factor `>= 1`. It creates two horizontal probe segments at `y + 0.51`, flattens each candidate shape into AABBs, accepts the first AABB intersecting either segment, stores its `maxY`, and breaks after that candidate's clearance checks. It sets `autoJumpTime = 1` only if the resulting height is greater than `0.5` and at most the jump-effect-adjusted `q` (default `1.2`). The source also checks blocks above the selected candidate and can return early if the clearance exceeds `q` or the original near-player column is obstructed. The witness assumes no Jump effect, so `q` remains `1.2F`.

For a non-null local player, both versions retain the same effective collision admission for an ordinary boat: the candidate bounding box must intersect the slightly inflated query and the player must pass `canCollideWith`; the boat returns `true` from `canBeCollidedWith`. B adds a spectator exclusion, which does not exclude this non-player vanilla candidate. The A and B collision-shape lists use the boat bounding box. Block candidate geometry in the paired iterators was already closed in the accepted full-pair audit; the fence collision provider and exact 1.5-block height were also verified here.

## Reachable source witness and movement consumer

Consider a grounded, on-foot local player on ordinary full-block ground, with auto-jump enabled, jump input released, no active timer, no Jump effect, no overhead obstruction, normal block jump factor, and forward movement into the auto-jump probe region. Arrange a vanilla boat and a fence so each candidate AABB intersects one of the two probe segments and the swept query, with the boat/fence clearances checked as above. There need be only one eligible entity candidate; entity-internal list ordering is irrelevant.

- In 1.17.1 the block stream precedes the entity stream. If the fence is the first block AABB intersecting a probe, its `maxY` is player feet plus `1.5`. That height exceeds `q = 1.2`, so the timer write is not reached; the later boat is never examined because the loop breaks on the fence.
- In 1.18 and later in this pair, the boat list precedes block collisions. Its top is player feet plus `0.5625`, inside the strict `> 0.5` and `<= 1.2` range. With the source guards and clearances satisfied, the timer is set to `1`; the later fence is never examined.
- On the next local-player `aiStep`, a positive timer is decremented and forces `input.jumping = true`. `LocalPlayer#serverAiStep` copies that input to `jumping`; `LocalPlayer#isEffectiveAi` returns true, so the enclosing `LivingEntity#aiStep` invokes that method for the local player. The grounded jump branch then calls `jumpFromGround` when `noJumpDelay == 0`. This is a direct player movement response. The order change can therefore change whether the player jumps in this ordinary-world precondition.

The block/entity layout is a source-level existence witness derived from exact vanilla geometry and filters, not a claim that a particular saved map or runtime recording has been inspected. Runtime validation remains not performed.

## Version boundary

The 1.17.1 source is block-first. Exact canonical 1.18 source is entity-first, and exact 1.18.1 source retains the same `LocalPlayer`, `CollisionGetter` and `EntityGetter` bytes as 1.18. Exact 1.18.2 source also retains the entity-first order. The first changed stable release is therefore **1.18** (Minecraft version ID `1.18`), rather than unknown within the former endpoint interval.

## Closure and disposition

- Finding evidence: source-confirmed order delta; concrete auto-jump timer and jump-consumer consequence source-confirmed under the witness preconditions.
- Closed dependencies: entity query predicate and inflated bounds; collidable vanilla Boat eligibility and dimensions; first-AABB test and early break; fence collision geometry and height; auto-jump guards, timer write/read and `jumpFromGround` consumer; first-changed stable boundary 1.18.
- Still out of scope for this follow-up: runtime/gameplay validation and unrelated full-pair work. The full-pair audit record is preserved unchanged.
- Implementation status: not assessed here. No implementation sources were opened.
