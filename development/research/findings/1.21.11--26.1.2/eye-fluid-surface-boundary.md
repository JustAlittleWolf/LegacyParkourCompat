# Eye-fluid detection includes the exact surface
1.21.11, Mojmap: `net/minecraft/world/entity/Entity.java`, `updateFluidOnEyes`, lines 1524–1534. A fluid contributes its tags only when `blockY + height > eyeY`.
26.1.2, published unobfuscated: `net/minecraft/world/entity/EntityFluidInteraction.java`, `update`, lines 58–70. Eye membership is set when `eyeY >= fluidBottom && eyeY <= fluidTop`.
Difference: At an eye position exactly equal to the fluid top, the older strict comparison reports outside while the newer inclusive comparison reports inside. This changes `isUnderWater` and movement predicates that use it, including swimming state and swim-related travel behavior, for a player whose eye lies exactly on a fluid surface.
