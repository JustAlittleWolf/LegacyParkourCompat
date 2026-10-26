# Swimming sprint retention accepts small positive forward input
1.14.1, Yarn `1.14.1+build.10`: `1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, lines 642 and 668–677.
1.14.2, Yarn `1.14.2+build.7`: `1.14.2/yarn/net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, lines 641 and 667–676; `client/input/Input.java`, `method_20622`, lines 22–23.

Difference: In the already swimming, sprinting branch the old forward failure is `movementForward < 0.8F`; the new failure is `!input.method_20622()`, whose body accepts `movementForward > 1.0E-5F`. Both versions combine this failure with `!food` before applying `!onGround && !input.sneaking`, and both stop unconditionally when outside water. Food placement therefore does not change in 1.14.2.

Movement meaning: With swimming and sprint flags already set, inside water, airborne, not sneaking and enough food, a post-input forward value in `(1.0E-5F, 0.8F)` clears sprint in 1.14.1 and retains it in 1.14.2. This is a symbolic source consequence. A concrete item-free vanilla keyboard path to that intermediate value in this already-swimming branch remains unclosed: KeyboardInput produces full forward or zero absent slowdown; sneak slowdown also enables this branch's sneak exemption. Do not treat this draft as reachability acceptance.

1.13.2, Ornithe Feather: `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, lines 722 and 748–757, and 1.14, Ornithe Feather: same relative class, lines 654 and 680–689, use the 0.8F forward failure and gated food placement. These earlier releases establish the old expression directly.
