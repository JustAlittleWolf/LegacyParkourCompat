# F002: Gravity suppression added for synchronized no-gravity state

- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1302–1470.
- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1332–1506.

## Difference

For the default-false state, the compared movement branches retain the same gravity terms and operation order. 1.10.2 adds a generic entity flag check so those terms are skipped when `isNoGravity()` is true.

The ordinary no-gravity false path also retains the same water/lava vertical multipliers and the same 0.8 movement multiplier; 1.10.2 extracts the `0.8F` into `getBaseMovementSpeedMultiplier()`, whose only current implementation returns `0.8F`.

Its climbing clamp replaces the local `w = 0.15F` use with the identical `-0.15F` / `0.15F` literals, so that is not a separate numeric behavior change.

## Movement meaning

Local `PlayerEntity` inherits `LivingEntity.moveRelative` and generic `Entity` in 1.10.2. 1.10.2's client entity-data handler applies server-provided synced data to the entity resolved by packet ID; 1.10.2 movement then reads `NO_GRAVITY`. 1.9.4 PlayerEntity inherits no generic no-gravity flag in 1.9.4.

Source proves the client-side conditional effect and the packet/data path, but the 1.10.2 server source is required to determine whether vanilla can set `NoGravity=true` for an ordinary player.

The expected default is false; no client-side local writer was identified.

Do not infer server movement rules from this client evidence.

If 1.10.2 receives `NoGravity=true` on a player, its applicable vertical gravity additions are skipped; 1.9.4 has no corresponding player state and applies those additions.

For the default false state, these branches do not differ.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.9.4--1.10.2/findings/F002-no-gravity.md).
