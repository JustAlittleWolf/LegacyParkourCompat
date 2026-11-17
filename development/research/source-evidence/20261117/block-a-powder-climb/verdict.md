# Independent verdict: Powder Snow climb Y boost

## Source and input gates

- **Frozen scope:** 1.20.6, 1.21, 1.21.1–1.21.6, 1.21.9–1.21.11 Mojmap, and 26.2 unobfuscated. This is the exact requested roster; 1.21.7 and 1.21.8 were not requested or inspected.
- **Roster identity:** `source-roster.json`, SHA-256 `cd09e9efaa6535cb6956ed43eb5dc3f8a37377336103eac17f5a20413476f88b`. Root sidecars or each mapping's hidden `.decompile-provenance.json` were rehashed against it. Every source class used below was rehashed against that release's source manifest or embedded source map; results are in `input-rehash.txt`.
- **Supplied claims:** the handed-off `comparison.md` and `powder-snow-climb-boost.md` hashes both matched the parent's supplied SHA-256 values before review.
- **Roster metadata caveat:** my initial PowerShell roster serializer mishandled `sourceCount` for embedded manifests (1.21, 1.21.2, 1.21.6, 1.21.9), writing repeated `1` values. The frozen roster's version/mapping scope and provenance/ready/source-index hashes are intact. I independently counted the embedded source maps as 5,363; 5,654; 6,103; and 6,386 entries respectively. This does not affect source-file hash verification.
- **Publication gate:** versions with `*.ready.json` have matching version/mapping/source-manifest fields. Four versions rely on their in-mapping provenance/source map and have no root ready marker. The relevant files match those maps. 1.21.9 also has a sibling staging directory; no staged files were used. The 26.2 source is the published `unobfuscated` tree.

## Claim-by-claim verdicts

### 1. Predicate changes from current contact to previous-tick contact — **CONFIRMED**

The compared operation is `LivingEntity.handleRelativeFrictionAndCalculateMovement`. Its source gate is:

