# Diagonal collision axis order

1.13.2, Ornithe Feather Gen2: `net/minecraft/entity/Entity.java`, `move(MoverType,double,double,double)`, lines 578–599. After collecting collisions for the requested movement, it clips Y first, then X, then Z.
1.14.4, Ornithe Feather Gen2: `net/minecraft/entity/Entity.java`, `m_64271959`/`m_79801352`, lines 692–738. For a vector with at least two nonzero components, it clips Y first, then starts with Z when `abs(dx) < abs(dz)` and otherwise starts with X; the other horizontal component is resolved afterward.
Difference: With both horizontal deltas nonzero and unequal magnitudes, the newer order starts with the lesser-magnitude horizontal component; the older order is fixed X then Z. The new resolver also branches between aggregate-shape and world-aware single-axis helpers based on zero components.
Movement meaning: Ordinary diagonal player travel reaches the two-horizontal-component branch. Where the player box and collision geometry make available overlap depend on which horizontal axis is clipped first, the resolved movement vector and resulting position can differ. This is source-confirmed; an exact runtime/map witness remains open.
