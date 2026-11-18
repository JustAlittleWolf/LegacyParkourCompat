# Elytra glide routing at climbable feet: 1.16.5 → 1.17

## Scope and conclusion

Bounded source comparison: dry player movement with the default active Elytra state already set, at a ladder/vine feet block. The pre-move `LivingEntity.travel` routing and glide threshold/write match. No difference was found in this slice. This does not establish full-pair coverage or runtime parity.

Both releases route `travel` through the same ordered gates: effective-AI/local-control, slow-falling preamble, water, lava, then `isFallFlying()`, then the ordinary friction/climbable path. Thus, under the stated dry premise and active Elytra state, neither fluid gate matches and the glide branch is chosen before the ordinary climbable movement handler.

In both versions the glide branch checks `vec36.y > -0.5` and writes `fallDistance = 1.0F`; otherwise it leaves the value untouched at that point. The slow-falling preamble can first reset it to `0.0F` when initial Y velocity is nonpositive and the effect is present; the glide write then supersedes that reset when its strict threshold passes. The `m / 0.4` glide term is also unchanged. The only movement-branch flag write here clears shared flag 7 only when `onGround && !level.isClientSide`; it does not clear the active flag for an airborne client.

The ordinary, non-gliding friction route calls `handleOnClimbable`, which resets `fallDistance` to `0.0F`, clamps X/Z to ±0.15 and Y to at least -0.15; the later climbable collision/jump gate can set Y to `0.2`. `onClimbable()` still recognizes feet-block climbable tags and usable trapdoors. 1.17 adds walkable powder snow as another condition in the collision/jump gate, unrelated to the ladder/vine feet case. Since active gliding takes the earlier branch, these ordinary climbable writes are not executed for the scoped pre-move route.

Excluded: Elytra activation, post-move consequences, final tick/updateFallFlying behavior, fluids, block geometry, damage, and other movement behavior. The observation is bounded to this route and is not a finding of a version difference.

## Paired source references

- 1.16.5 Mojmap `net/minecraft/world/entity/LivingEntity.java`: `travel` entry and preamble, lines 1914–1924; glide branch and grounded flag write, 1979–2022; normal climbable route, 2022–2042 and 2062–2104; `onClimbable`, 1363–1380.
- 1.17 Mojmap `net/minecraft/world/entity/LivingEntity.java`: `travel` entry and preamble, lines 1999–2009; glide branch and grounded flag write, 2064–2107; normal climbable route, 2107–2131 and 2151–2194; `onClimbable`, 1423–1439.

## Source identity and integrity

- 1.16.5: `decompiled_minecraft/1.16.5/mojmap.provenance.json` identifies version `1.16.5`, namespace `mojmap`, exact-run mapping option `mojmap`; `mojmap.ready.json` identifies ready source count and client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. The ready marker's source-manifest SHA-256 (`9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`) matches the actual `mojmap.sources.sha256` file. Its entry for `LivingEntity.java` is `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`, matching the source file's actual SHA-256.
- 1.17: `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` records the exact `1.17` metadata URL/input hash (`.../packages/0d9ace8a2ecfd1f4c782786f4b985a499240ff12/1.17.json`, `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`), Mojang client JAR hash `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, mapping text hash `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`, and the source hash for this file `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`; the latter matches the actual source file SHA-256.
- Mapping family was Mojmap on both sides. No online mapping lookup or source generation was used.