| Exact release / mapping | Source range | Powder Snow sub-gate | `LivingEntity.java` SHA-256 |
|---|---:|---|---|
| 1.20.6 Mojmap | 2274–2277 | `getInBlockState().is(Blocks.POWDER_SNOW)` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.21 Mojmap | 2257–2260 | current `getInBlockState()` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.1 Mojmap | 2257–2260 | current `getInBlockState()` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.2 Mojmap | 2356–2359 | current `getInBlockState()` | `087390495f4fdfd14b9e12230e7aea4fdc50913bd4d1d91119e72892881cfc52` |
| 1.21.3 Mojmap | 2356–2359 | current `getInBlockState()` | `087390495f4fdfd14b9e12230e7aea4fdc50913bd4d1d91119e72892881cfc52` |
| 1.21.4 Mojmap | 2367–2370 | current `getInBlockState()` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` |
| 1.21.5 Mojmap | 2398–2401 | `wasInPowderSnow` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| 1.21.6 Mojmap | 2450–2453 | `wasInPowderSnow` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| 1.21.9 Mojmap | 2480–2483 | `wasInPowderSnow` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.10 Mojmap | 2480–2483 | `wasInPowderSnow` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 Mojmap | 2538–2541 | `wasInPowderSnow` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 26.2 published unobfuscated | 2680–2683 | `wasInPowderSnow` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

The adjacent 1.21.4 → 1.21.5 sources establish the boundary. The rest of this roster confirms the old form through 1.21.4 and the latched form in every requested release from 1.21.5 onward. This does not establish anything about omitted patches or releases.

### 2. Jump/collision and climbable gates — **CONFIRMED, WITH WORDING CAVEAT**

The full condition remains `(horizontalCollision || jumping) && (onClimbable || powderSnowBranch)`. The Powder Snow branch is `(current-or-previous-contact) && canEntityWalkOnPowderSnow(this)`. A separate `onClimbable()` alternative remains: a ladder/vine-style climbable can trigger the same Y replacement without Powder Snow or leather boots. So leather boots are required for the **ordinary Player's Powder Snow branch under vanilla tag contents**, not for every possible Y boost.

For an ordinary local player, `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`, which extends `LivingEntity` (1.21.5 source declarations: `LocalPlayer.java:93`, `AbstractClientPlayer.java:17`, `Player.java:120`). `LocalPlayer.applyInput` copies the jump-key state to `jumping` before `LivingEntity.aiStep` proceeds to travel (`LocalPlayer.java:608–620`; `LivingEntity.java:2711–2713, 2752–2767`). Thus jump is a reachable branch input for LocalPlayer; the predicate does not itself require the player to be on ground.

### 3. Leather boots and entity-tag admission — **CONFIRMED, WITH VANILLA-DATA GATE**

`PowderSnowBlock.canEntityWalkOnPowderSnow` first accepts `EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS`; otherwise it accepts a `LivingEntity` whose feet slot contains `Items.LEATHER_BOOTS` (1.20.6 `PowderSnowBlock.java:127–131`; 1.21.5 `:137–142`; 26.2 `:144–148`). The built-in provider's tag list is rabbit, endermite, silverfish and fox, with no Player (1.20.6 `EntityTypeTagsProvider.java:45`; 1.21.5 `:47`; 26.2 `:62`). Under the vanilla built-in tag, a normal Player therefore needs leather boots for this branch. A datapack or other tag modification can add entity types and changes this gate; the source verdict does not assume such a modification.

### 4. Current vs latched state and tick timing — **CONFIRMED**

`Entity.baseTick` copies `isInPowderSnow` to `wasInPowderSnow` and then clears the current flag (1.21.5 `Entity.java:438–460`). `LocalPlayer.tick` delegates through `Player.tick` and `LivingEntity.tick`, reaching that base tick before the AI/travel pass (`LocalPlayer.java:191–195`; `Player.java:271–298`; `LivingEntity.java:2459–2462`). In `LivingEntity.aiStep`, input/jump processing precedes travel; block effects are applied after travel (`LivingEntity.java:2711–2713, 2724–2748, 2752–2771`). Powder Snow's inside effect marks the current flag at that post-travel point: in 1.21.4 `PowderSnowBlock.entityInside` directly calls `setIsInPowderSnow(true)` (`PowderSnowBlock.java:60–80`); in 1.21.5 it applies `InsideBlockEffectType.FREEZE`, whose effect calls the same setter (`PowderSnowBlock.java:82–95`; `InsideBlockEffectType.java:6–12`). The latch thus carries a prior movement pass's Powder Snow contact into the next tick. This flag-setting timing predates the predicate change; the confirmed behavioral delta is the later check reading the latch instead of current state.

At the boost check, `move` has already run. `getInBlockState()` resolves the entity's current `blockPosition`, and a changed block position invalidates the cached state (`Entity.java:3358–3364, 3440–3447`), so the old predicate is a current-position check at this point, not the previous-contact latch.

### 5. Vector operation order — **CONFIRMED UNCHANGED AT THE PATCH**

Both 1.21.4 (`LivingEntity.java:2362–2372`) and 1.21.5 (`:2394–2404`) order the path as `moveRelative` → `handleOnClimbable` → `move` → read `getDeltaMovement()` → evaluate gate → construct a vector with only Y set to `0.2` → return it. `travelInAir` then uses that returned Y, applies gravity, and stores the gravity-adjusted value with the friction multiplier (`LivingEntity.java:2224–2246` in 1.21.5). The returned intermediate Y is `0.2`; it is not the final stored vertical velocity.

### 6. Reachable ordinary LocalPlayer witness outside current Powder Snow — **SOURCE-REACHABLE; NO TRAJECTORY SIMULATION CLAIMED**

Preconditions: a non-flying LocalPlayer with leather boots; during one movement pass its body traverses/intersects Powder Snow while descending, so the block's inside effect records `isInPowderSnow=true`; it finishes that pass outside Powder Snow and off a climbable; on the next tick it is in air travel with the jump key held (or collides horizontally). In 1.21.5, the Powder Snow collision context may have an empty collision shape while descending, while `getEntityInsideCollisionShape` substitutes `Shapes.block()` for an empty shape (`PowderSnowBlock.java:107–129`); the block's `entityInside` effect can therefore record contact on a traversal that ends outside the block. The class-specific collision/inside-effect source supports this setup; this verdict does not audit the generic block-intersection scanner.

On the next tick, base tick sets `wasInPowderSnow=true` and clears `isInPowderSnow`; LocalPlayer input sets `jumping=true`; after the current move, the player's current `getInBlockState()` can be air rather than Powder Snow. With no climbable, the 1.21.4 current-state branch then fails while the 1.21.5 prior-contact branch passes and returns a vector whose Y is `0.2`. This establishes a reachable source-level divergence. It does not claim an exact full-tick position or displacement. Air travel is required; water/lava and fall-flying use other travel branches.

## Overall verdict

The proposed difference is source-confirmed for ordinary LocalPlayer applicability, subject to its Powder Snow admission gate: leather boots under vanilla built-in tags, plus either jump input or horizontal collision. The exact predicate changes between 1.21.4 and 1.21.5; state capture and post-travel contact recording explain the one-tick carry; operation order is unchanged. Preserve the caveat that the same vector boost has an independent `onClimbable()` route and that this source review is not runtime parity evidence.
