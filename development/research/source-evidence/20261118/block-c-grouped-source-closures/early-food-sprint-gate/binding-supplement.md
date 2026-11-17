# Hidden provenance binding supplement

Status: COMPLETED — exact 1.9.0–1.9.3 source identities are now bound for the reviewed sprint-gate slice. This supplement preserves and extends, but does not edit, the earlier verdict.

## Bound records

Comparison artifact SHA-256: `a764421ee8d782d98937371ce9aabaffbff2df2420c71586e32be3671a18d874`.
Prior verdict SHA-256: `9e0917eb519f90a29d33c13297863b9b22addebd37037cc27378fc5cd82b6c7a`.

Each nested `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json` was read directly by absolute path. Its `sources` entry for `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` equals the SHA-256 of the current Java file. Its `inputs` binds a Mojang client JAR, Feather version metadata, and that release's Feather Gen2 merged mapping JAR. The recorded runtime is Eclipse Adoptium 25.0.3+9-LTS on Windows 11 amd64; the pipeline is `lpc-decompile-v1` with the same declared decompiler/remap options across these four manifests.

| Release | Hidden provenance SHA-256 | LocalClientPlayerEntity SHA-256 | Feather Gen2 mergedv2 mapping input SHA-256 |
| --- | --- | --- | --- |
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` |

The respective metadata input URLs end in `/versions/feather/1.9`, `/1.9.1`, `/1.9.2`, and `/1.9.3`; the respective mapping artifact URLs identify the matching `<version>+build.2` Feather Gen2 release. These identifiers are distinct per release even where decompiled source bytes coincide.

## Slice retrace and verdict

The exact sprint gate bodies were retraced in all four bound files (`LocalClientPlayerEntity.java`: 1.9 `:657–684`, 1.9.1/1.9.2 `:658–685`, 1.9.3 `:661–688`). Each uses `foodLevel > 6.0F || abilities.canFly`; the double-tap route, held-key route and stop condition retain the same gate predicates and ordering as the already reviewed 1.8.9 and 1.9.4 endpoints. The `f = 0.8F` threshold and pre-input-tick `bl3` capture are also retained. The 1.9.1 and 1.9.2 files have identical SHA-256; the 1.9.3 file matches the 1.9.4 file SHA-256. The 1.9.0 gate block was separately inspected and matches those predicates/order despite its distinct source-file hash.

Therefore, the previously reported intermediate provenance limitation is closed for this bounded sprint food/`canFly` gate claim: the reviewed no-difference result now has exact-release source bindings for every 1.9.x point from 1.9 through 1.9.4. The separate 1.10.2 flight double-tap guard remains a distinct subordinate observation. This does not claim full movement-pair coverage, establish an implementation cutover, or assess runtime parity.

## Terminal status

COMPLETED