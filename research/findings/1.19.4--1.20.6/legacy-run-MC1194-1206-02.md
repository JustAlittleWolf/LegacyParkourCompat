# MC1194-1206-02: Travel gravity now comes from a synchronized attribute

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, getDefaultGravity 1101-1107.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 1983-1994.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2104-2112; getDefaultGravity 2100-2102; createLivingAttributes 292-308; getFluidFallingAdjustedMovement 2282-2295.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, createAttributes 221-230; class declaration / field initialization 137.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 42-44.

Difference: 1.20.6 supplies the gravity value to travel and fluid falling adjustment from a player attribute. 1.19.4 uses the literal `0.08`, with the descending Slow Falling override to `0.01`. The new attribute's vanilla player default is exactly `0.08`, so the standard unmodified gravity values match across these endpoints, including the usual `Math.min(0.08, 0.01) == 0.01` Slow Falling case. 1.20.6 diverges when a synchronized attribute value differs: for example, a value of `0.0` with `isNoGravity() == false` skips gravity application in 1.20.6, while 1.19.4 still uses its fixed gravity. Values below `0.01` also remain below that ceiling under 1.20.6 Slow Falling but not under 1.19.4's assignment.

Movement meaning: `LocalPlayer.aiStep()` delegates to `LivingEntity.aiStep()`, which reaches `LivingEntity.travel()` for local player movement. The gravity input is the server-synchronized 1.20.6 attribute; this source comparison does not infer which server-side modifiers or game rules can supply a changed value. The checked vanilla Slow Falling registration is an ordinary effect entry; no gravity attribute modifier has yet been established from its registration. Further effect/data closure remains in Stage 6. Source-proven: when the 1.20.6 gravity attribute differs from `0.08`, travel can write a different vertical velocity; when it is `0.0` while the entity gravity flag is false, the new fluid guard also differs. Under the vanilla default attribute value, these particular gravity values are unchanged.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-02.md).
