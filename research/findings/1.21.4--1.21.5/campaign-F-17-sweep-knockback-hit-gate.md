# F-17: Sweep-target knockback now requires damage acceptance

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, hurtServer 1216-1218; hurtServer 1219.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, hurtServer 1177-1182.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1209-1223.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1174-1184.

Difference: For a swept target Player whose `hurt`/`hurtServer` attempt returns false, 1.21.4 still applies the sweep knockback impulse, while 1.21.5 skips it together with the post-attack effects. The direct target velocity writer is the movement behavior in scope here; attack and damage outcomes are not emulated by this finding.

Movement meaning: The attacking player must enter the sweep-attack loop and the swept target must be a Player. When that target rejects damage, 1.21.4 still calls its knockback method; 1.21.5 does not. The target's `LivingEntity#knockback` then writes its velocity in 1.21.4, after which the target's regular player movement path consumes that state. This finding is distinct from F-16, which covers the Wind Burst impulse applied to the attacking player through the same changed damage-acceptance gate. In the stated branch, 1.21.4 applies a directional sweep impulse to the target Player and 1.21.5 leaves the target's velocity unchanged by that sweep call.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-17-sweep-knockback-hit-gate.md).
