# Jump cutoff boundary audit (r2)

## Verdict

Within the supplied canonical source roots, **1.9 is the first checked stable release with the 0.003 component-velocity zero cutoff**: 1.8.9 uses 0.005, and 1.9, 1.9.1, 1.9.2, and 1.9.3 use 0.003. The 1.9.1, 1.9.2, and 1.9.3 bodies retain the 1.9 rule unchanged. This supports the stable-release boundary 1.8.9 → 1.9. The source roots and their publication manifests identify the inspected releases; this bounded audit did not inspect pre-release builds.

## Compared operation

In `net/minecraft/entity/living/LivingEntity.java`, `mobTick` first applies its conditional `0.98` velocity damping to X, Y, and Z (the `else if (!this.isLocallyControlled())` branch), then checks the absolute value of each component in X/Y/Z order and assigns `0.0` when the strict comparison succeeds. Only the threshold differs across the compared versions:

- 1.8.9, lines 1406–1416: `Math.abs(velocityX/Y/Z) < 0.005`.
- 1.9, lines 1653–1663: `Math.abs(velocityX/Y/Z) < 0.003`.
- 1.9.1, lines 1657–1667: same strict `< 0.003` checks and zero assignments.
- 1.9.2, lines 1657–1667: same strict `< 0.003` checks and zero assignments.
- 1.9.3, lines 1666–1676: same strict `< 0.003` checks and zero assignments.

Equality to the threshold does not trigger zeroing in any inspected source. The 1.9 wiki sentence says the “Momentum threshold” was lowered from 0.005 to 0.003; it agrees with the source transition but is corroboration, not the basis for source acceptance.

## Source hashes and publication identities

The computed source SHA-256 values match the corresponding manifest entries for all four 1.9+ sources. The 1.8.9 source hash also matches its version-root source manifest.

| Release | `LivingEntity.java` SHA-256 | Feather mapping publication | Mojang client publication |
|---|---|---|---|
| 1.8.9 | `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E` | Feather Gen2 `1.8.9+build.2`, mergedv2; provenance identifies Vineflower 1.12.0 | Version-root provenance identifies the game version and source manifest |
| 1.9 | `5B5936656AACC64A4D75DB7B370985F9456AC96076331DA047BD246F76A705FB` | `feather-gen2/1.9+build.2/feather-gen2-1.9+build.2-mergedv2.jar`; SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; Feather metadata `/v3/versions/feather/1.9`, SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | Mojang version metadata package `9178828493e41a24452841039710f05728c466d6/1.9.json`; client object `2f67dfe8953299440d1902f9124f0f2c3a2c940f`; both hashes are recorded in the manifest |
| 1.9.1 | `D8824F670037A88753C428C27213989FC5173B1D7C638F498F73395C145B6FD9` | `feather-gen2/1.9.1+build.2/feather-gen2-1.9.1+build.2-mergedv2.jar`; SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; Feather metadata `/v3/versions/feather/1.9.1`, SHA-256 `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | Mojang version metadata package `fdca0cf4bdab9959fce30413202a00a1bce35be5/1.9.1.json`; client object `9bc7f02323d90b9385c1a5dbd47fb144a3fb8835`; both hashes are recorded in the manifest |
| 1.9.2 | `D8824F670037A88753C428C27213989FC5173B1D7C638F498F73395C145B6FD9` | `feather-gen2/1.9.2+build.2/feather-gen2-1.9.2+build.2-mergedv2.jar`; SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; Feather metadata `/v3/versions/feather/1.9.2`, SHA-256 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | Mojang version metadata package `9623ca73a57c6d183a28513c894c2337cfe65995/1.9.2.json`; client object `19106fd5e222dca0f2dde9f66db8384c9a7db957`; both hashes are recorded in the manifest |
| 1.9.3 | `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5` | `feather-gen2/1.9.3+build.2/feather-gen2-1.9.3+build.2-mergedv2.jar`; SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; Feather metadata `/v3/versions/feather/1.9.3`, SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985` | Mojang version metadata package `4d37195227760207d397224c509ded4b0c68ea37/1.9.3.json`; client object `b6985b0d3a0520dfb6f17eeb1e8ba58ce9577061`; both hashes are recorded in the manifest |

The four mapping-local `.decompile-provenance.json` manifests identify the same `lpc-decompile-v1` pipeline and record Eclipse Adoptium Java 25.0.3+9 on Windows 11, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Tiny Remapper 0.14.1, and the exact downloaded artifact hashes shown above. In each manifest, the `sources["net/minecraft/entity/living/LivingEntity.java"]` digest equals the computed file digest. The 1.9.1 and 1.9.2 whole-file digests are identical, as are their recorded hashes; each release nevertheless has its own Feather and Mojang publication identities in its provenance manifest.
