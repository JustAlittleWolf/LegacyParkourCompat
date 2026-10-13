# F009: Approximate horizontal collision flag

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 674–680.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 471–475.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/util/math/MathHelper.java`, `m_68886716`, lines 155–157.

Difference: 1.13.2 sets horizontal collision when requested and resolved X or Z differ exactly. 1.14.4 uses `!MathHelper.m_68886716(requested,resolved)` for each horizontal component; the helper treats values within its tolerance (`1.0E-5`) as equal. 1.14.4 still uses exact component inequality in the later velocity-cancellation checks.

Movement meaning: Entity.move stores the flag after the collision solver; LivingEntity travel and player logic consume collision flags in later movement decisions.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F009-approximate-horizontal-collision-flag.md).
