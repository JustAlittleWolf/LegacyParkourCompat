# Powder Snow climb boost: source comparison

## Finding

The first verified source change in the assigned release sequence is between 1.21.4 and 1.21.5. `LivingEntity.handleRelativeFrictionAndCalculateMovement` changes its Powder Snow eligibility gate from a current `getInBlockState()` check to the latched `wasInPowderSnow` flag. The surrounding movement-vector order does not change: relative input, climbable limiting, movement, delta-vector read, gate, local vector Y replacement with `0.2`, return. Air travel then applies gravity and friction to that returned vector before storing the next delta movement.

## Exact evidence identity

All source files cited below were SHA-256 checked against their version's `*.sources.sha256` publication manifest or the hidden `mojmap/.decompile-provenance.json` source map. The decompilation identity is the supplied Mojmap source for 1.20.6 through 1.21.11 and the published unobfuscated source for 26.2. Mojmap pins use the official client mapping input (`client.txt`) and exact client artifact in their provenance.

For the change pair, 1.21.4 Mojmap has source-manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, client JAR SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`, and `client_mappings.txt` SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`. 1.21.5 Mojmap has source-manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, client JAR SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`, and `client_mappings.txt` SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`. The source-file hashes in the table are the individual checked `LivingEntity.java` SHA-256 values.

## Predicate coverage across requested exact releases

| Release / mapping | `LivingEntity.java` predicate range | Predicate form | SHA-256 of cited source |
|---|---:|---|---|
| 1.20.6 Mojmap | 2274–2277 | `getInBlockState().is(POWDER_SNOW)` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.21 Mojmap | 2257–2260 | `getInBlockState().is(POWDER_SNOW)` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.1 Mojmap | 2257–2260 | `getInBlockState().is(POWDER_SNOW)` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.2 Mojmap | 2356–2359 | `getInBlockState().is(POWDER_SNOW)` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| 1.21.3 Mojmap | 2356–2359 | `getInBlockState().is(POWDER_SNOW)` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| 1.21.4 Mojmap | 2366–2369 | `getInBlockState().is(POWDER_SNOW)` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| 1.21.5 Mojmap | 2398–2401 | `wasInPowderSnow` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| 1.21.6 Mojmap | 2450–2453 | `wasInPowderSnow` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| 1.21.9 Mojmap | 2480–2483 | `wasInPowderSnow` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.10 Mojmap | 2480–2483 | `wasInPowderSnow` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 Mojmap | 2538–2541 | `wasInPowderSnow` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 26.2 published unobfuscated | 2680–2683 | `wasInPowderSnow` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

The sampled exact sources show the old predicate through 1.21.4 and the new predicate beginning at 1.21.5, then preserved through 26.2. The exact introduced boundary is established only for the supplied adjacent pair 1.21.4 → 1.21.5; no earlier historical interval was requested.

## Reachable player path and gates

* In 1.21.4 `PowderSnowBlock.entityInside` at `world/level/block/PowderSnowBlock.java:60–80` calls `makeStuckInBlock` for a LivingEntity only when its `getInBlockState()` is that Powder Snow block, then sets `isInPowderSnow` true. The base block inside-collision shape is `Shapes.block()` (`world/level/block/state/BlockBehaviour.java:353–355`), and `Entity` calls `entityInside` for intersected inside shapes (`world/entity/Entity.java:1059–1073`).
* In 1.21.5 `PowderSnowBlock.entityInside` at `world/level/block/PowderSnowBlock.java:62–95` preserves the same `makeStuckInBlock` gate, then applies `InsideBlockEffectType.FREEZE`. That effect sets `isInPowderSnow=true` (`world/entity/InsideBlockEffectType.java:7–12`). `LivingEntity.tick` calls `travel` before `applyEffectsFromBlocks` (`world/entity/LivingEntity.java:2763–2771`); the collector invokes inside callbacks and applies their effects at the end of that movement pass (`world/entity/Entity.java:791–803`, `1086–1098`). Base entity tick snapshots `wasInPowderSnow = isInPowderSnow` and clears the current flag (`world/entity/Entity.java:459–460`). Thus the contact recorded after one movement pass is available through `wasInPowderSnow` on the next tick.
* The 1.21.5 Powder Snow inside-collision shape is the contextual collision shape, with an empty shape replaced by `Shapes.block()` (`world/level/block/PowderSnowBlock.java:108–110`). Its ordinary collision shape is solid only for falling blocks or an entity that can walk on Powder Snow and is above the block while not descending (`:114–129`). This preserves an inside callback for an ordinary body overlap even when the contextual collision shape is empty.
* `canEntityWalkOnPowderSnow` first accepts the walkable-mob tag, otherwise requires a LivingEntity wearing leather boots (`PowderSnowBlock.java:137–142` in 1.21.5). The tag provider lists rabbit, endermite, silverfish and fox (`data/tags/EntityTypeTagsProvider.java:47`), not Player; leather boots are therefore required for the ordinary player's climb gate. Without boots, the Powder Snow branch remains false.
* The Powder Snow boost is additionally gated by `horizontalCollision || jumping` and occurs in `travelInAir`. With prior-tick Powder Snow overlap and leather boots, a player whose current air movement jumps or reports horizontal collision receives a returned vector with `y=0.2` even when its current in-block state has already moved out of Powder Snow. 1.21.4 requires the post-move current `getInBlockState()` to still be Powder Snow at this check, so that carried-contact witness does not pass the old gate.

## Player callback and vector ordering

The `Player.makeStuckInBlock` override is unchanged in behavior across the pair (`world/entity/player/Player.java:1585–1590` in 1.21.4; `:1559–1564` in 1.21.5): non-flying players delegate to `Entity.makeStuckInBlock`, while every player resets its current impulse context. The base method resets fall distance and stores the stuck-speed multiplier (`Entity.java:2553–2556` in 1.21.4; `:2547–2550` in 1.21.5). This is the direct contact callback; it does not supply the climb boost.

Both versions order the climb-boost vector path as `moveRelative` → `handleOnClimbable` → `move` → read `getDeltaMovement` → test the gate → replace only the returned vector's Y component with `0.2`. `travelInAir` then starts with the returned Y, applies gravity, and stores delta movement with the usual friction multiplier (`LivingEntity.java:2202–2224` in 1.21.4; `:2224–2246` in 1.21.5). Therefore the changed gate changes the next velocity vector after the current move; there is no changed vector operation order in this patch.

## Scope limits

This establishes the exact source predicate change and a reachable player state path. It does not claim a specific full-tick trajectory or numerical displacement, and it does not attribute any later movement difference to the entity-inside candidate enumeration refactor. No tests, builds, decompiles, runtime launches, or writes outside this lane were performed.
