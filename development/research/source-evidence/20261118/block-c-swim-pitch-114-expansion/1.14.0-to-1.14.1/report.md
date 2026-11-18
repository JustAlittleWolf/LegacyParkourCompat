# 1.14 → 1.14.1 swim pitch vertical-velocity writer

## Result

No source difference found in the bounded operation: `PlayerEntity.travel(Vec3d)`'s swim pitch-derived vertical-velocity write is byte-identical in 1.14 and 1.14.1 (`PlayerEntity.java` SHA-256 `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` on both sides). This is a checked no-difference slice, not an implementation finding.

## Compared behavior and movement path

- 1.14 Yarn, `net/minecraft/entity/player/PlayerEntity.java`, `travel(Vec3d)`, lines 1361–1373. The writer is gated by `isSwimming() && !hasVehicle()`. It reads `double g = getRotationVector().y`, chooses `double h = g < -0.2 ? 0.085 : 0.06`, then writes only when `g <= 0.0 || jumping || !fluidAtAboveSampleIsEmpty`. If eligible, it snapshots velocity and calls `setVelocity(lv.add(0.0, (g - lv.y) * h, 0.0))`.
- 1.14.1 Yarn, same file/member/lines and identical source hash.
- For a `PlayerEntity`, `isSwimming()` excludes flying and spectator players before delegating to the swimming flag (`PlayerEntity.java:1739–1741`, both versions). Thus an ordinary active swimmer without a vehicle enters this branch; the following flying branch is not selected and `super.travel(arg)` continues through `LivingEntity.travel` (`PlayerEntity.java:1375–1387`; `LivingEntity.java:1763+`). The pitch write occurs before that inherited travel call.
- `Entity.getRotationVector()` delegates using current `pitch` and `yaw` (`Entity.java:1635–1637`, both versions). The shared helper at `Entity.java:1180–1188` computes `h = pitch * (float)(Math.PI / 180.0)`, `i = -yaw * (float)(Math.PI / 180.0)`, then float `cos/sin` intermediates and returns `new Vec3d(k * l, -m, j * l)`. Consequently the sampled Y component is float-rounded `-sin(pitchRadians)` widened into the Vec3d double component; yaw affects only X/Z. `g` and `h` in the player writer, and the velocity delta arithmetic, are doubles.
- The fluid check is the last disjunct and is short-circuited: it is evaluated only when `g > 0.0` and `jumping` is false. Its sampled position expression is exactly `new BlockPos(this.x, this.y + 1.0 - 0.1, this.z)`, evaluated before inherited movement. `BlockPos(double, double, double)` delegates to `Vec3i(double, double, double)`, which floors each coordinate. The state/fluid lookup and the underlying fluid/collision rules are outside this assignment; only the writer's sample site/order was traced.
- Therefore, for upward-looking `g <= 0`, the correction always runs; for downward-looking `g > 0`, it runs only while jumping or when that floored block above the player has a nonempty fluid state. The `0.085` coefficient applies strictly below `g == -0.2`; at `-0.2` and above the coefficient is `0.06`. The direct Y write moves current vertical velocity a fraction `h` toward `g` before ordinary inherited travel. These gates, constants, expression order and write are unchanged across the pair.

## Exact source identities

Canonical local sources are under `decompiled_minecraft/<version>/yarn/`. Provenance manifests report `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`.

| Version | Yarn mapping artifact (SHA-256) | Intermediary artifact (SHA-256) | Client JAR (SHA-256) | Provenance manifest (SHA-256) |
|---|---|---|---|---|
| 1.14 | `1.14+build.21` — `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17` | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` |
| 1.14.1 | `1.14.1+build.10` — `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |

The manifests' source hashes also match the canonical-file bytes for the examined files:

| Relative source path | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/util/math/BlockPos.java` | `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a` | `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a` |
| `net/minecraft/util/math/Vec3i.java` | `534d56ad4fd83ee0f2c2187afa2e34145de41944d1d1ad092e1a8bc5e9833c61` | `534d56ad4fd83ee0f2c2187afa2e34145de41944d1d1ad092e1a8bc5e9833c61` |

## Coverage boundary

Compared the pitch-to-vector helper, player swim pitch writer and eligibility, the above-position construction/flooring needed to characterize its sample, and the immediate inherited `LivingEntity.travel` dispatch. No difference was found in the writer path. This report makes no claim about underlying fluid-query behavior, collision, broader swim input/drag, or other tick writers. No intermediate patch release was examined, so an introduction date within 1.14–1.14.1 is not inferred.
