# F-FALLFLY-EQUIPMENT-COMPONENT — component-based glide eligibility

- **1.21.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.1--1.21.3/findings/F-FALLFLY-EQUIPMENT-COMPONENT.md).
- **1.21.3**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.1--1.21.3/findings/F-FALLFLY-EQUIPMENT-COMPONENT.md).

## Difference

The ordinary built-in Elytra gate has matching grounds, water, levitation and durability limits at these endpoints. 1.21.3 additionally lets an equipped stack pass the flight-start gate when its synchronized item state carries GLIDER and EQUIPPABLE components naming the current slot; 1.21.1's source accepts only the Elytra item in CHEST. 1.21.3's built-in Elytra is registered with those components, while the 1.21.3 source API can also admit another server-supplied item/component combination.

## Movement meaning

Serverbound START_FALL_FLYING -> `Player.tryToStartFallFlying` -> `canGlide` -> equipment component/slot/durability predicate -> shared fall-flying flag -> 1.21.3 travel's fall-flying branch. 1.21.1's command reaches its item-ID/chest-slot predicate.

The component and equipment data are synchronized item state; this finding does not infer custom server data or its default production beyond the 1.21.3-side Java registration cited above.

Source-proven: 1.21.3 admits a component-qualified matching-slot stack that 1.21.1's Elytra-ID/chest-only condition rejects.

The additional case requires 1.21.3-side equipment data with no 1.21.1-side `GLIDER` component representation, so it is an explicit modern-only/out-of-scope disposition.

No different trajectory for the ordinary built-in Elytra is established.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.1--1.21.3/findings/F-FALLFLY-EQUIPMENT-COMPONENT.md).
