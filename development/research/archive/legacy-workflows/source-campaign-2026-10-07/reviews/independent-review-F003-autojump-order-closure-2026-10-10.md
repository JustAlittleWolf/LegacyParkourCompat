# Independent blind review: F-003 auto-jump order closure

- Review date: 2026-10-10
- Reviewer checkout: `fix/review-autojump-order-closure-2026-10-10`
- Review base: `a5870a155106dbdf2a45010b267ae146c7735a8b`
- Snapshot ID: `F003-1.17.1-1.18.2-closure-a5870a1`
- Snapshot path: `1.17.1--1.18.2/followups/F-003-autojump-order-snapshot-2026-10-10.md`
- Snapshot Git blob: `5a679c822289aaa8e33f8d5d47535369f9937377`
- Snapshot raw SHA-256: `24b974df8b039fe36dc4d9b438f5c00507b2379072c0d0e6adf5acc66193c942`
- Additive memo: `1.17.1--1.18.2/followups/F-003-autojump-order-closure-2026-10-10.md`; Git blob `5597488756b3ff8add2e2ebd3377008ea0d4c374`; raw SHA-256 `650bfad8716b04406ad45f030a0231dbea8a0a1e844865bbf280dcb50c691f73`
- Source event: `F003-1.17.1-1.18.2-2026-10-10-closure-01`, recorded by commit `b7d8b3bfd6255fcdff5964f858c4af17ca2d9738`
- Verdict: **accepted**
- Pair state: prior accepted full-pair audit remains preserved; this review is finding-specific and does not change pair state.
- Runtime validation: not performed.

## Independent source verification

The snapshot and memo hashes match the stated Git blobs and raw SHA-256 values. The four exact Mojmap readiness manifests report `ready`; I recomputed each source and artifact manifest SHA-256 from `build/movement-campaign-2026-10-07/ready/<version>/` and matched the `mojmap.ready.json` values. The client identities and manifest digests match the memo. I independently hashed the cited source files in the read-only `decompiled_minecraft` publications and confirmed each hash occurs in that version's source manifest.

| Version | Client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Result |
| --- | --- | --- | --- | --- |
| 1.17.1 | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | ready; identities match |
| 1.18 | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | ready; identities match |
| 1.18.1 | `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | ready; identities match |
| 1.18.2 | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | ready; identities match |

Exact source bindings independently checked (paths are relative to each version's `mojmap` root):

| Operation | 1.17.1 source / SHA-256 | 1.18.2 source / SHA-256 | Intermediate confirmation |
| --- | --- | --- | --- |
| `LocalPlayer#updateAutoJump`, guards, first-AABB break and timer write | `net/minecraft/client/player/LocalPlayer.java` 904–1002; `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | same path 900–1014; `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | 1.18 and 1.18.1: `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `CollisionGetter#getCollisions` order | `net/minecraft/world/level/CollisionGetter.java` 57–59; `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747` | same path 67–75; `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` | 1.18 and 1.18.1: same B hash |
| Entity query filters and bounds | `net/minecraft/world/level/EntityGetter.java` 56–71; `2491753cf00c868e42b1451b255a6ed2c1a4a3c4194445f47772bb592e6cc3e9` | same path 57–75; `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` | 1.18 and 1.18.1: same B hash |
| Player collision gate; Boat eligibility and dimensions | `Entity.java` 1681–1688 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `EntityType.java` 175–177 `acea872f54bf5b2996aa7af28791b91da62f2b4bbd12c43af52a40f563e88b52`; `vehicle/Boat.java` 125–136 `70ae54899433bc5a7d3df16360732206ff1a754a9eb4ee29be6b2536f164d27d` | `Entity.java` 1666–1673 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; `EntityType.java` 178–180 `1fed7ce770fe7f1e49efefb0760f28c798510f5f947e1adba29c8d8fd6c72887`; `vehicle/Boat.java` 125–136 `4e09ba747b5ee6a6a63c9663bebc96c561013501126550fe55e506b9cce33fe4` | Entity, EntityType and Boat hashes match 1.18/1.18.1 B sources |
| Fence collision provider | `block/FenceBlock.java` 30–35 `901c348c92afeb9b6054c44377a64b3edefc9ef0bf1fb6e121893029e98532c1`; `block/CrossCollisionBlock.java` 36–53 `4a4b225bb69f07ce38c492c97e2dab6d847563d86982fd47d0ac6e9b90923c66` | `FenceBlock.java` 30–35 `b9b38034baf9829bcd80a18d1eb60aabfdd9fb9885f37c69f4126fcbdc42c174`; `CrossCollisionBlock.java` 36–53 `de1c71e81995e9d7e66e829904e7aeaf2d15dbc88d30a30c20c6dd384b5a4c79` | Provider hashes match 1.18/1.18.1 B sources |
| Timer read, `serverAiStep` copy and grounded jump consumer | `LocalPlayer.java` 618, 672–675; `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`; `LivingEntity.java` 1975, 2455–2527; `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `LocalPlayer.java` 611, 665–668; `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; `LivingEntity.java` 1981, 2461–2533; `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | 1.18.1 same LocalPlayer/EntityGetter/CollisionGetter source bytes as 1.18; 1.18.2 preserves the cited consumer path |

## Finding and dependency verdict

The paired `LocalPlayer#updateAutoJump` code has the same guards, probes, first intersecting AABB selection, candidate-clearance checks, `maxY` height use, `> 0.5` / `<= q` timer gate, and `autoJumpTime = 1` write. The changed operation is the source iterable order: 1.17.1 `CollisionGetter#getCollisions` concatenates block shapes before entity shapes; 1.18, 1.18.1 and 1.18.2 return entity shapes before block shapes. The cited `LocalPlayer`, `CollisionGetter` and `EntityGetter` source hashes are identical between 1.18 and 1.18.1, and 1.18.2 retains the same order. Thus 1.18 is the first changed stable release among the exact ready versions checked.

