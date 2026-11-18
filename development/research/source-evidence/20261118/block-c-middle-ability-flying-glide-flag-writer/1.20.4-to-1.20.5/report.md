# 1.20.4 to 1.20.5: local ability flight with synchronized glide state

## Frozen scope and verdict

Compared the bounded local-player path for an ordinary dry player with an enabled Elytra in the chest slot, a synchronized FallFlying shared flag (bit 7), and legal `mayfly` ability. The player is not a passenger. Source-only review; no product implementation, wiki, backlog, runtime or generated source was consulted.

**Verdict:** The flag-clear operation and its timing during real movement are unchanged. In both versions, `Player.travel` enters the `abilities.flying && !isPassenger()` branch, calls `super.travel` first, preserves 60% of the pre-travel vertical velocity, resets fall distance, and then unconditionally writes shared flag 7 false. Consequently, when bit 7 is live at tick entry and the player is airborne, the superclass movement can use the glide branch for that movement before the local client clears the flag at the end of `Player.travel`. When the player is grounded, `LivingEntity.updateFallFlying` rejects the glide state before travel; the local-client method does not itself write that result, but `Player.travel` still clears bit 7 after its superclass call.

There is a separate 1.20.5 local toggle delta: enabling flight while grounded now calls `jumpFromGround()` immediately after setting `abilities.flying`. 1.20.4 only toggles the ability. This changes the velocity entering that tick's movement, but does not move the flag-clear write earlier or add an `abilities.flying` check to `updateFallFlying`. For the airborne toggle case, the added on-ground jump is unreachable and the glide-then-clear ordering is unchanged. For a grounded tick that still carries bit 7, 1.20.5 adds a jump impulse before superclass movement and then clears the bit at the existing `Player.travel` point.

## Exact source evidence

- **1.20.4, Mojmap:** `LocalPlayer.aiStep`, lines 717–742 toggles flight on the second eligible jump press when `mayfly` is true, sends the ability update, and only attempts a new Elytra start when `!abilities.flying`. `Player.travel`, lines 1451–1472, has the flight branch and bit-7 clear after `super.travel` and `resetFallDistance`. `LivingEntity.aiStep`, lines 2590–2605, calls `updateFallFlying` immediately before `travel`; `LivingEntity.updateFallFlying`, lines 2641–2665, checks bit 7, airborne, nonpassenger, no levitation, and enabled chest Elytra, then writes bit 7 only when `!level.isClientSide`. `LivingEntity.travel`, lines 2099–2138, selects glide movement from `isFallFlying()` and only clears on landing server-side.
- **1.20.5, Mojmap:** `LocalPlayer.aiStep`, lines 720–749, has the same toggle gates and Elytra-start exclusion, with the added `jumpFromGround()` call when flight becomes enabled on ground at lines 732–735. `Player.travel`, lines 1473–1494, retains the 1.20.4 flight branch and write order. `LivingEntity.aiStep`, lines 2670–2683, retains update-before-travel order; `LivingEntity.updateFallFlying`, lines 2721–2745, retains the same gates and server-only flag write. `LivingEntity.travel`, lines 2168–2207, retains glide selection and server-only landing clear.
- **Eligibility/write checks:** `Player.tryToStartFallFlying` is unchanged in the relevant gates (1.20.4 lines 1506–1516; 1.20.5 lines 1534–1544): airborne, not already gliding, not in water, no levitation, enabled chest Elytra. `Player.startFallFlying` writes bit 7 true; `stopFallFlying` writes true then false (1.20.4 lines 1518–1525; 1.20.5 lines 1546–1553). Neither predicate includes `abilities.flying`. `LocalPlayer.aiStep` does exclude the normal jump-to-start request while flying. The direct flying clear is the `Player.travel` write above, not a new `updateFallFlying` gate.
- **Separate fall-distance note:** In both `Player.travel` implementations, `resetFallDistance()` is scheduled after `super.travel` and before `setSharedFlag(7, false)`. The new grounded-toggle `jumpFromGround()` call is before the movement superclass call and does not itself write fall distance. These are distinct operations; the unchanged travel reset is not evidence of a new glide predicate.

## Source identity and integrity

Both sides are exact releases under official Mojang client mappings (`mojmap`), decompiled with Vineflower. The checked source-file hashes equal their entries in the corresponding source manifests.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Client SHA-256 |
|---|---|---|---|---|
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` |

| Relied-on source | SHA-256 |
|---|---|
| 1.20.4 `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| 1.20.5 `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| 1.20.4 `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.5 `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.4 `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.5 `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |

The checked manifests and marker identities establish the published local source set. The 1.20.4 provenance record is legacy-format and does not itself provide the 1.20.5-style detailed exact-run artifact section; its ready marker supplies the exact release, mapping, client hash, and source-manifest identity. No source input gap affected this bounded conclusion.
