# Explosion player-knockback packet producer: source-only pair slice

Pair: Minecraft 26.1.2 → 26.2, exact published `unobfuscated` sources. Scope is limited to the server-side computation/storage of an explosion player-knockback vector and its placement in `ClientboundExplodePacket`. This does not establish client trajectory, ordinary runtime behavior, or historical movement on a logical server.

## Exact source identities

Canonical source roots are `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. Actual provenance sidecars are `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` (SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`) and `decompiled_minecraft/26.2/unobfuscated.provenance.json` (SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`); each declares its exact release and `unobfuscated` namespace. The version-root `unobfuscated.ready.json` files declare ready status and bind source-manifest files `unobfuscated.sources.sha256`. Their manifest digests are `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (26.1.2) and `a7ad74fc712567eb87136afc1eaFCFD602B4088E9869F3596331FD34FAEFE894` (26.2). The actual sidecar forms and these hashes were checked.

Cited Java-file SHA-256 values match both current bytes and the corresponding source-manifest entries:

| Relative Java path | 26.1.2 | 26.2 |
| --- | --- | --- |
| `net/minecraft/world/level/ServerExplosion.java` | `967e53a1b867dccbed532773886aef52f660e30f44a7841d0ae58ffd28c60cb8` | `0ac4e90fddb6d4983b828c3a14f6fff176d151a40b777c2fe99a2fba5ab1be6f` |
| `net/minecraft/server/level/ServerLevel.java` | `e6648bb88d22f33c43272cf7434102ed98784f165ba8f1fcb21231600e5987b8` | `06c436ebb91c04f8c30b58bdf115f87da8b5d0d14d5551c6b33e228744037cb9` |
| `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `cb83a7fba6db21183955f38dce919a98ed5bc47366aedd15f00a73c3963b366a` | same |
| `net/minecraft/world/level/ExplosionDamageCalculator.java` | `f8234d261a5f0a429c51f744af0b9961eaa1705cccea5fccabdeb4c99c0f0828` | same |
| `net/minecraft/world/level/EntityBasedExplosionDamageCalculator.java` | `840ace635b3e6a168a67d27f1d383a72817e4a9b7d3cc6724052ff0e1b24851a` | same |
| `net/minecraft/world/level/SimpleExplosionDamageCalculator.java` | `add62472340846d50ca57d662f88e97d53aaabe2ae70afaf304914490a5da4e5` | same |

## Ordered producer path and bounded comparison

1. `ServerLevel.explode` creates `ServerExplosion` with the supplied `ExplosionDamageCalculator`, calls `explosion.explode()`, then iterates server players with `player.distanceToSqr(center) < 4096.0`. For each recipient it looks up that player's optional vector in `explosion.getHitPlayers()` and sends a `ClientboundExplodePacket(center, radius, blockCount, optionalVector, ...)` (26.1.2 `ServerLevel.java:1200-1232`; 26.2 `:1212-1244`). The extracted method body is text-identical between versions; only its source line range shifts.

2. `ServerExplosion.explode` calls `hurtEntities()` before block interaction/fire and returns the block count (26.1.2 / 26.2 `ServerExplosion.java:235-251`); the extracted body is text-identical. `hurtEntities` returns only when `radius < 1.0E-5F`, enumerates entities in its expanded AABB, rejects `ignoreExplosion`, and keeps `dist = sqrt(distanceToSqr(center))/(radius*2)` only when `dist <= 1.0` (both versions `ServerExplosion.java:172-212`; extracted method bodies are text-identical).

3. For each retained entity, the code chooses position for PrimedTnt or eye position otherwise, normalizes the vector from explosion center, reads the supplied calculator's damage and knockback multipliers, computes exposure, and reads `Attributes.EXPLOSION_KNOCKBACK_RESISTANCE` for living entities (zero for others). It computes `knockbackPower = (1.0 - dist) * exposure * knockbackMultiplier * (1.0 - knockbackResistance)`, scales the direction, and calls `entity.push(knockback)` on the server entity. It records that same vector in `hitPlayers` for `Player` instances that are not spectators and are not creative-flying. The order and gates match exactly in both source versions.

4. The base `ExplosionDamageCalculator.getKnockbackMultiplier` returns `1.0F` in both versions; `SimpleExplosionDamageCalculator` returns zero for any `Player` whose `abilities.flying` flag is true, regardless of creative status; otherwise it returns its configured optional multiplier or the base value. Its local variable name `creativeFlying` does not add an `isCreative()` condition. `EntityBasedExplosionDamageCalculator` does not override the knockback multiplier. These method bodies are identical across the pair. The source-wide override search found no additional `getKnockbackMultiplier` declarations beyond base and simple implementations. Values supplied by each caller/calculator, player attributes, exposure/occlusion inputs, and event callers remain outside this bounded report.

5. `ClientboundExplodePacket` contains `Optional<Vec3> playerKnockback`; its record declaration and codec are byte-identical (both files have SHA-256 `cb83a7fba6db21183955f38dce919a98ed5bc47366aedd15f00a73c3963b366a`). This report stops at packet construction. It does not re-audit the client packet handler or imply that a server-side `entity.push` executes on the local client.

## Bounded verdict and open gates

**Bounded NO_DIFF:** The inspected server-side explosion-to-player-knockback-vector calculation, storage/gating, and `ClientboundExplodePacket` field placement are source-equivalent between 26.1.2 and 26.2. No source-visible difference was found in the formula, operation order, any-flying Player calculator gate, separate spectator/creative-flying hitPlayers-recording gate, recipient-distance gate, or optional-vector construction in this slice.

This is a server producer comparison, not a claim that historical movement runs server-side. It does not close explosion callers, calculator-specific data/arguments, attribute values/modifiers, terrain exposure, packet transport/application, other response channels, or full-pair movement coverage. No artifact bytes, build, tests, game launch, product/backlog/wiki/Git history, or runtime behavior were inspected.
