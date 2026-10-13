# sneaking input is rescaled during creative flight

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 488-637.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `public void tick() {`, lines 13-38.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 604-760.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `public void tick() {`, lines 13-50.

Difference: Both keyboard providers multiply sideways and forward input by `0.3` when sneaking. 1.8.9's local creative-flight branch adjusts vertical velocity but leaves those scaled horizontal values as-is. 1.9.4's branch, when the player is flying, is the camera, and the current input is sneaking, divides both horizontal values by `0.3` before applying the vertical fly-speed change.

The exact sequence is multiply-and-cast in `KeyboardInput.tick`, then divide-and-cast in 1.9.4's `LocalClientPlayerEntity.mobTick`; do not algebraically replace that sequence in an implementation.

Movement meaning: Keyboard input is sampled immediately before local movement gates in `mobTick`.

Under the stated no-item-use/no-riding precondition, the camera and `abilities.flying` guard selects the flight branch; 1.9.4 removes the sneak multiplier from both horizontal arguments before the living tick later passes them through `PlayerEntity#moveRelative(float,float)` to its superclass. 1.8.9 keeps the multiplied values.

The source proves a different pair of horizontal arguments supplied to flight travel while sneaking under the stated preconditions.

1.9, ornithe-feather; `feather-gen2-1.9+build.2-mergedv2.jar`: `1.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, prior-forward snapshot and input/item production at lines 641–649, and flight rescaling at lines 710–713; `1.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 13–50. Exact stable 1.9 already performs the stored-axis divide-and-cast, establishing the first stable release boundary after 1.8.9. Snapshots and releases before 1.8.9 are not compared here.

The same three-phase scalar producer is retained in these inspected Feather publications, each `feather-gen2-<version>+build.2-mergedv2.jar`. References are to `<version>/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`:

| Exact versions | Prior forward snapshot | Input poll/item scaling | Flight divide-and-cast |
| --- | --- | --- | --- |
| 1.9 | 644 | 645–649 | 710–713 |
| 1.9.4 | 648 | 649–653 | 715–718 |
| 1.10, 1.10.1, 1.10.2 | 664 | 665–669 | 738–741 |
| 1.11, 1.11.1, 1.11.2 | 676 | 677–681 | 750–753 |
| 1.12, 1.12.1 | 692 | 693–698 | 767–770 |
| 1.12.2 | 697 | 698–703 | 772–775 |
| 1.13 | 697 | 698–703 | 793–796 |
| 1.13.1, 1.13.2 | 701 | 702–707 | 797–800 |

Across the inspected 1.8.9–1.13.2 sources, `KeyboardInput.tick` polls current sneak input and, solely when that input is true, stores `(float)(axis * 0.3)` on both horizontal axes. There is no spectator, passenger, flight, swimming, collision-fit or previous-sneak condition in that keyboard gate. Then the local player stores `axis *= 0.2F` when using an item and not riding; 1.8.9 names this item predicate `hasItemInUse`, while 1.9 onward names it `isUsingItem`. Current sprint checks occur after those two phases. In 1.9–1.13.2 the flight division occurs later, after current sprint checks and ability-flight decisions, under the current `abilities.flying && isCamera() && input.sneaking` conditions. Its own gate has no separate spectator or riding condition.

The division mutates the stored input object, so the next tick's prior-forward snapshot includes its result as well as any preceding item scaling. In 1.8.9 that snapshot instead retains the keyboard/item-scaled value without flight division. The living movement fields are separate copies: for example, 1.9 `LocalClientPlayerEntity.serverTickAi`, lines 582–587, copies input into sideways/forward fields for the camera; `1.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `mobTick`, calls that producer at line 1673 and later damps the copies by `0.98F`, lines 1694–1695. The input object is not damped, so `0.98F` does not enter the next tick's prior-forward input. This establishes the input producer and retained snapshot values, independently of subsequent travel or vertical flight motion.
