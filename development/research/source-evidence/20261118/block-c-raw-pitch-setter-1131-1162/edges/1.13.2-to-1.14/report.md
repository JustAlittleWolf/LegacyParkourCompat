# Ordinary player pitch turn: 1.13.2 → 1.14.0

## Result

No source difference in the bounded ordinary camera-turn pitch update. In both exact Feather decompilations, `Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` computes `d = cursorDeltaY * 0.15` and `e = cursorDeltaX * 0.15` as doubles; writes `pitch = (float)(pitch + d)` before writing yaw; clamps current pitch to `[-90.0F, 90.0F]`; then writes `lastPitch = (float)(lastPitch + d)`, writes last yaw, and clamps `lastPitch` to the same bounds. The order and expressions match exactly.

This statement is bounded to inputs whose arithmetic result casts to finite floats for both pitch fields. It makes no claim about NaN or infinity behavior, input-device scheduling, rendering, or packet/network behavior. No change to the historical setter sequence or pitch movement consequence is established for this slice.

The live movement consumer is the active `isFallFlying()` branch in `LivingEntity.travel`: both releases derive the pitch angle from `this.pitch * (float)(Math.PI / 180.0)` and use its sine/cosine in the Elytra velocity update. Thus the branch reads the already stored player pitch when that travel branch runs. This consumer check only establishes the pitch-to-movement dependency; this report does not compare the full Elytra algorithm or claim tick scheduling.

## Exact source locations

- 1.13.2, Ornithe Feather: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera`, lines 318–330. Pitch assignment/cast and clamp: 321–323; previous-pitch assignment/cast and clamp: 324–326.
- 1.14.0, Ornithe Feather: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera`, lines 314–326. Pitch assignment/cast and clamp: 317–319; previous-pitch assignment/cast and clamp: 320–322.
- 1.13.2, Ornithe Feather: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, active fall-flying branch of `travel`, lines 1488–1513; pitch angle at 1494 and angle-driven vertical/horizontal terms at 1498–1513.
- 1.14.0, Ornithe Feather: `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, active fall-flying branch of `travel`, lines 1790–1818; pitch angle at 1797 and angle-driven vertical/horizontal terms at 1801–1818.

## Immutable local source identity

Both trees are read-only canonical local outputs, each with `status: ready` in its ready marker. The mapping family is **Ornithe Feather** on both sides; the artifacts are release-specific, so this is a same-family comparison, not a claim that the mapping archives are identical.

| Release | Mapping artifacts | Client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.13.2 | `feather-gen2-1.13.2+build.2-mergedv2.jar`; `feather-gen2-1.13.2+build.2.tiny` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |
| 1.14.0 | `feather-gen2-1.14+build.2-mergedv2.jar`; `feather-gen2-1.14+build.2.tiny` | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df` |

Ready-marker SHA-256: 1.13.2 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; 1.14.0 `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`.

Provenance-file SHA-256: 1.13.2 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; 1.14.0 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`.

The source-manifest entries independently identify the relied files: 1.13.2 `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; 1.14.0 `Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`, `LivingEntity.java` `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79`.

