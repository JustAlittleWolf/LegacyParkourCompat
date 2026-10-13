# F01: shared physical key no longer selects only one movement mapping

- **1.21.8**, mojmap: `net/minecraft/client/KeyMapping.java`; lines 48–53.
- **1.21.10**, mojmap: `net/minecraft/client/KeyMapping.java`; lines 33–44.

## Difference

Under the same concrete precondition—both movement mappings are initially released, and the user assigns one physical key to both forward (`keyUp`) and backward (`keyDown`)—pressing that key updates only the single mapping stored for the key in 1.21.8.

The identity of that mapping depends on the `ALL` iteration/overwrite order and is deliberately not asserted.

Consequently, exactly one of the two input booleans is down and the forward/backward impulse is `+1.0F` or `-1.0F`.

In 1.21.10, the physical key maps to a list and the key event sets every mapping in it.

Both booleans become down, so `calculateImpulse` returns `0.0F`.

The `KeyboardInput` implementation is otherwise byte-for-byte identical in the verified paired source artifact.

## Movement meaning

Client keyboard press/release -> `KeyboardHandler.keyPress` -> `KeyMapping.set` -> mapping value(s) -> `KeyboardInput.tick` reads forward/backward mapping state -> `LocalPlayer` input tick -> `LivingEntity.aiStep` calls `applyInput`, constructs movement input, and reaches travel -> movement-relative acceleration adds the transformed input to player delta movement.

The player input path is active when the local player is controlled and movement simulation is enabled.

The finding concerns a user-configured duplicate key; it does not affect default bindings, where forward and backward use distinct keys.

No resource, tag, attribute, effect, equipment, or server-supplied value participates in selecting or calculating these two key states. 1.21.8's duplicate winner is not resolved here.

The existing player velocity and other movement forces remain independent inputs to the later movement step.

Source-proven: for the stated precondition, 1.21.8 supplies a nonzero forward/backward input component while 1.21.10 supplies zero; that component reaches the player's movement travel path. `Entity.moveRelative` adds the resultant input acceleration to delta movement.

Predicted only: the player's resulting velocity and position can therefore diverge between versions under otherwise matching state.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.8--1.21.10/findings/F01-duplicate-movement-key-input.md).
