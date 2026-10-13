# F009: Approximate horizontal collision flag

- **1.13.2**, ornithe-feather: `Entity.java`; lines 674-680.
- **1.14.4**, ornithe-feather: `Entity.java`; lines 471-475.

## Difference

1.13.2 sets horizontal collision when requested and resolved X or Z differ exactly. 1.14.4 uses `!MathHelper.m_68886716(requested,resolved)` for each horizontal component; the helper treats values within its tolerance (`1.0E-5`) as equal. 1.14.4 still uses exact component inequality in the later velocity-cancellation checks.

## Movement meaning

Entity.move stores the flag after the collision solver; LivingEntity travel and player logic consume collision flags in later movement decisions.

Callers and downstream flag readers have not been fully enumerated.

For a horizontal component changed by a small amount within the helper tolerance, 1.14.4 may leave `collidingHorizontally` false where 1.13.2 sets it true, while exact velocity cancellation may still occur.

Exact helper expression and consumer consequences should be independently audited; no movement result is tested.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F009-approximate-horizontal-collision-flag.md).
