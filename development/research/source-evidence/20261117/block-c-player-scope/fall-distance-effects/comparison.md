# Slow Falling and Levitation fall-distance reset comparison

Status: COMPLETED. This is a bounded source comparison, not a pair-wide movement audit. All eight requested snapshots were read from `decompiled_minecraft/<version>/mojmap`. The relevant `LivingEntity.java` body hash matched its source manifest/provenance on every version.

## Exact inputs and source identity

Mapping family: Mojmap / official Mojang client mappings. Files compared: `net/minecraft/world/entity/LivingEntity.java`, `net/minecraft/world/effect/MobEffects.java`, and for the default gravity context in 1.20.5/1.20.6, `net/minecraft/world/entity/ai/attributes/Attributes.java`.

| Release | `LivingEntity.java` SHA-256 | Source identity evidence |
|---|---|---|
| 1.19.4 | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `mojmap.ready.json` says 1.19.4/mojmap; source-manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20 | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | In-source `.decompile-provenance.json` per-file source hash matches; input `client.jar` SHA-256 `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, `client.txt` SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de` |
| 1.20.1 | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `mojmap.ready.json` says 1.20.1/mojmap; source-manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |
| 1.20.2 | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `mojmap.ready.json` says 1.20.2/mojmap; source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |
| 1.20.3 | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | In-source `.decompile-provenance.json` per-file source hash matches; input `client.jar` SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, `client.txt` SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` |
| 1.20.4 | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `mojmap.ready.json` says 1.20.4/mojmap; source-manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `mojmap.ready.json` says 1.20.5/mojmap; source-manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |
| 1.20.6 | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `mojmap.ready.json` says 1.20.6/mojmap; source-manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` |

The 1.20 and 1.20.3 folders have no top-level ready marker or Mojmap provenance file; their in-source per-file provenance and client/mapping input hashes were used instead. Exact release labels are the supplied canonical folder labels. This weaker evidence form is the only source-identity caveat in the bounded assignment.

## Difference: effect reset moved to the shared pre-travel phase

**1.19.4:** `LivingEntity.travel(Vec3)` is guarded by `isControlledByLocalInstance()` (`LivingEntity.java:1983-1990`). Its Slow Falling prologue sets the local gravity value from `0.08` to `0.01` and calls `resetFallDistance()` only when the current vertical delta is `<= 0.0` and Slow Falling is present (`1985-1990`). It runs before the water and fall-flying branch selection. Levitation resets later, inside the ordinary travel gravity calculation (`2092-2096`), after `handleRelativeFrictionAndCalculateMovement` has already called `move`; that reset is gated by Levitation and the ordinary travel branch. It is not reached through the water or elytra branch. Climbing also has its own independent reset: `handleOnClimbable` calls `resetFallDistance()` before movement (`2180-2195`, called from `handleRelativeFrictionAndCalculateMovement` at `2152-2156`).

**1.20:** `LivingEntity.aiStep()` adds a common effect check after `updateFallFlying()` and before dispatch to `travelRidden` or `travel` (`LivingEntity.java:2559-2573`): if either Slow Falling or Levitation is active, it resets fall distance (`2565-2567`). The reset is no longer in the downward Slow Falling prologue or the Levitation gravity branch. This new gate is before route selection, has no vertical-velocity predicate, and is outside `travel`'s `isControlledByLocalInstance()` guard. The old gravity-effect predicates still choose movement values inside `travel`: for Slow Falling, default `0.08` becomes `0.01` only on downward/equal velocity (`2004-2010`); Levitation still has its vertical-velocity adjustment (`2112-2116`). Neither movement-value gate controls the new reset.

### Consequences by assigned route

- **Ordinary downward travel:** Slow Falling's reset remains pre-movement for a locally controlled player, but its reset gate now also clears fall distance while rising. Levitation changes order: 1.19.4 moved first and cleared fall distance after movement; 1.20 clears it before movement, so this tick's downward displacement can add to the cleared value.
- **Water:** Slow Falling was already checked before the water branch, but only for non-positive vertical velocity. The 1.20 check also fires while rising. Levitation's 1.19.4 reset branch was bypassed by water travel; 1.20 checks it before entering the water route. Any displacement after that check can again accumulate fall distance during the same tick.
- **Climbing:** The existing climbable handler independently resets fall distance before its movement in both endpoints. The new effect reset is earlier, before route dispatch; the climb handler remains. For Levitation, the old ordinary-travel reset ran after movement when that branch was reached; the new shared reset runs before it.
- **Gliding:** Slow Falling's old local-travel reset already preceded the gliding branch when vertical velocity was non-positive; 1.20 adds the upward-velocity case and moves the check outside the travel authority guard. Levitation had no reset in the old elytra branch; the 1.20 pre-route check now clears fall distance before gliding. The gliding branch still calls `checkSlowFallDistance()`; this is a distinct clamp, not the effect reset.

A bounded reachable witness for the new Levitation coverage is a locally controlled player with Levitation and nonzero fall distance entering an existing vanilla water block while moving vertically. In 1.19.4 the water branch skips the ordinary Levitation reset; in 1.20 the pre-travel effect gate clears the value before the water branch and its movement. This claim is about the location and ordering of the fall-distance write only; it predicts no damage or full trajectory result.

## Effect and default-gravity context

Both effects are registered in 1.19.4 and remain registered in every supplied later snapshot; neither effect is introduced within this comparison window. In 1.19.4 through 1.20.4 Slow Falling uses a local baseline gravity of `0.08` and substitutes `0.01` for a non-positive vertical delta. In 1.20.5/1.20.6, `LivingEntity.getGravity()` reads the GRAVITY attribute (`LivingEntity.java:2101`), whose default value remains `0.08` (`Attributes.java:42-44`); the downward Slow Falling gate takes `Math.min(gravity, 0.01)` (`LivingEntity.java:2104-2110`). These movement-value gates persist alongside the new, direction-independent fall-distance reset. The report does not compare the rest of the travel equations.

## No-difference slices and limits

- The reset gate and its pre-route position are unchanged in the supplied 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, and 1.20.6 snapshots. Line offsets differ; the condition remains `hasEffect(SLOW_FALLING) || hasEffect(LEVITATION)` followed by one `resetFallDistance()`.
- The Slow Falling downward movement-value gate remains present throughout; its source expression changes in 1.20.5 to use the default-0.08 gravity attribute, but the fall-distance reset gate does not change.
- Climbable movement retains its independent pre-move reset in both endpoints. This comparison does not assert equivalence of the broader climb travel code.
- Effect registration confirms both effects exist in the examined window; their original introduction releases precede the assigned lower bound and were not searched.
- No fluid travel equations, broad movement equations, damage resolution, or non-player/server behavior were compared.

## Gaps

No functional-source gap remains for the assigned slices: `LivingEntity.java` hashes matched source manifests or per-source provenance for all eight versions, and the intact method bodies were inspected. The 1.20/1.20.3 source folders lack the stronger top-level ready metadata present on the other snapshots; see the source identity caveat above. No intermediate release beyond the supplied snapshots was inspected.