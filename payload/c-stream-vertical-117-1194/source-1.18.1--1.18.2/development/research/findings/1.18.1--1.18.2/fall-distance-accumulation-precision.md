# Fall-distance accumulation precision

1.18.1, Mojmap: `decompiled_minecraft/1.18.1/mojmap/net/minecraft/world/entity/Entity.java`, `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 979–981.

1.18.2, Mojmap: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 989–991.

Difference: On the ordinary airborne downward branch (`!onGround` and resolved Y displacement `< 0.0`), 1.18.1 computes `(float)(fallDistance - displacement)`: subtraction occurs in double precision, followed by one float conversion. 1.18.2 computes `fallDistance -= (float) displacement`: it first converts the displacement to float, then subtracts in float precision.

Movement meaning: In dry, ordinary, non-gliding and non-flying local-player travel, a fall-distance value of `0.25F` with resolved downward Y displacement `-0.08` accumulates to `0.33000001311302185F` in 1.18.1 and `0.32999998331069946F` in 1.18.2, adjacent float values differing by one ULP. This changes common per-tick vertical fall state and can affect later threshold checks.
