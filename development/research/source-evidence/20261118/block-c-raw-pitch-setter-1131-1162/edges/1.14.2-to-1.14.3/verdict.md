# Blind source audit: 1.14.2 → 1.14.3 pitch path

## Verdict

No source difference found in the bounded operation. The ordinary local turn path, current/previous pitch writes and clamps, and the dry active-Elytra current-pitch read have the same relevant arithmetic, casts, gates, and order in 1.14.2 and 1.14.3. This verdict does not establish the full-pair inventory or any intermediate patch boundary.

## Frozen evidence identity

Frozen before any author/report access. The digest is SHA-256 over the following canonical UTF-8 payload, with LF separators and no terminal newline: first the scope line shown below; then for each version in ascending order, its `version` line, manifest SHA-256, mapping-jar and client-jar input URL/hash lines sorted by URL, and the five source lines in the order Mouse, ClientPlayerEntity, Entity, LivingEntity, MathHelper.

`e04a16bc037a03d7b602d6b46d6b9ebf63db2812b3efbc319ddf95bbe49682f1`

`scope=ordinary-local-turn-pitch;current-and-previous-finite-clamps-order;dry-active-local-elytra-pitch-read;compare=1.14.2-yarn-to-1.14.3-yarn`

The canonical source roots are version-first: `decompiled_minecraft/<version>/yarn/`. This matches `buildSrc/README.md`'s output convention. The provenance manifests in those roots bind the generated source hashes to exact client and Yarn mapping artifact inputs; the source evidence was not read from the ignored mapping/decompile cache. Both manifests' cited source hashes match the actual files.

| Version | Version-root provenance SHA-256 | Exact client input SHA-256 | Exact Yarn input SHA-256 |
| --- | --- | --- | --- |
| 1.14.2 Yarn | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` (`ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar`) | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` (`1.14.2+build.7`, v2 jar) |
| 1.14.3 Yarn | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` (`af100b34ec7ef2b8b9cf7775b544d21d690dddec/client.jar`) | `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` (`1.14.3+build.13`, v2 jar) |

Actual source SHA-256 values, verified against each version-root manifest:

| Relative source path under `<version>/yarn/` | 1.14.2 | 1.14.3 |
| --- | --- | --- |
| `net/minecraft/client/Mouse.java` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |

## Source trace

- `Mouse.updateMouse` in both releases only calls the local `this.client.player.changeLookDirection(j, k * n)` after the same cursor-focus/lock and non-null-player gates. Mouse smoothing, sensitivity calculation, input reset, and Y inversion are byte-identical (`Mouse.java`, lines 184–214).
- `Entity.changeLookDirection` has the same operations in both releases (1.14.2 lines 315–325; 1.14.3 lines 314–324): compute double `f = e * 0.15` and `g = d * 0.15`; cast `(this.pitch + f)` to float and write current pitch; cast/write yaw; clamp current pitch; cast/write `(this.prevPitch + f)`; cast/write previous yaw; clamp previous pitch; then notify a vehicle if present. The `float` casts precede their corresponding clamps. For overflow to ±infinity, the subsequent clamp writes ±90; `MathHelper.clamp(float, float, float)` uses the same comparisons in both versions (lines 89–95). NaN is not converted by that clamp. There is no finite-value guard or operation-order change.
- The local `ClientPlayerEntity` returns `true` from `canMoveVoluntarily` in both (lines 472–474) and delegates to `super.tickMovement()` (line 761), whose `LivingEntity.tickMovement` calls `travel` with the current movement input (1.14.2 line 2210; 1.14.3 line 2220).
- For the dry path, `LivingEntity.travel` first passes its voluntary-movement gate, then the `!isInsideWater()` and `!isInLava()` gates (1.14.2 lines 1769–1780; 1.14.3 lines 1779–1790). When `isFallFlying()` is true, the gliding branch reads the current field directly as `float l = this.pitch * (float) (Math.PI / 180.0)` (1.14.2 line 1787; 1.14.3 line 1797), then uses `cos(l)` and, for negative `l`, `sin(l)` in the velocity update. It does not read `prevPitch` or interpolate rotation. `isFallFlying()` returns the active fall-flying flag in both (1.14.2 line 2596; 1.14.3 line 2604).

Only the source-only audit was performed; no implementation, tests, runtime launch, source generation, product/research/wiki/Git inspection, or author material access.

## Phase two: comparison against the frozen source report

The assigned report was read only after verifying its literal whole-file SHA-256: `14f98ed4f76770b68a14bc55274c116d06fd2e7e0160c2332033ac946aba86c9` (6,087 bytes). My independent bounded verdict agrees: no movement-behavior difference was found in the direct ordinary local pitch response, current/previous pitch casts and clamps, or dry active-Elytra current-pitch read. The report’s claimed manifest and source identities align with the independently verified version-root manifests and source bytes. I also confirmed the intermediary and Yarn mapping input URL/hash pairs in both manifests.

One precision note for operation-order claims: the report summarizes `changeLookDirection` by listing the pitch writes and clamps, while omitting the interleaved yaw writes. The exact sequence in both source bodies is: compute double `f = e * 0.15` and `g = d * 0.15`; cast/write current pitch; cast/write yaw; clamp current pitch; cast/write previous pitch; cast/write previous yaw; clamp previous pitch; then vehicle callback. This omission does not change the no-difference verdict, but the pitch summary alone is not a complete literal instruction sequence.

## Exact path and publication-sidecar check

The version-root layout is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.2\yarn\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.3\yarn\`; each version root has only a `yarn` directory. Each canonical source root contains `com\`, `net\`, and `.decompile-provenance.json`; there are no additional version-root/source-root files alongside those entries. The manifest paths are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.2\yarn\.decompile-provenance.json` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.3\yarn\.decompile-provenance.json`. Their nested `sources` maps bind the cited source files and matched their actual hashes. Thus this evidence is version-root source evidence with exact manifest pins; it is not a mapping-root source tree.

The separate cache roots are `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.14.2\` and `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.14.3\`. Both contain `client-intermediary.jar` and `client-yarn.jar` with the exact local hashes stated in the assigned report. Both also contain `client.jar.pin.json` and `version.json.pin.json`. Neither cache root contains `client-intermediary.jar.pin.json` or `client-yarn.jar.pin.json`; those are generated cache outputs, not the exact intermediary/Yarn mapping-input artifacts. The exact mapping-input identities are the URLs and hashes in each version-root manifest’s `inputs` map.

No separately named ready/source/artifact publication sidecars were present in either checked version root or canonical source root beyond `.decompile-provenance.json`. The nested `sources` map and the separate cached jars do not establish that such sidecars were published. Keep the publication-sidecar gate open until the owner supplies or confirms the exact required filenames and placement; this does not invalidate the source/mapping pins or change the bounded no-difference result.