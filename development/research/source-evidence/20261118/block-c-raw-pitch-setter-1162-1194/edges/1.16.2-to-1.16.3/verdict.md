# 1.16.2 → 1.16.3 direct `Entity.turn` pitch response audit

## Frozen verdict

**No source difference in the assigned operation or its ordinary active local Elytra path.** The 1.16.2 and 1.16.3 Mojmap `Entity.turn`, float clamp helper, normal mouse-input caller, local player tick route, and Elytra travel consumer are source-byte-identical under the verified source hashes below. The behavior described here is present in both releases; this pair supplies no 1.16.2→1.16.3 implementation delta for it.

Scope: ordinary direct invocation of `Entity.turn(double, double)` inherited by `LocalPlayer`; pitch response order and types; a legal mouse-input path whose first float pitch result is greater than 360 degrees; and the pitch read by real local-player Elytra travel. No scheduled/injected callback is needed or considered.

## Exact source identities

**1.16.2 Mojmap.** Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.2\mojmap`. Its ready marker identifies version `1.16.2`, mapping `mojmap`, status `ready`, 3,516 source files, and client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. The ready marker's declared source-manifest SHA-256 is `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; artifact-manifest SHA-256 is `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`. Actual sidecar hashes match those declarations. Ready-marker file SHA-256: `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; provenance file SHA-256: `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`. The pinned artifact manifest records client mappings `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`, mapped client jar `559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90`, and raw client jar as listed in the ready marker.

**1.16.3 Mojmap.** Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.3\mojmap`. This tree has no ready marker; its hidden `.decompile-provenance.json` was used as provenance, SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. Its recorded pipeline is `lpc-decompile-v1` with single-thread remapping/decompilation, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, and Java 25.0.3+9-LTS. The version metadata, client, and Mojang client-mappings inputs are locally pinned and their cached bytes match the provenance inputs: `version.json` SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; `client.jar` SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; `client_mappings.txt` SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`. The provenance records the version metadata URL ending `/1.16.3.json` and the mapping URL ending `/client.txt`.

### Cited Java source hashes

For every file below, the 1.16.2 manifest hash and the 1.16.3 provenance source hash both match the current file bytes, and the hash is identical across releases:

| Mojmap source file | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| `net/minecraft/client/Option.java` | `3cf6e755e1fb3e406220fbabebf9a2603d05d228d61788013863cce9426adb7d` |
| `net/minecraft/client/Options.java` | `8682739229287d53940441fcaa6a6d034cb52a60a85ee6246a987e27c9329fba` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |

## Source trace and reachability

- `client/MouseHandler.java:202` accumulates cursor Y displacement. `turnPlayer` at lines 212–242 computes `f = sensitivity * 0.6F + 0.2F`, `g = f * f * f * 8.0`, applies `g` to the accumulated cursor deltas, and calls `minecraft.player.turn(j, k * n)` when the ordinary player reference is non-null. `client/Option.java:234–240` exposes sensitivity on the normal 0.0–1.0 slider. Cursor delta is not clamped in this path.
- Thus sensitivity 1.0 and a normal 600-pixel vertical cursor displacement provide a legal source-context example: `f` is about 0.8, `g` about 4.096, the pitch argument to `turn` about 2457.6, and the `turn` increment `e * 0.15` about 368.64 degrees. This proves the first setter can produce a post-cast float above 360 without an artificial scheduled call. The final clamp immediately reduces it to +90 degrees (or -90 for the opposite direction).
- `world/entity/Entity.java:340–350`: `turn` first evaluates `double f = e * 0.15`, then stores current pitch as `(float)(this.xRot + f)`. Addition is in double precision because `f` is double; the result is narrowed to float before `Mth.clamp`. It next stores yaw, clamps current pitch with float bounds `-90.0F, 90.0F`, adds the same double delta to `xRotO` and narrows that separate result to float, updates `yRotO`, and then clamps `xRotO` with the same float bounds. The passenger notification follows these writes. There is no modulo/wrap in this method.
- `util/Mth.java:94–99` defines the float clamp as: if value `<` lower bound, return lower bound; otherwise if value `>` upper bound, return upper bound; otherwise return value. It uses float arguments/comparisons and performs the lower comparison before the upper comparison.
- The local-player route is concrete: `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`, which extends `LivingEntity`, which extends `Entity`. `LocalPlayer.aiStep` (`client/player/LocalPlayer.java:627–788`) includes the Elytra start path and calls `super.aiStep()`. `Player.aiStep` (`world/entity/player/Player.java:483`) calls `super.aiStep()`. `LivingEntity.aiStep` calls `updateFallFlying()` and then `travel(...)` at lines 2457–2459. `Player.travel` falls through to `super.travel` when the player is not in ability flight; `LivingEntity.travel` line 1979 has the active `isFallFlying()` branch. There `float j = this.xRot * (float)(Math.PI / 180.0)` at line 1986 reads the current pitch field, and the branch also obtains its look vector from `getLookAngle()` (which uses current `xRot`). It does not use `xRotO` for the Elytra angle. The ordinary direct turn therefore affects the pitch used by the real local Elytra travel on that tick.
- The cited Java files have equal full-file SHA-256 across 1.16.2 and 1.16.3. Their relevant methods and the call context are therefore byte-identical in this pair: the reachable >360 intermediate, cast-before-clamp sequence, float clamp order, and current-pitch Elytra consumption are shared behavior, not a version difference.

No runtime test or launch was performed; this is an exact-source audit only.

## Phase-two independent comparison

Compared only against the frozen author report at `c-raw-pitch-setter-1162-1163-source/report.md`, SHA-256 `4450a902b2fdd8dd699256f981361659b73af20dfe393ebaa62913f3af6d782d`. Its conclusion agrees: `Entity.turn` has no 1.16.2→1.16.3 difference; the double increment is narrowed to float before the current-pitch clamp, the previous-pitch update is separate, and active Elytra `LivingEntity.travel` reads current `xRot`. Its cited `Entity.java` and `LivingEntity.java` hashes match this audit's frozen source hashes. No technical contradiction found.

The author report's direct example `turn(0.0, 3000.0)` demonstrates the >360 intermediate using an ordinary finite argument. This audit additionally traces a normal `MouseHandler` input path that can yield a post-float value above 360 without scheduling. The author's claim that all 36 1.16.3 inputs were found and rehashed is broader than this independent audit's local pin verification: I checked the version metadata, client jar and client-mappings local pins, but did not independently rehash all 36 inputs. This evidence-scope difference does not affect the compared Java claim or the matching verdict.