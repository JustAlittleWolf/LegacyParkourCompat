# F-006: Fall-flying sprint gate changes underwater drag after entering water

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 1 local sprint eligibility; stage 3 water travel
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: a non-sprinting local player enters water while fall-flying with a valid Elytra, then holds sprint underwater with sufficient forward input and food, while not using an item, blinded, mounted, affected by Depth Strider or Dolphin's Grace
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A `decompiled_minecraft/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()`, lines 698–705; SHA-256 `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`. The manual sprint-start branch has no `isFallFlying()` predicate. It permits sprinting while underwater when the forward impulse, food, item-use and blindness predicates pass.
- B `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` lines 696–698 and `canStartSprinting()` lines 1022–1029; SHA-256 `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`. `canStartSprinting()` adds `!this.isFallFlying()`, so B refuses to begin sprinting in this state.
- Both `world/entity/LivingEntity.java` files retain fall-flying when the player is airborne, not a passenger, not levitating, and wearing an enabled Elytra (`updateFallFlying()` A lines 2590–2612, B lines 2588–2610). Neither method clears the glide flag merely because the player entered water. A and B `Player.tryToStartFallFlying()` reject *starting* in water, but that does not prevent a previously active glide flag from persisting on water entry.
- Both `world/entity/LivingEntity.java` files select water travel before the fall-flying branch. A lines 2019–2049, B lines 1993–2023; file SHA-256 A `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`, B `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`. Water drag is initialized from `isSprinting() ? 0.9F : getWaterSlowDown()`; `getWaterSlowDown()` is `0.8F` in both. With no Depth Strider or Dolphin's Grace, it scales the moved X/Z velocity by this factor.
- `LivingEntity.getFluidFallingAdjustedMovement()` is otherwise the same in A lines 2174–2186 and B lines 2165–2177: when gravity applies and the player is not sprinting, it adjusts Y by the fluid/gravity term. This dependent behavior is noted but not needed for the horizontal-drag conclusion.

## Source-level difference

The local sprint decision occurs in `LocalPlayer.aiStep()` before `super.aiStep()` runs the living-entity travel tick. If the player enters water while already fall-flying, the glide update preserves the shared flag in both versions while the enabled Elytra remains equipped. Under the stated input conditions A sets sprinting; B's added fall-flying gate does not. The subsequent water branch therefore multiplies horizontal velocity by `0.9F` in A and `0.8F` in B when Depth Strider and Dolphin's Grace are absent.

## Consequence and uncertainty

For this source-reachable state, B retains less of the already-moved horizontal velocity during water travel. This finding concerns the underwater travel drag after water entry; it does not claim that a player can initiate Elytra flight underwater. No trajectory was evaluated. Exact introduction release is unknown within `(1.18.2, 1.19.4]`.

## Handoff

This closes only the fall-flying predicate's effect on this water-travel slice. General sprint-start eligibility, passenger behavior, glide physics, fluid height/flow and Depth Strider/Dolphin's Grace data closure remain separate dependencies. Runtime validation is deferred.
