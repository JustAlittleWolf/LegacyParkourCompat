# 1.10.1 → 1.10.2 glide-over-climb alias gate

## Result

Within the assigned local-player glide/climb boundary, 1.10.1 and 1.10.2 have no source difference. With an already-set fall-flying bit, dry local travel takes the gliding branch before the ordinary climb branch. On ladder/vine feet this skips ordinary climb cleanup, including its `fallDistance = 0.0F`. The pre-move glide rule sets `fallDistance = 1.0F` only when the incoming `velocityY > -0.5`; equality does not set it. The server remains authoritative for bit 7.

This is a bounded finding for this operation only; it does not freeze the pair or establish runtime parity.

## Exact source evidence

Both sides are local decompiled Minecraft sources in the `ornithe-feather` namespace, using Feather Gen2 build.2 mappings.

- **1.10.1:** `decompiled_minecraft/1.10.1/ornithe-feather/`. Its hidden in-tree `.decompile-provenance.json` pins the client jar `338b205f…ba72`, Feather artifact `feather-gen2-1.10.1+build.2-mergedv2.jar` (`6e8ba448…9a37`), and Feather version metadata (`8648e97e…904a`). Provenance file SHA-256: `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`. The hashes listed below match its `sources` entries.
- **1.10.2:** `decompiled_minecraft/1.10.2/ornithe-feather/`, checked against the adjacent `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, and `ornithe-feather.sources.sha256`. The provenance identifies `versionId=1.10.2`, namespace `ornithe-feather`, and mapping artifacts `feather-gen2-1.10.2+build.2-mergedv2.jar` plus `feather-gen2-1.10.2+build.2.tiny`. The adjacent source manifest SHA-256 is `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; it matches the ready marker's `sourceManifestSha256`. The ready marker identifies exact version metadata `1.10.2` and client SHA-256 `7cdf7fcd…be1e9`. The current local source files' hashes match their entries in that manifest.

| Source / operation | 1.10.1 lines | 1.10.2 lines | SHA-256 on both sides |
|---|---:|---:|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, client activation request and local `falling` refresh | 730–737 | 730–737 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/entity/living/LivingEntity.java`, local movement entry, glide branch, pre-move threshold, glide collision/landing handling | 1332–1385 | 1332–1385 | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/living/LivingEntity.java`, normal movement and climb cleanup | 1385–1424 | 1385–1424 | same as above |
| `net/minecraft/entity/living/LivingEntity.java`, feet-based `isClimbing()` predicate | 928–940 | 928–940 | same as above |
| `net/minecraft/entity/living/LivingEntity.java`, server flight-bit update before travel | 1742–1773 | 1742–1773 | same as above |
| `net/minecraft/entity/living/LivingEntity.java`, `isFallFlying` bit-7 reader | 2088–2090 | 2088–2090 | same as above |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, `START_FALL_FLYING` packet gate | 845–852 | 845–852 | `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, `setFlying` / `clearFlying` flag writers | 1042–1048 | 1042–1048 | `de500c8bf8e82a3f89d3a7f7208f3eed3d47f616344fb1e72aaacfd9e331a03f` |
| `net/minecraft/entity/living/player/PlayerEntity.java`, adjacent `moveRelative` wrapper | 1391–1408 | 1391–1408 | 1.10.1 `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501`; 1.10.2 `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |

`PlayerEntity.java` is not byte-identical across the pair, but the cited wrapper lines are identical. The shared `LivingEntity.java` bytes contain both applicable travel alternatives, their ordering, and the climb predicate.

## Reachability and state authority

For a local non-riding player with a dry world, `LivingEntity.moveRelative` enters its active-flight alternative only after the local-control/logical-movement gate and the water/lava gates, then tests `isFallFlying()` (bit 7). That branch is checked before its `else` ordinary movement code.

The ordinary `isClimbing()` predicate reads the block at `(floor(x), floor(shape.minY), floor(z))`; spectators return false. Ladder and vine blocks return true. In ordinary movement, this predicate applies horizontal clamps to `[-0.15F, 0.15F]`, resets fall distance to `0.0F`, limits downward velocity to `-0.15`, and suppresses negative vertical velocity while sneaking. After movement, horizontal collision while climbing sets vertical velocity to `0.2`. None of those climb cleanup operations run in the already-active glide branch, even when the feet predicate sees a ladder or vine.

At the start of living travel, `flyingTick()` rechecks an existing bit-7 value against airborne, non-riding, and a usable chest-slot Elytra; only on the logical server does it write the resulting bit. `isFallFlying()` simply reads bit 7. The local client sends a `START_FALL_FLYING` request only under its jump/airborne/descending/not-already-flying gates and with a usable Elytra. The server handler accepts that request only if the player is not grounded, has `velocityY < 0.0`, is not already fall-flying, is not in water, and has a usable chest-slot Elytra; it then invokes the server player flag writer. Thus a local Elytra alone does not activate this path: this assignment's precondition is an already-active, server-authorized bit.

## Threshold and immediate boundary

The glide alternative evaluates `if (velocityY > -0.5)` before applying glide acceleration and before `move(...)`; only then does it assign `fallDistance = 1.0F`. Exactly `-0.5` and values below it leave the existing fall distance untouched at that point. The branch then computes glide velocity and moves. If that move reaches ground, the server clears bit 7 after movement; the contact tick has already used the glide branch. On a later travel tick with the flag cleared, ordinary movement may take the climb path and perform its cleanup if the feet still resolve to ladder/vine.

## Bounds

Compared only the paired local activation/refresh, bit-7 authority and updater, active gliding travel, ordinary climb cleanup and feet predicate, including their enclosing gates and ordering. The source confirms no 1.10.1→1.10.2 difference in this bounded operation. No runtime, build, test, decompilation, wiki, product implementation or broader pair-coverage claim is included.
