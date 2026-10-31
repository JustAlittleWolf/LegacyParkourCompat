# Independent review: airborne sprint speed, 1.19.3→1.19.4

**Verdict: ACCEPT** as a reachable local-player movement delta. This is the ordinary on-foot airborne speed provider; it does not depend on vehicles or creative flight.

## Source path and ordering

In both versions, `LocalPlayer.aiStep` processes the current input and can update the sprint flag before invoking `super.aiStep()` (`1.19.3 net/minecraft/client/player/LocalPlayer.java:731–814`; 1.19.4 `:645–795`). That super-call enters `Player.aiStep()` and then `LivingEntity.aiStep()`, which reaches `travel()` and ordinary movement.

In 1.19.3, `Player.aiStep()` calls `super.aiStep()` first, then assigns `flyingSpeed = 0.02F` and adds `0.006F` when currently sprinting (`net/minecraft/world/entity/player/Player.java:496–517`). The ordinary airborne movement consumer uses the field in `LivingEntity.getFrictionInfluencedSpeed()` (`LivingEntity.java:2246–2247`). Therefore travel uses the field value retained from the preceding post-travel update, while the sprint flag may already have changed in the current `LocalPlayer.aiStep`.

In 1.19.4, the post-super field assignment is gone (`Player.aiStep`, `:499–517`). Air movement instead calls virtual `getFlyingSpeed()` (`LivingEntity.java:2197–2198`), and `Player.getFlyingSpeed()` reads the current sprint and flight state (`Player.java:2112–2117`). For an ordinary non-creative on-foot player, it returns `0.025999999F` when sprinting and `0.02F` otherwise. This makes the current tick's sprint transition affect that tick's airborne movement request.

## Numeric detail and movement consequence

Under Java `float` rounding, the old operation `0.02F + 0.006F` rounds to `0.026F` (float bits `0x3cd4fdf4`), while the new `0.025999999F` literal is `0.025999998673796654F` (bits `0x3cd4fdf3`), one ULP lower. So the provider changes in both timing at sprint-state transitions and value during steady airborne sprint. The value feeds `moveRelative` before `Entity.move`, changing the requested horizontal displacement in ordinary client movement. No runtime or trajectory test was run.
