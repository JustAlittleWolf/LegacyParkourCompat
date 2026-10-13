# Underwater fall flying sprint

Difference: Start ordinary glide while dry, then enter water while valid glide equipment retains the fall-flying state. On underwater held-sprint+forward tick,1.21.4 stops and cannot start sprint because fallFlying is unconditionally vetoed;1.21.5 permits fallFlying when underwater and its run/swim stop predicates omit that veto. The unchanged fluid-first water travel consumes the differing sprint state, selecting0.9F versus non-sprint slowdown under the stated modifier exclusions.

Conditions: airborne non-passenger/no Levitation Player starts valid-equipment glide in air before entering water; underwater held sprint key and forward input; enough food; no blindness,item use,slow movement,horizontal collision or disallowed passenger; fluid contact refreshed while glide remains valid; for differing water drag,no DolphinGrace and water-movement efficiency<1.0F.

Source locators:

- `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/shouldStopSprinting/canStartSprinting, lines ['664-666', '816-822', '1053-1061'].
- `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep/shouldStopRunSprinting/shouldStopSwimSprinting/canStartSprinting, lines ['722-752', '837-852', '1065-1075'].
- `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying, lines ['1528-1535'].
- `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying, lines ['1507-1514'].
- `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel/updateFallFlying/canGlide, lines ['2189-2199', '2825-2830', '2847-2859'].
- `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel/updateFallFlying/canGlide, lines ['2213-2222', '2815-2820', '2837-2849'].
- `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, baseTick, lines ['453-455'].
- `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, baseTick, lines ['461-463'].

Movement meaning: The underwater sprint decision changes the slowdown consumed by the unchanged fluid-first water travel path under the stated modifier exclusions.
