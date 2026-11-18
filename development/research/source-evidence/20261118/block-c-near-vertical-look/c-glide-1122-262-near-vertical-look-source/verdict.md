# Independent source audit verdict: 1.12.2 → 26.2 Elytra near-vertical look-vector gate

## Frozen input and verdict scope

- Input report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1122-262-near-vertical-look-source\report.md`
- Frozen input SHA-256: `152a5c403175787915ed6aa6b0d3e8414375e3346538418f7db25f82617b43a0`
- Verdict: **ACCEPTED, bounded to the near-vertical look-vector behavior and first active dry Elytra movement consumption described below.** This accepts the formula, input interval, and reachable player path. It does not accept overall Elytra parity, a cutover release inside the endpoint pair, or runtime parity.

## Local source identity check

Canonical read-only roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\`. Their local ready markers state exact `versionId=1.12.2`, `mapping=ornithe-feather`, Feather Gen2 build.2 mergedv2/Tiny artifact names, and exact `versionId=26.2`, `mapping=unobfuscated`; both name Vineflower. The marker-declared tree sizes match the current canonical trees: 2,050 Java files / 10,518,527 bytes and 7,055 Java files / 33,813,717 bytes respectively.

I recomputed SHA-256 for every compared source file. All ten match the frozen input report:

| Release/path relative to canonical root | SHA-256 |
|---|---|
| 1.12.2 `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| 1.12.2 `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
| 1.12.2 `net/minecraft/client/util/SmoothUtil.java` | `006deade4fbc18ce0430b996eee58b92aee778087fc73600fa45686a6de962e6` |
| 1.12.2 `net/minecraft/client/Mouse.java` | `b6c9132938c64f2b79138e87de60cee4b965e6c28d485798d7131ec2bf0fd058` |
| 26.2 `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| 26.2 `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |

Provenance limitation: the ready markers record the source-manifest and artifact-manifest hashes, but those manifests and the marker-declared original `build/movement-campaign-2026-10-07/ready/...` directories are absent from this checkout. The markers' manifest/artifact digests and raw input artifact hashes therefore could not be recomputed here. Exact release/mapping identity is confirmed from the local ready markers, and the canonical tree counts, byte totals, and all compared Java source hashes were recomputed. This verdict is source-content-bound to those direct hashes and marker metadata; it does not claim independent revalidation of the original downloaded artifacts.

## Independent source findings

**Historical lookup.** In 1.12.2 `Entity.getRotationVector(float,float)` (`Entity.java:1239–1244`) computes pitch horizontal factor `-MathHelper.cos(-pitch * (float)(Math.PI / 180.0))`. `getLookVector()` uses current `pitch` and `yaw` (`Entity.java:1690–1691`). `MathHelper.cos(float)` performs float multiply/add then truncating `int` cast and a 65,536-entry mask (`MathHelper.java:19–20`); the table is populated with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` (`:426–429`).

**26.2 lookup.** `Entity.getLookAngle()` passes `getXRot()` and `getYRot()` to `calculateViewVector` (`Entity.java:2579–2580`). `calculateViewVector` rounds the degree conversion into `float`, then uses `Mth.cos(realXRot)` for both horizontal components (`:1966–1974`). `Mth.cos(double)` evaluates the double index expression, casts to `long` (truncating toward zero), masks, and reads the sine table (`Mth.java:31–55`). Entry zero is exactly `+0.0F` from `(float)Math.sin(0)`.

**Static numeric result.** I enumerated consecutive binary32 encodings, evaluating the source-typed operations and casts above. Exactly 720 pitches produce the modern pre-mask cast value 0 and thus horizontal cosine `+0.0F`:

- Lower endpoint: `-90.0F` (`0xC2B40000`).
- Highest included float toward zero: `-89.99451446533203F` (`0xC2B3FD31`).
- The next float toward zero is `-89.9945068359375F` (`0xC2B3FD30`); its pre-cast index is `1.0004794579526788`, so it casts/masks to index 1.
- Across the 720 values, the uncast modern index expression ranges from `-0.00045592633614433` through `0.999236059959003`; every cast truncates to zero.
- The old pitch table indices for these same inputs are only 32767 or 32768. Both table entries are nonzero (`~9.58738E-05F` and `~1.2246469E-16F`). Thus the old horizontal pitch factor remains nonzero even at exactly `-90.0F`.

The interval consists entirely of finite, negative pitch values within the normal clamped camera range. This is a local static arithmetic proof from the decompiled expressions, not a game, packet, or runtime test.

**Ordinary camera reachability and movement use.** In 26.2 `MouseHandler` routes ordinary mouse deltas to `minecraft.player.turn(...)` (`MouseHandler.java:355–375`). With smooth camera enabled it uses `SmoothDouble.getNewDeltaValue`; otherwise it routes the sensitivity-scaled delta directly. `Entity.turn` applies the pitch delta and calls `setXRot`, which clamps to `[-90,90]`; `setXRot` writes the `xRot` field read by `getXRot()` (`Entity.java:489–495, 3906–3915`). This is authoritative entity rotation, not renderer-only interpolation. The enabled smooth-camera route produces fractional deltas, so ordinary camera input can sample strict interior negative pitches near the clamp; I make no claim that a specific schedule visits every one of the 720 encodings.

On a dry, already-fall-flying 26.2 tick, `LivingEntity.travel` selects `travelFallFlying` (`LivingEntity.java:2430–2437`), which calls `updateFallFlyingMovement` before moving (`:2581–2590`). That method reads `getLookAngle`, computes `lookHorLength`, and gates descent transfer, pitch-up transfer, and horizontal alignment on `lookHorLength > 0` (`:2602–2625`). For every pitch in the enumerated interval, both horizontal look components and this length are exactly zero, suppressing those gated branches.

In 1.12.2, the eligible locally controlled/non-water/non-lava-or-flying-ability `moveRelative` branch enters when `isFallFlying()` is already true (`LivingEntity.java:1425–1434`). Its first direction-sensitive calculation obtains `getLookVector()` then `j = sqrt(x² + z²)` (`:1434–1438`). At the same pitch interval the historical pitch factor is nonzero; for ordinary finite yaw the sine/cosine pair cannot both be zero, hence `j > 0`. The descent-transfer and horizontal-alignment branches execute when their velocity preconditions hold (`:1442–1447, 1456–1459`); the pitch-up branch also divides by this positive `j` (`:1449–1454`). Therefore the bounded first-movement distinction is accepted: 1.12.2 retains a nonzero direction-dependent horizontal look and unguarded positive denominator, while 26.2 has exact zero horizontal look and suppresses its explicitly gated operations.

## Acceptance boundary

Accepted: the source-level old-versus-new look-vector math, the finite negative 720-float interval including the exact `-90.0F` endpoint, its ordinary camera-to-authoritative-rotation path, and the first already-active dry Elytra movement consumption/gates.

Not established: later-26.2 Elytra force/gravity parity, other travel branches, every release boundary between 1.12.2 and 26.2, a particular mouse/frame sequence visiting every float, or any runtime parity.