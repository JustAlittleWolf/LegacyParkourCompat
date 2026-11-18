# C source slice: ordinary local yaw and dry relative acceleration

## Result

**No 1.14.2→1.14.3 behavioral difference was found in this assigned slice.** The ordinary cursor path updates both `yaw` and `prevYaw` using the same double delta and float cast in the same order. It adds no finite check or yaw wrap. The first ordinary dry, non-fall-flying travel branch derives its acceleration from grounded state and then rotates relative input using the current `yaw` in both releases. This closes only the assigned source slice; it is not a full pair coverage or audit claim.

## Exact source identity

Read-only canonical nested source trees:

- `decompiled_minecraft/1.14.2/yarn/`: Minecraft 1.14.2, Yarn `1.14.2+build.7`; client JAR SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; Yarn mapping JAR SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`. Nested `.decompile-provenance.json` SHA-256 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`.
- `decompiled_minecraft/1.14.3/yarn/`: Minecraft 1.14.3, Yarn `1.14.3+build.13`; client JAR SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; Yarn mapping JAR SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`. Nested `.decompile-provenance.json` SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`.

For each side, the nested provenance `sources` digest was checked against the bytes of `Mouse.java`, `Entity.java`, `LivingEntity.java`, and `MathHelper.java`; all matched. The source SHA-256s (identical across versions where noted) are:

| Relative source | 1.14.2 SHA-256 | 1.14.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/Mouse.java` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` | same |
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` | same |
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| `net/minecraft/block/Block.java` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` | `d8728c19555875aee8f47ea138bf836d02acf9076d69fce289baad93c60e715f` |

## Paired operation trace

- **Ordinary cursor route:** `Mouse.updateMouse()` calls `client.player.changeLookDirection(j, k * n)` when the cursor is locked, the window focused, and a player exists (both `Mouse.java:184–220`; byte-identical source). In `Entity.changeLookDirection`, both releases compute `double f = e * 0.15` and `double g = d * 0.15`, then write `yaw = (float)(yaw + g)` before `prevYaw = (float)(prevYaw + g)`. Only pitch is clamped. No `Float.isFinite`, `Double.isFinite`, or yaw wrapping appears in this routine. References: 1.14.2 `Entity.java:315–327`; 1.14.3 `Entity.java:314–326`. The compared bodies are text-identical.
- **First ordinary dry travel acceleration:** In `LivingEntity.travel`, after water/lava and fall-flying gates, the normal branch computes slipperiness `v`, then `updateVelocity(method_18802(v), arg)` (`1.14.2 LivingEntity.java:1823–1829`; `1.14.3:1833–1839`). `method_18802` returns `getMovementSpeed() * (0.21600002F / (v*v*v))` on ground, otherwise `field_6281`, initialized to `0.02F` (`1.14.2:142`, `1944–1946`; `1.14.3:143`, `1954–1956`). No change was found in these expressions/gates.
- **Yaw contribution:** `Entity.updateVelocity` passes `this.yaw` to `movementInputToVelocity`; the latter normalizes only when input length squared is greater than one, scales by the acceleration, obtains float sine/cosine from `MathHelper`, then returns `(x*cos-z*sin, y, z*cos+x*sin)`. References: 1.14.2 `Entity.java:1011–1026`; 1.14.3 `Entity.java:1055–1070`. The bodies match. `MathHelper.sin/cos` table indexing also matches (`MathHelper.java:9–30`).

## Numeric witness

Use a valid ordinary mouse state: cursor locked and focused, player present, smooth camera off, sensitivity `0.5F`, and accumulated horizontal cursor delta `600.0`. `Mouse.updateMouse` yields sensitivity factor `0.5`, cubic scale `1.0`, and horizontal delta `j=600`; the cursor path passes it unchanged. `changeLookDirection` therefore adds `600 * 0.15 = 90` degrees to current and previous yaw. Starting both at `0`, both become `90F` in either release (no clamp/wrap is applied to yaw).

On the next normal dry travel with forward input `(0,0,1)`, yaw `90F`, ordinary flat-block slipperiness `v=0.6F` (the block settings default, `Block.java:842` / `1.14.3:839`), and player movement speed `0.1F` (`PlayerEntity.initAttributes`, `1.14.2:170–175` / `1.14.3:171–176`), the ground acceleration scalar is approximately `0.1F` (`0.21600002F / (0.6F³)` is approximately one); the airborne scalar is `0.02F`. The unchanged rotation gives approximately `(-0.1,0,0)` grounded and `(-0.02,0,0)` airborne (the lookup-table cosine contributes only a negligible near-zero Z residual at 90°). This demonstrates the source's current-yaw contribution for a reachable local turn; it does not create a 1.14.2/1.14.3 delta.

