# Current turn pitch precision

1.16.5, Mojmap. `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double, double)`, lines 340–352.

1.17, Mojmap. `decompiled_minecraft/1.17/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double, double)`, lines 377–389.

For the current pitch, 1.16.5 multiplies the second input by `0.15D`, adds it to the promoted float pitch in double precision, narrows the sum to float and clamps it to `[-90.0F, 90.0F]`. In 1.17 the input narrows to float before multiplication by `0.15F`. The addition is also in float, followed by the first pitch setter and a later clamp. Exact adjacent comparisons trace the double-input calculation back to `Entity.updateLocalPlayerCamera(double, double)` in 1.13, Ornithe Feather, lines 318–326. The 1.12.2 Ornithe Feather method takes float input.

An ordinary finite local mouse update with current pitch `-4.0696001052856445F` and pitch input `4.096000411987318D` yields `-3.455199956893921F` in 1.16.5 and `-3.4552001953125F` in 1.17. The two in-range values select different sine-table entries on the next dry active Elytra movement path and change the local player's first self-move request.
