# F-03: mounted sprint state can carry into player movement after dismount

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, canSprint 3163-3165; removeVehicle 1798-1804.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, removeVehicle 1784-1790.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, setSprinting 1964-1974; getFrictionInfluencedSpeed 2246-2248.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, getFrictionInfluencedSpeed 2197-2199.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 693-719; removeVehicle 172-176.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 685-701; removeVehicle 172-176.

Difference: 1.19.3 permits a local player to enter sprinting while a passenger, subject to its input, vehicle-ground, item-use, blindness and other existing guards; its food helper explicitly allows passengers. 1.19.4 routes both sprint-start paths through `canStartSprinting`, which denies passengers when the vehicle does not report `canSprint()` and local control. Existing vanilla rideable entities inherit the 1.19.4 `Entity` default of false. The added Camel override is a new-version vehicle and is outside this historical comparison. Both versions retain sprint state through `Entity#removeVehicle` and the LocalPlayer override. If 1.19.3 entered sprint while riding an existing vehicle and a dismount occurs before the next local tick, the sprint flag and speed modifier remain. With food above the normal sprint threshold, continued forward input, and a grounded post-dismount player tick whose other stop conditions are false, 1.19.3's grounded movement consumes the sprint-modified movement speed. 1.19.4 cannot enter that mounted sprint state under the same vehicle precondition, so that carry-over path is absent.

Movement meaning: LocalPlayer passenger/input gate -> `setSprinting(true)` -> `LivingEntity#setSprinting` sprint flag and movement-speed modifier -> entity/player dismount clears vehicle link without clearing sprint -> next LocalPlayer tick retains sprint while forward input and food satisfy the existing stop check -> grounded `LivingEntity` movement-speed consumer. Vehicle physics are excluded; only the player's sprint state and post-dismount movement are in scope. Source proves the eligibility and persistence differences and a reachable post-dismount grounded movement path under the stated conditions. The direct food value is a movement predicate input; food-system production remains excluded.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-03-mounted-sprint-carryover.md).
