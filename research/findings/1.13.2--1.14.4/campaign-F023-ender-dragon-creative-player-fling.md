# F023: Ender Dragon wing fling excludes creative players in 1.14.4

- **1.13.2**, ornithe-feather: `EnderDragonEntity.java`; lines 304-305.
- **1.14.4**, ornithe-feather: `EnderDragonEntity.java`; lines 293-300.

## Difference

1.13.2's two-argument wing query resolves through `EntityView.getEntities(exclude,bounds)`, whose default passes `EntityFilter.NOT_SPECTATOR`.

That predicate rejects spectator players but allows non-spectator creative players. 1.14.4 passes `EntityFilter.NOT_SPECTATOR_NOT_CREATIVE` explicitly to each wing query.

Its player branch requires both `!isSpectator()` and `!isCreative()`, so a creative player is removed before `flingMobs` receives the list.

The paired `flingMobs` writer is the same: for each living entity it computes the x/z offset from the dragon body center, computes `h = f*f + g*g`, and calls `addVelocity(f/h*4.0, 0.2F, g/h*4.0)`.

The separate damage condition follows the impulse in both versions and is excluded from this movement finding.

## Movement meaning

The wing query runs server-side when `damagedTimer == 0`.

If a non-spectator creative player intersects the expanded right- or left-wing query bounds, 1.13.2 includes that player and applies the direct velocity impulse; 1.14.4 filters the player out.

The server velocity writer is consumed by the client entity-velocity packet path bounded in S062.

Dragon movement, wing-part movement and damage resolution are excluded; only the direct player velocity response and its reachability filter are in scope.

The source proves a changed query predicate for creative-player reachability and a consequent conditional difference in whether the player receives the wing impulse.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F023-ender-dragon-creative-player-fling.md).
