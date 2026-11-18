# C — Entity yaw precision, 1.16.2 → 1.16.3

## Exact source identity

- **1.16.2, Mojmap:** `decompiled_minecraft/1.16.2/mojmap`. The ready marker identifies version `1.16.2`, Mojmap, and client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. Its source-manifest SHA-256 is `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; artifact-manifest SHA-256 is `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`. The Mojmap artifact is `1.16.2/client-mojmap.jar`, SHA-256 `559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90`.
- **1.16.3, Mojmap:** `decompiled_minecraft/1.16.3/mojmap`. The embedded `.decompile-provenance.json` SHA-256 is `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. It pins release metadata `1.16.3` (SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`), client jar (SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`), and Mojang `client.txt` mappings (SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`).
- The compared `Entity.java` bytes match across releases (SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`); `LivingEntity.java` bytes also match (SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`). These hashes agree with the 1.16.2 source manifest and 1.16.3 embedded source map.

## Bounded comparison

In both versions, `Entity.turn(double d, double e)` computes `g = d * 0.15` as a double, then assigns `yRot = (float)(yRot + g)`; after the current pitch update/clamp, it separately assigns `yRotO = (float)(yRotO + g)`. Thus current and previous yaw retain separate float state, with the same addition-before-cast order and no yaw clamp in this method. The exact text is identical: `Entity.java:340–347`.

For the first ordinary dry movement contribution, `LivingEntity.travel(Vec3)` reaches the non-water, non-lava, non-fall-flying branch at `LivingEntity.java:2022–2026` for either ground or air movement, then `handleRelativeFrictionAndCalculateMovement` calls `moveRelative` at `LivingEntity.java:2062–2065`. The inherited `Entity.moveRelative` passes the stored current `this.yRot` to `getInputVector` and adds its result to delta movement (`Entity.java:1075–1077`). Those call boundaries and statements are byte-identical in both versions.

## Result

**No difference in the assigned slice.** Direct local `Entity.turn` yaw arithmetic, casts, and current/previous update order are unchanged; the first dry ground/air `moveRelative` contribution still consumes current stored yaw. Because both complete compared source files are byte-identical, no finite witness is applicable. This result does not cover the trigonometric implementation inside `getInputVector`, other yaw consumers, or excluded movement branches.
