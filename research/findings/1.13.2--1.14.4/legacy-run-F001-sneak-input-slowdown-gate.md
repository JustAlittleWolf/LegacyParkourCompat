# F001: Pose-aware sneak input slowdown gate

- **1.13.2**, ornithe-feather: `net/minecraft/client/entity/living/player/KeyboardInput.java`; lines 46–49.
- **1.14.4**, ornithe-feather: `net/minecraft/client/entity/living/player/KeyboardInput.java`; lines 13–25.

## Difference

In 1.13.2, `KeyboardInput.tick()` multiplies sideways input and then forward input by `(float)(value * 0.3)` whenever the sneak key is held.

In 1.14, `tick(boolean bl, boolean bl2)` uses the same two operations when `!bl2 && (this.sneaking || bl)`.

The local player computes `bl` from pose-fit state before calling the input tick and supplies `isSpectator()` as `bl2`.

The exact 1.14 group base confirms the pose-aware gate first appears at 1.14.

At the 1.14.4 endpoint, `KeyboardInput.tick` applies slowdown when the player is not a spectator and either the sneak key is held or the local pose-based predicate is true.

That predicate requires the player not to be flying or swimming, the sneaking pose to fit, and either the sneak key to be held or the standing pose not to fit.

An outside-water swimming pose is a separate alternative.

F002 records that the swimming exclusion differs from the 1.14 base and 1.14.1; its exact first patch is deferred to the minor-version pass.

When the local player is flying with sneak held, the direct sneak-key branch still applies the `0.3` factor in 1.14.4.

The later flight compensation divides the sampled input fields after the local horizontal movement speeds have already been copied, so it does not undo the current tick's slowdown.

The compatibility gate therefore keeps slowdown active for flying sneak input; the later field compensation is recorded as source context and is not applied to the modern movement vector.

## Movement meaning

Local client player `mobTick()` → `KeyboardInput.tick(...)` → sideways/forward input fields → local player movement-speed fields.

In 1.14, the gate reads spectator state, flight state, sneak-key state, pose dimensions, collision availability, swimming pose, and water state. `PlayerEntity.updatePlayerPose()` selects a crouching or swimming pose when the requested pose does not fit; collision checks use the entity's pose dimensions and block/entity collision shapes.

Both endpoint player paths feed the sampled values into player movement.

The claim is limited to the client player path.

Source proves the changed predicate and the ordering of the later flight compensation.

It predicts additional slowdown while a non-flying player is forced into a fitting sneak pose without holding sneak, no slowdown for spectators, and slowdown for flying sneak input through the direct key branch.

Other movement slices in this run remain pending.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.13.2--1.14.4/findings/F001-sneak-input-slowdown-gate.md).
