# 1.10.2 → 1.11: active Elytra glide at ladder/vine feet

## Result

For an ordinary dry local player with a usable default chest-slot Elytra and the fall-flying flag already active, `LivingEntity#moveRelative` selects its glide branch before the ordinary movement branch in both releases. The branch does not call `isClimbing()`: at ladder/vine feet it therefore bypasses the ordinary climb clamps, `fallDistance = 0.0F`, sneaking descent stop, and post-move horizontal-collision `velocityY = 0.2` response. This route and the glide threshold/math are unchanged across the pair.

There is a distinct downstream movement change. In 1.10.2 the glide branch calls `Entity#move(dx,dy,dz)`, whose sneaking-player ledge guard probes one block down. In 1.11 it calls `Entity#move(MoverType.SELF,dx,dy,dz)`, whose matching guard probes `-stepHeight`; `LivingEntity` initializes that to `0.6F` in both versions. Thus when the glide move begins grounded and sneaking, the edge-support/collision path differs. With `onGround == false`, that particular guard is inactive. This is not a change to the pre-glide fall-distance or vertical-velocity writes.

## Activation and authority bounds

- The local-player request in both releases is edge-triggered jumping (`input.jumping && !bl`, with `bl` captured before `input.tick()`), airborne, `velocityY < 0.0`, not already fall-flying, and not in ability flight. It then requires a usable chest-slot Elytra and sends `START_FALL_FLYING`. 1.10.2 has an explicit null check around the stack; 1.11 uses the non-null `ItemStack.EMPTY` API. For the stated usable Elytra case the gate is the same.
- The server packet handler accepts only when not grounded, descending, not already fall-flying, and not in water, then checks the usable chest Elytra and sets flag 7. It does not check `isClimbing()`, so ladder/vine feet do not block activation. The rejected outer-predicate branch calls `clearFlying()`; that method writes flag 7 true and then false in both releases. Failure of the inner equipment test has no corresponding clear call.
- `LivingEntity#flyingTick()` runs before `moveRelative()`. It validates the active flag against airborne, non-riding state and a usable Elytra, and only writes the resulting flag on the logical server. On the server, every twentieth active flying tick can damage/break the Elytra. The local client consumes the synchronized flag and does not perform that authority write.
- Inside the glide branch, before look/velocity math or movement, strict `velocityY > -0.5` writes `fallDistance = 1.0F`; equality at `-0.5` does not. Then vertical velocity adds `-0.08 + m * 0.06`; negative descent with a nonzero horizontal look adds its correction; negative pitch adds the `o * 3.2` lift; and vertical velocity is multiplied by `0.98F` immediately before `move`. These statements and order match across the pair.
- Glide collision wall sound/damage and on-ground flag clearing in `moveRelative` are guarded by `!world.isClient`; they are server-side effects. They are not part of the local client's movement math or evidence of client damage parity.

## Exact source references

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- 1.10.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:661-665,730-735`; 1.11 same file `:673-677,742-747` — prior-jump capture, input tick and Elytra request gate.
- 1.10.2 `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java:845-857`; 1.11 same file `:835-847` — server request acceptance and flag action.
- 1.10.2 `net/minecraft/server/entity/living/player/ServerPlayerEntity.java:1042-1049`; 1.11 same file `:1046-1053` — set/clear helper ordering.
- 1.10.2 `net/minecraft/entity/living/LivingEntity.java:928-946,1332-1423,1746-1771,2088-2089`; 1.11 same file `:978-996,1386-1477,1800-1825,2143-2144` — feet-block climb predicate, glide-vs-climb routing/math, `flyingTick()` order/authority, flag getter.
- 1.10.2 `net/minecraft/entity/Entity.java:446-485` (especially `:472`, fixed `-1.0` probe); 1.11 same file `:455-510` (especially `:478-481`, SELF/PLAYER gate and `-stepHeight` probe). `LivingEntity.java:165` and `:172` respectively assign `stepHeight = 0.6F`.
- The related ordinary climb cleanup is 1.10.2 `LivingEntity.java:1406-1423` and 1.11 `:1460-1477`; it is in the non-glide branch. The player ability-flight wrapper that clears flag 7 after its own `super.moveRelative` is 1.10.2 `PlayerEntity.java:1391-1405` and 1.11 `:1385-1399`; ability flight is excluded by the stated default-player condition.

## Source identity and integrity

Exact source directories read: `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. The release IDs come from their local ready/provenance sidecars; no `latest` or other moving alias was used. Mapping namespace is `ornithe-feather` on both sides (Feather build.2 artifacts).

| Side | Ready/provenance identity | Source-manifest SHA-256 | Compared source SHA-256 (`Entity.java`; `LivingEntity.java`; `PlayerEntity.java`) |
|---|---|---|---|
| 1.10.2 | ready `versionId=1.10.2`, `versionMetadataId=1.10.2`; provenance `versionId=1.10.2`, namespace `ornithe-feather`; client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`; `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |
| 1.11 | ready and provenance `versionId=1.11`, `resolvedVersionId=1.11`, namespace `ornithe-feather`; client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`; `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`; `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |

For each listed source file, the computed file SHA-256 matched its local source-manifest entry. The computed manifest hashes match each ready marker. Local provenance-file hashes: 1.10.2 `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; 1.11 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` (also the 1.11 ready marker's declared provenance hash). The 1.10.2 local verification sidecar records 1,845/1,845 source files matching and no raw input artifact differences; it separately reports a regenerated derived remapped-jar hash difference, with the original pinned digest retained. The 1.11 provenance says its movement diagnostics review and source-pair review were not performed; the 125-warning decompiler log is not an independent method-by-method audit. The compared method bodies are present and source-hash verified, but this report does not close the full pair inventory.

## Coverage boundary

This report closes only the requested active-glide-at-feet route, its activation/server flag gates, the immediately adjacent movement operation, and the identified server-side damage/flag effects for exact 1.10.2 → 1.11. It is not full-pair coverage, an independent audit, an implementation decision, a runtime parity result, or a claim about the precise patch introducing the `MoverType`/step-height difference.
