# Independent source audit: Elytra near-vertical look slice

## Verdict

**Accepted for the bounded claim:** the supplied report correctly finds no 1.9.1-to-1.9.2 source difference in the ordinary dry, already-active Elytra movement, look-vector construction, or trigonometric lookup slice. This verdict does not accept or close full-pair movement coverage and makes no runtime-parity claim.

## Bound inputs

- Claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-191-192-near-vertical-look-alias/report.md`
- Claim SHA-256: `3bc17d57ddb47563fcda86fe19c97152edeabc5094eefd751f8d905234eb71c2`
- Read-only local Feather trees: `decompiled_minecraft/1.9.1/ornithe-feather/` and `decompiled_minecraft/1.9.2/ornithe-feather/`.
- Provenance manifest SHA-256: 1.9.1 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`; 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- Manifest inputs independently inspected: Feather Gen2 `1.9.1+build.2` artifact `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`, release metadata `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`; Feather Gen2 `1.9.2+build.2` artifact `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`, release metadata `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`. The mapping file itself has no separate manifest identity; no online mapping/source lookup was used.

## Independent trace and evidence

The three files named in the claim are byte-identical in both trees, and their current byte hashes agree with the manifests:

| Source path below each mapping root | SHA-256, both releases |
| --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| `net/minecraft/util/math/Vec3d.java` | `9c1c6438c2c7f87d78d12d80cc58a6c03644eabf51edf8d8b56bcf61b32afe1a` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aeaad183e1e9c2d13f8fe40cca0139eb` |

- Reachable first call: `LivingEntity.java`, line 1702 calls `moveRelative(...)`. For a local client player, `LocalClientPlayerEntity.java` lines 444–446 overrides `isLocallyControlled()` to return `true`; the first gate at `LivingEntity.java` lines 1293–1297 therefore admits the local player before the water/lava predicates and active `isFallFlying()` flag. The dry, active preconditions are properly bounded by the claim. These files and the cited bodies are byte-identical across the pair.
- Glide computation: `LivingEntity.java` lines 1302–1327 use `getLookVector()`, then calculate `i` from the current pitch, `j = sqrt(x*x + z*z)`, and `k` from horizontal velocity. The negative-pitch branch at lines 1317–1322 divides by `j` without a `j > 0` guard; the later alignment alone has that guard at 1324. This operation order and the ungated divisions match exactly on both sides.
- Look construction: `LivingEntity.java` lines 1808–1809 requests `getRotationVector(1.0F)`. `Entity.java` lines 1110–1125 selects current pitch/yaw at that tick delta and constructs the vector using the same float casts and `MathHelper` calls in both releases.
- Magnitude/casts: `Vec3d.java` line 90 computes length through `MathHelper.sqrt(double)`; that overload casts `Math.sqrt` back to `float` (`MathHelper.java` lines 27–29), then the result is widened when used by glide math. Horizontal `j` instead uses `Math.sqrt` directly and is a `double`. Both files are byte-identical across the pair.
- Trigonometric lookup: `MathHelper.java` lines 15–20 use `(int)(x * 10430.378F)` (cosine adds `16384.0F`) then mask with `65535`; lines 422–425 fill the float table from `Math.sin(i * Math.PI * 2.0 / 65536.0)`. The lookup casts, truncation, mask and table construction match exactly.

The local player `PlayerEntity.java` file is not byte-identical: its only line difference is `writeCustomNbt`, line 714, which writes `DataVersion` 175 versus 176. That persistence value does not affect the traced movement path. The supplied report only asserts byte identity for `LivingEntity.java`, `Entity.java`, and `MathHelper.java`, so this does not contradict its claim.

## Scope and limits

This audit covers the ordinary dry local-client, already-active Elytra branch; the first movement call/gate; current camera look construction; casts, trig-table lookup, vector length and horizontal magnitude; and the exact-pair source-difference claim. It excludes activation and its prerequisites, malformed/non-finite inputs, end-of-glide behavior, other travel branches, all remaining movement inventory, and runtime A/B testing. The pair remains unfrozen.

