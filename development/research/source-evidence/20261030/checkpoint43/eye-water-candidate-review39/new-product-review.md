# Product adapter review: eye-water query 39

Reviewed commit: `680540d5686cc233d1c7a4109ae846d478612285` in `.native-integrations/eye-water-product40` (base `405b19c3`).

## Verdict

**Bounded behavior accepted, with one source-comment correction.** The adapter consolidates the candidate into the existing `PlayerEyeWaterQueryHook`/`PlayerMixin` producer seam, adds the 1.15.2 change, and preserves the former v1_14_2 query implementation through a direct extraction into `PlayerEyeWaterSampler`. No second interface, mixin, or fence is introduced. Static review only; no build, tests, or runtime parity claim.

## Source-fidelity checks

The exact 1.15, 1.15.1, and 1.15.2 Mojmap sources match for `Player.updateIsUnderwater()` and `Entity.isUnderLiquid(Tag<Fluid>, boolean)`. The player method uses the direct water query before inherited ticking. The entity query rejects boat passengers first; reads the current eye Y; floors X/eye-Y/Z to a block position; checks the selected chunk when its boolean gate is true; reads that fluid state; checks the requested tag; and uses a strict comparison against block Y plus contextual fluid height with `0.11111111F` added to the float height first.

The shared sampler retains each operation in the same order. `getY() + getEyeHeight()` is equivalent to the modern entity's current-eye calculation (`getEyeY()` returns position Y plus the same float eye-height field converted to double). `BlockPos.containing` floors the same coordinates as the historical double-coordinate `BlockPos` construction. `hasChunkAt(BlockPos)` in the current 26.2 `LevelReader` dispatches through block-coordinate chunk lookup (`SectionPos.blockToSectionCoord` for X/Z), which is the same floor-to-chunk mapping as historical `x >> 4` and `z >> 4`; `ClientLevel` supplies `hasChunk(int, int)` and does not override `hasChunkAt`. The sampler preserves the loaded check before the fluid lookup. It preserves the WATER tag and contextual `getHeight(level, pos)` call, and `PlayerEyeWaterMath.isSubmerged` retains the float addition, subsequent block-Y addition, and strict double `<` expression.

The passenger handling also fits the fixed historical profile: represented `Boat` vehicles return false; the existing `AbstractBoat`/non-`Boat` branch returns empty so newer unsupported boat subtypes use native behavior. That existing optional fallback remains intact.

The diff moves the existing v1_14_2 method body into `PlayerEyeWaterSampler` without changing its expression or control flow, then calls it from both version changes. The v1_15_2 class is public, has a public no-argument constructor, and is annotated `@MovementChange(emulates = V1_15_2)`. Its `PlayerEyeWaterQueryHook` interface is already used by the existing `PlayerMixin` redirect, which targets the compiled `Player.isEyeInFluid(TagKey)` call owner. The integration diff leaves that redirect unchanged.

`ChangeResolver` selects the lowest registered emulation version that is greater than or equal to the selected profile. Thus v1_13 remains selected for profiles at/before its applicable boundary, v1_14_2 remains selected when it is the closest later change, and v1_15_2 is selected for 1.15 profiles. No later implementation was added, so profiles from 1.16 onward retain native cached reads. The new class is under the scanned `change/v1_15_2/` subtree and is annotated for automatic client discovery; no manual catalog changes are needed.

## Required correction

`PlayerEyeWaterSampler`'s source comment says `1.14.2 Yarn/Feather PlayerEntity#tick/updateWaterSubmersion`. The exact 1.14.2 Yarn source calls `PlayerEntity#updateInWater()` from `tick`; `updateWaterSubmersion` is the Feather 1.14.4 name cited by the existing v1_14_2 class. Correct that comment to name 1.14.2 Yarn's actual member (and identify Feather 1.14.4 separately only if retained). This is documentation evidence accuracy; I found no source-gate, arithmetic, resolver, discovery, or fallback defect in the adapter.
