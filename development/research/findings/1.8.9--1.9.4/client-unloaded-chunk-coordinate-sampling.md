# unloaded-client-chunk sampling uses different block coordinates

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1119-1239.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float sideways, float forwards) {`, lines 1302-1469.

Difference: 1.8.9 truncates the floating coordinate toward zero before choosing a chunk; 1.9.4 floors it.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

No chunk-loading state or subsequent trajectory is inferred.

Movement meaning: 1.8.9 truncates the floating coordinate toward zero before choosing a chunk; 1.9.4 floors it.

The resulting chunk differs only at negative chunk boundaries for coordinates just below a multiple of 16, such as `-0.2` (1.8.9 stores 0, 1.9.4 stores -1).

If this maps the two versions to chunks with different loadedness on a client, their Y-velocity update takes different branches: ordinary gravity versus the unloaded fallback (`-0.1` above Y=0, otherwise `0`), followed by the shared `0.98F` multiplier.

No chunk-loading state or subsequent trajectory is inferred.
