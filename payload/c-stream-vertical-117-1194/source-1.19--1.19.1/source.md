# Source comparison: 1.19 → 1.19.1, ordinary vertical air integration

## Authenticated inputs

Exact releases: Minecraft 1.19 and 1.19.1, Mojmap. The release-level `mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256` hashes match the supplied pins:

| Release | `ready.json` SHA-256 | `provenance.json` SHA-256 | `sources.sha256` SHA-256 |
|---|---|---|---|
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |

The `ready` and provenance records identify the respective exact versions, Mojmap namespace, mapped-source manifest digest, client artifact, and successful Vineflower generation. Every Java file cited below has a SHA-256 matching its line in that release's authenticated `mojmap.sources.sha256`.

| Release | Relative source file | Source SHA-256 |
|---|---|---|
| 1.19 | `net/minecraft/client/player/LocalPlayer.java` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.1 | `net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19 | `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` |
| 1.19.1 | `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19.1 | `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.19.1 | `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |

## Slice and call-path coverage

- `LocalPlayer.tick`: 1.19 lines 198–216; 1.19.1 lines 200–218. With the horizontal chunk loaded, both call `super.tick()`.
- `Player.tick`: 1.19 lines 229–257; 1.19.1 lines 230–258. Both call `super.tick()` after `updateIsUnderwater()`.
- `LivingEntity.tick`: both start at line 2257. Both call `super.tick()` first; at 1.19 line 2296 then calls `aiStep()` unconditionally. At 1.19.1 lines 2296–2298 call it only under `if (!this.isRemoved())`.
- `Entity.tick`/`baseTick`: both at `Entity.java` lines 403–465. `baseTick` calls `checkOutOfWorld()` at line 458 after earlier fluid-state work. `checkOutOfWorld` at lines 471–475 discards when `getY() < level.getMinBuildHeight() - 64`; `outOfWorld` at 527–529 calls `discard()` on both releases.
- Local-player virtual AI path: `LocalPlayer.isEffectiveAi()` returns `true` (1.19 line 575; 1.19.1 line 581). `LocalPlayer.aiStep()` calls `super.aiStep()` (1.19 line 887; 1.19.1 line 893); `Player.aiStep()` calls its superclass, and `LivingEntity.aiStep()` reaches `travel` (1.19 lines 2580–2586; 1.19.1 lines 2583–2589).
- Common vertical prefilter: `LivingEntity.aiStep()` applies the same `Math.abs(deltaMovement.y) < 0.003` zeroing (1.19 lines 2523–2539; 1.19.1 lines 2526–2542). The surrounding method differs only by line offsets in this slice.
- Ordinary travel: `Player.travel()` delegates to `LivingEntity.travel()` unless the excluded swimming/passenger or flying branches apply (1.19 lines 1459–1489; 1.19.1 lines 1464–1494). In `LivingEntity.travel`, the ordinary branch is 1.19 lines 2150–2178 and 1.19.1 lines 2150–2178. It computes movement via `handleRelativeFrictionAndCalculateMovement`, then applies the ordinary gravity and vertical friction in the same order: subtract `0.08` unless no-gravity, then set Y velocity to `$$29 * 0.98F` unless friction is discarded. `handleRelativeFrictionAndCalculateMovement` is identical at lines 2194–2205 and moves using the pre-gravity delta before that subtraction/drag.
- Common fall state: `Entity.move` at 547–679 and `checkFallDamage` at 991–1002 have the same in-air vertical handling: after the movement request/collision result, a negative actual Y displacement adds its `(float)` magnitude to `fallDistance`; grounded movement resets it. `resetFallDistance()` remains `fallDistance = 0.0F` at 2138–2140. No ordinary-air fall-state difference was found.
- `LocalPlayer.tick`, `Player.tick`, `Player.aiStep`, and `Player.travel` have no other vertical-air change in the examined bodies. Their cited methods retain the same substantive sequence; line offsets reflect nearby source additions.

## Difference candidate

Canonical relative finding path proposed: `development/research/findings/1.19--1.19.1/removed-entity-continues-local-ai-step.md`.

1.19, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `tick()`, lines 2257–2297; `net/minecraft/world/entity/Entity.java`, `baseTick()` lines 407–465, `checkOutOfWorld()` lines 471–475, `outOfWorld()` lines 527–529.

1.19.1, Mojmap: same files and mapped members; `LivingEntity.tick()` lines 2257–2299, with the new guard at 2296–2298. The relevant `Entity` methods retain the same out-of-world threshold and discard path.

Difference: after `super.tick()` returns, 1.19 calls `aiStep()` even if `baseTick()` just discarded the entity. 1.19.1 calls `aiStep()` only when the entity is not removed.

Movement meaning / causal witness: Let `B = level.getMinBuildHeight()`. On a dry, free-space, nonpassenger, nonswimming, non-gliding, nonflying local-player tick with Y `< B - 64` and starting vertical velocity `-0.2`, `Entity.baseTick()` discards the player on both versions. On 1.19 the in-progress local tick continues through AI/travel: the unchanged movement helper first requests Y `-0.2`; `checkFallDamage` accumulates the resulting descent; ordinary travel then changes vertical velocity to `(-0.2 - 0.08) * 0.98F`, approximately `-0.2744000053405762`. On 1.19.1 the new removed guard skips AI/travel, leaving the preexisting vertical velocity and position unchanged for that invocation. The threshold is strict: Y equal to `B - 64` does not trigger this path. This is a one-tick post-discard edge, not a difference in normal active-player gravity/drag.

## Dependencies and exclusions

The witness depends on the exact `LocalPlayer.tick → Player.tick → LivingEntity.tick → Entity.tick/baseTick → checkOutOfWorld/discard` path, the `LivingEntity.tick` post-super guard, the `LocalPlayer.aiStep → Player.aiStep → LivingEntity.aiStep → travel` virtual call path, and ordinary `Entity.move`/`checkFallDamage` behavior. It assumes a loaded horizontal chunk, dry/free space, a locally ticking nonremoved player at tick entry, no special effects or equipment movement modifiers, no swimming/passenger/gliding/flying state, and ordinary local AI travel. Collision and block queries are treated as boundaries with the requested Y displacement followed.

Excluded: jump causation/input, statuses (including Slow Falling and Levitation), equipment, angles/horizontal dynamics, fluids, climbing, blocks/support and collision providers, visual/network effects, nonplayer and vehicle movement, fall damage, health/food, and the ground-contact GameEvent attribution change (`Entity.move` around line 649; `checkFallDamage` around line 995). These do not alter the ordinary airborne gravity/fall-state comparison made here.

No source-evidence blocker remains. No build, test, game launch, source generation, or product/Git write was performed.
