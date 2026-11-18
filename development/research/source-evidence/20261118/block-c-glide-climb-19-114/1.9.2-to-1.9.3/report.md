# Elytra glide versus ladder/vine climb: 1.9.2 → 1.9.3

## Scope and verdict

Exact adjacent releases: Minecraft **1.9.2 → 1.9.3** (not 1.19.x), using the local `ornithe-feather` decompilations. Bounded state: a sole locally controlled player in dry air, not riding, with chest-slot Elytra already active (`getFlag(7)` true), and feet at a ladder or vine block. I traced activation/flag authority to establish that precondition, then compared the first movement dispatch, the glide and ordinary climb branches, and the feet-position predicate for both contacts.

**Verdict for this slice: no 1.9.2 → 1.9.3 movement delta found.** The extracted `LivingEntity.moveRelative(float,float)`, `flyingTick()`, `isClimbing()` and `isFallFlying()` bodies are exactly equal as decompiled text on both sides (their isolated-body SHA-256 values are equal below). The applicable strict vertical threshold, `fallDistance = 1.0F` write, branch precedence, ordinary-climb cleanup, flag validation and player start request are unchanged. This is a bounded no-difference report, not full pair coverage or evidence for intermediate patch boundaries.

## Ordered path and gates

- **Local start request / already-active state.** `LocalClientPlayerEntity.serverTickAi()` reaches the Elytra request gate at 1.9.2 lines 704–709 and 1.9.3 lines 707–712. Its predicate is `input.jumping && !bl && !onGround && velocityY < 0.0 && !isFallFlying() && !abilities.flying`; then it checks chest equipment for `Items.ELYTRA` plus `ElytraItem.canFly` and sends `START_FALL_FLYING`. The local method sends a request; it does not set flag 7 here.
- **Flag authority and upkeep.** The server handler at 1.9.2 and 1.9.3 lines 846–854 accepts that request only when not grounded, descending, not already fall-flying, not in water, and wearing a usable chest Elytra; it calls `setFlying()`. `ServerPlayerEntity.setFlying()` sets flag 7 true (1.9.2 line 1041; 1.9.3 line 1045). In `LivingEntity.mobTick()`, `flyingTick()` precedes `moveRelative()` on both sides (1.9.2 lines 1701–1702; 1.9.3 lines 1710–1711). `flyingTick()` validates the current flag against airborne/not-riding and usable chest Elytra, but writes the validated flag only under `!world.isClient`; this makes the local client's already-active replicated flag the movement dispatch input rather than a locally revalidated/write-through state.
- **First movement dispatch.** `LivingEntity.moveRelative()` first gates on locally controlled or logical-side movement, then excludes water and lava for this dry case. It tests `isFallFlying()` before the ordinary branch. With flag 7 true, it enters the glide branch; it does not run `isClimbing()` or ordinary climb cleanup before that branch's first `move` call.
- **Strict Y gate before movement.** At 1.9.2 lines 1297–1332 and 1.9.3 lines 1306–1341, the glide branch begins: only when `velocityY > -0.5` (strict greater-than) it writes `fallDistance = 1.0F`. This occurs before look-vector math, velocity updates, drag and the first `move(this.velocityX, this.velocityY, this.velocityZ)`. At exactly `-0.5`, that assignment is skipped. The branch then retains the same glide calculations and client/server collision gates on both releases. After the first `move`, horizontal-collision damage and grounded flag clearing are both gated by `!world.isClient` (1.9.2 lines 1333–1345; 1.9.3 lines 1342–1354). Thus the local client does not clear its active flag on this post-move ground contact; the server performs that update.
- **Ordinary climb comparison.** If flag 7 is false, the ordinary dry branch checks `isClimbing()` before movement. At 1.9.2 lines 1367–1385 and 1.9.3 lines 1376–1394 it clamps X/Z velocity to `[-0.15F, 0.15F]`, resets `fallDistance` to `0.0F`, caps descent at `-0.15`, and zeroes descent for sneaking players. It then calls `move`; only afterwards, if horizontal collision and still climbing, it sets Y velocity to `0.2`. Gravity and `0.98F` Y drag follow. This cleanup is bypassed by the active glide branch in both versions.
- **A/B feet contacts.** `isClimbing()` reads the block at `(floor(x), floor(getShape().minY), floor(z))`, returns false for spectators, and accepts either `Blocks.LADDER` or `Blocks.VINE` (or its separate qualifying trapdoor case). Thus the ladder and vine cases are both feet-position contacts at this exact block lookup, with no difference between the releases. The glide branch does not consult this predicate; the ordinary branch does.

## Exact source evidence

All references are under `decompiled_minecraft/<version>/ornithe-feather/`.

| Operation | 1.9.2 | 1.9.3 |
| --- | --- | --- |
| Local start request | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:704–709` | `.../LocalClientPlayerEntity.java:707–712` |
| Server request gate | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java:846–854` | same path, `:846–854` |
| Server flag activation | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java:1040–1046` | same path, `:1044–1050` |
| Tick flag upkeep / order | `net/minecraft/entity/living/LivingEntity.java:1636–1728` | same path, `:1645–1737` |
| Glide and climb movement | `net/minecraft/entity/living/LivingEntity.java:1293–1460` | same path, `:1302–1469` |
| Feet-contact predicate | `net/minecraft/entity/living/LivingEntity.java:900–923` | same path, `:905–928` |
| Flag getter | `net/minecraft/entity/living/LivingEntity.java:2042–2044` | same path, `:2052–2054` |

The full class-byte hashes for each file read are listed in provenance below. For the four movement bodies compared exactly, the isolated-body hashes are:

| `LivingEntity` body | 1.9.2 and 1.9.3 SHA-256 (same on both) |
| --- | --- |
| `moveRelative(float sideways, float forwards)` | `8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd` |
| `flyingTick()` | `a6b70cf51e2a4f3e4bd14ce0189793795ad590a73a4bdacc4ce0da11992f1fe0` |
| `isClimbing()` | `b3cb19f4c2d27c81dcaecd98b04d7e2243b0bd3d2ad62f744d6620ff85a2327a` |
| `isFallFlying()` | `7a9851fa3cd3041eb7d58523b5176a962aca1985630ca487ee25b79db8f84a39` |

## Local provenance and hashes

The canonical local source roots are exactly:

- `decompiled_minecraft/1.9.2/ornithe-feather/`
- `decompiled_minecraft/1.9.3/ornithe-feather/`

Each root has `.decompile-provenance.json`. I computed its SHA-256 and rehashed every source file listed here; all actual file hashes equal the corresponding manifest `sources` values. Mapping identity is Ornithe Feather Gen2 build.2 for the exact release. The manifest records Vineflower 1.12.0 and the local decompiler runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`.

| Exact side | Provenance manifest SHA-256 | Mojang client input SHA-256 | Feather mapping artifact SHA-256 | Feather version metadata SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` | `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985` |

SHA-256 of source files actually read (manifest match confirmed):

| Relative source path | 1.9.2 | 1.9.3 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| `net/minecraft/item/ElytraItem.java` | `880009469dda5e3d81716c45a0508fd0d0c2260f141e572fe9e6a080bcccd95a` | `20db6fd5b7438c4957c19565e593d79497a4712d9ce71fd5af0a9a4a61ad8cf2` |

This report covers the specified ordinary dry, already-active Elytra glide versus ladder/vine climb branch only. It does not assert whole-pair coverage, runtime parity, or behavior outside this precondition.