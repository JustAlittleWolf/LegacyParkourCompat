# Elytra near-vertical look path: 1.9.3 → 1.9.4

## Bounded verdict

For an already-active, dry, locally controlled player taking the Elytra branch of `LivingEntity.moveRelative`, the paired sources show **no 1.9.3→1.9.4 change** in the look-vector LUT, horizontal denominator, sign/zero gates, velocity writes, or call to `move`. The full `LivingEntity.java`, `Entity.java`, and `MathHelper.java` files have identical SHA-256 digests across the pair. This is a no-difference result for this operation only; it does not close pair coverage.

## Exact-source identity and integrity

Mapping family is Ornithe Feather. The local 1.9.3 source provenance pins the exact client artifact (`b6985b0d…/client.jar`, SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`), Feather metadata for `1.9.3`, and `feather-gen2-1.9.3+build.2-mergedv2.jar` (SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`). 1.9.4's ready/provenance markers identify exact release `1.9.4`, mapping option `feather`, Feather build 2 artifacts, and client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. The 1.9.4 source-manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; it matches the ready marker.

For each compared file, I checked actual file bytes against the release's source hash manifest/provenance. Both releases declare and actually hash to the same values:

| Source | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

## Exact behavior in both releases

- `LivingEntity.java:1302–1338`: `moveRelative` takes the existing `isFallFlying()` path when locally controlled (or when logical-side movement is being updated), and outside water/lava for a non-flying player. It obtains `vec3d = getLookVector()`, computes `i = pitch * (float)(Math.PI / 180.0)`, then `j = sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)` and horizontal speed `k = sqrt(velocityX² + velocityZ²)`.
- `LivingEntity.java:1319–1324`: the dive-pull contribution is gated by both `velocityY < 0.0` and `j > 0.0`; it computes `n = velocityY * -0.1 * m` and adds `vec3d.x * n / j`, `vec3d.z * n / j` to horizontal velocity.
- `LivingEntity.java:1326–1330`: for upward camera pitch (`i < 0.0F`), it computes `o = k * -MathHelper.sin(i) * 0.04`, raises vertical velocity by `o * 3.2`, and subtracts `vec3d.x * o / j` and `vec3d.z * o / j`. **This branch has no `j > 0.0` guard before division.** For the requested ordinary finite near-vertical look, preserve the exact small horizontal look components and resulting positive denominator; do not replace it with a normalized vector or add a threshold. No zero/NaN or crafted-input behavior is claimed here.
- `LivingEntity.java:1333–1335`: a separate `j > 0.0` guard gates horizontal speed alignment. Afterward, the code applies `0.99F`, `0.98F`, `0.99F` velocity damping in X/Y/Z order and calls `move(velocityX, velocityY, velocityZ)` at line 1340.
- `LivingEntity.java:1817–1829` and `Entity.java:1124–1130`: `getLookVector()` uses current pitch and head yaw (tick delta `1.0F`); the rotation vector is formed from float yaw/pitch radian expressions and `MathHelper.sin/cos` table lookups. Its horizontal coordinates are `g * h` and `f * h`, with `h = -MathHelper.cos(-pitch * (float)(PI / 180.0F))`.
- `MathHelper.java:15–20`: `sin(float x)` indexes `SINE_TABLE[(int)(x * 10430.378F) & 65535]`; `cos(float x)` indexes `SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]`. The explicit float constants, truncating cast, offset-before-cast and mask are part of the path.

These statements describe the reachable movement branch and source math, not a measured trajectory. The active-flight transition, server packet handling, other movement branches, collision response, and exact intermediate patch boundary are outside this bounded check.

