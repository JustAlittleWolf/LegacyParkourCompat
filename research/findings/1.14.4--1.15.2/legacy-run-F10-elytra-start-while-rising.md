# F10: 1.15.2 can start Elytra flight while the player is rising

- **1.14.4**, mojmap: `LocalPlayer.java`; line 707.
- **1.15.2**, mojmap: `LocalPlayer.java`; lines 715–718.

## Difference

When all listed gates hold while vertical velocity is nonnegative, 1.14.4 neither sends the command locally nor accepts it on the server. 1.15.2 starts fall-flying locally and the server helper accepts it because neither checks vertical direction.

The local player therefore enters the fall-flying travel branch earlier in the jump arc.

## Movement meaning

Jump-key rising edge -> local Elytra gates -> 1.15.2's immediate shared-flag update and `START_FALL_FLYING` command -> server Elytra gates -> living fall-flying travel.

The activation timing difference follows from both client and server source.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F10-elytra-start-while-rising.md).