The entity-query dependency is closed for the witness. In 1.17.1, the candidate bounding box must intersect the query inflated by `1.0E-7` and pass the supplied predicate and `player.canCollideWith`. In 1.18+, a non-null player query uses `NO_SPECTATORS` and `player.canCollideWith`, with the same query inflation. The single witness entity is a non-spectator vanilla Boat; `Boat#canBeCollidedWith` returns true and the player-side predicate admits it. Its registered bounding box dimensions are `1.375F × 0.5625F` in both sides. With only one entity candidate, entity-internal list ordering cannot affect the result.

The block dependency is also closed: the FenceBlock collision shape always includes its central post from block-local Y `0` through `24/16 = 1.5`, including when its connection flags are false. The paired source confirms this construction and unchanged dimensions. For an explicit geometric witness, take a sprinting player centered at `(x=0.2, z=0.0)`, feet at `y=64`, moving toward negative Z; an isolated fence block at `(0,64,-1)` has a post whose collision box spans X `0.375..0.625`, Z `-0.625..-0.375`, Y `64..65.5`. One auto-jump probe line is at X `0.5`. The player has `0.075` blocks of forward clearance to the post and a normal sprint step reaches it. A Boat centered at `(x=0.5, y=64, z=-1.5)` has its box spanning X `-0.1875..1.1875`, Z `-2.1875..-0.8125`, Y `64..64.5625`. The fence and boat boxes are separated in Z, each intersects the swept query, and each intersects that probe line at Y `64.51`. Leave the candidate-above cells and the player's original clearance column empty. This avoids an incidental boat/fence collision and does not rely on entity list ordering.

Under those ordinary on-foot conditions, the first qualifying block AABB in 1.17.1 is the 1.5-high fence post. It exceeds `q=1.2`, so no timer is written before the first-candidate loop breaks. In 1.18+, the eligible Boat AABB is visited first; its rise is `0.5625`, so the timer becomes `1` before the fence is visited. On the next LocalPlayer AI step the timer is decremented and sets `input.jumping`; `serverAiStep` copies that input into the player's `jumping` state. The grounded `LivingEntity#aiStep` jump branch reaches `jumpFromGround` when its existing jump-delay gate permits. This is a concrete changed player input and movement response, not an ordering-only claim.

## Scope disposition

Accepted as in-scope **normal player auto-jump response**. The Boat contributes a vanilla entity collision AABB to the player's collision query; no Boat movement, passenger behavior, or vehicle physics is emulated or claimed. The source witness changes the player's next-tick jump input and potential jump velocity. Exact source proves the result under the stated preconditions; runtime validation remains not performed.

## Dependencies and next step

- Closed for this finding: entity query bounds and filters; player collision predicate; Boat eligibility and dimensions; fence provider and 1.5-block shape; first-candidate break; auto-jump guards, timer writer and reader; LocalPlayer-to-LivingEntity jump consumer; exact stable boundary at 1.18.
- Unrelated pair work and runtime validation remain outside this finding-specific review.
- Next: record this verdict against the exact snapshot in the source-review ledger/integration index and proceed with the separately authorized finding-specific implementation handoff. Do not change the frozen pair audit or describe runtime parity as verified.

## Review isolation and process

I read the project README and source discovery/campaign protocols, then only the assigned finding snapshot/memo/event and exact ready source publications. I did not read implementation sources or either wiki lane, change shared source artifacts, decompile, build, test, or launch a client/server/runtime. Review files are the only intended additions on this branch.
