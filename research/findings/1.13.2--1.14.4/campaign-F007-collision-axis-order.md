# F007: Collision axis order

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 578–599.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move, m_79801352`, lines 444–475, 708–771.

Difference: 1.13.2 obtains one collision list and resolves Y, then X, then Z. 1.14.4 delegates to a solver that resolves Y and orders horizontal axes by requested component magnitude, so diagonal motion can resolve Z before X or X before Z.

Movement meaning: Living travel and other displacement callers invoke Entity.move.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F007-collision-axis-order.md).
