# Frozen source roster: 1.15.2 Mojmap → 26.2 unobfuscated lava presence

Pair: Minecraft 1.15.2 (Mojmap) → 26.2 (published/unobfuscated)
Question boundary: ordinary on-foot LocalPlayer lava-presence state and callback timing; distinction from fluid-height, direct player jump/travel consumers; same vanilla block/fluid state witness including stored LiquidBlock.LEVEL vs fluid amount and all movement-relevant inputs.

Source files frozen before claim inspection:

1.15.2 Mojmap
- net/minecraft/client/player/LocalPlayer.java
- net/minecraft/world/entity/Entity.java
- net/minecraft/world/entity/LivingEntity.java
- net/minecraft/world/entity/player/Player.java
- net/minecraft/world/level/block/LiquidBlock.java
- net/minecraft/world/level/block/state/BlockState.java
- net/minecraft/world/level/Level.java
- net/minecraft/world/level/material/Fluid.java
- net/minecraft/world/level/material/FlowingFluid.java
- net/minecraft/world/level/material/LavaFluid.java
- net/minecraft/world/level/material/FluidState.java

26.2 unobfuscated
- net/minecraft/client/player/LocalPlayer.java
- net/minecraft/world/entity/Entity.java
- net/minecraft/world/entity/LivingEntity.java
- net/minecraft/world/entity/player/Player.java
- net/minecraft/world/level/block/LiquidBlock.java
- net/minecraft/world/level/block/state/BlockState.java
- net/minecraft/world/level/Level.java
- net/minecraft/world/level/material/Fluid.java
- net/minecraft/world/level/material/FlowingFluid.java
- net/minecraft/world/level/material/LavaFluid.java
- net/minecraft/world/level/material/FluidState.java
- net/minecraft/world/entity/EntityFluidInteraction.java

Members to inspect, both sides where present:
- LocalPlayer.tick/aiStep and ordinary local-player tick entry/call order
- Entity.tick/baseTick, fluid state update, fluid-presence fields/predicates, fluid-height scan/update and callback/collision processing
- LivingEntity.tick/aiStep/jumpFromGround/travel/fluid jump consumers
- Player overrides/ability gates that alter those calls
- LiquidBlock.LEVEL and shape/legacy fluid-state conversion
- FluidState amount/height, FlowingFluid amount/source-height math, LavaFluid overrides
- relevant Level block/fluid-state retrieval only where needed to close the scan inputs

This roster is frozen before reading the approved comparison claim. No product implementation, wiki material, findings index, other pair or Git history consulted.