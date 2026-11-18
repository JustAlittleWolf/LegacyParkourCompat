# Provenance addendum — 1.9 → 1.9.3

This addendum supersedes only the evidence-availability statement in `verdict.md`; it does not alter the frozen roster or the content-level conclusions. Comparison claim identity remains SHA-256 `bba2e664adf8c6d855cd3afcfc2e05f8411d87ebfdff426b94946d6c179a422`.

## Exact manifest identities

- 1.9 manifest: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9\ornithe-feather\.decompile-provenance.json`; SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
  - Exact Minecraft release identity: `1.9` metadata URL `https://piston-meta.mojang.com/v1/packages/9178828493e41a24452841039710f05728c466d6/1.9.json`, locally pinned input SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`; client artifact input SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`.
  - Ornithe Feather input: `feather-gen2/1.9+build.2/feather-gen2-1.9+build.2-mergedv2.jar`, SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; Feather metadata input SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`.
- 1.9.3 manifest: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.3\ornithe-feather\.decompile-provenance.json`; SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
  - Exact Minecraft release identity: `1.9.3` metadata URL `https://piston-meta.mojang.com/v1/packages/4d37195227760207d397224c509ded4b0c68ea37/1.9.3.json`, locally pinned input SHA-256 `2ec3b7abc12f0ad4d4e3c777dc0c429fa948ffd4d1bdbb89a81aecbd30021be5`; client artifact input SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`.
  - Ornithe Feather input: `feather-gen2/1.9.3+build.2/feather-gen2-1.9.3+build.2-mergedv2.jar`, SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; Feather metadata input SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`.

## Integrity check

Parsed both manifests and recomputed SHA-256 for each of the nine relied-on paths enumerated for that endpoint in the comparison roster (`Block.java`, `FenceBlock.java`, `LiquidBlock.java`, `Material.java`, `World.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `LocalClientPlayerEntity.java`). All 18 current source hashes equal their respective manifest `sources` values; all also equal the comparison's declared file hashes. No mismatches or missing paths.

## Evidence consequence

The provenance gate is closed for the exact 1.9 → 1.9.3 comparison. The prior `verdict.md` statement that local provenance could not be bound was based on enumerating only the version directory; this addendum records the exact hidden mapping-root manifests found on reinspection. The earlier conditional source-content verdict is now source-bound. No intermediate patch is inferred.