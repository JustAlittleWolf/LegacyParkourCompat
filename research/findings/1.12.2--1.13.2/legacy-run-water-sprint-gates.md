# 1.13.2 changes sprint start and cancellation conditions in water

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 719-745.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 723-758.

## Difference

The client sprint state responds differently to water.

In 1.13.2, a submerged player can start sprinting through the double-tap path while not on ground; a held sprint key cannot start sprint while in water unless the player is submerged.

Existing sprint cancellation also depends on swimming state and water contact. 1.12.2 has neither the submerged double-tap option nor the held-key water gate or swimming-specific cancellation branch.

Applicability within mixed/partial submersion depends on the computed water state and swimming flag; other sprint eligibility conditions still apply.

## Movement meaning

`LocalClientPlayerEntity.mobTick()` samples input and changes the sprint flag.

In 1.13.2, `Entity` refreshes the water flags and swimming state from fluid contact; `PlayerEntity.isSwimming()` filters for flying and spectator state.

These client player paths are reachable during ordinary water movement.

Exact collision/fluid-state production and timing interactions continue in stages 2-5.

This source difference does not imply altered block states.

Source proves the predicates and writes to sprint/swimming flags differ.

The predicted consequence is a different sprint flag, which feeds later movement-speed selection and underwater swim movement.

This finding does not quantify displacement.

The server may synchronize abilities and movement state; server behavior is outside the client-only conclusion.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-sprint-gates.md).
