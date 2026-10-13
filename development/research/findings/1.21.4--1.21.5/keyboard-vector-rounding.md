# Keyboard diagonal input is normalized in float before travel in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, getInputVector 1429-1441.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, getInputVector 1458-1470.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2766-2784.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2712-2717.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, serverAiStep 607-617.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, modifyInput 623-658; applyInput 607-621.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick 20-31.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java`, tick 22-31.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/ClientInput.java`, class declaration / field initialization 5-13.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/phys/Vec2.java`, normalized 48-51.

Difference: For simultaneous forward-plus-side keyboard input, 1.21.4 keeps two raw unit impulses through the local-player setup and reaches `Entity#getInputVector` with the scaled Vec3, which normalizes in double precision when its squared length exceeds `1.0`. 1.21.5 first normalizes the two keyboard axes as a float `Vec2`, then scales and applies the new square-movement correction before the same Vec3 consumer. The client player path is supplied by `KeyboardInput`; the source inventory found it as the only `ClientInput` subclass and both `ClientPacketListener` construction sites install it for the local player. The ideal diagonal direction and magnitude are normalized in both paths. The operation order and precision differ: 1.21.5 rounds the direction in float before the later movement speed and yaw operations, while 1.21.4 carries the scaled raw components into a double Vec3 normalization. The new correction also preserves scaled diagonal movement under the item-use and sneaking multipliers. This finding isolates the normalization precision; the separate `0.98F` input-damping difference on 1.21.5's controlled-camera path is recorded in F-09.

Movement meaning: Local key state -> `KeyboardInput#tick` -> `LocalPlayer.aiStep` input sampling -> 1.21.4 `serverAiStep` / 1.21.5 `applyInput` -> `LivingEntity.aiStep` -> player travel -> `Entity#getInputVector` -> acceleration/velocity. 1.21.4 uses the public `ClientInput` impulses; 1.21.5 reads `ClientInput.moveVector`. The source proves the precision and operation-order change. This can change low-order float/double bits in diagonal per-tick acceleration, which later travel arithmetic can propagate into position. It does not establish a measured trajectory or the size/persistence of any difference. Single-axis and zero keyboard input do not exercise diagonal normalization. Exact rounding through the downstream movement branches remains part of the open travel slices.
