# Resolved SELF movement below the zero-displacement cutoff no longer updates position

1.13.2, Ornithe Feather: `net/minecraft/entity/Entity.java`, `Entity.move`, lines 473, 579, and 676. For `MoverType.SELF`, nonzero requested movement enters collision resolution at line 579; after resolution, `setPositionFromShape()` runs unconditionally at line 676. A nonzero resolved SELF displacement is applied to position regardless of its squared length.

The 1.13.2 `MoverType.PISTON` branch is separate and runs before collision resolution: lines 478–513 accumulate the per-axis piston movement and return when its adjusted nonzero axis has absolute value at most `1.0E-5F`. This piston-only gate does not describe the SELF comparison above.

1.14, Ornithe Feather: `net/minecraft/entity/Entity.java`, `Entity.move`, lines 453–456. 1.14.1–1.14.3, Yarn: `net/minecraft/entity/Entity.java`, `Entity.move`, respectively lines 453–456, 455–458, and 462–464. 1.14.4, Mojmap: `net/minecraft/world/entity/Entity.java`, `Entity.move`, lines 467–469. Each patch applies the position gate after collision resolution: it moves the bounding box and updates position only when the resolved vector's squared length is greater than `1.0E-7`. This gate is after the PISTON-specific input filter, outside its branch, and also applies to SELF movement.

Difference: the resolved-vector cutoff first appears in release 1.14 and is present in every 1.14 patch through 1.14.4. A nonzero resolved SELF displacement with squared length at most `1.0E-7` (Euclidean length at most about `0.00031623` blocks) changes position in 1.13.2 but leaves position unchanged in 1.14–1.14.4. Later collision and velocity handling still consumes the resolved vector.

Reachability: player travel calls the shared `Entity.move` path with `MoverType.SELF` (1.13.2 `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1478–1575; 1.14.4 `net/minecraft/world/entity/player/Player.java`, lines 1380–1404, and `net/minecraft/world/entity/LivingEntity.java`, lines 1822–1901).

Movement meaning: a local player traveling horizontally toward a vanilla solid block from a gap of `0.0002` blocks can have collision resolution return `(0.0002, 0, 0)`. The 1.13.2 SELF path closes the gap; the 1.14–1.14.4 position gate skips that translation because the resolved vector's squared length is `4.0E-8`.
